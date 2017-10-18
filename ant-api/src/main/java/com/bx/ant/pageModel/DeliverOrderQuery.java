package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

/**
 * Created by 黄晓渝 on 2017/10/13.
 */
public class DeliverOrderQuery extends DeliverOrder {
    private String supplierName;
    private String statusName;
    private String deliveryStatusName;
    private String payStatusName;
    private String shopPayStatusName;
    private Date startDate;
    private Date endDate;
    private String shopName;

    public String getStatusName() {
        return ConvertNameUtil.getString(this.statusName);
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getDeliveryStatusName() {
        return ConvertNameUtil.getString(this.deliveryStatusName);
    }

    public void setDeliveryStatusName(String deliveryStatusName) {
        this.deliveryStatusName = deliveryStatusName;
    }

    public String getPayStatusName() {
        return ConvertNameUtil.getString(this.payStatusName);
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    public String getShopPayStatusName() {
        return ConvertNameUtil.getString(this.shopPayStatusName);
    }

    public void setShopPayStatusName(String shopPayStatusName) {
        this.shopPayStatusName = shopPayStatusName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
