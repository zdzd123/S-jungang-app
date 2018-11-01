package com.jgzy.core.shopOrder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("付款-vo")
public class CalcAmountVo {
    @ApiModelProperty(value = "是否是预付单 否=1，是=2", example = "1")
    private Integer IsPresell;
    @ApiModelProperty(value = "实际需支付", example = "200")
    private BigDecimal actualAmount = new BigDecimal("0");
    //@ApiModelProperty(value = "品牌费", example = "200")
    //private BigDecimal originatorAmount = new BigDecimal("0");
    @ApiModelProperty(value = "判断是否购买了权额", example = "true")
    private boolean advanceFlag;
    @ApiModelProperty(value = "单个商品具体金额")
    private List<CalcSingleAmountVo> calcAmountVoList;

    public boolean isAdvanceFlag() {
        return advanceFlag;
    }

    public void setAdvanceFlag(boolean advanceFlag) {
        this.advanceFlag = advanceFlag;
    }

    public Integer getIsPresell() {
        return IsPresell;
    }

    public void setIsPresell(Integer isPresell) {
        IsPresell = isPresell;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

//    public BigDecimal getOriginatorAmount() {
//        return originatorAmount;
//    }
//
//    public void setOriginatorAmount(BigDecimal originatorAmount) {
//        this.originatorAmount = originatorAmount;
//    }

    public List<CalcSingleAmountVo> getCalcAmountVoList() {
        return calcAmountVoList;
    }

    public void setCalcAmountVoList(List<CalcSingleAmountVo> calcAmountVoList) {
        this.calcAmountVoList = calcAmountVoList;
    }

    @Override
    public String toString() {
        return "CalcAmountVo{" +
                "IsPresell=" + IsPresell +
                ", actualAmount=" + actualAmount +
                ", calcAmountVoList=" + calcAmountVoList +
                '}';
    }
}
