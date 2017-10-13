/*
 * @author John
 * @date - 2017-05-08
 */

package com.mobian.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_recharge_log")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbRechargeLog implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbRechargeLog";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_BALANCE_ID = "余额ID";
	public static final String ALIAS_AMOUNT = "充值金额";
	public static final String ALIAS_APPLY_LOGIN_ID = "申请人";
	public static final String ALIAS_CONTENT = "申请备注";
	public static final String ALIAS_HANDLE_STATUS = "处理状态";
	public static final String ALIAS_HANDLE_LOGIN_ID = "处理人";
	public static final String ALIAS_HANDLE_REMARK = "处理结果";
	public static final String ALIAS_HANDLE_TIME = "处理时间";
	public static final String ALIAS_REF_TYPE = "业务类型";
	public static final String ALIAS_REMITTER = "汇款人";
	public static final String ALIAS_REMITTER_TIME = "汇款时间";
	public static final String ALIAS_BANK_CODE_NAME = "银行名称";
	public static final String ALIAS_PAYCODE = "汇款单号";

	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_HANDLE_TIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private Integer id;
	//
	private Integer tenantId;
	//@NotNull 
	private Date addtime;
	//@NotNull 
	private Date updatetime;
	//@NotNull 
	private Boolean isdeleted;
	//@NotNull 
	private Integer balanceId;
	//@NotNull 
	private Integer amount;
	//@Length(max=36)
	private String applyLoginId;
	//@Length(max=512)
	private String content;
	//@Length(max=4)
	private String handleStatus;
	//@Length(max=36)
	private String handleLoginId;
	//@Length(max=512)
	private String handleRemark;
	//
	private Date handleTime;

	private String refType;
	//@Length(max=32)
	private String remitter;
	//
	private Date remitterTime;
	//@Length(max=4)
	private String bankCode;
	//@Length(max=32)
	private String payCode;

	//columns END


		public TmbRechargeLog(){
		}
		public TmbRechargeLog(Integer id) {
			this.id = id;
		}
	

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public Integer getId() {
		return this.id;
	}
	
	@Column(name = "tenant_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getTenantId() {
		return this.tenantId;
	}
	
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	

	@Column(name = "addtime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	

	@Column(name = "updatetime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public Date getUpdatetime() {
		return this.updatetime;
	}
	
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@Column(name = "isdeleted", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Boolean getIsdeleted() {
		return this.isdeleted;
	}
	
	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	@Column(name = "balance_id", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getBalanceId() {
		return this.balanceId;
	}
	
	public void setBalanceId(Integer balanceId) {
		this.balanceId = balanceId;
	}
	
	@Column(name = "amount", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Column(name = "apply_login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getApplyLoginId() {
		return this.applyLoginId;
	}
	
	public void setApplyLoginId(String applyLoginId) {
		this.applyLoginId = applyLoginId;
	}
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "handle_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getHandleStatus() {
		return this.handleStatus;
	}
	
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	@Column(name = "handle_login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getHandleLoginId() {
		return this.handleLoginId;
	}
	
	public void setHandleLoginId(String handleLoginId) {
		this.handleLoginId = handleLoginId;
	}
	
	@Column(name = "handle_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getHandleRemark() {
		return this.handleRemark;
	}
	
	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}
	

	@Column(name = "handle_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getHandleTime() {
		return this.handleTime;
	}
	
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	@Column(name = "ref_type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	@Column(name = "remitter", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getRemitter() {
		return remitter;
	}

	public void setRemitter(String remitter) {
		this.remitter = remitter;
	}

	@Column(name = "remitter_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getRemitterTime() {
		return remitterTime;
	}

	public void setRemitterTime(Date remitterTime) {
		this.remitterTime = remitterTime;
	}

	@Column(name = "pay_code", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	@Column(name = "bank_code", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Override
	public String toString() {
		return "TmbRechargeLog{" +
				"id=" + id +
				", tenantId=" + tenantId +
				", addtime=" + addtime +
				", updatetime=" + updatetime +
				", isdeleted=" + isdeleted +
				", balanceId=" + balanceId +
				", amount=" + amount +
				", applyLoginId='" + applyLoginId + '\'' +
				", content='" + content + '\'' +
				", handleStatus='" + handleStatus + '\'' +
				", handleLoginId='" + handleLoginId + '\'' +
				", handleRemark='" + handleRemark + '\'' +
				", handleTime=" + handleTime +
				", refType='" + refType + '\'' +
				", remitter='" + remitter + '\'' +
				", remitterTime=" + remitterTime +
				", bankCode='" + bankCode + '\'' +
				", payCode='" + payCode + '\'' +
				'}';
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
			.append("ApplyLoginId",getApplyLoginId())
			.append("Content",getContent())
			.append("HandleStatus",getHandleStatus())
			.append("HandleLoginId",getHandleLoginId())
			.append("HandleRemark",getHandleRemark())
			.append("HandleTime",getHandleTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbRechargeLog == false) return false;
		if(this == obj) return true;
		MbRechargeLog other = (MbRechargeLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

