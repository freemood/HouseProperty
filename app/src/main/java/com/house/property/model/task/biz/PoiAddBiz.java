package com.house.property.model.task.biz;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.house.property.base.BaseBiz;
import com.house.property.http.helper.ParseHelper;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.http.retrofit.HttpRequest;
import com.house.property.model.task.entity.BuildDetailsVO;
import com.house.property.model.task.entity.DimPOIVO;
import com.house.property.model.task.entity.GisObjVO;
import com.house.property.model.task.entity.MessageEventVO;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

import static com.house.property.http.retrofit.HttpRequest.Method.POST;
import static com.house.property.utils.Utils.getMap;

public class PoiAddBiz extends BaseBiz {
    /**
     *新增楼栋详细信息
     */
    private final String API_LOGIN = "build/addNewPoi";
    public void addNewPoi(DimPOIVO bean, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        Gson gson = new Gson();
        String jsonStr = gson.toJson(bean);
        request =getMap(jsonStr);

        request.put(HttpRequest.API_URL, API_LOGIN);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                MessageEventVO bean = new Gson().fromJson(jsonElement, MessageEventVO.class);
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
