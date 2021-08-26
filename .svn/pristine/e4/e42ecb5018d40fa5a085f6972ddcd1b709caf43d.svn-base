package com.augurit.agmobile.gzpssb.pshpublicaffair;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.pshpublicaffair.adapter.PSHAffairAdapter;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHEventBean;
import com.augurit.agmobile.gzpssb.pshpublicaffair.service.PSHAffairService;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.affairdetail.PSHAffairDetailContract;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.affairdetail.PSHAffairDetailPresenter;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition.PSHAffairFilterConditionEvent;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.cmpt.widget.searchview.util.Util;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.augurit.agmobile.gzpssb.pshaffair
 * Created by sdb on 2018/4/10  15:18.
 * Desc：
 */

public class PSHPublicAffairFragment extends Fragment implements PSHAffairDetailContract.View, PSHAffairAdapter.PshAffairClickListener {

    private PSHAffairAdapter pshAffairAdapter;
    private PSHAffairService pshAffairService;
    PSHAffairDetailPresenter pshAffairDetailPresenter;
    private List<String> list = new ArrayList<>();

    private int page = 1;
    private boolean loadMore = true;
    private View rootView, fl_loading;
    private XRecyclerView rvPshaffair;
    private ProgressLinearLayout pbLoading;

    //筛选条件
    private String district = null;  //行政区划
    private String bigType = null;   //大类
    private String smallType = null;       //小类
    private Long startDate = null;
    private Long endDate = null;
    private TextView tvTotalNum;
    private PSHAffairDetail pshAffairDetail;
    private Calendar cal;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        rootView = View.inflate(getActivity(), R.layout.psh_affair_fragment, null);
        pbLoading = (ProgressLinearLayout) rootView.findViewById(R.id.pb_loading);
        fl_loading = rootView.findViewById(R.id.fl_loading);
        rvPshaffair = (XRecyclerView) rootView.findViewById(R.id.rv_pshaffair);
        pshAffairService = new PSHAffairService(getActivity());
        pshAffairDetailPresenter = new PSHAffairDetailPresenter(this, getActivity());
        initView();
        return rootView;
    }


    @Override
    public void onGetPSHAffairDetail(PSHAffairDetail pshAffairDetail) {
        Intent intent = new Intent(getActivity(), SewerageTableActivity.class);
        intent.putExtra("pshAffair", pshAffairDetail);
        intent.putExtra("fromPSHAffair", true);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        fl_loading.setVisibility(View.GONE);
    }

    @Override
    public void onClickAffair(int affairId) {
        fl_loading.setVisibility(View.VISIBLE);
        pshAffairDetailPresenter.getPSHAffairDetail(affairId);
    }

    protected void initView() {

        rvPshaffair.setLayoutManager(new LinearLayoutManager(getContext()));

        tvTotalNum = new TextView(getActivity());
        tvTotalNum.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvTotalNum.setTextSize(Util.spToPx(6));
        tvTotalNum.setPadding(Util.dpToPx(10), Util.dpToPx(10), 0, 0);
        tvTotalNum.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        pshAffairAdapter = new PSHAffairAdapter(getActivity());
        pshAffairAdapter.setPshAffairClickListener(this);
        rvPshaffair.setAdapter(pshAffairAdapter);
        rvPshaffair.setLoadingMoreEnabled(true);
        rvPshaffair.setPullRefreshEnabled(true);
        rvPshaffair.addHeaderView(tvTotalNum);

        //查看地图
//        view.findViewById(R.id.ll_see_map).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), FacilityAffairDistributeMapActivity.class);
//                intent.putExtra("uploadType", uploadType);
//                intent.putExtra("facilityType", facilityType);
//                intent.putExtra("startDate", startTime);
//                intent.putExtra("endDate", endTime);
//                if (distrct == null) {
//                    FacilityAffairService facilityAffairService = new FacilityAffairService(getActivity());
//                    distrct = facilityAffairService.getParentOrgOfCurrentUser();
//                    //如果是市级用户，那么可以看到全部的区域
//                    boolean ifCurrentUserBelongToCityUser = facilityAffairService.ifCurrentUserBelongToCityUser();
//                    if (ifCurrentUserBelongToCityUser) {
//                        distrct = "全部";
//                    }
//                }
//                intent.putExtra("district", distrct);
//                startActivity(intent);
//            }
//        });

        rootView.findViewById(R.id.ll_see_map).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllAddDoorNoMapActivity.class);
                startActivity(intent);
            }
        });

        rvPshaffair.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadMore = false;
                loadEventList(false);
                rvPshaffair.setNoMore(false);
            }

            @Override
            public void onLoadMore() {
                page++;
                loadMore = true;
                loadEventList(false);
            }
        });

        loadEventList(true);

    }

    private void loadEventList(boolean isShowPb) {
        if (isShowPb) {
            pbLoading.showLoading();
        }
        if(startDate == null){
            cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            startDate = new Date(2018 - 1900, 0, 1).getTime();
        }

        if(endDate == null){
            cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);       //获取年月日时分秒
            int month = cal.get(Calendar.MONTH) + 1;   //获取到的月份是从0开始计数
            int day = cal.get(Calendar.DAY_OF_MONTH);
            endDate = new Date(year - 1900, month - 1, day + 1).getTime();
        }
        //根据条件筛选问题上报列表
        pshAffairService.getPSHAffairList(page, district, bigType, smallType, startDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PSHEventBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (loadMore) {
                            rvPshaffair.loadMoreComplete();
                        } else {
                            rvPshaffair.refreshComplete();
                        }
                        if (page == 1) {
                            showLoadedError("");
                        }
                    }

                    @Override
                    public void onNext(PSHEventBean pshEventBean) {
                        if (pshEventBean == null || pshEventBean.getCode() != 200) {
                            showLoadedError("");
                            return;
                        }
                        List<PSHEventBean.DataBean> eventAffairBeaListn = pshEventBean.getData();
                        if (ListUtil.isEmpty(eventAffairBeaListn)) {
                            if (page == 1) {
                                showLoadedEmpty();
                            } else {
                                rvPshaffair.loadMoreComplete();
                                rvPshaffair.setNoMore(true);
                            }
                            return;
                        }
                        pbLoading.showContent();
                        tvTotalNum.setText("总数：" + pshEventBean.getTotal());
                        if (loadMore) {
                            pshAffairAdapter.addData(eventAffairBeaListn);
                            pshAffairAdapter.notifyDataSetChanged();
                            rvPshaffair.loadMoreComplete();
                        } else {
                            pshAffairAdapter.notifyDataSetChanged(eventAffairBeaListn);
                            rvPshaffair.refreshComplete();
                        }
                        if (eventAffairBeaListn.size() < PSHAffairService.LOAD_ITEM_PER_PAGE) {
                            rvPshaffair.setNoMore(true);
                        }

                    }
                });
    }


    public void showLoadedError(String errorReason) {
        pbLoading.showError("获取数据失败", errorReason, "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEventList(true);
            }
        });
    }

    public void showLoadedEmpty() {
        pbLoading.showError("", "暂无数据", "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEventList(true);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onReceivedFacilityAffairFilterCondition(PSHAffairFilterConditionEvent facilityAffairFilterConditionEvent) {
        page = 1;
        loadMore=false;
        this.district = facilityAffairFilterConditionEvent.getDistrct();
        this.bigType = facilityAffairFilterConditionEvent.getBigType();
        this.smallType = facilityAffairFilterConditionEvent.getSmallType();
        this.startDate = facilityAffairFilterConditionEvent.getStartTime();
        this.endDate = facilityAffairFilterConditionEvent.getEndTime();
        loadEventList(true);
    }

}
