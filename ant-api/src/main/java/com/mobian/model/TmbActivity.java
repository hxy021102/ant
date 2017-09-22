/*
 * @author John
 * @date - 2017-08-10
 */

package com.mobian.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_activity")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbActivity implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbActivity";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_NAME = "活动名称";
	public static final String ALIAS_EXPIRY_DATE_START = "开始时间";
	public static final String ALIAS_EXPIRY_DATE_END = "结束时间";
	public static final String ALIAS_VALID = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	
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
	private String name;
	//
	private Date expiryDateStart;
	//
	private Date expiryDateEnd;
	//
	private Boolean valid;
	//@Length(max=512)
	private String remark;

	private String refId;

	//columns END
	private String type;

	private String languageType;


		public TmbActivity(){
		}
		public TmbActivity(Integer id) {
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
	
	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "type", unique = false, nullable = false, insertable = true, updatable = true, length = 6)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "language_type", unique = false, nullable = false, insertable = true, updatable = true, length = 6)
	public String getLanguageType() {
		return languageType;
	}

	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}

	@Column(name = "ref_id", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("Name",getName())
			.append("ExpiryDateStart",getExpiryDateStart())
			.append("ExpiryDateEnd",getExpiryDateEnd())
			.append("Valid",getValid())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbActivity == false) return false;
		if(this == obj) return true;
		MbActivity other = (MbActivity)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

