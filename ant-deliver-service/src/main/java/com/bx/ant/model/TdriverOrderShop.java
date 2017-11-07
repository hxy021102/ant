/*
 * @author John
 * @date - 2017-11-03
 */

package com.bx.ant.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "driver_order_shop")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TdriverOrderShop implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "DriverOrderShop";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_DELIVER_ORDER_SHOP_ID = "门店运单ID";
	public static final String ALIAS_SHOP_ID = "门店ID";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_AMOUNT = "总价";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	public static final String ALIAS_DRIVER_ORDER_SHOP_BILL_ID = "骑手支付账单";

	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME =com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private Long id;
	//
	private Integer tenantId;
	//@NotNull 
	private Date addtime;
	//@NotNull 
	private Date updatetime;
	//@NotNull 
	private Boolean isdeleted;
	//
	private Long deliverOrderShopId;
	//
	private Integer shopId;
	//@Length(max=10)
	private String status;
	//
	private Integer amount;
	//@Length(max=10)
	private String payStatus;
	//
	private Long driverOrderShopBillId;

	//
	private Integer driverAccountId;

	private String remark;
	//columns END


		public TdriverOrderShop(){
		}
		public TdriverOrderShop(Long id) {
			this.id = id;
		}
	

	public void setId(Long id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 19)
	public Long getId() {
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
	
	@Column(name = "deliver_order_shop_id", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Long getDeliverOrderShopId() {
		return this.deliverOrderShopId;
	}
	
	public void setDeliverOrderShopId(Long deliverOrderShopId) {
		this.deliverOrderShopId = deliverOrderShopId;
	}
	
	@Column(name = "shop_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getShopId() {
		return this.shopId;
	}
	
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "amount", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Column(name = "pay_status", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getPayStatus() {
		return this.payStatus;
	}
	
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	@Column(name = "driver_order_shop_bill_id", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Long getDriverOrderShopBillId() {
		return this.driverOrderShopBillId;
	}
	
	public void setDriverOrderShopBillId(Long driverOrderShopBillId) {
		this.driverOrderShopBillId = driverOrderShopBillId;
	}
	@Column(name = "driver_account_id", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
	public Integer getDriverAccountId() {
		return driverAccountId;
	}

	public void setDriverAccountId(Integer driverAccountId) {
		this.driverAccountId = driverAccountId;
	}

	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("DeliverOrderShopId",getDeliverOrderShopId())
			.append("ShopId",getShopId())
			.append("Status",getStatus())
			.append("Amount",getAmount())
			.append("PayStatus",getPayStatus())
			.append("DriverOrderShopBillId",getDriverOrderShopBillId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof DriverOrderShop == false) return false;
		if(this == obj) return true;
		DriverOrderShop other = (DriverOrderShop)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

