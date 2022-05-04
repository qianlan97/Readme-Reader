package com.lina.baselibs.view;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
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
import com.lina.baselibs.databinding.LinaEditTextForeBinding;
import com.lina.baselibs.utils.SolveEditTextScrollClash;

public class LinaEditTextFore extends FrameLayout {


    private Context mContext;
    private CallBack mCallBack;
    LinaEditTextForeBinding binding;
    private String type = "";

    public LinaEditTextFore(@NonNull Context context) {
        super(context, null);
    }

    public LinaEditTextFore(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;
        initLayout();
    }

    public LinaEditTextFore(@NonNull Context context, @NonNull AttributeSet attrs, @NonNull int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initLayout();
    }

    private void initLayout() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = LinaEditTextForeBinding.inflate(inflater, this);
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
        binding.et.setOnTouchListener(new SolveEditTextScrollClash(binding.et));
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
        binding.et.addTextChangedListener(new TextWatcher() {
            char kongge = ' ';
            //改变之前text长度
            int beforeTextLength = 0;
            //改变之前的文字
            private CharSequence beforeChar;
            //改变之后text长度
            int onTextLength = 0;
            //是否改变空格或光标
            boolean isChanged = false;
            // 记录光标的位置
            int location = 0;
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            //已有空格数量
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = binding.et.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == kongge) {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }
                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19|| index == 24|| index == 29|| index == 34|| index == 39||
                                index == 44 || index == 49 || index == 54|| index == 59)) {
                            buffer.insert(index, kongge);
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    binding.et.setText(str);
                    Editable etable = binding.et.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
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

    public void setInputType(int type) {
        binding.et.setInputType(type);
    }

    public void setTransformationMethod(TransformationMethod method) {
        binding.et.setTransformationMethod(method);
    }

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public void setTopVISIBLE() {
        binding.llTop.setVisibility(VISIBLE);
    }

    public void setImgClickListener(ImgClickListener imgClickListener) {
        this.imgClickListener = imgClickListener;
    }
    public void setEditTextTop() {
        binding.et.setGravity(Gravity.TOP);
    }
    public interface ImgClickListener {
        void onClick(EditText editText);
    }

    private ImgClickListener imgClickListener;

    public interface CallBack {
        void hasContent(String content);

        void noContent();
    }

    public void setFileType(String fileType){
        this.type = fileType;
    }

}
