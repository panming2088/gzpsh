package com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.constant.GzpsConstant;
import com.augurit.agmobile.gzps.common.model.Result;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.service.GzpsService;
import com.augurit.agmobile.gzps.common.util.BeanUtil;
import com.augurit.agmobile.gzps.common.widget.TakePhotoTableItem;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorArg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorInfoBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.JbjMonitorService;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.JbjMonitorFragment;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.MonitorEventActivity;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.MonitorEventFragment;
import com.augurit.agmobile.gzps.publicaffair.event.UpdateAdviceEvent;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzps.uploadevent.event.UpdateAfterDeleteEvent;
import com.augurit.agmobile.gzps.uploadevent.event.UpdateAfterEditEvent;
import com.augurit.agmobile.gzps.uploadevent.event.UpdateJournalEvent;
import com.augurit.agmobile.gzps.uploadevent.model.EventDetail;
import com.augurit.agmobile.gzps.uploadevent.model.ProblemUploadBean;
import com.augurit.agmobile.gzps.uploadevent.util.CodeToStringUtils;
import com.augurit.agmobile.gzps.uploadevent.view.eventedit.EventEditActivity;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventDetail;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventProcess;
import com.augurit.agmobile.gzpssb.uploadevent.model.SharedEvent;
import com.augurit.agmobile.gzpssb.uploadevent.service.PSHUploadEventService;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventflow.PSHEventProcessView;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventflow.PshSendNextLinkActivity;
import com.augurit.agmobile.mapengine.map.geometry.LatLng;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.selectlocation.util.SelectLocationConstant;
import com.augurit.agmobile.patrolcore.selectlocation.view.SelectLocationActivity;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.service.LoginService;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 事件详情界面
 * <p>
 * 待受理  处理中  已办结
 * <p>
 * Created by xcl on 2017/11/11.
 */
public class PSHEventDetailFragment extends Fragment {

    private String[] urgency_type_array = {"一般", "较紧急", "紧急"};
    private TextItemTableItem textitem_upload_user;
    private TextItemTableItem textitem_parent_org;
    private TextItemTableItem textitem_upload_date;
    private TextItemTableItem textitem_upload_address;
    private TextView tv_check_location;
    private View ll_report_addr;
    private TextView tv_check_component_location;
    private View ll_addr;
    private TextItemTableItem textitem_upload_compttype;
    private TextItemTableItem textitem_upload_eventtype;
    private TextItemTableItem textitem_upload_urgency;
    private TextFieldTableItem textfield_description;
    private TakePhotoTableItem photo_item;
    //    private RecyclerView rv_handle_process;
//    private RecyclerView rv_advice;
//    private HandleProcessAdapter handleProcessAdapter;
//    private EventAdviceAdapter eventAdviceAdapter;
    private ProgressLinearLayout pb_loading;
    private LinearLayout ll_advice;
    private LinearLayout ll_event_process;

    private View ll_signevent;
    private View ll_eventhandle;
    private View ll_add_advice;

    private View btn_signevent;
    private View btn_rollback;
    private View btn_reassign;
    private TextView btn_submit;
    private View btn_add_journal;
    private View btn_addvice;

    private PSHUploadEventService mEventService;
    private ProgressDialog pd;

    private int journalPageNo = 1;

    private PSHEventListItem mEventListItem;
    private int eventPState = GzpsConstant.EVENT_P_STATE_HANDLING;//事件流程类型 待受理  处理中  已办结
    private PSHEventDetail.FormBean mEventDetail;

    private boolean readOnly = true;

    private String mProcInstDbId;//用于任务转派

    private String psdyJgId; //排水单元监管id

    private View ll_retrieve;
    private View btn_retrieve;

    private View ll_delete;
    private View btn_delete;

    private View ll_edit;
    private View btn_edit;
    private PSHEventDetail mDetail;
    private String state = "dcl";
    private Context mContext;
    private TextItemTableItem textitem_upload_psdy;
    private TextFieldTableItem textfield_zgjy;
    private ViewGroup ll_info;
    private ViewGroup ll_psdyjg_info;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_psh_eventdetail, null);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEventListItem = (PSHEventListItem) ((Activity) mContext).getIntent().getSerializableExtra("eventList");
        eventPState = ((Activity) mContext).getIntent().getIntExtra("eventPState", GzpsConstant.EVENT_P_STATE_HANDLING);
        state = ((Activity) mContext).getIntent().getStringExtra("state");
        readOnly = ((Activity) mContext).getIntent().getBooleanExtra("readonly", false);
        psdyJgId = ((Activity) mContext).getIntent().getStringExtra("psdyJgId");
        if (mEventListItem != null && String.valueOf(mEventListItem.getId()) != null) {
            mProcInstDbId = String.valueOf(mEventListItem.getId());
        }
        mEventService = new PSHUploadEventService(mContext.getApplicationContext());
        initView(view);
        initListener(view);
        initData();
        EventBus.getDefault().register(this);
    }

    private void initData() {
        String taskInstDbid = null;
        String procInstDbid = null;
//        if(!GzpsConstant.EVENT_OPEN.equals(mEventListItem.getState())){
//            procInstDbid = mEventListItem.getProcInstDbId();
//        } else {
//            taskInstDbid = mEventListItem.getTaskInstDbid();
//            procInstDbid = mEventListItem.getProcInstDbId();
//        }
        /*if(TextUtils.isEmpty(psdyJgId) && !TextUtils.isEmpty(mEventListItem.getJgId())){
            psdyJgId = mEventListItem.getJgId();
        }
        if(!TextUtils.isEmpty(psdyJgId)) {
            getPsdyJgDetail();
        }*/
        if ("dmjc".equals(mEventListItem.getSslx())
                || "kgjc".equals(mEventListItem.getSslx())
                || "lgjc".equals(mEventListItem.getSslx())) {
            goLgDetail(mEventListItem);
        }
        getEventDetail();
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
                            return JsonUtil.getObject(str, new TypeToken<Result3<JbjMonitorInfoBean.WtData>>() {
                            }.getType());
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
                        if (result == null || result.getCode() != 200 || result.getData() == null) {
                            return;
                        }
                        ll_info.setVisibility(View.GONE);
                        ll_psdyjg_info.setVisibility(View.VISIBLE);
                        JbjMonitorArg arg = new JbjMonitorArg();
                        arg.wtData = result.getData();
                        arg.readOnly = true;
                        int checkType = 1;
                        if ("dmjc".equals(arg.wtData.getSslx())) {
                            checkType = 1;
                        } else if ("kgjc".equals(arg.wtData.getSslx())) {
                            checkType = 2;
                        } else if ("lgjc".equals(arg.wtData.getSslx())) {
                            checkType = 3;
                        }
                        arg.checkType = checkType;
//                        arg.subtype = mEventListItem.getPshmc().substring(0, mEventListItem.getPshmc().indexOf("-"));
                        MonitorEventFragment fragment = MonitorEventFragment.getInstance(arg);
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        ft.replace(R.id.ll_psdyjg_info, fragment);
                        ft.commit();
                    }
                });
    }

    private void getPsdyJgDetail() {
        new JbjMonitorService(getContext()).getJbjJgDetail(psdyJgId)
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
                        Result3<String> reslut = JsonUtil.getObject(s, new TypeToken<Result3<String>>() {
                        }.getType());
                        if (reslut.getCode() != 200) {
                            return;
                        }
                        ll_info.setVisibility(View.GONE);
                        ll_psdyjg_info.setVisibility(View.VISIBLE);
                        JbjMonitorArg args = new JbjMonitorArg();
                        args.jbjMonitorInfoBean = JsonUtil.getObject(s, JbjMonitorInfoBean.class);
                        args.subtype = mEventListItem.getPshmc().substring(0, mEventListItem.getPshmc().indexOf("-"));
                        args.readOnly = true;
                        JbjMonitorFragment fragment = JbjMonitorFragment.getInstance(args);
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        ft.replace(R.id.ll_psdyjg_info, fragment);
                        ft.commit();
                    }
                });
    }

    private void getEventDetail() {
        PSHUploadEventService uploadEventService = new PSHUploadEventService(mContext.getApplicationContext());
        uploadEventService.getDetails(mEventListItem.getId(), state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PSHEventDetail>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogUtil.MessageBoxCannotCancel(mContext, null, "请求数据出错！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((Activity) mContext).finish();
                            }
                        });
                        /*pb_loading.showError("加载失败", "", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                initData();
                            }
                        });*/
                    }

                    @Override
                    public void onNext(PSHEventDetail result) {
                        String json = JsonUtil.getJson(result);
                        if (result == null
                                || result.getCode() != 200
                                || result.getForm() == null) {
                            DialogUtil.MessageBoxCannotCancel(mContext, null, "请求数据出错！", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((Activity) mContext).finish();
                                }
                            });
                            /*pb_loading.showError("数据为空", "", "刷新", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    initData();
                                }
                            });*/
                            return;
                        }
//                        pb_loading.showContent();
                        mDetail = result;
                        PSHEventDetail.FormBean form = result.getForm();
                        refreshView(form);

                    }
                });
    }

    private void refreshView(PSHEventDetail.FormBean eventDetail) {
        mEventDetail = eventDetail;
        /**
         * 填充事件详情
         */
        textitem_upload_user.setText(eventDetail.getSbr());
        textitem_parent_org.setText(eventDetail.getParentOrgName());
        textitem_upload_date.setText(TimeUtil.getStringTimeYMDS(new Date(eventDetail.getAppSbsj())));
        textitem_upload_address.setText(eventDetail.getSzwz());
        tv_check_location.setText(eventDetail.getReportaddr());
        tv_check_component_location.setText(eventDetail.getSzwz());
        if ("other".equals(eventDetail.getSslx())) {
            textitem_upload_psdy.setVisibility(View.VISIBLE);
            textitem_upload_compttype.setVisibility(View.GONE);
            textitem_upload_psdy.setText(StringUtil.isEmpty(eventDetail.getPsdyName()) ? "" : eventDetail.getPsdyName());
//            textitem_upload_eventtype.setVisibility(View.GONE);
//            textitem_upload_compttype.setText(eventDetail.getPshmc());
            textitem_upload_eventtype.setText(CodeToStringUtils.getSpinnerValuesByMultiCode(
                    eventDetail.getWtlx()));
        } else {
            textitem_upload_psdy.setVisibility(View.GONE);
            textitem_upload_compttype.setText(eventDetail.getPshmc());
            textitem_upload_eventtype.setText(CodeToStringUtils.getSpinnerValuesByMultiCode(
                    eventDetail.getWtlx()));
        }
//        try {
//            textitem_upload_urgency.setText(urgency_type_array[Integer.valueOf(
//                    eventDetail.getJjcd()) - 1]);
//        } catch (Exception e) {
//
//        }
        if (!StringUtil.isEmpty(eventDetail.getZgjy())) {
            textfield_zgjy.setVisibility(View.VISIBLE);
            textfield_zgjy.setText(eventDetail.getZgjy());
        } else {
            textfield_zgjy.setVisibility(View.GONE);
        }
        textfield_description.setText(eventDetail.getWtms());
        if (!ListUtil.isEmpty(eventDetail.getFiles())) {
            List<Photo> photoList = new ArrayList<>();
            for (PSHEventDetail.FormBean.FilesBean filesBean : eventDetail.getFiles()) {
                Photo photo = new Photo();
                photo.setHasBeenUp(1);
                photo.setPhotoPath(filesBean.getPath());
                photo.setThumbPath(filesBean.getThumbPath());
                photo.setPhotoName(filesBean.getName());
                photo.setField1("photo");
                photoList.add(photo);
            }
            photo_item.setSelectedPhotos(photoList);
        }

                        /*String currLink = mEventListItem.getActivityName();
                        if (eventPState == GzpsConstant.EVENT_P_STATE_UNSIGN) {
                            ll_signevent.setVisibility(View.VISIBLE);
                        }
                        if (eventPState == GzpsConstant.EVENT_P_STATE_UNFINISH) {
                            ll_add_advice.setVisibility(View.VISIBLE);
                        }
                        if (eventPState == GzpsConstant.EVENT_P_STATE_HANDLING) {
                            ll_eventhandle.setVisibility(View.VISIBLE);
                            if (GzpsConstant.LINK_GET_TASK.equals(currLink)) {
                                btn_add_journal.setVisibility(View.VISIBLE);
                            }
                        }*/

        User user = new LoginService(mContext.getApplicationContext(), AMDatabase.getInstance()).getUser();
        boolean allow = checkIfCurrentUserAllowAddAdvice();
        if (allow) {
//            ll_add_advice.setVisibility(View.VISIBLE);//TODO 暂时屏蔽领导插话
        }

        //如果当前任务状态是待办，且环节处理人是当前用户，则显示任务处理的按钮
        if (eventPState == GzpsConstant.EVENT_P_STATE_HANDLING) {
            ll_eventhandle.setVisibility(View.VISIBLE);
        }

        if (readOnly) {
            ll_eventhandle.setVisibility(View.GONE);
            ll_add_advice.setVisibility(View.GONE);
            ll_signevent.setVisibility(View.GONE);
        }

//                        photo_item.setSelectedPhotos(eventDetail.get());

                        /*if (!ListUtil.isEmpty(eventDetail.getOpinion())){
                            for (EventProcess eventProcess : eventDetail.getEventProcess()){
                                PSHEventProcessView eventProcessView = new PSHEventProcessView(PshEventDetailActivity1.this);
                                eventProcessView.initView(eventProcess);
                                eventProcessView.addTo(ll_event_process);
                            }
                        }*/

                        /*//显示领导插话（意见）列表
                        if (!ListUtil.isEmpty(eventDetail.getOpinion())) {
                            for (EventDetail.OpinionBean opinion : eventDetail.getOpinion()) {
                                AdviceView adviceView = new AdviceView(PshEventDetailActivity1.this);
                                adviceView.initView(opinion);
                                adviceView.addTo(ll_advice);
                            }
                        }*/

        if ("true".equals(mEventDetail.getIsbyself())) {
            //自行处理，不走流程
//            findViewById(R.id.tv_isbyself).setVisibility(View.VISIBLE);
        } else {
//                            getEventHandlesAndJournals();
        }
        //获取事件处理过程、施工日志及评论意见
        /*AESEncodeSJID(new Callback2<String>() {
            @Override
            public void onSuccess(String s) {
                getEventHandlesAndJournals(s);
            }

            @Override
            public void onFail(Exception error) {
                ToastUtil.shortToast(PshEventDetailActivity1.this, "无法获取处理情况");
            }
        });*/
        getEventHandlesAndJournals("" + mEventDetail.getId());

//        if (GzpsConstant.LINK_SEND_TASK.equals(mEventDetail.getCurNode())) {
//            btn_submit.setText("派发任务");
//            //    User user = new LoginService(getApplicationContext(), AMDatabase.getInstance()).getUser();
//            //如果当前角色是Rg和Rm,则任务排放按钮不可见
//            if (user.getRoleCode().contains(GzpsConstant.roleCodes[3])
//                    || user.getRoleCode().contains(GzpsConstant.roleCodes[4])) {
//                btn_submit.setVisibility(View.GONE);
//            }
//
//        } else if (GzpsConstant.LINK_FH.equals(mEventDetail.getCurNode())) {
////                            btn_submit.setText("填写意见");
//        } else if (GzpsConstant.LINK_GET_TASK.equals(mEventDetail.getCurNode())) {
//            btn_submit.setText("完成施工");
//        }
//
//        //问题上报环节不允许回退
//        if (GzpsConstant.LINK_PROBLEM_REPORT.equals(mEventDetail.getCurNode())) {
//            btn_rollback.setVisibility(View.GONE);
//        }
//
//        //任务派发环节可以转派
//        if (GzpsConstant.LINK_SEND_TASK.equals(mEventDetail.getCurNode())) {
//            btn_reassign.setVisibility(View.VISIBLE);
//        }
//
////                        handleProcessAdapter.addData(eventDetail.getEventProcess());
////                        handleProcessAdapter.notifyDataSetChanged();
////
////                        eventAdviceAdapter.addData(eventDetail.getAdvice());
////                        eventAdviceAdapter.notifyDataSetChanged();
//
//        //后端字段控制是否可以撤回
//        if (mEventDetail.isRetrieve()) {
//            ll_retrieve.setVisibility(View.VISIBLE);
//        } else {
//            ll_retrieve.setVisibility(View.GONE);
//        }
//
//        //后端字段控制是否可以删除
//        if (mEventDetail.isDeleteTask()) {
//            ll_delete.setVisibility(View.VISIBLE);
//        } else {
//            ll_delete.setVisibility(View.GONE);
//        }
//
//        //问题上报环节而且当前用户是上报人,可进行编辑问题
////        if(GzpsConstant.LINK_PROBLEM_REPORT.equals(mEventDetail.getCurNode()) &&
////                (mEventDetail.getEvent().getUserName().contains(user.getUserName()))){
////            ll_edit.setVisibility(View.VISIBLE);
////        }else {
////            ll_edit.setVisibility(View.GONE);
////        }
//        if (mEventDetail.isEditAble()) {
//            ll_edit.setVisibility(View.VISIBLE);
//        } else {
//            ll_edit.setVisibility(View.GONE);
//        }
    }

    private void initView(View view) {
        pd = new ProgressDialog(mContext);
        ((TextView) view.findViewById(R.id.tv_title)).setText("问题详情");

        ll_info = view.findViewById(R.id.ll_info);
        ll_psdyjg_info = view.findViewById(R.id.ll_psdyjg_info);
        textitem_upload_user = (TextItemTableItem) view.findViewById(R.id.textitem_upload_user);
        textitem_parent_org = (TextItemTableItem) view.findViewById(R.id.textitem_parent_org);
        textitem_upload_date = (TextItemTableItem) view.findViewById(R.id.textitem_upload_date);
        textitem_upload_address = (TextItemTableItem) view.findViewById(R.id.textitem_upload_address);
        tv_check_location = (TextView) view.findViewById(R.id.tv_check_location);
        ll_report_addr = view.findViewById(R.id.ll_report_addr);
        tv_check_component_location = (TextView) view.findViewById(R.id.tv_check_component_location);
        ll_addr = view.findViewById(R.id.ll_addr);
        textitem_upload_psdy = (TextItemTableItem) view.findViewById(R.id.textitem_upload_psdy);
        textitem_upload_compttype = (TextItemTableItem) view.findViewById(R.id.textitem_upload_compttype);
        textitem_upload_eventtype = (TextItemTableItem) view.findViewById(R.id.textitem_upload_eventtype);
        textitem_upload_urgency = (TextItemTableItem) view.findViewById(R.id.textitem_upload_urgency);
        textfield_description = (TextFieldTableItem) view.findViewById(R.id.textfield_description);
        textfield_zgjy = (TextFieldTableItem) view.findViewById(R.id.textfield_zgjy);
        photo_item = (TakePhotoTableItem) view.findViewById(R.id.photo_item);
        pb_loading = (ProgressLinearLayout) view.findViewById(R.id.pb_loading);

        textitem_upload_user.setReadOnly();
        textitem_parent_org.setEditable(false);
        textitem_upload_date.setReadOnly();
        textitem_upload_address.setReadOnly();
        textitem_upload_compttype.setEditable(false);
        textitem_upload_psdy.setEditable(false);
        textitem_upload_eventtype.setEditable(false);
        textitem_upload_urgency.setEditable(false);
        textfield_description.setReadOnly();
        textfield_zgjy.setReadOnly();
        photo_item.setAddPhotoEnable(false);
        photo_item.setTitle("现场照片");
        photo_item.setReadOnly();

        ll_advice = (LinearLayout) view.findViewById(R.id.ll_advice);
        ll_event_process = (LinearLayout) view.findViewById(R.id.ll_event_process);
        ll_signevent = view.findViewById(R.id.ll_signevent);
        ll_eventhandle = view.findViewById(R.id.ll_eventhandle);
        ll_add_advice = view.findViewById(R.id.ll_add_advice);

//        rv_handle_process = (RecyclerView) findViewById(R.id.rv_handle_process);
//        rv_handle_process.setLayoutManager(new LinearLayoutManager(this));
//        handleProcessAdapter = new HandleProcessAdapter(this,new ArrayList<EventProcess>());
//        rv_handle_process.setAdapter(handleProcessAdapter);
//
//        rv_advice = (RecyclerView) findViewById(R.id.rv_advice);
//        rv_advice.setLayoutManager(new LinearLayoutManager(this));
//        eventAdviceAdapter = new EventAdviceAdapter(this,new ArrayList<EventAdvice>());
//        rv_advice.setAdapter(eventAdviceAdapter);

        btn_signevent = view.findViewById(R.id.btn_signevent);
        btn_rollback = view.findViewById(R.id.btn_rollback);
        btn_reassign = view.findViewById(R.id.btn_reassign);
        btn_submit = (TextView) view.findViewById(R.id.btn_submit);
        btn_add_journal = view.findViewById(R.id.btn_add_journal);
        btn_addvice = view.findViewById(R.id.btn_addvice);


        String currLinkName = mEventListItem.getState();
//        String currLink = mEventListItem.getActivityName();
        /*User user = new LoginService(this.getApplicationContext(), AMDatabase.getInstance()).getUser();
        if (eventPState == GzpsConstant.EVENT_P_STATE_HANDLING
                && !user.getLoginName().equals(mEventDetail.getCurOpLoginName())) {
            ll_add_advice.setVisibility(View.VISIBLE);
        }*/
        if (eventPState == GzpsConstant.EVENT_P_STATE_HANDLING) {
            ll_eventhandle.setVisibility(View.VISIBLE);
            if (GzpsConstant.LINK_GET_TASK_NAME.equals(currLinkName)) {
                btn_add_journal.setVisibility(View.VISIBLE);
            }
        }

        if (!GzpsConstant.EVENT_OPEN.equals(mEventListItem.getState())) {
            ll_eventhandle.setVisibility(View.GONE);
        }
//        ll_eventhandle.setVisibility(View.VISIBLE);


        //处理中才会显示撤回按钮
        ll_retrieve = (View) view.findViewById(R.id.ll_retrieve);
        btn_retrieve = (Button) view.findViewById(R.id.btn_retrieve);

        if (eventPState == GzpsConstant.EVENT_P_STATE_HANDLED) {
//            ll_retrieve.setVisibility(View.VISIBLE); todo 暂时注释撤回功能
        } else {
            ll_retrieve.setVisibility(View.GONE);
        }


        //待受理中才会显示删除按钮
        ll_delete = (View) view.findViewById(R.id.ll_delete);
        btn_delete = (Button) view.findViewById(R.id.btn_delete);
        /*
        if(eventPState == GzpsConstant.EVENT_P_STATE_HANDLING){
            ll_delete.setVisibility(View.VISIBLE);
        }else {
            ll_delete.setVisibility(View.GONE);
        }
        */
        ll_edit = view.findViewById(R.id.ll_edit);
        btn_edit = view.findViewById(R.id.btn_edit);


    }

    private void initListener(View view) {
//        view.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        view.findViewById(R.id.share_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AESEncodeSJID(new Callback2<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            String url = BaseInfoManager.getSupportUrl(mContext.getApplicationContext())
                                    + "event/eventDetail.html?";
                            String param = "sjid=" + URLEncoder.encode(s);
                            url += param;
                            Intent wechatIntent = new Intent(Intent.ACTION_SEND);
                            wechatIntent.setPackage("com.tencent.mm");
                            wechatIntent.setType("text/plain");
                            wechatIntent.putExtra(Intent.EXTRA_TEXT, url);
                            startActivity(wechatIntent);
                        } catch (Exception e) {
                            ToastUtil.shortToast(mContext, "请确认手机是否已安装微信");
                        }
                    }

                    @Override
                    public void onFail(Exception error) {
                        ToastUtil.shortToast(mContext, "无法连接网络，请检查网络设置");
                    }
                });
            }
        });

        ll_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String x = String.valueOf(mEventDetail.getX());
                String y = String.valueOf(mEventDetail.getY());
                String addr = mEventDetail.getSzwz();

                if (StringUtil.isEmpty(x)
                        || StringUtil.isEmpty(y)) {
                    ToastUtil.shortToast(mContext, "地址信息缺失");
                    return;
                }

                Intent intent = new Intent(mContext, PshSelectLocationActivity.class);
                intent.putExtra(SelectLocationConstant.IF_READ_ONLY, true);
                intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_LOCATION, new LatLng(Double.valueOf(y),
                        Double.valueOf(x)));
                intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_ADDRESS, addr);
                intent.putExtra(SelectLocationConstant.INITIAL_SCALE, PatrolLayerPresenter.initScale);
                startActivity(intent);
            }
        });

        ll_report_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = String.valueOf(mEventDetail.getReportx());
                String y = String.valueOf(mEventDetail.getReporty());
                String addr = mEventDetail.getReportaddr();

                if (StringUtil.isEmpty(x)
                        || StringUtil.isEmpty(y)) {
                    ToastUtil.shortToast(mContext, "地址信息缺失");
                    return;
                }

                Intent intent = new Intent(mContext, SelectLocationActivity.class);
                intent.putExtra(SelectLocationConstant.IF_READ_ONLY, true);
                intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_LOCATION, new LatLng(Double.valueOf(y),
                        Double.valueOf(x)));
                intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_ADDRESS, addr);
                intent.putExtra(SelectLocationConstant.INITIAL_SCALE, PatrolLayerPresenter.initScale);
                startActivity(intent);
            }
        });

        btn_signevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signEvent();
            }
        });

        btn_rollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollback();
            }
        });

        btn_reassign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reassign();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        btn_add_journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, com.augurit.agmobile.gzps.uploadevent.view.eventflow.AddEventJournalActivity.class);
                intent.putExtra("taskInstDbid", mEventListItem.getId());
                intent.putExtra("sjid", mEventDetail.getId() + "");
                startActivity(intent);
            }
        });

        btn_addvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAdviceDialog();
            }
        });


        //向服务器请求撤回
        btn_retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.augurit.agmobile.gzps.uploadevent.view.eventflow.RetriveDialog dialog = com.augurit.agmobile.gzps.uploadevent.view.eventflow.RetriveDialog.getInstance(
                        String.valueOf(mProcInstDbId));
                dialog.show(getChildFragmentManager(), "retriveback");
//                AlertDialog dialog = new AlertDialog.Builder(PshEventDetailActivity1.this)
//                        .setTitle("提示:")
//                        .setMessage("确定撤回当前任务吗?")
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                retrive(null);
//                            }
//                        })
//                        .create();
//                dialog.show();
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.agmobile_red));
//                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
//                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
            }

        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("提示:")
                        .setMessage("任务删除后所有数据将无法恢复,是否确定删除当前任务?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete();
                            }
                        })
                        .create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.agmobile_red));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
            }
        });

        //重新编辑问题
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EventEditActivity.class);
                ProblemUploadBean bean = getProblemUploadBean();
                if (bean != null) {
                    intent.putExtra("preProblemUploadBean", bean);
                    intent.putExtra("reportId", mEventDetail.getId());
                }
                startActivity(intent);

            }
        });
    }


    private ProblemUploadBean getProblemUploadBean() {
        ProblemUploadBean bean = new ProblemUploadBean();
//        if (mEventDetail != null) {
//            bean.setSBR(mEventDetail.getEvent().getUserName());
//            bean.setReportaddr(mEventDetail.getEvent().getReportaddr());
//            bean.setReportx(mEventDetail.getEvent().getReportx());
//            bean.setReporty(mEventDetail.getEvent().getReporty());
//            bean.setSSLX(mEventDetail.getEvent().getComponentType());
//            bean.setBHLX(mEventDetail.getEvent().getEventType());
//            bean.setJJCD(urgency_type_array[Integer.valueOf(
//                    mEventDetail.getEvent().getUrgency()) - 1]);
//            bean.setWTMS(mEventDetail.getEvent().getDescription());
//
//            bean.setBZ(mEventDetail.getEvent().getRemark());
//            bean.setJDMC(mEventDetail.getEvent().getRoad());
//            bean.setSZWZ(mEventDetail.getEvent().getAddr());
//            bean.setX(mEventDetail.getEvent().getX());
//            bean.setY(mEventDetail.getEvent().getY());
//            bean.setLayer_id(mEventDetail.getEvent().getLayerId());
//            bean.setLayer_name(mEventDetail.getEvent().getLayerName());
//            bean.setLayerurl(mEventDetail.getEvent().getLayerurl());
//            bean.setObject_id(mEventDetail.getEvent().getObjectId());
//            bean.setUsid(mEventDetail.getEvent().getUsid());
//
//            if (!ListUtil.isEmpty(mEventDetail.getEvent().getFiles())) {
//                List<Photo> photoList = new ArrayList<>();
//                for (EventDetail.EventBean.FilesBean filesBean : mEventDetail.getEvent().getFiles()) {
//                    Photo photo = new Photo();
//                    photo.setHasBeenUp(1);
//                    photo.setPhotoPath(filesBean.getPath());
//                    photo.setField1("photo");
//                    photoList.add(photo);
//                }
//                bean.setPhotos(photoList);
//            }
//        }
        return bean;

    }

    /**
     * 撤回
     */
//    private void retrive(String retrieveComments){
//        pd.show();
//        mEventService.wfRetrive(mProcInstDbId,retrieveComments)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Result<String>>() {
//                    @Override
//                    public void onCompleted() {
//                        pd.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        pd.dismiss();
//                    }
//
//                    @Override
//                    public void onNext(Result<String> stringResult) {
//                        pd.dismiss();
//                        if(stringResult.isSuccess()){
//                            ToastUtil.longToast(PshEventDetailActivity1.this,"撤回成功!");
//                            EventBus.getDefault().post(new UpdateAfterRetriveEvent(mProcInstDbId));
//
//                            finish();
//                        }else {
//                            if(!TextUtils.isEmpty(stringResult.getMessage())){
//                                ToastUtil.longToast(PshEventDetailActivity1.this,stringResult.getMessage());
//                            }else {
//                                ToastUtil.longToast(PshEventDetailActivity1.this,"撤回失败!");
//                            }
//                        }
//                    }
//                });
//
//    }

    /**
     * 删除
     */
    private void delete() {
        pd.show();
        mEventService.wfDelete(String.valueOf(mEventDetail.getId()), mProcInstDbId, mEventDetail.getObjectId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                        pd.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        pd.dismiss();
                    }

                    @Override
                    public void onNext(Result<String> stringResult) {
                        pd.dismiss();
                        if (stringResult != null && stringResult.isSuccess()) {
                            ToastUtil.longToast(mContext, "删除成功!");
                            EventBus.getDefault().post(new UpdateAfterDeleteEvent(mProcInstDbId));
                            ((Activity) mContext).finish();
                        } else {
                            if (!TextUtils.isEmpty(stringResult.getMessage())) {
                                ToastUtil.longToast(mContext, stringResult.getMessage());
                            } else {
                                ToastUtil.longToast(mContext, "无法删除!");
                            }
                        }
                    }
                });

    }

    /**
     * 加密问题ID，然后获取问题处理情况、施工日志、管理层意见
     */
    private void AESEncodeSJID(final Callback2<String> callback) {
        GzpsService gzpsService = new GzpsService(mContext.getApplicationContext());
        gzpsService.AESEncode("" + mEventDetail.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result2<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.shortToast(mContext, "无法获取处理情况！");
                    }

                    @Override
                    public void onNext(Result2<String> stringResult2) {
                        if (stringResult2.getCode() == 200
                                && !StringUtil.isEmpty(stringResult2.getData())) {
                            callback.onSuccess(stringResult2.getData());
                        } else {
                            callback.onFail(new Exception(""));
                        }

                    }
                });
    }

    /**
     * 获取处理情况、施工日志及评论意见
     */
    private void getEventHandlesAndJournals(String sjid) {

        mEventService.getSggcLogList(sjid + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PSHEventProcess>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                    }

                    @Override
                    public void onNext(List<PSHEventProcess> eventProcessList) {
                        journalPageNo++;
                        if (!ListUtil.isEmpty(eventProcessList)) {
                            ll_advice.removeAllViews();
                            ll_event_process.removeAllViews();


                            List<PSHEventProcess> realEventProcess = new ArrayList<>();
                            for (PSHEventProcess eventProcess : eventProcessList) {
                                if (!StringUtil.isEmpty(eventProcess.getLx())
                                        && eventProcess.getLx().equals("100")) {
                                    EventDetail.OpinionBean opinion = new EventDetail.OpinionBean();
                                    opinion.setOpinion(eventProcess.getContent());
                                    opinion.setUserName(eventProcess.getUsername());
                                    opinion.setTime(eventProcess.getAppTime());
                                    //显示领导插话（意见）列表
                                    com.augurit.agmobile.gzps.uploadevent.view.eventflow.AdviceView adviceView = new com.augurit.agmobile.gzps.uploadevent.view.eventflow.AdviceView(mContext);
                                    adviceView.initView(opinion);
                                    adviceView.addTo(ll_advice);
                                } else {
                                    realEventProcess.add(eventProcess);
                                }
                            }

                            if ("true".equals(mEventDetail.getIsbyself())) {
                                //如果是自行处理，不显示处理过程
                                return;
                            }

                            int index = 0;
                            boolean isFinished = false;
                            /*if(GzpsConstant.LINK_FH.equals(mEventDetail.getCurNode())
                                    && mEventDetail.getEventState().contains("已完成")){
                                isFinished = true;
                            }*/
                            if (StringUtil.isEmpty(mEventDetail.getState())
                                    || "3".equals(mEventDetail.getState())) {
                                isFinished = true;
                            }
                            for (PSHEventProcess eventProcess : realEventProcess) {

//                                if (!isFinished && (index == realEventProcess.size() - 1)) {
//                                    continue;
//                                }
                                PSHEventProcessView eventProcessView = new PSHEventProcessView(mContext);
                                eventProcessView.initView(eventProcess, isFinished, index, realEventProcess.size());
                                eventProcessView.addTo(ll_event_process);

                                index++;
                            }
                        }
                    }
                });
    }

    /**
     * 检查当前用户是否允许对该事件发表意见
     *
     * @return
     */
    private boolean checkIfCurrentUserAllowAddAdvice() {
        boolean allow = false;
        User user = new LoginService(mContext.getApplicationContext(), AMDatabase.getInstance()).getUser();

        for (String district : GzpsConstant.districtsSimple) {
            if (user.getOrgName().contains(district)) {   //筛选出用户所在机构
                if (mEventDetail.getParentOrgName().contains(district)) {   //事件上报所在区域
                    if (user.getRoleCode().contains(GzpsConstant.roleCodes[3])
                            || user.getRoleCode().contains(GzpsConstant.roleCodes[4])) {
                        //Rg和Rm角色才能发表意见
                        allow = true;
                    }
                }
            }

        }
        if (user.getOrgName().contains(GzpsConstant.districtsSimple[0])) {
            if (user.getRoleCode().contains(GzpsConstant.roleCodes[3])
                    || user.getRoleCode().contains(GzpsConstant.roleCodes[4])) {
                //Rg和Rm角色才能发表意见
                allow = true;
            }
        }
        return allow;
    }

    private void signEvent() {
        mEventService.signEvent(String.valueOf(mEventListItem.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        pd.show();
                    }
                })
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        pd.dismiss();
                        ToastUtil.shortToast(mContext, "操作失败，请重试！");
                    }

                    @Override
                    public void onNext(Boolean s) {
                        if (s) {
                            pd.dismiss();
                            ToastUtil.shortToast(mContext, "操作成功");
                            ((Activity) mContext).setResult(123);
                            ((Activity) mContext).finish();
                        } else {
                            pd.dismiss();
                            ToastUtil.shortToast(mContext, "操作失败，请重试！");
                        }
                    }
                });
    }

    /**
     * 回退
     */
    private void rollback() {
        com.augurit.agmobile.gzps.uploadevent.view.eventflow.RollBackDialog rollBackDialog = com.augurit.agmobile.gzps.uploadevent.view.eventflow.RollBackDialog.getInstance(
                String.valueOf(mEventDetail.getId()),
                String.valueOf(mEventListItem.getId()),
                mEventDetail.getCode());
        rollBackDialog.show(getChildFragmentManager(), "rollback");
    }

    /**
     * 转派
     */
    private void reassign() {
        String activityName = mEventListItem.getState();
        long sjid = mEventDetail.getId(); //masterEntityKey

        com.augurit.agmobile.gzps.uploadevent.view.eventflow.ReassignDialog reassignDialog = com.augurit.agmobile.gzps.uploadevent.view.eventflow.ReassignDialog.getInstance(mEventDetail.getObjectId(),
                mEventDetail.getParentOrgId(), mEventDetail.getParentOrgName(), mProcInstDbId,
                activityName, String.valueOf(sjid));
        reassignDialog.show(getChildFragmentManager(), "reassign");
    }

    private void submit() {
        ArrayList<PSHEventDetail.NextlinkBean> nextlinkBeans = new ArrayList<>(mDetail.getNextlink());
        Intent intent = new Intent(mContext, PshSendNextLinkActivity.class);
        intent.putExtra("taskInstId", mEventListItem.getId() + "");
        intent.putExtra("wtsbId", mEventListItem.getId());
        intent.putExtra("currLinkCode", mEventDetail.getId());
        intent.putExtra("state", mEventDetail.getState());
        intent.putExtra("name", mDetail.getName());

        intent.putExtra("procInstId", mProcInstDbId);
        intent.putExtra("sjid", String.valueOf(mEventDetail.getId()));

        intent.putExtra("nextLink", nextlinkBeans);
        startActivityForResult(intent, 456);
        /*SendNextLinkDialog sendNextLinkDialog = SendNextLinkDialog.getInstance(mEventListItem.getTaskInstDbid(),
                mEventListItem.getActivityName(), nextlinkBeans);
        sendNextLinkDialog.show(getSupportFragmentManager(), "addEventAdvice");*/
    }

    private void showAddAdviceDialog() {
        com.augurit.agmobile.gzps.uploadevent.view.eventflow.AddEventAdviceDialog addEventAdviceDialog = com.augurit.agmobile.gzps.uploadevent.view.eventflow.AddEventAdviceDialog.getInstance(mEventListItem.getPshmc(), mEventDetail.getId() + "");
        addEventAdviceDialog.show(getChildFragmentManager(), "addEventAdvice");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 456
                && resultCode == 456) {
            ((Activity) mContext).setResult(123);
            ((Activity) mContext).finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateAdviceEvent(UpdateAdviceEvent event) {
        if (event != null && event.getOpinion() != null) {
            com.augurit.agmobile.gzps.uploadevent.view.eventflow.AdviceView adviceView = new com.augurit.agmobile.gzps.uploadevent.view.eventflow.AdviceView(mContext);
            adviceView.initView(event.getOpinion());
            adviceView.addTo(ll_advice);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateJounaryEvent(UpdateJournalEvent event) {

        getEventHandlesAndJournals("" + mEventDetail.getId());
        /*AESEncodeSJID(new Callback2<String>() {
            @Override
            public void onSuccess(String s) {
                getEventHandlesAndJournals(s);
            }

            @Override
            public void onFail(Exception error) {
                ToastUtil.shortToast(PshEventDetailActivity1.this, "无法获取处理情况");
            }
        });*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEditEvent(UpdateAfterEditEvent editEvent) {
        if (editEvent.getUpdateState().equals(UpdateAfterEditEvent.UPDATE_DETAIL)) {
            //编辑成功,重新刷新详情页面
            initData();
        } else if (editEvent.getUpdateState().equals(UpdateAfterEditEvent.UPDATE_LIST)) {
            //编辑成功,并且在编辑中已经指派了下一环节处理人了,则退出当前详情页面,并且刷新列表
            EventBus.getDefault().post(new UpdateAfterDeleteEvent(mProcInstDbId));
            ((Activity) mContext).finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSharedEvent(SharedEvent event) {
        AESEncodeSJID(new Callback2<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    String url = BaseInfoManager.getSupportUrl(mContext.getApplicationContext())
                            + "event/eventDetail.html?";
                    String param = "sjid=" + URLEncoder.encode(s);
                    url += param;
                    Intent wechatIntent = new Intent(Intent.ACTION_SEND);
                    wechatIntent.setPackage("com.tencent.mm");
                    wechatIntent.setType("text/plain");
                    wechatIntent.putExtra(Intent.EXTRA_TEXT, url);
                    startActivity(wechatIntent);
                } catch (Exception e) {
                    ToastUtil.shortToast(mContext, "请确认手机是否已安装微信");
                }
            }

            @Override
            public void onFail(Exception error) {
                ToastUtil.shortToast(mContext, "无法连接网络，请检查网络设置");
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mContext = context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mContext = activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }
}
