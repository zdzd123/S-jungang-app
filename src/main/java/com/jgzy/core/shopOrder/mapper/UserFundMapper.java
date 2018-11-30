package com.jgzy.core.shopOrder.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.vo.MyTeamDetailVo;
import com.jgzy.core.personalCenter.vo.MyTeamVo;
import com.jgzy.entity.po.UserFund;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-22
 */
@Repository
public interface UserFundMapper extends BaseMapper<UserFund> {

    void insertUserFund(UserFund userFund);

    MyTeamVo selectSumIncreaseMoney(@Param("id") Integer id);

    /**
     * 查询统计
     *
     * @param idList 用户id
     * @param status
     * @return 用户统计
     */
    List<MyTeamDetailVo> selectUserSumIncreaseMoney(Page<MyTeamDetailVo> page, @Param("list") List<String> idList, @Param("status") String status);

    /**
     * 按月份查询统计
     *
     * @param idList     用户id
     * @param monthBegin 开始时间
     * @param monthEnd   结束时间
     * @param status
     * @return 用户统计
     */
    List<MyTeamDetailVo> selectUserSumIncreaseMoneyList(@Param("list") List<String> idList, @Param("begin") String monthBegin,
                                                        @Param("end") String monthEnd, @Param("status") String status);

    /**
     * 统计佣金
     *
     * @param id     用户id
     * @param status
     * @return 佣金
     */
    MyTeamVo selectStatisticsIncreaseMoney(@Param("id") Integer id, @Param("status") String status, @Param("begin") String monthBegin,
                                           @Param("end") String monthEnd);

    /**
     * 我的团队列表
     *
     * @param id id
     * @return 我的团队
     */
    String getDistributionList(@Param("id") Integer id);

    /**
     * 查询我的累积收益
     *
     * @param id id
     * @return 我的累积收益
     */
    MyTeamDetailVo selectMySumIncreaseMoneyList(@Param("id") Integer id);
}
