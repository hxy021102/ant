/*
 * @author John
 * @date - 2017-10-30
 */

package com.mobian.model;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_withdraw_log")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbWithdrawLog implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbWithdrawLog";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_BALANCE_ID = "余额ID";
	public static final String ALIAS_AMOUNT = "提现金额";
	public static final String ALIAS_REF_TYPE = "业务类型";
	public static final String ALIAS_APPLY_LOGIN_ID = "申请人ID";
	public static final String ALIAS_REMITTER = "收款人";
	public static final String ALIAS_REMITTER_TIME = "收款时间";
	public static final String ALIAS_CONTENT = "申请备注";
	public static final String ALIAS_BANK_CODE = "银行编码";
	public static final String ALIAS_HANDLE_STATUS = "处理状态";
	public static final String ALIAS_HANDLE_LOGIN_ID = "处理人";
	public static final String ALIAS_HANDLE_REMARK = "处理结果";
	public static final String ALIAS_HANDLE_TIME = "处理时间";
	public static final String ALIAS_PAY_CODE = "流水号";
//	public static final String ALI
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_REMITTER_TIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_HANDLE_TIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private java.lang.Integer id;
	//
	private java.lang.Integer tenantId;
	//@NotNull 
	private java.util.Date addtime;
	//@NotNull 
	private java.util.Date updatetime;
	//@NotNull 
	private java.lang.Boolean isdeleted;
	//@NotNull 
	private java.lang.Integer balanceId;
	//@NotNull 
	private java.lang.Integer amount;
	//@NotBlank @Length(max=10)
	private java.lang.String refType;
	//@Length(max=36)
	private java.lang.String applyLoginId;
	//@Length(max=32)
	private java.lang.String receiver;
	//
	private java.util.Date receiverTime;
	//@Length(max=512)
	private java.lang.String content;
	//@Length(max=10)
	private java.lang.String bankCode;
	//@Length(max=4)
	private java.lang.String handleStatus;
	//@Length(max=36)
	private java.lang.String handleLoginId;
	//@Length(max=512)
	private java.lang.String handleRemark;
	//
	private java.util.Date handleTime;
	//@Length(max=64)
	private java.lang.String payCode;
	//@Length(max=64)
	private java.lang.String receiverAccount;

	private java.lang.String applyLoginIP;
	//columns END


		public TmbWithdrawLog(){
		}
		public TmbWithdrawLog(Integer id) {
			this.id = id;
		}
	

	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public java.lang.Integer getId() {
		return this.id;
	}
	
	@Column(name = "tenant_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getTenantId() {
		return this.tenantId;
	}
	
	public void setTenantId(java.lang.Integer tenantId) {
		this.tenantId = tenantId;
	}
	

	@Column(name = "addtime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public java.util.Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}
	

	@Column(name = "updatetime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@Column(name = "isdeleted", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public java.lang.Boolean getIsdeleted() {
		return this.isdeleted;
	}
	
	public void setIsdeleted(java.lang.Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	@Column(name = "balance_id", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getBalanceId() {
		return this.balanceId;
	}
	
	public void setBalanceId(java.lang.Integer balanceId) {
		this.balanceId = balanceId;
	}
	
	@Column(name = "amount", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(java.lang.Integer amount) {
		this.amount = amount;
	}
	
	@Column(name = "ref_type", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public java.lang.String getRefType() {
		return this.refType;
	}
	
	public void setRefType(java.lang.String refType) {
		this.refType = refType;
	}
	
	@Column(name = "apply_login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getApplyLoginId() {
		return this.applyLoginId;
	}
	
	public void setApplyLoginId(java.lang.String applyLoginId) {
		this.applyLoginId = applyLoginId;
	}
	
	@Column(name = "receiver", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getReceiver() {
		return this.receiver;
	}
	
	public void setReceiver(java.lang.String remitter) {
		this.receiver = remitter;
	}
	

	@Column(name = "receiver_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getReceiverTime() {
		return this.receiverTime;
	}
	
	public void setReceiverTime(java.util.Date remitterTime) {
		this.receiverTime = remitterTime;
	}
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	@Column(name = "bank_code", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getBankCode() {
		return this.bankCode;
	}
	
	public void setBankCode(java.lang.String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name = "handle_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getHandleStatus() {
		return this.handleStatus;
	}
	
	public void setHandleStatus(java.lang.String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	@Column(name = "handle_login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getHandleLoginId() {
		return this.handleLoginId;
	}
	
	public void setHandleLoginId(java.lang.String handleLoginId) {
		this.handleLoginId = handleLoginId;
	}
	
	@Column(name = "handle_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public java.lang.String getHandleRemark() {
		return this.handleRemark;
	}
	
	public void setHandleRemark(java.lang.String handleRemark) {
		this.handleRemark = handleRemark;
	}
	

	@Column(name = "handle_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getHandleTime() {
		return this.handleTime;
	}
	
	public void setHandleTime(java.util.Date handleTime) {
		this.handleTime = handleTime;
	}
	
	@Column(name = "pay_code", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getPayCode() {
		return this.payCode;
	}
	
	public void setPayCode(java.lang.String payCode) {
		this.payCode = payCode;
	}

	@Column(name = "receiver_account", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getReceiverAccount() {
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount) {
		this.receiverAccount = receiverAccount;
	}

	@Column(name = "apply_login_ip", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getApplyLoginIP() {
		return applyLoginIP;
	}

	public void setApplyLoginIP(String applyLoginIP) {
		this.applyLoginIP = applyLoginIP;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("BalanceId",getBalanceId())
			.append("Amount",getAmount())
			.append("RefType",getRefType())
			.append("ApplyLoginId",getApplyLoginId())
			.append("Remitter",getRemitter())
			.append("RemitterTime",getRemitterTime())
			.append("Content",getContent())
			.append("BankCode",getBankCode())
			.append("HandleStatus",getHandleStatus())
			.append("HandleLoginId",getHandleLoginId())
			.append("HandleRemark",getHandleRemark())
			.append("HandleTime",getHandleTime())
			.append("PayCode",getPayCode())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbWithdrawLog == false) return false;
		if(this == obj) return true;
		MbWithdrawLog other = (MbWithdrawLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

