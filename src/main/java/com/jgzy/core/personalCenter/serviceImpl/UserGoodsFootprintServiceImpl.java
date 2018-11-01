package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.mapper.UserGoodsFootprintMapper;
import com.jgzy.core.personalCenter.service.IUserGoodsFootprintService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserGoodsFootprint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@Service
public class UserGoodsFootprintServiceImpl extends ServiceImpl<UserGoodsFootprintMapper, UserGoodsFootprint> implements IUserGoodsFootprintService {
    @Autowired
    private UserGoodsFootprintMapper userGoodsFootprintMapper;

    @Override
    public Page<UserGoodsFootprint> getUserGoodsFootprint(Page<UserGoodsFootprint> page) {
        Integer id = UserUuidThreadLocal.get().getId();
        return page.setRecords(userGoodsFootprintMapper.selectUserGoodsFootprint(page, id));
    }
}
