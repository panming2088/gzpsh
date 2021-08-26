package com.augurit.agmobile.gzpssb.fire;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.widget.SpinnerTableItem;
import com.augurit.agmobile.gzps.common.widget.TakePhotoTableItem;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.test.PhotoExampleActivity;
import com.augurit.agmobile.gzps.uploadfacility.model.FacilityAddressErrorModel;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.uploadfacility.view.SendFacilityOwnerShipUnit;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.FacilityAddressErrorView;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.RefreshMyUploadList;
import com.augurit.agmobile.gzpssb.fire.model.GroundfireBean;
import com.augurit.agmobile.gzpssb.fire.service.PSHUploadFireService;
import com.augurit.agmobile.gzpssb.uploadfacility.service.PSHUploadFacilityService;
import com.augurit.agmobile.gzpssb.uploadfacility.view.tranship.PSHFacilityAttributeView;
import com.augurit.agmobile.gzpssb.utils.RadioGroupUtil;
import com.augurit.agmobile.patrolcore.common.model.LocationInfo;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.agmobile.patrolcore.locate.BaiduLocationManager;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.loc.util.LocationUtil;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.service.LoginService;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.google.android.flexbox.FlexboxLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.tencent.bugly.crashreport.CrashReport;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
/**
 * 数据新增界面
 * @author 创建人 ：panm
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.modifiedIdentification
 * @createTime 创建时间 ：17/11/3
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/11/3
 * @modifyMemo 修改备注：
 */

public class PSHUploadNewFireActivity extends BaseActivity {
    private View btn_upload,btn_edit,btn_delete;
    private TextItemTableItem textitem_town;
    private TextItemTableItem textitem_village;
    private TextItemTableItem textitem_name;
    private TextItemTableItem tableitem_current_user;
//    private TextFieldTableItem textitem_description;
    private TextItemTableItem tableitem_current_time;
    private SpinnerTableItem areaSpinnerTableItem;
    private View upload_bottom_info;
    //位置信息控件组合
    private LinearLayout selectLocationContainer;
    private PSHUploadFireService pshUploadFireService ;
//    private LinearLayout ll_container;
    private PSHFacilityAttributeView facilityAttributeView;
    private DetailAddress detailAddress;
    private BaiduLocationManager baiduLocationManager;
    private Toast toast;
    private ProgressDialog progressDialog;
    private CountDownTimer countDownTimer; //用于上报时超过一定时间提示一次
    private FlexboxLayout ll_problems_container;
//    private FacilityProblemView facilityProblemView;
    private FacilityAddressErrorView mComponentAddressErrorView;
    private GroundfireBean finalGroundfireBean;
    private GroundfireBean originalGroundfireBean;

    private ArrayList<String> areas;
    private List<Photo> selectedPhotos;
    private String cityVillage = null;
    private TakePhotoTableItem take_photo_item;
    private RadioGroup sfwhRadioGroup,sflsRadioGroup,szwzRadioGroup;
    private HashMap<String,Object> spinnerData;
    private  boolean isFromMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reedit_upload_new_fire);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        /**
         * 在Activity跳转之前已经确保detailAddress和location不为空，所以下面的代码省去了这两个变量的非空判断
         */
        detailAddress = getIntent().getParcelableExtra("detailAddress");
        isFromMap = getIntent().getBooleanExtra("isFromMap",false);
        initDataAndParamete();
        initView();
        initUser();
        //获取用户当前位置
        startLocate();
    }

    private void initDataAndParamete() {
        pshUploadFireService = new PSHUploadFireService(getApplicationContext());
        originalGroundfireBean = (GroundfireBean) getIntent().getSerializableExtra("groundfireBean");
        finalGroundfireBean = new GroundfireBean();
        if(originalGroundfireBean!=null){
            finalGroundfireBean.setX(originalGroundfireBean.getX());
            finalGroundfireBean.setY(originalGroundfireBean.getY());
            finalGroundfireBean.setAddr(originalGroundfireBean.getAddr());
            finalGroundfireBean.setRoad(originalGroundfireBean.getRoad());
        }else {
            double x = getIntent().getDoubleExtra("x", 0);
            double y = getIntent().getDoubleExtra("y", 0);
            finalGroundfireBean.setX(x);
            finalGroundfireBean.setY(y);
            finalGroundfireBean.setAddr(detailAddress.getDetailAddress());
            finalGroundfireBean.setRoad(detailAddress.getStreet());
        }
    }

    private void initUser() {
        User user = new LoginService(this, AMDatabase.getInstance()).getUser();
        String userName = user.getUserName();
        String userId = user.getId();
        finalGroundfireBean.setMarkPerson(userName);
        finalGroundfireBean.setMarkPersonId(userId);
        tableitem_current_user.setText(userName);
    }


    private void initView() {
        areaSpinnerTableItem = (SpinnerTableItem) findViewById(R.id.spinneritem_area);
        textitem_town = (TextItemTableItem) findViewById(R.id.textitem_town);
        textitem_village= (TextItemTableItem) findViewById(R.id.textitem_village);
        textitem_name = (TextItemTableItem) findViewById(R.id.textitem_name);
        textitem_name.setText("地上式消防栓");
        textitem_name.setReadOnly();
        szwzRadioGroup = (RadioGroup) findViewById(R.id.rb_szwz);
        sflsRadioGroup = (RadioGroup) findViewById(R.id.rb_sfls);
        sfwhRadioGroup = (RadioGroup) findViewById(R.id.rb_sfwh);

        take_photo_item = (TakePhotoTableItem) findViewById(R.id.take_photo_item);
        take_photo_item.setPhotoExampleEnable(true);
        take_photo_item.setRequired(true);
        upload_bottom_info = findViewById(R.id.upload_bottom_info);
        //填表时间
        tableitem_current_time = (TextItemTableItem) findViewById(R.id.tableitem_current_time);
        //填表人
        tableitem_current_user = (TextItemTableItem) findViewById(R.id.tableitem_current_user);
        tableitem_current_user.setReadOnly();
         // 描述
//        textitem_description = (TextFieldTableItem) findViewById(R.id.textitem_description);


        take_photo_item.getmExample().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PSHUploadNewFireActivity.this, PhotoExampleActivity.class);
                startActivity(intent);
            }
        });
        ((TextView) findViewById(R.id.tv_title)).setText(null == originalGroundfireBean ? "地上式消防栓上报":"地上式消防栓详情");
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_edit.getVisibility() == View.GONE){
                    showAlertDialog();
                }else{
                    PSHUploadNewFireActivity.this.finish();
                }
            }
        });
        spinnerData = new HashMap<>();
//        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        spinnerData = getArea(this);
        if(spinnerData.size()==0){
            spinnerData.put("天河区","天河区");
            spinnerData.put("海珠区","海珠区");
            spinnerData.put("越秀区","越秀区");
            spinnerData.put("荔湾区","荔湾区");
            spinnerData.put("黄埔区","黄埔区");
        }
        areaSpinnerTableItem.setSpinnerData(spinnerData);
        if(null != originalGroundfireBean){
            setBottomVisibility(true);
            fillUI();
            take_photo_item.setPhotoNumShow(false, 3);
        }else{
            take_photo_item.setPhotoNumShow(true, 3);
            setBottomVisibility(false);
        }

        sfwhRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                finalGroundfireBean.setSfwh(checkedId == R.id.rb_sfwh_yes?"1":"0");
            }
        });
        szwzRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                finalGroundfireBean.setSzwz(checkedId == R.id.rb_szwz_yes?"1":"0");
            }
        });
        sflsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                finalGroundfireBean.setSfls(checkedId == R.id.rb_sfls_yes?"1":"0");
            }
        });

        /**
         * 部件位置
         */
        double x = getIntent().getDoubleExtra("x", 0);
        double y = getIntent().getDoubleExtra("y", 0);
        UploadedFacility temp = new UploadedFacility();
        if (detailAddress != null) {
            temp.setAddr(detailAddress.getDetailAddress());
            temp.setRoad(detailAddress.getStreet());
        }
        temp.setX(x);
        temp.setY(y);
        if(originalGroundfireBean!=null){
            temp.setX(originalGroundfireBean.getX());
            temp.setY(originalGroundfireBean.getY());
            temp.setAddr(originalGroundfireBean.getAddr());
            temp.setRoad(originalGroundfireBean.getRoad());
        }
        selectLocationContainer = (LinearLayout) findViewById(R.id.rl_select_location_container);
        mComponentAddressErrorView = new FacilityAddressErrorView(PSHUploadNewFireActivity.this,
                temp);
        selectLocationContainer.removeAllViews();
        mComponentAddressErrorView.addTo(selectLocationContainer);
        /**
         * 设施问题
         */
        ll_problems_container = (FlexboxLayout) findViewById(R.id.ll_problems_container);
        /**
         * 提交
         */
        btn_upload = findViewById(R.id.btn_upload);
        btn_delete= findViewById(R.id.btn_delete);
        btn_edit = findViewById(R.id.btn_edit);
        if(null != originalGroundfireBean){
            btn_edit.setVisibility(View.VISIBLE);
            btn_upload.setVisibility(View.GONE);
            btn_delete.setVisibility(View.GONE);
            setReadOnly(true);
        }else {
            btn_upload.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.GONE);
            btn_delete.setVisibility(View.GONE);
        }

        RxView.clicks(btn_edit)
                .throttleFirst(2, TimeUnit.SECONDS)   //2秒钟之内只取一个点击事件，防抖操作
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        btn_upload.setVisibility(View.VISIBLE);
                        btn_delete.setVisibility(View.VISIBLE);
                        btn_edit.setVisibility(View.GONE);
                        ((TextView) findViewById(R.id.tv_title)).setText("编辑地上式消防栓");
                        setReadOnly(false);
                    }
                });
        RxView.clicks(btn_delete)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        showDeleteDialog();
                    }
                });
        RxView.clicks(btn_upload)
                .throttleFirst(2, TimeUnit.SECONDS)   //2秒钟之内只取一个点击事件，防抖操作
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                       if(!checkParam()) return;

                        if (progressDialog == null) {
                            progressDialog = new ProgressDialog(PSHUploadNewFireActivity.this);
                            progressDialog.setMessage("正在提交，请等待");
                            progressDialog.setCancelable(false);
                        }

                        if (!progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                        //20秒提示一次
                        countDownTimer = new CountDownTimer(PSHUploadFacilityService.TIMEOUT * 1000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                long time = millisUntilFinished / 1000;
                                if (progressDialog != null) {
                                    progressDialog.setMessage("正在提交，请等待。   " + time + "s");
                                }
                                if (time % 20 == 0) {
                                    ToastUtil.longToast(PSHUploadNewFireActivity.this, "网络忙，请稍等");
                                }
                            }

                            @Override
                            public void onFinish() {

                            }
                        };

                        if (countDownTimer != null) {
                            countDownTimer.start();
                        }
                        Observable<ResponseBody> observable = null;
                        List<Photo> selectedPhotos = take_photo_item.getSelectedPhotos();
                        List<Photo> selectedThumbnail = take_photo_item.getThumbnailPhotos();
                        if(null != originalGroundfireBean){
                            finalGroundfireBean.setDeleteId(getDeletePhotoIds());
                            List<Photo> addPhotos = new ArrayList<>();
                            List<Photo> addThumbnails = new ArrayList<>();

                            for(Photo photo :selectedPhotos){
                                if(photo.getId() == 0){
                                    addPhotos.add(photo);
                                }
                            }
                            for(Photo photo :selectedThumbnail){
                                if(photo.getId() == 0){
                                    addThumbnails.add(photo);
                                }
                            }
                            finalGroundfireBean.setId(originalGroundfireBean.getId());
                            finalGroundfireBean.setObjectId(originalGroundfireBean.getObjectId());
                            finalGroundfireBean.setMarkId(originalGroundfireBean.getMarkId());
                            finalGroundfireBean.setPhotos8(addPhotos);
                            finalGroundfireBean.setThumbnailPhotos8(addThumbnails);
//                            finalGroundfireBean.setDescription(textitem_description.getText());
                            if(isFromMap){
                                finalGroundfireBean.setId(TextUtils.isEmpty(finalGroundfireBean.getMarkId())?0l:Long.valueOf(finalGroundfireBean.getMarkId()));
                            }
                            observable = pshUploadFireService.toUpdateFire(finalGroundfireBean);
                        }else {
                            finalGroundfireBean.setPhotos8(selectedPhotos);
                            finalGroundfireBean.setThumbnailPhotos8(selectedThumbnail);
                            observable =   pshUploadFireService.uploadGroundFire(finalGroundfireBean);
                        }
                        observable.subscribeOn(Schedulers.io())
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
                                        showToast("提交失败");
                                        CrashReport.postCatchedException(new Exception("新增上报失败，上报用户是：" +
                                                BaseInfoManager.getUserName(PSHUploadNewFireActivity.this) + "原因是：" + e.getLocalizedMessage()));
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
                                            showToast("提交成功");
                                            EventBus.getDefault().post(new RefreshMyUploadList());
                                            EventBus.getDefault().post(finalGroundfireBean);
                                            finish();
                                        } else {
                                            showToast("提交失败，原因是：" + responseBody.getMessage());
                                            CrashReport.postCatchedException(new Exception("新增上报失败，上报用户是：" +
                                                    BaseInfoManager.getUserName(PSHUploadNewFireActivity.this) + "原因是：" + responseBody.getMessage()));
                                        }
                                    }
                                });
                    }
                });
    }

    private void showDeleteDialog() {
        DialogUtil.MessageBox(this, "提醒", "是否确定要删除？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    private void delete() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(PSHUploadNewFireActivity.this);
            progressDialog.setMessage("正在删除，请等待");
            progressDialog.setCancelable(false);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        pshUploadFireService.toDeleteFire(isFromMap?originalGroundfireBean.getMarkId():originalGroundfireBean.getId()+"")
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
                        showToast("删除失败");
                        CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
                                BaseInfoManager.getUserName(PSHUploadNewFireActivity.this) + "原因是：" + e.getLocalizedMessage()));
                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (responseBody.getCode() == 200) {
                            showToast("删除成功");
                            EventBus.getDefault().post(new RefreshMyUploadList());
                            EventBus.getDefault().post(finalGroundfireBean);
                            finish();
                        } else {
                            showToast("删除失败，原因是：" + responseBody.getMessage());
                            CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
                                    BaseInfoManager.getUserName(PSHUploadNewFireActivity.this) + "原因是：" + responseBody.getMessage()));
                        }
                    }
                });
    }

    /**
     * 删除图片的id
     * @return
     */
    private String getDeletePhotoIds() {
        StringBuffer sb = new StringBuffer();
        List<Photo> deletedPhotos = take_photo_item.getDeletedPhotos();
        if(!ListUtil.isEmpty(deletedPhotos)) {
            for (Photo photo : deletedPhotos) {
                sb.append(photo.getId()).append(",");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    private void setReadOnly(boolean isReadOnly) {
        if(isReadOnly){
            take_photo_item.setReadOnly();
            take_photo_item.setRequired(false);
            take_photo_item.setPhotoExampleEnable(false);
            take_photo_item.setImageIsShow(false);
            areaSpinnerTableItem.setEditable(false);
           //位置信息
            ((TextItemTableItem)selectLocationContainer.findViewById(R.id.tableitem_correct_road)).setReadOnly();
            ((TextItemTableItem)selectLocationContainer.findViewById(R.id.tableitem_correct_location)).setReadOnly();
            selectLocationContainer.findViewById(R.id.ll_select_correct_location).setEnabled(false);

            textitem_town.setReadOnly();
            textitem_village.setReadOnly();
//            textitem_name.setReadOnly();
            RadioGroupUtil.disableRadioGroup(sfwhRadioGroup);
            RadioGroupUtil.disableRadioGroup(sflsRadioGroup);
            RadioGroupUtil.disableRadioGroup(szwzRadioGroup);
//            textitem_description.setReadOnly();
            tableitem_current_time.setReadOnly();
            setBottomVisibility(true);
        }else{
            take_photo_item.setEditable();
            take_photo_item.setRequired(true);
            take_photo_item.setPhotoExampleEnable(true);
            take_photo_item.setImageIsShow(take_photo_item.getSelectedPhotos().size()>=3?false:true);
            //位置信息
            ((TextItemTableItem)selectLocationContainer.findViewById(R.id.tableitem_correct_road)).setEditable(true);
            ((TextItemTableItem)selectLocationContainer.findViewById(R.id.tableitem_correct_location)).setEditable(true);
            selectLocationContainer.findViewById(R.id.ll_select_correct_location).setEnabled(true);

            areaSpinnerTableItem.setEditable(true);
            textitem_town.setEditable(true);
            textitem_village.setEditable(true);
//            textitem_name.setReadOnly();
//            textitem_name.setEditable(true);
            RadioGroupUtil.enableRadioGroup(sfwhRadioGroup);
            RadioGroupUtil.enableRadioGroup(sflsRadioGroup);
            RadioGroupUtil.enableRadioGroup(szwzRadioGroup);
//            textitem_description.setEnableEdit(true);
            tableitem_current_time.setEnabled(true);
            setBottomVisibility(false);
        }
    }

    private void setBottomVisibility(boolean isShow) {
        upload_bottom_info.setVisibility(isShow?View.VISIBLE:View.GONE);
    }



    private void fillUI() {
        for (int i=0;i<areas.size();i++){
            if(areas.get(i).equals(originalGroundfireBean.getArea())){
                areaSpinnerTableItem.selectItem(i);
            }
        }
        take_photo_item.setSelectedPhotos(originalGroundfireBean.getPhotos8());
        textitem_town.setText(originalGroundfireBean.getTown());
        textitem_village.setText(originalGroundfireBean.getVillage());
//        textitem_name.setText(originalGroundfireBean.getName());
//        textitem_description.setText(originalGroundfireBean.getDescription());
        if (originalGroundfireBean.getMarkTime() != 0) {
            tableitem_current_time.setText(TimeUtil.getStringTimeMdHmChines(new Date(Long.valueOf(originalGroundfireBean.getMarkTime()))));
        }

        ((RadioButton)sfwhRadioGroup.getChildAt("1".equals(originalGroundfireBean.getSfwh())?0:1)).setChecked(true);
        ((RadioButton)sflsRadioGroup.getChildAt("1".equals(originalGroundfireBean.getSfls())?0:1)).setChecked(true);
        ((RadioButton)szwzRadioGroup.getChildAt("1".equals(originalGroundfireBean.getSzwz())?0:1)).setChecked(true);
    }

    private boolean checkParam() {
        selectedPhotos = take_photo_item.getSelectedPhotos();
        if (ListUtil.isEmpty(selectedPhotos) || selectedPhotos.size() < 3) {
            showToast("请按“拍照须知”要求至少提供三张照片！");
            return false;
        }
        /**
         * 填充地址
         */
        boolean success = completeAddress();
        if (!success) {
            return false;
        }

        if(TextUtils.isEmpty(areaSpinnerTableItem.getText())){
            ToastUtil.shortToast(this,"请选择区域");
            return false;
        }
        if(TextUtils.isEmpty(textitem_town.getText())){
            ToastUtil.shortToast(this,"请填写镇街");
            return false;
        }
        if(TextUtils.isEmpty(textitem_village.getText())){
            ToastUtil.shortToast(this,"请填写社区居委");
            return false;
        }
        if(TextUtils.isEmpty(textitem_name.getText())){
            ToastUtil.shortToast(this,"请填写地上式消防栓名称");
            return false;
        }

        finalGroundfireBean.setVillage(textitem_village.getText());
        finalGroundfireBean.setArea(areaSpinnerTableItem.getText());
        finalGroundfireBean.setTown(textitem_town.getText());
        finalGroundfireBean.setName(textitem_name.getText());
        finalGroundfireBean.setSfwh(sfwhRadioGroup.getCheckedRadioButtonId() == R.id.rb_sfwh_yes?"1":"0");
        finalGroundfireBean.setSfls(sflsRadioGroup.getCheckedRadioButtonId() == R.id.rb_sfls_yes?"1":"0");
        finalGroundfireBean.setSzwz(szwzRadioGroup.getCheckedRadioButtonId() == R.id.rb_szwz_yes?"1":"0");
        finalGroundfireBean.setUpdateTime(System.currentTimeMillis()+"");
        if(originalGroundfireBean!=null){
            finalGroundfireBean.setId(originalGroundfireBean.getId());
            finalGroundfireBean.setMarkPerson(originalGroundfireBean.getMarkPerson());
            finalGroundfireBean.setMarkPersonId(originalGroundfireBean.getMarkPersonId());
            finalGroundfireBean.setMarkId(originalGroundfireBean.getMarkId());
        }else{
            finalGroundfireBean.setId(null);
        }
        return true;
    }


    private  HashMap<String,Object> getArea(Context context) {
        TableDBService     dbService = new TableDBService(context.getApplicationContext());
        List<DictionaryItem> areaItem = dbService.getDictionaryByTypecodeInDB("A206");//区域的数据字典

        if (!ListUtil.isEmpty(areaItem)) {
            for (DictionaryItem dict :areaItem) {
                if (!StringUtil.isEmpty(dict.getName())
                        && !StringUtil.isEmpty(dict.getValue())) {
                    spinnerData.put(dict.getName() , dict.getValue());
                }
            }
        }
        return sortHashMap(spinnerData);
    }

    public  HashMap<String,Object> sortHashMap(HashMap<String,Object> map){
        Set<Map.Entry<String,Object>> entey = map.entrySet();
        List<Map.Entry<String,Object>> list = new ArrayList<Map.Entry<String,Object>>(entey);
        Collections.sort(list, new Comparator<Map.Entry<String,Object>>() {
            @Override
            public int compare(Map.Entry<String,Object> o1, Map.Entry<String,Object> o2) {
                //按照age倒敘排列
                return o2.getKey().compareToIgnoreCase(o2.getKey());
            }
        });
        LinkedHashMap<String,Object> linkedHashMap = new LinkedHashMap<String,Object>();
        areas = new ArrayList<>();
        for(Map.Entry<String,Object> entry:list){
            linkedHashMap.put(entry.getKey(),entry.getValue());
            areas.add(entry.getKey());

        }
        return linkedHashMap;
}

    private boolean completeAddress() {
        if (mComponentAddressErrorView != null) {
            if (!mComponentAddressErrorView.ifAllowUpload()) {
                ToastUtil.iconShortToast(this, R.mipmap.ic_alert_yellow, mComponentAddressErrorView.getNotAllowUploadReason());
                return false;
            }
            FacilityAddressErrorModel facilityAddressErrorModel = mComponentAddressErrorView.getFacilityAddressErrorModel();
            finalGroundfireBean.setAddr(facilityAddressErrorModel.getCorrectAddress());
            finalGroundfireBean.setX(facilityAddressErrorModel.getCorrectX());
            finalGroundfireBean.setY(facilityAddressErrorModel.getCorrectY());
            finalGroundfireBean.setRoad(facilityAddressErrorModel.getRoad());
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
                    if (lastLocation.getLocation() != null) {
                        finalGroundfireBean.setUserLocationX(lastLocation.getLocation().getLongitude());
                        finalGroundfireBean.setUserLocationY(lastLocation.getLocation().getLatitude());
                    }
                    if (lastLocation.getDetailAddress() != null) {
                        finalGroundfireBean.setUserAddr(lastLocation.getDetailAddress().getDetailAddress());
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

    @Override
    public void onBackPressed() {
        if(btn_edit.getVisibility() == View.GONE){
            showAlertDialog();
        }else{
            PSHUploadNewFireActivity.this.finish();
        }
        //super.onBackPressed();
    }

    public void showToast(String toastString) {
        if (toast == null || toast.getView().findViewById(R.id.tv_toast_text) == null) {
            initToast(toastString);
        } else {
            ((TextView) toast.getView().findViewById(com.augurit.am.fw.R.id.tv_toast_text)).setText(toastString);
        }
        //显示提示
        toast.show();
    }

    public void initToast(String toastString) {
        //使用带图标的Toast提示显示
        toast = new Toast(getApplication());
        //设置Tosat的属性，如显示时间
        toast.setDuration(Toast.LENGTH_SHORT);
        View view1 = View.inflate(this, com.augurit.am.fw.R.layout.view_toast, null);
        ((TextView) view1.findViewById(com.augurit.am.fw.R.id.tv_toast_text)).setText(toastString);
        ((ImageView) view1.findViewById(com.augurit.am.fw.R.id.iv_icon)).setImageResource(R.mipmap.ic_alert_yellow);
        //将布局管理器添加进Toast
        toast.setView(view1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if (!LocationUtil.ifUnRegister()) {
            LocationUtil.unregister(PSHUploadNewFireActivity.this);
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (baiduLocationManager != null) {
            baiduLocationManager.stopLocate();
        }

//        if (facilityProblemView != null) {
//            facilityProblemView = null;
//        }

        if (facilityAttributeView != null) {
            facilityAttributeView.destroy();
            facilityAttributeView = null;
        }
    }

    public void showAlertDialog() {
        DialogUtil.MessageBox(this, "提示", "是否放弃本次编辑", new DialogInterface.OnClickListener() {
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
     * 接收权属单位
     *
     * @param sendFacilityOwnerShipUnit
     */
    @Subscribe
    public void onReceivedFacilityOwnerShipUnit(SendFacilityOwnerShipUnit sendFacilityOwnerShipUnit) {
        if (sendFacilityOwnerShipUnit != null && !TextUtils.isEmpty(sendFacilityOwnerShipUnit.originOwnership)) {
//            cbOthers.setChecked(true);
//            etOthers.setText(sendFacilityOwnerShipUnit.originOwnership);
        }
    }

}
