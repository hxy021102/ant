package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class MbPayment implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer orderId;
	private String orderType;
	private Integer amount;
	private String payWay;
	private Boolean status;
	private String reason;

	// fields to set in service
	@Deprecated
	private String payConsist;
	private String remitter;
	private String remitterTime;
	private String remark;
	private String refId;
	private String bankCode;
	private Integer shopId;

	private List<MbPaymentItem> mbPaymentItems;

	private String payWayName;

	public String getPayConsist() {
		return payConsist;
	}

	public void setPayConsist(String payConsist) {
		this.payConsist = payConsist;
	}

	public String getRemitter() {
		return remitter;
	}

	public void setRemitter(String remitter) {
		this.remitter = remitter;
	}

	public String getRemitterTime() {
		return remitterTime;
	}

	public void setRemitterTime(String remitterTime) {
		this.remitterTime = remitterTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getBankCodeName() {
		return ConvertNameUtil.getString(bankCode);
	}
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPayWayName() {
		return ConvertNameUtil.getString(payWay);
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
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
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public String getOrderType() {
		return this.orderType;
	}
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
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public Boolean getStatus() {
		return this.status;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return this.reason;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public List<MbPaymentItem> getMbPaymentItems() {
		return mbPaymentItems;
	}

	public void setMbPaymentItems(List<MbPaymentItem> mbPaymentItems) {
		this.mbPaymentItems = mbPaymentItems;
	}
}
