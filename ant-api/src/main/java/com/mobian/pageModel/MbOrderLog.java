package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbOrderLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer orderId;
	private String loginId;
	private String content;
	private String remark;
	private String logType;

	private String loginName;
	private String logTypeName;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLogTypeName() {
		return ConvertNameUtil.getString(logType);
	}

	public void setLogTypeName(String logTypeName) {
		this.logTypeName = logTypeName;
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
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Integer getOrderId() {
		return this.orderId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getLoginId() {
		return this.loginId;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	public String getLogType() {
		return this.logType;
	}

}
