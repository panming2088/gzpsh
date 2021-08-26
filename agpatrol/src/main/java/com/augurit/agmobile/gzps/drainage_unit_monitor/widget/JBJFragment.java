package com.augurit.agmobile.gzps.drainage_unit_monitor.widget;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.model.Result3;
import com.augurit.agmobile.gzps.common.util.StringUtil;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.CleanLocationMarkEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.RefreshJBJEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.adapter.JBJMonitorAdapter;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JBJ;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyJg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorArg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyJgjl;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.MonitorService;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.JbjMonitorListActivity;
import com.augurit.agmobile.gzpssb.monitor.view.WellMonitorListActivity;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.view.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

public class JBJFragment extends Fragment {
    private MonitorService service;
    private EditText etKey;
    private ImageView ivSearch;
    private TextView tvEmpty;
    private LinearLayout llKey;
    private FrameLayout flEmpty;
    private AutoLoadRecyclerView recyclerView;
    private Button btnSubmit;
    private JBJMonitorAdapter adapter;
    private ProgressDialog pd;
    private FrameLayout flLoading;

    private Component component;
    private long id;
    private String name;

    private int pageNo = 1;
    private final int pageSize = 15;
    private String type = "";
    private int left;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_drainage_monitor_detail_jbj, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        service = new MonitorService(getContext());
        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshJBJ(RefreshJBJEvent event){
        adapter.setMonitored(event.objectid);
    }

    private void initView(){
        llKey = (LinearLayout) getView().findViewById(R.id.ll_key);
        flLoading = (FrameLayout) getView().findViewById(R.id.fl_loading);
        tvEmpty = (TextView) getView().findViewById(R.id.tv_empty);
        flEmpty = (FrameLayout) getView().findViewById(R.id.fl_empty);
        etKey = (EditText) getView().findViewById(R.id.et_key);
        ivSearch = (ImageView) getView().findViewById(R.id.iv_search);
        recyclerView = getView().findViewById(R.id.recycler_view);
        btnSubmit = (Button) getView().findViewById(R.id.btn_submit);
        btnSubmit.setVisibility(View.GONE);
        adapter = new JBJMonitorAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setAutoLoadListenerListener(new AutoLoadRecyclerView.AutoLoadListener() {
            @Override
            public void refresh() {
                pageNo = 1;
                loadData();

            }

            @Override
            public void loadMore() {
                pageNo++;
                loadData();

            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CleanLocationMarkEvent());
                type = etKey.getText().toString();
                pageNo = 1;
                loadData();
            }
        });
        adapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<JBJ>() {
            @Override
            public void onItemClick(View view, int position, JBJ selectedData) {
                /*JbjMonitorArg args = new JbjMonitorArg();
                args.jbjObjectId = String.valueOf(selectedData.getObjectid());
                args.X = StringUtil.valueOf(selectedData.getX());
                args.Y = StringUtil.valueOf(selectedData.getY());
                args.addr = selectedData.getAddr();
                args.subtype = selectedData.getSubtype();
                args.jbjType = selectedData.getSort();
                args.usid = selectedData.getUsid();
                args.wellType = selectedData.getLy();
                Map<String, Object> map = component.getGraphic().getAttributes();
//                args.psdyId = getAttrValue(map, "编号");
                args.psdyId = getAttrValue(map, "OBJECTID");
                args.psdyName = getAttrValue(map, "单元名称");
                args.reportType = selectedData.getReportType();
                JbjMonitorListActivity.start(getContext(), args);*/

                Intent intent = new Intent(getActivity(), WellMonitorListActivity.class);
                intent.putExtra("objectid", String.valueOf(selectedData.getObjectid()));
                intent.putExtra("wellType", selectedData.getLy());
                intent.putExtra("usid", "");
                boolean isJHJWell = false;
                if("接户井".equals(selectedData.getSubtype())){
                    isJHJWell = true;
                }
                intent.putExtra("subtype", isJHJWell);
                intent.putExtra("type", String.valueOf(selectedData.getSort()));
                intent.putExtra("X", String.valueOf(selectedData.getX()));
                intent.putExtra("Y", String.valueOf(selectedData.getY()));
                Map<String, Object> map = component.getGraphic().getAttributes();
                intent.putExtra("psdyId", getAttrValue(map, "OBJECTID"));
                intent.putExtra("psdyName", getAttrValue(map, "单元名称"));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, JBJ selectedData) {

            }
        });

        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.refresh();
                setEmptyVisible(false);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.getPsdyJg(id)
                        .subscribe(new Subscriber<Result3<PsdyJg, PsdyJgjl>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(Result3<PsdyJg, PsdyJgjl> result) {
                                if(result.getCode() != 200){
                                    ToastUtil.shortToast(getContext(), "服务器繁忙，请稍后重试");
                                    return;
                                }
                                int left = result.getData().getJbjNum() + result.getData().getJhjNum() - result.getData().getJbjJgNum() - result.getData().getJhjJgNum();
                                String title = null;
                                if(left > 0){
                                    title = "请继续监管！";
                                } else {
                                    title = "已完成监管，是否提交？";
                                }
                                new ShowMsgPopupwindow(getContext(), title, left + "", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pd = new ProgressDialog(getContext());
                                        pd.setMessage("正在提交...");
                                        pd.show();
                                        User user = new LoginRouter(getContext(), AMDatabase.getInstance()).getUser();
                                        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                                        service.addPsdyJg(user.getLoginName(), user.getId(), user.getUserName(),
                                                date, id + "", name)
                                                .subscribe(new Subscriber<Result2>() {
                                                    @Override
                                                    public void onCompleted() {

                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        pd.dismiss();
                                                        ToastUtil.shortToast(getContext(), "保存失败");
                                                    }

                                                    @Override
                                                    public void onNext(Result2 result2) {
                                                        pd.dismiss();
                                                        if(result2.getCode() == 200){
                                                            ToastUtil.shortToast(getContext(), result2.getMessage());
                                                        } else {
                                                            ToastUtil.shortToast(getContext(), "保存失败");
                                                        }
                                                    }
                                                });
                                    }
                                }).showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
                            }
                        });

            }
        });
    }

    public void setComponent(Component component){
        this.component = component;
    }

    public void setId(long id){
        this.id = id;
        type = "";
        pageNo = 1;
        loadData();
        adapter.setList(new ArrayList<JBJ>());
    }

    public void refresh(){
        adapter.notifyDataSetChanged();
    }

    private void setEmptyVisible(boolean visible){
        if(visible){
            flEmpty.setVisibility(View.VISIBLE);
        } else {
            flEmpty.setVisibility(View.GONE);

        }
    }

    public void setName(String name){
        this.name = name;
    }

    private void initData(){
        loadData();
    }

    private void loadData(){
        if(service == null || id == 0){
            return;
        }

        if(pageNo == 1){
            flLoading.setVisibility(View.VISIBLE);
        }
        service.getPsdyJgJbjList(id, pageNo, pageSize, type)
                .subscribe(new Subscriber<List<JBJ>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        flLoading.setVisibility(View.GONE);
                        e.printStackTrace();
                        if(adapter.getItemCount() == 0){
                            setEmptyVisible(true);
                        } else if(tvEmpty.getVisibility() != View.GONE){
                            setEmptyVisible(false);
                        }
                    }

                    @Override
                    public void onNext(List<JBJ> list) {
                        if (pageNo == 1) {
                            adapter.setList(list);
                            flLoading.setVisibility(View.GONE);
                        } else {
                            adapter.addList(list);
                        }
                        recyclerView.complete();
                        if (list.size() < pageSize) {
                            recyclerView.noMore();
                        }
                        if(adapter.getItemCount() == 0){
                            setEmptyVisible(true);
                        } else if(tvEmpty.getVisibility() != View.GONE){
                            setEmptyVisible(false);
                        }
                    }
                });
    }

    private String getAttrValue(Map<String, Object> attrMap, String key){
        Object value = attrMap.get(key);
        if(value == null){
            return "";
        } else {
            return value.toString();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setLeft(left);
    }

    public void setLeft(int left){
        this.left = left;
//        if(isVisible()) {
//            btnSubmit.setTranslationY(left - llKey.getHeight() - DensityUtils.dp2px(getContext(), 60));
//        }
        flEmpty.getLayoutParams().height = left - llKey.getHeight();
        flLoading.getLayoutParams().height = left - llKey.getHeight() - DensityUtils.dp2px(getContext(), 70);
        if(flEmpty.getVisibility() == View.VISIBLE){
            flEmpty.requestLayout();
        }
        if(flLoading.getVisibility() == View.VISIBLE){
            flLoading.requestLayout();
        }
    }
}