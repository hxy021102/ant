/*
 * @author John
 * @date - 2017-04-18
 */

package com.mobian.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_order_invoice")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbOrderInvoice implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbOrderInvoice";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "开票时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_ORDER_ID = "订单ID";
	public static final String ALIAS_INVOICE_STATUS = "发票状态";
	public static final String ALIAS_COMPANY_NAME = "公司抬头";
	public static final String ALIAS_COMPANY_TFN = "公司税号";
	public static final String ALIAS_REGISTER_ADDRESS = "注册地址";
	public static final String ALIAS_REGISTER_PHONE = "注册电话";
	public static final String ALIAS_BANK_NAME = "银行名称";
	public static final String ALIAS_BANK_NUMBER = "银行卡号";
	public static final String ALIAS_INVOICE_USE = "发票用途";
	public static final String ALIAS_LOGIN_ID = "开票人";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_SHOPNAME = "客户名称";

	
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
	private Integer orderId;
	//@Length(max=4)
	private String invoiceStatus;
	//@Length(max=128)
	private String companyName;
	//@Length(max=128)
	private String companyTfn;
	//@Length(max=512)
	private String registerAddress;
	//@Length(max=32)
	private String registerPhone;
	//@Length(max=128)
	private String bankName;
	//@Length(max=64)
	private String bankNumber;
	//@Length(max=4)
	private String invoiceUse;
	//
	private String loginId;
	//@Length(max=512)
	private String remark;
	private Integer shopId;
	//columns END


		public TmbOrderInvoice(){
		}
		public TmbOrderInvoice(Integer id) {
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
	
	@Column(name = "order_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "invoice_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}
	
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	
	@Column(name = "company_name", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getCompanyName() {
		return this.companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Column(name = "company_tfn", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getCompanyTfn() {
		return this.companyTfn;
	}
	
	public void setCompanyTfn(String companyTfn) {
		this.companyTfn = companyTfn;
	}
	
	@Column(name = "register_address", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getRegisterAddress() {
		return this.registerAddress;
	}
	
	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}
	
	@Column(name = "register_phone", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getRegisterPhone() {
		return this.registerPhone;
	}
	
	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}
	
	@Column(name = "bank_name", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Column(name = "bank_number", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getBankNumber() {
		return this.bankNumber;
	}
	
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	
	@Column(name = "invoice_use", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getInvoiceUse() {
		return this.invoiceUse;
	}
	
	public void setInvoiceUse(String invoiceUse) {
		this.invoiceUse = invoiceUse;
	}
	
	@Column(name = "login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getLoginId() {
		return this.loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "shop_id", unique = false, nullable = true, insertable = true, updatable = true, length = 11)

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("OrderId",getOrderId())
			.append("InvoiceStatus",getInvoiceStatus())
			.append("CompanyName",getCompanyName())
			.append("CompanyTfn",getCompanyTfn())
			.append("RegisterAddress",getRegisterAddress())
			.append("RegisterPhone",getRegisterPhone())
			.append("BankName",getBankName())
			.append("BankNumber",getBankNumber())
			.append("InvoiceUse",getInvoiceUse())
			.append("LoginId",getLoginId())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbOrderInvoice == false) return false;
		if(this == obj) return true;
		MbOrderInvoice other = (MbOrderInvoice)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

