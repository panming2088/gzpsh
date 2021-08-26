package com.augurit.agmobile.gzpssb.uploadevent.view.eventlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzps.publicaffair.view.eventaffair.EventAffairDetailActivity;
import com.augurit.agmobile.gzps.uploadevent.event.UpdateAfterDeleteEvent;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.uploadfacility.service.CorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.service.UploadFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.util.UploadLayerFieldKeyConstant;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzpssb.journal.service.DialyPatrolService;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.pshpublicaffair.service.PSHAffairService;
import com.augurit.agmobile.gzpssb.uploadevent.adapter.PSHEventListAdapter;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.service.PSHUploadEventService;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.PshEventAffairDetailActivity;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.PshEventDetailActivity1;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.service.LoginService;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.ListUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 已上报
 * Created by liangsh on 2017/11/25.
 */

public class PshMyUploadEventListActivity extends BaseActivity {

    private TextView title_tv;
    private ProgressLinearLayout pb_loading;
    private XRecyclerView mRecyclerView;
    private PSHEventListAdapter adapter;

    private PSHUploadEventService mEventService;

    private int pageNo = 1;
    private final int pageSize = 10;
    private boolean loadMore = true;
    private DialyPatrolService mPatrolService;
    private View fl_loading;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploadevent_list);
        EventBus.getDefault().register(this);
        mEventService = new PSHUploadEventService(this.getApplicationContext());
        initView();
        loadEventList(true);
    }

    private void initView(){
        mRecyclerView = (XRecyclerView) findViewById(R.id.event_list_rv);
        title_tv = (TextView) findViewById(R.id.tv_title);
        fl_loading = findViewById(R.id.fl_loading);
        title_tv.setText("任务列表(已上报)");
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pb_loading = (ProgressLinearLayout) findViewById(R.id.pb_loading);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setPullRefreshEnabled(false);
        adapter = new PSHEventListAdapter(null, 7,this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerItemClickListener<PSHEventListItem>() {
            @Override
            public void onItemClick(View view, int position, PSHEventListItem selectedData) {
                if("psh".equals(selectedData.getSslx())) {
                    getPSHUnitDetail(selectedData.getPshid(), selectedData);
                } else if("jbj".equals(selectedData.getSslx()) || "jhj".equals(selectedData.getSslx())) {
                    getjbjhDetail(selectedData);
                } else if (("dmjc".equals(selectedData.getSslx())
                        || "kgjc".equals(selectedData.getSslx()))
                        && selectedData.getPshid() != null && selectedData.getPshid() != 0) {
                    getjbjhDetail(selectedData);
                }  else {
                    goDetail(selectedData, null, null, null);
                }
//                Intent intent = new Intent(PshMyUploadEventListActivity.this, PSHEventAffairDetailActivity.class);
//                intent.putExtra("eventAffairBean", selectedData);
//                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, PSHEventListItem selectedData) {

            }
        });
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                loadMore = false;
                loadEventList(false);
                mRecyclerView.setNoMore(false);
            }

            @Override
            public void onLoadMore() {
                pageNo++;
                loadMore = true;
                loadEventList(false);
            }
        });
    }

    private void loadEventList(boolean showPb) {
        if(showPb){
            pb_loading.showLoading();
        }

        User user = new LoginService(this.getApplicationContext(), AMDatabase.getInstance()).getUser();
        mEventService.getEventList(pageNo, pageSize, "sb",null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PSHEventListItem>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (loadMore) {
                            mRecyclerView.loadMoreComplete();
                        } else {
                            mRecyclerView.refreshComplete();
                        }
                        if (pageNo == 1){
                            showLoadedError("");
                        }
                    }

                    @Override
                    public void onNext(List<PSHEventListItem> s) {
                        if (ListUtil.isEmpty(s)) {
                            if(pageNo == 1) {
                                pb_loading.showError("", "暂无数据", "刷新", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pageNo = 1;
                                        loadEventList(true);
                                    }
                                });
                            } else {
                                mRecyclerView.loadMoreComplete();
                                mRecyclerView.setNoMore(true);
                            }
                            return;
                        }
                        pb_loading.showContent();
                        if (loadMore) {
                            adapter.loadMore(s);
                            mRecyclerView.loadMoreComplete();
                        } else {
                            adapter.refresh(s);
                            mRecyclerView.refreshComplete();
                        }
                        if(s.size() < pageSize){
                            mRecyclerView.setNoMore(true);
                        }
                    }
                });
    }


    public void showLoadedError(String errorReason) {
        pb_loading.showError("获取数据失败", errorReason, "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEventList(true);
            }
        });
    }

    public void showLoadedEmpty() {
        pb_loading.showError("", "暂无数据", "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEventList(true);
            }
        });
    }

    public void getPSHUnitDetail(Long unitId, final PSHEventListItem selectdData) {
        if (mPatrolService == null) {
            mPatrolService = new DialyPatrolService(this);
        }
        fl_loading.setVisibility(View.VISIBLE);
        Observable<PSHAffairDetail> observable;
        if(TextUtils.isEmpty(selectdData.getPshDj()) || "0".equals(selectdData.getPshDj())){
            observable = mPatrolService.getPSHUnitDetail(unitId);
        } else {
            observable = new PSHAffairService(this).getEjpshDetail(unitId.intValue());
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<PSHAffairDetail>() {

                    @Override
                    public void onCompleted() {
                        fl_loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        fl_loading.setVisibility(View.GONE);
                        Toast.makeText(PshMyUploadEventListActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                        goDetail(selectdData, null, null, null);
                    }

                    @Override
                    public void onNext(PSHAffairDetail pshAffairDetail) {
                        goDetail(selectdData, pshAffairDetail, null, null);
                    }
                });
    }

    private void getjbjhDetail(final PSHEventListItem selectdData){
        fl_loading.setVisibility(View.VISIBLE);
        long markId = selectdData.getPshid();
        String reportType = selectdData.getReportType();
        if(UploadLayerFieldKeyConstant.CORRECT_ERROR.equals(reportType)){
            CorrectFacilityService correctFacilityService = new CorrectFacilityService(this);
            correctFacilityService.getModificationById(markId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ModifiedFacility>() {
                        @Override
                        public void onCompleted() {
                            fl_loading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            fl_loading.setVisibility(View.GONE);
                            Toast.makeText(PshMyUploadEventListActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                            goDetail(selectdData, null, null, null);
                        }

                        @Override
                        public void onNext(ModifiedFacility modifiedFacility) {
                            goDetail(selectdData, null, modifiedFacility, null);
                        }
                    });
        } else {
            UploadFacilityService uploadFacilityService = new UploadFacilityService(this);
            uploadFacilityService.getUploadFacilityById(markId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UploadedFacility>() {
                        @Override
                        public void onCompleted() {
                            fl_loading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            fl_loading.setVisibility(View.GONE);
                            Toast.makeText(PshMyUploadEventListActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                            goDetail(selectdData, null, null, null);
                        }

                        @Override
                        public void onNext(UploadedFacility uploadedFacility) {
                            goDetail(selectdData, null, null, uploadedFacility);
                        }
                    });
        }
    }

    private void goDetail(PSHEventListItem selectdData, PSHAffairDetail pshAffairDetail, ModifiedFacility modifiedFacility, UploadedFacility uploadedFacility) {
        Intent intent = new Intent(PshMyUploadEventListActivity.this, PshEventAffairDetailActivity.class);
        intent.putExtra("fromPSHAffair", true);
        intent.putExtra("fromMyUpload", true);
        intent.putExtra("showDelBtn", true);
        intent.putExtra("isDialy", true);
        intent.putExtra("eventAffairBean", selectdData);
        intent.putExtra("isEjPsh", "1".equals(selectdData.getPshDj()));
        if (pshAffairDetail != null) {
            intent.putExtra("pshAffair", pshAffairDetail);
            if(pshAffairDetail.getData() != null) {
                if("1".equals(selectdData.getPshDj())){
                    pshAffairDetail.getData().coverEjToYj();
                }
                intent.putExtra("isTempStorage", "4".equals(pshAffairDetail.getData().getState()));
            }
        }
        if(modifiedFacility != null){
            intent.putExtra("modifiedFacility", modifiedFacility);
        }
        if(uploadedFacility != null){
            intent.putExtra("uploadedFacility", uploadedFacility);
        }
        startActivity(intent);
    }

    @Subscribe
    public void onRefreshList(UpdateAfterDeleteEvent updateAfterDeleteEvent) {
        pageNo = 1;
        loadMore =false;
        loadEventList(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
