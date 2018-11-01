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
 * @since 2018-10-17
 */
@ApiModel("用户优惠券-po")
@TableName("user_activity_coupon")
public class UserActivityCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 店铺信息id
     */
    @ApiModelProperty(value = "店铺信息id", example = "1")
    private Integer shopInfoId;
    /**
     * 面额
     */
    @ApiModelProperty(value = "面额", example = "100")
    private BigDecimal amount;
    /**
     * 单笔订单满(元)使用
     */
    @ApiModelProperty(value = "单笔订单满(元)使用", example = "1000")
    private BigDecimal meetAmount;
    /**
     * 状态(未使用=1|已使用=2)
     */
    @ApiModelProperty(value = "状态(未使用=1|已使用=2)", example = "1")
    private Integer couponState;
    /**
     * 持有人(用户id)
     */
    @ApiModelProperty(value = "持有人(用户id)", example = "1")
    private Integer couponUserInfoId;
    /**
     * 有效期开始
     */
    @ApiModelProperty(value = "有效期开始", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date validDateBegin;
    /**
     * 有效期结束
     */
    @ApiModelProperty(value = "有效期结束", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date validDateEnd;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "couponNote", example = "couponNote")
    private String couponNote;


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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMeetAmount() {
        return meetAmount;
    }

    public void setMeetAmount(BigDecimal meetAmount) {
        this.meetAmount = meetAmount;
    }

    public Integer getCouponState() {
        return couponState;
    }

    public void setCouponState(Integer couponState) {
        this.couponState = couponState;
    }

    public Integer getCouponUserInfoId() {
        return couponUserInfoId;
    }

    public void setCouponUserInfoId(Integer couponUserInfoId) {
        this.couponUserInfoId = couponUserInfoId;
    }

    public Date getValidDateBegin() {
        return validDateBegin;
    }

    public void setValidDateBegin(Date validDateBegin) {
        this.validDateBegin = validDateBegin;
    }

    public Date getValidDateEnd() {
        return validDateEnd;
    }

    public void setValidDateEnd(Date validDateEnd) {
        this.validDateEnd = validDateEnd;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getCouponNote() {
        return couponNote;
    }

    public void setCouponNote(String couponNote) {
        this.couponNote = couponNote;
    }

    @Override
    public String toString() {
        return "UserActivityCoupon{" +
                ", id=" + id +
                ", shopInfoId=" + shopInfoId +
                ", amount=" + amount +
                ", meetAmount=" + meetAmount +
                ", couponState=" + couponState +
                ", couponUserInfoId=" + couponUserInfoId +
                ", validDateBegin=" + validDateBegin +
                ", validDateEnd=" + validDateEnd +
                ", addTime=" + addTime +
                ", couponNote=" + couponNote +
                "}";
    }
}
