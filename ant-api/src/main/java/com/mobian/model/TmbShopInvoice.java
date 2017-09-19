/*
 * @author John
 * @date - 2017-08-01
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_shop_invoice")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbShopInvoice implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbShopInvoice";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SHOP_ID = "门店ID";
	public static final String ALIAS_COMPANY_NAME = "公司抬头";
	public static final String ALIAS_COMPANY_TFN = "公司税号";
	public static final String ALIAS_REGISTER_ADDRESS = "注册地址";
	public static final String ALIAS_REGISTER_PHONE = "注册电话";
	public static final String ALIAS_BANK_NAME = "银行名称";
	public static final String ALIAS_BANK_NUMBER = "银行卡号";
	public static final String ALIAS_INVOICE_USE = "发票用途";
	public static final String ALIAS_INVOICE_TYPE = "发票类型";
	public static final String ALIAS_INVOICE_DEFAULT = "默认发票模板";
	
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
	private Integer shopId;
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
	//@Length(max=4)
	private String invoiceType;

	private Boolean invoiceDefault;
	//columns END


		public TmbShopInvoice(){
		}
		public TmbShopInvoice(Integer id) {
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
	
	@Column(name = "shop_id", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getShopId() {
		return this.shopId;
	}
	
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
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
	
	@Column(name = "invoice_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Column(name = "invoice_default", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public Boolean getInvoiceDefault() {
		return invoiceDefault;
	}

	public void setInvoiceDefault(Boolean invoiceDefault) {
		this.invoiceDefault = invoiceDefault;
	}
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("ShopId",getShopId())
			.append("CompanyName",getCompanyName())
			.append("CompanyTfn",getCompanyTfn())
			.append("RegisterAddress",getRegisterAddress())
			.append("RegisterPhone",getRegisterPhone())
			.append("BankName",getBankName())
			.append("BankNumber",getBankNumber())
			.append("InvoiceUse",getInvoiceUse())
			.append("InvoiceType",getInvoiceType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbShopInvoice == false) return false;
		if(this == obj) return true;
		MbShopInvoice other = (MbShopInvoice)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

