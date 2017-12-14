package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbStockOutOrder implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer mbStockOutId;
	private Integer deliverOrderId;
	private String status;
	private String statusName;

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
	public void setMbStockOutId(Integer mbStockOutId) {
		this.mbStockOutId = mbStockOutId;
	}
	
	public Integer getMbStockOutId() {
		return this.mbStockOutId;
	}
	public void setDeliverOrderId(Integer deliverOrderId) {
		this.deliverOrderId = deliverOrderId;
	}
	
	public Integer getDeliverOrderId() {
		return this.deliverOrderId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}

	public String getStatusName() {
		return ConvertNameUtil.getString(this.status);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


}
