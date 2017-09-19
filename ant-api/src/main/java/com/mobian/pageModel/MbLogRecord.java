package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbLogRecord implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String logUserId;
	private String logUserName;
	private String url;
	private String urlName;
	private String formData;
	private String result;

	private Boolean isSuccess;

	private Integer processTime;


	private Date startTime;
	private Date endTime;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	public void setLogUserId(String logUserId) {
		this.logUserId = logUserId;
	}
	
	public String getLogUserId() {
		return this.logUserId;
	}
	public void setLogUserName(String logUserName) {
		this.logUserName = logUserName;
	}
	
	public String getLogUserName() {
		return this.logUserName;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	
	public String getUrlName() {
		return this.urlName;
	}
	public void setFormData(String formData) {
		this.formData = formData;
	}
	
	public String getFormData() {
		return this.formData;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Integer processTime) {
		this.processTime = processTime;
	}
}
