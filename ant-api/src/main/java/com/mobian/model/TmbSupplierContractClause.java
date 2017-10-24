/*
 * @author John
 * @date - 2017-10-24
 */

package com.mobian.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_supplier_contract_clause")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbSupplierContractClause implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbSupplierContractClause";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SUPPLIER_CONTRACT_ID = "供应商合同ID";
	public static final String ALIAS_CLAUSE_CODE = "条款编码";
	public static final String ALIAS_VALUE = "计算值";
	public static final String ALIAS_REMARK = "备注";
	
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
	private Integer supplierContractId;
	//@Length(max=10)
	private String clauseCode;
	//@Length(max=10)
	private String value;
	//@Length(max=255)
	private String remark;
	//columns END


		public TmbSupplierContractClause(){
		}
		public TmbSupplierContractClause(Integer id) {
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
	
	@Column(name = "supplier_contract_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSupplierContractId() {
		return this.supplierContractId;
	}
	
	public void setSupplierContractId(Integer supplierContractId) {
		this.supplierContractId = supplierContractId;
	}
	
	@Column(name = "clause_code", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getClauseCode() {
		return this.clauseCode;
	}
	
	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}
	
	@Column(name = "value", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getRemark() {
		return this.remark;
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
			.append("SupplierContractId",getSupplierContractId())
			.append("ClauseCode",getClauseCode())
			.append("Value",getValue())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbSupplierContractClause == false) return false;
		if(this == obj) return true;
		MbSupplierContractClause other = (MbSupplierContractClause)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

