package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.schedule.DealOverTimeOrderTasks;
import com.jgzy.core.shopOrder.service.IShopGoodsCommentService;
import com.jgzy.core.shopOrder.service.IShopGoodsOrderService;
import com.jgzy.core.shopOrder.vo.ShopGoodsCommentVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.ShopGoodsComment;
import com.jgzy.entity.po.ShopGoodsOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
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
 * @since 2018-10-19
 */
@RefreshScope
@RestController
@RequestMapping("/api/shopGoodsComment")
@Api(value = "商品评价接口", description = "商品评价接口")
public class ShopGoodsCommentController {
    @Autowired
    private IShopGoodsCommentService shopGoodsCommentService;
    @Autowired
    private IShopGoodsOrderService shopGoodsOrderService;

    @GetMapping(value = "/detail/{shopGoodsId:\\d+}")
    @ApiImplicitParam(name = "shopGoodsId", value = "商品ID", required = true, paramType = "path", dataType = "Integer")
    @ApiOperation(value = "商品详情页面展示用评价", notes = "商品详情页面展示用评价")
    public ResultWrapper<ShopGoodsCommentVo> detail(@PathVariable("shopGoodsId") Integer shopGoodsId) {
        ResultWrapper<ShopGoodsCommentVo> resultWrapper = new ResultWrapper<>();
        int count = shopGoodsCommentService.selectCount(
                new EntityWrapper<ShopGoodsComment>()
                        .eq("shop_goods_id", shopGoodsId));
        // 查询商品评价和用户信息
        Page<ShopGoodsCommentVo> page = new Page<>(1, 1);
        page = shopGoodsCommentService.selectMyGoodsCommentPage(page, shopGoodsId);
        if (!CollectionUtils.isEmpty(page.getRecords())) {
            ShopGoodsCommentVo shopGoodsCommentVo = page.getRecords().get(0);
            shopGoodsCommentVo.setCommentCount(count);
            resultWrapper.setResult(shopGoodsCommentVo);
        }
        return resultWrapper;
    }

    @ApiOperation(value = "用户评价（分页）", notes = "用户评价（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<ShopGoodsCommentVo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                            @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
                                                            @ApiParam(value = "商品ID") @RequestParam(required = false) Integer shopGoodsId) {
        ResultWrapper<Page<ShopGoodsCommentVo>> resultWrapper = new ResultWrapper<>();
        Page<ShopGoodsCommentVo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        // 查询商品评价和用户信息
        page = shopGoodsCommentService.selectMyGoodsCommentPage(page, shopGoodsId);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "新增商品评价", notes = "新增商品评价")
    @PostMapping(value = "/save")
    public ResultWrapper<ShopGoodsComment> save(@RequestBody @Validated List<ShopGoodsComment> poList) {
        ResultWrapper<ShopGoodsComment> resultWrapper = new ResultWrapper<>();
        String shopGoodsOrderId = poList.get(0).getShopGoodsOrderId();
        // 过滤订单
        ShopGoodsOrder order = shopGoodsOrderService.selectById(shopGoodsOrderId);
        if (!order.getOrderStatus().equals(BaseConstant.ORDER_STATUS_5)) {
            resultWrapper.setErrorMsg("该订单无法评价");
            return resultWrapper;
        }
        Date date = new Date();
        Integer id = UserUuidThreadLocal.get().getId();
        for (ShopGoodsComment shopGoodsComment : poList) {
            shopGoodsComment.setUserId(id);
            shopGoodsComment.setAppraiseTime(date);
        }
        boolean successful = shopGoodsCommentService.insertBatch(poList);
        ShopGoodsOrder shopGoodsOrder = new ShopGoodsOrder();
        shopGoodsOrder.setId(Integer.valueOf(shopGoodsOrderId));
        // 订单关闭
        shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_11);
        shopGoodsOrderService.updateById(shopGoodsOrder);
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }
}

