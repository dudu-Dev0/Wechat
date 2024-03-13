package com.dudu.wechat.model;
import com.dudu.wechat.utils.SharedPreferencesUtil;
import java.util.Random;

public class BaseRequest {
    public long Uin = (Long)SharedPreferencesUtil.getData(SharedPreferencesUtil.UIN,0L);
    public String Sid = (String)SharedPreferencesUtil.getData(SharedPreferencesUtil.SID,"");
    public String Skey = (String)SharedPreferencesUtil.getData(SharedPreferencesUtil.SKEY,"");
    public String DeviceID= "e" + Math.abs((new Random()).nextLong() % 100000000000000L);
    public BaseRequest(){;
    }
}
