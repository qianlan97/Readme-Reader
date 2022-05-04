package com.lina.baselibs.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lina.baselibs.view.DialogGoing;

public abstract class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();
    protected View mRootView;
    protected Context mContext;
    protected DialogGoing dialog;

    protected abstract View attachLayout();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = attachLayout();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDialog();
        initView();
        initListener();
    }
    private void initDialog() {
        if(null == dialog){
            dialog = new DialogGoing();
        }

    }
    public void show(String str,boolean cancle){
//        if(dialog.isAdded()){
//            this.beginTransaction().remove(mCountryChooseDialog).commit();
//        }
        dialog.setCancelable(cancle);
        dialog.setText(str);
        dialog.show(getFragmentManager(),"");
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mContext = null;
        if (mRootView != null && mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
            mRootView = null;
        }
    }

    protected void startActivity(Class<?> toClass) {
        startActivity(toClass, null);
    }

    protected void startActivity(Class<?> toClass, Bundle bundle) {
        Intent intent = new Intent(mContext, toClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startActivityForResult(Class<?> toClass, int requestCode) {
        startActivityForResult(toClass, null, requestCode);
    }

    protected void startActivityForResult(Class<?> toClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mContext, toClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void dismiss(){
        if(dialog != null && dialog.isVisible()){
            dialog.dismiss();
        }
    }
}
