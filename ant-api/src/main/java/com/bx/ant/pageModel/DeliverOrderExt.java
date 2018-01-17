package com.bx.ant.pageModel;

import com.mobian.pageModel.MbShop;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wanxp on 17-9-27.
 */
public class DeliverOrderExt extends DeliverOrder {

    private Long millisecond;
    private String[] statusList;
    private BigDecimal distance;
    private Integer shopPayAmount;
    private String balanceLogType;
    private String shopPayWay;

    private DeliverOrderShop deliverOrderShop;
    private MbShop mbShop;
    private List<DeliverOrderItem> deliverOrderItemList;
    private List<DeliverOrderShopItem> deliverOrderShopItemList;
    private List<SupplierItemRelationView> supplierItemRelationViewList;

    public List<DeliverOrderItem> getDeliverOrderItemList() {
        return deliverOrderItemList;
    }

    public void setDeliverOrderItemList(List<DeliverOrderItem> deliverOrderItemList) {
        this.deliverOrderItemList = deliverOrderItemList;
    }

    public List<DeliverOrderShopItem> getDeliverOrderShopItemList() {
        return deliverOrderShopItemList;
    }

    public void setDeliverOrderShopItemList(List<DeliverOrderShopItem> deliverOrderShopItemList) {
        this.deliverOrderShopItemList = deliverOrderShopItemList;
    }

    public String[] getStatusList() {
        return statusList;
    }

    public void setStatusList(String[] statusList) {
        this.statusList = statusList;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Long getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(Long millisecond) {
        this.millisecond = millisecond;
    }

    public Integer getShopPayAmount() {
        return shopPayAmount;
    }

    public void setShopPayAmount(Integer shopPayAmount) {
        this.shopPayAmount = shopPayAmount;
    }

    public String getBalanceLogType() {
        return balanceLogType;
    }

    public void setBalanceLogType(String balanceLogType) {
        this.balanceLogType = balanceLogType;
    }

    public String getShopPayWay() {
        return shopPayWay;
    }

    public void setShopPayWay(String shopPayWay) {
        this.shopPayWay = shopPayWay;
    }

    public DeliverOrderShop getDeliverOrderShop() {
        return deliverOrderShop;
    }

    public void setDeliverOrderShop(DeliverOrderShop deliverOrderShop) {
        this.deliverOrderShop = deliverOrderShop;
    }

    public MbShop getMbShop() {
        return mbShop;
    }

    public void setMbShop(MbShop mbShop) {
        this.mbShop = mbShop;
    }

    public List<SupplierItemRelationView> getSupplierItemRelationViewList() {
        return supplierItemRelationViewList;
    }

    public void setSupplierItemRelationViewList(List<SupplierItemRelationView> supplierItemRelationViewList) {
        this.supplierItemRelationViewList = supplierItemRelationViewList;
    }
}
