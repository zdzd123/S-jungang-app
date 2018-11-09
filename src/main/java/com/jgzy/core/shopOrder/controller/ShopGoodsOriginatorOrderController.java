package com.jgzy.core.shopOrder.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.*;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
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
import org.apache.commons.lang.StringUtils;
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
    private IUserFundService userFundService;
    @Autowired
    private IShopStockService shopStockService;
    @Autowired
    private IOriginatorDiscountInfoService originatorDiscountInfoService;
    @Autowired
    private IUserAddressService userAddressService;
    @Autowired
    private IUserAddressShipService userAddressShipService;
    @Autowired
    private IUserInfoService userInfoService;

    @ApiOperation(value = "添加订单唤起微信支付", notes = "添加订单唤起微信支付")
    @PostMapping(value = "/weixin/save")
    @Transactional
    public ResultWrapper<Map<String, String>> weixinSave(@RequestBody @Validated List<ShopGoodsOrderVo> voList, BindingResult result) throws Exception {
        ResultWrapper<Map<String, String>> resultWrapper = new ResultWrapper<>();
        if (result.hasErrors()) {
            resultWrapper.setErrorMsg(result.getFieldError().toString());
            return resultWrapper;
        }
        boolean validateFlag = validateOrderParam(voList);
        if (!validateFlag) {
            resultWrapper.setErrorMsg("参数校验失败！");
            return resultWrapper;
        }
        // 运费方式
        Integer carriageType = voList.get(0).getCarriageType();
        // 是否存入库存
        Integer isStock = voList.get(0).getIsStock();
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
        // 微信认证 openid (必填)
        UserOauth userOauth = userOauthService.selectOne(
                new EntityWrapper<UserOauth>()
                        .eq("user_id", UserUuidThreadLocal.get().getId()));
        if (userOauth == null || userOauth.getOauthOpenid() == null) {
            throw new OptimisticLockingFailureException("微信认证有误");
        }
        // 余额支付时，查询余额
        UserInfo userInfo = null;
        if (voList.get(0).getPayType().equals(BaseConstant.PAY_TYPE_4) || voList.get(0).getPayType().equals(BaseConstant.PAY_TYPE_5)) {
            userInfo = userInfoService.selectOne(new EntityWrapper<UserInfo>()
                    .eq("id", UserUuidThreadLocal.get().getId()));
        }
        // 计算订单金额
        CalcAmountVo calcAmountVo = calcAmount(voList, advanceRechargeRecordList, shopGoodsList, userInfo);
        // 待审核订单 等待计算运费
        if (carriageType != null && carriageType.equals(BaseConstant.CARRIAGE_TYPE_2)) {
            // 插入预付单
            ShopGoodsOrder shopGoodsOrder = initShopGoodsOrder(BaseConstant.ORDER_STATUS_0, calcAmountVo, voList.get(0));
            shopGoodsOrder.setTradeNo(shopGoodsOrder.getOrderNo());
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
            return resultWrapper;
        }
        // 生成权额信息
        if (calcAmountVo.getActualAmount() == null || calcAmountVo.getActualAmount().compareTo(new BigDecimal(0)) == 0) {
            // 权额支付信息
            List<AdvanceRechargeRecord> myAdvanceRechargeRecordList = initAdvanceRechargeRecordByAmount(calcAmountVo, advanceRechargeRecordList);
            /* 余额充足 1.更新权额 2.插入订单 */
            if (!CollectionUtils.isEmpty(myAdvanceRechargeRecordList)) {
                boolean updateBatchById = advanceRechargeRecordService.updateBatchById(myAdvanceRechargeRecordList);
                if (!updateBatchById) {
                    throw new OptimisticLockingFailureException("权额更新失败");
                }
            }
            // 插入订单
            ShopGoodsOrder shopGoodsOrder = initShopGoodsOrder(BaseConstant.ORDER_STATUS_3, calcAmountVo, voList.get(0));
            if (isStock.equals(BaseConstant.IS_STOCK_2)) {
                shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_11);
            }
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
            if (isStock.equals(BaseConstant.IS_STOCK_2)) {
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
                    boolean insert;
                    if (stock == null) {
                        shopStock.setCreateTime(new Date());
                        insert = shopStockService.insert(shopStock);
                    } else {
                        shopStock.setUpdateTime(new Date());
                        if (shopStock.getDescribe().length() > 700) {
                            shopStock.setDescribe(stock.getDescribe().substring(stock.getDescribe().length() - 300) + "," + shopStock.getDescribe());
                        }
                        insert = shopStockService.update(shopStock, new EntityWrapper<ShopStock>()
                                .eq("user_info_id", UserUuidThreadLocal.get().getId())
                                .eq("shop_goods_id", shopGoodsOrderDetail.getShopGoodsId()));
                    }
                    if (!insert) {
                        throw new OptimisticLockingFailureException("库存插入失败");
                    }
                }
            }
            // 插入流水权额流水
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
            // 如果有余额
            if (userInfo != null && userInfo.getBalance1() != null) {
                boolean b = userInfoService.updateById(userInfo);
                if (!b) {
                    throw new OptimisticLockingFailureException("用户余额更新失败");
                }
                // 插入流水余额流水
                UserFund remain = new UserFund();
                remain.setTradeUserId(UserUuidThreadLocal.get().getId());
                remain.setDecreaseMoney(shopGoodsOrder.getTotalPoint());
                remain.setOrderNo(shopGoodsOrder.getOrderNo());
                remain.setTradeType(BaseConstant.TRADE_TYPE_2);
                remain.setTradeDescribe("余额支付");
                remain.setAccountType(BaseConstant.ACCOUNT_TYPE_2);
                remain.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
                remain.setPayType(BaseConstant.PAY_TYPE_4);
                userFundService.InsertUserFund(remain);
            }
            // 推送模版消息
//            TemplateMessageUtil.initPaySuccessTemplate(userOauth.getOauthOpenid(), shopGoodsOrder.getOrderNo(), shopGoodsOrder.getOrderAmountTotal().toString(),
//                    shopGoodsOrder.getCouponAmount().toString(), shopGoodsOrder.getTotalRealPayment().toString());
            return resultWrapper;
        } else {
            // 插入预付单
            ShopGoodsOrder shopGoodsOrder = initShopGoodsOrder(BaseConstant.ORDER_STATUS_1, calcAmountVo, voList.get(0));
            shopGoodsOrder.setTradeNo(shopGoodsOrder.getOrderNo());
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
            // 第三方支付
//            BigDecimal actualAmount = calcAmountVo.getActualAmount();
            // TODO 测试用0.01元
            BigDecimal actualAmount = new BigDecimal("0.01");
            String ip = InetAddress.getLocalHost().getHostAddress();
            Map<String, String> resultMap = weiXinPay(userOauth.getOauthOpenid(), ip, shopGoodsOrder.getOrderNo(), actualAmount);
            resultWrapper.setResult(resultMap);
            return resultWrapper;
        }
    }

    /**
     * 检验参数
     *
     * @param voList 参数
     * @return flag
     */
    private boolean validateOrderParam(List<ShopGoodsOrderVo> voList) {
        for (ShopGoodsOrderVo shopGoodsOrderVo : voList) {
            // 权额支付
            if (shopGoodsOrderVo.getPayType().equals(BaseConstant.PAY_TYPE_3)) {
                if (shopGoodsOrderVo.getAdvanceRechargeIds() == null) {
                    return false;
                }
            }
            // 实时发货
            if (shopGoodsOrderVo.getIsStock().equals(BaseConstant.IS_STOCK_1)) {
                if (shopGoodsOrderVo.getShipperId() == null || shopGoodsOrderVo.getUserAddressId() == null) {
                    return false;
                }
                // 运费标识
                if (shopGoodsOrderVo.getCarriageType() == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 微信支付
     *
     * @param ip           ip
     * @param out_trade_no 订单号
     * @param actualAmount 实付金额
     * @return 微信支付参数
     * @throws IOException e
     */
    private Map<String, String> weiXinPay(String openid, String ip, String out_trade_no, BigDecimal actualAmount) throws IOException {
        Map<String, String> resultMap = new HashMap<>();
        // 权额不足 唤起第三方支付
        // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        String subject = "军港之业合伙人订单"; // 订单名称 (必填)

        String product_id = ""; // 产品id (非必填)
        String notify_url = "http://jgapi.china-mail.com.cn/api/constant/payNotify/weixinNotifyUrl";
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
        resultMap.put("order_ids", "");
        return resultMap;
    }

    /**
     * 通过金额得出权额支付信息
     *
     * @param calcAmountVo              商品金额
     * @param advanceRechargeRecordList 权额
     * @return 待更新权额
     */
    private List<AdvanceRechargeRecord> initAdvanceRechargeRecordByAmount(CalcAmountVo calcAmountVo, List<AdvanceRechargeRecord> advanceRechargeRecordList) {
        List<AdvanceRechargeRecord> myAdvanceRechargeRecordList = new ArrayList<>();
        for (CalcSingleAmountVo calcSingleAmountVo : calcAmountVo.getCalcAmountVoList()) {
            if (StringUtils.isEmpty(calcSingleAmountVo.getAdvanceIds())) {
                continue;
            }
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
     * @param type             0-待审核 1-待支付  3-待收货  11-交易关闭
     * @param calcAmountVo     商品价格
     * @param shopGoodsOrderVo 参数
     * @return 商品信息
     */
    private ShopGoodsOrder initShopGoodsOrder(Integer type, CalcAmountVo calcAmountVo, ShopGoodsOrderVo shopGoodsOrderVo) {
        // 支付类型
        int payType = shopGoodsOrderVo.getPayType();
        // 是否放入库存 1=不存入 2=存入
        Integer isStock = shopGoodsOrderVo.getIsStock();
        //运费标识 1-到付 2-等待计算
        Integer carriageType = shopGoodsOrderVo.getCarriageType();
        ShopGoodsOrder shopGoodsOrder = new ShopGoodsOrder();
        // 合伙人订单
        shopGoodsOrder.setOrderSource(BaseConstant.ORDER_SOURCE_1);
        shopGoodsOrder.setSubmitOrderUser(UserUuidThreadLocal.get().getId());
        // 是否存入库存
        shopGoodsOrder.setIsStock(isStock);
        if (isStock.equals(BaseConstant.IS_STOCK_2)) {
            shopGoodsOrder.setReceiveAddress("库存单");
        } else {
            // 地址 不是库存单则拼接地址
            UserAddressVo userAddressVo = userAddressService.selectDetailById(shopGoodsOrderVo.getUserAddressId());
            // 收货地址
            shopGoodsOrder.setReceiveMan(userAddressVo.getName());
            shopGoodsOrder.setContactPhone(userAddressVo.getPhone());
            String address = userAddressVo.getProvince() +
                    userAddressVo.getCity() +
                    userAddressVo.getArea() +
                    userAddressVo.getAddress();
            shopGoodsOrder.setReceiveAddress(address);
            // 发货地址
            UserAddressVo userShipperAddress = userAddressShipService.selectDetailById(shopGoodsOrderVo.getShipperId());
            shopGoodsOrder.setShipper(userShipperAddress.getName());
            shopGoodsOrder.setShipperPhone(userShipperAddress.getPhone());
            String shipperAddress = userShipperAddress.getProvince() +
                    userShipperAddress.getCity() +
                    userShipperAddress.getArea() +
                    userShipperAddress.getAddress();
            shopGoodsOrder.setShipperAddress(shipperAddress);
        }

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
            // 实际付款
            shopGoodsOrder.setTotalRealPayment(shopGoodsOrder.getTotalRealPayment() == null ?
                    calcSingleAmountVo.getSingleRealAmount() :
                    shopGoodsOrder.getTotalRealPayment().add(calcSingleAmountVo.getSingleRealAmount()));
            // 优惠金额
            shopGoodsOrder.setCouponAmount(shopGoodsOrder.getCouponAmount() == null ?
                    calcSingleAmountVo.getSingleRateAmount() :
                    shopGoodsOrder.getCouponAmount().add(calcSingleAmountVo.getSingleRateAmount()));
            // 余额
            shopGoodsOrder.setTotalPoint(shopGoodsOrder.getTotalPoint() == null ?
                    calcSingleAmountVo.getSingleRemainAmount() :
                    shopGoodsOrder.getTotalPoint().add(calcSingleAmountVo.getSingleRemainAmount()));
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
        // 订单状态
        switch (type) {
            case 0:
                shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_0);
                break;
            case 1:
                shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_1);
                break;
            case 3:
                shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_3);
                shopGoodsOrder.setPayTime(date);
                break;
            case 11:
                shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_11);
                shopGoodsOrder.setPayTime(date);
                shopGoodsOrder.setClosingTime(date);
                break;
        }
        // 运费标识
        shopGoodsOrder.setCarriageType(carriageType);
        // 权额ids
        shopGoodsOrder.setBlessing(calcAmountVo.getAdvanceIds());
        return shopGoodsOrder;
    }

    /**
     * 初始化订单详情
     *
     * @param shopGoodsOrderVoList 参数列表
     * @param shopGoodsOrder       订单
     * @param shopGoodsList        商品列表
     * @return 商品详情
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
            shopGoodsOrderDetail.setMarketPrice(shopGoods.getMarketPrice());
            shopGoodsOrderDetail.setCostPrice(shopGoods.getCostPrice());
            shopGoodsOrderDetail.setMenberPrice(shopGoods.getMenberPrice());
            shopGoodsOrderDetail.setGoodsUnitCount(shopGoods.getGoodsUnitCount());
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
    public ResultWrapper<CalcAmountVo> calcAmountForShow(@RequestBody @Validated List<ShopGoodsOrderVo> voList, BindingResult result) {
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
        // 余额支付时，查询余额
        UserInfo userInfo = null;
        if (voList.get(0).getPayType().equals(BaseConstant.PAY_TYPE_4) || voList.get(0).getPayType().equals(BaseConstant.PAY_TYPE_5)) {
            userInfo = userInfoService.selectOne(new EntityWrapper<UserInfo>()
                    .eq("id", UserUuidThreadLocal.get().getId()));
        }
        // 计算订单金额
        CalcAmountVo calcAmountVo = calcAmount(voList, advanceRechargeRecordList, shopGoodsList, userInfo);
        if (!CollectionUtils.isEmpty(advanceRechargeRecordList)) {
            calcAmountVo.setAdvanceFlag(true);
        }
        resultWrapper.setResult(calcAmountVo);
        return resultWrapper;
    }

    /**
     * 计算订单金额
     *
     * @param voList 参数列表
     * @return 订单金额
     * @throws OptimisticLockingFailureException err
     */
    private CalcAmountVo calcAmount(List<ShopGoodsOrderVo> voList, List<AdvanceRechargeRecord> advanceRechargeRecordList,
                                    List<ShopGoods> shopGoodsList, UserInfo userInfo) throws OptimisticLockingFailureException {
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
        List<OriginatorDiscountInfo> originatorDiscountInfoList = originatorDiscountInfoService.selectList(
                new EntityWrapper<OriginatorDiscountInfo>()
                        .orderBy("discount_rate desc"));
        // 总金额  付款金额 (必填)
        BigDecimal totalCount = new BigDecimal("0");
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
            double singleCount = vo.getCount();
            Integer goodsUnitCount = shopGoods.getGoodsUnitCount();
            BigDecimal bigDecimalCount = BigDecimalUtil.mul(singleCount, goodsUnitCount);
            double count = bigDecimalCount.doubleValue();
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
            // 计算折扣：1.满减折扣 2.合伙人本身折扣 3.权额折扣 取折扣率最高的计算
            BigDecimal discountRate = new BigDecimal(1);
            // TODO 写死了id，当商品为礼盒和酒具的时候不打折
            if (shopGoods.getPlatformGoodsCategoryId() != 4 && shopGoods.getPlatformGoodsCategoryId() != 5) {
                // 所有商品的数量不包含礼盒酒具
                totalCount = totalCount.add(bigDecimalCount);
                // 权额折扣  判断权额能否购买
                if (vo.getPayType().equals(BaseConstant.PAY_TYPE_3) || vo.getPayType().equals(BaseConstant.PAY_TYPE_5)) {
                    // 权额id
                    String advanceRechargeIds = vo.getAdvanceRechargeIds();
                    for (AdvanceRechargeRecord advanceRechargeRecord : advanceRechargeRecordList) {
//                        if (advanceRechargeRecord.getDiscountRate().compareTo(discountRate) > 0) {
//                            continue;
//                        }
                        if (advanceRechargeIds == null || !advanceRechargeIds.contains(advanceRechargeRecord.getId().toString())) {
                            continue;
                        }
                        // 权额折扣
//                        BigDecimal advanceRate = advanceRechargeRecord.getDiscountRate();
                        // 权额的折扣更大
//                        if (advanceRate.compareTo(discountRate) <= 0) {
                        // 权额金额
                        BigDecimal advanceAmount = advanceRechargeRecord.getAmount();
                        if (advanceAmount.compareTo(singleTotalAmount) >= 0) {
                            // 权额充足
                            singleCalcAmountVo.setAdvanceAmount(singleCalcAmountVo.getAdvanceAmount().add(singleTotalAmount));
                            advanceRechargeRecord.setAmount(advanceRechargeRecord.getAmount().subtract(singleTotalAmount));
                            singleTotalAmount = new BigDecimal("0");
                            singleCalcAmountVo.setAdvanceIds(advanceRechargeRecord.getId().toString());
                            // 实付金额
//                            singleCalcAmountVo.setSingleRealAmount(singleCalcAmountVo.getSingleRealAmount().add(singleTotalAmount));
                            break;
                        } else {
                            // 权额不足
                            singleCalcAmountVo.setAdvanceAmount(singleCalcAmountVo.getAdvanceAmount().add(advanceAmount));
                            singleTotalAmount = singleTotalAmount.subtract(advanceRechargeRecord.getAmount());
                            advanceRechargeRecord.setAmount(new BigDecimal("0"));
                            singleCalcAmountVo.setAdvanceIds(singleCalcAmountVo.getAdvanceIds() == null ?
                                    advanceRechargeRecord.getId().toString() :
                                    singleCalcAmountVo.getAdvanceIds() + "," + advanceRechargeRecord.getId().toString());
                            // 实付金额
//                            singleCalcAmountVo.setSingleRealAmount(singleCalcAmountVo.getSingleRealAmount().add(advanceAmount));
                        }
//                        }
                    }
                    // 处理余额
                    if (vo.getPayType().equals(BaseConstant.PAY_TYPE_5)) {
                        // 判断金额是否充足
                        if (userInfo.getBalance1().compareTo(singleTotalAmount) >= 0) {
                            singleCalcAmountVo.setSingleRemainAmount(singleTotalAmount);
                            singleTotalAmount = new BigDecimal("0");
                        } else {
                            singleCalcAmountVo.setSingleRemainAmount(userInfo.getBalance1());
                            singleTotalAmount = singleTotalAmount.subtract(userInfo.getBalance1());
                            userInfo.setBalance1(new BigDecimal("0"));
                        }
                    }
                } else if (vo.getPayType().equals(BaseConstant.PAY_TYPE_4)) {
                    // 判断金额是否充足
                    if (userInfo.getBalance1().compareTo(singleTotalAmount) >= 0) {
                        singleCalcAmountVo.setSingleRemainAmount(singleTotalAmount);
                        singleTotalAmount = new BigDecimal("0");
                    } else {
                        userInfo.setBalance1(new BigDecimal("0"));
                        singleCalcAmountVo.setSingleRemainAmount(userInfo.getBalance1());
                        singleTotalAmount = singleTotalAmount.subtract(userInfo.getBalance1());
                    }
                } else {
                    // 本身折扣
                    discountRate = discountRate.compareTo(originatorInfo.getDiscount()) >= 0 ?
                            originatorInfo.getDiscount() : discountRate;
                }
                // 满减折扣
                int size = 0;
                for (int i = 0; i < originatorDiscountInfoList.size(); i++) {
                    // 权限不够只能使用三级折扣
                    if (originatorInfo.getDiscountStatus() != 1 && i > 2) {
                        size = i - 1;
                        break;
                    }
                    if (originatorDiscountInfoList.get(i).getAmount().compareTo(singleTotalAmount) > 0) {
                        size = i - 1;
                        break;
                    }
                }
                // 满减折扣
                if (size >= 0 && discountRate.compareTo(originatorDiscountInfoList.get(size).getDiscountRate()) > 0) {
                    discountRate = originatorDiscountInfoList.get(size).getDiscountRate();
                }
            }
            // 剩余总金额打折
            singleCalcAmountVo.setSingleRateAmount(singleTotalAmount.subtract(singleTotalAmount.multiply(discountRate)));
            // 累加上剩余总金额
            singleCalcAmountVo.setSingleRealAmount(singleCalcAmountVo.getSingleRealAmount().add(singleTotalAmount.multiply(discountRate)));
            // 实付金额
            calcAmountVo.setActualAmount(calcAmountVo.getActualAmount().add(singleCalcAmountVo.getSingleRealAmount()));
            // 支付权额id
            calcAmountVo.setAdvanceIds(calcAmountVo.getAdvanceIds() == null ?
                    singleCalcAmountVo.getAdvanceIds() :
                    calcAmountVo.getAdvanceIds() + "," + singleCalcAmountVo.getAdvanceIds());
            singleCalcAmountVoList.add(singleCalcAmountVo);
        }
        BigDecimal materialCosts = new BigDecimal("0");
        if (voList.get(0).getIsStock().equals(BaseConstant.IS_STOCK_1)) {
            // 计算耗材费
            materialCosts = CommonUtil.calcMaterialCosts(totalCount.intValue(), new BigDecimal("0"));
            calcAmountVo.setMaterialAmount(materialCosts);
        }
        // 总金额
        calcAmountVo.setActualAmount(calcAmountVo.getActualAmount().add(materialCosts));
        // 实际支付费用
        calcAmountVo.setCalcAmountVoList(singleCalcAmountVoList);
        return calcAmountVo;
    }
}
