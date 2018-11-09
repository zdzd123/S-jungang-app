package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.service.IService;
import com.jgzy.entity.po.UserDistributionConstant;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-11-09
 */
public interface IUserDistributionConstantService extends IService<UserDistributionConstant> {

    UserDistributionConstant selectMyDistributionById(Integer id);

    UserDistributionConstant selectParentDistributionById(Integer id);
}
