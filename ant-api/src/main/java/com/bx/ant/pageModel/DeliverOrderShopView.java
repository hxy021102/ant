package com.bx.ant.pageModel;

import java.util.List;

/**
 * Created by w9777 on 2017/10/23.
 */
public class DeliverOrderShopView extends DeliverOrderShop {

    private DeliverOrder delvierOrder;
    private List<DeliverOrderShopItem> deliverOrderShopItemList;

    public DeliverOrder getDelvierOrder() {
        return delvierOrder;
    }

    public void setDelvierOrder(DeliverOrder delvierOrder) {
        this.delvierOrder = delvierOrder;
    }

    public List<DeliverOrderShopItem> getDeliverOrderShopItemList() {
        return deliverOrderShopItemList;
    }

    public void setDeliverOrderShopItemList(List<DeliverOrderShopItem> deliverOrderShopItemList) {
        this.deliverOrderShopItemList = deliverOrderShopItemList;
    }
}
