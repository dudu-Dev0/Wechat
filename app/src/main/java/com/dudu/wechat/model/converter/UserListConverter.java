package com.dudu.wechat.model.converter;
import androidx.room.TypeConverter;
import com.dudu.wechat.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class UserListConverter {
    @TypeConverter
    public String userList2Json(ArrayList<User> list) {
        return new Gson().toJson(list);
    }
    @TypeConverter
    public ArrayList<User> json2UserList(String str) {
        return new Gson().fromJson(str,new TypeToken<ArrayList<User>>(){}.getType());
    }
}
