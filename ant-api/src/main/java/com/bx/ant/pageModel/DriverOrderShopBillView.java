package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by w9777 on 2017/11/3.
 */
public class DriverOrderShopBillView extends DriverOrderShopBill {
    private String handleStatusName;
    private String handleLoginName;
    private String payWayName;
    private List<DriverOrderShop> driverOrderShopList;
    private Long[] orderShopIds;
    private Date addtimeBegin;
    private Date addtimeEnd;

    public String getHandleStatusName() {
        return ConvertNameUtil.getString(super.getHandleStatus());
    }

    public void setHandleStatusName(String handleStatusName) {
        this.handleStatusName = handleStatusName;
    }

    public String getHandleLoginName() {
        return handleLoginName;
    }

    public void setHandleLoginName(String handleLoginName) {
        this.handleLoginName = handleLoginName;
    }

    public String getPayWayName() {
        return ConvertNameUtil.getString(super.getPayWay());
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public List<DriverOrderShop> getDriverOrderShopList() {
        return driverOrderShopList;
    }

    public void setDriverOrderShopList(List<DriverOrderShop> driverOrderShopList) {
        this.driverOrderShopList = driverOrderShopList;
    }

    public Long[] getOrderShopIds() {
        return orderShopIds;
    }

    public void setOrderShopIds(Long[] orderShopIds) {
        this.orderShopIds = orderShopIds;
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

}
