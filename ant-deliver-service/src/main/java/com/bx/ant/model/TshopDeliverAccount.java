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
@Table(name = "shop_deliver_account")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TshopDeliverAccount implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ShopDeliverAccount";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_USER_NAME = "账号";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_NICK_NAME = "昵称";
	public static final String ALIAS_ICON = "头像";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_REF_ID = "第三方账号ID";
	public static final String ALIAS_REF_TYPE = "第三方账号";
	
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
	//@Length(max=32)
	private String userName;
	//@Length(max=32)
	private String password;
	//@Max(127)
	private Integer nickName;
	//@Length(max=512)
	private String icon;
	//@NotBlank @Length(max=4)
	private String sex;
	//@Length(max=64)
	private String refId;
	//@Length(max=4)
	private String refType;
	//columns END


		public TshopDeliverAccount(){
		}
		public TshopDeliverAccount(Integer id) {
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
	
	@Column(name = "user_name", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "password", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "nick_name", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public Integer getNickName() {
		return this.nickName;
	}
	
	public void setNickName(Integer nickName) {
		this.nickName = nickName;
	}
	
	@Column(name = "icon", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getIcon() {
		return this.icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Column(name = "sex", unique = false, nullable = false, insertable = true, updatable = true, length = 4)
	public String getSex() {
		return this.sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name = "ref_id", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getRefId() {
		return this.refId;
	}
	
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	@Column(name = "ref_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getRefType() {
		return this.refType;
	}
	
	public void setRefType(String refType) {
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
			.append("UserName",getUserName())
			.append("Password",getPassword())
			.append("NickName",getNickName())
			.append("Icon",getIcon())
			.append("Sex",getSex())
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
		if(obj instanceof ShopDeliverAccount == false) return false;
		if(this == obj) return true;
		ShopDeliverAccount other = (ShopDeliverAccount)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

