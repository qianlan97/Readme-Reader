package com.biz.readme.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.biz.readme.MainActivity;
import com.biz.readme.R;
import com.biz.readme.databinding.ActivityMainBinding;
import com.biz.readme.databinding.ActivityWelcomeBinding;
import com.lina.baselibs.ui.BaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WelcomeActivity extends BaseActivity {

    private ActivityWelcomeBinding binding;
    private void startMainActivity(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2000);//设置动画播放时长500毫秒
        binding.viewWelcom.startAnimation(alphaAnimation);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setFillAfter(true);
        //设置动画监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            //动画结束
            @Override
            public void onAnimationEnd(Animation animation) {
                //页面的跳转
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);


                startActivity(intent);
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected View attachLayout() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ActivityWelcomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        startMainActivity();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
