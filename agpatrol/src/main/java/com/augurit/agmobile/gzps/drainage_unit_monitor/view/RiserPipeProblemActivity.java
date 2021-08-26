package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorArg;

/**
 * @author: liangsh
 * @createTime: 2021/5/7
 */
public class RiserPipeProblemActivity extends AppCompatActivity {

    private RiserPipeProblemFragment fragment;

    public static void start(Context context, JbjMonitorArg args){
        Intent intent = new Intent(context, RiserPipeProblemActivity.class);
        intent.putExtra("data", args);
        context.startActivity(intent);
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riser_pipe_problem);
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_title = findViewById(R.id.tv_title);
        JbjMonitorArg args = (JbjMonitorArg) getIntent().getSerializableExtra("data");
        if(args.readOnly){
            tv_title.setText("问题详情");
        } else {
            tv_title.setText("立管检查新增");
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = RiserPipeProblemFragment.getInstance(args);
        transaction.add(R.id.ll_container, fragment);
        transaction.commit();
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if(fragment != null){
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
