package com.augurit.agmobile.gzps.setting.view.myupload;


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
import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzps.common.util.LoadDataConstant;
import com.augurit.agmobile.gzps.setting.service.MyCorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.util.ServerAttachmentToPhotoUtil;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.RefreshMyModificationListEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.facilitydetail.ModifyFacilityDetailActivity;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.ModifyFacilitySumEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.MyModiiedyFacilityListAdapter;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.RefreshMyUploadList;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * ??????????????????
 *
 * @author ????????? ???xuciluan
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.gzps.componentmaintenance
 * @createTime ???????????? ???17/10/14
 * @modifyBy ????????? ???xuciluan,luobiao
 * @modifyTime ???????????? ???17/10/14
 * @modifyMemo ???????????????
 */

public class MyCheckedFacilityListFragment extends Fragment {

    private MyModiiedyFacilityListAdapter mSearchResultAdapter;
    private ProgressLinearLayout pb_loading;
    private XRecyclerView rv_component_list;
    private TextView tv_component_counts;
    private TextView all_count, install_count, no_install_count, no_audit_count;
    private LinearLayout all_ll, install_ll, no_install_ll, no_audit_ll;
    private Context mContext;
    private FacilityFilterCondition mFacilityFilterCondition = null;
    private int page = 1;
    private String checkState = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_my_checked_facility, null);

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

        all_count = (TextView) view.findViewById(R.id.all_install_count);
        install_count = (TextView) view.findViewById(R.id.install_count);
        no_install_count = (TextView) view.findViewById(R.id.no_install_count);
        no_audit_count = (TextView) view.findViewById(R.id.no_audited_count);

        all_ll = (LinearLayout) view.findViewById(R.id.all_install_ll);
        install_ll = (LinearLayout) view.findViewById(R.id.install_ll);
        no_install_ll = (LinearLayout) view.findViewById(R.id.no_install_ll);
        no_audit_ll = (LinearLayout) view.findViewById(R.id.no_audited_ll);

        rv_component_list = (XRecyclerView) view.findViewById(R.id.rv_component_list);
        rv_component_list.setPullRefreshEnabled(true);
        rv_component_list.setLoadingMoreEnabled(true);
        rv_component_list.setLayoutManager(new LinearLayoutManager(mContext));
        mSearchResultAdapter = new MyModiiedyFacilityListAdapter(mContext, new ArrayList<ModifiedFacility>());
        rv_component_list.setAdapter(mSearchResultAdapter);
        mSearchResultAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                int selectPosition = position;
                if (page > 1) {
                    selectPosition = position - mSearchResultAdapter.getDataList().size() - page * LoadDataConstant.LOAD_ITEM_PER_PAGE;
                }
                Intent intent = new Intent(mContext, ModifyFacilityDetailActivity.class);
                intent.putExtra("data", mSearchResultAdapter.getDataList().get(position - 1));
                mContext.startActivity(intent);
//                if (mContext instanceof IChangeTabListener) {
//                    IChangeTabListener tabListener = (IChangeTabListener) mContext;
//                    tabListener.changeToTab(1); //??????????????????
//                    org.greenrobot.eventbus.EventBus.getDefault().post(new SendModifiedFacilityEvent(mSearchResultAdapter.getDataList().get(position - 1), page, selectPosition - 1));
//                }
                // ((ModifiedIdentificationActivity3) mContext).showMapFragment((ArrayList<ModifiedFacility>) onePageData, page, selectPosition);
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

        ((TextView) view.findViewById(R.id.tv_title)).setText("????????????");

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
        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.light_green_alpha));
    }

    public void showLoadedError(String errorReason) {
        pb_loading.showError("??????????????????", errorReason, "??????", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDatas(true);
            }
        });
    }

    public void showLoadedEmpty() {
        pb_loading.showError("", "????????????", "??????", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDatas(true);
            }
        });
    }

    public void loadDatas(boolean ifShowPb) {
        if (ifShowPb)
            pb_loading.showLoading();

        final MyCorrectFacilityService identificationService = new MyCorrectFacilityService(mContext);
        identificationService.getMyModifications(page, LoadDataConstant.LOAD_ITEM_PER_PAGE, checkState, mFacilityFilterCondition)
                //?????????????????????????????????
                .map(new Func1<Result3<List<ModifiedFacility>>, List<ModifiedFacility>>() {
                    @Override
                    public List<ModifiedFacility> call(final Result3<List<ModifiedFacility>> modifiedFacilityResult2) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (modifiedFacilityResult2 == null) {
                                    EventBus.getDefault().post(new ModifyFacilitySumEvent(0));
                                    all_count.setText(0);
                                    install_count.setText(0);
                                    no_install_count.setText(0);
                                    no_audit_count.setText(0);
                                } else {
                                    if (modifiedFacilityResult2.getPass() != null && modifiedFacilityResult2.getDoubt() != null && modifiedFacilityResult2.getNo() != null) {
                                        int sum = Integer.valueOf(modifiedFacilityResult2.getPass()) + Integer.valueOf(modifiedFacilityResult2.getDoubt()) + Integer.valueOf(modifiedFacilityResult2.getNo());
                                        EventBus.getDefault().post(new ModifyFacilitySumEvent(sum));
                                        all_count.setText(getString(Double.valueOf(sum)));
                                        install_count.setText(getString(Double.valueOf(modifiedFacilityResult2.getPass())));
                                        no_install_count.setText(getString(Double.valueOf(modifiedFacilityResult2.getDoubt())));
                                        no_audit_count.setText(getString(Double.valueOf(modifiedFacilityResult2.getNo())));
                                    }
                                }
                            }
                        });

                        List<ModifiedFacility> identifications = new ArrayList<>();
                        List<ModifiedFacility> data = modifiedFacilityResult2.getData();
                        if (!ListUtil.isEmpty(data)) {
                            int i = 0;
                            for (ModifiedFacility modifiedFacility : data) {
                                modifiedFacility.setOrder(i);
                                identifications.add(modifiedFacility);
                                i++;
                            }
                        }
                        return identifications;
                    }
                })
                .flatMap(new Func1<List<ModifiedFacility>, Observable<ModifiedFacility>>() {
                    @Override
                    public Observable<ModifiedFacility> call(List<ModifiedFacility> modifiedIdentifications) {
                        return Observable.from(modifiedIdentifications);
                    }
                })
                //????????????
                .flatMap(new Func1<ModifiedFacility, Observable<ModifiedFacility>>() {
                    @Override
                    public Observable<ModifiedFacility> call(final ModifiedFacility modifiedIdentification) {
                        return identificationService.getMyModificationAttachments(modifiedIdentification.getId())
                                .map(new Func1<ServerAttachment, ModifiedFacility>() {
                                    @Override
                                    public ModifiedFacility call(ServerAttachment serverIdentificationAttachment) {
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
                //??????????????????
                .map(new Func1<List<ModifiedFacility>, List<ModifiedFacility>>() {
                    @Override
                    public List<ModifiedFacility> call(List<ModifiedFacility> modifiedIdentifications) {
                        Collections.sort(modifiedIdentifications, new Comparator<ModifiedFacility>() {
                            @Override
                            public int compare(ModifiedFacility modifiedIdentification, ModifiedFacility t1) {
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
                .subscribe(new Subscriber<List<ModifiedFacility>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (page == 1) {
                            showLoadedError(e.getLocalizedMessage());
                        } else {
                            ToastUtil.shortToast(mContext, "??????????????????");
                        }
                    }

                    @Override
                    public void onNext(List<ModifiedFacility> modifiedIdentifications) {

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
                        tv_component_counts.setText("????????????" + mSearchResultAdapter.getDataList().size() + "?????????");
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

}
