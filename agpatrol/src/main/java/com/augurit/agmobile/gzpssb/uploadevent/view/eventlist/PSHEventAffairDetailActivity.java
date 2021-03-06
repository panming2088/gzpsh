package com.augurit.agmobile.gzpssb.uploadevent.view.eventlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.constant.GzpsConstant;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.service.GzpsService;
import com.augurit.agmobile.gzps.common.widget.TakePhotoTableItem;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.publicaffair.event.UpdateAdviceEvent;
import com.augurit.agmobile.gzps.publicaffair.model.EventAffair;
import com.augurit.agmobile.gzps.uploadevent.model.EventDetail;
import com.augurit.agmobile.gzps.uploadevent.model.EventProcess;
import com.augurit.agmobile.gzps.uploadevent.service.UploadEventService;
import com.augurit.agmobile.gzps.uploadevent.view.eventflow.AddEventAdviceDialog;
import com.augurit.agmobile.gzps.uploadevent.view.eventflow.AdviceView;
import com.augurit.agmobile.gzps.uploadevent.view.eventflow.EventProcessView;
import com.augurit.agmobile.gzps.uploadevent.view.eventlist.EventDetailMapActivity;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventDetail;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventProcess;
import com.augurit.agmobile.gzpssb.uploadevent.service.PSHUploadEventService;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventflow.PSHEventProcessView;
import com.augurit.agmobile.mapengine.map.geometry.LatLng;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.selectlocation.util.SelectLocationConstant;
import com.augurit.agmobile.patrolcore.selectlocation.view.SelectLocationActivity;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.service.LoginService;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
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
import rx.schedulers.Schedulers;

/**
 * Created by liangsh on 2017/11/17.
 */

public class PSHEventAffairDetailActivity extends BaseActivity {

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

    private LinearLayout ll_advice;
    private LinearLayout ll_event_process;
    private View ll_add_advice;
    private View btn_addvice;

    private PSHEventListItem mEventAffairBean;
    private PSHUploadEventService mEventService;
    private PSHEventDetail.FormBean mEventDetail;
    private PSHEventDetail mDetail;

    public void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.activity_psh_event_affair_detail);
        mEventAffairBean = (PSHEventListItem) getIntent().getSerializableExtra("eventAffairBean");
        mEventService = new PSHUploadEventService(this.getApplicationContext());

        initView();
        initData();

        EventBus.getDefault().register(this);
    }


    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText("????????????");
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        ll_event_process = (LinearLayout) findViewById(R.id.ll_event_process);
        ll_advice = (LinearLayout) findViewById(R.id.ll_advice);
        ll_add_advice = findViewById(R.id.ll_add_advice);
        btn_addvice = findViewById(R.id.btn_addvice);


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

        ll_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEventDetail != null) {
                    //???????????????
                    String x = String.valueOf(mEventDetail.getX());
                    String y = String.valueOf(mEventDetail.getY());
                    String addr = mEventDetail.getSzwz();

                    if (StringUtil.isEmpty(x)
                            || StringUtil.isEmpty(y)) {
                        ToastUtil.shortToast(PSHEventAffairDetailActivity.this, "??????????????????");
                        return;
                    }

                    Intent intent = new Intent(PSHEventAffairDetailActivity.this, SelectLocationActivity.class);
                    intent.putExtra(SelectLocationConstant.IF_READ_ONLY, true);
                    intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_LOCATION, new LatLng(Double.valueOf(y),
                            Double.valueOf(x)));
                    intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_ADDRESS, addr);
                    intent.putExtra(SelectLocationConstant.INITIAL_SCALE, PatrolLayerPresenter.initScale);
                    startActivity(intent);
                }
            }
        });

        ll_report_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEventDetail == null){
                    return;
                }
                String x = String.valueOf(mEventDetail.getReportx());
                String y = String.valueOf(mEventDetail.getReporty());
                String addr = mEventDetail.getReportaddr();

                if (StringUtil.isEmpty(x)
                        || StringUtil.isEmpty(y)) {
                    ToastUtil.shortToast(PSHEventAffairDetailActivity.this, "??????????????????");
                    return;
                }

                Intent intent = new Intent(PSHEventAffairDetailActivity.this, SelectLocationActivity.class);
                intent.putExtra(SelectLocationConstant.IF_READ_ONLY, true);
                intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_LOCATION, new LatLng(Double.valueOf(y),
                        Double.valueOf(x)));
                intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_ADDRESS, addr);
                intent.putExtra(SelectLocationConstant.INITIAL_SCALE, PatrolLayerPresenter.initScale);
                startActivity(intent);
            }
        });
        findViewById(R.id.share_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new LoginService(PSHEventAffairDetailActivity.this.getApplicationContext(), AMDatabase.getInstance()).getUser();
//                    String url = "????????????app??????";
                AESEncodeSJID(new Callback2<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            String url = BaseInfoManager.getSupportUrl(PSHEventAffairDetailActivity.this.getApplicationContext())
                                    + "event/eventDetail.html?";
                            String param = "sjid=" + URLEncoder.encode(s);
                            url += param;
                            Intent wechatIntent = new Intent(Intent.ACTION_SEND);
                            wechatIntent.setPackage("com.tencent.mm");
                            wechatIntent.setType("text/plain");
                            wechatIntent.putExtra(Intent.EXTRA_TEXT, url);
                            startActivity(wechatIntent);
                        } catch (Exception e) {
                            ToastUtil.shortToast(PSHEventAffairDetailActivity.this, "????????????????????????????????????");
                        }
                    }

                    @Override
                    public void onFail(Exception error) {
                        ToastUtil.shortToast(PSHEventAffairDetailActivity.this, "??????????????????????????????????????????");
                    }
                });
            }
        });

        btn_addvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAdviceDialog();
            }
        });

    }


    private void initData() {
        PSHUploadEventService uploadEventService = new PSHUploadEventService(getApplicationContext());
        uploadEventService.getDetails(mEventAffairBean.getId(), "sb")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PSHEventDetail>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogUtil.MessageBoxCannotCancel(PSHEventAffairDetailActivity.this, null, "?????????????????????", new DialogInterface.OnClickListener() {
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
                            DialogUtil.MessageBoxCannotCancel(PSHEventAffairDetailActivity.this, null, "?????????????????????", new DialogInterface.OnClickListener() {
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
        textitem_upload_user.setText(eventDetail.getSbr());
        textitem_parent_org.setText(eventDetail.getParentOrgName());
        textitem_upload_date.setText(TimeUtil.getStringTimeYMDS(new Date(eventDetail.getAppSbsj())));
        textitem_upload_address.setText(eventDetail.getSzwz());
        textitem_upload_compttype.setText(eventDetail.getPshmc());
        textitem_upload_eventtype.setText(getSpinnerValuesByMultiCode(eventDetail.getWtlx()));
        try {
            textitem_upload_urgency.setText(urgency_type_array[Integer.valueOf(eventDetail.getJjcd()) - 1]);
        } catch (Exception e) {

        }
        tv_check_location.setText(eventDetail.getReportaddr());
        tv_check_component_location.setText(eventDetail.getSzwz());
        textfield_description.setText(eventDetail.getWtms());
        if (!ListUtil.isEmpty(eventDetail.getFiles())) {
            List<Photo> photoList = new ArrayList<>();
            for (PSHEventDetail.FormBean.FilesBean filesBean : eventDetail.getFiles()) {
                Photo photo = new Photo();
                photo.setHasBeenUp(1);
                photo.setPhotoPath(filesBean.getPath());
                photo.setThumbPath(filesBean.getThumbPath());
                photo.setField1("photo");
                photoList.add(photo);
            }
            photo_item.setSelectedPhotos(photoList);
        }
        boolean allow = checkIfCurrentUserAllowAddAdvice();
        if (allow) {
//            ll_add_advice.setVisibility(View.VISIBLE);  todo ????????????????????????
        }

        /*
        if ("true".equals(mEventAffairBean.getIsbyself())) {
            //???????????????????????????
            findViewById(R.id.tv_isbyself).setVisibility(View.VISIBLE);
        } else {

        }
        */

        //??????????????????????????????????????????????????????
        /*AESEncodeSJID(new Callback2<String>() {
            @Override
            public void onSuccess(String s) {
                getEventHandlesAndJournals(s);
            }

            @Override
            public void onFail(Exception error) {
                ToastUtil.shortToast(EventAffairDetailActivity.this, "????????????????????????");
            }
        });*/
        getEventHandlesAndJournals("" +mEventAffairBean.getId());

    }

    /**
     * ????????????ID
     */
    private void AESEncodeSJID(final Callback2<String> callback) {
        GzpsService gzpsService = new GzpsService(this.getApplicationContext());
        gzpsService.AESEncode("" + mEventAffairBean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result2<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.shortToast(PSHEventAffairDetailActivity.this, "???????????????????????????");
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
     * ?????????????????????????????????
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
                        if (!ListUtil.isEmpty(eventProcessList)) {
                            List<PSHEventProcess> realEventProcess = new ArrayList<>();
                            for (PSHEventProcess eventProcess : eventProcessList) {
                                if (!StringUtil.isEmpty(eventProcess.getLx())
                                        && eventProcess.getLx().equals("100")) {
                                    EventDetail.OpinionBean opinion = new EventDetail.OpinionBean();
                                    opinion.setOpinion(eventProcess.getContent());
                                    opinion.setUserName(eventProcess.getUsername());
                                    opinion.setTime(eventProcess.getAppTime());
                                    //????????????????????????????????????
                                    AdviceView adviceView = new AdviceView(PSHEventAffairDetailActivity.this);
                                    adviceView.initView(opinion);
                                    adviceView.addTo(ll_advice);
                                } else {
                                    realEventProcess.add(eventProcess);
                                }
                            }

        //                    if ("true".equals(mEventAffairBean.getIsbyself())) {
                                //?????????????????????
//                                int index = 0;
//                                for (EventProcess eventProcess : realEventProcess) {
//                                    eventProcess.setLinkName("????????????(?????????)");
//                                    PSHEventProcessView eventProcessView = new PSHEventProcessView(EventAffairDetailActivity.this);
//                                    eventProcessView.initView(eventProcess, true, index, realEventProcess.size());
//                                    eventProcessView.addTo(ll_event_process);
//                                    index++;
//                                }
                              //  return;
    //                        }

                            int index = 0;
                            boolean isFinished = false;//0(?????????),1(?????????),2???????????????,3(????????????)
                            if (StringUtil.isEmpty(mEventAffairBean.getState())
                                    || "3".equals(mEventAffairBean.getState())) {
                                isFinished = true;
                            }
                            for (PSHEventProcess eventProcess : realEventProcess) {
//                                if (!isFinished && (index == realEventProcess.size() - 1)) {
//                                    continue;
//                                }
                                PSHEventProcessView eventProcessView = new PSHEventProcessView(PSHEventAffairDetailActivity.this);
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
        User user = new LoginService(PSHEventAffairDetailActivity.this.getApplicationContext(), AMDatabase.getInstance()).getUser();

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

    private void showAddAdviceDialog() {
        AddEventAdviceDialog addEventAdviceDialog = AddEventAdviceDialog.getInstance("", mEventAffairBean.getId() + "");
        addEventAdviceDialog.show(getSupportFragmentManager(), "addEventAdvice");
    }

    public String getSpinnerValueByCode(String code) {
        List<DictionaryItem> dictionaryItems = new TableDBService(this.getApplicationContext()).getDictionaryByCode(code);
        if (ListUtil.isEmpty(dictionaryItems)) {
            return "";
        }
        DictionaryItem dictionaryItem = dictionaryItems.get(0);
        return dictionaryItem.getName();
    }

    private String getSpinnerValuesByMultiCode(String codes) {
        if (StringUtil.isEmpty(codes)) {
            return "";
        }
        String[] codeArray = codes.split(",");
        String value = "";
        for (String code : codeArray) {
            value = value + "???" + getSpinnerValueByCode(code);
        }
        value = value.substring(1);
        return value;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateAdviceEvent(UpdateAdviceEvent event) {
        if (event != null && event.getOpinion() != null) {
            AdviceView adviceView = new AdviceView(PSHEventAffairDetailActivity.this);
            adviceView.initView(event.getOpinion());
            adviceView.addTo(ll_advice);
        }
    }
}
