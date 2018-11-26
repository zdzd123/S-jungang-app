package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 提现账户
 * </p>
 *
 * @author zou
 * @since 2018-11-21
 */
@TableName("dist_draw_cash_account")
@ApiModel("提现-po")
public class DistDrawCashAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户名称", example = "用户名称")
    private String userName;
    @ApiModelProperty(value = "手机号", example = "手机号")
    private String phoneNumber;
    @ApiModelProperty(value = "用户名称", example = "用户名称")
    private Integer accountType;
    @ApiModelProperty(value = "用户名称", example = "用户名称")
    private String alipayNo;
    @ApiModelProperty(value = "用户名称", example = "用户名称")
    private String wechatNo;
    @ApiModelProperty(value = "用户名称", example = "用户名称")
    private String bankCardNo;
    @ApiModelProperty(value = "用户名称", example = "用户名称")
    private String openBank;
    @ApiModelProperty(value = "用户名称", example = "用户名称")
    private String accountHolder;
    @ApiModelProperty(value = "用户名称", example = "用户名称")
    private String realName;
    @ApiModelProperty(value = "用户名称", example = "用户名称")
    private Integer belongUserId;
    @ApiModelProperty(value = "用户名称", example = "用户名称")
    private Date addTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Integer getBelongUserId() {
        return belongUserId;
    }

    public void setBelongUserId(Integer belongUserId) {
        this.belongUserId = belongUserId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "DistDrawCashAccount{" +
                ", id=" + id +
                ", userName=" + userName +
                ", phoneNumber=" + phoneNumber +
                ", accountType=" + accountType +
                ", alipayNo=" + alipayNo +
                ", wechatNo=" + wechatNo +
                ", bankCardNo=" + bankCardNo +
                ", openBank=" + openBank +
                ", accountHolder=" + accountHolder +
                ", realName=" + realName +
                ", belongUserId=" + belongUserId +
                ", addTime=" + addTime +
                "}";
    }
}
