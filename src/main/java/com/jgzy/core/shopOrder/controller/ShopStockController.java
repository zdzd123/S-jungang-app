package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IUserAddressService;
import com.jgzy.core.personalCenter.service.IUserAddressShipService;
import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.personalCenter.service.IUserOauthService;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
import com.jgzy.core.shopOrder.service.*;
import com.jgzy.core.shopOrder.vo.CalcAmountVo;
import com.jgzy.core.shopOrder.vo.CalcSingleAmountVo;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderVo;
import com.jgzy.core.shopOrder.vo.ShopStockVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.common.WeiXinData;
import com.jgzy.entity.common.WeiXinTradeType;
import com.jgzy.entity.po.*;
import com.jgzy.utils.CommonUtil;
import com.jgzy.utils.DateUtil;
import com.jgzy.utils.WeiXinNotify;
import com.jgzy.utils.WeiXinPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-31
 */
@RefreshScope
@RestController
@RequestMapping("/api/shopStock")
@Api(value = "库存接口", description = "库存接口")
public class ShopStockController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IShopStockService shopStockService;
    @Autowired
    private IShopGoodsOrderService shopGoodsOrderService;
    @Autowired
    private IShopGoodsOrderDetailService shopGoodsOrderDetailService;
    @Autowired
    private IUserOauthService userOauthService;
    @Autowired
    private IUserAddressService userAddressService;
    @Autowired
    private IUserAddressShipService userAddressShipService;
    @Autowired
    private IUserFundService userFundService;
    @Autowired
    private IShopGoodsService shopGoodsService;
    @Autowired
    private IUserInfoService userInfoService;

    @ApiOperation(value = "获取我的库存(分页)", notes = "获取我的库存(分页)")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<ShopStockVo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                     @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
                                                     @ApiParam(value = "平台分类id") @RequestParam(required = false) Integer platformGoodsCategoryId,
                                                     @ApiParam(value = "商品名称") @RequestParam(required = false) String shopName) {
        ResultWrapper<Page<ShopStockVo>> resultWrapper = new ResultWrapper<>();
        Page<ShopStockVo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = shopStockService.selectMyStock(page, platformGoodsCategoryId, shopName);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "库存发货计算耗材费", notes = "库存发货计算耗材费")
    @PostMapping(value = "/calcAmountForShow")
    public ResultWrapper<CalcAmountVo> calcAmountForShow(@RequestBody List<ShopGoodsOrderVo> voList) {
        ResultWrapper<CalcAmountVo> resultWrapper = new ResultWrapper<>();
        CalcAmountVo calcAmountVo = new CalcAmountVo();
//        List<Integer> shopGoodsIds = new ArrayList<>();
//        voList.forEach(shopGoodsOrderVo -> shopGoodsIds.add(shopGoodsOrderVo.getShopGoodsId()));
        // 查询商品
//        List<ShopGoods> shopGoodsList = shopGoodsService.selectList(
//                new EntityWrapper<ShopGoods>().in("id", shopGoodsIds));
//        if (shopGoodsList == null || shopGoodsList.size() != voList.size()) {
//            throw new OptimisticLockingFailureException("商品id有误");
//        }
        // 总个数
        int allCount = 0;
        List<CalcSingleAmountVo> calcSingleAmountVoList = new ArrayList<>();
        for (ShopGoodsOrderVo vo : voList) {
            ShopStock shopStock = shopStockService.selectById(vo.getId());
            if (shopStock == null) {
                throw new OptimisticLockingFailureException("该id库存有误" + vo.getId());
            }
            ShopGoods shopGoods = shopGoodsService.selectById(shopStock.getShopGoodsId());
//            // 获取商品信息
//            ShopGoods shopGoods = null;
//            for (ShopGoods myShopGoods : shopGoodsList) {
//                if (myShopGoods.getId().equals(vo.getShopGoodsId())) {
//                    shopGoods = myShopGoods;
//                    break;
//                }
//            }
            if (shopGoods == null) {
                throw new OptimisticLockingFailureException("该id商户有误" + vo.getShopGoodsId());
            }
            CalcSingleAmountVo calcSingleAmountVo = new CalcSingleAmountVo();
            // 添加商品信息用于显示
            calcSingleAmountVo.setShopInfoId(vo.getShopGoodsId());
            calcSingleAmountVo.setShopName(shopGoods.getShopName());
            calcSingleAmountVo.setMarketPrice(shopGoods.getMarketPrice());
            calcSingleAmountVo.setCostPrice(shopGoods.getCostPrice());
            calcSingleAmountVo.setMenberPrice(shopGoods.getMenberPrice());
            calcSingleAmountVo.setPic(shopGoods.getPic());
            // 商品单位
            calcSingleAmountVo.setGoodsUnit(shopGoods.getGoodsUnit());
            calcSingleAmountVo.setGoodsUnitCount(shopGoods.getGoodsUnitCount());
            // 商品库存
            calcSingleAmountVo.setStock(shopGoods.getStock());
            calcSingleAmountVoList.add(calcSingleAmountVo);
            // 商品数量
            calcSingleAmountVo.setCount(shopStock.getCount());
            calcSingleAmountVo.setAbstracts(shopGoods.getAbstracts());
            // 商品属性
//            List<ShopGoodsAttribute> shopGoodsAttributeList = shopGoodsAttributeService.selectList(
//                    new EntityWrapper<ShopGoodsAttribute>()
//                            .eq("shop_goods_id", shopGoods.getId())
//                            .ne("attribute_value_name", "")
//                            .ne("attribute_value_name", "下拉选择")
//                            .isNotNull("attribute_value_name"));
//            calcSingleAmountVo.setShopGoodsAttributeList(shopGoodsAttributeList);
            // 剔除酒具礼盒
            if (shopGoods.getPlatformGoodsCategoryId() != 4 || shopGoods.getPlatformGoodsCategoryId() != 5) {
                //总数,用于计算耗材费
                allCount += vo.getCount();
            }
        }
        calcAmountVo.setCalcAmountVoList(calcSingleAmountVoList);
        BigDecimal materialAmount = BigDecimal.ZERO;
        if (!voList.get(0).getIsStock().equals(BaseConstant.IS_STOCK_3)) {
            materialAmount = CommonUtil.calcMaterialCosts(allCount, BigDecimal.ZERO);
        }
        calcAmountVo.setMaterialAmount(materialAmount);
        calcAmountVo.setTotalAmount(materialAmount);
        calcAmountVo.setActualAmount(materialAmount);
        // 计算余额抵扣
        UserInfo userInfo;
        Integer payType = voList.get(0).getPayType();
        if (payType.equals(BaseConstant.PAY_TYPE_4)) {
            // 查询用户余额
            userInfo = userInfoService.selectOne(new EntityWrapper<UserInfo>()
                    .eq("id", UserUuidThreadLocal.get().getId()));
            BigDecimal balance = userInfo.getBalance1() == null ? BigDecimal.ZERO : userInfo.getBalance1();
            if (balance.compareTo(materialAmount) >= 0) {
                calcAmountVo.setRemainAmount(materialAmount);
                userInfo.setBalance1(balance.subtract(materialAmount));
                calcAmountVo.setActualAmount(BigDecimal.ZERO);
            } else {
                calcAmountVo.setRemainAmount(balance);
                userInfo.setBalance1(BigDecimal.ZERO);
                calcAmountVo.setActualAmount(materialAmount.subtract(balance));
            }
        }
        resultWrapper.setResult(calcAmountVo);
        return resultWrapper;
    }

    @ApiOperation(value = "库存发货生成订单", notes = "库存发货生成订单")
    @PostMapping(value = "/weixinPay")
    @Transactional
    public ResultWrapper<Map<String, String>> weixinPay(@RequestBody @Validated List<ShopStockVo> voList) throws Exception {
        ResultWrapper<Map<String, String>> resultWrapper = new ResultWrapper<>();
        Map<String, String> resultMap = new HashMap<>();
        BigDecimal zero = new BigDecimal("0");
        // 运费标识
        Integer carriageType = voList.get(0).getCarriageType();
        // 订单号
        String tradeNo = BaseConstant.PRE_ORDER_STOCK + CommonUtil.getTradeNo();
        Date date = new Date();
        // 订单
        ShopGoodsOrder shopGoodsOrder = new ShopGoodsOrder();
        // 库存订单
        shopGoodsOrder.setOrderSource(BaseConstant.ORDER_SOURCE_2);
        shopGoodsOrder.setOrderNo(tradeNo);
        shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_1);
        shopGoodsOrder.setSubmitOrderUser(UserUuidThreadLocal.get().getId());
        shopGoodsOrder.setCreateTime(date);
        shopGoodsOrder.setIsStock(BaseConstant.IS_STOCK_1);
        if (voList.get(0).getIsStock().equals(BaseConstant.IS_STOCK_3)) {
            shopGoodsOrder.setIsStock(BaseConstant.IS_STOCK_3);
        }
        // 收货地址和发货地址
        initAddress(shopGoodsOrder, voList.get(0));
        int count = 0;
        List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = new ArrayList<>();
        for (ShopStockVo shopStockVo : voList) {
            ShopGoodsOrderDetail shopGoodsOrderDetail = new ShopGoodsOrderDetail();
            ShopStockVo shopStock = shopStockService.selectMyStockById(shopStockVo.getId());
            if (shopStock == null) {
                throw new OptimisticLockingFailureException("该库存不存在！");
            }
            if (shopStock.getCount().compareTo(shopStockVo.getCount()) < 0) {
                throw new OptimisticLockingFailureException("商品:" + shopStock.getShopName() + " 库存不足！");
            }
            shopGoodsOrderDetail.setShopGoodsId(shopStock.getShopGoodsId());
            shopGoodsOrderDetail.setBuyCount(shopStockVo.getCount());
            shopGoodsOrderDetail.setAddTime(date);
            // 商品具体信息
            shopGoodsOrderDetail.setPic(shopStock.getPic());
            shopGoodsOrderDetail.setMenberPrice(shopStock.getMenberPrice());
            shopGoodsOrderDetail.setCostPrice(shopStock.getCostPrice());
            shopGoodsOrderDetail.setMarketPrice(shopStock.getMarketPrice());
            shopGoodsOrderDetail.setSortName(shopStock.getShopName());
            shopGoodsOrderDetailList.add(shopGoodsOrderDetail);
            count += shopStockVo.getCount();
            //库存
            ShopStock myShopStock = new ShopStock();
            myShopStock.setId(shopStock.getId());
            myShopStock.setUpdateTime(date);
            myShopStock.setCount(shopStock.getCount() - shopStockVo.getCount());
        }
        BigDecimal materialCosts = BigDecimal.ZERO;
        if (!shopGoodsOrder.getIsStock().equals(BaseConstant.IS_STOCK_3)) {
            materialCosts = CommonUtil.calcMaterialCosts(count, BigDecimal.ZERO);
        }
        shopGoodsOrder.setMaterialAmount(materialCosts);
        shopGoodsOrder.setOrderAmountTotal(materialCosts);
        shopGoodsOrder.setTradeNo(tradeNo);
        Integer payType = voList.get(0).getPayType();
        if (payType.equals(BaseConstant.PAY_TYPE_4)) {
            // 余额支付
            shopGoodsOrder.setPayType(BaseConstant.PAY_TYPE_4);
            // 查询用户余额
            UserInfo userInfo = userInfoService.selectOne(new EntityWrapper<UserInfo>()
                    .eq("id", UserUuidThreadLocal.get().getId()));
            BigDecimal balance = userInfo.getBalance1() == null ? zero : userInfo.getBalance1();
            if (balance.compareTo(materialCosts) >= 0) {
                shopGoodsOrder.setTotalRealPayment(zero);
                shopGoodsOrder.setTotalPoint(materialCosts);
                userInfo.setBalance1(balance.subtract(materialCosts));
            } else {
                shopGoodsOrder.setTotalRealPayment(materialCosts.subtract(balance));
                shopGoodsOrder.setTotalPoint(balance);
                userInfo.setBalance1(BigDecimal.ZERO);
            }
            // 更新用户余额
//            boolean b = userInfoService.updateById(userInfo);
            Date updateTime = userInfo.getUpdateTime();
            userInfo.setUpdateTime(date);
            boolean b = userInfoService.update(userInfo, new EntityWrapper<UserInfo>()
                    .eq(updateTime != null, "update_time", updateTime)
                    .eq("id", userInfo.getId()));
            if (!b) {
                throw new OptimisticLockingFailureException("用户余额更新失败！");
            }
            // 插入余额流水
            if (shopGoodsOrder.getTotalPoint() != null && shopGoodsOrder.getTotalPoint().compareTo(BigDecimal.ZERO) > 0) {
                // 插入冻结余额流水
                UserFund remain = new UserFund();
                remain.setTradeUserId(userInfo.getId());
                remain.setDecreaseMoney(shopGoodsOrder.getTotalPoint());
                remain.setOrderNo(shopGoodsOrder.getOrderNo());
                remain.setTradeType(BaseConstant.TRADE_TYPE_2);
                remain.setTradeDescribe("余额支付");
                remain.setAccountType(BaseConstant.ACCOUNT_TYPE_3);
                remain.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
                remain.setPayType(BaseConstant.PAY_TYPE_4);
                remain.setTradeTime(date);
                userFundService.InsertUserFund(remain);
            }
        } else if (payType.equals(BaseConstant.PAY_TYPE_2)) {
            // 微信支付
            shopGoodsOrder.setPayType(BaseConstant.PAY_TYPE_2);
            shopGoodsOrder.setTotalRealPayment(materialCosts);
        }
        if (carriageType.equals(2)) {
            // 等待计算
            shopGoodsOrder.setCarriageType(BaseConstant.CARRIAGE_TYPE_2);
            shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_0);
        } else if (carriageType.equals(1)) {
            // 到付 weixin支付
            shopGoodsOrder.setCarriageType(BaseConstant.CARRIAGE_TYPE_1);
            // 当不存在耗材费时 更新库存
            if (shopGoodsOrder.getTotalRealPayment() == null || shopGoodsOrder.getTotalRealPayment().compareTo(zero) == 0) {
                // 更新库存
                shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_3);
                shopGoodsOrder.setPayTime(date);
            } else {
                String ip = InetAddress.getLocalHost().getHostAddress();
                resultMap = weiXinPay(ip, tradeNo, materialCosts, "");
            }
        }
        // 逾期时间
        shopGoodsOrder.setValidOrderTime(DateUtil.getHoursLater(2));
        boolean insert = shopGoodsOrderService.insert(shopGoodsOrder);
        if (!insert) {
            throw new OptimisticLockingFailureException("订单插入失败！");
        }
        shopGoodsOrderDetailList.forEach(shopGoodsOrderDetail -> shopGoodsOrderDetail.setOrderId(shopGoodsOrder.getId()));
        boolean b = shopGoodsOrderDetailService.insertBatch(shopGoodsOrderDetailList);
        if (!b) {
            throw new OptimisticLockingFailureException("订单详情插入失败！");
        }
        // 下单扣除商品库存
        for (ShopGoodsOrderDetail shopGoodsOrderDetail : shopGoodsOrderDetailList) {
//            boolean a = shopGoodsService.updateStockById(shopGoodsOrderDetail.getBuyCount(),
//                    shopGoodsOrderDetail.getShopGoodsId());
//            if (!a){
//                throw new OptimisticLockingFailureException("商品库存扣除失败");
//            }
            // 下单扣除库存
            boolean c = shopStockService.updateMyShopStockByGoodsId(shopGoodsOrderDetail.getBuyCount(),
                    shopGoodsOrderDetail.getShopGoodsId(), shopGoodsOrder.getSubmitOrderUser());
            if (!c) {
                throw new OptimisticLockingFailureException("库存扣除失败");
            }
        }

        resultMap.put("orderNo", shopGoodsOrder.getOrderNo());
        resultWrapper.setResult(resultMap);
        return resultWrapper;
    }

    /**
     * 拼接地址
     *
     * @param shopGoodsOrder 商品订单
     * @param vo             参数
     * @return 商品订单
     */
    private boolean initAddress(ShopGoodsOrder shopGoodsOrder, ShopStockVo vo) {
        if (shopGoodsOrder.getIsStock().equals(BaseConstant.IS_STOCK_3)) {
            shopGoodsOrder.setReceiveMan(vo.getReceiveMan());
            shopGoodsOrder.setContactPhone(vo.getContactPhone());
            return true;
        }
        UserAddressVo userAddressVo = userAddressService.selectDetailById(vo.getAddressId());
        if (userAddressVo == null) {
            return false;
        }
        UserAddressVo userAddressShip = userAddressShipService.selectDetailById(vo.getShipAddressId());
        if (userAddressShip == null) {
            return false;
        }
        // 拼接地址
        shopGoodsOrder.setReceiveMan(userAddressVo.getName());
        shopGoodsOrder.setContactPhone(userAddressVo.getPhone());
        String address = userAddressVo.getProvince() +
                userAddressVo.getCity() +
                userAddressVo.getArea() +
                userAddressVo.getAddress();
        shopGoodsOrder.setReceiveAddress(address);
        shopGoodsOrder.setShipper(userAddressShip.getName());
        shopGoodsOrder.setShipperPhone(userAddressShip.getPhone());
        shopGoodsOrder.setShipperAddress(userAddressShip.getProvince() +
                userAddressShip.getCity() +
                userAddressShip.getArea() +
                userAddressShip.getAddress());
        return true;
    }


    /**
     * 微信支付
     *
     * @param ip           ip
     * @param out_trade_no 订单号
     * @param actualAmount 实付金额
     * @param order_ids    订单ids
     * @return map
     * @throws IOException err
     */
    private Map<String, String> weiXinPay(String ip, String out_trade_no, BigDecimal actualAmount, String order_ids) throws IOException {
        Map<String, String> resultMap = new HashMap<>();
        // 权额不足 唤起第三方支付
        // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        String subject = "军港之业库存订单"; // 订单名称 (必填)
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
        String notify_url = "http://jgapi.china-mail.com.cn/api/shopStock/constant/weixinNotifyUrl";
        // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
        // 检验订单状态以及订单的金额
        // TODO 测试用支付
        actualAmount = new BigDecimal("0.01");
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

    @ApiOperation(value = "微信回调接口", notes = "微信回调接口")
    @PostMapping(value = "/constant/weixinNotifyUrl")
    @Transactional
    public String weixinNotifyUrl(HttpServletRequest request) {
        WeiXinNotify notify = WeiXinPayUtil.notify_url(request);
        System.out.println("notify-------------------------------------------------------" + notify);
        String outTradeNo = notify.getOut_trade_no(); // 商户网站订单系统中唯一订单号
        BigDecimal totalAmount = new BigDecimal(notify.getTotal_fee()); // 付款金额

//        WeiXinNotify notify = new WeiXinNotify();
//        String outTradeNo = "KC1116165517-1779";
//        BigDecimal totalAmount = new BigDecimal("3");

        // 校验订单
        ShopGoodsOrder shopGoodsOrder = shopGoodsOrderService.selectOne(
                new EntityWrapper<ShopGoodsOrder>().eq("trade_no", outTradeNo));
        if (shopGoodsOrder == null) {
            logger.info("-----------------------OutTradeNo fail------------------------------");
            notify.setResultFail("OutTradeNo fail");
            return notify.getBodyXML();
        }
        if (!shopGoodsOrder.getOrderStatus().equals(BaseConstant.ORDER_STATUS_1)) {
            logger.info("-----------------------OrderStatus fail------------------------------");
            notify.setResultFail("OrderStatus fail" + shopGoodsOrder.getOrderStatus());
            return notify.getBodyXML();
        }
        // 判断金额是否正确
//        if (shopGoodsOrder.getTotalRealPayment().multiply(new BigDecimal("100")).compareTo(totalAmount) != 0) {
//            logger.info("-----------------------TotalAmount fail------------------------------");
//            notify.setResultFail("TotalAmount fail");
//            return notify.getBodyXML();
//        }
        // 更新余额
//        BigDecimal totalPoint = shopGoodsOrder.getTotalPoint();
        Date date = new Date();
//        if (shopGoodsOrder.getPayType().equals(BaseConstant.PAY_TYPE_4) && totalPoint!= null && totalPoint.compareTo(BigDecimal.ZERO) > 0){
//            UserInfo userInfo = userInfoService.selectById(shopGoodsOrder.getSubmitOrderUser());
//            if (userInfo.getPoint().compareTo(totalPoint) >= 0){
//                userInfo.setPoint(userInfo.getPoint().subtract(totalPoint));
//            }
//            boolean b = userInfoService.updateById(userInfo);
//            if (!b){
//                logger.info("-----------------------pointUpdate fail------------------------------");
//                notify.setResultFail("pointUpdate fail" + userInfo.getPoint());
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                return notify.getBodyXML();
//            }
//            // 插入流水
//            UserFund userFund = new UserFund();
//            userFund.setDecreaseMoney(shopGoodsOrder.getTotalRealPayment());
//            userFund.setTradeDescribe("余额支付");
//            userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_2);
//            userFund.setPayType(BaseConstant.PAY_TYPE_4);
//            userFund.setTradeUserId(shopGoodsOrder.getSubmitOrderUser());
//            userFund.setOrderNo(outTradeNo);
//            userFund.setTradeType(BaseConstant.TRADE_TYPE_2);
//            userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
//            userFund.setTradeTime(date);
//            userFundService.InsertUserFund(userFund);
//        }
        // 更新订单
        ShopGoodsOrder myShopGoodsOrder = new ShopGoodsOrder();
        myShopGoodsOrder.setId(shopGoodsOrder.getId());
        myShopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_3);
        myShopGoodsOrder.setPayTime(date);
        boolean updateById = shopGoodsOrderService.updateById(myShopGoodsOrder);
        if (!updateById) {
            logger.info("-----------------------updateOrder fail------------------------------");
            notify.setResultFail("updateOrder fail");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notify.getBodyXML();
        }
        // 更新商户库存
//        List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = shopGoodsOrderDetailService.selectList(
//                new EntityWrapper<ShopGoodsOrderDetail>()
//                        .eq("order_id", shopGoodsOrder.getId()));
//        for (ShopGoodsOrderDetail shopGoodsOrderDetail : shopGoodsOrderDetailList) {
//            ShopStock shopStock = shopStockService.selectOne(new EntityWrapper<ShopStock>()
//                    .eq("user_info_id", shopGoodsOrder.getSubmitOrderUser())
//                    .eq("shop_goods_id", shopGoodsOrderDetail.getShopGoodsId())
//                    .ge("count", shopGoodsOrderDetail.getBuyCount()));
//            if (shopStock == null) {
//                logger.info("-----------------------shopStock fail------------------------------");
//                notify.setResultFail("shopStock fail");
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                return notify.getBodyXML();
//            }
//            shopStock.setCount(shopStock.getCount() - shopGoodsOrderDetail.getBuyCount());
//            shopStock.setUpdateTime(date);
//            boolean c = shopStockService.updateById(shopStock);
//            if (!c) {
//                logger.info("-----------------------shopStock fail------------------------------");
//                notify.setResultFail("shopStock fail");
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                return notify.getBodyXML();
//            }
//            if (shopGoodsOrder.getIsStock().equals(BaseConstant.IS_STOCK_1)){
//                // 更新商品库存
//                ShopGoods shopGoods = shopGoodsService.selectById(shopGoodsOrder.getSubmitOrderUser());
//                if (shopGoods == null){
//                    logger.info("-----------------------shopGoodsStock fail------------------------------");
//                    notify.setResultFail("shopGoodsStock fail");
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                    return notify.getBodyXML();
//                }
//                shopGoods.setStock(shopGoods.getStock()-shopStock.getCount());
//                boolean b = shopGoodsService.updateById(shopGoods);
//                if (!b){
//                    logger.info("-----------------------shopGoodsStockUpdate fail------------------------------");
//                    notify.setResultFail("shopGoodsStockUpdate fail");
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                    return notify.getBodyXML();
//                }
//            }
//        }
        // 插入流水
        UserFund userFund = new UserFund();
        userFund.setDecreaseMoney(shopGoodsOrder.getTotalRealPayment());
        userFund.setTradeDescribe("微信支付");
        userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_4);
        userFund.setPayType(BaseConstant.PAY_TYPE_2);
        userFund.setTradeUserId(shopGoodsOrder.getSubmitOrderUser());
        userFund.setOrderNo(outTradeNo);
        userFund.setTradeType(BaseConstant.TRADE_TYPE_2);
        userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
        userFund.setTradeTime(date);
        userFundService.InsertUserFund(userFund);
        logger.info("-----------------------微信回调接口结束------------------------------");
        notify.setResultSuccess();
        return notify.getBodyXML();
    }

    @ApiOperation(value = "库存订单支付（重新计算运费）", notes = "库存订单支付（重新计算运费）")
    @GetMapping(value = "/weixinPayForOrder")
    @Transactional
    public ResultWrapper<Map<String, String>> weixinPayForOrder(@ApiParam(value = "订单编号", required = true) @RequestParam Integer orderId) throws Exception {
        ResultWrapper<Map<String, String>> resultWrapper = new ResultWrapper<>();
        ShopGoodsOrder shopGoodsOrder = shopGoodsOrderService.selectById(orderId);
        if (!shopGoodsOrder.getOrderStatus().equals(BaseConstant.ORDER_STATUS_1)) {
            throw new OptimisticLockingFailureException("订单不是支付状态");
        }
        if (shopGoodsOrder.getCarriageType().equals(BaseConstant.CARRIAGE_TYPE_2) &&
                shopGoodsOrder.getCarriage() != null && shopGoodsOrder.getCarriage().compareTo(new BigDecimal(0)) > 0) {
            throw new OptimisticLockingFailureException("订单运费有误");
        }
        shopGoodsOrder.setTotalRealPayment(shopGoodsOrder.getMaterialAmount().add(shopGoodsOrder.getCarriage()));
        String ip = InetAddress.getLocalHost().getHostAddress();
        // 微信支付
        Map<String, String> resultMap = weiXinPay(ip, shopGoodsOrder.getTradeNo(), shopGoodsOrder.getTotalRealPayment(), "");
        resultWrapper.setResult(resultMap);
        return resultWrapper;
    }

    @GetMapping(value = "/getAllAmount")
    @ApiOperation(value = "获取我的总资产", notes = "获取我的总资产")
    public ResultWrapper<ShopStockVo> getAllAmount() {
        ResultWrapper<ShopStockVo> resultWrapper = new ResultWrapper<>();
        ShopStockVo shopStockVo = shopStockService.getCountStock();
        resultWrapper.setResult(shopStockVo);
        return resultWrapper;
    }
}

