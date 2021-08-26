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
    private static final int TIMEOUT = 60;  //网络超时时间（秒）
    private ProgressDialog progressDialog;
    private String isExist;
    private boolean isAddNewDoorNo;
    private boolean isfromQuryAddressMapFragmnet;
    //是否是暂存状态，4即是暂存
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
            //点击排水户查看详情后字段isEdit为true时，显示编辑按钮
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

//        setDataTitle("广州市排水户信息表");
//        setRightIsVisiable(View.GONE);
        fragmentManager = getChildFragmentManager();
        ;
//        baseInfoData.llBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pshAffairDetail == null || EDIT_MODE) {
//                    //生成对话框
//                    DialogUtil.MessageBox(context, "提示", "是否确定放弃本次编辑？", new DialogInterface.OnClickListener() {
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
         * 第一层单位点击进来的
         */
        unitListBeans = (SewerageItemBean.UnitListBean) getArguments().getSerializable("unitListBeans");

        /**
         * 第二层单位点击进来的
         */
        roomclickunitListBeans = (SewerageRoomClickItemBean.UnitListBean) getArguments().getSerializable("RoomclickunitListBeans");

        initFragment();
        initEvent();
    }

    /**
     * 防抖操作
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
                            //审核通过后，依然放开编辑按钮， 点击这个按钮 提示“本条数据已通过审核，如再次编辑并提交，将标示为未审核状态”  提交后后台标示为未审核状态。
                            DialogUtil.MessageBox(mContext, "温馨提示", "本条数据已通过审核，如再次编辑并提交，将标示为未审核状态",
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
                        //审核通过后，依然放开编辑按钮， 点击这个按钮 提示“本条数据已通过审核，如再次编辑并提交，将标示为未审核状态”  提交后后台标示为未审核状态。
                        DialogUtil.MessageBox(mContext, "提示", "是否确定要注销？",
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
     * 初始化所有基
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
        //只能从新增门牌进去后进行排水户录入的时候，才会拼接街路巷字段，其他的不拼接
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
     * 显示fragment
     *
     * @param fragment 要显示的fragment
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
     * 隐藏其他fragment
     *
     * @param transaction 控制器
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
     * 版本更新
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
     * @param isTempStorage 暂存进来的
     * @param isCommit      是否是提交按钮
     */
    private void commit(final boolean isTempStorage, final boolean isCommit) {
//        Observable.create(new Observable.OnSubscribe<SewerageInfoBean>() {
//
//            @Override
//            public void call(Subscriber<? super SewerageInfoBean> subscriber) {
        final SewerageInfoBean infoBean = SewerageBeanManger.getInstance().getInfoBean();
        ((SewerageTableOneFragment) fragments.get(0)).getString();//设置值
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
                    DialogUtil.MessageBox(mContext, "温馨提示", "本排水户信息将提交为待审核，是否继续？",
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
                DialogUtil.MessageBox(mContext, "温馨提示", "本排水户信息将存为草稿，是否继续？",
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
                ToastUtil.shortToast(mContext, !isCommit ? "暂存失败" : "提交失败");
                AMCrashWriter.getInstance(mContext.getApplicationContext()).writeCrash(error, "排水户上报接口提交失败");
                CrashReportUtil.reportBugMsg(mContext, error, "排水户上报接口提交失败");
//                    CrashReport.postCatchedException(new Exception("排水户上报接口提交失败--用户名："+
//                            new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName()+ error.getMessage()));
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                dismissDialog();
                if (responseBody != null && responseBody.getCode() == 200) {
                    if (!isCommit) {
                        ToastUtil.shortToast(mContext, "暂存成功");
                    } else if (!isTempStorage && fromMyUpload) {
                        ToastUtil.shortToast(mContext, "修改成功");
                    } else {
                        ToastUtil.shortToast(mContext, "提交成功");
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
//                    //从排水户界面新增门牌进来，进行排水户录入，成功后返回
//                    if (isfromQuryAddressMapFragmnet && isAddNewDoorNo) {
//                        Intent intent = new Intent(mContext, SewerageActivity.class);
//                        //暂存就位1，不设置调查显示为调查完成
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
                    ToastUtil.shortToast(mContext, responseBody != null && !TextUtils.isEmpty(responseBody.getMessage()) ? responseBody.getMessage() : "提交失败");
//                    showLoadedError("");
                }
            }
        });
    }

    private void showDialog() {
        showWaitDialog();

        //20秒提示一次
        countDownTimer = new CountDownTimer(TIMEOUT * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long time = millisUntilFinished / 1000;
                if (progressDialog != null && !((Activity) mContext).isFinishing()) {
                    progressDialog.setMessage("正在提交，请等待。   " + time + "s");
                    if (time == 1) {
                        if (progressDialog != null && progressDialog.isShowing() && !((Activity) mContext).isFinishing()) {
                            progressDialog.dismiss();
                            ToastUtil.shortToast(mContext, "提交失败");
                        }
                    }
                }
                if (time % 40 == 0) {
                    ToastUtil.longToast(mContext, "网络忙，请稍等");
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
            progressDialog.setMessage("正在提交，请等待");
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
            if (LoginConstant.isCheckOut) {//是否校验
                List<SewerageInfoBean.WellBeen> wellBeen = sewerageInfoBean.getWellBeen();//管信息
             /*   if (ListUtil.isEmpty(sewerageInfoBean.getPhotos0())&&!EDIT_MODE) {
                    ToastUtil.shortToast(mContext, "至少要有一张现场照片");
                    isNull = true;
                    return isNull;
                }*/

                if (ListUtil.isEmpty(sewerageTableActivity.getPhotoItem().getSelectedPhotos()) || sewerageTableActivity.getPhotoItem().getSelectedPhotos().size() < 2) {
                    ToastUtil.shortToast(mContext, "至少要有两张现场照片");
                    isNull = true;
                    return isNull;
                }

                if (TextUtils.isEmpty(sewerageInfoBean.getName())) {
                    ToastUtil.shortToast(mContext, "排水户名称不能为空");
                    isNull = true;
                    return isNull;
                }
                if (TextUtils.isEmpty(sewerageInfoBean.getTown())) {
                    ToastUtil.shortToast(mContext, "所属镇（街）不能为空");
                    isNull = true;
                    return isNull;
                }
                if (TextUtils.isEmpty(sewerageInfoBean.getMph())) {
                    ToastUtil.shortToast(mContext, "门牌号不能为空");
                    isNull = true;
                    return isNull;
                }
//                if (TextUtils.isEmpty(sewerageInfoBean.getPsdyId()) || TextUtils.isEmpty(sewerageInfoBean.getPsdyName())) {
//                    ToastUtil.shortToast(mContext, "关联排水单元不能为空");
//                    isNull = true;
//                    return isNull;
//                }
              /*  if (TextUtils.isEmpty(sewerageInfoBean.getVolume())) {
                    ToastUtil.shortToast(mContext, "月用水量（吨）不能为空");
                    isNull = true;
                    return isNull;
                }*/
                if (sewerageInfoBean.isSfgypsh()) {
                    //水表号要限制必填
//                    if(TextUtils.isEmpty(sewerageInfoBean.getWaterNo())){
//                        ToastUtil.shortToast(mContext, "工业类排水户水表号必填");
//                        isNull = true;
//                        return isNull;
//                    }

                    //属于工业类排水户  必须上传管网图
                    if ("0".equals(sewerageInfoBean.getHasCert5())) {
                        ToastUtil.shortToast(mContext, "工业类排水户管网图至少有一张照片或pdf文件");
                        isNull = true;
                        return isNull;
                    }
//
//
//                            || (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneFive().getSelectedPhotos())
//                            || sewerageTableActivity.getTakephotoSewerageoneFive().getSelectedPhotos().size() < 1)
//                            || sewerageTableActivity.getAgFilePicker().getSelectFiles().size()<1) {
//                        ToastUtil.shortToast(mContext, "工业类排水户管网图至少有一张照片或pdf文件");
//                        isNull = true;
//                        return isNull;
//                    }

                    //行业类别大类只能选择“有毒有害”。小类也只能选择“有毒有害”对应的小类。
//                    if (!"有毒有害排污类".equals(sewerageInfoBean.getDischargerType1())) {
//                        ToastUtil.shortToast(mContext, "工业类排水户污水类别只能选有毒有害排污类");
//                        isNull = true;
//                        return isNull;
//                    }

                    //属于工业类排水户  接驳情况必须至少添加一个接驳井
//                    if (ListUtil.isEmpty(wellBeen)) {
//                        ToastUtil.shortToast(mContext, "工业类排水户至少添加一个接驳井");
//                        isNull = true;
//                        return isNull;
//                    }
                    //预处理设施必须选择
//                    if (TextUtils.isEmpty(sewerageInfoBean.getFac1()) || TextUtils.isEmpty(sewerageInfoBean.getFac2())
//                            || TextUtils.isEmpty(sewerageInfoBean.getFac3()) || TextUtils.isEmpty(sewerageInfoBean.getFac4())) {
//                        ToastUtil.shortToast(mContext, "工业类排水户预处理设施必须全部选择");
//                        isNull = true;
//                        return isNull;
//                    }

                }


                if (TextUtils.isEmpty(sewerageInfoBean.getAddr())) {
                    ToastUtil.shortToast(mContext, "详细地址不能为空");
                    isNull = true;
                    return isNull;
                }





              /*  if (sewerageInfoBean.getHasCert1() == null) {
                    ToastUtil.shortToast(mContext, "工商营业执照至少要选择一项");
                    isNull = true;
                    return isNull;
                }*/

                if ("1".equals(sewerageInfoBean.getHasCert1())) {
                    if (TextUtils.isEmpty(sewerageInfoBean.getCert1Code())) {
                        ToastUtil.shortToast(mContext, "工商营业执照编码不能为空");
                        isNull = true;
                        return isNull;
                    }
                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneOne().getSelectedPhotos())) {
                        ToastUtil.shortToast(mContext, "至少要有一张工商营业执照照片");
                        isNull = true;
                        return isNull;
                    }
                }
                if ("1".equals(sewerageInfoBean.getHasCert2())) {
                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneTwo().getSelectedPhotos())) {
                        ToastUtil.shortToast(mContext, "至少要有一张接驳意见核准书照片");
                        isNull = true;
                        return isNull;
                    }
                }

                if ("1".equals(sewerageInfoBean.getHasCert7())) {
                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneSeven().getSelectedPhotos())) {
                        ToastUtil.shortToast(mContext, "至少要有一张登记表照片");
                        isNull = true;
                        return isNull;
                    }
                }
                if ("1".equals(sewerageInfoBean.getHasCert3())) {
                    if (TextUtils.isEmpty(sewerageInfoBean.getCert3Code())) {
                        ToastUtil.shortToast(mContext, "排水许可证编码不能为空");
                        isNull = true;
                        return isNull;
                    }

                    if (TextUtils.isEmpty(sewerageInfoBean.getPsxkLx())) {
                        ToastUtil.shortToast(mContext, "排水户类型不能为空");
                        isNull = true;
                        return isNull;
                    }

                    if (TextUtils.isEmpty(sewerageInfoBean.getPsxkFzrq())) {
                        ToastUtil.shortToast(mContext, "发证日期不能为空");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getPsxkJzrq())) {
                        ToastUtil.shortToast(mContext, "截止日期不能为空");
                        isNull = true;
                        return isNull;
                    }

                    if ("0".equals(sewerageInfoBean.getIsRight())) {
                        ToastUtil.shortToast(mContext, "截止日期不能小于发证日期");
                        isNull = true;
                        return isNull;
                    }

                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneThree().getSelectedPhotos())) {
                        ToastUtil.shortToast(mContext, "至少要有一张排水许可证照片");
                        isNull = true;
                        return isNull;
                    }

                }
                if ("1".equals(sewerageInfoBean.getHasCert4())) {

                    if (TextUtils.isEmpty(sewerageInfoBean.getCert4Code())) {
                        ToastUtil.shortToast(mContext, "排污许可证编码不能为空");
                        isNull = true;
                        return isNull;
                    }
                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneFour().getSelectedPhotos())) {
                        ToastUtil.shortToast(mContext, "至少要有一张排污许可证照片");
                        isNull = true;
                        return isNull;
                    }
                }


                if ("1".equals(sewerageInfoBean.getHasCert5())) {
                    if (ListUtil.isEmpty(sewerageTableActivity.getTakephotoSewerageoneFive().getSelectedPhotos())
                            && ListUtil.isEmpty(sewerageTableActivity.getAgFilePicker().getSelectFiles())) {
                        ToastUtil.shortToast(mContext, "至少要有一张管网图照片或pdf附件");
                        isNull = true;
                        return isNull;
                    }
                }

                if (!ListUtil.isEmpty(sewerageInfoBean.getFiles())) {
                    for (FileBean fileBean : sewerageInfoBean.getFiles()) {
                        if (!fileBean.getName().endsWith(".pdf")) {
                            ToastUtil.shortToast(mContext, "管网图请上传pdf格式");
                            isNull = true;
                            return isNull;
                        }
                    }
                }
                //默认污水类别是生活排污类
                if (TextUtils.isEmpty(sewerageInfoBean.getDischargerType3())) {
                    ToastUtil.shortToast(mContext, "行业类别不能为空");
                    isNull = true;
                    return isNull;
                }
                //todo 暂时注释掉单位类型2
//                if (TextUtils.isEmpty(sewerageInfoBean.getDischargerType2())) {
//                    ToastUtil.shortToast(mContext, "单位类型不能为空");
//                    isNull = true;
//                    return isNull;
//                }
                if ("其他".equals(sewerageInfoBean.getDischargerType3()) || "其他：".equals(sewerageInfoBean.getDischargerType3()) && "".equals(((SewerageTableTwoFragment) fragments.get(1)).getEditOther().getText().toString().trim())) {
                    ToastUtil.shortToast(mContext, "行业类别-其他不能为空");
                    isNull = true;
                    return isNull;
                }
                /*else if(!TextUtils.isEmpty(sewerageInfoBean.getDischargerType2())){
                //单位类型有值,校验是否选择“其他”
                if(((SewerageTableTwoFragment) fragments.get(1)).checkOtherIsNull()){
                    ToastUtil.shortToast(mContext,"当前已选择了其他，请在下方的输入框内输入内容");
                    isNull=true;
                }
            } */
//                if (!ListUtil.isEmpty(wellBeen)) {
//                    for (int i = 0; i < wellBeen.size(); i++) {
//                        if (TextUtils.isEmpty(wellBeen.get(i).getPipeType())) {
//                            ToastUtil.shortToast(mContext, "管类别不能为空");
//                            isNull = true;
//                            return isNull;
//                        }
//                        if (TextUtils.isEmpty(wellBeen.get(i).getWellType())) {
//                            ToastUtil.shortToast(mContext, "井类别不能为空");
//                            isNull = true;
//                            return isNull;
//                        }
//                        if (TextUtils.isEmpty(wellBeen.get(i).getWellDir())) {
//                            ToastUtil.shortToast(mContext, "排水去向不能为空");
//                            isNull = true;
//                            return isNull;
//                        }
//                    }
//
//                }

//                if (!sewerageInfoBean.getDischargerType1().equals(SewerageTableTwoFragment.lifeWater)) {
                //排水类型为非典型类型
                if (TextUtils.isEmpty(sewerageInfoBean.getFac1()) || TextUtils.isEmpty(sewerageInfoBean.getFac2())
                        || TextUtils.isEmpty(sewerageInfoBean.getFac3()) || TextUtils.isEmpty(sewerageInfoBean.getFac4())) {
                    ToastUtil.shortToast(mContext, "预处理设施信息不能为空");
                    isNull = true;
                    return isNull;
                }
//                }
             /*   if (sewerageInfoBean.getFac1() == null) {
                    ToastUtil.shortToast(mContext, "隔油池设施至少要选择一项");
                    isNull = true;
                    return isNull;
                }*/
                if ("有".equals(sewerageInfoBean.getFac1())) {
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac1Cont())) {
                        ToastUtil.shortToast(mContext, "隔油池运行情况不能为空");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac1Main())) {
                        ToastUtil.shortToast(mContext, "隔油池是否进行养护不能为空");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac1Record())) {
                        ToastUtil.shortToast(mContext, "隔油池养护记录及台账不能为空");
                        isNull = true;
                        return isNull;
                    }
                }


            /*    if (sewerageInfoBean.getFac2() == null) {
                    ToastUtil.shortToast(mContext, "格栅设施至少要选择一项");
                    isNull = true;
                    return isNull;
                }*/
                if ("有".equals(sewerageInfoBean.getFac2())) {
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac2Cont())) {
                        ToastUtil.shortToast(mContext, "格栅运行情况不能为空");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac2Main())) {
                        ToastUtil.shortToast(mContext, "格栅是否进行养护不能为空");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac2Record())) {
                        ToastUtil.shortToast(mContext, "格栅养护记录及台账不能为空");
                        isNull = true;
                        return isNull;
                    }
                }
              /*  if (sewerageInfoBean.getFac3() == null) {
                    ToastUtil.shortToast(mContext, "沉淀池设施至少要选择一项");
                    isNull = true;
                    return isNull;
                }*/
                if ("有".equals(sewerageInfoBean.getFac3())) {
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac3Cont())) {
                        ToastUtil.shortToast(mContext, "沉淀池运行情况不能为空");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac3Main())) {
                        ToastUtil.shortToast(mContext, "沉淀池是否进行养护不能为空");
                        isNull = true;

                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac3Record())) {
                        ToastUtil.shortToast(mContext, "沉淀池养护记录及台账不能为空");
                        isNull = true;
                        return isNull;
                    }
                }
               /* if (sewerageInfoBean.getFac4() == null) {
                    ToastUtil.shortToast(mContext, "其他预处理设施至少要选择一项");
                    isNull = true;
                    return isNull;
                }*/

                if ("否".equals(sewerageInfoBean.getFac4())) {
                    isNull = true;
                    return isNull;
                }
                if (sewerageInfoBean.getFac4() == null) {
                    isNull = false;
                    return isNull;
                }
                if (!"无".equals(sewerageInfoBean.getFac4())) {
                    if ("".equals(sewerageInfoBean.getFac4())) {
                        ToastUtil.shortToast(mContext, "其他预处理措施设施名称不能为空");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac4Cont())) {
                        ToastUtil.shortToast(mContext, "其他预处理措施运行情况不能为空");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac4Main())) {
                        ToastUtil.shortToast(mContext, "其他预处理措施是否进行养护不能为空");
                        isNull = true;
                        return isNull;
                    }
                    if (TextUtils.isEmpty(sewerageInfoBean.getFac4Record())) {
                        ToastUtil.shortToast(mContext, "其他预处理措施养护记录及台账不能为空");
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
        EventBus.getDefault().post(new PhotoEvent(requestCode, resultCode, data));//第一个是要传递的数据   第二个参数是标记
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
            //生成对话框
            DialogUtil.MessageBox(mContext, "提示", "是否确定放弃本次编辑？", new DialogInterface.OnClickListener() {
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
                            ToastUtil.shortToast(mContext, "删除失败");
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            if (responseBody != null && responseBody.getCode() == 200) {
                                ToastUtil.shortToast(mContext, "注销成功");
                                EventBus.getDefault().post(new RefreshMyUploadList());
                                ((Activity) mContext).finish();
                            } else {
                                ToastUtil.shortToast(mContext, "注销失败");
                            }
                        }
                    });
        }

    }
}
