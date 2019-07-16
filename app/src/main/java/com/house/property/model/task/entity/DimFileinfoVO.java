package com.house.property.model.task.entity;

import java.io.Serializable;

import okhttp3.MultipartBody;


public class DimFileinfoVO implements Serializable {
    //@ApiModelProperty("id")
    private String id;
    //@ApiModelProperty("关联主表主键 比如小区或楼栋id")
    private String relaSrckey;
    //@ApiModelProperty("关联源表")
    private String relaSrctab;
    //@ApiModelProperty("文件类型")
    private String fileType;
    //@ApiModelProperty("文件业务类型")
    private String fileBtype;
    //@ApiModelProperty("文件路径")
    private String filePath;
    //@ApiModelProperty("文件序号")
    private String fileSeq;
    //@ApiModelProperty("状态")
    private String attrState;
    //@ApiModelProperty("文件名称")
    private String fileName;
    //@ApiModelProperty("文件路径")
    private String fileSdcardPath;
    //    @ApiModelProperty("文件坐标信息对象")
    private FileCoordinateInfoVO fileCoordinateInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelaSrckey() {
        return relaSrckey;
    }

    public void setRelaSrckey(String relaSrckey) {
        this.relaSrckey = relaSrckey;
    }

    public String getRelaSrctab() {
        return relaSrctab;
    }

    public void setRelaSrctab(String relaSrctab) {
        this.relaSrctab = relaSrctab;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileBtype() {
        return fileBtype;
    }

    public void setFileBtype(String fileBtype) {
        this.fileBtype = fileBtype;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSeq() {
        return fileSeq;
    }

    public void setFileSeq(String fileSeq) {
        this.fileSeq = fileSeq;
    }

    public String getAttrState() {
        return attrState;
    }

    public void setAttrState(String attrState) {
        this.attrState = attrState;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSdcardPath() {
        return fileSdcardPath;
    }

    public void setFileSdcardPath(String fileSdcardPath) {
        this.fileSdcardPath = fileSdcardPath;
    }

    public FileCoordinateInfoVO getFileCoordinateInfo() {
        return fileCoordinateInfo;
    }

    public void setFileCoordinateInfo(FileCoordinateInfoVO fileCoordinateInfo) {
        this.fileCoordinateInfo = fileCoordinateInfo;
    }
}