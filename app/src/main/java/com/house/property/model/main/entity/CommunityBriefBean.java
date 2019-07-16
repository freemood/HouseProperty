package com.house.property.model.main.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommunityBriefBean implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("commName")
    private String commName;

    @SerializedName("commAddress")
    private String commAddress;

    @SerializedName("gisId")
    private String gisId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getCommAddress() {
        return commAddress;
    }

    public void setCommAddress(String commAddress) {
        this.commAddress = commAddress;
    }

    public String getGisId() {
        return gisId;
    }

    public void setGisId(String gisId) {
        this.gisId = gisId;
    }
}