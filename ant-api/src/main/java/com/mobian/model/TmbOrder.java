/*
 * @author John
 * @date - 2017-04-25
 */

package com.mobian.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_order")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbOrder implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbOrder";
	public static final String ALIAS_ID = "订单ID";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "下单时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_USER_NICK_NAME = "昵称";
	public static final String ALIAS_TOTAL_PRICE = "总金额";
	public static final String ALIAS_TOTAL_REFUND_AMOUNT = "退款金额";
	public static final String ALIAS_STATUS = "订单状态";
	public static final String ALIAS_DELIVERY_WAY = "配送方式";
	public static final String ALIAS_DELIVERY_STATUS = "配送状态";
	public static final String ALIAS_DELIVERY_REQUIRE_TIME = "时间要求";
	public static final String ALIAS_DELIVERY_ADDRESS = "配送地址";
	public static final String ALIAS_DELIVERY_REGION = "配送地区";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	public static final String ALIAS_PAY_WAY = "支付方式";
	public static final String ALIAS_PAY_TIME = "支付时间";
	public static final String ALIAS_INVOICE_WAY = "开票方式";
	public static final String ALIAS_INVOICE_STATUS = "发票状态";
	public static final String ALIAS_CONTACT_PHONE = "联系电话";
	public static final String ALIAS_CONTACT_PEOPLE = "收货人";
	public static final String ALIAS_USER_REMARK = "用户备注";
	public static final String ALIAS_SHOP_ID = "shopId";
	public static final String ALIAS_SHOP_NAME = "客户名称";
	public static final String ALIAS_ORDER_PRICE = "订单金额";
	public static final String ALIAS_DELIVERY_PRICE = "配送费用";
	public static final String ALIAS_DELIVERY_DRIVER = "司机";
	public static final String ALIAS_DELIVERY_TIME = "发货时间";

	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_DELIVERY_REQUIRE_TIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_PAY_TIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	private Integer userId;
	//
	private Integer totalPrice;
	//退款金额@Length(max=10)
	private Integer totalRefundAmount;
	//@Length(max=4)
	private String status;
	//@Length(max=10)
	private Integer deliveryWarehouseId;
	//@Length(max=4)
	private String deliveryWay;
	//@Length(max=4)
	private String deliveryStatus;
	//
	private Date deliveryRequireTime;
	//@Length(max=512)
	private String deliveryAddress;
	//
	private Integer deliveryRegion;
	private Integer deliveryCost;
	//@Length(max=4)
	private String payStatus;
	//@Length(max=4)
	private String payWay;
	//
	private Date payTime;
	//@Length(max=4)
	private String invoiceWay;
	//@Length(max=32)
	private String contactPhone;
	//@Length(max=32)
	private String contactPeople;
	//@Length(max=512)
	private String userRemark;

	private String deliveryDriver;

	private Date deliveryTime;

	private String addLoginId;

	private Integer shopId;
	//columns END


		public TmbOrder(){
		}
		public TmbOrder(Integer id) {
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
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getUserId() {
		return this.userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "total_price", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getTotalPrice() {
		return this.totalPrice;
	}
	
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "delivery_way", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getDeliveryWay() {
		return this.deliveryWay;
	}
	
	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}
	
	@Column(name = "delivery_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getDeliveryStatus() {
		return this.deliveryStatus;
	}
	
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	

	@Column(name = "delivery_require_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getDeliveryRequireTime() {
		return this.deliveryRequireTime;
	}
	
	public void setDeliveryRequireTime(Date deliveryRequireTime) {
		this.deliveryRequireTime = deliveryRequireTime;
	}
	
	@Column(name = "delivery_address", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getDeliveryAddress() {
		return this.deliveryAddress;
	}
	
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	@Column(name = "delivery_region", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getDeliveryRegion() {
		return this.deliveryRegion;
	}
	
	public void setDeliveryRegion(Integer deliveryRegion) {
		this.deliveryRegion = deliveryRegion;
	}
	
	@Column(name = "pay_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getPayStatus() {
		return this.payStatus;
	}
	
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	@Column(name = "pay_way", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getPayWay() {
		return this.payWay;
	}
	
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	

	@Column(name = "pay_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getPayTime() {
		return this.payTime;
	}
	
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@Column(name = "invoice_way", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getInvoiceWay() {
		return this.invoiceWay;
	}
	
	public void setInvoiceWay(String invoiceWay) {
		this.invoiceWay = invoiceWay;
	}
	
	@Column(name = "contact_phone", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getContactPhone() {
		return this.contactPhone;
	}
	
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@Column(name = "contact_people", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getContactPeople() {
		return this.contactPeople;
	}
	
	public void setContactPeople(String contactPeople) {
		this.contactPeople = contactPeople;
	}
	
	@Column(name = "user_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getUserRemark() {
		return this.userRemark;
	}
	
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	@Column(name = "delivery_driver", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getDeliveryDriver() {
		return deliveryDriver;
	}

	public void setDeliveryDriver(String deliveryDriver) {
		this.deliveryDriver = deliveryDriver;
	}

	@Column(name = "delivery_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	

	@Column(name = "delivery_warehouse_id",unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getDeliveryWarehouseId() {
		return deliveryWarehouseId;
	}

	public void setDeliveryWarehouseId(Integer deliveryWarehouseId) {
		this.deliveryWarehouseId = deliveryWarehouseId;
	}
	@Column(name = "total_refund_amount", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getTotalRefundAmount() {
		return totalRefundAmount;
	}

	public void setTotalRefundAmount(Integer totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}


	@Column(name = "add_login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getAddLoginId() {
		return addLoginId;
	}

	public void setAddLoginId(String addLoginId) {
		this.addLoginId = addLoginId;
	}
	@Column(name = "shop_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	@Column(name = "delivery_cost", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(Integer deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("UserId",getUserId())
			.append("TotalPrice",getTotalPrice())
			.append("Status",getStatus())
			.append("DeliveryWay",getDeliveryWay())
			.append("DeliveryStatus",getDeliveryStatus())
			.append("DeliveryRequireTime",getDeliveryRequireTime())
			.append("DeliveryAddress",getDeliveryAddress())
			.append("DeliveryRegion",getDeliveryRegion())
			.append("PayStatus",getPayStatus())
			.append("PayWay",getPayWay())
			.append("PayTime",getPayTime())
			.append("InvoiceWay",getInvoiceWay())
			.append("ContactPhone",getContactPhone())
			.append("ContactPeople",getContactPeople())
			.append("UserRemark",getUserRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbOrder == false) return false;
		if(this == obj) return true;
		MbOrder other = (MbOrder)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

