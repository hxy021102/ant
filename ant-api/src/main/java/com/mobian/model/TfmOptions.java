
/*
 * @author John
 * @date - 2016-08-27
 */

package com.mobian.model;

import javax.persistence.*;

import com.mobian.util.Constants;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "fm_options")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TfmOptions implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "FmOptions";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ADDTIME = "创建时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_PROPERTIES_ID = "属性ID";
	public static final String ALIAS_VALUE = "值";
	public static final String ALIAS_SEQ = "排序";
	
	//date formats
	public static final String FORMAT_ADDTIME = Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@NotNull 
	private java.util.Date addtime;
	//@NotNull 
	private java.util.Date updatetime;
	//@NotNull 
	private Boolean isdeleted;
	//@Length(max=36)
	private String propertiesId;
	//@Length(max=100)
	private String value;
	//
	private Integer seq;
	//columns END


		public TfmOptions(){
		}
		public TfmOptions(String id) {
			this.id = id;
		}
	

	public void setId(String id) {
		this.id = id;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}
	

	@Column(name = "addtime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public java.util.Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}
	

	@Column(name = "updatetime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@Column(name = "isdeleted", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Boolean getIsdeleted() {
		return this.isdeleted;
	}
	
	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	@Column(name = "properties_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getPropertiesId() {
		return this.propertiesId;
	}
	
	public void setPropertiesId(String propertiesId) {
		this.propertiesId = propertiesId;
	}
	
	@Column(name = "value", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name = "seq", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSeq() {
		return this.seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("PropertiesId",getPropertiesId())
			.append("Value",getValue())
			.append("Seq",getSeq())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof FmOptions == false) return false;
		if(this == obj) return true;
		FmOptions other = (FmOptions)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

