package com.bx.ant.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DriverFreightRule implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer id;	
	private java.lang.Integer tenantId;	
	private Date addtime;			
	private Date updatetime;			
	private java.lang.Boolean isdeleted;	
	private java.lang.Integer weightLower;	
	private java.lang.Integer weightUpper;	
	private java.lang.Integer distanceLower;	
	private java.lang.Integer distanceUpper;	
	private java.lang.Integer regionId;
	private java.lang.Integer freight;
	private java.lang.String loginId;
	private java.lang.String type;

	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}

	
	public void setTenantId(java.lang.Integer tenantId) {
		this.tenantId = tenantId;
	}
	
	public java.lang.Integer getTenantId() {
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
	public void setIsdeleted(java.lang.Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	public java.lang.Boolean getIsdeleted() {
		return this.isdeleted;
	}
	public void setWeightLower(java.lang.Integer weightLower) {
		this.weightLower = weightLower;
	}
	
	public java.lang.Integer getWeightLower() {
		return this.weightLower;
	}
	public void setWeightUpper(java.lang.Integer weightUpper) {
		this.weightUpper = weightUpper;
	}
	
	public java.lang.Integer getWeightUpper() {
		return this.weightUpper;
	}
	public void setDistanceLower(java.lang.Integer distanceLower) {
		this.distanceLower = distanceLower;
	}
	
	public java.lang.Integer getDistanceLower() {
		return this.distanceLower;
	}
	public void setDistanceUpper(java.lang.Integer distanceUpper) {
		this.distanceUpper = distanceUpper;
	}
	
	public java.lang.Integer getDistanceUpper() {
		return this.distanceUpper;
	}
	public void setRegionId(java.lang.Integer regionId) {
		this.regionId = regionId;
	}
	
	public java.lang.Integer getRegionId() {
		return this.regionId;
	}
	public void setFreight(java.lang.Integer freight) {
		this.freight = freight;
	}
	
	public java.lang.Integer getFreight() {
		return this.freight;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
