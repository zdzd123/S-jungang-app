package com.jgzy.core.shopOrder.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.mapper.UserInfoMapper;
import com.jgzy.core.personalCenter.vo.MyTeamDetailVo;
import com.jgzy.core.personalCenter.vo.MyTeamVo;
import com.jgzy.core.shopOrder.mapper.UserFundMapper;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserFund;
import com.jgzy.entity.po.UserInfo;
import com.jgzy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    private UserInfoMapper userInfoMapper;

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
        myTeamVo.setOriginatorIncreaseMoney(myTeamDetailVo.getOriginatorIncreaseMoney());
        myTeamVo.setIncreaseMoney(myTeamDetailVo.getIncreaseMoney());
        // 查询团队成员
        String ids = userFundMapper.getDistributionList(id);
        List<String> splitList = Arrays.asList(ids.split(","));
        List<String> idList = new ArrayList<>(splitList);
        // 移除前两位
        idList.removeIf(s -> s.equals("$"));
        idList.removeIf(s -> s.equals(String.valueOf(id)));
        Page<MyTeamDetailVo> page = new Page<>(1,3);
        if (!CollectionUtils.isEmpty(idList)) {
            // 查询合伙人团队列表
            List<MyTeamDetailVo> oriTeam = userFundMapper.selectUserSumIncreaseMoney(page, idList, "0");
            // 剔除不是合伙人的空数据
            List<Integer> removeIds = new ArrayList<>();
            for (MyTeamDetailVo detail:oriTeam){
                if (detail.getOriginatorIncreaseMoney().compareTo(BigDecimal.ZERO) == 0){
                    UserInfo userInfo = userInfoMapper.selectMyUserLevelById(detail.getId());
                    if (userInfo.getUserLevelId() == null){
                        removeIds.add(detail.getId());
                    }
                }
            }
            List<Integer> finalOriRemoveIds = removeIds;
            oriTeam.removeIf(a -> finalOriRemoveIds.contains(a.getId()));
            myTeamVo.setMyOriginatorTeamDetailVoList(oriTeam);
            // 查询消费者团队列表
            List<MyTeamDetailVo> team = userFundMapper.selectUserSumIncreaseMoney(page, idList, "1");
            // 剔除不是消费者的空数据
            removeIds = new ArrayList<>();
            for (MyTeamDetailVo detail:oriTeam){
                if (detail.getIncreaseMoney().compareTo(BigDecimal.ZERO) == 0){
                    UserInfo userInfo = userInfoMapper.selectMyUserLevelById(detail.getId());
                    if (userInfo.getUserLevelId() != null){
                        removeIds.add(detail.getId());
                    }
                }
            }
            List<Integer> finalRemoveIds = removeIds;
            team.removeIf(a -> finalRemoveIds.contains(a.getId()));
            myTeamVo.setMyTeamDetailVoList(team);
        }
        return myTeamVo;
    }

    @Override
    public Page<MyTeamDetailVo> selectUserSumIncreaseMoneyPage(Page<MyTeamDetailVo> page, Integer id, String status) {
        String ids = userFundMapper.getDistributionList(id);
        List<String> splitList = Arrays.asList(ids.split(","));
        List<String> idList = new ArrayList<>(splitList);
        // 移除前两位
        idList.removeIf(s -> s.equals("$"));
        idList.removeIf(s -> s.equals(String.valueOf(id)));
        if (!CollectionUtils.isEmpty(idList)) {
            List<MyTeamDetailVo> myTeamDetailVoList = userFundMapper.selectUserSumIncreaseMoney(page, idList, status);
            // 剔除null数据
            List<Integer> removeIds = new ArrayList<>();
            for (MyTeamDetailVo detail:myTeamDetailVoList){
                if (detail.getOriginatorIncreaseMoney().compareTo(BigDecimal.ZERO) == 0){
                    UserInfo userInfo = userInfoMapper.selectMyUserLevelById(detail.getId());
                    if (status.equals("0") && userInfo.getUserLevelId() == null){
                        removeIds.add(detail.getId());
                    }else if (status.equals("1") && userInfo.getUserLevelId() != null){
                        removeIds.add(detail.getId());
                    }
                }
            }
            myTeamDetailVoList.removeIf(a -> removeIds.contains(a.getId()));
            List<MyTeamDetailVo> myTeamDetailVoMonthList = userFundMapper.selectUserSumIncreaseMoneyList(idList,
                    DateUtil.getMonthBegin(new Date()), DateUtil.getMonthEnd(new Date()), status);
            for (MyTeamDetailVo myTeamDetailVo : myTeamDetailVoList) {
                for (MyTeamDetailVo month : myTeamDetailVoMonthList) {
                    //  拼接月金额
                    if (month.getId() != null && month.getId().equals(myTeamDetailVo.getId())) {
                        myTeamDetailVo.setMonthIncreaseMoney(month.getIncreaseMoney());
                        myTeamDetailVo.setMonthOriginatorIncreaseMoney(month.getOriginatorIncreaseMoney());
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
        MyTeamVo myTeamVo = userFundMapper.selectStatisticsIncreaseMoney(UserUuidThreadLocal.get().getId(), status, null, null);
        if (myTeamVo != null) {
            MyTeamVo monthVo = userFundMapper.selectStatisticsIncreaseMoney(UserUuidThreadLocal.get().getId(), status,
                    DateUtil.getMonthBegin(new Date()), DateUtil.getMonthEnd(new Date()));
            myTeamVo.setMonthIncreaseMoney(monthVo.getTotalIncreaseMoney());
        }
        return myTeamVo;
    }
}
