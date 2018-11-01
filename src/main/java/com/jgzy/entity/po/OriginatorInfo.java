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
 * @since 2018-10-19
 */
@ApiModel("合伙人-po")
@TableName("originator_info")
public class OriginatorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户id", example = "1")
    private Integer userId;
    @ApiModelProperty(value = "用户名称", example = "1")
    private String userName;
    @ApiModelProperty(value = "身份证号码", example = "111111")
    private String identifyNo;
//    @ApiModelProperty(value = "公司名称", example = "阿里巴巴")
//    private String companyName;
//    @ApiModelProperty(value = "是否股东", example = "0-不是 1-是")
//    private Integer isShareholder;
    @ApiModelProperty(value = "身份证照片", example = "1")
    private String pic;
    @ApiModelProperty(value = "手机号", example = "18360419883")
    private String phone;
    @ApiModelProperty(value = "微信号", example = "1")
    private String weixin;
    @ApiModelProperty(value = "结婚证或转退证照片", example = "1")
    private String marriagePic;
//    @ApiModelProperty(value = "代理类型(1=省 2=市 3=区)", example = "1")
//    private Integer agentType;
//    @ApiModelProperty(value = "省id", example = "1")
//    private Integer provinceId;
//    @ApiModelProperty(value = "市id", example = "1")
//    private Integer cityId;
//    @ApiModelProperty(value = "区id", example = "1")
//    private Integer areaId;
//    @ApiModelProperty(value = "经营地址", example = "1")
    private String address;
//    @ApiModelProperty(value = "内容", example = "内容")
//    private String contact;
    @ApiModelProperty(value = "状态(0-正常 1-锁定 2-待审核 3-未通过 4-待付款)", example = "1")
    private Integer status;
//    @ApiModelProperty(value = "推荐码uuid", example = "1")
//    private String recommendCode;
    @ApiModelProperty(value = "addTime", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "折扣", example = "1")
    private BigDecimal discount;
    @ApiModelProperty(value = "查看折扣标识 0-关闭 1-打开", example = "1")
    private Integer discountStatus;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getMarriagePic() {
        return marriagePic;
    }

    public void setMarriagePic(String marriagePic) {
        this.marriagePic = marriagePic;
    }

    public Integer getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(Integer discountStatus) {
        this.discountStatus = discountStatus;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentifyNo() {
        return identifyNo;
    }

    public void setIdentifyNo(String identifyNo) {
        this.identifyNo = identifyNo;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "OriginatorInfo{" +
                ", id=" + id +
                ", userId=" + userId +
                ", userName=" + userName +
                ", identifyNo=" + identifyNo +
                ", pic=" + pic +
                ", addTime=" + addTime +
                "}";
    }
}
