package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;

/**
 * @author: liangsh
 * @createTime: 2021/5/7
 */
public class TextItemProblem extends TextItemTableItem {

    protected View line;

    public TextItemProblem(Context context) {
        this(context,null);
    }

    public TextItemProblem(Context context, AttributeSet attrs) {
        super(context, attrs);
        line = findViewById(R.id.line);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_text_item_problem;
    }

    public void setLineVisiable(boolean visiable){
        line.setVisibility(visiable ? VISIBLE : GONE);
    }
}
