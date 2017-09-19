package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbSupplierFinanceLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer supplierStockInId;
	private String payLoginId;
	private String payLoginName;
	private String payStatus;
	private String invoiceStatus;
	private String invoiceLoginId;
	private String payRemark;
	private String invoiceRemark;
	private String invoiceNo;

	public String getPayLoginName() {
		return payLoginName;
	}

	public void setPayLoginName(String payLoginName) {
		this.payLoginName = payLoginName;
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
	public void setPayLoginId(String payLoginId) {
		this.payLoginId = payLoginId;
	}
	
	public String getPayLoginId() {
		return this.payLoginId;
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
	public void setInvoiceLoginId(String invoiceLoginId) {
		this.invoiceLoginId = invoiceLoginId;
	}
	
	public String getInvoiceLoginId() {
		return this.invoiceLoginId;
	}
	public void setPayRemark(String payRemark) {
		this.payRemark = payRemark;
	}
	
	public String getPayRemark() {
		return this.payRemark;
	}
	public void setInvoiceRemark(String invoiceRemark) {
		this.invoiceRemark = invoiceRemark;
	}
	
	public String getInvoiceRemark() {
		return this.invoiceRemark;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
}
