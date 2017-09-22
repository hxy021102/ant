package com.mobian.pageModel;

/**
 * Created by 黄晓渝 on 2017/9/13.
 */
public class MbOrderDistribution {
    private String orderKindName;
    private Integer orderTotal;
    private String[] orderDayName;
    private Integer[] orderDayNumber;

    public String getOrderKindName() {
        return orderKindName;
    }

    public void setOrderKindName(String orderKindName) {
        this.orderKindName = orderKindName;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String[] getOrderDayName() {
        return orderDayName;
    }

    public void setOrderDayName(String[] orderDayName) {
        this.orderDayName = orderDayName;
    }

    public Integer[] getOrderDayNumber() {
        return orderDayNumber;
    }

    public void setOrderDayNumber(Integer[] orderDayNumber) {
        this.orderDayNumber = orderDayNumber;
    }
}
