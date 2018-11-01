package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-10-19
 */
@ApiModel("购物车-po")
@TableName("user_goods_cart")
public class UserGoodsCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 商品信息id
     */
    @ApiModelProperty(value = "商品信息id", example = "1")
    private Integer goodsId;
    /**
     * 商品规格id
     */
    @ApiModelProperty(value = "商品规格id", example = "1")
    private Integer shopGoodsSpecId;
    /**
     * 购物数量
     */
    @ApiModelProperty(value = "购物数量", example = "1")
    private Integer goodsCartCounts;
    /**
     * 购物人id
     */
    @ApiModelProperty(value = "购物人id", example = "1")
    private Integer cartUserInfoId;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "liveId", example = "1")
    private Integer liveId;
    @ApiModelProperty(value = "type", example = "1")
    private Integer type;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getShopGoodsSpecId() {
        return shopGoodsSpecId;
    }

    public void setShopGoodsSpecId(Integer shopGoodsSpecId) {
        this.shopGoodsSpecId = shopGoodsSpecId;
    }

    public Integer getGoodsCartCounts() {
        return goodsCartCounts;
    }

    public void setGoodsCartCounts(Integer goodsCartCounts) {
        this.goodsCartCounts = goodsCartCounts;
    }

    public Integer getCartUserInfoId() {
        return cartUserInfoId;
    }

    public void setCartUserInfoId(Integer cartUserInfoId) {
        this.cartUserInfoId = cartUserInfoId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserGoodsCart{" +
                ", id=" + id +
                ", goodsId=" + goodsId +
                ", shopGoodsSpecId=" + shopGoodsSpecId +
                ", goodsCartCounts=" + goodsCartCounts +
                ", cartUserInfoId=" + cartUserInfoId +
                ", addTime=" + addTime +
                ", liveId=" + liveId +
                ", type=" + type +
                "}";
    }
}
