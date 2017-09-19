package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbRechargeLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;
	private Date updatetime;
	private Boolean isdeleted;
	private Integer balanceId;
	private Integer amount;
	private String applyLoginId;
	private String applyLoginName;
	private String content;
	private String handleStatus;
	private String handleLoginId;
	private String handleRemark;
	private Date handleTime;
	private String refType;
	private String remitter;
	private Date remitterTime;
	private String handleLoginName;
	private String bankCode;
	private Integer shopId;
	private String shopName;

	private String payCode;
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}






	public String getHandleStatusName() {
		return ConvertNameUtil.getString(this.handleStatus);
	}

	public String getRefTypeName() {
		return ConvertNameUtil.getString(this.refType);
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
	public void setBalanceId(Integer balanceId) {
		this.balanceId = balanceId;
	}

	public Integer getBalanceId() {
		return this.balanceId;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getAmount() {
		return this.amount;
	}
	public void setApplyLoginId(String applyLoginId) {
		this.applyLoginId = applyLoginId;
	}

	public String getApplyLoginId() {
		return this.applyLoginId;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getHandleStatus() {
		return this.handleStatus;
	}
	public void setHandleLoginId(String handleLoginId) {
		this.handleLoginId = handleLoginId;
	}

	public String getHandleLoginId() {
		return this.handleLoginId;
	}
	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}

	public String getHandleRemark() {
		return this.handleRemark;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	
	public Date getHandleTime() {
		return this.handleTime;
	}

	public String getApplyLoginName() {
		return applyLoginName;
	}

	public void setApplyLoginName(String applyLoginName) {
		this.applyLoginName = applyLoginName;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public String getRemitter() {
		return remitter;
	}

	public void setRemitter(String remitter) {
		this.remitter = remitter;
	}

	public Date getRemitterTime() {
		return remitterTime;
	}

	public void setRemitterTime(Date remitterTime) {
		this.remitterTime = remitterTime;
	}

	public String getHandleLoginName() {
		return handleLoginName;
	}

	public void setHandleLoginName(String handleLoginName) {
		this.handleLoginName = handleLoginName;
	}

	public String getBankCode() {
		return bankCode;
	}
	public String getBankCodeName() {
		return ConvertNameUtil.getString(bankCode);
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Override
	public String toString() {
		return "MbRechargeLog{" +
				"id=" + id +
				", tenantId=" + tenantId +
				", addtime=" + addtime +
				", updatetime=" + updatetime +
				", isdeleted=" + isdeleted +
				", balanceId=" + balanceId +
				", amount=" + amount +
				", applyLoginId='" + applyLoginId + '\'' +
				", applyLoginName='" + applyLoginName + '\'' +
				", content='" + content + '\'' +
				", handleStatus='" + handleStatus + '\'' +
				", handleLoginId='" + handleLoginId + '\'' +
				", handleRemark='" + handleRemark + '\'' +
				", handleTime=" + handleTime +
				", refType='" + refType + '\'' +
				", remitter='" + remitter + '\'' +
				", remitterTime=" + remitterTime +
				", handleLoginName='" + handleLoginName + '\'' +
				", bankCode='" + bankCode + '\'' +
				", payCode='" + payCode + '\'' +
				'}';
	}
}
