package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
import com.jgzy.entity.po.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@Repository
public interface UserAddressMapper extends BaseMapper<UserAddress> {

    List<ProvinceInfo> selectProvince(@Param("id") Integer id);

    List<CityInfo> selectCity(@Param("id") Integer id, @Param("provinceId") Integer provinceId);

    List<AreaInfo> selectArea(@Param("id") Integer id, @Param("cityId") Integer cityId);

    List<UserAddressVo> selectUserAddress(Page<UserAddressVo> page, @Param("id") Integer id);

    UserAddressVo selectDetailById(@Param("userAddressId") Integer userAddressId);

    List<UserAddressVo> selectUserAddressList(@Param("id") Integer id);
}
