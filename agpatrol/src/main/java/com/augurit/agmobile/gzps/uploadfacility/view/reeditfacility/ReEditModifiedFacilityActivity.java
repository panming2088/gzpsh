package com.augurit.agmobile.gzps.uploadfacility.view.reeditfacility;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.widget.FlexRadioGroup;
import com.augurit.agmobile.gzps.common.widget.TakePhotoTableItem;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.uploadfacility.model.FacilityAddressErrorModel;
import com.augurit.agmobile.gzps.uploadfacility.model.FacilityInfoErrorModel;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.service.CorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.util.UploadLayerFieldKeyConstant;
import com.augurit.agmobile.gzps.uploadfacility.view.SendFacilityOwnerShipUnit;
import com.augurit.agmobile.gzps.uploadfacility.view.UploadFacilitySuccessEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.FacilityAddressErrorView;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.FacilityInfoErrorView;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.RefreshMyModificationListEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.facilityprobrem.FacilityProblemView;
import com.augurit.am.cmpt.login.model.User;
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
 * ??????????????????
 *
 * @author ????????? ???xuciluan
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.gzps.upload.view
 * @createTime ???????????? ???17/12/18
 * @modifyBy ????????? ???xuciluan
 * @modifyTime ???????????? ???17/12/18
 * @modifyMemo ???????????????
 */

//todo xcl bug???1. ??????????????????????????????????????????????????????????????????????????????????????????????????????x,y???????????????????????????????????????????????????????????????
public class ReEditModifiedFacilityActivity extends BaseActivity {


    private ModifiedFacility originalModifiedFacility;

    private TakePhotoTableItem take_photo_item;
    private View btn_upload_journal;
    private ProgressDialog progressDialog;
    private TextView tv_select_or_check_location;
    private TextItemTableItem textitem_component_type;
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
    private FacilityInfoErrorView componentInfoErrorView;
    private RadioButton lastSelectButton = null;
    private TextItemTableItem textitem_facility;
    private FlexRadioGroup rg_1;
    private CheckBox rb_component_not_exist;
    private LinearLayout ll_address_container;
    private LinearLayout ll_address;
    private View ll_textitem_description_container;
    private String originalCorrectType = null;

    private CountDownTimer countDownTimer; //?????????????????????????????????????????????
    private FlexboxLayout ll_problems_container;
    private FacilityProblemView facilityProblemView;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_correct_or_confirm_facility);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        originalModifiedFacility = getIntent().getParcelableExtra("data");
        if (originalModifiedFacility != null) {
            originalCorrectType = originalModifiedFacility.getCorrectType();
        }

        initView();

        initUser();

    }

    private void initUser() {
        User user = new LoginService(this, AMDatabase.getInstance()).getUser();
        String userName = user.getUserName();
        String userId = user.getId();

        originalModifiedFacility.setMarkPerson(userName);
        originalModifiedFacility.setMarkPersonId(userId);
        tableitem_current_user.setText(userName);
    }

    private void initView() {

        take_photo_item = (TakePhotoTableItem) findViewById(R.id.take_photo_item);
        take_photo_item.setPhotoExampleEnable(true);
        take_photo_item.setRequired(true);
        if (originalModifiedFacility != null) {
            take_photo_item.setSelectedPhotos(originalModifiedFacility.getPhotos());
        }


        ((TextView) findViewById(R.id.tv_title)).setText("????????????");

        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo ????????????????????????????????????????????????????????????????????????????????????????????????
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
        if (originalModifiedFacility != null) {
            textitem_description.setText(originalModifiedFacility.getDescription());
        }


        /**
         * ????????????
         */
        ll_problems_container = (FlexboxLayout) findViewById(R.id.ll_problems_container);
        final View ll_problems_container_parent = findViewById(R.id.ll_problems_container_parent);
        if (originalModifiedFacility != null) {
            facilityProblemView = new FacilityProblemView(this, ll_problems_container, originalModifiedFacility.getpCode(), originalModifiedFacility.getChildCode());
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
                        if (OTHERS.equals(errorType)) {
                            List<Photo> selectedPhotos = take_photo_item.getSelectedPhotos();
                            if (ListUtil.isEmpty(selectedPhotos) || selectedPhotos.size() < 3) {
                                ToastUtil.iconLongToast(getApplicationContext(), R.mipmap.ic_alert_yellow, "?????????????????????????????????????????????????????????");
                                return;
                            }
                        }

                        if ("???????????????".equals(originalCorrectType) && COMPONENT_NOT_EXIST.equals(errorType)) {
                            ToastUtil.iconLongToast(getApplicationContext(), R.mipmap.ic_alert_yellow, "?????????????????????????????????????????????????????????");
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
                            progressDialog = new ProgressDialog(ReEditModifiedFacilityActivity.this);
                            progressDialog.setMessage("????????????????????????");
                            progressDialog.setCancelable(false);
                        }
                        progressDialog.show();
                        upload();
                    }
                });

        /**
         * ????????????
         */
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        ll_address_container = (LinearLayout) findViewById(R.id.ll_address_container);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_textitem_description_container = findViewById(R.id.ll_textitem_description_container);

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

        if (originalModifiedFacility != null && cbInnerCity.getText().toString().equals(originalModifiedFacility.getCityVillage())){
            cbInnerCity.setChecked(true);
        }else if (originalModifiedFacility != null && cbDontKnowWhere.getText().toString().equals(originalModifiedFacility.getCityVillage())){
            cbDontKnowWhere.setChecked(true);
        }else if (originalModifiedFacility != null && !TextUtils.isEmpty(originalModifiedFacility.getCityVillage())){
            /**
             * ???????????????"?????????"/"?????????"??????????????????
             */
            cbOthers.setChecked(true);
            etOthers.setText(originalModifiedFacility.getCityVillage());
        }

        /**
         * ????????????
         */
        componentAddressErrorView = new FacilityAddressErrorView(ReEditModifiedFacilityActivity.this,
                originalModifiedFacility);
        ll_address_container.removeAllViews();
        componentAddressErrorView.addTo(ll_address_container);
        /**
         * ????????????
         */
        componentInfoErrorView = new FacilityInfoErrorView(ReEditModifiedFacilityActivity.this,
                originalModifiedFacility);
        ll_container.removeAllViews();
        componentInfoErrorView.addTo(ll_container);
        /**
         * ????????????
         */
        textitem_facility = (TextItemTableItem) findViewById(R.id.textitem_facility);
        textitem_facility.setReadOnly();
        if (originalModifiedFacility != null) {
            textitem_facility.setText(originalModifiedFacility.getLayerName());
        }
        /**
         * ???????????????
         */
        rb_component_not_exist = (CheckBox) findViewById(R.id.rb_component_not_exist);
        rb_component_not_exist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    errorType = COMPONENT_NOT_EXIST;
                    ll_container.setVisibility(View.GONE);
                    ll_address.setVisibility(View.GONE);
                    take_photo_item.setVisibility(View.GONE);
                    ll_textitem_description_container.setVisibility(View.GONE);
                    ll_problems_container_parent.setVisibility(View.GONE);
                } else {
                    errorType = OTHERS;
                    ll_container.setVisibility(View.VISIBLE);
                    ll_address.setVisibility(View.VISIBLE);
                    take_photo_item.setVisibility(View.VISIBLE);
                    ll_textitem_description_container.setVisibility(View.VISIBLE);
                    ll_problems_container_parent.setVisibility(View.VISIBLE);
                }
            }
        });

        if ("???????????????".equals(originalModifiedFacility.getCorrectType())) {
            rb_component_not_exist.setChecked(true);
        }
    }

    private void upload() {

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

        completeCorrectTypeAndReportType();

        originalModifiedFacility.setDescription(textitem_description.getText());
        originalModifiedFacility.setMarkPerson(tableitem_current_user.getText());

        completeTime();
        completePhotos();


        /**
         * ????????????
         */
        if (cbDontKnowWhere.isChecked() || cbInnerCity.isChecked()){
            originalModifiedFacility.setCityVillage(cityVillage);
        }else if (cbOthers.isChecked()){
            originalModifiedFacility.setCityVillage(etOthers.getText().toString());
        } else {
            originalModifiedFacility.setCityVillage("");
        }

        /**
         * ??????????????????
         */
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
                    ToastUtil.longToast(ReEditModifiedFacilityActivity.this, "?????????????????????");
                }
            }

            @Override
            public void onFinish() {

            }
        };

        if (countDownTimer != null) {
            countDownTimer.start();
        }

        CorrectFacilityService identificationService = new CorrectFacilityService(getApplicationContext());

        identificationService.upload(originalModifiedFacility)
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
                        ToastUtil.shortToast(ReEditModifiedFacilityActivity.this, "????????????");
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
                            ToastUtil.shortToast(ReEditModifiedFacilityActivity.this, "????????????");
                            EventBus.getDefault().post(new RefreshMyModificationListEvent());
                            if (originalModifiedFacility.deletedPhotoIds != null) {
                                originalModifiedFacility.deletedPhotoIds.clear();
                            }
                            EventBus.getDefault().post(new UploadFacilitySuccessEvent(originalModifiedFacility));
                            finish();
                        } else {

                            ToastUtil.shortToast(ReEditModifiedFacilityActivity.this, "???????????????????????????" + responseBody.getMessage());
                        }
                    }
                });

    }

    private void completeFacilityProblem() {
        if (OTHERS.equals(errorType)) {
            //????????????
            List<String> selectedLargeAndSmallItemList = facilityProblemView.getSelectedParentAndChildICodeList();
            //????????????
            if (selectedLargeAndSmallItemList != null && !TextUtils.isEmpty(selectedLargeAndSmallItemList.get(0))) {
                originalModifiedFacility.setpCode(selectedLargeAndSmallItemList.get(0));
            } else {
                originalModifiedFacility.setpCode("");
            }

            //????????????
            if (selectedLargeAndSmallItemList != null && !TextUtils.isEmpty(selectedLargeAndSmallItemList.get(1))) {
                originalModifiedFacility.setChildCode(selectedLargeAndSmallItemList.get(1));
            } else {
                originalModifiedFacility.setChildCode("");
            }
        } else {
            //???????????????
            originalModifiedFacility.setChildCode("");
            originalModifiedFacility.setpCode("");
        }
    }

    private void completePhotos() {

        /**
         * ?????????????????????????????????????????????????????????????????????????????????????????????
         */
        if (COMPONENT_NOT_EXIST.equals(errorType)) {

            List<Photo> deletedPhotos = originalModifiedFacility.getPhotos();
            List<String> deletedPhotoIds = new ArrayList<>();
            if (!ListUtil.isEmpty(deletedPhotos)) {
                for (Photo photo : deletedPhotos) {
                    if (photo.getId() != 0) {
                        deletedPhotoIds.add(String.valueOf(photo.getId()));
                    }
                }
                originalModifiedFacility.setDeletedPhotoIds(deletedPhotoIds);
            }

            originalModifiedFacility.setPhotos(new ArrayList<Photo>());

        } else {
            List<Photo> selectedPhotos = take_photo_item.getSelectedPhotos();

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
                originalModifiedFacility.setPhotos(newAddedPhotos);
            }

            List<Photo> deletedPhotos = take_photo_item.getDeletedPhotos();
            List<String> deletedPhotoIds = new ArrayList<>();
            if (!ListUtil.isEmpty(deletedPhotos)) {
                for (Photo photo : deletedPhotos) {
                    if (photo.getId() != 0) {
                        deletedPhotoIds.add(String.valueOf(photo.getId()));
                    }

                }
                originalModifiedFacility.setDeletedPhotoIds(deletedPhotoIds);
            }
        }

    }


    private void completeTime() {
        long currentTimeMillis = System.currentTimeMillis();
        originalModifiedFacility.setUpdateTime(currentTimeMillis);
        originalModifiedFacility.setMarkTime(currentTimeMillis);
    }

    private boolean completeAddress() {
        if (componentAddressErrorView != null && componentAddressErrorView.getFacilityAddressErrorModel().isHasModified()
                && OTHERS.equals(errorType)) {
            if (!componentAddressErrorView.ifAllowUpload()) {
                ToastUtil.iconShortToast(this, R.mipmap.ic_alert_yellow, componentAddressErrorView.getNotAllowUploadReason());
                return false;
            }
            FacilityAddressErrorModel facilityAddressErrorModel = componentAddressErrorView.getFacilityAddressErrorModel();
            originalModifiedFacility.setAddr(facilityAddressErrorModel.getCorrectAddress());
            originalModifiedFacility.setX(facilityAddressErrorModel.getCorrectX());
            originalModifiedFacility.setY(facilityAddressErrorModel.getCorrectY());
            originalModifiedFacility.setRoad(facilityAddressErrorModel.getRoad());
            return true;
        }
        return !errorType.equals(OTHERS);
    }


    private boolean completeAttributes() {
        if (componentInfoErrorView != null && OTHERS.equals(errorType)) {
            FacilityInfoErrorModel facilityInfoErrorModel = componentInfoErrorView.getFacilityInfoErrorModel();
            if (!facilityInfoErrorModel.isIfAllowUpload()) {
                ToastUtil.iconShortToast(getApplicationContext(), R.mipmap.ic_alert_yellow, facilityInfoErrorModel.getNotAllowUploadReason());
                return false;
            }
            originalModifiedFacility.setAttrOne(facilityInfoErrorModel.getAttrOne());
            originalModifiedFacility.setAttrTwo(facilityInfoErrorModel.getAttrTwo());
            originalModifiedFacility.setAttrThree(facilityInfoErrorModel.getAttrThree());
            originalModifiedFacility.setAttrFour(facilityInfoErrorModel.getAttrFour());
            originalModifiedFacility.setAttrFive(facilityInfoErrorModel.getAttrFive());
            return true;
        }
        return !errorType.equals(OTHERS);
    }

    private void completeCorrectTypeAndReportType() {


        if (OTHERS.equals(errorType)) {
            if (componentInfoErrorView.getFacilityInfoErrorModel().isHasModified() &&
                    componentAddressErrorView.getFacilityAddressErrorModel().isHasModified()) {

                originalModifiedFacility.setCorrectType("?????????????????????");
                originalModifiedFacility.setReportType(UploadLayerFieldKeyConstant.CORRECT_ERROR);

            } else if (componentInfoErrorView.getFacilityInfoErrorModel().isHasModified()
                    && !componentAddressErrorView.getFacilityAddressErrorModel().isHasModified()) {

                originalModifiedFacility.setCorrectType("????????????");
                originalModifiedFacility.setReportType(UploadLayerFieldKeyConstant.CORRECT_ERROR);

            } else if (!componentInfoErrorView.getFacilityInfoErrorModel().isHasModified() &&
                    componentAddressErrorView.getFacilityAddressErrorModel().isHasModified()) {
                originalModifiedFacility.setCorrectType("????????????");
                originalModifiedFacility.setReportType(UploadLayerFieldKeyConstant.CORRECT_ERROR);
            } else {
                originalModifiedFacility.setCorrectType("????????????");
                originalModifiedFacility.setReportType(UploadLayerFieldKeyConstant.CONFIRM);
            }

            List<Photo> selectedPhotos = take_photo_item.getSelectedPhotos();
            originalModifiedFacility.setPhotos(selectedPhotos);
            originalModifiedFacility.setThumbnailPhotos(take_photo_item.getThumbnailPhotos());
        } else {
            originalModifiedFacility.setCorrectType("???????????????");
            originalModifiedFacility.setReportType(UploadLayerFieldKeyConstant.CORRECT_ERROR);
        }
    }


    @Override
    public void onBackPressed() {
        showAlertDialog();
        //super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (take_photo_item != null) {
            take_photo_item.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (facilityProblemView != null){
            facilityProblemView = null;
        }

        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
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
