package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbActivityAction implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer activityRuleId;
	private String name;
	private Integer seq;
	private String actionType;
	private String parameter1;
	private String parameter2;

	

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
	public void setActivityRuleId(Integer activityRuleId) {
		this.activityRuleId = activityRuleId;
	}
	
	public Integer getActivityRuleId() {
		return this.activityRuleId;
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
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public String getActionType() {
		return this.actionType;
	}
	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}
	
	public String getParameter1() {
		return this.parameter1;
	}
	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}
	
	public String getParameter2() {
		return this.parameter2;
	}

}
