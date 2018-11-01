package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.service.IUserGoodsCartService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserGoodsCart;
import com.jgzy.utils.DateUtil;
import com.jgzy.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@RefreshScope
@RestController
@RequestMapping("/api/userGoodsCart")
@Api(value = "购物车接口", description = "购物车接口")
public class UserGoodsCartController {
    @Autowired
    private IUserGoodsCartService userGoodsCartService;

    @ApiOperation(value = "新增购物车", notes = "新增购物车")
    @PostMapping(value = "/save")
    public ResultWrapper<UserGoodsCart> save(@RequestBody @Validated UserGoodsCart po) {
        ResultWrapper<UserGoodsCart> resultWrapper = new ResultWrapper<>();
        po.setCartUserInfoId(UserUuidThreadLocal.get().getId());
        po.setAddTime(DateUtil.getDate());

        EntityWrapper<UserGoodsCart> entityWrapper = new EntityWrapper<UserGoodsCart>();
        entityWrapper.eq("cart_user_info_id", po.getCartUserInfoId());
        entityWrapper.eq("goods_id", po.getGoodsId());
        // 查询商品是否存在
        int count = userGoodsCartService.selectCount(entityWrapper);
        boolean successful;
        if (count == 0){
            successful= userGoodsCartService.insert(po);
        }else {
            successful = userGoodsCartService.update(po, entityWrapper);
        }

        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "查询购物车信息（分页）", notes = "查询购物车信息（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserGoodsCart>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                       @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize) {
        ResultWrapper<Page<UserGoodsCart>> resultWrapper = new ResultWrapper<>();
        Page<UserGoodsCart> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        Integer id = UserUuidThreadLocal.get().getId();

        Set<String> orderByList = new HashSet<>();
        orderByList.add("id");
        page = userGoodsCartService.selectPage(page,
                new EntityWrapper<UserGoodsCart>()
                        .eq("cart_user_info_id", id)
                        .orderDesc(orderByList));

        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "更新指定ID的", notes = "更新指定ID的")
    @PutMapping("/update")
    public ResultWrapper<UserGoodsCart> update(@RequestBody @Validated UserGoodsCart po) {
        ResultWrapper<UserGoodsCart> resultWrapper = new ResultWrapper<>();
        Integer id = po.getId();
        boolean successful = false;
        if (ValidatorUtil.isNotNullOrEmpty(id)) {
            successful = userGoodsCartService.updateById(po);
        }
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "删除指定ID的用户", notes = "删除指定ID的用户")
    @ApiImplicitParam(name = "id",value = "用户ID",required = true,paramType = "path",dataType="Integer")
    @DeleteMapping({"/{id:\\d+}"})
    public ResultWrapper delete(@PathVariable("id") Integer id) {
        ResultWrapper resultWrapper = new ResultWrapper<>();
        boolean successful = false;
        if (ValidatorUtil.isNotNullOrEmpty(id)) {
            successful = userGoodsCartService.deleteById(id);
        }
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }
}

