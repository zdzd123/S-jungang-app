package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.personalCenter.vo.UserDistributionVo;
import com.jgzy.entity.po.UserDistribution;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-26
 */
public interface IUserDistributionService extends IService<UserDistribution> {
    /**
     * 获取合伙人团队列表
     *
     * @param page 分页
     * @return 合伙人团队
     */
    Page<UserDistributionVo> getMyUserDistributionList(Page<UserDistributionVo> page);
}
