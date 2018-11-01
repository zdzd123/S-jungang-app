package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jgzy.core.shopOrder.service.IPlatformGoodsCategoryService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.PlatformGoodsCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 平台商品分类 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-18
 */
@RefreshScope
@RestController
@RequestMapping("/api/platformGoodsCategory")
@Api(value = "平台商品分类接口", description = "平台商品分类接口")
public class PlatformGoodsCategoryController {
    @Autowired
    private IPlatformGoodsCategoryService platformGoodsCategoryService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取平台商品的所有分类", notes = "获取平台商品的所有分类")
    public ResultWrapper<List<PlatformGoodsCategory>> list(){
        ResultWrapper<List<PlatformGoodsCategory>> resultWrapper = new ResultWrapper<>();
        List<PlatformGoodsCategory> platformGoodsCategoryList = platformGoodsCategoryService.selectList(new EntityWrapper<PlatformGoodsCategory>());
        resultWrapper.setResult(platformGoodsCategoryList);
        return resultWrapper;
    }
}

