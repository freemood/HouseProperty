package com.house.property.http.Api;

import com.house.property.http.retrofit.HttpResponse;

import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Api接口
 */
public interface Api {


    /**
     * GET请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @GET
    Observable<HttpResponse> get(@Url String url, @QueryMap TreeMap<String, Object> request);
    /**
     * put请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @PUT
    Observable<HttpResponse> put(@Url String url, @Body TreeMap<String, Object> request);

    /**
     * POST请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @POST
    Observable<HttpResponse> post(@Url String url, @Body TreeMap<String, Object> request);

    /**
     * patch请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @FormUrlEncoded
    @PATCH
    Observable<HttpResponse> patch(@Url String url, @FieldMap TreeMap<String, Object> request);

    /**
     * DELETE
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @HTTP(method = "DELETE",hasBody = true)
    Observable<HttpResponse> delete(@Url String url, @Body TreeMap<String, Object> request);

    /**
     * POST请求
     *表单
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @POST
    Observable<HttpResponse> postFrom(@Url String url, @Body RequestBody request);

    /**
     * get请求
     *表单
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @GET
    Observable<HttpResponse> getFrom(@Url String url, @Body RequestBody request);

    /**
     * put请求
     *表单
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @PUT
    Observable<HttpResponse> putFrom(@Url String url, @Body RequestBody request);

    @Streaming
    @POST
    @Multipart
    Observable<HttpResponse> upFilesInfo(@Url String fileUrl, @Part MultipartBody.Part file, @PartMap Map<String, RequestBody> data);
}
