package com.jgzy.core.shopOrder.controller;


import com.jgzy.core.shopOrder.service.ISinglePageService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.SinglePage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-23
 */
@RefreshScope
@RestController
@RequestMapping("/api/singlePage")
@Api(value = "单页信息接口",description = "单页信息接口")
public class SinglePageController {
    @Autowired
    private ISinglePageService singlePageService;

    @GetMapping(value = "/detail/{id:\\d+}")
    @ApiOperation(value = "查询信息单页", notes = "查询信息单页")
    @ApiImplicitParam(name = "id",value = "用户ID",required = true, paramType = "path",dataType = "Integer")
    public ResultWrapper<SinglePage> detail(@PathVariable("id") Integer id) {
        ResultWrapper<SinglePage> resultWrapper = new ResultWrapper<>();
        SinglePage singlePage = singlePageService.selectById(id);
        resultWrapper.setResult(singlePage);
        return resultWrapper;
    }
}

