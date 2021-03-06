package com.augurit.agmobile.gzpssb.uploadevent.view.uploadevent;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.baiduselectlocation.BaiduSelectLocationActivity;
import com.augurit.agmobile.gzps.common.baiduselectlocation.model.BaiduSelectLocationModel;
import com.augurit.agmobile.gzps.common.constant.GzpsConstant;
import com.augurit.agmobile.gzps.common.constant.LayerUrlConstant;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.selectcomponent.SelectComponentFinishEvent2;
import com.augurit.agmobile.gzps.common.widget.AutoBreakViewGroup;
import com.augurit.agmobile.gzps.common.widget.MyGridView;
import com.augurit.agmobile.gzps.common.widget.SpinnerTableItem2;
import com.augurit.agmobile.gzps.common.widget.TakePhotoTableItem;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentFieldKeyConstant;
import com.augurit.agmobile.gzps.uploadevent.adapter.EventTypeAdapter;
import com.augurit.agmobile.gzps.uploadevent.adapter.FacilityTypeAdapter;
import com.augurit.agmobile.gzps.uploadevent.adapter.NextLinkAssigneersAdapter;
import com.augurit.agmobile.gzps.uploadevent.dao.DataResult;
import com.augurit.agmobile.gzps.uploadevent.dao.GetPersonByOrgApiData;
import com.augurit.agmobile.gzps.uploadevent.model.Assigneers;
import com.augurit.agmobile.gzps.uploadevent.model.OrgItem;
import com.augurit.agmobile.gzps.uploadevent.model.Person;
import com.augurit.agmobile.gzps.uploadevent.util.PhotoUploadType;
import com.augurit.agmobile.gzpssb.activity.SewerageDrainageUnitSelectMapActivity;
import com.augurit.agmobile.gzpssb.bean.DrainageUnit;
import com.augurit.agmobile.gzpssb.bean.DrainageUnitListBean;
import com.augurit.agmobile.gzpssb.common.view.MultiTakePhotoTableItem;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournal;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemBean;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemFeatureOrAddr;
import com.augurit.agmobile.gzpssb.uploadevent.model.SelectFinishEvent;
import com.augurit.agmobile.gzpssb.uploadevent.model.SelectHouseFinishEvent;
import com.augurit.agmobile.gzpssb.uploadevent.service.PSHUploadEventService;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.agmobile.mapengine.map.geometry.LatLng;
import com.augurit.agmobile.patrolcore.baiduapi.BaiduApiService;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.agmobile.patrolcore.selectlocation.model.BaiduGeocodeResult;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.am.cmpt.common.Callback1;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.loc.util.LocationUtil;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.service.LoginService;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.google.android.flexbox.FlexboxLayout;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * ??????????????????
 * R1 ???????????????
 * Rg???Rm??????
 */
public class PSHEventUploadActivity extends BaseActivity {

    private final String PSH_EVENT_TYPE_DICT = "A204";//?????????????????????????????????
    private final String WSJ_OUTSIDE_EVENT_TYPE_DICT = "A208";//???????????????????????????????????????????????????
    private final String WSJ_INSIDE_EVENT_TYPE_DICT = "A209";//???????????????????????????????????????????????????
    private final String YSJ_OUTSIDE_EVENT_TYPE_DICT = "A210";//???????????????????????????????????????
    private final String YSJ_INSIDE_EVENT_TYPE_DICT = "A211";//???????????????????????????????????????
    private final String KD_EVENT_TYPE_DICT = "A213";//??????????????????

    private MultiTakePhotoTableItem photo_item;
    private TakePhotoTableItem photo_item_self;

    private AutoBreakViewGroup disease_type_rg, urgency_state_rg;
    private MyGridView gv_facilitytype;
    private FacilityTypeAdapter mFacilityTypeAdapter;
    private View ll_eventtype;
    private TextView tv_event_type_outside;
    private MyGridView gv_eventtype;
    private TextView tv_event_type_inside;
    private MyGridView gv_eventtype2;
    private EventTypeAdapter mEventTypeAdapter;
    private EventTypeAdapter mEventTypeAdapter2;
    private RadioGroup.LayoutParams params;
    private Button upload_btn;
    private View btn_save_draft;
    private CheckBox cb_isbyself;
    private View ll_nextlink_assigneers;
    private MyGridView gv_assignee;
    private TextView tv_select_or_check_location;
    private View ll_nextlilnk_org_rg_rm;     //Rg???Rm???????????????????????????UI
    private AutoBreakViewGroup radio_nextlink_org_rg_rm;   //Rg???Rm???????????????UI
    private ProgressDialog progressDialog;

    private TableDBService dbService;

    private String componentId;
    private String layerId;
    private String layerName;
    private String[] urgency_type_array = {"??????", "?????????", "??????"};
    private double reportX;       //?????????????????????X??????
    private double reportY;   //?????????????????????Y??????
    private String reportAddr;      //???????????????????????????

    //?????????????????????,?????????????????????"?????????"??????,??????????????????,????????????????????????????????????
    private String isbyself = "false"; //??????????????????

    private String facilityCode, diseaseCode, urgencyCode, facilityName, diseaseName, urgencyName;

    private Component mSelComponent;
    private DetailAddress mSelDetailAddress;
    private ProblemBean problemUploadBean = null;
    private List<OrgItem> orgItems;
    private List<DictionaryItem> facility_type_list;
    private List<DictionaryItem> problemList;
    private List<DictionaryItem> problemList2;
    private TextItemTableItem roadItem, addrItem;
    private TextFieldTableItem problemitem;
    private TextFieldTableItem remarkItem;
    private NextLinkAssigneersAdapter assigneersAdapter;
    private Assigneers.Assigneer selAssignee;

    private String selAssignOrg;


    private String activityName;
    private String sjid;
    private String isSendMessage = "0";//???????????????,1?????????

    private CheckBox cb_is_send_message;//??????????????????????????????


    private View ll_self_process;//??????????????????UI
    private TextFieldTableItem problem_tab_item_self;
    private PSHHouse mUnitListBean;
    private PSHJournal mJournal;
    private Geometry mGeometry = null;
    private TextFieldTableItem
            problem_tab_zgjy;
    private View ll_zgjy;
    private View img_table_sewerageone_drainage_unit_loading;
    private ArrayList<DrainageUnit> drainageUnitList;
    private SpinnerTableItem2 sp_table_sewerageone_drainage_unit;
    private ImageView img_table_sewerageone_drainage_unit_location;
    private String psdyId;
    private String psdyName;
    private ObjectAnimator drainageUnitLoadingAnimator;
    private boolean isDrainageDepartment = false;
    private View ll_psdy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_upload_psh);
        mUnitListBean = getIntent().getParcelableExtra("UnitListBean");
        mJournal = getIntent().getParcelableExtra("journal");
        initView();
        initListener();
        initData();

        // ?????????????????????reportx???reporty???reportaddr??????????????????????????????
        startLocate();
    }

    private void initData() {
        problemUploadBean = new ProblemBean();
        dbService = new TableDBService(PSHEventUploadActivity.this);
//        facility_type_list = dbService.getDictionaryByTypecodeInDB(PSH_EVENT_TYPE_DICT);

        ((TextView) findViewById(R.id.tv_title)).setText("????????????");
        //???????????????????????????
//        initFacilityView(facility_type_list);
        //???????????????????????????
        if (mJournal != null) {
            initEventTypeView(facility_type_list, mJournal.getChildCode());
        } else {
//            initEventTypeView(facility_type_list, null);
        }
        initUrgencyView();

        User user = new LoginService(getApplicationContext(), AMDatabase.getInstance()).getUser();
        //?????????Rg???Rm ?????????????????????
//        if(user.getRoleCode().contains(GzpsConstant.roleCodes[3])
//                || user.getRoleCode().contains(GzpsConstant.roleCodes[4])){
//            ll_nextlilnk_org_rg_rm.setVisibility(View.VISIBLE);
//            getOrgList();
//
//        }else {
//            ll_nextlilnk_org_rg_rm.setVisibility(View.GONE);
//            getNextLinkAssigneers();
//
//        }

        if (mJournal != null) {
            photo_item.setSelectedPhotos(mJournal.getPhotos());
            photo_item.setSelThumbPhotos(mJournal.getThumbnailPhotos());
            photo_item.setReadOnly();
            photo_item.setAddPhotoEnable(false);
            tv_select_or_check_location.setText(mJournal.getPshName());
            addrItem.setText(mJournal.getAddr());
            problemUploadBean.setPshid(mJournal.getPshId());
            problemUploadBean.setPshmc(mJournal.getPshName());
            problemUploadBean.setX(String.valueOf(mJournal.getX()));
            problemUploadBean.setY(String.valueOf(mJournal.getY()));
        }
        loadUserData();
    }

    private void loadUserData() {
        new PSHUploadEventService(getApplicationContext()).getJurisdictionByLoginName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DataResult dataResult) {
                        if (dataResult != null && dataResult.getCode() == 200) {
                            isDrainageDepartment = dataResult.isIsPsgs();
                        }
                        if (isDrainageDepartment) {
                            ll_zgjy.setVisibility(View.VISIBLE);
                        } else {
                            ll_zgjy.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void initView() {

        ll_nextlilnk_org_rg_rm = (View) findViewById(R.id.ll_nextlilnk_org);
        radio_nextlink_org_rg_rm = (AutoBreakViewGroup) findViewById(R.id.radio_nextlink_org);


        gv_assignee = (MyGridView) findViewById(R.id.gv_assignee);

        roadItem = (TextItemTableItem) findViewById(R.id.road_tab_item);
        addrItem = (TextItemTableItem) findViewById(R.id.addr_tab_item);
        ll_zgjy = findViewById(R.id.ll_zgjy);
        ll_psdy = findViewById(R.id.ll_psdy);
        //todo ???????????????????????????????????????????????????????????????????????????

        img_table_sewerageone_drainage_unit_location = (ImageView) findViewById(R.id.img_table_sewerageone_drainage_unit_location);
        img_table_sewerageone_drainage_unit_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = sp_table_sewerageone_drainage_unit.getCurrentPosition();
                if (drainageUnitList != null && !drainageUnitList.isEmpty()) {
                    if (currentPosition == -1) {
                        currentPosition = 0;
                    }
                    psdyId = drainageUnitList.get(currentPosition).getObjectId();
                    psdyName = drainageUnitList.get(currentPosition).getName();
                }
                SewerageDrainageUnitSelectMapActivity.start(PSHEventUploadActivity.this, psdyId);
            }
        });
        sp_table_sewerageone_drainage_unit = (SpinnerTableItem2) findViewById(R.id.sp_table_sewerageone_drainage_unit);
        problemitem = (TextFieldTableItem) findViewById(R.id.problem_tab_item);
        img_table_sewerageone_drainage_unit_loading = findViewById(R.id.img_table_sewerageone_drainage_unit_loading);
        drainageUnitLoadingAnimator = ObjectAnimator.ofFloat(img_table_sewerageone_drainage_unit_loading, "rotation", 0, 360)
                .setDuration(1000);
        drainageUnitLoadingAnimator.setInterpolator(new LinearInterpolator());
        drainageUnitLoadingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        drainageUnitLoadingAnimator.start();

        problem_tab_zgjy = (TextFieldTableItem) findViewById(R.id.problem_tab_zgjy);
        problemitem.setRequireTag();
        problem_tab_zgjy.setRequireTag();
        tv_select_or_check_location = (TextView) findViewById(R.id.tv_select_or_check_location);
        //personItem = (TextItemTableItem) findViewById(R.id.patrol_person);
        //remarkItem = (TextFieldTableItem) findViewById(R.id.remark);
        photo_item = (MultiTakePhotoTableItem) findViewById(R.id.photo_item);
        photo_item.setPhotoExampleEnable(false);
        photo_item.setRequired(true);
        photo_item_self = (TakePhotoTableItem) findViewById(R.id.photo_item_self);
        photo_item.setPhotoExampleEnable(false);

        disease_type_rg = (AutoBreakViewGroup) findViewById(R.id.disease_type_rg);
        urgency_state_rg = (AutoBreakViewGroup) findViewById(R.id.urgency_state_rg);
        gv_facilitytype = (MyGridView) findViewById(R.id.gv_facilitytype);
        ll_eventtype = findViewById(R.id.ll_eventtype);
        tv_event_type_outside = findViewById(R.id.tv_event_type_outside);
        gv_eventtype = (MyGridView) findViewById(R.id.gv_eventtype);
        tv_event_type_inside = findViewById(R.id.tv_event_type_inside);
        gv_eventtype2 = findViewById(R.id.gv_eventtype2);
        cb_isbyself = (CheckBox) findViewById(R.id.cb_isbyself);
        ll_nextlink_assigneers = findViewById(R.id.ll_nextlink_assigneers);

        upload_btn = (Button) findViewById(R.id.problem_commint);
        btn_save_draft = findViewById(R.id.btn_save_draft);
        int screenWidths = getScreenWidths();
        params = new AutoBreakViewGroup.LayoutParams(screenWidths / 3, AutoBreakViewGroup
                .LayoutParams
                .WRAP_CONTENT);

        mFacilityTypeAdapter = new FacilityTypeAdapter(this);
        mFacilityTypeAdapter.setOnItemClickListener(new OnRecyclerItemClickListener<DictionaryItem>() {
            @Override
            public void onItemClick(View view, int position, DictionaryItem selectedData) {
                facilityCode = selectedData.getCode();
                facilityName = selectedData.getName();
                if ("??????".equals(facilityName)) {
                    ll_eventtype.setVisibility(View.GONE);
                } else {
                    ll_eventtype.setVisibility(View.VISIBLE);
                    problemList = dbService.getChildDictionaryByPCodeInDB(facilityCode);
                    initEventTypeView(problemList, null);
                }
            }

            @Override
            public void onItemLongClick(View view, int position, DictionaryItem selectedData) {

            }
        });
        gv_facilitytype.setAdapter(mFacilityTypeAdapter);
        mEventTypeAdapter = new EventTypeAdapter(this);
        gv_eventtype.setAdapter(mEventTypeAdapter);

        mEventTypeAdapter2 = new EventTypeAdapter(this);
        gv_eventtype2.setAdapter(mEventTypeAdapter2);


        //  initUrgencyView();
        assigneersAdapter = new NextLinkAssigneersAdapter(this);
        gv_assignee.setAdapter(assigneersAdapter);
        assigneersAdapter.setOnItemClickListener(new OnRecyclerItemClickListener<Assigneers.Assigneer>() {
            @Override
            public void onItemClick(View view, int position, Assigneers.Assigneer selectedData) {
                selAssignee = selectedData;
            }

            @Override
            public void onItemLongClick(View view, int position, Assigneers.Assigneer selectedData) {

            }
        });
        progressDialog = new ProgressDialog(PSHEventUploadActivity.this);
        progressDialog.setMessage("????????????...");
        EventBus.getDefault().register(this);

        cb_is_send_message = (CheckBox) findViewById(R.id.cb_is_send_message);

        ll_self_process = findViewById(R.id.ll_self_process);

        problem_tab_item_self = (TextFieldTableItem) findViewById(R.id.problem_tab_item_self);
    }

    private void initListener() {
        disease_type_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);
                String code = problemList.get(index).getCode();
                diseaseName = radioButton.getText().toString();
                diseaseCode = code;
            }
        });
        urgency_state_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);
                urgencyName = radioButton.getText().toString();
                urgencyCode = (index + 1) + "";
            }
        });

        //????????????????????????
        RxView.clicks(upload_btn)
                .throttleFirst(2, TimeUnit.SECONDS)   //2???????????????????????????????????????????????????
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        User user = new LoginService(getApplicationContext(), AMDatabase.getInstance()).getUser();
                        if ((user.getRoleCode().contains(GzpsConstant.roleCodes[2])
                       /* ||user.getRoleCode().contains(GzpsConstant.roleCodes[3])
                        || user.getRoleCode().contains(GzpsConstant.roleCodes[4]) */)) {
                            //Rg???Rm??????????????????
                            ToastUtil.longToast(PSHEventUploadActivity.this,
                                    "????????????????????????APP??????????????????????????????????????????????????????????????????????????????????????????????????????");
                            return;
                        }

                        if (user.getRoleCode().contains(GzpsConstant.roleCodes[1])) {
                            ToastUtil.longToast(PSHEventUploadActivity.this,
                                    "?????????????????????APP??????????????????????????????????????????????????????????????????????????????????????????????????????");
                            return;
                        }

                        progressDialog.show();

                        ProblemBean paramsEntity = getParamsEntity();

                        if (paramsEntity == null) {
                            progressDialog.dismiss();
                            return;
                        }
                        sendToServer();
                    }
                });

        //???????????????????????????
        RxView.clicks(btn_save_draft)
                .throttleFirst(2, TimeUnit.SECONDS)   //2???????????????????????????????????????????????????
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        User user = new LoginService(getApplicationContext(), AMDatabase.getInstance()).getUser();
                        if ((user.getRoleCode().contains(GzpsConstant.roleCodes[2])
                       /* ||user.getRoleCode().contains(GzpsConstant.roleCodes[3])
                        || user.getRoleCode().contains(GzpsConstant.roleCodes[4]) */)) {
                            //Rg???Rm??????????????????
                            ToastUtil.longToast(PSHEventUploadActivity.this,
                                    "????????????????????????APP??????????????????????????????????????????????????????????????????????????????????????????????????????");
                            return;
                        }

                        if (user.getRoleCode().contains(GzpsConstant.roleCodes[1])) {
                            ToastUtil.longToast(PSHEventUploadActivity.this,
                                    "?????????????????????APP??????????????????????????????????????????????????????????????????????????????????????????????????????");
                            return;
                        }

                        ProblemBean paramsEntity = getDraftParamsEntity();
                        if (paramsEntity == null) {
                            progressDialog.dismiss();
                            return;
                        }
                        saveDraft();
                    }
                });

        /**
         * ???????????????????????????
         */
        findViewById(R.id.ll_select_component).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PSHEventUploadActivity.this,
                        EventSelectMapActivity.class);
                if (mGeometry != null) {
                    intent.putExtra("geometry", mGeometry);
                } else if (mSelDetailAddress != null) {
                    Point point = new Point();
                    point.setX(mSelDetailAddress.getX());
                    point.setY(mSelDetailAddress.getY());
                    intent.putExtra("geometry", point);
                } else if (mJournal != null) {
                    intent = new Intent(PSHEventUploadActivity.this, BaiduSelectLocationActivity.class);
                    BaiduSelectLocationModel baiduSelectLocationModel = new BaiduSelectLocationModel();
                    baiduSelectLocationModel.setInitScale(-1);
                    baiduSelectLocationModel.setIfReadOnly(true);
                    String address = null;
                    Geometry facilityLocation = null;
                    facilityLocation = new Point(mJournal.getX(), mJournal.getY());
                    baiduSelectLocationModel.setLastSelectAddress(mJournal.getAddr());
                    baiduSelectLocationModel.setFacilityOriginLocation(facilityLocation);
                    baiduSelectLocationModel.setLastSelectLocation(facilityLocation);
                    intent.putExtra("baiduselectlocation", baiduSelectLocationModel);
                }
                startActivity(intent);
            }
        });

        //??????????????????
        cb_isbyself.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isbyself = "true";
                    ll_nextlink_assigneers.setVisibility(View.GONE);
                    ll_self_process.setVisibility(View.VISIBLE);
                } else {
                    isbyself = "false";
                    ll_nextlink_assigneers.setVisibility(View.VISIBLE);
                    ll_self_process.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.MessageBox(PSHEventUploadActivity.this, "??????", "?????????????????????????????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        });


        radio_nextlink_org_rg_rm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(checkedRadioButtonId);
                int index = ((RadioGroup) radioButton.getParent()).indexOfChild(radioButton);
                OrgItem orgItem = orgItems.get(index);
                selAssignOrg = orgItem.getName();
                getPersonByOrgCodeAndName(orgItem.getName(), orgItem.getCode());

                //   assigneersAdapter.notifyDatasetChanged(orgItems);
            }
        });
    }


    private void getPersonByOrgCodeAndName(String name, String code) {
        new PSHUploadEventService(getApplicationContext()).getPersonByOrgApiDataObservable(code, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetPersonByOrgApiData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetPersonByOrgApiData getPersonByOrgApiData) {
                        List<Person> personList = getPersonByOrgApiData.getUserFormList();
                        if (personList != null && !personList.isEmpty()) {
                            List<Assigneers.Assigneer> assigneeList = new ArrayList<>();
                            for (Person person : personList) {
                                assigneeList.add(new Assigneers.Assigneer(person.getCode(), person.getName()));
                            }
                            assigneersAdapter.notifyDatasetChanged(assigneeList);
                        }
                    }
                });
    }

    private void getOrgList() {
        new PSHUploadEventService(getApplicationContext()).getOrgItemList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<OrgItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<OrgItem> list) {
                        if (list != null &&
                                !list.isEmpty()) {

                            orgItems = list;
                            int size = orgItems.size();
                            for (int i = 0; i < size; i++) {
                                RadioButton radioButton = new RadioButton(PSHEventUploadActivity.this);
                                radioButton.setText(orgItems.get(i).getName());
                                //    radioButton.setTag(nextLinkOrgs);
                                radioButton.setLayoutParams(params);
                                radio_nextlink_org_rg_rm.addView(radioButton);

                                if (i == 0) {
                                    radioButton.setChecked(true);
                                }
                            }


                        }

                    }
                });
    }

    /**
     * ??????????????????????????????R1????????????????????????
     */
    private void getNextLinkAssigneers() {
        new PSHUploadEventService(getApplicationContext()).getTaskUserByloginName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Assigneers>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<Assigneers> assigneersList) {
                        if (assigneersList != null
                                && !ListUtil.isEmpty(assigneersList)) {
                            List<Assigneers.Assigneer> assigneeList = new ArrayList<>();
                            for (Assigneers assigneers : assigneersList) {
                                assigneeList.addAll(assigneers.getAssigneers());
                            }
                            assigneersAdapter.notifyDatasetChanged(assigneeList);

                        }
                    }
                });
    }

    /**
     * ???????????????????????????????????????
     *
     * @param selectComponentFinishEvent
     */
    @Subscribe
    public void onReceivedFinishedSelectEvent2(SelectComponentFinishEvent2 selectComponentFinishEvent) {
        Component component = selectComponentFinishEvent.getFindResult();
        mSelComponent = component;
        mSelDetailAddress = selectComponentFinishEvent.getDetailAddress();


        if (mSelComponent != null) {
            //??????????????????
            layerName = component.getLayerName();
//            selFacilityType(layerName);
            mFacilityTypeAdapter.selectItemByName(layerName);
            mFacilityTypeAdapter.setEnable(false);
//            RadioGroupUtil.disableRadioGroup(facility_type_rg);  //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            String layerId = getLayerId(selectComponentFinishEvent);
            this.layerId = layerId;
            setObjectId(component);
            setXY(selectComponentFinishEvent);
            String subType = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SUBTYPE));
            String type = LayerUrlConstant.getLayerNameByUnknownLayerUrl(component.getLayerUrl());
            String title = StringUtil.getNotNullString(type, "");
            if (!ListUtil.isEmpty(subType)) {
                title = title + "(" + StringUtil.getNotNullString(subType, "") + ")";
            }
            tv_select_or_check_location.setText(title);
//            addrItem.setText(StringUtil.getNotNullString(mSelComponent.getGraphic().getAttributeValue("ADDR"), ""));
//            roadItem.setText("");
            if (selectComponentFinishEvent.getFindResult().getGraphic().getGeometry() instanceof Point) {
                Point point = (Point) selectComponentFinishEvent.getFindResult().getGraphic().getGeometry();
                requestAddress(point.getX(), point.getY(), new Callback1<BaiduGeocodeResult>() {
                    @Override
                    public void onCallback(BaiduGeocodeResult baiduGeocodeResult) {
                        if (baiduGeocodeResult == null) {
                            return;
                        }
                        addrItem.setText(StringUtil.getNotNullString(baiduGeocodeResult.getDetailAddress(), ""));
                        roadItem.setText(baiduGeocodeResult.getResult().getAddressComponent().getStreet());
                    }
                });
            }

        } else if (mSelDetailAddress != null) {
            //??????????????????
            mFacilityTypeAdapter.setEnable(true);
//            RadioGroupUtil.enableRadioGroup(facility_type_rg);   //?????????????????????????????????????????????
            tv_select_or_check_location.setText(mSelDetailAddress.getDetailAddress());
            addrItem.setText(mSelDetailAddress.getDetailAddress());
//            roadItem.setText(mSelDetailAddress.getStreet());
        }

    }

    private void setObjectId(Component component) {
        Integer objectid = component.getObjectId();
        componentId = objectid + "";
    }

    private void setXY(SelectComponentFinishEvent2 selectComponentFinishEvent) {
        if (selectComponentFinishEvent.getFindResult().getGraphic().getGeometry() instanceof Point) {
            Point point = (Point) selectComponentFinishEvent.getFindResult().getGraphic().getGeometry();
            //            modifiedIdentification.setX(point.getX());
            //            modifiedIdentification.setY(point.getY());
        }
    }

    /**
     * ???url?????????????????????id
     *
     * @param selectComponentFinishEvent
     * @return
     */
    @NonNull
    private String getLayerId(SelectComponentFinishEvent2 selectComponentFinishEvent) {
        int i = selectComponentFinishEvent.getFindResult().getLayerUrl().lastIndexOf("/");
        return selectComponentFinishEvent.getFindResult().getLayerUrl().substring(i + 1);
    }


    /**
     * ??????????????????
     */
    private void sendToServer() {
        PSHUploadEventService eventService = new PSHUploadEventService(PSHEventUploadActivity.this);
        String json = JsonUtil.getJson(problemUploadBean);
        HashMap<String, RequestBody> photoBody = getPhotoBody(photo_item.getSelectedPhotos(), photo_item.getThumbnailPhotos());

        //???????????????????????????
//        if("true".equals(isbyself)) {
//            if (getPhotoBodyForSelfProcess(photo_item_self.getSelectedPhotos()) !=null
//             && !getPhotoBodyForSelfProcess(photo_item_self.getSelectedPhotos()).isEmpty()) {
//                photoBody.putAll(getPhotoBodyForSelfProcess(photo_item_self.getSelectedPhotos()));
//            }
//        }

        String assigneeCode = null;
        String assigneeName = null;
//        if("false".equals(isbyself) && selAssignee != null){
//            //??????????????????????????????????????????????????????
//            assigneeCode = selAssignee.getUserCode();
//            assigneeName = selAssignee.getUserName();
//        }
//        String isSendMessage = "0";
//        if(cb_is_send_message.isChecked()){
//            isSendMessage = "1";
//        }else {
//            isSendMessage ="0";
//        }
        eventService.uploadWtsb(json, photoBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result2<String>>() {
                    @Override
                    public void onNext(Result2<String> s) {
                        if (s.getCode() != 200) {
                            progressDialog.dismiss();
                            ToastUtil.shortToast(PSHEventUploadActivity.this, s.getMessage());
                        } else {
                            progressDialog.dismiss();
                            ToastUtil.shortToast(PSHEventUploadActivity.this, "????????????");
                            finish();
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ToastUtil.shortToast(PSHEventUploadActivity.this, "???????????????????????????");
                    }
                });

    }

    /**
     * ?????????????????????
     */
    private void saveDraft() {
        AMDatabase.getInstance().save(problemUploadBean);
        List<Photo> photoList = photo_item.getSelectedPhotos();

        for (Photo photo : photoList) {
            photo.setProblem_id(problemUploadBean.getDbid() + "");
            photo.setPhotoName(PhotoUploadType.UPLOAD_FOR_PROBLEAM + photo.getPhotoName());
            AMDatabase.getInstance().save(photo);
        }

        //?????????????????????
        if ("true".equals(isbyself)) {
            List<Photo> photoList1 = photo_item_self.getSelectedPhotos();
            for (Photo photo : photoList1) {
                photo.setProblem_id(problemUploadBean.getDbid() + "");
                photo.setPhotoName(PhotoUploadType.UPLOAD_FOR_SELF + photo.getPhotoName());
                AMDatabase.getInstance().save(photo);
            }
        }
        ToastUtil.shortToast(this.getApplicationContext(), "????????????");
        finish();
    }

    /**
     * ?????????????????????????????????
     */
    private void startLocate() {
        LocationUtil.register(this, 1000, 0, new LocationUtil.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {

            }

            @Override
            public void onLocationChanged(Location location) {
//                lat = location.getLongitude() + "";
//                lng = location.getLatitude() + "";
                requestAddress(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
    }

    /**
     * ?????????????????????????????????
     *
     * @param location
     */
    private void requestAddress(final Location location) {
        BaiduApiService baiduApiService = new BaiduApiService(this);
        baiduApiService.parseLocation(new LatLng(location.getLatitude(), location.getLongitude()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaiduGeocodeResult>() {
                    @Override
                    public void call(BaiduGeocodeResult baiduGeocodeResult) {
//                        addrItem.setText(baiduGeocodeResult.getDetailAddress());
//                        roadItem.setText(baiduGeocodeResult.getResult().getAddressComponent().getStreet
//                                ());
                        if (baiduGeocodeResult != null) {
                            reportAddr = baiduGeocodeResult.getDetailAddress();
                            reportX = location.getLongitude();
                            reportY = location.getLatitude();
                        }

                    }
                });
    }

    /**
     * ?????????????????????????????????
     */
    private void requestAddress(final double x, final double y, final Callback1<BaiduGeocodeResult> callback) {
        BaiduApiService baiduApiService = new BaiduApiService(this);
        baiduApiService.parseLocation(new LatLng(y, x))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaiduGeocodeResult>() {
                    @Override
                    public void call(BaiduGeocodeResult baiduGeocodeResult) {
//                        addrItem.setText(baiduGeocodeResult.getDetailAddress());
//                        roadItem.setText(baiduGeocodeResult.getResult().getAddressComponent().getStreet
//                                ());
                        if (baiduGeocodeResult != null) {
                            callback.onCallback(baiduGeocodeResult);
                        } else {

                        }

                    }
                });
    }

    //???????????????????????????
    private HashMap<String, RequestBody> getPhotoBody(List<Photo> selectedPhotos, List<Photo> thumbPhotos) {
        String prefix = PhotoUploadType.UPLOAD_FOR_PROBLEAM;
        int i = 0;
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        if (!ListUtil.isEmpty(selectedPhotos)) {
            for (Photo photo : selectedPhotos) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    requestMap.put("file" + i + "\"; filename=\"" + prefix + file.getName()
                            , RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                }
            }
        }

        if (!ListUtil.isEmpty(thumbPhotos)) {
            for (Photo photo : thumbPhotos) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    requestMap.put("file" + i + "\"; filename=\"" + prefix + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                }
            }

        }
        return requestMap;
    }

    //???????????????????????????
    private HashMap<String, RequestBody> getPhotoBodyForSelfProcess(List<Photo> selectedPhotos) {
        String prefix = PhotoUploadType.UPLOAD_FOR_SELF;
        int i = 0;
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        if (!ListUtil.isEmpty(selectedPhotos)) {
            for (Photo photo : selectedPhotos) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    requestMap.put("file" + i + "\"; filename=\"" + prefix + file.getName()
                            , RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                }
            }
            return requestMap;
        }
        return null;
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @return
     */
    private ProblemBean getParamsEntity() {
        if (ListUtil.isEmpty(photo_item.getSelectedPhotos())) {
            ToastUtil.shortToast(this, "?????????????????????");
            return null;
        }
        if (!TextUtils.isEmpty(addrItem.getText())
                && (mJournal != null || mGeometry != null)) {
            problemUploadBean.setSzwz(addrItem.getText());
        } else {
            ToastUtil.shortToast(this, "?????????????????????");
            return null;
        }
//        if (!TextUtils.isEmpty(roadItem.getText())) {
//            problemUploadBean.setJDMC(roadItem.getText());
//        } else {
//            ToastUtil.shortToast(this, "????????????????????????");
//            return null;
//        }
//        if (!TextUtils.isEmpty(facilityCode)) {
//            problemUploadBean.setSSLX(facilityCode);
//            //uploadBean.setSSLX_NAME(facilityName);
//        } else {
//            ToastUtil.shortToast(this, "?????????????????????");
//            return null;
//        }
        /*if (!TextUtils.isEmpty(diseaseCode)) {
            problemUploadBean.setBHLX(diseaseCode);
            //uploadBean.setBHLX_NAME(diseaseName);

        } else {
            ToastUtil.shortToast(this, "?????????????????????");
            return null;
        }*/

        //??????????????????????????????
        List<DictionaryItem> selectedEventTypes = mEventTypeAdapter.getSelectedEventTypeList();
        List<DictionaryItem> selectedEventTypes2 = mEventTypeAdapter2.getSelectedEventTypeList();
        problemUploadBean.setPsdyId("");
        problemUploadBean.setPsdyName("");
        if ("??????".equals(facilityName) || TextUtils.isEmpty(problemUploadBean.getSslx())) {
            //???????????????????????????????????????????????????
            diseaseCode = "";
        } else if (!TextUtils.isEmpty(problemUploadBean.getSslx())) {
            if ("psh".equals(problemUploadBean.getSslx()) || "other".equals(problemUploadBean.getSslx())) {
                if (ListUtil.isEmpty(selectedEventTypes)) {
                    ToastUtil.shortToast(this, "?????????????????????");
                    return null;
                } else {
                    diseaseCode = "";
                    for (DictionaryItem dictionaryItem : selectedEventTypes) {
                        diseaseCode += "," + dictionaryItem.getCode();
                    }
                    diseaseCode = diseaseCode.substring(1);
                }
                if ("other".equals(problemUploadBean.getSslx())) {
                    //todo ?????????????????????
                    int currentPosition = sp_table_sewerageone_drainage_unit.getCurrentPosition();
                    if (drainageUnitList != null && !drainageUnitList.isEmpty()) {
                        if (currentPosition == -1) {
                            currentPosition = 0;
                        }
                        psdyId = drainageUnitList.get(currentPosition).getObjectId();
                        psdyName = drainageUnitList.get(currentPosition).getName();
                        problemUploadBean.setPsdyId(psdyId);
                        problemUploadBean.setPsdyName(psdyName);
                    } else {
                        problemUploadBean.setPsdyId("");
                        problemUploadBean.setPsdyName("");
                    }

                }
            } else if ("jhj".equals(problemUploadBean.getSslx()) || "jbj".equals(problemUploadBean.getSslx())) {
                if (ListUtil.isEmpty(selectedEventTypes) && ListUtil.isEmpty(selectedEventTypes2)) {
                    ToastUtil.shortToast(this, "?????????????????????");
                    return null;
                }
                /*if(ListUtil.isEmpty(selectedEventTypes2)){
                    ToastUtil.shortToast(this, "???????????????????????????");
                    return null;
                }*/
                diseaseCode = "";
                if (!ListUtil.isEmpty(selectedEventTypes)) {
                    for (DictionaryItem dictionaryItem : selectedEventTypes) {
                        diseaseCode += "," + dictionaryItem.getCode();
                    }
                }
                if (!ListUtil.isEmpty(selectedEventTypes2)) {
                    for (DictionaryItem dictionaryItem : selectedEventTypes2) {
                        diseaseCode += "," + dictionaryItem.getCode();
                    }
                }
                diseaseCode = diseaseCode.substring(1);

            }
        } else {
            diseaseCode = "";
            for (DictionaryItem dictionaryItem : selectedEventTypes) {
                diseaseCode += "," + dictionaryItem.getCode();
            }
            diseaseCode = diseaseCode.substring(1);
        }
        problemUploadBean.setWtlx(diseaseCode);

//        if (!TextUtils.isEmpty(urgencyCode)) {
//            problemUploadBean.setJjcd(urgencyCode);
//            //uploadBean.setJJCD_NAME(urgencyName);
//        } else {
//            ToastUtil.shortToast(this, "?????????????????????");
//            return null;
//        }
        //????????????????????????????????????????????????????????????
        if (TextUtils.isEmpty(problemitem.getText())) {
            ToastUtil.shortToast(this, "?????????????????????");
            return null;
        }
        problemUploadBean.setWtms(problemitem.getText());

        problemUploadBean.setWtly("0");

//        if("false".equals(isbyself)){
//            if(selAssignee == null){
//                ToastUtil.shortToast(this, "??????????????????????????????");
//                return null;
//            }
//            problemUploadBean.setAssignee(selAssignee.getUserCode());
//            problemUploadBean.setAssigneeName(selAssignee.getUserName());
//        }

        String lng = null;
        String lat = null;

        if (mSelComponent != null) {
            lng = StringUtil.valueOf(((Point) mSelComponent.getGraphic().getGeometry()).getX());
            lat = StringUtil.valueOf(((Point) mSelComponent.getGraphic().getGeometry()).getY());
        } else if (mSelDetailAddress != null) {
            lng = StringUtil.valueOf(mSelDetailAddress.getX());
            lat = StringUtil.valueOf(mSelDetailAddress.getY());
        } else {
            lng = StringUtil.valueOf(reportX);
            lat = StringUtil.valueOf(reportY);
            problemUploadBean.setReportaddr(reportAddr);
        }

        if (!TextUtils.isEmpty(lng)) {
            problemUploadBean.setReportx(lng);
        }
        if (!TextUtils.isEmpty(lat)) {
            problemUploadBean.setReporty(lat);
        }
        if (isDrainageDepartment && StringUtil.isEmpty(problem_tab_zgjy.getText())) {
            ToastUtil.shortToast(this, "?????????????????????");
            return null;
        }
        problemUploadBean.setZgjy(problem_tab_zgjy.getText());//????????????

        problemUploadBean.setReportx(StringUtil.valueOf(reportX));
        problemUploadBean.setReporty(StringUtil.valueOf(reportY));
        problemUploadBean.setReportaddr(reportAddr);
        problemUploadBean.setSBR(BaseInfoManager.getUserName(PSHEventUploadActivity.this));
        problemUploadBean.setLoginname(BaseInfoManager.getLoginName(PSHEventUploadActivity.this));

        return problemUploadBean;
    }

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @return
     */
    private ProblemBean getDraftParamsEntity() {

        if (ListUtil.isEmpty(photo_item.getSelectedPhotos())) {
            ToastUtil.shortToast(this, "?????????????????????");
            return null;
        }
        if (!TextUtils.isEmpty(addrItem.getText())
                && (mSelComponent != null || mSelDetailAddress != null)) {
            problemUploadBean.setSzwz(addrItem.getText());
        } else {
            ToastUtil.shortToast(this, "?????????????????????");
            return null;
        }

        /*if (!TextUtils.isEmpty(diseaseCode)) {
            problemUploadBean.setBHLX(diseaseCode);
            //uploadBean.setBHLX_NAME(diseaseName);

        } else {

        }*/

        List<DictionaryItem> selectedEventTypes = mEventTypeAdapter.getSelectedEventTypeList();
        if (ListUtil.isEmpty(selectedEventTypes)) {
            diseaseCode = "";
        } else {
            diseaseCode = "";
            for (DictionaryItem dictionaryItem : selectedEventTypes) {
                diseaseCode = diseaseCode + "," + dictionaryItem.getCode();
            }
            diseaseCode = diseaseCode.substring(1);
            problemUploadBean.setWtlx(diseaseCode);
        }

//        if (!TextUtils.isEmpty(urgencyCode)) {
//            problemUploadBean.setJjcd(urgencyCode);
//            //uploadBean.setJJCD_NAME(urgencyName);
//        } else {
//
//        }
        if (!TextUtils.isEmpty(problemitem.getText())) {
            problemUploadBean.setWtms(problemitem.getText());
        } else {
        }

        problemUploadBean.setWtly("0");

        String lng = null;
        String lat = null;

        if (mSelComponent != null) {
            lng = StringUtil.valueOf(((Point) mSelComponent.getGraphic().getGeometry()).getX());
            lat = StringUtil.valueOf(((Point) mSelComponent.getGraphic().getGeometry()).getY());
        } else if (mSelDetailAddress != null) {
            lng = StringUtil.valueOf(mSelDetailAddress.getX());
            lat = StringUtil.valueOf(mSelDetailAddress.getY());
        } else {
            lng = StringUtil.valueOf(reportX);
            lat = StringUtil.valueOf(reportY);
            problemUploadBean.setReportaddr(reportAddr);
        }

        if (!TextUtils.isEmpty(lng)) {
            problemUploadBean.setReportx(lng);
        }
        if (!TextUtils.isEmpty(lat)) {
            problemUploadBean.setReporty(lat);
        }

        problemUploadBean.setReportx(StringUtil.valueOf(reportX));
        problemUploadBean.setReporty(StringUtil.valueOf(reportY));
        problemUploadBean.setReportaddr(reportAddr);
        problemUploadBean.setSBR(BaseInfoManager.getUserName(PSHEventUploadActivity.this));
        problemUploadBean.setLoginname(BaseInfoManager.getLoginName(PSHEventUploadActivity.this));

        return problemUploadBean;
    }

    //????????????
    private void initUrgencyView() {
        for (int i = 0; i < urgency_type_array.length; i++) {
            RadioButton radioButton = new RadioButton(PSHEventUploadActivity.this);
            radioButton.setText(urgency_type_array[i]);
            radioButton.setLayoutParams(params);
            urgency_state_rg.addView(radioButton);
        }
//        selUrgencyState(urgency_type_array[0]);
    }

    private void initEventTypeView(List<DictionaryItem> eventTypeList, String defaultStr) {
        List<DictionaryItem> itemList = new ArrayList<>();
        for (DictionaryItem item : eventTypeList) {
            if (item.getValue().equals("0")) {
                continue;
            }
            itemList.add(item);
        }
        Collections.sort(itemList, new Comparator<DictionaryItem>() {
            @Override
            public int compare(DictionaryItem o1, DictionaryItem o2) {
                String code = o1.getCode();
                String target = code;
                if (code.length() > 1) {
                    target = code.replaceAll("[^(0-9)]", "");//??????????????????
                }

                String code2 = o2.getCode();
                String target2 = code;
                if (code2.length() > 1) {
                    target2 = code2.replaceAll("[^(0-9)]", "");//??????????????????
                }

                int num1 = Integer.valueOf(target);
                int num2 = Integer.valueOf(target2);
                int result = 0;
                if (num1 > num2) {
                    result = 1;
                }

                if (num1 < num2) {
                    result = -1;
                }
                return result;
            }
        });
        mEventTypeAdapter.notifyDataSetChanged(itemList);

        List<DictionaryItem> selectedDictionaryItem = new ArrayList<>();
        if (defaultStr != null) {
            String[] strs = defaultStr.split(",");
            for (int j = 0; j < strs.length; j++) {
                for (int i = 0; i < itemList.size(); i++) {
                    if (itemList.get(i).getCode().equals(strs[j])) {

                        selectedDictionaryItem.add(itemList.get(i));
                    }
                }
            }
            if (!selectedDictionaryItem.isEmpty()) {
                mEventTypeAdapter.setmSelectedEventTypeList(selectedDictionaryItem);
            }
        }
    }

    private void initEventTypeView2(List<DictionaryItem> eventTypeList, String defaultStr) {
        List<DictionaryItem> itemList = new ArrayList<>();
        for (DictionaryItem item : eventTypeList) {
            if (item.getValue().equals("0")) {
                continue;
            }
            itemList.add(item);
        }
        Collections.sort(itemList, new Comparator<DictionaryItem>() {
            @Override
            public int compare(DictionaryItem o1, DictionaryItem o2) {
                String code = o1.getCode();
                String target = code;
                if (code.length() > 1) {
                    target = code.replaceAll("[^(0-9)]", "");//??????????????????
                }

                String code2 = o2.getCode();
                String target2 = code;
                if (code2.length() > 1) {
                    target2 = code2.replaceAll("[^(0-9)]", "");//??????????????????
                }

                int num1 = Integer.valueOf(target);
                int num2 = Integer.valueOf(target2);
                int result = 0;
                if (num1 > num2) {
                    result = 1;
                }

                if (num1 < num2) {
                    result = -1;
                }
                return result;
            }
        });
        mEventTypeAdapter2.notifyDataSetChanged(itemList);

        List<DictionaryItem> selectedDictionaryItem = new ArrayList<>();
        if (defaultStr != null) {
            String[] strs = defaultStr.split(",");
            for (int j = 0; j < strs.length; j++) {
                for (int i = 0; i < itemList.size(); i++) {
                    if (itemList.get(i).getCode().equals(strs[j])) {

                        selectedDictionaryItem.add(itemList.get(i));
                    }
                }
            }
            if (!selectedDictionaryItem.isEmpty()) {
                mEventTypeAdapter2.setmSelectedEventTypeList(selectedDictionaryItem);
            }
        }
    }

    //????????????
    @Deprecated
    private void initDiseaseView(List<DictionaryItem> problemList) {
        disease_type_rg.removeAllViews();
        if (ListUtil.isEmpty(problemList)) {
            return;
        }
        Collections.sort(problemList, new Comparator<DictionaryItem>() {
            @Override
            public int compare(DictionaryItem o1, DictionaryItem o2) {
                String code = o1.getCode();
                String target = code;
                if (code.length() > 1) {
                    target = code.replaceAll("[^(0-9)]", "");//??????????????????
                }

                String code2 = o2.getCode();
                String target2 = code;
                if (code2.length() > 1) {
                    target2 = code2.replaceAll("[^(0-9)]", "");//??????????????????
                }

                int num1 = Integer.valueOf(target);
                int num2 = Integer.valueOf(target2);
                int result = 0;
                if (num1 > num2) {
                    result = 1;
                }

                if (num1 < num2) {
                    result = -1;
                }
                return result;
            }
        });
        Random ra = new Random();
        for (int i = 0; i < problemList.size(); i++) {
            RadioButton radioButton = new RadioButton(PSHEventUploadActivity.this);
//            radioButton.setId(ra.nextInt());
            radioButton.setText(problemList.get(i).getName());
            radioButton.setLayoutParams(params);
            disease_type_rg.addView(radioButton);
            if (i == 0) {
                //                // ????????????????????????1 ???????????????????????????????????????
                //                //??????id ??????radioButton ??????
                //                RadioButton rb_checked = (RadioButton) radioGroup.findViewById(radioButton.getId());
                //                //??????????????????
                //                rb_checked.setChecked(true);

                // ????????????????????????2
//                disease_type_rg.check(radioButton.getId());
            }
        }
        selEventType(problemList.get(0).getName());
    }

    //???????????????????????????
    private void initFacilityType(List<DictionaryItem> facility_type_list) {
        Collections.sort(facility_type_list, new Comparator<DictionaryItem>() {
            @Override
            public int compare(DictionaryItem o1, DictionaryItem o2) {
                String code = o1.getCode();
                String target = code;
                if (code.length() > 1) {
                    target = code.replaceAll("[^(0-9)]", "");//??????????????????
                }

                String code2 = o2.getCode();
                String target2 = code;
                if (code2.length() > 1) {
                    target2 = code2.replaceAll("[^(0-9)]", "");//??????????????????
                }

                int num1 = Integer.valueOf(target);
                int num2 = Integer.valueOf(target2);
                int result = 0;
                if (num1 > num2) {
                    result = 1;
                }

                if (num1 < num2) {
                    result = -1;
                }
                return result;
            }
        });
        mFacilityTypeAdapter.notifyDataSetChanged(facility_type_list);
        mFacilityTypeAdapter.setSelectedPosition(0);
    }

    //???????????????????????????
    @Deprecated
    private void initFacilityView(List<DictionaryItem> facility_type_list) {
        List<DictionaryItem> itemList = new ArrayList<>();
        for (DictionaryItem item : facility_type_list) {
            if (item.getValue().equals("0")) {
                continue;
            }
            itemList.add(item);
        }
        Collections.sort(itemList, new Comparator<DictionaryItem>() {
            @Override
            public int compare(DictionaryItem o1, DictionaryItem o2) {
                String code = o1.getCode();
                String target = code;
                if (code.length() > 1) {
                    target = code.replaceAll("[^(0-9)]", "");//??????????????????
                }

                String code2 = o2.getCode();
                String target2 = code;
                if (code2.length() > 1) {
                    target2 = code2.replaceAll("[^(0-9)]", "");//??????????????????
                }

                int num1 = Integer.valueOf(target);
                int num2 = Integer.valueOf(target2);
                int result = 0;
                if (num1 > num2) {
                    result = 1;
                }

                if (num1 < num2) {
                    result = -1;
                }
                return result;
            }
        });
        Random ra = new Random();
        for (int i = 0; i < facility_type_list.size(); i++) {
            RadioButton radioButton = new RadioButton(PSHEventUploadActivity.this);
//            radioButton.setId(ra.nextInt());
//            radioButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_radiobutton2, null));
            radioButton.setText(facility_type_list.get(i).getName());
            radioButton.setLayoutParams(params);
//            facility_type_rg.addView(radioButton);
            if (i == 0) {
                //                // ????????????????????????1 ???????????????????????????????????????
                //                //??????id ??????radioButton ??????
                //                RadioButton rb_checked = (RadioButton) radioGroup.findViewById(radioButton.getId());
                //                //??????????????????
                //                rb_checked.setChecked(true);

                // ????????????????????????2
//                facility_type_rg.check(radioButton.getId());
            }
        }
        selFacilityType(facility_type_list.get(0).getName());
    }

    private void initNextLinkAssignnersRadioButton(Assigneers assigneers) {
        /**
         *  64dp???????????????{@link DrawerLayout#MIN_DRAWER_MARGIN}+10dp*2??????????????????padding=84dp
         */
        float margin = DensityUtils.dp2px(this, 60);
        float width = DensityUtils.getWidth(this);
        for (Assigneers.Assigneer assigneer : assigneers.getAssigneers()) {
            RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.radiobutton_view, null);
            rb.setText(assigneer.getUserName());
            rb.setTag(assigneer);
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams((int) (width - margin) / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
            rb.setLayoutParams(lp);
        }

    }

    /**
     * ???????????????????????????
     *
     * @param layerName
     */
    private void selFacilityType(String layerName) {
//        for (int i = 0; i < facility_type_rg.getChildCount(); i++) {
//            View rbv = facility_type_rg.getChildAt(i);
//            if (rbv instanceof RadioButton) {
//                RadioButton rb = (RadioButton) rbv;
//                if (rb.getText().toString().equals(layerName)) {
//                    facility_type_rg.check(rb.getId());
//                    break;
//                }
//            }
//        }
    }

    /**
     * ???????????????????????????
     *
     * @param eventTypes
     */
    private void selEventType(String eventTypes) {
        String[] eventTypeArr = eventTypes.split(",");
        mEventTypeAdapter.setSelectedEventTypes(eventTypeArr);
    }

    /**
     * ???????????????????????????????????????
     * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param selectHouseFinishEvent
     */
    @Subscribe
    public void onReceivedFinishedSelectEvent2(SelectHouseFinishEvent selectHouseFinishEvent) {
        if (selectHouseFinishEvent != null) {
            PSHHouse pshHouse = selectHouseFinishEvent.getPSHHouse();
            if (pshHouse != null) {
                tv_select_or_check_location.setText(pshHouse.getName());
                problemUploadBean.setPshid(Long.valueOf(pshHouse.getId()));
                problemUploadBean.setPshmc(pshHouse.getName());
                addrItem.setText(pshHouse.getAddr());
            }
            mGeometry = selectHouseFinishEvent.getGeometry();
            if (mGeometry != null) {
                Point point = (Point) mGeometry;
                problemUploadBean.setX(String.valueOf(point.getX()));
                problemUploadBean.setY(String.valueOf(point.getY()));
            }
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @param event
     */
    @Subscribe
    public void onReceivedFinishedSelectEvent2(SelectFinishEvent event) {
        if (event != null) {
            ProblemFeatureOrAddr featureOrAddr = event.featureOrAddr;
            if (featureOrAddr != null) {
                tv_select_or_check_location.setText(featureOrAddr.getName());
                problemUploadBean.setPshid(Long.valueOf(featureOrAddr.getId()));
                problemUploadBean.setPshDj(featureOrAddr.getPshDj());
                if (!TextUtils.isEmpty(featureOrAddr.getSslx())) {
                    ll_psdy.setVisibility(View.GONE);
                    problemUploadBean.setPshmc(featureOrAddr.getName());
                    ll_eventtype.setVisibility(View.VISIBLE);
                    problemUploadBean.setSslx(featureOrAddr.getSslx());
                    problemUploadBean.setReportType(featureOrAddr.getReportType());
                    if ("psh".equals(featureOrAddr.getSslx())) {
                        facility_type_list = dbService.getDictionaryByTypecodeInDB(PSH_EVENT_TYPE_DICT);
                        tv_event_type_outside.setVisibility(View.GONE);
                        tv_event_type_inside.setVisibility(View.GONE);
                        gv_eventtype2.setVisibility(View.GONE);
                        initEventTypeView(facility_type_list, null);
                    } else {
                        tv_event_type_outside.setVisibility(View.VISIBLE);
                        tv_event_type_inside.setVisibility(View.VISIBLE);
                        gv_eventtype2.setVisibility(View.VISIBLE);
                        if (featureOrAddr.getAttrTwo() != null
                                && (featureOrAddr.getAttrTwo().contains("??????")
                                || featureOrAddr.getAttrTwo().contains("??????"))) {
                            facility_type_list = dbService.getDictionaryByTypecodeInDB(WSJ_OUTSIDE_EVENT_TYPE_DICT);
                            problemList2 = dbService.getDictionaryByTypecodeInDB(WSJ_INSIDE_EVENT_TYPE_DICT);
                            initEventTypeView(facility_type_list, null);
                            initEventTypeView2(problemList2, null);
                        } else if (featureOrAddr.getAttrTwo() != null
                                && featureOrAddr.getAttrTwo().contains("??????")) {
                            facility_type_list = dbService.getDictionaryByTypecodeInDB(YSJ_OUTSIDE_EVENT_TYPE_DICT);
                            problemList2 = dbService.getDictionaryByTypecodeInDB(YSJ_INSIDE_EVENT_TYPE_DICT);
                            initEventTypeView(facility_type_list, null);
                            initEventTypeView2(problemList2, null);
                        }
                    }
                } else {
                    ll_psdy.setVisibility(View.VISIBLE);
                    tv_select_or_check_location.setText(featureOrAddr.getAddr());
                    ll_eventtype.setVisibility(View.VISIBLE);
//                    ll_eventtype.setVisibility(View.GONE);
                    problemUploadBean.setSslx("other");
//                    problemUploadBean.setReportType(null);
                    problemUploadBean.setPshmc("");
                    facility_type_list = dbService.getDictionaryByTypecodeInDB(KD_EVENT_TYPE_DICT);
                    tv_event_type_outside.setVisibility(View.GONE);
                    tv_event_type_inside.setVisibility(View.GONE);
                    gv_eventtype2.setVisibility(View.GONE);
                    initEventTypeView(facility_type_list, null);
                }
                addrItem.setText(featureOrAddr.getAddr());
            }
            mGeometry = event.mGeometry;
            if (mGeometry != null) {
                Point point = (Point) mGeometry;
                problemUploadBean.setX(String.valueOf(point.getX()));
                problemUploadBean.setY(String.valueOf(point.getY()));
            }
        }
    }

    /**
     * ???????????????????????????
     *
     * @param eventType
     */
    @Deprecated
    private void selEventType2(String eventType) {
        for (int i = 0; i < disease_type_rg.getChildCount(); i++) {
            View rbv = disease_type_rg.getChildAt(i);
            if (rbv instanceof RadioButton) {
                RadioButton rb = (RadioButton) rbv;
                if (rb.getText().toString().equals(eventType)) {
                    disease_type_rg.check(rb.getId());
                    break;
                }
            }
        }
    }

    /**
     * ???????????????????????????
     *
     * @param urgency
     */
    private void selUrgencyState(String urgency) {
        for (int i = 0; i < urgency_state_rg.getChildCount(); i++) {
            View rbv = urgency_state_rg.getChildAt(i);
            if (rbv instanceof RadioButton) {
                RadioButton rb = (RadioButton) rbv;
                if (rb.getText().toString().equals(urgency)) {
                    urgency_state_rg.check(rb.getId());
                    break;
                }
            }
        }
    }

    private int getScreenWidths() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        return width;
    }

    @Override
    public void onBackPressed() {
        DialogUtil.MessageBox(PSHEventUploadActivity.this, "??????", "?????????????????????????????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (photo_item != null) {
            photo_item.onActivityResult(requestCode, resultCode, data);
        }

        if (photo_item_self != null) {
            photo_item_self.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationUtil.unregister(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onDrainageUnitLoadFinish(DrainageUnitListBean drainageUnits) {
        img_table_sewerageone_drainage_unit_loading.setVisibility(View.GONE);
        if (drainageUnits != null && drainageUnits.drainageList != null && !drainageUnits.drainageList.isEmpty()) {
            // ????????????
            if (drainageUnitList == null) {
                drainageUnitList = new ArrayList<>(drainageUnits.drainageList.size());
            }
            drainageUnitList.clear();
            final Map<String, Object> drainageUnitNames = new LinkedHashMap<>(drainageUnits.drainageList.size());
            for (DrainageUnit unit:drainageUnits.drainageList) {
//                final DrainageUnit unit = drainageUnits.drainageList.get(i);
//                final DrainageUnit unit = drainageUnits.drainageList.get(i);
                drainageUnitList.add(unit);
                drainageUnitNames.put(unit.getName(), unit.getName());
            }
            sp_table_sewerageone_drainage_unit.setSpinnerData(drainageUnitNames);
//            tableSewerageOneData.imgTableSewerageoneDrainageUnitLocation.setVisibility(pshAffairDetail == null ? View.GONE : View.VISIBLE);
        } else {
            if (drainageUnitList != null) {
                drainageUnitList.clear();
            }
            sp_table_sewerageone_drainage_unit.removeAll();
            // ??????????????????????????????????????????????????????
//            tableSewerageOneData.imgTableSewerageoneDrainageUnitLocation.setVisibility(View.VISIBLE);
        }
        // ?????????????????????????????????????????????
        img_table_sewerageone_drainage_unit_location.setVisibility(View.VISIBLE);
    }
}
