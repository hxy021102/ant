package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DeliverOrderItem implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Long deliverOrderId;
	private Integer itemId;
	private Integer price;
	private Integer inPrice;
	private Integer freight;

	

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

}
