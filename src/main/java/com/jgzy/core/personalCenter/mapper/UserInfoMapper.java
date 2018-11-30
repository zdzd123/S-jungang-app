package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.core.personalCenter.vo.PersonalCenterVo;
import com.jgzy.core.personalCenter.vo.UserInfoVo;
import com.jgzy.entity.po.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-15
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    PersonalCenterVo statistics(@Param("id") Integer id, @Param("now") String now);

    /**
     * 更新股权基金
     *
     * @param id
     * @param stockRight
     * @return
     */
    boolean updateStockRight(@Param("id") Integer id, @Param("stockRight") BigDecimal stockRight);

    /**
     * 更新佣金
     *
     * @param id
     * @param commission
     * @return
     */
    boolean updateCommissionDiscount(@Param("id") Integer id, @Param("commission") BigDecimal commission);

    /**
     * 查询用户信息和等级
     *
     * @param id 用户id
     * @return 用户信息
     */
    UserInfo selectMyUserLevelById(@Param("id") Integer id);

    /**
     * 提现冻结
     *
     * @param id          用户id
     * @param withdrawNum 提现金额
     * @return flag
     */
    int withDrawAmount(@Param("id") Integer id, @Param("withdrawNum") BigDecimal withdrawNum);

    /**
     * 查询用户详细详细
     *
     * @param id 用户id
     * @return 用户详细
     */
    UserInfoVo selectMyUserJoinOriginatorInfo(@Param("id") Integer id);

    /**
     * 更新用户金额
     *
     * @param myUser 用户
     * @return flag
     */
    boolean updateMyBalance(UserInfo myUser);
}
