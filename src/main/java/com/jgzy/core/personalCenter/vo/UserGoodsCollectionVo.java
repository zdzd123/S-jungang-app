package com.jgzy.core.personalCenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户足迹-vo")
public class UserGoodsCollectionVo {
    @ApiModelProperty(value = "id", example = "1")
    private String id;
    @ApiModelProperty(value = "商品ID", example = "1")
    private String goodsId;
    @ApiModelProperty(value = "平台分类ID", example = "1")
    private String platformGoodsCategoryId;
    @ApiModelProperty(value = "商品名称", example = "商品名称")
    private String shopName;
    @ApiModelProperty(value = "商品普通消费者价格", example = "商品普通消费者价格")
    private String costPrice;
    @ApiModelProperty(value = "商品原价", example = "商品原价")
    private String marketPrice;
    @ApiModelProperty(value = "会员价", example = "会员价")
    private String menberPrice;
    @ApiModelProperty(value = "商品图片", example = "商品图片")
    private String pic;
    @ApiModelProperty(value = "商品状态", example = "商品状态")
    private String status;
    @ApiModelProperty(value = "商品数量", example = "商品数量")
    private String goodsUnit;
    @ApiModelProperty(value = "收藏数量", example = "收藏数量")
    private String count;
    @ApiModelProperty(value = "商品描述", example = "商品描述")
    private String abstracts;
    @ApiModelProperty(value = "商品库存", example = "商品库存")
    private String stock;
    @ApiModelProperty(value = "箱量", example = "箱量")
    private String goodsUnitCount;

    public String getGoodsUnitCount() {
        return goodsUnitCount;
    }

    public void setGoodsUnitCount(String goodsUnitCount) {
        this.goodsUnitCount = goodsUnitCount;
    }

    public String getPlatformGoodsCategoryId() {
        return platformGoodsCategoryId;
    }

    public void setPlatformGoodsCategoryId(String platformGoodsCategoryId) {
        this.platformGoodsCategoryId = platformGoodsCategoryId;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getMenberPrice() {
        return menberPrice;
    }

    public void setMenberPrice(String menberPrice) {
        this.menberPrice = menberPrice;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
