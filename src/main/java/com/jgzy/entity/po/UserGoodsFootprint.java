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
 * @since 2018-10-17
 */
@ApiModel("用户足迹_po")
@TableName("user_goods_footprint")
public class UserGoodsFootprint implements Serializable {

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
     * 足迹人(用户id)
     */
    @ApiModelProperty(value = "足迹人(用户id)", example = "1")
    private Integer footprintUserInfoId;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "删除 1-使用 0-删除", example = "1")
    private Integer userDel;


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

    public Integer getFootprintUserInfoId() {
        return footprintUserInfoId;
    }

    public void setFootprintUserInfoId(Integer footprintUserInfoId) {
        this.footprintUserInfoId = footprintUserInfoId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getUserDel() {
        return userDel;
    }

    public void setUserDel(Integer userDel) {
        this.userDel = userDel;
    }

    @Override
    public String toString() {
        return "UserGoodsFootprint{" +
                ", id=" + id +
                ", goodsId=" + goodsId +
                ", footprintUserInfoId=" + footprintUserInfoId +
                ", addTime=" + addTime +
                ", userDel=" + userDel +
                "}";
    }
}
