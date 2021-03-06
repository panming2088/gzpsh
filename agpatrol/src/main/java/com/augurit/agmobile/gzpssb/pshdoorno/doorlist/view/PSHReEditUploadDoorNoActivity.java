package com.augurit.agmobile.gzpssb.pshdoorno.doorlist.view;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.widget.TakePhotoTableItem;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.uploadfacility.model.FacilityAddressErrorModel;
import com.augurit.agmobile.gzps.uploadfacility.model.FacilityAttributeModel;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.uploadfacility.view.SendFacilityOwnerShipUnit;
import com.augurit.agmobile.gzps.uploadfacility.view.UploadFacilitySuccessEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.FacilityAddressErrorView;
import com.augurit.agmobile.gzps.uploadfacility.view.facilityprobrem.FacilityProblemView;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.FacilityAttributeView;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.RefreshMyUploadList;
import com.augurit.agmobile.gzpssb.uploadfacility.service.PSHUploadFacilityService;
import com.augurit.agmobile.patrolcore.common.model.LocationInfo;
import com.augurit.agmobile.patrolcore.locate.BaiduLocationManager;
import com.augurit.am.cmpt.loc.util.LocationUtil;
import com.augurit.am.cmpt.login.service.LoginService;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.google.android.flexbox.FlexboxLayout;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * ????????????????????????
 *
 * @author ????????? ???xuciluan
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.gzps.upload.view
 * @createTime ???????????? ???17/12/18
 * @modifyBy ????????? ???xuciluan
 * @modifyTime ???????????? ???17/12/18
 * @modifyMemo ???????????????
 */

public class PSHReEditUploadDoorNoActivity extends BaseActivity {

    private TakePhotoTableItem take_photo_item;
    private View btn_upload_journal;

    private TextView tv_select_or_check_location;
    private TextItemTableItem tableitem_current_time;
    private TextItemTableItem tableitem_current_user;
    private TextFieldTableItem textitem_description;
    private LinearLayout ll_attributelist_container;

    private LinearLayout ll_container;
    private FacilityAttributeView facilityAttributeView;


    private UploadedFacility uploadedFacility;
    private BaiduLocationManager baiduLocationManager;
    private ProgressDialog progressDialog;
    private CountDownTimer countDownTimer; //?????????????????????????????????????????????
    private FlexboxLayout ll_problems_container;
    private FacilityProblemView facilityProblemView;
    private FacilityAddressErrorView mComponentAddressErrorView;

    private CheckBox cbInnerCity;
    private CheckBox cbDontKnowWhere;

    private String cityVillage;
    private CheckBox cbOthers;
    private EditText etOthers;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reedit_upload_new_doorno);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        /**
         * ???Activity????????????????????????detailAddress???location????????????????????????????????????????????????????????????????????????
         */
        uploadedFacility = getIntent().getParcelableExtra("data");

        initView();

        //????????????????????????
        startLocate();
    }


    private void initView() {

        take_photo_item = (TakePhotoTableItem) findViewById(R.id.take_photo_item);
        take_photo_item.setPhotoExampleEnable(true);
        take_photo_item.setRequired(true);
        if (uploadedFacility != null) {
            take_photo_item.setSelectedPhotos(uploadedFacility.getPhotos());
        }
        ((TextView) findViewById(R.id.tv_title)).setText("????????????");

        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo ????????????????????????????????????????????????????????????????????????????????????????????????
                showAlertDialog();
            }
        });

        ll_container = (LinearLayout) findViewById(R.id.ll_container);


        /**
         * ????????????
         */
        tableitem_current_time = (TextItemTableItem) findViewById(R.id.tableitem_current_time);
        tableitem_current_time.setVisibility(View.GONE);


        /**
         * ????????????
         */
        ll_attributelist_container = (LinearLayout) findViewById(R.id.ll_attributelist_container);
        facilityAttributeView = new FacilityAttributeView(PSHReEditUploadDoorNoActivity.this, uploadedFacility);
        ll_attributelist_container.removeAllViews();
        ll_attributelist_container.setVisibility(View.VISIBLE);
        facilityAttributeView.addTo(ll_attributelist_container);
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

        cbInnerCity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    cityVillage = cbInnerCity.getText().toString();
                    cbDontKnowWhere.setChecked(false);
                    cbOthers.setChecked(false);
                }
            }
        });

        cbDontKnowWhere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    cityVillage = cbDontKnowWhere.getText().toString();
                    cbInnerCity.setChecked(false);
                    cbOthers.setChecked(false);
                }
            }
        });

        cbOthers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    etOthers.setEnabled(true);
                    cbInnerCity.setChecked(false);
                    cbDontKnowWhere.setChecked(false);
                }else {
                    etOthers.setEnabled(false);
                }
            }
        });

        if (uploadedFacility != null && cbInnerCity.getText().toString().equals(uploadedFacility.getCityVillage())){
            cbInnerCity.setChecked(true);
        }else if (uploadedFacility != null && cbDontKnowWhere.getText().toString().equals(uploadedFacility.getCityVillage())){
            cbDontKnowWhere.setChecked(true);
        }else if (uploadedFacility != null && !TextUtils.isEmpty(uploadedFacility.getCityVillage())){
            /**
             * ???????????????"?????????"/"?????????"??????????????????
             */
            cbOthers.setChecked(true);
            etOthers.setText(uploadedFacility.getCityVillage());
        }


        /**
         * ?????????
         */
        tableitem_current_user = (TextItemTableItem) findViewById(R.id.tableitem_current_user);
        LoginService loginService = new LoginService(this, AMDatabase.getInstance());
        tableitem_current_user.setText(loginService.getUser().getUserName());
        tableitem_current_user.setReadOnly();
        uploadedFacility.setMarkPersonId(loginService.getUser().getLoginName());
        uploadedFacility.setMarkPerson(loginService.getUser().getUserName());

        /**
         * ??????
         */
        textitem_description = (TextFieldTableItem) findViewById(R.id.textitem_description);
        if (uploadedFacility != null) {
            textitem_description.setText(uploadedFacility.getDescription());
        }

        /**
         * ????????????
         */
        LinearLayout selectLocationContainer = (LinearLayout) findViewById(R.id.rl_select_location_container);
        mComponentAddressErrorView = new FacilityAddressErrorView(PSHReEditUploadDoorNoActivity.this,
                uploadedFacility);
        selectLocationContainer.removeAllViews();
        mComponentAddressErrorView.addTo(selectLocationContainer);


        /**
         * ????????????
         */
        ll_problems_container = (FlexboxLayout) findViewById(R.id.ll_problems_container);
        if (uploadedFacility != null) {
            facilityProblemView = new FacilityProblemView(this, ll_problems_container, uploadedFacility.getpCode(), uploadedFacility.getChildCode());
        } else {
            facilityProblemView = new FacilityProblemView(this, ll_problems_container);
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
         * ??????
         */
        btn_upload_journal = findViewById(R.id.btn_upload_journal);
        RxView.clicks(btn_upload_journal)
                .throttleFirst(2, TimeUnit.SECONDS)   //2???????????????????????????????????????????????????
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                        List<Photo> selectedPhotos = take_photo_item.getSelectedPhotos();
                        if (ListUtil.isEmpty(selectedPhotos) || selectedPhotos.size() < 3) {
                            ToastUtil.iconLongToast(getApplicationContext(), R.mipmap.ic_alert_yellow, "?????????????????????????????????????????????????????????");
                            return;
                        }

                        boolean ifCompleteAttributeSuccess = true;
                        if (facilityAttributeView.getFacilityAttributeModel() != null
                                && facilityAttributeView.getFacilityAttributeModel().getLayerName() != null) {
                            FacilityAttributeModel facilityAttributeModel = facilityAttributeView.getFacilityAttributeModel();
                            if (!facilityAttributeModel.isIfAllowUpload()) {
                                ToastUtil.iconShortToast(getApplicationContext(), R.mipmap.ic_alert_yellow,
                                        facilityAttributeModel.getNotAllowUploadReason());
                                ifCompleteAttributeSuccess = false;
                            }
                            uploadedFacility.setLayerName(facilityAttributeModel.getLayerName());
                            uploadedFacility.setComponentType(facilityAttributeModel.getLayerName());
                            uploadedFacility.setAttrOne(facilityAttributeModel.getAttrOne());
                            uploadedFacility.setAttrTwo(facilityAttributeModel.getAttrTwo());
                            uploadedFacility.setAttrThree(facilityAttributeModel.getAttrThree());
                            uploadedFacility.setAttrFour(facilityAttributeModel.getAttrFour());
                            uploadedFacility.setAttrFive(facilityAttributeModel.getAttrFive());
                        }

                        /**
                         * ?????????????????????????????????????????????????????????
                         */
                        if (!ifCompleteAttributeSuccess) {
                            return;
                        }

                        if (TextUtils.isEmpty(uploadedFacility.getComponentType())) {
                            ToastUtil.shortToast(getApplicationContext(), "????????????????????????");
                            return;
                        }

                        boolean b = completeAddress();
                        if (!b){
                            return;
                        }

                        /**
                         * ???????????????????????????????????????????????????
                         */
                        if (cbOthers.isChecked() && TextUtils.isEmpty(etOthers.getText().toString())){
                            ToastUtil.iconShortToast(getApplicationContext(),R.mipmap.ic_alert_yellow,"???????????????????????????");
                            return;
                        }

                        if (progressDialog == null) {
                            progressDialog = new ProgressDialog(PSHReEditUploadDoorNoActivity.this);
                            progressDialog.setMessage("????????????????????????");
                            progressDialog.setCancelable(false);
                        }

                        progressDialog.show();
                        uploadedFacility.setMarkTime(System.currentTimeMillis());
                        uploadedFacility.setUpdateTime(System.currentTimeMillis());
                        uploadedFacility.setDescription(textitem_description.getText());

                        /**
                         * ????????????
                         */
                        if (cbDontKnowWhere.isChecked() || cbInnerCity.isChecked()){
                            uploadedFacility.setCityVillage(cityVillage);
                        }else if (cbOthers.isChecked()){
                            uploadedFacility.setCityVillage(etOthers.getText().toString());
                        } else {
                            uploadedFacility.setCityVillage("");
                        }

                        /**
                         * ?????????????????????id???0?????????????????????????????????id????????????0???
                         */
                        if (!ListUtil.isEmpty(selectedPhotos)) {
                            List<Photo> newAddedPhotos = new ArrayList<>();
                            for (Photo photo : selectedPhotos) {
                                if (photo.getId() == 0) {
                                    newAddedPhotos.add(photo);
                                }
                            }
                            uploadedFacility.setPhotos(newAddedPhotos);
                        }
                        uploadedFacility.setThumbnailPhotos(take_photo_item.getThumbnailPhotos());

                        List<Photo> deletedPhotos = take_photo_item.getDeletedPhotos();
                        List<String> deletedPhotoIds = new ArrayList<>();
                        if (!ListUtil.isEmpty(deletedPhotos)) {
                            for (Photo photo : deletedPhotos) {
                                if (photo.getId() != 0) {
                                    deletedPhotoIds.add(String.valueOf(photo.getId()));
                                }
                            }
                            uploadedFacility.setDeletedPhotoIds(deletedPhotoIds);
                        }


                        //????????????
                        List<String> selectedLargeAndSmallItemList = facilityProblemView.getSelectedParentAndChildICodeList();
                        //????????????
                        if (selectedLargeAndSmallItemList!= null && !TextUtils.isEmpty(selectedLargeAndSmallItemList.get(0))) {
                            uploadedFacility.setpCode(selectedLargeAndSmallItemList.get(0));
                        }else {
                            uploadedFacility.setpCode("");
                        }

                        //????????????
                        if (selectedLargeAndSmallItemList!= null && !TextUtils.isEmpty(selectedLargeAndSmallItemList.get(1))) {
                            uploadedFacility.setChildCode(selectedLargeAndSmallItemList.get(1));
                        }else {
                            uploadedFacility.setChildCode("");
                        }


                        //20???????????????
                        countDownTimer = new CountDownTimer(PSHUploadFacilityService.TIMEOUT * 1000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                long time = millisUntilFinished / 1000;
                                if (progressDialog != null) {
                                    progressDialog.setMessage("???????????????????????????   " + time + "s");
                                }
                                if (time % 20 == 0) {
                                    ToastUtil.longToast(PSHReEditUploadDoorNoActivity.this, "?????????????????????");
                                }
                            }

                            @Override
                            public void onFinish() {

                            }
                        };

                        if (countDownTimer != null) {
                            countDownTimer.start();
                        }

                        PSHUploadFacilityService identificationService = new PSHUploadFacilityService(getApplicationContext());
                        identificationService.upload(uploadedFacility)
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
                                        ToastUtil.shortToast(PSHReEditUploadDoorNoActivity.this, "????????????");
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
                                        if (responseBody.getCode() == 200) {
                                            ToastUtil.shortToast(PSHReEditUploadDoorNoActivity.this, "????????????");
                                            EventBus.getDefault().post(new RefreshMyUploadList());
                                            //uploadedFacility.setPhotos(take_photo_item.getSelectedPhotos());
                                            if (uploadedFacility.deletedPhotoIds != null) {
                                                uploadedFacility.deletedPhotoIds.clear();
                                            }
                                            EventBus.getDefault().post(new UploadFacilitySuccessEvent(uploadedFacility));
                                            finish();
                                        } else {
                                            ToastUtil.shortToast(PSHReEditUploadDoorNoActivity.this, "???????????????????????????" + responseBody.getMessage());
                                        }
                                    }
                                });
                    }
                });

    }


    private boolean completeAddress() {
        if (mComponentAddressErrorView != null) {
            if (!mComponentAddressErrorView.ifAllowUpload()) {
                ToastUtil.iconShortToast(this, R.mipmap.ic_alert_yellow, mComponentAddressErrorView.getNotAllowUploadReason());
                return false;
            }
            FacilityAddressErrorModel facilityAddressErrorModel = mComponentAddressErrorView.getFacilityAddressErrorModel();
            uploadedFacility.setAddr(facilityAddressErrorModel.getCorrectAddress());
            uploadedFacility.setX(facilityAddressErrorModel.getCorrectX());
            uploadedFacility.setY(facilityAddressErrorModel.getCorrectY());
            uploadedFacility.setRoad(facilityAddressErrorModel.getRoad());
            return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (take_photo_item != null) {
            take_photo_item.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void startLocate() {

        baiduLocationManager = new BaiduLocationManager(this);
        baiduLocationManager.startLocate(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LocationInfo lastLocation = baiduLocationManager.getLastLocation();
                if (lastLocation != null) {
                    baiduLocationManager.stopLocate();
                    uploadedFacility.setUserLocationX(lastLocation.getLocation().getLongitude());
                    uploadedFacility.setUserLocationY(lastLocation.getLocation().getLatitude());
                    uploadedFacility.setUserAddr(lastLocation.getDetailAddress().getDetailAddress());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if (!LocationUtil.ifUnRegister()) {
            LocationUtil.unregister(PSHReEditUploadDoorNoActivity.this);
        }

        if (baiduLocationManager != null) {
            baiduLocationManager.stopLocate();
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (facilityProblemView != null){
            facilityProblemView = null;
        }

        if (facilityAttributeView != null) {
            facilityAttributeView.destroy();
            facilityAttributeView = null;
        }
    }


    @Override
    public void onBackPressed() {
        showAlertDialog();
        //super.onBackPressed();
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
     * @param sendFacilityOwnerShipUnit
     */
    @Subscribe
    public void onReceivedFacilityOwnerShipUnit(SendFacilityOwnerShipUnit sendFacilityOwnerShipUnit){
        if (sendFacilityOwnerShipUnit != null && !TextUtils.isEmpty(sendFacilityOwnerShipUnit.originOwnership)){
            cbOthers.setChecked(true);
            etOthers.setText(sendFacilityOwnerShipUnit.originOwnership);
        }
    }

}
