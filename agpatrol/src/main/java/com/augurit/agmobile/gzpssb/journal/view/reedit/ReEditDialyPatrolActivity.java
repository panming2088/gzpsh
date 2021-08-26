package com.augurit.agmobile.gzpssb.journal.view.reedit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.uploadevent.dao.DataResult;
import com.augurit.agmobile.gzps.uploadfacility.model.FacilityAddressErrorModel;
import com.augurit.agmobile.gzps.uploadfacility.model.FacilityAttributeModel;
import com.augurit.agmobile.gzps.uploadfacility.service.CorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.view.UploadFacilitySuccessEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.FacilityAddressErrorView;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.RefreshMyModificationListEvent;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.common.view.MultiTakePhotoTableItem;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournal;
import com.augurit.agmobile.gzpssb.journal.service.DialyPatrolService;
import com.augurit.agmobile.gzpssb.journal.view.DialyPatrolAttributeView;
import com.augurit.agmobile.gzpssb.journal.view.DialyPatrolProblemView;
import com.augurit.agmobile.gzpssb.journal.view.uploadnewdialy.DialyPatrolAddNewActivity;
import com.augurit.agmobile.gzpssb.uploadevent.service.PSHUploadEventService;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.service.LoginService;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.ResourceUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.google.android.flexbox.FlexboxLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 包名：com.augurit.agmobile.gzpssb.journal.view
 * 类描述：
 * 创建人：PC23
 * 创建时间：2018/12/19 10:26
 * 修改人：PC23
 * 修改时间：2018/12/19 10:26
 * 修改备注：
 */
public class ReEditDialyPatrolActivity extends AppCompatActivity {
    private TextView tv_title;
    private View ll_back;
    private TextItemTableItem tableitem_current_user;
    private User mUser;
    private Button btnSave;
    private Button btnSaveAndUploadProblem;
    private ProgressDialog progressDialog;
    private MultiTakePhotoTableItem mPhotoTableItem;
    private CountDownTimer countDownTimer;
    private PSHJournal mPSHJournal;
    private boolean isEvent = false;
    private SewerageItemBean.UnitListBean mUnitListBean;
    private TextFieldTableItem textitem_description;
    private LinearLayout ll_container;
    private DialyPatrolAttributeView componentInfoErrorView;
    private FlexboxLayout ll_problems_container;
    private DialyPatrolProblemView facilityProblemView;
    private LinearLayout ll_address_container;
    private LinearLayout ll_address;
    private DetailAddress mDetailAddress;
    private FacilityAddressErrorView componentAddressErrorView;
    private TextItemTableItem textitem_facility;
    private TextItemTableItem textitem_menpai;
    private PSHJournal originalModifiedFacility;
    private TextItemTableItem tableitem_current_time;
    private boolean isDrainageDepartment = false;
    private View ll_zgjy;
    private TextFieldTableItem problem_tab_zgjy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reedit_dialy_patrol);
        originalModifiedFacility = getIntent().getParcelableExtra("data");
        initView();
        initData();
        loadUserData();
    }

    private void initData() {
        mUser = new LoginService(this, AMDatabase.getInstance()).getUser();
        if (mUser != null) {
            tableitem_current_user.setText(mUser.getUserName());
            mPSHJournal.setLoginName(mUser.getLoginName());
        }

        if (MyApplication.doorBean != null) {
            textitem_menpai.setText(MyApplication.doorBean.getStree());
            mPSHJournal.setMph(MyApplication.doorBean.getStree());
            mPSHJournal.setsGuid(MyApplication.doorBean.getS_guid());
        }
        if (originalModifiedFacility != null) {
            tableitem_current_time.setText(TimeUtil.getStringTimeYMDMChines(new Date(originalModifiedFacility.getRecordTime())));
        }
    }

    private void initView() {
        mPSHJournal = new PSHJournal();
        if(originalModifiedFacility != null){
            mPSHJournal.setId(originalModifiedFacility.getId());
            mPSHJournal.setPshId(originalModifiedFacility.getPshId());
            mPSHJournal.setsGuid(originalModifiedFacility.getsGuid());
            mPSHJournal.setMph(originalModifiedFacility.getMph());
            mPSHJournal.setPshName(originalModifiedFacility.getPshName());
        }

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("再次编辑");
        ll_back = findViewById(R.id.ll_back);
        tableitem_current_user = (TextItemTableItem) findViewById(R.id.tableitem_current_user);
        tableitem_current_time = (TextItemTableItem) findViewById(R.id.tableitem_current_time);
        tableitem_current_time.setReadOnly();
        textitem_facility = (TextItemTableItem) findViewById(R.id.textitem_facility);
        textitem_facility.setReadOnly();
        textitem_menpai = (TextItemTableItem) findViewById(R.id.textitem_menpai);
        textitem_menpai.setReadOnly();
        ll_zgjy = findViewById(R.id.ll_zgjy);
        problem_tab_zgjy = (TextFieldTableItem) findViewById(R.id.problem_tab_zgjy);
//        problem_tab_zgjy.setRequireTag();

        textitem_description = (TextFieldTableItem) findViewById(R.id.textitem_description);
        if (originalModifiedFacility != null) {
            textitem_description.setText(originalModifiedFacility.getDescription());
            problem_tab_zgjy.setText(originalModifiedFacility.getZgjy());
            textitem_menpai.setText(originalModifiedFacility.getMph());
            textitem_facility.setText(originalModifiedFacility.getPshName());
        }

        mPhotoTableItem = (MultiTakePhotoTableItem) findViewById(R.id.take_photo_item);
        mPhotoTableItem.setRequired(true);
        if (originalModifiedFacility != null) {
            mPhotoTableItem.setSelectedPhotos(originalModifiedFacility.getPhotos());
        }
        btnSave = (Button) findViewById(R.id.btn_upload_journal);
        btnSaveAndUploadProblem = (Button) findViewById(R.id.btn_upload_event_journal);
        tableitem_current_user.setReadOnly();


        ll_address_container = (LinearLayout) findViewById(R.id.ll_address_container);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);

        /**
         * 设施地址
         */
        if (mDetailAddress == null) {
            mDetailAddress = new DetailAddress();
            mDetailAddress.setDetailAddress(originalModifiedFacility.getAddr());
            mDetailAddress.setStreet(originalModifiedFacility.getRoad());
            mDetailAddress.setX(originalModifiedFacility.getX());
            mDetailAddress.setY(originalModifiedFacility.getY());
        }
        componentAddressErrorView = new FacilityAddressErrorView(ReEditDialyPatrolActivity.this,
                mDetailAddress);

        ll_address_container.removeAllViews();
        componentAddressErrorView.addTo(ll_address_container);

        /**
         * 属性容器
         */
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        componentInfoErrorView = new DialyPatrolAttributeView(ReEditDialyPatrolActivity.this, originalModifiedFacility);
        ll_container.removeAllViews();
        componentInfoErrorView.addTo(ll_container);

        /**
         * 设施问题
         */
        ll_problems_container = (FlexboxLayout) findViewById(R.id.ll_problems_container);
        if (originalModifiedFacility != null) {
            facilityProblemView = new DialyPatrolProblemView(this, ll_problems_container, originalModifiedFacility.getPcode(), originalModifiedFacility.getChildCode());
        } else {
            facilityProblemView = new DialyPatrolProblemView(this, ll_problems_container);
        }

        final View ll_problems_container_parent = findViewById(R.id.ll_problems_container_parent);
        /**
         * 重置设施
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

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowBackDialog();
            }
        });
        RxView.clicks(btnSave)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {

                    @Override
                    public void call(Void aVoid) {
                        isEvent = false;
                        List<Photo> selectedPhotos = mPhotoTableItem.getSelectedPhotos();
                        if (ListUtil.isEmpty(selectedPhotos) || selectedPhotos.size()<=1) {
                            ToastUtil.shortToast(ReEditDialyPatrolActivity.this, "请按“拍照须知”要求至少提供两张照片！");
                            //ToastUtil.iconLongToast(getApplicationContext(), R.mipmap.ic_alert_yellow, "请按“拍照须知”要求至少提供三张照片！");;
                            return;
                        }
                        mPSHJournal.setPhotos(selectedPhotos);
                        mPSHJournal.setThumbnailPhotos(mPhotoTableItem.getThumbnailPhotos());
                        progressDialog = new ProgressDialog(ReEditDialyPatrolActivity.this);
                        progressDialog.setMessage("正在提交，请等待");
                        progressDialog.setCancelable(false);
//                        progressDialog.show();
                        upload();
                    }
                });
        RxView.clicks(btnSaveAndUploadProblem)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        isEvent = true;
                        List<Photo> selectedPhotos = mPhotoTableItem.getSelectedPhotos();
                        if (ListUtil.isEmpty(selectedPhotos) || selectedPhotos.size()<=1) {
                            ToastUtil.shortToast(ReEditDialyPatrolActivity.this, "请按“拍照须知”要求至少提供两张照片！");
                            //ToastUtil.iconLongToast(getApplicationContext(), R.mipmap.ic_alert_yellow, "请按“拍照须知”要求至少提供三张照片！");;
                            return;
                        }
                        mPSHJournal.setPhotos(selectedPhotos);
                        progressDialog = new ProgressDialog(ReEditDialyPatrolActivity.this);
                        progressDialog.setMessage("正在提交，请等待");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        upload();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        onShowBackDialog();
    }

    private void onShowBackDialog() {
        DialogUtil.MessageBox(ReEditDialyPatrolActivity.this, "提示", "是否确定放弃本次编辑？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                EventBus.getDefault().post(new BackPressEvent());
                finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void upload() {
        boolean b = completeAttributes();
        /**
         * 如果此时完善属性不成功，不继续向下执行
         */
        if (!b) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            return;
        }
        completeFacilityProblem();
        //20秒提示一次
        countDownTimer = new CountDownTimer(CorrectFacilityService.TIMEOUT * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long time = millisUntilFinished / 1000;
                if (progressDialog != null) {
                    progressDialog.setMessage("正在提交，请等待。   " + time + "s");
                }
                if (time % 20 == 0) {
                    ToastUtil.longToast(ReEditDialyPatrolActivity.this, "网络忙，请稍等");
                }
            }

            @Override
            public void onFinish() {

            }
        };

        boolean b1 = completeAddress();
        /**
         * 如果此时完善性地址不成功，不继续向下执行
         */
        if (!b1) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            return;
        }
        completePhotos();

        if (!StringUtil.isEmpty(textitem_description.getText())) {
            mPSHJournal.setDescription(textitem_description.getText().trim());
        }
        mPSHJournal.setZgjy("");
        if (isDrainageDepartment && !StringUtil.isEmpty(problem_tab_zgjy.getText().trim())) {
            mPSHJournal.setZgjy(problem_tab_zgjy.getText().trim());
        }else if(isDrainageDepartment  && (!ListUtil.isEmpty(facilityProblemView.getSelectedParentAndChildICodeList())
                && !StringUtil.isEmpty(facilityProblemView.getSelectedParentAndChildICodeList().get(0)))){
            ToastUtil.longToast(ReEditDialyPatrolActivity.this,"存在涉嫌违法问题，请填写整改建议");
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            return;
        }
        if (ListUtil.isEmpty(facilityProblemView.getSelectedParentAndChildICodeList())
                || StringUtil.isEmpty(facilityProblemView.getSelectedParentAndChildICodeList().get(0))) {
            uploadData();
        } else {
            MessageBox(ReEditDialyPatrolActivity.this, "提示", "     存在违法问题，如有对设施问题进行修改，请自行到相应的问题上报界面进行问题类型的修改。", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    uploadData();
                }
            }, null);
        }
    }

    private void uploadData() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        if (countDownTimer != null) {
            countDownTimer.start();
        }
        //数据纠错
        DialyPatrolService identificationService = new DialyPatrolService(getApplicationContext());
        identificationService.editJournal(mPSHJournal)
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
                        CrashReport.postCatchedException(new Exception("巡检上报失败，上报用户是：" +
                                BaseInfoManager.getUserName(ReEditDialyPatrolActivity.this) + "原因是：" + e.getLocalizedMessage()));
                        ToastUtil.longToast(ReEditDialyPatrolActivity.this, "提交失败");
                        //ToastUtil.shortToast(CorrectOrConfirimFacilityActivity.this, "提交失败");
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
                            ToastUtil.shortToast(ReEditDialyPatrolActivity.this, "提交成功");
                            EventBus.getDefault().post(new RefreshMyModificationListEvent());
                            if (originalModifiedFacility.deletedPhotoIds != null) {
                                originalModifiedFacility.deletedPhotoIds.clear();
                            }
                            EventBus.getDefault().post(new UploadFacilitySuccessEvent(originalModifiedFacility));
                            finish();
                        } else {
                            CrashReport.postCatchedException(new Exception("巡检上报失败，上报用户是：" +
                                    BaseInfoManager.getUserName(ReEditDialyPatrolActivity.this) + "原因是：" + responseBody.getMessage()));
                            ToastUtil.longToast(ReEditDialyPatrolActivity.this, "保存失败，原因是：" + responseBody.getMessage());
                            //ToastUtil.shortToast(CorrectOrConfirimFacilityActivity.this, "保存失败，原因是：" + responseBody.getMessage());
                        }
                    }
                });
    }

    private void completePhotos() {

        List<Photo> selectedPhotos = mPhotoTableItem.getSelectedPhotos();
        /**
         * 只有新增的图片id是0，从服务端获取到的图片id都是不为0的
         */
        if (!ListUtil.isEmpty(selectedPhotos)) {
            List<Photo> newAddedPhotos = new ArrayList<>();
            for (Photo photo : selectedPhotos) {
                if (photo.getId() == 0) {
                    newAddedPhotos.add(photo);
                }
            }
            mPSHJournal.setPhotos(newAddedPhotos);
        }

        List<Photo> deletedPhotos = mPhotoTableItem.getDeletedPhotos();
        List<String> deletedPhotoIds = new ArrayList<>();
        if (!ListUtil.isEmpty(deletedPhotos)) {
            for (Photo photo : deletedPhotos) {
                if (photo.getId() != 0) {
                    deletedPhotoIds.add(String.valueOf(photo.getId()));
                }

            }
            mPSHJournal.setDeletedPhotoIds(deletedPhotoIds);
            mPSHJournal.setAttachment(mPSHJournal.getDeletedPhotoIds());
        }
    }

    private boolean completeAddress() {
        /**
         * 如果不是设施不存在
         */
        if (componentAddressErrorView != null) {
            if (!componentAddressErrorView.ifAllowUpload()) {
                ToastUtil.iconShortToast(this, R.mipmap.ic_alert_yellow, componentAddressErrorView.getNotAllowUploadReason());
                return false;
            }
            FacilityAddressErrorModel facilityAddressErrorModel = componentAddressErrorView.getFacilityAddressErrorModel();
            // if (componentAddressErrorView.getFacilityAddressErrorModel().isHasModified()) {
            mPSHJournal.setAddr(facilityAddressErrorModel.getCorrectAddress());
            mPSHJournal.setRoad(facilityAddressErrorModel.getRoad());
            mPSHJournal.setX(facilityAddressErrorModel.getCorrectX());
            mPSHJournal.setY(facilityAddressErrorModel.getCorrectY());
            return true;
        }
        return false;

    }

    private boolean completeAttributes() {
        if (componentInfoErrorView != null) {
            FacilityAttributeModel facilityAttributeModel = componentInfoErrorView.getFacilityAttributeModel();
            if (!facilityAttributeModel.isIfAllowUpload()) {
                ToastUtil.iconShortToast(getApplicationContext(), R.mipmap.ic_alert_yellow, facilityAttributeModel.getNotAllowUploadReason());
                return false;
            }

            mPSHJournal.setAttrOne(facilityAttributeModel.getAttrOne());
            mPSHJournal.setAttrTwo(facilityAttributeModel.getAttrTwo());
            mPSHJournal.setAttrThree(facilityAttributeModel.getAttrThree());
            mPSHJournal.setAttrFour(facilityAttributeModel.getAttrFour());
            mPSHJournal.setAttrFive(facilityAttributeModel.getAttrFive());
            return true;
        }
        return true;
    }

    private void completeFacilityProblem() {

        //设施问题
        List<String> selectedLargeAndSmallItemList = facilityProblemView.getSelectedParentAndChildICodeList();
        //父类编码
        if (selectedLargeAndSmallItemList != null && !TextUtils.isEmpty(selectedLargeAndSmallItemList.get(0))) {
            mPSHJournal.setPcode(selectedLargeAndSmallItemList.get(0));
        } else {
            mPSHJournal.setPcode("");
        }

        //子类编码
        if (selectedLargeAndSmallItemList != null && !TextUtils.isEmpty(selectedLargeAndSmallItemList.get(1))) {
            mPSHJournal.setChildCode(selectedLargeAndSmallItemList.get(1));
        } else {
            mPSHJournal.setChildCode("");
        }
    }

    public void MessageBox(Context context, String title, String msg,
                           DialogInterface.OnClickListener positiveListener,
                           DialogInterface.OnClickListener negetiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(msg)) {
            builder.setMessage(msg);
        }
        if (positiveListener != null) {
            builder.setPositiveButton(
                    ResourceUtil.getStringId(context, "sure"), positiveListener);
        }
        if (negetiveListener != null) {
            builder.setNegativeButton(
                    ResourceUtil.getStringId(context, "cancel"),
                    negetiveListener);
        }
        builder.setCancelable(false);
        builder.create().setCanceledOnTouchOutside(false);
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mPhotoTableItem != null) {
            mPhotoTableItem.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
}
