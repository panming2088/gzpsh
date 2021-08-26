package com.augurit.agmobile.gzpssb.pshpublicaffair;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.publicaffair.model.EventAffair;
import com.augurit.agmobile.gzps.publicaffair.view.condition.EventAffairConditionEvent;
import com.augurit.agmobile.gzps.publicaffair.view.eventaffair.EventAffairDetailActivity;
import com.augurit.agmobile.gzps.publicaffair.view.eventaffair.EventAffairListAdapter;
import com.augurit.agmobile.gzps.uploadevent.service.UploadEventService;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.uploadfacility.service.CorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.service.UploadFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.util.UploadLayerFieldKeyConstant;
import com.augurit.agmobile.gzpssb.journal.service.DialyPatrolService;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.pshpublicaffair.service.PSHAffairService;
import com.augurit.agmobile.gzpssb.uploadevent.adapter.PSHEventListAdapter;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.model.PshEventAffair;
import com.augurit.agmobile.gzpssb.uploadevent.service.PSHUploadEventService;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.PshEventAffairDetailActivity;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventlist.PSHEventAffairDetailActivity;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventlist.PshMyUploadEventListActivity;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
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
 * 事务公开中的问题上报列表
 * Created by liangsh on 2017/11/17.
 */

public class PshEventAffairFragment extends Fragment {

    private ProgressLinearLayout pb_loading;
    private LinearLayout ll_sum;
    private TextView tv_sum;
    private LinearLayout ll_handling_sum;
    private TextView tv_handling_sum;
    private LinearLayout ll_finished_sum;
    private TextView tv_finished_sum;
    private XRecyclerView mRecyclerView;
    private PSHEventListAdapter adapter;

    private PSHUploadEventService mEventService;

    private int pageNo = 1;
    private final int pageSize = 10;
    private boolean loadMore = true;

    //筛选条件
    private String district = null;  //行政区划
    private String sslx = null;
    private String componentTypeCode = null;   //设施类型数据字典编码
    private String eventTypeCode = null;       //问题类型数据字典编码
    private String state = null;               //处理中还是已办结
    private String psdyName = null;

    private Context mContext;
    private DialyPatrolService mPatrolService;
    private View fl_loading;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_psh_eventaffair, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mEventService = new PSHUploadEventService(getContext());
        ll_sum = (LinearLayout) view.findViewById(R.id.ll_sum);
        fl_loading = view.findViewById(R.id.fl_loading);
        tv_sum = (TextView) view.findViewById(R.id.tv_sum);
        ll_handling_sum = (LinearLayout) view.findViewById(R.id.ll_handling_sum);
        tv_handling_sum = (TextView) view.findViewById(R.id.tv_handling_sum);
        ll_finished_sum = (LinearLayout) view.findViewById(R.id.ll_finished_sum);
        tv_finished_sum = (TextView) view.findViewById(R.id.tv_finished_sum);
        pb_loading = (ProgressLinearLayout) view.findViewById(R.id.pb_loading);
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.event_list_rv);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setPullRefreshEnabled(false);
        adapter = new PSHEventListAdapter(null, 7, getContext());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerItemClickListener<PSHEventListItem>() {
            @Override
            public void onItemClick(View view, int position, PSHEventListItem selectedData) {
                if ("psh".equals(selectedData.getSslx())) {
                    getPSHUnitDetail(selectedData.getPshid(), selectedData);
                } else if ("jbj".equals(selectedData.getSslx()) || "jhj".equals(selectedData.getSslx())) {
                    getjbjhDetail(selectedData);
                } else if (("dmjc".equals(selectedData.getSslx())
                        || "kgjc".equals(selectedData.getSslx()))
                        && (selectedData.getPshid() != null && selectedData.getPshid() != 0)) {
                    getjbjhDetail(selectedData);
                } else {
                    goDetail(selectedData, null, null, null);
                }
//                Intent intent = new Intent(getContext(), PSHEventAffairDetailActivity.class);
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

        ll_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTopTabColor(ll_sum);
                state = null;
                pageNo = 1;
                loadMore = false;
                loadEventList(true);
                mRecyclerView.setNoMore(false);
            }
        });
        ll_handling_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTopTabColor(ll_handling_sum);
                state = "0";
                pageNo = 1;
                loadMore = false;
                loadEventList(true);
                mRecyclerView.setNoMore(false);
            }
        });
        ll_finished_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTopTabColor(ll_finished_sum);
                state = "1";
                pageNo = 1;
                loadMore = false;
                loadEventList(true);
                mRecyclerView.setNoMore(false);
            }
        });
        loadEventList(true);
    }

    private void loadEventList(boolean showPb) {
        if (showPb) {
            pb_loading.showLoading();
        }
        //根据条件筛选问题上报列表
        mEventService.getProblemReport(pageNo, pageSize, district, componentTypeCode, sslx, eventTypeCode, state, psdyName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PshEventAffair>() {
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
                        if (pageNo == 1) {
                            showLoadedError("");
                        }
                    }

                    @Override
                    public void onNext(PshEventAffair s) {
                        if (s == null) {
                            showLoadedError("");
                            return;
                        }
                        List<PSHEventListItem> eventAffairBeaListn = s.getList();
                        tv_handling_sum.setText("" + s.getHandling());
                        tv_finished_sum.setText("" + s.getFinished());
                        tv_sum.setText("" + (s.getFinished() + s.getHandling()));
                        if (ListUtil.isEmpty(eventAffairBeaListn)) {
                            if (pageNo == 1) {
                                showLoadedEmpty();
                            } else {
                                mRecyclerView.loadMoreComplete();
                                mRecyclerView.setNoMore(true);
                            }
                            return;
                        }
                        pb_loading.showContent();
                        if (loadMore) {
                            adapter.loadMore(eventAffairBeaListn);
                            mRecyclerView.loadMoreComplete();
                        } else {
                            adapter.refresh(eventAffairBeaListn);
                            mRecyclerView.refreshComplete();
                        }
                        if (eventAffairBeaListn.size() < pageSize) {
                            mRecyclerView.setNoMore(true);
                        }

                    }
                });
    }

    private void switchTopTabColor(LinearLayout linearLayout) {
        ll_sum.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        ll_handling_sum.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        ll_finished_sum.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.light_green_alpha));
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


    @Subscribe
    public void onEventConditionChanged(EventAffairConditionEvent eventAffairConditionEvent) {
        if (eventAffairConditionEvent.getDistrict() != null
                && eventAffairConditionEvent.getDistrict().contains("净水")) {
            this.district = "净水";
        } else {
            this.district = eventAffairConditionEvent.getDistrict();
        }
        this.sslx = eventAffairConditionEvent.sslx;
        this.componentTypeCode = eventAffairConditionEvent.getComponentTypeCode();
        this.eventTypeCode = eventAffairConditionEvent.getEventTypeCode();
        this.psdyName = eventAffairConditionEvent.psdyName;
        pageNo = 1;
        state = null;
        switchTopTabColor(ll_sum);
        loadMore = false;
        loadEventList(true);
        mRecyclerView.setNoMore(false);
    }

    public void getPSHUnitDetail(Long unitId, final PSHEventListItem selectdData) {
        if (mPatrolService == null) {
            mPatrolService = new DialyPatrolService(mContext);
        }
        fl_loading.setVisibility(View.VISIBLE);
        Observable<PSHAffairDetail> observable;
        if(TextUtils.isEmpty(selectdData.getPshDj()) || "0".equals(selectdData.getPshDj())){
            observable = mPatrolService.getPSHUnitDetail(unitId);
        } else {
            observable = new PSHAffairService(getContext()).getEjpshDetail(unitId.intValue());
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
                        Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
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
            CorrectFacilityService correctFacilityService = new CorrectFacilityService(getContext());
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
                            Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                            goDetail(selectdData, null, null, null);
                        }

                        @Override
                        public void onNext(ModifiedFacility modifiedFacility) {
                            goDetail(selectdData, null, modifiedFacility, null);
                        }
                    });
        } else {
            UploadFacilityService uploadFacilityService = new UploadFacilityService(getContext());
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
                            Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(mContext, PshEventAffairDetailActivity.class);
        intent.putExtra("fromPSHAffair", true);
        intent.putExtra("fromMyUpload", true);
        intent.putExtra("isDialy", true);
        intent.putExtra("eventAffairBean", selectdData);
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
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mContext = context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mContext = activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

}
