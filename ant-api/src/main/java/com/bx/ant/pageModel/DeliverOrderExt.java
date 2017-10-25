package com.bx.ant.pageModel;

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

    private List<DeliverOrderItem> deliverOrderItemList;
    private List<DeliverOrderShopItem> deliverOrderShopItemList;

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
}
