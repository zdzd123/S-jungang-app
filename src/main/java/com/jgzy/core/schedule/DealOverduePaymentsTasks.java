package com.jgzy.core.schedule;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IAdvanceRechargeRecordService;
import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.shopOrder.service.*;
import com.jgzy.entity.po.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DealOverduePaymentsTasks {

    private static final Logger log = LoggerFactory.getLogger(DealOverduePaymentsTasks.class);

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
    @Autowired
    private IShopStockService shopStockService;

    /**
     * 处理逾期订单 10分钟
     */
    @Scheduled(fixedRate = 10 * 60 * 1000)
//    @Scheduled(fixedRate = 1000)
    @Transactional
    public void dealOverduePayments() {
        log.info("---------------------" + "开始处理逾期订单" + "----------------------------");
        Date date = new Date();
        // 查询过期未支付商品订单
        List<ShopGoodsOrder> shopGoodsOrderList = shopGoodsOrderService.selectList(new EntityWrapper<ShopGoodsOrder>()
                .le("valid_order_time", date)
                .ne("is_read", 1)
                .eq("order_status", BaseConstant.ORDER_STATUS_1)
                .orNew()
                .eq("order_status", BaseConstant.ORDER_STATUS_12)
                .ne("is_read", 1)
                .orNew()
                .eq("order_status", BaseConstant.ORDER_STATUS_13)
                .ne("is_read", 1)
        );
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
                    String levelId = userFund.getTradeDescribe().substring(0, 1);
                    // 权额支付id、等级和金额
                    AdvanceRechargeRecord advanceRechargeRecord = advanceRechargeRecordService.selectOne(
                            new EntityWrapper<AdvanceRechargeRecord>()
                                    .eq("user_id", shopGoodsOrder.getSubmitOrderUser())
                                    .eq("level_id", levelId));
                    if (advanceRechargeRecord == null) {
                        log.info("该用户不存在等级为：" + levelId + "的权额！订单ID为" + shopGoodsOrder.getId());
                        throw new OptimisticLockingFailureException("该用户不存在等级为：" + levelId + "的权额！订单ID为" + shopGoodsOrder.getId());
                    }
                    Date updateTime = advanceRechargeRecord.getUpdateTime();
                    advanceRechargeRecord.setAmount(advanceRechargeRecord.getAmount().subtract(increaseMoney).add(decreaseMoney));
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
                    myUserFund.setTradeDescribe("逾期订单返还" + levelId + "级权额");
                    myUserFund.setOrderNo(shopGoodsOrder.getOrderNo());
                    myUserFund.setTradeTime(date);
                    myUserFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                    myUserFund.setAccountType(BaseConstant.ACCOUNT_TYPE_8);
                    myUserFund.setPayType(BaseConstant.PAY_TYPE_3);
                    myUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_3);
                    myUserFundList.add(myUserFund);
                }
                // 2：余额支付
                if (userFund.getPayType().equals(BaseConstant.PAY_TYPE_4)) {
                    UserInfo userInfo = userInfoService.selectById(shopGoodsOrder.getSubmitOrderUser());
                    if (userInfo == null) {
                        log.info("该用户不存在!订单ID为：" + shopGoodsOrder.getId());
                        throw new OptimisticLockingFailureException("该用户不存在!订单ID为：" + shopGoodsOrder.getId());
                    }
                    userInfo.setBalance1(userInfo.getBalance1().subtract(increaseMoney).add(decreaseMoney));
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
                    myUserFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_3);
                    myUserFundList.add(myUserFund);
                }
                // 3：优惠券
            }
            // 更新订单
            if (shopGoodsOrder.getOrderStatus() == BaseConstant.ORDER_STATUS_1) {
                shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_12);
            }
            // 更新订单已使用
            shopGoodsOrder.setIsRead(1);
            boolean update = shopGoodsOrderService.update(shopGoodsOrder, new EntityWrapper<ShopGoodsOrder>()
                    .eq("id", shopGoodsOrder.getId()));
            if (!update) {
                log.info("更新订单失败！订单ID为：" + shopGoodsOrder.getId());
                throw new OptimisticLockingFailureException("更新订单失败！订单ID为：" + shopGoodsOrder.getId());
            }
            // 直接下单的订单返还商品库存、包括库存发货
            List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = shopGoodsOrderDetailService.selectList(
                    new EntityWrapper<ShopGoodsOrderDetail>()
                            .eq("order_id", shopGoodsOrder.getId()));
            for (ShopGoodsOrderDetail shopGoodsOrderDetail : shopGoodsOrderDetailList) {
                if (!shopGoodsOrder.getOrderSource().equals(BaseConstant.ORDER_SOURCE_2)) {
                    // 返还商品库存
                    boolean b = shopGoodsService.updateStockById(shopGoodsOrderDetail.getBuyCount() * -1, shopGoodsOrderDetail.getShopGoodsId());
                    if (!b) {
                        log.info("更新商品库存失败！订单ID为：" + shopGoodsOrder.getId());
                        throw new OptimisticLockingFailureException("更新商品库存失败！订单ID为：" + shopGoodsOrder.getId());
                    }
                } else {
                    // 返还库存
                    boolean b1 = shopStockService.updateMyShopStockByGoodsId(shopGoodsOrderDetail.getBuyCount() * -1,
                            shopGoodsOrderDetail.getShopGoodsId(), shopGoodsOrder.getSubmitOrderUser());
                    if (!b1) {
                        log.info("更新库存失败！订单ID为：" + shopGoodsOrder.getId());
                        throw new OptimisticLockingFailureException("更新库存失败！订单ID为：" + shopGoodsOrder.getId());
                    }
                    // 存入入库流水
                    UserFund userFund = new UserFund();
                    userFund.setTradeUserId(shopGoodsOrder.getSubmitOrderUser());
                    userFund.setDecreaseMoney(new BigDecimal(shopGoodsOrderDetail.getBuyCount()));
                    userFund.setOrderNo(shopGoodsOrder.getOrderNo());
                    userFund.setTradeType(BaseConstant.TRADE_TYPE_1);
                    userFund.setTradeDescribe("库存退还");
                    userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_10);
                    userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_12);
                    userFund.setPayType(BaseConstant.PAY_TYPE_10);
                    userFund.setTradeTime(date);
                    userFundService.InsertUserFund(userFund);
                }
            }
            // 插入流水
            for (UserFund userFund : myUserFundList) {
                userFundService.InsertUserFund(userFund);
            }
        }
        log.info("---------------------" + "逾期订单处理完毕" + "----------------------------");
    }

    public void returnOverduePayments(ShopGoodsOrder shopGoodsOrder) {
        log.info("---------------------" + "开始处理返还逾期订单" + "----------------------------");
        Date date = new Date();
        List<UserFund> myUserFundList = new ArrayList<>();
        // 查询所有该订单产生的流水
        List<UserFund> userFundList = userFundService.selectList(
                new EntityWrapper<UserFund>().eq("order_no", shopGoodsOrder.getOrderNo()));
        for (UserFund userFund : userFundList) {
            // 支出金额
            BigDecimal decreaseMoney = userFund.getDecreaseMoney();
            // 收益金额
            BigDecimal increaseMoney = userFund.getIncreaseMoney();
            // 1：权额支付 取支出的条目
            if (userFund.getPayType().equals(BaseConstant.PAY_TYPE_3) && decreaseMoney != null && decreaseMoney.compareTo(BigDecimal.ZERO) > 0) {
                String levelId = userFund.getTradeDescribe().substring(0, 1);
                // 权额支付id、等级和金额
                AdvanceRechargeRecord advanceRechargeRecord = advanceRechargeRecordService.selectOne(
                        new EntityWrapper<AdvanceRechargeRecord>()
                                .eq("user_id", shopGoodsOrder.getSubmitOrderUser())
                                .eq("level_id", levelId));
                if (advanceRechargeRecord == null) {
                    log.info("该用户不存在等级为：" + levelId + "的权额！订单ID为" + shopGoodsOrder.getId());
                    throw new OptimisticLockingFailureException("该用户不存在等级为：" + levelId + "的权额！订单ID为" + shopGoodsOrder.getId());
                }
                advanceRechargeRecord.setAmount(decreaseMoney);
                advanceRechargeRecord.setUpdateTime(date);
//                boolean update = advanceRechargeRecordService.update(advanceRechargeRecord, new EntityWrapper<AdvanceRechargeRecord>()
//                        .eq("update_time", updateTime)
//                        .eq("id", advanceRechargeRecord.getId()));
                boolean update = advanceRechargeRecordService.updateMyAdvanceRecharge(advanceRechargeRecord);
                if (!update) {
                    log.info("更新权额失败！权额ID为：" + advanceRechargeRecord.getId());
                    throw new OptimisticLockingFailureException("更新权额失败！权额ID为：" + advanceRechargeRecord.getId());
                }
                // 插入流水
                userFund.setTradeDescribe("逾期订单支付成功扣款");
                myUserFundList.add(userFund);
            }
            // 2：余额支付 取支出的条目
            if (userFund.getPayType().equals(BaseConstant.PAY_TYPE_4) && decreaseMoney != null && decreaseMoney.compareTo(BigDecimal.ZERO) > 0) {
                UserInfo userInfo = userInfoService.selectById(shopGoodsOrder.getSubmitOrderUser());
                if (userInfo == null) {
                    log.info("该用户不存在!订单ID为：" + shopGoodsOrder.getId());
                    throw new OptimisticLockingFailureException("该用户不存在!订单ID为：" + shopGoodsOrder.getId());
                }
                UserInfo myUser = new UserInfo();
                myUser.setId(userInfo.getId());
                myUser.setBalance1(decreaseMoney);
                myUser.setUpdateTime(new Date());
                boolean update = userInfoService.updateMyBalance(userInfo);
                if (!update) {
                    log.info("更新用户余额失败！用户ID为：" + userInfo.getId());
                    throw new OptimisticLockingFailureException("更新用户余额失败！用户ID为：" + userInfo.getId());
                }
                // 插入流水
                userFund.setTradeDescribe("逾期订单支付成功扣款");
                myUserFundList.add(userFund);
            }
            // 3：优惠券
        }
        // 更新订单
        shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_1);
        // 更新订单未使用
        shopGoodsOrder.setIsRead(0);
        boolean update = shopGoodsOrderService.update(shopGoodsOrder, new EntityWrapper<ShopGoodsOrder>()
                .eq("id", shopGoodsOrder.getId())
                .eq("order_status", BaseConstant.ORDER_STATUS_12));
        if (!update) {
            log.info("更新订单失败！订单ID为：" + shopGoodsOrder.getId());
            throw new OptimisticLockingFailureException("更新订单失败！订单ID为：" + shopGoodsOrder.getId());
        }
        // 直接下单的订单扣除商品库存、包括库存发货
        List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = shopGoodsOrderDetailService.selectList(
                new EntityWrapper<ShopGoodsOrderDetail>()
                        .eq("order_id", shopGoodsOrder.getId()));
        for (ShopGoodsOrderDetail shopGoodsOrderDetail : shopGoodsOrderDetailList) {
            if (!shopGoodsOrder.getOrderSource().equals(BaseConstant.ORDER_SOURCE_2)) {
                // 返还商品库存
                boolean b = shopGoodsService.updateStockById(shopGoodsOrderDetail.getBuyCount(), shopGoodsOrderDetail.getShopGoodsId());
                if (!b) {
                    log.info("更新商品库存失败！订单ID为：" + shopGoodsOrder.getId());
                    throw new OptimisticLockingFailureException("更新商品库存失败！订单ID为：" + shopGoodsOrder.getId());
                }
            } else {
                // 返还库存
                boolean b1 = shopStockService.updateMyShopStockByGoodsId(shopGoodsOrderDetail.getBuyCount(),
                        shopGoodsOrderDetail.getShopGoodsId(), shopGoodsOrder.getSubmitOrderUser());
                if (!b1) {
                    log.info("更新库存失败！订单ID为：" + shopGoodsOrder.getId());
                    throw new OptimisticLockingFailureException("更新库存失败！订单ID为：" + shopGoodsOrder.getId());
                }
                // 存入入库流水
                UserFund userFund = new UserFund();
                userFund.setTradeUserId(shopGoodsOrder.getSubmitOrderUser());
                userFund.setDecreaseMoney(new BigDecimal(shopGoodsOrderDetail.getBuyCount()));
                userFund.setOrderNo(shopGoodsOrder.getOrderNo());
                userFund.setTradeType(BaseConstant.TRADE_TYPE_2);
                userFund.setTradeDescribe("逾期订单支付成功库存扣除");
                userFund.setAccountType(BaseConstant.ACCOUNT_TYPE_10);
                userFund.setBussinessType(BaseConstant.BUSSINESS_TYPE_11);
                userFund.setPayType(BaseConstant.PAY_TYPE_10);
                userFund.setTradeTime(date);
                myUserFundList.add(userFund);
            }
        }
        // 插入流水
        for (UserFund userFund : myUserFundList) {
            userFundService.InsertUserFund(userFund);
        }
        log.info("---------------------处理返还逾期订单结束----------------------------");
    }
}
