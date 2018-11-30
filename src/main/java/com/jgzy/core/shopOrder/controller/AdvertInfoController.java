package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.shopOrder.service.*;
import com.jgzy.core.shopOrder.vo.AdvertInfoVo;
import com.jgzy.core.shopOrder.vo.HomePageVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-18
 */
@RefreshScope
@RestController
@RequestMapping("/api/constant/advertInfo")
@Api(value = "广告信息接口", description = "广告信息接口")
public class AdvertInfoController {
    @Autowired
    private IAdvertInfoService advertInfoService;
    @Autowired
    private IAdvertItemsService advertItemsService;
    @Autowired
    private IShopGoodsService shopGoodsService;
    @Autowired
    private IActionInfoService actionInfoService;
    @Autowired
    private IActionGiftPackService actionGiftPackService;
    @Autowired
    private ISinglePageService singlePageService;
    @Autowired
    private IPlatformGoodsCategoryService platformGoodsCategoryService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取广告信息", notes = "获取广告信息")
    public ResultWrapper<List<AdvertInfoVo>> list(@ApiParam(value = "广告位置(首页=6 非活动轮播图=7 活动分类展示图=8 无=1)", required = true)
                                                  @RequestParam Integer advertSite) {
        ResultWrapper<List<AdvertInfoVo>> resultWrapper = new ResultWrapper<>();
        List<AdvertInfoVo> advertInfoList = advertInfoService.getAdvertInfo(null, advertSite);
        resultWrapper.setResult(advertInfoList);
        return resultWrapper;
    }

    @GetMapping(value = "/detail/{id:\\d+}")
    @ApiOperation(value = "通过id获取广告详细", notes = "通过id获取广告详细")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    public ResultWrapper<AdvertInfoVo> detail(@PathVariable("id") Integer id) {
        ResultWrapper<AdvertInfoVo> resultWrapper = new ResultWrapper<>();
        AdvertInfoVo advertInfoVo = new AdvertInfoVo();
        List<AdvertItems> advertItemsList = advertItemsService.selectList(
                new EntityWrapper<AdvertItems>()
                        .eq("advert_info_id", id));
        for (AdvertItems advertItems : advertItemsList) {
            String parameter = advertItems.getPicValueParameter();
            switch (advertItems.getPicValueType()) {
                // 跳转类型 0-无 2-商品详情 3-活动详情 4-礼包详情 5-单页
                case BaseConstant.PIC_VALUE_TYPE_3:
                    ActionInfo actionInfo = actionInfoService.selectById(parameter);
                    advertInfoVo.setActionInfo(actionInfo);
                    break;
                case BaseConstant.PIC_VALUE_TYPE_4:
                    ActionGiftPack actionGiftPack = actionGiftPackService.selectById(parameter);
                    advertInfoVo.setActionGiftPack(actionGiftPack);
                    break;
                case BaseConstant.PIC_VALUE_TYPE_5:
                    SinglePage singlePage = singlePageService.selectById(parameter);
                    advertInfoVo.setSinglePage(singlePage);
                    break;
            }
        }
        resultWrapper.setResult(advertInfoVo);
        return resultWrapper;
    }

    @GetMapping(value = "/homePage")
    @ApiOperation(value = "首页", notes = "首页")
    public ResultWrapper<HomePageVo> detail() {
        ResultWrapper<HomePageVo> resultWrapper = new ResultWrapper<>();
        HomePageVo homePageVo = new HomePageVo();
        // 获取所有广告，当为商品广告时获取商品信息
        List<AdvertInfoVo> allAdvert = advertInfoService.getAdvertInfo(null, null);
        // 轮播图
        List<AdvertInfoVo> viewPage = new ArrayList<>();
        List<AdvertInfoVo> viewPlatform = new ArrayList<>();
        List<AdvertInfoVo> viewPageShop = new ArrayList<>();
        List<AdvertInfoVo> viewPageMsg = new ArrayList<>();
        for (AdvertInfoVo advertInfo : allAdvert) {
            if (advertInfo.getAdvertSite() == BaseConstant.ADVERT_SITE_6) {
                if (advertInfo.getPicValueType().equals(BaseConstant.PIC_VALUE_TYPE_1)){
                    // 大类
                    advertInfo.setPlatformGoodsCategoryId(Integer.parseInt(advertInfo.getPicValueParameter()));
                }else if (advertInfo.getPicValueType().equals(BaseConstant.PIC_VALUE_TYPE_2)){
                    // 商品
                    advertInfo.setShopGoodsId(Integer.parseInt(advertInfo.getPicValueParameter()));
                }else if (advertInfo.getPicValueType().equals(BaseConstant.PIC_VALUE_TYPE_5)){
                    // 单页
                    advertInfo.setSinglePageId(Integer.parseInt(advertInfo.getPicValueParameter()));
                }
                viewPage.add(advertInfo);
            } else if (advertInfo.getAdvertSite() == BaseConstant.ADVERT_SITE_8) {
                if (advertInfo.getPicValueType().equals(BaseConstant.PIC_VALUE_TYPE_1)){
                    // 包含大类,通过大类查找商品
                    List<ShopGoods> shopGoodsList = shopGoodsService.selectList(
                            new EntityWrapper<ShopGoods>()
                                    .eq("platform_goods_category_id", advertInfo.getPicValueParameter())
                                    .eq("is_special", BaseConstant.IS_SPECIAL_0)
                                    .last("limit 5"));
                    advertInfo.setShopGoods(shopGoodsList);
                    advertInfo.setPlatformGoodsCategoryId(Integer.parseInt(advertInfo.getPicValueParameter()));
                    viewPlatform.add(advertInfo);
                }else if (advertInfo.getPicValueType().equals(BaseConstant.PIC_VALUE_TYPE_2)){
                    // 商品
                    advertInfo.setShopGoodsId(Integer.parseInt(advertInfo.getPicValueParameter()));
                    viewPageShop.add(advertInfo);
                }else if (advertInfo.getPicValueType().equals(BaseConstant.PIC_VALUE_TYPE_5)){
                    // 单页
                    advertInfo.setSinglePageId(Integer.parseInt(advertInfo.getPicValueParameter()));
                    viewPageMsg.add(advertInfo);
                }
            }
        }
        homePageVo.setViewPage(viewPage);
        homePageVo.setViewPlatform(viewPlatform);
        homePageVo.setViewPageShop(viewPageShop);
        homePageVo.setViewPageMsg(viewPageMsg);
        resultWrapper.setResult(homePageVo);
        return resultWrapper;
    }
}

