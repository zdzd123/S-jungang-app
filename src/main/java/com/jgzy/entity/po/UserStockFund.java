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
 * 用户库存流水
 * </p>
 *
 * @author zou
 * @since 2019-01-11
 */
@ApiModel("用户库存流水-po")
@TableName("user_stock_fund")
public class UserStockFund implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 交易人(用户id)
     */
    @ApiModelProperty(value = "用户id", example = "1")
    private Integer tradeUserId;
    /**
     * 交易商品id
     */
    @ApiModelProperty(value = "交易商品id", example = "1")
    private Integer tradeShopGoodsId;
    /**
     * 交易时间
     */
    @ApiModelProperty(value = "交易时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date tradeTime;
    /**
     * 交易方式(收入=1|支出=2)
     */
    @ApiModelProperty(value = "交易方式(收入=1|支出=2)", example = "1")
    private Integer tradeType;
    /**
     * 增加
     */
    @ApiModelProperty(value = "增加", example = "1")
    private BigDecimal increaseMoney;
    /**
     * 减少
     */
    @ApiModelProperty(value = "减少", example = "1")
    private BigDecimal decreaseMoney;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", example = "1")
    private String tradeDescribe;
    /**
     * 对应商品余额
     */
    @ApiModelProperty(value = "对应商品余额", example = "1")
    private BigDecimal tradeCount;
    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id", example = "1")
    private String orderNo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTradeUserId() {
        return tradeUserId;
    }

    public void setTradeUserId(Integer tradeUserId) {
        this.tradeUserId = tradeUserId;
    }

    public Integer getTradeShopGoodsId() {
        return tradeShopGoodsId;
    }

    public void setTradeShopGoodsId(Integer tradeShopGoodsId) {
        this.tradeShopGoodsId = tradeShopGoodsId;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getIncreaseMoney() {
        return increaseMoney;
    }

    public void setIncreaseMoney(BigDecimal increaseMoney) {
        this.increaseMoney = increaseMoney;
    }

    public BigDecimal getDecreaseMoney() {
        return decreaseMoney;
    }

    public void setDecreaseMoney(BigDecimal decreaseMoney) {
        this.decreaseMoney = decreaseMoney;
    }

    public String getTradeDescribe() {
        return tradeDescribe;
    }

    public void setTradeDescribe(String tradeDescribe) {
        this.tradeDescribe = tradeDescribe;
    }

    public BigDecimal getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(BigDecimal tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "UserStockFund{" +
                ", id=" + id +
                ", tradeUserId=" + tradeUserId +
                ", tradeShopGoodsId=" + tradeShopGoodsId +
                ", tradeTime=" + tradeTime +
                ", tradeType=" + tradeType +
                ", increaseMoney=" + increaseMoney +
                ", decreaseMoney=" + decreaseMoney +
                ", tradeDescribe=" + tradeDescribe +
                ", tradeCount=" + tradeCount +
                ", orderNo=" + orderNo +
                "}";
    }
}
