package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IAdvanceRechargeInfoService;
import com.jgzy.core.personalCenter.service.IAdvanceRechargeOrderService;
import com.jgzy.core.personalCenter.service.IAdvanceRechargeRecordService;
import com.jgzy.core.personalCenter.service.IUserOauthService;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.common.WeiXinData;
import com.jgzy.entity.common.WeiXinTradeType;
import com.jgzy.entity.po.*;
import com.jgzy.utils.CommonUtil;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
 * @since 2018-10-24
 */
@RefreshScope
@RestController
@RequestMapping("/api/advanceRechargeOrder")
@Api(value = "权额支付订单", description = "权额支付订单")
public class AdvanceRechargeOrderController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IAdvanceRechargeOrderService advanceRechargeOrderService;
    @Autowired
    private IAdvanceRechargeInfoService advanceRechargeInfoService;
    @Autowired
    private IAdvanceRechargeRecordService advanceRechargeRecordService;
    @Autowired
    private IUserOauthService userOauthService;
    @Autowired
    private IUserFundService userFundService;

    @ApiOperation(value = "新增权额订单", notes = "新增权额订单")
    @PostMapping(value = "/save")
    public ResultWrapper<HashMap<String, String>> save(@ApiParam(value = "权额id", required = true) @RequestParam Integer id,
                                                       @ApiParam(value = "支付类型 支付宝=1|微信支付=2") @RequestParam Integer payType) {
        ResultWrapper<HashMap<String, String>> resultWrapper = new ResultWrapper<>();

        try {
            if (payType != BaseConstant.PAY_TYPE_2) {
                resultWrapper.setErrorMsg("支付类型错误");
                return resultWrapper;
            }
            AdvanceRechargeOrder advanceRechargeOrder = new AdvanceRechargeOrder();
            // 生成权额订单
            AdvanceRechargeInfo advanceRechargeInfo = advanceRechargeInfoService.selectById(id);
            // 返回参数
            HashMap<String, String> resultMap = new HashMap<String, String>();
            //唤起第三方支付 weixin
            // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
            BigDecimal total_fee = advanceRechargeInfo.getAmount();
            String subject = "军港之业订单"; // 订单名称 (必填)
            // 商户网站订单系统中唯一订单号(必填) 订单号和支付订单号
            String out_trade_no = CommonUtil.getTradeNo();
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
            String notify_url = "/api/advanceRechargeOrder/constant/weixinNotifyUrl";
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
            advanceRechargeOrder.setOrderNo(out_trade_no);
            advanceRechargeOrder.setCreateTime(new Date());
            advanceRechargeOrder.setLevelId(advanceRechargeInfo.getLevelId());
            advanceRechargeOrder.setAmount(advanceRechargeInfo.getAmount());
            advanceRechargeOrder.setDiscountRate(advanceRechargeInfo.getDiscountRate());
            advanceRechargeOrder.setOrderStatus(BaseConstant.ORDER_STATUS_1);
            advanceRechargeOrder.setPayType(BaseConstant.PAY_TYPE_2);
            advanceRechargeOrder.setSubmitOrderUser(UserUuidThreadLocal.get().getId());
            boolean insert = advanceRechargeOrderService.insert(advanceRechargeOrder);
            if (!insert) {
                throw new OptimisticLockingFailureException("订单插入失败");
            }
            resultMap.put("appId", wxData.get("appId"));
            resultMap.put("nonceStr", wxData.get("nonceStr"));
            resultMap.put("timeStamp", wxData.get("timeStamp"));
            resultMap.put("signType", wxData.get("signType"));
            resultMap.put("packageValue", wxData.get("package"));
            resultMap.put("sign", wxData.get("sign"));
            resultMap.put("order_no", out_trade_no);
            resultMap.put("order_ids", out_trade_no);
            resultWrapper.setResult(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultWrapper;
    }

    @ApiOperation(value = "微信回调接口", notes = "微信回调接口")
    @PostMapping(value = "/constant/weixinNotifyUrl")
    public String weixinNotifyUrl(HttpServletRequest request) {
        WeiXinNotify notify = WeiXinPayUtil.notify_url(request);
        String outTradeNo = notify.getOut_trade_no(); // 商户网站订单系统中唯一订单号
        BigDecimal totalAmount = new BigDecimal(notify.getTotal_fee());// 付款金额

        AdvanceRechargeOrder advanceRechargeOrder = advanceRechargeOrderService.selectOne(
                new EntityWrapper<AdvanceRechargeOrder>().eq("order_no", outTradeNo));
        if (advanceRechargeOrder == null || advanceRechargeOrder.getAmount() == null ||
                advanceRechargeOrder.getAmount().compareTo(totalAmount) != 0) {
            notify.setResultFail("order fail");
            return notify.getBodyXML();
        }
        // 更新订单
        AdvanceRechargeOrder myAdvanceRechargeOrder = new AdvanceRechargeOrder();
        myAdvanceRechargeOrder.setId(advanceRechargeOrder.getId());
        myAdvanceRechargeOrder.setOrderStatus(BaseConstant.ORDER_STATUS_11);
        myAdvanceRechargeOrder.setPayTime(new Date());
        advanceRechargeOrderService.updateById(myAdvanceRechargeOrder);
        // 插入流水
        UserFund userFund = new UserFund();
        userFund.setTradeUserId(advanceRechargeOrder.getSubmitOrderUser());
        userFund.setDecreaseMoney(totalAmount);
        userFund.setOrderNo(outTradeNo);
        userFund.setTradeType(BaseConstant.TRADE_TYPE_1);
        userFund.setTradeDescribe("权额充值");
        userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_8);
        userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_2);
        userFund.setPayType(BaseConstant.PAY_TYPE_2);
        userFundService.InsertUserFund(userFund);
        // 插入权额
        AdvanceRechargeRecord advanceRechargeRecord = advanceRechargeRecordService.selectOne(
                new EntityWrapper<AdvanceRechargeRecord>()
                        .eq("level_id", advanceRechargeOrder.getLevelId())
                        .eq("discount_rate", advanceRechargeOrder.getDiscountRate()));
        // 存在权额是更新，不存在插入
        if (advanceRechargeRecord != null){
            advanceRechargeRecord.setAmount(advanceRechargeRecord.getAmount().add(totalAmount));
            advanceRechargeRecord.setUpdateTime(new Date());
            advanceRechargeRecordService.updateById(advanceRechargeRecord);
        }else {
            advanceRechargeRecord = new AdvanceRechargeRecord();
            advanceRechargeRecord.setLevelId(advanceRechargeOrder.getLevelId());
            advanceRechargeRecord.setAmount(totalAmount);
            advanceRechargeRecord.setCreateTime(new Date());
            advanceRechargeRecord.setDiscountRate(advanceRechargeOrder.getDiscountRate());
            advanceRechargeRecord.setUserId(advanceRechargeOrder.getSubmitOrderUser());
            advanceRechargeRecordService.insert(advanceRechargeRecord);
        }
        logger.info("-----------------------微信回调接口结束------------------------------");
        notify.setResultSuccess();
        return notify.getBodyXML();
    }
}

