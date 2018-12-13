package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.service.IUserDistributionService;
import com.jgzy.core.personalCenter.vo.UserDistributionVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserDistribution;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Date;

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

    @ApiOperation(value = "合伙人列表(分页)", notes = "合伙人列表(分页)")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserDistributionVo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                            @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize){
        ResultWrapper<Page<UserDistributionVo>> resultWrapper = new ResultWrapper<>();
        Page<UserDistributionVo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userDistributionService.getMyUserDistributionList(page);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @GetMapping(value = "/save/{parentId}")
    @ApiOperation(value = "绑定用户关系", notes = "绑定用户关系")
    @ApiImplicitParam(name = "parentId",value = "父级ID", paramType = "path",dataType = "String")
    public ResultWrapper<UserDistribution> save(@PathVariable("parentId") String parentId) {
        ResultWrapper<UserDistribution> resultWrapper = new ResultWrapper<>();

        Integer id = UserUuidThreadLocal.get().getId();
        if(parentId.equals(String.valueOf(id))){
            resultWrapper.setErrorMsg("不可绑定自身");
            resultWrapper.setSuccessful(false);
            return resultWrapper;
        }
        UserDistribution userDistribution = userDistributionService.selectOne(new EntityWrapper<UserDistribution>()
                .eq("user_id", id));
        Integer myParentId;
        if (parentId.equals("null")){
            myParentId = null;
        }else {
            myParentId = Integer.valueOf(parentId);
        }
        if (userDistribution == null){
            // 绑定成为下级
            userDistribution = new UserDistribution();
            userDistribution.setParentId(myParentId);
            userDistribution.setUserId(id);
            userDistribution.setAddTime(new Date());
            boolean insert = userDistributionService.insert(userDistribution);
            resultWrapper.setSuccessful(insert);
        }

        return resultWrapper;
    }

}

