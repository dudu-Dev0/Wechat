package com.dudu.wechat.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.dudu.wechat.model.converter.UserListConverter;
import java.util.ArrayList;

@Entity(tableName = "contacts")
public class User {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public long Uni;
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

    public User() {}
}
