/*
 * @author John
 * @date - 2017-11-09
 */

package com.bx.ant.model;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "driver_freight_rule")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TdriverFreightRule implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "DriverFreightRule";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_WEIGHT_LOWER = "重量下限包含";
	public static final String ALIAS_WEIGHT_UPPER = "重量上限不包含";
	public static final String ALIAS_DISTANCE_LOWER = "距离下限包含";
	public static final String ALIAS_DISTANCE_UPPER = "距离上限不包含";
	public static final String ALIAS_REGION = "区域";
	public static final String ALIAS_FREIGHT = "运费";
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private java.lang.Integer id;
	//
	private java.lang.Integer tenantId;
	//@NotNull 
	private java.util.Date addtime;
	//@NotNull 
	private java.util.Date updatetime;
	//@NotNull 
	private java.lang.Boolean isdeleted;
	//
	private java.lang.Integer weightLower;
	//
	private java.lang.Integer weightUpper;
	//
	private java.lang.Integer distanceLower;
	//
	private java.lang.Integer distanceUpper;
	//
	private java.lang.Integer regionId;
	//
	private java.lang.Integer freight;

	private java.lang.String loginId;
	//columns END


		public TdriverFreightRule(){
		}
		public TdriverFreightRule(Integer id) {
			this.id = id;
		}
	

	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public java.lang.Integer getId() {
		return this.id;
	}
	
	@Column(name = "tenant_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getTenantId() {
		return this.tenantId;
	}
	
	public void setTenantId(java.lang.Integer tenantId) {
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
	public java.lang.Boolean getIsdeleted() {
		return this.isdeleted;
	}
	
	public void setIsdeleted(java.lang.Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	@Column(name = "weight_lower", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getWeightLower() {
		return this.weightLower;
	}
	
	public void setWeightLower(java.lang.Integer weightLower) {
		this.weightLower = weightLower;
	}
	
	@Column(name = "weight_upper", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getWeightUpper() {
		return this.weightUpper;
	}
	
	public void setWeightUpper(java.lang.Integer weightUpper) {
		this.weightUpper = weightUpper;
	}
	
	@Column(name = "distance_lower", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDistanceLower() {
		return this.distanceLower;
	}
	
	public void setDistanceLower(java.lang.Integer distanceLower) {
		this.distanceLower = distanceLower;
	}
	
	@Column(name = "distance_upper", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDistanceUpper() {
		return this.distanceUpper;
	}
	
	public void setDistanceUpper(java.lang.Integer distanceUpper) {
		this.distanceUpper = distanceUpper;
	}
	
	@Column(name = "region_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getRegionId() {
		return this.regionId;
	}
	
	public void setRegionId(java.lang.Integer regionId) {
		this.regionId = regionId;
	}
	
	@Column(name = "freight", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getFreight() {
		return this.freight;
	}
	
	public void setFreight(java.lang.Integer freight) {
		this.freight = freight;
	}

	@Column(name = "login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("WeightLower",getWeightLower())
			.append("WeightUpper",getWeightUpper())
			.append("DistanceLower",getDistanceLower())
			.append("DistanceUpper",getDistanceUpper())
			.append("Region",getRegion())
			.append("Freight",getFreight())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof DriverFreightRule == false) return false;
		if(this == obj) return true;
		DriverFreightRule other = (DriverFreightRule)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

