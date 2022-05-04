package com.lina.baselibs.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.lina.baselibs.R;
import com.lina.baselibs.databinding.LinaEditTextBinding;
import com.lina.baselibs.utils.SolveEditTextScrollClash;

public class LinaEditText extends FrameLayout {


    private Context mContext;
    private CallBack mCallBack;
    LinaEditTextBinding binding;
    private String type = "";

    public LinaEditText(@NonNull Context context) {
        super(context, null);
    }

    public LinaEditText(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;
        initLayout();
    }

    public LinaEditText(@NonNull Context context, @NonNull AttributeSet attrs, @NonNull int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initLayout();
    }

    private void initLayout() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = LinaEditTextBinding.inflate(inflater, this);
        binding.et.setBackgroundResource(R.drawable.lina_edittext_unfocus_bg);
        binding.et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(getContent())) {
                    if (mCallBack != null) {
                        mCallBack.noContent();
                    }
                } else {
                    if (mCallBack != null) {
                        mCallBack.hasContent(getContent());
                    }
                }
            }
        });
        binding.et.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText _v = (EditText) v;
                if (!hasFocus) {
                    if (binding.ivPull.getVisibility() == VISIBLE) {
                        binding.ivPull.setImageResource(R.drawable.icon_pull_unselect);
                    }
                    _v.setBackgroundResource(R.drawable.lina_edittext_unfocus_bg);
                } else {
                    if (binding.ivPull.getVisibility() == VISIBLE) {
                        binding.ivPull.setImageResource(R.drawable.icon_pull_selected);
                    }
                    _v.setBackgroundResource(R.drawable.lina_edittext_focus_bg);
                }

            }
        });
        binding.et.setOnTouchListener(new SolveEditTextScrollClash(binding.et));
        binding.et.setOnClickListener(v -> {
            if (binding.ivFile.getVisibility() == View.VISIBLE) {
                fileClickListener.onClick(binding.et);
            } else if (imgClickListener != null) {
                imgClickListener.onClick(binding.et);
            }
        });
    }

    public void setText(String text) {
        binding.tv.setText(text);
    }

    public void setHint(String hint) {
        binding.et.setHint(hint);
    }

    public String getContent() {
        return binding.et.getText().toString().trim();
    }

    public void setContent(String content) {
        binding.et.setText(content);
    }

    public void setError(String content) {
        binding.et.setError(content);
    }

    public void setEtEnabled(boolean enabled) {
        binding.et.setEnabled(enabled);
    }

    public void setEtFocusable(boolean enabled) {
        binding.et.setFocusable(enabled);
    }

    @Override
    public void setClickable(boolean enabled) {
        binding.et.setClickable(enabled);
    }

    @Override
    public void setFocusableInTouchMode(boolean enabled) {
        binding.et.setFocusableInTouchMode(enabled);
    }

    public void setInputType(int type) {
        binding.et.setInputType(type);
    }

    public void setTransformationMethod(TransformationMethod method) {
        binding.et.setTransformationMethod(method);
    }

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public void setPullVisible() {
        binding.et.setPadding(20, 0, 0, 30);
        binding.ivPull.setVisibility(VISIBLE);
    }

    public void setSelectVisible() {
        binding.et.setPadding(20, 0, 0, 30);
        binding.ivSelect.setVisibility(VISIBLE);
    }

    public void setFileVisible() {
        binding.et.setPadding(20, 0, 0, 30);
        binding.ivFile.setVisibility(VISIBLE);
    }

    public void setImgClickListener(ImgClickListener imgClickListener) {
        this.imgClickListener = imgClickListener;
    }

    public void setEditTextTop() {
        binding.et.setGravity(Gravity.TOP);
    }

    public void setFileClickListener(FileClickListener fileClickListener) {
        this.fileClickListener = fileClickListener;
    }

    public interface ImgClickListener {
        void onClick(EditText editText);
    }

    private ImgClickListener imgClickListener;

    public interface CallBack {
        void hasContent(String content);

        void noContent();
    }

    public void setFileType(String fileType) {
        this.type = fileType;
    }
    public interface FileClickListener {
        void onClick(EditText editText);
    }

    private FileClickListener fileClickListener;
}
