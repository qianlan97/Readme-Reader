package com.biz.readme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.biz.readme.databinding.ActivityMainBinding;
import com.biz.readme.entity.MessageEvent;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lina.baselibs.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private NavController controller;




    @Override
    protected View attachLayout() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ActivityMainBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        controller = Navigation.findNavController(this,R.id.nav_host_fragment_content_main);

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = false)
    public void onStatusEvent(MessageEvent event) {
        Log.d("TAG", "onStatusEventM: "+event.satus);
        if(event.satus.equals("true")){
            binding.etSearch.setBackground(getDrawable(R.drawable.bg_drak_search));
//            binding.viewV.setBackgroundColor(getContext().getColor(R.color.theme_white));
            binding.etSearch.setHintTextColor(getColor(R.color.white));
            binding.etSearch.setTextColor(getColor(R.color.white));
        }else{
            binding.etSearch.setBackground(getDrawable(R.drawable.bg_search));
            binding.etSearch.setHintTextColor(getColor(R.color.theme_black));
            binding.etSearch.setTextColor(getColor(R.color.theme_black));

//            binding.viewV.setBackgroundColor(Color.BLACK);
        }
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    protected void initListener() {
        binding.ivBookClose.setOnClickListener(view -> {
            if (controller.getCurrentDestination().getId() == R.id.FirstFragment){
                return;
            }
            if (controller.getCurrentDestination().getId() == R.id.SecondFragment){
                controller.navigate(R.id.action_SecondFragment_to_FirstFragment);

            }
            if (controller.getCurrentDestination().getId() == R.id.HistoryFragment){
                controller.navigate(R.id.action_h_to_f);

            }
            if (controller.getCurrentDestination().getId() == R.id.MineFragment){
                controller.navigate(R.id.action_m_to_f);
            }
        });

        binding.ivSetting.setOnClickListener(view -> {
            if (controller.getCurrentDestination().getId() == R.id.SecondFragment){
                return;
            }
            if (controller.getCurrentDestination().getId() == R.id.FirstFragment){
                controller.navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
            if (controller.getCurrentDestination().getId() == R.id.HistoryFragment){
                controller.navigate(R.id.action_h_to_s);
            }
            if (controller.getCurrentDestination().getId() == R.id.MineFragment){
                controller.navigate(R.id.action_m_to_s);
            }
        });
        binding.ivBookTop.setOnClickListener(v -> {
            if (controller.getCurrentDestination().getId() == R.id.HistoryFragment){
                return;
            }
            if (controller.getCurrentDestination().getId() == R.id.FirstFragment){
                controller.navigate(R.id.action_F_to_m);
            }
            if (controller.getCurrentDestination().getId() == R.id.SecondFragment){
                controller.navigate(R.id.action_s_t_m);
            }
            if (controller.getCurrentDestination().getId() == R.id.HistoryFragment){
                controller.navigate(R.id.action_h_to_m);
            }
        });
    }

}
