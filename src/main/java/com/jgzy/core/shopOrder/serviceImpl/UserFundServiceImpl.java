package com.jgzy.core.shopOrder.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.vo.MyTeamDetailVo;
import com.jgzy.core.personalCenter.vo.MyTeamVo;
import com.jgzy.core.shopOrder.mapper.UserFundMapper;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserFund;
import com.jgzy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public MyTeamVo selectSumIncreaseMoney(Integer id) {
        return userFundMapper.selectSumIncreaseMoney(id);
    }

    @Override
    public Page<MyTeamDetailVo> selectUserSumIncreaseMoney(Page<MyTeamDetailVo> page, Integer id) {
        List<MyTeamDetailVo> myTeamDetailVoList = userFundMapper.selectUserSumIncreaseMoney(page, id, null);
        page.setRecords(myTeamDetailVoList);
        return page;
    }

    @Override
    public Page<MyTeamDetailVo> selectUserSumIncreaseMoneyPage(Page<MyTeamDetailVo> page, Integer id, String status) {
        List<MyTeamDetailVo> myTeamDetailVoList = userFundMapper.selectUserSumIncreaseMoney(page, id, status);
        List<MyTeamDetailVo> myTeamDetailVoMonthList = userFundMapper.selectUserSumIncreaseMoneyList(id,
                DateUtil.getMonthBegin(new Date()), DateUtil.getMonthEnd(new Date()), status);
        for (MyTeamDetailVo myTeamDetailVo:myTeamDetailVoList){
            for (MyTeamDetailVo month:myTeamDetailVoMonthList){
                //  拼接月金额
                if (month.getId() != null && month.getId().equals(myTeamDetailVo.getId())){
                    myTeamDetailVo.setMonthIncreaseMoney(month.getIncreaseMoney());
                    myTeamDetailVo.setMonthOriginatorIncreaseMoney(month.getOriginatorIncreaseMoney());
                    break;
                }
            }
        }
        page.setRecords(myTeamDetailVoList);
        return page;
    }

    @Override
    public MyTeamVo selectStatisticsIncreaseMoney(String status) {
        MyTeamVo myTeamVo = userFundMapper.selectStatisticsIncreaseMoney(UserUuidThreadLocal.get().getId(), status, null, null);
        if (myTeamVo != null) {
            MyTeamVo monthVo = userFundMapper.selectStatisticsIncreaseMoney(UserUuidThreadLocal.get().getId(), status,
                    DateUtil.getMonthBegin(new Date()), DateUtil.getMonthEnd(new Date()));
            myTeamVo.setMonthIncreaseMoney(monthVo.getTotalIncreaseMoney());
        }
        return myTeamVo;
    }
}
