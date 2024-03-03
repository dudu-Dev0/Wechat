package com.dudu.wechat.model.response;
import com.dudu.wechat.model.BaseResponse;
import com.dudu.wechat.model.User;
import com.dudu.wechat.synckey.model.SyncKey;
import java.util.ArrayList;

public class InitResponse{
    public BaseResponse BaseResponse;
    public int Count;
    public ArrayList<User> ContactList;
    public SyncKey SyncKey;
    public User User;
    public String SKey;
    public long ClientVersion;
}

