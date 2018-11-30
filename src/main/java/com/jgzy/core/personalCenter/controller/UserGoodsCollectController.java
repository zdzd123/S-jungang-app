package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.service.IUserGoodsCollectService;
import com.jgzy.core.personalCenter.vo.UserGoodsCollectionVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserGoodsCollect;
import com.jgzy.utils.DateUtil;
import com.jgzy.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
@RequestMapping("/api/userGoodsCollect")
@Api(value = "个人收藏接口", description = "个人收藏接口")
public class UserGoodsCollectController {
    @Autowired
    private IUserGoodsCollectService userGoodsCollectService;

    @ApiOperation(value = "新增个人收藏", notes = "新增个人收藏")
    @PostMapping(value = "/save")
    public ResultWrapper<UserGoodsCollect> save(@RequestBody @Validated UserGoodsCollect po) {
        ResultWrapper<UserGoodsCollect> resultWrapper = new ResultWrapper<>();
        UserGoodsCollect userGoodsCollect = userGoodsCollectService.selectOne(new EntityWrapper<UserGoodsCollect>()
                .eq("goods_id", po.getGoodsId())
                .eq("collect_user_info_id", UserUuidThreadLocal.get().getId()));
        if (userGoodsCollect == null) {
            po.setCollectUserInfoId(UserUuidThreadLocal.get().getId());
            po.setCollectTime(DateUtil.getDate());
            boolean successful = userGoodsCollectService.insert(po);
            resultWrapper.setSuccessful(successful);
        } else {
            userGoodsCollect.setUserDel(1);
            userGoodsCollect.setCollectTime(new Date());
            boolean b = userGoodsCollectService.updateById(userGoodsCollect);
            resultWrapper.setSuccessful(b);
        }
        return resultWrapper;
    }

    @ApiOperation(value = "查询个人收藏（分页）", notes = "查询个人收藏（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserGoodsCollectionVo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                               @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize) {
        ResultWrapper<Page<UserGoodsCollectionVo>> resultWrapper = new ResultWrapper<>();
        Page<UserGoodsCollectionVo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userGoodsCollectService.getUserGoodsCollectByUserId(page);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @GetMapping(value = "/checkGoodsCollect")
    @ApiOperation(value = "查看商品是否收藏", notes = "查看商品是否收藏")
    public ResultWrapper<Boolean> checkGoodsCollect(@ApiParam(value = "商品id", required = true) @RequestParam Integer goodsId) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<>();
        int i = userGoodsCollectService.selectCount(new EntityWrapper<UserGoodsCollect>()
                .eq("goods_id", goodsId)
                .eq("collect_user_info_id", UserUuidThreadLocal.get().getId())
                .eq("user_del", 1));
        resultWrapper.setResult(true);
        if (i == 0) {
            resultWrapper.setResult(false);
        }
        return resultWrapper;
    }

    @ApiOperation(value = "删除指定ID的收藏", notes = "删除指定ID的收藏")
    @ApiImplicitParam(name = "id", value = "收藏ID", required = true, paramType = "path", dataType = "Integer")
    @DeleteMapping("/{id:\\d+}")
    public ResultWrapper<UserGoodsCollect> delete(@PathVariable("id") Integer id) {
        ResultWrapper<UserGoodsCollect> resultWrapper = new ResultWrapper<>();
        boolean successful = false;
        if (ValidatorUtil.isNotNullOrEmpty(id)) {
            UserGoodsCollect userGoodsCollect = userGoodsCollectService.selectOne(new EntityWrapper<UserGoodsCollect>()
                    .eq("goods_id", id)
                    .eq("collect_user_info_id", UserUuidThreadLocal.get().getId()));
            if (userGoodsCollect == null) {
                throw new OptimisticLockingFailureException("不存在该商品收藏");
            }
            userGoodsCollect.setUserDel(2);
            successful = userGoodsCollectService.updateById(userGoodsCollect);
        }
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }
}

