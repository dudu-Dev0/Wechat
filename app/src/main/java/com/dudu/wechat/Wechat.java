package com.dudu.wechat;
import android.app.Application;
import android.content.Context;
import com.dudu.wechat.utils.SharedPreferencesUtil;
public class Wechat extends Application {
    private static Context context;
    
    @Override
    public void onCreate() {
        context = getApplicationContext();
        SharedPreferencesUtil.getInstance(context,"config");
    }
 
    public static Context getContext() {
        return context;
    }
}
