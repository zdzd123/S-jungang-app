package com.jgzy.core.shopOrder.vo;

import com.jgzy.entity.po.ActionGiftPack;
import com.jgzy.entity.po.ActionInfo;
import com.jgzy.entity.po.ShopGoods;
import com.jgzy.entity.po.SinglePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("广告信息-VO")
public class AdvertInfoVo {
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "广告位置(首页=6 非活动轮播图=7 活动分类展示图=8 无=1)", example = "1")
    private Integer advertSite;
    @ApiModelProperty(value = "商品大类id", example = "1")
    private Integer platformGoodsCategoryId;
    @ApiModelProperty(value = "图片地址", example = "/songnaer/upload/20180517/201805171712270640.jpg")
    private String pic;
    @ApiModelProperty(value = "跳转类型 0-无 2-商品详情 3-活动详情 4-礼包详情 5-单页", example = "0")
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
    @ApiModelProperty(value = "商品信息")
    private List<ShopGoods> shopGoods;
    @ApiModelProperty(value = "活动信息")
    private ActionInfo actionInfo;
    @ApiModelProperty(value = "礼包信息")
    private ActionGiftPack actionGiftPack;
    @ApiModelProperty(value = "单页")
    private SinglePage singlePage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlatformGoodsCategoryId() {
        return platformGoodsCategoryId;
    }

    public void setPlatformGoodsCategoryId(Integer platformGoodsCategoryId) {
        this.platformGoodsCategoryId = platformGoodsCategoryId;
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

    public Integer getAdvertSite() {
        return advertSite;
    }

    public void setAdvertSite(Integer advertSite) {
        this.advertSite = advertSite;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<ShopGoods> getShopGoods() {
        return shopGoods;
    }

    public void setShopGoods(List<ShopGoods> shopGoods) {
        this.shopGoods = shopGoods;
    }

    public ActionInfo getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public ActionGiftPack getActionGiftPack() {
        return actionGiftPack;
    }

    public void setActionGiftPack(ActionGiftPack actionGiftPack) {
        this.actionGiftPack = actionGiftPack;
    }

    public SinglePage getSinglePage() {
        return singlePage;
    }

    public void setSinglePage(SinglePage singlePage) {
        this.singlePage = singlePage;
    }
}
