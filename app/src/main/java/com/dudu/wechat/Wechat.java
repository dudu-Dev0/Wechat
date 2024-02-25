package com.dudu.wechat;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import com.dudu.wechat.utils.SharedPreferencesUtil;
public class Wechat extends Application {
    private static Context context;
    
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SharedPreferencesUtil.getInstance(context,"config");
    }
 
    public static Context getContext() {
        return context;
    }
    
    /**
     * 重写attachBaseContext方法，用于调整应用内dpi
     * 应用内dpi调整来自BiliClient
     * @param old The origin context.
     */
    public static Context getFitDisplayContext(Context old){
        Context newContext = old;
        
        float density = context.getResources().getDisplayMetrics().widthPixels/Constants.REFERENCE_DPI; //获取放大dpi倍数
        
        try{
            //DisplayMetrics displayMetrics = old.getResources().getDisplayMetrics();
            Configuration configuration = old.getResources().getConfiguration();
            configuration.densityDpi = (int)(/*displayMetrics.densityDpi*/320 * density);    //锁死dpi为320，防止设备默认dpi不同导致界面出问题
            newContext = old.createConfigurationContext(configuration);
        }catch (Exception e){
            Toast.makeText(newContext, "调整缩放失败, 请联系开发者", Toast.LENGTH_SHORT).show();
            Log.e("调整dpi", "不支持application");
        }
        return newContext;
    }
}
