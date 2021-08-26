package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.TakePhotoTableItem;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;

import java.util.List;

/**
 * @author: liangsh
 * @createTime: 2021/5/7
 */
public class ProblemTypeView extends LinearLayout {

    private TextView tv_problem_type;
//    private SwitchCompat switch_check;
//    private RadioGroup rg_check;
    private RadioButton rb_no;
    private RadioButton rb_yes;
    private TakePhotoItemProblem photo_item;

    private boolean isNeedPhoto = true;
    private int max = 9; //图片最多选择数量
    private boolean checked = false;

    public ProblemTypeView(Context context) {
        this(context, null);
    }

    public ProblemTypeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        LayoutInflater.from(context).inflate(R.layout.item_riser_pipe_problem_type, this);
        tv_problem_type = findViewById(R.id.tv_problem_type);
//        rg_check = findViewById(R.id.rg_check);
        rb_no = findViewById(R.id.rb_no);
        rb_yes = findViewById(R.id.rb_yes);
        photo_item = findViewById(R.id.photo_item);

        /*rg_check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isNeedPhoto) {
                    if (checkedId == R.id.rb_yes && rb_yes.isChecked()) {
                        checked = true;
                        photo_item.setVisibility(VISIBLE);
                    } else {
                        checked = false;
                        photo_item.setVisibility(GONE);
                    }
                }
            }
        });*/
        rb_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checked = true;
                    photo_item.setVisibility(VISIBLE);
                    rb_no.setChecked(false);
                } else {
                    checked = false;
                    photo_item.setVisibility(GONE);
                }
            }
        });
        rb_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checked = false;
                    photo_item.setVisibility(GONE);
                    rb_yes.setChecked(false);
                }
            }
        });
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProblemTypeView);
            String textName = a.getString(R.styleable.ProblemTypeView_ptv_title);
            int color = a.getColor(R.styleable.ProblemTypeView_ptv_title_color,
                    Color.parseColor("#444444"));
            isNeedPhoto = a.getBoolean(R.styleable.ProblemTypeView_ptv_need_photo, true);
            max = a.getInt(R.styleable.ProblemTypeView_ptv_max_photo, 9);
            if (!TextUtils.isEmpty(textName)) {
                tv_problem_type.setText(textName);
            }
            a.recycle();
            setTextColor(color);
        }
        if (isNeedPhoto && checked) {
            photo_item.setVisibility(VISIBLE);
        } else {
            photo_item.setVisibility(GONE);
        }
        photo_item.setPhotoNumShow(true, max);
    }

    public void setText(String text) {
        tv_problem_type.setText(text);
    }

    public void setTextColor(int color) {
        tv_problem_type.setTextColor(color);
        photo_item.setTitleColor(color);
    }

    /**
     * 是否选中了“是”
     * @return
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * 有没有选择“是”或“否”中的任一个
     * @return
     */
    public boolean isSelected(){
        return rb_no.isChecked() || rb_yes.isChecked();
    }

    public void setChecked(boolean checked) {
        if(checked){
            rb_yes.setChecked(true);
            this.checked = true;
        } else {
            rb_no.setChecked(true);
            this.checked = false;
        }
    }

    public boolean isNeedPhoto() {
        return isNeedPhoto;
    }

    public void setNeedPhoto(boolean needPhoto) {
        isNeedPhoto = needPhoto;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        photo_item.setPhotoNumShow(true, max);
    }

    public void setEnable(boolean enable) {
        rb_yes.setEnabled(enable);
        rb_no.setEnabled(enable);
        if (enable) {
            photo_item.setEditable();
        } else {
            photo_item.setReadOnly();
            photo_item.setAddPhotoEnable(false);
        }
    }

    public List<Photo> getPhotos() {
        return photo_item.getSelectedPhotos();
    }

    public void setPhotos(List<Photo> photos) {
        photo_item.setSelectedPhotos(photos);
    }

    public List<Photo> getThumbnailPhotos() {
        return photo_item.getThumbnailPhotos();
    }

    public void clear() {
        rb_yes.setChecked(false);
        rb_no.setChecked(false);
        photo_item.clear();
        photo_item.setVisibility(GONE);
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        photo_item.onActivityResult(requestCode, resultCode, data);
    }

}
