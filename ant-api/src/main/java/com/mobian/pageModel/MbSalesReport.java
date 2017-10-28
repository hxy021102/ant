package com.mobian.pageModel;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/9.
 */
public class MbSalesReport implements java.io.Serializable{
    private static final long serialVersionUID = 5454155825314635342L;
    private Integer itemId;
    private String itemCode;
    private String itemName;
    //发货总量
    private Integer quantity;
    // 退回量
    private Integer backQuantity;
    //实际销售量
    private Integer salesQuantity;
    //销售总金额
    private Integer totalPrice;
    private Date startDate;
    private Date endDate;
    //退回商品金额
    private Integer backMoney;

    /**
     * 进货成本
     */
    private Integer totalCost;

    //订单状态
    private String  orderStatus;
    //发货仓库
    private Integer warehouseId;
    private String  warehouseName;

    private String shopType;

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(Integer backMoney) {
        this.backMoney = backMoney;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getBackQuantity() {
        return backQuantity;
    }

    public void setBackQuantity(Integer backQuantity) {
        this.backQuantity = backQuantity;
    }

    public Integer getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(Integer salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
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

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }
}
