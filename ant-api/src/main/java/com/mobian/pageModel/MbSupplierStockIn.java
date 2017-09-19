package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbSupplierStockIn implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;
	private Date updatetime;
	private Boolean isdeleted;
	private Integer supplierOrderId;
	private String status;
	private String signPeopleId;
	private String signPeopleName;
	private Date signDate;
	private String receiveUrl;
	private String loginId;
	private String loginName;
	private String payStatus;
	private String payStatusName;
	private String invoiceStatus;
	private String invoiceStatusName;
	private Integer warehouseId;
	private String warehouseName;
	private String remark;
	private String driverLoginId;
	private String driverName;
	private Date stockinTimeBegin;
	private Date stockinTimeEnd;
	private String supplierName;
	private Integer paymentDays;

	public Integer getPaymentDays() {
		return paymentDays;
	}

	public void setPaymentDays(Integer paymentDays) {
		this.paymentDays = paymentDays;
	}

	public Date getStockinTimeBegin() {
		return stockinTimeBegin;
	}

	public void setStockinTimeBegin(Date stockinTimeBegin) {
		this.stockinTimeBegin = stockinTimeBegin;
	}

	public Date getStockinTimeEnd() {
		return stockinTimeEnd;
	}

	public void setStockinTimeEnd(Date stockinTimeEnd) {
		this.stockinTimeEnd = stockinTimeEnd;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInvoiceStatusName() {
		return ConvertNameUtil.getString(this.invoiceStatus);
	}

	public void setInvoiceStatusName(String invoiceStatusName) {
		this.invoiceStatusName = invoiceStatusName;
	}

	public String getSignPeopleName() {
		return signPeopleName;
	}
	public void setSignPeopleName(String signPeopleName) {
		this.signPeopleName = signPeopleName;
	}
	public String getPayStatusName() {return  ConvertNameUtil.getString(this.payStatus);}
	public void setPayStatusName(String payStatusName) {this.payStatusName = payStatusName;}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
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
	public void setSupplierOrderId(Integer supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}
	public Integer getSupplierOrderId() {
		return this.supplierOrderId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return this.status;
	}
	public void setSignPeopleId(String signPeopleId) {
		this.signPeopleId = signPeopleId;
	}
	public String getSignPeopleId() {
		return this.signPeopleId;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Date getSignDate() {
		return this.signDate;
	}
	public void setReceiveUrl(String receiveUrl) {
		this.receiveUrl = receiveUrl;
	}
	public String getReceiveUrl() {
		return this.receiveUrl;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLoginId() {
		return this.loginId;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public String getPayStatus() {
		return this.payStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	
	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	public Integer getWarehouseId() {
		return this.warehouseId;
	}

	public String getDriverLoginId() {
		return driverLoginId;
	}

	public void setDriverLoginId(String driverLoginId) {
		this.driverLoginId = driverLoginId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
}
