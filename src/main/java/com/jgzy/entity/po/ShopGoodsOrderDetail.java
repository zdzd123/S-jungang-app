package com.jgzy.entity.po;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zou
 * @since 2018-10-25
 */
@ApiModel("订单详情-po")
@TableName("shop_goods_order_detail")
public class ShopGoodsOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "订单id", example = "1")
    private Integer orderId;
    @ApiModelProperty(value = "商品id", example = "1")
    private Integer shopGoodsId;
    @ApiModelProperty(value = "商品名称", example = "1")
    private String sortName;
    @ApiModelProperty(value = "商品图片(多张以\",\"分隔)", example = "1")
    private String pic;
    @ApiModelProperty(value = "市场价(元)", example = "1")
    private BigDecimal marketPrice;
    @ApiModelProperty(value = "成本价(元)", example = "1")
    private BigDecimal costPrice;
    @ApiModelProperty(value = "会员价(元)", example = "1")
    private BigDecimal menberPrice;
    @ApiModelProperty(value = "购买数量", example = "1")
    private Integer buyCount;
    @ApiModelProperty(value = "箱量", example = "1")
    private Integer goodsUnitCount;
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "platformGoodsSpecNames", example = "1")
    private String platformGoodsSpecNames;
    @ApiModelProperty(value = "platformGoodsSpecValueNames", example = "1")
    private String platformGoodsSpecValueNames;
    @ApiModelProperty(value = "liveId", example = "1")
    private Integer liveId;
    @TableField("hide_hasPaid")
    @ApiModelProperty(value = "hideHaspaid", example = "1")
    private BigDecimal hideHaspaid;
    @TableField("hide_discountedPrice")
    @ApiModelProperty(value = "hideHaspaid", example = "1")
    private BigDecimal hideDiscountedprice;
    @ApiModelProperty(value = "hideFreight", example = "1")
    private BigDecimal hideFreight;
    @TableField("hide_pointMoney")
    @ApiModelProperty(value = "hidePointmoney", example = "1")
    private BigDecimal hidePointmoney;
    @ApiModelProperty(value = "possiblePoint", example = "1")
    private Integer possiblePoint;
    @ApiModelProperty(value = "是否已经评价", example = "1")
    private Integer isEvaluate;
    @ApiModelProperty(value = "extract", example = "1")
    private Integer extract;
    @ApiModelProperty(value = "extractTime", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date extractTime;

    public Integer getGoodsUnitCount() {
        return goodsUnitCount;
    }

    public void setGoodsUnitCount(Integer goodsUnitCount) {
        this.goodsUnitCount = goodsUnitCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getShopGoodsId() {
        return shopGoodsId;
    }

    public void setShopGoodsId(Integer shopGoodsId) {
        this.shopGoodsId = shopGoodsId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getPlatformGoodsSpecNames() {
        return platformGoodsSpecNames;
    }

    public void setPlatformGoodsSpecNames(String platformGoodsSpecNames) {
        this.platformGoodsSpecNames = platformGoodsSpecNames;
    }

    public String getPlatformGoodsSpecValueNames() {
        return platformGoodsSpecValueNames;
    }

    public void setPlatformGoodsSpecValueNames(String platformGoodsSpecValueNames) {
        this.platformGoodsSpecValueNames = platformGoodsSpecValueNames;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public BigDecimal getHideHaspaid() {
        return hideHaspaid;
    }

    public void setHideHaspaid(BigDecimal hideHaspaid) {
        this.hideHaspaid = hideHaspaid;
    }

    public BigDecimal getHideDiscountedprice() {
        return hideDiscountedprice;
    }

    public void setHideDiscountedprice(BigDecimal hideDiscountedprice) {
        this.hideDiscountedprice = hideDiscountedprice;
    }

    public BigDecimal getHideFreight() {
        return hideFreight;
    }

    public void setHideFreight(BigDecimal hideFreight) {
        this.hideFreight = hideFreight;
    }

    public BigDecimal getHidePointmoney() {
        return hidePointmoney;
    }

    public void setHidePointmoney(BigDecimal hidePointmoney) {
        this.hidePointmoney = hidePointmoney;
    }

    public Integer getPossiblePoint() {
        return possiblePoint;
    }

    public void setPossiblePoint(Integer possiblePoint) {
        this.possiblePoint = possiblePoint;
    }

    public Integer getIsEvaluate() {
        return isEvaluate;
    }

    public void setIsEvaluate(Integer isEvaluate) {
        this.isEvaluate = isEvaluate;
    }

    public Integer getExtract() {
        return extract;
    }

    public void setExtract(Integer extract) {
        this.extract = extract;
    }

    public Date getExtractTime() {
        return extractTime;
    }

    public void setExtractTime(Date extractTime) {
        this.extractTime = extractTime;
    }

    @Override
    public String toString() {
        return "ShopGoodsOrderDetail{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", shopGoodsId=" + shopGoodsId +
                ", sortName='" + sortName + '\'' +
                ", pic='" + pic + '\'' +
                ", marketPrice=" + marketPrice +
                ", costPrice=" + costPrice +
                ", menberPrice=" + menberPrice +
                ", buyCount=" + buyCount +
                ", addTime=" + addTime +
                ", platformGoodsSpecNames='" + platformGoodsSpecNames + '\'' +
                ", platformGoodsSpecValueNames='" + platformGoodsSpecValueNames + '\'' +
                ", liveId=" + liveId +
                ", hideHaspaid=" + hideHaspaid +
                ", hideDiscountedprice=" + hideDiscountedprice +
                ", hideFreight=" + hideFreight +
                ", hidePointmoney=" + hidePointmoney +
                ", possiblePoint=" + possiblePoint +
                ", isEvaluate=" + isEvaluate +
                ", extract=" + extract +
                ", extractTime=" + extractTime +
                '}';
    }
}
