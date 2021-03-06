package com.augurit.agmobile.gzpssb.journal.view.detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.constant.LoginConstant;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.widget.FileBean;
import com.augurit.agmobile.gzps.event.PhotoEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.RefreshMyUploadList;
import com.augurit.agmobile.gzpssb.SewerageTableActivityData;
import com.augurit.agmobile.gzpssb.bean.SewerageInfoBean;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.bean.SewerageRoomClickItemBean;
import com.augurit.agmobile.gzpssb.fragment.MyBaseFragment;
import com.augurit.agmobile.gzpssb.fragment.SewerageTableFourFragment;
import com.augurit.agmobile.gzpssb.fragment.SewerageTableOneFragment;
import com.augurit.agmobile.gzpssb.fragment.SewerageTableThreeFragment;
import com.augurit.agmobile.gzpssb.fragment.SewerageTableTwoFragment;
import com.augurit.agmobile.gzpssb.journal.model.ReBack;
import com.augurit.agmobile.gzpssb.pshdoorno.add.service.UploadUserInfoService;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.utils.CrashReportUtil;
import com.augurit.agmobile.gzpssb.utils.SetColorUtil;
import com.augurit.agmobile.gzpssb.utils.SewerageBeanManger;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.am.cmpt.update.utils.CheckUpdateUtils;
import com.augurit.am.cmpt.update.utils.UpdateState;
import com.augurit.am.cmpt.update.view.ApkUpdateManager;
import com.augurit.am.fw.log.save.imp.AMCrashWriter;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xiaoyu on 2018/4/3.
 */

public class SewerageTableFragment extends MyBaseFragment {

    private SewerageTableActivityData sewerageTableActivityData;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;
    private PSHAffairDetail pshAffairDetail;
    private SewerageItemBean.UnitListBean unitListBeans;
    private SewerageRoomClickItemBean.UnitListBean roomclickunitListBeans;
    private CountDownTimer countDownTimer;
    private boolean fromMyUpload;
    private boolean fromPSHAffair;
    private boolean isEjPsh = false;
    private boolean EDIT_MODE, initFrag0, initFrag1, initFrag2, initFrag3;
    private int curIndex;
    private static final int TIMEOUT = 60;  //???????????????????????????
    private ProgressDialog progressDialog;
    private String isExist;
    private boolean isAddNewDoorNo;
    private boolean isfromQuryAddressMapFragmnet;
    //????????????????????????4????????????
    private boolean isTempStorage;
    private boolean isDialy;
    private boolean isList;
    private boolean isCancel;
    private boolean isReEdit;
    private Context mContext;
    private DetailAddress mDetailAddress;

    public static SewerageTableFragment newInstance(Bundle bundle) {
        SewerageTableFragment sewerageCompanyFragment = new SewerageTableFragment();
        sewerageCompanyFragment.setArguments(bundle);
        return sewerageCompanyFragment;
    }

    @Override
    public int initview() {
        return R.layout.activity_seweragetable;
    }

 /*   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (pshAffairDetail == null) {
                back();
            } else {
                finish();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    }*/

    @Override
    public void initData() {
        sewerageTableActivityData = getBind();
        sewerageTableActivityData.setSeweragetableonclic(new SewerageTableOnclick());
        EventBus.getDefault().register(this);
        SewerageBeanManger.getInstance().clear();

        pshAffairDetail = (PSHAffairDetail) getArguments().getSerializable("pshAffair");
        mDetailAddress = (DetailAddress) getArguments().getParcelable("adderss");
        fromPSHAffair = getArguments().getBoolean("fromPSHAffair", false);
        fromMyUpload = getArguments().getBoolean("fromMyUpload", false);
        isTempStorage = getArguments().getBoolean("isTempStorage", false);
        isEjPsh = getArguments().getBoolean("isEjPsh", false);
        isDialy = getArguments().getBoolean("isDialy", false);
        isList = getArguments().getBoolean("isList", false);
        isCancel = getArguments().getBoolean("isCancel", false);
        isReEdit = getArguments().getBoolean("isReEdit", false);
        isfromQuryAddressMapFragmnet = getArguments().getBoolean("isfromQuryAddressMapFragmnet", false);
        isExist = getArguments().getString("isExist");

        if (fromMyUpload && !isCancel) {
            sewerageTableActivityData.btnSeweragetableCommit.setVisibility(View.GONE);
            sewerageTableActivityData.btnSeweragetableEdit.setVisibility(View.VISIBLE);
//            if(!isList) {
//                sewerageTableActivityData.btnSeweragetableCancel.setVisibility(View.VISIBLE);
//            }
            sewerageTableActivityData.btnSeweragetableDel.setVisibility(View.GONE);
            setValue();
        } else if (isCancel) {
            sewerageTableActivityData.btnSeweragetableCommit.setVisibility(View.GONE);
        }

        sewerageTableActivityData.btnTempStorage.setVisibility(fromMyUpload ? View.GONE : View.VISIBLE);

        if (pshAffairDetail != null && fromPSHAffair) {
            //????????????????????????????????????isEdit???true????????????????????????
            if ("true".equals(pshAffairDetail.getIsEdit()) && !isDialy && !isCancel) {
                sewerageTableActivityData.btnTempStorage.setVisibility(View.GONE);
                sewerageTableActivityData.btnSeweragetableCommit.setVisibility(View.GONE);
                sewerageTableActivityData.btnSeweragetableDel.setVisibility(View.GONE);
                sewerageTableActivityData.btnSeweragetableEdit.setVisibility(View.VISIBLE);
                if (!isList) {
                    sewerageTableActivityData.btnSeweragetableCancel.setVisibility(View.VISIBLE);
                }
            } else {
                sewerageTableActivityData.llBottom.setVisibility(View.GONE);
            }
        }
        if (isReEdit) {
//            sewerageTableActivityData.btnSeweragetableCancel.setVisibility(View.GONE);
            sewerageTableActivityData.btnSeweragetableCommit.setVisibility(View.GONE);
            sewerageTableActivityData.btnSeweragetableEdit.setVisibility(View.VISIBLE);
            sewerageTableActivityData.llBottom.setVisibility(View.VISIBLE);
        }

//        setDataTitle("???????????????????????????");
//        setRightIsVisiable(View.GONE);
        fragmentManager = getChildFragmentManager();
        ;
//        baseInfoData.llBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pshAffairDetail == null || EDIT_MODE) {
//                    //???????????????
//                    DialogUtil.MessageBox(context, "??????", "?????????????????????????????????", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    }, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                } else {
//                    finish();
//                }
//
//            }
//        });

//        if (mInfoBean == null) {
//            mInfoBean = new SewerageInfoBean();
//        }


        /**
         * ??????????????????????????????
         */
        unitListBeans = (SewerageItemBean.UnitListBean) getArguments().getSerializable("unitListBeans");

        /**
         * ??????????????????????????????
         */
        roomclickunitListBeans = (SewerageRoomClickItemBean.UnitListBean) getArguments().getSerializable("RoomclickunitListBeans");

        initFragment();
        initEvent();
    }

    /**
     * ????????????
     */
    private void initEvent() {
        RxView.clicks(sewerageTableActivityData.btnSeweragetableCommit)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        checkVersionUpdate(isTempStorage, true);
//                     commit(isTempStorage,true);
                    }
                });
        RxView.clicks(sewerageTableActivityData.btnTempStorage)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        checkVersionUpdate(true, false);
//                      commit(true,false);
                    }
                });

        RxView.clicks(sewerageTableActivityData.btnSeweragetableEdit)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (fromMyUpload && pshAffairDetail != null && "2".equals(pshAffairDetail.getData().getState())) {
                            //????????????????????????????????????????????? ?????????????????? ????????????????????????????????????????????????????????????????????????????????????????????????  ??????????????????????????????????????????
                            DialogUtil.MessageBox(mContext, "????????????", "????????????????????????????????????????????????????????????????????????????????????",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            setEditMode();
                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dismissDialog();
                                        }
                                    });
                        } else {
                            setEditMode();
                        }
                    }
                });
        RxView.clicks(sewerageTableActivityData.btnSeweragetableCancel)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //????????????????????????????????????????????? ?????????????????? ????????????????????????????????????????????????????????????????????????????????????????????????  ??????????????????????????????????????????
                        DialogUtil.MessageBox(mContext, "??????", "????????????????????????",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        cancel();
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dismissDialog();
                                    }
                                });
                    }
                });
    }

    private void setValue() {
        if (pshAffairDetail == null || pshAffairDetail.getData() == null) {
            return;
        }
        PSHAffairDetail.DataBean data = pshAffairDetail.getData();
        SewerageBeanManger.getInstance().setJzwcode(data.getJzwcode());
        SewerageBeanManger.getInstance().setLoginName(data.getLoginName());
        SewerageBeanManger.getInstance().setDirectOrgId(data.getDirectOrgId());
        SewerageBeanManger.getInstance().setDirectOrgName(data.getDirectOrgName());
        SewerageBeanManger.getInstance().setTeamOrgId(data.getTeamOrgId());
        SewerageBeanManger.getInstance().setTeamOrgName(data.getTeamOrgName());
        SewerageBeanManger.getInstance().setParentOrgId(data.getParentOrgId());
        SewerageBeanManger.getInstance().setParentOrgName(data.getParentOrgName());
        SewerageBeanManger.getInstance().setId(data.getId());
        SewerageBeanManger.getInstance().setDoorplateAddressCode(data.getDoorplateAddressCode());
        SewerageBeanManger.getInstance().setHouseIdFlag(data.getHouseIdFlag());
        SewerageBeanManger.getInstance().setHouseId(data.getHouseId());
        SewerageBeanManger.getInstance().setMarkPersonId(data.getMarkPersonId());
        SewerageBeanManger.getInstance().setMarkPerson(data.getMarkPerson());
//        SewerageBeanManger.getInstance().setMarkTime(new Date(data.getMarkTime()));
        SewerageBeanManger.getInstance().setArea(data.getArea());
        SewerageBeanManger.getInstance().setVillage(data.getVillage());
        SewerageBeanManger.getInstance().setStreet(data.getStreet());
        SewerageBeanManger.getInstance().setDischargerType1(data.getDischargerType1());
        SewerageBeanManger.getInstance().setDischargerType2(data.getDischargerType2());
        SewerageBeanManger.getInstance().setDischargerType3(data.getDischargerType3());
        SewerageBeanManger.getInstance().setPsxkFzrq(data.getPsxkFzrq());
        SewerageBeanManger.getInstance().setPsxkJzrq(data.getPsxkJzrq());
        SewerageBeanManger.getInstance().setFac1(data.getFac1());
        SewerageBeanManger.getInstance().setFac1Cont(data.getFac1Cont());
        SewerageBeanManger.getInstance().setFac1Main(data.getFac1Main());
        SewerageBeanManger.getInstance().setFac1Record(data.getFac1Record());
        SewerageBeanManger.getInstance().setFac2(data.getFac2());
        SewerageBeanManger.getInstance().setFac2Cont(data.getFac2Cont());
        SewerageBeanManger.getInstance().setFac2Main(data.getFac2Main());
        SewerageBeanManger.getInstance().setFac2Record(data.getFac2Record());
        SewerageBeanManger.getInstance().setFac3(data.getFac3());
        SewerageBeanManger.getInstance().setFac3Cont(data.getFac3Cont());
        SewerageBeanManger.getInstance().setFac3Main(data.getFac3Main());
        SewerageBeanManger.getInstance().setFac3Record(data.getFac3Record());
        SewerageBeanManger.getInstance().setFac4(data.getFac4());
        SewerageBeanManger.getInstance().setFac4Cont(data.getFac4Cont());
        SewerageBeanManger.getInstance().setFac4Main(data.getFac4Main());
        SewerageBeanManger.getInstance().setFac4Record(data.getFac4Record());
        SewerageBeanManger.getInstance().setState(data.getState());
        SewerageBeanManger.getInstance().setCheckPersonId(data.getCheckPersonId());
        SewerageBeanManger.getInstance().setCheckPerson(data.getCheckPerson());
//        SewerageBeanManger.getInstance().setCheckTime(new Data);
        SewerageBeanManger.getInstance().setCheckDesription(data.getCheckDesription());
        SewerageBeanManger.getInstance().setWellBeen(data.getWellBeen());
        SewerageBeanManger.getInstance().setX(data.getX());
        SewerageBeanManger.getInstance().setY(data.getY());
        SewerageBeanManger.getInstance().setUnitId(data.getUnitId());
        SewerageBeanManger.getInstance().setDescription(data.getDescription());
//        SewerageBeanManger.getInstance().setAddType(data.getad());
    }


    /**
     * ??????????????????
     */
    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        Bundle bundle = new Bundle();
        bundle.putSerializable("pshAffair", pshAffairDetail);
        bundle.putBoolean("fromMyUpload", fromMyUpload);
        bundle.putBoolean("fromPSHAffair", fromPSHAffair);
        bundle.putBoolean("isEjPsh", isEjPsh);
        bundle.putSerializable("unitListBeans", unitListBeans);
        bundle.putParcelable("adderss", mDetailAddress);
        bundle.putBoolean("isTempStorage", isTempStorage);
        bundle.putSerializable("roomclickunitListBeans", roomclickunitListBeans);
        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????
        bundle.putBoolean("isfromQuryAddressMapFragmnet", isfromQuryAddressMapFragmnet);

        fragments.add(SewerageTableOneFragment.newInstance(bundle));
        fragments.add(SewerageTableTwoFragment.newInstance(bundle));
        fragments.add(SewerageTableThreeFragment.newInstance(bundle));
        fragments.add(SewerageTableFourFragment.newInstance(bundle));

        ((SewerageTableOneFragment) fragments.get(0)).setViewCreatedListener(new SewerageTableOneFragment.onViewCreatedListener() {
            @Override
            public void onViewCreated() {
                initFrag0 = true;
                if (EDIT_MODE) {
                    ((SewerageTableOneFragment) fragments.get(0)).setEditMode(true);
                }
            }
        });

        ((SewerageTableTwoFragment) fragments.get(1)).setViewCreatedListener(new SewerageTableTwoFragment.onViewCreatedListener() {
            @Override
            public void onViewCreated() {

                initFrag1 = true;
                if (EDIT_MODE) {
                    ((SewerageTableTwoFragment) fragments.get(1)).setEditMode(true);
                }
            }
        });

        ((SewerageTableThreeFragment) fragments.get(2)).setViewCreatedListener(new SewerageTableThreeFragment.onViewCreatedListener() {
            @Override
            public void onViewCreated() {
                initFrag2 = true;
                if (EDIT_MODE) {
                    ((SewerageTableThreeFragment) fragments.get(2)).setEditMode(true);
                }
            }
        });
        ((SewerageTableFourFragment) fragments.get(3)).setViewCreatedListener(new SewerageTableFourFragment.onViewCreatedListener() {
            @Override
            public void onViewCreated() {

                initFrag3 = true;
                if (EDIT_MODE) {
                    ((SewerageTableFourFragment) fragments.get(3)).setEditMode(true);
                }
            }
        });

        showFragment(fragments.get(0));

    }

    /**
     * ??????fragment
     *
     * @param fragment ????????????fragment
     */
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.ll_container, fragment, fragment.getClass().getName());
        }
        transaction.commit();
    }

    /**
     * ????????????fragment
     *
     * @param transaction ?????????
     */
    private void hideFragment(FragmentTransaction transaction) {
        for (int i = 0; fragments.size() > i; i++) {
            if (fragments.get(i).isVisible()) {
                transaction.hide(fragments.get(i));
            }
        }
    }


    private class SewerageTableOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_fg_seweragetable_one:
                    setCurChooseTab(0);
                    break;
                case R.id.ll_fg_seweragetable_two:
                    setCurChooseTab(1);
                    break;
                case R.id.ll_fg_seweragetable_three:
                    setCurChooseTab(2);
                    break;
                case R.id.ll_fg_seweragetable_four:
                    setCurChooseTab(3);
                    break;
            }

        }
    }

    private void setEditMode() {
        ((SewerageTableOneFragment) fragments.get(0)).hideBoottomSheet();
        ((SewerageTableOneFragment) fragments.get(0)).hideLlstate();
        sewerageTableActivityData.btnSeweragetableCommit.setVisibility(View.VISIBLE);
        sewerageTableActivityData.btnSeweragetableEdit.setVisibility(View.GONE);
        sewerageTableActivityData.btnSeweragetableCancel.setVisibility(View.GONE);
//        sewerageTableActivityData.btnSeweragetableDel.setVisibility("true".equals(pshAffairDetail.getIsEdit()) ? View.GONE : View.VISIBLE);
        sewerageTableActivityData.btnSeweragetableDel.setVisibility(View.GONE);
        sewerageTableActivityData.btnTempStorage.setVisibility((fromMyUpload && !isTempStorage) || "true".equals(pshAffairDetail.getIsEdit()) ? View.GONE : View.VISIBLE);
        EDIT_MODE = true;
        if (initFrag0) {
            ((SewerageTableOneFragment) fragments.get(0)).setEditMode(true);
        }
        if (initFrag1) {
            ((SewerageTableTwoFragment) fragments.get(1)).setEditMode(true);
        }
        if (initFrag2) {
            ((SewerageTableThreeFragment) fragments.get(2)).setEditMode(true);
        }
        if (initFrag3) {
            ((SewerageTableFourFragment) fragments.get(3)).setEditMode(true);
        }
    }

    private void setCurChooseTab(int tabIndex) {
        SetColorUtil.setBgColor(mContext, tabIndex,
                sewerageTableActivityData.llFgSeweragetableOne, sewerageTableActivityData.llFgSeweragetableTwo,
                sewerageTableActivityData.llFgSeweragetableThree, sewerageTableActivityData.llFgSeweragetableFour
        );
        curIndex = tabIndex;
        showFragment(fragments.get(tabIndex));

    }

    /**
     * ????????????
     *
     * @param isTempStorage
     * @param isComit
     */
    private void checkVersionUpdate(boolean isTempStorage, boolean isComit) {
        checkVersionUpdateWithPermissonCheck(mContext, LoginConstant.APP_UPDATE_URL_ARR, isTempStorage, isComit);
    }

    private void checkVersionUpdateWithPermissonCheck(Context context, String appUpdateUrlArr[], final boolean isTempStorage, final boolean isComit) {
        String updateUrl = appUpdateUrlArr[new Random().nextInt(appUpdateUrlArr.length)];
        //String updateUrl = "http://" + LoginConstant.BASE_GZPS_URL + "/appFile/apk_version.json";
        CheckUpdateUtils.setServerUrl(updateUrl);
        showWaitDialog();
        new ApkUpdateManager(context, UpdateState.INNER_UPDATE, new ApkUpdateManager.NoneUpdateCallback() {
            @Override
            public void onFinish(boolean isNeedUpdate) {
                if (!isNeedUpdate) {
                    commit(isTempStorage, isComit);
                } else {
                    dismissWaitDialog();
                }
            }
        }).checkUpdate();
    }

    /**
     * @param isTempStorage ???????????????
     * @param isCommit      ?????????????????????
     */
    private void commit(final boolean isTempStorage, final boolean isCommit) {
//        Observable.create(new Observable.OnSubscribe<SewerageInfoBean>() {
//
//            @Override
//            public void call(Subscriber<? super SewerageInfoBean> subscriber) {
        final SewerageInfoBean infoBean = SewerageBeanManger.getInstance().getInfoBean();
        ((SewerageTableOneFragment) fragments.get(0)).getString();//?????????
        ((SewerageTableOneFragment) fragments.get(0)).getTab1AllParams();
//                subscriber.onNext(infoBean);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<SewerageInfoBean>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                    }
//
//                    @Override
//                    public void onNext(final SewerageInfoBean infoBean) {
        if (!check(infoBean)) {
            if (!isCommit) {
                SewerageBeanManger.getInstance().setSaveType(1);
                SewerageBeanManger.getInstance().setState("4");
            } else {
                SewerageBeanManger.getInstance().setState("1");
                SewerageBeanManger.getInstance().setSaveType(0);
            }

            if (isCommit) {
                if (isTempStorage) {
                    DialogUtil.MessageBox(mContext, "????????????", "?????????????????????????????????????????????????????????",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    commitData(infoBean, isCommit);
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dismissDialog();
                                }
                            });
                } else {
                    commitData(infoBean, isCommit);
                }
            } else {
                DialogUtil.MessageBox(mContext, "????????????", "???????????????????????????????????????????????????",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                commitData(infoBean, isCommit);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dismissDialog();
                            }
                        });
            }

        } else {
            dismissWaitDialog();
        }
//                    }
//                });


        /*    ((SewerageTableTwoFragment) fragments.get(1)).setDefault();*/


    }

    private void commitData(final SewerageInfoBean infoBean, final boolean isCommit) {
        Observable<ResponseBody> responseBodyObservable;

        if (fromMyUpload || pshAffairDetail != null) {
            SewerageBeanManger.getInstance().setId(pshAffairDetail != null && pshAffairDetail.getData() != null ? pshAffairDetail.getData().getId() : "");
            responseBodyObservable = new UploadUserInfoService(mContext).toUpdateCollect(infoBean);
        } else {
//            SewerageBeanManger.getInstance().setDoorplateAddressCode(MyApplication.GUID);
//            SewerageBeanManger.getInstance().setX(MyApplication.X);
//            SewerageBeanManger.getInstance().setY(MyApplication.Y);
//            infoBean.setHouseId(getIntent().getStringExtra("HouseId"));
//            if (!TextUtils.isEmpty(isExist) && "1".equals(isExist)) {
//                infoBean.setAdd_type("0");
//            }
//
//            isAddNewDoorNo = getIntent().getBooleanExtra("isAddNewDoorNo", false);
//            infoBean.setHouseIdFlag(getIntent().getStringExtra("HouseIdFlag"));
//            infoBean.setUnitId(getIntent().getStringExtra("UnitId"));
            responseBodyObservable = new UploadUserInfoService(mContext).uploadUserInfo(infoBean);
        }
        showDialog();

        responseBodyObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable error) {
                dismissDialog();
                ToastUtil.shortToast(mContext, !isCommit ? "????????????" : "????????????");
                AMCrashWriter.getInstance(mContext.getApplicationContext()).writeCrash(error, "?????????????????????????????????");
                CrashReportUtil.reportBugMsg(mContext, error, "?????????????????????????????????");
//                    CrashReport.postCatchedException(new Exception("?????????????????????????????????--????????????"+
//                            new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName()+ error.getMessage()));
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                dismissDialog();
                if (responseBody != null && responseBody.getCode() == 200) {
                    if (!isCommit) {
                        ToastUtil.shortToast(mContext, "????????????");
                    } else if (!isTempStorage && fromMyUpload) {
                        ToastUtil.shortToast(mContext, "????????????");
                    } else {
                        ToastUtil.shortToast(mContext, "????????????");
                    }
                    EventBus.getDefault().post(new RefreshMyUploadList());
                    ((Activity) mContext).finish();
//                    if (fromMyUpload) {
//                        EventBus.getDefault().post(new RefreshMyUploadList());
//                        if (null != MyApplication.doorBean && MyApplication.doorBean.getS_guid().equals(infoBean.getDoorplateAddressCode())) {
//                            MyApplication.doorBean.setISTATUE("2");
//                            ((Activity)mContext).finish();
//                            SewerageCompanyFragment.isSearch = false;
//                            SewerageActivity.isSearch = false;
//                            ((Activity)mContext).finish();
//                            return;
//                        }
//                    } else {
//                        if (isCommit) {
//                            EventBus.getDefault().post(new RefreshDataListEvent(MyApplication.refreshListType));
//                            if (null != MyApplication.doorBean && MyApplication.doorBean.getS_guid().equals(infoBean.getDoorplateAddressCode())) {
//                                MyApplication.doorBean.setISTATUE("2");
//                            }
//                        } else {
//
//                        }
//                    }
//                    //??????????????????????????????????????????????????????????????????????????????
//                    if (isfromQuryAddressMapFragmnet && isAddNewDoorNo) {
//                        Intent intent = new Intent(mContext, SewerageActivity.class);
//                        //????????????1???????????????????????????????????????
//                        if (isCommit) {
//                            MyApplication.doorBean.setISTATUE("2");
//                        }
//                        intent.putExtra("doorBean", MyApplication.doorBean);
//                        startActivity(intent);
//                        ((Activity)mContext).finish();
//                        return;
//                    }
//
//                    if (isAddNewDoorNo) {
//                        Intent intent = new Intent(mContext, SewerageActivity.class);
//                        MyApplication.doorBean.setISTATUE("2");
//                        intent.putExtra("doorBean", MyApplication.doorBean);
//                        startActivity(intent);
//                    }
//                    ((Activity)mContext).finish();
////                    SewerageCompanyFragment.isSearch = true;
////                    SewerageActivity.isSearch = true;
//                    Bundle bundle = new Bundle();
//                    bundle.putString("refresh", isCommit ? "1" : "");
//                    EventBus.getDefault().post(bundle);
                } else {
                    ToastUtil.shortToast(mContext, responseBody != null && !TextUtils.isEmpty(responseBody.getMessage()) ? responseBody.getMessage() : "????????????");
//                    showLoadedError("");
                }
            }
        });
    }

    private void showDialog() {
        showWaitDialog();

        //20???????????????
        countDownTimer = new CountDownTimer(TIMEOUT * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long time = millisUntilFinished / 1000;
                if (progressDialog != null && !((Activity) mContext).isFinishing()) {
                    progressDialog.setMessage("???????????????????????????   " + time + "s");
                    if (time == 1) {
                        if (progressDialog != null && progressDialog.isShowing() && !((Activity) mContext).isFinishing()) {
                            progressDialog.dismiss();
                            ToastUtil.shortToast(mContext, "????????????");
                        }
                    }
                }
                if (time % 40 == 0) {
                    ToastUtil.longToast(mContext, "?????????????????????");
                }
            }

            @Override
            public void onFinish() {

            }
        };

        if (countDownTimer != null) {
            countDownTimer.start();
        }
    }

    private void dismissWaitDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showWaitDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("????????????????????????");
            progressDialog.setCancelable(false);
        }
        if (!progressDialog.isShowing() && !((Activity) mContext).isFinishing()) {
            progressDialog.show();
        }
    }

    private boolean check(SewerageInfoBean sewerageInfoBean) {
        SewerageTableOneFragment sewerageTableActivity = ((SewerageTableOneFragment) fragments.get(0));
        boolean isNull = false;
        try {
            if (LoginConstant.isCheckOut) {//????????????
                List<SewerageInfoBean.WellBeen> wellBeen = sewerageInfoBean.getWellBeen();//?????????
             /*   if (ListUtil.isEmpty(sewerageInfoBean.getPhotos0())&&!EDIT_MODE) {
                    ToastUtil.shortToast(mContext, "??????????????????????????????");
                    isNull = true;
                    return isNull;
                }*/

                if (ListUtil.isEmpty(sewerageTableActivity.getPhotoItem().getSelectedPhotos()) || sewerageTableActivity.getPhotoItem().getSelectedPhotos().size() < 2) {
                    ToastUtil.shortToast(mContext, "??????????????????????????????");
                    isNull = true;
                    return isNull;
                }

                if (TextUtils.isEmpty(sewerageInfoBean.getName())) {
                    ToastUtil.shortToast(mContext, "???????????????????????????");
                    isNull = true;
                    return isNull;
                }
                if (TextUtils.isEmpty(sewerageInfoBean.getTown())) {
                    ToastUtil.shortToast(mContext, "??????????????????????????????");
                    isNull = true;
                    return isNull;
                }
                if (TextUtils.isEmpty(sewerageInfoBean.getMph())) {
                    ToastUtil.shortToast(mContext, "?????????????????????");
                    isNull = true;
                    return isNull;
                }
//                if (TextUtils.isEmpty(sewerageInfoBean.getPsdyId()) || TextUtils.isEmpty(sewerageInfoBean.getPsdyName())) {
//                    ToastUtil.shortToast(mContext, "??????????????????????????????");
//                    isNull = true;
//                    return isNull;
//                }
              /*  if (TextUtils.isEmpty(sewerageInfoBean.getVolume())) {
                    ToastUtil.shortToast(mContext, "?????????????????????????????????");
                    isNull = true;
                    return isNull;
                }*/
                if (sewerageInfoBean.isSfgypsh()) {
                    //????????????????????????
//                    if(TextUtils.isEmpty(sewerageInfoBean.getWaterNo())){
//                        ToastUtil.shortToast(mContext, "?????????????????????????????????");
//                        isNull = true;
//                        return isNull;
//                    }

                    //????????????????????????  ?????????????????????
                    if ("0".equals(sewerageInfoBean.getHasCert5())) {
                        ToastUtil.shortToast(mContext, "???????????????????????????????????????????????????pdf??????");
                        isNull = true;
                        return isNull;
                    }
//
//
//                            || (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneFive().getSelectedPhotos())
//                            || sewerageTableActivity.getTakephotoSewerageoneFive().getSelectedPhotos().size() < 1)
//                            || sewerageTableActivity.getAgFilePicker().getSelectFiles().size()<1) {
//                        ToastUtil.shortToast(mContext, "???????????????????????????????????????????????????pdf??????");
//                        isNull = true;
//                        return isNull;
//                    }

                    //????????????????????????????????????????????????????????????????????????????????????????????????????????????
//                    if (!"?????????????????????".equals(sewerageInfoBean.getDischargerType1())) {
//                        ToastUtil.shortToast(mContext, "????????????????????????????????????????????????????????????");
//                        isNull = true;
//                        return isNull;
//                    }

                    //????????????????????????  ?????????????????????????????????????????????
//                    if (ListUtil.isEmpty(wellBeen)) {
//                        ToastUtil.shortToast(mContext, "?????????????????????????????????????????????");
//                        isNull = true;
//                        return isNull;
//                    }
                    //???????????????????????????
//                    if (TextUtils.isEmpty(sewerageInfoBean.getFac1()) || TextUtils.isEmpty(sewerageInfoBean.getFac2())
//                            || TextUtils.isEmpty(sewerageInfoBean.getFac3()) || TextUtils.isEmpty(sewerageInfoBean.getFac4())) {
//                        ToastUtil.shortToast(mContext, "???????????????????????????????????????????????????");
//                        isNull = true;
//                        return isNull;
//                    }

                }


                if (TextUtils.isEmpty(sewerageInfoBean.getAddr())) {
                    ToastUtil.shortToast(mContext, "????????????????????????");
                    isNull = true;
                    return isNull;
                }





              /*  if (sewerageInfoBean.getHasCert1() == null) {
                    ToastUtil.shortToast(mContext, "???????????????????????????????????????");
                    isNull = true;
                    return isNull;
                }*/

                if ("1".equals(sewerageInfoBean.getHasCert1())) {
                    if (TextUtils.isEmpty(sewerageInfoBean.getCert1Code())) {
                        ToastUtil.shortToast(mContext, "????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneOne().getSelectedPhotos())) {
                        ToastUtil.shortToast(mContext, "??????????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                }
                if ("1".equals(sewerageInfoBean.getHasCert2())) {
                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneTwo().getSelectedPhotos())) {
                        ToastUtil.shortToast(mContext, "?????????????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                }

                if ("1".equals(sewerageInfoBean.getHasCert7())) {
                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneSeven().getSelectedPhotos())) {
                        ToastUtil.shortToast(mContext, "?????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                }
                if ("1".equals(sewerageInfoBean.getHasCert3())) {
                    if (TextUtils.isEmpty(sewerageInfoBean.getCert3Code())) {
                        ToastUtil.shortToast(mContext, "?????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }

                    if (TextUtils.isEmpty(sewerageInfoBean.getPsxkLx())) {
                        ToastUtil.shortToast(mContext, "???????????????????????????");
                        isNull = true;
                        return isNull;
                    }

                    if (TextUtils.isEmpty(sewerageInfoBean.getPsxkFzrq())) {
                        ToastUtil.shortToast(mContext, "????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getPsxkJzrq())) {
                        ToastUtil.shortToast(mContext, "????????????????????????");
                        isNull = true;
                        return isNull;
                    }

                    if ("0".equals(sewerageInfoBean.getIsRight())) {
                        ToastUtil.shortToast(mContext, "????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }

                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneThree().getSelectedPhotos())) {
                        ToastUtil.shortToast(mContext, "???????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }

                }
                if ("1".equals(sewerageInfoBean.getHasCert4())) {

                    if (TextUtils.isEmpty(sewerageInfoBean.getCert4Code())) {
                        ToastUtil.shortToast(mContext, "?????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneFour().getSelectedPhotos())) {
                        ToastUtil.shortToast(mContext, "???????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                }


                if ("1".equals(sewerageInfoBean.getHasCert5())) {
                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneFive().getSelectedPhotos())
                            && ListUtil.isEmpty(sewerageTableActivity.getAgFilePicker().getSelectFiles())) {
                        ToastUtil.shortToast(mContext, "????????????????????????????????????pdf??????");
                        isNull = true;
                        return isNull;
                    }
                }

                if (!ListUtil.isEmpty(sewerageInfoBean.getFiles())) {
                    for (FileBean fileBean : sewerageInfoBean.getFiles()) {
                        if (!fileBean.getName().endsWith(".pdf")) {
                            ToastUtil.shortToast(mContext, "??????????????????pdf??????");
                            isNull = true;
                            return isNull;
                        }
                    }
                }
                //????????????????????????????????????
                if (TextUtils.isEmpty(sewerageInfoBean.getDischargerType3())) {
                    ToastUtil.shortToast(mContext, "????????????????????????");
                    isNull = true;
                    return isNull;
                }
                //todo ???????????????????????????2
//                if (TextUtils.isEmpty(sewerageInfoBean.getDischargerType2())) {
//                    ToastUtil.shortToast(mContext, "????????????????????????");
//                    isNull = true;
//                    return isNull;
//                }
                if ("??????".equals(sewerageInfoBean.getDischargerType3()) || "?????????".equals(sewerageInfoBean.getDischargerType3()) && "".equals(((SewerageTableTwoFragment) fragments.get(1)).getEditOther().getText().toString().trim())) {
                    ToastUtil.shortToast(mContext, "????????????-??????????????????");
                    isNull = true;
                    return isNull;
                }
                /*else if(!TextUtils.isEmpty(sewerageInfoBean.getDischargerType2())){
                //??????????????????,??????????????????????????????
                if(((SewerageTableTwoFragment) fragments.get(1)).checkOtherIsNull()){
                    ToastUtil.shortToast(mContext,"??????????????????????????????????????????????????????????????????");
                    isNull=true;
                }
            } */
//                if (!ListUtil.isEmpty(wellBeen)) {
//                    for (int i = 0; i < wellBeen.size(); i++) {
//                        if (TextUtils.isEmpty(wellBeen.get(i).getPipeType())) {
//                            ToastUtil.shortToast(mContext, "?????????????????????");
//                            isNull = true;
//                            return isNull;
//                        }
//                        if (TextUtils.isEmpty(wellBeen.get(i).getWellType())) {
//                            ToastUtil.shortToast(mContext, "?????????????????????");
//                            isNull = true;
//                            return isNull;
//                        }
//                        if (TextUtils.isEmpty(wellBeen.get(i).getWellDir())) {
//                            ToastUtil.shortToast(mContext, "????????????????????????");
//                            isNull = true;
//                            return isNull;
//                        }
//                    }
//
//                }

//                if (!sewerageInfoBean.getDischargerType1().equals(SewerageTableTwoFragment.lifeWater)) {
                //??????????????????????????????
                if (TextUtils.isEmpty(sewerageInfoBean.getFac1()) || TextUtils.isEmpty(sewerageInfoBean.getFac2())
                        || TextUtils.isEmpty(sewerageInfoBean.getFac3()) || TextUtils.isEmpty(sewerageInfoBean.getFac4())) {
                    ToastUtil.shortToast(mContext, "?????????????????????????????????");
                    isNull = true;
                    return isNull;
                }
//                }
             /*   if (sewerageInfoBean.getFac1() == null) {
                    ToastUtil.shortToast(mContext, "????????????????????????????????????");
                    isNull = true;
                    return isNull;
                }*/
                if ("???".equals(sewerageInfoBean.getFac1())) {
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac1Cont())) {
                        ToastUtil.shortToast(mContext, "?????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac1Main())) {
                        ToastUtil.shortToast(mContext, "???????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac1Record())) {
                        ToastUtil.shortToast(mContext, "??????????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                }


            /*    if (sewerageInfoBean.getFac2() == null) {
                    ToastUtil.shortToast(mContext, "?????????????????????????????????");
                    isNull = true;
                    return isNull;
                }*/
                if ("???".equals(sewerageInfoBean.getFac2())) {
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac2Cont())) {
                        ToastUtil.shortToast(mContext, "??????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac2Main())) {
                        ToastUtil.shortToast(mContext, "????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac2Record())) {
                        ToastUtil.shortToast(mContext, "???????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                }
              /*  if (sewerageInfoBean.getFac3() == null) {
                    ToastUtil.shortToast(mContext, "????????????????????????????????????");
                    isNull = true;
                    return isNull;
                }*/
                if ("???".equals(sewerageInfoBean.getFac3())) {
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac3Cont())) {
                        ToastUtil.shortToast(mContext, "?????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac3Main())) {
                        ToastUtil.shortToast(mContext, "???????????????????????????????????????");
                        isNull = true;

                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac3Record())) {
                        ToastUtil.shortToast(mContext, "??????????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                }
               /* if (sewerageInfoBean.getFac4() == null) {
                    ToastUtil.shortToast(mContext, "??????????????????????????????????????????");
                    isNull = true;
                    return isNull;
                }*/

                if ("???".equals(sewerageInfoBean.getFac4())) {
                    isNull = true;
                    return isNull;
                }
                if (sewerageInfoBean.getFac4() == null) {
                    isNull = false;
                    return isNull;
                }
                if (!"???".equals(sewerageInfoBean.getFac4())) {
                    if ("".equals(sewerageInfoBean.getFac4())) {
                        ToastUtil.shortToast(mContext, "?????????????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac4Cont())) {
                        ToastUtil.shortToast(mContext, "?????????????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac4Main())) {
                        ToastUtil.shortToast(mContext, "???????????????????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac4Record())) {
                        ToastUtil.shortToast(mContext, "??????????????????????????????????????????????????????");
                        isNull = true;
                        return isNull;
                    }
                }
            }
        } catch (Exception e) {
            dismissWaitDialog();
            Log.e("TAG", "Exception:" + e.toString());
        }

        return isNull;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EventBus.getDefault().post(new PhotoEvent(requestCode, resultCode, data));//??????????????????????????????   ????????????????????????
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissDialog();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mContext = context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mContext = activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

    @Subscribe
    public void onEventBack(ReBack ReBack) {
        if (pshAffairDetail == null || EDIT_MODE) {
            //???????????????
            DialogUtil.MessageBox(mContext, "??????", "?????????????????????????????????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((Activity) mContext).finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            ((Activity) mContext).finish();
        }
    }

    private void cancel() {
        if (pshAffairDetail != null) {
            new UploadUserInfoService(mContext)
                    .toCancelCollect(pshAffairDetail.getData().getId())
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.shortToast(mContext, "????????????");
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            if (responseBody != null && responseBody.getCode() == 200) {
                                ToastUtil.shortToast(mContext, "????????????");
                                EventBus.getDefault().post(new RefreshMyUploadList());
                                ((Activity) mContext).finish();
                            } else {
                                ToastUtil.shortToast(mContext, "????????????");
                            }
                        }
                    });
        }

    }
}
