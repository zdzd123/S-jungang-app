package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.entity.po.UserActivityCoupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@Repository
public interface UserActivityCouponMapper extends BaseMapper<UserActivityCoupon> {

    List<UserActivityCoupon> selectUserActivityCoupon(Page<UserActivityCoupon> page, @Param("type") Integer type,
                                                      @Param("id") Integer id, @Param("now") String now);
}
