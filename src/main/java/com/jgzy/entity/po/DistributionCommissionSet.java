package com.jgzy.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zou
 * @since 2018-11-16
 */
@ApiModel("分销等级-po")
public class DistributionCommissionSet implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "一级代理(%)", example = "1")
    private Integer shopInfoId;
    /**
     * 一级代理(%)
     */
    @ApiModelProperty(value = "id", example = "1")
    private BigDecimal oneLevelAgents;
    /**
     * 二级代理(%)
     */
    @ApiModelProperty(value = "二级代理(%)", example = "1")
    private BigDecimal twoLevelAgents;
    /**
     * 三级代理(%)
     */
    @ApiModelProperty(value = "三级代理(%)", example = "1")
    private BigDecimal threeLevelAgents;


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

    public BigDecimal getOneLevelAgents() {
        return oneLevelAgents;
    }

    public void setOneLevelAgents(BigDecimal oneLevelAgents) {
        this.oneLevelAgents = oneLevelAgents;
    }

    public BigDecimal getTwoLevelAgents() {
        return twoLevelAgents;
    }

    public void setTwoLevelAgents(BigDecimal twoLevelAgents) {
        this.twoLevelAgents = twoLevelAgents;
    }

    public BigDecimal getThreeLevelAgents() {
        return threeLevelAgents;
    }

    public void setThreeLevelAgents(BigDecimal threeLevelAgents) {
        this.threeLevelAgents = threeLevelAgents;
    }

    @Override
    public String toString() {
        return "DistributionCommissionSet{" +
                ", id=" + id +
                ", shopInfoId=" + shopInfoId +
                ", oneLevelAgents=" + oneLevelAgents +
                ", twoLevelAgents=" + twoLevelAgents +
                ", threeLevelAgents=" + threeLevelAgents +
                "}";
    }
}
