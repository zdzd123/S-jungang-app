package com.jgzy.core.personalCenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("我的团队-vo")
public class MyTeamVo {
    @ApiModelProperty(value = "昵称", example = "昵称")
    private String nickname;
    @ApiModelProperty(value = "头像", example = "/1.png")
    private String headPortrait;
    @ApiModelProperty(value = "余额1(可提现)", example = "11")
    private BigDecimal balance1;
    @ApiModelProperty(value = "冻结金额", example = "11")
    private BigDecimal balance4;
    @ApiModelProperty(value = "会员等级id", example = "1")
    private Integer userLevelId;
    @ApiModelProperty(value = "消费者累积收益", example = "11")
    private BigDecimal increaseMoney;
    @ApiModelProperty(value = "合伙人累积收益", example = "11")
    private BigDecimal originatorIncreaseMoney;
//    @ApiModelProperty(value = "月累积收益", example = "11")
//    private BigDecimal monthIncreaseMoney;
    @ApiModelProperty(value = "总共累积收益", example = "11")
    private BigDecimal totalIncreaseMoney;
    @ApiModelProperty(value = "总共累积消费", example = "11")
    private BigDecimal totalDecreaseMoney;
    @ApiModelProperty(value = "月累积消费", example = "11")
    private BigDecimal monthDecreaseMoney;
    @ApiModelProperty(value = "团员信息")
    private List<MyTeamDetailVo> myTeamDetailVoList;
    @ApiModelProperty(value = "合伙人团员信息")
    private List<MyTeamDetailVo> myOriginatorTeamDetailVoList;

    public BigDecimal getTotalIncreaseMoney() {
        return totalIncreaseMoney;
    }

    public void setTotalIncreaseMoney(BigDecimal totalIncreaseMoney) {
        this.totalIncreaseMoney = totalIncreaseMoney;
    }

    public BigDecimal getTotalDecreaseMoney() {
        return totalDecreaseMoney;
    }

    public void setTotalDecreaseMoney(BigDecimal totalDecreaseMoney) {
        this.totalDecreaseMoney = totalDecreaseMoney;
    }

    public BigDecimal getMonthDecreaseMoney() {
        return monthDecreaseMoney;
    }

    public void setMonthDecreaseMoney(BigDecimal monthDecreaseMoney) {
        this.monthDecreaseMoney = monthDecreaseMoney;
    }

    public List<MyTeamDetailVo> getMyOriginatorTeamDetailVoList() {
        return myOriginatorTeamDetailVoList;
    }

    public void setMyOriginatorTeamDetailVoList(List<MyTeamDetailVo> myOriginatorTeamDetailVoList) {
        this.myOriginatorTeamDetailVoList = myOriginatorTeamDetailVoList;
    }

    public List<MyTeamDetailVo> getMyTeamDetailVoList() {
        return myTeamDetailVoList;
    }

    public void setMyTeamDetailVoList(List<MyTeamDetailVo> myTeamDetailVoList) {
        this.myTeamDetailVoList = myTeamDetailVoList;
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

    public BigDecimal getBalance1() {
        return balance1;
    }

    public void setBalance1(BigDecimal balance1) {
        this.balance1 = balance1;
    }

    public BigDecimal getBalance4() {
        return balance4;
    }

    public void setBalance4(BigDecimal balance4) {
        this.balance4 = balance4;
    }

    public Integer getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(Integer userLevelId) {
        this.userLevelId = userLevelId;
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
