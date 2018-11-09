package com.jgzy.core.personalCenter.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.service.IUserAddressShipService;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserAddressShip;
import com.jgzy.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/api/userAddressShip")
@Api(value = "发货地址接口", description = "发货地址接口")
public class UserAddressShipController {
    @Autowired
    private IUserAddressShipService userAddressShipService;

    @GetMapping(value = "/detail/{id:\\d+}")
    @ApiOperation(value = "获取默认发货地址", notes = "获取默认发货地址")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Integer")
    public ResultWrapper<UserAddressVo> detail(@PathVariable("id") Integer id) {
        ResultWrapper<UserAddressVo> resultWrapper = new ResultWrapper<>();
        UserAddressVo userAddressVo = userAddressShipService.selectDetailById(id);
        resultWrapper.setResult(userAddressVo);
        return resultWrapper;
    }

    @ApiOperation(value = "获取所有发货地址", notes = "获取所有发货地址")
    @GetMapping(value = "/list")
    public ResultWrapper<List<UserAddressVo>> listPage(){
        ResultWrapper<List<UserAddressVo>> resultWrapper = new ResultWrapper<>();
        List<UserAddressVo> userAddressVoList = userAddressShipService.selectMyList();
        resultWrapper.setResult(userAddressVoList);
        return resultWrapper;
    }

    @ApiOperation(value = "发货地址（分页）", notes = "发货地址（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserAddressShip>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                         @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize) {
        ResultWrapper<Page<UserAddressShip>> resultWrapper = new ResultWrapper<>();
        Page<UserAddressShip> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userAddressShipService.getUserAddressShip(page);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "更新指定ID的发货地址", notes = "更新指定ID的发货地址")
    @PutMapping("/update")
    public ResultWrapper<UserAddressShip> update(@RequestBody @Validated UserAddressShip po) {
        ResultWrapper<UserAddressShip> resultWrapper = new ResultWrapper<>();
        Integer id = po.getId();
        boolean successful = false;
        if (ValidatorUtil.isNotNullOrEmpty(id)) {
            successful = userAddressShipService.updateById(po);
        }
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "新增发货地址", notes = "新增发货地址")
    @PostMapping(value = "/save")
    public ResultWrapper<UserAddressShip> save(@RequestBody @Validated UserAddressShip po) {
        ResultWrapper<UserAddressShip> resultWrapper = new ResultWrapper<>();
        po.setCreateUser(UserUuidThreadLocal.get().getId());
        po.setAddTime(new Date());
        boolean successful = userAddressShipService.insert(po);
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "删除指定ID的发货地址", notes = "删除指定ID的发货地址")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Integer")
    @DeleteMapping({"/delete/{id:\\d+}"})
    public ResultWrapper delete(@PathVariable("id") Integer id) {
        ResultWrapper resultWrapper = new ResultWrapper<>();
        boolean successful;
        if (ValidatorUtil.isNotNullOrEmpty(id)) {
            successful = userAddressShipService.deleteById(id);
        } else {
            successful = false;
        }
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }
}
