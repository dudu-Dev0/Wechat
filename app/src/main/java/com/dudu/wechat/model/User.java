package com.dudu.wechat.model;
import java.util.ArrayList;

public class User {
    public long Uni;
    public String UserName;
    public String NickName;
    public String DisplayName;
    public String HeadImgUrl;
    public int Sex;//0未设置1男2女
    public String Province;
    public String City;
    public String Signature;
    public ArrayList<User> MemberList;
    public int MemberCount;
    public int IsOwner;//自己的群？0不是或非群聊
    public User(){}
}
