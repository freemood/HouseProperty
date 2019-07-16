package com.house.property.model.task.entity;

import java.io.Serializable;

public class PoiTypeBean implements Serializable {
    String typeCode;
    String typeName;
    String typeLevelOne;
    String typeLevelTwo;
    String typeLevelThree;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeLevelOne() {
        return typeLevelOne;
    }

    public void setTypeLevelOne(String typeLevelOne) {
        this.typeLevelOne = typeLevelOne;
    }

    public String getTypeLevelTwo() {
        return typeLevelTwo;
    }

    public void setTypeLevelTwo(String typeLevelTwo) {
        this.typeLevelTwo = typeLevelTwo;
    }

    public String getTypeLevelThree() {
        return typeLevelThree;
    }

    public void setTypeLevelThree(String typeLevelThree) {
        this.typeLevelThree = typeLevelThree;
    }
}
