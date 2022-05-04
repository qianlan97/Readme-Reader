package com.biz.readme.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.biz.readme.App;
import com.biz.readme.R;
import com.biz.readme.databinding.FragmentHistoryBinding;
import com.biz.readme.databinding.FragmentSecondBinding;
import com.biz.readme.entity.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SecondFragment extends Fragment {

    public FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = false)
    public void onStatusEvent(MessageEvent event) {
        Log.d("TAG", "onStatusEvent: "+event.satus);
        if(event.satus.equals("true")){
            binding.viewV.setBackgroundColor(getContext().getColor(R.color.theme_black));
        }else{
            binding.viewV.setBackgroundColor(getContext().getColor(R.color.theme_white));
        }
    }
 private boolean isNight = false;
 private boolean isNotification = false;
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        binding.viewReadHistory.setOnClickListener(view1 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_toHistoryFragment));
         isNight = App.getInstance().isFinish();
        if (!isNight){
            binding.ivMode.setBackground(getResources().getDrawable(R.mipmap.icon_select_enable));
            binding.viewV.setBackgroundColor(getContext().getColor(R.color.theme_white));

        }else {
            binding.ivMode.setBackground(getResources().getDrawable(R.mipmap.icon_select_able));
            binding.viewV.setBackgroundColor(getContext().getColor(R.color.theme_black));

        }
        binding.ivMode.setOnClickListener(v -> {
            if (!isNight){
                isNight = true;
                App.getInstance().setFinish(true);
                EventBus.getDefault().post(new MessageEvent("true"));
                binding.ivMode.setBackground(getResources().getDrawable(R.mipmap.icon_select_able));
            }else {
                isNight  =false ;
                EventBus.getDefault().post(new MessageEvent("false"));
                App.getInstance().setFinish(false);
                binding.ivMode.setBackground(getResources().getDrawable(R.mipmap.icon_select_enable));

            }
        });
        binding.ivNotification.setOnClickListener(v -> {
            if (isNotification){
                isNotification = false;
                binding.ivNotification.setBackground(getResources().getDrawable(R.mipmap.icon_select_enable));
            }else {
                isNotification  =true ;
                binding.ivNotification.setBackground(getResources().getDrawable(R.mipmap.icon_select_able));

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}