/*
 * @author John
 * @date - 2017-05-09
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_user_address")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbUserAddress implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "MbUserAddress";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_REGION_ID = "行政区划ID（保留字段）";
	public static final String ALIAS_PROVINCE_NAME = "国标收货地址第一级地址（省）";
	public static final String ALIAS_CITY_NAME = "国标收货地址第二级地址（市）";
	public static final String ALIAS_COUNTY_NAME = "国标收货地址第三级地址（县/区）";
	public static final String ALIAS_DETAIL_INFO = "详细地址";
	public static final String ALIAS_POSTAL_CODE = "邮政编码";
	public static final String ALIAS_USER_NAME = "收货人";
	public static final String ALIAS_TEL_NUMBER = "联系电话";
	public static final String ALIAS_DEFAULT_ADDRESS = "是否为默认收货地址";
	public static final String ALIAS_ADDRESS = "地址";

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
	private Integer userId;
	//
	private Integer regionId;
	//@Length(max=128)
	private String provinceName;
	//@Length(max=128)
	private String cityName;
	//@Length(max=128)
	private String countyName;
	//@Length(max=512)
	private String detailInfo;
	//@Length(max=36)
	private String postalCode;
	//@Length(max=50)
	private String userName;
	//@Length(max=20)
	private String telNumber;
	//
	private Boolean defaultAddress;
	//columns END


	public TmbUserAddress(){
	}
	public TmbUserAddress(Integer id) {
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

	@Column(name = "user_id", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "region_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	@Column(name = "provinceName", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@Column(name = "cityName", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "countyName", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getCountyName() {
		return this.countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	@Column(name = "detailInfo", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getDetailInfo() {
		return this.detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	@Column(name = "postalCode", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "userName", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "telNumber", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getTelNumber() {
		return this.telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	@Column(name = "default_address", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getDefaultAddress() {
		return this.defaultAddress;
	}

	public void setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("UserId",getUserId())
			.append("RegionId",getRegionId())
			.append("ProvinceName",getProvinceName())
			.append("CityName",getCityName())
			.append("CountyName",getCountyName())
			.append("DetailInfo",getDetailInfo())
			.append("PostalCode",getPostalCode())
			.append("UserName",getUserName())
			.append("TelNumber",getTelNumber())
			.append("DefaultAddress",getDefaultAddress())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbUserAddress == false) return false;
		if(this == obj) return true;
		MbUserAddress other = (MbUserAddress)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

