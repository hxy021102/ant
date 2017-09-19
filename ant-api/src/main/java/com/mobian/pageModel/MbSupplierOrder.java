package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class MbSupplierOrder implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;
	private Date updatetime;
	private Boolean isdeleted;
	private Integer supplierId;
	private Integer totalPrice;
	private String status;
	private String supplierPeopleId;
	private Date planStockInDate;
	private String reviewerId;
	private Date reviewDate;
	private String reviewComment;
	private Integer warehouseId;
	private Integer supplierContractId;
	private String remark;
	//新增字段
	private  String supplierName;
	private String supplierPeopleName;
	private String reviewerName;
	private String warehouseName;
	private String code;
	private List<MbSupplierOrderItem> mbSupplierOrderItemList;

	private Date addtimeStart;
	private Date addtimeEnd;

	public List<MbSupplierOrderItem> getMbSupplierOrderItemList() {
		return mbSupplierOrderItemList;
	}

	public void setMbSupplierOrderItemList(List<MbSupplierOrderItem> mbSupplierOrderItemList) {
		this.mbSupplierOrderItemList = mbSupplierOrderItemList;
	}
	public Integer getSupplierContractId() {
		return supplierContractId;
	}

	public void setSupplierContractId(Integer supplierContractId) {
		this.supplierContractId = supplierContractId;
	}
	public String getStatusName(){return  ConvertNameUtil.getString(status);}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierPeopleName() {
		return supplierPeopleName;
	}

	public void setSupplierPeopleName(String supplierPeopleName) {
		this.supplierPeopleName = supplierPeopleName;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public void setId(Integer value) {
		this.id = value;
	}

	public Integer getId() {
		return this.id;
	}


	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getTenantId() {
		return this.tenantId;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getAddtime() {
		return this.addtime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}
	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Boolean getIsdeleted() {
		return this.isdeleted;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getSupplierId() {
		return this.supplierId;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getTotalPrice() {
		return this.totalPrice;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
	public void setSupplierPeopleId(String supplierPeopleId) {
		this.supplierPeopleId = supplierPeopleId;
	}

	public String getSupplierPeopleId() {
		return this.supplierPeopleId;
	}
	public void setPlanStockInDate(Date planStockInDate) {
		this.planStockInDate = planStockInDate;
	}

	public Date getPlanStockInDate() {
		return this.planStockInDate;
	}
	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}

	public String getReviewerId() {
		return this.reviewerId;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Date getReviewDate() {
		return this.reviewDate;
	}
	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public String getReviewComment() {
		return this.reviewComment;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getWarehouseId() {
		return this.warehouseId;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public Date getAddtimeEnd() {
		return addtimeEnd;
	}

	public void setAddtimeEnd(Date addtimeEnd) {
		this.addtimeEnd = addtimeEnd;
	}

	public Date getAddtimeStart() {
		return addtimeStart;
	}

	public void setAddtimeStart(Date addtimeStart) {
		this.addtimeStart = addtimeStart;
	}
}
