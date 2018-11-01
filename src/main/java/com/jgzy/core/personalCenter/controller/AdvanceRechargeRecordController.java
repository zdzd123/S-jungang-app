package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.core.personalCenter.service.IAdvanceRechargeInfoService;
import com.jgzy.core.personalCenter.service.IAdvanceRechargeRecordService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.AdvanceRechargeInfo;
import com.jgzy.entity.po.AdvanceRechargeRecord;
import com.jgzy.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Date;
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
@RequestMapping("/api/advanceRechargeRecord")
@Api(value = "用户权额接口", description = "用户权额接口")
public class AdvanceRechargeRecordController {
    @Autowired
    private IAdvanceRechargeInfoService advanceRechargeInfoService;
    @Autowired
    private IAdvanceRechargeRecordService advanceRechargeRecordService;

    @GetMapping(value = "/detail")
    @ApiOperation(value = "查询用户权额", notes = "查询用户权额")
    public ResultWrapper<List<AdvanceRechargeRecord>> detail() {
        ResultWrapper<List<AdvanceRechargeRecord>> resultWrapper = new ResultWrapper<>();
        List<AdvanceRechargeRecord> advanceRechargeRecordList = advanceRechargeRecordService.selectList(
                new EntityWrapper<AdvanceRechargeRecord>()
                        .eq("user_id", UserUuidThreadLocal.get().getId())
                        .ne("amount", 0)
                        .orderBy("level_id"));
        resultWrapper.setResult(advanceRechargeRecordList);
        return resultWrapper;
    }
}

