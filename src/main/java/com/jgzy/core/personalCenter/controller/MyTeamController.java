package com.jgzy.core.personalCenter.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.personalCenter.vo.MyTeamDetailVo;
import com.jgzy.core.personalCenter.vo.MyTeamVo;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserFund;
import com.jgzy.entity.po.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/api/myTeam")
@Api(value = "我的团队", description = "我的团队")
public class MyTeamController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserFundService userFundService;

    @GetMapping(value = "/detail")
    @ApiOperation(value = "我的团队页面初始化", notes = "我的团队页面初始化")
    public ResultWrapper<MyTeamVo> detail() {
        ResultWrapper<MyTeamVo> resultWrapper = new ResultWrapper<>();
        Integer id = UserUuidThreadLocal.get().getId();
        MyTeamVo myTeamVo = new MyTeamVo();
        // 查询用户余额
        UserInfo userInfo = userInfoService.selectMyUserLevelById(id);
        initMyTeam(myTeamVo, userInfo);
        // 查询消费者和合伙人累积收益
        Page<MyTeamDetailVo> page = new Page<>(1, 3);
        page = userFundService.selectUserSumIncreaseMoney(page, id);
        // 消费者团队
        List<MyTeamDetailVo> myTeamList = new ArrayList<>();
        BigDecimal increaseMoney = BigDecimal.ZERO;
        // 合伙人团队
        List<MyTeamDetailVo> myOriginatorTeamList = new ArrayList<>();
        BigDecimal originatorIncreaseMoney = BigDecimal.ZERO;
        for (MyTeamDetailVo myTeamDetailVo : page.getRecords()) {
            if (myTeamDetailVo.getId() == null || myTeamDetailVo.getId().equals(userInfo.getId())) {
                continue;
            }
            if (myTeamDetailVo.getStatus() != null && myTeamDetailVo.getStatus().equals(BaseConstant.ORIGINATOR_INFO_STATUS_0)) {
                myOriginatorTeamList.add(myTeamDetailVo);
                originatorIncreaseMoney = originatorIncreaseMoney.add(myTeamDetailVo.getOriginatorIncreaseMoney());
            } else if (myTeamDetailVo.getStatus() != null) {
                myTeamList.add(myTeamDetailVo);
                increaseMoney = increaseMoney.add(myTeamDetailVo.getIncreaseMoney());
            }
        }
        // 团队
        myTeamVo.setMyTeamDetailVoList(myTeamList);
        myTeamVo.setIncreaseMoney(increaseMoney);
        // 金额
        myTeamVo.setMyOriginatorTeamDetailVoList(myOriginatorTeamList);
        myTeamVo.setOriginatorIncreaseMoney(originatorIncreaseMoney);
        UserFund userFund = userFundService.selectOne(new EntityWrapper<UserFund>()
                .eq("trade_user_id", id)
                .eq("trade_type", BaseConstant.TRADE_TYPE_1)
                .eq("account_type", BaseConstant.ACCOUNT_TYPE_2)
                .eq("pay_type", BaseConstant.PAY_TYPE_4)
                .and("(bussiness_type = " + BaseConstant.BUSSINESS_TYPE_7 + " OR bussiness_type = " + BaseConstant.BUSSINESS_TYPE_8 + ")")
                .orderBy("id DESC")
                .last("limit 1"));
        myTeamVo.setTotalIncreaseMoney(userFund == null ? BigDecimal.ZERO : userFund.getBalance());
        resultWrapper.setResult(myTeamVo);
        return resultWrapper;
    }

    /**
     * beanCopy
     *
     * @param myTeamVo myTeamVo
     * @param userInfo userInfo
     */
    private void initMyTeam(MyTeamVo myTeamVo, UserInfo userInfo) {
        myTeamVo.setNickname(userInfo.getNickname());
        myTeamVo.setHeadPortrait(userInfo.getHeadPortrait());
        myTeamVo.setBalance1(userInfo.getBalance1());
        myTeamVo.setBalance4(userInfo.getBalance4());
        myTeamVo.setUserLevelId(userInfo.getUserLevelId());
    }

    @ApiOperation(value = "我的团队(分页)", notes = "我的团队(分页)")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<MyTeamDetailVo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                        @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
                                                        @ApiParam(value = "0-合伙人 1-消费者", required = true) @RequestParam(defaultValue = "10") String status) {
        ResultWrapper<Page<MyTeamDetailVo>> resultWrapper = new ResultWrapper<>();
        Page<MyTeamDetailVo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = userFundService.selectUserSumIncreaseMoneyPage(page, UserUuidThreadLocal.get().getId(), status);
        resultWrapper.setResult(page);
        return resultWrapper;
    }

    @GetMapping(value = "/statistics")
    @ApiOperation(value = "我的团队统计", notes = "我的团队统计")
    public ResultWrapper<MyTeamVo> statistics(@ApiParam(value = "0-合伙人 1-消费者", required = true) @RequestParam(defaultValue = "10") String status) {
        ResultWrapper<MyTeamVo> resultWrapper = new ResultWrapper<>();
        MyTeamVo myTeamVo = userFundService.selectStatisticsIncreaseMoney(status);
        resultWrapper.setResult(myTeamVo);
        return resultWrapper;
    }
}
