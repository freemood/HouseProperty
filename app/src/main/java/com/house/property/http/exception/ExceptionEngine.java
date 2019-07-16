package com.house.property.http.exception;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;


/**
 * 错误/异常处理工具
 */
public class ExceptionEngine {
    //对应HTTP的状态码
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;

    public static final int UN_KNOWN_ERROR = 1000;//未知错误
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;//解析(服务器)数据错误
    public static final int ANALYTIC_CLIENT_DATA_ERROR = 1002;//解析(客户端)数据错误
    public static final int CONNECT_ERROR = 1003;//网络连接错误
    public static final int TIME_OUT_ERROR = 1004;//网络连接超时

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        String msg = "";
        JSONObject jb = null;
        String error = "";
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpExc = (HttpException) e;
            ex = new ApiException(e, httpExc.code());
            try {
                if (null == httpExc.response() || null == httpExc.response().errorBody()) {
                    return ex;
                }
                msg = httpExc.response().errorBody().string();
                jb = new JSONObject(msg);
                error = jb.optString("message");
            } catch (Exception e1) {
                ex.setMsg("网络错误");
            }
            switch (httpExc.code()) {
                case UNAUTHORIZED:
                    ex = new ApiException(e, UNAUTHORIZED);
                    ex.setMsg(error);
                    break;
                case FORBIDDEN:
                    ex = new ApiException(e, FORBIDDEN);
                    ex.setMsg(error);
                    break;
                case NOT_FOUND:
                    ex = new ApiException(e, NOT_FOUND);
                    if (error.contains("密码")) {
                        error = "账号或密码错误";
                    }
                    ex.setMsg(error);
                    break;
                case REQUEST_TIMEOUT:
                    ex = new ApiException(e, REQUEST_TIMEOUT);
                    ex.setMsg(error);
                    break;
                case GATEWAY_TIMEOUT:
                    ex = new ApiException(e, GATEWAY_TIMEOUT);
                    ex.setMsg(error);
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex = new ApiException(e, INTERNAL_SERVER_ERROR);
                    ex.setMsg(error);
                    break;
                case BAD_GATEWAY:
                    ex = new ApiException(e, BAD_GATEWAY);
                    ex.setMsg(error);
                    break;
                case SERVICE_UNAVAILABLE:
                    ex = new ApiException(e, SERVICE_UNAVAILABLE);
                    ex.setMsg(error);
                    break;
                default:
                    ex.setMsg("网络错误");  //均视为网络错误
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {    //服务器返回的错误
            ServerException serverExc = (ServerException) e;
            ex = new ApiException(serverExc, serverExc.getCode());
            ex.setMsg(serverExc.getMsg());
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {  //解析数据错误
            ex = new ApiException(e, ANALYTIC_SERVER_DATA_ERROR);
            ex.setMsg("解析错误");
            return ex;
        } else if (e instanceof ConnectException || e instanceof SSLHandshakeException || e instanceof UnknownHostException) {//连接网络错误
            ex = new ApiException(e, CONNECT_ERROR);
            ex.setMsg("连接失败");
            return ex;
        } else if (e instanceof SocketTimeoutException) {//网络超时
            ex = new ApiException(e, TIME_OUT_ERROR);
            ex.setMsg("网络超时");
            return ex;
        } else {  //未知错误
            ex = new ApiException(e, UN_KNOWN_ERROR);
            ex.setMsg("未知错误");
            return ex;
        }
    }

}
