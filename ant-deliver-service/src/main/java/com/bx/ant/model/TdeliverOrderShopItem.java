/*
 * @author John
 * @date - 2017-09-21
 */

package com.bx.ant.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "deliver_order_shop_item")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TdeliverOrderShopItem implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "DeliverOrderShopItem";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_DELIVER_ORDER_SHOP_ID = "接单ID";
	public static final String ALIAS_DELIVER_ORDER_ID = "运单ID";
	public static final String ALIAS_SHOP_ID = "门店ID";
	public static final String ALIAS_ITEM_ID = "商品ID";
	public static final String ALIAS_PRICE = "单价";
	public static final String ALIAS_IN_PRICE = "采购价";
	public static final String ALIAS_FREIGHT = "运费";
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	private Long deliverOrderId;
	//
	private Integer shopId;
	//
	private Integer itemId;
	//
	private Integer price;
	//
	private Integer inPrice;
	//
	private Integer freight;

	private Integer quantity;
	//columns END


		public TdeliverOrderShopItem(){
		}
		public TdeliverOrderShopItem(Long id) {
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
	
	@Column(name = "deliver_order_id", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Long getDeliverOrderId() {
		return this.deliverOrderId;
	}
	
	public void setDeliverOrderId(Long deliverOrderId) {
		this.deliverOrderId = deliverOrderId;
	}
	
	@Column(name = "shop_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getShopId() {
		return this.shopId;
	}
	
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	@Column(name = "item_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getItemId() {
		return this.itemId;
	}
	
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	@Column(name = "price", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getPrice() {
		return this.price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	@Column(name = "in_price", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getInPrice() {
		return this.inPrice;
	}
	
	public void setInPrice(Integer inPrice) {
		this.inPrice = inPrice;
	}
	
	@Column(name = "freight", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getFreight() {
		return this.freight;
	}
	
	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	@Column(name = "quantity", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
			.append("DeliverOrderId",getDeliverOrderId())
			.append("ShopId",getShopId())
			.append("ItemId",getItemId())
			.append("Price",getPrice())
			.append("InPrice",getInPrice())
			.append("Freight",getFreight())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof DeliverOrderShopItem == false) return false;
		if(this == obj) return true;
		DeliverOrderShopItem other = (DeliverOrderShopItem)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

