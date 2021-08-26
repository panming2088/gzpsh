package com.augurit.agmobile.gzps.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.patrolcore.common.util.MaxLengthInputFilter;
import com.augurit.am.cmpt.widget.searchview.util.adapter.TextWatcherAdapter;

import java.io.UnsupportedEncodingException;

/**
 * 表单文本项
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.common.widget
 * @createTime 创建时间 ：17/11/7
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/11/7
 * @modifyMemo 修改备注：
 */

public class TextItemTableItem extends RelativeLayout {

    private TextView tv_left;

    public EditText getEt_right() {
        return et_right;
    }

    private EditText et_right;
    private View tv_requiredTag;
    private LinearLayout ll_left;

    public TextItemTableItem(Context context) {
        this(context,null);
    }

    public TextItemTableItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(getLayoutId(), this);
        tv_left = (TextView) view.findViewById(R.id.tv_1);
        tv_requiredTag = view.findViewById(R.id.tv_requiredTag);
        ll_left=(LinearLayout)view.findViewById(R.id.ll_tv1);
        et_right = (EditText) view.findViewById(R.id.et_1);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextItemTableItem);
        String textName = a.getString(R.styleable.TextItemTableItem_textViewName);
        String hint = a.getString(R.styleable.TextItemTableItem_editTextHint);
        int maxLength = a.getInt(R.styleable.TextItemTableItem_editTextMaxLength, 50);

        tv_left.setText(textName);
        et_right.setHint(hint);

        if(maxLength != -1){
            setMaxLength(maxLength);
        }

        a.recycle();
    }

    protected int getLayoutId(){
        return R.layout.view_text_item;
    }

    public void setTextViewName(String textViewName){
        tv_left.setText(textViewName);
    }

    public void setText(String text) {
        et_right.setText(text);
    }

    public void setMaxLength(final int maxLength){
        et_right.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public String getText() {
        return et_right.getText().toString();
    }


    public String getTextViewName(){

        return tv_left.getText().toString();
    }
    public void setReadOnly(){
        et_right.setEnabled(false);
    }

    public void setEditable(boolean editable){
        et_right.setEnabled(editable);
    }

    public void setOnEditTextClickListener(View.OnClickListener onClickListener){
        et_right.setOnClickListener(onClickListener);
    }

    public void setEditTextColor(int color){
        et_right.setTextColor(color);
    }

    public void setRequireTag(){
        tv_requiredTag.setVisibility(VISIBLE);
    }

    /**
     *设置输入方式为数字
     */
    public void setInputTypeAboutNum(){
        et_right.setKeyListener(new
                DigitsKeyListener(false,true));
    }
    /**
     * 设置宽度
     */
    public void setWidth(){

        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) ll_left.getLayoutParams();
        layoutParams.width=LayoutParams.WRAP_CONTENT;
        ll_left.setLayoutParams(layoutParams);
    }

    public void setTextViewName(Spanned spanned) {
        tv_left.setText(spanned);
    }
}
