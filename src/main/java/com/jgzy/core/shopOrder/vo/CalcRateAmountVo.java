package com.jgzy.core.shopOrder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("商品折扣-vo")
public class CalcRateAmountVo {
    @ApiModelProperty(value = "商品总金额", example = "200")
    private BigDecimal singleTotalAmount = new BigDecimal("0");
    @ApiModelProperty(value = "剩余金额享受折扣", example = "100")
    private BigDecimal remainAmount = new BigDecimal("0");
    @ApiModelProperty(value = "折扣率", example = "0.5")
    private BigDecimal discountRate = new BigDecimal("0");

    public BigDecimal getSingleTotalAmount() {
        return singleTotalAmount;
    }

    public void setSingleTotalAmount(BigDecimal singleTotalAmount) {
        this.singleTotalAmount = singleTotalAmount;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public String toString() {
        return "CalcRateAmountVo{" +
                "singleTotalAmount=" + singleTotalAmount +
                ", remainAmount=" + remainAmount +
                ", discountRate=" + discountRate +
                '}';
    }

}
