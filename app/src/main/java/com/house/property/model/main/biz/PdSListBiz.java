package com.house.property.model.main.biz;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.house.property.base.BaseBiz;
import com.house.property.http.helper.ParseHelper;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.http.retrofit.HttpRequest;
import com.house.property.model.main.entity.PdSListBean;
import com.house.property.model.main.entity.TaskBean;
import com.house.property.model.main.entity.TaskListBean;
import com.house.property.model.task.entity.PdSVO;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import static com.house.property.http.retrofit.HttpRequest.Method.POST;

public class PdSListBiz extends BaseBiz {
    /**
     *类型列表
     */
    private final String API_LOGIN = "task/getPdsTypes";
    public void getPdsTypes(LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put(HttpRequest.API_URL, API_LOGIN);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                PdSListBean bean = new PdSListBean();
                JsonArray arrayJson = jsonElement.getAsJsonArray();
                List<PdSVO> list = new ArrayList<>();
                Iterator it = arrayJson.iterator();
                while (it.hasNext()) {
                    JsonElement e = (JsonElement) it.next();
                    //JsonElement转换为JavaBean对象
                    PdSVO pe = new Gson().fromJson(e, PdSVO.class);
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
