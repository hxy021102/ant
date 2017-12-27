package com.bx.ant.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class SupplierInterfaceConfig implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String interfaceType;
	private String customerId;
	private String appKey;
	private String appSecret;
	private String serviceUrl;
	private String version;
	private String warehouseCode;
	private String logisticsCode;
	private String statusMap;
	private String remark;
	private Boolean online;

	

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
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	public String getInterfaceType() {
		return this.interfaceType;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	public String getAppKey() {
		return this.appKey;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	public String getAppSecret() {
		return this.appSecret;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	public String getServiceUrl() {
		return this.serviceUrl;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getVersion() {
		return this.version;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	
	public String getWarehouseCode() {
		return this.warehouseCode;
	}
	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}
	
	public String getLogisticsCode() {
		return this.logisticsCode;
	}
	public void setStatusMap(String statusMap) {
		this.statusMap = statusMap;
	}
	
	public String getStatusMap() {
		return this.statusMap;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setOnline(Boolean online) {
		this.online = online;
	}
	
	public Boolean getOnline() {
		return this.online;
	}

}
