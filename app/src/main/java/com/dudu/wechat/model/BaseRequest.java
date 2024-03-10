package com.dudu.wechat.model;
import java.util.Random;

public class BaseRequest {
    public long Uin;
    public String Sid;
    public String Skey;
    public String DeviceID= "e" + Math.abs((new Random()).nextLong() % 100000000000000L);
    public BaseRequest(long Uin,String Sid,String Skey){
        this.Uin = Uin;
        this.Sid = Sid;
        this.Skey = Skey;
    }
}
