package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class DeliverOrderPay implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Long deliverOrderId;
	private Integer supplierId;
	private String status;
	private Integer amount;
	private String payWay;
	private String payWayName;
	private Integer supplierOrderBillId;
	private Long[] deliverOrderIds;
	private String[] deliverOrderPayStatus;



	

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
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	
	public Integer getSupplierId() {
		return this.supplierId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public String getStatusName() {return ConvertNameUtil.getString(this.status);}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
	public String getPayWay() {
		return this.payWay;
	}

	public Integer getSupplierOrderBillId() {
		return supplierOrderBillId;
	}

	public void setSupplierOrderBillId(Integer supplierOrderBillId) {
		this.supplierOrderBillId = supplierOrderBillId;
	}

	public String getPayWayName() {
		return ConvertNameUtil.getString(this.payWay);
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	public Long[] getDeliverOrderIds() {
		return deliverOrderIds;
	}

	public void setDeliverOrderIds(Long[] deliverOrderIds) {
		this.deliverOrderIds = deliverOrderIds;
	}

	public String[] getDeliverOrderPayStatus() {
		return deliverOrderPayStatus;
	}

	public void setDeliverOrderPayStatus(String[] deliverOrderPayStatus) {
		this.deliverOrderPayStatus = deliverOrderPayStatus;
	}
}
