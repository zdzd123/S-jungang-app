package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 提现明细
 * </p>
 *
 * @author zou
 * @since 2018-11-21
 */
@TableName("dist_draw_cash_detail")
@ApiModel(value = "提现-po")
public class DistDrawCashDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "账户类型(支付宝=1|微信=2|银行=3)", example = "1")
    private Integer accountType;
    @ApiModelProperty(value = "支付宝账号", example = "1")
    private String alipayNo;
    @ApiModelProperty(value = "微信号", example = "1")
    private String wechatNo;
    @ApiModelProperty(value = "银行卡号", example = "1")
    private String bankCardNo;
    @ApiModelProperty(value = "银行卡开户行", example = "1")
    private String openBank;
    @ApiModelProperty(value = "银行卡开户人", example = "1")
    private String accountHolder;
    @ApiModelProperty(value = "真实姓名(支付宝账号以及微信账号)", example = "1")
    private String realName;
    @ApiModelProperty(value = "提现金额", example = "1")
    private BigDecimal withdrawNum;
    @ApiModelProperty(value = "提现备注", example = "1")
    private String withdrawRemark;
    @ApiModelProperty(value = "提现状态(提现中=1|提现成功=2|提现失败=3)", example = "1")
    private Integer withdrawState;
    @ApiModelProperty(value = "提现人(用户id)", example = "1")
    private Integer userId;
    @ApiModelProperty(value = "提现申请时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyTime;
    @ApiModelProperty(value = "提现成功时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date successTime;
    @ApiModelProperty(value = "提现失败时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date failTime;
    @ApiModelProperty(value = "提现交易编号", example = "1")
    private String partnerTradeNo;
    @ApiModelProperty(value = "提现手续费", example = "1")
    private BigDecimal fee;
    @ApiModelProperty(value = "提现实际到账金额", example = "1")
    private BigDecimal amount;
    @ApiModelProperty(value = "更新时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public BigDecimal getWithdrawNum() {
        return withdrawNum;
    }

    public void setWithdrawNum(BigDecimal withdrawNum) {
        this.withdrawNum = withdrawNum;
    }

    public String getWithdrawRemark() {
        return withdrawRemark;
    }

    public void setWithdrawRemark(String withdrawRemark) {
        this.withdrawRemark = withdrawRemark;
    }

    public Integer getWithdrawState() {
        return withdrawState;
    }

    public void setWithdrawState(Integer withdrawState) {
        this.withdrawState = withdrawState;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getFailTime() {
        return failTime;
    }

    public void setFailTime(Date failTime) {
        this.failTime = failTime;
    }

    public String getPartnerTradeNo() {
        return partnerTradeNo;
    }

    public void setPartnerTradeNo(String partnerTradeNo) {
        this.partnerTradeNo = partnerTradeNo;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "DistDrawCashDetail{" +
                ", id=" + id +
                ", accountType=" + accountType +
                ", alipayNo=" + alipayNo +
                ", wechatNo=" + wechatNo +
                ", bankCardNo=" + bankCardNo +
                ", openBank=" + openBank +
                ", accountHolder=" + accountHolder +
                ", realName=" + realName +
                ", withdrawNum=" + withdrawNum +
                ", withdrawRemark=" + withdrawRemark +
                ", withdrawState=" + withdrawState +
                ", userId=" + userId +
                ", applyTime=" + applyTime +
                ", successTime=" + successTime +
                ", failTime=" + failTime +
                ", partnerTradeNo=" + partnerTradeNo +
                ", fee=" + fee +
                ", amount=" + amount +
                ", updateTime=" + updateTime +
                "}";
    }
}
