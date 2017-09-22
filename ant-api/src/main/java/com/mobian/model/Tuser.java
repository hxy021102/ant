package com.mobian.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "tuser")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tuser implements java.io.Serializable {

	public static final String ALIAS_UTYPE = "用户类型";
	public static final String ALIAS_THIRD_USER = "第三方账号";
	public static final String ALIAS_HEAD_IMAGE = "头像";
	public static final String ALIAS_NICKNAME = "昵称";
	public static final String ALIAS_AREA_CODE = "地区";
	public static final String ALIAS_BIRTHDAY = "生日";
	public static final String ALIAS_BARDIAN = "个性签名";
	public static final String ALIAS_MEMBER_V = "会议级别";
	
	
	private String id;
	private Date createdatetime;
	private Date modifydatetime;
	private String name;
	private String pwd;
	private Set<Trole> troles = new HashSet<Trole>(0);
	
	private String utype;
	//@Length(max=36)
	private String thirdUser;
	//@Length(max=256)
	private String headImage;
	//@Length(max=36)
	private String nickname;
	//@Length(max=36)
	private String areaCode;
	//@Length(max=18)
	private String birthday;
	//@Length(max=128)
	private String bardian;
	//@Length(max=4)
	private String memberV;

	@Column(name = "is_star")
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isStar;
	@Column(name = "is_tarento")
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isTarento;

	private String email;
	private String orgName;
	private String phone;
	
	public Tuser() {
	}

	public Tuser(String id, String name, String pwd) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
	}

	public Tuser(String id, Date createdatetime, Date modifydatetime, String name, String pwd, Set<Trole> troles) {
		this.id = id;
		this.createdatetime = createdatetime;
		this.modifydatetime = modifydatetime;
		this.name = name;
		this.pwd = pwd;
		this.troles = troles;
	}

	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME", length = 19)
	public Date getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME", length = 19)
	public Date getModifydatetime() {
		return this.modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PWD", nullable = false, length = 100)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "utype", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getUtype() {
		return this.utype;
	}
	
	public void setUtype(String utype) {
		this.utype = utype;
	}
	
	@Column(name = "third_user", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getThirdUser() {
		return this.thirdUser;
	}
	
	public void setThirdUser(String thirdUser) {
		this.thirdUser = thirdUser;
	}
	
	@Column(name = "head_image", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getHeadImage() {
		return this.headImage;
	}
	
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	@Column(name = "nickname", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Column(name = "areaCode", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getAreaCode() {
		return this.areaCode;
	}
	
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	@Column(name = "birthday", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public String getBirthday() {
		return this.birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	@Column(name = "bardian", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getBardian() {
		return this.bardian;
	}
	
	public void setBardian(String bardian) {
		this.bardian = bardian;
	}
	
	@Column(name = "member_v", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getMemberV() {
		return this.memberV;
	}
	
	public void setMemberV(String memberV) {
		this.memberV = memberV;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tuser_trole", joinColumns = { @JoinColumn(name = "TUSER_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) })
	public Set<Trole> getTroles() {
		return this.troles;
	}

	public void setTroles(Set<Trole> troles) {
		this.troles = troles;
	}

	public Boolean getIsStar() {
		return isStar;
	}

	public void setIsStar(Boolean isStar) {
		this.isStar = isStar;
	}

	public Boolean getIsTarento() {
		return isTarento;
	}

	public void setIsTarento(Boolean isTarento) {
		this.isTarento = isTarento;
	}

	@Column(name = "email", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "org_name", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(name = "phone", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
