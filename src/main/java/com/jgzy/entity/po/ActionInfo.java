package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 活动表
 * </p>
 *
 * @author zou
 * @since 2018-10-23
 */
@ApiModel("活动详情-po")
@TableName("action_info")
public class ActionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "活动类型id", example = "1")
    private Integer typeId;
    @ApiModelProperty(value = "标题", example = "1")
    private String title;
    @ApiModelProperty(value = "首页展示图", example = "首页展示图")
    private String showPics;
    @ApiModelProperty(value = "首页展示视频地址", example = "首页展示视频地址")
    private String videoUrl;
    @ApiModelProperty(value = "活动详情", example = "活动详情")
    private String actionDetail;
    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;
    @ApiModelProperty(value = "创建时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "开始时间",example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @ApiModelProperty(value = "结束时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    @ApiModelProperty(value = "活动状态（已结束=1|进行中=2）", example = "1")
    private Integer state;
    @ApiModelProperty(value = "礼包id", example = "1")
    private Integer giftPackId;
    @ApiModelProperty(value = "修改时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    @ApiModelProperty(value = "删除时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date deleteTime;
    @ApiModelProperty(value = "是否删除（是=1|否=2）", example = "1")
    private Integer isDelete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShowPics() {
        return showPics;
    }

    public void setShowPics(String showPics) {
        this.showPics = showPics;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getActionDetail() {
        return actionDetail;
    }

    public void setActionDetail(String actionDetail) {
        this.actionDetail = actionDetail;
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

    public Integer getGiftPackId() {
        return giftPackId;
    }

    public void setGiftPackId(Integer giftPackId) {
        this.giftPackId = giftPackId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "ActionInfo{" +
                ", id=" + id +
                ", typeId=" + typeId +
                ", title=" + title +
                ", showPics=" + showPics +
                ", videoUrl=" + videoUrl +
                ", actionDetail=" + actionDetail +
                ", sort=" + sort +
                ", addTime=" + addTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                ", giftPackId=" + giftPackId +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                ", isDelete=" + isDelete +
                "}";
    }

}
