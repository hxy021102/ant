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
@Table(name = "mb_shop_coupons")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbShopCoupons implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbShopCoupons";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除,0删除";
	public static final String ALIAS_COUPONS_ID = "券ID";
	public static final String ALIAS_SHOP_ID = "门店ID";
	public static final String ALIAS_QUANTITY_TOTAL = "门店券初始数量";
	public static final String ALIAS_QUANTITY_USED = "门店消耗本券数量";
	public static final String ALIAS_STATUS = "门店券的状态";
	public static final String ALIAS_TIME_START = "券开始时间";
	public static final String ALIAS_TIME_END = "券过期时间";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_COUPONS_NAME="券名称";
	public static final String ALIAS_LOGIN_ID = "操作人ID";
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_TIME_START = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_TIME_END = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	private Integer couponsId;
	//@NotNull 
	private Integer shopId;
	//@NotNull 
	private Integer quantityTotal;
	//@NotNull 
	private Integer quantityUsed;
	//@NotBlank @Length(max=5)
	private String status;
	//
	private Date timeStart;
	//
	private Date timeEnd;
	//@Length(max=253)
	private String remark;
	//columns END
	private String loginId;

	private String payType;


		public TmbShopCoupons(){
		}
		public TmbShopCoupons(Integer id) {
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
	
	@Column(name = "coupons_id", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getCouponsId() {
		return this.couponsId;
	}
	
	public void setCouponsId(Integer couponsId) {
		this.couponsId = couponsId;
	}
	
	@Column(name = "shop_id", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getShopId() {
		return this.shopId;
	}
	
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	@Column(name = "quantity_total", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
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
	

	@Column(name = "time_start", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getTimeStart() {
		return this.timeStart;
	}
	
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
	

	@Column(name = "time_end", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getTimeEnd() {
		return this.timeEnd;
	}
	
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 253)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}


	@Column(name = "pay_type", unique = false, nullable = false, insertable = true, updatable = true, length = 5)
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("CouponsId",getCouponsId())
			.append("ShopId",getShopId())
			.append("QuantityTotal",getQuantityTotal())
			.append("QuantityUsed",getQuantityUsed())
			.append("Status",getStatus())
			.append("TimeStart",getTimeStart())
			.append("TimeEnd",getTimeEnd())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbShopCoupons == false) return false;
		if(this == obj) return true;
		MbShopCoupons other = (MbShopCoupons)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

