package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.personalCenter.vo.UserGoodsCartVo;
import com.jgzy.entity.po.UserGoodsCart;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
public interface IUserGoodsCartService extends IService<UserGoodsCart> {
    /**
     * 查询购物车信息
     *
     * @param page 分页
     * @return 购物车
     */
    Page<UserGoodsCartVo> selectMyUserCartPage(Page<UserGoodsCartVo> page);
}
