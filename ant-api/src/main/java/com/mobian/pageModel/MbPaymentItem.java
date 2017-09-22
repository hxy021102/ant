package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbPaymentItem implements java.io.Serializable {


	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer paymentId;
	private String payWay;
	private Integer amount;
	private String remitter;
	private Date remitterTime;			
	private String remark;
	private String refId;
	private String bankCode;
	private String payCode;
	private Integer couponsId;
	private String couponsName;
	private String bankCodeName;



	private String payWayName;

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
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	
	public Integer getPaymentId() {
		return this.paymentId;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
	public String getPayWay() {
		return this.payWay;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	public void setRemitter(String remitter) {
		this.remitter = remitter;
	}
	
	public String getRemitter() {
		return this.remitter;
	}
	public void setRemitterTime(Date remitterTime) {
		this.remitterTime = remitterTime;
	}
	
	public Date getRemitterTime() {
		return this.remitterTime;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	public String getRefId() {
		return this.refId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public Integer getCouponsId() {
		return couponsId;
	}

	public void setCouponsId(Integer couponsId) {
		this.couponsId = couponsId;
	}

	public String getCouponsName() {
		return couponsName;
	}

	public void setCouponsName(String couponsName) {
		this.couponsName = couponsName;
	}

	public String getBankCodeName() {
		return ConvertNameUtil.getString(this.bankCode);
	}
	public void setBankCodeName(String bankCodeName) {
		this.bankCodeName = bankCodeName;
	}
}
