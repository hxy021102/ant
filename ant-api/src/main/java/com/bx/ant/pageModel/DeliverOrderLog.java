package com.bx.ant.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DeliverOrderLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Long deliverOrderId;
	private String logType;
	private String content;
	private String loginId;

	

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
	public void setDeliverOrderId(Long deliverOrderId) {
		this.deliverOrderId = deliverOrderId;
	}
	
	public Long getDeliverOrderId() {
		return this.deliverOrderId;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	public String getLogType() {
		return this.logType;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getLoginId() {
		return this.loginId;
	}

}
