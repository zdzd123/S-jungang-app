package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@ApiModel("收获地址-po")
@TableName("user_address")
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 收货人姓名
     */
    @ApiModelProperty(value = "收货人姓名", example = "张三")
    private String name;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", example = "18311111111")
    private String phone;
    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编", example = "214116")
    private String zipCode;
    /**
     * 省id
     */
    @ApiModelProperty(value = "省id", example = "1")
    private Integer provinceId;
    /**
     * 市id
     */
    @ApiModelProperty(value = "市id", example = "1")
    private Integer cityId;
    /**
     * 区id
     */
    @ApiModelProperty(value = "区id", example = "1")
    private Integer areaId;
    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址", example = "新吴区")
    private String address;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "1")
    private Integer createUser;
    /**
     * 默认地址(否=1|是=2)
     */
    @ApiModelProperty(value = "默认地址(否=1|是=2)", example = "1")
    private Integer isDefault;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
        ", id=" + id +
        ", name=" + name +
        ", phone=" + phone +
        ", zipCode=" + zipCode +
        ", provinceId=" + provinceId +
        ", cityId=" + cityId +
        ", areaId=" + areaId +
        ", address=" + address +
        ", createUser=" + createUser +
        ", isDefault=" + isDefault +
        ", addTime=" + addTime +
        "}";
    }
}
