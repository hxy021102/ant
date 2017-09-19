
/*
 * @author John
 * @date - 2015-08-06
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "dive_region")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TdiveRegion implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "DiveRegion";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_REGION_LEVEL = "区域级别";
	public static final String ALIAS_REGION_NAME_ZH = "区域中文名";
	public static final String ALIAS_REGION_NAME_EN = "区域英文名";
	public static final String ALIAS_REGION_PARENT_ID = "区域父编码";
	public static final String ALIAS_REGION_ID = "区域编码";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private Integer id;
	//
	private Integer regionLevel;
	//@Length(max=128)
	private String regionNameZh;
	//@Length(max=128)
	private String regionNameEn;
	//@Length(max=10)
	private String regionParentId;
	//@Length(max=10)
	private String regionId;
	//columns END


		public TdiveRegion(){
		}
		public TdiveRegion(Integer id) {
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

	@Column(name = "region_level", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getRegionLevel() {
		return this.regionLevel;
	}

	public void setRegionLevel(Integer regionLevel) {
		this.regionLevel = regionLevel;
	}

	@Column(name = "region_name_zh", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getRegionNameZh() {
		return this.regionNameZh;
	}

	public void setRegionNameZh(String regionNameZh) {
		this.regionNameZh = regionNameZh;
	}

	@Column(name = "region_name_en", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getRegionNameEn() {
		return this.regionNameEn;
	}

	public void setRegionNameEn(String regionNameEn) {
		this.regionNameEn = regionNameEn;
	}

	@Column(name = "region_parent_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getRegionParentId() {
		return this.regionParentId;
	}

	public void setRegionParentId(String regionParentId) {
		this.regionParentId = regionParentId;
	}

	@Column(name = "region_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getRegionId() {
		return this.regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("RegionLevel",getRegionLevel())
			.append("RegionNameZh",getRegionNameZh())
			.append("RegionNameEn",getRegionNameEn())
			.append("RegionParentId",getRegionParentId())
			.append("RegionId",getRegionId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof DiveRegion == false) return false;
		if(this == obj) return true;
		DiveRegion other = (DiveRegion)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

