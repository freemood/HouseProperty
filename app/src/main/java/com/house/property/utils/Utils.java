package com.house.property.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.house.property.R;
import com.house.property.base.BaseApplication;
import com.house.property.model.task.entity.PdSVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static android.content.Context.MODE_PRIVATE;

public class Utils {
    /**
     * 匹配手机号的规则：[3578]是手机号第二位可能出现的数字
     */
    public static final String REGEX_MOBILE = "^[1][3578][0-9]{9}$";
    public static String ROOT_NAME = "app";
    public static ListView listView;
    public static StringBuffer listSb;

    public static void write(String key, Object value) {
        SharedPreferences spfs = BaseApplication.getContext().getApplicationContext().getSharedPreferences(ROOT_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spfs.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Double) {
            editor.putString(key, String.valueOf(((Double) value).doubleValue()));
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            throw new IllegalArgumentException("not support type");
        }
        editor.commit();
    }

    public static void writeSharedPreferences(String shareName, Map<String, String> properties) {
        SharedPreferences.Editor editor = BaseApplication.getContext().getSharedPreferences(shareName,
                MODE_PRIVATE).edit();

        if (properties != null) {
            for (Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                editor.putString(entry.getKey(), entry.getValue());
            }
        }
        editor.commit();
    }

    public static String readSharedPreferences(String shareName, String key) {
        SharedPreferences editor = BaseApplication.getContext().getSharedPreferences(shareName,
                MODE_PRIVATE);
        return editor.getString(key, "");
    }

    public static String readSharedPreferences(String key) {
        SharedPreferences editor = BaseApplication.getContext().getSharedPreferences(ROOT_NAME,
                MODE_PRIVATE);
        return editor.getString(key, "");
    }

    public static boolean readBoolean(String key, boolean defValue) {
        SharedPreferences spfs = BaseApplication.getContext().getApplicationContext().getSharedPreferences(ROOT_NAME, Context.MODE_PRIVATE);
        return spfs.getBoolean(key, defValue);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    public static void showMultiChoiceItems(final Activity activity, List<PdSVO> typeList, final TextView appearanceEducationTv) {
        if (null == typeList || 0 == typeList.size()) {
            return;
        }
        int size = typeList.size();
        String[] str = new String[size];
        for (int j = 0; j < size; j++) {
            PdSVO pdSVO = typeList.get(j);
            if (!TextUtils.isEmpty(pdSVO.getName())) {
                str[j] = pdSVO.getName();
            }
        }
        final int count = str.length;
        String checkStr = appearanceEducationTv.getText().toString();
        boolean[] isChecks = new boolean[count];
        for (int i = 0; i < count; i++) {
            if (checkStr.contains(str[i])) {
                isChecks[i] = true;
            } else {
                isChecks[i] = false;
            }
        }
        AlertDialog builder = new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.btn_checks))
                .setMultiChoiceItems(str, isChecks, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                }).setPositiveButton(activity.getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuffer listSb = new StringBuffer();
                        for (int i = 0; i < count; i++) {
                            if (listView.getCheckedItemPositions().get(i)) {
                                listSb.append(listView.getAdapter().getItem(i)).append(",");
                            }
                        }
                        // 用户至少选择了一个列表项
                        if (listView.getCheckedItemPositions().size() > 0) {
                            String ss = listSb.toString();
                            ss = ss.substring(0, listSb.length() - 1);
                            appearanceEducationTv.setText(ss);
                        }
                        // 用户未选择任何列表项
                        else if (listView.getCheckedItemPositions().size() <= 0) {
                            ToastUtils.showToast(activity, activity.getResources().getString(R.string.btn_checks_one));
                        }
                    }
                }).setNegativeButton("取消", null).create();
        listView = builder.getListView();
        builder.show();
    }

    /**
     * 日期选择
     *
     * @param activity
     * @param tv
     */
    public static void showDatePickerDialog(Activity activity, final TextView tv) {
        Calendar calendar = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 检查年月日是否合法
     *
     * @param ymd
     * @return
     */
    public static boolean checkYearMonthDay(String ymd) {
        if (ymd == null || ymd.length() == 0) {
            return false;
        }
        String s = ymd.replaceAll("[/\\- ]", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = format.parse(s);
            if (!format.format(date).equals(s)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**

     *   将json 数组转换为Map 对象

     * @param jsonString

     * @return

     */

    public static TreeMap<String, Object> getMap(String jsonString)

    {
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(jsonString);
        Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            TreeMap<String, Object> valueMap = new TreeMap<String, Object>();
            while (keyIter.hasNext())
            {
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;

    }
    public static List<String> removeDuplicate(List<String> list)
    {
        Set set = new LinkedHashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

}
