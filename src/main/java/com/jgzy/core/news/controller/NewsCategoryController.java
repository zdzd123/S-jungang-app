package com.jgzy.core.news.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.core.news.service.INewsCategoryService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.NewsCategory;
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
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-11-06
 */
@RefreshScope
@RestController
@RequestMapping("/api/newsCategory")
@Api(value = "商品资讯分类", description = "商品资讯分类")
public class NewsCategoryController {
    @Autowired
    private INewsCategoryService newsCategoryService;

    @GetMapping(value = "/detail")
    @ApiOperation(value = "查询商品资讯分类", notes = "查询商品资讯分类")
    public ResultWrapper<List<NewsCategory>> detail() {
        ResultWrapper<List<NewsCategory>> resultWrapper = new ResultWrapper();
        List<NewsCategory> newsCategoryList = newsCategoryService.selectList(new EntityWrapper<NewsCategory>().orderBy("sort_id asc"));
        resultWrapper.setResult(newsCategoryList);
        return resultWrapper;
    }

}

