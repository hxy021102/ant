/*
 * @author John
 * @date - 2017-08-10
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_activity_action")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbActivityAction implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbActivityAction";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_ACTIVITY_RULE_ID = "活动规则ID";
	public static final String ALIAS_NAME = "action名称";
	public static final String ALIAS_SEQ = "排序";
	public static final String ALIAS_ACTION_TYPE = "行动类型";
	public static final String ALIAS_PARAMETER1 = "参数1";
	public static final String ALIAS_PARAMETER2 = "参数2";
	
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
	private Integer activityRuleId;
	//@Length(max=64)
	private String name;
	//@NotNull 
	private Integer seq;
	//@NotBlank @Length(max=8)
	private String actionType;
	//@NotBlank @Length(max=32)
	private String parameter1;
	//@NotBlank @Length(max=32)
	private String parameter2;
	//columns END


		public TmbActivityAction(){
		}
		public TmbActivityAction(Integer id) {
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
	
	@Column(name = "activity_rule_id", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getActivityRuleId() {
		return this.activityRuleId;
	}
	
	public void setActivityRuleId(Integer activityRuleId) {
		this.activityRuleId = activityRuleId;
	}
	
	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "seq", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getSeq() {
		return this.seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@Column(name = "action_type", unique = false, nullable = false, insertable = true, updatable = true, length = 8)
	public String getActionType() {
		return this.actionType;
	}
	
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	@Column(name = "parameter1", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getParameter1() {
		return this.parameter1;
	}
	
	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}
	
	@Column(name = "parameter2", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getParameter2() {
		return this.parameter2;
	}
	
	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("ActivityRuleId",getActivityRuleId())
			.append("Name",getName())
			.append("Seq",getSeq())
			.append("ActionType",getActionType())
			.append("Parameter1",getParameter1())
			.append("Parameter2",getParameter2())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbActivityAction == false) return false;
		if(this == obj) return true;
		MbActivityAction other = (MbActivityAction)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

