package com.jgzy.entity.po;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-10-19
 */
@ApiModel("商品订单-po")
@TableName("shop_goods_order")
public class ShopGoodsOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "订单id", example = "1")
    private String orderNo;
    @ApiModelProperty(value = "状态(待审核=0待付款=1|待尾款=2|待成团=61|团失败=62|待发货=3|待收货=4|待评价=5|已评价=6|待退款=7|已退款=8|待退货=9|已退货=10|交易关闭=11)", example = "1")
    private Integer orderStatus;
    @ApiModelProperty(value = "下单人(用户id)", example = "1")
    private Integer submitOrderUser;
    @ApiModelProperty(value = "blessing", example = "blessing")
    private String blessing;
    @ApiModelProperty(value = "isRedpacket", example = "isRedpacket")
    private Integer isRedpacket;
    @ApiModelProperty(value = "商品id", example = "1")
    private Integer shopInfoId;
    @ApiModelProperty(value = "实付金额(元)", example = "100")
    private BigDecimal actualAmount;
    @ApiModelProperty(value = "支付方式(积分=9|支付宝=1|微信支付=2)", example = "1")
    private Integer payType;
    @ApiModelProperty(value = "是否需要发票(否=1|是=2)", example = "1")
    private Integer needInvoice;
    @ApiModelProperty(value = "发票类型(普通发票=1|电子发票=2)", example = "1")
    private Integer invoiceType;
    @ApiModelProperty(value = "发票抬头", example = "1")
    private String invoiceHead;
    @ApiModelProperty(value = "发票内容", example = "1")
    private String invoiceContent;
    @ApiModelProperty(value = "快递公司", example = "1")
    private String expressCompany;
    @ApiModelProperty(value = "快递单号", example = "1")
    private String expressNo;
    @ApiModelProperty(value = "收货人", example = "1")
    private String receiveMan;
    @ApiModelProperty(value = "联系电话", example = "18311111111")
    private String contactPhone;
    @ApiModelProperty(value = "收货地址", example = "1")
    private String receiveAddress;
    @ApiModelProperty(value = "发货人", example = "1")
    private String shipper;
    @ApiModelProperty(value = "发货联系电话", example = "18311111111")
    private String shipperPhone;
    @ApiModelProperty(value = "发货地址", example = "1")
    private String shipperAddress;
    @ApiModelProperty(value = "订单总额(元)", example = "100")
    private BigDecimal orderAmountTotal;
    @ApiModelProperty(value = "运费(元)", example = "1")
    private BigDecimal carriage;
    @ApiModelProperty(value = "运费标识 1-到付 2-等待计算", example = "1")
    private Integer carriageType;
    @ApiModelProperty(value = "优惠金额(元)", example = "1")
    private BigDecimal couponAmount;
    @ApiModelProperty(value = "权额购买金额", example = "200")
    private BigDecimal advanceAmount;
    @ApiModelProperty(value = "耗材费", example = "200")
    private BigDecimal materialAmount;
    @ApiModelProperty(value = "创建时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty(value = "付款时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date payTime;
    @ApiModelProperty(value = "发货时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date sendOutTime;
    @ApiModelProperty(value = "订单有效期", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date validOrderTime;
    @ApiModelProperty(value = "是否团购(否=1，是=2)", example = "1")
    private Integer isGroupbuy;
    @ApiModelProperty(value = "是否预售(否=1，是=2)", example = "1")
    private Integer isPresell;
    @ApiModelProperty(value = "备注", example = "1")
    private String remarks;
    @ApiModelProperty(value = "订单标签", example = "1")
    private String shopOrderTagIds;
    @ApiModelProperty(value = "抵用券id", example = "1")
    private Integer shopActivityCouponId;
    @ApiModelProperty(value = "consumptionPoints", example = "1")
    private BigDecimal consumptionPoints;
    @ApiModelProperty(value = "paymentStage", example = "1")
    private Integer paymentStage;
    @ApiModelProperty(value = "transactionNumber", example = "1")
    private String transactionNumber;
    @ApiModelProperty(value = "关闭时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date closingTime;
    @ApiModelProperty(value = "订单来源（合伙人=1|库存=2|普通消费者=3|品牌费=4）", example = "1")
    private Integer orderSource;
    @ApiModelProperty(value = "订单标签", example = "1")
    private String shopOrderTag;
    @ApiModelProperty(value = "groupbuyOrderId", example = "1")
    private Integer groupbuyOrderId;
    @ApiModelProperty(value = "tradeNo", example = "1")
    private String tradeNo;
    @ApiModelProperty(value = "用户删除 0-删除", example = "1")
    private Integer userDel;
    @ApiModelProperty(value = "判断是否入库 1-下单直邮 2-放入库存 3-库存自提", example = "1")
    private Integer isStock;
    @ApiModelProperty(value = "isPoint", example = "1")
    private Integer isPoint;
    @ApiModelProperty(value = "返点标识 0-未返点 1-已返点", example = "1")
    private Integer isRead;
    @ApiModelProperty(value = "unionId", example = "1")
    private String unionId;
    @ApiModelProperty(value = "parentOrderId", example = "1")
    private Integer parentOrderId;
    @ApiModelProperty(value = "总金额", example = "1")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "总消耗余额", example = "1")
    private BigDecimal totalPoint;
    @TableField("total_Real_payment")
    @ApiModelProperty(value = "总实际付款", example = "1")
    private BigDecimal totalRealPayment;

    public Integer getCarriageType() {
        return carriageType;
    }

    public void setCarriageType(Integer carriageType) {
        this.carriageType = carriageType;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShipperPhone() {
        return shipperPhone;
    }

    public void setShipperPhone(String shipperPhone) {
        this.shipperPhone = shipperPhone;
    }

    public String getShipperAddress() {
        return shipperAddress;
    }

    public void setShipperAddress(String shipperAddress) {
        this.shipperAddress = shipperAddress;
    }

    public BigDecimal getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(BigDecimal advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public BigDecimal getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(BigDecimal materialAmount) {
        this.materialAmount = materialAmount;
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

    public String getBlessing() {
        return blessing;
    }

    public void setBlessing(String blessing) {
        this.blessing = blessing;
    }

    public Integer getIsRedpacket() {
        return isRedpacket;
    }

    public void setIsRedpacket(Integer isRedpacket) {
        this.isRedpacket = isRedpacket;
    }

    public Integer getShopInfoId() {
        return shopInfoId;
    }

    public void setShopInfoId(Integer shopInfoId) {
        this.shopInfoId = shopInfoId;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(Integer needInvoice) {
        this.needInvoice = needInvoice;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceHead() {
        return invoiceHead;
    }

    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
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

    public String getReceiveMan() {
        return receiveMan;
    }

    public void setReceiveMan(String receiveMan) {
        this.receiveMan = receiveMan;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public BigDecimal getOrderAmountTotal() {
        return orderAmountTotal;
    }

    public void setOrderAmountTotal(BigDecimal orderAmountTotal) {
        this.orderAmountTotal = orderAmountTotal;
    }

    public BigDecimal getCarriage() {
        return carriage;
    }

    public void setCarriage(BigDecimal carriage) {
        this.carriage = carriage;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
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

    public Date getSendOutTime() {
        return sendOutTime;
    }

    public void setSendOutTime(Date sendOutTime) {
        this.sendOutTime = sendOutTime;
    }

    public Date getValidOrderTime() {
        return validOrderTime;
    }

    public void setValidOrderTime(Date validOrderTime) {
        this.validOrderTime = validOrderTime;
    }

    public Integer getIsGroupbuy() {
        return isGroupbuy;
    }

    public void setIsGroupbuy(Integer isGroupbuy) {
        this.isGroupbuy = isGroupbuy;
    }

    public Integer getIsPresell() {
        return isPresell;
    }

    public void setIsPresell(Integer isPresell) {
        this.isPresell = isPresell;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getShopOrderTagIds() {
        return shopOrderTagIds;
    }

    public void setShopOrderTagIds(String shopOrderTagIds) {
        this.shopOrderTagIds = shopOrderTagIds;
    }

    public Integer getShopActivityCouponId() {
        return shopActivityCouponId;
    }

    public void setShopActivityCouponId(Integer shopActivityCouponId) {
        this.shopActivityCouponId = shopActivityCouponId;
    }

    public BigDecimal getConsumptionPoints() {
        return consumptionPoints;
    }

    public void setConsumptionPoints(BigDecimal consumptionPoints) {
        this.consumptionPoints = consumptionPoints;
    }

    public Integer getPaymentStage() {
        return paymentStage;
    }

    public void setPaymentStage(Integer paymentStage) {
        this.paymentStage = paymentStage;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    public String getShopOrderTag() {
        return shopOrderTag;
    }

    public void setShopOrderTag(String shopOrderTag) {
        this.shopOrderTag = shopOrderTag;
    }

    public Integer getGroupbuyOrderId() {
        return groupbuyOrderId;
    }

    public void setGroupbuyOrderId(Integer groupbuyOrderId) {
        this.groupbuyOrderId = groupbuyOrderId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getUserDel() {
        return userDel;
    }

    public void setUserDel(Integer userDel) {
        this.userDel = userDel;
    }

    public Integer getIsStock() {
        return isStock;
    }

    public void setIsStock(Integer isStock) {
        this.isStock = isStock;
    }

    public Integer getIsPoint() {
        return isPoint;
    }

    public void setIsPoint(Integer isPoint) {
        this.isPoint = isPoint;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Integer getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(Integer parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(BigDecimal totalPoint) {
        this.totalPoint = totalPoint;
    }

    public BigDecimal getTotalRealPayment() {
        return totalRealPayment;
    }

    public void setTotalRealPayment(BigDecimal totalRealPayment) {
        this.totalRealPayment = totalRealPayment;
    }

    @Override
    public String toString() {
        return "ShopGoodsOrder{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", orderStatus=" + orderStatus +
                ", submitOrderUser=" + submitOrderUser +
                ", blessing='" + blessing + '\'' +
                ", isRedpacket=" + isRedpacket +
                ", shopInfoId=" + shopInfoId +
                ", actualAmount=" + actualAmount +
                ", payType=" + payType +
                ", needInvoice=" + needInvoice +
                ", invoiceType=" + invoiceType +
                ", invoiceHead='" + invoiceHead + '\'' +
                ", invoiceContent='" + invoiceContent + '\'' +
                ", expressCompany='" + expressCompany + '\'' +
                ", expressNo='" + expressNo + '\'' +
                ", receiveMan='" + receiveMan + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", shipper='" + shipper + '\'' +
                ", shipperPhone='" + shipperPhone + '\'' +
                ", shipperAddress='" + shipperAddress + '\'' +
                ", orderAmountTotal=" + orderAmountTotal +
                ", carriage=" + carriage +
                ", couponAmount=" + couponAmount +
                ", advanceAmount=" + advanceAmount +
                ", materialAmount=" + materialAmount +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", sendOutTime=" + sendOutTime +
                ", validOrderTime=" + validOrderTime +
                ", isGroupbuy=" + isGroupbuy +
                ", isPresell=" + isPresell +
                ", remarks='" + remarks + '\'' +
                ", shopOrderTagIds='" + shopOrderTagIds + '\'' +
                ", shopActivityCouponId=" + shopActivityCouponId +
                ", consumptionPoints=" + consumptionPoints +
                ", paymentStage=" + paymentStage +
                ", transactionNumber='" + transactionNumber + '\'' +
                ", closingTime=" + closingTime +
                ", orderSource=" + orderSource +
                ", shopOrderTag='" + shopOrderTag + '\'' +
                ", groupbuyOrderId=" + groupbuyOrderId +
                ", tradeNo='" + tradeNo + '\'' +
                ", userDel=" + userDel +
                ", isStock=" + isStock +
                ", isPoint=" + isPoint +
                ", isRead=" + isRead +
                ", unionId='" + unionId + '\'' +
                ", parentOrderId=" + parentOrderId +
                ", totalAmount=" + totalAmount +
                ", totalPoint=" + totalPoint +
                ", totalRealPayment=" + totalRealPayment +
                '}';
    }
}
