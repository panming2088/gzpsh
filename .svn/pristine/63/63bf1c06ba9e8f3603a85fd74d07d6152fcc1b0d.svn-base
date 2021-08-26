package com.augurit.agmobile.gzpssb.pshdoorno.add.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.bean.DoorNOBean;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.DoorNoRespone;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.UploadDoorNoBean;
import com.augurit.agmobile.gzpssb.pshdoorno.add.presenter.AddDoorNoPresent;
import com.augurit.agmobile.gzpssb.pshdoorno.base.BaseActivty;
import com.augurit.agmobile.gzpssb.pshdoorno.base.BasePersenter;
import com.augurit.agmobile.gzpssb.pshdoorno.base.IView;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.mode.UploadDoorNoDetailBean;
import com.augurit.agmobile.gzpssb.utils.SetMaxLengthUtils;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;

/**
 * 数据新增界面
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.modifiedIdentification
 * @createTime 创建时间 ：17/11/3
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/11/3
 * @modifyMemo 修改备注：
 */

public class PSHUploadNewDoorNoActivity extends BaseActivty implements IView, View.OnClickListener {
    private DetailAddress mDetailAddress;
    private TextItemTableItem mItemOne;
    private TextItemTableItem mItemTwo;
    private TextItemTableItem mItemThree;
    private TextItemTableItem mItemFour;
    private TextItemTableItem mItemFive;

    private TextItemTableItem mItemSix;
    private TextItemTableItem mItemSeven;
    private TextFieldTableItem mTextFieldTableItem;
    private Button btnUpload;
    private ProgressDialog progressDialog;
    private double x;
    private double y;
    private Boolean isEditDoorNo;
    private UploadDoorNoDetailBean mCurrentUploadedDoorNo;
    private UploadDoorNoBean mUploadDoorNoBean;
    private boolean isfromQuryAddressMapFragmnet;

    @Override
    public void initData() {

        mCurrentUploadedDoorNo = (UploadDoorNoDetailBean) getIntent().getSerializableExtra("data");
        isEditDoorNo = mCurrentUploadedDoorNo != null;
        x = getIntent().getDoubleExtra("x", 0);
        y = getIntent().getDoubleExtra("y", 0);
        isfromQuryAddressMapFragmnet = getIntent().getBooleanExtra("isfromQuryAddressMapFragmnet", false);
        mDetailAddress = getIntent().getParcelableExtra("detailAddress");
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(PSHUploadNewDoorNoActivity.this);
            progressDialog.setMessage("正在提交，请等待");
            progressDialog.setCancelable(false);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void disProgress(Object o) {
        if (progressDialog != null) {
            DoorNoRespone rb = null;
            if (o instanceof DoorNoRespone) {
                rb = (DoorNoRespone) o;
            }
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                if (null != rb) {
                    if (rb.getCode() == 200) {

                        if (mDetailAddress != null && isfromQuryAddressMapFragmnet) {
                            ToastUtil.longToast(PSHUploadNewDoorNoActivity.this, "新增门牌成功，请进行排水户录入");
                            /**
                             * 新增门牌成功后，直接跳到排水户上报界面
                             */
                            Intent intent = new Intent(PSHUploadNewDoorNoActivity.this, SewerageTableActivity.class);
                            SewerageItemBean sewerageItemBean = new SewerageItemBean();
//                            sewerageItemBean.setAddress(mUploadDoorNoBean.get);
                            sewerageItemBean.setJlx(mUploadDoorNoBean.getSsjlx());
                            sewerageItemBean.setJwh(mUploadDoorNoBean.getSssqcjwh());
                            sewerageItemBean.setMpwzhm(mUploadDoorNoBean.getMpdzmc());
                            sewerageItemBean.setXzq(mUploadDoorNoBean.getSsxzqh());
                            sewerageItemBean.setXzj(mUploadDoorNoBean.getSsxzjd());
                            sewerageItemBean.setSmallAddress(mUploadDoorNoBean.getSsxzqh() + mUploadDoorNoBean.getMpdzmc());
                            MyApplication.SEWERAGEITEMBEAN = sewerageItemBean;

                            DoorNOBean doorNOBean = new DoorNOBean();
                            doorNOBean.setISTATUE("1");
                            doorNOBean.setIsExist("1");
                            doorNOBean.setS_guid(rb.getS_guid());
                            doorNOBean.setDzdm(rb.getDzdm());
                            doorNOBean.setX(mUploadDoorNoBean.getZxjd());
                            doorNOBean.setY(mUploadDoorNoBean.getZxwd());
                            MyApplication.doorBean = doorNOBean;
                            MyApplication.DZDM = rb.getDzdm();
                            MyApplication.GUID = rb.getS_guid();

                            //重置一些参数
                            MyApplication.refreshListType = 1;
                            MyApplication.buildId = "";
                            MyApplication.ID = "";
                            MyApplication.SEWERAGEROOMCLICKITEMBEAN = null;
                            MyApplication.X = x;
                            MyApplication.Y = y;
                            intent.putExtra("isExist", "0");
                            intent.putExtra("HouseIdFlag", "0");
                            intent.putExtra("HouseId", MyApplication.buildId);
                            intent.putExtra("UnitId", "");
                            intent.putExtra("isExist", "1");
                            intent.putExtra("isAddNewDoorNo", true);
                            intent.putExtra("isfromQuryAddressMapFragmnet", isfromQuryAddressMapFragmnet);
                            startActivity(intent);
                            PSHUploadNewDoorNoActivity.this.finish();

                        } else {
                            ToastUtil.shortToast(PSHUploadNewDoorNoActivity.this, "提交成功");
                            Intent intent = new Intent();
                            intent.putExtra("doorBean", mCurrentUploadedDoorNo);
                            setResult(124, intent);
                            PSHUploadNewDoorNoActivity.this.finish();
                        }

                    } else {
                        ToastUtil.shortToast(PSHUploadNewDoorNoActivity.this, "提交失败");
                    }
                } else {
                    ToastUtil.shortToast(PSHUploadNewDoorNoActivity.this, rb != null && !TextUtils.isEmpty(rb.getMessage()) ? rb.getMessage() : "提交失败");
                }
            }
        }
    }


    @Override
    public void initView() {
        setContentView(R.layout.activity_upload_new_doorno);
        ((TextView) findViewById(R.id.tv_title)).setText(isEditDoorNo ? "编辑门牌" : "新增门牌号");
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        mItemOne = (TextItemTableItem) findViewById(R.id.text_item_one);
        mItemTwo = (TextItemTableItem) findViewById(R.id.text_item_two);
        mItemThree = (TextItemTableItem) findViewById(R.id.text_item_three);
        mItemFour = (TextItemTableItem) findViewById(R.id.text_item_four);
        mItemFive = (TextItemTableItem) findViewById(R.id.text_item_five);
        mItemSix = (TextItemTableItem) findViewById(R.id.text_item_six);
        mItemSeven = (TextItemTableItem) findViewById(R.id.text_item_seven);
        btnUpload = (Button) findViewById(R.id.btn_upload_door_no);

        mItemOne.setTextViewName(Html.fromHtml("行政区<font color='#FF0000'> *</font>"));
        mItemTwo.setTextViewName(Html.fromHtml("镇街<font color='#FF0000'> *</font>"));
        mItemThree.setTextViewName(Html.fromHtml("村(居)委会<font color='#FF0000'> *</font>"));
        mItemFour.setTextViewName(Html.fromHtml("街路巷<font color='#FF0000'> *</font>"));
        mItemFive.setTextViewName(Html.fromHtml("门牌号<font color='#FF0000'> *</font>"));
        SetMaxLengthUtils.setMaxLength(mItemTwo.getEt_right(), SetMaxLengthUtils.MAX_LENGTH_15);
        SetMaxLengthUtils.setMaxLength(mItemOne.getEt_right(), SetMaxLengthUtils.MAX_LENGTH_15);
        SetMaxLengthUtils.setMaxLength(mItemThree.getEt_right(), SetMaxLengthUtils.MAX_LENGTH_15);
        SetMaxLengthUtils.setMaxLength(mItemFour.getEt_right(), SetMaxLengthUtils.MAX_LENGTH_15);
        SetMaxLengthUtils.setMaxLength(mItemFive.getEt_right(), SetMaxLengthUtils.MAX_LENGTH_15);


        mItemSix.setVisibility(View.GONE);
        mItemSeven.setVisibility(View.GONE);

        btnUpload.setOnClickListener(this);
        mTextFieldTableItem = (TextFieldTableItem) findViewById(R.id.tf_table_upload_indicate);
        mTextFieldTableItem.setMaxLength(SetMaxLengthUtils.MAX_LENGTH_200);
//        double x = getIntent().getDoubleExtra("x", 0);
//        double y = getIntent().getDoubleExtra("y", 0);
        if (null != mDetailAddress) {
            mItemOne.setText(mDetailAddress.getDistrict());
            mItemFour.setText(mDetailAddress.getStreet());
            mItemFive.setText(mDetailAddress.getStreet_number());
        }
        if (isEditDoorNo) {
            mItemOne.setText(mCurrentUploadedDoorNo.getSsxzqh());
            mItemTwo.setText(mCurrentUploadedDoorNo.getSsxzjd());
            mItemThree.setText(mCurrentUploadedDoorNo.getSssqcjwh());
            mItemFour.setText(mCurrentUploadedDoorNo.getSsjlx());
            mItemFive.setText(mCurrentUploadedDoorNo.getMpdzmc());
            mItemSix.setText(mCurrentUploadedDoorNo.getMarkPerson());
            mTextFieldTableItem.setText(mCurrentUploadedDoorNo.getDescription());
//            mItemSeven.setText(mCurrentUploadedDoorNo.getMarkPerson());

        }
    }


    private void sunmitDoorNo() {
        mUploadDoorNoBean = new UploadDoorNoBean();

        mUploadDoorNoBean.setSsxzqh(mItemOne.getText().toString().replaceAll("\\s*|\t|\r|\n",""));
        mUploadDoorNoBean.setSsxzjd(mItemTwo.getText().toString().replaceAll("\\s*|\t|\r|\n",""));
        mUploadDoorNoBean.setSssqcjwh(mItemThree.getText().toString().replaceAll("\\s*|\t|\r|\n",""));
        mUploadDoorNoBean.setSsjlx(mItemFour.getText().toString().replaceAll("\\s*|\t|\r|\n",""));
        mUploadDoorNoBean.setMpdzmc(mItemFive.getText().toString().replaceAll("\\s*|\t|\r|\n",""));
        mUploadDoorNoBean.setDescription(mTextFieldTableItem.getText().toString());


        mUploadDoorNoBean.setZxjd(isEditDoorNo ? mCurrentUploadedDoorNo.getZxjd() : x);
        mUploadDoorNoBean.setZxwd(isEditDoorNo ? mCurrentUploadedDoorNo.getZxwd() : y);
        mUploadDoorNoBean.setsGuid(isEditDoorNo ? mCurrentUploadedDoorNo.getsGuid() : "");
        mUploadDoorNoBean.setObjectId(isEditDoorNo ? mCurrentUploadedDoorNo.getObjectId() : "");

        if (mCurrentUploadedDoorNo != null) {
            mCurrentUploadedDoorNo.setSsxzqh(mItemOne.getText().toString());
            mCurrentUploadedDoorNo.setSsxzjd(mItemTwo.getText().toString());
            mCurrentUploadedDoorNo.setSssqcjwh(mItemThree.getText().toString());
            mCurrentUploadedDoorNo.setSsjlx(mItemFour.getText().toString());
            mCurrentUploadedDoorNo.setMpdzmc(mItemFive.getText().toString());
            mCurrentUploadedDoorNo.setDescription(mTextFieldTableItem.getText().toString());
//            mCurrentUploadedDoorNo.setZxjd(x);
//            mCurrentUploadedDoorNo.setZxwd(y);
        }


        //二次编辑后提交传SGID，区分新增门牌
        mPresent.submitData(mUploadDoorNoBean);
    }

    @Override
    public boolean checkParams() {
        if (TextUtils.isEmpty(mItemOne.getText())|| TextUtils.isEmpty(mItemOne.getText().replaceAll("\\s*|\t|\r|\n",""))) {
            ToastUtil.shortToast(this, "行政区不能为空");
            return false;
        }

        if (TextUtils.isEmpty(mItemTwo.getText())|| TextUtils.isEmpty(mItemTwo.getText().replaceAll("\\s*|\t|\r|\n",""))) {
            ToastUtil.shortToast(this, "镇街不能为空");
            return false;
        }

        if (TextUtils.isEmpty(mItemThree.getText())|| TextUtils.isEmpty(mItemThree.getText().replaceAll("\\s*|\t|\r|\n",""))) {
            ToastUtil.shortToast(this, "村(居)委会不能为空");
            return false;
        }

        if (TextUtils.isEmpty(mItemFour.getText())|| TextUtils.isEmpty(mItemFour.getText().replaceAll("\\s*|\t|\r|\n",""))) {
            ToastUtil.shortToast(this, "街路巷不能为空");
            return false;
        }

        if (TextUtils.isEmpty(mItemFive.getText())|| TextUtils.isEmpty(mItemFive.getText().replaceAll("\\s*|\t|\r|\n",""))) {
            ToastUtil.shortToast(this, "门牌号不能为空");
            return false;
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        showAlertDialog();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected BasePersenter createPresent() {
        return new AddDoorNoPresent(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload_door_no:
                sunmitDoorNo();

                break;

            default:
                break;

        }
    }
}
