package com.dudu.wechat.api;
import com.dudu.wechat.model.response.InitResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ContactApi {
    
    @POST("cgi-bin/mmwebwx-bin/webwxinit?lang=zh_CN")
    Call<InitResponse> initClient(@Query("pass_ticket")String passTicket,@Body InitResponse body);
    
}
