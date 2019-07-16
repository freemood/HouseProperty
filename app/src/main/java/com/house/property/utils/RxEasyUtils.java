package com.house.property.utils;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.house.property.http.retrofit.RxActionManagerImpl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RxEasyUtils {
    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 100;
    public final static int WRITE_TIMEOUT = 60;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    public static String get(String url) {
        try {
            Request request = new Request.Builder().url(url).get().build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String post(String url, String json) {
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String patch(String url, String json) {
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .patch(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void startGetObservable(final String url, final Handler handler) {
        Observable observable = Observable.interval(1, TimeUnit.SECONDS).create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext(get(url));
                observableEmitter.onComplete();
            }
        });
        callRxJavaServer(observable, handler);
    }

    public static void startPostObservable(final String url, final String json, final Handler handler) {
        Observable observable = Observable.interval(1, TimeUnit.SECONDS).create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext(post(url, json));
                observableEmitter.onComplete();
            }
        });
        callRxJavaServer(observable, handler);
    }

    public static void startPatchObservable(final String url, final String json, final Handler handler) {
        Observable observable = Observable.interval(1, TimeUnit.SECONDS).create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext(patch(url, json));
                observableEmitter.onComplete();
            }
        });
        callRxJavaServer(observable, handler);
    }

    public static void callRxJavaServer(final Observable observable, final Handler handler) {

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), false, 100)
                .subscribe(new Observer<String>() {
                    Disposable mDisposable = null;

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        String mTag = String.valueOf(System.currentTimeMillis());
                        mDisposable = disposable;
                        RxActionManagerImpl.getInstance().add(mTag, mDisposable);
                    }

                    @Override
                    public void onNext(String result) {
                        mDisposable.dispose();
                        Message msg = new Message();
                        if (TextUtils.isEmpty(result)) {
                            msg.what = 99;
                            handler.sendMessage(msg);
                            return;
                        }

                        msg.obj = result;
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mDisposable.dispose();
                        Message msg = new Message();
                        msg.what = 99;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
