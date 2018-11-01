package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.shopOrder.service.IShopGoodsCommentService;
import com.jgzy.core.shopOrder.service.IShopGoodsOrderService;
import com.jgzy.core.shopOrder.vo.ShopGoodsCommentVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.ShopGoodsComment;
import com.jgzy.entity.po.ShopGoodsOrder;
import com.jgzy.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Collections;
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

    @GetMapping(value = "/detail")
    @ApiOperation(value = "商品详情页面展示用评价", notes = "商品详情页面展示用评价")
    public ResultWrapper<ShopGoodsCommentVo> detail() {
        ResultWrapper<ShopGoodsCommentVo> resultWrapper = new ResultWrapper<>();
        ShopGoodsCommentVo shopGoodsCommentVo = new ShopGoodsCommentVo();

        EntityWrapper<ShopGoodsComment> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("status", BaseConstant.COMMENT_STATUS_2);
        int count = shopGoodsCommentService.selectCount(entityWrapper);
        List<ShopGoodsComment> shopGoodsCommentList = shopGoodsCommentService.selectList(entityWrapper.last("LIMIT 1"));
        if (!CollectionUtils.isEmpty(shopGoodsCommentList) && count != 0) {
            BeanUtils.copyProperties(shopGoodsCommentList.get(0), shopGoodsCommentVo);
            shopGoodsCommentVo.setCommentCount(count);
            resultWrapper.setResult(shopGoodsCommentVo);
        }

        return resultWrapper;
    }

    @ApiOperation(value = "用户评价（分页）", notes = "用户评价（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<ShopGoodsComment>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                          @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize) {
        ResultWrapper<Page<ShopGoodsComment>> resultWrapper = new ResultWrapper<>();
        Page<ShopGoodsComment> page = new Page<ShopGoodsComment>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = shopGoodsCommentService.selectPage(page,
                new EntityWrapper<ShopGoodsComment>()
                        .eq("status", BaseConstant.COMMENT_STATUS_2));
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "新增商品评价", notes = "新增商品评价")
    @PostMapping(value = "/save")
    public ResultWrapper<ShopGoodsComment> save(@RequestBody @Validated ShopGoodsComment po){
        ResultWrapper<ShopGoodsComment> resultWrapper = new ResultWrapper<>();
        boolean successful = shopGoodsCommentService.insert(po);
        ShopGoodsOrder shopGoodsOrder = new ShopGoodsOrder();
        shopGoodsOrder.setId(po.getShopGoodsId());
        shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_6);
        // 更新商品为已评价
        boolean update = shopGoodsOrderService.updateById(shopGoodsOrder);
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }
}

