package com.biz.readme.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.biz.readme.App;
import com.biz.readme.MainActivity;
import com.biz.readme.R;
import com.biz.readme.databinding.ActivityMainBinding;
import com.biz.readme.databinding.FragmentFirstBinding;
import com.biz.readme.db.BookDataBase;
import com.biz.readme.db.BookHistory;
import com.biz.readme.entity.MessageEvent;
import com.biz.readme.ui.pdf.PdfReadActivity;
import com.biz.readme.ui.txt.TxtReadActivity;
import com.biz.readme.utils.Constant;
import com.biz.readme.utils.FileUtil;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lina.baselibs.adapter.recyclerview.MultiItemTypeAdapter;
import com.lina.baselibs.adapter.recyclerview.RecyclerCommonAdapter;
import com.lina.baselibs.adapter.recyclerview.RecyclerViewHolder;
import com.lina.baselibs.ui.BaseFragment;
import com.lina.baselibs.utils.FileUtils;
import com.lina.baselibs.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class FirstFragment extends BaseFragment {

    private FragmentFirstBinding binding;
    private List<BookHistory> datas = new ArrayList<>();

    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    private ActivityResultLauncher<Intent> intentActivityPdfLauncher;
    private ActivityResultLauncher<Intent> intentActivityTxtLauncher;

    private RecyclerCommonAdapter<BookHistory> adapter;

    @Override
    protected View attachLayout() {
        EventBus.getDefault().register(this);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = FragmentFirstBinding.inflate(inflater);
        return binding.getRoot();
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = false)
    public void onStatusEvent(MessageEvent event) {
        Log.d("TAG", "onStatusEventF: "+event.satus);
        if(event.satus.equals("true")){
            binding.bottomViewDatas.setBackgroundColor(getContext().getColor(R.color.theme_black));
        }else{
            binding.bottomViewDatas.setBackgroundColor(getContext().getColor(R.color.read_theme_0_bg));
        }
    }
    @Override
    protected void initView() {

        initIntent();
        initAdapter();
        if(App.getInstance().isFinish()){
            binding.bottomViewDatas.setBackgroundColor(getContext().getColor(R.color.theme_black));
        }else{
            binding.bottomViewDatas.setBackgroundColor(getContext().getColor(R.color.read_theme_0_bg));
        }
        LinearLayoutManager lMaster = new LinearLayoutManager(mContext);
        lMaster.setOrientation(RecyclerView.VERTICAL);
        binding.rvList.setLayoutManager(lMaster);
        binding.rvList.setAdapter(adapter);
    }

    /**
     * 页面跳转返回处理
     */
    private void initIntent() {
        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //此处是跳转的result回调方法
                        if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                            Uri uri = result.getData().getData();
                            String filePath1;
                            File file = null;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                file = FileUtil.uri2FileQ(getActivity(), uri);
                                filePath1 = file.getPath();
                            } else {
                                String filePath = FileUtils.getChooseFileResultPath(getContext(), uri);

                                filePath1 = filePath;
                                if (filePath1 != null) {
                                    file = new File(filePath1);
                                }
                            }
                            if (file == null || TextUtils.isEmpty(filePath1)) {
                                return;
                            }

                            String fileName1 = file.getName();
                            String suffix = fileName1.substring(fileName1.lastIndexOf(".") + 1);  //
                            if (filePath1.contains("pdf")) {

                                Intent intentSign = new Intent(mContext, PdfReadActivity.class);
                                String fileName = filePath1.substring(filePath1.lastIndexOf("/") + 1, filePath1.length());
                                intentSign.putExtra("filePath", filePath1);
                                intentSign.putExtra("title", fileName);
                                intentActivityPdfLauncher.launch(intentSign);

                            } else if (suffix.contains("txt")) {

                                Intent intentSign = new Intent(mContext, TxtReadActivity.class);
                                intentSign.putExtra("filePath", filePath1);
                                intentSign.putExtra("title", fileName1);
                                intentActivityTxtLauncher.launch(intentSign);

                            } else {
                                ToastUtil.showShort(mContext, "文件选择失败，请稍后再试？");
                            }
                        }
                    }
                });
        intentActivityPdfLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //此处是跳转的PDF阅读的回调方法
                        if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
//                            BookHistory bookHistory = new BookHistory();
//                            bookHistory.name = result.getData().getStringExtra("name");
//                            bookHistory.path = result.getData().getStringExtra("path");
//                            bookHistory.index = result.getData().getIntExtra("index",0);
//                            bookHistory.pages = result.getData().getIntExtra("pages",0);
//                            bookHistory.readNum = result.getData().getIntExtra("pages",0);
//                            BookDataBase.getInstance(mContext).bookDao().insertAll(bookHistory);
//                            List<BookHistory> list = BookDataBase.getInstance(mContext).bookDao().getAll();
//                            if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
//
//                            }
                        }
                    }
                });
        intentActivityTxtLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //此处是跳转的TXT阅读的回调方法
                        if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {

                        }
                    }
                });
    }

    private void initAdapter() {
        adapter = new RecyclerCommonAdapter<BookHistory>(mContext, R.layout.item_book, new ArrayList<>()) {
            @Override
            protected void convert(RecyclerViewHolder holder, BookHistory bookHistory, int position) {
                holder.setText(R.id.tv_book_name,bookHistory.name);
                if(position > 0){
                    holder.getView(R.id.tv_1).setVisibility(View.GONE);
                    holder.getView(R.id.tv_2).setVisibility(View.GONE);
                }else{
                    holder.getView(R.id.tv_1).setVisibility(View.VISIBLE);
                    holder.getView(R.id.tv_2).setVisibility(View.VISIBLE);
                }
            }
        };
    }

    @Override
    protected void initData() {
        XXPermissions.with(mContext)
                .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {


                        FileUtils.createPath(Constant.PATH);

                        Vector<String> vector = FileUtil.getFileNames(Constant.PATH);
                        datas.clear();
                        for (int i = 0; i < vector.size(); i++) {
                            String name = vector.get(i);
                            BookHistory bookHistory = new BookHistory();
                            bookHistory.name = name;
                            bookHistory.path = Constant.PATH + vector.get(i);
//                            bookHistory.type = vector.get(i).substring(vector.get(i).lastIndexOf("."),vector.get(i).length());
                            BookDataBase.getInstance(mContext).bookDao().insertAll(bookHistory);
                        }
//                        Collections.sort(datas); //对list排序
//                        if (datas.size() == 0) {
//                            show("加载中...", false);
//                            getWebList();
//                        }
                        datas.addAll(BookDataBase.getInstance(mContext).bookDao().getAll());
                        adapter.setDatas(datas);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        XXPermissions.startPermissionActivity(mContext);
                    }
                });

    }

    @Override
    protected void initListener() {
            binding.ivSelectFile.setOnClickListener(view -> {
                XXPermissions.with(mContext)
                        .permission(Permission.READ_EXTERNAL_STORAGE)
                        .request(new OnPermissionCallback() {

                            @Override
                            public void onGranted(List<String> permissions, boolean all) {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("*/*");//无类型限制
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intentActivityResultLauncher.launch(intent);
                            }

                            @Override
                            public void onDenied(List<String> permissions, boolean never) {
                                XXPermissions.startPermissionActivity(mContext);
                            }
                        });
            });

            binding.ivFiler.setOnClickListener(v -> {
                filerDialogShow(filerLiked,App.getInstance().isFinish());
            });
            binding.ivSort.setOnClickListener(v -> {
                sortDialogShow(sortNum,App.getInstance().isFinish());
            });
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    if(adapter.getDatas().get(position).name.contains("txt") ){

                        Intent intentSign = new Intent(mContext, TxtReadActivity.class);
                        String fileName = adapter.getDatas().get(position).name.substring(
                                adapter.getDatas().get(position).name.lastIndexOf("/") + 1,
                                adapter.getDatas().get(position).name.length());
                        intentSign.putExtra("filePath", adapter.getDatas().get(position).path);
                        intentSign.putExtra("title", fileName);
                        intentActivityTxtLauncher.launch(intentSign);

                    }else if(adapter.getDatas().get(position).name.contains("pdf")){

                        Intent intentSign = new Intent(mContext, PdfReadActivity.class);
                        String fileName = adapter.getDatas().get(position).name.substring(
                                adapter.getDatas().get(position).name.lastIndexOf("/") + 1,
                                adapter.getDatas().get(position).name.length());
                        intentSign.putExtra("filePath", adapter.getDatas().get(position).path);
                        intentSign.putExtra("title", fileName);
                        intentActivityPdfLauncher.launch(intentSign);

                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

    }
    private boolean filerLiked = false;
    Dialog filerDialog;
    private View viewSaved ,viewLiked,viewFiler;
    private ImageView ivSaved ,ivLiked;
    private void filerDialogShow(boolean fileriked,boolean theme) {
        if (filerDialog != null && filerDialog.isShowing()) {
        } else {
            filerDialog = new Dialog(getActivity());
            filerDialog.show();
        }
        Window window = filerDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setContentView(R.layout.filer_dialog);
        filerDialog.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog不消失}
        viewLiked = window.findViewById(R.id.view_liked);
        viewSaved = window.findViewById(R.id.view_saved);
        viewFiler = window.findViewById(R.id.viewFiler);
        ivLiked = window.findViewById(R.id.iv_liked);
        ivSaved = window.findViewById(R.id.iv_saved);
        if(!theme){
            viewFiler.setBackground(getResources().getDrawable(R.drawable.home_dialog_bg));
        }else {
            viewFiler.setBackground(getResources().getDrawable(R.drawable.home_dialog_drak_bg));
        }
        if(fileriked){
            ivLiked.setVisibility(View.VISIBLE);
            ivSaved.setVisibility(View.GONE);
        }else {
            ivLiked.setVisibility(View.GONE);
            ivSaved.setVisibility(View.VISIBLE);
        }

        viewLiked.setOnClickListener(v -> {
            if(!filerLiked){
                filerLiked=true;
                ivLiked.setVisibility(View.VISIBLE);
                ivSaved.setVisibility(View.GONE);
                filerDialog.dismiss();
            }
        });
        viewSaved.setOnClickListener(v -> {
            if(filerLiked){
                filerLiked=false;
                ivLiked.setVisibility(View.GONE);
                ivSaved.setVisibility(View.VISIBLE);
                filerDialog.dismiss();
            }
        });
    }

    private int sortNum = 1;
    Dialog sortDialog;
    private View viewNameA ,viewNameZ,viewTimeN,viewTimeO,viewSort;
    private ImageView ivNameA ,ivNameZ,ivTimeN,ivTimeO;
    private void sortDialogShow(int sort,boolean theme) {
        if (sortDialog != null && sortDialog.isShowing()) {
        } else {
            sortDialog = new Dialog(getActivity());
            sortDialog.show();
        }
        Window window = sortDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setContentView(R.layout.sort_dialog);
        sortDialog.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog不消失}
        viewNameA = window.findViewById(R.id.view_name_a);
        viewNameZ = window.findViewById(R.id.view_name_z);
        viewTimeN = window.findViewById(R.id.view_time_n);
        viewSort = window.findViewById(R.id.viewSort);
        viewTimeO = window.findViewById(R.id.view_time_o);
        ivNameA = window.findViewById(R.id.iv_name_a);
        ivNameZ = window.findViewById(R.id.iv_name_z);
        ivTimeN = window.findViewById(R.id.iv_time_n);
        ivTimeO = window.findViewById(R.id.iv_time_o);
        if(!theme){
            viewSort.setBackground(getResources().getDrawable(R.drawable.home_dialog_bg));
        }else {
            viewSort.setBackground(getResources().getDrawable(R.drawable.home_dialog_drak_bg));
        }
        if(sort==1){
            ivTimeN.setVisibility(View.VISIBLE);
            ivTimeO.setVisibility(View.GONE);
            ivNameZ.setVisibility(View.GONE);
            ivNameA.setVisibility(View.GONE);
        }else if(sort==2){
            ivTimeN.setVisibility(View.GONE);
            ivTimeO.setVisibility(View.VISIBLE);
            ivNameZ.setVisibility(View.GONE);
            ivNameA.setVisibility(View.GONE);
        }else if(sort==3){
            ivTimeN.setVisibility(View.GONE);
            ivTimeO.setVisibility(View.GONE);

            ivNameA.setVisibility(View.VISIBLE);
            ivNameZ.setVisibility(View.GONE);
        }else {
            ivTimeN.setVisibility(View.GONE);
            ivTimeO.setVisibility(View.GONE);
            ivNameA.setVisibility(View.GONE);
            ivNameZ.setVisibility(View.VISIBLE);
        }

        viewTimeN.setOnClickListener(v -> {
            if(sortNum!=1){
                sortNum =1;
                ivTimeN.setVisibility(View.VISIBLE);
                ivTimeO.setVisibility(View.GONE);
                ivNameZ.setVisibility(View.GONE);
                ivNameA.setVisibility(View.GONE);
            }
        });
        viewTimeO.setOnClickListener(v -> {
            if(sortNum!=2){
                sortNum =2;
                ivTimeN.setVisibility(View.GONE);
                ivTimeO.setVisibility(View.VISIBLE);
                ivNameZ.setVisibility(View.GONE);
                ivNameA.setVisibility(View.GONE);
            }
        });
        viewNameA.setOnClickListener(v -> {
            if(sortNum!=3){
                sortNum =3;
                ivTimeN.setVisibility(View.GONE);
                ivTimeO.setVisibility(View.GONE);
                ivNameZ.setVisibility(View.GONE);
                ivNameA.setVisibility(View.VISIBLE);
            }
        });
        viewNameZ.setOnClickListener(v -> {
            if(sortNum!=4){
                sortNum =4;
                ivTimeN.setVisibility(View.GONE);
                ivTimeO.setVisibility(View.GONE);
                ivNameZ.setVisibility(View.VISIBLE);
                ivNameA.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}