/*
 * @author John
 * @date - 2017-04-18
 */

package com.mobian.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_payment")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbPayment implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbPayment";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "0成功1失败";
	public static final String ALIAS_ORDER_ID = "订单ID";
	public static final String ALIAS_ORDER_TYPE = "订单类型，OT01=商家订单|OT02=C端订单";
	public static final String ALIAS_AMOUNT = "订单金额";
	public static final String ALIAS_PAY_WAY = "支付方式";
	public static final String ALIAS_STATUS = "支付状态";
	public static final String ALIAS_REASON = "支付失败，写原因";
	public static final String ALIAS_PAY_CONSIST = "支付组成";
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	private Integer orderId;
	//@Length(max=32)
	private String orderType;
	//
	private Integer amount;
	//@Length(max=4)
	private String payWay;
	//
	private Boolean status;
	//@Length(max=512)
	private String reason;
	//columns END


		public TmbPayment(){
		}
		public TmbPayment(Integer id) {
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
	
	@Column(name = "order_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "order_type", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getOrderType() {
		return this.orderType;
	}
	
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	@Column(name = "amount", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Column(name = "pay_way", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getPayWay() {
		return this.payWay;
	}
	
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getStatus() {
		return this.status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@Column(name = "reason", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getReason() {
		return this.reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("OrderId",getOrderId())
			.append("OrderType",getOrderType())
			.append("Amount",getAmount())
			.append("PayWay",getPayWay())
			.append("Status",getStatus())
			.append("Reason",getReason())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbPayment == false) return false;
		if(this == obj) return true;
		MbPayment other = (MbPayment)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

