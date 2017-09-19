/*
 * @author John
 * @date - 2017-07-15
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_supplier_stock_in")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbSupplierStockIn implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbSupplierStockIn";
	public static final String ALIAS_ID = "入库ID";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SUPPLIER_ORDER_ID = "采购订单ID";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_SIGN_PEOPLE_ID = "签收人";
	public static final String ALIAS_SIGN_DATE = "签收日期";
	public static final String ALIAS_RECEIVE_URL = "签收单附件";
	public static final String ALIAS_LOGIN_ID = "操作人签收的人";
	public static final String ALIAS_PAY_STATUS = "付款状态";
	public static final String ALIAS_INVOICE_STATUS = "开票状态";
	public static final String ALIAS_WAREHOUSE_ID = "仓库";
	public static final String ALIAS_WAREHOUSE_NAME = "仓库名";
	public static final String ALIAS_DRIVER_LOGIN_ID = "司机";
	public static final String ALIAS_SUPPLIER_NAME="供应商名称";
	public static final String ALIAS_PAYMENT_DAYS= "账期";
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_SIGN_DATE = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	//
	private Integer supplierOrderId;
	//@Length(max=4)
	private String status;
	//@Length(max=36)
	private String signPeopleId;
	//
	private Date signDate;
	//@Length(max=512)
	private String receiveUrl;
	//@Length(max=36)
	private String loginId;
	//@Length(max=4)
	private String payStatus;
	//@Length(max=4)
	private String invoiceStatus;
	//
	private Integer warehouseId;

	private String remark;

	private String driverLoginId;
	//columns END


		public TmbSupplierStockIn(){
		}
		public TmbSupplierStockIn(Integer id) {
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
	
	@Column(name = "supplier_order_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSupplierOrderId() {
		return this.supplierOrderId;
	}
	
	public void setSupplierOrderId(Integer supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "sign_people_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getSignPeopleId() {
		return this.signPeopleId;
	}
	
	public void setSignPeopleId(String signPeopleId) {
		this.signPeopleId = signPeopleId;
	}
	

	@Column(name = "sign_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getSignDate() {
		return this.signDate;
	}
	
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	@Column(name = "receive_url", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getReceiveUrl() {
		return this.receiveUrl;
	}
	
	public void setReceiveUrl(String receiveUrl) {
		this.receiveUrl = receiveUrl;
	}
	
	@Column(name = "login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getLoginId() {
		return this.loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	@Column(name = "pay_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getPayStatus() {
		return this.payStatus;
	}
	
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	@Column(name = "invoice_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}
	
	public void setInvoiceStatus(String invoiceStatus) {
			this.invoiceStatus = invoiceStatus;
	}
	
	@Column(name = "warehouse_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getWarehouseId() {
		return this.warehouseId;
	}
	
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "driver_login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getDriverLoginId() {
		return driverLoginId;
	}

	public void setDriverLoginId(String driverLoginId) {
		this.driverLoginId = driverLoginId;
	}
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("SupplierOrderId",getSupplierOrderId())
			.append("Status",getStatus())
			.append("SignPeopleId",getSignPeopleId())
			.append("SignDate",getSignDate())
			.append("ReceiveUrl",getReceiveUrl())
			.append("LoginId",getLoginId())
			.append("PayStatus",getPayStatus())
			.append("InvoiceStatus",getInvoiceStatus())
			.append("WarehouseId",getWarehouseId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbSupplierStockIn == false) return false;
		if(this == obj) return true;
		MbSupplierStockIn other = (MbSupplierStockIn)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

