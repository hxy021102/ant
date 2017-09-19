/*
 * @author John
 * @date - 2017-08-18
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_log_record")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbLogRecord implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbLogRecord";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_TENANT_ID = "tenantId";
	public static final String ALIAS_ADDTIME = "addtime";
	public static final String ALIAS_UPDATETIME = "updatetime";
	public static final String ALIAS_ISDELETED = "isdeleted";
	public static final String ALIAS_LOG_USER_ID = "操作人ID";
	public static final String ALIAS_LOG_USER_NAME = "操作人姓名";
	public static final String ALIAS_URL = "操作的地址";
	public static final String ALIAS_URL_NAME = "操作的名称";
	public static final String ALIAS_FORM_DATA = "操作的JSON内容";
	
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
	//@NotBlank @Length(max=36)
	private String logUserId;
	//@Length(max=36)
	private String logUserName;
	//@Length(max=512)
	private String url;
	//@Length(max=128)
	private String urlName;
	//@Length(max=65535)
	private String formData;
	//columns END

	private String result;

	private Boolean isSuccess;

	private Integer processTime;


		public TmbLogRecord(){
		}
		public TmbLogRecord(Integer id) {
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
	
	@Column(name = "log_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getLogUserId() {
		return this.logUserId;
	}
	
	public void setLogUserId(String logUserId) {
		this.logUserId = logUserId;
	}
	
	@Column(name = "log_user_name", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getLogUserName() {
		return this.logUserName;
	}
	
	public void setLogUserName(String logUserName) {
		this.logUserName = logUserName;
	}
	
	@Column(name = "url", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "url_name", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getUrlName() {
		return this.urlName;
	}
	
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	
	@Column(name = "form_data", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public String getFormData() {
		return this.formData;
	}
	
	public void setFormData(String formData) {
		this.formData = formData;
	}

	@Column(name = "result", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "is_success", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	@Column(name = "process_time", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Integer processTime) {
		this.processTime = processTime;
	}
	

	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("LogUserId",getLogUserId())
			.append("LogUserName",getLogUserName())
			.append("Url",getUrl())
			.append("UrlName",getUrlName())
			.append("FormData",getFormData())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbLogRecord == false) return false;
		if(this == obj) return true;
		MbLogRecord other = (MbLogRecord)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

