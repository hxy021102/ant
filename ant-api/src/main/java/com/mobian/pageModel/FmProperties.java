package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class FmProperties implements java.io.Serializable {

    private static final long serialVersionUID = 5454155825314635342L;

    private String id;
    private Date addtime;
    private Date updatetime;
    private Boolean isdeleted;
    private String name;
    private String goodName;
    private String description;
    private String fieldType;
    private Boolean require;
    private String defaultValue;

    private String groupId;

    private Integer seq;


    private List<FmOptions> fmOptionsList;


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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodName() {
        return this.goodName;
    }

    public String getGoodNameName() {
        return ConvertNameUtil.getString(this.goodName);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public String getFieldTypeName() {
        return ConvertNameUtil.getString(this.fieldType);
    }

    public void setRequire(Boolean require) {
        this.require = require;
    }

    public Boolean getRequire() {
        return this.require;
    }

    public String getRequireName() {

        return ConvertNameUtil.getString(this.require == null || this.require == false ? "0" : "1");
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public List<FmOptions> getFmOptionsList() {
        return fmOptionsList;
    }

    public void setFmOptionsList(List<FmOptions> fmOptionsList) {
        this.fmOptionsList = fmOptionsList;
    }
}
