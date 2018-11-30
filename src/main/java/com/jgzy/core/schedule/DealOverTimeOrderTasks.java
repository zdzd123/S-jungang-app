package com.jgzy.core.schedule;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IUserDistributionService;
import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.shopOrder.service.IShopGoodsOrderService;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.jgzy.entity.po.ShopGoodsOrder;
import com.jgzy.entity.po.UserDistribution;
import com.jgzy.entity.po.UserFund;
import com.jgzy.entity.po.UserInfo;
import com.jgzy.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DealOverTimeOrderTasks {
    private static final Logger log = LoggerFactory.getLogger(DealOverTimeOrderTasks.class);

    @Autowired
    private IShopGoodsOrderService shopGoodsOrderService;
    @Autowired
    private IUserFundService userFundService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserDistributionService userDistributionService;

    /**
     * 处理15天未确定订单 10分钟
     * 每天凌晨2点
     */
    @Scheduled(fixedRate = 10 * 60 * 1000)
//    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void dealOverduePayments() {
        Date date = new Date();
        // 查询过期未支付商品订单
        log.info("---------------------" + "处理15天未确定订单" + "----------------------------");
        List<ShopGoodsOrder> list4 = shopGoodsOrderService.selectList(new EntityWrapper<ShopGoodsOrder>()
                .eq("order_status", BaseConstant.ORDER_STATUS_4)
                .and("date_add(send_out_time, INTERVAL 15 DAY) <= '" + DateUtil.getNow() + "'")
                .last("limit 10"));
        if (!CollectionUtils.isEmpty(list4)) {
            List<ShopGoodsOrder> myOrderList = new ArrayList<>();
            for (ShopGoodsOrder shopGoodsOrder : list4) {
                ShopGoodsOrder myOrder = new ShopGoodsOrder();
                myOrder.setId(shopGoodsOrder.getId());
                myOrder.setOrderStatus(BaseConstant.ORDER_STATUS_5);
                myOrderList.add(myOrder);
            }
            shopGoodsOrderService.updateBatchById(myOrderList);
        }
        log.info("---------------------" + "15天未确定订单处理结束" + "----------------------------");
        log.info("---------------------" + "处理30天未评价订单" + "----------------------------");
        List<ShopGoodsOrder> list5 = shopGoodsOrderService.selectList(new EntityWrapper<ShopGoodsOrder>()
                .eq("order_status", BaseConstant.ORDER_STATUS_5)
                .and("date_add(send_out_time, INTERVAL 30 DAY) <= '" + DateUtil.getNow() + "'")
                .last("limit 10"));
        if (!CollectionUtils.isEmpty(list5)) {
            List<ShopGoodsOrder> myOrderList = new ArrayList<>();
            for (ShopGoodsOrder shopGoodsOrder : list4) {
                ShopGoodsOrder myOrder = new ShopGoodsOrder();
                myOrder.setId(shopGoodsOrder.getId());
                myOrder.setOrderStatus(BaseConstant.ORDER_STATUS_11);
                myOrder.setClosingTime(date);
                myOrderList.add(myOrder);
            }
            shopGoodsOrderService.updateBatchById(myOrderList);
            dealCommissionAmount();
        }
        log.info("---------------------" + "30天未评价订单处理结束" + "----------------------------");
    }

    /**
     * 订单关闭时，处理佣金股权和分销金额的解冻（可提现）
     */
    @Async
    @Transactional
    public void dealCommissionAmount() {
        log.info("---------------------" + "开始处理关闭订单的金额" + "----------------------------");
        // 查询关闭未处理订单 返点标识 0-未返点 1-已返点
        List<ShopGoodsOrder> shopGoodsOrderList = shopGoodsOrderService.selectList(new EntityWrapper<ShopGoodsOrder>()
                .eq("order_status", BaseConstant.ORDER_STATUS_11)
                .eq("is_read", 0));
        if (CollectionUtils.isEmpty(shopGoodsOrderList)) {
            return;
        }
        List<ShopGoodsOrder> myOrderList = new ArrayList<>();
        for (ShopGoodsOrder shopGoodsOrder : shopGoodsOrderList) {
            // 更新订单
            ShopGoodsOrder myOrder = new ShopGoodsOrder();
            myOrder.setId(shopGoodsOrder.getId());
            myOrder.setIsRead(1);
            myOrderList.add(myOrder);
            // 查询所有该订单产生的流水
            List<UserFund> userFundList = userFundService.selectList(
                    new EntityWrapper<UserFund>().eq("order_no", shopGoodsOrder.getOrderNo()));
            // 查询订单用户
            UserInfo userInfo = userInfoService.selectById(shopGoodsOrder.getSubmitOrderUser());
            //普通消费者分销
            UserDistribution userDistribution = userDistributionService.selectOne(
                    new EntityWrapper<UserDistribution>()
                            .eq("user_id", shopGoodsOrder.getSubmitOrderUser()));
            // 余额
            BigDecimal balance1 = BigDecimal.ZERO;

            BigDecimal parentBalance1 = BigDecimal.ZERO;
            // 股权
            BigDecimal point = BigDecimal.ZERO;
            List<UserFund> myUserFundList = new ArrayList<>();
            for (UserFund userFund : userFundList) {
                // 1。品牌费
                BigDecimal fundAmount = userFund.getIncreaseMoney().subtract(userFund.getDecreaseMoney());
                if (userFund.getAccountType().equals(BaseConstant.ACCOUNT_TYPE_9) &&
                        userFund.getPayType().equals(BaseConstant.PAY_TYPE_6) &&
                        userFund.getBussinessType().equals(BaseConstant.BUSSINESS_TYPE_51)) {
                    // 品牌费余额
                    balance1 = balance1.add(fundAmount);
                    // 新增流水
                    userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_5);
                    myUserFundList.add(userFund);
                }
                // 2。佣金
                if (userFund.getAccountType().equals(BaseConstant.ACCOUNT_TYPE_2) &&
                        userFund.getPayType().equals(BaseConstant.PAY_TYPE_4) &&
                        userFund.getBussinessType().equals(BaseConstant.BUSSINESS_TYPE_71)) {
                    // 父级佣金
                    parentBalance1 = parentBalance1.add(fundAmount);
                    // 新增流水
                    userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_7);
                    myUserFundList.add(userFund);
                }
                // 3。股权
                if (userFund.getAccountType().equals(BaseConstant.ACCOUNT_TYPE_1) &&
                        userFund.getPayType().equals(BaseConstant.PAY_TYPE_9) &&
                        userFund.getBussinessType().equals(BaseConstant.BUSSINESS_TYPE_41)) {
                    point = point.add(fundAmount);
                    // 新增流水
                    userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_4);
                    myUserFundList.add(userFund);
                }
                // 4。分销
                if (userFund.getAccountType().equals(BaseConstant.ACCOUNT_TYPE_2) &&
                        userFund.getPayType().equals(BaseConstant.PAY_TYPE_4) &&
                        userFund.getBussinessType().equals(BaseConstant.BUSSINESS_TYPE_81)) {
                    // 分销
                    point = point.add(userFund.getIncreaseMoney()).subtract(userFund.getDecreaseMoney());
                    if (userDistribution != null && userDistribution.getParentId() != null &&
                            userFund.getTradeUserId().equals(userDistribution.getParentId())) {
                        // 父级分销
                        parentBalance1 = parentBalance1.add(fundAmount);
                    } else {
                        // 祖父级分销
                        UserInfo user = new UserInfo();
                        user.setId(userFund.getTradeUserId());
                        user.setBalance2(fundAmount);
                        user.setBalance3(new BigDecimal("-" + fundAmount.toString()));
                        boolean b = userInfoService.updateMyBalance(user);
                        if (!b) {
                            throw new OptimisticLockingFailureException("更新祖父级分销失败！");
                        }
                    }
                    // 新增流水
                    userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_8);
                    myUserFundList.add(userFund);
                }
            }
            // 更新父级金额
            if (userDistribution != null && userDistribution.getParentId() != null &&
                    parentBalance1.compareTo(BigDecimal.ZERO) > 0) {
                //父级分销
                UserInfo parentUser = new UserInfo();
                parentUser.setId(userDistribution.getParentId());
                parentUser.setBalance1(parentBalance1);
                parentUser.setBalance3(new BigDecimal("-" + parentBalance1.toString()));
                boolean b = userInfoService.updateMyBalance(parentUser);
                if (!b) {
                    throw new OptimisticLockingFailureException("更新父级分销失败！");
                }
            }
            // 更新本人金额
            UserInfo my = new UserInfo();
            my.setId(shopGoodsOrder.getSubmitOrderUser());
            my.setBalance1(balance1);
            my.setBalance2(new BigDecimal("-" + balance1.toString()));
            my.setBalance3(new BigDecimal("-" + point.toString()));
            my.setPoint(point);
            boolean b = userInfoService.updateMyBalance(my);
            if (!b) {
                throw new OptimisticLockingFailureException("更新本人金额失败！");
            }
            // 插入流水
            for (UserFund userFund : myUserFundList) {
                userFundService.InsertUserFund(userFund);
            }
        }
        boolean b = shopGoodsOrderService.updateBatchById(myOrderList);
        if (!b) {
            throw new OptimisticLockingFailureException("更新订单失败！");
        }
        log.info("---------------------" + "关闭订单的金额处理结束" + "----------------------------");
    }
}
