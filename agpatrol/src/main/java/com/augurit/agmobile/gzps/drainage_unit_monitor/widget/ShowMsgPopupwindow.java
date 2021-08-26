package com.augurit.agmobile.gzps.drainage_unit_monitor.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;

public class ShowMsgPopupwindow extends PopupWindow {
    private View view;
    private Context context;
    private String title;
    private String checked;
    private View.OnClickListener submitClickListener;

    public ShowMsgPopupwindow(Context context, String title, String checked, View.OnClickListener submitClickListener){
        this.context = context;
        this.title = title;
        this.checked = checked;
        this.submitClickListener = submitClickListener;
        initView();
    }

    private void initView(){
        view = LayoutInflater.from(context).inflate(R.layout.popwindow_monitor_msg, null);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setContentView(view);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvMonitored = (TextView) view.findViewById(R.id.tv_monitored);
        TextView tvChecked = (TextView) view.findViewById(R.id.tv_checked);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        View line = (View) view.findViewById(R.id.line);
        TextView tvSubmit = (TextView) view.findViewById(R.id.tv_submit);

        view.findViewById(R.id.ll_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvTitle.setText(title);
        Spannable sp = new SpannableString("未监管接驳井数：" + checked);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#F39800")), 8,
                (8 + checked.length()), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvMonitored.setText(sp);
        sp = new SpannableString("资料检查：已检查");
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#22AC38")), 5,
                8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvChecked.setText(sp);

        if("0".equals(checked)){
            tvSubmit.setText("确认提交");
            tvSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(submitClickListener != null){
                        submitClickListener.onClick(v);
                    }
                    dismiss();
                }
            });
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            tvCancel.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
            tvSubmit.setText("确定");
            tvSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }


}
