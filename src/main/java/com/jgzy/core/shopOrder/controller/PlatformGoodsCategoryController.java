package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.core.shopOrder.service.IPlatformGoodsCategoryService;
import com.jgzy.core.shopOrder.vo.PlatformGoodsCategoryVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.PlatformGoodsCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    @Value("#{'${platformGoodsCategoryList}'.split(',')}")
    private List<Integer> specialPlatformGoodsCategoryList;


    @GetMapping(value = "/list")
    @ApiOperation(value = "获取平台商品的所有分类", notes = "获取平台商品的所有分类")
    public ResultWrapper<List<PlatformGoodsCategoryVo>> list() {
        ResultWrapper<List<PlatformGoodsCategoryVo>> resultWrapper = new ResultWrapper<>();
        List<PlatformGoodsCategory> platformGoodsCategoryList = platformGoodsCategoryService.selectList(
                new EntityWrapper<>());
        List<PlatformGoodsCategoryVo> myPlatformGoodsCategoryList = new ArrayList<>();
        for (PlatformGoodsCategory platformGoodsCategory : platformGoodsCategoryList) {
            PlatformGoodsCategoryVo platformGoodsCategoryVo = new PlatformGoodsCategoryVo();
            BeanUtils.copyProperties(platformGoodsCategory, platformGoodsCategoryVo);
            if (specialPlatformGoodsCategoryList.contains(platformGoodsCategory.getId())) {
                platformGoodsCategoryVo.setSpecial(true);
            }
            myPlatformGoodsCategoryList.add(platformGoodsCategoryVo);
        }
        resultWrapper.setResult(myPlatformGoodsCategoryList);
        return resultWrapper;
    }
}

