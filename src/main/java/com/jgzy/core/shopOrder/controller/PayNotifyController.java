package com.jgzy.core.shopOrder.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.config.AlipayConfig;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.*;
import com.jgzy.core.schedule.DealOverTimeOrderTasks;
import com.jgzy.core.schedule.DealOverduePaymentsTasks;
import com.jgzy.core.shopOrder.service.*;
import com.jgzy.core.shopOrder.vo.ShopgoodsOrderDetailVo;
import com.jgzy.entity.po.*;
import com.jgzy.utils.DateUtil;
import com.jgzy.utils.WeiXinNotify;
import com.jgzy.utils.WeiXinPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@RefreshScope
@RestController
@RequestMapping("/api/constant/payNotify")
@Api(value = "支付回调接口", description = "支付回调接口")
public class PayNotifyController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IShopGoodsOrderService shopGoodsOrderService;
    @Autowired
    private IUserFundService userFundService;
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private IShopGoodsOrderDetailService shopGoodsOrderDetailService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserDistributionConstantService userDistributionConstantService;
    @Autowired
    private IOriginatorInfoOrderService originatorInfoOrderService;
    @Autowired
    private IOriginatorInfoService originatorInfoService;
    @Autowired
    private IUserDistributionService userDistributionService;
    @Autowired
    private IDistributionCommissionSetService distributionCommissionSetService;
    @Autowired
    private IShopStockService shopStockService;
    @Autowired
    private DealOverTimeOrderTasks dealOverTimeOrderTasks;
    @Autowired
    private IAdvanceRechargeRecordService advanceRechargeRecordService;
    @Autowired
    private DealOverduePaymentsTasks dealOverduePaymentsTasks;

    @Value("#{'${platformGoodsCategoryList}'.split(',')}")
    private List<Integer> specialPlatformGoodsCategoryList;

    @ApiOperation(value = "支付宝回调接口", notes = "支付宝回调接口")
    @PostMapping(value = "/aliNotifyUrl")
    public String aliNotifyUrl(HttpServletRequest request) {
        logger.info("-----------------------支付宝异步回调接口开始------------------------------");
        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map<String, ?> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"),
                // "utf-8");
                params.put(name, valueStr);
            }
            // 第一步： 在通知返回参数列表中，除去sign、sign_type两个参数外，凡是通知返回回来的参数皆是待验签的参数
            // 第二步： 将剩下参数进行url_decode, 然后进行字典排序，组成字符串，得到待签名字符串：
            // 第三步： 将签名参数（sign）使用base64解码为字节码串。
            // 第四步： 使用RSA的验签方法，通过签名字符串、签名参数（经过base64解码）及支付宝公钥验证签名。
            boolean rsaCheckV2 = AlipaySignature.rsaCheckV1(params, alipayConfig.getALIPAY_PUBLIC_KEY(),
                    AlipayConfig.CHARSET, (String) params.get("sign_type"));
            // 第五步：在步骤四验证签名正确后，必须再严格按照如下描述校验通知数据的正确性。
            logger.info("-----------------------rsaCheckV2:" + rsaCheckV2 + "------------------------------");
            if (rsaCheckV2) {
                // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号；
                String outTradeNo = params.get("out_trade_no");
                // String outTradeNo = new
                // String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
                // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）；
                BigDecimal totaAmount = new BigDecimal(params.get("total_amount"));
                // 3、校验通知中的seller_id（或者seller_email)
                // 是否为out_trade_no这笔单据对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）；
                //String sellerId = params.get("seller_id");
                //String sellerEmail = params.get("seller_email");
                // 4、验证app_id是否为该商户本身
                //String appId = params.get("app_id");
                // 校验订单
                List<ShopGoodsOrder> shopGoodsOrderList = shopGoodsOrderService.selectList(
                        new EntityWrapper<ShopGoodsOrder>()
                                .eq("trade_no", outTradeNo));
                if (CollectionUtils.isEmpty(shopGoodsOrderList)) {
                    logger.info("-----------------------OutTradeNo fail------------------------------");
                    return "OutTradeNo fail";
                }
                ShopGoodsOrder shopGoodsOrder = shopGoodsOrderList.get(0);
                if (shopGoodsOrder.getTotalAmount().compareTo(totaAmount) != 0) {
                    logger.info("-----------------------TotalAmount fail------------------------------");
                    return "TotalAmount fail";
                }
                // 更新订单
                shopGoodsOrder.setTotalRealPayment(totaAmount);
                shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_3);
                shopGoodsOrder.setPayTime(DateUtil.parseDateTime(params.get("time_end")));
                shopGoodsOrderService.update(shopGoodsOrder,
                        new EntityWrapper<>(shopGoodsOrder)
                                .eq("trade_no", outTradeNo));
                // 插入流水
                UserFund userFund = new UserFund();
                userFund.setTradeUserId(shopGoodsOrder.getSubmitOrderUser());
                userFund.setIncreaseMoney(totaAmount);
                userFund.setOrderNo(outTradeNo);
                userFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                userFund.setTradeDescribe("支付宝支付");
                userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_4);
                userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
                userFund.setPayType(BaseConstant.PAY_TYPE_1);
                //String b = userFundService.InsertUserFund(userFund);
                logger.info("-----------------------支付宝异步回调接口结束------------------------------");
                return "success";
            } else {
                logger.info("-----------------------sign fail------------------------------");
                return "sign fail";
            }
        } catch (AlipayApiException | OptimisticLockingFailureException e) {
            e.printStackTrace();
        }
        logger.info("-----------------------fail------------------------------");
        return "fail";
    }

    @ApiOperation(value = "微信回调接口", notes = "微信回调接口")
    @PostMapping(value = "/weixinNotifyUrl")
    @Transactional
    public String weixinNotifyUrl(HttpServletRequest request) {
        logger.info("-----------------------——微信回调开始——------------------------------");
        Date date = new Date();
        WeiXinNotify notify = WeiXinPayUtil.notify_url(request);
        String outTradeNo = notify.getOut_trade_no(); // 商户网站订单系统中唯一订单号
        BigDecimal totalAmount = new BigDecimal(notify.getTotal_fee()); // 付款金额

//        WeiXinNotify notify = new WeiXinNotify();
//        String outTradeNo = "HHR010412184884796";
//        BigDecimal totalAmount = new BigDecimal("141375");
        logger.info("-----------------------——微信回调订单编号" + outTradeNo + "——------------------------------");

        // 校验订单
        ShopGoodsOrder shopGoodsOrder = shopGoodsOrderService.selectOne(
                new EntityWrapper<ShopGoodsOrder>().eq("trade_no", outTradeNo));
        if (shopGoodsOrder == null) {
            logger.info("-----------------------OutTradeNo fail------------------------------");
            notify.setResultFail("OutTradeNo fail" + outTradeNo);
            return notify.getBodyXML();
        }
        if (shopGoodsOrder.getTotalRealPayment().multiply(new BigDecimal("100")).compareTo(totalAmount) != 0) {
            logger.info("-----------------------TotalAmount fail------------------------------");
            notify.setResultFail("TotalAmount fail" + totalAmount);
            return notify.getBodyXML();
        }
        if (!shopGoodsOrder.getOrderStatus().equals(BaseConstant.ORDER_STATUS_1) &&
                !shopGoodsOrder.getOrderStatus().equals(BaseConstant.ORDER_STATUS_12)) {
            logger.info("-----------------------OrderStatus fail------------------------------");
            notify.setResultFail("OrderStatus fail" + shopGoodsOrder.getOrderStatus());
            return notify.getBodyXML();
        }
        // 逾期订单扣除已返金额重新下单
        if (shopGoodsOrder.getOrderStatus().equals(BaseConstant.ORDER_STATUS_12)) {
            dealOverduePaymentsTasks.returnOverduePayments(shopGoodsOrder);
        }

        Integer id = shopGoodsOrder.getSubmitOrderUser();
        List<UserFund> userFundList = new ArrayList<>();
        //订单
        ShopGoodsOrder myShopGoodsOrder = new ShopGoodsOrder();
        myShopGoodsOrder.setId(shopGoodsOrder.getId());
        myShopGoodsOrder.setPayTime(date);
        // 入库单为结束
        // 存入库存
        List<ShopgoodsOrderDetailVo> shopGoodsOrderDetailList = shopGoodsOrderDetailService.
                selectMyShopGoodsDetailList(shopGoodsOrder.getId());
        myShopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_3);
        if (shopGoodsOrder.getIsStock() != null && shopGoodsOrder.getIsStock().equals(BaseConstant.IS_STOCK_2)) {
            myShopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_5);
            myShopGoodsOrder.setClosingTime(date);
            for (ShopgoodsOrderDetailVo shopGoodsOrderDetail : shopGoodsOrderDetailList) {
                ShopStock shopStock = new ShopStock();
                shopStock.setUserInfoId(id);
                shopStock.setShopGoodsId(shopGoodsOrderDetail.getShopGoodsId());
                shopStock.setCount(shopGoodsOrderDetail.getBuyCount());
                shopStock.setDescribe(shopGoodsOrder.getOrderNo());
                ShopStock stock = shopStockService.selectOne(
                        new EntityWrapper<ShopStock>()
                                .eq("user_info_id", id)
                                .eq("shop_goods_id", shopGoodsOrderDetail.getShopGoodsId()));
                boolean insert;
                if (stock == null) {
                    shopStock.setCreateTime(date);
                    insert = shopStockService.insert(shopStock);
                } else {
                    shopStock.setUpdateTime(date);
                    if (stock.getDescribe() != null && stock.getDescribe().length() > 650) {
                        shopStock.setDescribe(stock.getDescribe().substring(stock.getDescribe().length() - 300) + "," + shopStock.getDescribe());
                    } else {
                        shopStock.setDescribe(stock.getDescribe() + "," + shopStock.getDescribe());
                    }
                    shopStock.setCount(shopStock.getCount() + stock.getCount());
                    insert = shopStockService.update(shopStock, new EntityWrapper<ShopStock>()
                            .eq("user_info_id", id)
                            .eq("shop_goods_id", shopGoodsOrderDetail.getShopGoodsId()));
                }
                if (!insert) {
                    throw new OptimisticLockingFailureException("库存插入失败");
                }
                // 存入入库流水
                UserFund userFund = new UserFund();
                userFund.setTradeUserId(id);
                userFund.setIncreaseMoney(new BigDecimal(shopStock.getCount()));
                userFund.setOrderNo(shopGoodsOrder.getOrderNo());
                userFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                userFund.setTradeDescribe("存入库存");
                userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_10);
                userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_10);
                userFund.setPayType(BaseConstant.PAY_TYPE_10);
                userFund.setTradeTime(date);
                userFundService.InsertUserFund(userFund);
            }
        }
        // 更新订单
        boolean updateOrder = shopGoodsOrderService.updateById(myShopGoodsOrder);
        if (!updateOrder) {
            logger.info("-----------------------updateOrder  fail------------------------------");
            notify.setResultFail("updateOrder  fail");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notify.getBodyXML();
        }
        // 实付金额
        BigDecimal totalRealPayment = shopGoodsOrder.getTotalRealPayment();
        // 实付流水
        UserFund userFundOrder = new UserFund();
        userFundOrder.setTradeUserId(id);
        userFundOrder.setDecreaseMoney(totalRealPayment);
        userFundOrder.setTradeDescribe("微信支付");
        userFundOrder.setOrderNo(outTradeNo);
        userFundOrder.setTradeTime(date);
        userFundOrder.setTradeType(BaseConstant.TRADE_TYPE_2);
        userFundOrder.setAccountType(BaseConstant.ACCOUNT_TYPE_4);
        userFundOrder.setPayType(BaseConstant.PAY_TYPE_2);
        userFundOrder.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
        userFundList.add(userFundOrder);
        // 处理品牌费、佣金股权和分销
        BigDecimal balance2 = BigDecimal.ZERO; // 待使用股权
        BigDecimal balance3 = BigDecimal.ZERO; // 待使用佣金
        // 判断是否是合伙人
        int i = originatorInfoService.selectCount(new EntityWrapper<OriginatorInfo>()
                .eq("user_id", id)
                .eq("status", 0));
        boolean isOriginator;
        isOriginator = i != 0;
        // 权额购买金额需要按照购买时的折扣反推金额
        BigDecimal advanceAmount = BigDecimal.ZERO;
        if (shopGoodsOrder.getAdvanceAmount() != null && shopGoodsOrder.getAdvanceAmount().compareTo(BigDecimal.ZERO) > 0) {
            String[] advanceList = shopGoodsOrder.getBlessing().split(":");
            for (String singleAdvance : advanceList) {
                // 权额ID，等级和金额
                String[] paras = singleAdvance.split(",");
                AdvanceRechargeRecord advanceRechargeRecord = advanceRechargeRecordService.selectById(paras[0]);
                BigDecimal singleAdvanceAmount = new BigDecimal(paras[2]);
                // 权额金额需要反推至充值金额
                advanceAmount = advanceAmount.add(singleAdvanceAmount.multiply(advanceRechargeRecord.getDiscountRate()));
            }
        }
        // 实付金额 = 权额购买金额+余额购买金额+实际支付金额-运费-耗材费
        BigDecimal commissionAmount = advanceAmount
                .add(shopGoodsOrder.getTotalPoint() == null ? BigDecimal.ZERO : shopGoodsOrder.getTotalPoint())
                .add(shopGoodsOrder.getTotalRealPayment() == null ? BigDecimal.ZERO : shopGoodsOrder.getTotalRealPayment())
                .subtract(shopGoodsOrder.getCarriage() == null ? BigDecimal.ZERO : shopGoodsOrder.getCarriage())
                .subtract(shopGoodsOrder.getMaterialAmount() == null ? BigDecimal.ZERO : shopGoodsOrder.getMaterialAmount());
        // 不是合伙人扣除优惠券
        if (!isOriginator) {
            commissionAmount = commissionAmount.subtract(shopGoodsOrder.getCouponAmount() == null ? BigDecimal.ZERO : shopGoodsOrder.getCouponAmount());
        }
        // 实付金额，剔除特殊商品
        for (ShopgoodsOrderDetailVo shopgoodsOrderDetailVo : shopGoodsOrderDetailList) {
            if (specialPlatformGoodsCategoryList.contains(shopgoodsOrderDetailVo.getPlatformGoodsCategoryId())) {
                if (shopGoodsOrder.getOrderSource().equals(BaseConstant.ORDER_SOURCE_3)) {
                    BigDecimal cost = shopgoodsOrderDetailVo.getCostPrice().multiply(new BigDecimal(shopgoodsOrderDetailVo.getBuyCount().toString()));
                    commissionAmount = commissionAmount.subtract(cost);
                } else if (shopGoodsOrder.getOrderSource().equals(BaseConstant.ORDER_SOURCE_1)) {
                    BigDecimal cost = shopgoodsOrderDetailVo.getMenberPrice().multiply(new BigDecimal(shopgoodsOrderDetailVo.getBuyCount().toString()));
                    commissionAmount = commissionAmount.subtract(cost);
                }
            }
        }
        // 品牌费返现
        OriginatorInfoOrder originatorInfoOrder = originatorInfoOrderService.selectOne(
                new EntityWrapper<OriginatorInfoOrder>()
                        .eq("order_status", BaseConstant.ORDER_STATUS_11)
                        .eq("submit_order_user", id));
        if (originatorInfoOrder != null && originatorInfoOrder.getRemianAmount() != null && originatorInfoOrder.getRemianAmount().compareTo(BigDecimal.ZERO) > 0) {
            //返回2%剩余品牌费
            BigDecimal oriAmount = commissionAmount.multiply(BaseConstant.ORIGINATOR_RATE);
            if (oriAmount.compareTo(BigDecimal.ZERO) > 0) {
                if (originatorInfoOrder.getRemianAmount().compareTo(oriAmount) < 0) {
                    oriAmount = originatorInfoOrder.getRemianAmount();
                }
                originatorInfoOrder.setRemianAmount(originatorInfoOrder.getRemianAmount().subtract(oriAmount));
                originatorInfoOrder.setUpdateTime(date);
                boolean updateOri = originatorInfoOrderService.updateById(originatorInfoOrder);
                if (!updateOri) {
                    logger.info("-----------------------updateOri  fail------------------------------");
                    notify.setResultFail("updateOri  fail");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return notify.getBodyXML();
                }
                //插入品牌费扣除流水
                UserFund userFund = new UserFund();
                userFund.setTradeUserId(id);
                userFund.setDecreaseMoney(oriAmount);
                userFund.setTradeDescribe("品牌费返还");
                userFund.setOrderNo(outTradeNo);
                userFund.setTradeTime(date);
                userFund.setTradeType(BaseConstant.TRADE_TYPE_2);
                userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_9);
                userFund.setPayType(BaseConstant.PAY_TYPE_6);
                userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_9);
                userFundList.add(userFund);
                //插入品牌费冻结流水
                UserFund userFund1 = new UserFund();
                userFund1.setTradeUserId(id);
                userFund1.setIncreaseMoney(oriAmount);
                userFund1.setTradeDescribe("品牌费返还");
                userFund1.setOrderNo(outTradeNo);
                userFund1.setTradeTime(date);
                userFund1.setTradeType(BaseConstant.TRADE_TYPE_1);
                userFund1.setAccountType(BaseConstant.ACCOUNT_TYPE_3);
                userFund1.setPayType(BaseConstant.PAY_TYPE_6);
                userFund1.setBussinessType(BaseConstant.BUSSINESS_TYPE_51);
                userFundList.add(userFund1);
                // 品牌费加入待使用佣金
                balance3 = balance3.add(oriAmount);
            }
        }
        // 查询用户姓名
        UserInfo userInfo = userInfoService.selectById(id);
        if (shopGoodsOrder.getOrderSource().equals(BaseConstant.ORDER_SOURCE_1)) {
            // 合伙人佣金 合伙人股权
            // 通过用户id获取该用户的佣金和股权
            UserDistributionConstant myDistribution = userDistributionConstantService.selectMyDistributionById(id);
            UserDistributionConstant parentDistribution = userDistributionConstantService.selectParentDistributionById(id);
            // 合伙人股权
            if (myDistribution != null && myDistribution.getStockRightDiscount().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal stockRight = commissionAmount.multiply(myDistribution.getStockRightDiscount());
                if (stockRight.compareTo(BigDecimal.ZERO) > 0) {
                    UserFund distributionUserFund = new UserFund();
                    distributionUserFund.setTradeUserId(id);
                    distributionUserFund.setIncreaseMoney(stockRight);
                    distributionUserFund.setTradeDescribe("股权收益");
                    distributionUserFund.setOrderNo(outTradeNo);
                    distributionUserFund.setTradeTime(date);
                    distributionUserFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                    distributionUserFund.setAccountType(BaseConstant.ACCOUNT_TYPE_1);
                    distributionUserFund.setPayType(BaseConstant.PAY_TYPE_9);
                    distributionUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_41);
                    userFundList.add(distributionUserFund);
                    // 待使用股权
                    balance2 = balance2.add(stockRight);
                }
            }
            // 合伙人佣金
            if (parentDistribution != null && parentDistribution.getCommissionDiscount().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal commission = commissionAmount.multiply(parentDistribution.getCommissionDiscount());
                if (commission.compareTo(BigDecimal.ZERO) > 0) {
                    boolean updateCommission = userInfoService.updateCommissionDiscount(parentDistribution.getId(), commission);
                    if (!updateCommission) {
                        logger.info("-----------------------updateDistribute  fail------------------------------");
                        notify.setResultFail("updateDistribute  fail");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return notify.getBodyXML();
                    }
                    //插入佣金和股权流水
                    UserFund commissionUserFund = new UserFund();
                    commissionUserFund.setTradeUserId(parentDistribution.getId());
                    commissionUserFund.setIncreaseMoney(commission);
                    commissionUserFund.setOrderNo(outTradeNo);
                    commissionUserFund.setTradeTime(date);
                    commissionUserFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                    commissionUserFund.setAccountType(BaseConstant.ACCOUNT_TYPE_3);
                    commissionUserFund.setPayType(BaseConstant.PAY_TYPE_4);
                    commissionUserFund.setTradeDescribe("合伙人冻结佣金收益，姓名：" + userInfo.getNickname());
                    commissionUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_71);
                    userFundList.add(commissionUserFund);
                }
            }
        } else if (shopGoodsOrder.getOrderSource().equals(BaseConstant.ORDER_SOURCE_3)) {
            //普通消费者分销
            UserDistribution userDistribution = userDistributionService.selectOne(
                    new EntityWrapper<UserDistribution>()
                            .eq("user_id", id));
            if (userDistribution != null && userDistribution.getParentId() != null && userDistribution.getParentId() != null) {
                // 查询供应商分销额
                DistributionCommissionSet distributionCommissionSet = distributionCommissionSetService.selectOne(
                        new EntityWrapper<>());
                if (distributionCommissionSet != null && distributionCommissionSet.getOneLevelAgents() != null) {
                    BigDecimal amount = commissionAmount.multiply(distributionCommissionSet.getOneLevelAgents()
                            .divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP));
                    if (amount.compareTo(BigDecimal.ZERO) > 0) {
                        //一级分销 返点待使用
                        userInfoService.updateCommissionDiscount(userDistribution.getParentId(), amount);
                        // 插入一级分销流水
                        UserFund distributionUserFund = new UserFund();
                        distributionUserFund.setTradeUserId(userDistribution.getParentId());
                        distributionUserFund.setIncreaseMoney(amount);
                        distributionUserFund.setTradeDescribe("冻结收益一，姓名：" + userInfo.getNickname());
                        distributionUserFund.setOrderNo(outTradeNo);
                        distributionUserFund.setTradeTime(date);
                        distributionUserFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                        distributionUserFund.setAccountType(BaseConstant.ACCOUNT_TYPE_3);
                        distributionUserFund.setPayType(BaseConstant.PAY_TYPE_4);
                        distributionUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_81);
                        userFundList.add(distributionUserFund);
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
                        //二级分销 返点待使用
                        userInfoService.updateCommissionDiscount(parentDistribution.getParentId(), amount);
                        // 插入二级分销流水
                        UserFund distributionUserFund = new UserFund();
                        distributionUserFund.setTradeUserId(parentDistribution.getParentId());
                        distributionUserFund.setIncreaseMoney(amount);
                        distributionUserFund.setTradeDescribe("冻结收益二，姓名：" + userInfo.getNickname());
                        distributionUserFund.setOrderNo(outTradeNo);
                        distributionUserFund.setTradeTime(date);
                        distributionUserFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                        distributionUserFund.setAccountType(BaseConstant.ACCOUNT_TYPE_3);
                        distributionUserFund.setPayType(BaseConstant.PAY_TYPE_4);
                        distributionUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_81);
                        userFundList.add(distributionUserFund);
                    }
                }
            }
        }
        // 插入流水
        for (UserFund fund : userFundList) {
            userFundService.InsertUserFund(fund);
        }
        // 更新本人待使用金额
        UserInfo myUser = new UserInfo();
        myUser.setId(id);
        myUser.setBalance2(balance2);
        myUser.setBalance3(balance3);
        myUser.setUpdateTime(date);
        boolean flag = userInfoService.updateMyBalance(myUser);
        if (!flag) {
            logger.info("-----------------------updateMyUser  fail------------------------------");
            notify.setResultFail("updateMyUser fail");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notify.getBodyXML();
        }
        logger.info("-----------------------微信回调接口结束------------------------------");
        notify.setResultSuccess();
        if (shopGoodsOrder.getIsStock() != null && shopGoodsOrder.getIsStock().equals(BaseConstant.IS_STOCK_2)) {
            // 异步处理关闭订单
            dealOverTimeOrderTasks.dealCommissionAmount();
        }
        // 给管理员发送订单消息
        shopGoodsOrderService.sendOrderTemplateToManager(shopGoodsOrder);
        return notify.getBodyXML();
    }
}
