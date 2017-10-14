package com.bx.ant.pageModel;

import com.bx.ant.pageModel.DeliverOrderShopItem;

/**
 * Created by wanxp on 17-9-27.
 */
public class DeliverOrderShopItemExt extends DeliverOrderShopItem {
    private String itemName;
    private String pictureUrl;
    private String quantityUnitName;


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

    public String getQuantityUnitName() {
        return quantityUnitName;
    }

    public void setQuantityUnitName(String quantityUnitName) {
        this.quantityUnitName = quantityUnitName;
    }
}
