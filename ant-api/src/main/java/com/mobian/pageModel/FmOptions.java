package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class FmOptions implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String propertiesId;
	private String value;
	private Integer seq;

	

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
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
	public void setPropertiesId(String propertiesId) {
		this.propertiesId = propertiesId;
	}
	
	public String getPropertiesId() {
		return this.propertiesId;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public Integer getSeq() {
		return this.seq;
	}

}
