package com.augurit.agmobile.gzpssb.jbjpsdy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;

/**
 * @PROJECT GZPSH
 * @USER Augurit
 * @CREATE 2021/3/26 11:26
 */
public class JbjPsdySewerageUserListHeaderItemView extends LinearLayout {

    private TextView tvTitle;
    private TextView tvValue;

    private int value = 0;

    public JbjPsdySewerageUserListHeaderItemView(Context context) {
        super(context);
        init(context);
    }

    public JbjPsdySewerageUserListHeaderItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JbjPsdySewerageUserListHeaderItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // ==============================自定义方法==============================

    public void setData(String title, int value) {
        setTitle(title);
        setValue(value);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setValue(int value) {
        this.value = value;
        tvValue.setText(String.valueOf(value));
    }

    public int getValue() {
        return value;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.drainage_view_jbj_psdy_sewerage_user_list_header_item, this);
        initView();
    }

    private void initView() {
        tvTitle = findViewById(R.id.title);
        tvValue = findViewById(R.id.value);
    }
}
