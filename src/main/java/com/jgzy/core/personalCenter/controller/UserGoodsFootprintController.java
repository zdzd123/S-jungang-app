package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.service.IUserGoodsFootprintService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.UserGoodsFootprint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@RefreshScope
@RestController
@RequestMapping("/api/userGoodsFootprint")
@Api(value = "用户足迹接口", description = "用户足迹接口")
public class UserGoodsFootprintController {
    @Autowired
    private IUserGoodsFootprintService userGoodsFootprintService;

    @ApiOperation(value = "获取个人足迹（分页）", notes = "获取个人足迹（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserGoodsFootprint>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                            @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize) {
        ResultWrapper<Page<UserGoodsFootprint>> resultWrapper = new ResultWrapper<>();
        Page<UserGoodsFootprint> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userGoodsFootprintService.getUserGoodsFootprint(page);
        resultWrapper.setResult(page);
        return resultWrapper;
    }
}

