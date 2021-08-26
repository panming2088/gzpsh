package com.augurit.agmobile.gzpssb.secondpsh;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.ModifyFacilitySumEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.UploadFacilitySumEvent;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.event.RefreshPsdyData;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfo;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondPshRefreshEvent;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.service.SewerageLayerService;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.pshpublicaffair.service.PSHAffairService;
import com.augurit.agmobile.gzpssb.secondpsh.service.SecondLevelPshService;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

public class SecondLevelPshListFragment extends Fragment {
    private SecondLevelPshInfo secondLevelPshInfo;
    private SecondPshListAdapter mSearchResultAdapter;
    private ProgressLinearLayout pb_loading;
    private XRecyclerView rv_component_list;
    private TextView tv_component_counts;
    private TextView all_count, install_count, no_install_count, no_audit_count, temp_storage_count;
    private LinearLayout all_ll, install_ll, no_install_ll, no_audit_ll, temp_storage_ll;
    private String checkState = null;
    private Context mContext;
    private int page = 1;
    private ModifyFacilitySumEvent mModifyFacilitySumEvent;
    private boolean isIfReceivedAddFacilitySum = false;
    private Button btn_add;

    //筛选条件
    private String district = null;  //行政区划
    private String bigType = null;   //大类
    private String smallType = null;       //小类
    private Long startDate = null;
    private Long endDate = null;
    private String address = null;
    private Long uploadid = null;
    private String orgname = null;
    private SewerageItemBean.UnitListBean unitListBean;
    private SewerageLayerService mSewerageLayerService;
    private PSHAffairService pshAffairService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_second_psh_list, null);
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
        secondLevelPshInfo = (SecondLevelPshInfo) getActivity().getIntent().getSerializableExtra("data");
        //  onePageData = new ArrayList<>();
        pb_loading = (ProgressLinearLayout) view.findViewById(R.id.pb_loading);
        all_count = (TextView) view.findViewById(R.id.all_install_count);
        install_count = (TextView) view.findViewById(R.id.install_count);
        no_install_count = (TextView) view.findViewById(R.id.no_install_count);
        no_audit_count = (TextView) view.findViewById(R.id.no_audited_count);
        temp_storage_count = (TextView) view.findViewById(R.id.temp_storage_count);

        unitListBean = (SewerageItemBean.UnitListBean) ((SecondLevelPshListActivity) mContext).getIntent().getSerializableExtra("unitListBean");
        all_ll = (LinearLayout) view.findViewById(R.id.all_install_ll);
        install_ll = (LinearLayout) view.findViewById(R.id.install_ll);
        no_install_ll = (LinearLayout) view.findViewById(R.id.no_install_ll);
        no_audit_ll = (LinearLayout) view.findViewById(R.id.no_audited_ll);
        temp_storage_ll = (LinearLayout) view.findViewById(R.id.temp_storage_ll);
        btn_add = (Button) view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), SewerageTableActivity.class);
                MyApplication.SEWERAGEITEMBEAN = null;
                MyApplication.GUID = null;
                MyApplication.X = unitListBean.getX();
                MyApplication.Y = unitListBean.getY();
                intent.putExtra("isExist", false);
                intent.putExtra("HouseIdFlag", "0");
                intent.putExtra("isAllowSaveLocalDraft", false);
                intent.putExtra("UnitId", "");
                DetailAddress detailAddress = new DetailAddress();
                detailAddress.setDetailAddress(unitListBean.getAddr());
                intent.putExtra("adderss", detailAddress);
                intent.putExtra("psdyName", unitListBean.getPsdyName());
                intent.putExtra("psdyId", unitListBean.getPsdyId());
                intent.putExtra("isAllowSaveLocalDraft", true);
                intent.putExtra("isAddEj", true);
                intent.putExtra("unitListBeans", unitListBean);
                startActivity(intent);
                if (StringUtil.isEmpty(unitListBean.getPsdyName())) {//没有排水单元的情况下搜索周边排水单元
                    rv_component_list.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // TODO 开始检索周边排水单元
                            EventBus.getDefault().post(new RefreshPsdyData(unitListBean.getX(), unitListBean.getY()));
                        }
                    }, 300);
                }
            }
        });
        rv_component_list = (XRecyclerView) view.findViewById(R.id.rv_component_list);
        rv_component_list.setPullRefreshEnabled(true);
        rv_component_list.setLoadingMoreEnabled(true);
        rv_component_list.setLayoutManager(new LinearLayoutManager(mContext));
        mSearchResultAdapter = new SecondPshListAdapter(mContext, new ArrayList<SecondLevelPshInfo.SecondPshInfo>());
        rv_component_list.setAdapter(mSearchResultAdapter);
        mSearchResultAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
//                int selectPosition = position;
//                if (page > 1) {
//                    selectPosition = position - mSearchResultAdapter.getDataList().size() - page * LoadDataConstant.LOAD_ITEM_PER_PAGE;
//                }
//
//                Intent intent = new Intent(getActivity(), SecondLevelPshDetailActivity.class);
//                intent.putExtra("unitListBean", unitListBean);
//                intent.putExtra("data", mSearchResultAdapter.getDataList().get(position - 1));
//                mContext.startActivity(intent);
                getPshDetail(Integer.valueOf(mSearchResultAdapter.getDataList().get(position -1).getId()),false,false,true);
            }
        });
        mSearchResultAdapter.setSwitchListener(new SecondPshListAdapter.onSwitchListener() {
            @Override
            public void onItemSwitchLister(SecondLevelPshInfo.SecondPshInfo data) {
                //一级排水户转二级排水户
//                Intent intent = new Intent(getContext(), SewerageTableActivity.class);
//                MyApplication.SEWERAGEITEMBEAN = null;
//                MyApplication.GUID = null;
//                MyApplication.X = unitListBean.getX();
//                MyApplication.Y = unitListBean.getY();
//                intent.putExtra("HouseIdFlag", "0");
//                intent.putExtra("UnitId", "");
//                DetailAddress detailAddress = new DetailAddress();
//                detailAddress.setDetailAddress(data.getEjaddr());
//                intent.putExtra("adderss", detailAddress);
//                intent.putExtra("isEjZYj", true);
//                intent.putExtra("isAddEj", false);
//                intent.putExtra("SecondPshInfo", data);//这里是二级排水户的信息
//                intent.putExtra("psdyname", unitListBean.getPsdyName());
//                intent.putExtra("isAllowSaveLocalDraft", false);
//                startActivity(intent);
                getPshDetail(Integer.valueOf(data.getId()),false,true,false);
//                if (StringUtil.isEmpty(unitListBean.getPsdyName())) {
//                    rv_component_list.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            // TODO 开始检索周边排水单元
//                            EventBus.getDefault().post(new RefreshPsdyData(unitListBean.getX(), unitListBean.getY()));
//                        }
//                    }, 300);
//                }
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

    public void loadDatas(boolean ifShowPb) {
        if (ifShowPb)
            pb_loading.showLoading();
        final SecondLevelPshService identificationService = new SecondLevelPshService(mContext);
        identificationService.getSecondLevelPshList(unitListBean.getId()+"",page, 10, bigType,smallType,startDate, endDate,uploadid,address,orgname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SecondLevelPshInfo>() {
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
                    public void onNext(SecondLevelPshInfo modifiedIdentifications) {
                        if (ListUtil.isEmpty(modifiedIdentifications.getData()) && page == 1) {
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
                            mSearchResultAdapter.addData(modifiedIdentifications.getData());
                            mSearchResultAdapter.notifyDataSetChanged();
                        } else if (page == 1) {
                            mSearchResultAdapter.notifyDataChanged(modifiedIdentifications.getData());
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
    public void RefreshListEvent(SecondPshRefreshEvent secondPshRefreshEvent) {
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
    public void refreshList(EJPSHUploadFilterConditionEvent facilityAffairFilterConditionEvent) {
        page = 1;
        this.bigType = facilityAffairFilterConditionEvent.getBigType();
        this.smallType = facilityAffairFilterConditionEvent.getSmallType();
        this.startDate = facilityAffairFilterConditionEvent.getStartTime();
        this.endDate = facilityAffairFilterConditionEvent.getEndTime();
        this.address = facilityAffairFilterConditionEvent.getOrgPosition();
        this.uploadid = facilityAffairFilterConditionEvent.getUploadid();
        this.orgname = facilityAffairFilterConditionEvent.getOrgName();
        loadDatas(true);
    }

    public void getPshDetail(int id, final boolean isAddEj, final boolean isEjZYj,final boolean isEjPsh) {
        if (pshAffairService == null) {
            pshAffairService = new PSHAffairService(mContext);
        }
        pshAffairService.getEjpshDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<PSHAffairDetail>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
//                        view.onGetPSHAffairDetail(null);
                    }

                    @Override
                    public void onNext(final PSHAffairDetail pshAffairDetail) {
                        if (pshAffairDetail != null) {
                            if (pshAffairDetail.getData() == null) {
                                ToastUtil.shortToast(mContext, "无二级排水户数据");
                                return;
                            }
                            pshAffairDetail.getData().coverEjToYj();
                            Intent intent = new Intent(getActivity(), SewerageTableActivity.class);
                            intent.putExtra("pshAffair", pshAffairDetail);
                            intent.putExtra("fromMyUpload", true);
//            intent.putExtra("isTempStorage", !TextUtils.isEmpty(checkState) && "4".equals(checkState));
                            intent.putExtra("isTempStorage", false);
                            intent.putExtra("isCancel", false);
//                            intent.putExtra("isIndustry", isIndustry);
                            intent.putExtra("isList", true);
                            intent.putExtra("isEjZYj", isEjZYj);
                            intent.putExtra("isAddEj", isAddEj);
                            intent.putExtra("isEjPsh", isEjPsh);
                            startActivity(intent);
                            if (StringUtil.isEmpty(pshAffairDetail.getData().getPsdyName())) {
                                rv_component_list.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO 开始检索周边排水单元
                                        EventBus.getDefault().post(new RefreshPsdyData(pshAffairDetail.getData().getX(), pshAffairDetail.getData().getY()));
                                    }
                                }, 300);
                            }
                        }
                    }
                });
    }
}
