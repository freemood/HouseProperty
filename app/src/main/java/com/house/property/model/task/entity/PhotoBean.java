package com.house.property.model.task.entity;


import java.io.Serializable;

public class PhotoBean implements Serializable {
    /**
     * wgs84纬度
     */
    private double wgs84Latitude;
    /**
     * wgs84经度
     */
    private double wgs84Longitude;
    /**
     * 高德纬度
     */
    private double gaoDeLatitude;
    /**
     * 高德经度
     */
    private double gaoDeLongitude;
    /**
     * 小区id
     */
    private String id;
    /**
     * 图片地址
     */
    private String path;
    /**
     * 照片类型
     */
    private String type;

    public double getWgs84Latitude() {
        return wgs84Latitude;
    }

    public void setWgs84Latitude(double wgs84Latitude) {
        this.wgs84Latitude = wgs84Latitude;
    }

    public double getWgs84Longitude() {
        return wgs84Longitude;
    }

    public void setWgs84Longitude(double wgs84Longitude) {
        this.wgs84Longitude = wgs84Longitude;
    }

    public double getGaoDeLatitude() {
        return gaoDeLatitude;
    }

    public void setGaoDeLatitude(double gaoDeLatitude) {
        this.gaoDeLatitude = gaoDeLatitude;
    }

    public double getGaoDeLongitude() {
        return gaoDeLongitude;
    }

    public void setGaoDeLongitude(double gaoDeLongitude) {
        this.gaoDeLongitude = gaoDeLongitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
