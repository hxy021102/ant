/*
 * @author John
 * @date - 2017-04-18
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_payment_item")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbPaymentItem implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbPaymentItem";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "0成功1失败";
	public static final String ALIAS_PAYMENT_ID = "支付记录ID";
	public static final String ALIAS_PAY_WAY = "支付方式";
	public static final String ALIAS_AMOUNT = "订单金额";
	public static final String ALIAS_REMITTER = "汇款人";
	public static final String ALIAS_REMITTER_TIME = "汇款时间";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_REF_ID = "refId";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_BANK_CODE_NAME = "银行名称";
	public static final String ALIAS_PAY_CODE = "银行汇款单号";
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_REMITTER_TIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	//
	private Integer paymentId;
	//@Length(max=4)
	private String payWay;
	//
	private Integer amount;
	//@Length(max=32)
	private String remitter;
	//
	private Date remitterTime;
	//@Length(max=512)
	private String remark;
	//@Length(max=64)
	private String refId;
	//@Length(max=4)
	private String bankCode;
	//@Length(max=64)
	private String payCode;

	private Integer couponsId;

	//columns END


		public TmbPaymentItem(){
		}
		public TmbPaymentItem(Integer id) {
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

	@Column(name = "payment_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	@Column(name = "pay_way", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getPayWay() {
		return this.payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	@Column(name = "amount", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Column(name = "remitter", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getRemitter() {
		return this.remitter;
	}

	public void setRemitter(String remitter) {
		this.remitter = remitter;
	}


	@Column(name = "remitter_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getRemitterTime() {
		return this.remitterTime;
	}

	public void setRemitterTime(Date remitterTime) {
		this.remitterTime = remitterTime;
	}

	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "ref_id", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getRefId() {
		return this.refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	@Column(name = "bank_code", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@Column(name = "pay_code", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	@Column(name = "coupons_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getCouponsId() {
		return couponsId;
	}

	public void setCouponsId(Integer couponsId) {
		this.couponsId = couponsId;
	}
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("PaymentId",getPaymentId())
			.append("PayWay",getPayWay())
			.append("Amount",getAmount())
			.append("Remitter",getRemitter())
			.append("RemitterTime",getRemitterTime())
			.append("Remark",getRemark())
			.append("RefId",getRefId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbPaymentItem == false) return false;
		if(this == obj) return true;
		MbPaymentItem other = (MbPaymentItem)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

