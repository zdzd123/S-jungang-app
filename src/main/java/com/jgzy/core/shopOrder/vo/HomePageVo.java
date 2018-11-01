package com.jgzy.core.shopOrder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel("首页信息-VO")
public class HomePageVo {
    @ApiModelProperty(value = "轮播图")
    private List<AdvertInfoVo> viewPage;
    @ApiModelProperty(value = "商品图")
    private List<AdvertInfoVo> viewPageShop;
    @ApiModelProperty(value = "信息图")
    private List<AdvertInfoVo> viewPageMsg;

    public List<AdvertInfoVo> getViewPage() {
        return viewPage;
    }

    public void setViewPage(List<AdvertInfoVo> viewPage) {
        this.viewPage = viewPage;
    }

    public List<AdvertInfoVo> getViewPageShop() {
        return viewPageShop;
    }

    public void setViewPageShop(List<AdvertInfoVo> viewPageShop) {
        this.viewPageShop = viewPageShop;
    }

    public List<AdvertInfoVo> getViewPageMsg() {
        return viewPageMsg;
    }

    public void setViewPageMsg(List<AdvertInfoVo> viewPageMsg) {
        this.viewPageMsg = viewPageMsg;
    }
}
