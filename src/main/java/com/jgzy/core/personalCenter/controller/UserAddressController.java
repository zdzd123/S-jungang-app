package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.service.IUserAddressService;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.AreaInfo;
import com.jgzy.entity.po.CityInfo;
import com.jgzy.entity.po.ProvinceInfo;
import com.jgzy.entity.po.UserAddress;
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

    @GetMapping(value = "/detail/{id:\\d+}")
    @ApiOperation(value = "获取指定id收获地址", notes = "获取指定id收获地址")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Integer")
    public ResultWrapper<UserAddressVo> detail(@PathVariable("id") Integer id) {
        ResultWrapper<UserAddressVo> resultWrapper = new ResultWrapper<>();
        UserAddressVo userAddressVo = userAddressService.selectDetailById(id);
        resultWrapper.setResult(userAddressVo);
        return resultWrapper;
    }

    @ApiOperation(value = "获取所有收获地址", notes = "获取所有收获地址")
    @GetMapping(value = "list")
    public ResultWrapper<List<UserAddressVo>> listPage() {
        ResultWrapper<List<UserAddressVo>> resultWrapper = new ResultWrapper<>();
        List<UserAddressVo> userAddressList = userAddressService.selectMyList();
        resultWrapper.setResult(userAddressList);
        return resultWrapper;
    }

    @ApiOperation(value = "获取所有收获地址和发货地址", notes = "获取所有收获地址和发货地址")
    @GetMapping(value = "allList")
    public ResultWrapper<List<UserAddressVo>> allListPage() {
        ResultWrapper<List<UserAddressVo>> resultWrapper = new ResultWrapper<>();
        List<UserAddressVo> userAddressList = userAddressService.selectAllAddressPageList();
        resultWrapper.setResult(userAddressList);
        return resultWrapper;
    }

    @ApiOperation(value = "收货地址（分页）", notes = "收货地址（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserAddressVo>> shipListPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                           @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize) {
        ResultWrapper<Page<UserAddressVo>> resultWrapper = new ResultWrapper<>();
        Page<UserAddressVo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
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
            if (po.getIsDefault().equals(2)) {
                UserAddress my = new UserAddress();
                my.setIsDefault(1);
                successful = userAddressService.update(my, new EntityWrapper<UserAddress>()
                        .eq("create_user", UserUuidThreadLocal.get().getId()));
            }else {
                userAddressService.updateById(po);
            }
            if (successful) {
                successful = userAddressService.updateById(po);
            }
        }
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "新增收货地址", notes = "新增收货地址")
    @PostMapping(value = "/save")
    public ResultWrapper<UserAddress> save(@RequestBody @Validated UserAddress po) {
        ResultWrapper<UserAddress> resultWrapper = new ResultWrapper<>();
        po.setCreateUser(UserUuidThreadLocal.get().getId());
        po.setAddTime(new Date());
        if (po.getIsDefault() == null) {
            po.setIsDefault(1);
        }else if (po.getIsDefault() == 2){
            UserAddress my = new UserAddress();
            my.setIsDefault(1);
            userAddressService.update(my, new EntityWrapper<UserAddress>()
                    .eq("create_user", UserUuidThreadLocal.get().getId()));
        }
        boolean successful = userAddressService.insert(po);
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "删除指定ID的收货地址", notes = "删除指定ID的收货地址")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Integer")
    @DeleteMapping({"/delete/{id:\\d+}"})
    public ResultWrapper delete(@PathVariable("id") Integer id) {
        ResultWrapper resultWrapper = new ResultWrapper<>();
        boolean successful = false;
        if (ValidatorUtil.isNotNullOrEmpty(id)) {
            successful = userAddressService.deleteById(id);
        }
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }


}

