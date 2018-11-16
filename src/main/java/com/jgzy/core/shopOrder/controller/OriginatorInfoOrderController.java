package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IUserOauthService;
import com.jgzy.core.shopOrder.service.IOriginatorInfoOrderService;
import com.jgzy.core.shopOrder.service.IOriginatorInfoService;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.common.WeiXinData;
import com.jgzy.entity.common.WeiXinTradeType;
import com.jgzy.entity.po.OriginatorInfo;
import com.jgzy.entity.po.OriginatorInfoOrder;
import com.jgzy.entity.po.UserFund;
import com.jgzy.entity.po.UserOauth;
import com.jgzy.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-23
 */
@RefreshScope
@RestController
@RequestMapping("/api/originatorInfoOrder")
@Api(value = "合伙人缴费订单", description = "合伙人缴费订单")
public class OriginatorInfoOrderController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${originator.amount}")
    private BigDecimal amount;

    @Autowired
    private IOriginatorInfoOrderService originatorInfoOrderService;
    @Autowired
    private IOriginatorInfoService originatorInfoService;
    @Autowired
    private IUserFundService userFundService;
    @Autowired
    private IUserOauthService userOauthService;

    @GetMapping(value = "/pay")
    @ApiOperation(value = "合伙人缴纳品牌费", notes = "合伙人缴纳品牌费")
    @Transactional
    public ResultWrapper<HashMap<String, String>> pay() throws Exception {
        ResultWrapper<HashMap<String, String>> resultWrapper = new ResultWrapper<>();
        // 返回参数
        HashMap<String, String> resultMap = new HashMap<String, String>();
        Integer id = UserUuidThreadLocal.get().getId();
        int count = originatorInfoService.selectCount(new EntityWrapper<OriginatorInfo>()
                .eq("status", 0)
                .eq("user_id", id));
        if (count != 0) {
            throw new OptimisticLockingFailureException("已成为正式合伙人");
        }
        int orderCount = originatorInfoOrderService.selectCount(new EntityWrapper<OriginatorInfoOrder>()
                .eq("submit_order_user", id)
                .eq("order_status", BaseConstant.ORDER_STATUS_11));
        if (orderCount != 0) {
            throw new OptimisticLockingFailureException("已缴纳过品牌费");
        }
        // 查询已生成的订单
        OriginatorInfoOrder originatorInfoOrder = originatorInfoOrderService.selectOne(new EntityWrapper<OriginatorInfoOrder>()
                .eq("submit_order_user", id)
                .eq("order_status", BaseConstant.ORDER_STATUS_1));
        if (originatorInfoOrder == null){
            // init
            originatorInfoOrder = new OriginatorInfoOrder();
            originatorInfoOrder.setOrderStatus(BaseConstant.ORDER_STATUS_1);
            originatorInfoOrder.setSubmitOrderUser(id);
            originatorInfoOrder.setOrderAmount(amount);
            originatorInfoOrder.setRemianAmount(amount);
            originatorInfoOrder.setCreateTime(DateUtil.getDate());
            originatorInfoOrder.setPayType(BaseConstant.PAY_TYPE_2);
            // 订单号
            String out_trade_no = CommonUtil.getTradeNo();
            originatorInfoOrder.setOrderNo(out_trade_no);
            // 预期时间
            originatorInfoOrder.setValidOrderTime(DateUtil.getHoursLater(2));
            // 插入订单
            boolean orderInsert = originatorInfoOrderService.insert(originatorInfoOrder);
            if (!orderInsert) {
                throw new OptimisticLockingFailureException("插入订单失败！");
            }
        }
        String subject = "合伙人费用缴纳"; // 订单名称 (必填)
        // 获取服务器ip
        String ip = InetAddress.getLocalHost().getHostAddress();
        UserOauth userOauth = userOauthService.selectOne(
                new EntityWrapper<UserOauth>()
                        .eq("user_id", id));
        if (userOauth == null || userOauth.getOauthOpenid() == null) {
            resultMap.put("return_code", "FAIL");
            resultMap.put("err_code_des", "openid is null");
            resultWrapper.setResult(resultMap);
            return resultWrapper;
        }
        String openid = userOauth.getOauthOpenid(); // 微信认证 openid (必填)
        String product_id = ""; // 产品id (非必填)
        String notify_url = "http://jgapi.china-mail.com.cn/api/originatorInfoOrder/constant/weixinNotifyUrl";
        // 检验订单状态以及订单的金额
        // TODO 测试用付款
        amount = new BigDecimal("0.01");
        WeiXinData wxData = WeiXinPayUtil.makePreOrder(WeiXinTradeType.JSAPI, openid, product_id,
                originatorInfoOrder.getOrderNo(), subject, amount.doubleValue(), ip, notify_url);
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

        // 订单成功
        resultMap.put("appId", wxData.get("appId"));
        resultMap.put("nonceStr", wxData.get("nonceStr"));
        resultMap.put("timeStamp", wxData.get("timeStamp"));
        resultMap.put("signType", wxData.get("signType"));
        resultMap.put("packageValue", wxData.get("package"));
        resultMap.put("sign", wxData.get("sign"));
        resultMap.put("order_no", originatorInfoOrder.getOrderNo());
        resultMap.put("order_ids", "");
        resultWrapper.setResult(resultMap);
        return resultWrapper;
    }

    @ApiOperation(value = "微信回调接口", notes = "微信回调接口")
    @PostMapping(value = "/constant/weixinNotifyUrl")
    @Transactional
    public String weixinNotifyUrl(HttpServletRequest request) {
        System.out.println("==========微信支付回调开始=============");
        WeiXinNotify notify = WeiXinPayUtil.notify_url(request);
        String outTradeNo = notify.getOut_trade_no(); // 商户网站订单系统中唯一订单号
        BigDecimal totaAmount = new BigDecimal(notify.getTotal_fee()); // 付款金额
        OriginatorInfoOrder originatorInfoOrder = originatorInfoOrderService.selectOne(
                new EntityWrapper<OriginatorInfoOrder>()
                        .eq("order_no", outTradeNo));
        // 校验订单
        if (originatorInfoOrder == null) {
            logger.info("-----------------------OutTradeNo fail------------------------------");
            notify.setResultFail("OutTradeNo fail" + outTradeNo);
            return notify.getBodyXML();
        }
        if (!originatorInfoOrder.getOrderStatus().equals(BaseConstant.ORDER_STATUS_1)){
            logger.info("-----------------------OrderStatus fail------------------------------");
            notify.setResultFail("OrderStatus fail" + originatorInfoOrder.getOrderStatus());
            return notify.getBodyXML();
        }
        // TODO 测试用注释
//        if (originatorInfoOrder.getOrderAmount().multiply(new BigDecimal("100")).compareTo(totaAmount) != 0) {
//            logger.info("-----------------------TotalAmount fail------------------------------");
//            logger.info("-----------------------" + notify.toString() + "------------------------------");
//            notify.setResultFail("TotalAmount fail" + totaAmount);
//            return notify.getBodyXML();
//        }
        logger.info("-----------------------" + notify + "------------------------------");
        // 更新订单
        originatorInfoOrder.setOrderStatus(BaseConstant.ORDER_STATUS_11);
        originatorInfoOrder.setPayTime(new Date());
        boolean order_no = originatorInfoOrderService.update(originatorInfoOrder,
                new EntityWrapper<OriginatorInfoOrder>()
                        .eq("order_no", outTradeNo));
        if (!order_no) {
            notify.setResultFail("insert order fail");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notify.getBodyXML();
        }
        // 更新合伙人信息
        OriginatorInfo originatorInfo = new OriginatorInfo();
        originatorInfo.setUserId(originatorInfoOrder.getSubmitOrderUser());
        originatorInfo.setStatus(0);
        boolean updateById = originatorInfoService.update(originatorInfo,
                new EntityWrapper<OriginatorInfo>()
                        .eq("user_id", originatorInfo.getUserId()));
        if (!updateById) {
            notify.setResultFail("insert userFund fail");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notify.getBodyXML();
        }
        // 插入流水
        UserFund userFund = new UserFund();
        userFund.setTradeUserId(originatorInfoOrder.getSubmitOrderUser());
        userFund.setIncreaseMoney(originatorInfoOrder.getOrderAmount());
        userFund.setOrderNo(outTradeNo);
        userFund.setTradeTime(new Date());
        userFund.setTradeType(BaseConstant.TRADE_TYPE_2);
        userFund.setTradeDescribe("微信支付");
        userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_9);
        userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_1);
        userFund.setPayType(BaseConstant.PAY_TYPE_2);
        userFundService.InsertUserFund(userFund);
        // 微信认证 openid (必填)
//        UserOauth userOauth = userOauthService.selectOne(
//                new EntityWrapper<UserOauth>()
//                        .eq("user_id", UserUuidThreadLocal.get().getId()));
//        if (userOauth == null || userOauth.getOauthOpenid() == null) {
//            throw new OptimisticLockingFailureException("微信认证有误");
//        }
//        // 推送模版消息
//        TemplateMessageUtil.initOriPaySuccessTemplate(userOauth.getOauthOpenid(), originatorInfoOrder.getOrderNo(),
//                originatorInfoOrder.getOrderAmount().toString());
        logger.info("-----------------------微信回调接口结束------------------------------");
        notify.setResultSuccess();
        return notify.getBodyXML();
    }

    @GetMapping(value = "/detail")
    @ApiOperation(value = "查询合伙人订单", notes = "查询合伙人订单")
    public ResultWrapper<OriginatorInfoOrder> detail(@ApiParam(value = "状态(待付款=1|交易关闭=11)") @RequestParam(required = false) Integer orderStatus) {
        ResultWrapper<OriginatorInfoOrder> resultWrapper = new ResultWrapper<>();
        OriginatorInfoOrder originatorInfoOrder = originatorInfoOrderService.selectOne(
                new EntityWrapper<OriginatorInfoOrder>()
                        .eq("submit_order_user", UserUuidThreadLocal.get().getId())
                        .eq("order_status", orderStatus)
                        .last("limit 1"));
        resultWrapper.setResult(originatorInfoOrder);
        return resultWrapper;
    }
}

