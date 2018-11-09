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
import com.jgzy.core.shopOrder.service.IShopGoodsOrderDetailService;
import com.jgzy.core.shopOrder.service.IShopGoodsOrderService;
import com.jgzy.core.shopOrder.service.IShopGoodsService;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.common.WeiXinData;
import com.jgzy.entity.common.WeiXinTradeType;
import com.jgzy.entity.po.*;
import com.jgzy.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IAdvanceRechargeRecordService advanceRechargeRecordService;
    @Autowired
    private IUserInfoService userInfoService;

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
            // 插入流水
//        UserFund userFund = new UserFund();
//        boolean myUserFund = userFundService.InsertUserFund(userFund);
//        if (!myUserFund){
//            System.out.println("插入流水失败");
//        }
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
        // 总金额  付款金额 (必填)
        BigDecimal total_fee = new BigDecimal(0);
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
            Integer payType = vo.getPayType();
            if (payType != BaseConstant.PAY_TYPE_2) {
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
            shopGoodsOrder.setOrderNo(CommonUtil.getTradeNo());
            // 总金额
            total_fee = total_fee.add(shopGoodsOrder.getTotalRealPayment());
            // 订单id
            shopGoodsOrderDetailList.add(shopGoodsOrderDetail);
        }
        // 返回参数
        HashMap<String, String> resultMap = new HashMap<String, String>();
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
        String notify_url = "http://jgapi.china-mail.com.cn/api/payNotify/constant/weixinNotifyUrl";
        // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
        // 检验订单状态以及订单的金额
        WeiXinData wxData = WeiXinPayUtil.makePreOrder(WeiXinTradeType.JSAPI, openid, product_id,
                out_trade_no, subject, total_fee.doubleValue(), ip, notify_url);
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
        // 更新优惠券已使用
        boolean b = userActivityCouponService.updateBatchById(userActivityCouponList);
        if (!b) {
            throw new OptimisticLockingFailureException("优惠券更新失败");
        }
        resultMap.put("appId", wxData.get("appId"));
        resultMap.put("nonceStr", wxData.get("nonceStr"));
        resultMap.put("timeStamp", wxData.get("timeStamp"));
        resultMap.put("signType", wxData.get("signType"));
        resultMap.put("packageValue", wxData.get("package"));
        resultMap.put("sign", wxData.get("sign"));
        resultMap.put("order_no", out_trade_no);
        resultMap.put("order_ids", order_ids);
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
        if (count > shopGoods.getStock()) {
            resultWrapper.setErrorCode(ErrorCodeEnum.ERROR_ARGS.getKey());
            resultWrapper.setErrorMsg("库存不够！");
            return false;
        }
        // TODO init订单详情信息
        shopGoodsOrderDetail.setBuyCount(vo.getCount());
        initShopOrderDetail(shopGoods, shopGoodsOrderDetail);
        // 总金额
        shopGoodsOrder.setOrderAmountTotal(BigDecimalUtil.mul(shopGoods.getSalePrice(), count));
        // 验证优惠券
        Integer userActivityCouponId = vo.getUserActivityCouponId();
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
                    shopGoodsOrder.setCouponAmount(userActivityCoupon.getAmount());
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
        shopGoodsOrder.setTotalRealPayment(shopGoodsOrder.getOrderAmountTotal().subtract(
                shopGoodsOrder.getCouponAmount()));
        // 下单时间
        shopGoodsOrder.setCreateTime(date);
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
        //shopGoodsOrderDetail.setSalePrice(shopGoods.getSalePrice());
        shopGoodsOrderDetail.setAddTime(new Date());
    }

    @GetMapping(value = "/detail")
    @ApiOperation(value = "查询订单", notes = "查询订单")
    public ResultWrapper<ShopGoodsOrderVo> detail(@ApiParam(value = "订单id") @RequestParam(required = false) Integer id,
                                                  @ApiParam(value = "订单编号") @RequestParam(required = false) String orderNo) {
        ResultWrapper<ShopGoodsOrderVo> resultWrapper = new ResultWrapper<>();
        ShopGoodsOrderVo shopGoodsOrderVo = new ShopGoodsOrderVo();

        ShopGoodsOrder shopGoodsOrder = shopGoodsOrderService.selectOne(
                new EntityWrapper<ShopGoodsOrder>().eq(id != null, "id", id)
                        .eq(StringUtils.isNotEmpty(orderNo), "order_no", orderNo));
        if (shopGoodsOrder == null) {
            return resultWrapper;
        }
        BeanUtils.copyProperties(shopGoodsOrder, shopGoodsOrderVo);
        if (shopGoodsOrder.getShopActivityCouponId() != null) {
            UserActivityCoupon userActivityCoupon = userActivityCouponService.selectById(shopGoodsOrder.getShopActivityCouponId());
            // 优惠金额
            shopGoodsOrderVo.setAmount(userActivityCoupon.getAmount());
            // 满减要求
            shopGoodsOrderVo.setMeetAmount(userActivityCoupon.getMeetAmount());
        }
        List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = shopGoodsOrderDetailService.selectList(new EntityWrapper<ShopGoodsOrderDetail>().eq("order_id", shopGoodsOrder.getId()));
        shopGoodsOrderVo.setShopGoodsOrderDetailList(shopGoodsOrderDetailList);
        resultWrapper.setResult(shopGoodsOrderVo);
        return resultWrapper;
    }

    @ApiOperation(value = "查询订单（分页）", notes = "查询订单（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<ShopGoodsOrder>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                        @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
                                                        @ApiParam(value = "订单状态待审核=0待付款=1|待尾款=2|" +
                                                                "待成团=61|团失败=62|待发货=3|待收货=4|待评价=5|" +
                                                                "已评价=6|待退款=7|已退款=8|待退货=9|已退货=10|" +
                                                                "交易关闭=11)") @RequestParam(required = false) Integer orderStatus,
                                                        @ApiParam("订单来源(合伙人=1|库存=2|普通消费者=3|品牌费=4) 逗号分割") @RequestParam(required = false) String orderSource) {
        ResultWrapper<Page<ShopGoodsOrder>> resultWrapper = new ResultWrapper<>();
        Page<ShopGoodsOrder> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        EntityWrapper<ShopGoodsOrder> entityWrapper = new EntityWrapper<>();
        if (orderStatus != null) {
            entityWrapper.eq("order_status", orderStatus);
        }
        if (orderSource != null) {
            String[] split = orderSource.split(",");
            entityWrapper.in("order_source", split);
        }
        Set<String> orderByList = new HashSet<>();
        orderByList.add("id");
        page = shopGoodsOrderService.selectPage(page, entityWrapper.orderDesc(orderByList));
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "更新指定ID的订单", notes = "更新指定ID的订单")
    @PutMapping("/update")
    public ResultWrapper<ShopGoodsOrder> update(@RequestBody @Validated ShopGoodsOrder po) {
        ResultWrapper<ShopGoodsOrder> resultWrapper = new ResultWrapper<>();
        Integer id = po.getId();
        boolean successful = false;
        if (ValidatorUtil.isNotNullOrEmpty(id)) {
            successful = shopGoodsOrderService.updateById(po);
        }
        resultWrapper.setSuccessful(successful);
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
        if (shopGoodsOrder == null) {
            throw new OptimisticLockingFailureException("该商品不存在！");
        }
        // 判断权额是否充足
        if (shopGoodsOrder.getAdvanceAmount() != null && shopGoodsOrder.getBlessing() != null) {
            String[] split = shopGoodsOrder.getBlessing().split(",");
            List<AdvanceRechargeRecord> advanceRechargeRecordList = advanceRechargeRecordService.selectList(new EntityWrapper<AdvanceRechargeRecord>()
                    .in("id", split));
            BigDecimal allAdvanceAmount = new BigDecimal("0");
            for (AdvanceRechargeRecord advanceRechargeRecord : advanceRechargeRecordList) {
                allAdvanceAmount = allAdvanceAmount.add(advanceRechargeRecord.getAmount());
            }
            if (allAdvanceAmount.compareTo(shopGoodsOrder.getAdvanceAmount()) < 0) {
                throw new OptimisticLockingFailureException("权额不足，请重新下单！");
            }
        }
        // 余额不足
        if (shopGoodsOrder.getTotalPoint() != null && (shopGoodsOrder.getPayType().equals(BaseConstant.PAY_TYPE_4)
                || shopGoodsOrder.getPayType().equals(BaseConstant.PAY_TYPE_5))) {
            UserInfo userInfo = userInfoService.selectById(UserUuidThreadLocal.get().getId());
            if (userInfo.getBalance1().compareTo(shopGoodsOrder.getTotalPoint()) < 0) {
                throw new OptimisticLockingFailureException("余额不足，请重新下单！");
            }
        }
        // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        String subject = null;
        String notify_url = "http://jgapi.china-mail.com.cn/api/payNotify/constant/weixinNotifyUrl";
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
        shopGoodsOrder.setTradeNo(shopGoodsOrder.getOrderNo());
        WeiXinData wxData = WeiXinPayUtil.makePreOrder(WeiXinTradeType.JSAPI, openid, product_id,
                shopGoodsOrder.getOrderNo(), subject, shopGoodsOrder.getTotalRealPayment().doubleValue(), ip, notify_url);
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

}

