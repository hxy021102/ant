/*
 * @author John
 * @date - 2017-07-15
 */

package com.mobian.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_supplier")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbSupplier implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbSupplier";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_NAME = "供应商名称";
	public static final String ALIAS_REGION_ID = "行政区划";
	public static final String ALIAS_REGION_NAME = "行政区划";
	public static final String ALIAS_ADDRESS = "具体地址";
	public static final String ALIAS_CONTACT_PHONE = "联系电话";
	public static final String ALIAS_CONTACT_PEOPLE = "联系人";
	public static final String ALIAS_CERTIFICATE_LIST = "证照资质";
	public static final String ALIAS_WAREHOUSE_ID = "仓库";
	
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
	//@NotBlank @Length(max=128)
	private String name;
	//
	private Integer regionId;
	//@Length(max=512)
	private String address;
	//@Length(max=32)
	private String contactPhone;
	//@Length(max=32)
	private String contactPeople;
	//@Length(max=512)
	private String certificateList;
	//
	private Integer warehouseId;
	//columns END


		public TmbSupplier(){
		}
		public TmbSupplier(Integer id) {
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
	
	@Column(name = "contact_phone", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getContactPhone() {
		return this.contactPhone;
	}
	
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@Column(name = "contact_people", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getContactPeople() {
		return this.contactPeople;
	}
	
	public void setContactPeople(String contactPeople) {
		this.contactPeople = contactPeople;
	}
	
	@Column(name = "certificate_list", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getCertificateList() {
		return this.certificateList;
	}
	
	public void setCertificateList(String certificateList) {
		this.certificateList = certificateList;
	}
	
	@Column(name = "warehouse_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getWarehouseId() {
		return this.warehouseId;
	}
	
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
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
			.append("RegionId",getRegionId())
			.append("Address",getAddress())
			.append("ContactPhone",getContactPhone())
			.append("ContactPeople",getContactPeople())
			.append("CertificateList",getCertificateList())
			.append("WarehouseId",getWarehouseId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbSupplier == false) return false;
		if(this == obj) return true;
		MbSupplier other = (MbSupplier)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

