package com.house.property.model.task.entity;


import java.io.Serializable;

public class CommunityManagementBean implements Serializable {

    private String id;
    private String managementName;
    private String managementAddr;
    private String managementLevel;
    private String feeStd;

    private String isFengbi;

    private String isMenjin;

    private String isEcarControl;

    private String isFaceControl;

    private String isVisitorInterview;

    private String envLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManagementName() {
        return managementName;
    }

    public void setManagementName(String managementName) {
        this.managementName = managementName;
    }

    public String getManagementAddr() {
        return managementAddr;
    }

    public void setManagementAddr(String managementAddr) {
        this.managementAddr = managementAddr;
    }

    public String getManagementLevel() {
        return managementLevel;
    }

    public void setManagementLevel(String managementLevel) {
        this.managementLevel = managementLevel;
    }

    public String getFeeStd() {
        return feeStd;
    }

    public void setFeeStd(String feeStd) {
        this.feeStd = feeStd;
    }

    public String getIsFengbi() {
        return isFengbi;
    }

    public void setIsFengbi(String isFengbi) {
        this.isFengbi = isFengbi;
    }

    public String getIsMenjin() {
        return isMenjin;
    }

    public void setIsMenjin(String isMenjin) {
        this.isMenjin = isMenjin;
    }

    public String getIsEcarControl() {
        return isEcarControl;
    }

    public void setIsEcarControl(String isEcarControl) {
        this.isEcarControl = isEcarControl;
    }

    public String getIsFaceControl() {
        return isFaceControl;
    }

    public void setIsFaceControl(String isFaceControl) {
        this.isFaceControl = isFaceControl;
    }

    public String getIsVisitorInterview() {
        return isVisitorInterview;
    }

    public void setIsVisitorInterview(String isVisitorInterview) {
        this.isVisitorInterview = isVisitorInterview;
    }

    public String getEnvLevel() {
        return envLevel;
    }

    public void setEnvLevel(String envLevel) {
        this.envLevel = envLevel;
    }
}