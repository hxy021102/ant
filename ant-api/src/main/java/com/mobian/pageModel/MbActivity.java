package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbActivity implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String name;
	private Date expiryDateStart;			
	private Date expiryDateEnd;			
	private Boolean valid;
	private String  validName;
	private String remark;
	private String type;
	private String refId;
	private String languageType;
	private String typeName;
	private String languageTypeName;

	public String getValidName() {
		return validName;
	}

	public void setValidName(String validName) {
		this.validName = validName;
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
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setExpiryDateStart(Date expiryDateStart) {
		this.expiryDateStart = expiryDateStart;
	}
	
	public Date getExpiryDateStart() {
		return this.expiryDateStart;
	}
	public void setExpiryDateEnd(Date expiryDateEnd) {
		this.expiryDateEnd = expiryDateEnd;
	}
	
	public Date getExpiryDateEnd() {
		return this.expiryDateEnd;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	public Boolean getValid() {
		return this.valid;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLanguageType() {
		return this.languageType;
	}

	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}

	public String getTypeName() {
		return ConvertNameUtil.getString(this.type);
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLanguageTypeName() {
		return ConvertNameUtil.getString(this.languageType);
	}

	public void setLanguageTypeName(String languageTypeName) {
		this.languageTypeName = languageTypeName;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
}
