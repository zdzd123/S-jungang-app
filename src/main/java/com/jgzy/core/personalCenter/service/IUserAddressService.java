package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
import com.jgzy.entity.po.AreaInfo;
import com.jgzy.entity.po.CityInfo;
import com.jgzy.entity.po.ProvinceInfo;
import com.jgzy.entity.po.UserAddress;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
public interface IUserAddressService extends IService<UserAddress> {

    /**
     * 通过id获取省
     *
     * @param id
     * @return
     */
    List<ProvinceInfo> getProvince(Integer id);

    /**
     * 通过id.或者provinceId查询对应的市
     *
     * @param id
     * @param provinceId
     * @return
     */
    List<CityInfo> getCity(Integer id, Integer provinceId);

    /**
     * 通过id.或者cityId查询对应的区
     *
     * @param id
     * @param cityId
     * @return
     */
    List<AreaInfo> getArea(Integer id, Integer cityId);

    /**
     * 查看收货地址
     *
     * @param page
     * @return
     */
    Page<UserAddress> getUserAddress(Page<UserAddress> page);

    /**
     * 查询收货地址详细
     *
     * @param userAddressId
     * @return
     */
    UserAddressVo selectDetailById(Integer userAddressId);
}
