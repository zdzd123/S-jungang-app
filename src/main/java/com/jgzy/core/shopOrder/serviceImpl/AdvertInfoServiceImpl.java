package com.jgzy.core.shopOrder.serviceImpl;

import com.jgzy.core.shopOrder.mapper.AdvertInfoMapper;
import com.jgzy.core.shopOrder.service.IAdvertInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.shopOrder.vo.AdvertInfoVo;
import com.jgzy.entity.po.AdvertInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-18
 */
@Service
public class AdvertInfoServiceImpl extends ServiceImpl<AdvertInfoMapper, AdvertInfo> implements IAdvertInfoService {
    @Autowired
    private AdvertInfoMapper advertInfoMapper;

    @Override
    public List<AdvertInfoVo> getAdvertInfo(Integer id, Integer site) {
        return advertInfoMapper.selectAdvert(id, site);
    }
}
