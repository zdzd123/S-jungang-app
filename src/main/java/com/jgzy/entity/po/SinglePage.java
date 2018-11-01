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
 * @since 2018-10-23
 */
@ApiModel("单页-po")
@TableName("single_page")
public class SinglePage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", example = "1")
    private Integer type;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", example = "标题")
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", example = "内容")
    private String content;
    @ApiModelProperty(value = "state", example = "1")
    private Integer state;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "addTime", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "pic", example = "pic")
    private String pic;
    @ApiModelProperty(value = "url", example = "url")
    private String url;
    /**
     * 概述
     */
    @ApiModelProperty(value = "summary", example = "summary")
    private String summary;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "SinglePage{" +
                ", id=" + id +
                ", type=" + type +
                ", title=" + title +
                ", content=" + content +
                ", state=" + state +
                ", addTime=" + addTime +
                ", pic=" + pic +
                ", url=" + url +
                ", summary=" + summary +
                "}";
    }
}
