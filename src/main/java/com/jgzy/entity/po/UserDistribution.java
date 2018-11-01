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
 * @since 2018-10-26
 */
@ApiModel("分销-po")
@TableName("user_distribution")
public class UserDistribution implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "父级 id", example = "1")
    private Integer parentId;
    @ApiModelProperty(value = "所有父级", example = "1")
    private String classList;
    @ApiModelProperty(value = "所属分销等级", example = "1")
    private Integer classLayer;
    @ApiModelProperty(value = "用户id", example = "1")
    private Integer userId;
    @ApiModelProperty(value = "id", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getClassList() {
        return classList;
    }

    public void setClassList(String classList) {
        this.classList = classList;
    }

    public Integer getClassLayer() {
        return classLayer;
    }

    public void setClassLayer(Integer classLayer) {
        this.classLayer = classLayer;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "UserDistribution{" +
                ", id=" + id +
                ", parentId=" + parentId +
                ", classList=" + classList +
                ", classLayer=" + classLayer +
                ", userId=" + userId +
                ", addTime=" + addTime +
                "}";
    }
}
