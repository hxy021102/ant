/*
 * @author John
 * @date - 2017-09-21
 */

package com.bx.ant.model;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "shop_deliver_apply")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TshopDeliverApply implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ShopDeliverApply";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SHOP_ID = "用户ID";
	public static final String ALIAS_DELIVERY_WAY = "配送方式";
	public static final String ALIAS_ONLINE = "上线 0下线 1上线";
	public static final String ALIAS_RESULT = "结果";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_ACCOUNT_ID = "账号ID";
	public static final String ALIAS_MAXDELIVERYDISTANCE = "最大配送距离";
	
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
	private Integer shopId;
	//@Length(max=10)
	private String deliveryWay;
	//
	private Boolean online;
	//@Length(max=512)
	private String result;
	//@NotBlank @Length(max=10)
	private String status;
	//
	private Integer accountId;
	//
	private BigDecimal maxDeliveryDistance;
	//columns END


		public TshopDeliverApply(){
		}
		public TshopDeliverApply(Integer id) {
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
	
	@Column(name = "shop_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getShopId() {
		return this.shopId;
	}
	
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	@Column(name = "delivery_way", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getDeliveryWay() {
		return this.deliveryWay;
	}
	
	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}
	
	@Column(name = "online", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getOnline() {
		return this.online;
	}
	
	public void setOnline(Boolean online) {
		this.online = online;
	}
	
	@Column(name = "result", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getResult() {
		return this.result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	@Column(name = "status", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "account_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getAccountId() {
		return this.accountId;
	}
	
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	@Column(name = "max_delivery_distance", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public BigDecimal getMaxDeliveryDistance() {
		return maxDeliveryDistance;
	}

	public void setMaxDeliveryDistance(BigDecimal maxDeliveryDistance) {
		this.maxDeliveryDistance = maxDeliveryDistance;
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
			.append("DeliveryWay",getDeliveryWay())
			.append("Online",getOnline())
			.append("Result",getResult())
			.append("Status",getStatus())
			.append("AccountId",getAccountId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ShopDeliverApply == false) return false;
		if(this == obj) return true;
		ShopDeliverApply other = (ShopDeliverApply)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

