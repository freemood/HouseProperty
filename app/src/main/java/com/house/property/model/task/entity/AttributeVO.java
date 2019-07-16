package com.house.property.model.task.entity;


import java.io.Serializable;

public class AttributeVO implements Serializable {

    private String id;

    private String attrSrckey;

    private String attrSrctab;

    private String attrSrccol;

    private String attrName;

    private String attrCode;
    private String attrValue;
    private String attrState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttrSrckey() {
        return attrSrckey;
    }

    public void setAttrSrckey(String attrSrckey) {
        this.attrSrckey = attrSrckey;
    }

    public String getAttrSrctab() {
        return attrSrctab;
    }

    public void setAttrSrctab(String attrSrctab) {
        this.attrSrctab = attrSrctab;
    }

    public String getAttrSrccol() {
        return attrSrccol;
    }

    public void setAttrSrccol(String attrSrccol) {
        this.attrSrccol = attrSrccol;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getAttrState() {
        return attrState;
    }

    public void setAttrState(String attrState) {
        this.attrState = attrState;
    }
}