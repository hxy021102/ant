package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbContract implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String code;
	private String name;
	private Integer shopId;
	private String shopName;
	private Date expiryDateStart;			
	private Date expiryDateEnd;			
	private Boolean valid;
	private String attachment;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	public Integer getShopId() {
		return this.shopId;
	}
	public void setExpiryDateStart(Date expiryDateStart) {
		this.expiryDateStart = expiryDateStart;
	}
	
	public Date getExpiryDateStart() {
		return this.expiryDateStart;
	}
	public void setExpiryDateEnd(Date expiryDateEnd) {
		this.expiryDateEnd = expiryDateEnd;
	}
	
	public Date getExpiryDateEnd() {
		return this.expiryDateEnd;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	public Boolean getValid() {
		return this.valid;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	public String getAttachment() {
		return this.attachment;
	}

}
