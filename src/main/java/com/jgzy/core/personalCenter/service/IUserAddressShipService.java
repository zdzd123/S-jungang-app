package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
import com.jgzy.entity.po.UserAddressShip;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
public interface IUserAddressShipService extends IService<UserAddressShip> {
    /**
     * 查看收货地址
     *
     * @param page
     * @return
     */
    Page<UserAddressShip> getUserAddressShip(Page<UserAddressShip> page);

    UserAddressVo selectDetailById(Integer shipAddressId);

    List<UserAddressVo> selectMyList();
}
