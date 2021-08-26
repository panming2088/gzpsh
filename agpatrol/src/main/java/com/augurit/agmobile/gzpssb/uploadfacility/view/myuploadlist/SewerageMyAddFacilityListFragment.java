package com.augurit.agmobile.gzpssb.uploadfacility.view.myuploadlist;

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

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.ModifyFacilitySumEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.UploadFacilitySumEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.RefreshMyUploadList;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.affairdetail.PSHAffairDetailContract;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.affairdetail.PSHAffairDetailPresenter;
import com.augurit.agmobile.gzpssb.uploadfacility.model.UploadedListBean;
import com.augurit.agmobile.gzpssb.uploadfacility.service.SewerageMyUploadService;
import com.augurit.agmobile.gzpssb.uploadfacility.view.condiction.UploadFilterConditionEvent;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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

public class SewerageMyAddFacilityListFragment extends Fragment implements PSHAffairDetailContract.View {

    private SewerageMyUploadedListAdapter mSearchResultAdapter;
    private ProgressLinearLayout pb_loading;
    private XRecyclerView rv_component_list;
    private TextView tv_component_counts;
    private TextView all_count, install_count, no_install_count, no_audit_count, temp_storage_count;
    private LinearLayout all_ll, install_ll, no_install_ll, no_audit_ll, temp_storage_ll;
    private String checkState = null;
    private Context mContext;

    private int page = 1;

    private ModifyFacilitySumEvent mModifyFacilitySumEvent;

    private FacilityFilterCondition mUploadedFacilityFilterCondition;

    private boolean isIfReceivedAddFacilitySum = false;
    PSHAffairDetailPresenter pshAffairDetailPresenter;

    //筛选条件
    private String district = null;  //行政区划
    private String bigType = null;   //大类
    private String smallType = null;       //小类
    private Long startDate = null;
    private Long endDate = null;
    private String address = null;
    private Long uploadid = null;
    private String orgname = null;
    //是否是从主界面工业排水户图标点击进来
    private boolean isIndustry;
    private int drs = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_my_add_facility, null);

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
        isIndustry = ((Activity) mContext).getIntent().getBooleanExtra("isIndustry", false);
        pshAffairDetailPresenter = new PSHAffairDetailPresenter(this, getActivity());
        //  onePageData = new ArrayList<>();
        pb_loading = (ProgressLinearLayout) view.findViewById(R.id.pb_loading);
        all_count = (TextView) view.findViewById(R.id.all_install_count);
        install_count = (TextView) view.findViewById(R.id.install_count);
        no_install_count = (TextView) view.findViewById(R.id.no_install_count);
        no_audit_count = (TextView) view.findViewById(R.id.no_audited_count);
        temp_storage_count = (TextView) view.findViewById(R.id.temp_storage_count);

        all_ll = (LinearLayout) view.findViewById(R.id.all_install_ll);
        install_ll = (LinearLayout) view.findViewById(R.id.install_ll);
        no_install_ll = (LinearLayout) view.findViewById(R.id.no_install_ll);
        no_audit_ll = (LinearLayout) view.findViewById(R.id.no_audited_ll);
        temp_storage_ll = (LinearLayout) view.findViewById(R.id.temp_storage_ll);

        rv_component_list = (XRecyclerView) view.findViewById(R.id.rv_component_list);
        rv_component_list.setPullRefreshEnabled(true);
        rv_component_list.setLoadingMoreEnabled(true);
        rv_component_list.setLayoutManager(new LinearLayoutManager(mContext));
        mSearchResultAdapter = new SewerageMyUploadedListAdapter(mContext, new ArrayList<UploadedListBean>());
        rv_component_list.setAdapter(mSearchResultAdapter);
        if (isIndustry) {
            temp_storage_ll.setVisibility(View.GONE);
            no_install_ll.setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.tv_pass)).setText("已关联门牌");
            ((TextView) view.findViewById(R.id.tv_un_pass)).setText("未关联门牌");
            ((TextView) view.findViewById(R.id.tv_sum)).setText("总    数");
        }

//        RxView.clicks(sewerageTableActivityData.btnSeweragetableDel)
//                .throttleFirst(2, TimeUnit.SECONDS)
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//
//                    }
//                           });
        mSearchResultAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
//                int selectPosition = position;
//                if (page > 1) {
//                    selectPosition = position - mSearchResultAdapter.getDataList().size() - page * LoadDataConstant.LOAD_ITEM_PER_PAGE;
//                }
//
//                Intent intent = new Intent(getActivity(), SewerageTableActivity.class);
//                intent.putExtra("uploadedListBean", mSearchResultAdapter.getDataList().get(position - 1));
//                intent.putExtra("fromUploaded", true);
//                mContext.startActivity(intent);
                pshAffairDetailPresenter.getPSHAffairDetail(mSearchResultAdapter.getDataList().get(position - 1).getId());
                rv_component_list.setEnabled(false);
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

        all_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkState = null;
                drs = 0;
                page = 1;
                switchTopTabColor((LinearLayout) view);
                loadDatas(true);
            }
        });

        install_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkState = "2";
                drs = 2;
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

        temp_storage_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkState = "4";
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
                drs = 1;
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
        EventBus.getDefault().register(this);
    }

    private void switchTopTabColor(LinearLayout linearLayout) {
        all_ll.setBackgroundColor(mContext.getResources().getColor(R.color.white_alpha));
        install_ll.setBackgroundColor(mContext.getResources().getColor(R.color.white_alpha));
        no_install_ll.setBackgroundColor(mContext.getResources().getColor(R.color.white_alpha));
        no_audit_ll.setBackgroundColor(mContext.getResources().getColor(R.color.white_alpha));
        temp_storage_ll.setBackgroundColor(mContext.getResources().getColor(R.color.white_alpha));
        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.light_green_alpha));
    }


    public void showLoadedError(String errorReason) {
        pb_loading.showError("获取数据失败", "", "刷新", new View.OnClickListener() {
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

    @Override
    public void onResume() {
        super.onResume();
        if (rv_component_list != null) {
            rv_component_list.setEnabled(true);
        }
    }

    public void loadDatas(boolean ifShowPb) {
        if (ifShowPb)
            pb_loading.showLoading();

        final SewerageMyUploadService identificationService = new SewerageMyUploadService(mContext);
        identificationService.getCollectList(page, checkState, district, bigType, smallType, startDate, endDate, uploadid, address, orgname, drs, isIndustry)
                .map(new Func1<Result3<List<UploadedListBean>>, List<UploadedListBean>>() {
                    @Override
                    public List<UploadedListBean> call(final Result3<List<UploadedListBean>> listResult2) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (listResult2 == null) {
                                    all_count.setText(0);
                                    install_count.setText(0);
                                    no_install_count.setText(0);
                                    no_audit_count.setText(0);
                                    temp_storage_count.setText(0);
                                    if (mModifyFacilitySumEvent == null) {
                                        //如果还没有收到纠错的数量
                                        isIfReceivedAddFacilitySum = true;
                                    } else {
                                        /**
                                         *  如果此时收到了纠错数量，那么相加得到总数发送给{@link com.augurit.agmobile.gzps.setting.MyUploadStatisticActivity}
                                         */
                                        int total = mModifyFacilitySumEvent.getSum();
//                                        EventBus.getDefault().post(new UploadFacilitySumEvent(total));
                                    }
                                } else {
                                    if (listResult2.getPass() != null && listResult2.getNo() != null) {
//                                        if( !isIndustry && listResult2.getDoubt() != null){}
                                        try {
                                            if (isIndustry) {
                                                int sum = Integer.valueOf(listResult2.getPass()) + Integer.valueOf(listResult2.getNo());
                                                all_count.setText(getString(Double.valueOf(sum)));
                                                //审核通过
                                                install_count.setText(getString(Double.valueOf(listResult2.getPass())));
                                                //未审核
                                                no_audit_count.setText(getString(Double.valueOf(listResult2.getNo())));
                                                //发送事件通知
                                                if (mModifyFacilitySumEvent == null) {
                                                    //如果还没有收到纠错的数量
                                                    isIfReceivedAddFacilitySum = true;
                                                } else {
                                                    /**
                                                     *  如果此时收到了纠错数量，那么相加得到总数发送给{@link com.augurit.agmobile.gzps.setting.MyUploadStatisticActivity}
                                                     */
                                                }
                                            } else {
                                                int sum = Integer.valueOf(listResult2.getPass()) + Integer.valueOf(listResult2.getDoubt()) + Integer.valueOf(listResult2.getNo()) + Integer.valueOf(listResult2.getZc());
                                                all_count.setText(getString(Double.valueOf(sum)));
                                                //审核通过
                                                install_count.setText(getString(Double.valueOf(listResult2.getPass())));
                                                no_install_count.setText(getString(Double.valueOf(listResult2.getDoubt())));
                                                //未审核
                                                no_audit_count.setText(getString(Double.valueOf(listResult2.getNo())));
                                                //TODO
                                                temp_storage_count.setText(getString(Double.valueOf(listResult2.getZc())));
                                                //发送事件通知
                                                if (mModifyFacilitySumEvent == null) {
                                                    //如果还没有收到纠错的数量
                                                    isIfReceivedAddFacilitySum = true;
                                                } else {
                                                    /**
                                                     *  如果此时收到了纠错数量，那么相加得到总数发送给{@link com.augurit.agmobile.gzps.setting.MyUploadStatisticActivity}
                                                     */
                                                }
                                            }
                                        } catch (Exception e) {
                                            //这里加try..catch的原因是为了就算发送过程发生错误，也不会影响列表的展示
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        });
                        List<UploadedListBean> identifications = new ArrayList<>();

                        List<UploadedListBean> data = listResult2.getData();
                        if (!ListUtil.isEmpty(data)) {
                            int i = 0;
                            for (UploadedListBean modifiedFacility : data) {
//                                modifiedFacility.setOrder(i);
                                identifications.add(modifiedFacility);
                                i++;
                            }
                        }
                        return identifications;
                    }
                })

//                .flatMap(new Func1<List<UploadedFacility>, Observable<UploadedFacility>>() {
//                    @Override
//                    public Observable<UploadedFacility> call(List<UploadedFacility> modifiedIdentifications) {
//                        return Observable.from(modifiedIdentifications);
//                    }
//                })
//                //获取附件
//                .flatMap(new Func1<UploadedFacility, Observable<UploadedFacility>>() {
//                    @Override
//                    public Observable<UploadedFacility> call(final UploadedFacility modifiedIdentification) {
//                        return identificationService.getMyUploadAttachments(modifiedIdentification.getId())
//                                .map(new Func1<ServerAttachment, UploadedFacility>() {
//                                    @Override
//                                    public UploadedFacility call(ServerAttachment serverIdentificationAttachment) {
//                                        List<ServerAttachment.ServerAttachmentDataBean> data = serverIdentificationAttachment.getData();
//                                        if (!ListUtil.isEmpty(data)) {
//                                            List<Photo> photos = new ArrayList<>();
//                                            for (ServerAttachment.ServerAttachmentDataBean dataBean : data) {
//                                                Photo photo = new Photo();
//                                                photo.setId(Long.valueOf(dataBean.getId()));
//                                                photo.setPhotoPath(dataBean.getAttPath());
//                                                photo.setThumbPath(dataBean.getThumPath());
//                                                photos.add(photo);
//                                            }
//                                            modifiedIdentification.setPhotos(photos);
//                                        }
//                                        return modifiedIdentification;
//                                    }
//                                });
//                    }
//                })
//                .toList()
//                //进行手动排序
//                .map(new Func1<List<UploadedFacility>, List<UploadedFacility>>() {
//                    @Override
//                    public List<UploadedFacility> call(List<UploadedFacility> modifiedIdentifications) {
//                        Collections.sort(modifiedIdentifications, new Comparator<UploadedFacility>() {
//                            @Override
//                            public int compare(UploadedFacility modifiedIdentification, UploadedFacility t1) {
//                                if (modifiedIdentification.getOrder() > t1.getOrder()) {
//                                    return 1;
//                                } else {
//                                    return -1;
//                                }
//                            }
//                        });
//                        return modifiedIdentifications;
//                    }
//                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UploadedListBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (page == 1) {
                            showLoadedError(e.getLocalizedMessage());
                            if (mModifyFacilitySumEvent == null) {
                                //如果还没有收到纠错的数量
                                isIfReceivedAddFacilitySum = true;
                            } else {
                                /**
                                 *  如果此时收到了纠错数量，那么相加得到总数发送给{@link com.augurit.agmobile.gzps.setting.MyUploadStatisticActivity}
                                 */
                                int total = mModifyFacilitySumEvent.getSum();
                                EventBus.getDefault().post(new UploadFacilitySumEvent(total));
                            }
                        } else {
                            ToastUtil.shortToast(mContext, "加载更多失败");
                        }
                    }

                    @Override
                    public void onNext(List<UploadedListBean> modifiedIdentifications) {

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
    public void RefreshMyModificationListEvent(RefreshMyUploadList refreshMyModificationListEvent) {
        page = 1;
        isIfReceivedAddFacilitySum = false;
        loadDatas(true);
    }

    @Subscribe
    public void onReceivedModifiedFacilitySumEvent(ModifyFacilitySumEvent sumEvent) {
        if (!isIfReceivedAddFacilitySum) {
            //还没有收到新增总数
            this.mModifyFacilitySumEvent = sumEvent;
        } else {
            /**
             *  如果此时收到了新增数量，那么相加得到总数发送给{@link com.augurit.agmobile.gzps.setting.MyUploadStatisticActivity}
             */
            this.mModifyFacilitySumEvent = sumEvent;
            if (all_count != null && all_count.getText() != null
                    && all_count.getText().toString() != null) {
                String s = all_count.getText().toString().replace(",", "");
                try {
                    Integer sum = Integer.valueOf(s);
                    int total = sumEvent.getSum() + sum;
                    EventBus.getDefault().post(new UploadFacilitySumEvent(total));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Subscribe
    public void refreshList(UploadFilterConditionEvent facilityAffairFilterConditionEvent) {
        page = 1;
        this.district = facilityAffairFilterConditionEvent.getDistrct();
        this.bigType = facilityAffairFilterConditionEvent.getBigType();
        this.smallType = facilityAffairFilterConditionEvent.getSmallType();
        this.startDate = facilityAffairFilterConditionEvent.getStartTime();
        this.endDate = facilityAffairFilterConditionEvent.getEndTime();
        this.address = facilityAffairFilterConditionEvent.getAddress();
        this.uploadid = facilityAffairFilterConditionEvent.getUploadid();
        this.orgname = facilityAffairFilterConditionEvent.getOrgname();
        loadDatas(true);

    }


    @Override
    public void onGetPSHAffairDetail(PSHAffairDetail pshAffairDetail) {
        try {
            if (pshAffairDetail != null) {
                if (pshAffairDetail.getData() == null) {
                    ToastUtil.shortToast(mContext, "无排水户数据");
                    return;
                }
                Intent intent = new Intent(getActivity(), SewerageTableActivity.class);
                intent.putExtra("pshAffair", pshAffairDetail);
                intent.putExtra("fromMyUpload", true);
//            intent.putExtra("isTempStorage", !TextUtils.isEmpty(checkState) && "4".equals(checkState));
                intent.putExtra("isTempStorage", "4".equals(pshAffairDetail.getData().getState()));
                intent.putExtra("isCancel", "0".equals(pshAffairDetail.getData().getState()));
                intent.putExtra("isIndustry", isIndustry);
                intent.putExtra("isList", true);
                this.startActivity(intent);
            }
        } catch (Exception e) {
        }
    }
}
