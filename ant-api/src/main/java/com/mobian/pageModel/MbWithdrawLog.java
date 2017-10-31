package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbWithdrawLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer id;	
	private java.lang.Integer tenantId;	
	private Date addtime;			
	private Date updatetime;			
	private java.lang.Boolean isdeleted;	
	private java.lang.Integer balanceId;	
	private java.lang.Integer amount;	
	private java.lang.String refType;	
	private java.lang.String applyLoginId;	
	private java.lang.String receiver;
	private Date receiverTime;
	private java.lang.String content;	
	private java.lang.String bankCode;	
	private java.lang.String handleStatus;	
	private java.lang.String handleLoginId;	
	private java.lang.String handleRemark;	
	private Date handleTime;			
	private java.lang.String payCode;
	private java.lang.String receiverAccount;

	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}

	
	public void setTenantId(java.lang.Integer tenantId) {
		this.tenantId = tenantId;
	}
	
	public java.lang.Integer getTenantId() {
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
	public void setIsdeleted(java.lang.Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	public java.lang.Boolean getIsdeleted() {
		return this.isdeleted;
	}
	public void setBalanceId(java.lang.Integer balanceId) {
		this.balanceId = balanceId;
	}
	
	public java.lang.Integer getBalanceId() {
		return this.balanceId;
	}
	public void setAmount(java.lang.Integer amount) {
		this.amount = amount;
	}
	
	public java.lang.Integer getAmount() {
		return this.amount;
	}
	public void setRefType(java.lang.String refType) {
		this.refType = refType;
	}
	
	public java.lang.String getRefType() {
		return this.refType;
	}
	public void setApplyLoginId(java.lang.String applyLoginId) {
		this.applyLoginId = applyLoginId;
	}
	
	public java.lang.String getApplyLoginId() {
		return this.applyLoginId;
	}
	public void setReceiver(java.lang.String receiver) {
		this.receiver = receiver;
	}
	
	public java.lang.String getReceiver() {
		return this.receiver;
	}
	public void setReceiverTime(Date receiverTime) {
		this.receiverTime = receiverTime;
	}
	
	public Date getReceiverTime() {
		return this.receiverTime;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setBankCode(java.lang.String bankCode) {
		this.bankCode = bankCode;
	}
	
	public java.lang.String getBankCode() {
		return this.bankCode;
	}
	public void setHandleStatus(java.lang.String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	public java.lang.String getHandleStatus() {
		return this.handleStatus;
	}
	public void setHandleLoginId(java.lang.String handleLoginId) {
		this.handleLoginId = handleLoginId;
	}
	
	public java.lang.String getHandleLoginId() {
		return this.handleLoginId;
	}
	public void setHandleRemark(java.lang.String handleRemark) {
		this.handleRemark = handleRemark;
	}
	
	public java.lang.String getHandleRemark() {
		return this.handleRemark;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	
	public Date getHandleTime() {
		return this.handleTime;
	}
	public void setPayCode(java.lang.String payCode) {
		this.payCode = payCode;
	}
	
	public java.lang.String getPayCode() {
		return this.payCode;
	}

	public String getReceiverAccount() {
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount) {
		this.receiverAccount = receiverAccount;
	}
}
