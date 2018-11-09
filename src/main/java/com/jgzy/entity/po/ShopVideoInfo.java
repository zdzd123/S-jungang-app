package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableName;
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
 * @since 2018-11-06
 */
@ApiModel("资讯视频-po")
@TableName("shop_video_info")
public class ShopVideoInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "店铺信息id", example = "1")
    private Integer shopInfoId;
    @ApiModelProperty(value = "平台视频分类id", example = "1")
    private Integer platformVideoCategoryId;
    @ApiModelProperty(value = "标题", example = "1")
    private String title;
    @ApiModelProperty(value = "副标题", example = "1")
    private String subtitle;
    @ApiModelProperty(value = "图片", example = "1")
    private String showPics;
    @ApiModelProperty(value = "时长", example = "1")
    private Double timeLength;
    @ApiModelProperty(value = "播放地址", example = "1")
    private String playUrl;
    @ApiModelProperty(value = "备注", example = "1")
    private String remarks;
    @ApiModelProperty(value = "讲师", example = "1")
    private String teacher;
    @ApiModelProperty(value = "视频描述", example = "1")
    private String describe;
    @ApiModelProperty(value = "播放次数", example = "1")
    private Integer playTimes;
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(Integer playTimes) {
        this.playTimes = playTimes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopInfoId() {
        return shopInfoId;
    }

    public void setShopInfoId(Integer shopInfoId) {
        this.shopInfoId = shopInfoId;
    }

    public Integer getPlatformVideoCategoryId() {
        return platformVideoCategoryId;
    }

    public void setPlatformVideoCategoryId(Integer platformVideoCategoryId) {
        this.platformVideoCategoryId = platformVideoCategoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getShowPics() {
        return showPics;
    }

    public void setShowPics(String showPics) {
        this.showPics = showPics;
    }

    public Double getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Double timeLength) {
        this.timeLength = timeLength;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "ShopVideoInfo{" +
                "id=" + id +
                ", shopInfoId=" + shopInfoId +
                ", platformVideoCategoryId=" + platformVideoCategoryId +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", showPics='" + showPics + '\'' +
                ", timeLength=" + timeLength +
                ", playUrl='" + playUrl + '\'' +
                ", remarks='" + remarks + '\'' +
                ", teacher='" + teacher + '\'' +
                ", describe='" + describe + '\'' +
                ", playTimes=" + playTimes +
                ", addTime=" + addTime +
                '}';
    }
}
