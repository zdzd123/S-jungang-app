package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
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
 * @since 2018-11-09
 */
@ApiModel("用户佣金股权-po")
@TableName("user_distribution_constant")
public class UserDistributionConstant implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "合伙人等级 1-初级 2-正式 3-中级 4-高级 5-直属", example = "1")
    private Integer levelId;
    @ApiModelProperty(value = "佣金系数", example = "1")
    private BigDecimal commissionDiscount;
    @ApiModelProperty(value = "股权比例", example = "1")
    private BigDecimal stockRightDiscount;
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "更新时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public BigDecimal getCommissionDiscount() {
        return commissionDiscount;
    }

    public void setCommissionDiscount(BigDecimal commissionDiscount) {
        this.commissionDiscount = commissionDiscount;
    }

    public BigDecimal getStockRightDiscount() {
        return stockRightDiscount;
    }

    public void setStockRightDiscount(BigDecimal stockRightDiscount) {
        this.stockRightDiscount = stockRightDiscount;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserDistributionConstant{" +
        ", id=" + id +
        ", levelId=" + levelId +
        ", commissionDiscount=" + commissionDiscount +
        ", stockRightDiscount=" + stockRightDiscount +
        ", addTime=" + addTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
