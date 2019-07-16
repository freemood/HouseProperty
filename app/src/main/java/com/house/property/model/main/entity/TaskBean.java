package com.house.property.model.main.entity;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polygon;
import com.google.gson.annotations.SerializedName;
import com.house.property.model.task.entity.GisObjVO;

import java.io.Serializable;
import java.util.List;

public class TaskBean implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("state")
    private String state;
    @SerializedName("dispObjType")
    private String dispObjType;

    @SerializedName("dispObjId")
    private String dispObjId;

    @SerializedName("orderType")
    private String orderType;

    @SerializedName("actType")
    private String actType;

    @SerializedName("busiObjType")
    private String busiObjType;

    @SerializedName("busiObjId")
    private String busiObjId;

    @SerializedName("title")
    private String title;

    @SerializedName("remark")
    private String remark;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("community")
    CommunityBriefBean community;

    @SerializedName("gisObj")
    GisObjVO gisObj;
    //@ApiModelProperty("楼栋的坐标")
    List<GisObjVO> gisObjBuildList;
    private LatLng[] latLngs;

    //@ApiModelProperty("审核人类型")
    private String shenheDisptype;

    //@ApiModelProperty("审核人id")
    private String shenheDispid;

   //@ApiModelProperty("当前人类型")
    private String currDisptype;

   // @ApiModelProperty("当前人id")
    private String currDispid;
//    private Polygon polygon;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDispObjType() {
        return dispObjType;
    }

    public void setDispObjType(String dispObjType) {
        this.dispObjType = dispObjType;
    }

    public String getDispObjId() {
        return dispObjId;
    }

    public void setDispObjId(String dispObjId) {
        this.dispObjId = dispObjId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public String getBusiObjType() {
        return busiObjType;
    }

    public void setBusiObjType(String busiObjType) {
        this.busiObjType = busiObjType;
    }

    public String getBusiObjId() {
        return busiObjId;
    }

    public void setBusiObjId(String busiObjId) {
        this.busiObjId = busiObjId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public CommunityBriefBean getCommunity() {
        return community;
    }

    public void setCommunity(CommunityBriefBean community) {
        this.community = community;
    }

    public GisObjVO getGisObj() {
        return gisObj;
    }

    public void setGisObj(GisObjVO gisObj) {
        this.gisObj = gisObj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public LatLng[] getLatLngs() {
        return latLngs;
    }

    public void setLatLngs(LatLng[] latLngs) {
        this.latLngs = latLngs;
    }
//
//    public Polygon getPolygon() {
//        return polygon;
//    }
//
//    public void setPolygon(Polygon polygon) {
//        this.polygon = polygon;
//    }


    public List<GisObjVO> getGisObjBuildList() {
        return gisObjBuildList;
    }

    public void setGisObjBuildList(List<GisObjVO> gisObjBuildList) {
        this.gisObjBuildList = gisObjBuildList;
    }

    public String getShenheDisptype() {
        return shenheDisptype;
    }

    public void setShenheDisptype(String shenheDisptype) {
        this.shenheDisptype = shenheDisptype;
    }

    public String getShenheDispid() {
        return shenheDispid;
    }

    public void setShenheDispid(String shenheDispid) {
        this.shenheDispid = shenheDispid;
    }

    public String getCurrDisptype() {
        return currDisptype;
    }

    public void setCurrDisptype(String currDisptype) {
        this.currDisptype = currDisptype;
    }

    public String getCurrDispid() {
        return currDispid;
    }

    public void setCurrDispid(String currDispid) {
        this.currDispid = currDispid;
    }
}
