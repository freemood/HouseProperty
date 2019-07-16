package com.house.property.model.task.upload;

import com.house.property.http.retrofit.HttpResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 数据请求服务
 */
public interface ApiService {
    @Streaming
    @POST
    @Multipart
    Call<HttpResponse> upFilesInfo(@Url String fileUrl, @PartMap Map<String, RequestBody> data, @Part MultipartBody.Part[] parts);

}
