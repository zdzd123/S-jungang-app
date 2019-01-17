package com.jgzy.core.personalCenter.serviceImpl;

import com.jgzy.core.personalCenter.mapper.UserStockFundMapper;
import com.jgzy.core.personalCenter.service.IUserStockFundService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.entity.po.UserStockFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户库存流水 服务实现类
 * </p>
 *
 * @author zou
 * @since 2019-01-11
 */
@Service
public class UserStockFundServiceImpl extends ServiceImpl<UserStockFundMapper, UserStockFund> implements IUserStockFundService {
    @Autowired
    private UserStockFundMapper userStockFundMapper;

    @Override
    public void insertMyUserStockBatch(List<UserStockFund> userStockFundList) {
        for (UserStockFund userStockFund:userStockFundList){
            userStockFundMapper.insertMyUserStockFund(userStockFund);
        }
    }

    @Override
    public void insertMyUserStock(UserStockFund userStockFund) {
        userStockFundMapper.insertMyUserStockFund(userStockFund);
    }
}
