package com.dudu.wechat.utils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtil {
    
    public static final String LOGIN_BASE_URL = "https://login.wx.qq.com/";
    public static final String MAIN_BASE_URL = "https://wx.qq.com/";
    public static final String FILE_BASE_URL = "https://file.wx.qq.com/";
    
    private static Retrofit retrofit(String baseUrl) {
        return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }
    public static <T>T createLogin(Class<T> t) {
        return retrofit(LOGIN_BASE_URL).create(t);
    }
    public static <T>T createFile(Class<T> t) {
        return retrofit(FILE_BASE_URL).create(t);
    }
    public static <T>T create(Class<T> t) {
        return retrofit(MAIN_BASE_URL).create(t);
    }
    public static <T>T create(Class<T> t,String baseUrl) {
        return retrofit(baseUrl).create(t);
    }
    
}
