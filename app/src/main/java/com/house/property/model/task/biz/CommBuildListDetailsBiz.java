package com.house.property.model.task.biz;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.house.property.base.BaseBiz;
import com.house.property.http.helper.ParseHelper;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.http.retrofit.HttpRequest;
import com.house.property.model.task.entity.BuildDetailsVO;
import com.house.property.model.task.entity.CommBuildListDetailsVO;
import com.house.property.model.task.entity.CommunityDetailsBean;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import static com.house.property.http.retrofit.HttpRequest.Method.POST;

public class CommBuildListDetailsBiz extends BaseBiz {
    /**
     *小区所有楼栋详细信息
     */
    private final String API_LOGIN = "community/getCommBuildDetailsList";
    public void getCommBuildDetailsList(String id,LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("id", id);
        request.put(HttpRequest.API_URL, API_LOGIN);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                CommBuildListDetailsVO bean = new CommBuildListDetailsVO();
                JsonArray arrayJson = jsonElement.getAsJsonArray();
                List<BuildDetailsVO> list = new ArrayList<>();
                Iterator it = arrayJson.iterator();
                while (it.hasNext()) {
                    JsonElement e = (JsonElement) it.next();
                    //JsonElement转换为JavaBean对象
                    BuildDetailsVO pe = new Gson().fromJson(e, BuildDetailsVO.class);
                    list.add(pe);
                }
                bean.setList(list);
                Object[] obj = new Object[1];
                obj[0] = bean;
                return obj;
            }
        });

        /**
         * 发送请求
         */
        getRequest().request(POST, request, lifecycle, callback);
    }
}
