package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbSupplier implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String name;
	private Integer regionId;
	private String regionName;
	private String address;
	private String contactPhone;
	private String contactPeople;
	private String certificateList;
	private Integer warehouseId;
	private String  warehouseName;
	//新加地址
	private String regionPath;
	//供应商code
	private String supplierCode;
	//财务联系人
	private String financialContact;
	//财务联系人电话
	private String financialContactPhone;
	public String getRegionPath() {
		return regionPath;
	}
	public void setRegionPath(String regionPath) {
		this.regionPath = regionPath;
	}
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}


	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
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
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	
	public Integer getRegionId() {
		return this.regionId;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public String getContactPhone() {
		return this.contactPhone;
	}
	public void setContactPeople(String contactPeople) {
		this.contactPeople = contactPeople;
	}
	
	public String getContactPeople() {
		return this.contactPeople;
	}
	public void setCertificateList(String certificateList) {
		this.certificateList = certificateList;
	}
	
	public String getCertificateList() {
		return this.certificateList;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	public Integer getWarehouseId() {
		return this.warehouseId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getFinancialContact() {
		return financialContact;
	}

	public void setFinancialContact(String financialContact) {
		this.financialContact = financialContact;
	}

	public String getFinancialContactPhone() {
		return financialContactPhone;
	}

	public void setFinancialContactPhone(String financialContactPhone) {
		this.financialContactPhone = financialContactPhone;
	}


}
