package com.dudu.wechat.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.dudu.wechat.R;
import com.dudu.wechat.api.LoginApi;
import com.dudu.wechat.utils.JavaScriptUtil;
import com.dudu.wechat.utils.QRCodeUtil;
import com.google.android.material.card.MaterialCardView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QRCodeLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_login);
        
        refreshQRCode();
        
        ((ImageView)findViewById(R.id.qr_code_view)).setOnClickListener(view->{
            refreshQRCode();
        });
        
    }
    private void refreshQRCode() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://login.wx.qq.com/").build();
        LoginApi api = retrofit.create(LoginApi.class);
        Call<ResponseBody> call = api.getUuid(System.currentTimeMillis());
        new Thread(()->{
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
                                            ((ImageView)findViewById(R.id.qr_code_view)).setImageBitmap(QRCodeUtil.createQRCodeBitmap("https://login.weixin.qq.com/l/"+uuid,100,100));
                                            ((MaterialCardView)findViewById(R.id.qr_code_card)).setVisibility(View.VISIBLE);
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
         }).start();
    }
}
