package com.sa.all_cui.mix_core.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by all-cui on 2017/8/11.
 * GET请求--》获取资源
 * POST请求--》传输实体
 * PUT请求--》传输文件 自身不带验证机制，一般不会使用；REST架构设计中会开放这个端口
 * DELETE请求--》删除文件，同PUT方法
 */

public interface RestService {

    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap Map<String, Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    /*
    * retrofit默认的下载模式，是先下载到内存中，等下载完成后统一写入到文件中；这样就会造成一
    * 个问题就是文件过大时造成内存泄漏，导致软件崩溃，解决方法就是加上注解@Streaming一遍下载
    * 一边将下载的内容写入到文件中
    * */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    /*
    * 表单上传
    * */
    @Multipart
    @POST
    Call<String> upLoad(@Url String url, @Part MultipartBody.Part file);

}

