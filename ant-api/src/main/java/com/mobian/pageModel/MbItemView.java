package com.mobian.pageModel;

/**
 * Created by 黄晓渝 on 2017/11/24.
 */
public class MbItemView extends MbItem {
    private Integer buyPrice;
    private Integer itemId;
    public Integer getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Integer buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
