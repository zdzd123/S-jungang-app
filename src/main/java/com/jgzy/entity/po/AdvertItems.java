package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zou
 * @since 2018-10-18
 */
@ApiModel("广告信息详细信息-po")
@TableName("advert_items")
public class AdvertItems implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "广告信息表id", example = "1")
    private Integer advertInfoId;
    @ApiModelProperty(value = "图片地址", example = "/songnaer/upload/20180517/201805171712270640.jpg")
    private String pic;
    @ApiModelProperty(value = "跳转类型", example = "1")
    private Integer picValueType;
    @ApiModelProperty(value = "参数", example = "123")
    private String picValueParameter;
    @ApiModelProperty(value = "picValueUrl", example = "picValueUrl")
    private String picValueUrl;
    @ApiModelProperty(value = "活动类型", example = "1")
    private String extraField1;
    @ApiModelProperty(value = "活动类型", example = "1")
    private String extraField2;
    @ApiModelProperty(value = "活动类型", example = "1")
    private String extraField3;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdvertInfoId() {
        return advertInfoId;
    }

    public void setAdvertInfoId(Integer advertInfoId) {
        this.advertInfoId = advertInfoId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getPicValueType() {
        return picValueType;
    }

    public void setPicValueType(Integer picValueType) {
        this.picValueType = picValueType;
    }

    public String getPicValueParameter() {
        return picValueParameter;
    }

    public void setPicValueParameter(String picValueParameter) {
        this.picValueParameter = picValueParameter;
    }

    public String getPicValueUrl() {
        return picValueUrl;
    }

    public void setPicValueUrl(String picValueUrl) {
        this.picValueUrl = picValueUrl;
    }

    public String getExtraField1() {
        return extraField1;
    }

    public void setExtraField1(String extraField1) {
        this.extraField1 = extraField1;
    }

    public String getExtraField2() {
        return extraField2;
    }

    public void setExtraField2(String extraField2) {
        this.extraField2 = extraField2;
    }

    public String getExtraField3() {
        return extraField3;
    }

    public void setExtraField3(String extraField3) {
        this.extraField3 = extraField3;
    }

    @Override
    public String toString() {
        return "AdvertItems{" +
                ", id=" + id +
                ", advertInfoId=" + advertInfoId +
                ", pic=" + pic +
                ", picValueType=" + picValueType +
                ", picValueParameter=" + picValueParameter +
                ", picValueUrl=" + picValueUrl +
                ", extraField1=" + extraField1 +
                ", extraField2=" + extraField2 +
                ", extraField3=" + extraField3 +
                "}";
    }
}
