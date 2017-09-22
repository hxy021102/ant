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
@Table(name = "mb_problem_track_item")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbProblemTrackItem implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbProblemTrackItem";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_CONTENT = "处理内容";
	public static final String ALIAS_FILE = "附件";
	public static final String ALIAS_OWNER_ID = "处理人";
	public static final String ALIAS_LAST_OWNER_ID = "上次处理人";
	
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
	//@Length(max=512)
	private String content;
	//@Length(max=512)
	private String file;
	//@Length(max=36)
	private String ownerId;
	//
	private String  lastOwnerId;

	//columns END
	private Integer problemOrderId;

		public TmbProblemTrackItem(){
		}
		public TmbProblemTrackItem(Integer id) {
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

	@Column(name = "problem_order_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getProblemOrderId() {
		return problemOrderId;
	}

	public void setProblemOrderId(Integer problemOrderId) {
		this.problemOrderId = problemOrderId;
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
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "file", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getFile() {
		return this.file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	@Column(name = "owner_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getOwnerId() {
		return this.ownerId;
	}
	
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	@Column(name = "last_owner_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String  getLastOwnerId() {
		return this.lastOwnerId;
	}
	
	public void setLastOwnerId(String lastOwnerId) {
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
			.append("Content",getContent())
			.append("File",getFile())
			.append("OwnerId",getOwnerId())
			.append("LastOwnerId",getLastOwnerId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbProblemTrackItem == false) return false;
		if(this == obj) return true;
		MbProblemTrackItem other = (MbProblemTrackItem)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

