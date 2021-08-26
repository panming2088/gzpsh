package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.patrolcore.common.util.MaxLengthInputFilter;
import com.augurit.am.cmpt.widget.searchview.util.adapter.TextWatcherAdapter;

import java.io.UnsupportedEncodingException;

/**
 * @author: liangsh
 * @createTime: 2021/5/7
 */
public class TextFieldItemProblem extends TextFieldTableItem {

    public TextFieldItemProblem(Context context) {
        this(context, null);
    }

    public TextFieldItemProblem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_text_field_item_problem;
    }

    public void setMaxLength(final int maxLength) {
        tv_size.setVisibility(VISIBLE);

        //tv_size.setText("0/"+ maxLength);
        if (TextUtils.isEmpty(et_right.getText().toString())) {
            tv_size.setText("0/" + maxLength);
        } else {
            tv_size.setText(et_right.getText().toString().length() + "/" + maxLength);
        }

        et_right.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        et_right.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                String inputText = s.toString();
                if (TextUtils.isEmpty(inputText)) {
                    tv_size.setText("0/" + maxLength);
                    return;
                }
                tv_size.setText(inputText.length() + "/" + maxLength);
            }
        });
    }
}
