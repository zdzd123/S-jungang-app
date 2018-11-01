package com.jgzy.core.shopOrder.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.service.IUserAddressService;
import com.jgzy.core.personalCenter.service.IUserAddressShipService;
import com.jgzy.core.personalCenter.service.IUserOauthService;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
import com.jgzy.core.shopOrder.service.IShopGoodsOrderDetailService;
import com.jgzy.core.shopOrder.service.IShopGoodsOrderService;
import com.jgzy.core.shopOrder.service.IShopStockService;
import com.jgzy.core.shopOrder.vo.ShopStockVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.common.WeiXinData;
import com.jgzy.entity.common.WeiXinTradeType;
import com.jgzy.entity.po.*;
import com.jgzy.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-31
 */
@RefreshScope
@RestController
@RequestMapping("/api/shopStock")
@Api(value = "库存接口", description = "库存接口")
public class ShopStockController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IShopStockService shopStockService;
    @Autowired
    private IShopGoodsOrderService shopGoodsOrderService;
    @Autowired
    private IShopGoodsOrderDetailService shopGoodsOrderDetailService;
    @Autowired
    private IUserOauthService userOauthService;
    @Autowired
    private IUserAddressService userAddressService;
    @Autowired
    private IUserAddressShipService userAddressShipService;

    @GetMapping(value = "/detail")
    @ApiOperation(value = "获取我的库存", notes = "获取我的库存")
    public ResultWrapper<ShopStockVo> detail(@ApiParam(value = "平台分类id") @RequestParam(required = false) Integer platformGoodsCategoryId,
                                             @ApiParam(value = "商品名称") @RequestParam(required = false) String shopName) {
        ResultWrapper<ShopStockVo> resultWrapper = new ResultWrapper<>();
        ShopStockVo shopStockVo = shopStockService.selectMyStock(platformGoodsCategoryId, shopName);

        resultWrapper.setResult(shopStockVo);
        return resultWrapper;
    }

    @ApiOperation(value = "库存发货计算耗材费", notes = "库存发货计算耗材费")
    @GetMapping(value = "/calcAmountForShow")
    public ResultWrapper<BigDecimal> calcAmountForShow(@ApiParam(value = "商品数量", required = true) @RequestParam Integer count) {
        ResultWrapper<BigDecimal> resultWrapper = new ResultWrapper<>();

        BigDecimal materialAmount = calcMaterialCosts(count, new BigDecimal(0));

        resultWrapper.setResult(materialAmount);
        return resultWrapper;
    }

    @ApiOperation(value = "库存发货生成订单", notes = "库存发货生成订单")
    @PostMapping(value = "/weixinPay")
    @Transactional
    public ResultWrapper<Map<String, String>> weixinPay(@RequestBody @Validated List<ShopStockVo> voList) throws Exception {
        ResultWrapper<Map<String, String>> resultWrapper = new ResultWrapper<>();
        Map<String, String> resultMap = new HashMap<>();
        // 运费标识
        Integer carriageType = voList.get(0).getCarriageType();
        // 订单号
        String tradeNo = BaseConstant.PRE_ORDER_STOCK + CommonUtil.getTradeNo();
        Date date = new Date();
        // 订单
        ShopGoodsOrder shopGoodsOrder = new ShopGoodsOrder();
        shopGoodsOrder.setOrderNo(BaseConstant.PRE_ORDER_STOCK + tradeNo);
        shopGoodsOrder.setSubmitOrderUser(UserUuidThreadLocal.get().getId());
        shopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_1);
        shopGoodsOrder.setCreateTime(date);
        // 收货地址和发货地址
        initAddress(shopGoodsOrder, voList.get(0).getAddressId(), voList.get(0).getShipAddressId());
        int count = 0;
        List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = new ArrayList<>();
        List<ShopStock> shopStockList = new ArrayList<>();
        for (ShopStockVo shopStockVo : voList) {
            ShopGoodsOrderDetail shopGoodsOrderDetail = new ShopGoodsOrderDetail();
            ShopStock shopStock = shopStockService.selectById(shopStockVo.getId());
            if (shopStock == null) {
                throw new OptimisticLockingFailureException("不存在该库存！");
            }
            if (shopStock.getCount().compareTo(shopStockVo.getCount()) < 0) {
                throw new OptimisticLockingFailureException("库存不足！");
            }
            shopGoodsOrderDetail.setShopGoodsId(shopStock.getShopGoodsId());
            shopGoodsOrderDetail.setBuyCount(shopStockVo.getCount());
            shopGoodsOrderDetail.setAddTime(date);
            shopGoodsOrderDetailList.add(shopGoodsOrderDetail);
            count += shopStockVo.getCount();
            //库存
            shopStock.setUpdateTime(date);
            shopStock.setCount(shopStock.getCount() - shopStockVo.getCount());
            shopStockList.add(shopStock);
        }
        BigDecimal materialCosts = calcMaterialCosts(count, new BigDecimal(0));
        shopGoodsOrder.setMaterialAmount(materialCosts);
        if (carriageType.equals(2)) {
            // 等待计算
            shopGoodsOrder.setCarriageType(BaseConstant.CARRIAGE_TYPE_2);
        } else if (carriageType.equals(1)) {
            // 到付 weixin支付
            shopGoodsOrder.setTradeNo(tradeNo);
            shopGoodsOrder.setPayType(BaseConstant.PAY_TYPE_2);
            shopGoodsOrder.setCarriageType(BaseConstant.CARRIAGE_TYPE_1);
            shopGoodsOrder.setOrderAmountTotal(materialCosts);
            shopGoodsOrder.setTotalRealPayment(materialCosts);
            String ip = InetAddress.getLocalHost().getHostAddress();
            resultMap = weiXinPay(ip, tradeNo, materialCosts, "");
        }
        boolean insert = shopGoodsOrderService.insert(shopGoodsOrder);
        if (!insert) {
            throw new OptimisticLockingFailureException("订单插入失败！");
        }
        shopGoodsOrderDetailList.forEach(shopGoodsOrderDetail -> shopGoodsOrderDetail.setOrderId(shopGoodsOrder.getId()));
        boolean b = shopGoodsOrderDetailService.insertBatch(shopGoodsOrderDetailList);
        if (!b) {
            throw new OptimisticLockingFailureException("订单详情插入失败！");
        }
        resultWrapper.setResult(resultMap);
        return resultWrapper;
    }

    /**
     * 拼接地址
     *
     * @param shopGoodsOrder
     * @param addressId
     * @param shipAddressId
     * @return
     */
    private boolean initAddress(ShopGoodsOrder shopGoodsOrder, Integer addressId, Integer shipAddressId) {
        UserAddressVo userAddressVo = userAddressService.selectDetailById(addressId);
        if (userAddressVo == null) {
            return false;
        }
        UserAddressVo userAddressShip = userAddressShipService.selectDetailById(shipAddressId);
        if (userAddressShip == null) {
            return false;
        }
        // 拼接地址
        shopGoodsOrder.setReceiveMan(userAddressVo.getName());
        shopGoodsOrder.setContactPhone(userAddressVo.getPhone());
        String address = userAddressVo.getProvince() +
                userAddressVo.getCity() +
                userAddressVo.getArea() +
                userAddressVo.getAddress();
        shopGoodsOrder.setReceiveAddress(address);
        shopGoodsOrder.setShipper(userAddressShip.getName());
        shopGoodsOrder.setShipperPhone(userAddressShip.getPhone());
        shopGoodsOrder.setShipperAddress(userAddressShip.getProvince() +
                userAddressShip.getCity() +
                userAddressShip.getArea() +
                userAddressShip.getAddress());
        return true;
    }


    /**
     * 微信支付
     *
     * @param ip
     * @param out_trade_no
     * @param actualAmount
     * @param order_ids
     * @return
     * @throws IOException
     */
    private Map<String, String> weiXinPay(String ip, String out_trade_no, BigDecimal actualAmount, String order_ids) throws IOException {
        Map<String, String> resultMap = new HashMap<>();
        // 权额不足 唤起第三方支付
        // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        String subject = "军港之业库存订单"; // 订单名称 (必填)
        // 获取服务器ip
        UserOauth userOauth = userOauthService.selectOne(
                new EntityWrapper<UserOauth>()
                        .eq("user_id", UserUuidThreadLocal.get().getId()));
        if (userOauth == null || userOauth.getOauthOpenid() == null) {
            resultMap.put("return_code", "FAIL");
            resultMap.put("err_code_des", "openid is null");
            return resultMap;
        }
        String openid = userOauth.getOauthOpenid(); // 微信认证 openid (必填)
        String product_id = ""; // 产品id (非必填)
        String notify_url = "http://jgapi.china-mail.com.cn/api/shopStock/constant/weixinNotifyUrl";
        // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
        // 检验订单状态以及订单的金额
        WeiXinData wxData = WeiXinPayUtil.makePreOrder(WeiXinTradeType.JSAPI, openid, product_id,
                out_trade_no, subject, actualAmount.doubleValue(), ip, notify_url);
        // 订单失败
        if (wxData.hasKey("return_code") && wxData.get("return_code").equals("FAIL")) {
            resultMap.put("return_code", wxData.get("return_code"));
            if (wxData.get("return_msg") != null) {
                resultMap.put("return_msg", wxData.get("return_msg"));
            } else if (wxData.get("err_code_des") != null) {
                resultMap.put("err_code_des", wxData.get("err_code_des"));
            }
            return resultMap;
        }
        resultMap.put("appId", wxData.get("appId"));
        resultMap.put("nonceStr", wxData.get("nonceStr"));
        resultMap.put("timeStamp", wxData.get("timeStamp"));
        resultMap.put("signType", wxData.get("signType"));
        resultMap.put("packageValue", wxData.get("package"));
        resultMap.put("sign", wxData.get("sign"));
        resultMap.put("order_no", out_trade_no);
        resultMap.put("order_ids", order_ids);
        return resultMap;
    }

    /**
     * 计算耗材费
     *
     * @param count         个数
     * @param materialCosts 金额
     * @return 耗材费
     */
    private BigDecimal calcMaterialCosts(int count, BigDecimal materialCosts) {
        if (count > 24) {
            return materialCosts;
        }
        switch (count) {
            case 1:
                materialCosts = BigDecimalUtil.add(materialCosts, 3);
                break;
            case 2:
                materialCosts = materialCosts.add(BigDecimalUtil.mul(2.4, count));
                break;
            case 3:
                materialCosts = materialCosts.add(BigDecimalUtil.mul(2, count));
                break;
            case 4:
                materialCosts = materialCosts.add(BigDecimalUtil.mul(1.8, count));
                break;
            case 5:
                materialCosts = materialCosts.add(BigDecimalUtil.mul(1.5, count));
                break;
            case 6:
                materialCosts = materialCosts.add(BigDecimalUtil.mul(1.4, count));
                break;
            default:
                int remainder = count % 6;
                materialCosts = BigDecimalUtil.mul(1.4, count - remainder);
                if (remainder != 0){
                    materialCosts = calcMaterialCosts(remainder, materialCosts);
                }
                break;
        }
        return materialCosts;
    }

    @ApiOperation(value = "微信回调接口", notes = "微信回调接口")
    @PostMapping(value = "/constant/weixinNotifyUrl")
    @Transactional
    public String weixinNotifyUrl(HttpServletRequest request) {
//        WeiXinNotify notify = WeiXinPayUtil.notify_url(request);
//        System.out.println("notify-------------------------------------------------------" + notify);
//        String outTradeNo = notify.getOut_trade_no(); // 商户网站订单系统中唯一订单号
//        BigDecimal totalAmount = new BigDecimal(notify.getTotal_fee()); // 付款金额

        WeiXinNotify notify = new WeiXinNotify();
        String outTradeNo = "KC110109052816156";
        BigDecimal totalAmount = new BigDecimal("3");

        // 校验订单
        ShopGoodsOrder shopGoodsOrder = shopGoodsOrderService.selectOne(
                new EntityWrapper<ShopGoodsOrder>().eq("trade_no", outTradeNo));
        if (shopGoodsOrder == null) {
            logger.info("-----------------------OutTradeNo fail------------------------------");
            notify.setResultFail("OutTradeNo fail");
            return notify.getBodyXML();
        }
        // 判断金额是否正确
        if (shopGoodsOrder.getTotalRealPayment().compareTo(totalAmount) != 0) {
            logger.info("-----------------------TotalAmount fail------------------------------");
            notify.setResultFail("TotalAmount fail");
            return notify.getBodyXML();
        }
        // 更新订单
        ShopGoodsOrder myShopGoodsOrder = new ShopGoodsOrder();
        myShopGoodsOrder.setId(shopGoodsOrder.getId());
        myShopGoodsOrder.setOrderStatus(BaseConstant.ORDER_STATUS_3);
        myShopGoodsOrder.setPayTime(new Date());
        boolean updateById = shopGoodsOrderService.updateById(myShopGoodsOrder);
        if (!updateById) {
            logger.info("-----------------------updateOrder fail------------------------------");
            notify.setResultFail("updateOrder fail");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notify.getBodyXML();
        }
        // 更新库存
        List<ShopGoodsOrderDetail> shopGoodsOrderDetailList = shopGoodsOrderDetailService.selectList(
                new EntityWrapper<ShopGoodsOrderDetail>()
                        .eq("order_id", shopGoodsOrder.getId()));
        for (ShopGoodsOrderDetail shopGoodsOrderDetail : shopGoodsOrderDetailList) {
            ShopStock shopStock = shopStockService.selectOne(new EntityWrapper<ShopStock>()
                    .eq("user_info_id", shopGoodsOrder.getSubmitOrderUser())
                    .eq("shop_goods_id", shopGoodsOrderDetail.getShopGoodsId())
                    .ge("count", shopGoodsOrderDetail.getBuyCount()));
            if (shopStock == null) {
                logger.info("-----------------------shopStock fail------------------------------");
                notify.setResultFail("shopStock fail");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return notify.getBodyXML();
            }
            shopStock.setCount(shopStock.getCount() - shopGoodsOrderDetail.getBuyCount());
            shopStock.setUpdateTime(new Date());
            boolean c = shopStockService.updateById(shopStock);
            if (!c) {
                logger.info("-----------------------shopStock fail------------------------------");
                notify.setResultFail("shopStock fail");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return notify.getBodyXML();
            }
        }
        logger.info("-----------------------微信回调接口结束------------------------------");
        notify.setResultSuccess();
        return notify.getBodyXML();
    }

    @ApiOperation(value = "库存订单支付（重新计算运费）", notes = "库存订单支付（重新计算运费）")
    @GetMapping(value = "/weixinPayForOrder")
    @Transactional
    public ResultWrapper<Map<String, String>> weixinPayForOrder(@ApiParam(value = "订单编号", required = true) @RequestParam Integer orderId) throws Exception{
        ResultWrapper<Map<String, String>> resultWrapper = new ResultWrapper<>();
        Map<String, String> resultMap = new HashMap<>();
        ShopGoodsOrder shopGoodsOrder = shopGoodsOrderService.selectById(orderId);
        if (!shopGoodsOrder.getOrderStatus().equals(BaseConstant.ORDER_STATUS_1)) {
            throw new OptimisticLockingFailureException("订单不是支付状态");
        }
        if (shopGoodsOrder.getCarriageType().equals(BaseConstant.CARRIAGE_TYPE_2) &&
                shopGoodsOrder.getCarriage() != null && shopGoodsOrder.getCarriage().compareTo(new BigDecimal(0)) > 0){
            throw new OptimisticLockingFailureException("订单运费有误");
        }
        shopGoodsOrder.setTotalRealPayment(shopGoodsOrder.getMaterialAmount().add(shopGoodsOrder.getCarriage()));
        String ip = InetAddress.getLocalHost().getHostAddress();
        // 微信支付
        resultMap = weiXinPay(ip, shopGoodsOrder.getTradeNo(), shopGoodsOrder.getTotalRealPayment(), "");
        resultWrapper.setResult(resultMap);
        return resultWrapper;
    }
}

