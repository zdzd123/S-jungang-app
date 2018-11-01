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
@ApiModel(value = "区常量-po")
@TableName("area_info")
public class AreaInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "110101")
    private Integer id;
    @ApiModelProperty(value = "区名", example = "东城区")
    private String name;
    @ApiModelProperty(value = "市id", example = "110100")
    private Integer cityId;


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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "AreaInfo{" +
        ", id=" + id +
        ", name=" + name +
        ", cityId=" + cityId +
        "}";
    }
}
