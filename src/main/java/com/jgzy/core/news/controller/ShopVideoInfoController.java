package com.jgzy.core.news.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.news.service.IShopVideoInfoService;
import com.jgzy.core.news.vo.ShopVideoInfoVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.ShopVideoInfo;
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
@RequestMapping("/api/shopVideoInfo")
@Api(value = "资讯视频接口", description = "资讯视频接口")
public class ShopVideoInfoController {
    @Autowired
    private IShopVideoInfoService shopVideoInfoService;

    @ApiOperation(value = "资讯视频(分页)", notes = "资讯视频(分页)")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<ShopVideoInfo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                       @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
                                                       @ApiParam(value = "平台分类ID") @RequestParam(required = false) String platformVideoCategoryId,
                                                       @ApiParam(value = "1-商学院 2-庄主在线") @RequestParam(required = false) String type) {
        ResultWrapper<Page<ShopVideoInfo>> resultWrapper = new ResultWrapper<>();
        Page<ShopVideoInfo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = shopVideoInfoService.selectMyPage(page, platformVideoCategoryId, type);
//        page = shopVideoInfoService.selectPage(page, new EntityWrapper<ShopVideoInfo>()
//                .eq(StringUtils.isNotEmpty(platformVideoCategoryId), "platform_video_category_id", platformVideoCategoryId));
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "更新播放次数", notes = "更新播放次数")
    @PutMapping("/update/{id:\\d+}")
    @ApiImplicitParam(name = "id", value = "视频id", required = true, paramType = "path", dataType = "Integer")
    public ResultWrapper<Boolean> update(@PathVariable("id") Integer id) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<>();
        Boolean success = shopVideoInfoService.updatePlayTimes(id);
        resultWrapper.setSuccessful(success);
        resultWrapper.setResult(success);
        return resultWrapper;
    }

    @GetMapping(value = "/detail/{id:\\d+}")
    @ApiOperation(value = "获取资讯详情", notes = "获取资讯详情")
    @ApiImplicitParam(name = "id", value = "视频id", required = true, paramType = "path", dataType = "Integer")
    public ResultWrapper<ShopVideoInfoVo> detail(@PathVariable("id") Integer id) {
        ResultWrapper<ShopVideoInfoVo> resultWrapper = new ResultWrapper<>();
        ShopVideoInfoVo shopVideoInfo = shopVideoInfoService.selectMyShopVideo(id);
        resultWrapper.setResult(shopVideoInfo);
        return resultWrapper;
    }
}

