package com.lina.baselibs.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lina.baselibs.R;
import com.lina.baselibs.databinding.LinaNavigationBarBinding;

public class LinaNavigationBar extends LinearLayout {

    private Context mContext;
    private int[] src1 = new int[2], src2 = new int[2], src3 = new int[2], src4 = new int[2], src5 = new int[2];
    private ClickListener mClickListener;
    private LinaNavigationBarBinding linaNavigationBarBinding;

    public LinaNavigationBar(@NonNull Context context) {
        super(context);
    }

    public LinaNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initLayout();
        initListener();
    }

    public LinaNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initLayout();
        initListener();
    }

    private void initLayout() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linaNavigationBarBinding = LinaNavigationBarBinding.inflate(inflater,this);
        linaNavigationBarBinding.getRoot().setBackgroundColor(Color.parseColor("#FFFFFF"));
        ((LinearLayout)(linaNavigationBarBinding.getRoot())).setOrientation(LinearLayout.VERTICAL);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        linaNavigationBarBinding.rl1.setOnTouchListener((v, event) -> {
            rl1();
            mClickListener.onClick1();
            return false;
        });
        linaNavigationBarBinding.rl2.setOnTouchListener((v, event) -> {
            rl2();
            mClickListener.onClick2();
            return false;
        });
        linaNavigationBarBinding.rl3.setOnTouchListener((v, event) -> {
            rl3();
            mClickListener.onClick3();
            return false;
        });
        linaNavigationBarBinding.rl4.setOnTouchListener((v, event) -> {
            rl4();
            mClickListener.onClick4();
            return false;
        });
        linaNavigationBarBinding.rl5.setOnTouchListener((v, event) -> {
            rl5();
            mClickListener.onClick5();
            return false;
        });
    }

    public int getFlFragmentId() {
        return R.id.fl_fragment;
    }

    public LinaNavigationBar setText(String s1, String s2, String s3, String s4, String s5) {
        linaNavigationBarBinding.tv1.setText(s1);
        linaNavigationBarBinding.tv2.setText(s2);
        linaNavigationBarBinding.tv3.setText(s3);
        linaNavigationBarBinding.tv4.setText(s4);
        linaNavigationBarBinding.tv5.setText(s5);
        return this;
    }

    public LinaNavigationBar setSrc1(int iv, int ivPressed) {
        src1[0] = iv;
        src1[1] = ivPressed;
        return this;
    }

    public LinaNavigationBar setSrc2(int iv, int ivPressed) {
        src2[0] = iv;
        src2[1] = ivPressed;
        return this;
    }

    public LinaNavigationBar setSrc3(int iv, int ivPressed) {
        src3[0] = iv;
        src3[1] = ivPressed;
        return this;
    }

    public LinaNavigationBar setSrc4(int iv, int ivPressed) {
        src4[0] = iv;
        src4[1] = ivPressed;
        return this;
    }

    public LinaNavigationBar setSrc5(int iv, int ivPressed) {
        src5[0] = iv;
        src5[1] = ivPressed;
        return this;
    }

    public void seekTo(int position) {
        switch (position) {
            case 0:
                rl1();
                break;
            case 1:
                rl2();
                break;
            case 2:
                rl3();
                break;
            case 3:
                rl4();
                break;
            case 4:
                rl5();
                break;
            default:
                break;
        }
    }

    public LinaNavigationBar setListener(ClickListener onClick) {
        this.mClickListener = onClick;
        return this;
    }

    public void initView() {
        rl1();
    }

    public interface ClickListener {
        void onClick1();

        void onClick2();

        void onClick3();

        void onClick4();

        void onClick5();
    }

    private void rl1() {
        linaNavigationBarBinding.iv1.setImageResource(src1[1]);
        linaNavigationBarBinding.iv2.setImageResource(src2[0]);
        linaNavigationBarBinding.iv3.setImageResource(src3[0]);
        linaNavigationBarBinding.iv4.setImageResource(src4[0]);
        linaNavigationBarBinding.iv5.setImageResource(src5[0]);

        linaNavigationBarBinding.tv1.setTextColor(Color.parseColor("#ff349af9"));
        linaNavigationBarBinding.tv2.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv3.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv4.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv5.setTextColor(Color.parseColor("#ff7f7f7f"));

        linaNavigationBarBinding.tv1.setTextSize(12);
        linaNavigationBarBinding.tv2.setTextSize(10);
        linaNavigationBarBinding.tv3.setTextSize(10);
        linaNavigationBarBinding.tv4.setTextSize(10);
        linaNavigationBarBinding.tv5.setTextSize(10);
    }

    private void rl2() {
        linaNavigationBarBinding.iv1.setImageResource(src1[0]);
        linaNavigationBarBinding.iv2.setImageResource(src2[1]);
        linaNavigationBarBinding.iv3.setImageResource(src3[0]);
        linaNavigationBarBinding.iv4.setImageResource(src4[0]);
        linaNavigationBarBinding.iv5.setImageResource(src5[0]);

        linaNavigationBarBinding.tv1.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv2.setTextColor(Color.parseColor("#ff349af9"));
        linaNavigationBarBinding.tv3.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv4.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv5.setTextColor(Color.parseColor("#ff7f7f7f"));

        linaNavigationBarBinding.tv1.setTextSize(10);
        linaNavigationBarBinding.tv2.setTextSize(12);
        linaNavigationBarBinding.tv3.setTextSize(10);
        linaNavigationBarBinding.tv4.setTextSize(10);
        linaNavigationBarBinding.tv5.setTextSize(10);
    }

    private void rl3() {
        linaNavigationBarBinding.iv1.setImageResource(src1[0]);
        linaNavigationBarBinding.iv2.setImageResource(src2[0]);
        linaNavigationBarBinding.iv3.setImageResource(src3[1]);
        linaNavigationBarBinding.iv4.setImageResource(src4[0]);
        linaNavigationBarBinding.iv5.setImageResource(src5[0]);

        linaNavigationBarBinding.tv1.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv2.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv3.setTextColor(Color.parseColor("#ff349af9"));
        linaNavigationBarBinding.tv4.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv5.setTextColor(Color.parseColor("#ff7f7f7f"));

        linaNavigationBarBinding.tv1.setTextSize(10);
        linaNavigationBarBinding.tv2.setTextSize(10);
        linaNavigationBarBinding.tv3.setTextSize(12);
        linaNavigationBarBinding.tv4.setTextSize(10);
        linaNavigationBarBinding.tv5.setTextSize(10);
    }

    private void rl4() {
        linaNavigationBarBinding.iv1.setImageResource(src1[0]);
        linaNavigationBarBinding.iv2.setImageResource(src2[0]);
        linaNavigationBarBinding.iv3.setImageResource(src3[0]);
        linaNavigationBarBinding.iv4.setImageResource(src4[1]);
        linaNavigationBarBinding.iv5.setImageResource(src5[0]);

        linaNavigationBarBinding.tv1.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv2.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv3.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv4.setTextColor(Color.parseColor("#ff349af9"));
        linaNavigationBarBinding.tv5.setTextColor(Color.parseColor("#ff7f7f7f"));

        linaNavigationBarBinding.tv1.setTextSize(10);
        linaNavigationBarBinding.tv2.setTextSize(10);
        linaNavigationBarBinding.tv3.setTextSize(10);
        linaNavigationBarBinding.tv4.setTextSize(12);
        linaNavigationBarBinding.tv5.setTextSize(10);

    }
    private void rl5() {
        linaNavigationBarBinding.iv1.setImageResource(src1[0]);
        linaNavigationBarBinding.iv2.setImageResource(src2[0]);
        linaNavigationBarBinding.iv3.setImageResource(src3[0]);
        linaNavigationBarBinding.iv4.setImageResource(src4[0]);
        linaNavigationBarBinding.iv5.setImageResource(src5[1]);

        linaNavigationBarBinding.tv1.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv2.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv3.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv4.setTextColor(Color.parseColor("#ff7f7f7f"));
        linaNavigationBarBinding.tv5.setTextColor(Color.parseColor("#ff349af9"));

        linaNavigationBarBinding.tv1.setTextSize(10);
        linaNavigationBarBinding.tv2.setTextSize(10);
        linaNavigationBarBinding.tv3.setTextSize(10);
        linaNavigationBarBinding.tv4.setTextSize(10);
        linaNavigationBarBinding.tv5.setTextSize(12);

    }
}
