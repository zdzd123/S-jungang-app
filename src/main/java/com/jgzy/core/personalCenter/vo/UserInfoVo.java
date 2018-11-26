package com.jgzy.core.personalCenter.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("用户信息-vo")
public class UserInfoVo {
    @ApiModelProperty(value = "用户id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "昵称", example = "昵称")
    private String nickname;
    @ApiModelProperty(value = "头像", example = "/1.png")
    private String headPortrait;
    @ApiModelProperty(value = "积分", example = "11")
    private BigDecimal point;
    @ApiModelProperty(value = "余额1(可提现)", example = "11")
    private BigDecimal balance1;
    @ApiModelProperty(value = "冻结金额", example = "11")
    private BigDecimal balance4;
    @ApiModelProperty(value = "性别（1=男|2=女", example = "1")
    private Integer gender;
    @ApiModelProperty(value = "更新时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    @ApiModelProperty(value = "用户名称", example = "1")
    private String userName;
    @ApiModelProperty(value = "合伙人等级 1-初级 2-正式 3-中级 4-高级 5-直属", example = "1")
    private Integer levelId;
    @ApiModelProperty(value = "身份证号码", example = "111111")
    private String identifyNo;
    @ApiModelProperty(value = "手机号", example = "18360419883")
    private String phone;
    @ApiModelProperty(value = "微信号", example = "1")
    private String weixin;
    @ApiModelProperty(value = "状态(0-正常 1-锁定 2-待审核 3-未通过 4-待付款)", example = "1")
    private Integer status;
    @ApiModelProperty(value = "折扣", example = "1")
    private BigDecimal discount;
    @ApiModelProperty(value = "查看折扣标识 0-关闭 1-打开", example = "1")
    private Integer discountStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public BigDecimal getBalance1() {
        return balance1;
    }

    public void setBalance1(BigDecimal balance1) {
        this.balance1 = balance1;
    }

    public BigDecimal getBalance4() {
        return balance4;
    }

    public void setBalance4(BigDecimal balance4) {
        this.balance4 = balance4;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getIdentifyNo() {
        return identifyNo;
    }

    public void setIdentifyNo(String identifyNo) {
        this.identifyNo = identifyNo;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(Integer discountStatus) {
        this.discountStatus = discountStatus;
    }
}
