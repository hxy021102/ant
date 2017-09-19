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
@Table(name = "mb_supplier_order")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbSupplierOrder implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "MbSupplierOrder";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SUPPLIER_ID = "供应商ID";
	public static  final String ALIAS_SUPPLIER_NAME = "供应商名称";   //新加供应商名称
	public static final String ALIAS_TOTAL_PRICE = "总金额";
	public static final String ALIAS_STATUS = "订单状态";
	public static final String ALIAS_SUPPLIER_PEOPLE_ID = "采购人ID";
	public static final String ALIAS_SUPPLIER_PEOPLE_NAME = "采购人名称";  //新加增采购人名称
	public static final String ALIAS_PLAN_STOCK_IN_DATE = "计划入库时间";
	public static final String ALIAS_REVIEWER_ID = "审核人ID";
	public static final String ALIAS_REVIEWER_NAME= "审核人名称";   //新加审核人名称
	public static final String ALIAS_REVIEW_DATE = "审核时间";
	public static final String ALIAS_REVIEW_COMMENT = "审核意见";
	public static final String ALIAS_WAREHOUSE_ID = "仓库ID";
	public static final String ALIAS_WAREHOUSE_NAME = "仓库名称";    //新加仓库名称
	public static final String ALIAS_REMARK = "备注";

	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_PLAN_STOCK_IN_DATE = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_REVIEW_DATE = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;


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
	private Integer supplierId;
	//
	private Integer totalPrice;
	//@Length(max=4)
	private String status;
	//@Length(max=36)
	private String supplierPeopleId;
	//
	private Date planStockInDate;
	//@Length(max=36)
	private String reviewerId;
	//
	private Date reviewDate;
	//@Length(max=512)
	private String reviewComment;
	//
	private Integer warehouseId;
	//@Length(max=512)
	private String remark;
	//columns END
	private Integer supplierContractId;




		public TmbSupplierOrder(){
		}
		public TmbSupplierOrder(Integer id) {
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
	@Column(name = "supplier_contract_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSupplierContractId() {
		return supplierContractId;
	}
	public void setSupplierContractId(Integer supplierContractId) {
		this.supplierContractId= supplierContractId;
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

	@Column(name = "supplier_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "total_price", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "supplier_people_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getSupplierPeopleId() {
		return this.supplierPeopleId;
	}

	public void setSupplierPeopleId(String supplierPeopleId) {
		this.supplierPeopleId = supplierPeopleId;
	}


	@Column(name = "plan_stock_in_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getPlanStockInDate() {
		return this.planStockInDate;
	}

	public void setPlanStockInDate(Date planStockInDate) {
		this.planStockInDate = planStockInDate;
	}

	@Column(name = "reviewer_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getReviewerId() {
		return this.reviewerId;
	}

	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}


	@Column(name = "review_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getReviewDate() {
		return this.reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	@Column(name = "review_comment", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getReviewComment() {
		return this.reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	@Column(name = "warehouse_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("SupplierId",getSupplierId())
			.append("TotalPrice",getTotalPrice())
			.append("Status",getStatus())
			.append("SupplierPeopleId",getSupplierPeopleId())
			.append("PlanStockInDate",getPlanStockInDate())
			.append("ReviewerId",getReviewerId())
			.append("ReviewDate",getReviewDate())
			.append("ReviewComment",getReviewComment())
			.append("WarehouseId",getWarehouseId())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbSupplierOrder == false) return false;
		if(this == obj) return true;
		MbSupplierOrder other = (MbSupplierOrder)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

