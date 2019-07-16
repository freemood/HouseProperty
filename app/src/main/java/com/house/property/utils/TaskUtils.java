package com.house.property.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Spinner;
import android.widget.TextView;

import com.house.property.adapter.MySpinnerAdapter;
import com.house.property.base.BaseApplication;
import com.house.property.model.task.entity.AttributeVO;
import com.house.property.model.task.entity.CommunityDetailsBean;
import com.house.property.model.task.entity.PdSVO;

import java.util.ArrayList;
import java.util.List;

public class TaskUtils {
    public static void getAttributeTypeList(List<AttributeVO> list, String type, TextView tv) {
        StringBuffer sb = new StringBuffer();
        for (AttributeVO attributeVO : list) {
            if (type.equals(attributeVO.getAttrSrccol())) {
                sb.append(attributeVO.getAttrValue()).append(",");
            }
        }
        String attribute = sb.toString();
        if (TextUtils.isEmpty(attribute)) {
            tv.setText("请选择");
            return;
        }
        attribute = attribute.substring(0, sb.length() - 1);
        tv.setText(attribute);
    }

    public static void initSpinner(Context context, Spinner spinner, List<PdSVO> pdSVOList, String str) {
        List<String> list = new ArrayList<>();
        for (PdSVO pdSVO : pdSVOList) {
            list.add(pdSVO.getName());
            if (!TextUtils.isEmpty(str) && str.equals(pdSVO.getId())) {
                str = pdSVO.getName();
            }
        }
        list.add("请选择");
        MySpinnerAdapter mySpinnerAdapter = new MySpinnerAdapter(context, com.house.property.R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(mySpinnerAdapter);
        if (TextUtils.isEmpty(str)) {
            spinner.setSelection(list.size() - 1, true);
        } else {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (str.equals(list.get(i))) {
                    spinner.setSelection(i, true);
                }
            }

        }

    }

    public static List<PdSVO> getTypeList(List<PdSVO> pdSVOlist, String type) {
        List<PdSVO> list = new ArrayList<>();
        for (PdSVO pdSVO : pdSVOlist) {
            if (type.equals(pdSVO.getPdType())) {
                list.add(pdSVO);
            }
        }
        return list;
    }

    public static String reTextNull(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }


    public static boolean showNullToast(String str, String toastStr) {
        if (TextUtils.isEmpty(str) || "请选择".equals(str)) {
            StringBuffer sb = new StringBuffer();
            if ("请选择".equals(str)) {
                sb.append("请选择").append(toastStr);
            } else {
                sb.append(toastStr).append("未填写");
            }
            ToastUtils.showToast(BaseApplication.getContext(), sb.toString());
            return true;
        }
        return false;
    }

    public static String getSpinnerKey(String str, List<PdSVO> list) {
        if (null == list || 0 == list.size() || TextUtils.isEmpty(str)) {
            return "";
        }
        for (PdSVO vo : list) {
            if (str.equals(vo.getName())) {
                return vo.getId();
            }
        }
        return "";
    }

    //更新多选项list
    public static void addUpdateAttributeList(List<AttributeVO> allAttributeList, List<PdSVO> list, String str, String id, String type) {
        if (null == list || 0 == list.size() || TextUtils.isEmpty(str)) {
            return;
        }
        if (null == allAttributeList) {
            allAttributeList = new ArrayList<>();
        }
        String[] strs = str.split(",");
        int length = strs.length;
        for (int i = 0; i < length; i++) {
            if (!TextUtils.isEmpty(strs[i])) {
                for (PdSVO vo : list) {
                    if (strs[i].equals(vo.getName())) {
                        AttributeVO attributeVO = new AttributeVO();
                        attributeVO.setAttrCode(vo.getId());
                        attributeVO.setAttrName("");
                        attributeVO.setAttrSrccol(vo.getPdType());
                        attributeVO.setAttrSrckey(id);
                        attributeVO.setAttrSrctab(type);
                        attributeVO.setAttrValue(vo.getName());
                        allAttributeList.add(attributeVO);
                    }
                }
            }
        }
    }

}
