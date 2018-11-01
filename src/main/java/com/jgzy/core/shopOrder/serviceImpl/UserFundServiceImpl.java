package com.jgzy.core.shopOrder.serviceImpl;

import com.jgzy.core.shopOrder.mapper.UserFundMapper;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.entity.po.UserFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-22
 */
@Service
public class UserFundServiceImpl extends ServiceImpl<UserFundMapper, UserFund> implements IUserFundService {
    @Autowired
    private UserFundMapper userFundMapper;

    @Override
    public void InsertUserFund(UserFund userFund) {
        userFundMapper.insertUserFund(userFund);
    }
}
