package com.house.property.model.main.biz;

import com.house.property.base.BaseBiz;
import com.house.property.http.helper.ParseHelper;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.http.retrofit.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.house.property.model.main.entity.LoginBean;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

import static com.house.property.http.retrofit.HttpRequest.Method.POST;

public class LoginBiz extends BaseBiz {
    /**
     *登录信息
     */
    private final String API_LOGIN = "user/login";
    public void startLoginParm(String username, String password, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("code", username);
        request.put("name", username);
        request.put("password", password);
        request.put(HttpRequest.API_URL, API_LOGIN);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                LoginBean bean = new Gson().fromJson(jsonElement, LoginBean.class);
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
