package com.lina.baselibs.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lina.baselibs.R;

public class YiNavigationBar extends LinearLayout {

    private Context mContext;
    private RelativeLayout rl1, rl2, rl3;
    private ImageView iv1, iv2, iv3;
    private TextView tv1, tv2, tv3;
    private int[] src1 = new int[2], src2 = new int[2], src3 = new int[2];
    private ClickListener mClickListener;

    public YiNavigationBar(@NonNull Context context) {
        super(context);
    }

    public YiNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initLayout();
        initListener();
    }

    public YiNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initLayout();
        initListener();
    }

    private void initLayout() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.lina_navigation_bar, this, true);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        rl1 = view.findViewById(R.id.rl_1);
        rl2 = view.findViewById(R.id.rl_2);
        rl3 = view.findViewById(R.id.rl_3);

        iv1 = view.findViewById(R.id.iv_1);
        iv2 = view.findViewById(R.id.iv_2);
        iv3 = view.findViewById(R.id.iv_3);

        tv1 = view.findViewById(R.id.tv_1);
        tv2 = view.findViewById(R.id.tv_2);
        tv3 = view.findViewById(R.id.tv_3);

    }

    @SuppressLint("ClickableViewAccessibility") private void initListener() {
        rl1.setOnTouchListener(new OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                rl1();
                mClickListener.onClick1();
                return false;
            }
        });
        rl2.setOnTouchListener(new OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                rl2();
                mClickListener.onClick2();
                return false;
            }
        });
        rl3.setOnTouchListener(new OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                rl3();
                mClickListener.onClick3();
                return false;
            }
        });

    }

    public int getFlFragmentId() {
        return R.id.fl_fragment;
    }

    public YiNavigationBar setText(String s1, String s2, String s3) {
        tv1.setText(s1);
        tv2.setText(s2);
        tv3.setText(s3);

        return this;
    }

    public YiNavigationBar setSrc1(int iv, int ivPressed) {
        src1[0] = iv;
        src1[1] = ivPressed;
        return this;
    }

    public YiNavigationBar setSrc2(int iv, int ivPressed) {
        src2[0] = iv;
        src2[1] = ivPressed;
        return this;
    }

    public YiNavigationBar setSrc3(int iv, int ivPressed) {
        src3[0] = iv;
        src3[1] = ivPressed;
        return this;
    }


    public YiNavigationBar setListener(ClickListener onClick) {
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

    }

    private void rl1() {
        rl1.setBackgroundResource(R.drawable.navigation_bar_1_pressed);
        rl2.setBackgroundResource(R.drawable.navigation_bar_2);
        rl3.setBackgroundResource(R.drawable.navigation_bar_3);

        iv1.setImageResource(src1[1]);
        iv2.setImageResource(src2[0]);
        iv3.setImageResource(src3[0]);

        tv1.setTextColor(Color.parseColor("#FFFFFF"));
        tv2.setTextColor(Color.parseColor("#FF6D85"));
        tv3.setTextColor(Color.parseColor("#FF895D"));

    }

    private void rl2() {
        rl1.setBackgroundResource(R.drawable.navigation_bar_1);
        rl2.setBackgroundResource(R.drawable.navigation_bar_2_pressed);
        rl3.setBackgroundResource(R.drawable.navigation_bar_3);

        iv1.setImageResource(src1[0]);
        iv2.setImageResource(src2[1]);
        iv3.setImageResource(src3[0]);

        tv1.setTextColor(Color.parseColor("#FF5450"));
        tv2.setTextColor(Color.parseColor("#FFFFFF"));
        tv3.setTextColor(Color.parseColor("#FF895D"));

    }

    private void rl3() {
        rl1.setBackgroundResource(R.drawable.navigation_bar_1);
        rl2.setBackgroundResource(R.drawable.navigation_bar_2);
        rl3.setBackgroundResource(R.drawable.navigation_bar_3_pressed);

        iv1.setImageResource(src1[0]);
        iv2.setImageResource(src2[0]);
        iv3.setImageResource(src3[1]);

        tv1.setTextColor(Color.parseColor("#FF5450"));
        tv2.setTextColor(Color.parseColor("#FF6D85"));
        tv3.setTextColor(Color.parseColor("#FFFFFF"));

    }


}
