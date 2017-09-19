package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbProblemTrackItem implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String content;
	private String file;
	private String ownerId;
	private String  lastOwnerId;

	private String status;



	private Integer problemOrderId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getProblemOrderId() {
		return problemOrderId;
	}

	public void setProblemOrderId(Integer problemOrderId) {
		this.problemOrderId = problemOrderId;
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
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getFile() {
		return this.file;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getOwnerId() {
		return this.ownerId;
	}
	public void setLastOwnerId(String  lastOwnerId) {
		this.lastOwnerId = lastOwnerId;
	}
	
	public String getLastOwnerId() {
		return this.lastOwnerId;
	}

}
