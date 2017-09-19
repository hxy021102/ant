/*
 * @author John
 * @date - 2017-08-03
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_coupons")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbCoupons implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbCoupons";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除,0删除";
	public static final String ALIAS_NAME = "券票名称";
	public static final String ALIAS_CODE = "券票编码";
	public static final String ALIAS_TYPE = "券票类型";
	public static final String ALIAS_DISCOUNT = "券票优惠";
	public static final String ALIAS_QUANTITY_TOTAL = "券票总量";
	public static final String ALIAS_QUANTITY_USED = "券票使用量";
	public static final String ALIAS_STATUS = "券票状态";
	public static final String ALIAS_MONEY_THRESHOLD = "消费阈值";
	public static final String ALIAS_TIME_OPEN = "开启时间";
	public static final String ALIAS_TIME_CLOSE = "关闭时间";
	public static final String ALIAS_DESCRIPTION = "券票描述";
	public static final String ALIAS_PRICE = "券票购买价";
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_TIME_OPEN = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_TIME_CLOSE = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	//@NotBlank @Length(max=32)
	private String code;
	//@NotBlank @Length(max=5)
	private String type;
	//@NotNull 
	private Integer discount;
	//@NotNull 
	private Integer quantityTotal;
	//@NotNull 
	private Integer quantityUsed;
	//@NotBlank @Length(max=5)
	private String status;
	//@NotNull 
	private Integer moneyThreshold;
	//
	private Date timeOpen;
	//
	private Date timeClose;
	//@Length(max=125)
	private String description;
	//columns END
	private Integer price;

		public TmbCoupons(){
		}
		public TmbCoupons(Integer id) {
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
	
	@Column(name = "code", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "type", unique = false, nullable = false, insertable = true, updatable = true, length = 5)
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "discount", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getDiscount() {
		return this.discount;
	}
	
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	
	@Column(name = "quantity_total", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getQuantityTotal() {
		return this.quantityTotal;
	}
	
	public void setQuantityTotal(Integer quantityTotal) {
		this.quantityTotal = quantityTotal;
	}
	
	@Column(name = "quantity_used", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getQuantityUsed() {
		return this.quantityUsed;
	}
	
	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}
	
	@Column(name = "status", unique = false, nullable = false, insertable = true, updatable = true, length = 5)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "money_threshold", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getMoneyThreshold() {
		return this.moneyThreshold;
	}
	
	public void setMoneyThreshold(Integer moneyThreshold) {
		this.moneyThreshold = moneyThreshold;
	}
	

	@Column(name = "time_open", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getTimeOpen() {
		return this.timeOpen;
	}
	
	public void setTimeOpen(Date timeOpen) {
		this.timeOpen = timeOpen;
	}
	

	@Column(name = "time_close", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getTimeClose() {
		return this.timeClose;
	}
	
	public void setTimeClose(Date timeClose) {
		this.timeClose = timeClose;
	}
	
	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 125)
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "price", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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
			.append("Code",getCode())
			.append("Type",getType())
			.append("Discount",getDiscount())
			.append("QuantityTotal",getQuantityTotal())
			.append("QuantityUsed",getQuantityUsed())
			.append("Status",getStatus())
			.append("MoneyThreshold",getMoneyThreshold())
			.append("TimeOpen",getTimeOpen())
			.append("TimeClose",getTimeClose())
			.append("Describition",getDescription())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbCoupons == false) return false;
		if(this == obj) return true;
		MbCoupons other = (MbCoupons)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

