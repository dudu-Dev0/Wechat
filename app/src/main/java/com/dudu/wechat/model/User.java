package com.dudu.wechat.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.dudu.wechat.model.converter.UserListConverter;
import java.util.ArrayList;

@Entity(tableName = "contacts", indices = {@Index(value = {"UserName"},unique = true)})
public class User {

    public long Uni;
    
    @PrimaryKey
    @NonNull
    public String UserName;
    public String NickName;
    public String DisplayName;
    public String HeadImgUrl;
    public int Sex; // 0未设置1男2女
    public String Province;
    public String City;
    public String Signature;
    @TypeConverters({UserListConverter.class})
    public ArrayList<User> MemberList;
    public int MemberCount;
    public int IsOwner; // 自己的群？0不是或非群聊
    public boolean isSession;//是否展示在聊天页
    public String EncryChatRoomId;
    
    public User() {}
}
