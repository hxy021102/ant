/*
 * @author John
 * @date - 2017-04-18
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_item_stock_log")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbItemStockLog implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbItemStockLog";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_ITEM_STOCK_ID = "库存大盘ID";
	public static final String ALIAS_QUANTITY = "数量";
	public static final String ALIAS_LOGIN_ID = "操作人ID";
	public static final String ALIAS_LOGIN_NAME = "操作人";
	public static final String ALIAS_LOG_TYPE = "库存日志类型";
	public static final String ALIAS_REASON = "原因";
	public static final String ALIAS_REMARK = "备注";
	
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
	private java.util.Date addtime;
	//@NotNull 
	private java.util.Date updatetime;
	//@NotNull 
	private Boolean isdeleted;
	//@NotNull 
	private Integer itemStockId;
	//
	private Integer quantity;
	//
	private String loginId;
	//@Length(max=4)
	private String logType;
	//@Length(max=512)
	private String reason;
	//@Length(max=512)
	private String remark;
	//columns END

	private Integer costPrice;

	private Integer endQuantity;

	private Integer inPrice;


		public TmbItemStockLog(){
		}
		public TmbItemStockLog(Integer id) {
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
	public java.util.Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}
	

	@Column(name = "updatetime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@Column(name = "isdeleted", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Boolean getIsdeleted() {
		return this.isdeleted;
	}
	
	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	@Column(name = "item_stock_id", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getItemStockId() {
		return this.itemStockId;
	}
	
	public void setItemStockId(Integer itemStockId) {
		this.itemStockId = itemStockId;
	}
	
	@Column(name = "quantity", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Column(name = "login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getLoginId() {
		return this.loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	@Column(name = "log_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getLogType() {
		return this.logType;
	}
	
	public void setLogType(String logType) {
		this.logType = logType;
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
	@Column(name = "cost_price", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Integer costPrice) {
		this.costPrice = costPrice;
	}

	@Column(name = "end_quantity", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getEndQuantity() {
		return endQuantity;
	}

	public void setEndQuantity(Integer endQuantity) {
		this.endQuantity = endQuantity;
	}
	@Column(name = "in_price", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getInPrice() {
		return inPrice;
	}

	public void setInPrice(Integer inPrice) {
		this.inPrice = inPrice;
	}

	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("ItemStockId",getItemStockId())
			.append("Quantity",getQuantity())
			.append("LoginId",getLoginId())
			.append("LogType",getLogType())
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
		if(obj instanceof MbItemStockLog == false) return false;
		if(this == obj) return true;
		MbItemStockLog other = (MbItemStockLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

