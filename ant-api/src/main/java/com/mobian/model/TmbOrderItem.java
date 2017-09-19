/*
 * @author John
 * @date - 2017-04-25
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_order_item")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbOrderItem implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbOrderItem";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_ITEM_ID = "商品ID";
	public static final String ALIAS_ITEM_NAME = "商品名称";
	public static final String ALIAS_QUANTITY = "数量";
	public static final String ALIAS_MARKET_PRICE = "市场价";
	public static final String ALIAS_BUY_PRICE = "订单价";
	public static final String ALIAS_ORDER_ID = "订单ID";
	public static final String ALIAS_USABLE_QUANTITY="可用库存量";
	
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
	private java.util.Date addtime;
	//@NotNull
	private java.util.Date updatetime;
	//@NotNull
	private Boolean isdeleted;
	//
	private Integer itemId;
	//
	private Integer quantity;
	//
	private Integer marketPrice;
	//
	private Integer buyPrice;
	//
	private Integer orderId;
	//columns END


		public TmbOrderItem(){
		}
		public TmbOrderItem(Integer id) {
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
	public Boolean getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	@Column(name = "item_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "quantity", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "market_price", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getMarketPrice() {
		return this.marketPrice;
	}

	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}

	@Column(name = "buy_price", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBuyPrice() {
		return this.buyPrice;
	}

	public void setBuyPrice(Integer buyPrice) {
		this.buyPrice = buyPrice;
	}

	@Column(name = "order_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("ItemId",getItemId())
			.append("Quantity",getQuantity())
			.append("MarketPrice",getMarketPrice())
			.append("BuyPrice",getBuyPrice())
			.append("OrderId",getOrderId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbOrderItem == false) return false;
		if(this == obj) return true;
		MbOrderItem other = (MbOrderItem)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

