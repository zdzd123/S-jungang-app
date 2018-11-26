package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.mapper.UserGoodsCollectMapper;
import com.jgzy.core.personalCenter.service.IUserGoodsCollectService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.personalCenter.vo.UserGoodsCollectionVo;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserGoodsCollect;
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
public class UserGoodsCollectServiceImpl extends ServiceImpl<UserGoodsCollectMapper, UserGoodsCollect> implements IUserGoodsCollectService {
    @Autowired
    private UserGoodsCollectMapper userGoodsCollectMapper;

    @Override
    public Page<UserGoodsCollectionVo> getUserGoodsCollectByUserId(Page<UserGoodsCollectionVo> page) {
        Integer id = UserUuidThreadLocal.get().getId();
        return page.setRecords(userGoodsCollectMapper.selectUserGoodsCollectByUserId(page, id));
    }
}
