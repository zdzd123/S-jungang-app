package com.jgzy.core.shopOrder.vo;

import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("库存总商品耗材费-vo")
public class CalcStockAmountVo {
    // 运费
    private BigDecimal carriage;
    // 耗材费
    private BigDecimal materialAmount;
    // 单个商品
    private List<ShopStockVo> singleStockVoList;

    public BigDecimal getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(BigDecimal materialAmount) {
        this.materialAmount = materialAmount;
    }

    public List<ShopStockVo> getSingleStockVoList() {
        return singleStockVoList;
    }

    public void setSingleStockVoList(List<ShopStockVo> singleStockVoList) {
        this.singleStockVoList = singleStockVoList;
    }

    public BigDecimal getCarriage() {
        return carriage;
    }

    public void setCarriage(BigDecimal carriage) {
        this.carriage = carriage;
    }
}
