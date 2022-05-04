package com.biz.readme.ui.txt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.biz.readme.App;
import com.biz.readme.R;
import com.biz.readme.databinding.ActivityPdfReadBinding;
import com.biz.readme.databinding.ActivityTxtReadBinding;
import com.biz.readme.utils.ScreenUtil;
import com.biz.readme.utils.StatusBarUtil;
import com.biz.readme.widget.PageView;
import com.biz.readme.widget.RealPageView;
import com.lina.baselibs.ui.BaseActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TxtReadActivity extends BaseActivity  {

    private ActivityTxtReadBinding binding;

    // 当前小说阅读进度（本地 txt 用）
    private float mTxtNovelProgress;
    // 小说内容（本地 txt 用）
    private String mNovelContent;
    // 小说进度（本地 txt 用）
    private String mNovelProgress = "";

    // 以下内容通过 Intent 传入
    private String mNovelUrl;   // 小说 url，本地小说为 filePath
    private String mName;   // 小说名

    // 从 sp 中读取
    private float mTextSize;    // 字体大小
    private float mRowSpace;    // 行距
    private int mTheme;         // 阅读主题
    private float mBrightness;  // 屏幕亮度，为 -1 时表示系统亮度
    private boolean mIsNightMode=true;           // 是否为夜间模式
    private int mTurnType=0;      // 翻页模式：0 为正常，1 为仿真

    private float mMinTextSize = 36f;
    private float mMaxTextSize = 76f;
    private float mMinRowSpace = 0f;
    private float mMaxRowSpace = 48f;
    private TextView mNovelProgressTv;
    private TextView mStateTv;


    private SeekBar mNovelProcessSb;
    private int mType = 1;


    private TextView mNovelTitleTv;
    @Override
    protected View attachLayout() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ActivityTxtReadBinding.inflate(inflater);
        return binding.getRoot();
    }
    private RealPageView mPageView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPageView = findViewById(R.id.pv_read_page_view);
        mNovelTitleTv = findViewById(R.id.tv_read_novel_title);
        mNovelProgressTv = findViewById(R.id.tv_read_novel_progress);
        mStateTv = findViewById(R.id.tv_read_state);

        mNovelProcessSb = findViewById(R.id.sb_read_novel_progress);
        mPageView.setPageViewListener(new PageView.PageViewListener() {
            @Override
            public void updateProgress(String progress) {
                mNovelProgress = progress;
                mNovelProgressTv.setText(progress);
            }

            @Override
            public void next() {
                if (mType == 1) {
                    showShortToast("已经到最后了");
                }
            }

            @Override
            public void pre() {
                  if (mType == 1){
                    showShortToast("已经到最前了");
                }
            }

            @Override
            public void nextPage() {
                if (mType == 1) {
                    updateChapterProgress();
                }
            }

            @Override
            public void prePage() {
                if (mType == 1) {
                    updateChapterProgress();
                }
            }

            @Override
            public void showOrHideSettingBar() {
            }
        });
        mPageView.post(() -> mPageView.updateBitmap());
    }

    @Override
    protected void initData() {
        mNovelUrl =getIntent().getStringExtra("filePath");
        Log.d(TAG, "initData: "+mNovelUrl);
        mName = getIntent().getStringExtra("title");
        loadTxt(mNovelUrl);
    }
    public void loadTxt(final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(filePath);
                BufferedReader br = null;
                StringBuilder builder = null;
                String error = "";
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    builder = new StringBuilder();
                    String str;
                    while ((str = br.readLine()) != null) {
                        builder.append(str);
                        builder.append("\n");
                    }
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "e1 = " + e.getMessage());
                    e.printStackTrace();
                    error = "该小说已从本地删除";
                } catch (UnsupportedEncodingException e) {
                    Log.d(TAG, "e2 = " + e.getMessage());
                    e.printStackTrace();
                    error = e.getMessage();
                } catch (IOException e) {
                    Log.d(TAG, "e3 = " + e.getMessage());
                    e.printStackTrace();
                    error = e.getMessage();
                } finally {
                    try {
                        if (br != null) {
                            br.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                final String finalError = error;
                final String text =  builder == null? "" : builder.toString();
                Log.d(TAG, "run: "+text);
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (finalError.equals("")) {
                        mNovelContent = text;
                        mStateTv.setVisibility(View.GONE);
                        mPageView.initDrawText(text, mPosition);
                        mNovelTitleTv.setText(mName);
                        mNovelProgress = "";
                        updateChapterProgress();
                    } else {
                        mStateTv.setText("该文件已从本地删除");
                    }
                });
            }
        }).start();
        if (App.getInstance().isFinish()){
            mTheme = 2;
        }else {
            mTheme = 0;

        }

        updateWithTheme();
    }
    @Override
    protected void initListener() {
  binding.ivBookTop.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          if(mIsNightMode){
//              nightMode();
              mIsNightMode = false;

              mTheme = 3;
              updateWithTheme();
          }else{
              mIsNightMode = true;
              mTheme = 4;
              updateWithTheme();
//              dayMode();
          }
      }
  });
    }

    private int mPosition=1;  // 文本开始读取位置
    private void updateChapterProgress() {
        int progress = 0;
        if (mType == 1) {    // 本地 txt
            if (mNovelProgress.equals("")) {
                if (mNovelContent.length() != 0) {
                    progress = (int) (100 * (float)mPosition / (mNovelContent.length() - 1));
                }
            } else {
                try {
                    progress = (int) Float.parseFloat(
                            mNovelProgress.substring(0, mNovelProgress.length()-1));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        mNovelProcessSb.setProgress(progress);
    }




    /**
     * 根据主题更新阅读界面
     */
    private void updateWithTheme() {
        if (mIsNightMode) {
            // 退出夜间模式
//            mDayAndNightModeIv.setImageResource(R.mipmap.read_night);
//            mDayAndNightModeTv.setText(getResources().getString(R.string.read_night_mode));
            mIsNightMode = false;
        }
        int bgColor = getResources().getColor(R.color.read_theme_0_bg);
        int textColor = getResources().getColor(R.color.read_theme_0_text);
        int backBgColor = getResources().getColor(R.color.read_theme_0_back_bg);
        int backTextColor = getResources().getColor(R.color.read_theme_0_back_text);
        switch (mTheme) {
            case 0:
//                mTheme0.setSelected(true);
                bgColor = getResources().getColor(R.color.read_theme_0_bg);
                textColor = getResources().getColor(R.color.read_theme_0_text);
                backBgColor = getResources().getColor(R.color.read_theme_0_back_bg);
                backTextColor = getResources().getColor(R.color.read_theme_0_back_text);
                break;
            case 1:
//                mTheme1.setSelected(true);

                bgColor = getResources().getColor(R.color.read_theme_1_bg);
                textColor = getResources().getColor(R.color.read_theme_1_text);
                backBgColor = getResources().getColor(R.color.read_theme_1_back_bg);
                backTextColor = getResources().getColor(R.color.read_theme_1_back_text);
                break;
            case 2:
//                mTheme2.setSelected(true);
                bgColor = getResources().getColor(R.color.theme_black);
                textColor = getResources().getColor(R.color.white);
                backBgColor = getResources().getColor(R.color.theme_black);
                backTextColor = getResources().getColor(R.color.white);
                break;
            case 3:
//                mTheme3.setSelected(true);
                bgColor = getResources().getColor(R.color.read_theme_3_bg);
                textColor = getResources().getColor(R.color.read_theme_3_text);
                backBgColor = getResources().getColor(R.color.read_theme_3_back_bg);
                backTextColor = getResources().getColor(R.color.read_theme_3_back_text);
                break;
            case 4:
//                mTheme4.setSelected(true);
                bgColor = getResources().getColor(R.color.read_theme_4_bg);
                textColor = getResources().getColor(R.color.read_theme_4_text);
                backBgColor = getResources().getColor(R.color.read_theme_4_back_bg);
                backTextColor = getResources().getColor(R.color.read_theme_4_back_text);
                break;
        }
        // 设置相关颜色
        mNovelTitleTv.setTextColor(textColor);
        mNovelProgressTv.setTextColor(textColor);
        mStateTv.setTextColor(textColor);
        mPageView.setTextColor(textColor);
        mPageView.setBgColor(bgColor);
        mPageView.setBackTextColor(backTextColor);
        mPageView.setBackBgColor(backBgColor);
        mPageView.updateBitmap();
//        if (PageView.IS_TEST) {
            mPageView.setBackgroundColor(bgColor);
            mPageView.invalidate();
//        }
    }
}