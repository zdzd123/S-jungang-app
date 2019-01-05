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
@ApiModel("资讯分类-po")
@TableName("news_category")
public class NewsCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    //    @ApiModelProperty(value = "上级分类id", example = "1")
//    private Integer parentId;
    @ApiModelProperty(value = "名称", example = "名称")
    private String name;
    //    @ApiModelProperty(value = "菜单ID列表(逗号分隔开)", example = "1")
//    private String classList;
//    @ApiModelProperty(value = "导航深度", example = "1")
//    private Integer classLayer;
    @ApiModelProperty(value = "排序", example = "1")
    private Integer sortId;

    @ApiModelProperty(value = "addTime", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "1-独家专区 2-游学记", example = "1")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "NewsCategory{" +
                ", id=" + id +
                ", name=" + name +
                ", sortId=" + sortId +
                ", addTime=" + addTime +
                "}";
    }
}
