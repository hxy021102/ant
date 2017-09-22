/*
 * @author John
 * @date - 2017-04-18
 */

package com.mobian.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_contract")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbContract implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbContract";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_CODE = "合同代码";
	public static final String ALIAS_NAME = "合同名称";
	public static final String ALIAS_SHOP_ID = "门店ID";
	public static final String ALIAS_SHOP_NAME = "客户名称";
	public static final String ALIAS_EXPIRY_DATE_START = "合同有效期开始时间";
	public static final String ALIAS_EXPIRY_DATE_END = "合同有效期结束时间";
	public static final String ALIAS_VALID = "是否有效";
	public static final String ALIAS_ATTACHMENT = "附件";
	
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
	private Integer shopId;
	//
	private Date expiryDateStart;
	//
	private Date expiryDateEnd;
	//
	private Boolean valid;
	//@Length(max=512)
	private String attachment;
	//columns END


		public TmbContract(){
		}
		public TmbContract(Integer id) {
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
	
	@Column(name = "shop_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getShopId() {
		return this.shopId;
	}
	
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	

	@Column(name = "expiry_date_start", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Date getExpiryDateStart() {
		return this.expiryDateStart;
	}
	
	public void setExpiryDateStart(Date expiryDateStart) {
		this.expiryDateStart = expiryDateStart;
	}
	

	@Column(name = "expiry_date_end", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
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
			.append("ShopId",getShopId())
			.append("ExpiryDateStart",getExpiryDateStart())
			.append("ExpiryDateEnd",getExpiryDateEnd())
			.append("Valid",getValid())
			.append("Attachment",getAttachment())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbContract == false) return false;
		if(this == obj) return true;
		MbContract other = (MbContract)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

