package com.biz.readme.ui.pdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import com.biz.readme.databinding.ActivityMainBinding;
import com.biz.readme.databinding.ActivityPdfReadBinding;
import com.lina.baselibs.ui.BaseActivity;
import com.lina.baselibs.view.LinaToolBar;

import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;

public class PdfReadActivity extends BaseActivity {

    private ActivityPdfReadBinding binding;
    private  PDFPagerAdapter adapter;

    @Override
    protected View attachLayout() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ActivityPdfReadBinding.inflate(inflater);
        return binding.getRoot();
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
//        binding.toolbar.setContent(getIntent().getStringExtra("title"));
//        binding.toolbar.setIvRightVisible(View.INVISIBLE);
        adapter = new PDFPagerAdapter(mContext, getIntent().getStringExtra("filePath"));
        binding.pdfViewPager.setAdapter(adapter);
        binding.tvCurrent.setText("1");
        binding.tvNum.setText("/" + adapter.getCount());
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
//        binding.toolbar.setIvLeftClickListener(new LinaToolBar.ClickListener() {
//            @Override
//            public void onClick() {
//                Intent intent = new Intent();
//                intent.putExtra("name",getIntent().getStringExtra("title"));
//                intent.putExtra("path",getIntent().getStringExtra("filePath"));
//                intent.putExtra("index",Integer.parseInt(binding.tvCurrent.getText().toString()));
//                intent.putExtra("pages",adapter.getCount());
//                setResult(Activity.RESULT_OK, intent);
//                finish();
//            }
//        });
        binding.pdfViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.tvCurrent.setText(Integer.toString(position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.tvPre.setOnClickListener(v -> {
            binding.pdfViewPager.setCurrentItem(binding.pdfViewPager.getCurrentItem() - 1);
        });

        binding.tvNext.setOnClickListener(v -> {
            binding.pdfViewPager.setCurrentItem(binding.pdfViewPager.getCurrentItem() + 1);
        });
    }

}