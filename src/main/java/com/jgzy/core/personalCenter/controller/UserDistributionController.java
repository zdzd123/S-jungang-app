package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.service.IUserDistributionService;
import com.jgzy.core.personalCenter.vo.UserDistributionVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.UserDistribution;
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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-26
 */
@RefreshScope
@RestController
@RequestMapping("/api/userDistribution")
@Api(value = "合伙人列表接口", description = "合伙人列表接口")
public class UserDistributionController {
    @Autowired
    private IUserDistributionService userDistributionService;

    @ApiOperation(value = "(分页)", notes = "(分页)")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserDistributionVo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                            @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize){
        ResultWrapper<Page<UserDistributionVo>> resultWrapper = new ResultWrapper<>();
        Page<UserDistributionVo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userDistributionService.getMyUserDistributionList(page);
        resultWrapper.setResult(page);
        return resultWrapper;
    }
}

