package com.jgzy.core.personalCenter.controller;


import com.alipay.api.domain.DiscountDetail;
import com.jgzy.constant.BaseConstant;
import com.jgzy.constant.ErrorCodeEnum;
import com.jgzy.core.personalCenter.service.IDistDrawCashDetailService;
import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.personalCenter.vo.UserInfoVo;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.DistDrawCashDetail;
import com.jgzy.entity.po.UserFund;
import com.jgzy.entity.po.UserInfo;
import com.jgzy.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 提现明细 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-11-21
 */
@RefreshScope
@RestController
@RequestMapping("/api/distDrawCashDetail")
@Api(value = "提现接口", description = "提现接口")
public class DistDrawCashDetailController {
    @Autowired
    private IDistDrawCashDetailService distDrawCashDetailService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserFundService userFundService;

    @ApiOperation(value = "新增提现信息", notes = "新增提现信息")
    @PostMapping
    @Transactional
    public ResultWrapper<DiscountDetail> save(@RequestBody @Validated DistDrawCashDetail po) {
        ResultWrapper<DiscountDetail> resultWrapper = new ResultWrapper<>();
        // 判断金额是否充足
        Integer id = UserUuidThreadLocal.get().getId();
        UserInfoVo userInfo = userInfoService.selectMyUserJoinOriginatorInfo(id);
        if (userInfo.getBalance1()==null || userInfo.getBalance1().compareTo(po.getWithdrawNum()) < 0) {
            resultWrapper = new ResultWrapper<>(ErrorCodeEnum.ERROR_ARGS.getKey(), "余额不足，请重新提现！");
            return resultWrapper;
        }
        // init提现信息
        po.setApplyTime(new Date());
        po.setUserId(id);
        po.setRealName(userInfo.getUserName());
        po.setWechatNo(userInfo.getWeixin());
        po.setWithdrawState(1);
        po.setWithdrawRemark("微信提现");
        po.setAccountType(2);
        po.setPartnerTradeNo(WeiXinInfoController.GetRandomCode(32, 3));
        boolean successful = distDrawCashDetailService.insert(po);
        if (!successful) {
            throw new OptimisticLockingFailureException("插入提现信息失败！");
        }
        // 冻结用户金额
        boolean flag = userInfoService.withDrawAmount(id, po.getWithdrawNum());
        if (!flag){
            throw new OptimisticLockingFailureException("插入冻结金额失败！");
        }
        // 插入余额转出流水
        UserFund userFund = new UserFund();
        userFund.setTradeUserId(id);
        userFund.setDecreaseMoney(po.getWithdrawNum());
        userFund.setOrderNo(po.getPartnerTradeNo());
        userFund.setTradeType(BaseConstant.TRADE_TYPE_2);
        userFund.setTradeDescribe("用户：" + userInfo.getNickname() + "提现放入提现账户");
        userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_2);
        userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_6);
        userFund.setPayType(BaseConstant.PAY_TYPE_4);
        userFund.setTradeTime(new Date());
        userFundService.InsertUserFund(userFund);
        // 插入提现流水
        UserFund fund = new UserFund();
        fund.setTradeUserId(id);
        fund.setIncreaseMoney(po.getWithdrawNum());
        fund.setOrderNo(po.getPartnerTradeNo());
        fund.setTradeType(BaseConstant.TRADE_TYPE_1);
        fund.setTradeDescribe("用户：" + userInfo.getNickname() + "提现放入提现金额账户");
        fund.setAccountType(BaseConstant.ACCOUNT_TYPE_6);
        fund.setBussinessType(BaseConstant.BUSSINESS_TYPE_61);
        fund.setPayType(BaseConstant.PAY_TYPE_4);
        fund.setTradeTime(new Date());
        userFundService.InsertUserFund(fund);
        return resultWrapper;
    }
}

