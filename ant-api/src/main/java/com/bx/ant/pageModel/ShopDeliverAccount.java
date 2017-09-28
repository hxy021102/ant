package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ShopDeliverAccount implements java.io.Serializable {

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

}
