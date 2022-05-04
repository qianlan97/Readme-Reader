package com.biz.readme.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.biz.readme.App;
import com.biz.readme.R;
import com.biz.readme.databinding.FragmentHistoryBinding;
import com.biz.readme.databinding.FragmentMineBinding;
import com.biz.readme.entity.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MineFragment extends Fragment {

    public FragmentMineBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMineBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = false)
    public void onStatusEvent(MessageEvent event) {
        Log.d("TAG", "onStatusEventF: "+event.satus);
        if(event.satus.equals("true")){
            binding.viwvV.setBackgroundColor(getContext().getColor(R.color.theme_black));
        }else{
            binding.viwvV.setBackgroundColor(getContext().getColor(R.color.theme_white));
        }
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        if (!App.getInstance().isFinish()){
            binding.viwvV.setBackgroundColor(getContext().getColor(R.color.theme_white));

        }else {
            binding.viwvV.setBackgroundColor(getContext().getColor(R.color.theme_black));

        }
//        binding.view_read_history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
//            }
//        });
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