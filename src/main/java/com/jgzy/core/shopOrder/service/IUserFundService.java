package com.jgzy.core.shopOrder.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.personalCenter.vo.MyTeamDetailVo;
import com.jgzy.core.personalCenter.vo.MyTeamVo;
import com.jgzy.entity.po.UserFund;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-22
 */
public interface IUserFundService extends IService<UserFund> {

    void InsertUserFund(UserFund userFund);

    /**
     * 统计消费者和合伙人佣金和分销收益
     *
     * @param id 用户id
     * @return 消费者和合伙人佣金和分销收益
     */
    MyTeamVo selectSumIncreaseMoney(Integer id);

    /**
     * 统计消费者和合伙人佣金和分销收益和累积榜单
     *
     * @param id 用户id
     * @return 消费者和合伙人佣金和分销收益
     */
    MyTeamVo selectUserSumIncreaseMoney(Integer id);

    /**
     * 统计消费者和合伙人佣金和分销收益按用户排序
     *
     * @param id     用户id
     * @param status 0-合伙人 1-消费者
     * @return 消费者和合伙人佣金和分销收益
     */
    Page<MyTeamDetailVo> selectUserSumIncreaseMoneyPage(Page<MyTeamDetailVo> page, Integer id, String status);

    /**
     * 我的团队统计
     *
     * @return 统计结果
     * @param status
     */
    MyTeamVo selectStatisticsIncreaseMoney(String status);
}
