package com.jgzy.core.personalCenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("我的团队-团员信息-vo")
public class MyTeamDetailVo {
    @ApiModelProperty(value = "用户id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "昵称", example = "昵称")
    private String nickname;
    @ApiModelProperty(value = "头像", example = "/1.png")
    private String headPortrait;
    @ApiModelProperty(value = "消费者累积收益", example = "11")
    private BigDecimal increaseMoney;
    //    @ApiModelProperty(value = "消费者月累积收益", example = "11")
//    private BigDecimal monthIncreaseMoney;
    @ApiModelProperty(value = "消费者冻结累积收益", example = "11")
    private BigDecimal freezeIncreaseMoney;
    @ApiModelProperty(value = "消费者解冻累积收益", example = "11")
    private BigDecimal unfreezeIncreaseMoney;
    @ApiModelProperty(value = "合伙人累积收益", example = "11")
    private BigDecimal originatorIncreaseMoney;
    @ApiModelProperty(value = "合伙人冻结累积收益", example = "11")
    private BigDecimal freezeOriginatorIncreaseMoney;
    @ApiModelProperty(value = "合伙人解冻累积收益", example = "11")
    private BigDecimal unfreezeOriginatorIncreaseMoney;
    //    @ApiModelProperty(value = "合伙人月累积收益", example = "11")
//    private BigDecimal monthOriginatorIncreaseMoney;
    @ApiModelProperty(value = "月累积收益", example = "11")
    private BigDecimal monthDecreaseMoney;
    @ApiModelProperty(value = "累积消费", example = "11")
    private BigDecimal totalDecreaseMoney;
    @ApiModelProperty(value = "逾期返还金额", example = "11")
    private BigDecimal overDuePayments;
    @ApiModelProperty(value = "合伙人状态", example = "11")
    private Integer status;

    public BigDecimal getFreezeIncreaseMoney() {
        return freezeIncreaseMoney;
    }

    public void setFreezeIncreaseMoney(BigDecimal freezeIncreaseMoney) {
        this.freezeIncreaseMoney = freezeIncreaseMoney;
    }

    public BigDecimal getUnfreezeIncreaseMoney() {
        return unfreezeIncreaseMoney;
    }

    public void setUnfreezeIncreaseMoney(BigDecimal unfreezeIncreaseMoney) {
        this.unfreezeIncreaseMoney = unfreezeIncreaseMoney;
    }

    public BigDecimal getFreezeOriginatorIncreaseMoney() {
        return freezeOriginatorIncreaseMoney;
    }

    public void setFreezeOriginatorIncreaseMoney(BigDecimal freezeOriginatorIncreaseMoney) {
        this.freezeOriginatorIncreaseMoney = freezeOriginatorIncreaseMoney;
    }

    public BigDecimal getUnfreezeOriginatorIncreaseMoney() {
        return unfreezeOriginatorIncreaseMoney;
    }

    public void setUnfreezeOriginatorIncreaseMoney(BigDecimal unfreezeOriginatorIncreaseMoney) {
        this.unfreezeOriginatorIncreaseMoney = unfreezeOriginatorIncreaseMoney;
    }

    public BigDecimal getMonthDecreaseMoney() {
        return monthDecreaseMoney;
    }

    public void setMonthDecreaseMoney(BigDecimal monthDecreaseMoney) {
        this.monthDecreaseMoney = monthDecreaseMoney;
    }

    public BigDecimal getOverDuePayments() {
        return overDuePayments;
    }

    public void setOverDuePayments(BigDecimal overDuePayments) {
        this.overDuePayments = overDuePayments;
    }

    public BigDecimal getIncreaseMoney() {
        return increaseMoney;
    }

    public void setIncreaseMoney(BigDecimal increaseMoney) {
        this.increaseMoney = increaseMoney;
    }

    public BigDecimal getOriginatorIncreaseMoney() {
        return originatorIncreaseMoney;
    }

    public void setOriginatorIncreaseMoney(BigDecimal originatorIncreaseMoney) {
        this.originatorIncreaseMoney = originatorIncreaseMoney;
    }

    public BigDecimal getTotalDecreaseMoney() {
        return totalDecreaseMoney;
    }

    public void setTotalDecreaseMoney(BigDecimal totalDecreaseMoney) {
        this.totalDecreaseMoney = totalDecreaseMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }
}
