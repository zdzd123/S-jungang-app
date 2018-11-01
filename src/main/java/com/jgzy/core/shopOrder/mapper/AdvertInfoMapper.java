package com.jgzy.core.shopOrder.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.core.shopOrder.vo.AdvertInfoVo;
import com.jgzy.entity.po.AdvertInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-18
 */
@Repository
public interface AdvertInfoMapper extends BaseMapper<AdvertInfo> {

    List<AdvertInfoVo> selectAdvert(@Param("id")Integer id, @Param("advertSite") Integer advertSite);
}
