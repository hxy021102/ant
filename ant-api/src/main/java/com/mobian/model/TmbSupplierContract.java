/*
 * @author John
 * @date - 2017-07-15
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_supplier_contract")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbSupplierContract implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbSupplierContract";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_CODE = "合同代码";
	public static final String ALIAS_NAME = "合同名称";
	public static final String ALIAS_SUPPLIER_ID = "供应商ID";
	//新加供应商名称
	public static final String ALIAS_SUPPLIER_NAME = "供应商名称";
	public static final String ALIAS_EXPIRY_DATE_START = "有效期开始";
	public static final String ALIAS_EXPIRY_DATE_END = "有效期期结束";
	public static final String ALIAS_VALID = "是否有效";
	public static final String ALIAS_ATTACHMENT = "附件";
	public static final String ALIAS_CONTRACT_TYPE = "合同类型";
	public static final String ALIAS_RATE = "税率";
	public static final String ALIAS_PAYMENT_DAYS = "账期";
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_EXPIRY_DATE_START = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_EXPIRY_DATE_END = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	//@Length(max=64)
	private String code;
	//@NotBlank @Length(max=128)
	private String name;
	//
	private Integer supplierId;
	//
	private Date expiryDateStart;
	//
	private Date expiryDateEnd;
	//
	private Boolean valid;
	//@Length(max=512)
	private String attachment;
	//@Length(max=4)
	private String contractType;
	//
	private Integer paymentDays;
	//columns END

	private Integer rate; //费率


		public TmbSupplierContract(){
		}
		public TmbSupplierContract(Integer id) {
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
	
	@Column(name = "code", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 128)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "supplier_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "rate", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	@Column(name = "expiry_date_start", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getExpiryDateStart() {
		return this.expiryDateStart;
	}
	
	public void setExpiryDateStart(Date expiryDateStart) {
		this.expiryDateStart = expiryDateStart;
	}
	

	@Column(name = "expiry_date_end", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getExpiryDateEnd() {
		return this.expiryDateEnd;
	}
	
	public void setExpiryDateEnd(Date expiryDateEnd) {
		this.expiryDateEnd = expiryDateEnd;
	}
	
	@Column(name = "valid", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getValid() {
		return this.valid;
	}
	
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	@Column(name = "attachment", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getAttachment() {
		return this.attachment;
	}
	
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	@Column(name = "contract_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getContractType() {
		return this.contractType;
	}
	
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	@Column(name = "payment_days", unique = false, nullable = true, insertable = true, updatable = true, length = 11)

	public Integer getPaymentDays() {
		return paymentDays;
	}

	public void setPaymentDays(Integer paymentDays) {
		this.paymentDays = paymentDays;
	}
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("Code",getCode())
			.append("Name",getName())
			.append("SupplierId",getSupplierId())
			.append("ExpiryDateStart",getExpiryDateStart())
			.append("ExpiryDateEnd",getExpiryDateEnd())
			.append("Valid",getValid())
			.append("Attachment",getAttachment())
			.append("ContractType",getContractType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbSupplierContract == false) return false;
		if(this == obj) return true;
		MbSupplierContract other = (MbSupplierContract)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

