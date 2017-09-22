/*
 * @author John
 * @date - 2017-04-18
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_item")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbItem implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MbItem";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TENANT_ID = "租户ID";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_CODE = "商品代码";
	public static final String ALIAS_NAME = "商品名称";
	public static final String ALIAS_CATEGORY_ID = "商品分类";
	public static final String ALIAS_QUANTITY_UNIT = "SKU";
	public static final String ALIAS_MARKET_PRICE = "市场价格";
	public static final String ALIAS_SEQ = "C端列表排列顺序";
	public static final String ALIAS_URL = "icon商品小图标";
	public static final String ALIAS_DESCRIBTION = "商品描述";
	public static final String ALIAS_ITEM_PARAM = "商品参数json格式";
	public static final String ALIAS_IMAGE_LIST = "商品详情滚动图片";
	public static final String ALIAS_QUANTITY = "库存";
	public static final String ALIAS_ISPACK = "包装品";
	public static final String ALIAS_PACK_ID = "回收包装";
	public static final String ALIAS_WEIGHT="商品重量";
	public static final String ALIAS_ISSHELVES="上架";
	public static final String ALIAS_PURCHASE_PRICE="采购价格";

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
	private java.util.Date addtime;
	//@NotNull
	private java.util.Date updatetime;
	//@NotNull 
	private Boolean isdeleted;
	private Boolean isShelves;

	//@NotBlank @Length(max=32)
	private String code;
	//@NotBlank @Length(max=128)
	private String name;
	//
	private Integer categoryId;
	//@Length(max=100)
	private String quantityUnitName;
	//
	private Integer marketPrice;
	private Integer purchasePrice;
	private Integer weight;
	//@Length(max=100)
	private String url;

	private Integer seq;
	//columns END
	private String describtion; //商品描述
	private String itemParam; //商品参数json格式
	private String imageList; //商品详情滚动图片
	private String introduceList; //商品参数介绍
	private Integer quantity; //商品库存数量

	private Boolean ispack;
	private Integer packId;

		public TmbItem(){
		}
		public TmbItem(Integer id) {
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
	
	@Column(name = "code", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 128)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "category_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getCategoryId() {
		return this.categoryId;
	}
	
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	@Column(name = "quantity_unit_name", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getQuantityUnitName() {
		return this.quantityUnitName;
	}
	
	public void setQuantityUnitName(String quantityUnitName) {
		this.quantityUnitName = quantityUnitName;
	}
	
	@Column(name = "market_price", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getMarketPrice() {
		return this.marketPrice;
	}
	
	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}
	@Column(name = "url", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "seq", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "describtion", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	@Column(name = "item_param", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getItemParam() {
		return itemParam;
	}

	public void setItemParam(String itemParam) {
		this.itemParam = itemParam;
	}

	@Column(name = "image_list", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getImageList() {
		return imageList;
	}

	public void setImageList(String imageList) {
		this.imageList = imageList;
	}

	@Column(name = "introduce_list", unique = false, nullable = true, insertable = true, updatable = true, length = 2000)
	public String getIntroduceList() {
		return introduceList;
	}

	public void setIntroduceList(String introduceList) {
		this.introduceList = introduceList;
	}

	@Column(name = "quantity", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	@Column(name = "ispack", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Boolean getIspack() {
		return ispack;
	}

	public void setIspack(Boolean ispack) {
		this.ispack = ispack;
	}
	@Column(name = "pack_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getPackId() {
		return packId;
	}

	public void setPackId(Integer packId) {
		this.packId = packId;
	}
	@Column(name = "weight", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getWeight() { return weight;}
	public void setWeight(Integer weight) { this.weight = weight;}

	@Column(name = "isshelves", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Boolean getIsShelves() { return isShelves; }
	public void setIsShelves(Boolean isShelves) { this.isShelves = isShelves;}

	@Column(name = "purchase_price", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getPurchasePrice() { return purchasePrice; }
	public void setPurchasePrice(Integer purchasePrice) { this.purchasePrice = purchasePrice;}
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TenantId",getTenantId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("Code",getCode())
			.append("Name",getName())
			.append("CategoryId",getCategoryId())
			.append("QuantityUnit",getQuantityUnit())
			.append("MarketPrice",getMarketPrice())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MbItem == false) return false;
		if(this == obj) return true;
		MbItem other = (MbItem)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

