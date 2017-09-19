package com.mobian.enums;

/**
 * Created by john on 16/8/31.
 */
public enum FieldType {
    BOOLEAN("FT01","布尔类型"),
    OPTION("FT02","非布尔选项"),
    TEXT("FT03","文本");
    private String type;
    private String description;
    FieldType(String type,String description){
        this.type = type;
        this.description =description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
