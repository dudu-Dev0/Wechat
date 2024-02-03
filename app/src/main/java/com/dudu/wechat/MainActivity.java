package com.dudu.wechat;

import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.dudu.wechat.api.LoginApi;
import com.dudu.wechat.databinding.ActivityMainBinding;
import com.dudu.wechat.utils.JavaScriptUtil;
import com.google.android.material.chip.Chip;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://login.wx.qq.com/").build();
        LoginApi api = retrofit.create(LoginApi.class);
        Call<ResponseBody> call = api.getUuid(System.currentTimeMillis());
        // new Thread(()->{
        call.enqueue(
                new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(
                            Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                               // Log.e("", response.body().string());

                                String uuid =
                                        JavaScriptUtil.getString(
                                                response.body().string(), "window.QRLogin.uuid");
                                Log.e("uuid", uuid);
                                runOnUiThread(
                                        () -> {
                                            ((Chip) findViewById(R.id.chip)).setText(uuid);
                                        });
                            } catch (Exception err) {

                            }
                            // 处理获取到的用户数据
                        } else {
                            // 处理请求失败的情况
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // 处理请求失败的情况
                        Log.e("", t.toString());
                    }
                });
        // });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
