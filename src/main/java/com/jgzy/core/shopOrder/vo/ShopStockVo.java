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
    @ApiModelProperty(value = "成本价", example = "1")
    private BigDecimal costPrice;
    @ApiModelProperty(value = "市场价", example = "1")
    private BigDecimal marketPrice;
    @ApiModelProperty(value = "备注", example = "1")
    private String remarks;
    @NotNull
    @ApiModelProperty(value = "商品库存数量", example = "1")
    private Integer stock;
    @NotNull
    @ApiModelProperty(value = "运费标识 1-到付 2-等待计算", example = "1")
    private Integer carriageType;
    @ApiModelProperty(value = "1-物流园自提 2-送货上门", example = "1")
    private Integer carriageTypeDetail;
    @NotNull
    @ApiModelProperty(value = "收货地址", example = "1")
    private Integer addressId;
    @NotNull
    @ApiModelProperty(value = "发货地址", example = "1")
    private Integer shipAddressId;
    @ApiModelProperty(value = "总瓶数", example = "1")
    private BigDecimal allCount;
    @ApiModelProperty(value = "总资产", example = "1")
    private BigDecimal allAmount;
    @ApiModelProperty(value = "支付类型 2-微信 4-余额", example = "1")
    private Integer payType;
    @ApiModelProperty(value = "是否放入库存 1=不存入 2=存入 3=自提", example = "1")
    private Integer isStock;
    @ApiModelProperty(value = "收货人", example = "1")
    private String receiveMan;
    @ApiModelProperty(value = "联系电话", example = "18311111111")
    private String contactPhone;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getCarriageTypeDetail() {
        return carriageTypeDetail;
    }

    public void setCarriageTypeDetail(Integer carriageTypeDetail) {
        this.carriageTypeDetail = carriageTypeDetail;
    }

    public String getReceiveMan() {
        return receiveMan;
    }

    public void setReceiveMan(String receiveMan) {
        this.receiveMan = receiveMan;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getIsStock() {
        return isStock;
    }

    public void setIsStock(Integer isStock) {
        this.isStock = isStock;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getAllCount() {
        return allCount;
    }

    public void setAllCount(BigDecimal allCount) {
        this.allCount = allCount;
    }

    public BigDecimal getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }

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
