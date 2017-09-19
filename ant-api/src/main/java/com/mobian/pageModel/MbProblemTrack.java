package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbProblemTrack implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String title;
	private String details;
	private String status;
	private String ownerId;
	private String refType;
	private Integer orderId;
	private String lastOwnerId;

	private String statusName;
	private String refTypeName;
	private String ownerName;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String owerName) {
		this.ownerName = owerName;
	}

	public String getRefTypeName() {
		return ConvertNameUtil.getString(refType);
	}

	public void setRefTypeName(String refTypeName) {
		this.refTypeName = refTypeName;
	}

	public String getStatusName() {
		return ConvertNameUtil.getString(status);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getDetails() {
		return this.details;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getOwnerId() {
		return this.ownerId;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	
	public String getRefType() {
		return this.refType;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Integer getOrderId() {
		return this.orderId;
	}
	public void setLastOwnerId(String  lastOwnerId) {
		this.lastOwnerId = lastOwnerId;
	}
	
	public String  getLastOwnerId() {
		return this.lastOwnerId;
	}

}
