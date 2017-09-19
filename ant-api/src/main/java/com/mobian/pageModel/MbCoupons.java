package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbCoupons implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String name;
	private String code;
	private String type;
	private Integer discount;
	private Integer quantityTotal;
	private Integer quantityUsed;
	private String status;
	private Integer moneyThreshold;
	private Date timeOpen;			
	private Date timeClose;			
	private String description;
	private String typeName;
	private String keyword;
	private Integer price;
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
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	
	public Integer getDiscount() {
		return this.discount;
	}
	public void setQuantityTotal(Integer quantityTotal) {
		this.quantityTotal = quantityTotal;
	}
	
	public Integer getQuantityTotal() {
		return this.quantityTotal;
	}
	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}
	
	public Integer getQuantityUsed() {
		return this.quantityUsed;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setMoneyThreshold(Integer moneyThreshold) {
		this.moneyThreshold = moneyThreshold;
	}
	
	public Integer getMoneyThreshold() {
		return this.moneyThreshold;
	}
	public void setTimeOpen(Date timeOpen) {
		this.timeOpen = timeOpen;
	}

	public Date getTimeOpen() {
		return this.timeOpen;
	}
	public void setTimeClose(Date timeClose) {
		this.timeClose = timeClose;
	}
	
	public Date getTimeClose() {
		return this.timeClose;
	}
	public void setDescription(String describition) {
		this.description = describition;
	}
	
	public String getDescription() {
		return this.description;
	}

	public String getTypeName() {
		return ConvertNameUtil.getString(this.type);
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getStatusName() {
		return ConvertNameUtil.getString(status);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
