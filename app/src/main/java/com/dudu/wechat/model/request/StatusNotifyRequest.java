package com.dudu.wechat.model.request;
import com.dudu.wechat.Wechat;
import com.dudu.wechat.model.BaseRequest;
import com.dudu.wechat.utils.NetworkUtil;
import com.dudu.wechat.utils.SharedPreferencesUtil;

public class StatusNotifyRequest {
    public BaseRequest BaseRequest = new BaseRequest();
    public int Code = 3;
    public String FromUserName = (String)SharedPreferencesUtil.getData(SharedPreferencesUtil.USER_NAME,"");
    public String ToUserName = (String)SharedPreferencesUtil.getData(SharedPreferencesUtil.USER_NAME,"");
    public long ClientMsgId = System.currentTimeMillis();
}
