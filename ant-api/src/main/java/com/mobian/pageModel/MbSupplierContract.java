package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbSupplierContract implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String code;
	private String name;
	private Integer supplierId;
	//新加供应商名称属性
	private String  supplierName;
	private Date expiryDateStart;			
	private Date expiryDateEnd;			
	private Boolean valid;
	private String attachment;
	private String contractType;
	private Integer rate;
	private Integer paymentDays;

	public Integer getPaymentDays() {
		return paymentDays;
	}

	public void setPaymentDays(Integer paymentDays) {
		this.paymentDays = paymentDays;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	
	public Integer getSupplierId() {
		return this.supplierId;
	}
	public void setExpiryDateStart(Date expiryDateStart) {
		this.expiryDateStart = expiryDateStart;
	}
	
	public Date getExpiryDateStart() {
		return this.expiryDateStart;
	}
	public void setExpiryDateEnd(Date expiryDateEnd) {
		this.expiryDateEnd = expiryDateEnd;
	}
	
	public Date getExpiryDateEnd() {
		return this.expiryDateEnd;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	public Boolean getValid() {
		return this.valid;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	public String getAttachment() {
		return this.attachment;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	public String getContractType() {
		return this.contractType;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}
}
