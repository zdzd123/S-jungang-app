package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.mapper.UserActivityCouponMapper;
import com.jgzy.core.personalCenter.service.IUserActivityCouponService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserActivityCoupon;
import com.jgzy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@Service
public class UserActivityCouponServiceImpl extends ServiceImpl<UserActivityCouponMapper, UserActivityCoupon> implements IUserActivityCouponService {
    @Autowired
    private UserActivityCouponMapper userActivityCouponMapper;

    @Override
    public Page<UserActivityCoupon> getUserActivityCoupon(Page<UserActivityCoupon> page, Integer type) {
        Integer id = UserUuidThreadLocal.get().getId();
        String now = DateUtil.getNow();
        return page.setRecords(userActivityCouponMapper.selectUserActivityCoupon(page, type, id, now));
    }
}
