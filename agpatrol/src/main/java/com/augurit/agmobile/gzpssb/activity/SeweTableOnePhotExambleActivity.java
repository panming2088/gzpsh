package com.augurit.agmobile.gzpssb.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;

/**
 * @author 创建人 ：ouyangzhibao
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzpssb.activity
 * @createTime 创建时间 ：2018/4/24
 * @modifyBy 修改人 ：ouyangzhibao
 * @modifyTime 修改时间 ：2018/4/24
 * @modifyMemo 修改备注：
 */
public class SeweTableOnePhotExambleActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewe_tab1_photo);
        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.tv_title)).setText("拍照须知");
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}