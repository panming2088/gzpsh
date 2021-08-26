package com.augurit.agmobile.gzpssb.pshpublicaffair;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzps.common.util.LoadDataConstant;
import com.augurit.agmobile.gzps.publicaffair.service.FacilityAffairService;
import com.augurit.agmobile.gzps.publicaffair.view.condition.FacilityAffairFilterConditionEvent;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzps.uploadfacility.util.ServerAttachmentToPhotoUtil;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.RefreshMyModificationListEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.ModifyFacilitySumEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.RefreshMyUploadList;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournal;
import com.augurit.agmobile.gzpssb.journal.service.DialyPatrolService;
import com.augurit.agmobile.gzpssb.journal.view.adapter.DialyPatrolListAdapter;
import com.augurit.agmobile.gzpssb.journal.view.detail.DialyPatrolDetailActivity;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.cmpt.widget.searchview.util.Util;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 我的纠错列表
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.componentmaintenance
 * @createTime 创建时间 ：17/10/14
 * @modifyBy 修改人 ：xuciluan,luobiao
 * @modifyTime 修改时间 ：17/10/14
 * @modifyMemo 修改备注：
 */

public class PublicAffairDialyPatrolListFragment extends Fragment {

    private DialyPatrolListAdapter mSearchResultAdapter;
    private ProgressLinearLayout pb_loading;
    private XRecyclerView rv_component_list;
    private TextView tv_component_counts;
    private Context mContext;
    private FacilityFilterCondition mFacilityFilterCondition = null;
    private FacilityAffairFilterConditionEvent mAffairFilterConditionEvent = null;
    private int page = 1;
    private String checkState = null;
    private TextView tvTotalNum;
    private Calendar cal;
    private Long startDate;
    private Long endDate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_my_journallist, null);

        return inflate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
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

        pb_loading = (ProgressLinearLayout) view.findViewById(R.id.pb_loading);
        tvTotalNum = new TextView(getActivity());
        tvTotalNum.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvTotalNum.setTextSize(Util.spToPx(6));
        tvTotalNum.setPadding(Util.dpToPx(10), Util.dpToPx(10), 0, 0);
        tvTotalNum.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        rv_component_list = (XRecyclerView) view.findViewById(R.id.rv_component_list);
        rv_component_list.setPullRefreshEnabled(true);
        rv_component_list.setLoadingMoreEnabled(true);
        rv_component_list.addHeaderView(tvTotalNum);
        rv_component_list.setLayoutManager(new LinearLayoutManager(mContext));
        mSearchResultAdapter = new DialyPatrolListAdapter(mContext, new ArrayList<PSHJournal>());
        rv_component_list.setAdapter(mSearchResultAdapter);
        mSearchResultAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                // TODO: 2018/12/19 日志详情
                int selectPosition = position;
                if (page > 1) {
                    selectPosition = position - mSearchResultAdapter.getDataList().size() - page * LoadDataConstant.LOAD_ITEM_PER_PAGE;
                }
                Intent intent = new Intent(mContext, DialyPatrolDetailActivity.class);
                intent.putExtra("data", mSearchResultAdapter.getDataList().get(position - 2));
                intent.putExtra("ISEDIT", false);
                mContext.startActivity(intent);
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

        tv_component_counts = (TextView) view.findViewById(R.id.tv_component_counts);

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
        EventBus.getDefault().register(this);
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

        if (startDate == null) {
            startDate = getCurrentQuarterStartTime();
        }

        if (endDate == null) {
            cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);       //获取年月日时分秒
            int month = cal.get(Calendar.MONTH) + 1;   //获取到的月份是从0开始计数
            int day = cal.get(Calendar.DAY_OF_MONTH);
            endDate = new Date(year - 1900, month - 1, day + 1).getTime();
        }
        if (mAffairFilterConditionEvent == null) {
            String district = null;
            FacilityAffairService facilityAffairService = new FacilityAffairService(mContext.getApplicationContext());
            boolean b = facilityAffairService.ifCurrentUserBelongToCityUser();
            if (!b) {
                district = facilityAffairService.getParentOrgOfCurrentUser();
            }
            mAffairFilterConditionEvent = new FacilityAffairFilterConditionEvent(district, null, null, startDate, endDate);
        }

        final DialyPatrolService identificationService = new DialyPatrolService(mContext);
        identificationService.getPublicDiary(page, LoadDataConstant.LOAD_ITEM_PER_PAGE, mAffairFilterConditionEvent)
                //获取列表（不包含附件）
                .map(new Func1<Result3<List<PSHJournal>>, List<PSHJournal>>() {
                    @Override
                    public List<PSHJournal> call(final Result3<List<PSHJournal>> modifiedFacilityResult2) {
                        List<PSHJournal> identifications = new ArrayList<>();
                        List<PSHJournal> data = modifiedFacilityResult2.getData();
                        if (!StringUtil.isEmpty(modifiedFacilityResult2.getTotal())) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvTotalNum.setText("总数：" + modifiedFacilityResult2.getTotal());
                                }
                            });
                        }
                        if (!ListUtil.isEmpty(data)) {
                            int i = 0;
                            for (PSHJournal modifiedFacility : data) {
                                modifiedFacility.setOrder(i);
                                identifications.add(modifiedFacility);
                                i++;
                            }
                        }
                        return identifications;
                    }
                })
                .flatMap(new Func1<List<PSHJournal>, Observable<PSHJournal>>() {
                    @Override
                    public Observable<PSHJournal> call(List<PSHJournal> modifiedIdentifications) {
                        return Observable.from(modifiedIdentifications);
                    }
                })
                //获取附件
                .flatMap(new Func1<PSHJournal, Observable<PSHJournal>>() {
                    @Override
                    public Observable<PSHJournal> call(final PSHJournal modifiedIdentification) {
                        return identificationService.getMyModificationAttachments(modifiedIdentification.getId())
                                .map(new Func1<ServerAttachment, PSHJournal>() {
                                    @Override
                                    public PSHJournal call(ServerAttachment serverIdentificationAttachment) {
                                        List<ServerAttachment.ServerAttachmentDataBean> data = serverIdentificationAttachment.getData();
                                        if (!ListUtil.isEmpty(data)) {
                                            List<Photo> photos = new ArrayList<>();
                                            for (ServerAttachment.ServerAttachmentDataBean dataBean : data) {
                                                Photo photo = ServerAttachmentToPhotoUtil.getPhoto(dataBean);
                                                photos.add(photo);
                                            }
                                            modifiedIdentification.setPhotos(photos);
                                        }
                                        return modifiedIdentification;
                                    }
                                });
                    }
                })
                .toList()
                //进行手动排序
                .map(new Func1<List<PSHJournal>, List<PSHJournal>>() {
                    @Override
                    public List<PSHJournal> call(List<PSHJournal> modifiedIdentifications) {
                        Collections.sort(modifiedIdentifications, new Comparator<PSHJournal>() {
                            @Override
                            public int compare(PSHJournal modifiedIdentification, PSHJournal t1) {
                                if (modifiedIdentification.getOrder() > t1.getOrder()) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        });
                        return modifiedIdentifications;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PSHJournal>>() {
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
                    public void onNext(List<PSHJournal> modifiedIdentifications) {

                        if (ListUtil.isEmpty(modifiedIdentifications) && page == 1) {
                            showLoadedEmpty();
                            return;
                        }

                        if (ListUtil.isEmpty(modifiedIdentifications) && page > 1) {
                            rv_component_list.setNoMore(true);
                            return;
                        }


                        pb_loading.showContent();
                        rv_component_list.loadMoreComplete();
                        rv_component_list.refreshComplete();
                        if (page > 1) {
                            mSearchResultAdapter.addData(modifiedIdentifications);
                            mSearchResultAdapter.notifyDataSetChanged();
                        } else if (page == 1) {
                            mSearchResultAdapter.notifyDataChanged(modifiedIdentifications);
                            rv_component_list.scrollToPosition(0);
                        }
                        tv_component_counts.setVisibility(View.GONE);
                        tv_component_counts.setText("一共有：" + mSearchResultAdapter.getDataList().size() + "条数据");
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


    @Subscribe
    public void RefreshRefreshMyUploadListEvent(RefreshMyUploadList refreshMyModificationListEvent) {
        if (mSearchResultAdapter != null) {
            EventBus.getDefault().post(new ModifyFacilitySumEvent(mSearchResultAdapter.getItemCount()));
        }
    }

    @Subscribe
    public void RefreshMyModificationListEvent(RefreshMyModificationListEvent refreshMyModificationListEvent) {
        page = 1;
        loadDatas(true);
    }

    @Subscribe
    public void onRefreshList(FacilityFilterCondition facilityFilterCondition) {
        if (FacilityFilterCondition.MODIFIED_LIST.equals(facilityFilterCondition.filterListType)) {
            this.mFacilityFilterCondition = facilityFilterCondition;
            this.page = 1;
            loadDatas(true);
        }
    }

    @Subscribe
    public void onReceivedFacilityAffairFilterCondition(FacilityAffairFilterConditionEvent facilityAffairFilterConditionEvent) {
        page = 1;
        mAffairFilterConditionEvent = facilityAffairFilterConditionEvent;
        loadDatas(true);
    }


    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     *
     * @return
     */
    public static Long getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Long date = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            int year = c.get(Calendar.YEAR);       //获取年月日时分秒
            int month = c.get(Calendar.MONTH) + 1;   //获取到的月份是从0开始计数
            int day = c.get(Calendar.DAY_OF_MONTH);
            date = new Date(year - 1900, month - 1, day).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
