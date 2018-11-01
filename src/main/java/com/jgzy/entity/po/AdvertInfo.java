package com.jgzy.entity.po;

import java.util.Date;

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
 * @since 2018-10-18
 */
@ApiModel("广告信息-po")
@TableName("advert_info")
public class AdvertInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "商品大类id", example = "1")
    private Integer platformGoodsCategoryId;
    /**
     * 广告位置(首页=1)
     */
    @ApiModelProperty(value = "广告位置(首页=6 非活动轮播图=7 活动分类展示图=8 无=1)", example = "1")
    private Integer advertSite;
    /**
     * 图片(多张","分隔)
     */
    @ApiModelProperty(value = "图片(多张\",\"分隔)", example = "1,2")
    private String pic;
    /**
     * 图片链接地址
     */
    @ApiModelProperty(value = "图片链接地址", example = "1.png,2.png")
    private String picLink;
    /**
     * 图片值
     */
    @ApiModelProperty(value = "图片值", example = "1")
    private Integer shopGoodsId;
    /**
     * 图片值类型(默认=0，商品=1 资讯=2)
     */
    @ApiModelProperty(value = "图片值类型(默认=0，商品=1 资讯=2)", example = "0")
    private Integer picValueType;
    /**
     * 有效期开始日期
     */
    @ApiModelProperty(value = "有效期开始日期", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    /**
     * 有效期结束日期
     */
    @ApiModelProperty(value = "有效期结束日期", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    /**
     * 状态(未开启=1|已开启=2)
     */
    @ApiModelProperty(value = "状态(未开启=1|已开启=2)", example = "1")
    private Integer state;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "validStartTime", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date validStartTime;
    @ApiModelProperty(value = "validEndTime", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date validEndTime;
    @ApiModelProperty(value = "status", example = "1")
    private Integer status;
    @ApiModelProperty(value = "remarks", example = "remarks")
    private String remarks;

    public Integer getPlatformGoodsCategoryId() {
        return platformGoodsCategoryId;
    }

    public void setPlatformGoodsCategoryId(Integer platformGoodsCategoryId) {
        this.platformGoodsCategoryId = platformGoodsCategoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdvertSite() {
        return advertSite;
    }

    public void setAdvertSite(Integer advertSite) {
        this.advertSite = advertSite;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public Integer getShopGoodsId() {
        return shopGoodsId;
    }

    public void setShopGoodsId(Integer shopGoodsId) {
        this.shopGoodsId = shopGoodsId;
    }

    public Integer getPicValueType() {
        return picValueType;
    }

    public void setPicValueType(Integer picValueType) {
        this.picValueType = picValueType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Date getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(Date validStartTime) {
        this.validStartTime = validStartTime;
    }

    public Date getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "AdvertInfo{" +
                ", id=" + id +
                ", advertSite=" + advertSite +
                ", pic=" + pic +
                ", picLink=" + picLink +
                ", shopGoodsId=" + shopGoodsId +
                ", picValueType=" + picValueType +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                ", sort=" + sort +
                ", addTime=" + addTime +
                ", validStartTime=" + validStartTime +
                ", validEndTime=" + validEndTime +
                ", status=" + status +
                ", remarks=" + remarks +
                "}";
    }
}
