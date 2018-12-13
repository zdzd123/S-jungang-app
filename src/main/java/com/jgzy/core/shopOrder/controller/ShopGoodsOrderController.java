package com.jgzy.core.shopOrder.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.config.AlipayConfig;
import com.jgzy.constant.BaseConstant;
import com.jgzy.constant.ErrorCodeEnum;
import com.jgzy.core.personalCenter.service.*;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
import com.jgzy.core.schedule.DealOverTimeOrderTasks;
import com.jgzy.core.shopOrder.service.*;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderStatisticVo;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderVo;
import com.jgzy.core.shopOrder.vo.ShopgoodsOrderDetailVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.common.WeiXinData;
import com.jgzy.entity.common.WeiXinTradeType;
import com.jgzy.entity.po.*;
import com.jgzy.utils.BigDecimalUtil;
import com.jgzy.utils.CommonUtil;
import com.jgzy.utils.DateUtil;
import com.jgzy.utils.WeiXinPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@RefreshScope
@RestController
@RequestMapping("/api/shopGoodsOrder")
@Api(value = "商品订单接口", description = "商品订单接口")
public class ShopGoodsOrderController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IShopGoodsOrderService shopGoodsOrderService;
    @Autowired
    private IShopGoodsOrderDetailService shopGoodsOrderDetailService;
    @Autowired
    private IShopGoodsService shopGoodsService;
    @Autowired
    private IUserAddressService userAddressService;
    @Autowired
    private IUserActivityCouponService userActivityCouponService;
    @Autowired
    private IUserOauthService userOauthService;
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserGoodsCartService userGoodsCartService;
    @Autowired
    private IUserDistributionService userDistributionService;
    @Autowired
    private IDistributionCommissionSetService distributionCommissionSetService;
    @Autowired
    private IUserFundService userFundService;
    @Autowired
    private DealOverTimeOrderTasks dealOverTimeOrderTasks;

    @Value("#{'${platformGoodsCategoryList}'.split(',')}")
    private List<Integer> specialPlatformGoodsCategoryList;

    @ApiOperation(value = "添加订单唤起支付宝支付", notes = "添加订单唤起支付宝支付")
    @PostMapping(value = "/ali/save")
    @Transactional
    public ResultWrapper<String> aliSave(@RequestBody @Validated ShopGoodsOrderVo vo, BindingResult result) {
        ResultWrapper<String> resultWrapper = new ResultWrapper<>();
        if (result.hasErrors()) {
            return resultWrapper;
        }
        // 订单信息
        ShopGoodsOrder shopGoodsOrder = new ShopGoodsOrder();
        Integer payType = vo.getPayType();
        if (payType != BaseConstant.PAY_TYPE_1) {
            resultWrapper.setResult("支付类型错误");
            return resultWrapper;
        }
        // 校验订单信息
//        boolean checkForPay = checkForPay(vo, shopGoodsOrder, new ResultWrapper<>());
//        if (!checkForPay) {
//            resultWrapper.setSuccessful(checkForPay);
//            return resultWrapper;
//        }
        //唤起第三方支付 支付宝
        logger.info("-----------------实例化alipayClient客户端-------------------");
        // 实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, alipayConfig.getAPPID(),
                alipayConfig.getRSA_PRIVATE_KEY(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, alipayConfig.getALIPAY_PUBLIC_KEY(),
                alipayConfig.getSIGN_TYPE());
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        String tradeNo = CommonUtil.getTradeNo();
        // 支付宝订单号
        model.setOutTradeNo(tradeNo);
        shopGoodsOrder.setTradeNo(tradeNo);
        model.setSubject("订单名称 (必填)");
        model.setTotalAmount(shopGoodsOrder.getTotalAmount().toString());
        model.setBody("订单描述");
        model.setTimeoutExpress(AlipayConfig.TIMEOUT_EXPRESS);
        model.setProductCode(AlipayConfig.PRODUCT_CODE);
        request.setBizModel(model);
        request.setNotifyUrl(alipayConfig.getNOTIFY_URL()); // 设置异步通知地址
        request.setReturnUrl(alipayConfig.getRETURN_URL()); // 设置同步地址
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            logger.info("-----------------操作结束-------------------");
            // 插入订单
            boolean successful = shopGoodsOrderService.insert(shopGoodsOrder);
            if (!successful) {
                throw new OptimisticLockingFailureException("订单插入失败");
            }
            // 更新优惠券已使用
            UserActivityCoupon userActivityCoupon = new UserActivityCoupon();
            userActivityCoupon.setId(shopGoodsOrder.getShopActivityCouponId());
            userActivityCoupon.setCouponState(BaseConstant.COUPON_STATE_2);
            boolean b = userActivityCouponService.updateById(userActivityCoupon);
            if (!b) {
                throw new OptimisticLockingFailureException("优惠券更新失败");
            }
            resultWrapper.setSuccessful(successful);
            resultWrapper.setResult(response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return resultWrapper;
    }

    @ApiOperation(value = "添加订单唤起微信支付", notes = "添加订单唤起微信支付")
    @PostMapping(value = "/weixin/save")
    @Transactional
    public ResultWrapper<HashMap<String, String>> weixinSave(@RequestBody @Validated List<ShopGoodsOrderVo> voList, BindingResult result) throws Exception {
        ResultWrapper<HashMap<String, String>> resultWrapper = new ResultWrapper<>();
        if (result.hasErrors()) {
            return resultWrapper;
        }
        // 支付类型
        Integer payType = voList.get(0).getPayType();
        // 总订单信息
        ShopGoodsOrder shopGoodsOrder = new ShopGoodsOrder();
        // 订单详情
        List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = new ArrayList<>();
        // 优惠券
        List<UserActivityCoupon> userActivityCouponList = new ArrayList<>();
        // 订单IDStr
        String order_ids = "";
        // 商户网站订单系统中唯一订单号(必填) 订单号和支付订单号
        String out_trade_no = CommonUtil.getTradeNo();
        for (ShopGoodsOrderVo vo : voList) {
            // 订单详情
            ShopGoodsOrderDetail shopGoodsOrderDetail = new ShopGoodsOrderDetail();
            // 优惠券信息
            UserActivityCoupon userActivityCoupon = new UserActivityCoupon();
            if (payType != BaseConstant.PAY_TYPE_2 && payType != BaseConstant.PAY_TYPE_4) {
                resultWrapper.setErrorMsg("支付类型错误");
                return resultWrapper;
            }
            // 支付类型
            shopGoodsOrder.setPayType(payType);
            // 校验订单信息
            boolean checkForPay = checkForPay(vo, shopGoodsOrder, shopGoodsOrderDetail, resultWrapper);
            if (!checkForPay) {
                resultWrapper.setSuccessful(checkForPay);
                return resultWrapper;
            }
            // 优惠券信息
            if (shopGoodsOrder.getShopActivityCouponId() != null) {
                userActivityCoupon.setId(shopGoodsOrder.getShopActivityCouponId());
                userActivityCoupon.setCouponState(BaseConstant.COUPON_STATE_2);
                userActivityCouponList.add(userActivityCoupon);
            }
            // 微信订单
            shopGoodsOrder.setTradeNo(out_trade_no);
            // 订单ID
            shopGoodsOrder.setOrderNo(out_trade_no);
            // 包邮
            shopGoodsOrder.setCarriageType(BaseConstant.CARRIAGE_TYPE_3);
            // 出库方式 1-下单邮寄
            shopGoodsOrder.setIsStock(BaseConstant.IS_STOCK_1);
            // 订单id
            shopGoodsOrderDetailList.add(shopGoodsOrderDetail);
        }
        // 返回参数
        HashMap<String, String> resultMap = new HashMap<>();
        // 查询用户
        UserInfo userInfo = userInfoService.selectById(UserUuidThreadLocal.get().getId());
        if (payType.equals(BaseConstant.PAY_TYPE_4)) {
            //  余额支付
            if (userInfo.getBalance1() != null && userInfo.getBalance1().compareTo(shopGoodsOrder.getTotalRealPayment()) < 0) {
                // 余额不足
                shopGoodsOrder.setTotalPoint(userInfo.getBalance1());
                shopGoodsOrder.setTotalRealPayment(shopGoodsOrder.getTotalRealPayment().subtract(userInfo.getBalance1()));
            } else {
                shopGoodsOrder.setTotalPoint(shopGoodsOrder.getTotalRealPayment());
                shopGoodsOrder.setTotalRealPayment(BigDecimal.ZERO);
            }
        }
        if (shopGoodsOrder.getTotalRealPayment() != null && shopGoodsOrder.getTotalRealPayment().compareTo(BigDecimal.ZERO) > 0) {
            //唤起第三方支付 weixin
            // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
            String subject = "军港之业消费者订单"; // 订单名称 (必填)
            // 获取服务器ip
            String ip = InetAddress.getLocalHost().getHostAddress();
            UserOauth userOauth = userOauthService.selectOne(
                    new EntityWrapper<UserOauth>()
                            .eq("user_id", UserUuidThreadLocal.get().getId()));
            if (userOauth == null || userOauth.getOauthOpenid() == null) {
                resultMap.put("return_code", "FAIL");
                resultMap.put("err_code_des", "openid is null");
                resultWrapper.setResult(resultMap);
                return resultWrapper;
            }
            String openid = userOauth.getOauthOpenid(); // 微信认证 openid (必填)
            String product_id = ""; // 产品id (非必填)
            String notify_url = "http://jgapi.china-mail.com.cn/api/constant/payNotify/weixinNotifyUrl";
            // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
            // 检验订单状态以及订单的金额
            // TODO 测试用
            WeiXinData wxData = WeiXinPayUtil.makePreOrder(WeiXinTradeType.JSAPI, openid, product_id,
                    out_trade_no, subject, /*shopGoodsOrder.getTotalRealPayment()*/0.01d, ip, notify_url);
            // 订单失败
            if (wxData.hasKey("return_code") && wxData.get("return_code").equals("FAIL")) {
                resultMap.put("return_code", wxData.get("return_code"));
                if (wxData.get("return_msg") != null) {
                    resultMap.put("return_msg", wxData.get("return_msg"));
                } else if (wxData.get("err_code_des") != null) {
                    resultMap.put("err_code_des", wxData.get("err_code_des"));
                }
                resultWrapper.setResult(resultMap);
                return resultWrapper;
            }
            resultMap.put("appId", wxData.get("appId"));
            resultMap.put("nonceStr", wxData.get("nonceStr"));
            resultMap.put("timeStamp", wxData.get("timeStamp"));
            resultMap.put("signType", wxData.get("signType"));
            resultMap.put("packageValue", wxData.get("package"));
            resultMap.put("sign", wxData.get("sign"));
            resultMap.put("order_ids", order_ids);
        } else {
            //普通消费者分销
            UserDistribution userDistribution = userDistributionService.selectOne(
                    new EntityWrapper<UserDistribution>()
                            .eq("user_id", userInfo.getId()));
            BigDecimal commissionAmount = shopGoodsOrder.getOrderAmountTotal().subtract(
                    shopGoodsOrder.getCouponAmount() == null ? BigDecimal.ZERO : shopGoodsOrder.getCouponAmount());
            // 实付金额，剔除特殊商品
            for (ShopGoodsOrderDetail shopGoodsOrderDetail : shopGoodsOrderDetailList) {
                ShopGoods shopGoods = shopGoodsService.selectById(shopGoodsOrderDetail.getShopGoodsId());
                if (specialPlatformGoodsCategoryList.contains(shopGoods.getPlatformGoodsCategoryId())) {
                    BigDecimal cost = shopGoodsOrderDetail.getCostPrice().multiply(new BigDecimal(shopGoodsOrderDetail.getBuyCount().toString()));
                    commissionAmount = commissionAmount.subtract(cost);
                }
            }
            if (userDistribution != null && userDistribution.getParentId() != null && userDistribution.getParentId() != null) {
                // 查询供应商分销额
                DistributionCommissionSet distributionCommissionSet = distributionCommissionSetService.selectOne(
                        new EntityWrapper<>());
                if (distributionCommissionSet != null && distributionCommissionSet.getOneLevelAgents() != null) {
                    BigDecimal amount = commissionAmount.multiply(distributionCommissionSet.getOneLevelAgents())
                            .divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
                    if (amount.compareTo(BigDecimal.ZERO) > 0) {
                        //一级分销
                        userInfoService.updateCommissionDiscount(userDistribution.getParentId(), amount);
                        // 插入一级分销流水
                        UserFund distributionUserFund = new UserFund();
                        distributionUserFund.setTradeUserId(userDistribution.getParentId());
                        distributionUserFund.setIncreaseMoney(amount);
                        distributionUserFund.setTradeDescribe("冻结收益一，姓名：" + userInfo.getNickname());
                        distributionUserFund.setOrderNo(shopGoodsOrder.getOrderNo());
                        distributionUserFund.setTradeTime(new Date());
                        distributionUserFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                        distributionUserFund.setAccountType(BaseConstant.ACCOUNT_TYPE_3);
                        distributionUserFund.setPayType(BaseConstant.PAY_TYPE_4);
                        distributionUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_81);
                        userFundService.InsertUserFund(distributionUserFund);
                    }
                }
                // 二级分销
                UserDistribution parentDistribution = userDistributionService.selectOne(
                        new EntityWrapper<UserDistribution>()
                                .eq("user_id", userDistribution.getParentId()));
                if (parentDistribution != null && parentDistribution.getParentId() != null && distributionCommissionSet != null
                        && distributionCommissionSet.getTwoLevelAgents() != null) {
                    BigDecimal amount = commissionAmount.multiply(distributionCommissionSet.getTwoLevelAgents()
                            .divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP));
                    if (amount.compareTo(BigDecimal.ZERO) > 0) {
                        userInfoService.updateCommissionDiscount(parentDistribution.getParentId(), amount);
                        // 插入二级分销流水
                        UserFund distributionUserFund = new UserFund();
                        distributionUserFund.setTradeUserId(parentDistribution.getParentId());
                        distributionUserFund.setIncreaseMoney(amount);
                        distributionUserFund.setTradeDescribe("冻结收益二，姓名：" + userInfo.getNickname());
                        distributionUserFund.setOrderNo(shopGoodsOrder.getOrderNo());
                        distributionUserFund.setTradeTime(new Date());
                        distributionUserFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                        distributionUserFund.setAccountType(BaseConstant.ACCOUNT_TYPE_3);
                        distributionUserFund.setPayType(BaseConstant.PAY_TYPE_4);
                        distributionUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_81);
                        userFundService.InsertUserFund(distributionUserFund);
                    }
                }
            }
            // 待发货订单
            shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_3);
            shopGoodsOrder.setPayTime(new Date());
        }
        // 插入预订单
        boolean successful = shopGoodsOrderService.insert(shopGoodsOrder);
        if (!successful) {
            throw new OptimisticLockingFailureException("订单插入失败");
        }
        // 插入订单详情
        shopGoodsOrderDetailList.forEach(shopGoodsOrderDetail -> shopGoodsOrderDetail.setOrderId(shopGoodsOrder.getId()));
        boolean insertBatch = shopGoodsOrderDetailService.insertBatch(shopGoodsOrderDetailList);
        if (!insertBatch) {
            throw new OptimisticLockingFailureException("订单详情插入失败");
        }
        if (!CollectionUtils.isEmpty(userActivityCouponList)) {
            // 更新优惠券已使用
            boolean b = userActivityCouponService.updateBatchById(userActivityCouponList);
            if (!b) {
                throw new OptimisticLockingFailureException("优惠券更新失败");
            }
        }
        // 删除购物车
        for (ShopGoodsOrderVo vo : voList) {
            if (vo.getCartId() != null) {
                UserGoodsCart userGoodsCart = new UserGoodsCart();
                userGoodsCart.setId(vo.getCartId());
                userGoodsCart.setLiveId(2);
                userGoodsCartService.updateById(userGoodsCart);
            }
        }
        // 用户扣除余额
        if (shopGoodsOrder.getTotalPoint() != null && shopGoodsOrder.getTotalPoint().compareTo(BigDecimal.ZERO)>0) {
            UserInfo myUser = new UserInfo();
            myUser.setId(userInfo.getId());
            myUser.setBalance1(new BigDecimal("-" + shopGoodsOrder.getTotalPoint().toString()));
            boolean b = userInfoService.updateMyBalance(myUser);
            if (!b) {
                throw new OptimisticLockingFailureException("用户余额更新失败");
            }
            UserFund remain = new UserFund();
            remain.setTradeUserId(userInfo.getId());
            remain.setDecreaseMoney(shopGoodsOrder.getTotalPoint());
            remain.setOrderNo(shopGoodsOrder.getOrderNo());
            remain.setTradeType(BaseConstant.TRADE_TYPE_2);
            remain.setTradeDescribe("余额支付");
            remain.setAccountType(BaseConstant.ACCOUNT_TYPE_2);
            remain.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
            remain.setPayType(BaseConstant.PAY_TYPE_4);
            remain.setTradeTime(new Date());
            userFundService.InsertUserFund(remain);
        }
        // 扣除商品库存
        for (ShopGoodsOrderDetail shopGoodsOrderDetail : shopGoodsOrderDetailList) {
            // 下单扣除商品库存库存
            boolean a = shopGoodsService.updateStockById(shopGoodsOrderDetail.getBuyCount(),
                    shopGoodsOrderDetail.getShopGoodsId());
            if (!a) {
                throw new OptimisticLockingFailureException("库存扣除失败");
            }
        }
        resultMap.put("orderNo", out_trade_no);
        resultWrapper.setResult(resultMap);
        return resultWrapper;
    }

    /**
     * 提交订单信息校验
     *
     * @param vo             参数
     * @param shopGoodsOrder 商品消息
     * @return 地址检验标识
     */
    private boolean checkForPay(ShopGoodsOrderVo vo, ShopGoodsOrder shopGoodsOrder, ShopGoodsOrderDetail shopGoodsOrderDetail,
                                ResultWrapper<HashMap<String, String>> resultWrapper) {
        shopGoodsOrder.setSubmitOrderUser(UserUuidThreadLocal.get().getId());
        // 普通消费者订单
        shopGoodsOrder.setOrderSource(BaseConstant.ORDER_SOURCE_3);
        Date date = DateUtil.getDate();
        // 验证地址
        Integer userAddressId = vo.getUserAddressId();
        UserAddressVo userAddressVo = userAddressService.selectDetailById(userAddressId);
        // 拼接地址
        shopGoodsOrder.setReceiveMan(userAddressVo.getName());
        shopGoodsOrder.setContactPhone(userAddressVo.getPhone());
        String address = userAddressVo.getProvince() +
                userAddressVo.getCity() +
                userAddressVo.getArea() +
                userAddressVo.getAddress();
        shopGoodsOrder.setReceiveAddress(address);
        // 计算订单金额
        double count = vo.getCount();
        ShopGoods shopGoods = shopGoodsService.selectById(vo.getShopGoodsId());
        if (!shopGoods.getStockType().equals(1)) {
            // 1-无库存可销售
            if (count > shopGoods.getStock()) {
                resultWrapper.setErrorCode(ErrorCodeEnum.ERROR_ARGS.getKey());
                resultWrapper.setErrorMsg("商品:" + shopGoods.getShopName() + " 库存不足！");
                return false;
            }
        }
        // TODO init订单详情信息
        shopGoodsOrderDetail.setBuyCount(vo.getCount());
        initShopOrderDetail(shopGoods, shopGoodsOrderDetail);
        // 总金额
        BigDecimal orderAmountTotal = shopGoodsOrder.getOrderAmountTotal() == null ?
                BigDecimal.ZERO : shopGoodsOrder.getOrderAmountTotal();
        BigDecimal shopAmount = BigDecimalUtil.mul(shopGoods.getCostPrice(), count);
        shopGoodsOrder.setOrderAmountTotal(orderAmountTotal.add(
                shopAmount));
        // 验证优惠券
        Integer userActivityCouponId = vo.getUserActivityCouponId();
        shopGoodsOrder.setCouponAmount(shopGoodsOrder.getCouponAmount() == null ? BigDecimal.ZERO : shopGoodsOrder.getCouponAmount());
        if (userActivityCouponId != null) {
            List<UserActivityCoupon> userActivityCouponList = userActivityCouponService.selectList(new EntityWrapper<UserActivityCoupon>()
                    .eq("id", userActivityCouponId)
                    .eq("coupon_state", BaseConstant.COUPON_STATE_1)
                    .le("valid_date_begin", date)
                    .ge("valid_date_end", date));
            if (!CollectionUtils.isEmpty(userActivityCouponList)) {
                UserActivityCoupon userActivityCoupon = userActivityCouponList.get(0);
                BigDecimal meetAmount = userActivityCoupon.getMeetAmount();
                if (shopGoodsOrder.getOrderAmountTotal().compareTo(meetAmount) >= 0) {
                    // 优惠券id
                    shopGoodsOrder.setShopActivityCouponId(userActivityCouponId);
                    // 优惠金额
                    shopGoodsOrder.setCouponAmount(shopGoodsOrder.getCouponAmount().add(userActivityCoupon.getAmount()));
                } else {
                    resultWrapper.setErrorCode(ErrorCodeEnum.ERROR_ARGS.getKey());
                    resultWrapper.setErrorMsg("优惠券无法使用！");
                    return false;
                }
            } else {
                resultWrapper.setErrorCode(ErrorCodeEnum.ERROR_ARGS.getKey());
                resultWrapper.setErrorMsg("优惠券不存在！");
                return false;
            }
        }
        shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_1);
        // 实付金额
        BigDecimal totalRealPayment = shopGoodsOrder.getTotalRealPayment() == null ?
                BigDecimal.ZERO : shopGoodsOrder.getTotalRealPayment();
        shopGoodsOrder.setTotalRealPayment(totalRealPayment.add(shopAmount).subtract(shopGoodsOrder.getCouponAmount()));
        // 下单时间
        shopGoodsOrder.setCreateTime(date);
        // 逾期时间
        shopGoodsOrder.setValidOrderTime(DateUtil.getHoursLater(2));
        return true;
    }

    /**
     * 初始化订单详情信息
     *
     * @param shopGoods            商品信息
     * @param shopGoodsOrderDetail 订单详情
     */
    private void initShopOrderDetail(ShopGoods shopGoods, ShopGoodsOrderDetail shopGoodsOrderDetail) {
        shopGoodsOrderDetail.setShopGoodsId(shopGoods.getId());
        shopGoodsOrderDetail.setSortName(shopGoods.getShopName());
        shopGoodsOrderDetail.setPic(shopGoods.getPic());
        shopGoodsOrderDetail.setMarketPrice(shopGoods.getMarketPrice());
        shopGoodsOrderDetail.setCostPrice(shopGoods.getCostPrice());
        shopGoodsOrderDetail.setMenberPrice(shopGoods.getMenberPrice());
        shopGoodsOrderDetail.setAddTime(new Date());
    }

    @GetMapping(value = "/detail")
    @ApiOperation(value = "查询订单", notes = "查询订单")
    public ResultWrapper<ShopGoodsOrderVo> detail(@ApiParam(value = "订单id") @RequestParam(required = false) Integer id,
                                                  @ApiParam(value = "订单编号") @RequestParam(required = false) String orderNo) {
        ResultWrapper<ShopGoodsOrderVo> resultWrapper = new ResultWrapper<>();
        ShopGoodsOrderVo shopGoodsOrderVo = shopGoodsOrderService.selectOneOrder(id, orderNo);
        resultWrapper.setResult(shopGoodsOrderVo);
        return resultWrapper;
    }

    @ApiOperation(value = "查询订单（分页）", notes = "查询订单（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<ShopGoodsOrderVo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                          @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
                                                          @ApiParam(value = "订单状态待审核=0待付款=1|待尾款=2|" +
                                                                  "待成团=61|团失败=62|待发货=3|待收货=4|待评价=5|" +
                                                                  "已评价=6|待退款=7|已退款=8|待退货=9|已退货=10|" +
                                                                  "交易关闭=11)") @RequestParam(required = false) Integer orderStatus,
                                                          @ApiParam("订单来源(合伙人=1|库存=2|普通消费者=3|品牌费=4) 逗号分割") @RequestParam(required = false) String orderSource) {
        ResultWrapper<Page<ShopGoodsOrderVo>> resultWrapper = new ResultWrapper<>();
        Page<ShopGoodsOrderVo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = shopGoodsOrderService.selectMyOrderPage(page, orderStatus, orderSource);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "订单收货", notes = "订单收货")
    @ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "path", dataType = "Integer")
    @GetMapping(value = "/update/{id:\\d+}")
    public ResultWrapper<ShopGoodsOrder> update(@PathVariable("id") Integer id) {
        ResultWrapper<ShopGoodsOrder> resultWrapper = new ResultWrapper<>();
        ShopGoodsOrder shopGoodsOrder = shopGoodsOrderService.selectById(id);
        // 待收货-->待评价
        if (shopGoodsOrder.getOrderStatus().equals(BaseConstant.ORDER_STATUS_4)) {
            ShopGoodsOrder myOrder = new ShopGoodsOrder();
            myOrder.setId(shopGoodsOrder.getId());
            myOrder.setOrderStatus(BaseConstant.ORDER_STATUS_5);
            boolean b = shopGoodsOrderService.updateById(myOrder);
            resultWrapper.setSuccessful(b);
            // 异步处理关闭订单
            dealOverTimeOrderTasks.dealCommissionAmount();
            return resultWrapper;
        }
        resultWrapper.setErrorMsg("订单状态不正确，请检查订单！");
        resultWrapper.setSuccessful(false);
        return resultWrapper;
    }

    @ApiOperation(value = "删除指定ID的订单", notes = "删除指定ID的订单")
    @ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "path", dataType = "Integer")
    @DeleteMapping("/{id:\\d+}")
    public ResultWrapper<ShopGoodsOrder> delete(@PathVariable("id") Integer id) {
        ResultWrapper<ShopGoodsOrder> resultWrapper = new ResultWrapper<>();
        ShopGoodsOrder shopGoodsOrder = shopGoodsOrderService.selectById(id);
        if (shopGoodsOrder.getOrderStatus().equals(BaseConstant.ORDER_STATUS_12) ||
                shopGoodsOrder.getOrderStatus().equals(BaseConstant.ORDER_STATUS_11)) {
            ShopGoodsOrder myOrder = new ShopGoodsOrder();
            myOrder.setUserDel(BaseConstant.USER_DEL);
            myOrder.setId(shopGoodsOrder.getId());
            boolean b = shopGoodsOrderService.updateById(myOrder);
            resultWrapper.setSuccessful(b);
            return resultWrapper;
        }
        resultWrapper.setErrorMsg("订单状态不正确，请检查订单！");
        resultWrapper.setSuccessful(false);
        return resultWrapper;
    }

    @ApiOperation(value = "未支付订单唤起微信支付", notes = "未支付订单唤起微信支付")
    @PostMapping(value = "/weixin/remianPay/{id:\\d+}")
    @ApiImplicitParam(name = "id", value = "订单", required = true, paramType = "path", dataType = "Integer")
    @Transactional
    public ResultWrapper<Map<String, String>> remianPay(@PathVariable("id") String id) throws Exception {
        ResultWrapper<Map<String, String>> resultWrapper = new ResultWrapper<>();
        Map<String, String> resultMap = new HashMap<>();
        ShopGoodsOrder shopGoodsOrder = shopGoodsOrderService.selectById(id);
        if (shopGoodsOrder == null || shopGoodsOrder.getOrderStatus().compareTo(BaseConstant.ORDER_STATUS_3) >= 0) {
            throw new OptimisticLockingFailureException("该订单不存在、或者该订单已付款！");
        }
        BigDecimal carriage = shopGoodsOrder.getCarriage();
        if (shopGoodsOrder.getCarriageType().equals(BaseConstant.CARRIAGE_TYPE_2) &&
                (carriage == null || carriage.compareTo(BigDecimal.ZERO) == 0)) {
            throw new OptimisticLockingFailureException("等待计算运费");
        }
        // 订单逾期
        if (shopGoodsOrder.getValidOrderTime() == null || shopGoodsOrder.getValidOrderTime().before(new Date())) {
            throw new OptimisticLockingFailureException("该订单已逾期，请重新提交订单");
        }
        // 订单编号
        shopGoodsOrder.setTradeNo(shopGoodsOrder.getOrderNo());
        shopGoodsOrderService.updateById(shopGoodsOrder);
        // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        String subject = null;
        String notify_url = "http://jgapi.china-mail.com.cn/api/constant/payNotify/weixinNotifyUrl";
        if (shopGoodsOrder.getOrderNo().contains(BaseConstant.PRE_ORDER)) {
            subject = "军港之业合伙人订单"; // 订单名称 (必填)
        } else if (shopGoodsOrder.getOrderNo().contains(BaseConstant.PRE_ORDER_STOCK)) {
            subject = "军港之业库存订单"; // 订单名称 (必填)
            notify_url = "http://jgapi.china-mail.com.cn/api/shopStock/constant/weixinNotifyUrl";
        } else {
            subject = "军港之业消费者订单"; // 订单名称 (必填)
        }
        // 获取服务器ip
        String ip = InetAddress.getLocalHost().getHostAddress();
        UserOauth userOauth = userOauthService.selectOne(
                new EntityWrapper<UserOauth>()
                        .eq("user_id", UserUuidThreadLocal.get().getId()));
        if (userOauth == null || userOauth.getOauthOpenid() == null) {
            resultMap.put("return_code", "FAIL");
            resultMap.put("err_code_des", "openid is null");
            resultWrapper.setResult(resultMap);
            return resultWrapper;
        }
        String openid = userOauth.getOauthOpenid(); // 微信认证 openid (必填)
        String product_id = ""; // 产品id (非必填)
        // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
        // 检验订单状态以及订单的金额
//        WeiXinData wxData = WeiXinPayUtil.makePreOrder(WeiXinTradeType.JSAPI, openid, product_id,
//                shopGoodsOrder.getOrderNo(), subject, shopGoodsOrder.getTotalRealPayment().doubleValue(), ip, notify_url);
        BigDecimal aa = new BigDecimal("0.01");
        WeiXinData wxData = WeiXinPayUtil.makePreOrder(WeiXinTradeType.JSAPI, openid, product_id,
                shopGoodsOrder.getTradeNo(), subject, aa.doubleValue(), ip, notify_url);
        // 订单失败
        if (wxData.hasKey("return_code") && wxData.get("return_code").equals("FAIL")) {
            resultMap.put("return_code", wxData.get("return_code"));
            if (wxData.get("return_msg") != null) {
                resultMap.put("return_msg", wxData.get("return_msg"));
            } else if (wxData.get("err_code_des") != null) {
                resultMap.put("err_code_des", wxData.get("err_code_des"));
            }
            resultWrapper.setResult(resultMap);
            return resultWrapper;
        }
        resultMap.put("appId", wxData.get("appId"));
        resultMap.put("nonceStr", wxData.get("nonceStr"));
        resultMap.put("timeStamp", wxData.get("timeStamp"));
        resultMap.put("signType", wxData.get("signType"));
        resultMap.put("packageValue", wxData.get("package"));
        resultMap.put("sign", wxData.get("sign"));
        resultMap.put("order_no", shopGoodsOrder.getOrderNo());
        resultMap.put("order_ids", "");
        resultWrapper.setResult(resultMap);
        return resultWrapper;
    }

    @GetMapping(value = "/statistics")
    @ApiOperation(value = "统计订单", notes = "统计订单")
    public ResultWrapper<List<ShopGoodsOrderStatisticVo>> detail() {
        ResultWrapper<List<ShopGoodsOrderStatisticVo>> resultWrapper = new ResultWrapper<>();
        List<ShopGoodsOrderStatisticVo> statistics = shopGoodsOrderService.statistics();
        resultWrapper.setResult(statistics);
        return resultWrapper;
    }
}

