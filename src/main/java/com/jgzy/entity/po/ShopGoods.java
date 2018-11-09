package com.jgzy.entity.po;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 商品信息
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@ApiModel("商品信息-po")
@TableName("shop_goods")
public class ShopGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 店铺信息id
     */
    @ApiModelProperty(value = "店铺信息id", example = "1")
    private Integer shopInfoId;
    /**
     * 商品名
     */
    @ApiModelProperty(value = "商品名", example = "巴朗波尔多干红葡萄酒")
    private String shopName;
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号", example = "201810161519120411")
    private String shopNum;
    /**
     * 平台商品分类id
     */
    @ApiModelProperty(value = "平台商品分类id", example = "1")
    private Integer platformGoodsCategoryId;
    /**
     * 平台商品品牌id
     */
    @ApiModelProperty(value = "平台商品品牌id", example = "1")
    private Integer platformGoodsBrandId;
    /**
     * 店铺内商品分类id
     */
    @ApiModelProperty(value = "店铺内商品分类id", example = "1")
    private Integer shopGoodsCategoryId;
    /**
     * 市场价
     */
    @ApiModelProperty(value = "市场价", example = "100")
    private BigDecimal marketPrice;
    /**
     * 销售价
     */
    @ApiModelProperty(value = "销售价", example = "100")
    private BigDecimal salePrice;
    /**
     * 库存
     */
    @ApiModelProperty(value = "库存", example = "1")
    private Integer stock;
    @ApiModelProperty(value = "tradingPoints", example = "1")
    private Integer tradingPoints;
    /**
     * 返利
     */
    @ApiModelProperty(value = "返利(0不分销)", example = "1")
    private Integer rebate;
    @ApiModelProperty(value = "商品计量单位(件=1|重量=2|体积=3)", example = "1")
    private Integer unit;
    @ApiModelProperty(value = "商品重量(kg)", example = "1")
    private BigDecimal weight;
    @ApiModelProperty(value = "商品体积(m³)", example = "1")
    private BigDecimal cubage;
    /**
     * 库存方式（0库存销售=1|0库存自动下架=2）
     */
    @ApiModelProperty(value = "库存方式（0库存销售=1|0库存自动下架=2）", example = "1")
    private Integer stockType;
    /**
     * 运费(卖家承担运费=1|买家承担运费=2)
     */
    @ApiModelProperty(value = "运费(卖家承担运费=1|买家承担运费=2)", example = "1")
    private BigDecimal freight;
    /**
     * 运费模板id
     */
    @ApiModelProperty(value = "运费模板id", example = "1")
    private Integer shopExpressCostTempletId;
    @ApiModelProperty(value = "平邮(元)", example = "1")
    private BigDecimal snailMail;
    @ApiModelProperty(value = "快递(元)", example = "1")
    private BigDecimal fastMail;
    @ApiModelProperty(value = "EMS(元)", example = "1")
    private BigDecimal ems;
    @ApiModelProperty(value = "是否虚拟物品(否=1|是=2", example = "1")
    private Integer virtualGoods;
    @ApiModelProperty(value = "id", example = "1")
    private Integer isPresell;
    @ApiModelProperty(value = "id", example = "1")
    private Integer isGroupbuy;
    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述", example = "商品描述")
    private String shopDescribe;
    /**
     * 图片(多张以","分隔)
     */
    @ApiModelProperty(value = "图片(多张以\",\"分隔)", example = "1.jpg")
    private String pic;
    /**
     * 状态(未上架=1|已上架=2)
     */
    @ApiModelProperty(value = "状态(未上架=1|已上架=2|已下架=3)", example = "1")
    private Integer status;
    /**
     * 上架时间
     */
    @ApiModelProperty(value = "上架时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date groundingTime;
    /**
     * 下架时间
     */
    @ApiModelProperty(value = "下架时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date undercarriageTime;
    /**
     * 访问数
     */
    @ApiModelProperty(value = "访问数", example = "1")
    private Integer accessNumber;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releaseTime;
    @ApiModelProperty(value = "shopGoodsVideo", example = "shopGoodsVideo")
    private String shopGoodsVideo;
    @ApiModelProperty(value = "lastLiveId", example = "1")
    private Integer lastLiveId;
    /**
     * 索引码
     */
    @ApiModelProperty(value = "索引码", example = "1")
    private String indexCode;
    @ApiModelProperty(value = "isLimit", example = "1")
    private Integer isLimit;
    /**
     * 限购开始时间
     */
    @ApiModelProperty(value = "限购开始时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date limitStartTime;
    /**
     * 限购结束时间
     */
    @ApiModelProperty(value = "限购结束时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date limitEndTime;
    /**
     * 限购数量
     */
    @ApiModelProperty(value = "限购数量", example = "1")
    private Integer limitNum;
    @ApiModelProperty(value = "limitExplain", example = "limitExplain")
    private String limitExplain;
    /**
     * 成本价
     */
    @ApiModelProperty(value = "成本价", example = "1")
    private BigDecimal costPrice;
    /**
     * 会员价
     */
    @ApiModelProperty(value = "会员价", example = "1")
    private BigDecimal menberPrice;
    @ApiModelProperty(value = "grossProfit", example = "1")
    private BigDecimal grossProfit;
    @ApiModelProperty(value = "royaltyRatio", example = "1")
    private BigDecimal royaltyRatio;
    @ApiModelProperty(value = "brand", example = "1")
    private String brand;
    @ApiModelProperty(value = "商品单位", example = "1")
    private String goodsUnit;
    @ApiModelProperty(value = "箱量", example = "1")
    private Integer goodsUnitCount;
    @ApiModelProperty(value = "goodsBarcode", example = "1")
    private String goodsBarcode;
    @ApiModelProperty(value = "shopBarcode", example = "1")
    private String shopBarcode;
    @TableField("goods_QR_code")
    @ApiModelProperty(value = "goodsQrCode", example = "1")
    private String goodsQrCode;
    @ApiModelProperty(value = "labels", example = "1")
    private String labels;
    @ApiModelProperty(value = "keywords", example = "1")
    private String keywords;
    @ApiModelProperty(value = "point", example = "1")
    private Integer point;
    @ApiModelProperty(value = "appSalesAmount", example = "1")
    private Integer appSalesAmount;
    @ApiModelProperty(value = "appCollectAmount", example = "1")
    private Integer appCollectAmount;
    @ApiModelProperty(value = "appClickAmount", example = "1")
    private Integer appClickAmount;
    @ApiModelProperty(value = "appPvAmount", example = "1")
    private Integer appPvAmount;
    @ApiModelProperty(value = "channel", example = "1")
    private Integer channel;
    @ApiModelProperty(value = "remarks", example = "1")
    private String remarks;
    @ApiModelProperty(value = "abstracts", example = "1")
    private String abstracts;
    @ApiModelProperty(value = "homePageSort", example = "1")
    private Integer homePageSort;
    @ApiModelProperty(value = "listSort", example = "1")
    private Integer listSort;
    @ApiModelProperty(value = "weixinSalesAmount", example = "1")
    private Integer weixinSalesAmount;
    @ApiModelProperty(value = "weixinCollectAmount", example = "1")
    private Integer weixinCollectAmount;
    @ApiModelProperty(value = "weixinClickAmount", example = "1")
    private Integer weixinClickAmount;
    @ApiModelProperty(value = "weixinPvAmount", example = "1")
    private Integer weixinPvAmount;
    @ApiModelProperty(value = "liveSalesAmount", example = "1")
    private Integer liveSalesAmount;
    @ApiModelProperty(value = "liveCollectAmount", example = "1")
    private Integer liveCollectAmount;
    @ApiModelProperty(value = "liveClickAmount", example = "1")
    private Integer liveClickAmount;
    @ApiModelProperty(value = "livePvAmount", example = "1")
    private Integer livePvAmount;
    @ApiModelProperty(value = "meitiSalesAmount", example = "1")
    private Integer meitiSalesAmount;
    @ApiModelProperty(value = "meitiCollectAmount", example = "1")
    private Integer meitiCollectAmount;
    @ApiModelProperty(value = "meitiClickAmount", example = "1")
    private Integer meitiClickAmount;
    @ApiModelProperty(value = "meitiPvAmount", example = "1")
    private Integer meitiPvAmount;
    @ApiModelProperty(value = "jingdongSalesAmount", example = "1")
    private Integer jingdongSalesAmount;
    @ApiModelProperty(value = "jingdongCollectAmount", example = "1")
    private Integer jingdongCollectAmount;
    @ApiModelProperty(value = "jingdongClickAmount", example = "1")
    private Integer jingdongClickAmount;
    @ApiModelProperty(value = "jingdongPvAmount", example = "1")
    private Integer jingdongPvAmount;
    @ApiModelProperty(value = "tianmaoSalesAmount", example = "1")
    private Integer tianmaoSalesAmount;
    @ApiModelProperty(value = "tianmaoCollectAmount", example = "1")
    private Integer tianmaoCollectAmount;
    @ApiModelProperty(value = "tianmaoClickAmount", example = "1")
    private Integer tianmaoClickAmount;
    @ApiModelProperty(value = "tianmaoPvAmount", example = "1")
    private Integer tianmaoPvAmount;
    @ApiModelProperty(value = "location", example = "1")
    private String location;
    @ApiModelProperty(value = "isLock", example = "1")
    private Integer isLock;
    @ApiModelProperty(value = "pointExchange", example = "1")
    private Integer pointExchange;
    @ApiModelProperty(value = "needPoint", example = "1")
    private Integer needPoint;
    @ApiModelProperty(value = "commissionLevelOne", example = "1")
    private BigDecimal commissionLevelOne;
    @ApiModelProperty(value = "commissionLevelTwo", example = "1")
    private BigDecimal commissionLevelTwo;
    @ApiModelProperty(value = "commissionOnOff", example = "1")
    private Integer commissionOnOff;
    @ApiModelProperty(value = "supplierLabel", example = "1")
    private String supplierLabel;
    @ApiModelProperty(value = "platformLabel", example = "1")
    private String platformLabel;
    @ApiModelProperty(value = "reservedCoding2", example = "1")
    private String reservedCoding2;
    @ApiModelProperty(value = "reservedCoding3", example = "1")
    private String reservedCoding3;
    @ApiModelProperty(value = "barcodeReservation2", example = "1")
    private String barcodeReservation2;
    @ApiModelProperty(value = "barcodeReservation3", example = "1")
    private String barcodeReservation3;
    @ApiModelProperty(value = "festivalIds", example = "1")
    private String festivalIds;
    @ApiModelProperty(value = "objectIds", example = "1")
    private String objectIds;
    @ApiModelProperty(value = "provinceId", example = "1")
    private Integer provinceId;
    /**
     * 礼包id
     */
    @ApiModelProperty(value = "礼包id", example = "1")
    private Integer packageId;
    /**
     * 0-非特定 ，1特定商品
     */
    @ApiModelProperty(value = "0-非特定 ，1特定商品", example = "1")
    private Integer isSpecial;
    @ApiModelProperty(value = "reservedCoding1", example = "1")
    private String reservedCoding1;
    @ApiModelProperty(value = "barcodeReservation1", example = "1")
    private String barcodeReservation1;

    public Integer getGoodsUnitCount() {
        return goodsUnitCount;
    }

    public void setGoodsUnitCount(Integer goodsUnitCount) {
        this.goodsUnitCount = goodsUnitCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopInfoId() {
        return shopInfoId;
    }

    public void setShopInfoId(Integer shopInfoId) {
        this.shopInfoId = shopInfoId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopNum() {
        return shopNum;
    }

    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }

    public Integer getPlatformGoodsCategoryId() {
        return platformGoodsCategoryId;
    }

    public void setPlatformGoodsCategoryId(Integer platformGoodsCategoryId) {
        this.platformGoodsCategoryId = platformGoodsCategoryId;
    }

    public Integer getPlatformGoodsBrandId() {
        return platformGoodsBrandId;
    }

    public void setPlatformGoodsBrandId(Integer platformGoodsBrandId) {
        this.platformGoodsBrandId = platformGoodsBrandId;
    }

    public Integer getShopGoodsCategoryId() {
        return shopGoodsCategoryId;
    }

    public void setShopGoodsCategoryId(Integer shopGoodsCategoryId) {
        this.shopGoodsCategoryId = shopGoodsCategoryId;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getTradingPoints() {
        return tradingPoints;
    }

    public void setTradingPoints(Integer tradingPoints) {
        this.tradingPoints = tradingPoints;
    }

    public Integer getRebate() {
        return rebate;
    }

    public void setRebate(Integer rebate) {
        this.rebate = rebate;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getCubage() {
        return cubage;
    }

    public void setCubage(BigDecimal cubage) {
        this.cubage = cubage;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Integer getShopExpressCostTempletId() {
        return shopExpressCostTempletId;
    }

    public void setShopExpressCostTempletId(Integer shopExpressCostTempletId) {
        this.shopExpressCostTempletId = shopExpressCostTempletId;
    }

    public BigDecimal getSnailMail() {
        return snailMail;
    }

    public void setSnailMail(BigDecimal snailMail) {
        this.snailMail = snailMail;
    }

    public BigDecimal getFastMail() {
        return fastMail;
    }

    public void setFastMail(BigDecimal fastMail) {
        this.fastMail = fastMail;
    }

    public BigDecimal getEms() {
        return ems;
    }

    public void setEms(BigDecimal ems) {
        this.ems = ems;
    }

    public Integer getVirtualGoods() {
        return virtualGoods;
    }

    public void setVirtualGoods(Integer virtualGoods) {
        this.virtualGoods = virtualGoods;
    }

    public Integer getIsPresell() {
        return isPresell;
    }

    public void setIsPresell(Integer isPresell) {
        this.isPresell = isPresell;
    }

    public Integer getIsGroupbuy() {
        return isGroupbuy;
    }

    public void setIsGroupbuy(Integer isGroupbuy) {
        this.isGroupbuy = isGroupbuy;
    }

    public String getShopDescribe() {
        return shopDescribe;
    }

    public void setShopDescribe(String shopDescribe) {
        this.shopDescribe = shopDescribe;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGroundingTime() {
        return groundingTime;
    }

    public void setGroundingTime(Date groundingTime) {
        this.groundingTime = groundingTime;
    }

    public Date getUndercarriageTime() {
        return undercarriageTime;
    }

    public void setUndercarriageTime(Date undercarriageTime) {
        this.undercarriageTime = undercarriageTime;
    }

    public Integer getAccessNumber() {
        return accessNumber;
    }

    public void setAccessNumber(Integer accessNumber) {
        this.accessNumber = accessNumber;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getShopGoodsVideo() {
        return shopGoodsVideo;
    }

    public void setShopGoodsVideo(String shopGoodsVideo) {
        this.shopGoodsVideo = shopGoodsVideo;
    }

    public Integer getLastLiveId() {
        return lastLiveId;
    }

    public void setLastLiveId(Integer lastLiveId) {
        this.lastLiveId = lastLiveId;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public Integer getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(Integer isLimit) {
        this.isLimit = isLimit;
    }

    public Date getLimitStartTime() {
        return limitStartTime;
    }

    public void setLimitStartTime(Date limitStartTime) {
        this.limitStartTime = limitStartTime;
    }

    public Date getLimitEndTime() {
        return limitEndTime;
    }

    public void setLimitEndTime(Date limitEndTime) {
        this.limitEndTime = limitEndTime;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public String getLimitExplain() {
        return limitExplain;
    }

    public void setLimitExplain(String limitExplain) {
        this.limitExplain = limitExplain;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getMenberPrice() {
        return menberPrice;
    }

    public void setMenberPrice(BigDecimal menberPrice) {
        this.menberPrice = menberPrice;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    public BigDecimal getRoyaltyRatio() {
        return royaltyRatio;
    }

    public void setRoyaltyRatio(BigDecimal royaltyRatio) {
        this.royaltyRatio = royaltyRatio;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsBarcode() {
        return goodsBarcode;
    }

    public void setGoodsBarcode(String goodsBarcode) {
        this.goodsBarcode = goodsBarcode;
    }

    public String getShopBarcode() {
        return shopBarcode;
    }

    public void setShopBarcode(String shopBarcode) {
        this.shopBarcode = shopBarcode;
    }

    public String getGoodsQrCode() {
        return goodsQrCode;
    }

    public void setGoodsQrCode(String goodsQrCode) {
        this.goodsQrCode = goodsQrCode;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getAppSalesAmount() {
        return appSalesAmount;
    }

    public void setAppSalesAmount(Integer appSalesAmount) {
        this.appSalesAmount = appSalesAmount;
    }

    public Integer getAppCollectAmount() {
        return appCollectAmount;
    }

    public void setAppCollectAmount(Integer appCollectAmount) {
        this.appCollectAmount = appCollectAmount;
    }

    public Integer getAppClickAmount() {
        return appClickAmount;
    }

    public void setAppClickAmount(Integer appClickAmount) {
        this.appClickAmount = appClickAmount;
    }

    public Integer getAppPvAmount() {
        return appPvAmount;
    }

    public void setAppPvAmount(Integer appPvAmount) {
        this.appPvAmount = appPvAmount;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public Integer getHomePageSort() {
        return homePageSort;
    }

    public void setHomePageSort(Integer homePageSort) {
        this.homePageSort = homePageSort;
    }

    public Integer getListSort() {
        return listSort;
    }

    public void setListSort(Integer listSort) {
        this.listSort = listSort;
    }

    public Integer getWeixinSalesAmount() {
        return weixinSalesAmount;
    }

    public void setWeixinSalesAmount(Integer weixinSalesAmount) {
        this.weixinSalesAmount = weixinSalesAmount;
    }

    public Integer getWeixinCollectAmount() {
        return weixinCollectAmount;
    }

    public void setWeixinCollectAmount(Integer weixinCollectAmount) {
        this.weixinCollectAmount = weixinCollectAmount;
    }

    public Integer getWeixinClickAmount() {
        return weixinClickAmount;
    }

    public void setWeixinClickAmount(Integer weixinClickAmount) {
        this.weixinClickAmount = weixinClickAmount;
    }

    public Integer getWeixinPvAmount() {
        return weixinPvAmount;
    }

    public void setWeixinPvAmount(Integer weixinPvAmount) {
        this.weixinPvAmount = weixinPvAmount;
    }

    public Integer getLiveSalesAmount() {
        return liveSalesAmount;
    }

    public void setLiveSalesAmount(Integer liveSalesAmount) {
        this.liveSalesAmount = liveSalesAmount;
    }

    public Integer getLiveCollectAmount() {
        return liveCollectAmount;
    }

    public void setLiveCollectAmount(Integer liveCollectAmount) {
        this.liveCollectAmount = liveCollectAmount;
    }

    public Integer getLiveClickAmount() {
        return liveClickAmount;
    }

    public void setLiveClickAmount(Integer liveClickAmount) {
        this.liveClickAmount = liveClickAmount;
    }

    public Integer getLivePvAmount() {
        return livePvAmount;
    }

    public void setLivePvAmount(Integer livePvAmount) {
        this.livePvAmount = livePvAmount;
    }

    public Integer getMeitiSalesAmount() {
        return meitiSalesAmount;
    }

    public void setMeitiSalesAmount(Integer meitiSalesAmount) {
        this.meitiSalesAmount = meitiSalesAmount;
    }

    public Integer getMeitiCollectAmount() {
        return meitiCollectAmount;
    }

    public void setMeitiCollectAmount(Integer meitiCollectAmount) {
        this.meitiCollectAmount = meitiCollectAmount;
    }

    public Integer getMeitiClickAmount() {
        return meitiClickAmount;
    }

    public void setMeitiClickAmount(Integer meitiClickAmount) {
        this.meitiClickAmount = meitiClickAmount;
    }

    public Integer getMeitiPvAmount() {
        return meitiPvAmount;
    }

    public void setMeitiPvAmount(Integer meitiPvAmount) {
        this.meitiPvAmount = meitiPvAmount;
    }

    public Integer getJingdongSalesAmount() {
        return jingdongSalesAmount;
    }

    public void setJingdongSalesAmount(Integer jingdongSalesAmount) {
        this.jingdongSalesAmount = jingdongSalesAmount;
    }

    public Integer getJingdongCollectAmount() {
        return jingdongCollectAmount;
    }

    public void setJingdongCollectAmount(Integer jingdongCollectAmount) {
        this.jingdongCollectAmount = jingdongCollectAmount;
    }

    public Integer getJingdongClickAmount() {
        return jingdongClickAmount;
    }

    public void setJingdongClickAmount(Integer jingdongClickAmount) {
        this.jingdongClickAmount = jingdongClickAmount;
    }

    public Integer getJingdongPvAmount() {
        return jingdongPvAmount;
    }

    public void setJingdongPvAmount(Integer jingdongPvAmount) {
        this.jingdongPvAmount = jingdongPvAmount;
    }

    public Integer getTianmaoSalesAmount() {
        return tianmaoSalesAmount;
    }

    public void setTianmaoSalesAmount(Integer tianmaoSalesAmount) {
        this.tianmaoSalesAmount = tianmaoSalesAmount;
    }

    public Integer getTianmaoCollectAmount() {
        return tianmaoCollectAmount;
    }

    public void setTianmaoCollectAmount(Integer tianmaoCollectAmount) {
        this.tianmaoCollectAmount = tianmaoCollectAmount;
    }

    public Integer getTianmaoClickAmount() {
        return tianmaoClickAmount;
    }

    public void setTianmaoClickAmount(Integer tianmaoClickAmount) {
        this.tianmaoClickAmount = tianmaoClickAmount;
    }

    public Integer getTianmaoPvAmount() {
        return tianmaoPvAmount;
    }

    public void setTianmaoPvAmount(Integer tianmaoPvAmount) {
        this.tianmaoPvAmount = tianmaoPvAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Integer getPointExchange() {
        return pointExchange;
    }

    public void setPointExchange(Integer pointExchange) {
        this.pointExchange = pointExchange;
    }

    public Integer getNeedPoint() {
        return needPoint;
    }

    public void setNeedPoint(Integer needPoint) {
        this.needPoint = needPoint;
    }

    public BigDecimal getCommissionLevelOne() {
        return commissionLevelOne;
    }

    public void setCommissionLevelOne(BigDecimal commissionLevelOne) {
        this.commissionLevelOne = commissionLevelOne;
    }

    public BigDecimal getCommissionLevelTwo() {
        return commissionLevelTwo;
    }

    public void setCommissionLevelTwo(BigDecimal commissionLevelTwo) {
        this.commissionLevelTwo = commissionLevelTwo;
    }

    public Integer getCommissionOnOff() {
        return commissionOnOff;
    }

    public void setCommissionOnOff(Integer commissionOnOff) {
        this.commissionOnOff = commissionOnOff;
    }

    public String getSupplierLabel() {
        return supplierLabel;
    }

    public void setSupplierLabel(String supplierLabel) {
        this.supplierLabel = supplierLabel;
    }

    public String getPlatformLabel() {
        return platformLabel;
    }

    public void setPlatformLabel(String platformLabel) {
        this.platformLabel = platformLabel;
    }

    public String getReservedCoding2() {
        return reservedCoding2;
    }

    public void setReservedCoding2(String reservedCoding2) {
        this.reservedCoding2 = reservedCoding2;
    }

    public String getReservedCoding3() {
        return reservedCoding3;
    }

    public void setReservedCoding3(String reservedCoding3) {
        this.reservedCoding3 = reservedCoding3;
    }

    public String getBarcodeReservation2() {
        return barcodeReservation2;
    }

    public void setBarcodeReservation2(String barcodeReservation2) {
        this.barcodeReservation2 = barcodeReservation2;
    }

    public String getBarcodeReservation3() {
        return barcodeReservation3;
    }

    public void setBarcodeReservation3(String barcodeReservation3) {
        this.barcodeReservation3 = barcodeReservation3;
    }

    public String getFestivalIds() {
        return festivalIds;
    }

    public void setFestivalIds(String festivalIds) {
        this.festivalIds = festivalIds;
    }

    public String getObjectIds() {
        return objectIds;
    }

    public void setObjectIds(String objectIds) {
        this.objectIds = objectIds;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getReservedCoding1() {
        return reservedCoding1;
    }

    public void setReservedCoding1(String reservedCoding1) {
        this.reservedCoding1 = reservedCoding1;
    }

    public String getBarcodeReservation1() {
        return barcodeReservation1;
    }

    public void setBarcodeReservation1(String barcodeReservation1) {
        this.barcodeReservation1 = barcodeReservation1;
    }

    @Override
    public String toString() {
        return "ShopGoods{" +
                ", id=" + id +
                ", shopInfoId=" + shopInfoId +
                ", shopName=" + shopName +
                ", shopNum=" + shopNum +
                ", platformGoodsCategoryId=" + platformGoodsCategoryId +
                ", platformGoodsBrandId=" + platformGoodsBrandId +
                ", shopGoodsCategoryId=" + shopGoodsCategoryId +
                ", marketPrice=" + marketPrice +
                ", salePrice=" + salePrice +
                ", stock=" + stock +
                ", tradingPoints=" + tradingPoints +
                ", rebate=" + rebate +
                ", unit=" + unit +
                ", weight=" + weight +
                ", cubage=" + cubage +
                ", stockType=" + stockType +
                ", freight=" + freight +
                ", shopExpressCostTempletId=" + shopExpressCostTempletId +
                ", snailMail=" + snailMail +
                ", fastMail=" + fastMail +
                ", ems=" + ems +
                ", virtualGoods=" + virtualGoods +
                ", isPresell=" + isPresell +
                ", isGroupbuy=" + isGroupbuy +
                ", shopDescribe=" + shopDescribe +
                ", pic=" + pic +
                ", status=" + status +
                ", groundingTime=" + groundingTime +
                ", undercarriageTime=" + undercarriageTime +
                ", accessNumber=" + accessNumber +
                ", releaseTime=" + releaseTime +
                ", shopGoodsVideo=" + shopGoodsVideo +
                ", lastLiveId=" + lastLiveId +
                ", indexCode=" + indexCode +
                ", isLimit=" + isLimit +
                ", limitStartTime=" + limitStartTime +
                ", limitEndTime=" + limitEndTime +
                ", limitNum=" + limitNum +
                ", limitExplain=" + limitExplain +
                ", costPrice=" + costPrice +
                ", menberPrice=" + menberPrice +
                ", grossProfit=" + grossProfit +
                ", royaltyRatio=" + royaltyRatio +
                ", brand=" + brand +
                ", goodsUnit=" + goodsUnit +
                ", goodsBarcode=" + goodsBarcode +
                ", shopBarcode=" + shopBarcode +
                ", goodsQrCode=" + goodsQrCode +
                ", labels=" + labels +
                ", keywords=" + keywords +
                ", point=" + point +
                ", appSalesAmount=" + appSalesAmount +
                ", appCollectAmount=" + appCollectAmount +
                ", appClickAmount=" + appClickAmount +
                ", appPvAmount=" + appPvAmount +
                ", channel=" + channel +
                ", remarks=" + remarks +
                ", abstracts=" + abstracts +
                ", homePageSort=" + homePageSort +
                ", listSort=" + listSort +
                ", weixinSalesAmount=" + weixinSalesAmount +
                ", weixinCollectAmount=" + weixinCollectAmount +
                ", weixinClickAmount=" + weixinClickAmount +
                ", weixinPvAmount=" + weixinPvAmount +
                ", liveSalesAmount=" + liveSalesAmount +
                ", liveCollectAmount=" + liveCollectAmount +
                ", liveClickAmount=" + liveClickAmount +
                ", livePvAmount=" + livePvAmount +
                ", meitiSalesAmount=" + meitiSalesAmount +
                ", meitiCollectAmount=" + meitiCollectAmount +
                ", meitiClickAmount=" + meitiClickAmount +
                ", meitiPvAmount=" + meitiPvAmount +
                ", jingdongSalesAmount=" + jingdongSalesAmount +
                ", jingdongCollectAmount=" + jingdongCollectAmount +
                ", jingdongClickAmount=" + jingdongClickAmount +
                ", jingdongPvAmount=" + jingdongPvAmount +
                ", tianmaoSalesAmount=" + tianmaoSalesAmount +
                ", tianmaoCollectAmount=" + tianmaoCollectAmount +
                ", tianmaoClickAmount=" + tianmaoClickAmount +
                ", tianmaoPvAmount=" + tianmaoPvAmount +
                ", location=" + location +
                ", isLock=" + isLock +
                ", pointExchange=" + pointExchange +
                ", needPoint=" + needPoint +
                ", commissionLevelOne=" + commissionLevelOne +
                ", commissionLevelTwo=" + commissionLevelTwo +
                ", commissionOnOff=" + commissionOnOff +
                ", supplierLabel=" + supplierLabel +
                ", platformLabel=" + platformLabel +
                ", reservedCoding2=" + reservedCoding2 +
                ", reservedCoding3=" + reservedCoding3 +
                ", barcodeReservation2=" + barcodeReservation2 +
                ", barcodeReservation3=" + barcodeReservation3 +
                ", festivalIds=" + festivalIds +
                ", objectIds=" + objectIds +
                ", provinceId=" + provinceId +
                ", packageId=" + packageId +
                ", isSpecial=" + isSpecial +
                ", reservedCoding1=" + reservedCoding1 +
                ", barcodeReservation1=" + barcodeReservation1 +
                "}";
    }
}
