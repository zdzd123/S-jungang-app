package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.service.IUserGoodsFootprintService;
import com.jgzy.core.personalCenter.vo.UserGoodsFootprintVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.UserGoodsFootprint;
import com.jgzy.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
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

    @ApiOperation(value = "新增用户足迹", notes = "新增用户足迹")
    @PostMapping
    public ResultWrapper<UserGoodsFootprint> save(@RequestBody @Validated UserGoodsFootprint po){
        ResultWrapper<UserGoodsFootprint> resultWrapper = new ResultWrapper<>();
        boolean successful = userGoodsFootprintService.insertMyFoot(po);
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "获取个人足迹（分页）", notes = "获取个人足迹（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserGoodsFootprintVo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                              @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize) {
        ResultWrapper<Page<UserGoodsFootprintVo>> resultWrapper = new ResultWrapper<>();
        Page<UserGoodsFootprintVo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userGoodsFootprintService.getUserGoodsFootprint(page);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "删除指定ID的足迹", notes = "删除指定ID的足迹")
    @ApiImplicitParam(name = "id",value = "足迹ID",required = true, paramType = "path",dataType = "Integer")
    @DeleteMapping("/{id:\\d+}")
    public ResultWrapper<UserGoodsFootprint> delete(@PathVariable("id") Integer id){
        ResultWrapper<UserGoodsFootprint> resultWrapper = new ResultWrapper<>();
        UserGoodsFootprint userGoodsFootprint = new UserGoodsFootprint();
        userGoodsFootprint.setId(id);
        userGoodsFootprint.setUserDel(2);
        boolean b = userGoodsFootprintService.updateById(userGoodsFootprint);
        resultWrapper.setSuccessful(b);
        return resultWrapper;
    }
}

