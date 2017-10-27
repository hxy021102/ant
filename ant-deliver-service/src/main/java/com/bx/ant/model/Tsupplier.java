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
@Table(name = "supplier")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tsupplier implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Supplier";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_APP_KEY = "应用appKey";
	public static final String ALIAS_APP_SECRET = "appSecret";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_NAME = "接入方名称";
	public static final String ALIAS_ADDRESS = "接入方地址";
	public static final String ALIAS_CHARTER_URL = "营业执照";
	public static final String ALIAS_CONTACTER = "联系人";
	public static final String ALIAS_CONTACT_PHONE = "联系电话";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_LOGIN_ID = "loginId";
	public static final String ALIAS_AUDIT_DATE = "审核日期";
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_AUDIT_DATE = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	//@Length(max=32)
	private String appKey;
	//@Length(max=32)
	private String appSecret;
	//@Length(max=10)
	private String status;
	//@Length(max=128)
	private String name;
	//@Length(max=512)
	private String address;
	//@Length(max=512)
	private String charterUrl;
	//@Length(max=32)
	private String contacter;
	//@Length(max=32)
	private String contactPhone;
	//@Length(max=65535)
	private String remark;
	//@Length(max=36)
	private String loginId;
	//
	private Date auditDate;
	//columns END


		public Tsupplier(){
		}
		public Tsupplier(Integer id) {
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
	
	@Column(name = "app_key", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getAppKey() {
		return this.appKey;
	}
	
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	@Column(name = "app_secret", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getAppSecret() {
		return this.appSecret;
	}
	
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "address", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "charter_url", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getCharterUrl() {
		return this.charterUrl;
	}
	
	public void setCharterUrl(String charterUrl) {
		this.charterUrl = charterUrl;
	}
	
	@Column(name = "contacter", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getContacter() {
		return this.contacter;
	}
	
	public void setContacter(String contacter) {
		this.contacter = contacter;
	}
	
	@Column(name = "contact_phone", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getContactPhone() {
		return this.contactPhone;
	}
	
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 65535)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "loginId", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getLoginId() {
		return this.loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	

	@Column(name = "audit_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAuditDate() {
		return this.auditDate;
	}
	
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("AppKey",getAppKey())
			.append("AppSecret",getAppSecret())
			.append("Status",getStatus())
			.append("Name",getName())
			.append("Address",getAddress())
			.append("CharterUrl",getCharterUrl())
			.append("Contacter",getContacter())
			.append("ContactPhone",getContactPhone())
			.append("Remark",getRemark())
			.append("LoginId",getLoginId())
			.append("AuditDate",getAuditDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Supplier == false) return false;
		if(this == obj) return true;
		Supplier other = (Supplier)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

