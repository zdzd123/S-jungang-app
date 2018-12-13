package com.jgzy.core.shopOrder.vo;

import com.jgzy.entity.po.ShopGoodsAttribute;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("单个商品付款-vo")
public class CalcSingleAmountVo {
    @ApiModelProperty(value = "商品id", example = "1")
    private Integer shopInfoId;
//    @ApiModelProperty(value = "商品总金额", example = "200")
//    private BigDecimal singleTotalAmount = new BigDecimal("0");
//    @ApiModelProperty(value = "商品折扣金额", example = "200")
//    private BigDecimal singleRateAmount = new BigDecimal("0");
//    @ApiModelProperty(value = "商品实际支付金额", example = "200")
//    private BigDecimal singleRealAmount = new BigDecimal("0");
//    @ApiModelProperty(value = "使用权额id", example = "200")
//    private String advanceIds;
//    @ApiModelProperty(value = "权额金额", example = "200")
//    private BigDecimal advanceAmount = new BigDecimal("0");
//    @ApiModelProperty(value = " 余额金额", example = "200")
//    private BigDecimal singleRemainAmount;

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
    @ApiModelProperty(value = "商品单位", example = "5")
    private String goodsUnit;
    @ApiModelProperty(value = "箱量", example = "5")
    private Integer goodsUnitCount;
    @ApiModelProperty(value = "库存", example = "5")
    private Integer stock;
    @ApiModelProperty(value = "商品数量", example = "5")
    private Integer count;
    @ApiModelProperty(value = "商品简介", example = "5")
    private String abstracts;
    @ApiModelProperty(value = "0-默认 1-0库存可销售", example = "5")
    private Integer stockType;
//    private List<ShopGoodsAttribute> shopGoodsAttributeList;

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }


    public Integer getGoodsUnitCount() {
        return goodsUnitCount;
    }

    public void setGoodsUnitCount(Integer goodsUnitCount) {
        this.goodsUnitCount = goodsUnitCount;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
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

    public Integer getShopInfoId() {
        return shopInfoId;
    }

    public void setShopInfoId(Integer shopInfoId) {
        this.shopInfoId = shopInfoId;
    }

    @Override
    public String toString() {
        return "CalcSingleAmountVo{" +
                "shopInfoId=" + shopInfoId +
                ", shopName='" + shopName + '\'' +
                ", marketPrice=" + marketPrice +
                ", costPrice=" + costPrice +
                ", menberPrice=" + menberPrice +
                ", pic='" + pic + '\'' +
                '}';
    }
}
