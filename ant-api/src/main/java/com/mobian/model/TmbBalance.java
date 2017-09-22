/*
 * @author John
 * @date - 2017-04-18
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_balance")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbBalance implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbBalance";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_AMOUNT = "参考名称";
	public static final String ALIAS_REF_ID = "具体地址";
	public static final String ALIAS_REF_TYPE = "1=商家端余额，2=用户端余额";
	
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
	//@NotNull 
	private Integer amount;
	//
	private Integer refId;
	//
	private Integer refType;
	//columns END


		public TmbBalance(){
		}
		public TmbBalance(Integer id) {
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
	
	@Column(name = "amount", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Column(name = "ref_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getRefId() {
		return this.refId;
	}
	
	public void setRefId(Integer refId) {
		this.refId = refId;
	}
	
	@Column(name = "ref_type", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Integer getRefType() {
		return this.refType;
	}
	
	public void setRefType(Integer refType) {
		this.refType = refType;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("Amount",getAmount())
			.append("RefId",getRefId())
			.append("RefType",getRefType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbBalance == false) return false;
		if(this == obj) return true;
		MbBalance other = (MbBalance)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

