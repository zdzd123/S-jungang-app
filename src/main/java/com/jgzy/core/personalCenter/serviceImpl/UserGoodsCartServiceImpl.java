package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.mapper.UserGoodsCartMapper;
import com.jgzy.core.personalCenter.service.IUserGoodsCartService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.personalCenter.vo.UserGoodsCartVo;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserGoodsCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@Service
public class UserGoodsCartServiceImpl extends ServiceImpl<UserGoodsCartMapper, UserGoodsCart> implements IUserGoodsCartService {
    @Autowired
    private UserGoodsCartMapper userGoodsCartMapper;

    @Override
    public Page<UserGoodsCartVo> selectMyUserCartPage(Page<UserGoodsCartVo> page) {
        List<UserGoodsCartVo> userGoodsCartVoList = userGoodsCartMapper.selectMyUserCartPage(page,
                UserUuidThreadLocal.get().getId());
        page.setRecords(userGoodsCartVoList);
        return page;
    }
}
