package com.augurit.agmobile.gzpssb.uploadevent.view.eventlist;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.constant.GzpsConstant;
import com.augurit.agmobile.gzps.common.model.Result;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.service.GzpsService;
import com.augurit.agmobile.gzps.common.widget.TakePhotoTableItem;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.publicaffair.event.UpdateAdviceEvent;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * ??????????????????
 * <p>
 * ?????????  ?????????  ?????????
 * <p>
 * Created by xcl on 2017/11/11.
 */
public class PSHEventDetailActivity extends BaseActivity {

    private String[] urgency_type_array = {"??????", "?????????", "??????"};
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
    private int eventPState = GzpsConstant.EVENT_P_STATE_HANDLING;//?????????????????? ?????????  ?????????  ?????????
    private PSHEventDetail.FormBean mEventDetail;

    private boolean readOnly = true;

    private String mProcInstDbId;//??????????????????

    private View ll_retrieve;
    private View btn_retrieve;

    private View ll_delete;
    private View btn_delete;

    private View ll_edit;
    private View btn_edit;
    private PSHEventDetail mDetail;
    private String state = "dcl";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psh_eventdetail);
        mEventListItem = (PSHEventListItem) getIntent().getSerializableExtra("eventList");
        eventPState = getIntent().getIntExtra("eventPState", GzpsConstant.EVENT_P_STATE_HANDLING);
        state = getIntent().getStringExtra("state");
        readOnly = getIntent().getBooleanExtra("readonly", false);
        if (mEventListItem != null && String.valueOf(mEventListItem.getId()) != null) {
            mProcInstDbId = String.valueOf(mEventListItem.getId());
        }
        mEventService = new PSHUploadEventService(this.getApplicationContext());
        initView();
        initListener();
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
        PSHUploadEventService uploadEventService = new PSHUploadEventService(getApplicationContext());
        uploadEventService.getDetails(mEventListItem.getId(), state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PSHEventDetail>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogUtil.MessageBoxCannotCancel(PSHEventDetailActivity.this, null, "?????????????????????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        /*pb_loading.showError("????????????", "", "??????", new View.OnClickListener() {
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
                            DialogUtil.MessageBoxCannotCancel(PSHEventDetailActivity.this, null, "?????????????????????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            /*pb_loading.showError("????????????", "", "??????", new View.OnClickListener() {
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
         * ??????????????????
         */
        textitem_upload_user.setText(eventDetail.getSbr());
        textitem_parent_org.setText(eventDetail.getParentOrgName());
        textitem_upload_date.setText(TimeUtil.getStringTimeYMDS(new Date(eventDetail.getAppSbsj())));
        textitem_upload_address.setText(eventDetail.getSzwz());
        tv_check_location.setText(eventDetail.getReportaddr());
        tv_check_component_location.setText(eventDetail.getSzwz());
        textitem_upload_compttype.setText(eventDetail.getPshmc());
        textitem_upload_eventtype.setText(CodeToStringUtils.getSpinnerValuesByMultiCode(
                eventDetail.getWtlx()));
        try {
            textitem_upload_urgency.setText(urgency_type_array[Integer.valueOf(
                    eventDetail.getJjcd()) - 1]);
        } catch (Exception e) {

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

        User user = new LoginService(PSHEventDetailActivity.this.getApplicationContext(), AMDatabase.getInstance()).getUser();
        boolean allow = checkIfCurrentUserAllowAddAdvice();
        if (allow) {
//            ll_add_advice.setVisibility(View.VISIBLE);//TODO ????????????????????????
        }

        //??????????????????????????????????????????????????????????????????????????????????????????????????????
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

                        /*//????????????????????????????????????
                        if (!ListUtil.isEmpty(eventDetail.getOpinion())) {
                            for (EventDetail.OpinionBean opinion : eventDetail.getOpinion()) {
                                AdviceView adviceView = new AdviceView(PshEventDetailActivity1.this);
                                adviceView.initView(opinion);
                                adviceView.addTo(ll_advice);
                            }
                        }*/

        if ("true".equals(mEventDetail.getIsbyself())) {
            //???????????????????????????
            findViewById(R.id.tv_isbyself).setVisibility(View.VISIBLE);
        } else {
//                            getEventHandlesAndJournals();
        }
        //??????????????????????????????????????????????????????
        /*AESEncodeSJID(new Callback2<String>() {
            @Override
            public void onSuccess(String s) {
                getEventHandlesAndJournals(s);
            }

            @Override
            public void onFail(Exception error) {
                ToastUtil.shortToast(PshEventDetailActivity1.this, "????????????????????????");
            }
        });*/
        getEventHandlesAndJournals("" + mEventDetail.getId());

//        if (GzpsConstant.LINK_SEND_TASK.equals(mEventDetail.getCurNode())) {
//            btn_submit.setText("????????????");
//            //    User user = new LoginService(getApplicationContext(), AMDatabase.getInstance()).getUser();
//            //?????????????????????Rg???Rm,??????????????????????????????
//            if (user.getRoleCode().contains(GzpsConstant.roleCodes[3])
//                    || user.getRoleCode().contains(GzpsConstant.roleCodes[4])) {
//                btn_submit.setVisibility(View.GONE);
//            }
//
//        } else if (GzpsConstant.LINK_FH.equals(mEventDetail.getCurNode())) {
////                            btn_submit.setText("????????????");
//        } else if (GzpsConstant.LINK_GET_TASK.equals(mEventDetail.getCurNode())) {
//            btn_submit.setText("????????????");
//        }
//
//        //?????????????????????????????????
//        if (GzpsConstant.LINK_PROBLEM_REPORT.equals(mEventDetail.getCurNode())) {
//            btn_rollback.setVisibility(View.GONE);
//        }
//
//        //??????????????????????????????
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
//        //????????????????????????????????????
//        if (mEventDetail.isRetrieve()) {
//            ll_retrieve.setVisibility(View.VISIBLE);
//        } else {
//            ll_retrieve.setVisibility(View.GONE);
//        }
//
//        //????????????????????????????????????
//        if (mEventDetail.isDeleteTask()) {
//            ll_delete.setVisibility(View.VISIBLE);
//        } else {
//            ll_delete.setVisibility(View.GONE);
//        }
//
//        //????????????????????????????????????????????????,?????????????????????
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

    private void initView() {
        pd = new ProgressDialog(this);
        ((TextView) findViewById(R.id.tv_title)).setText("????????????");

        textitem_upload_user = (TextItemTableItem) findViewById(R.id.textitem_upload_user);
        textitem_parent_org = (TextItemTableItem) findViewById(R.id.textitem_parent_org);
        textitem_upload_date = (TextItemTableItem) findViewById(R.id.textitem_upload_date);
        textitem_upload_address = (TextItemTableItem) findViewById(R.id.textitem_upload_address);
        tv_check_location = (TextView) findViewById(R.id.tv_check_location);
        ll_report_addr = findViewById(R.id.ll_report_addr);
        tv_check_component_location = (TextView) findViewById(R.id.tv_check_component_location);
        ll_addr = findViewById(R.id.ll_addr);
        textitem_upload_compttype = (TextItemTableItem) findViewById(R.id.textitem_upload_compttype);
        textitem_upload_eventtype = (TextItemTableItem) findViewById(R.id.textitem_upload_eventtype);
        textitem_upload_urgency = (TextItemTableItem) findViewById(R.id.textitem_upload_urgency);
        textfield_description = (TextFieldTableItem) findViewById(R.id.textfield_description);
        photo_item = (TakePhotoTableItem) findViewById(R.id.photo_item);
        pb_loading = (ProgressLinearLayout) findViewById(R.id.pb_loading);

        textitem_upload_user.setReadOnly();
        textitem_parent_org.setEditable(false);
        textitem_upload_date.setReadOnly();
        textitem_upload_address.setReadOnly();
        textitem_upload_compttype.setEditable(false);
        textitem_upload_eventtype.setEditable(false);
        textitem_upload_urgency.setEditable(false);
        textfield_description.setReadOnly();
        photo_item.setAddPhotoEnable(false);
        photo_item.setTitle("????????????");
        photo_item.setReadOnly();

        ll_advice = (LinearLayout) findViewById(R.id.ll_advice);
        ll_event_process = (LinearLayout) findViewById(R.id.ll_event_process);
        ll_signevent = findViewById(R.id.ll_signevent);
        ll_eventhandle = findViewById(R.id.ll_eventhandle);
        ll_add_advice = findViewById(R.id.ll_add_advice);

//        rv_handle_process = (RecyclerView) findViewById(R.id.rv_handle_process);
//        rv_handle_process.setLayoutManager(new LinearLayoutManager(this));
//        handleProcessAdapter = new HandleProcessAdapter(this,new ArrayList<EventProcess>());
//        rv_handle_process.setAdapter(handleProcessAdapter);
//
//        rv_advice = (RecyclerView) findViewById(R.id.rv_advice);
//        rv_advice.setLayoutManager(new LinearLayoutManager(this));
//        eventAdviceAdapter = new EventAdviceAdapter(this,new ArrayList<EventAdvice>());
//        rv_advice.setAdapter(eventAdviceAdapter);

        btn_signevent = findViewById(R.id.btn_signevent);
        btn_rollback = findViewById(R.id.btn_rollback);
        btn_reassign = findViewById(R.id.btn_reassign);
        btn_submit = (TextView) findViewById(R.id.btn_submit);
        btn_add_journal = findViewById(R.id.btn_add_journal);
        btn_addvice = findViewById(R.id.btn_addvice);


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


        //?????????????????????????????????
        ll_retrieve = (View) findViewById(R.id.ll_retrieve);
        btn_retrieve = (Button) findViewById(R.id.btn_retrieve);

        if (eventPState == GzpsConstant.EVENT_P_STATE_HANDLED) {
//            ll_retrieve.setVisibility(View.VISIBLE); todo ????????????????????????
        } else {
            ll_retrieve.setVisibility(View.GONE);
        }


        //????????????????????????????????????
        ll_delete = (View) findViewById(R.id.ll_delete);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        /*
        if(eventPState == GzpsConstant.EVENT_P_STATE_HANDLING){
            ll_delete.setVisibility(View.VISIBLE);
        }else {
            ll_delete.setVisibility(View.GONE);
        }
        */
        ll_edit = findViewById(R.id.ll_edit);
        btn_edit = findViewById(R.id.btn_edit);


    }

    private void initListener() {
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.share_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AESEncodeSJID(new Callback2<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            String url = BaseInfoManager.getSupportUrl(PSHEventDetailActivity.this.getApplicationContext())
                                    + "event/eventDetail.html?";
                            String param = "sjid=" + URLEncoder.encode(s);
                            url += param;
                            Intent wechatIntent = new Intent(Intent.ACTION_SEND);
                            wechatIntent.setPackage("com.tencent.mm");
                            wechatIntent.setType("text/plain");
                            wechatIntent.putExtra(Intent.EXTRA_TEXT, url);
                            startActivity(wechatIntent);
                        } catch (Exception e) {
                            ToastUtil.shortToast(PSHEventDetailActivity.this, "????????????????????????????????????");
                        }
                    }

                    @Override
                    public void onFail(Exception error) {
                        ToastUtil.shortToast(PSHEventDetailActivity.this, "??????????????????????????????????????????");
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
                    ToastUtil.shortToast(PSHEventDetailActivity.this, "??????????????????");
                    return;
                }

                Intent intent = new Intent(PSHEventDetailActivity.this, SelectLocationActivity.class);
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
                    ToastUtil.shortToast(PSHEventDetailActivity.this, "??????????????????");
                    return;
                }

                Intent intent = new Intent(PSHEventDetailActivity.this, SelectLocationActivity.class);
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
                Intent intent = new Intent(PSHEventDetailActivity.this, com.augurit.agmobile.gzps.uploadevent.view.eventflow.AddEventJournalActivity.class);
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


        //????????????????????????
        btn_retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.augurit.agmobile.gzps.uploadevent.view.eventflow.RetriveDialog dialog = com.augurit.agmobile.gzps.uploadevent.view.eventflow.RetriveDialog.getInstance(
                        String.valueOf(mProcInstDbId));
                dialog.show(getSupportFragmentManager(), "retriveback");
//                AlertDialog dialog = new AlertDialog.Builder(PshEventDetailActivity1.this)
//                        .setTitle("??????:")
//                        .setMessage("????????????????????????????")
//                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
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
                AlertDialog dialog = new AlertDialog.Builder(PSHEventDetailActivity.this)
                        .setTitle("??????:")
                        .setMessage("??????????????????????????????????????????,???????????????????????????????")
                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
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

        //??????????????????
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PSHEventDetailActivity.this, EventEditActivity.class);
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
     * ??????
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
//                            ToastUtil.longToast(PshEventDetailActivity1.this,"????????????!");
//                            EventBus.getDefault().post(new UpdateAfterRetriveEvent(mProcInstDbId));
//
//                            finish();
//                        }else {
//                            if(!TextUtils.isEmpty(stringResult.getMessage())){
//                                ToastUtil.longToast(PshEventDetailActivity1.this,stringResult.getMessage());
//                            }else {
//                                ToastUtil.longToast(PshEventDetailActivity1.this,"????????????!");
//                            }
//                        }
//                    }
//                });
//
//    }

    /**
     * ??????
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
                            ToastUtil.longToast(PSHEventDetailActivity.this, "????????????!");
                            EventBus.getDefault().post(new UpdateAfterDeleteEvent(mProcInstDbId));
                            finish();
                        } else {
                            if (!TextUtils.isEmpty(stringResult.getMessage())) {
                                ToastUtil.longToast(PSHEventDetailActivity.this, stringResult.getMessage());
                            } else {
                                ToastUtil.longToast(PSHEventDetailActivity.this, "????????????!");
                            }
                        }
                    }
                });

    }

    /**
     * ????????????ID??????????????????????????????????????????????????????????????????
     */
    private void AESEncodeSJID(final Callback2<String> callback) {
        GzpsService gzpsService = new GzpsService(this.getApplicationContext());
        gzpsService.AESEncode("" + mEventDetail.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result2<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.shortToast(PSHEventDetailActivity.this, "???????????????????????????");
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
     * ????????????????????????????????????????????????
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
                                    //????????????????????????????????????
                                    com.augurit.agmobile.gzps.uploadevent.view.eventflow.AdviceView adviceView = new com.augurit.agmobile.gzps.uploadevent.view.eventflow.AdviceView(PSHEventDetailActivity.this);
                                    adviceView.initView(opinion);
                                    adviceView.addTo(ll_advice);
                                } else {
                                    realEventProcess.add(eventProcess);
                                }
                            }

                            if ("true".equals(mEventDetail.getIsbyself())) {
                                //?????????????????????????????????????????????
                                return;
                            }

                            int index = 0;
                            boolean isFinished = false;
                            /*if(GzpsConstant.LINK_FH.equals(mEventDetail.getCurNode())
                                    && mEventDetail.getEventState().contains("?????????")){
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
                                PSHEventProcessView eventProcessView = new PSHEventProcessView(PSHEventDetailActivity.this);
                                eventProcessView.initView(eventProcess, isFinished, index, realEventProcess.size());
                                eventProcessView.addTo(ll_event_process);

                                index++;
                            }
                        }
                    }
                });
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @return
     */
    private boolean checkIfCurrentUserAllowAddAdvice() {
        boolean allow = false;
        User user = new LoginService(PSHEventDetailActivity.this.getApplicationContext(), AMDatabase.getInstance()).getUser();

        for (String district : GzpsConstant.districtsSimple) {
            if (user.getOrgName().contains(district)) {   //???????????????????????????
                if (mEventDetail.getParentOrgName().contains(district)) {   //????????????????????????
                    if (user.getRoleCode().contains(GzpsConstant.roleCodes[3])
                            || user.getRoleCode().contains(GzpsConstant.roleCodes[4])) {
                        //Rg???Rm????????????????????????
                        allow = true;
                    }
                }
            }

        }
        if (user.getOrgName().contains(GzpsConstant.districtsSimple[0])) {
            if (user.getRoleCode().contains(GzpsConstant.roleCodes[3])
                    || user.getRoleCode().contains(GzpsConstant.roleCodes[4])) {
                //Rg???Rm????????????????????????
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
                        ToastUtil.shortToast(PSHEventDetailActivity.this, "???????????????????????????");
                    }

                    @Override
                    public void onNext(Boolean s) {
                        if (s) {
                            pd.dismiss();
                            ToastUtil.shortToast(PSHEventDetailActivity.this, "????????????");
                            setResult(123);
                            finish();
                        } else {
                            pd.dismiss();
                            ToastUtil.shortToast(PSHEventDetailActivity.this, "???????????????????????????");
                        }
                    }
                });
    }

    /**
     * ??????
     */
    private void rollback() {
        com.augurit.agmobile.gzps.uploadevent.view.eventflow.RollBackDialog rollBackDialog = com.augurit.agmobile.gzps.uploadevent.view.eventflow.RollBackDialog.getInstance(
                String.valueOf(mEventDetail.getId()),
                String.valueOf(mEventListItem.getId()),
                mEventDetail.getCode());
        rollBackDialog.show(getSupportFragmentManager(), "rollback");
    }

    /**
     * ??????
     */
    private void reassign() {
        String activityName = mEventListItem.getState();
        long sjid = mEventDetail.getId(); //masterEntityKey

        com.augurit.agmobile.gzps.uploadevent.view.eventflow.ReassignDialog reassignDialog = com.augurit.agmobile.gzps.uploadevent.view.eventflow.ReassignDialog.getInstance(mEventDetail.getObjectId(),
                mEventDetail.getParentOrgId(), mEventDetail.getParentOrgName(), mProcInstDbId,
                activityName, String.valueOf(sjid));
        reassignDialog.show(getSupportFragmentManager(), "reassign");
    }

    private void submit() {
        ArrayList<PSHEventDetail.NextlinkBean> nextlinkBeans = new ArrayList<>(mDetail.getNextlink());
        Intent intent = new Intent(PSHEventDetailActivity.this, PshSendNextLinkActivity.class);
        intent.putExtra("taskInstId", mEventListItem.getId()+"");
        intent.putExtra("wtsbId", mEventListItem.getId());
        intent.putExtra("currLinkCode", mEventDetail.getId());
        intent.putExtra("state", mEventDetail.getState());

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
        addEventAdviceDialog.show(getSupportFragmentManager(), "addEventAdvice");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 456
                && resultCode == 456) {
            setResult(123);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateAdviceEvent(UpdateAdviceEvent event) {
        if (event != null && event.getOpinion() != null) {
            com.augurit.agmobile.gzps.uploadevent.view.eventflow.AdviceView adviceView = new com.augurit.agmobile.gzps.uploadevent.view.eventflow.AdviceView(this);
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
                ToastUtil.shortToast(PshEventDetailActivity1.this, "????????????????????????");
            }
        });*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEditEvent(UpdateAfterEditEvent editEvent) {
        if (editEvent.getUpdateState().equals(UpdateAfterEditEvent.UPDATE_DETAIL)) {
            //????????????,????????????????????????
            initData();
        } else if (editEvent.getUpdateState().equals(UpdateAfterEditEvent.UPDATE_LIST)) {
            //????????????,?????????????????????????????????????????????????????????,???????????????????????????,??????????????????
            EventBus.getDefault().post(new UpdateAfterDeleteEvent(mProcInstDbId));
            finish();
        }
    }
}
