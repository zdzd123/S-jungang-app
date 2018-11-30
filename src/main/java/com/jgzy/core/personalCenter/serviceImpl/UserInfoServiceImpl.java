package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.personalCenter.mapper.UserInfoMapper;
import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.personalCenter.vo.PersonalCenterVo;
import com.jgzy.core.personalCenter.vo.UserInfoVo;
import com.jgzy.core.shopOrder.mapper.OriginatorInfoMapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.OriginatorInfo;
import com.jgzy.entity.po.UserInfo;
import com.jgzy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-15
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private OriginatorInfoMapper originatorInfoMapper;

    @Override
    public PersonalCenterVo statistics() {
        UserInfo userInfo = UserUuidThreadLocal.get();
        String now = DateUtil.getNow();
        PersonalCenterVo personalCenterVo = userInfoMapper.statistics(userInfo.getId(), now);
        List<OriginatorInfo> originatorInfoList = originatorInfoMapper.selectList(new EntityWrapper<OriginatorInfo>()
                .eq("user_id", userInfo.getId())
                .eq("status", 0));
        personalCenterVo.setLevelId(CollectionUtils.isEmpty(originatorInfoList) ? null : originatorInfoList.get(0).getLevelId().toString());
        personalCenterVo.setNickName(userInfo.getNickname());
        personalCenterVo.setHeadPortrait(userInfo.getHeadPortrait());
        return personalCenterVo;
    }

    @Override
    public boolean updateStockRightDiscount(Integer id, BigDecimal stockRight) {
        return userInfoMapper.updateStockRight(id, stockRight);
    }

    @Override
    public boolean updateCommissionDiscount(Integer id, BigDecimal commission) {
        return userInfoMapper.updateCommissionDiscount(id, commission);
    }

    @Override
    public UserInfo selectMyUserLevelById(Integer id) {
        return userInfoMapper.selectMyUserLevelById(id);
    }

    @Override
    public boolean withDrawAmount(Integer id, BigDecimal withdrawNum) {
        return userInfoMapper.withDrawAmount(id, withdrawNum) == 1;
    }

    @Override
    public UserInfoVo selectMyUserJoinOriginatorInfo(Integer id) {
        return userInfoMapper.selectMyUserJoinOriginatorInfo(id);
    }

    @Override
    public boolean updateMyBalance(UserInfo myUser) {
        return userInfoMapper.updateMyBalance(myUser);
    }
}
