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
@Table(name = "mb_supplier_finance_log")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbSupplierFinanceLog implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbSupplierFinanceLog";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SUPPLIER_STOCK_IN_ID = "采购订单ID";
	public static final String ALIAS_PAY_LOGIN_ID = "操作人_支付";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	public static final String ALIAS_INVOICE_STATUS = "开票状态";
	public static final String ALIAS_INVOICE_LOGIN_ID = "操作人_开票人ID";
	public static final String ALIAS_PAY_REMARK = "支付备注";
	public static final String ALIAS_INVOICE_REMARK = "开票备注";
	public static final String ALIAS_INVOICE_NO = "发票号";
	
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
	//
	private Integer supplierStockInId;
	//@Length(max=36)
	private String payLoginId;
	//@Length(max=4)
	private String payStatus;
	//@Length(max=4)
	private String invoiceStatus;
	//@Length(max=36)
	private String invoiceLoginId;
	//@Length(max=512)
	private String payRemark;
	//@Length(max=512)
	private String invoiceRemark;
	//columns END
	private String invoiceNo;


		public TmbSupplierFinanceLog(){
		}
		public TmbSupplierFinanceLog(Integer id) {
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
	
	@Column(name = "supplier_stock_in_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSupplierStockInId() {
		return this.supplierStockInId;
	}
	
	public void setSupplierStockInId(Integer supplierStockInId) {
		this.supplierStockInId = supplierStockInId;
	}
	
	@Column(name = "pay_login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getPayLoginId() {
		return this.payLoginId;
	}
	
	public void setPayLoginId(String payLoginId) {
		this.payLoginId = payLoginId;
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
	
	@Column(name = "invoice_login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getInvoiceLoginId() {
		return this.invoiceLoginId;
	}
	
	public void setInvoiceLoginId(String invoiceLoginId) {
		this.invoiceLoginId = invoiceLoginId;
	}
	
	@Column(name = "pay_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getPayRemark() {
		return this.payRemark;
	}
	
	public void setPayRemark(String payRemark) {
		this.payRemark = payRemark;
	}
	
	@Column(name = "invoice_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getInvoiceRemark() {
		return this.invoiceRemark;
	}
	
	public void setInvoiceRemark(String invoiceRemark) {
		this.invoiceRemark = invoiceRemark;
	}

	@Column(name = "invoice_no", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("SupplierStockInId",getSupplierStockInId())
			.append("PayLoginId",getPayLoginId())
			.append("PayStatus",getPayStatus())
			.append("InvoiceStatus",getInvoiceStatus())
			.append("InvoiceLoginId",getInvoiceLoginId())
			.append("PayRemark",getPayRemark())
			.append("InvoiceRemark",getInvoiceRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbSupplierFinanceLog == false) return false;
		if(this == obj) return true;
		MbSupplierFinanceLog other = (MbSupplierFinanceLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

