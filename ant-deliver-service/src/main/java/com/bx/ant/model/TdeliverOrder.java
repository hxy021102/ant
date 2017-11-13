/*
 * @author John
 * @date - 2017-09-21
 */

package com.bx.ant.model;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "deliver_order")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TdeliverOrder implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "DeliverOrder";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SUPPLIER_ID = "供应商ID";
	public static final String ALIAS_AMOUNT = "总金额";
	public static final String ALIAS_STATUS = "订单状态";
	public static final String ALIAS_DELIVERY_STATUS = "配送状态";
	public static final String ALIAS_DELIVERY_REQUIRE_TIME = "deliveryRequireTime";
	public static final String ALIAS_DELIVERY_ADDRESS = "配送地址";
	public static final String ALIAS_DELIVERY_REGION = "配送地区";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	public static final String ALIAS_SHOP_PAY_STATUS = "门店结算";
	public static final String ALIAS_PAY_WAY = "支付方式";
	public static final String ALIAS_CONTACT_PHONE = "联系电话";
	public static final String ALIAS_CONTACT_PEOPLE = "联系人";
	public static final String ALIAS_REMARK = "备注";
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_DELIVERY_REQUIRE_TIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	private Integer supplierId;
	//
	private Integer amount;
	//@Length(max=10)
	private String status;
	//@Length(max=10)
	private String deliveryStatus;
	//
	private Date deliveryRequireTime;
	//@Length(max=512)
	private String deliveryAddress;
	//
	private Integer deliveryRegion;
	//@Length(max=10)
	private String payStatus;
	//@Length(max=10)
	private Integer shopId;
	//@Length(max=10)
	private String shopPayStatus;
	//@Length(max=10)
	private String payWay;
	//@Length(max=32)
	private String contactPhone;
	//@Length(max=32)
	private String contactPeople;
	//@Length(max=512)
	private String remark;

	private Integer weight;
	//columns END

	private String supplierOrderId;

	private BigDecimal longitude;

	private BigDecimal latitude;

	private String completeImages;
	private String completeRemark;


		public TdeliverOrder(){
		}
		public TdeliverOrder(Long id) {
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
	
	@Column(name = "supplier_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	
	@Column(name = "amount", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "delivery_status", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
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
	
	@Column(name = "pay_status", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getPayStatus() {
		return this.payStatus;
	}
	
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	@Column(name = "shop_pay_status", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getShopPayStatus() {
		return this.shopPayStatus;
	}
	
	public void setShopPayStatus(String shopPayStatus) {
		this.shopPayStatus = shopPayStatus;
	}
	
	@Column(name = "pay_way", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getPayWay() {
		return this.payWay;
	}
	
	public void setPayWay(String payWay) {
		this.payWay = payWay;
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
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "shop_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	@Column(name = "longitude", unique = false, nullable = true, insertable = true, updatable = true, scale = 6)
	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", unique = false, nullable = true, insertable = true, updatable = true, scale = 6)
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	@Column(name = "supplier_order_id", unique = false, nullable = true, insertable = true, updatable = true, scale = 32)
	public String getSupplierOrderId() {
		return supplierOrderId;
	}

	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}

	@Column(name = "complete_images", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getCompleteImages() {
		return completeImages;
	}

	public void setCompleteImages(String completeImages) {
		this.completeImages = completeImages;
	}

	@Column(name = "complete_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getCompleteRemark() {
		return completeRemark;
	}

	public void setCompleteRemark(String completeRemark) {
		this.completeRemark = completeRemark;
	}

	@Column(name = "weight", unique = false, nullable = true, insertable = true, updatable = true, scale = 11)
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("SupplierId",getSupplierId())
			.append("Amount",getAmount())
			.append("Status",getStatus())
			.append("DeliveryStatus",getDeliveryStatus())
			.append("DeliveryRequireTime",getDeliveryRequireTime())
			.append("DeliveryAddress",getDeliveryAddress())
			.append("DeliveryRegion",getDeliveryRegion())
			.append("PayStatus",getPayStatus())
			.append("ShopPayStatus",getShopPayStatus())
			.append("PayWay",getPayWay())
			.append("ContactPhone",getContactPhone())
			.append("ContactPeople",getContactPeople())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof DeliverOrder == false) return false;
		if(this == obj) return true;
		DeliverOrder other = (DeliverOrder)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

