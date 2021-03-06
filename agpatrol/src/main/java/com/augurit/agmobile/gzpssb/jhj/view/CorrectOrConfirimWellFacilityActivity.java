package com.augurit.agmobile.gzpssb.jhj.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.OnSpinnerChangedEvent;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.widget.FlexRadioGroup;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentFieldKeyConstant;
import com.augurit.agmobile.gzps.publicaffair.service.FacilityAffairService;
import com.augurit.agmobile.gzps.uploadfacility.model.FacilityAddressErrorModel;
import com.augurit.agmobile.gzps.uploadfacility.model.FacilityInfoErrorModel;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.service.CorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.util.UploadLayerFieldKeyConstant;
import com.augurit.agmobile.gzps.uploadfacility.view.SendFacilityOwnerShipUnit;
import com.augurit.agmobile.gzps.uploadfacility.view.UploadFacilitySuccessEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.FacilityAddressErrorView;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.RefreshMyModificationListEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.drainageentity.DrainageEntityView;
import com.augurit.agmobile.gzps.uploadfacility.view.drainageentity.SelectDrainageEntityActivity;
import com.augurit.agmobile.gzps.uploadfacility.view.facilityprobrem.FacilityProblemView;
import com.augurit.agmobile.gzpssb.common.view.MultiTakePhotoTableItem;
import com.augurit.agmobile.gzpssb.jbjpsdy.service.JournalService;
import com.augurit.agmobile.gzpssb.jhj.model.DoorNOBean;
import com.augurit.agmobile.gzpssb.jhj.model.RefreshTypeEvent;
import com.augurit.agmobile.mapengine.common.utils.GeometryUtil;
import com.augurit.agmobile.mapengine.map.geometry.LatLng;
import com.augurit.agmobile.patrolcore.baiduapi.BaiduApiService;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.agmobile.patrolcore.common.model.LocationInfo;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.agmobile.patrolcore.locate.BaiduLocationManager;
import com.augurit.agmobile.patrolcore.selectlocation.model.BaiduGeocodeResult;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.loc.util.LocationUtil;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.service.LoginService;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.cmpt.widget.spinner.AMSpinner;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.esri.core.geometry.Point;
import com.google.android.flexbox.FlexboxLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * ???????????????????????????????????????????????????????????????????????????????????????????????????
 * <p>
 * Created by xcl on 2017/12/15.
 */

public class CorrectOrConfirimWellFacilityActivity extends BaseActivity {

    private MultiTakePhotoTableItem takePhotoItem;
    private View btn_upload_journal;

    private ModifiedFacility modifiedFacility;

    private TextItemTableItem tableitem_current_time;
    private TextItemTableItem tableitem_current_user;
    private TextFieldTableItem textitem_description;
    private LinearLayout ll_container;

    //???????????????
    private static final String COMPONENT_NOT_EXIST = "1";
    //????????????
    private static final String OTHERS = "2";

    private String errorType = OTHERS;

    private FacilityAddressErrorView componentAddressErrorView;
    private WellFacilityInfoErrorView componentInfoErrorView;
    private RadioButton lastSelectButton = null;
    private Component mComponent;
    private TextItemTableItem textitem_facility;

    private FlexRadioGroup rg_1;
    private CheckBox rb_component_not_exist;
    private LinearLayout ll_address_container;
    private LinearLayout ll_address;

    private View ll_textitem_description_container;

    private BaiduLocationManager baiduLocationManager;
    private Toast toast;
    private ProgressDialog progressDialog;
    private CountDownTimer countDownTimer; //?????????????????????????????????????????????

    /**
     * ????????????
     */
    private FlexboxLayout ll_problems_container;
    private FacilityProblemView facilityProblemView;
    //???????????????
    private MultiTakePhotoTableItem take_photo_well;

    /**
     * ?????????
     */
    private View ll_drainage_entity;
    private View btn_add_drainage_entity;
    private FlexboxLayout ll_drainage_entity_container;
    DrainageEntityView mDrainageEntityView;


    /**
     * ?????????
     */
    private CheckBox cbInnerCity;
    /**
     * ?????????
     */
    private CheckBox cbDontKnowWhere;

    private String cityVillage;
    private CheckBox cbOthers;
    private EditText etOthers;
    private AMSpinner spinnerOthers;
    private boolean mManStatusIsSpinner = false;   //?????????????????????????????????Spinner??????EditText
    private String layerName;
    private LinearLayout ll_glzt_containt;
    private List<DoorNOBean> mDoorNOBean = new ArrayList<>();
    private List<DoorNOBean> mDoorNOBeans;
    //????????????????????????????????????
    private boolean isAllowEditWellType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_correct_or_confirm_well_facility);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        modifiedFacility = new ModifiedFacility();
        mComponent = (Component) getIntent().getSerializableExtra("component");
        isAllowEditWellType = getIntent().getBooleanExtra("isAllowEditWellType", false);
        if (mComponent != null) {
            layerName = mComponent.getLayerName();
        }
        initView();

        initUser();

        initData();

        requestLocation();

        //????????????????????????
        startLocate();
    }

    private void startLocate() {

        baiduLocationManager = new BaiduLocationManager(this);
        baiduLocationManager.startLocate(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LocationInfo lastLocation = baiduLocationManager.getLastLocation();
                if (lastLocation != null) {
                    baiduLocationManager.stopLocate();
                    if (lastLocation.getLocation() != null) {
                        modifiedFacility.setUserX(lastLocation.getLocation().getLongitude());
                        modifiedFacility.setUserY(lastLocation.getLocation().getLatitude());
                    }
                    if (lastLocation.getDetailAddress() != null) {
                        modifiedFacility.setUserAddr(lastLocation.getDetailAddress().getDetailAddress());
                    }
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    /**
     * ??????????????????
     */
    private void requestLocation() {
        if (mComponent != null) {
            Point geometryCenter = GeometryUtil.getGeometryCenter(mComponent.getGraphic().getGeometry());
            BaiduApiService selectLocationService = new BaiduApiService(this);
            selectLocationService.parseLocation(new LatLng(geometryCenter.getY(), geometryCenter.getX()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaiduGeocodeResult>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(BaiduGeocodeResult baiduGeocodeResult) {
                            if (componentAddressErrorView != null && baiduGeocodeResult != null) {
                                componentAddressErrorView.changeAddress(baiduGeocodeResult.getDetailAddress(), baiduGeocodeResult.getResult().getAddressComponent().getStreet());
                            }
                            if (baiduGeocodeResult != null) {
                                modifiedFacility.setAddr(baiduGeocodeResult.getDetailAddress());
                                modifiedFacility.setRoad(baiduGeocodeResult.getResult().getAddressComponent().getStreet());
                            }
                        }
                    });
        }
    }


    private void initData() {

        if (mComponent != null && mComponent.getGraphic() != null && mComponent.getGraphic().getAttributes() != null) {
            Map<String, Object> attributes = mComponent.getGraphic().getAttributes();
            Object o = attributes.get(ComponentFieldKeyConstant.ADDR);
            Object road = attributes.get(ComponentFieldKeyConstant.ROAD);
            if (o != null && road != null) {
                componentAddressErrorView.setAddress(o.toString(), road.toString());
            }
            String markId = StringUtil.getNotNullString(String.valueOf(attributes.get(ComponentFieldKeyConstant.MARK_ID)), "");
            String usid = StringUtil.getNotNullString(String.valueOf(attributes.get(ComponentFieldKeyConstant.USID)), "");
            String objectId = String.valueOf(attributes.get(ComponentFieldKeyConstant.OBJECTID));
            if (!StringUtil.isEmpty(markId)) {
                modifiedFacility.setId(Long.valueOf(markId));
            }
            modifiedFacility.setUsid(usid);
            modifiedFacility.setObjectId(objectId);
            modifiedFacility.setLayerName(mComponent.getLayerName());
            String title = "";
            if (!StringUtil.isEmpty(usid)) {
                title = StringUtil.getNotNullString(mComponent.getLayerName(), "") + "(" + StringUtil.getNotNullString(usid, "") + ")";
            } else {
                title = StringUtil.getNotNullString(mComponent.getLayerName(), "") + "(" + StringUtil.getNotNullString(objectId, "") + ")";
            }
            textitem_facility.setText(title);
            textitem_facility.setReadOnly();

            Point geometryCenter = GeometryUtil.getGeometryCenter(mComponent.getGraphic().getGeometry());
            modifiedFacility.setOriginX(geometryCenter.getX());
            modifiedFacility.setOriginY(geometryCenter.getY());

            modifiedFacility.setLayerUrl(mComponent.getLayerUrl());
        }
    }


    private void initUser() {
        User user = new LoginService(this, AMDatabase.getInstance()).getUser();
        String userName = user.getUserName();
        String userId = user.getId();

        modifiedFacility.setMarkPerson(userName);
        modifiedFacility.setMarkPersonId(userId);
        tableitem_current_user.setText(userName);
    }


    private void initView() {
        //????????????

        takePhotoItem = (MultiTakePhotoTableItem) findViewById(R.id.take_photo_item);
        takePhotoItem.setTitle("??????????????????????????????");
        takePhotoItem.setPhotoExampleEnable(true);
//        takePhotoItem.setRequired(true);
        ((TextView) findViewById(R.id.tv_title)).setText("????????????");

        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        /**
         * ????????????
         */
        tableitem_current_time = (TextItemTableItem) findViewById(R.id.tableitem_current_time);
        /**
         * ?????????
         */
        tableitem_current_user = (TextItemTableItem) findViewById(R.id.tableitem_current_user);
        tableitem_current_user.setReadOnly();
        /**
         * ??????
         */
        textitem_description = (TextFieldTableItem) findViewById(R.id.textitem_description);
        if (mComponent != null) {
            textitem_description.setText(StringUtil.getNotNullString(mComponent.getGraphic().getAttributeValue("DESRIPTION"), ""));
        }

        /**
         * ??????
         */
        btn_upload_journal = findViewById(R.id.btn_upload_journal);

        RxView.clicks(btn_upload_journal)
                .throttleFirst(2, TimeUnit.SECONDS)   //2???????????????????????????????????????????????????
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                        if (OTHERS.equals(errorType)) {
//                            List<Photo> selectedPhotos = takePhotoItem.getSelectedPhotos();
//                            if (ListUtil.isEmpty(selectedPhotos) || selectedPhotos.size() < 3) {
//                                showToast("?????????????????????????????????????????????????????????");
//                                //ToastUtil.iconLongToast(getApplicationContext(), R.mipmap.ic_alert_yellow, "?????????????????????????????????????????????????????????");;
//                                return;
//                            }
                        }

                        /**
                         * ???????????????????????????????????????????????????
                         */
                        if (cbOthers.isChecked() && !mManStatusIsSpinner && TextUtils.isEmpty(etOthers.getText().toString())) {
                            showToast("???????????????????????????");
                            return;
                        }


                        progressDialog = new ProgressDialog(CorrectOrConfirimWellFacilityActivity.this);
                        progressDialog.setMessage("????????????????????????");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        upload();
                    }
                });

        /**
         * ?????????
         */
        ll_drainage_entity = findViewById(R.id.ll_drainage_entity);
        ll_drainage_entity.setVisibility(View.GONE);
        btn_add_drainage_entity = findViewById(R.id.btn_add_drainage_entity);
        ll_drainage_entity_container = (FlexboxLayout) findViewById(R.id.ll_drainage_entity_container);
        btn_add_drainage_entity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CorrectOrConfirimWellFacilityActivity.this, SelectDrainageEntityActivity.class);
                if (mDrainageEntityView != null) {
                    intent.putExtra("data", mDrainageEntityView.getDrainageEntitys());
                }
                startActivityForResult(intent, SelectDrainageEntityActivity.SELECT_DRAINAGE_ENTITY);
            }
        });


        /**
         * ?????????
         */
        cbInnerCity = (CheckBox) findViewById(R.id.cb_inner_city);
        /**
         * ?????????
         */
        cbDontKnowWhere = (CheckBox) findViewById(R.id.cb_dont_know_where);
        /**
         * ??????
         */
        cbOthers = (CheckBox) findViewById(R.id.cb_others);
        etOthers = (EditText) findViewById(R.id.et_others);
        spinnerOthers = (AMSpinner) findViewById(R.id.spinner_others);
        spinnerOthers.setOnItemClickListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                if (item instanceof DictionaryItem) {
//                    etOthers.setText(((DictionaryItem) item).getName());
                }
            }
        });

        cbInnerCity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cityVillage = cbInnerCity.getText().toString();
                    cbDontKnowWhere.setChecked(false);
                    cbOthers.setChecked(false);
                }
            }
        });

        cbDontKnowWhere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cityVillage = cbDontKnowWhere.getText().toString();
                    cbInnerCity.setChecked(false);
                    cbOthers.setChecked(false);
                }
            }
        });
        cbOthers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etOthers.setEnabled(true);
                    spinnerOthers.setEditable(true);
                    cbInnerCity.setChecked(false);
                    cbDontKnowWhere.setChecked(false);
                    if (mManStatusIsSpinner) {
                        spinnerOthers.setVisibility(View.VISIBLE);
                    } else {
                        etOthers.setVisibility(View.VISIBLE);
                    }
                } else {
                    etOthers.setEnabled(false);
                    spinnerOthers.setEditable(false);
                    etOthers.setVisibility(View.GONE);
                    spinnerOthers.setVisibility(View.GONE);
                }
            }
        });
        String city_village = StringUtil.getNotNullString(mComponent.getGraphic().getAttributeValue("CITY_VILLAGE"), "");
        if (mComponent != null && cbInnerCity.getText().toString().equals(city_village)) {
            cbInnerCity.setChecked(true);
        } else if (mComponent != null && cbDontKnowWhere.getText().toString().equals(city_village)) {
            cbDontKnowWhere.setChecked(true);
        } else if (mComponent != null && !TextUtils.isEmpty(city_village)) {
            /**
             * ???????????????"?????????"/"?????????"??????????????????
             */
            cbOthers.setChecked(true);
            etOthers.setText(city_village);
        }

        /**
         * ????????????
         */
        ll_glzt_containt = (LinearLayout) findViewById(R.id.ll_glzt_containt);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        ll_address_container = (LinearLayout) findViewById(R.id.ll_address_container);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_textitem_description_container = findViewById(R.id.ll_textitem_description_container);

        /**
         * ????????????
         */
        componentAddressErrorView = new FacilityAddressErrorView(CorrectOrConfirimWellFacilityActivity.this,
                mComponent);
        ll_address_container.removeAllViews();
        componentAddressErrorView.addTo(ll_address_container);
        /**
         * ????????????
         */
        mComponent.setAllowEditWellType(isAllowEditWellType);
        componentInfoErrorView = new WellFacilityInfoErrorView(CorrectOrConfirimWellFacilityActivity.this,
                mComponent);
        ll_container.removeAllViews();
        componentInfoErrorView.addTo(ll_container);

        if (mComponent != null) {
            loadDataImage();
        }
        /**
         * ????????????
         */
        ll_problems_container = (FlexboxLayout) findViewById(R.id.ll_problems_container);

        String pCode = StringUtil.getNotNullString(mComponent.getGraphic().getAttributeValue("PCODE"), "");
        if (mComponent != null && !StringUtil.isEmpty(pCode)) {
            String childCode = StringUtil.getNotNullString(mComponent.getGraphic().getAttributeValue("CHILD_CODE"), "");
            facilityProblemView = new FacilityProblemView(this, ll_problems_container, pCode, childCode);
        } else {
            facilityProblemView = new FacilityProblemView(this, ll_problems_container);
        }
        final View ll_problems_container_parent = findViewById(R.id.ll_problems_container_parent);
        if ("?????????".equals(layerName)) {
            ll_problems_container_parent.setVisibility(View.GONE);
            ll_glzt_containt.setVisibility(View.GONE);
        } else {
//            ll_problems_container_parent.setVisibility(View.VISIBLE);
//            ll_glzt_containt.setVisibility(View.VISIBLE);
        }
        /**
         * ????????????
         */
        View tvResetProblems = findViewById(R.id.tv_reset_problems);
        tvResetProblems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (facilityProblemView != null) {
                    facilityProblemView.reset();
                }
            }
        });

        /**
         * ????????????
         */
        textitem_facility = (TextItemTableItem) findViewById(R.id.textitem_facility);

        rb_component_not_exist = (CheckBox) findViewById(R.id.rb_component_not_exist);
        rb_component_not_exist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    errorType = COMPONENT_NOT_EXIST;
                    ll_container.setVisibility(View.GONE);
                    ll_address.setVisibility(View.GONE);
                    takePhotoItem.setVisibility(View.GONE);
                    ll_textitem_description_container.setVisibility(View.GONE);
                    ll_problems_container_parent.setVisibility(View.GONE);
                } else {
                    errorType = OTHERS;
                    ll_container.setVisibility(View.VISIBLE);
                    ll_address.setVisibility(View.VISIBLE);
                    takePhotoItem.setVisibility(View.VISIBLE);
                    ll_textitem_description_container.setVisibility(View.VISIBLE);
                    ll_problems_container_parent.setVisibility(View.VISIBLE);
                }
            }
        });

        take_photo_well = (MultiTakePhotoTableItem) findViewById(R.id.take_photo_well);
    }

    private void loadDataImage() {
        final FacilityAffairService identificationService = new FacilityAffairService(this);
        String id = StringUtil.getNotNullString(mComponent.getGraphic().getAttributeValue("MARK_ID"), "0");
        identificationService.getModifiedDetail(Long.valueOf(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModifiedFacility>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ModifiedFacility modifiedIdentifications) {
                        if (modifiedIdentifications != null) {
                            takePhotoItem.setSelectedPhotos(modifiedIdentifications.getPhotos());
                            takePhotoItem.setSelThumbPhotos(modifiedIdentifications.getThumbnailPhotos());

                            take_photo_well.setSelectedPhotos(modifiedIdentifications.getWellPhotos());
                            take_photo_well.setSelThumbPhotos(modifiedIdentifications.getWellThumbnailPhotos());
                            modifiedIdentifications.setAllowEditWellType(isAllowEditWellType);
                            componentInfoErrorView = new WellFacilityInfoErrorView(CorrectOrConfirimWellFacilityActivity.this,
                                    modifiedIdentifications);
                            ll_container.removeAllViews();
                            componentInfoErrorView.addTo(ll_container);
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSpinnerChangedEvent(OnSpinnerChangedEvent onSpinnerChangedEvent) {
        /*if(YinjingAttributeViewUtil.ATTRIBUTE_ONE.equals(onSpinnerChangedEvent.getSpinnerName())){
            if(ll_drainage_entity != null) {
                if ("?????????".equals(onSpinnerChangedEvent.getKey())) {
                    ll_drainage_entity.setVisibility(View.VISIBLE);
                    if(mDrainageEntityView == null){
                        mDrainageEntityView = new DrainageEntityView(this, ll_drainage_entity_container);
                    }
                } else {
                    ll_drainage_entity.setVisibility(View.GONE);
                }
            }
        }*/
    }

    private void upload() {

        completeOriginAddress();

        completeOriginAttributes();

        boolean b = completeAttributes();
        /**
         * ?????????????????????????????????????????????????????????
         */
        if (!b) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            return;
        }

        completeCorrectTypeAndReportType();

        boolean b1 = completeAddress();
        /**
         * ????????????????????????????????????????????????????????????
         */
        if (!b1) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            return;
        }

        modifiedFacility.setDescription(textitem_description.getText());
        modifiedFacility.setMarkPerson(tableitem_current_user.getText());


        /**
         * ????????????
         */
        if (cbDontKnowWhere.isChecked() || cbInnerCity.isChecked()) {
            modifiedFacility.setCityVillage(cityVillage);
        } else if (cbOthers.isChecked()) {
            if (mManStatusIsSpinner) {
                modifiedFacility.setCityVillage(spinnerOthers.getText());
            } else {
                modifiedFacility.setCityVillage(etOthers.getText().toString());
            }
        } else {
            modifiedFacility.setCityVillage(null);
        }


        completeMarkTime();

        //????????????
        completeFacilityProblem();

        //20???????????????
        countDownTimer = new CountDownTimer(CorrectFacilityService.TIMEOUT * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long time = millisUntilFinished / 1000;
                if (progressDialog != null) {
                    progressDialog.setMessage("???????????????????????????   " + time + "s");
                }
                if (time % 20 == 0) {
                    ToastUtil.longToast(CorrectOrConfirimWellFacilityActivity.this, "?????????????????????");
                }
            }

            @Override
            public void onFinish() {

            }
        };

        if (countDownTimer != null) {
            countDownTimer.start();
        }

        //????????????
        CorrectFacilityService identificationService = new CorrectFacilityService(getApplicationContext());
        identificationService.upload(modifiedFacility)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                            countDownTimer = null;
                        }

                        btn_upload_journal.setEnabled(true);
                        CrashReport.postCatchedException(new Exception("???????????????????????????????????????" +
                                BaseInfoManager.getUserName(CorrectOrConfirimWellFacilityActivity.this) + "????????????" + e.getLocalizedMessage()));
                        showToast("????????????");
                        //ToastUtil.shortToast(CorrectOrConfirimWellFacilityActivity.this, "????????????");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                            countDownTimer = null;
                        }
                        btn_upload_journal.setEnabled(true);
                        if (responseBody.getCode() == 200) {
//                            showToast("????????????");
                            // ToastUtil.shortToast(CorrectOrConfirimWellFacilityActivity.this, "????????????");
                            EventBus.getDefault().post(new RefreshMyModificationListEvent());
                            EventBus.getDefault().post(new UploadFacilitySuccessEvent());
//                            finish();
//                            uploadJournal(modifiedFacility);
                        } else {
                            CrashReport.postCatchedException(new Exception("???????????????????????????????????????" +
                                    BaseInfoManager.getUserName(CorrectOrConfirimWellFacilityActivity.this) + "????????????" + responseBody.getMessage()));
                            showToast("???????????????????????????" + responseBody.getMessage());
                            //ToastUtil.shortToast(CorrectOrConfirimWellFacilityActivity.this, "???????????????????????????" + responseBody.getMessage());
                        }
                    }
                });
    }

    /**
     * ????????????????????????id
     *
     * @param mpBeen
     * @return
     */
    private List<Long> getMphDels(List<DoorNOBean> mpBeen) {
        List<Long> delIds = new ArrayList<>();
        if (ListUtil.isEmpty(mDoorNOBeans)) {
            return delIds;
        }
        for (DoorNOBean doorNOBean : mDoorNOBeans) {
            boolean isDeles = true;
            for (DoorNOBean doorNOBean1 : mpBeen) {
                if (doorNOBean.getMpObjectId().equals(doorNOBean1.getMpObjectId())) {
                    isDeles = false;
                    break;
                }
            }
            if (isDeles) {
                delIds.add(doorNOBean.getId());
            }
        }
        return delIds;
    }

    /**
     * ????????????????????????id
     *
     * @return
     */
    private List<Long> getAllMphIds() {
        List<Long> delIds = new ArrayList<>();
        if (ListUtil.isEmpty(mDoorNOBeans)) {
            return delIds;
        }
        for (DoorNOBean doorNOBean : mDoorNOBeans) {
            if (doorNOBean.getId() != null) {
                delIds.add(doorNOBean.getId());
            }
        }
        return delIds;
    }

    private void completeFacilityProblem() {
        if (OTHERS.equals(errorType)) {
            //????????????
            List<String> selectedLargeAndSmallItemList = facilityProblemView.getSelectedParentAndChildICodeList();
            //????????????
            if (selectedLargeAndSmallItemList != null && !TextUtils.isEmpty(selectedLargeAndSmallItemList.get(0))) {
                modifiedFacility.setpCode(selectedLargeAndSmallItemList.get(0));
            } else {
                modifiedFacility.setpCode("");
            }

            //????????????
            if (selectedLargeAndSmallItemList != null && !TextUtils.isEmpty(selectedLargeAndSmallItemList.get(1))) {
                modifiedFacility.setChildCode(selectedLargeAndSmallItemList.get(1));
            } else {
                modifiedFacility.setChildCode("");
            }
        } else {
            //???????????????
            modifiedFacility.setChildCode("");
            modifiedFacility.setpCode("");
        }
    }

    /**
     * ?????????????????????
     */
    private void completeOriginAttributes() {
        if (componentInfoErrorView != null) {
            FacilityInfoErrorModel facilityInfoErrorModel = componentInfoErrorView.getOriginalModel();
            modifiedFacility.setOriginAttrOne(facilityInfoErrorModel.getAttrOne());
            modifiedFacility.setOriginAttrTwo(facilityInfoErrorModel.getAttrTwo());
            modifiedFacility.setOriginAttrThree(facilityInfoErrorModel.getAttrThree());
            modifiedFacility.setOriginAttrFour(facilityInfoErrorModel.getAttrFour());
            modifiedFacility.setOriginAttrFive(facilityInfoErrorModel.getAttrFive());
        }
    }

    private void completeMarkTime() {
        long currentTimeMillis = System.currentTimeMillis();
        modifiedFacility.setMarkTime(currentTimeMillis);
        tableitem_current_time.setText(TimeUtil.getStringTimeYMDMChines(new Date(currentTimeMillis)));
    }

    private boolean completeAddress() {
        /**
         * ???????????????????????????
         */
        if (componentAddressErrorView != null && OTHERS.equals(errorType)) {
            if (!componentAddressErrorView.ifAllowUpload()) {
                ToastUtil.iconShortToast(this, R.mipmap.ic_alert_yellow, componentAddressErrorView.getNotAllowUploadReason());
                return false;
            }
            FacilityAddressErrorModel facilityAddressErrorModel = componentAddressErrorView.getFacilityAddressErrorModel();
            // if (componentAddressErrorView.getFacilityAddressErrorModel().isHasModified()) {
            modifiedFacility.setAddr(facilityAddressErrorModel.getCorrectAddress());
            modifiedFacility.setRoad(facilityAddressErrorModel.getRoad());
            //  }

            /**
             * ????????????????????????????????????????????????????????????????????????????????????
             */
//            if (modifiedFacility.getCorrectType().equals("????????????")) {
//                modifiedFacility.setX(modifiedFacility.getUserX());
//                modifiedFacility.setY(modifiedFacility.getUserY());
//            } else {
            modifiedFacility.setX(facilityAddressErrorModel.getCorrectX());
            modifiedFacility.setY(facilityAddressErrorModel.getCorrectY());

//            }
            return true;
        }

        /**
         * ?????????????????????????????????x,y????????????????????????????????????
         */
        if (COMPONENT_NOT_EXIST.equals(errorType) && mComponent != null && mComponent.getGraphic() != null) {
            Point geometryCenter = GeometryUtil.getGeometryCenter(mComponent.getGraphic().getGeometry());
            modifiedFacility.setX(geometryCenter.getX());
            modifiedFacility.setY(geometryCenter.getY());
        }

        return !errorType.equals(OTHERS);

    }

    private void completeOriginAddress() {
        if (componentAddressErrorView != null) {

            if (mComponent != null && mComponent.getGraphic().getAttributes() != null) {
                Map<String, Object> attributes = mComponent.getGraphic().getAttributes();
                Object o = attributes.get(ComponentFieldKeyConstant.ADDR);
                Object road = attributes.get(ComponentFieldKeyConstant.ROAD);
                if (o != null && road != null) {
                    modifiedFacility.setOriginRoad(road.toString());
                    modifiedFacility.setOriginAddr(o.toString());
                }
            }

            if (mComponent != null && mComponent.getGraphic() != null) {
                Point geometryCenter = GeometryUtil.getGeometryCenter(mComponent.getGraphic().getGeometry());
                modifiedFacility.setOriginX(geometryCenter.getX());
                modifiedFacility.setOriginY(geometryCenter.getY());
            }
        }
    }

    private boolean completeAttributes() {
        if (componentInfoErrorView != null && OTHERS.equals(errorType)) {
            FacilityInfoErrorModel facilityInfoErrorModel = componentInfoErrorView.getFacilityInfoErrorModel();
            if (!facilityInfoErrorModel.isIfAllowUpload()) {
                ToastUtil.iconShortToast(getApplicationContext(), R.mipmap.ic_alert_yellow, facilityInfoErrorModel.getNotAllowUploadReason());
                return false;
            }

            modifiedFacility.setAttrOne(facilityInfoErrorModel.getAttrOne());
            modifiedFacility.setAttrTwo(facilityInfoErrorModel.getAttrTwo());
            modifiedFacility.setAttrThree(facilityInfoErrorModel.getAttrThree());
            modifiedFacility.setAttrFour(facilityInfoErrorModel.getAttrFour());
            modifiedFacility.setAttrFive(facilityInfoErrorModel.getAttrFive());
            return true;
        }
        return true;
    }

    private void completeCorrectTypeAndReportType() {

        if (OTHERS.equals(errorType)) {
            if (componentInfoErrorView.getFacilityInfoErrorModel().isHasModified() &&
                    componentAddressErrorView.getFacilityAddressErrorModel().isHasModified()) {

                modifiedFacility.setCorrectType("?????????????????????");
                modifiedFacility.setReportType(UploadLayerFieldKeyConstant.CORRECT_ERROR);

            } else if (componentInfoErrorView.getFacilityInfoErrorModel().isHasModified()
                    && !componentAddressErrorView.getFacilityAddressErrorModel().isHasModified()) {

                modifiedFacility.setCorrectType("????????????");
                modifiedFacility.setReportType(UploadLayerFieldKeyConstant.CORRECT_ERROR);

            } else if (!componentInfoErrorView.getFacilityInfoErrorModel().isHasModified() &&
                    componentAddressErrorView.getFacilityAddressErrorModel().isHasModified()) {
                modifiedFacility.setCorrectType("????????????");
                modifiedFacility.setReportType(UploadLayerFieldKeyConstant.CORRECT_ERROR);
            } else {
                modifiedFacility.setCorrectType("????????????");
                modifiedFacility.setReportType(UploadLayerFieldKeyConstant.CONFIRM);
            }

            List<Photo> selectedPhotos = takePhotoItem.getSelectedPhotos();
            modifiedFacility.setPhotos(selectedPhotos);
            modifiedFacility.setThumbnailPhotos(takePhotoItem.getThumbnailPhotos());

            if (take_photo_well != null) {
                List<Photo> selectedPhotosWell = take_photo_well.getSelectedPhotos();
                modifiedFacility.setWellPhotos(selectedPhotosWell);
                modifiedFacility.setWellThumbnailPhotos(take_photo_well.getThumbnailPhotos());
            }

        } else {
            modifiedFacility.setCorrectType("???????????????");
            modifiedFacility.setReportType(UploadLayerFieldKeyConstant.CORRECT_ERROR);

            //?????????????????????????????????
//            if (mComponent != null && mComponent.getGraphic() != null &&
//                    mComponent.getGraphic().getAttributes() != null) {
//
//                Object addr = mComponent.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ADDR);
//                if (addr != null) {
//                    modifiedFacility.setOriginAddr(addr.toString());
//                } else {
//                    modifiedFacility.setOriginAddr("");
//                }
//
//                Object road = mComponent.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ROAD);
//                if (road != null) {
//                    modifiedFacility.setRoad(road.toString());
//                } else {
//                    modifiedFacility.setOriginAddr("");
//                }
//
//            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (takePhotoItem != null) {
            takePhotoItem.onActivityResult(requestCode, resultCode, data);
        }
        if (mDrainageEntityView != null) {
            mDrainageEntityView.onActivityResult(requestCode, resultCode, data);
        }
        if (findViewById(R.id.take_photo_well) != null) {
            take_photo_well = (MultiTakePhotoTableItem) findViewById(R.id.take_photo_well);
            take_photo_well.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe
    public void refreshFactifityType(RefreshTypeEvent event) {
        int type = event.getType();
        mComponent.setLayerName(type == 1 ? "??????" : (type == 2 ? "?????????" : "?????????"));
        if (mComponent != null && mComponent.getGraphic() != null && mComponent.getGraphic().getAttributes() != null) {
            Map<String, Object> attributes = mComponent.getGraphic().getAttributes();
            Object o = attributes.get(ComponentFieldKeyConstant.ADDR);
            Object road = attributes.get(ComponentFieldKeyConstant.ROAD);
            if (o != null && road != null) {
                componentAddressErrorView.setAddress(o.toString(), road.toString());
            }
            String markId = StringUtil.getNotNullString(String.valueOf(attributes.get(ComponentFieldKeyConstant.MARK_ID)), "");
            String usid = StringUtil.getNotNullString(String.valueOf(attributes.get(ComponentFieldKeyConstant.USID)), "");
            String objectId = String.valueOf(attributes.get(ComponentFieldKeyConstant.OBJECTID));
            if (!StringUtil.isEmpty(markId)) {
                modifiedFacility.setId(Long.valueOf(markId));
            }
            modifiedFacility.setUsid(usid);
            modifiedFacility.setObjectId(objectId);
            modifiedFacility.setLayerName(mComponent.getLayerName());
            String title = "";
            if (!StringUtil.isEmpty(usid)) {
                title = StringUtil.getNotNullString(mComponent.getLayerName(), "") + "(" + StringUtil.getNotNullString(usid, "") + ")";
            } else {
                title = StringUtil.getNotNullString(mComponent.getLayerName(), "") + "(" + StringUtil.getNotNullString(objectId, "") + ")";
            }
            textitem_facility.setText(title);
            textitem_facility.setReadOnly();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if (componentAddressErrorView != null) {
            componentAddressErrorView.destroy();
        }

        if (componentInfoErrorView != null) {
            componentInfoErrorView.destroy();
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (LocationUtil.ifUnRegister()) {
            LocationUtil.unregister(CorrectOrConfirimWellFacilityActivity.this);
        }

        if (facilityProblemView != null) {
            facilityProblemView = null;
        }
    }


    public void showToast(String toastString) {
        if (toast == null || toast.getView().findViewById(R.id.tv_toast_text) == null) {
            initToast(toastString);
        } else {
            ((TextView) toast.getView().findViewById(com.augurit.am.fw.R.id.tv_toast_text)).setText(toastString);
        }
        //????????????
        toast.show();
    }

    public void initToast(String toastString) {
        //??????????????????Toast????????????
        toast = new Toast(getApplication());
        //??????Tosat???????????????????????????
        toast.setDuration(Toast.LENGTH_SHORT);
        View view1 = View.inflate(this, com.augurit.am.fw.R.layout.view_toast, null);
        ((TextView) view1.findViewById(com.augurit.am.fw.R.id.tv_toast_text)).setText(toastString);
        ((ImageView) view1.findViewById(com.augurit.am.fw.R.id.iv_icon)).setImageResource(R.mipmap.ic_alert_yellow);
        //???????????????????????????Toast
        toast.setView(view1);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }

    public void showAlertDialog() {

        DialogUtil.MessageBox(this, "??????", "????????????????????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    /**
     * ??????????????????
     *
     * @param sendFacilityOwnerShipUnit
     */
    @Subscribe
    public void onReceivedFacilityOwnerShipUnit(SendFacilityOwnerShipUnit sendFacilityOwnerShipUnit) {
        if (sendFacilityOwnerShipUnit != null && !TextUtils.isEmpty(sendFacilityOwnerShipUnit.originOwnership)) {
            //cbOthers.setChecked(true);
            //etOthers.setText(sendFacilityOwnerShipUnit.originOwnership);

            //????????????????????????
            TableDBService tableDBService = new TableDBService(this);
            List<DictionaryItem> districts = tableDBService.getDictionaryByTypecodeInDB("A169");
            //????????????????????????????????????
            String districtCode = null;
            for (DictionaryItem district : districts) {
                boolean contains = sendFacilityOwnerShipUnit.originOwnership.contains(district.getName());
                if (contains) {
                    districtCode = district.getCode();
                    break;
                }
            }
            //??????spinner
            spinnerOthers.removeAll();
            //????????????????????????????????????????????????????????????
            if (districtCode != null) {
                List<DictionaryItem> ownershipDic = tableDBService.getChildDictionaryByPCodeInDB(districtCode);
                if (!ListUtil.isEmpty(ownershipDic)) {
                    mManStatusIsSpinner = true;
                    if (cbOthers.isChecked()) {
                        spinnerOthers.setVisibility(View.VISIBLE);
                        etOthers.setVisibility(View.GONE);
                    }
                    //??????
                    Collections.sort(ownershipDic, new Comparator<DictionaryItem>() {
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
                    //??????spinner
                    for (DictionaryItem dictionaryItem : ownershipDic) {
                        //??????spinner
                        spinnerOthers.addItems(dictionaryItem.getName(), dictionaryItem);
                    }
                    String city_village = StringUtil.getNotNullString(mComponent.getGraphic().getAttributeValue("CITY_VILLAGE"), "");
                    if (spinnerOthers.containsKey(city_village)) {
                        spinnerOthers.selectItem(city_village);
                    } else {
                        spinnerOthers.selectItem(0);
                    }
                } else {
                    mManStatusIsSpinner = false;
                    if (cbOthers.isChecked()) {
                        spinnerOthers.setVisibility(View.GONE);
                        etOthers.setVisibility(View.VISIBLE);
                    }
//                    etOthers.setText("");
                }
            } else {
                mManStatusIsSpinner = false;
                if (cbOthers.isChecked()) {
                    spinnerOthers.setVisibility(View.GONE);
                    etOthers.setVisibility(View.VISIBLE);
                }
//                etOthers.setText("");
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param modifiedFacility
     */
    private void uploadJournal(ModifiedFacility modifiedFacility) {
        //????????????
        modifiedFacility.setId(null);
        JournalService identificationService = new JournalService(getApplicationContext());
        identificationService.upload(modifiedFacility)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        CrashReport.postCatchedException(new Exception("???????????????????????????????????????" +
                                BaseInfoManager.getUserName(CorrectOrConfirimWellFacilityActivity.this) + "????????????" + e.getLocalizedMessage()));
                        ToastUtil.shortToast(CorrectOrConfirimWellFacilityActivity.this, "??????????????????");
                        //ToastUtil.shortToast(CorrectOrConfirimWellFacilityActivity.this, "????????????");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (responseBody.getCode() == 200) {
                            ToastUtil.shortToast(CorrectOrConfirimWellFacilityActivity.this, "????????????");
                        } else {
                            CrashReport.postCatchedException(new Exception("???????????????????????????????????????" +
                                    BaseInfoManager.getUserName(CorrectOrConfirimWellFacilityActivity.this) + "????????????" + responseBody.getMessage()));
                            ToastUtil.shortToast(CorrectOrConfirimWellFacilityActivity.this, "???????????????????????????????????????" + responseBody.getMessage());
                            //ToastUtil.shortToast(CorrectOrConfirimWellFacilityActivity.this, "???????????????????????????" + responseBody.getMessage());
                        }
                    }
                });
    }

}


