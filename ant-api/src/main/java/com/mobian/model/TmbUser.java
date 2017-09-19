/*
 * @author John
 * @date - 2017-04-18
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbUser implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbUser";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_USER_NAME = "账号";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_NICK_NAME = "昵称";
	public static final String ALIAS_PHONE = "联系电话";
	public static final String ALIAS_ICON = "头像";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_SHOP_ID = "shopId";
	public static final String ALIAS_SHOP_NAME = "客户名称";
	public static final String ALIAS_BALANCE = "余额";
	public static final String ALIAS_REF_ID = "第三方ID";
	public static final String ALIAS_REF_TYPE = "第三方类型";
	
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
	//@NotBlank @Length(max=32)
	private String userName;
	//@NotBlank @Length(max=32)
	private String password;
	//@NotBlank @Length(max=128)
	private String nickName;
	//@Length(max=32)
	private String phone;
	//@Length(max=256)
	private String icon;
	//@Length(max=4)
	private String sex;
	//@Length(max=11)
	private Integer shopId;
	//@Length(max=64)
	private String refId;
	//@Length(max=4)
	private String refType;
	//columns END


		public TmbUser(){
		}
		public TmbUser(Integer id) {
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

	@Column(name = "password", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "nick_name", unique = false, nullable = false, insertable = true, updatable = true, length = 128)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "phone", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "icon", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "sex", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "shop_id", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
	public Integer getShopId() {
		return this.shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
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
			.append("Phone",getPhone())
			.append("Icon",getIcon())
			.append("Sex",getSex())
			.append("ShopId",getShopId())
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
		if(obj instanceof MbUser == false) return false;
		if(this == obj) return true;
		MbUser other = (MbUser)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

