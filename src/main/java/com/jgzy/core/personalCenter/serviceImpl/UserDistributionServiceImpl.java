package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.mapper.UserDistributionMapper;
import com.jgzy.core.personalCenter.service.IUserDistributionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.personalCenter.vo.UserDistributionVo;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public Page<UserDistributionVo> getMyUserDistributionList(Page<UserDistributionVo> page) {
        Integer id = UserUuidThreadLocal.get().getId();
        String ids = userDistributionMapper.selectMyUserDistributionIdList(id);
        String[] split = ids.split(",");
        List<UserDistributionVo> userDistributionList = userDistributionMapper.selectMyUserDistributionList(page, split);
        page.setRecords(userDistributionList);
        return page;
    }
}
