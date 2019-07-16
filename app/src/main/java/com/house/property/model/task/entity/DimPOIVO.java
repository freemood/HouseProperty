package com.house.property.model.task.entity;

import java.io.Serializable;
import java.util.Date;


public class DimPOIVO implements Serializable {
    //@ApiModelProperty("id")
    private String id;
    //@ApiModelProperty("父POIID")
    private String parent;

    //@ApiModelProperty("名称")
    private String name;

    //@ApiModelProperty("别名")
    private String alias;

    //@ApiModelProperty("兴趣点类型编码")
    private String typecode;

    //@ApiModelProperty("兴趣点类型")
    private String type;

    //@ApiModelProperty("地址")
    private String address;

    //@ApiModelProperty("坐标")
    private String location;

    //@ApiModelProperty("离中心点距离")
    private String distance;

    //@ApiModelProperty("poi所在省份编码")
    private String pcode;

    //@ApiModelProperty("poi所在省份名称")
    private String pname;

    //@ApiModelProperty("城市编码")
    private String citycode;

    //@ApiModelProperty("城市名")
    private String cityname;

    //@ApiModelProperty("区域编码")
    private String adcode;

    //@ApiModelProperty("区域名称")
    private String adname;

    //@ApiModelProperty("电话")
    private String tel;

    //@ApiModelProperty("邮编")
    private String postcode;

    //@ApiModelProperty("网址")
    private String website;

    //@ApiModelProperty("邮箱")
    private String email;

    //@ApiModelProperty("入口经纬度")
    private String entrLocation;

    //@ApiModelProperty("出口经纬度")
    private String exitLocation;

    //@ApiModelProperty("POI的特色内容")
    private String tag;

    //@ApiModelProperty("仅在停车场类型POI的时候显示该字段展示停车场类型，包括：地下、地面、路边")
    private String parkingType;

    //@ApiModelProperty("所在商圈")
    private String businessArea;

    //@ApiModelProperty("深度信息(rating  评分  仅存在于餐饮、酒店、景点、影院类POI之下 cost  人均消费  仅存在于餐饮、酒店、景点、影院类POI之下)")
    private String bizType;

    //@ApiModelProperty("入库时间")
    private Date createDate;

    //@ApiModelProperty("修改时间")
    private Date modifyDate;

    //@ApiModelProperty("删除标记")
    private String delState;

    //@ApiModelProperty("空间数据ID")
    private String gisId;

    //@ApiModelProperty("备注")
    private String remark;

    //@ApiModelProperty("app采集")
    private String dataSrc;
    private String commId;
    private String commName;

    public String getCommId() {
        return commId;
    }

    public void setCommId(String commId) {
        this.commId = commId;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEntrLocation() {
        return entrLocation;
    }

    public void setEntrLocation(String entrLocation) {
        this.entrLocation = entrLocation;
    }

    public String getExitLocation() {
        return exitLocation;
    }

    public void setExitLocation(String exitLocation) {
        this.exitLocation = exitLocation;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getDelState() {
        return delState;
    }

    public void setDelState(String delState) {
        this.delState = delState;
    }

    public String getGisId() {
        return gisId;
    }

    public void setGisId(String gisId) {
        this.gisId = gisId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDataSrc() {
        return dataSrc;
    }

    public void setDataSrc(String dataSrc) {
        this.dataSrc = dataSrc;
    }
}