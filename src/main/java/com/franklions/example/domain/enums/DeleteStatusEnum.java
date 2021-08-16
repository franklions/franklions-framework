package com.franklions.example.domain.enums;

/**
 * @author flsh
 * @version 1.0
 * @date 2021/8/16
 * @since Jdk 1.8
 */
public enum DeleteStatusEnum {
    NORMAL(0,"Normal"),
    DELETED(1,"Deleted");

    private Integer typeCode;
    private String typeName;

    DeleteStatusEnum(Integer typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
