package com.dudu.wechat.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import java.lang.System;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginApi {
    @GET("jslogin?appid=wx782c26e4c19acffb&redirect_uri=https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?mod=desktop&fun=new")
    Call<ResponseBody> getUuid(@Query("_")long timeStamp);
}
