package com.augurit.agmobile.gzpssb.uploadevent.view.eventlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.OnRefreshEvent;
import com.augurit.agmobile.gzps.common.constant.GzpsConstant;
import com.augurit.agmobile.gzps.uploadevent.adapter.MyEventListAdapter;
import com.augurit.agmobile.gzps.uploadevent.event.UpdateAfterDeleteEvent;
import com.augurit.agmobile.gzps.uploadevent.event.UpdateAfterRetriveEvent;
import com.augurit.agmobile.gzps.uploadevent.model.EventListItem;
import com.augurit.agmobile.gzps.uploadevent.service.UploadEventService;
import com.augurit.agmobile.gzps.uploadevent.view.eventlist.EventDetailActivity;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.uploadfacility.service.CorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.service.UploadFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.util.UploadLayerFieldKeyConstant;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityListFilterConditionView;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.journal.service.DialyPatrolService;
import com.augurit.agmobile.gzpssb.journal.view.DialyPatrolListFilterConditionView;
import com.augurit.agmobile.gzpssb.journal.view.DialyPatrolRecordListActivity;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.pshpublicaffair.service.PSHAffairService;
import com.augurit.agmobile.gzpssb.uploadevent.adapter.PSHEventListAdapter;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.service.PSHUploadEventService;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.PshEventAffairDetailActivity;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.PshEventDetailActivity1;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.ListUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 待受理  处理中  已办结
 */
public class PSHEventListActivity extends BaseActivity {

    private ProgressLinearLayout pb_loading;
    private XRecyclerView mRecyclerView;
    private PSHEventListAdapter adapter;
    private TextView title_tv;

    private PSHUploadEventService mEventService;
    private DialyPatrolService mPatrolService;

    private int pageNo = 1;
    private final int pageSize = 10;
    private boolean loadMore = true;

    private int eventPState = GzpsConstant.EVENT_P_STATE_HANDLING;
    private String state = "";
    private View fl_loading;
    private DrawerLayout drawer_layout;
    private FacilityFilterCondition mFacilityFilterCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        mEventService = new PSHUploadEventService(PSHEventListActivity.this.getApplicationContext());
        initView();
        initData();

        EventBus.getDefault().register(this);
    }

    private void initView() {
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //设置右面的侧滑菜单只能通过编程来打开
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                GravityCompat.END);


        //筛选条件
        findViewById(R.id.ll_search).setVisibility(View.VISIBLE);
        ImageView iv_open_search = ((ImageView) findViewById(R.id.iv_open_search));
        iv_open_search.setImageResource(R.mipmap.ic_filter);
        ((TextView) findViewById(R.id.tv_search)).setText("筛选");
        findViewById(R.id.tv_search).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });
        mRecyclerView = (XRecyclerView) findViewById(R.id.event_list_rv);
        fl_loading = findViewById(R.id.fl_loading);
        title_tv = (TextView) findViewById(R.id.tv_title);
        title_tv.setText("任务列表");
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        final ViewGroup llUploadFilterCondition = (ViewGroup) findViewById(R.id.ll_event_filter_condition);
        new EventListFilterConditionView(this, "问题列表筛选", FacilityFilterCondition.NEW_ADDED_LIST, llUploadFilterCondition);

        llUploadFilterCondition.setVisibility(View.VISIBLE);
    }

    private void initData() {
        eventPState = getIntent().getIntExtra(GzpsConstant.EVENT_P_STATE_KEY, GzpsConstant.EVENT_P_STATE_HANDLING);
        if (eventPState == GzpsConstant.EVENT_P_STATE_HANDLING) {
            findViewById(R.id.ll_search).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.ll_search).setVisibility(View.GONE);
        }
        setTitle(eventPState);
        adapter = new PSHEventListAdapter(null, eventPState, PSHEventListActivity.this);
        pb_loading = (ProgressLinearLayout) findViewById(R.id.pb_loading);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(PSHEventListActivity.this));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerItemClickListener<PSHEventListItem>() {
            @Override
            public void onItemClick(View view, int position, PSHEventListItem selectdData) {
                if ("psh".equals(selectdData.getSslx())) {
                    getPSHUnitDetail(selectdData.getPshid(), selectdData);
                } else if ("jbj".equals(selectdData.getSslx()) || "jhj".equals(selectdData.getSslx())) {
                    getjbjhDetail(selectdData);
                } else if (("dmjc".equals(selectdData.getSslx())
                        || "kgjc".equals(selectdData.getSslx()))
                        && selectdData.getPshid() != null && selectdData.getPshid() != 0) {
                    getjbjhDetail(selectdData);
                }  else {
                    goDetail(selectdData, null, null, null);
                }
//                Intent intent = new Intent(PSHEventListActivity.this, PshEventDetailActivity1.class);
//                intent.putExtra("eventList", selectdData);
//                intent.putExtra("eventPState", eventPState);
//                intent.putExtra("state", state);
//                startActivityForResult(intent, 123);
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
        loadEventList(true);
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
                        Toast.makeText(PSHEventListActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                        goDetail(selectdData, null, null, null);
                    }

                    @Override
                    public void onNext(PSHAffairDetail pshAffairDetail) {
                        goDetail(selectdData, pshAffairDetail, null, null);
                    }
                });
    }

    private void getjbjhDetail(final PSHEventListItem selectdData) {
        fl_loading.setVisibility(View.VISIBLE);
        long markId = selectdData.getPshid();
        String reportType = selectdData.getReportType();
        if (UploadLayerFieldKeyConstant.CORRECT_ERROR.equals(reportType)) {
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
                            Toast.makeText(PSHEventListActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(PSHEventListActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                            goDetail(selectdData, null, null, null);
                        }

                        @Override
                        public void onNext(UploadedFacility uploadedFacility) {
                            goDetail(selectdData, null, null, uploadedFacility);
                        }
                    });
        }
    }

    private void goDetail(PSHEventListItem selectdData, PSHAffairDetail pshAffairDetail, ModifiedFacility modifiedFacility, UploadedFacility uploadedFacility){
        Intent intent = new Intent(PSHEventListActivity.this, PshEventDetailActivity1.class);
        intent.putExtra("fromPSHAffair", true);
        intent.putExtra("fromMyUpload", true);
        intent.putExtra("isDialy", true);
        intent.putExtra("eventList", selectdData);
        intent.putExtra("eventPState", eventPState);
        intent.putExtra("state", state);
        intent.putExtra("isEjPsh", "1".equals(selectdData.getPshDj()));
        if (pshAffairDetail != null) {
            intent.putExtra("pshAffair", pshAffairDetail);
            if (pshAffairDetail.getData() != null) {
                if("1".equals(selectdData.getPshDj())){
                    pshAffairDetail.getData().coverEjToYj();
                }
                intent.putExtra("isTempStorage", "4".equals(pshAffairDetail.getData().getState()));
            }
        }
        if (modifiedFacility != null) {
            intent.putExtra("modifiedFacility", modifiedFacility);
        }
        if (uploadedFacility != null) {
            intent.putExtra("uploadedFacility", uploadedFacility);
        }
        startActivityForResult(intent, 123);
    }

    private void loadEventList(boolean ifShowPb) {
        if (ifShowPb) {
            pb_loading.showLoading();
        }
        getEventListObserable(pageNo, pageSize, mFacilityFilterCondition)
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
                        pb_loading.showError("", "加载数据出错", "刷新", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pageNo = 1;
                                loadEventList(true);
                            }
                        });
                    }

                    @Override
                    public void onNext(List<PSHEventListItem> s) {
                        if (ListUtil.isEmpty(s)) {
                            if (pageNo == 1) {
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
                        if (s.size() < pageSize) {
                            mRecyclerView.setNoMore(true);
                        }
                    }
                });
    }


    public void setTitle(int eventState) {

        if (eventState == GzpsConstant.EVENT_P_STATE_HANDLED) {
            //处理中
            title_tv.setText("任务列表(处理中)");
        } else if (eventState == GzpsConstant.EVENT_P_STATE_FINISHED) {
            //已办结
            title_tv.setText("任务列表(已办结)");

        } else if (eventState == GzpsConstant.EVENT_P_STATE_HANDLING) {
            //待受理
            title_tv.setText("任务列表(待受理)");
        }

    }

    public void openDrawer() {
        drawer_layout.openDrawer(Gravity.RIGHT);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                GravityCompat.END);    //解除锁定
        drawer_layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //设置右面的侧滑菜单只能通过编程来打开
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                        GravityCompat.END);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private Observable<List<PSHEventListItem>> getEventListObserable(int pageNo, final int pageSize, FacilityFilterCondition mFacilityFilterCondition) {
        Observable<List<PSHEventListItem>> observable = null;
        switch (eventPState) {
            case GzpsConstant.EVENT_P_STATE_HANDLING:
                state = "dcl";
                observable = mEventService.getEventList(pageNo, pageSize, "dcl", mFacilityFilterCondition);
                break;
            case GzpsConstant.EVENT_P_STATE_HANDLED:
                state = "dbj";
                observable = mEventService.getEventList(pageNo, pageSize, "dbj", null);
                break;
            case GzpsConstant.EVENT_P_STATE_FINISHED:
                state = "ybj";
                observable = mEventService.getEventList(pageNo, pageSize, "ybj", null);
                break;
            case GzpsConstant.EVENT_P_STATE_UPLOADED:  //该情况下不用当前界面(Activity)
                break;
            default:
                break;
        }
        return observable;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123
                && resultCode == 123) {
            loadMore = false;
            pageNo = 1;
            loadEventList(true);
            EventBus.getDefault().post(new OnRefreshEvent());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateAfterRetriveEvent(UpdateAfterRetriveEvent event) {
//        if(event.getProcInstDbId() != null){
//            List<PSHEventListItem> pre = adapter.getListData();
//            List<PSHEventListItem> cur = new ArrayList<>();
//            for(PSHEventListItem item : pre){
//                if(!item.getWtlx().equals(event.getProcInstDbId())){
//                   cur.add(item);
//                }
//            }
//            adapter.refresh(cur);
//        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateAfterDeleteEvent(UpdateAfterDeleteEvent event) {
//        if(event.getProcInstDbId() != null ){
//            List<EventListItem> pre = adapter.getListData();
//            List<EventListItem> cur = new ArrayList<>();
//            for(EventListItem item : pre){
//                if(!item.getProcInstDbId().equals(event.getProcInstDbId())){
//                    cur.add(item);
//                }
//            }
//
//            adapter.refresh(cur);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onRefreshList(FacilityFilterCondition facilityFilterCondition) {
        drawer_layout.closeDrawers();
        mFacilityFilterCondition = facilityFilterCondition;
        pageNo = 1;
        loadMore = false;
        loadEventList(true);

    }
}
