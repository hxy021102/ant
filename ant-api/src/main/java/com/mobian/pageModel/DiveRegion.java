package com.mobian.pageModel;


public class DiveRegion implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer regionLevel;
	private String regionNameZh;
	private String regionNameEn;
	private String regionParentId;
	private String regionId;

	

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}

	
	public void setRegionLevel(Integer regionLevel) {
		this.regionLevel = regionLevel;
	}
	
	public Integer getRegionLevel() {
		return this.regionLevel;
	}
	public void setRegionNameZh(String regionNameZh) {
		this.regionNameZh = regionNameZh;
	}
	
	public String getRegionNameZh() {
		return this.regionNameZh;
	}
	public void setRegionNameEn(String regionNameEn) {
		this.regionNameEn = regionNameEn;
	}
	
	public String getRegionNameEn() {
		return this.regionNameEn;
	}
	public void setRegionParentId(String regionParentId) {
		this.regionParentId = regionParentId;
	}
	
	public String getRegionParentId() {
		return this.regionParentId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	public String getRegionId() {
		return this.regionId;
	}

}
