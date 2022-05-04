package com.lina.baselibs.view.indicater;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class DropViewPage extends ViewPager implements Touchable {

    private boolean touchable = true;

    public DropViewPage(@NonNull Context context) {
        super(context);
    }

    public DropViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (touchable) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (touchable)
            return super.onTouchEvent(ev);
        else
            return false;
    }

}
