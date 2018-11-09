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
@ApiModel("平台视频分类-po")
@TableName("platform_video_category")
public class PlatformVideoCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "分类名称", example = "分类名称")
    private String categoryName;
    //@ApiModelProperty(value = "页面是否显示(否=1|是=2)", example = "1")
    //private Integer isShow;
    @ApiModelProperty(value = "备注", example = "1")
    private String remarks;
    //@ApiModelProperty(value = "针对店铺是否显示(否=1|是=2)", example = "1")
    //private Integer forShopIsShow;
    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        return "PlatformVideoCategory{" +
                ", id=" + id +
                ", categoryName=" + categoryName +
                ", remarks=" + remarks +
                ", sort=" + sort +
                ", addTime=" + addTime +
                "}";
    }
}
