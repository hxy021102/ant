package com.bx.ant.pageModel;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class DeliverOrder implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer supplierId;
	private Integer amount;
	private String status;
	private String deliveryStatus;
	private Date deliveryRequireTime;			
	private String deliveryAddress;
	private Integer deliveryRegion;
	private String payStatus;
	private String shopPayStatus;
	private String payWay;
	private String contactPhone;
	private String contactPeople;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private String remark;
	private Integer shopId;
	private Double shopDistance;
	private String supplierOrderId;
	private String completeImages;
	private String completeRemark;
    private String deliverOrderLogType;

	private String deliveryType; // 派单类型
	private Long orderShopId;
	private String  orderLogRemark;

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
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	
	public Integer getSupplierId() {
		return this.supplierId;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
	public String getDeliveryStatus() {
		return this.deliveryStatus;
	}
	public void setDeliveryRequireTime(Date deliveryRequireTime) {
		this.deliveryRequireTime = deliveryRequireTime;
	}
	
	public Date getDeliveryRequireTime() {
		return this.deliveryRequireTime;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public String getDeliveryAddress() {
		return this.deliveryAddress;
	}
	public void setDeliveryRegion(Integer deliveryRegion) {
		this.deliveryRegion = deliveryRegion;
	}
	
	public Integer getDeliveryRegion() {
		return this.deliveryRegion;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public String getPayStatus() {
		return this.payStatus;
	}
	public void setShopPayStatus(String shopPayStatus) {
		this.shopPayStatus = shopPayStatus;
	}
	
	public String getShopPayStatus() {
		return this.shopPayStatus;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
	public String getPayWay() {
		return this.payWay;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public String getContactPhone() {
		return this.contactPhone;
	}
	public void setContactPeople(String contactPeople) {
		this.contactPeople = contactPeople;
	}
	
	public String getContactPeople() {
		return this.contactPeople;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public Double getShopDistance() {
		return shopDistance;
	}

	public void setShopDistance(Double shopDistance) {
		this.shopDistance = shopDistance;
	}

	public String getSupplierOrderId() {
		return supplierOrderId;
	}

	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}

	public String getCompleteImages() {
		return completeImages;
	}

	public void setCompleteImages(String completeImages) {
		this.completeImages = completeImages;
	}

	public String getCompleteRemark() {
		return completeRemark;
	}

	public void setCompleteRemark(String completeRemark) {
		this.completeRemark = completeRemark;
	}

	public String getDeliverOrderLogType() {
		return deliverOrderLogType;
	}

	public void setDeliverOrderLogType(String deliverOrderLogType) {
		this.deliverOrderLogType = deliverOrderLogType;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Long getOrderShopId() {
		return orderShopId;
	}

	public void setOrderShopId(Long orderShopId) {
		this.orderShopId = orderShopId;
	}

	public String getOrderLogRemark() {
		return orderLogRemark;
	}

	public void setOrderLogRemark(String orderLogRemark) {
		this.orderLogRemark = orderLogRemark;
	}
}
