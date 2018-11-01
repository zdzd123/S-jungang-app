package com.jgzy.core.shopOrder.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IAdvanceRechargeInfoService;
import com.jgzy.core.personalCenter.service.IAdvanceRechargeRecordService;
import com.jgzy.core.personalCenter.service.IUserOauthService;
import com.jgzy.core.shopOrder.service.*;
import com.jgzy.core.shopOrder.vo.CalcAmountVo;
import com.jgzy.core.shopOrder.vo.CalcSingleAmountVo;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.common.WeiXinData;
import com.jgzy.entity.common.WeiXinTradeType;
import com.jgzy.entity.po.*;
import com.jgzy.utils.BigDecimalUtil;
import com.jgzy.utils.CommonUtil;
import com.jgzy.utils.WeiXinPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.util.*;

@RefreshScope
@RestController
@RequestMapping("/api/shopGoodsOriginatorOrder")
@Api(value = "合伙人下单接口", description = "合伙人下单接口")
public class ShopGoodsOriginatorOrderController {
    @Autowired
    private IShopGoodsOrderService shopGoodsOrderService;
    @Autowired
    private IShopGoodsOrderDetailService shopGoodsOrderDetailService;
    @Autowired
    private IUserOauthService userOauthService;
    @Autowired
    private IShopGoodsService shopGoodsService;
    @Autowired
    private IOriginatorInfoService originatorInfoService;
    @Autowired
    private IAdvanceRechargeRecordService advanceRechargeRecordService;
    @Autowired
    private IAdvanceRechargeInfoService advanceRechargeInfoService;
    @Autowired
    private IUserFundService userFundService;
    @Autowired
    private IShopStockService shopStockService;

    @ApiOperation(value = "添加订单唤起微信支付", notes = "添加订单唤起微信支付")
    @PostMapping(value = "/weixin/save")
    @Transactional
    public ResultWrapper<Map<String, String>> weixinSave(@RequestBody @Validated List<ShopGoodsOrderVo> voList, BindingResult result) throws Exception {
        ResultWrapper<Map<String, String>> resultWrapper = new ResultWrapper<>();
        if (result.hasErrors()) {
            resultWrapper.setErrorMsg(result.getFieldError().toString());
            return resultWrapper;
        }
        // 获取用户权额
        List<AdvanceRechargeRecord> advanceRechargeRecordList = advanceRechargeRecordService.selectList(
                new EntityWrapper<AdvanceRechargeRecord>()
                        .eq("user_id", UserUuidThreadLocal.get().getId())
                        .ne("amount", 0)
                        .orderBy("level_id DESC"));
        List<Integer> shopGoodsIds = new ArrayList<>();
        voList.forEach(shopGoodsOrderVo -> shopGoodsIds.add(shopGoodsOrderVo.getShopGoodsId()));
        // 查询商品
        List<ShopGoods> shopGoodsList = shopGoodsService.selectList(
                new EntityWrapper<ShopGoods>().in("id", shopGoodsIds));
        if (shopGoodsList == null || shopGoodsList.size() != voList.size()) {
            throw new OptimisticLockingFailureException("商品id有误");
        }
        // 计算订单金额
        CalcAmountVo calcAmountVo = calcAmount(voList, advanceRechargeRecordList, shopGoodsList);
        // 生成权额信息
        if (calcAmountVo.getActualAmount() == null || calcAmountVo.getActualAmount().compareTo(new BigDecimal(0)) == 0) {
            // 权额支付信息
            List<AdvanceRechargeRecord> myAdvanceRechargeRecordList = initAdvanceRechargeRecordByAmount(calcAmountVo, advanceRechargeRecordList);
            // 余额充足 1.更新权额 2.插入订单
            boolean updateBatchById = advanceRechargeRecordService.updateBatchById(myAdvanceRechargeRecordList);
            if (!updateBatchById) {
                throw new OptimisticLockingFailureException("权额更新失败");
            }
            // 插入订单
            ShopGoodsOrder shopGoodsOrder = initShopGoodsOrder(11, calcAmountVo, BaseConstant.PAY_TYPE_3);
            boolean successful = shopGoodsOrderService.insert(shopGoodsOrder);
            if (!successful) {
                throw new OptimisticLockingFailureException("订单插入失败");
            }
            // 插入订单详情
            List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = initShopGoodsOrderDetail(voList, shopGoodsOrder, shopGoodsList);
            boolean batch = shopGoodsOrderDetailService.insertBatch(shopGoodsOrderDetailList);
            if (!batch) {
                throw new OptimisticLockingFailureException("订单详情插入失败");
            }
            // 存入库存
            for (ShopGoodsOrderDetail shopGoodsOrderDetail : shopGoodsOrderDetailList) {
                ShopStock shopStock = new ShopStock();
                shopStock.setUserInfoId(UserUuidThreadLocal.get().getId());
                shopStock.setShopGoodsId(shopGoodsOrderDetail.getShopGoodsId());
                shopStock.setCount(shopGoodsOrderDetail.getBuyCount());
                shopStock.setDescribe(shopGoodsOrderDetail.getOrderId().toString());
                ShopStock stock = shopStockService.selectOne(
                        new EntityWrapper<ShopStock>()
                                .eq("user_info_id", UserUuidThreadLocal.get().getId())
                                .eq("shop_goods_id", shopGoodsOrderDetail.getShopGoodsId()));
                if (stock == null) {
                    shopStock.setCreateTime(new Date());
                } else {
                    shopStock.setUpdateTime(new Date());
                    if (shopStock.getDescribe().length() > 700) {
                        shopStock.setDescribe(stock.getDescribe().substring(stock.getDescribe().length() - 300) + "," + shopStock.getDescribe());
                    }
                }
                boolean update = shopStockService.update(shopStock, new EntityWrapper<ShopStock>()
                        .eq("user_info_id", UserUuidThreadLocal.get().getId())
                        .eq("shop_goods_id", shopGoodsOrderDetail.getShopGoodsId()));
                if (!update) {
                    throw new OptimisticLockingFailureException("库存插入失败");
                }
            }
            // 插入流水
            UserFund userFund = new UserFund();
            userFund.setTradeUserId(UserUuidThreadLocal.get().getId());
            userFund.setDecreaseMoney(shopGoodsOrder.getTotalRealPayment());
            userFund.setOrderNo(shopGoodsOrder.getOrderNo());
            userFund.setTradeType(BaseConstant.TRADE_TYPE_2);
            userFund.setTradeDescribe("权额支付");
            userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_8);
            userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
            userFund.setPayType(BaseConstant.PAY_TYPE_3);
            userFundService.InsertUserFund(userFund);
        } else {
            // 第三方支付
            String out_trade_no = CommonUtil.getTradeNo();
            BigDecimal actualAmount = calcAmountVo.getActualAmount();
            String ip = InetAddress.getLocalHost().getHostAddress();
            // 插入预付单
            ShopGoodsOrder shopGoodsOrder = initShopGoodsOrder(1, calcAmountVo, BaseConstant.PAY_TYPE_2);
            shopGoodsOrder.setTradeNo(out_trade_no);
            boolean successful = shopGoodsOrderService.insert(shopGoodsOrder);
            if (!successful) {
                throw new OptimisticLockingFailureException("订单插入失败");
            }
            // 插入订单详情
            List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = initShopGoodsOrderDetail(voList, shopGoodsOrder, shopGoodsList);
            boolean batch = shopGoodsOrderDetailService.insertBatch(shopGoodsOrderDetailList);
            if (!batch) {
                throw new OptimisticLockingFailureException("订单详情插入失败");
            }
            Map<String, String> resultMap = weiXinPay(ip, out_trade_no, actualAmount, "");
            resultWrapper.setResult(resultMap);
            return resultWrapper;
        }
        return resultWrapper;
    }

    /**
     * 微信支付
     *
     * @param ip
     * @param out_trade_no
     * @param actualAmount
     * @param order_ids
     * @return
     * @throws IOException
     */
    private Map<String, String> weiXinPay(String ip, String out_trade_no, BigDecimal actualAmount, String order_ids) throws IOException {
        Map<String, String> resultMap = new HashMap<>();
        // 权额不足 唤起第三方支付
        // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        String subject = "军港之业合伙人订单"; // 订单名称 (必填)
        // 获取服务器ip
        UserOauth userOauth = userOauthService.selectOne(
                new EntityWrapper<UserOauth>()
                        .eq("user_id", UserUuidThreadLocal.get().getId()));
        if (userOauth == null || userOauth.getOauthOpenid() == null) {
            resultMap.put("return_code", "FAIL");
            resultMap.put("err_code_des", "openid is null");
            return resultMap;
        }
        String openid = userOauth.getOauthOpenid(); // 微信认证 openid (必填)
        String product_id = ""; // 产品id (非必填)
        String notify_url = "http://jgapi.china-mail.com.cn/api/payNotify/constant/weixinNotifyUrl";
        // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
        // 检验订单状态以及订单的金额
        WeiXinData wxData = WeiXinPayUtil.makePreOrder(WeiXinTradeType.JSAPI, openid, product_id,
                out_trade_no, subject, actualAmount.doubleValue(), ip, notify_url);
        // 订单失败
        if (wxData.hasKey("return_code") && wxData.get("return_code").equals("FAIL")) {
            resultMap.put("return_code", wxData.get("return_code"));
            if (wxData.get("return_msg") != null) {
                resultMap.put("return_msg", wxData.get("return_msg"));
            } else if (wxData.get("err_code_des") != null) {
                resultMap.put("err_code_des", wxData.get("err_code_des"));
            }
            return resultMap;
        }
        resultMap.put("appId", wxData.get("appId"));
        resultMap.put("nonceStr", wxData.get("nonceStr"));
        resultMap.put("timeStamp", wxData.get("timeStamp"));
        resultMap.put("signType", wxData.get("signType"));
        resultMap.put("packageValue", wxData.get("package"));
        resultMap.put("sign", wxData.get("sign"));
        resultMap.put("order_no", out_trade_no);
        resultMap.put("order_ids", order_ids);
        return resultMap;
    }

    /**
     * 通过金额得出权额支付信息
     *
     * @param calcAmountVo
     * @param advanceRechargeRecordList
     * @return
     */
    private List<AdvanceRechargeRecord> initAdvanceRechargeRecordByAmount(CalcAmountVo calcAmountVo, List<AdvanceRechargeRecord> advanceRechargeRecordList) {
        List<AdvanceRechargeRecord> myAdvanceRechargeRecordList = new ArrayList<>();
        for (CalcSingleAmountVo calcSingleAmountVo : calcAmountVo.getCalcAmountVoList()) {
            String[] split = calcSingleAmountVo.getAdvanceIds().split(",");
            for (String id : split) {
                AdvanceRechargeRecord myRecord = new AdvanceRechargeRecord();
                for (AdvanceRechargeRecord advanceRechargeRecord : advanceRechargeRecordList) {
                    if (myRecord.getAmount().compareTo(calcSingleAmountVo.getAdvanceAmount()) == 0) {
                        break;
                    }
                    if (!id.equals(advanceRechargeRecord.getId().toString())) {
                        continue;
                    }
                    myRecord.setId(Integer.valueOf(id));
                    if (calcSingleAmountVo.getAdvanceAmount().compareTo(advanceRechargeRecord.getAmount()) > 0) {
                        // 更新金额
                        myRecord.setAmount(new BigDecimal(0));
                        BigDecimal subtract = calcSingleAmountVo.getAdvanceAmount().subtract(advanceRechargeRecord.getAmount());
                        // 更新余额
                        advanceRechargeRecord.setAmount(subtract);
                    } else {
                        BigDecimal subtract = advanceRechargeRecord.getAmount().subtract(calcSingleAmountVo.getAdvanceAmount());
                        // 更新金额
                        myRecord.setAmount(subtract);
                        // 更新余额
                        advanceRechargeRecord.setAmount(new BigDecimal(0));
                    }
                }
                myAdvanceRechargeRecordList.add(myRecord);
            }
        }
        return myAdvanceRechargeRecordList;
    }

    /**
     * 初始化订单数据
     *
     * @param type         1-待支付  3-待收货  11-交易关闭
     * @param calcAmountVo
     * @param payType
     * @return
     */
    private ShopGoodsOrder initShopGoodsOrder(Integer type, CalcAmountVo calcAmountVo, int payType) {
        ShopGoodsOrder shopGoodsOrder = new ShopGoodsOrder();
        shopGoodsOrder.setSubmitOrderUser(UserUuidThreadLocal.get().getId());
        // 地址 不是预付单则拼接地址
        shopGoodsOrder.setReceiveAddress("预付单");
        // 金额
        for (CalcSingleAmountVo calcSingleAmountVo : calcAmountVo.getCalcAmountVoList()) {
            // 订单总额
            shopGoodsOrder.setOrderAmountTotal(shopGoodsOrder.getOrderAmountTotal() == null ?
                    calcSingleAmountVo.getSingleTotalAmount() :
                    shopGoodsOrder.getOrderAmountTotal().add(calcSingleAmountVo.getSingleTotalAmount()));
            // 权额支付金额
            shopGoodsOrder.setAdvanceAmount(shopGoodsOrder.getAdvanceAmount() == null ?
                    calcSingleAmountVo.getAdvanceAmount() :
                    shopGoodsOrder.getAdvanceAmount().add(calcSingleAmountVo.getAdvanceAmount()));
            // 耗材费
            //shopGoodsOrder.setMaterialAmount(calcSingleAmountVo.getMaterialAmount());
            // 实际付款
            shopGoodsOrder.setTotalRealPayment(shopGoodsOrder.getTotalRealPayment() == null ?
                    calcSingleAmountVo.getSingleRealAmount() :
                    shopGoodsOrder.getTotalRealPayment().add(calcSingleAmountVo.getSingleRealAmount()));
            // 优惠金额
            shopGoodsOrder.setCouponAmount(shopGoodsOrder.getCouponAmount() == null ?
                    calcSingleAmountVo.getSingleRateAmount() :
                    shopGoodsOrder.getCouponAmount().add(calcSingleAmountVo.getSingleRateAmount()));
        }
        // 支付类型:权额支付
        shopGoodsOrder.setPayType(payType);
        // 订单号
        String tradeNo = BaseConstant.PRE_ORDER + CommonUtil.getTradeNo();
        shopGoodsOrder.setOrderNo(tradeNo);
        // 订单来源（IOS=1|Android=2|Service(后台)=3|Live(直播)=4）
        //shopGoodsOrder.setOrderSource(shopGoodsOrderVo.getOrderSource());
        // 下单时间
        Date date = new Date();
        shopGoodsOrder.setCreateTime(date);
        if (type == 1) {
            // 订单状态
            shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_1);
        } else if (type == 3) {
            // 订单状态
            shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_3);
            shopGoodsOrder.setPayTime(date);
        } else if (type == 11) {
            // 订单状态
            shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_11);
            shopGoodsOrder.setPayTime(date);
        }
        return shopGoodsOrder;
    }

    /**
     * 初始化订单详情
     *
     * @param shopGoodsOrderVoList
     * @param shopGoodsOrder
     * @param shopGoodsList
     * @return
     */
    private List<ShopGoodsOrderDetail> initShopGoodsOrderDetail(List<ShopGoodsOrderVo> shopGoodsOrderVoList,
                                                                ShopGoodsOrder shopGoodsOrder,
                                                                List<ShopGoods> shopGoodsList) {
        Date date = new Date();
        List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = new ArrayList<>();
        for (ShopGoodsOrderVo shopGoodsOrderVo : shopGoodsOrderVoList) {
            ShopGoods shopGoods = null;
            for (ShopGoods myShopGoods : shopGoodsList) {
                if (myShopGoods.getId().equals(shopGoodsOrderVo.getShopGoodsId())) {
                    shopGoods = myShopGoods;
                    break;
                }
            }
            if (shopGoods == null) {
                throw new OptimisticLockingFailureException("该id商户有误" + shopGoodsOrderVo.getShopGoodsId());
            }

            ShopGoodsOrderDetail shopGoodsOrderDetail = new ShopGoodsOrderDetail();
            shopGoodsOrderDetail.setOrderId(shopGoodsOrder.getId());
            shopGoodsOrderDetail.setShopGoodsId(shopGoodsOrderVo.getShopGoodsId());
            shopGoodsOrderDetail.setSortName(shopGoods.getShopName());
            shopGoodsOrderDetail.setPic(shopGoods.getPic());
            shopGoodsOrderDetail.setMarketPrice(shopGoods.getMarketPrice());
            shopGoodsOrderDetail.setCostPrice(shopGoods.getCostPrice());
            shopGoodsOrderDetail.setMenberPrice(shopGoods.getMenberPrice());
            if (shopGoods.getGoodsUnit() != null && shopGoods.getGoodsUnit().equals("箱")) {
                shopGoodsOrderDetail.setBuyCount(shopGoodsOrderVo.getCount() * shopGoods.getGoodsUnitCount());
            } else {
                shopGoodsOrderDetail.setBuyCount(shopGoodsOrderVo.getCount());
            }
            shopGoodsOrderDetail.setAddTime(date);
            shopGoodsOrderDetailList.add(shopGoodsOrderDetail);
        }
        return shopGoodsOrderDetailList;
    }

    @ApiOperation(value = "确认订单金额", notes = "确认订单金额")
    @PostMapping(value = "/calcAmountForShow")
    public ResultWrapper<CalcAmountVo> calcAmountForShow(@RequestBody @Validated List<ShopGoodsOrderVo> voList, BindingResult result) throws Exception {
        ResultWrapper<CalcAmountVo> resultWrapper = new ResultWrapper<>();
        if (result.hasErrors()) {
            resultWrapper.setErrorMsg(result.getFieldError().toString());
            return resultWrapper;
        }
        // 获取用户权额
        List<AdvanceRechargeRecord> advanceRechargeRecordList = advanceRechargeRecordService.selectList(
                new EntityWrapper<AdvanceRechargeRecord>()
                        .eq("user_id", UserUuidThreadLocal.get().getId())
                        .ne("amount", 0)
                        .orderBy("level_id DESC"));
        List<Integer> shopGoodsIds = new ArrayList<>();
        voList.forEach(shopGoodsOrderVo -> shopGoodsIds.add(shopGoodsOrderVo.getShopGoodsId()));
        // 查询商品
        List<ShopGoods> shopGoodsList = shopGoodsService.selectList(
                new EntityWrapper<ShopGoods>().in("id", shopGoodsIds));
        if (shopGoodsList == null || shopGoodsList.size() != voList.size()) {
            throw new OptimisticLockingFailureException("商品id有误");
        }
        // 计算订单金额
        CalcAmountVo calcAmountVo = calcAmount(voList, advanceRechargeRecordList, shopGoodsList);
        if (!CollectionUtils.isEmpty(advanceRechargeRecordList)) {
            calcAmountVo.setAdvanceFlag(true);
        }
        resultWrapper.setResult(calcAmountVo);
        return resultWrapper;
    }

    /**
     * 计算订单金额
     *
     * @param voList
     * @return
     * @throws OptimisticLockingFailureException
     */
    private CalcAmountVo calcAmount(List<ShopGoodsOrderVo> voList, List<AdvanceRechargeRecord> advanceRechargeRecordList,
                                    List<ShopGoods> shopGoodsList) throws OptimisticLockingFailureException {
        CalcAmountVo calcAmountVo = new CalcAmountVo();
        List<CalcSingleAmountVo> singleCalcAmountVoList = new ArrayList<>();
        // 判断是否是合伙人
        OriginatorInfo originatorInfo = originatorInfoService.selectOne(
                new EntityWrapper<OriginatorInfo>()
                        .eq("user_id", UserUuidThreadLocal.get().getId())
                        .eq("status", BaseConstant.ORIGINATOR_INFO_STATUS_0));
        if (originatorInfo == null) {
            throw new OptimisticLockingFailureException("该商户不是合伙人");
        }
        // 权额折扣，同时用于判断满减
        List<AdvanceRechargeInfo> advanceRechargeInfos = advanceRechargeInfoService.selectList(
                new EntityWrapper<AdvanceRechargeInfo>()
                        .orderBy("level_id"));
        // 总金额  付款金额 (必填)
        for (ShopGoodsOrderVo vo : voList) {
            CalcSingleAmountVo singleCalcAmountVo = new CalcSingleAmountVo();
            // 获取商品信息
            ShopGoods shopGoods = null;
            for (ShopGoods myShopGoods : shopGoodsList) {
                if (myShopGoods.getId().equals(vo.getShopGoodsId())) {
                    shopGoods = myShopGoods;
                    break;
                }
            }
            if (shopGoods == null) {
                throw new OptimisticLockingFailureException("该id商户有误" + vo.getShopGoodsId());
            }
            // 添加商品信息用于显示
            singleCalcAmountVo.setShopInfoId(vo.getShopGoodsId());
            singleCalcAmountVo.setShopName(shopGoods.getShopName());
            singleCalcAmountVo.setMarketPrice(shopGoods.getMarketPrice());
            singleCalcAmountVo.setCostPrice(shopGoods.getCostPrice());
            singleCalcAmountVo.setMenberPrice(shopGoods.getMenberPrice());
            singleCalcAmountVo.setPic(shopGoods.getPic());
            // 计算订单金额
            double count = vo.getCount();
            // 判断库存是否充足
            if (count <= shopGoods.getStock()) {
                calcAmountVo.setIsPresell(BaseConstant.PRE_SELL_2);
            } else {
                if (calcAmountVo.getIsPresell() == null) {
                    calcAmountVo.setIsPresell(BaseConstant.PRE_SELL_1);
                } else {
                    calcAmountVo.setIsPresell(calcAmountVo.getIsPresell().equals(BaseConstant.PRE_SELL_2)
                            ? BaseConstant.PRE_SELL_2 : BaseConstant.PRE_SELL_1);
                }
            }
            // 总金额：会员价
            BigDecimal singleTotalAmount = BigDecimalUtil.mul(shopGoods.getMenberPrice(), count);
            singleCalcAmountVo.setSingleTotalAmount(singleTotalAmount);
            // 耗材费
//            BigDecimal materialCosts = new BigDecimal(0);
//            if (shopGoods.getGoodsUnit() != null & shopGoods.getGoodsUnitCount()!= null) {
//                calcMaterialCosts(vo.getCount(), shopGoods.getGoodsUnitCount(), shopGoods.getGoodsUnit(), new BigDecimal(0));
//            }
//            singleCalcAmountVo.setMaterialAmount(materialCosts);
            // 计算折扣：1.满减折扣 2.合伙人本身折扣 3.权额折扣 取折扣率最高的计算
            BigDecimal discountRate = new BigDecimal(1);
            // 满减折扣
            int size = 0;
            for (int i = 0; i < advanceRechargeInfos.size(); i++) {
                // 权限不够只能使用三级折扣
                if (originatorInfo.getDiscountStatus() != 1 && i > 2) {
                    size = i;
                    break;
                }
                if (advanceRechargeInfos.get(i).getAmount().compareTo(singleTotalAmount) > 0) {
                    size = i;
                    break;
                }
            }
            discountRate = size == 0 ? discountRate : advanceRechargeInfos.get(size).getDiscountRate();
            // 本身折扣
            discountRate = discountRate.compareTo(originatorInfo.getDiscount()) >= 0 ?
                    originatorInfo.getDiscount() : discountRate;
            // 权额折扣  判断权额能否购买
            if (vo.getPayType().equals(BaseConstant.PAY_TYPE_3)) {
                for (AdvanceRechargeRecord advanceRechargeRecord : advanceRechargeRecordList) {
                    if (advanceRechargeRecord.getDiscountRate().compareTo(discountRate) > 0) {
                        continue;
                    }
                    // 权额折扣
                    BigDecimal advanceRate = advanceRechargeRecord.getDiscountRate();
                    // 权额的折扣更大
                    if (advanceRate.compareTo(discountRate) <= 0) {
                        // 权额金额
                        BigDecimal advanceAmount = advanceRechargeRecord.getAmount().divide(advanceRate, 2, RoundingMode.HALF_UP);
                        if (advanceAmount.compareTo(singleTotalAmount) >= 0) {
                            // 权额充足
                            singleCalcAmountVo.setAdvanceAmount(singleCalcAmountVo.getAdvanceAmount() == null ? singleTotalAmount : singleCalcAmountVo.getAdvanceAmount().add(singleTotalAmount));
                            BigDecimal multiply = singleTotalAmount.multiply(advanceRate);
                            advanceRechargeRecord.setAmount(advanceRechargeRecord.getAmount().subtract(multiply));
                            singleTotalAmount = new BigDecimal(0);
                            singleCalcAmountVo.setAdvanceIds(advanceRechargeRecord.getId().toString());
                            // 实付金额
                            singleCalcAmountVo.setSingleRealAmount(singleCalcAmountVo.getSingleRealAmount().add(multiply));
                            break;
                        } else {
                            // 权额不足
                            singleCalcAmountVo.setAdvanceAmount(singleCalcAmountVo.getAdvanceAmount().add(advanceAmount));
                            singleTotalAmount = singleTotalAmount.subtract(advanceAmount);
                            advanceRechargeRecord.setAmount(advanceRechargeRecord.getAmount().subtract(advanceAmount));
                            singleCalcAmountVo.setAdvanceIds(singleCalcAmountVo.getAdvanceIds() == null ?
                                    advanceRechargeRecord.getId().toString() :
                                    singleCalcAmountVo.getAdvanceIds() + "," + advanceRechargeRecord.getId().toString());
                            // 实付金额
                            singleCalcAmountVo.setSingleRealAmount(singleCalcAmountVo.getSingleRealAmount().add(advanceAmount));
                        }
                    }
                }
            }
            // 剩余总金额打折
            singleCalcAmountVo.setSingleRateAmount(singleTotalAmount.subtract(singleTotalAmount.multiply(discountRate)));
            singleCalcAmountVo.setSingleRealAmount(singleTotalAmount.multiply(discountRate));
            // 实付金额
            calcAmountVo.setActualAmount(calcAmountVo.getActualAmount().add(singleCalcAmountVo.getSingleRealAmount().add(singleCalcAmountVo.getMaterialAmount())));
            singleCalcAmountVoList.add(singleCalcAmountVo);
        }
        // TODO 合伙人返品牌费
//        OriginatorInfoOrder originatorInfoOrder = originatorInfoOrderService.selectOne(
//                new EntityWrapper<OriginatorInfoOrder>()
//                        .eq("submit_order_user", UserUuidThreadLocal.get().getId())
//                        .ne("remian_amount", 0));
//        if (originatorInfoOrder != null) {
//            BigDecimal originatorAmount = originatorInfoOrder.getOrderAmount().multiply(BaseConstant.ORIGINATOR_RATE);
//            calcAmountVo.setOriginatorAmount(originatorAmount);
//        }
        // TODO 运费
        // TODO 礼盒费
        // 实际支付费用
        calcAmountVo.setCalcAmountVoList(singleCalcAmountVoList);
        return calcAmountVo;
    }
}
