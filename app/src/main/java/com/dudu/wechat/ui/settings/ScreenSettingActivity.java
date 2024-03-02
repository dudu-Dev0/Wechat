package com.dudu.wechat.ui.settings;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.dudu.wechat.R;
import com.dudu.wechat.ui.BaseActivity;
import com.dudu.wechat.utils.SharedPreferencesUtil;

public class ScreenSettingActivity extends BaseActivity {
    FrameLayout roundCard, squareCard;
    RadioButton roundRadio, squareRadio;
    ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout("activity_screen_setting");

        init();
    }

    protected void init() {
        roundCard = (FrameLayout) findViewById(R.id.round_screen_card);
        roundRadio = (RadioButton) findViewById(R.id.round_screen_radio);
        squareCard = (FrameLayout) findViewById(R.id.square_screen_card);
        squareRadio = (RadioButton) findViewById(R.id.square_screen_radio);
        btn = (ImageButton) findViewById(R.id.done_btn);

        String screenTypeSP =
                String.valueOf(
                        SharedPreferencesUtil.getData(
                                SharedPreferencesUtil.SCREEN_TYPE, "unknown"));
        Configuration cfg = new Configuration();
        if (screenTypeSP.equals("unknown")) {
            if (cfg.isScreenRound()) {
                roundRadio.setChecked(true);
                roundCard.setBackgroundResource(R.drawable.chip_background_medium_toggled);
            } else {
                squareRadio.setChecked(true);
                squareCard.setBackgroundResource(R.drawable.chip_background_medium_toggled);
            }
        }
        if (screenTypeSP.equals("round")) {
            roundRadio.setChecked(true);
            roundCard.setBackgroundResource(R.drawable.chip_background_medium_toggled);
        }
        if (screenTypeSP.equals("square")) {
            squareRadio.setChecked(true);
            squareCard.setBackgroundResource(R.drawable.chip_background_medium_toggled);
        }
        roundCard.setOnClickListener(
                view -> {
                    roundRadio.setChecked(true);
                    squareRadio.setChecked(false);
                    roundCard.setBackgroundResource(R.drawable.chip_background_medium_toggled);
                    squareCard.setBackgroundResource(R.drawable.chip_background_medium);
                });
        squareCard.setOnClickListener(
                view -> {
                    roundRadio.setChecked(false);
                    squareRadio.setChecked(true);
                    roundCard.setBackgroundResource(R.drawable.chip_background_medium);
                    squareCard.setBackgroundResource(R.drawable.chip_background_medium_toggled);
                });
        btn.setOnClickListener(
                view -> {
                    if (roundRadio.isChecked())
                        SharedPreferencesUtil.putData(SharedPreferencesUtil.SCREEN_TYPE, "round");
                    else SharedPreferencesUtil.putData(SharedPreferencesUtil.SCREEN_TYPE, "square");
                    setContentLayout("activity_screen_setting");
                    init();
                });
    }
}
