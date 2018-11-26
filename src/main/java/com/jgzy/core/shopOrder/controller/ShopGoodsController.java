package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.shopOrder.service.IOriginatorDiscountInfoService;
import com.jgzy.core.shopOrder.service.IOriginatorInfoService;
import com.jgzy.core.shopOrder.service.IShopGoodsService;
import com.jgzy.core.shopOrder.vo.CalcRateAmountVo;
import com.jgzy.core.shopOrder.vo.ShopGoodsVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.OriginatorDiscountInfo;
import com.jgzy.entity.po.OriginatorInfo;
import com.jgzy.entity.po.ShopGoods;
import com.jgzy.entity.po.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
@RequestMapping("/api/shopGoods")
@Api(value = "商品信息接口", description = "商品信息接口")
public class ShopGoodsController {
    @Autowired
    private IShopGoodsService shopGoodsService;
    @Autowired
    private IOriginatorInfoService originatorInfoService;
    @Autowired
    private IOriginatorDiscountInfoService originatorDiscountInfoService;

    @ApiOperation(value = "商品信息（分页）", notes = "商品信息（分页）")
    @PostMapping(value = "/page/list")
    public ResultWrapper<Page<ShopGoodsVo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                     @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
                                                     @RequestBody @Validated ShopGoods po) {
        ResultWrapper<Page<ShopGoodsVo>> resultWrapper = new ResultWrapper<>();
        Page<ShopGoodsVo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        EntityWrapper<ShopGoods> entityWrapper = new EntityWrapper<>();
        // 商品id
        if (po.getId() != null) {
            entityWrapper.eq("id", po.getId());
        }
        // 平台分类id
        if (po.getPlatformGoodsCategoryId() != null) {
            entityWrapper.eq("platform_goods_category_id", po.getPlatformGoodsCategoryId());
        }
        UserInfo userInfo = UserUuidThreadLocal.get();
        // 判断是否是合伙人
        int count = originatorInfoService.selectCount(
                new EntityWrapper<OriginatorInfo>()
                        .eq("user_id", userInfo.getId())
                        .eq("status", BaseConstant.ORIGINATOR_INFO_STATUS_0));
        // 特定商品-只有合伙人才能看到
        if (count == 0) {
            entityWrapper.eq("is_special", BaseConstant.NOT_SPECIAL);
            po.setIsSpecial(BaseConstant.NOT_SPECIAL);
        }
        // 状态(待上架=1|已上架=2|已下架=3)
        if (po.getStatus() != null) {
            entityWrapper.eq("status", po.getStatus());
        }
        // 商品名称
        if (po.getShopName() != null) {
            entityWrapper.like("shop_name", po.getShopName());
        }
        page = shopGoodsService.selectPageVo(page, entityWrapper);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "查询商品列表", notes = "查询商品列表")
    public ResultWrapper<List<ShopGoods>> list(@ApiParam(value = "商品id，按逗号分隔") @RequestParam String shopGoodsIds) {
        ResultWrapper<List<ShopGoods>> resultWrapper = new ResultWrapper<>();
        String[] split = shopGoodsIds.split(",");
        List<ShopGoods> shopGoodsList = shopGoodsService.selectList(new EntityWrapper<ShopGoods>()
                .in("id", split));
        resultWrapper.setResult(shopGoodsList);
        return resultWrapper;
    }

    @ApiOperation(value = "计算商品折扣", notes = "计算商品折扣")
    @GetMapping(value = "/calcAmount")
    public ResultWrapper<CalcRateAmountVo> calcAmount(@ApiParam(value = "总金额", required = true) @RequestParam BigDecimal amount) {
        // 判断是否是合伙人
        OriginatorInfo originatorInfo = originatorInfoService.selectOne(
                new EntityWrapper<OriginatorInfo>()
                        .eq("user_id", UserUuidThreadLocal.get().getId())
                        .eq("status", BaseConstant.ORIGINATOR_INFO_STATUS_0));
        List<OriginatorDiscountInfo> advanceRechargeInfos = originatorDiscountInfoService.selectList(
                new EntityWrapper<OriginatorDiscountInfo>()
                        .orderBy("discount_rate desc"));
        // 是合伙人并且能查看所有折扣
        int size = 0;
        for (int i = 0; i < advanceRechargeInfos.size(); i++) {

            if ((originatorInfo == null || originatorInfo.getDiscountStatus() != 1) && i > 2) {
                size = i - 1;
                break;
            }
            if (advanceRechargeInfos.get(i).getAmount().compareTo(amount) > 0) {
                size = i;
                break;
            }
        }
        // 需要折扣的金额
        BigDecimal totalAmount = advanceRechargeInfos.get(size).getAmount();
        // 剩余多少享受折扣
        BigDecimal remainAmount = totalAmount.subtract(amount);
        // 折扣
        BigDecimal discount = advanceRechargeInfos.get(size).getDiscountRate();
        CalcRateAmountVo singleCalcAmountVo = new CalcRateAmountVo();
        singleCalcAmountVo.setRemainAmount((remainAmount.compareTo(new BigDecimal(0)) <= 0) ? null : remainAmount);
        singleCalcAmountVo.setDiscountRate(discount);
        ResultWrapper<CalcRateAmountVo> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(singleCalcAmountVo);
        return resultWrapper;
    }

    @GetMapping(value = "/statistics")
    @ApiOperation(value = "统计普通商城商品数量", notes = "统计普通商城商品数量")
    public ResultWrapper<Integer> detail() {
        ResultWrapper<Integer> resultWrapper = new ResultWrapper<>();
        int count = shopGoodsService.selectCount(new EntityWrapper<ShopGoods>()
                .eq("is_special", BaseConstant.NOT_SPECIAL)
                .eq("status", 2));
        resultWrapper.setResult(count);
        return resultWrapper;
    }
}

