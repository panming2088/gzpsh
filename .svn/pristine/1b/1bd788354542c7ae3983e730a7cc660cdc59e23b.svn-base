package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.drainage_unit_monitor.adapter.JbjMonitorListAdapter;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjJgListBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorArg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorInfoBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.JbjMonitorService;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.UploadFacilitySumEvent;
import com.augurit.agmobile.gzpssb.monitor.model.WellMonitorInfo;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author: liangsh
 * @createTime: 2021/5/8
 */
public class JbjMonitorListFragment extends Fragment {

    private View root;
    private ProgressLinearLayout pb_loading;
    private XRecyclerView rv_component_list;
    private JbjMonitorListAdapter adapter;

    private int page = 1;

    private JbjMonitorArg args;

    public static JbjMonitorListFragment getInstance(JbjMonitorArg arg){
        JbjMonitorListFragment fragment = new JbjMonitorListFragment();
        Bundle data = new Bundle();
        data.putSerializable("data", arg);
        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_jbj_monitor_list, null);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init(){
        args = (JbjMonitorArg) getArguments().getSerializable("data");
        rv_component_list = root.findViewById(R.id.rv_component_list);
        pb_loading = (ProgressLinearLayout) root.findViewById(R.id.pb_loading);
        rv_component_list.setPullRefreshEnabled(true);
        rv_component_list.setLoadingMoreEnabled(true);
        rv_component_list.setLayoutManager(new LinearLayoutManager(getContext()));
        boolean isRain = args.jbjType.contains("雨水");
        adapter = new JbjMonitorListAdapter(isRain, args.subtype);
        rv_component_list.setAdapter(adapter);
        View ll_add = root.findViewById(R.id.ll_add);
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JbjMonitorActivity.start(getActivity(), args);
            }
        });

        adapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<JbjJgListBean>() {
            @Override
            public void onItemClick(View view, int position, JbjJgListBean selectedData) {
                getJgDetail(selectedData.getJgId());
            }

            @Override
            public void onItemLongClick(View view, int position, JbjJgListBean selectedData) {

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
        loadDatas(true);
    }

    private void getJgDetail(String jgId){
        new JbjMonitorService(getContext()).getJbjJgDetail(jgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Result3<String> reslut = JsonUtil.getObject(s, new TypeToken<String>(){}.getType());
                        if(reslut.getCode() == 201) {
                            ToastUtil.shortToast(getContext(), "该记录无监测、问题数据");
                            return;
                        } else if(reslut.getCode() != 200){
                            ToastUtil.shortToast(getContext(), reslut.getMessage());
                            return;
                        }
                        Log.e("aaaaa", s);
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

    public void showLoadedEmpty() {
        pb_loading.showError("", "暂无数据", "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDatas(true);
            }
        });
    }

    public void showLoadedError(String errorReason) {
        pb_loading.showError("获取数据失败", errorReason, "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDatas(true);
            }
        });
    }

    public void loadDatas(boolean ifShowPb) {
        if (ifShowPb)
            pb_loading.showLoading();
        new JbjMonitorService(getContext()).getJbjJgList(page, 15, args.usid, args.jbjObjectId)
                .map(new Func1<String, Result3<List<JbjJgListBean>>>() {
                    @Override
                    public Result3<List<JbjJgListBean>> call(String s) {
                        return JsonUtil.getObject(s, new TypeToken<Result3<List<JbjJgListBean>>>(){}.getType());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result3<List<JbjJgListBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (page == 1) {
                            showLoadedError(e.getLocalizedMessage());
                        }
                        ToastUtil.iconShortToast(getContext(), R.mipmap.ic_alert_yellow, "获取数据失败，请稍后重试");
                    }

                    @Override
                    public void onNext(Result3<List<JbjJgListBean>> listResult2) {
                        UploadFacilitySumEvent uploadFacilitySumEvent = new UploadFacilitySumEvent();
                        if (listResult2.getCode() == 200) {
                            if (ListUtil.isEmpty(listResult2.getData())) {
                                uploadFacilitySumEvent.setSum(0);
                                EventBus.getDefault().post(uploadFacilitySumEvent);
                                showLoadedEmpty();
                                return;
                            }
                            if (ListUtil.isEmpty(listResult2.getData()) && page > 1) {
                                rv_component_list.setNoMore(true);
                                return;
                            }
                            uploadFacilitySumEvent.setSum(Integer.valueOf(listResult2.getTotal()));
                            EventBus.getDefault().post(uploadFacilitySumEvent);
                            pb_loading.showContent();
                            rv_component_list.loadMoreComplete();
                            rv_component_list.refreshComplete();
                            if (page > 1) {
                                adapter.addData(listResult2.getData());
                                adapter.notifyDataSetChanged();
                            } else if (page == 1) {
                                adapter.notifyDataSetChanged(listResult2.getData());
                                rv_component_list.scrollToPosition(0);
                            }
                        } else {
//                            CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
//                                    BaseInfoManager.getUserName(mContext) + "原因是：" + s.getMessage()));
                            Toast.makeText(getContext(), listResult2.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
