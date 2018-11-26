package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zou
 * @since 2018-10-24
 */
@ApiModel("权额表-po")
@TableName("advance_recharge_info")
public class AdvanceRechargeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "会员等级 1-初级 2-正式 3-中级 4-高级 5-直属", example = "1")
    private Integer levelId;
    @ApiModelProperty(value = "购买金额", example = "5000")
    private BigDecimal amount;
    @ApiModelProperty(value = "折扣率", example = "0.8")
    private BigDecimal discountRate;
    @ApiModelProperty(value = "权额描述", example = "不可以用于购买酒具礼盒")
    private String describe;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public String toString() {
        return "AdvanceRechargeInfo{" +
                ", id=" + id +
                ", levelId=" + levelId +
                ", amount=" + amount +
                ", discountRate=" + discountRate +
                "}";
    }
}
