package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IUserDistributionService;
import com.jgzy.core.shopOrder.service.IOriginatorInfoService;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.OriginatorInfo;
import com.jgzy.entity.po.UserDistribution;
import com.jgzy.utils.DateUtil;
import com.jgzy.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@RefreshScope
@RestController
@RequestMapping("/api/originatorInfo")
@Api(value = "合伙人接口", description = "合伙人接口")
public class OriginatorInfoController {
    @Value("${originator.amount}")
    private BigDecimal originatorAmount;

    @Autowired
    private IOriginatorInfoService originatorInfoService;
    @Autowired
    private IUserDistributionService userDistributionService;

    @ApiOperation(value = "新增合伙人", notes = "新增合伙人")
    @PostMapping(value = "/save")
    @Transactional
    public ResultWrapper<String> save(@RequestBody @Validated OriginatorInfo po) {
        ResultWrapper<String> resultWrapper = new ResultWrapper<>();
        Date date = new Date();
        // 身份证验证 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
        String identifyNoMatch = "";
        boolean identifyNoMatches = po.getIdentifyNo().matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
        // 手机号验证
        boolean phoneMatches = po.getPhone().matches("1[34578]\\d{9}");
        if (!identifyNoMatches) {
            resultWrapper.setResult("身份证不正确");
            resultWrapper.setSuccessful(false);
            return resultWrapper;
        }
        if (!phoneMatches) {
            resultWrapper.setResult("手机号不正确");
            resultWrapper.setSuccessful(false);
            return resultWrapper;
        }
        // 父级id
//        Integer parentUserId = po.getUserId();
        po.setStatus(BaseConstant.ORIGINATOR_STATUS_2);
        po.setAddTime(date);
        // 本身id
        Integer id = UserUuidThreadLocal.get().getId();
        po.setUserId(id);
        int user_id = originatorInfoService.selectCount(new EntityWrapper<OriginatorInfo>().eq("user_id", id));
        if (user_id != 0) {
            throw new OptimisticLockingFailureException("已存在合伙人，不能重复绑定");
        }
        boolean successful = originatorInfoService.insert(po);
        // 合伙人绑定父级
        UserDistribution userDistribution = new UserDistribution();
//        userDistribution.setParentId(parentUserId);
        userDistribution.setUserId(id);
        userDistribution.setAddTime(date);
        if (!successful) {
            throw new OptimisticLockingFailureException("插入合伙人失败");
        }
        resultWrapper.setResult(DateUtil.formatDate(date, DateUtil.DATETIME_FORMAT));
        return resultWrapper;
    }

    @ApiOperation(value = "更新指定ID的合伙人", notes = "更新指定ID的合伙人")
    @PutMapping("/update")
    public ResultWrapper<OriginatorInfo> update(@RequestBody @Validated OriginatorInfo po) {
        ResultWrapper<OriginatorInfo> resultWrapper = new ResultWrapper<>();
        Integer id = po.getId();
        boolean successful = false;
        if (ValidatorUtil.isNotNullOrEmpty(id)) {
            successful = originatorInfoService.updateById(po);
        }
        resultWrapper.setSuccessful(successful);
        return resultWrapper;
    }

    @GetMapping(value = "/detail")
    @ApiOperation(value = "查询合伙人信息", notes = "查询合伙人信息")
    public ResultWrapper<OriginatorInfo> detail() {
        ResultWrapper<OriginatorInfo> resultWrapper = new ResultWrapper<>();
        Integer id = UserUuidThreadLocal.get().getId();
        OriginatorInfo originatorInfo = originatorInfoService.selectOne(
                new EntityWrapper<OriginatorInfo>().eq("user_id", id));
        resultWrapper.setResult(originatorInfo);
        return resultWrapper;
    }

    @GetMapping(value = "/getOriginatorAmount")
    @ApiOperation(value = "获取入伙费", notes = "获取入伙费")
    public ResultWrapper<BigDecimal> getOriginatorAmount() {
        ResultWrapper<BigDecimal> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(originatorAmount);
        return resultWrapper;
    }

    @ApiOperation(value = "查询合伙人（分页）", notes = "查询合伙人（分页）")
    @GetMapping(value = "/page/list")
    public ResultWrapper<Page<OriginatorInfo>> listPage(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") String pageNum,
                                                        @ApiParam(value = "每页数", required = true) @RequestParam(defaultValue = "10") String pageSize) {
        ResultWrapper<Page<OriginatorInfo>> resultWrapper = new ResultWrapper<>();
        Page<OriginatorInfo> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        page = originatorInfoService.selectPage(page);
        resultWrapper.setResult(page);
        return resultWrapper;
    }
}

