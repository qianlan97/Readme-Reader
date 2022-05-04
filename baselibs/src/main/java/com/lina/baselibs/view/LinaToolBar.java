package com.lina.baselibs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lina.baselibs.databinding.LinaToolBarBinding;

public class LinaToolBar extends FrameLayout {

    private Context mContext;
    private ImageView ivLeft;
    private TextView tv;
    private ImageView ivRight;
    LinaToolBarBinding linaToolBarBinding;

    public LinaToolBar(@NonNull Context context) {
        super(context);
    }

    public LinaToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initLayout();
    }

    public LinaToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initLayout();
    }

    private void initLayout() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linaToolBarBinding = LinaToolBarBinding.inflate(inflater,this);
        ivLeft = linaToolBarBinding.yIvLeft;
        ivRight = linaToolBarBinding.yIvRight;
        tv = linaToolBarBinding.yTv;
    }

    public void setIvLeftSrc(int id) {
        ivLeft.setImageResource(id);
    }

    public void setIvLeftClickListener(final LinaToolBar.ClickListener clickListener) {
        ivLeft.setOnClickListener(v -> clickListener.onClick());
    }

    public void setContent(String content) {
        tv.setText(content);
    }

    public void setImageRight(int id) {
        ivRight.setImageResource(id);
    }

    public void setIvRightClickListener(final LinaToolBar.ClickListener clickListener) {
        ivRight.setOnClickListener(v -> clickListener.onClick());
    }

    public void setToolBarBg(int id) {
        linaToolBarBinding.llToolBar.setBackgroundColor(getResources().getColor(id));
    }
    public interface ClickListener {
        void onClick();
    }

    public void setIvLeftGone() {
        ivLeft.setVisibility(INVISIBLE);
    }

    public void setIvRightVisible(int visibale) {
        ivRight.setVisibility(visibale);
    }


}
