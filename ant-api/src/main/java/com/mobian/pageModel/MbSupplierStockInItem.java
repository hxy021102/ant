package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbSupplierStockInItem implements java.io.Serializable {

    private static final long serialVersionUID = 5454155825314635342L;

    private Integer id;
    private Integer tenantId;
    private Date addtime;
    private Date updatetime;

    private Date updatetimeBegin;
    private Date updatetimeEnd;

    private Boolean isdeleted;
    private Integer supplierStockInId;
    private Integer itemId;
    private String productName;
    private Integer quantity;
    private Integer price;
    private String code;
    private String quantityUnitName;
    private String categoryName;
    private Integer supplierOrderId;
    private String  supplierName;
    private double  totalPrice;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(Integer supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getQuantityUnitName() {
        return quantityUnitName;
    }

    public void setQuantityUnitName(String quantityUnitName) {
        this.quantityUnitName = quantityUnitName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return this.id;
    }


    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getTenantId() {
        return this.tenantId;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getAddtime() {
        return this.addtime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Boolean getIsdeleted() {
        return this.isdeleted;
    }

    public void setSupplierStockInId(Integer supplierStockInId) {
        this.supplierStockInId = supplierStockInId;
    }

    public Integer getSupplierStockInId() {
        return this.supplierStockInId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemId() {
        return this.itemId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return this.price;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
