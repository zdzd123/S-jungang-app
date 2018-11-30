package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.personalCenter.vo.PersonalCenterVo;
import com.jgzy.core.personalCenter.vo.UserInfoVo;
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

    /**
     * 查询用户信息和用户等级
     *
     * @param id 用户id
     * @return 用户信息
     */
    UserInfo selectMyUserLevelById(Integer id);

    /**
     * 提现冻结金额
     *
     * @param id          用户id
     * @param withdrawNum 提现金额
     * @return flag
     */
    boolean withDrawAmount(Integer id, BigDecimal withdrawNum);

    /**
     * 查询用户详细信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    UserInfoVo selectMyUserJoinOriginatorInfo(Integer id);

    /**
     * 更新用户金额
     *
     * @param myUser user
     * @return flag
     */
    boolean updateMyBalance(UserInfo myUser);
}
