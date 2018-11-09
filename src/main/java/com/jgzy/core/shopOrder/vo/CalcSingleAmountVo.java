package com.jgzy.core.shopOrder.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("单个商品付款-vo")
public class CalcSingleAmountVo {
    @ApiModelProperty(value = "商品id", example = "1")
    private Integer shopInfoId;
    @ApiModelProperty(value = "商品总金额", example = "200")
    private BigDecimal singleTotalAmount = new BigDecimal("0");
    @ApiModelProperty(value = "商品折扣金额", example = "200")
    private BigDecimal singleRateAmount = new BigDecimal("0");
    @ApiModelProperty(value = "商品实际支付金额", example = "200")
    private BigDecimal singleRealAmount = new BigDecimal("0");
    @ApiModelProperty(value = "使用权额id", example = "200")
    private String advanceIds;
    @ApiModelProperty(value = "权额金额", example = "200")
    private BigDecimal advanceAmount = new BigDecimal("0");
    @ApiModelProperty(value = " 余额金额", example = "200")
    private BigDecimal singleRemainAmount;

    @ApiModelProperty(value = "商品名", example = "巴朗波尔多干红葡萄酒")
    private String shopName;
    @ApiModelProperty(value = "市场价", example = "100")
    private BigDecimal marketPrice;
    @ApiModelProperty(value = "成本价", example = "1")
    private BigDecimal costPrice;
    @ApiModelProperty(value = "会员价", example = "1")
    private BigDecimal menberPrice;
    //@ApiModelProperty(value = "商品描述", example = "商品描述")
    //private String shopDescribe;
    @ApiModelProperty(value = "图片(多张以\",\"分隔)", example = "1.jpg")
    private String pic;

    public BigDecimal getSingleRemainAmount() {
        return singleRemainAmount;
    }

    public void setSingleRemainAmount(BigDecimal singleRemainAmount) {
        this.singleRemainAmount = singleRemainAmount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getMenberPrice() {
        return menberPrice;
    }

    public void setMenberPrice(BigDecimal menberPrice) {
        this.menberPrice = menberPrice;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public BigDecimal getSingleRealAmount() {
        return singleRealAmount;
    }

    public void setSingleRealAmount(BigDecimal singleRealAmount) {
        this.singleRealAmount = singleRealAmount;
    }

    public String getAdvanceIds() {
        return advanceIds;
    }

    public void setAdvanceIds(String advanceIds) {
        this.advanceIds = advanceIds;
    }

    public Integer getShopInfoId() {
        return shopInfoId;
    }

    public void setShopInfoId(Integer shopInfoId) {
        this.shopInfoId = shopInfoId;
    }

    public BigDecimal getSingleRateAmount() {
        return singleRateAmount;
    }

    public void setSingleRateAmount(BigDecimal singleRateAmount) {
        this.singleRateAmount = singleRateAmount;
    }

    public BigDecimal getSingleTotalAmount() {
        return singleTotalAmount;
    }

    public void setSingleTotalAmount(BigDecimal singleTotalAmount) {
        this.singleTotalAmount = singleTotalAmount;
    }


    public BigDecimal getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(BigDecimal advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    @Override
    public String toString() {
        return "CalcSingleAmountVo{" +
                "shopInfoId=" + shopInfoId +
                ", singleTotalAmount=" + singleTotalAmount +
                ", singleRateAmount=" + singleRateAmount +
                ", singleRealAmount=" + singleRealAmount +
                ", advanceIds='" + advanceIds + '\'' +
                ", advanceAmount=" + advanceAmount +
                ", shopName='" + shopName + '\'' +
                ", marketPrice=" + marketPrice +
                ", costPrice=" + costPrice +
                ", menberPrice=" + menberPrice +
                ", pic='" + pic + '\'' +
                '}';
    }
}
