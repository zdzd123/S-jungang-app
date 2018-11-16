package com.jgzy.core.personalCenter.controller;


import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.personalCenter.vo.PersonalCenterVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserInfo;
import com.jgzy.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-15
 */
@RefreshScope
@RestController
@RequestMapping("/api/userInfo")
@Api(value = "个人中心页面展示接口", description = "个人中心页面展示接口")
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping("/statistics")
    @ApiOperation(value = "个人中心页面展示数据统计信息", notes = "个人中心页面展示数据统计信息")
    public ResultWrapper<PersonalCenterVo> statistics(){
        ResultWrapper<PersonalCenterVo> resultWrapper = new ResultWrapper<>();

        PersonalCenterVo personalCenterVo = userInfoService.statistics();
        resultWrapper.setResult(personalCenterVo);

        return resultWrapper;
    }

    @GetMapping("/detail")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    public ResultWrapper<UserInfo> detail(){
        ResultWrapper<UserInfo> resultWrapper = new ResultWrapper<>();
        Integer id = UserUuidThreadLocal.get().getId();
        UserInfo userInfo = userInfoService.selectById(id);
        resultWrapper.setResult(userInfo);

        return resultWrapper;
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    public ResultWrapper<UserInfo> update(@RequestBody @Validated UserInfo po){
        ResultWrapper<UserInfo> resultWrapper = new ResultWrapper<>();
        po.setId(UserUuidThreadLocal.get().getId());
        boolean successful = userInfoService.updateById(po);
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }
}

