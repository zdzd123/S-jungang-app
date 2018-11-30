package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.core.personalCenter.service.IAdvanceRechargeInfoService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.AdvanceRechargeInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-24
 */
@RefreshScope
@RestController
@RequestMapping("/api/advanceRechargeInfo")
@Api(value = "权额折扣接口", description = "权额折扣接口")
public class AdvanceRechargeInfoController {
    @Autowired
    private IAdvanceRechargeInfoService advanceRechargeInfoService;

    @GetMapping(value = "/detail")
    @ApiOperation(value = "查询权额折扣", notes = "查询权额折扣")
    public ResultWrapper<List<AdvanceRechargeInfo>> detail() {
        ResultWrapper<List<AdvanceRechargeInfo>> resultWrapper = new ResultWrapper<>();
        List<AdvanceRechargeInfo> advanceRechargeInfos = advanceRechargeInfoService.selectList(
                new EntityWrapper<AdvanceRechargeInfo>().orderBy("level_id ASC"));
        resultWrapper.setResult(advanceRechargeInfos);
        return resultWrapper;
    }
}

