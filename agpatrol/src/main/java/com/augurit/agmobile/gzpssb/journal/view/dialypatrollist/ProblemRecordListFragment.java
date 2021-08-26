package com.augurit.agmobile.gzpssb.journal.view.dialypatrollist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.journal.service.DialyPatrolService;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.uploadevent.adapter.PSHEventListAdapter;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.PshEventAffairDetailActivity;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.ListUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.journal.view
 * @createTime 创建时间 ：2018-07-04
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2018-07-04
 * @modifyMemo 修改备注：
 */

public class ProblemRecordListFragment extends Fragment{

    private ProgressLinearLayout pb_loading;
    //    private LinearLayout ll_sum;//总数
//    private TextView tv_sum;
//    private LinearLayout ll_pass_sum;//达标
//    private TextView tv_pass_sum;
//    private LinearLayout ll_timeouting_sum;//快超时
//    private TextView tv_timeouting_sum;
//    private LinearLayout ll_unpass_sum;//不达标
//    private TextView tv_unpass_sum;
    private XRecyclerView mRecyclerView;
    private PSHEventListAdapter adapter;
    //TODO
    private DialyPatrolService mEventService;

    private int pageNo = 1;
    private final int pageSize = 10;
    private boolean loadMore = true;
    private Context mContext;
    //筛选条件
    private String district = null;  //行政区划
    private String componentTypeCode = null;   //设施类型数据字典编码
    private String eventTypeCode = null;       //问题类型数据字典编码
    private String state = null;               //处理中还是已办结
    private Component mComponent;
    private PSHHouse mUnitListBean;
    private PSHAffairDetail pshAffairDetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_problem_record,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        EventB us.getDefault().register(this);
        mEventService = new DialyPatrolService(getContext());
        savedInstanceState = getArguments();
        mUnitListBean = ((Activity)mContext).getIntent().getParcelableExtra("UnitListBean");
        pshAffairDetail = (PSHAffairDetail) ((Activity)mContext).getIntent().getSerializableExtra("pshAffair");
        pb_loading = (ProgressLinearLayout) view.findViewById(R.id.pb_loading);
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.event_list_rv);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setPullRefreshEnabled(false);
        adapter = new PSHEventListAdapter(null, 7,getContext());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerItemClickListener<PSHEventListItem>() {
            @Override
            public void onItemClick(View view, int position, PSHEventListItem selectedData) {
                Intent intent = new Intent(mContext, PshEventAffairDetailActivity.class);
                intent.putExtra("pshAffair", pshAffairDetail);
                intent.putExtra("fromPSHAffair", true);
                intent.putExtra("fromMyUpload", true);
                intent.putExtra("isDialy", true);
                intent.putExtra("eventAffairBean", selectedData);
                if(pshAffairDetail.getData() != null) {
                    intent.putExtra("isTempStorage", "4".equals(pshAffairDetail.getData().getState()));
                }
                startActivity(intent);
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

    private void loadEventList(boolean showPb) {
        if(showPb){
            pb_loading.showLoading();
        }
        Observable<List<PSHEventListItem>> observable = null;

        observable =   mEventService.search(pageNo, pageSize, mUnitListBean.getId());
        observable.subscribeOn(Schedulers.io())
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
                        if (s == null){
//                            showLoadedError("");
                            showLoadedEmpty();
                            return;
                        }
                        List<PSHEventListItem> eventAffairBeaListn = s;
                        if (ListUtil.isEmpty(eventAffairBeaListn)) {
                            if(pageNo == 1) {
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
                        if(eventAffairBeaListn.size() < pageSize){
                            mRecyclerView.setNoMore(true);
                        }

                    }
                });
    }

//    private void switchTopTabColor(LinearLayout linearLayout){
//        ll_sum.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
//        ll_pass_sum.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
//        ll_timeouting_sum.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
//        ll_unpass_sum.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
//        linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.light_green_alpha));
//    }


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


//    @Subscribe
//    public void onEventConditionChanged(EventAffairConditionEvent eventAffairConditionEvent){
//        if(eventAffairConditionEvent.getDistrict() != null
//                && eventAffairConditionEvent.getDistrict().contains("净水")){
//            this.district = "净水";
//        } else {
//            this.district = eventAffairConditionEvent.getDistrict();
//        }
//        this.componentTypeCode = eventAffairConditionEvent.getComponentTypeCode();
//        this.eventTypeCode = eventAffairConditionEvent.getEventTypeCode();
//        pageNo = 1;
//        state = null;
//        switchTopTabColor(ll_sum);
//        loadMore = false;
//        loadEventList(true);
//        mRecyclerView.setNoMore(false);
//    }
@Override
public void onAttach(Activity activity) {
    super.onAttach(activity);
    if(activity != null){
        this.mContext = activity;
    }
}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null){
            this.mContext = context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

}
