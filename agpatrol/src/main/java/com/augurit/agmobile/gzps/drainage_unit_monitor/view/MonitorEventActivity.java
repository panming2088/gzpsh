package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.util.ListUtil;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.RefreshLGEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.SaveCheckEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorArg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.JbjMonitorService;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemBean;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemFeatureOrAddr;
import com.augurit.agmobile.gzpssb.uploadevent.model.SelectFinishEvent;
import com.augurit.agmobile.mapengine.map.geometry.LatLng;
import com.augurit.agmobile.patrolcore.baiduapi.BaiduApiService;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.agmobile.patrolcore.selectlocation.model.BaiduGeocodeResult;
import com.augurit.am.cmpt.loc.util.LocationUtil;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.AMFileOpUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author: liangsh
 * @createTime: 2021/5/19
 */
public class MonitorEventActivity extends BaseActivity {

    private MonitorEventFragment fragment;

    public static void start(Context context, JbjMonitorArg args) {
        Intent intent = new Intent(context, MonitorEventActivity.class);
        intent.putExtra("data", args);
        context.startActivity(intent);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_event_activity);

        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        JbjMonitorArg args = (JbjMonitorArg) getIntent().getSerializableExtra("data");
        TextView tv_title = findViewById(R.id.tv_title);

        String title = "";
        int checkType = args.checkType;
        if(checkType == 1){
            title = "地面检查";
        } else if(checkType == 2){
            title = "开盖检查";
        } else if (checkType == 3){
            title = "立管检查";
        }
        if(args.readOnly && args.jbjMonitorInfoBean != null){
            title = "新增" + title;
        }
        tv_title.setText(title);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = MonitorEventFragment.getInstance(args);
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
