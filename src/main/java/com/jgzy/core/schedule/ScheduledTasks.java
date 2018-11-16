package com.jgzy.core.schedule;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IAdvanceRechargeRecordService;
import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.shopOrder.service.IShopGoodsOrderDetailService;
import com.jgzy.core.shopOrder.service.IShopGoodsOrderService;
import com.jgzy.core.shopOrder.service.IShopGoodsService;
import com.jgzy.core.shopOrder.service.IUserFundService;
import com.jgzy.entity.po.*;
import com.jgzy.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private IShopGoodsOrderService shopGoodsOrderService;
    @Autowired
    private IShopGoodsOrderDetailService shopGoodsOrderDetailService;
    @Autowired
    private IUserFundService userFundService;
    @Autowired
    private IAdvanceRechargeRecordService advanceRechargeRecordService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IShopGoodsService shopGoodsService;

    /**
     * 处理逾期订单
     */
    @Scheduled(fixedRate = 300000)
    @Transactional
    public void dealOverduePayments() {
        log.info("---------------------" + "开始处理逾期订单" + "----------------------------");
        Date date = new Date();
        // 查询过期未支付商品订单
        List<ShopGoodsOrder> shopGoodsOrderList = shopGoodsOrderService.selectList(new EntityWrapper<ShopGoodsOrder>()
                .le("valid_order_time", date)
                .eq("order_status", BaseConstant.ORDER_STATUS_1)
                .last("limit 100"));
        List<UserFund> myUserFundList = new ArrayList<>();
        for (ShopGoodsOrder shopGoodsOrder : shopGoodsOrderList) {
            // 查询所有该订单产生的流水
            List<UserFund> userFundList = userFundService.selectList(
                    new EntityWrapper<UserFund>().eq("order_no", shopGoodsOrder.getOrderNo()));
            for (UserFund userFund : userFundList) {
                // 支出金额
                BigDecimal decreaseMoney = userFund.getDecreaseMoney();
                // 收益金额
                BigDecimal increaseMoney = userFund.getIncreaseMoney();
                // 1：权额支付
                if (userFund.getPayType().equals(BaseConstant.PAY_TYPE_3)) {
                    AdvanceRechargeRecord advanceRechargeRecord = advanceRechargeRecordService.selectOne(
                            new EntityWrapper<AdvanceRechargeRecord>()
                                    .eq("user_id", shopGoodsOrder.getSubmitOrderUser()));
                    if (advanceRechargeRecord == null) {
                        log.info("该用户不存在权额！订单ID为" + shopGoodsOrder.getId());
                        throw new OptimisticLockingFailureException("该用户不存在权额！订单ID为" + shopGoodsOrder.getId());
                    }
                    Date updateTime = advanceRechargeRecord.getUpdateTime();
                    advanceRechargeRecord.setAmount(advanceRechargeRecord.getAmount().add(increaseMoney).subtract(decreaseMoney));
                    advanceRechargeRecord.setUpdateTime(date);
                    boolean update = advanceRechargeRecordService.update(advanceRechargeRecord, new EntityWrapper<AdvanceRechargeRecord>()
                            .eq("update_time", updateTime)
                            .eq("id", advanceRechargeRecord.getId()));
                    if (!update) {
                        log.info("更新权额失败！权额ID为：" + advanceRechargeRecord.getId());
                        throw new OptimisticLockingFailureException("更新权额失败！权额ID为：" + advanceRechargeRecord.getId());
                    }
                    // 插入流水
                    UserFund myUserFund = new UserFund();
                    myUserFund.setTradeUserId(shopGoodsOrder.getSubmitOrderUser());
                    myUserFund.setIncreaseMoney(decreaseMoney);
                    myUserFund.setDecreaseMoney(increaseMoney);
                    myUserFund.setTradeDescribe("逾期订单返还权额");
                    myUserFund.setOrderNo(shopGoodsOrder.getOrderNo());
                    myUserFund.setTradeTime(date);
                    myUserFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                    myUserFund.setAccountType(BaseConstant.ACCOUNT_TYPE_8);
                    myUserFund.setPayType(BaseConstant.PAY_TYPE_3);
                    myUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_5);
                    myUserFundList.add(myUserFund);
                }
                // 2：余额支付
                if (userFund.getPayType().equals(BaseConstant.PAY_TYPE_4)) {
                    UserInfo userInfo = userInfoService.selectById(shopGoodsOrder.getSubmitOrderUser());
                    if (userInfo == null) {
                        log.info("该用户不存在!订单ID为：" + shopGoodsOrder.getId());
                        throw new OptimisticLockingFailureException("该用户不存在!订单ID为：" + shopGoodsOrder.getId());
                    }
                    userInfo.setBalance1(userInfo.getBalance1().add(increaseMoney).subtract(decreaseMoney));
                    Date updateTime = userInfo.getUpdateTime();
                    userInfo.setUpdateTime(date);
                    boolean update = userInfoService.update(userInfo, new EntityWrapper<UserInfo>()
                            .eq("update_time", updateTime)
                            .eq("id", userInfo.getId()));
                    if (!update) {
                        log.info("更新用户余额失败！用户ID为：" + userInfo.getId());
                        throw new OptimisticLockingFailureException("更新用户余额失败！用户ID为：" + userInfo.getId());
                    }
                    // 插入流水
                    UserFund myUserFund = new UserFund();
                    myUserFund.setTradeUserId(shopGoodsOrder.getSubmitOrderUser());
                    myUserFund.setIncreaseMoney(decreaseMoney);
                    myUserFund.setDecreaseMoney(increaseMoney);
                    myUserFund.setTradeDescribe("逾期订单返还余额");
                    myUserFund.setOrderNo(shopGoodsOrder.getOrderNo());
                    myUserFund.setTradeTime(date);
                    myUserFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                    myUserFund.setAccountType(BaseConstant.ACCOUNT_TYPE_2);
                    myUserFund.setPayType(BaseConstant.PAY_TYPE_4);
                    myUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_5);
                    myUserFundList.add(myUserFund);
                }
                // 3：优惠券
            }
            // 插入流水
            for (UserFund userFund : myUserFundList) {
                userFundService.InsertUserFund(userFund);
            }
            // 更新订单
            shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_12);
            boolean update = shopGoodsOrderService.update(shopGoodsOrder, new EntityWrapper<ShopGoodsOrder>()
                    .eq("id", shopGoodsOrder.getId())
                    .eq("order_status", BaseConstant.ORDER_STATUS_1));
            if (!update) {
                log.info("更新订单失败！订单ID为：" + shopGoodsOrder.getId());
                throw new OptimisticLockingFailureException("更新订单失败！订单ID为：" + shopGoodsOrder.getId());
            }
            // 直接下单的订单返还商品库存
            List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = shopGoodsOrderDetailService.selectList(
                    new EntityWrapper<ShopGoodsOrderDetail>()
                            .eq("order_id", shopGoodsOrder.getId()));
            for (ShopGoodsOrderDetail shopGoodsOrderDetail : shopGoodsOrderDetailList) {
                boolean b = shopGoodsService.updateStockById(shopGoodsOrderDetail.getBuyCount() * -1, shopGoodsOrderDetail.getShopGoodsId());
                if (!b) {
                    log.info("更新商品库存失败！订单ID为：" + shopGoodsOrder.getId());
                    throw new OptimisticLockingFailureException("更新商品库存失败！订单ID为：" + shopGoodsOrder.getId());
                }
            }
        }
        log.info("---------------------" + "逾期订单处理完毕" + "----------------------------");
    }
}
