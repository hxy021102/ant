package com.bx.ant.pageModel;

import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DeliverOrderItem;

import java.util.List;

/**
 * Created by wanxp on 17-9-27.
 */
public class DeliverOrderExt extends DeliverOrder {
    private List<DeliverOrderItem> deliverOrderItemList;

    public List<DeliverOrderItem> getDeliverOrderItemList() {
        return deliverOrderItemList;
    }

    public void setDeliverOrderItemList(List<DeliverOrderItem> deliverOrderItemList) {
        this.deliverOrderItemList = deliverOrderItemList;
    }
}
