package com.jgzy.core.news.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.news.service.INewsInfoService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.NewsInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/newsInfo")
@Api(value = "资讯消息接口", description = "资讯消息接口")
public class NewsInfoController {
    @Autowired
    private INewsInfoService newsInfoService;

    @ApiOperation(value = "资讯信息(分页)", notes = "资讯信息(分页)")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<NewsInfo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                  @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
                                                  @ApiParam(value = "资讯分类") @RequestParam(required = false) String newsCategoryId,
                                                  @ApiParam(value = "1-独家专区 2-游学记") @RequestParam(required = false) String type) {
        ResultWrapper<Page<NewsInfo>> resultWrapper = new ResultWrapper<>();
        Page<NewsInfo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = newsInfoService.selectMyPage(page, newsCategoryId, type);
//        page = newsInfoService.selectMyPage(page, new EntityWrapper<NewsInfo>()
//                .eq("status", 2)
//                .eq(StringUtils.isNotEmpty(newsCategoryId), "news_category_id", newsCategoryId));
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @GetMapping(value = "/detail/{id:\\d+}")
    @ApiOperation(value = "通过id查询资讯信息", notes = "通过id查询资讯信息")
    @ApiImplicitParam(name = "id", value = "视频id", required = true, paramType = "path", dataType = "Integer")
    public ResultWrapper<NewsInfo> detail(@PathVariable("id") Integer id) {
        ResultWrapper<NewsInfo> resultWrapper = new ResultWrapper<>();
        NewsInfo newsInfo = newsInfoService.selectById(id);
        resultWrapper.setResult(newsInfo);
        return resultWrapper;
    }
}

