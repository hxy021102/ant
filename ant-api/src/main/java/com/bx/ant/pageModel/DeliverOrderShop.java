package com.bx.ant.pageModel;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class DeliverOrderShop implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Long deliverOrderId;
	private Integer shopId;
	private String status;
	private Integer amount;
	private BigDecimal distance;
	private Date updatetimeBegin;
	private Date updatetimeEnd;

	private Date addtimeBegin;
	private Date addtimeEnd;


	private String shopPayStatus;
    private Long[] ids;

	private String deliveryType; // 派单类型
	private Integer orderId;     //创建的订单id
	private Integer freight; // 运费统一配置：分/件



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
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	public Integer getShopId() {
		return this.shopId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getAmount() {
		return this.amount;
	}

	public Date getUpdatetimeBegin() {
		return updatetimeBegin;
	}

	public void setUpdatetimeBegin(Date updatetimeBegin) {
		this.updatetimeBegin = updatetimeBegin;
	}

	public Date getUpdatetimeEnd() {
		return updatetimeEnd;
	}

	public void setUpdatetimeEnd(Date updatetimeEnd) {
		this.updatetimeEnd = updatetimeEnd;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public String getShopPayStatus() {
		return shopPayStatus;
	}

	public void setShopPayStatus(String shopPayStatus) {
		this.shopPayStatus = shopPayStatus;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Date getAddtimeBegin() {
		return addtimeBegin;
	}

	public void setAddtimeBegin(Date addtimeBegin) {
		this.addtimeBegin = addtimeBegin;
	}

	public Date getAddtimeEnd() {
		return addtimeEnd;
	}

	public void setAddtimeEnd(Date addtimeEnd) {
		this.addtimeEnd = addtimeEnd;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}
}
