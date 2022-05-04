package com.lina.baselibs.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lina.baselibs.utils.ToastUtil;
import com.lina.baselibs.view.DialogGoing;

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    protected Activity mActivity;
    protected DialogGoing dialog;

    protected abstract View attachLayout();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        int vis = getWindow().getDecorView().getSystemUiVisibility();
        vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
//        vis |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
//        vis |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        getWindow().getDecorView().setSystemUiVisibility(vis);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(Color.parseColor("#FFFFFF"));

        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        View layoutRes = attachLayout();
        if (layoutRes != null) {
            setContentView(layoutRes);
            initDialog();
            initView(savedInstanceState);
            initData();
            initListener();
        }
        setStatusBarFullTransparent();
        setFitSystemWindow(false);
    }

    private void initDialog() {
        if(dialog == null){
            dialog = new DialogGoing();
        }
    }

    public void show(String str,boolean cancle){
        dialog.setCancelable(cancle);
        dialog.setText(str);
        dialog.show(getSupportFragmentManager(),"");
    }

    public void setStatusBarColor(String color) {
        getWindow().setStatusBarColor(Color.parseColor(color));
    }

    public void setStatusBarColor(int color) {
        getWindow().setStatusBarColor(color);
    }

    public Context getContext() {
        return mContext;
    }

    public Activity getActivity() {
        return mActivity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void startActivity(Class<?> toClass) {
        startActivity(toClass, null);
    }

    public void startActivity(Class<?> toClass, Bundle bundle) {
        Intent intent = new Intent(mContext, toClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    protected void startActivityForResult(Class<?> toClass, int requestCode) {
        startActivityForResult(toClass, null, requestCode);
    }

    public void startActivityForResult(Class<?> toClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mContext, toClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void dismiss(){
        if(dialog != null && dialog.getDialog() !=null && dialog.getDialog().isShowing()){
            dialog.dismiss();
        }
    }
    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    protected void delayFinish(){
        new Thread() {
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);//休眠1秒
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    private View contentViewGroup;

    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN && dialog != null && dialog.getDialog() !=null && dialog.getDialog().isShowing()) {
            dialog.dismiss();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 弹出Toast
     *
     * @param content 弹出的内容
     */
    protected void showShortToast(String content) {
        ToastUtil.showShort(this, content);
    }
}
