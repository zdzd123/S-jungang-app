package com.jgzy.core.personalCenter.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IUserActivityCouponService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserActivityCoupon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
@RequestMapping("/api/userActivityCoupon")
@Api(value = "用户优惠券接口", description = "用户优惠券接口")
public class UserActivityCouponController {
    @Autowired
    private IUserActivityCouponService userActivityCouponService;

    @ApiOperation(value = "查询用户优惠券（分页）", notes = "查询用户优惠券（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserActivityCoupon>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                            @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
                                                            @ApiParam(value = "优惠券状态 null-所有,1-可使用,2-已使用,3-已过期") @RequestParam(required = false) Integer type) {
        ResultWrapper<Page<UserActivityCoupon>> resultWrapper = new ResultWrapper<>();
        Page<UserActivityCoupon> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userActivityCouponService.getUserActivityCoupon(page, type);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @ApiOperation(value = "获取优惠券列表", notes = "获取优惠券列表")
    @GetMapping(value = "/list")
    public ResultWrapper<List<UserActivityCoupon>> list(@ApiParam(value = "支付金额") @RequestParam(required = false) BigDecimal amount) {
        ResultWrapper<List<UserActivityCoupon>> resultWrapper = new ResultWrapper<>();

        Date date = new Date();
        List<UserActivityCoupon> userActivityCouponList = userActivityCouponService.selectList(
                new EntityWrapper<UserActivityCoupon>()
                        .eq("coupon_user_info_id", UserUuidThreadLocal.get().getId())
                        .eq("coupon_state", BaseConstant.COUPON_STATE_1)
                        .le("meet_amount", amount)
                        .ge("valid_date_end", date)
                        .le("valid_date_begin", date));
        resultWrapper.setResult(userActivityCouponList);

        return resultWrapper;
    }
}

