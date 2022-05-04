package com.lina.baselibs.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class LinaCircularProgressbar extends View {

    private int mDuration = 100;
    private int mProgress = 0;

    private Paint mPaint = new Paint();
    private RectF mRectF = new RectF();

    private int mBackgroundColor = Color.LTGRAY;
    private int mPrimaryColor = Color.parseColor("#FF5450");
    private float mStrokeWidth = 10F;

    /**
     * 进度条改变监听
     */
    public interface OnProgressChangeListener {
        /**
         * 进度改变事件，当进度条进度改变，就会调用该方法
         *
         * @param duration 总进度
         * @param progress 当前进度
         * @param rate     当前进度与总进度的商 即：rate = (float)progress / duration
         */
        void onChange(int duration, int progress, float rate);
    }

    private OnProgressChangeListener mOnChangeListener;

    /**
     * 设置进度条改变监听
     */
    public void setOnProgressChangeListener(OnProgressChangeListener l) {
        mOnChangeListener = l;
    }

    public LinaCircularProgressbar(@NonNull Context context) {
        super(context, null);
    }

    public LinaCircularProgressbar(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public LinaCircularProgressbar(@NonNull Context context, @NonNull AttributeSet attrs, @NonNull int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置进度条的最大值, 该值要 大于 0
     */
    public void setMax(int max) {
        if (max < 0) {
            max = 0;
        }
        mDuration = max;
    }

    /**
     * 得到进度条的最大值
     */
    public int getMax() {
        return mDuration;
    }

    /**
     * 设置进度条的当前的值
     */
    public void setProgress(int progress) {
        if (progress > mDuration) {
            progress = mDuration;
        }
        mProgress = progress;
        if (mOnChangeListener != null) {
            mOnChangeListener.onChange(mDuration, progress, getRateOfProgress());
        }
        invalidate();
    }

    /**
     * 得到进度条当前的值
     */
    public int getProgress() {
        return mProgress;
    }

    /**
     * 设置进度条背景的颜色
     */
    @Override public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    /**
     * 设置进度条进度的颜色
     */
    public void setPrimaryColor(int color) {
        mPrimaryColor = color;
    }

    /**
     * 设置环形的宽度
     */
    public void setCircleWidth(float width) {
        mStrokeWidth = width;

    }

    @Override protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int halfWidth = getWidth() / 2;
        int halfHeight = getHeight() / 2;
        int radius = halfWidth < halfHeight ? halfWidth : halfHeight;
        float halfStrokeWidth = mStrokeWidth / 2;

        // 设置画笔
        mPaint.setColor(mBackgroundColor);
        mPaint.setDither(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
        //设置图形为空心
        mPaint.setStyle(Paint.Style.STROKE);

        // 画背景
        canvas.drawCircle(halfWidth, halfHeight, radius - halfStrokeWidth, mPaint);

        // 画当前进度的圆环
        // 改变画笔颜色
        mPaint.setColor(mPrimaryColor);
        mRectF.top = halfHeight - radius + halfStrokeWidth;
        mRectF.bottom = halfHeight + radius - halfStrokeWidth;
        mRectF.left = halfWidth - radius + halfStrokeWidth;
        mRectF.right = halfWidth + radius - halfStrokeWidth;
        canvas.drawArc(mRectF, -90, getRateOfProgress() * 360, false, mPaint);
        canvas.save();
    }

    /**
     * 得到当前的进度的比率
     */
    private float getRateOfProgress() {
        return (float) mProgress / mDuration;
    }

}
