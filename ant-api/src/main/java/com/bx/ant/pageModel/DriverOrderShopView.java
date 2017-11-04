package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

/**
 * Created by w9777 on 2017/11/3.
 */
public class DriverOrderShopView extends DriverOrderShop {
    private String statusName;
    private String payStatusName;

    public String getStatusName() {
        return ConvertNameUtil.getString(super.getStatus());
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPayStatusName() {
        return ConvertNameUtil.getString(super.getStatus());
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }
}
