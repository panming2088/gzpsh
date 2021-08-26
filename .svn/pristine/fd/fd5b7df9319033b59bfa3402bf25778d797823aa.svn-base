package com.augurit.agmobile.gzpssb.uploadevent.view.uploadevent;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.selectcomponent.SelectComponentFinishEvent2;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.ModifyFacilitySumEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.RefreshMyUploadList;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.journal.service.DialyPatrolService;
import com.augurit.agmobile.gzpssb.journal.view.DialyPatrolRecordListActivity;
import com.augurit.agmobile.gzpssb.journal.view.adapter.PSHHouseListAdapter;
import com.augurit.agmobile.gzpssb.journal.view.uploadnewdialy.DialyPatrolAddNewActivity;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.uploadevent.adapter.EventHouseListAdapter;
import com.augurit.agmobile.gzpssb.uploadevent.model.SelectHouseFinishEvent;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.esri.core.geometry.Point;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我的新增列表
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.componentmaintenance
 * @createTime 创建时间 ：17/10/14
 * @modifyBy 修改人 ：xuciluan,luob
 * @modifyTime 修改时间 ：17/10/14
 * @modifyMemo 修改备注：
 */

public class EventHouseListFragment extends Fragment {

    private EventHouseListAdapter mSearchResultAdapter;
    private ProgressLinearLayout pb_loading;
    private XRecyclerView rv_component_list;
    private TextView all_count, install_count, no_install_count, no_audit_count;
    private LinearLayout all_ll, install_ll, no_install_ll, no_audit_ll;
    private String checkState = null;
    private Context mContext;

    private int page = 1;

    private ModifyFacilitySumEvent mModifyFacilitySumEvent;

    private FacilityFilterCondition mUploadedFacilityFilterCondition;

    private boolean isIfReceivedAddFacilitySum = false;
    private DetailAddress mDetailAddress;
    private DialyPatrolService mIdentificationService;
    private Point point;

    // private GetName getName;
    public static EventHouseListFragment newInstance(Bundle bundle) {
        EventHouseListFragment pshHouseListFragment = new EventHouseListFragment();
        pshHouseListFragment.setArguments(bundle);
        return pshHouseListFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_psh_house_list, null);

        return inflate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            this.mContext = context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            this.mContext = activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  onePageData = new ArrayList<>();
        EventBus.getDefault().register(this);
        pb_loading = (ProgressLinearLayout) view.findViewById(R.id.pb_loading);
        all_count = (TextView) view.findViewById(R.id.all_install_count);
        install_count = (TextView) view.findViewById(R.id.install_count);
        no_install_count = (TextView) view.findViewById(R.id.no_install_count);
        no_audit_count = (TextView) view.findViewById(R.id.no_audited_count);

        all_ll = (LinearLayout) view.findViewById(R.id.all_install_ll);
        install_ll = (LinearLayout) view.findViewById(R.id.install_ll);
        no_install_ll = (LinearLayout) view.findViewById(R.id.no_install_ll);
        no_audit_ll = (LinearLayout) view.findViewById(R.id.no_audited_ll);
        mDetailAddress = getArguments().getParcelable("detailAddress");
        point = (Point)getArguments().getSerializable("point");
        rv_component_list = (XRecyclerView) view.findViewById(R.id.rv_component_list);
        rv_component_list.setPullRefreshEnabled(false);
        rv_component_list.setLoadingMoreEnabled(false);
        rv_component_list.setLayoutManager(new LinearLayoutManager(mContext));
        mSearchResultAdapter = new EventHouseListAdapter(mContext, new ArrayList<PSHHouse>());
        rv_component_list.setAdapter(mSearchResultAdapter);
        mSearchResultAdapter.setOnRecycleitemOnClic(new EventHouseListAdapter.OnClickHouseListener() {
            @Override
            public void onItemLister(PSHHouse pshHouse) {
//                getPSHUnitDetail(Long.valueOf(pshHouse.getId()),pshHouse);
            }

            @Override
            public void onItemRecordListener(PSHHouse pshHouse) {
//                Intent intent = new Intent(mContext, DialyPatrolRecordListActivity.class);
//                intent.putExtra("UnitListBean", pshHouse);
//                startActivity(intent);
            }

            @Override
            public void onInspectionListenter(PSHHouse pshHouse) {
//                Intent intent = new Intent(mContext, DialyPatrolAddNewActivity.class);
//                intent.putExtra("UnitListBean", pshHouse);
//                if(mDetailAddress != null) {
//                    intent.putExtra("detailAddress", mDetailAddress);
//                }
//                intent.putExtra("point", point);
//                startActivity(intent);
                EventBus.getDefault().post(new SelectHouseFinishEvent(point, pshHouse));
                ((Activity)mContext).finish();
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


        all_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkState = null;
                page = 1;
                switchTopTabColor((LinearLayout) view);
                loadDatas(true);
            }
        });

        install_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkState = "2";
                page = 1;
                switchTopTabColor((LinearLayout) view);
                loadDatas(true);
            }
        });

        no_install_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkState = "3";
                page = 1;
                switchTopTabColor((LinearLayout) view);
                loadDatas(true);
            }
        });

        no_audit_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkState = "1";
                page = 1;
                switchTopTabColor((LinearLayout) view);
                loadDatas(true);
            }
        });


        view.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });

        ((TextView) view.findViewById(R.id.tv_title)).setText("我的纠错");

        view.findViewById(R.id.ll_search).setVisibility(View.VISIBLE);
        ImageView iv_open_search = (ImageView) view.findViewById(R.id.iv_open_search);
        iv_open_search.setImageResource(R.mipmap.ic_map);
        loadDatas(true);
//        EventBus.getDefault().register(this);
    }

    private void switchTopTabColor(LinearLayout linearLayout) {
        all_ll.setBackgroundColor(mContext.getResources().getColor(R.color.white_alpha));
        install_ll.setBackgroundColor(mContext.getResources().getColor(R.color.white_alpha));
        no_install_ll.setBackgroundColor(mContext.getResources().getColor(R.color.white_alpha));
        no_audit_ll.setBackgroundColor(mContext.getResources().getColor(R.color.white_alpha));
        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.light_green_alpha));
    }


    public void showLoadedError(String errorReason) {
        pb_loading.showError("获取数据失败", errorReason, "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDatas(true);
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

    public void loadDatas(boolean ifShowPb) {
        if (ifShowPb)
            pb_loading.showLoading();
        if (mIdentificationService == null) {
            mIdentificationService = new DialyPatrolService(mContext);
        }
        mIdentificationService.getPshBySGuid(MyApplication.doorBean.getS_guid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result3<List<PSHHouse>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (page == 1) {
                            showLoadedError(e.getLocalizedMessage());
                        } else {
                            ToastUtil.shortToast(mContext, "加载更多失败");
                        }
                    }

                    @Override
                    public void onNext(Result3<List<PSHHouse>> modifiedIdentifications) {
                        if (modifiedIdentifications.getCode() != 200) {
                            if (page == 1) {
                                showLoadedError("加载失败");
                            } else {
                                ToastUtil.shortToast(mContext, "加载更多失败");
                            }
                        }
                        List<PSHHouse> pshHouses = modifiedIdentifications.getData();

                        if (ListUtil.isEmpty(pshHouses) && page == 1) {
                            showLoadedEmpty();
                            return;
                        }

                        if (ListUtil.isEmpty(pshHouses) && page > 1) {
                            rv_component_list.setNoMore(true);
                            return;
                        }
                        pb_loading.showContent();
                        rv_component_list.loadMoreComplete();
                        rv_component_list.refreshComplete();
                        if (page > 1) {
                            mSearchResultAdapter.addData(pshHouses);
                            mSearchResultAdapter.notifyDataSetChanged();
                        } else if (page == 1) {
                            mSearchResultAdapter.notifyDataChanged(pshHouses);
                            rv_component_list.scrollToPosition(0);
                        }
//                        tv_component_counts.setVisibility(View.GONE);
//                        tv_component_counts.setText("一共有：" + mSearchResultAdapter.getDataList().size() + "条数据");
                    }
                });
    }

    private String getString(double n) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(n);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void getPSHUnitDetail(Long unitId, final PSHHouse pshHouse) {
        if (mIdentificationService == null) {
            mIdentificationService = new DialyPatrolService(mContext);
        }
        mIdentificationService.getPSHUnitDetail(unitId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<PSHAffairDetail>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(PSHAffairDetail pshAffairDetail) {
                        if (pshAffairDetail != null) {
                            if(pshAffairDetail.getData() == null){
                                ToastUtil.shortToast(mContext,"无排水户数据");
                                return;
                            }
                            Intent intent = new Intent(mContext, DialyPatrolRecordListActivity.class);
                            intent.putExtra("UnitListBean", pshHouse);
                            intent.putExtra("pshAffair", pshAffairDetail);
                            intent.putExtra("fromPSHAffair", true);
                            intent.putExtra("fromMyUpload", true);
                            intent.putExtra("isTempStorage",  "4".equals(pshAffairDetail.getData().getState()));
                            intent.putExtra("isDialy", false);
                            intent.putExtra("isReEdit", !"0".equals(pshAffairDetail.getData().getState()));
                            intent.putExtra("isCancel", "0".equals(pshAffairDetail.getData().getState()));
                            startActivity(intent);
                        }
                    }
                });
    }

    @Subscribe
    public void RefreshRefreshMyUploadListEvent(RefreshMyUploadList refreshMyModificationListEvent) {
        loadDatas(true);
    }

}
