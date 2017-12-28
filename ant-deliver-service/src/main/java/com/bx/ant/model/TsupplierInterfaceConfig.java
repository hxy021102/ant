/*
 * @author John
 * @date - 2017-12-26
 */

package com.bx.ant.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "supplier_interface_config")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TsupplierInterfaceConfig implements java.io.Serializable, IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SupplierInterfaceConfig";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_INTERFACE_TYPE = "接口类型，数据字段枚举";
	public static final String ALIAS_CUSTOMER_ID = " 客户ID，全局唯一";
	public static final String ALIAS_APP_KEY = "appkey";
	public static final String ALIAS_APP_SECRET = "appsecret";
	public static final String ALIAS_SERVICE_URL = "url";
	public static final String ALIAS_VERSION = "版本";
	public static final String ALIAS_WAREHOUSE_CODE = "仓库代码";
	public static final String ALIAS_LOGISTICS_CODE = "物流公司代码";
	public static final String ALIAS_STATUS_MAP = "状态映射";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_ONLINE = " 是否上线，布尔类型，1代表上线，代表否";
	
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
	//@Length(max=10)
	private String interfaceType;
	//@Length(max=64)
	private String customerId;
	//@Length(max=64)
	private String appKey;
	//@Length(max=64)
	private String appSecret;
	//@Length(max=512)
	private String serviceUrl;
	//@Length(max=10)
	private String version;
	//@Length(max=64)
	private String warehouseCode;
	//@Length(max=64)
	private String logisticsCode;
	//@Length(max=512)
	private String statusMap;
	//@Length(max=512)
	private String remark;
	//
	private Boolean online;
	//columns END
	private Integer supplierId;

		public TsupplierInterfaceConfig(){
		}
		public TsupplierInterfaceConfig(Integer id) {
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
	
	@Column(name = "interface_type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getInterfaceType() {
		return this.interfaceType;
	}
	
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	@Column(name = "customer_id", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "app_key", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getAppKey() {
		return this.appKey;
	}
	
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	@Column(name = "app_secret", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getAppSecret() {
		return this.appSecret;
	}
	
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	@Column(name = "service_url", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getServiceUrl() {
		return this.serviceUrl;
	}
	
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	@Column(name = "version", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getVersion() {
		return this.version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(name = "warehouse_code", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getWarehouseCode() {
		return this.warehouseCode;
	}
	
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	
	@Column(name = "logistics_code", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getLogisticsCode() {
		return this.logisticsCode;
	}
	
	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}
	
	@Column(name = "status_map", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getStatusMap() {
		return this.statusMap;
	}
	
	public void setStatusMap(String statusMap) {
		this.statusMap = statusMap;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "online", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getOnline() {
		return this.online;
	}
	
	public void setOnline(Boolean online) {
		this.online = online;
	}
	@Column(name = "supplier_Id", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("InterfaceType",getInterfaceType())
			.append("CustomerId",getCustomerId())
			.append("AppKey",getAppKey())
			.append("AppSecret",getAppSecret())
			.append("ServiceUrl",getServiceUrl())
			.append("Version",getVersion())
			.append("WarehouseCode",getWarehouseCode())
			.append("LogisticsCode",getLogisticsCode())
			.append("StatusMap",getStatusMap())
			.append("Remark",getRemark())
			.append("Online",getOnline())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierInterfaceConfig == false) return false;
		if(this == obj) return true;
		SupplierInterfaceConfig other = (SupplierInterfaceConfig)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

