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
 * @since 2018-10-23
 */
@ApiModel("合伙人认证缴费")
@TableName("originator_info_order")
public class OriginatorInfoOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "订单id", example = "1")
    private String orderNo;
    @ApiModelProperty(value = "状态(待付款=1|交易关闭=2)", example = "1")
    private Integer orderStatus;
    @ApiModelProperty(value = "下单人(用户id)", example = "1")
    private Integer submitOrderUser;
    @ApiModelProperty(value = "支付方式(积分=9|支付宝=1|微信支付=2)", example = "1")
    private Integer payType;
    @ApiModelProperty(value = "订单总额(元)", example = "1")
    private BigDecimal orderAmount;
    @ApiModelProperty(value = "剩余金额(元)", example = "1")
    private BigDecimal remianAmount;
    @ApiModelProperty(value = "创建时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty(value = "付款时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date payTime;
    @ApiModelProperty(value = "更新时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getRemianAmount() {
        return remianAmount;
    }

    public void setRemianAmount(BigDecimal remianAmount) {
        this.remianAmount = remianAmount;
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
        return "OriginatorInfoOrder{" +
                ", id=" + id +
                ", orderNo=" + orderNo +
                ", orderStatus=" + orderStatus +
                ", submitOrderUser=" + submitOrderUser +
                ", payType=" + payType +
                ", orderAmount=" + orderAmount +
                ", remianAmount=" + remianAmount +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                "}";
    }
}
