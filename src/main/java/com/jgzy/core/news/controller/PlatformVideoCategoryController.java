package com.jgzy.core.news.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.core.news.service.IPlatformVideoCategoryService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.PlatformVideoCategory;
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
 *  前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-11-06
 */
@RefreshScope
@RestController
@RequestMapping("/api/platformVideoCategory")
@Api(value = "平台视频分类接口", description = "平台视频分类接口")
public class PlatformVideoCategoryController {
    @Autowired
    private IPlatformVideoCategoryService platformVideoCategoryService;

    @GetMapping(value = "/detail")
    @ApiOperation(value = "查询平台视频分类", notes = "查询平台视频分类")
    public ResultWrapper<List<PlatformVideoCategory>> detail() {
        ResultWrapper<List<PlatformVideoCategory>> resultWrapper = new ResultWrapper<>();
        List<PlatformVideoCategory> platformVideoCategoryList = platformVideoCategoryService.selectList(new EntityWrapper<>());
        resultWrapper.setResult(platformVideoCategoryList);
        return resultWrapper;
    }
}

