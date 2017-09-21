package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ShopItem implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer shopId;
	private Integer itemId;
	private Integer price;
	private Integer inPrice;
	private Integer freight;
	private Integer quantity;
	private Boolean online;

	

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
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	public Integer getShopId() {
		return this.shopId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	public Integer getItemId() {
		return this.itemId;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Integer getPrice() {
		return this.price;
	}
	public void setInPrice(Integer inPrice) {
		this.inPrice = inPrice;
	}
	
	public Integer getInPrice() {
		return this.inPrice;
	}
	public void setFreight(Integer freight) {
		this.freight = freight;
	}
	
	public Integer getFreight() {
		return this.freight;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}
}
