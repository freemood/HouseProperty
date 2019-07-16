package com.house.property.model.task.entity;

import java.io.Serializable;

public class GisObjVO implements Serializable {

    // //@ApiModelProperty("小区id")
    private String id;
    // //@ApiModelProperty("小区名称")
    private String name;
    // //@ApiModelProperty("小区图块类型")
    private String type;
    // //@ApiModelProperty("小区区域坐标点")
    private String data;
    //@ApiModelProperty("小区区域中心坐标点x")
    private String centerX;
    //@ApiModelProperty("小区区域中心坐标点y")
    private String centerY;
     //@ApiModelProperty("图层id")
    private String levelId;

     //@ApiModelProperty("图层name")
    private String levelName;

     //@ApiModelProperty("图层类型")
    private String levelType;

     //@ApiModelProperty("图标name")
    private String labelTxt;

     //@ApiModelProperty("图标 x 位置")
    private String labelX;

     //@ApiModelProperty("图标 Y 位置")
    private String labelY;

     //@ApiModelProperty("状态")
    private String state;
   // @ApiModelProperty("楼栋Name")
    private String buildName;
    private String taskId;
   // @ApiModelProperty("楼栋No")
    private String buildNo;
    //@ApiModelProperty("楼栋ID")
    private String buildId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCenterX() {
        return centerX;
    }

    public void setCenterX(String centerX) {
        this.centerX = centerX;
    }

    public String getCenterY() {
        return centerY;
    }

    public void setCenterY(String centerY) {
        this.centerY = centerY;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType;
    }

    public String getLabelTxt() {
        return labelTxt;
    }

    public void setLabelTxt(String labelTxt) {
        this.labelTxt = labelTxt;
    }

    public String getLabelX() {
        return labelX;
    }

    public void setLabelX(String labelX) {
        this.labelX = labelX;
    }

    public String getLabelY() {
        return labelY;
    }

    public void setLabelY(String labelY) {
        this.labelY = labelY;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}