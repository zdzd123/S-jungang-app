package com.jgzy.core.personalCenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("足迹-vo ")
public class UserGoodsFootprintVo {
    @ApiModelProperty(value = "id", example = "1")
    private String id;
    @ApiModelProperty(value = "商品ID", example = "1")
    private String goodsId;
    @ApiModelProperty(value = "商品名称", example = "商品名称")
    private String shopName;
    @ApiModelProperty(value = "商品普通消费者价格", example = "商品普通消费者价格")
    private String costPrice;
    @ApiModelProperty(value = "商品图片", example = "商品图片")
    private String pic;
    @ApiModelProperty(value = "商品状态", example = "商品状态")
    private String status;
    @ApiModelProperty(value = "商品数量", example = "商品数量")
    private String goodsUnit;
    @ApiModelProperty(value = "商品描述", example = "商品描述")
    private String abstracts;
    @ApiModelProperty(value = "商品库存", example = "商品库存")
    private String stock;

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
