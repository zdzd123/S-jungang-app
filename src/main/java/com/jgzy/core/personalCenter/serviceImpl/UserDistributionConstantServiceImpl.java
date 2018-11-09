package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.personalCenter.mapper.UserDistributionConstantMapper;
import com.jgzy.core.personalCenter.service.IUserDistributionConstantService;
import com.jgzy.entity.po.UserDistributionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-11-09
 */
@Service
public class UserDistributionConstantServiceImpl extends ServiceImpl<UserDistributionConstantMapper, UserDistributionConstant> implements IUserDistributionConstantService {
    @Autowired
    private UserDistributionConstantMapper userDistributionConstantMapper;

    @Override
    public UserDistributionConstant selectMyDistributionById(Integer id) {
        return userDistributionConstantMapper.selectMyDistributionById(id);
    }

    @Override
    public UserDistributionConstant selectParentDistributionById(Integer id) {
        return userDistributionConstantMapper.selectParentDistributionById(id);
    }
}
