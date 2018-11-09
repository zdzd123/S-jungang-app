package com.jgzy.core.personalCenter.serviceImpl;

import com.jgzy.core.personalCenter.mapper.UserDistributionMapper;
import com.jgzy.core.personalCenter.service.IUserDistributionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.entity.po.UserDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-26
 */
@Service
public class UserDistributionServiceImpl extends ServiceImpl<UserDistributionMapper, UserDistribution> implements IUserDistributionService {
    @Autowired
    private UserDistributionMapper userDistributionMapper;

}
