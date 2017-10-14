package com.bx.ant.pageModel;



import com.mobian.pageModel.DeliverOrderItem;
import com.mobian.pageModel.DeliverOrderShopItem;

import java.util.List;

/**
 * Created by wanxp on 17-9-27.
 */
public class DeliverOrderExt extends DeliverOrder {

    private String[] statusList;

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
}
