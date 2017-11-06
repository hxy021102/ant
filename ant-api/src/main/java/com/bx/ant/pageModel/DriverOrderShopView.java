package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

/**
 * Created by w9777 on 2017/11/3.
 */
public class DriverOrderShopView extends DriverOrderShop {
    private String statusName;
    private String payStatusName;
    private Date updatetimeBegin;
    private Date updatetimeEnd;
    private DeliverOrderShop deliverOrderShop;

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

    public Date getUpdatetimeBegin() {
        return updatetimeBegin;
    }

    public void setUpdatetimeBegin(Date updatetimeBegin) {
        this.updatetimeBegin = updatetimeBegin;
    }

    public Date getUpdatetimeEnd() {
        return updatetimeEnd;
    }

    public void setUpdatetimeEnd(Date updatetimeEnd) {
        this.updatetimeEnd = updatetimeEnd;
    }

    public DeliverOrderShop getDeliverOrderShop() {
        return deliverOrderShop;
    }

    public void setDeliverOrderShop(DeliverOrderShop deliverOrderShop) {
        this.deliverOrderShop = deliverOrderShop;
    }
}
