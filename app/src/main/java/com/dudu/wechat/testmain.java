package com.dudu.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.dudu.wechat.ui.login.QRCodeLoginActivity;

public class testmain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmain);
        
        ((Button)findViewById(R.id.test)).setOnClickListener(view->{
            Intent intent = new Intent(this,QRCodeLoginActivity.class);
            startActivity(intent);
        });
    }
}
