package com.bx.ant.pageModel;

import com.mobian.pageModel.DeliverOrderShopItem;

/**
 * Created by wanxp on 17-9-27.
 */
public class DeliverOrderShopItemExt extends DeliverOrderShopItem {
    private String itemName;
    private String pictureUrl;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
