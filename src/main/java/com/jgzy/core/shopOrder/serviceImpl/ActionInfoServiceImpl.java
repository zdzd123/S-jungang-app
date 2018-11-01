package com.jgzy.core.shopOrder.serviceImpl;

import com.jgzy.core.shopOrder.mapper.ActionInfoMapper;
import com.jgzy.core.shopOrder.service.IActionInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.entity.po.ActionInfo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-23
 */
@Service
public class ActionInfoServiceImpl extends ServiceImpl<ActionInfoMapper, ActionInfo> implements IActionInfoService {

}
