package com.jgzy.core.personalCenter.vo;

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
@ApiModel("收获地址-vo")
public class UserAddressVo{
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
    @ApiModelProperty(value = "省", example = "江苏省")
    private String province;
    /**
     * 市id
     */
    @ApiModelProperty(value = "市", example = "无锡市")
    private String city;
    /**
     * 区id
     */
    @ApiModelProperty(value = "区", example = "锡山区")
    private String area;
    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址", example = "科技园")
    private String address;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserAddressVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
