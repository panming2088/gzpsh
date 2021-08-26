package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.augurit.agmobile.gzps.R;
import com.augurit.am.fw.utils.TimeUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: liangsh
 * @createTime: 2021/5/10
 */
public class TimePickerItem extends RelativeLayout {

    private TextView tv_left;
    private TextView tv_right;
    private View tv_requiredTag;
    private LinearLayout ll_left;

    private boolean editable = true;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private int mSecond = 0;

    public TimePickerItem(Context context) {
        this(context, null);
    }

    public TimePickerItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(getLayoutId(), this);
        tv_left = (TextView) view.findViewById(R.id.tv_1);
        tv_requiredTag = view.findViewById(R.id.tv_requiredTag);
        ll_left = (LinearLayout) view.findViewById(R.id.ll_tv1);
        tv_right = view.findViewById(R.id.tv_right);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TimePickerItem);
        String title = a.getString(R.styleable.TimePickerItem_tpi_title);
        a.recycle();
        if (!TextUtils.isEmpty(title)) {
            tv_left.setText(title);
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editable){
                    showDatePicker();
                }
            }
        });
    }

    protected int getLayoutId() {
        return R.layout.layout_time_picker_item;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month + 1;
                mDay = dayOfMonth;
                showTimePicker();
            }
        }, year, month, day).show();
    }

    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_DARK, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                Calendar c = Calendar.getInstance();
                c.set(mYear, mMonth, mDay, mHour, mMinute, 0);
                tv_right.setText(TimeUtil.getStringTimeYMDFromDate(calendar.getTime()));
            }
        }, hour, minute, true).show();
    }

    public void setTextViewName(String textViewName) {
        tv_left.setText(textViewName);
    }

    public String getTextViewName() {
        return tv_left.getText().toString();
    }

    public void setText(String text) {
        tv_right.setText(text);
    }

    public String getText() {
        return tv_right.getText().toString();
    }

    public void setReadOnly() {
        editable = false;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setRequireTag() {
        tv_requiredTag.setVisibility(VISIBLE);
    }

    /**
     * 设置宽度
     */
    public void setWidth() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ll_left.getLayoutParams();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        ll_left.setLayoutParams(layoutParams);
    }
}
