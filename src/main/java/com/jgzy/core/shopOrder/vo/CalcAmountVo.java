package com.jgzy.core.shopOrder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("付款-vo")
public class CalcAmountVo {
    @ApiModelProperty(value = "是否是预付单 否=1，是=2", example = "1")
    private Integer IsPresell;
    @ApiModelProperty(value = "订单总金额", example = "200")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "商品总金额", example = "200")
    private BigDecimal shopGoodsAmount;
    @ApiModelProperty(value = "实际需支付", example = "200")
    private BigDecimal actualAmount = new BigDecimal("0");
    @ApiModelProperty(value = "判断是否购买了权额", example = "true")
    private boolean advanceFlag;
    @ApiModelProperty(value = "订单权额使用金额", example = "true")
    private BigDecimal advanceAmount;
    @ApiModelProperty(value = "使用权额id", example = "200")
    private String advanceIds;
    @ApiModelProperty(value = "订单余额支付金额", example = "200")
    private BigDecimal remainAmount;
    @ApiModelProperty(value = "订单优惠金额", example = "200")
    private BigDecimal rateAmount;
    @ApiModelProperty(value = "耗材费", example = "200")
    private BigDecimal materialAmount = new BigDecimal("0");
    @ApiModelProperty(value = "单个商品具体金额")
    private List<CalcSingleAmountVo> calcAmountVoList;

    public BigDecimal getShopGoodsAmount() {
        return shopGoodsAmount;
    }

    public void setShopGoodsAmount(BigDecimal shopGoodsAmount) {
        this.shopGoodsAmount = shopGoodsAmount;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(BigDecimal advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public BigDecimal getRateAmount() {
        return rateAmount;
    }

    public void setRateAmount(BigDecimal rateAmount) {
        this.rateAmount = rateAmount;
    }

    public String getAdvanceIds() {
        return advanceIds;
    }

    public void setAdvanceIds(String advanceIds) {
        this.advanceIds = advanceIds;
    }

    public BigDecimal getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(BigDecimal materialAmount) {
        this.materialAmount = materialAmount;
    }

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
                ", advanceFlag=" + advanceFlag +
                ", materialAmount=" + materialAmount +
                ", advanceIds='" + advanceIds + '\'' +
                ", calcAmountVoList=" + calcAmountVoList +
                '}';
    }
}
