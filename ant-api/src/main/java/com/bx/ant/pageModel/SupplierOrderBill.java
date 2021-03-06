package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class SupplierOrderBill implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long id;
	private Integer tenantId;
	private Date addtime;
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer supplierId;
	private String status;
	private String remark;
	private Integer amount;
	private Date startDate;			
	private Date endDate;			
	private String payWay;
	//
	private String supplierName;
	private String payWayName;
    private String createTimeName;
    private double amountElement;
	private Integer[] supplierIds;

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
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
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	
	public Integer getSupplierId() {
		return this.supplierId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public String getStatusName() {
		return  ConvertNameUtil.getString(this.status);
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
	public String getPayWay() {
		return this.payWay;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPayWayName() {
		return ConvertNameUtil.getString(this.payWay);
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	public String getCreateTimeName() {
		return createTimeName;
	}

	public void setCreateTimeName(String createTimeName) {
		this.createTimeName = createTimeName;
	}

	public double getAmountElement() {
		return amountElement;
	}

	public void setAmountElement(double amountElement) {
		this.amountElement = amountElement;
	}

	public Integer[] getSupplierIds() {
		return supplierIds;
	}

	public void setSupplierIds(Integer[] supplierIds) {
		this.supplierIds = supplierIds;
	}
}
