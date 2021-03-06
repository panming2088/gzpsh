package com.augurit.agmobile.gzpssb.monitor.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.util.NumberUtil;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.RefreshJBJEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.MonitorService;
import com.augurit.agmobile.gzps.uploadfacility.service.UploadFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.RefreshMyUploadList;
import com.augurit.agmobile.gzpssb.common.view.MultiTakePhotoTableItem;
import com.augurit.agmobile.gzpssb.jbjpsdy.WellMonitorRefreshEvent;
import com.augurit.agmobile.gzpssb.monitor.model.WellMonitorInfo;
import com.augurit.agmobile.gzpssb.monitor.service.WellMonitorService;
import com.augurit.agmobile.gzpssb.monitor.view.time.ScreenInfo;
import com.augurit.agmobile.gzpssb.monitor.view.time.WheelMain;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.loc.util.LocationUtil;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.RadioGroupUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * ????????????????????????????????????
 *
 * @author ????????? ???xuciluan
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.gzps.upload.view
 * @createTime ???????????? ???17/12/18
 * @modifyBy ????????? ???xuciluan
 * @modifyTime ???????????? ???17/12/18
 * @modifyMemo ???????????????
 */

public class WellMonitorActivity extends BaseActivity {
    private MultiTakePhotoTableItem take_photo_item;
    private TextItemTableItem1 textItemGj;//??????
    private TextItemTableItem1 textItemAd;//????????????
    private TextItemTableItem1 textItemCod;//cod??????
    private TextItemTableItem1 textItemUser;//?????????
    private TextItemTableItem1 textItemType;//???????????????
    private TextItemTableItem1 textItemWsll;//????????????

    private RadioGroup rgWater, rgBlock;
    private RadioButton rbWaterYes, rbWaterNo;
    private RadioButton rbBlockYes, rbBlockNo;
    private Button btn_edit, btn_upload, btn_delete;
    private WellMonitorInfo orignMonitorInfo;
    private WellMonitorInfo finalMonitorInfo;
    private View view_water;//??????????????????
    private TextView tag_block, tag_water;
    private CountDownTimer countDownTimer;
    private ProgressDialog progressDialog;
    private WellMonitorService wellMonitorService;
    private String usid;//?????????usid
    private String jbjObjectId;//?????????objectid
    private String wellType;
    private TextView tittleTv;
    private Button btn_time;
    private boolean isFromMap;
    private Calendar cal;
    private List<Photo> selectedPhotos;
    boolean isRain;//??????????????????
    private WindowManager.LayoutParams params;
    private String type, X, Y, gj;
    WheelMain wheelMain;
    private boolean subtype;
    private TextView tvDsName;

    private String psdyId;
    private String psdyName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_monitor);
        orignMonitorInfo = getIntent().getParcelableExtra("WellMonitorInfo");
        initData();
        initView();
    }

    private void initData() {
        wellMonitorService = new WellMonitorService(this);
        orignMonitorInfo = (WellMonitorInfo) getIntent().getSerializableExtra("WellMonitorInfo");
        usid = getIntent().getStringExtra("usid");
        jbjObjectId = getIntent().getStringExtra("objectid");
        wellType = getIntent().getStringExtra("wellType");
        type = getIntent().getStringExtra("type");
        subtype = getIntent().getBooleanExtra("subtype",false);
        X = getIntent().getStringExtra("X");
        Y = getIntent().getStringExtra("Y");
        gj = getIntent().getStringExtra("gj");
        isFromMap = getIntent().getBooleanExtra("isFromMap", false);
        psdyId = getIntent().getStringExtra("psdyId");
        psdyName = getIntent().getStringExtra("psdyName");
    }

    private void initView() {
        if (finalMonitorInfo == null) {
            finalMonitorInfo = new WellMonitorInfo();
        }
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WellMonitorActivity.this.onBackPressed();
            }
        });
        tittleTv = ((TextView) findViewById(R.id.tv_title));
        tittleTv.setText(orignMonitorInfo == null ? "??????????????????" : "??????????????????");
        take_photo_item = (MultiTakePhotoTableItem) findViewById(R.id.take_photo_item);

        textItemGj = (TextItemTableItem1) findViewById(R.id.tableitem_diameter);
        textItemAd = (TextItemTableItem1) findViewById(R.id.tableitem_ad);
        textItemCod = (TextItemTableItem1) findViewById(R.id.tableitem_cod);
        textItemType = (TextItemTableItem1) findViewById(R.id.tableitem_type);
        tvDsName = (TextView) findViewById(R.id.tvDsName);
        textItemType.setTextViewName(subtype?"???????????????":"???????????????");
        textItemGj.setTextViewName(subtype?"??????????????????(mm)":"??????????????????(mm)");
        tvDsName.setText(subtype?"????????????????????????":"????????????????????????");
        textItemUser = (TextItemTableItem1) findViewById(R.id.tableitem_current_user);
        btn_time = (Button) findViewById(R.id.btn_time);
        textItemWsll = (TextItemTableItem1) findViewById(R.id.tableitem_wsll);
        tag_block = (TextView) findViewById(R.id.tag_block);
        tag_water = (TextView) findViewById(R.id.tag_water);

        setEditInputMode();
        rgWater = (RadioGroup) findViewById(R.id.rg_water);
        rgBlock = (RadioGroup) findViewById(R.id.rg_block);
        rbWaterYes = (RadioButton) findViewById(R.id.rb_water_yes);
        rbWaterNo = (RadioButton) findViewById(R.id.rb_water_no);
        rbBlockYes = (RadioButton) findViewById(R.id.rb_block_yes);
        rbBlockNo = (RadioButton) findViewById(R.id.rb_block_no);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        view_water = findViewById(R.id.ll_water);

        isRain = !TextUtils.isEmpty(type) && type.indexOf("??????") != -1;

        if (isRain) {
            textItemWsll.setVisibility(isRain ? View.GONE : View.VISIBLE);
            view_water.setVisibility(isRain ? View.VISIBLE : View.GONE);
        } else {
            view_water.setVisibility(View.GONE);
            textItemWsll.setVisibility(View.VISIBLE);
        }
        if (orignMonitorInfo != null) {
            fillUI(orignMonitorInfo);
            if (isFromMap) {
                setReadOnly(false);
                btn_edit.setVisibility(View.GONE);
                btn_upload.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.VISIBLE);
            } else {
                setReadOnly(true);
            }
        } else {
            if (!TextUtils.isEmpty(gj)) {
                textItemGj.setText(gj);
            }
            setReadOnly(false);
            Calendar calendar = Calendar.getInstance();
            btn_time.setText(calendar.get(Calendar.YEAR) + "-"
                    + ((calendar.get(Calendar.MONTH) + 1) > 9 ? (calendar.get(Calendar.MONTH) + 1) : "0" + (calendar.get(Calendar.MONTH) + 1)) + "-"
                    + (calendar.get(Calendar.DAY_OF_MONTH) > 9 ? calendar.get(Calendar.DAY_OF_MONTH) : "0" + calendar.get(Calendar.DAY_OF_MONTH)) + " "
                    + (calendar.get(Calendar.HOUR_OF_DAY) > 9 ? calendar.get(Calendar.HOUR_OF_DAY) : "0" + calendar.get(Calendar.HOUR_OF_DAY)) + ":"
                    + (calendar.get(Calendar.MINUTE) > 9 ? calendar.get(Calendar.MINUTE) : "0" + calendar.get(Calendar.MINUTE))
            );
        }
        textItemType.setText(type);
        textItemType.setReadOnly();

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });

        RxView.clicks(btn_upload)
                .throttleFirst(2, TimeUnit.SECONDS)   //2???????????????????????????????????????????????????
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (!checkParam()) return;
                        if (progressDialog == null) {
                            progressDialog = new ProgressDialog(WellMonitorActivity.this);
                            progressDialog.setMessage("????????????????????????");
                            progressDialog.setCancelable(false);
                        }
                        if (!progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                        //20???????????????
                        countDownTimer = new CountDownTimer(UploadFacilityService.TIMEOUT * 1000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                long time = millisUntilFinished / 1000;
                                if (progressDialog != null) {
                                    progressDialog.setMessage("???????????????????????????   " + time + "s");
                                }
                                if (time % 20 == 0) {
                                    ToastUtil.longToast(WellMonitorActivity.this, "?????????????????????");
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
                        if (null != orignMonitorInfo) {
                            finalMonitorInfo.setDeleteAttachment(getDeletePhotoIds());
                            List<Photo> addPhotos = new ArrayList<>();
                            List<Photo> addThumbnails = new ArrayList<>();

                            for (Photo photo : selectedPhotos) {
                                if (photo.getId() == 0) {
                                    addPhotos.add(photo);
                                }
                            }
                            for (Photo photo : selectedThumbnail) {
                                if (photo.getId() == 0) {
                                    addThumbnails.add(photo);
                                }
                            }
                            finalMonitorInfo.setPhotos(addPhotos);
                            finalMonitorInfo.setThumbnailPhotos(addThumbnails);
                            observable = wellMonitorService.addWellMonitor(finalMonitorInfo);
                        } else {
                            finalMonitorInfo.setPhotos(selectedPhotos);
                            finalMonitorInfo.setThumbnailPhotos(selectedThumbnail);
                            observable = wellMonitorService.addWellMonitor(finalMonitorInfo);
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
                                        ToastUtil.shortToast(WellMonitorActivity.this, "????????????");
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
                                            ToastUtil.shortToast(WellMonitorActivity.this, "????????????");
                                            EventBus.getDefault().post(new RefreshMyUploadList());
                                            EventBus.getDefault().post(new WellMonitorRefreshEvent(2));
                                            if(!TextUtils.isEmpty(psdyId) && !TextUtils.isEmpty(psdyName) && !TextUtils.isEmpty(wellType)) {
                                                savePsdyJg();
                                            } else {
                                                EventBus.getDefault().post(new RefreshJBJEvent(Long.parseLong(jbjObjectId)));
                                                finish();
                                            }
                                        } else {
                                            ToastUtil.shortToast(WellMonitorActivity.this, "???????????????????????????" + responseBody.getMessage());
                                        }
                                    }
                                });
                    }
                });

        RxView.clicks(btn_edit)
                .throttleFirst(2, TimeUnit.SECONDS)   //2???????????????????????????????????????????????????
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        tittleTv.setText("??????????????????");
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
    }

    /**
     * ??????????????????
     */
    private void savePsdyJg(){
        String loginName = new LoginRouter(getApplicationContext(), AMDatabase.getInstance()).getUser().getLoginName();
        new MonitorService(this.getApplicationContext()).addPsdyWtjc2(loginName, psdyId, psdyName, jbjObjectId, wellType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result2>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new RefreshJBJEvent(Long.parseLong(jbjObjectId)));
                        finish();
                    }

                    @Override
                    public void onNext(Result2 result2) {
                        EventBus.getDefault().post(new RefreshJBJEvent(Long.parseLong(jbjObjectId)));
                        finish();
                    }
                });
    }


    private void setEditInputMode() {
        //??????????????????????????????????????????
        textItemGj.getEt_right().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        textItemAd.getEt_right().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        textItemCod.getEt_right().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        textItemWsll.getEt_right().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        //???????????????????????????4????????????????????????
        textItemGj.getEt_right().addTextChangedListener(new DecimalInputTextWatcher(textItemGj.getEt_right(), 4, 2));
        textItemAd.getEt_right().addTextChangedListener(new DecimalInputTextWatcher(textItemAd.getEt_right(), 4, 2));
        textItemCod.getEt_right().addTextChangedListener(new DecimalInputTextWatcher(textItemCod.getEt_right(), 4, 2));
        textItemWsll.getEt_right().addTextChangedListener(new DecimalInputTextWatcher(textItemWsll.getEt_right(), 4, 2));


    }

    //????????????
    private void selectTime() {
        LayoutInflater inflater = LayoutInflater
                .from(WellMonitorActivity.this);
        final View timepickerview = inflater.inflate(
                R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(WellMonitorActivity.this);
        wheelMain = new WheelMain(timepickerview, true);
        wheelMain.screenheight = screenInfo.getHeight();
        Calendar calendar = Calendar.getInstance();
//        String time = btn_time.getText().toString();
//        if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
//            try {
//                calendar.setTime(dateFormat.parse(time));
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int h = calendar.getTime().getHours();
        int m = calendar.getTime().getMinutes();
        wheelMain.initDateTimePicker(year, month, day, h, m);
        new AlertDialog.Builder(WellMonitorActivity.this)
                .setTitle("????????????")
                .setView(timepickerview)
                .setPositiveButton("??????",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                btn_time.setText(wheelMain.getTime());
                            }
                        })
                .setNegativeButton("??????",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();
    }

    //????????????????????????
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    private void showDeleteDialog() {
        DialogUtil.MessageBox(this, "??????", "????????????????????????", new DialogInterface.OnClickListener() {
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
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("????????????????????????");
            progressDialog.setCancelable(false);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        wellMonitorService.deleteWellMonitor(orignMonitorInfo.getId())
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
                        ToastUtil.shortToast(WellMonitorActivity.this, "????????????");
                        CrashReport.postCatchedException(new Exception("?????????????????????????????????" +
                                BaseInfoManager.getUserName(WellMonitorActivity.this) + "????????????" + e.getLocalizedMessage()));
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (responseBody.getCode() == 200) {
                            ToastUtil.shortToast(WellMonitorActivity.this, "????????????");
                            EventBus.getDefault().post(new RefreshMyUploadList());
                            EventBus.getDefault().post(new WellMonitorRefreshEvent(1));
                            finish();
                        } else {
                            ToastUtil.shortToast(WellMonitorActivity.this, responseBody.getMessage());
                            CrashReport.postCatchedException(new Exception("?????????????????????????????????" +
                                    BaseInfoManager.getUserName(WellMonitorActivity.this) + "????????????" + responseBody.getMessage()));
                        }
                    }
                });
    }

    /**
     * ???????????????id
     *
     * @return
     */
    private String getDeletePhotoIds() {
        StringBuffer sb = new StringBuffer();
        List<Photo> deletedPhotos = take_photo_item.getDeletedPhotos();
        if (!ListUtil.isEmpty(deletedPhotos)) {
            for (Photo photo : deletedPhotos) {
                sb.append(photo.getId()).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private boolean checkParam() {
        selectedPhotos = take_photo_item.getSelectedPhotos();
        if (ListUtil.isEmpty(selectedPhotos) || selectedPhotos.size() < 1) {
            ToastUtil.shortToast(this, "?????????????????????????????????????????????????????????");
            return false;
        }

        if (TextUtils.isEmpty(textItemGj.getText())) {
            ToastUtil.shortToast(this, "???????????????");
            return false;
        }
        if (!NumberUtil.isNumber(textItemGj.getText())) {
            ToastUtil.shortToast(this, "?????????????????????");
            return false;
        }
        if (TextUtils.isEmpty(textItemAd.getText())) {
            ToastUtil.shortToast(this, "?????????????????????");
            return false;
        }
        if (!NumberUtil.isNumber(textItemAd.getText())) {
            ToastUtil.shortToast(this, "???????????????????????????");
            return false;
        }

        if (!TextUtils.isEmpty(textItemCod.getText()) && !NumberUtil.isNumber(textItemCod.getText())) {
            ToastUtil.shortToast(this, "Cod?????????????????????");
            return false;
        }
        if (!isRain) {
            if (TextUtils.isEmpty(textItemWsll.getText())) {
                ToastUtil.shortToast(this, "????????????????????????");
                return false;
            }
            if (!NumberUtil.isNumber(textItemWsll.getText())) {
                ToastUtil.shortToast(this, "??????????????????????????????");
                return false;
            }
        }
        finalMonitorInfo.setAd(textItemAd.getText());
        if (isRain) {
            finalMonitorInfo.setQtsfys(rgWater.getCheckedRadioButtonId() == R.id.rb_water_yes ? "1" : "0");
        } else {
            finalMonitorInfo.setRwsll(textItemWsll.getText().toString());
        }
        finalMonitorInfo.setSfds(rgBlock.getCheckedRadioButtonId() == R.id.rb_block_yes ? "1" : "0");
        finalMonitorInfo.setGdgj(textItemGj.getText());
        finalMonitorInfo.setAd(textItemAd.getText());
        if (!TextUtils.isEmpty(textItemCod.getText())) {
            finalMonitorInfo.setCod(textItemCod.getText().toString());
        }

        if (orignMonitorInfo != null) {
            finalMonitorInfo.setUsid(orignMonitorInfo.getUsid());
            finalMonitorInfo.setJbjObjectId(orignMonitorInfo.getJbjObjectId());
            finalMonitorInfo.setId(orignMonitorInfo.getId());
        } else {
            finalMonitorInfo.setUsid(usid);
            finalMonitorInfo.setJbjObjectId(jbjObjectId);
        }
        try {
            finalMonitorInfo.setJcsj(Long.valueOf(dateToStamp(btn_time.getText().toString())));
        } catch (ParseException e) {
            e.printStackTrace();
            finalMonitorInfo.setJcsj(System.currentTimeMillis());
        }
        finalMonitorInfo.setJbjType(type);
        if (!StringUtil.isEmpty(X) && !"null".equals(X)) {
            finalMonitorInfo.setJbjX(Double.valueOf(X));
        }
        if (!StringUtil.isEmpty(Y) && !"null".equals(Y)) {
            finalMonitorInfo.setJbjY(Double.valueOf(Y));
        }
        return true;
    }


    //????????????
    private void fillUI(WellMonitorInfo wellMonitorInfo) {
        take_photo_item.setSelectedPhotos(wellMonitorInfo.getPhotos());
        ((RadioButton) rgWater.getChildAt("1".equals(wellMonitorInfo.getQtsfys()) ? 0 : 1)).setChecked(true);
        ((RadioButton) rgBlock.getChildAt("1".equals(wellMonitorInfo.getSfds()) ? 0 : 1)).setChecked(true);
        textItemGj.setText(wellMonitorInfo.getGdgj() + "");
        textItemAd.setText(wellMonitorInfo.getAd() + "");
        if (!TextUtils.isEmpty(wellMonitorInfo.getCod()) && !TextUtils.isEmpty(wellMonitorInfo.getCod().trim())) {
            textItemCod.setText(wellMonitorInfo.getCod());
        }

        textItemWsll.setText(wellMonitorInfo.getRwsll() + "");
        textItemType.setText(wellMonitorInfo.getJbjType());
        if (wellMonitorInfo.getJcsj() != null && wellMonitorInfo.getJcsj() > 0) {
            btn_time.setText(stampToDate(wellMonitorInfo.getJcsj() + ""));
        }
        if (wellMonitorInfo.getJcsj() != null && wellMonitorInfo.getJcsj() != 0) {
            textItemUser.setText(TimeUtil.getStringTimeMdHmChines(new Date(Long.valueOf(wellMonitorInfo.getJcsj()))));
        }
    }

    //???????????????????????????
    private void setReadOnly(boolean isReadOnly) {
        if (isReadOnly) {

            take_photo_item.setReadOnly();
            take_photo_item.setRequired(false);
            take_photo_item.setTitle("???????????????");
            take_photo_item.setImageIsShow(false);
            take_photo_item.setPhotoNumShow(false, 9);
            textItemGj.setReadOnly();
            textItemAd.setReadOnly();
            textItemCod.setReadOnly();
            tag_block.setVisibility(View.GONE);
            tag_water.setVisibility(View.GONE);

            textItemUser.setReadOnly();
            btn_time.setEnabled(false);
            textItemWsll.setReadOnly();
            RadioGroupUtil.disableRadioGroup(rgWater);
            RadioGroupUtil.disableRadioGroup(rgBlock);

            btn_edit.setVisibility(View.VISIBLE);
            btn_upload.setVisibility(View.GONE);
            btn_delete.setVisibility(View.GONE);
        } else {
            take_photo_item.setEditable();
            take_photo_item.setRequired(true);
            take_photo_item.setImageIsShow(take_photo_item.getSelectedPhotos().size() >= 9 ? false : true);
            take_photo_item.setTitle("????????????(??????1???)");
            take_photo_item.setPhotoNumShow(true, 9);

            textItemGj.setEditable(true);
            textItemAd.setEditable(true);

            tag_block.setVisibility(View.VISIBLE);
            tag_water.setVisibility(View.VISIBLE);
            textItemGj.setRequireTag();
            textItemAd.setRequireTag();
            textItemWsll.setRequireTag();

            textItemCod.setEditable(true);
            textItemUser.setEditable(true);
            btn_time.setEnabled(true);
            textItemWsll.setEditable(true);
            RadioGroupUtil.enableRadioGroup(rgWater);
            RadioGroupUtil.enableRadioGroup(rgBlock);

            btn_upload.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(null == orignMonitorInfo ? View.GONE : View.VISIBLE);
            btn_edit.setVisibility(View.GONE);
        }
    }

    //????????????????????????
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (!LocationUtil.ifUnRegister()) {
            LocationUtil.unregister(WellMonitorActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        if (btn_edit.getVisibility() == View.GONE) {
            showAlertDialog();
        } else {
            this.finish();
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
}
