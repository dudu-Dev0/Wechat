package com.dudu.wechat.api;
import com.dudu.wechat.model.request.InitClientRequest;
import com.dudu.wechat.model.request.StatusNotifyRequest;
import com.dudu.wechat.model.response.GetContactsResponse;
import com.dudu.wechat.model.response.InitResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ContactApi {
    
    @POST("cgi-bin/mmwebwx-bin/webwxinit?lang=zh_CN")
    Call<InitResponse> initClient(@Query("pass_ticket")String passTicket,@Body InitClientRequest body);
    
    @POST("cgi-bin/mmwebwx-bin/webwxstatusnotify")
    Call<ResponseBody> statusNotify(@Query("pass_ticket")String passTicket,@Body StatusNotifyRequest body);
    
    @GET("cgi-bin/mmwebwx-bin/webwxgetcontact?seq=0")
    Call<GetContactsResponse> getContacts(@Query("pass_ticket")String passTicket,@Query("skey")String skey);
}
