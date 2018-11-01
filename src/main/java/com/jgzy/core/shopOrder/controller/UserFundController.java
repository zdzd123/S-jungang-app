package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.UserFund;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-22
 */
@RefreshScope
@RestController
@RequestMapping("/api/userFund")
@Api(value = "用户流水", description = "用户流水")
public class UserFundController {
    @Autowired
    private IUserFundService userFundService;

    @ApiOperation(value = "查询流水明细（分页）", notes = "查询流水明细（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserFund>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                  @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
                                                  @ApiParam(value = "开始时间") @RequestParam(required = false) String beginDate,
                                                  @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime,
                                                  @ApiParam(value = "支付类型 1-积分，2-余额，3-冻结，4-微信，5-支付宝，6-提现，7-荣誉值，8-权额") @RequestParam(required = false) String accountType) {
        ResultWrapper<Page<UserFund>> resultWrapper = new ResultWrapper<>();
        Page<UserFund> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userFundService.selectPage(page, new EntityWrapper<UserFund>()
                .ge(!StringUtils.isEmpty(beginDate), "trade_time", beginDate)
                .le(!StringUtils.isEmpty(endTime), "trade_time", endTime)
                .eq(!StringUtils.isEmpty(accountType), "account_type", accountType));
        resultWrapper.setResult(page);
        return resultWrapper;
    }
}

