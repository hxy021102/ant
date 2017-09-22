package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbUser implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String userName;
	private String password;
	private String nickName;
	private String phone;
	private String icon;
	private String sex;
	private Integer shopId;
	private String shopName;
	private Integer balance;
	private String refId;
	private String refType;
	private String tokenId;
	private MbUserAddress mbUserAddress; //默认的地址
	private String mbContract; //最新的合同编号
	private String validateCode; //短信验证码
	private Integer mbBalance; //余额
	private Integer cashBalance; //桶账余额
	private Integer balanceId;
	private MbShop mbShop; //认证的门店信息

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getNickName() {
		return this.nickName;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getIcon() {
		return this.icon;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getSex() {
		return this.sex;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	public Integer getShopId() {
		return this.shopId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	public String getRefId() {
		return this.refId;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	
	public String getRefType() {
		return this.refType;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public MbUserAddress getMbUserAddress() {
		return mbUserAddress;
	}

	public void setMbUserAddress(MbUserAddress mbUserAddress) {
		this.mbUserAddress = mbUserAddress;
	}

	public String getMbContract() {
		return mbContract;
	}

	public void setMbContract(String mbContract) {
		this.mbContract = mbContract;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public Integer getMbBalance() {
		return mbBalance;
	}

	public void setMbBalance(Integer mbBalance) {
		this.mbBalance = mbBalance;
	}

	public Integer getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(Integer cashBalance) {
		this.cashBalance = cashBalance;
	}

	public MbShop getMbShop() {
		return mbShop;
	}

	public void setMbShop(MbShop mbShop) {
		this.mbShop = mbShop;
	}

	public Integer getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(Integer balanceId) {
		this.balanceId = balanceId;
	}
}
