package com.bx.ant.pageModel;

/**
 * Created by 黄晓渝 2017/11/3.
 */
public class DriverOrderShopBillExt extends DriverOrderShopBill {
    private Double amountElement;
    private String addTimeString;
    private String startTimeString;
    private String endTimeString;
    private String userName;
    private String handleStatusName;

    public String getHandleStatusName() {
        return handleStatusName;
    }

    public void setHandleStatusName(String handleStatusName) {
        this.handleStatusName = handleStatusName;
    }

    public Double getAmountElement() {
        return amountElement;
    }

    public void setAmountElement(Double amountElement) {
        this.amountElement = amountElement;
    }

    public String getAddTimeString() {
        return addTimeString;
    }

    public void setAddTimeString(String addTimeString) {
        this.addTimeString = addTimeString;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
