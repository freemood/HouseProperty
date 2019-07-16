package com.house.property.http.retrofit;

import com.house.property.http.Api.Api;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.http.observer.HttpRxObservable;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Http请求类
 */
public class HttpFormRequest {

    /**
     * ApiUrl key 开发者可自定义
     */
    public static final String API_URL = "API_URL";
    public static final String TIME_OUT = "2019-12-31";
//    private final String appKey = "1889b37351288";
//    private final String token_key = "app-token";

    public enum Method {
        GET,
        POST,
        PATCH,
        PUT,
        DELETE,
        UPFILES
    }


    /**
     * 发送请求
     * 备注:不管理生命周期
     *
     * @param method   请求方式
     * @param prams    参数集合
     * @param callback 回调
     */
    public void request(Method method, String url, RequestBody prams, HttpRxCallback callback) {

        Observable<HttpResponse> apiObservable = handleRequest(method, url, prams);

        HttpRxObservable.getObservable(apiObservable, callback).subscribe(callback);

    }


    /**
     * 发送请求
     * 备注:自动管理生命周期
     *
     * @param method    请求方式
     * @param lifecycle 实现RxActivity/RxFragment 参数为空不管理生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    public void request(Method method, String url, RequestBody prams, LifecycleProvider lifecycle, HttpRxCallback callback) {
        Observable<HttpResponse> apiObservable = handleRequest(method, url, prams);

        HttpRxObservable.getObservable(apiObservable, lifecycle, callback).subscribe(callback);
    }


    /**
     * 发送请求
     * 备注:手动指定生命周期-Activity
     *
     * @param method    请求方式
     * @param lifecycle 实现RxActivity
     * @param event     指定生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    public void request(Method method, String url, RequestBody prams, LifecycleProvider<ActivityEvent> lifecycle, ActivityEvent event, HttpRxCallback callback) {
        Observable<HttpResponse> apiObservable = handleRequest(method, url, prams);

        HttpRxObservable.getObservable(apiObservable, lifecycle, event, callback).subscribe(callback);
    }


    /**
     * 发送请求
     * 备注:手动指定生命周期-Fragment
     *
     * @param method    请求方式
     * @param lifecycle 实现RxFragment
     * @param event     指定生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    public void request(Method method, String url, RequestBody prams, LifecycleProvider<FragmentEvent> lifecycle, FragmentEvent event, HttpRxCallback callback) {
        Observable<HttpResponse> apiObservable = handleRequest(method, url, prams);

        HttpRxObservable.getObservable(apiObservable, lifecycle, event, callback).subscribe(callback);
    }


    /**
     * 预处理请求
     *
     * @param method 请求方法
     * @param prams  参数集合
     * @return
     */
    private Observable<HttpResponse> handleRequest(Method method, String url, RequestBody prams) {
        Observable<HttpResponse> apiObservable = null;

        switch (method) {
            case GET:
                apiObservable = RetrofitUtils.get().retrofit().create(Api.class).getFrom(url, prams);
                break;
            case POST:
                apiObservable = RetrofitUtils.get().retrofit().create(Api.class).postFrom(url, prams);
                break;
            case PUT:
                apiObservable = RetrofitUtils.get().retrofit().create(Api.class).putFrom(url, prams);
                break;
            default:
                apiObservable = RetrofitUtils.get().retrofit().create(Api.class).postFrom(url, prams);
                break;

        }
        return apiObservable;
    }


}
