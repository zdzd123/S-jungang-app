package com.jgzy.entity.po;

import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 活动礼包表
 * </p>
 *
 * @author zou
 * @since 2018-10-23
 */
@TableName("action_gift_pack")
@ApiModel("活动礼包表-po")
public class ActionGiftPack implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "id", example = "1")
    private Integer actionId;
    @ApiModelProperty(value = "礼包名称", example = "礼包名称")
    private String giftPackName;
    @ApiModelProperty(value = "礼包展示图", example = "礼包展示图")
    private String giftPackPics;
    @ApiModelProperty(value = "礼包视频地址", example = "礼包视频地址")
    private String giftPackVideoUrl;
    @ApiModelProperty(value = "商品总价", example = "1")
    private BigDecimal goodsTotalPric;
    @ApiModelProperty(value = "实际总价", example = "1")
    private BigDecimal realTotalPric;
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "祝福语", example = "祝福语")
    private String wishContent;
    @ApiModelProperty(value = "备注", example = "备注")
    private String remark;
    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;
    @ApiModelProperty(value = "是否显示（是=1|否=2）", example = "1")
    private Integer isShow;
    @ApiModelProperty(value = "修改时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    @ApiModelProperty(value = "删除时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date deleteTime;
    @ApiModelProperty(value = "是否删除（是=1|否=2）", example = "1")
    private Integer isDelete;
    @ApiModelProperty(value = "礼包详情富文本", example = "礼包详情富文本")
    private String packDetail;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getGiftPackName() {
        return giftPackName;
    }

    public void setGiftPackName(String giftPackName) {
        this.giftPackName = giftPackName;
    }

    public String getGiftPackPics() {
        return giftPackPics;
    }

    public void setGiftPackPics(String giftPackPics) {
        this.giftPackPics = giftPackPics;
    }

    public String getGiftPackVideoUrl() {
        return giftPackVideoUrl;
    }

    public void setGiftPackVideoUrl(String giftPackVideoUrl) {
        this.giftPackVideoUrl = giftPackVideoUrl;
    }

    public BigDecimal getGoodsTotalPric() {
        return goodsTotalPric;
    }

    public void setGoodsTotalPric(BigDecimal goodsTotalPric) {
        this.goodsTotalPric = goodsTotalPric;
    }

    public BigDecimal getRealTotalPric() {
        return realTotalPric;
    }

    public void setRealTotalPric(BigDecimal realTotalPric) {
        this.realTotalPric = realTotalPric;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getWishContent() {
        return wishContent;
    }

    public void setWishContent(String wishContent) {
        this.wishContent = wishContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
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

    public String getPackDetail() {
        return packDetail;
    }

    public void setPackDetail(String packDetail) {
        this.packDetail = packDetail;
    }

    @Override
    public String toString() {
        return "ActionGiftPack{" +
                ", id=" + id +
                ", actionId=" + actionId +
                ", giftPackName=" + giftPackName +
                ", giftPackPics=" + giftPackPics +
                ", giftPackVideoUrl=" + giftPackVideoUrl +
                ", goodsTotalPric=" + goodsTotalPric +
                ", realTotalPric=" + realTotalPric +
                ", addTime=" + addTime +
                ", wishContent=" + wishContent +
                ", remark=" + remark +
                ", sort=" + sort +
                ", isShow=" + isShow +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                ", isDelete=" + isDelete +
                ", packDetail=" + packDetail +
                "}";
    }
}
