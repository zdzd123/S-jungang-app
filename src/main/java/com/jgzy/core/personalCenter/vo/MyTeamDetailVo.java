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
    @ApiModelProperty(value = "消费者月累积收益", example = "11")
    private BigDecimal monthIncreaseMoney;
    @ApiModelProperty(value = "合伙人累积收益", example = "11")
    private BigDecimal originatorIncreaseMoney;
    @ApiModelProperty(value = "合伙人月累积收益", example = "11")
    private BigDecimal monthOriginatorIncreaseMoney;
    @ApiModelProperty(value = "合伙人状态", example = "11")
    private Integer status;

    public BigDecimal getMonthIncreaseMoney() {
        return monthIncreaseMoney;
    }

    public void setMonthIncreaseMoney(BigDecimal monthIncreaseMoney) {
        this.monthIncreaseMoney = monthIncreaseMoney;
    }

    public BigDecimal getMonthOriginatorIncreaseMoney() {
        return monthOriginatorIncreaseMoney;
    }

    public void setMonthOriginatorIncreaseMoney(BigDecimal monthOriginatorIncreaseMoney) {
        this.monthOriginatorIncreaseMoney = monthOriginatorIncreaseMoney;
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
}
