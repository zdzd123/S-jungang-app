package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.entity.po.UserActivityCoupon;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
public interface IUserActivityCouponService extends IService<UserActivityCoupon> {

    /**
     * 查询用户优惠券
     *
     * @param page
     * @param type
     * @return
     */
    Page<UserActivityCoupon> getUserActivityCoupon(Page<UserActivityCoupon> page, Integer type);
}
