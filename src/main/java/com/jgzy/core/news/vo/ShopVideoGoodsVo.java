package com.jgzy.core.news.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("视频商品-vo")
public class ShopVideoGoodsVo {
    @ApiModelProperty(value = "商品id", example = "1")
    private Integer shopGoodsId;
    @ApiModelProperty(value = "商品名", example = "巴朗波尔多干红葡萄酒")
    private String shopName;
    @ApiModelProperty(value = "市场价", example = "100")
    private BigDecimal marketPrice;
    @ApiModelProperty(value = "库存", example = "1")
    private Integer stock;
    @ApiModelProperty(value = "库存方式（0-默认  库存销售=1  库存自动下架=2）", example = "1")
    private Integer stockType;
    @ApiModelProperty(value = "图片(多张以\",\"分隔)", example = "1.jpg")
    private String pic;
    @ApiModelProperty(value = "成本价", example = "1")
    private BigDecimal costPrice;
    @ApiModelProperty(value = "会员价", example = "1")
    private BigDecimal menberPrice;
    @ApiModelProperty(value = "商品单位", example = "1")
    private String goodsUnit;
    @ApiModelProperty(value = "箱量", example = "1")
    private Integer goodsUnitCount;
    @ApiModelProperty(value = "商品简介", example = "1")
    private String abstracts;

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public Integer getShopGoodsId() {
        return shopGoodsId;
    }

    public void setShopGoodsId(Integer shopGoodsId) {
        this.shopGoodsId = shopGoodsId;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public Integer getGoodsUnitCount() {
        return goodsUnitCount;
    }

    public void setGoodsUnitCount(Integer goodsUnitCount) {
        this.goodsUnitCount = goodsUnitCount;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }
}
