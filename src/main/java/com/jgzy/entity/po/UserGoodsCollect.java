package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@ApiModel("商品收藏-po")
@TableName("user_goods_collect")
public class UserGoodsCollect implements Serializable {

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
     * 收藏人(用户id)
     */
    @ApiModelProperty(value = "收藏人(用户id)", example = "1")
    private Integer collectUserInfoId;
    /**
     * 收藏时间
     */
    @ApiModelProperty(value = "收藏时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date collectTime;
    /**
     * 1-未删除 2-删除
     */
    @ApiModelProperty(value = "1-未删除 2-删除", example = "1")
    private Integer userDel = 1;


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

    public Integer getCollectUserInfoId() {
        return collectUserInfoId;
    }

    public void setCollectUserInfoId(Integer collectUserInfoId) {
        this.collectUserInfoId = collectUserInfoId;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public Integer getUserDel() {
        return userDel;
    }

    public void setUserDel(Integer userDel) {
        this.userDel = userDel;
    }

    @Override
    public String toString() {
        return "UserGoodsCollect{" +
                ", id=" + id +
                ", goodsId=" + goodsId +
                ", collectUserInfoId=" + collectUserInfoId +
                ", collectTime=" + collectTime +
                ", userDel=" + userDel +
                "}";
    }
}
