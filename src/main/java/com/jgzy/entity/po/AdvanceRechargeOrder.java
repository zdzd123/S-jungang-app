package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zou
 * @since 2018-10-24
 */
@ApiModel("用户权额-po")
@TableName("advance_recharge_order")
public class AdvanceRechargeOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "订单id", example = "1")
    private String orderNo;
    @ApiModelProperty(value = "状态(待付款=1|交易关闭=2)", example = "1")
    private Integer orderStatus;
    @ApiModelProperty(value = "下单人(用户id)", example = "1")
    private Integer submitOrderUser;
    @ApiModelProperty(value = "支付方式(积分=9|支付宝=1|微信支付=2)", example = "1")
    private Integer payType;
    @ApiModelProperty(value = "会员等级 1-初级 2-正式 3-中级 4-高级 5-直属", example = "1")
    private Integer levelId;
    @ApiModelProperty(value = "订单总额(元)", example = "1")
    private BigDecimal amount;
    @ApiModelProperty(value = "折扣", example = "0.1")
    private BigDecimal discountRate;
    @ApiModelProperty(value = "创建时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty(value = "付款时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getSubmitOrderUser() {
        return submitOrderUser;
    }

    public void setSubmitOrderUser(Integer submitOrderUser) {
        this.submitOrderUser = submitOrderUser;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return "AdvanceRechargeOrder{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", orderStatus=" + orderStatus +
                ", submitOrderUser=" + submitOrderUser +
                ", payType=" + payType +
                ", amount=" + amount +
                ", discountRate=" + discountRate +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                '}';
    }
}
