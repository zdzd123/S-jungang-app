package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 用户流水
 * </p>
 *
 * @author zou
 * @since 2018-10-22
 */
@ApiModel("用户流水-po")
@TableName("user_fund")
public class UserFund implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "交易时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tradeTime;
    @ApiModelProperty(value = "交易方式(收入=1|支出=2)", example = "1")
    private Integer tradeType;
    @ApiModelProperty(value = "增加", example = "1")
    private BigDecimal increaseMoney;
    @ApiModelProperty(value = "减少", example = "1")
    private BigDecimal decreaseMoney;
    @ApiModelProperty(value = "描述", example = "描述")
    private String tradeDescribe;
    @ApiModelProperty(value = "交易人(用户id)", example = "1")
    private Integer tradeUserId;
    @ApiModelProperty(value = "(1-消费，2-转入，3-转出，4-收益，5-退款，\n" +
            "6-已提现，61-提现退回，62-提现冻结，\n" +
            "7-合伙人佣金，71-合伙人佣金冻结，72-合伙人佣金冻结到期转出，73-合伙人佣金退回\n" +
            "8-消费者佣金，81-消费者佣金冻结，82-消费者佣金冻结到期转出，83-消费者佣金退回\n" +
            "9-自己消费佣金，91-自己消费佣金冻结，92-自己消费佣金冻结到期转出，93-自己消费佣金退回)", example = "1")
    private Integer bussinessType;
    @ApiModelProperty(value = "(1-积分，2-余额，3-冻结，4-微信，5-支付宝，6-提现，7-荣誉值)", example = "1")
    private Integer accountType;
    @ApiModelProperty(value = "支付方式", example = "1")
    private Integer payType;
    @ApiModelProperty(value = "对应账户余额", example = "1")
    private BigDecimal balance;
    @ApiModelProperty(value = "订单编号", example = "1")
    public String orderNo;// 订单编号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getIncreaseMoney() {
        return increaseMoney;
    }

    public void setIncreaseMoney(BigDecimal increaseMoney) {
        this.increaseMoney = increaseMoney;
    }

    public BigDecimal getDecreaseMoney() {
        return decreaseMoney;
    }

    public void setDecreaseMoney(BigDecimal decreaseMoney) {
        this.decreaseMoney = decreaseMoney;
    }

    public String getTradeDescribe() {
        return tradeDescribe;
    }

    public void setTradeDescribe(String tradeDescribe) {
        this.tradeDescribe = tradeDescribe;
    }

    public Integer getTradeUserId() {
        return tradeUserId;
    }

    public void setTradeUserId(Integer tradeUserId) {
        this.tradeUserId = tradeUserId;
    }

    public Integer getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(Integer bussinessType) {
        this.bussinessType = bussinessType;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "UserFund{" +
                ", id=" + id +
                ", tradeTime=" + tradeTime +
                ", tradeType=" + tradeType +
                ", increaseMoney=" + increaseMoney +
                ", decreaseMoney=" + decreaseMoney +
                ", tradeDescribe=" + tradeDescribe +
                ", tradeUserId=" + tradeUserId +
                ", bussinessType=" + bussinessType +
                ", accountType=" + accountType +
                ", payType=" + payType +
                ", balance=" + balance +
                "}";
    }
}
