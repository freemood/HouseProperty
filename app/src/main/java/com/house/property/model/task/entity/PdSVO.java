package com.house.property.model.task.entity;


import java.io.Serializable;

public class PdSVO implements Serializable {

    private String id;

    private String name;

    private String pdType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdType() {
        return pdType;
    }

    public void setPdType(String pdType) {
        this.pdType = pdType;
    }
}