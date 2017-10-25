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
@Table(name = "supplier_item_relation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TsupplierItemRelation implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SupplierItemRelation";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SUPPLIER_ID = "用户ID";
	public static final String ALIAS_ITEM_ID = "联系电话";
	public static final String ALIAS_PRICE = "总金额";
	public static final String ALIAS_IN_PRICE = "采购价";
	public static final String ALIAS_FREIGHT = "运费单价";
	public static final String ALIAS_ONLINE = "上下架 1上架、0下架";
	
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
	private Integer supplierId;
	//
	private Integer itemId;
	//
	private Integer price;
	//
	private Integer inPrice;
	//
	private Integer freight;
	//@NotNull 
	private Boolean online;

	private String supplierItemCode;
	//columns END


		public TsupplierItemRelation(){
		}
		public TsupplierItemRelation(Integer id) {
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
	
	@Column(name = "supplier_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
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
	
	@Column(name = "online", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Boolean getOnline() {
		return this.online;
	}
	
	public void setOnline(Boolean online) {
		this.online = online;
	}

	@Column(name="supplier_item_code", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getSupplierItemCode() {
		return supplierItemCode;
	}

	public void setSupplierItemCode(String supplierItemCode) {
		this.supplierItemCode = supplierItemCode;
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
			.append("ItemId",getItemId())
			.append("Price",getPrice())
			.append("InPrice",getInPrice())
			.append("Freight",getFreight())
			.append("Online",getOnline())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierItemRelation == false) return false;
		if(this == obj) return true;
		SupplierItemRelation other = (SupplierItemRelation)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

