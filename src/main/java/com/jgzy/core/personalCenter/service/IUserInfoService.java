package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.personalCenter.vo.PersonalCenterVo;
import com.jgzy.entity.po.UserInfo;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-15
 */
public interface IUserInfoService extends IService<UserInfo> {
    /**
     * 个人中心统计
     *
     * @return
     */
    PersonalCenterVo statistics();

    /**
     * 更新用户股权点数
     *
     * @param id
     * @return
     */
    boolean updateStockRightDiscount(Integer id, BigDecimal stockRight);

    /**
     * 更新佣金系数余额
     *
     * @param id
     * @return
     */
    boolean updateCommissionDiscount(Integer id, BigDecimal commission);
}
