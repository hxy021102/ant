package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

/**
 * Created by w9777 on 2017/11/3.
 */
public class DriverOrderShopView extends DriverOrderShop {
    private String statusName;
    private String payStatusName;
    private Date addtimeBegin;
    private Date addtimeEnd;
    private DeliverOrderShop deliverOrderShop;
    private String shopName;
    private String userName;
    private Date updatetimeBegin;
    private Date updatetimeEnd;
    private String createDate;
    private String updateDate;
    private Double amountElement;
    private Long[] ids;

    public String getStatusName() {
        return ConvertNameUtil.getString(super.getStatus());
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPayStatusName() {
        return ConvertNameUtil.getString(super.getPayStatus());
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    public DeliverOrderShop getDeliverOrderShop() {
        return deliverOrderShop;
    }

    public void setDeliverOrderShop(DeliverOrderShop deliverOrderShop) {
        this.deliverOrderShop = deliverOrderShop;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getAddtimeBegin() {
        return addtimeBegin;
    }

    public void setAddtimeBegin(Date addtimeBegin) {
        this.addtimeBegin = addtimeBegin;
    }

    public Date getAddtimeEnd() {
        return addtimeEnd;
    }

    public void setAddtimeEnd(Date addtimeEnd) {
        this.addtimeEnd = addtimeEnd;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Double getAmountElement() {
        return amountElement;
    }

    public void setAmountElement(Double amountElement) {
        this.amountElement = amountElement;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
