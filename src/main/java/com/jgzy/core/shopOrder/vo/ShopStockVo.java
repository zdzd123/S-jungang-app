package com.jgzy.core.shopOrder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel("库存-vo")
public class ShopStockVo {
    @ApiModelProperty(value = "库存id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "商品id", example = "1")
    private Integer shopGoodsId;
    @ApiModelProperty(value = "我的库存数量", example = "1")
    private Integer count;
    @ApiModelProperty(value = "商品名称", example = "1")
    private String shopName;
    @ApiModelProperty(value = "商品描述", example = "1")
    private String shopDescribe;
    @ApiModelProperty(value = "商品图片", example = "1.png")
    private String pic;
    @ApiModelProperty(value = "会员价", example = "1")
    private BigDecimal menberPrice;
    @NotNull
    @ApiModelProperty(value = "商品库存数量", example = "1")
    private Integer stock;
    @NotNull
    @ApiModelProperty(value = "运费标识 1-到付 2-等待计算", example = "1")
    private Integer carriageType;
    @NotNull
    @ApiModelProperty(value = "收货地址", example = "1")
    private Integer addressId;
    @NotNull
    @ApiModelProperty(value = "发货地址", example = "1")
    private Integer shipAddressId;

    public Integer getShopGoodsId() {
        return shopGoodsId;
    }

    public void setShopGoodsId(Integer shopGoodsId) {
        this.shopGoodsId = shopGoodsId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getShipAddressId() {
        return shipAddressId;
    }

    public void setShipAddressId(Integer shipAddressId) {
        this.shipAddressId = shipAddressId;
    }

    public Integer getCarriageType() {
        return carriageType;
    }

    public void setCarriageType(Integer carriageType) {
        this.carriageType = carriageType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDescribe() {
        return shopDescribe;
    }

    public void setShopDescribe(String shopDescribe) {
        this.shopDescribe = shopDescribe;
    }

    public BigDecimal getMenberPrice() {
        return menberPrice;
    }

    public void setMenberPrice(BigDecimal menberPrice) {
        this.menberPrice = menberPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
