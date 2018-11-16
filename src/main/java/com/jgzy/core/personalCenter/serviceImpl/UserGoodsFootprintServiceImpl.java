package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.mapper.UserGoodsFootprintMapper;
import com.jgzy.core.personalCenter.service.IUserGoodsFootprintService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.personalCenter.vo.UserGoodsFootprintVo;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserGoodsFootprint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
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
public class UserGoodsFootprintServiceImpl extends ServiceImpl<UserGoodsFootprintMapper, UserGoodsFootprint> implements IUserGoodsFootprintService {
    @Autowired
    private UserGoodsFootprintMapper userGoodsFootprintMapper;

    @Override
    public boolean insertMyFoot(UserGoodsFootprint po) {
        Integer id = UserUuidThreadLocal.get().getId();
        List<UserGoodsFootprint> userGoodsFootprintList = userGoodsFootprintMapper.selectList(
                new EntityWrapper<UserGoodsFootprint>()
                .eq("goods_id", po.getGoodsId())
                .eq("footprint_user_info_id", id));
        Integer insert;
        if (CollectionUtils.isEmpty(userGoodsFootprintList)){
            po.setFootprintUserInfoId(id);
            po.setAddTime(new Date());
            po.setUserDel(1);
            insert = userGoodsFootprintMapper.insert(po);
        }else{
            UserGoodsFootprint userGoodsFootprint = userGoodsFootprintList.get(0);
            userGoodsFootprint.setUserDel(1);
            userGoodsFootprint.setAddTime(new Date());
            insert = userGoodsFootprintMapper.updateById(userGoodsFootprint);
        }
        return insert != 0;
    }

    @Override
    public Page<UserGoodsFootprintVo> getUserGoodsFootprint(Page<UserGoodsFootprintVo> page) {
        Integer id = UserUuidThreadLocal.get().getId();
        return page.setRecords(userGoodsFootprintMapper.selectUserGoodsFootprint(page, id));
    }
}
