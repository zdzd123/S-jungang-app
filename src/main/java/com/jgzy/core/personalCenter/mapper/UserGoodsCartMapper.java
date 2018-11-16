package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.vo.UserGoodsCartVo;
import com.jgzy.entity.po.UserGoodsCart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@Repository
public interface UserGoodsCartMapper extends BaseMapper<UserGoodsCart> {
    /**
     * 查询购物车信息
     *
     * @param page 分页
     * @param id   用户id
     * @return 购物车
     */
    List<UserGoodsCartVo> selectMyUserCartPage(Page<UserGoodsCartVo> page, @Param("id") Integer id);
}
