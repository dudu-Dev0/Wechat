package com.dudu.wechat.api;

import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Url;

public interface EmptyApi {
    // 自定义url的请求
    @GET
    Call<ResponseBody> get(@Url String url, @HeaderMap Map<String, String> headers);

    @GET
    Call<ResponseBody> get(@Url String url);
}
