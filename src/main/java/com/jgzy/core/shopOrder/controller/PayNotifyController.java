package com.jgzy.core.shopOrder.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.config.AlipayConfig;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IAdvanceRechargeRecordService;
import com.jgzy.core.personalCenter.service.IUserDistributionConstantService;
import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.personalCenter.service.IUserOauthService;
import com.jgzy.core.shopOrder.service.*;
import com.jgzy.entity.po.*;
import com.jgzy.utils.DateUtil;
import com.jgzy.utils.WeiXinNotify;
import com.jgzy.utils.WeiXinPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IAdvanceRechargeRecordService advanceRechargeRecordService;
    @Autowired
    private IShopStockService shopStockService;
    @Autowired
    private IShopGoodsOrderDetailService shopGoodsOrderDetailService;
    @Autowired
    private IUserOauthService userOauthService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserDistributionConstantService userDistributionConstantService;
    @Autowired
    private IOriginatorInfoOrderService originatorInfoOrderService;
    @Autowired
    private IOriginatorInfoService originatorInfoService;

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
    public String weixinNotifyUrl(HttpServletRequest request) throws Exception {
        logger.info("-----------------------——微信回调开始——------------------------------");
        BigDecimal zero = new BigDecimal("0");
        Date date = new Date();
        // 佣金股权金额
        BigDecimal commissionAmont = new BigDecimal("0");
        WeiXinNotify notify = WeiXinPayUtil.notify_url(request);
        String outTradeNo = notify.getOut_trade_no(); // 商户网站订单系统中唯一订单号
        BigDecimal totalAmount = new BigDecimal(notify.getTotal_fee()); // 付款金额

//        WeiXinNotify notify = new WeiXinNotify();
//        String outTradeNo = "HHR110918001414628";

        // 校验订单
        ShopGoodsOrder shopGoodsOrder = shopGoodsOrderService.selectOne(
                new EntityWrapper<ShopGoodsOrder>().eq("trade_no", outTradeNo));
        if (shopGoodsOrder == null) {
            logger.info("-----------------------OutTradeNo fail------------------------------");
            notify.setResultFail("OutTradeNo fail" + outTradeNo);
            return notify.getBodyXML();
        }
        Integer id = shopGoodsOrder.getSubmitOrderUser();
        // TODO 测试用隐藏判断金额是否正确
//        if (shopGoodsOrder.getTotalRealPayment().multiply(new BigDecimal("100")).compareTo(totalAmount) != 0) {
//            logger.info("-----------------------TotalAmount fail------------------------------");
//            notify.setResultFail("TotalAmount fail" + totalAmount);
//            return notify.getBodyXML();
//        }
        // 佣金股权金
        commissionAmont = commissionAmont.add(shopGoodsOrder.getTotalRealPayment());
        List<UserFund> userFundList = new ArrayList<>();
        // 判断是否有权额支付
        if (shopGoodsOrder.getAdvanceAmount() != null && shopGoodsOrder.getAdvanceAmount().compareTo(zero) > 0) {
            // 佣金股权金
            commissionAmont = commissionAmont.add(shopGoodsOrder.getAdvanceAmount());
            // 获取用户权额
            List<AdvanceRechargeRecord> advanceRechargeRecordList = advanceRechargeRecordService.selectList(
                    new EntityWrapper<AdvanceRechargeRecord>()
                            .eq("user_id", id)
                            .ne("amount", 0)
                            .orderBy("level_id DESC"));
            List<AdvanceRechargeRecord> myAdvanceRechargeRecordList = new ArrayList<>();
            // 权额金额
            BigDecimal advanceAmount = shopGoodsOrder.getAdvanceAmount();
            if (advanceAmount != null && advanceAmount.compareTo(new BigDecimal(0)) > 0) {
                // 处理权额
                for (AdvanceRechargeRecord advanceRechargeRecord : advanceRechargeRecordList) {
                    if (!shopGoodsOrder.getBlessing().contains(advanceRechargeRecord.getId().toString())) {
                        continue;
                    }
                    if (advanceRechargeRecord.getAmount().compareTo(advanceAmount) >= 0) {
                        // 权额足够
                        AdvanceRechargeRecord myAdvanceRechargeRecord = new AdvanceRechargeRecord();
                        myAdvanceRechargeRecord.setId(advanceRechargeRecord.getId());
                        myAdvanceRechargeRecord.setAmount(advanceRechargeRecord.getAmount().subtract(advanceAmount));
                        myAdvanceRechargeRecordList.add(myAdvanceRechargeRecord);
                        break;
                    } else {
                        // 权额不足
                        AdvanceRechargeRecord myAdvanceRechargeRecord = new AdvanceRechargeRecord();
                        myAdvanceRechargeRecord.setId(advanceRechargeRecord.getId());
                        myAdvanceRechargeRecord.setAmount(new BigDecimal(0));
                        myAdvanceRechargeRecordList.add(myAdvanceRechargeRecord);
                    }
                }
                // 流水
                UserFund userFund = new UserFund();
                userFund.setTradeUserId(id);
                userFund.setDecreaseMoney(advanceAmount);
                userFund.setTradeDescribe("权额支付");
                userFund.setOrderNo(outTradeNo);
                userFund.setTradeTime(date);
                userFund.setTradeType(BaseConstant.TRADE_TYPE_2);
                userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_8);
                userFund.setPayType(BaseConstant.PAY_TYPE_3);
                userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
                userFundList.add(userFund);
            }
            // 更新权额
            if (!CollectionUtils.isEmpty(myAdvanceRechargeRecordList)) {
                boolean b = advanceRechargeRecordService.updateBatchById(myAdvanceRechargeRecordList);
                if (!b) {
                    logger.info("-----------------------updateAdvance fail------------------------------");
                    notify.setResultFail("updateAdvance fail");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return notify.getBodyXML();
                }
            }
        }
        //订单
        ShopGoodsOrder myShopGoodsOrder = new ShopGoodsOrder();
        myShopGoodsOrder.setId(shopGoodsOrder.getId());
        myShopGoodsOrder.setPayTime(date);
        // 入库单为结束
        if (shopGoodsOrder.getIsStock() != null && shopGoodsOrder.getIsStock().equals(BaseConstant.IS_STOCK_2)) {
            myShopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_11);
            myShopGoodsOrder.setClosingTime(date);
            // 存入库存
            List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = shopGoodsOrderDetailService.selectList(
                    new EntityWrapper<ShopGoodsOrderDetail>()
                            .eq("order_id", shopGoodsOrder.getId()));
            for (ShopGoodsOrderDetail shopGoodsOrderDetail : shopGoodsOrderDetailList) {
                ShopStock shopStock = new ShopStock();
                shopStock.setUserInfoId(id);
                shopStock.setShopGoodsId(shopGoodsOrderDetail.getShopGoodsId());
                shopStock.setCount(shopGoodsOrderDetail.getBuyCount());
                shopStock.setDescribe(shopGoodsOrderDetail.getOrderId().toString());
                ShopStock stock = shopStockService.selectOne(
                        new EntityWrapper<ShopStock>()
                                .eq("user_info_id", id)
                                .eq("shop_goods_id", shopGoodsOrderDetail.getShopGoodsId()));
                boolean stockFlag;
                if (stock == null) {
                    shopStock.setCreateTime(date);
                    stockFlag = shopStockService.insert(shopStock);
                } else {
                    shopStock.setUpdateTime(date);
                    if (shopStock.getDescribe().length() > 650) {
                        shopStock.setDescribe(stock.getDescribe().substring(stock.getDescribe().length() - 300));
                    }
                    shopStock.setDescribe(shopStock.getDescribe() + "," + shopGoodsOrder.getOrderNo());
                    shopStock.setCount(shopStock.getCount() + shopGoodsOrderDetail.getShopGoodsId());
                    stockFlag = shopStockService.update(shopStock, new EntityWrapper<ShopStock>()
                            .eq("user_info_id", id)
                            .eq("shop_goods_id", shopGoodsOrderDetail.getShopGoodsId()));

                }
                if (!stockFlag) {
                    logger.info("-----------------------stock fail------------------------------");
                    notify.setResultFail("stock fail");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return notify.getBodyXML();
                }
            }
        } else {
            myShopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_3);
        }
        // 余额更新
        UserInfo userInfo = userInfoService.selectById(id);
        if (shopGoodsOrder.getPayType().equals(BaseConstant.PAY_TYPE_5)) {
            boolean b = userInfoService.updateById(userInfo);
            if (!b) {
                logger.info("-----------------------updateUser  fail------------------------------");
                notify.setResultFail("updateUser  fail");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return notify.getBodyXML();
            }
            UserFund remain = new UserFund();
            remain.setTradeUserId(id);
            remain.setDecreaseMoney(shopGoodsOrder.getTotalPoint());
            remain.setOrderNo(shopGoodsOrder.getOrderNo());
            remain.setTradeType(BaseConstant.TRADE_TYPE_2);
            remain.setTradeDescribe("余额支付");
            remain.setAccountType(BaseConstant.ACCOUNT_TYPE_2);
            remain.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
            remain.setPayType(BaseConstant.PAY_TYPE_4);
            userFundList.add(remain);
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
        // 微信认证 openid (必填)
//        UserOauth userOauth = userOauthService.selectOne(
//                new EntityWrapper<UserOauth>()
//                        .eq("user_id", id));
//        if (userOauth == null || userOauth.getOauthOpenid() == null) {
//            logger.info("-----------------------微信认证有误  fail------------------------------");
//            notify.setResultFail("微信认证有误  fail");
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return notify.getBodyXML();
//        }
//        // 推送支付成功模版消息
//        TemplateMessageUtil.initPaySuccessTemplate(userOauth.getOauthOpenid(), shopGoodsOrder.getOrderNo(), shopGoodsOrder.getOrderAmountTotal().toString(),
//                shopGoodsOrder.getCouponAmount().toString(), shopGoodsOrder.getTotalRealPayment().toString());
        // TODO 品牌费返现
        OriginatorInfoOrder originatorInfoOrder = originatorInfoOrderService.selectOne(
                new EntityWrapper<OriginatorInfoOrder>()
                        .eq("order_status", BaseConstant.ORDER_STATUS_11)
                        .eq("submit_order_user", id));
        if (originatorInfoOrder.getRemianAmount() != null && originatorInfoOrder.getRemianAmount().compareTo(zero) > 0) {
            //返回2%剩余品牌费
            BigDecimal oriAmount = originatorInfoOrder.getOrderAmount().multiply(BaseConstant.ORIGINATOR_RATE);
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
            //插入品牌费流水
            UserFund userFund = new UserFund();
            userFund.setTradeUserId(id);
            userFund.setDecreaseMoney(oriAmount);
            userFund.setTradeDescribe("品牌费返还");
            userFund.setOrderNo(outTradeNo);
            userFund.setTradeTime(date);
            userFund.setTradeType(BaseConstant.TRADE_TYPE_1);
            userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_2);
            userFund.setPayType(BaseConstant.PAY_TYPE_4);
            userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_5);
            userFundList.add(userFund);
        }
        // TODO 合伙人佣金 合伙人股权
        // 通过用户id获取该用户的佣金和股权
        UserDistributionConstant myDistribution = userDistributionConstantService.selectMyDistributionById(id);
        UserDistributionConstant parentDistribution = userDistributionConstantService.selectParentDistributionById(id);
        if (myDistribution != null && parentDistribution.getStockRightDiscount().compareTo(zero) > 0) {
            boolean updateDiscount = userInfoService.updateStockRightDiscount(id, commissionAmont.multiply(myDistribution.getStockRightDiscount()));
            if (!updateDiscount) {
                logger.info("-----------------------updateDiscount  fail------------------------------");
                notify.setResultFail("updateDiscount  fail");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return notify.getBodyXML();
            }
            UserFund distributionUserFund = new UserFund();
            distributionUserFund.setTradeUserId(id);
            distributionUserFund.setIncreaseMoney(commissionAmont.multiply(myDistribution.getStockRightDiscount()));
            distributionUserFund.setTradeDescribe("股权收益");
            distributionUserFund.setOrderNo(outTradeNo);
            distributionUserFund.setTradeTime(date);
            distributionUserFund.setTradeType(BaseConstant.TRADE_TYPE_1);
            distributionUserFund.setAccountType(BaseConstant.ACCOUNT_TYPE_1);
            distributionUserFund.setPayType(BaseConstant.PAY_TYPE_9);
            distributionUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_4);
            userFundList.add(distributionUserFund);
        }
        if (parentDistribution != null && parentDistribution.getCommissionDiscount().compareTo(zero) > 0) {
            boolean updateCommission = userInfoService.updateCommissionDiscount(parentDistribution.getId(), commissionAmont.multiply(parentDistribution.getCommissionDiscount()));
            if (!updateCommission) {
                logger.info("-----------------------updateDistribute  fail------------------------------");
                notify.setResultFail("updateDistribute  fail");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return notify.getBodyXML();
            }
            //判断是否是合伙人
            int i = originatorInfoService.selectCount(new EntityWrapper<OriginatorInfo>()
                    .eq("user_id", id)
                    .eq("status", 0));
            //插入佣金和股权流水
            UserFund commissionUserFund = new UserFund();
            commissionUserFund.setTradeUserId(parentDistribution.getId());
            commissionUserFund.setDecreaseMoney(commissionAmont.multiply(parentDistribution.getCommissionDiscount()));
            commissionUserFund.setOrderNo(outTradeNo);
            commissionUserFund.setTradeTime(date);
            commissionUserFund.setTradeType(BaseConstant.TRADE_TYPE_1);
            commissionUserFund.setAccountType(BaseConstant.ACCOUNT_TYPE_2);
            commissionUserFund.setPayType(BaseConstant.PAY_TYPE_4);
            if (i == 0) {
                commissionUserFund.setTradeDescribe("消费者佣金收益，姓名：" + userInfo.getNickname());
                commissionUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_8);
            } else {
                commissionUserFund.setTradeDescribe("合伙人佣金收益，姓名：" + userInfo.getNickname());
                commissionUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_7);
            }
            userFundList.add(commissionUserFund);
        }
        // 插入流水
        for (UserFund fund : userFundList) {
//            fund.setTradeUserId(id);
//            fund.setOrderNo(outTradeNo);
//            fund.setTradeType(BaseConstant.TRADE_TYPE_2);
//            fund.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
            userFundService.InsertUserFund(fund);
        }
        logger.info("-----------------------微信回调接口结束------------------------------");
        notify.setResultSuccess();
        return notify.getBodyXML();
    }
}
