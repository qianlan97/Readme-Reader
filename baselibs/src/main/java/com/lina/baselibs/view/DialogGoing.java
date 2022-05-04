package com.lina.baselibs.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lina.baselibs.R;
import com.lina.baselibs.databinding.DialogGoingBinding;
import com.lina.baselibs.databinding.LayoutDatePickerBinding;

import static com.lina.baselibs.R.style.DialogDownLoad;

public class DialogGoing extends DialogFragment implements DialogInterface.OnKeyListener{

    private DialogGoingBinding binding;
    private String text = "加载中...";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogDownLoad);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DialogGoingBinding.inflate(inflater);
        binding.tvInfo.setText(text);
        return binding.getRoot();
    }



    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

    public void setText(String text) {
        this.text = text;
    }
}
