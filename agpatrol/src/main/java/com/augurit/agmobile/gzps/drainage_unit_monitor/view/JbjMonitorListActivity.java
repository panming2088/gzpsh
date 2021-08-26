package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.OnRefreshEvent;
import com.augurit.agmobile.gzps.common.constant.GzpsConstant;
import com.augurit.agmobile.gzps.common.util.BeanUtil;
import com.augurit.agmobile.gzps.drainage_unit_monitor.adapter.JbjMonitorListAdapter;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjJgListBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorArg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorInfoBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.JbjMonitorService;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.UploadFacilitySumEvent;
import com.augurit.agmobile.gzpssb.jbjpsdy.view.MyJbjMonitorListFragment;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.PshEventDetailActivity1;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventlist.PSHEventListActivity;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author: liangsh
 * @createTime: 2021/5/8
 */
public class JbjMonitorListActivity extends AppCompatActivity {

    private ProgressLinearLayout pb_loading;
    private XRecyclerView rv_component_list;
    private JbjMonitorListAdapter adapter;

    private int page = 1;

    private JbjMonitorArg args;

    public static void start(Context context, JbjMonitorArg args){
        Intent intent = new Intent(context, JbjMonitorListActivity.class);
        intent.putExtra("data", args);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jbj_monitor_list);
        args = (JbjMonitorArg) getIntent().getSerializableExtra("data");
        EventBus.getDefault().register(this);
        rv_component_list = findViewById(R.id.rv_component_list);
        pb_loading = (ProgressLinearLayout) findViewById(R.id.pb_loading);
        rv_component_list.setPullRefreshEnabled(true);
        rv_component_list.setLoadingMoreEnabled(true);
        rv_component_list.setLayoutManager(new LinearLayoutManager(this));
        boolean isRain = args.jbjType.contains("雨水");
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("监管列表");
        adapter = new JbjMonitorListAdapter(isRain, args.subtype);
        rv_component_list.setAdapter(adapter);
        View ll_add = findViewById(R.id.ll_add);
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JbjMonitorActivity.start(JbjMonitorListActivity.this, args);
            }
        });

        adapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<JbjJgListBean>() {
            @Override
            public void onItemClick(View view, int position, JbjJgListBean selectedData) {
                getJgDetail(selectedData.getJgId());
            }

            @Override
            public void onItemLongClick(View view, int position, JbjJgListBean selectedData) {

            }
        });

        rv_component_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadDatas(false);
            }

            @Override
            public void onLoadMore() {
                page++;
                loadDatas(false);
            }
        });
        loadDatas(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rv_component_list != null) {
            rv_component_list.setEnabled(true);
        }
    }

    private void getJgDetail(final String jgId){
        final ProgressDialog pd = ProgressDialog.show(this, "提示", "正在获取详情");

        new JbjMonitorService(this).getJbjJgDetail(jgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        pd.dismiss();
                        ToastUtil.shortToast(JbjMonitorListActivity.this, "获取监管详情失败");
                    }

                    @Override
                    public void onNext(String s) {
                        pd.dismiss();
                        Result3<String> reslut = JsonUtil.getObject(s, new TypeToken<Result3<String>>(){}.getType());
                        if(reslut.getCode() == 201) {
                            ToastUtil.shortToast(JbjMonitorListActivity.this, "该记录无监测、问题数据");
                            return;
                        } else if(reslut.getCode() != 200){
                            ToastUtil.shortToast(JbjMonitorListActivity.this, reslut.getMessage());
                            return;
                        }
                        JbjMonitorArg args = (JbjMonitorArg) BeanUtil.copy(JbjMonitorListActivity.this.args, new TypeToken<JbjMonitorArg>(){}.getType());
                        args.jbjMonitorInfoBean = JsonUtil.getObject(s, JbjMonitorInfoBean.class);
                        args.readOnly = true;
                        if(args.jbjMonitorInfoBean.getWtData() == null){
                            JbjMonitorActivity.start(JbjMonitorListActivity.this, args);
                        } else {
//                        JbjMonitorActivity.start(JbjMonitorListActivity.this, args);
                            PSHEventListItem eventListItem = new PSHEventListItem();
                            eventListItem.setId(Integer.parseInt(args.jbjMonitorInfoBean.getWtData().id));
                            eventListItem.setState(args.jbjMonitorInfoBean.getWtData().state);
                            eventListItem.setPshmc(args.jbjMonitorInfoBean.getWtData().getPshmc());
                            Intent intent = new Intent(JbjMonitorListActivity.this, PshEventDetailActivity1.class);
                            intent.putExtra("fromPSHAffair", false);
                            intent.putExtra("fromMyUpload", false);
                            intent.putExtra("isDialy", false);
                            intent.putExtra("eventList", eventListItem);
                            int eventPState = 0;
                            if("1".equals(args.jbjMonitorInfoBean.getWtData().sfTj)){
                                eventPState = GzpsConstant.EVENT_P_STATE_HANDLING;
                            }
                            intent.putExtra("eventPState", eventPState);
                            String state = "sb";
                            if(eventPState == GzpsConstant.EVENT_P_STATE_HANDLING){
                                state = "dcl";
                            }
                            intent.putExtra("state", state);
                            intent.putExtra("psdyJgId", jgId);
                            startActivity(intent);
                        }
                    }
                });
    }

    public void showLoadedEmpty() {
        pb_loading.showError("", "暂无数据", "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDatas(true);
            }
        });
    }

    public void showLoadedError(String errorReason) {
        pb_loading.showError("获取数据失败", errorReason, "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDatas(true);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(OnRefreshEvent event){
        page = 1;
        loadDatas(false);
    }

    public void loadDatas(boolean ifShowPb) {
        if (ifShowPb)
            pb_loading.showLoading();
        new JbjMonitorService(this).getJbjJgList(page, 15, args.usid, args.jbjObjectId)
                .map(new Func1<String, Result3<List<JbjJgListBean>>>() {
                    @Override
                    public Result3<List<JbjJgListBean>> call(String s) {
                        return JsonUtil.getObject(s, new TypeToken<Result3<List<JbjJgListBean>>>(){}.getType());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result3<List<JbjJgListBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (page == 1) {
                            showLoadedError(e.getLocalizedMessage());
                        }
                        ToastUtil.iconShortToast(JbjMonitorListActivity.this, R.mipmap.ic_alert_yellow, "获取数据失败，请稍后重试");
                    }

                    @Override
                    public void onNext(Result3<List<JbjJgListBean>> listResult2) {
                        if (listResult2.getCode() == 200) {
                            if (ListUtil.isEmpty(listResult2.getData())) {
                                if(page == 1) {
                                    showLoadedEmpty();
                                }
                                return;
                            }
                            if (ListUtil.isEmpty(listResult2.getData()) && page > 1) {
                                rv_component_list.setNoMore(true);
                                return;
                            }
                            pb_loading.showContent();
                            rv_component_list.loadMoreComplete();
                            rv_component_list.refreshComplete();
                            if (page > 1) {
                                adapter.addData(listResult2.getData());
                                adapter.notifyDataSetChanged();
                            } else if (page == 1) {
                                adapter.notifyDataSetChanged(listResult2.getData());
                                rv_component_list.scrollToPosition(0);
                            }
                        } else {
//                            CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
//                                    BaseInfoManager.getUserName(mContext) + "原因是：" + s.getMessage()));
                            ToastUtil.shortToast(JbjMonitorListActivity.this, listResult2.getMessage());
                        }
                    }
                });
    }

    public void onDestroy(){
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
