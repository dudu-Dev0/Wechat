package com.dudu.wechat.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.dudu.wechat.utils.SharedPreferencesUtil;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //由于某些nt厂商 圆屏表用方屏表的设置 导致layout_round无效 故出此下策
        //检测圆方屏并自动选取view加载，需重写setSquareContentView和setRoundContentView两个方法
        String screenType =
                String.valueOf(
                        SharedPreferencesUtil.getData(
                                SharedPreferencesUtil.SCREEN_TYPE, "unknown"));
        Configuration cfg = new Configuration();
        if (screenType.equals("unknown")) {
            if (cfg.isScreenRound()) setRoundContentView();
            else setSquareContentView();
        }
        if (screenType.equals("round")) setRoundContentView();
        if (screenType.equals("square")) setRoundContentView();
    }

    protected void setSquareContentView() {}
    

    protected void setRoundContentView() {}
    
}
