package com.mobian.pageModel;

/**
 * Created by 黄晓渝 on 2017/9/26.
 */
public class MbItemQuery extends MbItem {
    private Integer[] itemIds;
    private String deliverOrderShopIds;
    private Integer shopId;
    public Integer[] getItemIds() {
        return itemIds;
    }

    public void setItemIds(Integer[] itemIds) {
        this.itemIds = itemIds;
    }

    public String getDeliverOrderShopIds() {
        return deliverOrderShopIds;
    }

    public void setDeliverOrderShopIds(String deliverOrderShopIds) {
        this.deliverOrderShopIds = deliverOrderShopIds;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}
