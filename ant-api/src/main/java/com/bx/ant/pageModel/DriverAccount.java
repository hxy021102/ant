package com.bx.ant.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DriverAccount implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String userName;
	private String password;
	private String nickName;
	private String icon;
	private String sex;
	private String refId;
	private String refType;
	private String conactPhone;
	private String type;
	private String handleStatus;
	private String handleLoginId;
	private String handleRemark;
	private Boolean online;
	private Boolean autoPay;

	

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
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getNickName() {
		return this.nickName;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getIcon() {
		return this.icon;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getSex() {
		return this.sex;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	public String getRefId() {
		return this.refId;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	
	public String getRefType() {
		return this.refType;
	}
	public void setConactPhone(String conactPhone) {
		this.conactPhone = conactPhone;
	}
	
	public String getConactPhone() {
		return this.conactPhone;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	public String getHandleStatus() {
		return this.handleStatus;
	}
	public void setHandleLoginId(String handleLoginId) {
		this.handleLoginId = handleLoginId;
	}
	
	public String getHandleLoginId() {
		return this.handleLoginId;
	}
	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}
	
	public String getHandleRemark() {
		return this.handleRemark;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public Boolean getAutoPay() {
		return autoPay;
	}

	public void setAutoPay(Boolean autoPay) {
		this.autoPay = autoPay;
	}
}
