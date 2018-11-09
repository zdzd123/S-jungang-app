package com.jgzy.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zou
 * @since 2018-11-07
 */
@ApiModel("商品属性-po")
public class ShopGoodsAttribute implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "商品信息id", example = "1")
    private Integer shopGoodsId;
    @ApiModelProperty(value = "平台商品参数id", example = "1")
    private Integer platformGoodsAttributeId;
    @ApiModelProperty(value = "属性名称", example = "1")
    private String attributeName;
    @ApiModelProperty(value = "属性值名称", example = "1")
    private String attributeValueName;
    @ApiModelProperty(value = "属性值排序", example = "1")
    private Integer attributeValuePort;
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopGoodsId() {
        return shopGoodsId;
    }

    public void setShopGoodsId(Integer shopGoodsId) {
        this.shopGoodsId = shopGoodsId;
    }

    public Integer getPlatformGoodsAttributeId() {
        return platformGoodsAttributeId;
    }

    public void setPlatformGoodsAttributeId(Integer platformGoodsAttributeId) {
        this.platformGoodsAttributeId = platformGoodsAttributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValueName() {
        return attributeValueName;
    }

    public void setAttributeValueName(String attributeValueName) {
        this.attributeValueName = attributeValueName;
    }

    public Integer getAttributeValuePort() {
        return attributeValuePort;
    }

    public void setAttributeValuePort(Integer attributeValuePort) {
        this.attributeValuePort = attributeValuePort;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "ShopGoodsAttribute{" +
                ", id=" + id +
                ", shopGoodsId=" + shopGoodsId +
                ", platformGoodsAttributeId=" + platformGoodsAttributeId +
                ", attributeName=" + attributeName +
                ", attributeValueName=" + attributeValueName +
                ", attributeValuePort=" + attributeValuePort +
                ", addTime=" + addTime +
                "}";
    }
}
