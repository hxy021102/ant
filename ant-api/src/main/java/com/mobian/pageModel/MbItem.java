package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbItem implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Boolean isShelves;
	private String code;
	private String name;
	private Integer categoryId;
	private String quantityUnitName;
	private Integer marketPrice;
	private Integer purchasePrice;
	private Integer weight;
	private String  standard;   //规格
	private String barCode;     //条形码
	private String carton;      //箱规

	private String categoryName;
	private String url;
	private Integer seq;

	private String describtion; //商品描述
	private String itemParam; //商品参数json格式
	private String imageList; //商品详情滚动图片
	private Integer contractPrice; //商品合同价
	private Integer quantity; //商品库存数量
	private String introduceList;
	private Boolean ispack;
	private Integer packId;

	private String keyword;

	// 派单门店商品信息
	private Boolean online;
	private String status;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}


	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public String getQuantityUnitName() {
		return quantityUnitName;
	}

	public void setQuantityUnitName(String quantityUnitName) {
		this.quantityUnitName = quantityUnitName;
	}

	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	public Integer getMarketPrice() {
		return this.marketPrice;
	}

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public String getItemParam() {
		return itemParam;
	}

	public void setItemParam(String itemParam) {
		this.itemParam = itemParam;
	}

	public String getImageList() {
		return imageList;
	}

	public String[] getImageLists() {
		String[] rs = new String[3];
		if (imageList != null) {
			String[] igs = imageList.split("[,;]");
			if (igs != null) {
				for (int i = 0; i < igs.length && i < 3; i++) {
					rs[i] = igs[i];
				}
			}
		}
		return rs;
	}

	public void setImageList(String imageList) {
		this.imageList = imageList;
	}

	public Integer getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(Integer contractPrice) {
		this.contractPrice = contractPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getIntroduceList() {
		return introduceList;
	}

	public void setIntroduceList(String introduceList) {
		this.introduceList = introduceList;
	}

	public String[] getIntroduceLists() {
		String[] rs = new String[10];
		if (introduceList != null) {
			String[] igs = introduceList.split("[,;]");
			if (igs != null) {
				for (int i = 0; i < igs.length && i < 3; i++) {
					rs[i] = igs[i];
				}
			}
		}
		return rs;
	}

	public Boolean getIspack() {
		return ispack;
	}

	public void setIspack(Boolean ispack) {
		this.ispack = ispack;
	}

	public Integer getPackId() {
		return packId;
	}

	public void setPackId(Integer packId) {
		this.packId = packId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Boolean getIsShelves() { return isShelves; }

	public void setIsShelves(Boolean isShelves) { this.isShelves = isShelves; }

	public Integer getPurchasePrice() { return purchasePrice;}

	public void setPurchasePrice(Integer purchasePrice) { this.purchasePrice = purchasePrice; }

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getCarton() {
		return carton;
	}

	public void setCarton(String carton) {
		this.carton = carton;
	}
}
