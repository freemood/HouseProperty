package com.house.property.model.task.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class BuildDetailsVO implements Serializable {

    ////@ApiModelProperty("栋id")
    private String id;
    ////@ApiModelProperty("栋号")
    private String buildNo;
    ////@ApiModelProperty("栋名称")
    private String buildName;
    ////@ApiModelProperty("栋楼层总数")
    private Long buildFloorcnt;
    ////@ApiModelProperty("小区ID")
    private String commId;
    ////@ApiModelProperty("小区名称")
    private String commName;
    ////@ApiModelProperty("栋建成年代")
    private Date buildDate;
    ////@ApiModelProperty("地下层数")
    private Long underFloorcnt;
    ////@ApiModelProperty("地面所在层")
    private String groundFloorno;
    ////@ApiModelProperty("顶层所在层")
    private String topFloorno;
    ////@ApiModelProperty("住宅所在层")
    private String houseFloorno;
    ////@ApiModelProperty("建筑结构")
    private String buildStructure;
    ////@ApiModelProperty("建筑等级 ")
    private String buildLevel;
    ////@ApiModelProperty("建筑类型")
    private String buildType;
    ////@ApiModelProperty("销售许可证")
    private String salesLicense;
    ////@ApiModelProperty("土地面积")
    private String landArea;
    ////@ApiModelProperty("土地时间")
    private String landDate;
    ////@ApiModelProperty("空间数据ID")
    private String gisId;
    ////@ApiModelProperty("单元数")
    private String buildcellCnt;
    ////@ApiModelProperty("几梯几户")
    private String buildcellInfo;
    ////@ApiModelProperty("朝向")
    private String buildChaoxiang;
    ////@ApiModelProperty("住房属性")
    private String buildAttr;
    ////@ApiModelProperty("商铺所在层")
    private String shopFloorno;
    ////@ApiModelProperty("住房保养情况")
    private String buildCareinfo;
    ////@ApiModelProperty("外墙情况")
    private String extWall;
    ////@ApiModelProperty("顶层情况")
    private String topFloorinfo;
    ////@ApiModelProperty("是否景观楼")
    private String isJingguanlou;
    ////@ApiModelProperty("装修程度")
    private String zhuangxiuInfo;
    ////@ApiModelProperty("是否新风")
    private String isXinfeng;
    ////@ApiModelProperty("是否集中供暖")
    private String isJizhonggongnuan;
    ////@ApiModelProperty("是否门禁系统")
    private String isMengjin;
    ////@ApiModelProperty("是否梯控")
    private String isTikong;
    ////@ApiModelProperty("是否安防")
    private String isAnfang;
    ////@ApiModelProperty("是否有公共娱乐空间")
    private String isPubFunspace;
    ////@ApiModelProperty("是否消防设施")
    private String isFpd;
    ////@ApiModelProperty("是否有车库")
    private String isGarage;
    ////@ApiModelProperty("是否有储藏室")
    private String isStoreroom;
    ////@ApiModelProperty("是否二次供水")
    private String isWaterSupp;
    ////@ApiModelProperty("是否供电")
    private String isEpowerSupp;
    ////@ApiModelProperty("是否供气")
    private String isGasSupp;
    ////@ApiModelProperty("是否供热")
    private String isHeatSupp;
    ////@ApiModelProperty("是否阁楼")
    private String isGelou;
    ////@ApiModelProperty("是否电梯")
    private String isLift;
    ////@ApiModelProperty("是否地暖")
    private String isHeat;
    ////@ApiModelProperty("是否中央空调")
    private String isZhongyangkongtiao;
    ////@ApiModelProperty("特殊层")
    private String specFloorinfo;
    ////@ApiModelProperty("电污染")
    private String epowerPollinfo;
    ////@ApiModelProperty("是否有垃圾站")
    private String isRubbstation;
    ////@ApiModelProperty("噪音污染 ")
    private String noisePollinfo;
    ////@ApiModelProperty("气味污染")
    private String smellPollinfo;
    ////@ApiModelProperty("光污染")
    private String rayPollinfo;
    ////@ApiModelProperty("文化影响")
    private String culturePollinfo;
    ////@ApiModelProperty("备注")
    private String remark;
    private List<PdSVO> pdSList;
    ////@ApiModelProperty("栋楼空间数据")
    private GisObjVO gisObjVO;
    ////@ApiModelProperty("多选项对象")
    private List<AttributeVO> attributeLits;
    private String taskId;
   // //@ApiModelProperty("照片")
    private List<DimFileinfoVO> commPhotos;
    //@ApiModelProperty("水费类型")
    private String waterChargesType;
    //@ApiModelProperty("电费类型")
    private String electricityChargesType;
    //@ApiModelProperty("交付装修")
    private String deliveryDecoration;
    //@ApiModelProperty("楼栋保养（内外墙）")
    private String buildMaintenance;
    //@ApiModelProperty("特殊层号")
    private String specFloorinfoNo;
    //@ApiModelProperty("特殊户")
    private String specialHousehold;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public Long getBuildFloorcnt() {
        return buildFloorcnt;
    }

    public void setBuildFloorcnt(Long buildFloorcnt) {
        this.buildFloorcnt = buildFloorcnt;
    }

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

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    public Long getUnderFloorcnt() {
        return underFloorcnt;
    }

    public void setUnderFloorcnt(Long underFloorcnt) {
        this.underFloorcnt = underFloorcnt;
    }

    public String getGroundFloorno() {
        return groundFloorno;
    }

    public void setGroundFloorno(String groundFloorno) {
        this.groundFloorno = groundFloorno;
    }

    public String getTopFloorno() {
        return topFloorno;
    }

    public void setTopFloorno(String topFloorno) {
        this.topFloorno = topFloorno;
    }

    public String getHouseFloorno() {
        return houseFloorno;
    }

    public void setHouseFloorno(String houseFloorno) {
        this.houseFloorno = houseFloorno;
    }

    public String getBuildStructure() {
        return buildStructure;
    }

    public void setBuildStructure(String buildStructure) {
        this.buildStructure = buildStructure;
    }

    public String getBuildLevel() {
        return buildLevel;
    }

    public void setBuildLevel(String buildLevel) {
        this.buildLevel = buildLevel;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    public String getSalesLicense() {
        return salesLicense;
    }

    public void setSalesLicense(String salesLicense) {
        this.salesLicense = salesLicense;
    }

    public String getLandArea() {
        return landArea;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    public String getLandDate() {
        return landDate;
    }

    public void setLandDate(String landDate) {
        this.landDate = landDate;
    }

    public String getGisId() {
        return gisId;
    }

    public void setGisId(String gisId) {
        this.gisId = gisId;
    }

    public String getBuildcellCnt() {
        return buildcellCnt;
    }

    public void setBuildcellCnt(String buildcellCnt) {
        this.buildcellCnt = buildcellCnt;
    }

    public String getBuildcellInfo() {
        return buildcellInfo;
    }

    public void setBuildcellInfo(String buildcellInfo) {
        this.buildcellInfo = buildcellInfo;
    }

    public String getBuildChaoxiang() {
        return buildChaoxiang;
    }

    public void setBuildChaoxiang(String buildChaoxiang) {
        this.buildChaoxiang = buildChaoxiang;
    }

    public String getBuildAttr() {
        return buildAttr;
    }

    public void setBuildAttr(String buildAttr) {
        this.buildAttr = buildAttr;
    }

    public String getShopFloorno() {
        return shopFloorno;
    }

    public void setShopFloorno(String shopFloorno) {
        this.shopFloorno = shopFloorno;
    }

    public String getBuildCareinfo() {
        return buildCareinfo;
    }

    public void setBuildCareinfo(String buildCareinfo) {
        this.buildCareinfo = buildCareinfo;
    }

    public String getExtWall() {
        return extWall;
    }

    public void setExtWall(String extWall) {
        this.extWall = extWall;
    }

    public String getTopFloorinfo() {
        return topFloorinfo;
    }

    public void setTopFloorinfo(String topFloorinfo) {
        this.topFloorinfo = topFloorinfo;
    }

    public String getIsJingguanlou() {
        return isJingguanlou;
    }

    public void setIsJingguanlou(String isJingguanlou) {
        this.isJingguanlou = isJingguanlou;
    }

    public String getZhuangxiuInfo() {
        return zhuangxiuInfo;
    }

    public void setZhuangxiuInfo(String zhuangxiuInfo) {
        this.zhuangxiuInfo = zhuangxiuInfo;
    }

    public String getIsXinfeng() {
        return isXinfeng;
    }

    public void setIsXinfeng(String isXinfeng) {
        this.isXinfeng = isXinfeng;
    }

    public String getIsJizhonggongnuan() {
        return isJizhonggongnuan;
    }

    public void setIsJizhonggongnuan(String isJizhonggongnuan) {
        this.isJizhonggongnuan = isJizhonggongnuan;
    }

    public String getIsMengjin() {
        return isMengjin;
    }

    public void setIsMengjin(String isMengjin) {
        this.isMengjin = isMengjin;
    }

    public String getIsTikong() {
        return isTikong;
    }

    public void setIsTikong(String isTikong) {
        this.isTikong = isTikong;
    }

    public String getIsAnfang() {
        return isAnfang;
    }

    public void setIsAnfang(String isAnfang) {
        this.isAnfang = isAnfang;
    }

    public String getIsPubFunspace() {
        return isPubFunspace;
    }

    public void setIsPubFunspace(String isPubFunspace) {
        this.isPubFunspace = isPubFunspace;
    }

    public String getIsFpd() {
        return isFpd;
    }

    public void setIsFpd(String isFpd) {
        this.isFpd = isFpd;
    }

    public String getIsGarage() {
        return isGarage;
    }

    public void setIsGarage(String isGarage) {
        this.isGarage = isGarage;
    }

    public String getIsStoreroom() {
        return isStoreroom;
    }

    public void setIsStoreroom(String isStoreroom) {
        this.isStoreroom = isStoreroom;
    }

    public String getIsWaterSupp() {
        return isWaterSupp;
    }

    public void setIsWaterSupp(String isWaterSupp) {
        this.isWaterSupp = isWaterSupp;
    }

    public String getIsEpowerSupp() {
        return isEpowerSupp;
    }

    public void setIsEpowerSupp(String isEpowerSupp) {
        this.isEpowerSupp = isEpowerSupp;
    }

    public String getIsGasSupp() {
        return isGasSupp;
    }

    public void setIsGasSupp(String isGasSupp) {
        this.isGasSupp = isGasSupp;
    }

    public String getIsHeatSupp() {
        return isHeatSupp;
    }

    public void setIsHeatSupp(String isHeatSupp) {
        this.isHeatSupp = isHeatSupp;
    }

    public String getIsGelou() {
        return isGelou;
    }

    public void setIsGelou(String isGelou) {
        this.isGelou = isGelou;
    }

    public String getIsLift() {
        return isLift;
    }

    public void setIsLift(String isLift) {
        this.isLift = isLift;
    }

    public String getIsHeat() {
        return isHeat;
    }

    public void setIsHeat(String isHeat) {
        this.isHeat = isHeat;
    }

    public String getIsZhongyangkongtiao() {
        return isZhongyangkongtiao;
    }

    public void setIsZhongyangkongtiao(String isZhongyangkongtiao) {
        this.isZhongyangkongtiao = isZhongyangkongtiao;
    }

    public String getSpecFloorinfo() {
        return specFloorinfo;
    }

    public void setSpecFloorinfo(String specFloorinfo) {
        this.specFloorinfo = specFloorinfo;
    }

    public String getEpowerPollinfo() {
        return epowerPollinfo;
    }

    public void setEpowerPollinfo(String epowerPollinfo) {
        this.epowerPollinfo = epowerPollinfo;
    }

    public String getIsRubbstation() {
        return isRubbstation;
    }

    public void setIsRubbstation(String isRubbstation) {
        this.isRubbstation = isRubbstation;
    }

    public String getNoisePollinfo() {
        return noisePollinfo;
    }

    public void setNoisePollinfo(String noisePollinfo) {
        this.noisePollinfo = noisePollinfo;
    }

    public String getSmellPollinfo() {
        return smellPollinfo;
    }

    public void setSmellPollinfo(String smellPollinfo) {
        this.smellPollinfo = smellPollinfo;
    }

    public String getRayPollinfo() {
        return rayPollinfo;
    }

    public void setRayPollinfo(String rayPollinfo) {
        this.rayPollinfo = rayPollinfo;
    }

    public String getCulturePollinfo() {
        return culturePollinfo;
    }

    public void setCulturePollinfo(String culturePollinfo) {
        this.culturePollinfo = culturePollinfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public GisObjVO getGisObjVO() {
        return gisObjVO;
    }

    public void setGisObjVO(GisObjVO gisObjVO) {
        this.gisObjVO = gisObjVO;
    }

    public List<AttributeVO> getAttributeLits() {
        return attributeLits;
    }

    public void setAttributeLits(List<AttributeVO> attributeLits) {
        this.attributeLits = attributeLits;
    }

    public List<PdSVO> getPdSList() {
        return pdSList;
    }

    public void setPdSList(List<PdSVO> pdSList) {
        this.pdSList = pdSList;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<DimFileinfoVO> getCommPhotos() {
        return commPhotos;
    }

    public void setCommPhotos(List<DimFileinfoVO> commPhotos) {
        this.commPhotos = commPhotos;
    }

    public String getWaterChargesType() {
        return waterChargesType;
    }

    public void setWaterChargesType(String waterChargesType) {
        this.waterChargesType = waterChargesType;
    }

    public String getElectricityChargesType() {
        return electricityChargesType;
    }

    public void setElectricityChargesType(String electricityChargesType) {
        this.electricityChargesType = electricityChargesType;
    }

    public String getDeliveryDecoration() {
        return deliveryDecoration;
    }

    public void setDeliveryDecoration(String deliveryDecoration) {
        this.deliveryDecoration = deliveryDecoration;
    }

    public String getBuildMaintenance() {
        return buildMaintenance;
    }

    public void setBuildMaintenance(String buildMaintenance) {
        this.buildMaintenance = buildMaintenance;
    }

    public String getSpecFloorinfoNo() {
        return specFloorinfoNo;
    }

    public void setSpecFloorinfoNo(String specFloorinfoNo) {
        this.specFloorinfoNo = specFloorinfoNo;
    }

    public String getSpecialHousehold() {
        return specialHousehold;
    }

    public void setSpecialHousehold(String specialHousehold) {
        this.specialHousehold = specialHousehold;
    }
}