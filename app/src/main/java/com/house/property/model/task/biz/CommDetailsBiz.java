package com.house.property.model.task.biz;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.house.property.base.BaseBiz;
import com.house.property.http.helper.ParseHelper;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.http.retrofit.HttpRequest;
import com.house.property.model.task.entity.CommunityDetailsBean;
import com.trello.rxlifecycle2.LifecycleProvider;
import java.util.TreeMap;

import static com.house.property.http.retrofit.HttpRequest.Method.POST;

public class CommDetailsBiz extends BaseBiz {
    /**
     *小区详细信息
     */
    private final String API_LOGIN = "community/getCommunityDetails";
    public void getCommunityDetails(String id,LifecycleProvider lifecycle, HttpRxCallback callback) {
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
                CommunityDetailsBean bean = new Gson().fromJson(jsonElement, CommunityDetailsBean.class);
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
