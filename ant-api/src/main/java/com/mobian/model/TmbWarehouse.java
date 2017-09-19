/*
 * @author John
 * @date - 2017-04-18
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_warehouse")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbWarehouse implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbWarehouse";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_CODE = "仓库代码";
	public static final String ALIAS_NAME = "仓库名称";
	public static final String ALIAS_REGION_ID = "仓库所在行政区划";
	public static final String ALIAS_ADDRESS = "具体地址";
	public static final String ALIAS_WAREHOUSE_TYPE = "仓库类型";
	
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
	private String code;
	//@NotBlank @Length(max=128)
	private String name;
	//
	private Integer regionId;
	//@Length(max=512)
	private String address;
	//@NotBlank @Length(max=4)
	private String warehouseType;
	private BigDecimal longitude;
	private BigDecimal latitude;
	//columns END


		public TmbWarehouse(){
		}
		public TmbWarehouse(Integer id) {
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

	@Column(name = "code", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
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

	@Column(name = "region_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	@Column(name = "address", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "warehouse_type", unique = false, nullable = false, insertable = true, updatable = true, length = 4)
	public String getWarehouseType() {
		return this.warehouseType;
	}

	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}
	@Column(name = "longitude", unique = false, nullable = true, insertable = true, updatable = true,scale = 6)
	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	@Column(name = "latitude", unique = false, nullable = true, insertable = true, updatable = true,scale = 6)
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
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
			.append("RegionId",getRegionId())
			.append("Address",getAddress())
			.append("WarehouseType",getWarehouseType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbWarehouse == false) return false;
		if(this == obj) return true;
		MbWarehouse other = (MbWarehouse)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

