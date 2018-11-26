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
 * @since 2018-11-22
 */
@ApiModel("资讯视频-vo")
public class ShopVideoGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "店铺视频id", example = "1")
    private Integer shopVideoInfoId;
    @ApiModelProperty(value = "商品id", example = "1")
    private Integer shopGoodsId;
    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopVideoInfoId() {
        return shopVideoInfoId;
    }

    public void setShopVideoInfoId(Integer shopVideoInfoId) {
        this.shopVideoInfoId = shopVideoInfoId;
    }

    public Integer getShopGoodsId() {
        return shopGoodsId;
    }

    public void setShopGoodsId(Integer shopGoodsId) {
        this.shopGoodsId = shopGoodsId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "ShopVideoGoods{" +
        ", id=" + id +
        ", shopVideoInfoId=" + shopVideoInfoId +
        ", shopGoodsId=" + shopGoodsId +
        ", sort=" + sort +
        ", addTime=" + addTime +
        "}";
    }
}
