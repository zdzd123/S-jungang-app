package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.service.IUserAddressService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.*;
import com.jgzy.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/api/userAddress")
@Api(value = "收货地址接口", description = "收货地址接口")
public class UserAddressController {
    @Autowired
    private IUserAddressService userAddressService;

    @GetMapping(value = "/constant/province/list")
    @ApiOperation(value = "省常量", notes = "省常量")
    public ResultWrapper<List<ProvinceInfo>> provinceList(@ApiParam(value = "id null-所有") @RequestParam(required = false) Integer id) {
        ResultWrapper<List<ProvinceInfo>> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(userAddressService.getProvince(id));
        return resultWrapper;
    }

    @GetMapping(value = "/constant/city/list")
    @ApiOperation(value = "市常量", notes = "市常量")
    public ResultWrapper<List<CityInfo>> cityList(@ApiParam(value = "id null-所有") @RequestParam(required = false) Integer id,
                                                  @ApiParam(value = "provinceId null-所有") @RequestParam(required = false) Integer provinceId) {
        ResultWrapper<List<CityInfo>> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(userAddressService.getCity(id, provinceId));
        return resultWrapper;
    }

    @GetMapping(value = "/constant/area/list")
    @ApiOperation(value = "区常量", notes = "区常量")
    public ResultWrapper<List<AreaInfo>> areaList(@ApiParam(value = "id null-所有") @RequestParam(required = false) Integer id,
                                                  @ApiParam(value = "cityId null-所有") @RequestParam(required = false) Integer cityId) {
        ResultWrapper<List<AreaInfo>> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(userAddressService.getArea(id, cityId));
        return resultWrapper;
    }

    @ApiOperation(value = "收货地址（分页）", notes = "收货地址（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserAddress>> shipListPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                             @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize) {
        ResultWrapper<Page<UserAddress>> resultWrapper = new ResultWrapper<>();
        Page<UserAddress> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userAddressService.getUserAddress(page);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "更新指定ID的收货地址", notes = "更新指定ID的收货地址")
    @PutMapping("/update")
    public ResultWrapper<UserAddress> shipUpdate(@RequestBody @Validated UserAddress po) {
        ResultWrapper<UserAddress> resultWrapper = new ResultWrapper<>();
        Integer id = po.getId();
        boolean successful = false;
        if (ValidatorUtil.isNotNullOrEmpty(id)) {
            successful = userAddressService.updateById(po);
        }
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "新增收货地址", notes = "新增收货地址")
    @PostMapping(value = "/save")
    public ResultWrapper<UserAddress> save(@RequestBody @Validated UserAddress po) {
        ResultWrapper<UserAddress> resultWrapper = new ResultWrapper<>();
        Integer id = po.getId();
        boolean successful = false;
        if (ValidatorUtil.isNotNullOrEmpty(id)) {
            successful = userAddressService.insert(po);
        }
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "删除指定ID的收货地址", notes = "删除指定ID的收货地址")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Integer")
    @DeleteMapping({"/delete/{id:\\d+}"})
    public ResultWrapper delete(@PathVariable("id") Integer id) {
        ResultWrapper resultWrapper = new ResultWrapper<>();
        boolean successful;
        if (ValidatorUtil.isNotNullOrEmpty(id)) {
            successful = userAddressService.deleteById(id);
        } else {
            successful = false;
        }
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

}

