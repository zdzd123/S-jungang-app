package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserFund;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-22
 */
@RefreshScope
@RestController
@RequestMapping("/api/userFund")
@Api(value = "用户流水", description = "用户流水")
public class UserFundController {
    @Autowired
    private IUserFundService userFundService;

    @ApiOperation(value = "查询流水明细（分页）", notes = "查询流水明细（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<UserFund>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                  @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
                                                  @ApiParam(value = "开始时间") @RequestParam(required = false) String beginDate,
                                                  @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime,
                                                  @ApiParam(value = "1-消费，2-转入，3-转出， " +
                                                          "4-股权收益，41-股权收益冻结 " +
                                                          "5-退款，51-退款冻结， " +
                                                          "6-已提现，61-提现退回，62-提现冻结 " +
                                                          "7-合伙人佣金，71-合伙人佣金冻结，72-合伙人佣金冻结到期转出，73-合伙人佣金退回 " +
                                                          "8-消费者佣金，81-消费者佣金冻结，82-消费者佣金冻结到期转出，83-消费者佣金退回 " +
                                                          "9-自己消费佣金，91-自己消费佣金冻结，92-自己消费佣金冻结到期转出，93-自己消费佣金退回") @RequestParam(required = false) String bussinessType,
                                                  @ApiParam(value = "支付类型 1-积分，2-余额，3-冻结，4-微信，5-支付宝，6-提现，7-荣誉值，8-权额， 9-品牌费") @RequestParam(required = false) String accountType) {
        ResultWrapper<Page<UserFund>> resultWrapper = new ResultWrapper<>();
        Page<UserFund> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        if (beginDate != null && endTime != null && beginDate.compareTo(endTime) > 0) {
            throw new OptimisticLockingFailureException("开始时间不得大于结束时间");
        }
        String[] split = null;
        if (StringUtils.isNotEmpty(bussinessType)) {
            split = bussinessType.split(",");
        }
        page = userFundService.selectPage(page, new EntityWrapper<UserFund>()
                .ge(StringUtils.isNotEmpty(beginDate), "trade_time", beginDate + " 00:00:00")
                .le(StringUtils.isNotEmpty(endTime), "trade_time", endTime + " 23:59:59")
                .eq(StringUtils.isNotEmpty(accountType), "account_type", accountType)
                .in(StringUtils.isNotEmpty(bussinessType), "bussiness_type", split)
                .eq("trade_user_id", UserUuidThreadLocal.get().getId())
                .orderBy("trade_time DESC"));
        resultWrapper.setResult(page);
        return resultWrapper;
    }
//
//    @ApiOperation(value = "查询权额支付流水(分页)", notes = "查询权额支付流水(分页)")
//    @GetMapping(value = "advanceAmount/page/list")
//    public ResultWrapper<Page<UserFund>> advanceAmountListPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
//                                                               @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize,
//                                                               @ApiParam(value = "开始时间") @RequestParam(required = false) String beginDate,
//                                                               @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime,
//                                                               @ApiParam(value = "1-消费，2-转入，3-转出，4-收益，5-退款") @RequestParam(required = false) String bussinessType,
//                                                               @ApiParam(value = "支付类型 1-积分，2-余额，3-冻结，4-微信，5-支付宝，6-提现，7-荣誉值，8-权额") @RequestParam(required = false) String accountType) {
//        ResultWrapper<Page<UserFund>> resultWrapper = new ResultWrapper<>();
//        Page<UserFund> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//        page = userFundService.selectPage(page, new EntityWrapper<UserFund>()
//                .ge(!StringUtils.isEmpty(beginDate), "trade_time", beginDate + " 00:00:00")
//                .le(!StringUtils.isEmpty(endTime), "trade_time", endTime + " 23:59:59")
//                .eq(!StringUtils.isEmpty(bussinessType), "bussiness_type", bussinessType)
//                .eq("account_type", BaseConstant.ACCOUNT_TYPE_8)
//                .eq("trade_user_id", UserUuidThreadLocal.get().getId()));
//        resultWrapper.setResult(page);
//        return resultWrapper;
//    }
}

