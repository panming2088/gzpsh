package com.augurit.agmobile.gzpssb.secondpsh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfo;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfoManger;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondPshRefreshEvent;
import com.augurit.agmobile.gzpssb.secondpsh.service.SecondLevelPshService;
import com.augurit.agmobile.gzpssb.utils.SetColorUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SecondLevelPshDetailActivity extends BaseActivity implements View.OnClickListener {
    private  boolean EDIT_MODE  ;
    private ArrayList<Fragment> fragments;
    private SecondLevelPshInfo.SecondPshInfo secondPshInfo;
    private SecondLevelPshService pshService;
    private FragmentManager fragmentManager;
    private LinearLayout ll_fg_seweragetable_one,ll_fg_seweragetable_two;
    private int curIndex;
    private int mode = 0;
    private ProgressDialog progressDialog;
    //编辑、注销、提交、删除、保存草稿
    private Button btn_edit,btn_cancel,btn_commit,btn_delete,btn_save_draft;
    private boolean initFrag0;
    private boolean initFrag;
    private SewerageItemBean.UnitListBean unitListBean;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_psh_info);
        initView();
    }

    private void initView() {
        SecondLevelPshInfoManger.getInstance().clear();
        fragments = new ArrayList<Fragment>();
        secondPshInfo = (SecondLevelPshInfo.SecondPshInfo) getIntent().getSerializableExtra("data");
        String type = getIntent().getStringExtra("TYPE");
        unitListBean = (SewerageItemBean.UnitListBean) getIntent().getSerializableExtra("unitListBean");
        pshService = new SecondLevelPshService(this);
        fragmentManager = getSupportFragmentManager();
        ll_fg_seweragetable_one = (LinearLayout) findViewById(R.id.ll_fg_seweragetable_one);
        ll_fg_seweragetable_two = (LinearLayout) findViewById(R.id.ll_fg_seweragetable_two);
        ll_fg_seweragetable_one.setOnClickListener(this);
        ll_fg_seweragetable_two.setOnClickListener(this);
        findViewById(R.id.ll_back).setOnClickListener(this);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_commit = (Button) findViewById(R.id.btn_commit);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_save_draft = (Button) findViewById(R.id.btn_save_draft);
        findViewById(R.id.ll_back).setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_save_draft.setOnClickListener(this);
        setValue();
        ((TextView)findViewById(R.id.tv_title)).setText("二级排水户详情");
        if(!TextUtils.isEmpty(type) && type.equals("add")){
            mode = 1;//新增
            ((TextView)findViewById(R.id.tv_title)).setText("二级排水户新增");
            setMode(mode);
        }else if(secondPshInfo != null ){
            mode = 3;//查看
            setMode(mode);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("unitListBean",unitListBean);
        bundle.putSerializable("data",secondPshInfo);
        RxView.clicks(btn_commit)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                               @Override
                               public void call(Void aVoid) {
//                                   secondPshInfo = new SecondLevelPshInfo().new SecondPshInfo();
                                   ((SecondLevelPshBaseInfoFragment) fragments.get(0)).getData();
                                   uploadSecondPsh(SecondLevelPshInfoManger.getInstance().getInfoBean());

                               }
                           });

        RxView.clicks(btn_delete)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        DialogUtil.MessageBox(SecondLevelPshDetailActivity.this, "提醒", "是否确定要删除？", new DialogInterface.OnClickListener() {
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
                });

        SecondLevelPshBaseInfoFragment infoFragment = SecondLevelPshBaseInfoFragment.newInstance(bundle);
        SecondPshTwoFragment secondPshTwoFragment = SecondPshTwoFragment.newInstance(bundle);
        fragments.add(infoFragment);
        fragments.add(secondPshTwoFragment);
        showFragment(fragments.get(0));

        ((SecondLevelPshBaseInfoFragment) fragments.get(0)).setViewCreatedListener(new SecondLevelPshBaseInfoFragment.onViewCreatedListener() {
            @Override
            public void onViewCreated() {
                initFrag0 = true;
                if (EDIT_MODE) {
                    ((SecondLevelPshBaseInfoFragment) fragments.get(0)).setEditMode(true);
                }
            }
        });

        ((SecondPshTwoFragment) fragments.get(1)).setViewCreatedListener(new SecondPshTwoFragment.onViewCreatedListener() {
            @Override
            public void onViewCreated() {
                initFrag = true;
                if (EDIT_MODE) {
                    ((SecondPshTwoFragment) fragments.get(1)).setEditMode(true);
                }
            }
        });
    }

    private void setValue() {
        if(secondPshInfo!=null){
            SecondLevelPshInfoManger.getInstance().setEjaddr(secondPshInfo.getEjaddr());
            SecondLevelPshInfoManger.getInstance().setEjname(secondPshInfo.getEjname());
            SecondLevelPshInfoManger.getInstance().setPshtype1(secondPshInfo.getPshtype1());
            SecondLevelPshInfoManger.getInstance().setPshtype2(secondPshInfo.getPshtype2());
            SecondLevelPshInfoManger.getInstance().setPshtype3(secondPshInfo.getPshtype3());
            SecondLevelPshInfoManger.getInstance().setYjmenpai(secondPshInfo.getYjmenpai());
            SecondLevelPshInfoManger.getInstance().setYjname(secondPshInfo.getYjname());
            SecondLevelPshInfoManger.getInstance().setUnitId(secondPshInfo.getUnitId());
            SecondLevelPshInfoManger.getInstance().setYjname_id(secondPshInfo.getYjname_id());
        }
    }

    private void uploadSecondPsh(SecondLevelPshInfo.SecondPshInfo data1) {
        if(progressDialog == null) progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在提交中...");
        progressDialog.show();

        if(secondPshInfo!=null){
            data1.setId(secondPshInfo.getId());
        }else{
            data1.setYjname_id(unitListBean.getId()+"");
        }
        if (check(data1)) {
            pshService.addSecondPsh(data1,secondPshInfo)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            ToastUtil.shortToast(SecondLevelPshDetailActivity.this, "提交失败");
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (responseBody != null && responseBody.getCode() == 200) {
                                ToastUtil.shortToast(SecondLevelPshDetailActivity.this, "提交成功");
                                EventBus.getDefault().post(new SecondPshRefreshEvent());
                                SecondLevelPshDetailActivity.this.finish();
                            } else {
                                ToastUtil.shortToast(SecondLevelPshDetailActivity.this, "提交失败");
                            }
                        }
                    });
        }else{
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }


    private boolean check(SecondLevelPshInfo.SecondPshInfo data1) {
//        if(TextUtils.isEmpty(data1.getPshtype1())){
//            ToastUtil.shortToast(this,"排污大类不能为空");
//            return false;
//        }
//        if(TextUtils.isEmpty(data1.getPshtype2())){
//            ToastUtil.shortToast(this,"排污小类不能为空");
//            return false;
//        }
        if(TextUtils.isEmpty(data1.getPshtype3())){
            ToastUtil.shortToast(this,"行业类别不能为空");
            return false;
        }

        if(TextUtils.isEmpty(data1.getEjaddr())){
            ToastUtil.shortToast(this,"二级排水户地址不能为空");
            return false;
        }
        if(TextUtils.isEmpty(data1.getEjname())){
            ToastUtil.shortToast(this,"二级排水户名称不能为空");
            return false;
        }

        if ("其他".equals(data1.getPshtype3()) || "其他：".equals(data1.getPshtype3()) && "".equals(((SecondPshTwoFragment) fragments.get(1)).getEditOther().getText().toString().trim())) {
            ToastUtil.shortToast(this, "行业类别-其他不能为空");
            return false;
        }
//        if(TextUtils.isEmpty(data1.getPshtype1())){
//            ToastUtil.shortToast(this,"排污大类不能为空");
//            return false;
//        }
//        if(TextUtils.isEmpty(data1.getPshtype1())){
//            ToastUtil.shortToast(this,"排污大类不能为空");
//            return false;
//        }  if(TextUtils.isEmpty(data1.getPshtype1())){
//            ToastUtil.shortToast(this,"排污大类不能为空");
//            return false;
//        }
        return true;

    }

    private void delete() {
        if (secondPshInfo != null) {
            pshService.toDeleteCollect(secondPshInfo.getId())
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.shortToast(SecondLevelPshDetailActivity.this, "删除失败");
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            if (responseBody != null && responseBody.getCode() == 200) {
                                ToastUtil.shortToast(SecondLevelPshDetailActivity.this, "删除成功");
                                EventBus.getDefault().post(new SecondPshRefreshEvent());
                                SecondLevelPshDetailActivity.this.finish();
                            } else {
                                ToastUtil.shortToast(SecondLevelPshDetailActivity.this, "删除失败");
                            }
                        }
                    });
        }

    }

    private void setMode(int mode) {
        if(mode == 1){
            //新增
            btn_commit.setVisibility(View.VISIBLE);
       }else if(mode == 2){
            //编辑
            btn_commit.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.GONE);
            EDIT_MODE = true;
        }else{
            //正常模式
            btn_edit.setVisibility(View.VISIBLE);
            btn_commit.setVisibility(View.GONE);
        }
    }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fg_seweragetable_one:
                setCurChooseTab(0);
                break;
            case R.id.ll_fg_seweragetable_two:
                setCurChooseTab(1);
                break;
            case R.id.ll_back:
                backHelpTip();
                break;
            case R.id.btn_edit:
                setMode(2);
                if (initFrag0) {
                    ((SecondLevelPshBaseInfoFragment) fragments.get(0)).setEditMode(true);
                }
                if(initFrag){
                    ((SecondPshTwoFragment) fragments.get(1)).setEditMode(true);
                }
                break;
        }
    }

    private void backHelpTip() {
        if(btn_edit.getVisibility() == View.GONE){
            DialogUtil.MessageBox(SecondLevelPshDetailActivity.this, "提醒", "是否取消"+(secondPshInfo==null?"新增":"修改")+"？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SecondLevelPshDetailActivity.this.finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
        }else{
            SecondLevelPshDetailActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        backHelpTip();
    }
    private void setCurChooseTab(int tabIndex) {
        SetColorUtil.setBgColor(SecondLevelPshDetailActivity.this, tabIndex,
                ll_fg_seweragetable_one, ll_fg_seweragetable_two);
        curIndex = tabIndex;
        showFragment(fragments.get(tabIndex));

    }
}
