package com.augurit.agmobile.gzps.drainage_unit_monitor.widget;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.RefreshLGEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.SaveCheckEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.adapter.LGAdapter;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorArg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorInfoBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyWtjc;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.JbjMonitorService;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.MonitorService;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.MonitorEventActivity;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.RiserPipeProblemActivity;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.uploadfacility.service.CorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.service.UploadFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.util.UploadLayerFieldKeyConstant;
import com.augurit.agmobile.gzpssb.journal.service.DialyPatrolService;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.pshpublicaffair.service.PSHAffairService;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.PshEventAffairDetailActivity;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DMFragment extends Fragment {
    private EditText etKey;
    private ImageView ivSearch;
    private AutoLoadRecyclerView recyclerView;
    private LinearLayout llKey;
    private TextView tvReport;
    private TextView tvEmpty;
    private FrameLayout flEmpty;
    private LinearLayout llQuestion;
    private LinearLayout llItem;
    private FrameLayout flLoading;
    private LGAdapter adapter;
    private MonitorService service;
    private DialyPatrolService mPatrolService;
    private ProgressDialog pd;
    private LinearLayout llReporter;
    private TextView tvReporter;
    private LinearLayout llOperation;
    private Button btnReset;
    private Button btnSave;

    private Component component;
    private String type = null;

    private int pageNo = 1;
    private final int pageSize = 15;
    private String keyword;
    private boolean hasCreate = false;
    private int height;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_drainage_monitor_detail_lg, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        service = new MonitorService(getContext());
        hasCreate = true;
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
    public void refreshLG(RefreshLGEvent event) {
        setEmptyVisible(false);
        recyclerView.refresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void saveEvent(SaveCheckEvent event) {
        if(event.type == 0){
            llQuestion.setVisibility(View.GONE);
            llKey.setVisibility(View.VISIBLE);
            setEmptyVisible(false);
            recyclerView.refresh();
            type = null;
        }
    }

    private void initView() {
        llKey = (LinearLayout) getView().findViewById(R.id.ll_key);
        etKey = (EditText) getView().findViewById(R.id.et_key);
        flEmpty = (FrameLayout) getView().findViewById(R.id.fl_empty);
        flLoading = (FrameLayout) getView().findViewById(R.id.fl_loading);
        llQuestion = (LinearLayout) getView().findViewById(R.id.ll_question);
        llItem = (LinearLayout) getView().findViewById(R.id.ll_item);
        ivSearch = (ImageView) getView().findViewById(R.id.iv_search);
        recyclerView = (AutoLoadRecyclerView) getView().findViewById(R.id.recycler_view);
        tvEmpty = (TextView) getView().findViewById(R.id.tv_empty);
        tvReport = (TextView) getView().findViewById(R.id.tv_report);


        llReporter = (LinearLayout) getView().findViewById(R.id.ll_reporter);
        tvReporter = (TextView) getView().findViewById(R.id.tv_reporter);
        llOperation = (LinearLayout) getView().findViewById(R.id.ll_operation);
        btnReset = (Button) getView().findViewById(R.id.btn_reset);
        btnSave = (Button) getView().findViewById(R.id.btn_save);

        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventUpload();
            }
        });

        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmptyVisible(false);
                recyclerView.refresh();
            }
        });
        adapter = new LGAdapter(getContext());
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
        adapter.setOnItemClickListener(new LGAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, PSHEventListItem data, int position) {
                if ("psh".equals(data.getSslx())) {
                    getPSHUnitDetail(data.getPshid(), data);
                } else if ("jbj".equals(data.getSslx()) || "jhj".equals(data.getSslx())) {
                    getjbjhDetail(data);
                } else if (("dmjc".equals(data.getSslx())
                        || "kgjc".equals(data.getSslx()))
                        && data.getPshid() != null && data.getPshid() != 0) {
                    getjbjhDetail(data);
                } else {
                    goDetail(data, null, null, null);
                }
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = etKey.getText().toString();
                pageNo = 1;
                loadData();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetQuestion();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = llItem.getChildCount();
                for(int i = 0; i < count; i++){
                    if(!((RadioButton)llItem.getChildAt(i).findViewById(R.id.rb_right)).isChecked()){
                        ToastUtil.shortToast(getContext(), "请选择完监管项目后再保存");
                        return;
                    }
                }
                Map<String, Object> map = component.getGraphic().getAttributes();
                User user = new LoginRouter(getContext(), AMDatabase.getInstance()).getUser();
                service.addPsdyWtjc(user.getLoginName(), getAttrValue(map, "OBJECTID"), getAttrValue(map, "单元名称"), "0", "0")
                        .subscribe(new Subscriber<Result2>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Result2 result2) {
                                if(result2.getCode() == 200){
                                    llReporter.setVisibility(View.VISIBLE);
                                    llOperation.setVisibility(View.GONE);
                                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                                    User user = new LoginRouter(getContext(), AMDatabase.getInstance()).getUser();
                                    tvReporter.setText("上报人：" + user.getUserName() + " " + date);
                                    type = "0";
                                } else {
                                    ToastUtil.shortToast(getContext(), result2.getMessage());
                                }
                            }
                        });
            }
        });
    }

    private void initData() {
        if (component != null) {
            llItem.removeAllViews();

            pageNo = 1;
            loadData();
            adapter.setList(new ArrayList());
        }
    }

    public void reset() {
        keyword = null;
        etKey.setText("");
        if (hasCreate) {
            llItem.removeAllViews();

            pageNo = 1;
            loadData();
            adapter.setList(new ArrayList());
        }
    }

    private void getPsdyWtjc(){
        service.getPsdyWtjc(component.getObjectId() + "", "0")
                .subscribe(new Subscriber<PsdyWtjc>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        setEmptyVisible(true);
                    }

                    @Override
                    public void onNext(PsdyWtjc psdyWtjc) {
                        setEmptyVisible(false);
                        if("1".equals(psdyWtjc.getYwjc())){
                            llQuestion.setVisibility(View.GONE);
                            llKey.setVisibility(View.VISIBLE);
                        } else {
                            llQuestion.setVisibility(View.VISIBLE);
                            llKey.setVisibility(View.GONE);
                            long time = 0;
                            if(psdyWtjc.getMarkTime() != null){
                                type = "0";
                                time = psdyWtjc.getMarkTime().getTime();
                            } else {
                                type = null;
                            }
                            setQuestion(psdyWtjc.getMarkPerson(), time);
                        }

                    }
                });
    }

    public String getType(){
        return type;
    }

    private void setQuestion(String reporter, long time){
        llItem.addView(createQuestionCheck("污水冒溢"));
        llItem.addView(createQuestionCheck("井盖变形/破损/缺失"));
        llItem.addView(createQuestionCheck("井盖明显松动有异响"));
        llItem.addView(createQuestionCheck("井盖标识错误"));
        llItem.addView(createQuestionCheck("井盖被埋没/违章占压"));

        if(TextUtils.isEmpty(reporter)){
            llReporter.setVisibility(View.GONE);
            llOperation.setVisibility(View.VISIBLE);
        } else {
            llReporter.setVisibility(View.VISIBLE);
            llOperation.setVisibility(View.GONE);
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(time));
            tvReporter.setText("上报人：" + reporter + " " + date);
            int count = llItem.getChildCount();
            for(int i = 0; i < count; i++){
                ((RadioButton)llItem.getChildAt(i).findViewById(R.id.rb_right)).setChecked(true);
            }
        }
    }

    private void resetQuestion(){
        int count = llItem.getChildCount();
        for(int i = 0; i < count; i++){
            ((RadioButton)llItem.getChildAt(i).findViewById(R.id.rb_left)).setChecked(false);
            ((RadioButton)llItem.getChildAt(i).findViewById(R.id.rb_right)).setChecked(false);
        }
    }

    private View createQuestionCheck(String title){
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_lgjc, llItem, false);
        ((TextView)view.findViewById(R.id.tv_left)).setText(title);
        view.findViewById(R.id.rb_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final boolean isCheck = ((RadioButton)view.findViewById(R.id.rb_right)).isChecked();
                ((RadioButton)view.findViewById(R.id.rb_right)).setChecked(false);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((RadioButton)view.findViewById(R.id.rb_left)).setChecked(false);
                        ((RadioButton)view.findViewById(R.id.rb_right)).setChecked(isCheck);
                    }
                }, 1000);
                addEventUpload();
            }
        });
        view.findViewById(R.id.rb_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((RadioButton)view.findViewById(R.id.rb_left)).setChecked(false);
            }
        });
        return view;
    }

    private void setEmptyVisible(boolean visible){
        if(visible){
            flEmpty.setVisibility(View.VISIBLE);
        } else {
            flEmpty.setVisibility(View.GONE);
        }
    }

    public void refresh(){
        adapter.notifyDataSetChanged();
    }

    private void loadData() {
        if(pageNo == 1){
            flLoading.setVisibility(View.VISIBLE);
        }
        service.getEventList(pageNo, pageSize, "dmjc", null, component.getObjectId() + "", keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PSHEventListItem>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        flLoading.setVisibility(View.GONE);
                        if(adapter.getItemCount() == 0){
                            setEmptyVisible(true);
                        } else if(tvEmpty.getVisibility() != View.GONE){
                            setEmptyVisible(false);
                        }
                    }

                    @Override
                    public void onNext(List<PSHEventListItem> list) {
                        if (pageNo == 1) {
                            flLoading.setVisibility(View.GONE);
                            adapter.setList(list);
                        } else {
                            adapter.addList(list);
                        }
                        recyclerView.complete();
                        if (list.size() < pageSize) {
                            recyclerView.noMore();
                        }
                        if(adapter.getItemCount() == 0){
                            setEmptyVisible(true);
                            if(TextUtils.isEmpty(keyword)) {
                                getPsdyWtjc();
                            }
                        } else if(tvEmpty.getVisibility() != View.GONE){
                            setEmptyVisible(false);
                            type = "-1";
                        }
                    }
                });
    }

    public void getPSHUnitDetail(Long unitId, final PSHEventListItem selectdData) {
        if (mPatrolService == null) {
            mPatrolService = new DialyPatrolService(getContext());
        }
        pd = new ProgressDialog(getContext());
        pd.setMessage("请稍等...");
        pd.show();
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
                        pd.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                        goDetail(selectdData, null, null, null);
                    }

                    @Override
                    public void onNext(PSHAffairDetail pshAffairDetail) {
                        goDetail(selectdData, pshAffairDetail, null, null);
                    }
                });
    }

    private void getjbjhDetail(final PSHEventListItem selectdData) {
        pd = new ProgressDialog(getContext());
        pd.setMessage("请稍等...");
        pd.show();
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
                            pd.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            pd.dismiss();
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
                            pd.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            pd.dismiss();
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
        Intent intent = new Intent(getContext(), PshEventAffairDetailActivity.class);
        intent.putExtra("fromPSHAffair", true);
        intent.putExtra("fromMyUpload", true);
        intent.putExtra("showDelBtn", true);
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

    private void goLgDetail(PSHEventListItem selectdData) {
        String wtsbId = String.valueOf(selectdData.getId());
        String type = "sb";
        new JbjMonitorService(getContext()).getLgjcDetail(wtsbId, type)
                .map(new Func1<ResponseBody, Result3<JbjMonitorInfoBean.WtData>>() {
                    @Override
                    public Result3<JbjMonitorInfoBean.WtData> call(ResponseBody responseBody) {
                        try {
                            String str = responseBody.string();
                            return JsonUtil.getObject(str, new TypeToken<Result3<JbjMonitorInfoBean.WtData>>(){}.getType());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result3<JbjMonitorInfoBean.WtData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result3<JbjMonitorInfoBean.WtData> result) {
                        if(result == null || result.getCode() != 200 || result.getData() == null){
                            ToastUtil.shortToast(getContext(), "获取详情失败");
                            return;
                        }
                        JbjMonitorArg arg = new JbjMonitorArg();
                        arg.wtData = result.getData();
                        arg.readOnly = true;
                        RiserPipeProblemActivity.start(getActivity(), arg);
                    }
                });
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    private String getAttrValue(Map<String, Object> attrMap, String key) {
        Object value = attrMap.get(key);
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }

    private void addEventUpload(){
        JbjMonitorArg args = new JbjMonitorArg();
        Map<String, Object> map = component.getGraphic().getAttributes();
//                args.psdyId = getAttrValue(map, "编号");
        args.psdyId = getAttrValue(map, "OBJECTID");
        args.psdyName = getAttrValue(map, "单元名称");
        args.checkType = 1;
        MonitorEventActivity.start(getContext(), args);
    }

    @Override
    public void onResume() {
        super.onResume();
        setHeight(height);
    }

    public void setHeight(int height) {
        this.height = height;
        if (isVisible()) {
            tvReport.setTranslationY(height - tvReport.getHeight() - llKey.getHeight() - DensityUtils.dp2px(getContext(), 10));
        }
        flEmpty.getLayoutParams().height = height - llKey.getHeight();
        llQuestion.getLayoutParams().height = height;
        flLoading.getLayoutParams().height = height - llKey.getHeight() - DensityUtils.dp2px(getContext(), 40);
        if(flEmpty.getVisibility() == View.VISIBLE){
            flEmpty.requestLayout();
        }
        if(flLoading.getVisibility() == View.VISIBLE){
            flLoading.requestLayout();
        }
        if(llQuestion.getVisibility() == View.VISIBLE){
            llQuestion.requestLayout();
        }

    }
}
