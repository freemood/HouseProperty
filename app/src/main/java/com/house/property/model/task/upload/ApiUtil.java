package com.house.property.model.task.upload;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.house.property.http.retrofit.HttpResponse;
import com.house.property.model.task.entity.DimFileinfoVO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.house.property.base.Constants.BASE_API;

/**
 * 数据请求控制工具类
 */
public class ApiUtil {

    private static Retrofit retrofit;
    private final static int DEFAULT_TIMEOUT = 10;//超时时长，单位：秒ou

    /**
     * 初始化 Retrofit
     */
    private static Retrofit getApiRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            okHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
                    builder.addHeader("Content-Type","multipart/form-data");
                    return chain.proceed(builder.build());
                }
            });
            retrofit = new Retrofit.Builder()
                    .client(okHttpBuilder.build())
                    .baseUrl(BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 创建数据请求服务
     */
    private static ApiService getApiService() {
        return ApiUtil.getApiRetrofit().create(ApiService.class);
    }

    public static MultipartBody.Part[] imagesToMultipartBodyParts(@NonNull String key, @NonNull List<File> files) {
        return filesToMultipartBodyParts(key, MediaType.parse("image/jpg"), files);
    }

    public static MultipartBody.Part[] filesToMultipartBodyParts(@NonNull String key, @Nullable MediaType contentType, @NonNull List<File> files) {
        int size = files.size();
        MultipartBody.Part[] parts = new MultipartBody.Part[size];

        for (int i = 0; i < size; i++) {
            File file = files.get(i);
            RequestBody requestBody = RequestBody.create(contentType, file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(),requestBody);
            parts[i] = part;
        }
        return parts;
    }

    /**
     * 上传
     */
    public static Call<HttpResponse> upFilesInfo(List<DimFileinfoVO> dimFileinfoVOList) {
        Gson gson = new Gson();
        String jsonArray = gson.toJson(dimFileinfoVOList);
        RequestBody requestBodystr = RequestBody.create(MediaType.parse("text/plain"), jsonArray);
        Map<String, RequestBody> request = new HashMap<>();
        request.put("jsonArray", requestBodystr);

        List<File> fileList = new ArrayList<>();
        for (DimFileinfoVO dimFileinfoVO : dimFileinfoVOList) {
            //构建要上传的文件
            if (TextUtils.isEmpty(dimFileinfoVO.getFileSdcardPath())) {
                continue;
            }
            File file = new File(dimFileinfoVO.getFileSdcardPath());
            if (file.exists()) {
                fileList.add(file);
            }

        }
        return ApiUtil.getApiService().upFilesInfo("/gis/app/file/uploadFilesAndInfoPicture", request, imagesToMultipartBodyParts("files", fileList));
    }

}
