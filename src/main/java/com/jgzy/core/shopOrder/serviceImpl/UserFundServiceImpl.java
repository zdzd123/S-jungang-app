package com.jgzy.core.shopOrder.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.personalCenter.mapper.UserDistributionMapper;
import com.jgzy.core.personalCenter.vo.MyTeamDetailVo;
import com.jgzy.core.personalCenter.vo.MyTeamVo;
import com.jgzy.core.shopOrder.mapper.UserFundMapper;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserFund;
import com.jgzy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-22
 */
@Service
public class UserFundServiceImpl extends ServiceImpl<UserFundMapper, UserFund> implements IUserFundService {
    @Autowired
    private UserFundMapper userFundMapper;
    @Autowired
    private UserDistributionMapper userDistributionMapper;

    @Override
    public void InsertUserFund(UserFund userFund) {
        userFundMapper.insertUserFund(userFund);
    }

    @Override
    public MyTeamVo selectSumIncreaseMoney(Integer id) {
        return userFundMapper.selectSumIncreaseMoney(id);
    }

    @Override
    public MyTeamVo selectUserSumIncreaseMoney(Integer id) {
        MyTeamVo myTeamVo = new MyTeamVo();
        // 查询累积收益
        MyTeamDetailVo myTeamDetailVo = userFundMapper.selectMySumIncreaseMoneyList(id);
        // 合伙人累积收益：总收益+冻结收益-解冻收益
        myTeamVo.setOriginatorIncreaseMoney(myTeamDetailVo.getOriginatorIncreaseMoney()
                .add(myTeamDetailVo.getFreezeOriginatorIncreaseMoney()
                        .subtract(myTeamDetailVo.getUnfreezeOriginatorIncreaseMoney())));
        // 消费者累积收益：总收益+冻结收益-解冻收益
        myTeamVo.setIncreaseMoney(myTeamDetailVo.getIncreaseMoney()
                .add(myTeamDetailVo.getFreezeIncreaseMoney()
                        .subtract(myTeamDetailVo.getUnfreezeIncreaseMoney())));
        // 累积收益总金额
        myTeamVo.setTotalIncreaseMoney(myTeamDetailVo.getOriginatorIncreaseMoney().add(myTeamDetailVo.getIncreaseMoney()));
        // 查询团队成员
        // 1。查询普通消费者成员 2级
        List<String> myConsumerList = userDistributionMapper.selectMyConsumerList(id);
        // 2.查询合伙人 1级
        List<String> myPartnerList = userDistributionMapper.selectMyPartner(id);
        Page<MyTeamDetailVo> page = new Page<>(1, 3);
        if (!CollectionUtils.isEmpty(myPartnerList)) {
            // 查询合伙人团队列表
            List<MyTeamDetailVo> oriTeamList = userFundMapper.selectUserSumIncreaseMoney(page, myPartnerList, "0");
            myTeamVo.setMyOriginatorTeamDetailVoList(oriTeamList);
        }
        // 查询消费者团队列表
        if (!CollectionUtils.isEmpty(myConsumerList)) {
            List<MyTeamDetailVo> teamList = userFundMapper.selectUserSumIncreaseMoney(page, myConsumerList, "1");
            myTeamVo.setMyTeamDetailVoList(teamList);
        }
        return myTeamVo;
    }

    @Override
    public Page<MyTeamDetailVo> selectUserSumIncreaseMoneyPage(Page<MyTeamDetailVo> page, Integer id, String status) {
        // 查询团队成员
        List<String> ids;
        // 1.合伙人
        if (status.equals("0")) {
            ids = userDistributionMapper.selectMyPartner(id);
        } else {
            // 普通消费者
            ids = userDistributionMapper.selectMyConsumerList(id);
        }
        if (!CollectionUtils.isEmpty(ids)) {
            // 总金额
            List<MyTeamDetailVo> myTeamDetailVoList = userFundMapper.selectUserSumIncreaseMoney(page, ids, status);
            // 月金额
            List<MyTeamDetailVo> myTeamDetailVoMonthList = userFundMapper.selectUserSumIncreaseMoneyList(ids,
                    DateUtil.getMonthBegin(new Date()), DateUtil.getMonthEnd(new Date()), status);
            for (MyTeamDetailVo myTeamDetailVo : myTeamDetailVoList) {
                for (MyTeamDetailVo month : myTeamDetailVoMonthList) {
                    //  拼接月金额
                    if (month.getId() != null && month.getId().equals(myTeamDetailVo.getId())) {
                        myTeamDetailVo.setMonthDecreaseMoney(month.getTotalDecreaseMoney());
                        break;
                    }
                }
            }
            page.setRecords(myTeamDetailVoList);
        }
        return page;
    }

    @Override
    public MyTeamVo selectStatisticsIncreaseMoney(String status) {
        Integer id = UserUuidThreadLocal.get().getId();
        // 查询团队成员
        List<String> ids;
        // 1.合伙人
        if (status.equals("0")) {
            ids = userDistributionMapper.selectMyPartner(id);
        } else {
            // 普通消费者
            ids = userDistributionMapper.selectMyConsumerList(id);
        }
        if (!CollectionUtils.isEmpty(ids)) {
            MyTeamVo myTeamVo = userFundMapper.selectStatisticsIncreaseMoney(id, status, null, null, ids);
            if (myTeamVo != null) {
                MyTeamVo monthVo = userFundMapper.selectStatisticsIncreaseMoney(id, status,
                        DateUtil.getMonthBegin(new Date()), DateUtil.getMonthEnd(new Date()), ids);
                if (monthVo != null) {
                    myTeamVo.setMonthDecreaseMoney(monthVo.getTotalDecreaseMoney());
                }
            }
            return myTeamVo;
        }
        return null;
    }

    @Override
    public Page<UserFund> selectMyPage(Page<UserFund> page, String beginDate, String endTime, String bussinessType, String accountType, List<UserFund> userFundList) {
        Integer userId = UserUuidThreadLocal.get().getId();
        if (beginDate != null) {
            beginDate = beginDate + " 00:00:00";
        }
        if (endTime != null) {
            endTime = endTime + " 23:59:59";
        }
        String[] split = null;
        if (bussinessType != null) {
            split = bussinessType.split(",");
        }
        List<UserFund> myUserFundList = userFundMapper.selectMyPage(page, beginDate, endTime, split, accountType, userFundList, userId);
        return page.setRecords(myUserFundList);
    }
}
