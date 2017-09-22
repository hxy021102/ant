/*
 * @author John
 * @date - 2017-08-24
 */

package com.mobian.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_shop_coupons_log")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbShopCouponsLog implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbShopCouponsLog";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SHOP_COUPONS_ID = "门店券ID";
	public static final String ALIAS_QUANTITY_USED = "用量变更";
	public static final String ALIAS_QUANTITY_TOTAL = "总量变更";
	public static final String ALIAS_LOGIN_ID = "操作人ID";
	public static final String ALIAS_REF_ID = "业务ID";
	public static final String ALIAS_REF_TYPE = "业务类型";
	public static final String ALIAS_REASON = "原因";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_LOGIN_NAME = "操作人";
	public static final String ALIAS_SHOP_COUPONS_STATUS = "状态变更";

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
	private Integer shopCouponsId;
	//@NotNull 
	private Integer quantityUsed;
	//@NotNull
	private Integer quantityTotal;

	private String shopCouponsStatus;

	//@Length(max=64)
	private String loginId;
	//@Length(max=64)
	private String refId;
	//@Length(max=10)
	private String refType;
	//@Length(max=512)
	private String reason;
	//@Length(max=512)
	private String remark;
	//columns END


		public TmbShopCouponsLog(){
		}
		public TmbShopCouponsLog(Integer id) {
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
	
	@Column(name = "shop_coupons_id", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getShopCouponsId() {
		return this.shopCouponsId;
	}
	
	public void setShopCouponsId(Integer shopCouponsId) {
		this.shopCouponsId = shopCouponsId;
	}
	

	@Column(name = "login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getLoginId() {
		return this.loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	@Column(name = "ref_id", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getRefId() {
		return this.refId;
	}
	
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	@Column(name = "ref_type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getRefType() {
		return this.refType;
	}
	
	public void setRefType(String refType) {
		this.refType = refType;
	}
	
	@Column(name = "reason", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getReason() {
		return this.reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "quantity_used", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}

	@Column(name = "quantity_total", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getQuantityTotal() {
		return quantityTotal;
	}

	public void setQuantityTotal(Integer quantityTotal) {
		this.quantityTotal = quantityTotal;
	}

	@Column(name = "shop_coupons_status", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public String getShopCouponsStatus() {
		return shopCouponsStatus;
	}

	public void setShopCouponsStatus(String shopCouponsStatus) {
		this.shopCouponsStatus = shopCouponsStatus;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("ShopCouponsId",getShopCouponsId())
			.append("Quantity",getQuantity())
			.append("LoginId",getLoginId())
			.append("RefId",getRefId())
			.append("RefType",getRefType())
			.append("Reason",getReason())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbShopCouponsLog == false) return false;
		if(this == obj) return true;
		MbShopCouponsLog other = (MbShopCouponsLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

