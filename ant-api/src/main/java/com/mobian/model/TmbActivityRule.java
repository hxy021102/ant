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
@Table(name = "mb_activity_rule")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbActivityRule implements java.io.Serializable,IEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbActivityRule";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_ACTIVITY_ID = "活动ID";
	public static final String ALIAS_NAME = "规则名称";
	public static final String ALIAS_SEQ = "排序";
	public static final String ALIAS_LEFT_VALUE = "左值";
	public static final String ALIAS_OPERATOR = "运输符";
	public static final String ALIAS_RIGHT_VALUE = "右值";
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
	private Date addtime;
	//@NotNull 
	private Date updatetime;
	//@NotNull 
	private Boolean isdeleted;
	//@NotNull 
	private Integer activityId;
	//@Length(max=64)
	private String name;
	//@NotNull 
	private Integer seq;
	//@NotBlank @Length(max=32)
	private String leftValue;
	//@NotBlank @Length(max=4)
	private String operator;
	//@NotBlank @Length(max=32)
	private String rightValue;
	//@Length(max=512)
	private String remark;
	//columns END


		public TmbActivityRule(){
		}
		public TmbActivityRule(Integer id) {
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
	
	@Column(name = "activity_id", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getActivityId() {
		return this.activityId;
	}
	
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
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
	
	@Column(name = "left_value", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getLeftValue() {
		return this.leftValue;
	}
	
	public void setLeftValue(String leftValue) {
		this.leftValue = leftValue;
	}
	
	@Column(name = "operator", unique = false, nullable = false, insertable = true, updatable = true, length = 4)
	public String getOperator() {
		return this.operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(name = "right_value", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getRightValue() {
		return this.rightValue;
	}
	
	public void setRightValue(String rightValue) {
		this.rightValue = rightValue;
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
			.append("ActivityId",getActivityId())
			.append("Name",getName())
			.append("Seq",getSeq())
			.append("LeftValue",getLeftValue())
			.append("Operator",getOperator())
			.append("RightValue",getRightValue())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbActivityRule == false) return false;
		if(this == obj) return true;
		MbActivityRule other = (MbActivityRule)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

