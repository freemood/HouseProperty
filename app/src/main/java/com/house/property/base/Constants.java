package com.house.property.base;

import android.os.Environment;

import com.house.property.model.task.entity.PdSVO;

import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_PICTURES;

public class Constants {
    //http://47.102.155.171:8090/gis/app/
    //http://172.21.92.93:8090/gis/app/

    public final static String BASE_API = "http://172.21.94.51:8090/gis/app/";
    public final static String CONTENT_TYPE = "application/json;charset=UTF-8";
    public static String CAMERA_SAVE_PATH = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getPath();
    public final static String PHOTO_PATH = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getPath();
    public static final int TAKE_PHOTO = 5;

    public static final String TAKE_TYPE_DEALT = "TASK_DEALT";
    public static final String TAKE_TYPE_DONE = "TASK_DONE";
    public static final String TAKE_TYPE_THROUGH = "TASK_THROUGH";

    public static final String PDS_TYPE_YN = "YN";
    public static final String PDS_TYPE_ONE_TO_SERVEN = "ONE_TO_SERVEN";
    public static final String PDS_TYPE_EVN_LEVEL = "EVN_LEVEL";
    public static final String PDS_TYPE_ROAD = "LUMIAN_QINGKUANG";

    public static final String ATTRIBUTE_TYPE_COMMUNICATION = "DIM_COMMUNITY";
    public static final String ATTRIBUTE_TYPE_COMM_MANAGEMENT = "DIM_COMM_MANAGEMENT";
    public static final String ATTRIBUTE_TYPE_DIM_BUILD = "DIM_BUILD";
    //多选
    public static final String PDS_TYPE_INNER_PEITAOMIAOSHU = "INNER_PEITAOMIAOSHU";
    public static final String PDS_TYPE_INNER_EDU = "INNER_EDU";
    public static final String PDS_TYPE_JINGGUAN = "JINGGUAN";
    public static final String PDS_TYPE_HOUSE_ATTRIBUTE = "HOUSE_ATTRIBUTE";
    public static final String PDS_TYPE_WEIQIANG_FANGHU = "WEIQIANG_FANGHU";
    public static final String PDS_TYPE_TUDIXINGZHI = "TUDIXINGZHI";
    //建筑结构
    public static final String PDS_TYPE_BUILD_STRUCTURE = "BUILD_STRUCTURE";
    //建筑类型
    public static final String PDS_TYPE_BUILD_TYPE = "BUILD_TYPE";
    public static final String PDS_TYPE_WATER_SUPP = "WATER_SUPP";
    public static final String PDS_TYPE_EPOWER_SUPP = "EPOWER_SUPP";
    public static final String PDS_TYPE_WG_SECURITY = "WG_SECURITY";
    public static final String PDS_TYPE_CHANQQUANNIANXIAN = "CHANQQUANNIANXIAN";
    //建筑等级
    public static final String PDS_TYPE_JIANZU_LEVEL = "JIANZU_LEVEL";
    //特殊层
    public static final String PDS_TYPE_TESUCEN_TEDIAN = "TESUCEN_TEDIAN";
    //电
    public static final String PDS_TYPE_DIAN_WULAN = "DIAN_WULAN";
    //噪音
    public static final String PDS_TYPE_ZAOYIN_WULAN = "ZAOYIN_WULAN";
    //气味
    public static final String PDS_TYPE_QIWEI_WULAN = "QIWEI_WULAN";
    //光
    public static final String PDS_TYPE_GUAN_WULAN = "GUAN_WULAN";
    //文化
    public static final String PDS_TYPE_WENHUA_YINXIAN = "WENHUA_YINXIAN";
    //住房保养情况
    public static final String PDS_TYPE_ZUFANG_BAOYANG = "ZUFANG_BAOYANG";
    //外墙情况
    public static final String PDS_TYPE_WAIQIANG = "WAIQIANG";
    //顶层情况
    public static final String PDS_TYPE_DINGCENG = "DINGCENG";
    //装修程度
    public static final String PDS_TYPE_ZHUANGXIU_INFO = "ZHUANGXIU_INFO";
    public  static List<PdSVO> PDSVO_LIST = new ArrayList<>();
    public static String TASK_NUMBER = "";

    //门楼照片
    public static final String COMM_GATEHOUSE_PHOTO = "GATEHOUSE_PHOTO";
    //公共部分照片
    public static final String COMM_PLACES_PHOTO = "PLACES_PHOTO";
    //设施照片
    public static final String COMM_FACILITIES_PHOTO = "FACILITIES_PHOTO";
    //负面照片
    public static final String COMM_NEGATIVE_PHOTO = "NEGATIVE_PHOTO";
    //大门照片
    public static final String COMM_GATE_PHOTO = "GATE_PHOTO";
    //整栋楼照片
    public static final String COMM_ENTIRE_PHOTO = "ENTIRE_PHOTO";
}
