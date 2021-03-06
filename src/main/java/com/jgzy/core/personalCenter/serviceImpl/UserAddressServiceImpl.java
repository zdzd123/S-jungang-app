package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.personalCenter.mapper.UserAddressMapper;
import com.jgzy.core.personalCenter.service.IUserAddressService;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.AreaInfo;
import com.jgzy.entity.po.CityInfo;
import com.jgzy.entity.po.ProvinceInfo;
import com.jgzy.entity.po.UserAddress;
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
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<ProvinceInfo> getProvince(Integer id) {
        return userAddressMapper.selectProvince(id);
    }

    @Override
    public List<CityInfo> getCity(Integer id, Integer provinceId) {
        return userAddressMapper.selectCity(id, provinceId);
    }

    @Override
    public List<AreaInfo> getArea(Integer id, Integer cityId) {
        return userAddressMapper.selectArea(id, cityId);
    }

    @Override
    public Page<UserAddressVo> getUserAddress(Page<UserAddressVo> page) {
        Integer id = UserUuidThreadLocal.get().getId();
        return page.setRecords(userAddressMapper.selectUserAddress(page, id));
    }

    @Override
    public UserAddressVo selectDetailById(Integer userAddressId) {
        return userAddressMapper.selectDetailById(userAddressId);
    }

    @Override
    public List<UserAddressVo> selectMyList(String search) {
        Integer id = UserUuidThreadLocal.get().getId();
        return userAddressMapper.selectUserAddressList(id, search);
    }

    @Override
    public List<UserAddressVo> selectAllAddressPageList(String search) {
        return userAddressMapper.selectAllAddressPageList(UserUuidThreadLocal.get().getId(), search);
    }
}
