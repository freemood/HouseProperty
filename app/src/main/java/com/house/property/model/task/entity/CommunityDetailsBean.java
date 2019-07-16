package com.house.property.model.task.entity;


import java.io.Serializable;
import java.util.List;

public class CommunityDetailsBean implements Serializable {

    private String id;

    private String commName;

    private String commAddress;
    private String shangquanname;

    private String jianzhuniandai;

    private String chanqquannianxian;

    private String tudixingzhi;

    private String zongmianji;

    private String rongjilv;

    private String lvhualv;

    private String buildCnt;

    private String houseCnt;

    private String tingcheweiCnt;

    private String delState;

    private String kaifashang;

    private String weiqiangFanghu;

    private String jingguan;

    private String jianzhuMianji;

    private String innerPeitaomiaoshu;

    private String inOutCarparkCnt;

    private String innerEdu;

    private String houseAttribute;

    private String mainHuxing;

    private String shopCnt;

    private String waterSupp;

    private String epowerSupp;

    private String gasSupp;

    private String heatSupp;

    private String icdDev;

    private String liftService;

    private String safeService;

    private String envService;

    private String buildStructure;
    private String buildType;
    private String churukouCnt;
    private String lumianQingkuang;
    private String pics;
    private String gisId;
    private String managementId;

    private CommunityManagementBean managementObj;

    private List<PdSVO> pdSList;

    private List<AttributeVO> attributeLits;
    //@ApiModelProperty("照片")
    private List<DimFileinfoVO> commPhotos;
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

    public String getShangquanname() {
        return shangquanname;
    }

    public void setShangquanname(String shangquanname) {
        this.shangquanname = shangquanname;
    }

    public String getJianzhuniandai() {
        return jianzhuniandai;
    }

    public void setJianzhuniandai(String jianzhuniandai) {
        this.jianzhuniandai = jianzhuniandai;
    }

    public String getChanqquannianxian() {
        return chanqquannianxian;
    }

    public void setChanqquannianxian(String chanqquannianxian) {
        this.chanqquannianxian = chanqquannianxian;
    }

    public String getTudixingzhi() {
        return tudixingzhi;
    }

    public void setTudixingzhi(String tudixingzhi) {
        this.tudixingzhi = tudixingzhi;
    }

    public String getZongmianji() {
        return zongmianji;
    }

    public void setZongmianji(String zongmianji) {
        this.zongmianji = zongmianji;
    }

    public String getRongjilv() {
        return rongjilv;
    }

    public void setRongjilv(String rongjilv) {
        this.rongjilv = rongjilv;
    }

    public String getLvhualv() {
        return lvhualv;
    }

    public void setLvhualv(String lvhualv) {
        this.lvhualv = lvhualv;
    }

    public String getBuildCnt() {
        return buildCnt;
    }

    public void setBuildCnt(String buildCnt) {
        this.buildCnt = buildCnt;
    }

    public String getHouseCnt() {
        return houseCnt;
    }

    public void setHouseCnt(String houseCnt) {
        this.houseCnt = houseCnt;
    }

    public String getTingcheweiCnt() {
        return tingcheweiCnt;
    }

    public void setTingcheweiCnt(String tingcheweiCnt) {
        this.tingcheweiCnt = tingcheweiCnt;
    }

    public String getDelState() {
        return delState;
    }

    public void setDelState(String delState) {
        this.delState = delState;
    }

    public String getKaifashang() {
        return kaifashang;
    }

    public void setKaifashang(String kaifashang) {
        this.kaifashang = kaifashang;
    }

    public String getWeiqiangFanghu() {
        return weiqiangFanghu;
    }

    public void setWeiqiangFanghu(String weiqiangFanghu) {
        this.weiqiangFanghu = weiqiangFanghu;
    }

    public String getJingguan() {
        return jingguan;
    }

    public void setJingguan(String jingguan) {
        this.jingguan = jingguan;
    }

    public String getJianzhuMianji() {
        return jianzhuMianji;
    }

    public void setJianzhuMianji(String jianzhuMianji) {
        this.jianzhuMianji = jianzhuMianji;
    }

    public String getInnerPeitaomiaoshu() {
        return innerPeitaomiaoshu;
    }

    public void setInnerPeitaomiaoshu(String innerPeitaomiaoshu) {
        this.innerPeitaomiaoshu = innerPeitaomiaoshu;
    }

    public String getInOutCarparkCnt() {
        return inOutCarparkCnt;
    }

    public void setInOutCarparkCnt(String inOutCarparkCnt) {
        this.inOutCarparkCnt = inOutCarparkCnt;
    }

    public String getInnerEdu() {
        return innerEdu;
    }

    public void setInnerEdu(String innerEdu) {
        this.innerEdu = innerEdu;
    }

    public String getHouseAttribute() {
        return houseAttribute;
    }

    public void setHouseAttribute(String houseAttribute) {
        this.houseAttribute = houseAttribute;
    }

    public String getMainHuxing() {
        return mainHuxing;
    }

    public void setMainHuxing(String mainHuxing) {
        this.mainHuxing = mainHuxing;
    }

    public String getShopCnt() {
        return shopCnt;
    }

    public void setShopCnt(String shopCnt) {
        this.shopCnt = shopCnt;
    }

    public String getWaterSupp() {
        return waterSupp;
    }

    public void setWaterSupp(String waterSupp) {
        this.waterSupp = waterSupp;
    }

    public String getEpowerSupp() {
        return epowerSupp;
    }

    public void setEpowerSupp(String epowerSupp) {
        this.epowerSupp = epowerSupp;
    }

    public String getGasSupp() {
        return gasSupp;
    }

    public void setGasSupp(String gasSupp) {
        this.gasSupp = gasSupp;
    }

    public String getHeatSupp() {
        return heatSupp;
    }

    public void setHeatSupp(String heatSupp) {
        this.heatSupp = heatSupp;
    }

    public String getIcdDev() {
        return icdDev;
    }

    public void setIcdDev(String icdDev) {
        this.icdDev = icdDev;
    }

    public String getLiftService() {
        return liftService;
    }

    public void setLiftService(String liftService) {
        this.liftService = liftService;
    }

    public String getSafeService() {
        return safeService;
    }

    public void setSafeService(String safeService) {
        this.safeService = safeService;
    }

    public String getEnvService() {
        return envService;
    }

    public void setEnvService(String envService) {
        this.envService = envService;
    }

    public String getBuildStructure() {
        return buildStructure;
    }

    public void setBuildStructure(String buildStructure) {
        this.buildStructure = buildStructure;
    }

    public String getChurukouCnt() {
        return churukouCnt;
    }

    public void setChurukouCnt(String churukouCnt) {
        this.churukouCnt = churukouCnt;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getManagementId() {
        return managementId;
    }

    public void setManagementId(String managementId) {
        this.managementId = managementId;
    }

    public CommunityManagementBean getManagementObj() {
        return managementObj;
    }

    public void setManagementObj(CommunityManagementBean managementObj) {
        this.managementObj = managementObj;
    }

    public List<PdSVO> getPdSList() {
        return pdSList;
    }

    public void setPdSList(List<PdSVO> pdSList) {
        this.pdSList = pdSList;
    }

    public List<AttributeVO> getAttributeLits() {
        return attributeLits;
    }

    public void setAttributeLits(List<AttributeVO> attributeLits) {
        this.attributeLits = attributeLits;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    public String getLumianQingkuang() {
        return lumianQingkuang;
    }

    public void setLumianQingkuang(String lumianQingkuang) {
        this.lumianQingkuang = lumianQingkuang;
    }

    public String getGisId() {
        return gisId;
    }

    public void setGisId(String gisId) {
        this.gisId = gisId;
    }

    public List<DimFileinfoVO> getCommPhotos() {
        return commPhotos;
    }

    public void setCommPhotos(List<DimFileinfoVO> commPhotos) {
        this.commPhotos = commPhotos;
    }
}