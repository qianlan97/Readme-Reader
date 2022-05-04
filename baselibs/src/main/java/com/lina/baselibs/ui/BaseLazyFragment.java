package com.lina.baselibs.ui;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

public abstract class BaseLazyFragment extends BaseFragment {


    /**
     * Fragment 处理懒加载，为了防止 setUserVisibleHint 进入多次导致数据重复加载
     */
    protected boolean isUIVisible = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && mRootView != null && !isUIVisible) {
            isUIVisible = true;
            lazyLoad();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && mRootView != null && !isUIVisible) {
            isUIVisible = true;
            lazyLoad();
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isUIVisible = false;
    }

    @UiThread
    protected abstract void lazyLoad();

}
