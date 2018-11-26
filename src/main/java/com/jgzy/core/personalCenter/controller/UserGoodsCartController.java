package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.service.IUserGoodsCartService;
import com.jgzy.core.personalCenter.vo.UserGoodsCartVo;
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
        po.setLiveId(1);
        po.setGoodsCartCounts(1);

        EntityWrapper<UserGoodsCart> entityWrapper = new EntityWrapper<UserGoodsCart>();
        entityWrapper.eq("cart_user_info_id", po.getCartUserInfoId());
        entityWrapper.eq("goods_id", po.getGoodsId());
        // 查询商品是否存在
        int count = userGoodsCartService.selectCount(entityWrapper);
        boolean successful;
        if (count == 0) {
            successful = userGoodsCartService.insert(po);
        } else {
            successful = userGoodsCartService.update(po, entityWrapper);
        }

        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "查询购物车信息（分页）", notes = "查询购物车信息（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserGoodsCartVo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                         @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize) {
        ResultWrapper<Page<UserGoodsCartVo>> resultWrapper = new ResultWrapper<>();
        Page<UserGoodsCartVo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userGoodsCartService.selectMyUserCartPage(page);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "更新指定ID的购物车", notes = "更新指定ID的购物车")
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

    @ApiOperation(value = "删除指定ID的购物车", notes = "删除指定ID的购物车")
    @ApiImplicitParam(name = "id", value = "购物车ID", required = true, paramType = "path", dataType = "Integer")
    @DeleteMapping({"/{id:\\d+}"})
    public ResultWrapper<UserGoodsCart> delete(@PathVariable("id") Integer id) {
        ResultWrapper<UserGoodsCart> resultWrapper = new ResultWrapper<>();
        UserGoodsCart userGoodsCart = new UserGoodsCart();
        userGoodsCart.setId(id);
        userGoodsCart.setLiveId(2);
        boolean successful = userGoodsCartService.updateById(userGoodsCart);
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @ApiOperation(value = "获取购物车数量", notes = "获取购物车数量")
    @GetMapping(value = "/getCount")
    public ResultWrapper<Integer> getCount() {
        ResultWrapper<Integer> resultWrapper = new ResultWrapper<>();
        int count = userGoodsCartService.selectCount(new EntityWrapper<UserGoodsCart>()
                .eq("cart_user_info_id", UserUuidThreadLocal.get().getId())
                .eq("live_id", 1));
        resultWrapper.setResult(count);
        return resultWrapper;
    }
}

