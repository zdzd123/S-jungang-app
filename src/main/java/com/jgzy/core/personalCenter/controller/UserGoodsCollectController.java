package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.service.IUserGoodsCollectService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserGoodsCollect;
import com.jgzy.utils.DateUtil;
import com.jgzy.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@RefreshScope
@RestController
@RequestMapping("/api/userGoodsCollect")
@Api(value = "个人收藏接口", description = "个人收藏接口")
public class UserGoodsCollectController {
    @Autowired
    private IUserGoodsCollectService userGoodsCollectService;

    @ApiOperation(value = "新增个人收藏", notes = "新增个人收藏")
    @PostMapping(value = "/save")
    public ResultWrapper<UserGoodsCollect> save(@RequestBody @Validated UserGoodsCollect po){
        ResultWrapper<UserGoodsCollect> resultWrapper = new ResultWrapper<>();
        po.setCollectUserInfoId(UserUuidThreadLocal.get().getId());
        po.setCollectTime(DateUtil.getDate());
        boolean successful = userGoodsCollectService.insert(po);
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "查询个人收藏（分页）", notes = "查询个人收藏（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserGoodsCollect>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
            @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize) {
        ResultWrapper<Page<UserGoodsCollect>> resultWrapper = new ResultWrapper<>();
        Page<UserGoodsCollect> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userGoodsCollectService.getUserGoodsCollectByUserId(page);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @GetMapping(value = "/checkGoodsCollect")
    @ApiOperation(value = "查看商品是否收藏", notes = "查看商品是否收藏")
    public ResultWrapper<Boolean> checkGoodsCollect(@ApiParam(value = "商品id", required = true) @RequestParam Integer goodsId){
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<>();
        int i = userGoodsCollectService.selectCount(new EntityWrapper<UserGoodsCollect>().eq("goods_id", goodsId)
                .eq("collect_user_info_id", UserUuidThreadLocal.get().getId()));
        resultWrapper.setResult(true);
        if (i == 0){
            resultWrapper.setResult(false);
        }
        return resultWrapper;
    }
}

