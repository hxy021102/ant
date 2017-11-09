package com.bx.ant.pageModel;


import com.mobian.util.ConvertNameUtil;



@SuppressWarnings("serial")
public class ShopDeliverApplyQuery extends ShopDeliverApply {
    private String ShopName;
    private String deliveryTypeName; //派单类型名称

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getDeliveryTypeName() {
        return ConvertNameUtil.getString(this.deliveryTypeName);
    }

    public void setDeliveryTypeName(String deliveryTypeName) {
        this.deliveryTypeName = deliveryTypeName;
    }
}
