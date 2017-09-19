/*
 * @author John
 * @date - 2017-08-15
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_problem_track")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbProblemTrack implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbProblemTrack";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_TITLE = "问题标题";
	public static final String ALIAS_DETAILS = "详情";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_OWNER_ID = "处理人";
	public static final String ALIAS_REF_TYPE = "问题类型";
	public static final String ALIAS_ORDER_ID = "订单ID";
	public static final String ALIAS_LAST_OWNER_ID = "上级处理人";
	
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
	//@Length(max=128)
	private String title;
	//@Length(max=512)
	private String details;
	//@Length(max=4)
	private String status;
	//@Length(max=36)
	private String ownerId;
	//@Length(max=4)
	private String refType;
	//
	private Integer orderId;
	//
	private String lastOwnerId;






		public TmbProblemTrack(){
		}
		public TmbProblemTrack(Integer id) {
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
	
	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "details", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getDetails() {
		return this.details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "owner_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getOwnerId() {
		return this.ownerId;
	}
	
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	@Column(name = "ref_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getRefType() {
		return this.refType;
	}
	
	public void setRefType(String refType) {
		this.refType = refType;
	}
	
	@Column(name = "order_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "last_owner_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getLastOwnerId() {
		return this.lastOwnerId;
	}
	
	public void setLastOwnerId(String  lastOwnerId) {
		this.lastOwnerId = lastOwnerId;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("Title",getTitle())
			.append("Details",getDetails())
			.append("Status",getStatus())
			.append("OwnerId",getOwnerId())
			.append("RefType",getRefType())
			.append("OrderId",getOrderId())
			.append("LastOwnerId",getLastOwnerId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbProblemTrack == false) return false;
		if(this == obj) return true;
		MbProblemTrack other = (MbProblemTrack)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

