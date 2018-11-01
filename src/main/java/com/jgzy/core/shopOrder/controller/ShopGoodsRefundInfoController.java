package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.core.shopOrder.service.IShopGoodsRefundInfoService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.po.ShopGoodsRefundInfo;
import com.jgzy.utils.DateUtil;
import com.jgzy.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-23
 */
@RefreshScope
@RestController
@RequestMapping("/api/shopGoodsRefundInfo")
@Api(value = "退款退货接口", description = "退款退货接口")
public class ShopGoodsRefundInfoController {
    @Autowired
    private IShopGoodsRefundInfoService shopGoodsRefundInfoService;

    @ApiOperation(value = "退款退货新增", notes = "退款退货新增")
    @PostMapping(value = "/save")
    public ResultWrapper<ShopGoodsRefundInfo> save(@RequestBody @Validated ShopGoodsRefundInfo po) {
        ResultWrapper<ShopGoodsRefundInfo> resultWrapper = new ResultWrapper<>();
        po.setSubmitTime(DateUtil.getDate());
        boolean successful = shopGoodsRefundInfoService.insert(po);
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @GetMapping(value = "/detail/{orderNo:\\d+}")
    @ApiOperation(value = "查看退款退货信息", notes = "查看退款退货信息")
    @ApiImplicitParam(name = "orderNo", value = "订单ID", required = true, dataType = "Integer", paramType = "path")
    public ResultWrapper<ShopGoodsRefundInfo> detail(@PathVariable("orderNo") Integer orderNo) {
        ResultWrapper<ShopGoodsRefundInfo> resultWrapper = new ResultWrapper<>();
        List<ShopGoodsRefundInfo> shopGoodsRefundInfoList = shopGoodsRefundInfoService.selectList(
                new EntityWrapper<ShopGoodsRefundInfo>()
                        .eq("order_no", orderNo));
        if (!CollectionUtils.isEmpty(shopGoodsRefundInfoList)){
            resultWrapper.setResult(shopGoodsRefundInfoList.get(0));
        }
        return resultWrapper;
    }
}

