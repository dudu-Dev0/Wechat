package com.dudu.wechat;

import android.util.Log;
import android.widget.TextView;
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

        ((TextView)findViewById(R.id.chip)).setText(getIntent().getStringExtra("data"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
