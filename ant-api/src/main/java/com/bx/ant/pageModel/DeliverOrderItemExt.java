package com.bx.ant.pageModel;

import com.bx.ant.pageModel.DeliverOrderItem;

/**
 * Created by wanxp on 17-9-27.
 */
public class DeliverOrderItemExt extends DeliverOrderItem {
    private String itemName;
    private String pictureUrl;
    private String itemCode;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
}