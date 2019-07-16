package com.house.property.model.task.entity;


import java.io.Serializable;

public class FileCoordinateInfoVO implements Serializable {

    //@ApiModelProperty("文件坐标信息ID")
    private String id;
    //@ApiModelProperty("文件id")
    private String dimFileinfoId;
    //@ApiModelProperty("高德纬度")
    private String gaodeLatitude;
    //@ApiModelProperty("高德经度")
    private String gaodeLongitude;
    //@ApiModelProperty("GPS纬度")
    private String wgs84Latitude;
    //@ApiModelProperty("GPS经度")
    private String wgs84Longitude;
    //@ApiModelProperty("上传用户名")
    private String userName;
    //@ApiModelProperty("入库时间")
    private String createDate;
    //@ApiModelProperty("修改时间")
    private String modifyDate;
    //@ApiModelProperty("删除标记")
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDimFileinfoId() {
        return dimFileinfoId;
    }

    public void setDimFileinfoId(String dimFileinfoId) {
        this.dimFileinfoId = dimFileinfoId;
    }

    public String getGaodeLatitude() {
        return gaodeLatitude;
    }

    public void setGaodeLatitude(String gaodeLatitude) {
        this.gaodeLatitude = gaodeLatitude;
    }

    public String getGaodeLongitude() {
        return gaodeLongitude;
    }

    public void setGaodeLongitude(String gaodeLongitude) {
        this.gaodeLongitude = gaodeLongitude;
    }

    public String getWgs84Latitude() {
        return wgs84Latitude;
    }

    public void setWgs84Latitude(String wgs84Latitude) {
        this.wgs84Latitude = wgs84Latitude;
    }

    public String getWgs84Longitude() {
        return wgs84Longitude;
    }

    public void setWgs84Longitude(String wgs84Longitude) {
        this.wgs84Longitude = wgs84Longitude;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}