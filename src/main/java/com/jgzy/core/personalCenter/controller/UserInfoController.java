package com.jgzy.core.personalCenter.controller;


import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.personalCenter.vo.PersonalCenterVo;
import com.jgzy.core.personalCenter.vo.UserInfoVo;
import com.jgzy.core.shopOrder.service.IShopGoodsOrderService;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderStatisticVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private IShopGoodsOrderService shopGoodsOrderService;

    @GetMapping("/statistics")
    @ApiOperation(value = "个人中心页面展示数据统计信息", notes = "个人中心页面展示数据统计信息")
    public ResultWrapper<PersonalCenterVo> statistics(){
        ResultWrapper<PersonalCenterVo> resultWrapper = new ResultWrapper<>();
        // 统计用户收藏、足迹和抵用券
        PersonalCenterVo personalCenterVo = userInfoService.statistics();
        // 统计订单数量
        List<ShopGoodsOrderStatisticVo> shopGoodsOrderStatisticVoList = shopGoodsOrderService.statistics();
        personalCenterVo.setShopGoodsOrderStatisticVolist(shopGoodsOrderStatisticVoList);
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

    @GetMapping(value = "/getOriginatorUser")
    @ApiOperation(value = "查询用户合伙人信息", notes = "查询用户合伙人信息")
    public ResultWrapper<UserInfoVo> originatorUser() {
        ResultWrapper<UserInfoVo> resultWrapper = new ResultWrapper<>();
        UserInfoVo userInfoVo = userInfoService.selectMyUserJoinOriginatorInfo(UserUuidThreadLocal.get().getId());
        resultWrapper.setResult(userInfoVo);
        return resultWrapper;
    }
}

