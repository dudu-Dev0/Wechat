package com.dudu.wechat.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.wear.widget.drawer.PageIndicatorView;
import com.dudu.wechat.R;
import com.dudu.wechat.ui.BaseActivity;
import com.dudu.wechat.utils.DensityUtil;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends BaseActivity {
    private ViewPager viewPager;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout("activity_home");

        viewPager = (ViewPager)findViewById(R.id.home_pager);
        title = (TextView)findViewById(R.id.title);

        PageIndicatorView indicator = ((PageIndicatorView) findViewById(R.id.home_pager_indicator));
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setPager(viewPager);
        indicator.setDotRadius(DensityUtil.dpToPx(this,5));
        indicator.setDotRadiusSelected(DensityUtil.dpToPx(this,5));
        indicator.setDotColor(Color.GRAY);
        indicator.setDotColorSelected(Color.WHITE);
        indicator.setDotShadowColor(Color.parseColor("#00000000"));
        indicator.setDotFadeOutDuration(350000,TimeUnit.SECONDS);
        
        viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(
                            int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        Log.e("PageChanged", "position:" + position);
                        switch(position){
                            case 0:
                                title.setText("微信");
                                break;
                            default :
                                title.setText("Unknown");
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {}
                });
    }
}
