package com.dudu.wechat.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dudu.wechat.MainActivity;
import com.dudu.wechat.R;
import com.dudu.wechat.api.ContactApi;
import com.dudu.wechat.api.EmptyApi;
import com.dudu.wechat.api.LoginApi;
import com.dudu.wechat.model.User;
import com.dudu.wechat.model.request.InitClientRequest;
import com.dudu.wechat.model.response.InitResponse;
import com.dudu.wechat.ui.BaseActivity;
import com.dudu.wechat.utils.BitmapUtil;
import com.dudu.wechat.utils.DensityUtil;
import com.dudu.wechat.utils.HeaderParser;
import com.dudu.wechat.utils.JavaScriptUtil;
import com.dudu.wechat.utils.NetworkUtil;
import com.dudu.wechat.utils.QRCodeUtil;
import com.dudu.wechat.utils.SharedPreferencesUtil;
import com.google.android.material.card.MaterialCardView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QRCodeLoginActivity extends BaseActivity {
    private ImageView qrView;
    private MaterialCardView qrCard;
    private TextView tipTv;
    private TextView titleTv;
    private ScheduledExecutorService scheduledExecutorService;
    private String uuid = "";
    private boolean isLogin = false;
    private boolean isRequesting = false;
    private Thread mThread;

    // forTest
    String a = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout("activity_qr_code_login");

        qrView = (ImageView) findViewById(R.id.qr_code_view);
        qrCard = (MaterialCardView) findViewById(R.id.qr_code_card);
        tipTv = (TextView) findViewById(R.id.login_tip_tv);
        titleTv = (TextView) findViewById(R.id.title);

        refreshQRCode();
        mThread = new Thread(()->{
            while(!isLogin) {
                if(isLogin) break;
                if (!uuid.equals("")&&!isRequesting) {
                    isRequesting = true;
                    waitForScan();
                }
            }
        });
        mThread.start();

        ((ImageView) findViewById(R.id.qr_code_view))
                .setOnClickListener(
                        view -> {
                            refreshQRCode();
                        });
    }

    private void waitForScan() {
        Call<ResponseBody> call =
                NetworkUtil.createLogin(LoginApi.class, NetworkUtil.LOGIN_BASE_URL)
                        .waitForScan(uuid, System.currentTimeMillis());
        Log.e("WAITING_FOR_SCAN", call.request().url().url().toString());

        call.enqueue(
                new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(
                            Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String responseBody = response.body().string();
                            Log.e("resp", responseBody);
                            isRequesting = false;
                            if (response.isSuccessful()) {
                                runOnUiThread(
                                        () -> {
                                            switch (JavaScriptUtil.getInt(
                                                    responseBody, "window.code")) {
                                                case 200:
                                                    isLogin = true;
                                                    titleTv.setText("正在登录...");
                                                    tipTv.setText("加载数据中");
                                                    Log.e(
                                                            "direct",
                                                            JavaScriptUtil.getString(
                                                                    responseBody,
                                                                    "window.redirect_uri"));
                                                    Call<ResponseBody> loginCall =
                                                            NetworkUtil.create(EmptyApi.class)
                                                                    .get(
                                                                            JavaScriptUtil
                                                                                            .getString(
                                                                                                    responseBody,
                                                                                                    "window.redirect_uri")
                                                                                    + "&fun=new&version=v2&mod=desktop",
                                                                            Map.of());

                                                    loginCall.enqueue(
                                                            new Callback<ResponseBody>() {
                                                                @Override
                                                                public void onResponse(
                                                                        Call<ResponseBody> call,
                                                                        Response<ResponseBody>
                                                                                loginResponse) {
                                                                    Intent intent =
                                                                            new Intent(
                                                                                    QRCodeLoginActivity
                                                                                            .this,
                                                                                    MainActivity
                                                                                            .class);
                                                                    try {
                                                                        String headers =
                                                                                loginResponse
                                                                                        .headers()
                                                                                        .toString();
                                                                        String body =
                                                                                loginResponse
                                                                                        .body()
                                                                                        .string();
                                                                        Log.e("", body);
                                                                        
                                                                        String passTicket = body.substring(body.indexOf("<pass_ticket>")+13,body.indexOf("</pass_ticket>"));
                                                                        String skey = body.substring(body.indexOf("<skey>")+6,body.indexOf("</skey>"));
                                                                        SharedPreferencesUtil.putData(SharedPreferencesUtil.AUTH_TICKET,HeaderParser.get(headers,SharedPreferencesUtil.AUTH_TICKET));
                                                                        SharedPreferencesUtil.putData(SharedPreferencesUtil.LOAD_TIME,HeaderParser.get(headers,SharedPreferencesUtil.LOAD_TIME));
                                                                        SharedPreferencesUtil.putData(SharedPreferencesUtil.UIN,Long.valueOf(HeaderParser.get(headers,SharedPreferencesUtil.UIN)));
                                                                        SharedPreferencesUtil.putData(SharedPreferencesUtil.SID,HeaderParser.get(headers,SharedPreferencesUtil.SID));
                                                                        SharedPreferencesUtil.putData(SharedPreferencesUtil.DATA_TICKET,HeaderParser.get(headers,SharedPreferencesUtil.DATA_TICKET));
                                                                        SharedPreferencesUtil.putData(SharedPreferencesUtil.PASS_TICKET,passTicket);
                                                                        SharedPreferencesUtil.putData(SharedPreferencesUtil.SKEY,skey);
                                                                        //String data = loginResponse.body().string();
                                                                        InitClientRequest reqBody = new InitClientRequest();
                                                                        reqBody.BaseRequest = NetworkUtil.buildBaseRequest();
                                                                        Call<InitResponse> initCall = NetworkUtil.create(ContactApi.class).initClient(passTicket,reqBody);

                                                                        initCall.enqueue(new Callback<InitResponse>() {
                                                                            @Override
                                                                            public void onResponse(Call<InitResponse> call,Response<InitResponse> initResponse) {
                                                                                User self = initResponse.body().User;
                                                                                SharedPreferencesUtil.putData(SharedPreferencesUtil.USER_NAME,self.UserName);
                                                                                SharedPreferencesUtil.putData(SharedPreferencesUtil.NICK_NAME,self.NickName);
                                                                                SharedPreferencesUtil.putData(SharedPreferencesUtil.USER_AVATAR,NetworkUtil.MAIN_BASE_URL+self.HeadImgUrl);
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<InitResponse> call,Throwable t) {
                                                                                t.printStackTrace();
                                                                            }
                                                                        });

                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(
                                                                        Call<ResponseBody> call,
                                                                        Throwable t) {
                                                                    // 处理请求失败的情况
                                                                    Log.e("fail", t.toString());
                                                                    Toast.makeText(
                                                                                    QRCodeLoginActivity
                                                                                            .this,
                                                                                    "登录失败,请重试",
                                                                                    Toast
                                                                                            .LENGTH_LONG)
                                                                            .show();
                                                                }
                                                            });
                                                    break;
                                                case 201:
                                                    tipTv.setText("请在手机上\n确认登录");
                                                    titleTv.setText("扫码完成");
                                                    qrCard.getLayoutParams().height =
                                                            DensityUtil.dip2px(
                                                                    QRCodeLoginActivity.this, 80);
                                                    qrCard.getLayoutParams().width =
                                                            DensityUtil.dip2px(
                                                                    QRCodeLoginActivity.this, 80);
                                                    Log.e(
                                                            "avatar",
                                                            JavaScriptUtil.getBase64Data(
                                                                    responseBody,
                                                                    "window.userAvatar"));
                                                    Glide.with(QRCodeLoginActivity.this)
                                                            .load(
                                                                    BitmapUtil.base64ToBitmap(
                                                                            JavaScriptUtil
                                                                                    .getBase64Data(
                                                                                            responseBody,
                                                                                            "window.userAvatar")
                                                                                    .replace(
                                                                                            "data:img/jpg;base64,",
                                                                                            "")))
                                                            .diskCacheStrategy(
                                                                    DiskCacheStrategy.NONE)
                                                            .apply(
                                                                    RequestOptions
                                                                            .circleCropTransform())
                                                            .into(qrView);
                                                    break;
                                                case 408:
                                                    Toast.makeText(
                                                                    QRCodeLoginActivity.this,
                                                                    "登录超时",
                                                                    Toast.LENGTH_SHORT)
                                                            .show();
                                                    refreshQRCode();
                                                    break;
                                                default:
                                                    break;
                                            }
                                        });

                            } else {
                                runOnUiThread(
                                        () ->
                                                Toast.makeText(
                                                                QRCodeLoginActivity.this,
                                                                "请求失败请稍后重试",
                                                                Toast.LENGTH_SHORT)
                                                        .show());
                            }
                        } catch (Exception err) {
                            err.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // 处理请求失败的情况
                        Log.e("fail", t.toString());
                        isRequesting = false;
                    }
                });
    }

    private void refreshQRCode() {
        tipTv.setText("请使用手机微信扫码登录");
        titleTv.setText("扫码登录");
        qrCard.getLayoutParams().height = DensityUtil.dip2px(QRCodeLoginActivity.this, 120);
        qrCard.getLayoutParams().width = DensityUtil.dip2px(QRCodeLoginActivity.this, 120);
        Call<ResponseBody> call =
                NetworkUtil.createLogin(LoginApi.class, NetworkUtil.LOGIN_BASE_URL)
                        .getUuid(System.currentTimeMillis());
        Log.e("call", call.request().url().url().toString());
        call.enqueue(
                new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(
                            Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String responseBody = response.body().string();
                            if (response.isSuccessful()
                                    & JavaScriptUtil.getInt(responseBody, "window.QRLogin.code")
                                            == 200) {
                                uuid =
                                        JavaScriptUtil.getString(
                                                responseBody, "window.QRLogin.uuid");
                                Log.e("uuid", uuid);
                                runOnUiThread(
                                        () -> {
                                            ((ImageView) findViewById(R.id.qr_code_view))
                                                    .setImageBitmap(
                                                            QRCodeUtil.createQRCodeBitmap(
                                                                    "https://login.weixin.qq.com/l/"
                                                                            + uuid,
                                                                    100,
                                                                    100));
                                            ((MaterialCardView) findViewById(R.id.qr_code_card))
                                                    .setVisibility(View.VISIBLE);
                                        });
                            } else {
                                runOnUiThread(
                                        () ->
                                                Toast.makeText(
                                                                QRCodeLoginActivity.this,
                                                                "请求失败请稍后重试",
                                                                Toast.LENGTH_SHORT)
                                                        .show());
                            }
                        } catch (Exception err) {
                            err.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // 处理请求失败的情况
                        Log.e("", t.toString());
                        runOnUiThread(
                                () ->
                                        Toast.makeText(
                                                        QRCodeLoginActivity.this,
                                                        "请求失败请稍后重试",
                                                        Toast.LENGTH_SHORT)
                                                .show());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
        super.onDestroy();
    }
}
