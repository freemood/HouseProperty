package com.house.property.http.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * http响应参数实体类
 * 通过Gson解析属性名称需要与服务器返回字段对应,或者使用注解@SerializedName
 * 备注:这里与服务器约定返回格式
 *
 */
public class HttpResponse {

    /**
     * 描述信息
     */
    @SerializedName("timestamp")
    private String timestamp;
    /**
     * 描述信息
     */
    @SerializedName("returnMsg")
    private String msg;

    @SerializedName("message")
    private String message;
    /**
     * 状态码
     */
    @SerializedName("returnCode")
    private int code;

    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    @SerializedName("data")
    private JsonElement result;

    /**
     * 是否成功(这里约定200)
     *
     * @return
     */
    public boolean isSuccess() {
        return code == 200  ? true : false;
    }

    public String toString() {
        String response = "[http response]" + "{\"code\": " + code+ ",\"timestamp\":" + timestamp  + ",\"msg\":" + msg + ",\"result\":" + new Gson().toJson(result) + "}";
        return response;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JsonElement getResult() {
        return result;
    }

    public void setResult(JsonElement result) {
        this.result = result;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public HttpResponse setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

}
