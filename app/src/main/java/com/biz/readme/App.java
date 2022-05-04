package com.biz.readme;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.biz.readme.utils.Constant;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lina.baselibs.utils.FileUtils;

public class App extends Application {



    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        _context = this;
        context = getApplicationContext();

    }
    private static App _context;



    public static App getInstance() {
        return _context;
    }
    public static Context getContext() {
        return context;
    }
    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    private boolean isFinish = false;
}
