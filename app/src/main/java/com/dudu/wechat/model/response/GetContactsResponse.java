package com.dudu.wechat.model.response;
import com.dudu.wechat.model.BaseResponse;
import com.dudu.wechat.model.User;
import java.util.ArrayList;

public class GetContactsResponse {
    public BaseResponse BaseResponse;
    public int MemberCount;
    public ArrayList<User> MemberList;
}
