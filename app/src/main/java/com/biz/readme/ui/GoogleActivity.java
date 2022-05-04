package com.biz.readme.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.biz.readme.R;
import com.biz.readme.databinding.ActivityWelcomeBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lina.baselibs.ui.BaseActivity;



public class GoogleActivity extends BaseActivity {

    private ActivityWelcomeBinding binding;
    private GoogleApiClient mGoogleApiClient;



    private void startMainActivity(){

//        binding.viewWelcom.setBackgroundResource(R.mipmap.icon_google_logo);
//        binding.viewWelcom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

    @Override
    protected View attachLayout() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ActivityWelcomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        startMainActivity();
        initGoogle();
    }

    private void initGoogle(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestId().requestProfile().requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,new GoogleApiClient.OnConnectionFailedListener(){

            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Log.d("ZG","ddddd" + connectionResult.isSuccess());

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

    }





    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
