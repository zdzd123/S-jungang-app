package com.jgzy.entity.po;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zou
 * @since 2018-11-07
 */
@TableName("originator_discount_info")
@ApiModel("合伙人折扣-po")
public class OriginatorDiscountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "购买金额", example = "1")
    private BigDecimal amount;
    @ApiModelProperty(value = "折扣率", example = "1")
    private BigDecimal discountRate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "OriginatorDiscountInfo{" +
        ", id=" + id +
        ", amount=" + amount +
        ", discountRate=" + discountRate +
        "}";
    }
}
