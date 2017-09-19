/*
 * @author John
 * @date - 2017-08-18
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_stock_out")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbStockOut implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbStockOut";
	public static final String ALIAS_ID = "出库ID";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_STOCK_OUT_PEOPLE_ID = "出库人";
	public static final String ALIAS_STOCK_OUT_TIME = "出库时间";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_WAREHOUSE_ID = "仓库";
	public static final String ALIAS_LOGIN_ID = "操作人";
	public static final String ALIAS_STOCK_OUT_TYPE="出库类型";
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_STOCK_OUT_TIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	//@Length(max=36)
	private String stockOutPeopleId;
	//@NotNull 
	private Date stockOutTime;
	//@Length(max=512)
	private String remark;
	//@Length(max=36)
	private String loginId;
	//
	private Integer warehouseId;
	//@Length(max=4)
	private String stockOutType;
	//columns END


		public TmbStockOut(){
		}
		public TmbStockOut(Integer id) {
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

	@Column(name = "login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
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
	
	@Column(name = "stock_out_people_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getStockOutPeopleId() {
		return this.stockOutPeopleId;
	}
	
	public void setStockOutPeopleId(String stockOutPeopleId) {
		this.stockOutPeopleId = stockOutPeopleId;
	}
	

	@Column(name = "stock_out_time", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public Date getStockOutTime() {
		return this.stockOutTime;
	}
	
	public void setStockOutTime(Date stockOutTime) {
		this.stockOutTime = stockOutTime;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "warehouse_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	@Column(name = "stock_out_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getStockOutType() {
		return stockOutType;
	}

	public void setStockOutType(String stockOutType) {
		this.stockOutType = stockOutType;
	}
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("StockOutPeopleId",getStockOutPeopleId())
			.append("StockOutTime",getStockOutTime())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbStockOut == false) return false;
		if(this == obj) return true;
		MbStockOut other = (MbStockOut)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

