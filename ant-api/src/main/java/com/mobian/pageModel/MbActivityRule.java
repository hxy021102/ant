package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbActivityRule implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer activityId;
	private String name;
	private Integer seq;
	private String leftValue;
	private String operator;
	private String rightValue;
	private String remark;




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
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
	public Integer getActivityId() {
		return this.activityId;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public Integer getSeq() {
		return this.seq;
	}
	public void setLeftValue(String leftValue) {
		this.leftValue = leftValue;
	}
	
	public String getLeftValue() {
		return this.leftValue;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getOperator() {
		return this.operator;
	}
	public void setRightValue(String rightValue) {
		this.rightValue = rightValue;
	}
	
	public String getRightValue() {
		return this.rightValue;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}
}
