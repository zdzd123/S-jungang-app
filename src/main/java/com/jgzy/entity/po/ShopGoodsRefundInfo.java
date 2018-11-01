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
@ApiModel("退款/退货-po")
@TableName("shop_goods_refund_info")
public class ShopGoodsRefundInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "店铺信息id", example = "1")
    private Integer shopInfoId;
    @ApiModelProperty(value = "订单信息id", example = "1")
    private Integer orderId;
    @ApiModelProperty(value = "是否收到货物(已收到货=1|没有收到货=2)", example = "1")
    private Integer isReceive;
    @ApiModelProperty(value = "是否需要退货(我需要退货=1|不需要退货=2)", example = "1")
    private Integer needReturn;
    @ApiModelProperty(value = "退款原因name", example = "退款")
    private String returnReason;
    @ApiModelProperty(value = "需要退款的金额(元)", example = "1")
    private BigDecimal refundAmount;
    @ApiModelProperty(value = "退款说明", example = "不需要")
    private String refundRemark;
    @ApiModelProperty(value = "上传凭证", example = "1")
    private String uploadDocuments;
    @ApiModelProperty(value = "退款人(用户id)", example = "1")
    private Integer refundUserId;
    @ApiModelProperty(value = "状态(待卖家处理=1|卖家拒绝退款/退货=2|待买家退货=3|买家已退货,等待卖家确认收货=4|卖家已退款=5|买家取消=9)", example = "1")
    private Integer status;
    @ApiModelProperty(value = "拒绝退款时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date refuseRefundTime;
    @ApiModelProperty(value = "卖家同意退款/退货时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date agreeRefundTime;
    @ApiModelProperty(value = "买家已退货时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date returnedTime;
    @ApiModelProperty(value = "goodsDetailId", example = "1")
    private Integer goodsDetailId;
    @ApiModelProperty(value = "买家确认时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date buyerSubmitTime;
    @ApiModelProperty(value = "买家取消时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date buyerCancelTime;
    @ApiModelProperty(value = "disposeNo", example = "1")
    private String disposeNo;
    @ApiModelProperty(value = "handleRemark", example = "1")
    private String handleRemark;
    @ApiModelProperty(value = "handleName", example = "1")
    private String handleName;
    @ApiModelProperty(value = "readTime", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date readTime;
    @ApiModelProperty(value = "refundMethod", example = "1")
    private Integer refundMethod;
    @ApiModelProperty(value = "isRead", example = "1")
    private Integer isRead;
    @ApiModelProperty(value = "手续费", example = "1")
    private BigDecimal serviceCharge;
    @ApiModelProperty(value = "应退积分", example = "1")
    private BigDecimal refundPoint;
    @ApiModelProperty(value = "应退现金", example = "1")
    private BigDecimal refundMoney;
    @ApiModelProperty(value = "确认收货时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date confirmTime;
    @ApiModelProperty(value = "卖家换货发货物流公司", example = "1")
    private String expressCompany;
    @ApiModelProperty(value = "卖家换货发货物流编号", example = "1")
    private String expressNo;
    @ApiModelProperty(value = "卖家换货发货时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date sendOutTime;
    @ApiModelProperty(value = "买家换货确认收货时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date buyerConfirmTime;
    @ApiModelProperty(value = "提交时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date submitTime;


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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Integer isReceive) {
        this.isReceive = isReceive;
    }

    public Integer getNeedReturn() {
        return needReturn;
    }

    public void setNeedReturn(Integer needReturn) {
        this.needReturn = needReturn;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }

    public String getUploadDocuments() {
        return uploadDocuments;
    }

    public void setUploadDocuments(String uploadDocuments) {
        this.uploadDocuments = uploadDocuments;
    }

    public Integer getRefundUserId() {
        return refundUserId;
    }

    public void setRefundUserId(Integer refundUserId) {
        this.refundUserId = refundUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRefuseRefundTime() {
        return refuseRefundTime;
    }

    public void setRefuseRefundTime(Date refuseRefundTime) {
        this.refuseRefundTime = refuseRefundTime;
    }

    public Date getAgreeRefundTime() {
        return agreeRefundTime;
    }

    public void setAgreeRefundTime(Date agreeRefundTime) {
        this.agreeRefundTime = agreeRefundTime;
    }

    public Date getReturnedTime() {
        return returnedTime;
    }

    public void setReturnedTime(Date returnedTime) {
        this.returnedTime = returnedTime;
    }

    public Integer getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(Integer goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public Date getBuyerSubmitTime() {
        return buyerSubmitTime;
    }

    public void setBuyerSubmitTime(Date buyerSubmitTime) {
        this.buyerSubmitTime = buyerSubmitTime;
    }

    public Date getBuyerCancelTime() {
        return buyerCancelTime;
    }

    public void setBuyerCancelTime(Date buyerCancelTime) {
        this.buyerCancelTime = buyerCancelTime;
    }

    public String getDisposeNo() {
        return disposeNo;
    }

    public void setDisposeNo(String disposeNo) {
        this.disposeNo = disposeNo;
    }

    public String getHandleRemark() {
        return handleRemark;
    }

    public void setHandleRemark(String handleRemark) {
        this.handleRemark = handleRemark;
    }

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Integer getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(Integer refundMethod) {
        this.refundMethod = refundMethod;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getRefundPoint() {
        return refundPoint;
    }

    public void setRefundPoint(BigDecimal refundPoint) {
        this.refundPoint = refundPoint;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public Date getSendOutTime() {
        return sendOutTime;
    }

    public void setSendOutTime(Date sendOutTime) {
        this.sendOutTime = sendOutTime;
    }

    public Date getBuyerConfirmTime() {
        return buyerConfirmTime;
    }

    public void setBuyerConfirmTime(Date buyerConfirmTime) {
        this.buyerConfirmTime = buyerConfirmTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    @Override
    public String toString() {
        return "ShopGoodsRefundInfo{" +
                ", id=" + id +
                ", shopInfoId=" + shopInfoId +
                ", orderId=" + orderId +
                ", isReceive=" + isReceive +
                ", needReturn=" + needReturn +
                ", returnReason=" + returnReason +
                ", refundAmount=" + refundAmount +
                ", refundRemark=" + refundRemark +
                ", uploadDocuments=" + uploadDocuments +
                ", refundUserId=" + refundUserId +
                ", status=" + status +
                ", refuseRefundTime=" + refuseRefundTime +
                ", agreeRefundTime=" + agreeRefundTime +
                ", returnedTime=" + returnedTime +
                ", goodsDetailId=" + goodsDetailId +
                ", buyerSubmitTime=" + buyerSubmitTime +
                ", buyerCancelTime=" + buyerCancelTime +
                ", disposeNo=" + disposeNo +
                ", handleRemark=" + handleRemark +
                ", handleName=" + handleName +
                ", readTime=" + readTime +
                ", refundMethod=" + refundMethod +
                ", isRead=" + isRead +
                ", serviceCharge=" + serviceCharge +
                ", refundPoint=" + refundPoint +
                ", refundMoney=" + refundMoney +
                ", confirmTime=" + confirmTime +
                ", expressCompany=" + expressCompany +
                ", expressNo=" + expressNo +
                ", sendOutTime=" + sendOutTime +
                ", buyerConfirmTime=" + buyerConfirmTime +
                ", submitTime=" + submitTime +
                "}";
    }
}
