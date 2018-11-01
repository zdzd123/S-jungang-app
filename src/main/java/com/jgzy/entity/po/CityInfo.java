package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@ApiModel(value = "市常量-po")
@TableName("city_info")
public class CityInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "110100")
    private Integer id;
    @ApiModelProperty(value = "市名", example = "北京市")
    private String name;
    @ApiModelProperty(value = "省id", example = "110000")
    private Integer provinceId;


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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
        ", id=" + id +
        ", name=" + name +
        ", provinceId=" + provinceId +
        "}";
    }
}
