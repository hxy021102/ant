/*
 * @author John
 * @date - 2017-07-15
 */

package com.mobian.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_supplier_contract_item")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbSupplierContractItem implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbSupplierContractItem";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SUPPLIER_CONTRACT_ID = "合同ID";
	public static final String ALIAS_ITEM_ID = "商品ID";
	public static final String ALIAS_ITEM_NAME = "商品名称";
	public static final String ALIAS_PRICE = "合同价格";
	
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
	//@NotNull 
	private Integer supplierContractId;
	//
	private Integer itemId;
	//
	private Integer price;
	//columns END


		public TmbSupplierContractItem(){
		}
		public TmbSupplierContractItem(Integer id) {
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
	
	@Column(name = "supplier_contract_id", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getSupplierContractId() {
		return this.supplierContractId;
	}
	
	public void setSupplierContractId(Integer supplierContractId) {
		this.supplierContractId = supplierContractId;
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
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("SupplierContractId",getSupplierContractId())
			.append("ItemId",getItemId())
			.append("Price",getPrice())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbSupplierContractItem == false) return false;
		if(this == obj) return true;
		MbSupplierContractItem other = (MbSupplierContractItem)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

