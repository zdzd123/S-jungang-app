package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.personalCenter.mapper.UserAddressShipMapper;
import com.jgzy.core.personalCenter.service.IUserAddressShipService;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserAddressShip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@Service
public class UserAddressShipServiceImpl extends ServiceImpl<UserAddressShipMapper, UserAddressShip> implements IUserAddressShipService {
    @Autowired
    private UserAddressShipMapper userAddressShipMapper;

    @Override
    public Page<UserAddressVo> getUserAddressShip(Page<UserAddressVo> page) {
        Integer id = UserUuidThreadLocal.get().getId();
        return page.setRecords(userAddressShipMapper.selectUserAddressShip(page, id));
    }

    @Override
    public UserAddressVo selectDetailById(Integer shipAddressId) {
        return userAddressShipMapper.selectDetailById(shipAddressId);
    }

    @Override
    public List<UserAddressVo> selectMyList(String search) {
        Integer id = UserUuidThreadLocal.get().getId();
        return userAddressShipMapper.selectMyList(id, search);
    }
}
