package com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.uploadevent.event.UpdateAfterDeleteEvent;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityListFilterConditionView;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.UploadFacilitySumEvent;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.bean.SewerageRoomClickItemBean;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.uploadevent.adapter.PSHEventAffairPagerAdapter;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.model.SharedEvent;
import com.augurit.agmobile.gzpssb.uploadevent.service.PSHUploadEventService;
import com.augurit.agmobile.patrolcore.common.util.MaxLengthInputFilter;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.widget.searchview.util.adapter.TextWatcherAdapter;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.statistic
 * @createTime 创建时间 ：2017/8/15
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2017/8/15
 * @modifyMemo 修改备注：
 */

public class PshEventAffairDetailActivity extends BaseActivity {

    private TextView all_count, install_count, no_install_count;
    private DrawerLayout drawer_layout;
    private PSHEventAffairPagerAdapter adapter;
    private PSHAffairDetail pshAffairDetail;
    private ModifiedFacility modifiedFacility;
    private UploadedFacility uploadedFacility;
    private SewerageItemBean.UnitListBean unitListBeans;
    private SewerageRoomClickItemBean.UnitListBean roomclickunitListBeans;
    private boolean isDialy;
    private boolean isList;
    private boolean isCancel;
    private boolean fromMyUpload;
    private boolean fromPSHAffair;
    //是否是暂存状态，4即是暂存
    private boolean isTempStorage;
    private boolean isAddNewDoorNo;
    private boolean isfromQuryAddressMapFragmnet;
    private String isExist;
    private View delete;
    private boolean isFromPSHAffair = false;
    private boolean isEjPsh = false;
    private PSHUploadEventService mEventService;
    private PSHEventListItem mEventAffairBean;
    private TextFieldTableItem mFieldTableItem;
    private EditText et_content;
    private View btn_cancel;
    private View btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploadstats);
        mEventService = new PSHUploadEventService(this);
        Object psh = getIntent().getSerializableExtra("pshAffair");
        if (psh != null) {
            pshAffairDetail = (PSHAffairDetail) psh;
        }

        Object modify = getIntent().getParcelableExtra("modifiedFacility");
        if (modify != null) {
            modifiedFacility = (ModifiedFacility) modify;
        }

        Object upload = getIntent().getParcelableExtra("uploadedFacility");
        if (upload != null) {
            uploadedFacility = (UploadedFacility) upload;
        }

        isFromPSHAffair = getIntent().getBooleanExtra("fromPSHAffair", false);
        isTempStorage = getIntent().getBooleanExtra("isTempStorage", false);
        isEjPsh = getIntent().getBooleanExtra("isEjPsh", false);
        mEventAffairBean = (PSHEventListItem) getIntent().getSerializableExtra("eventAffairBean");
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText("问题上报信息");
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        all_count = (TextView) findViewById(R.id.all_install_count);
        delete = findViewById(R.id.delete);
        install_count = (TextView) findViewById(R.id.install_count);
        no_install_count = (TextView) findViewById(R.id.no_install_count);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //设置右面的侧滑菜单只能通过编程来打开
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                GravityCompat.END);


        //todo 暂时屏蔽微信分享
        findViewById(R.id.ll_search).setVisibility(View.GONE);
        ImageView iv_open_search = ((ImageView) findViewById(R.id.iv_open_search));
        iv_open_search.setVisibility(View.GONE);
        iv_open_search.setImageResource(R.mipmap.ic_filter);
        ((TextView) findViewById(R.id.tv_search)).setText("分享至微信");
        findViewById(R.id.tv_search).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new SharedEvent());
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogView();

            }
        });

        final ViewGroup llModifiedFilterCondition = (ViewGroup) findViewById(R.id.ll_modified_filter_condition);
        new FacilityListFilterConditionView(this, "校核列表筛选", FacilityFilterCondition.MODIFIED_LIST, llModifiedFilterCondition);

        final ViewGroup llUploadFilterCondition = (ViewGroup) findViewById(R.id.ll_upload_filter_condition);
        new FacilityListFilterConditionView(this, "新增列表筛选", FacilityFilterCondition.NEW_ADDED_LIST, llUploadFilterCondition);

        Bundle bundle = new Bundle();
        if (pshAffairDetail != null) {
            bundle.putSerializable("pshAffair", pshAffairDetail);
        }
        if (modifiedFacility != null) {
            bundle.putParcelable("modifiedFacility", modifiedFacility);
        }
        if (uploadedFacility != null) {
            bundle.putParcelable("uploadedFacility", uploadedFacility);
        }
        bundle.putBoolean("fromPSHAffair", isFromPSHAffair);
        bundle.putBoolean("fromMyUpload", true);
        bundle.putBoolean("isDialy", true);
        bundle.putBoolean("isTempStorage", isTempStorage);
        bundle.putBoolean("isEjPsh", isEjPsh);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        adapter = new PSHEventAffairPagerAdapter(getSupportFragmentManager(),
                this, bundle);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    llModifiedFilterCondition.setVisibility(View.GONE);
                    llUploadFilterCondition.setVisibility(View.GONE);
                } else {
                    llModifiedFilterCondition.setVisibility(View.GONE);
                    llUploadFilterCondition.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(adapter);

        //TabLayout
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        if ((pshAffairDetail != null && pshAffairDetail.getData() == null)
                || TextUtils.isEmpty(mEventAffairBean.getSslx())
                || adapter.getCount() == 1) {
            tabLayout.setVisibility(View.GONE);
            ((TextView) findViewById(R.id.tv_title)).setText("问题详情");
        }
    }

    /**
     * 创建填写删除原因窗口
     *
     * @return
     */
    private View createDialogView() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog dialog = alertDialogBuilder.create();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = View.inflate(this, R.layout.dialog_delete_event_reason, null);
        final TextView tv_size = (TextView) view.findViewById(R.id.tv_size);
        et_content = (EditText) view.findViewById(R.id.textfield_content);
        final int maxTotal = 100;
        et_content.setFilters(new InputFilter[]{new MaxLengthInputFilter(maxTotal,
                null, et_content, "长度不能超过" + maxTotal + "个字").setDismissErrorDelay(1500)});
        et_content.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                try {
                    String inputText = s.toString();
                    if (TextUtils.isEmpty(inputText)) {
                        tv_size.setText("0/" + maxTotal);
                        return;
                    }
                    tv_size.setText(inputText.getBytes("GB2312").length / 2 + "/" + maxTotal);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_submit = view.findViewById(R.id.btn_submit);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String advice = et_content.getText().toString();
                if (advice.isEmpty() || advice.trim().isEmpty()) {
                    ToastUtil.longToast(PshEventAffairDetailActivity.this, "请填写删除原因!");
                    return;
                }
                deleteEvent();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
        return view;
    }

    private void deleteEvent() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("删除中.....");
        progressDialog.show();
        mEventService.deleteEvent(mEventAffairBean.getId(), et_content.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result2<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        ToastUtil.iconShortToast(PshEventAffairDetailActivity.this, R.mipmap.ic_alert_yellow, "删除失败，请稍后重试");
                    }

                    @Override
                    public void onNext(Result2<String> s) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (s.getCode() == 200) {
                            //清空界面
                            ToastUtil.shortToast(PshEventAffairDetailActivity.this, "删除成功");
                            EventBus.getDefault().post(new UpdateAfterDeleteEvent(null));
                            finish();
                        } else {
                            CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
                                    BaseInfoManager.getUserName(PshEventAffairDetailActivity.this) + "原因是：" + s.getMessage()));
                            Toast.makeText(PshEventAffairDetailActivity.this, s.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    public void openDrawer() {
        drawer_layout.openDrawer(Gravity.RIGHT);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                GravityCompat.END);    //解除锁定
        drawer_layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //设置右面的侧滑菜单只能通过编程来打开
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                        GravityCompat.END);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventUploadFacilitySum(UploadFacilitySumEvent uploadFacilitySumEvent) {
        ((TextView) findViewById(R.id.tv_title)).setText("我的数据上报（" + getStringFromDouble(Double.valueOf(uploadFacilitySumEvent.getSum())) + "条）");
    }

    private String getStringFromDouble(double n) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(n);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventRefreshDel(DeleteEvent uploadFacilitySumEvent) {
//        delete.setVisibility(View.VISIBLE); //todo 暂时不要了
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 收到校核筛选条件中的『确定』按钮点击事件，事件发出在：{@link com.augurit.agmobile.gzps.setting.view.myupload.MyCheckedFacilityListFragment}
     *
     * @param eventAffairConditionEvent
     */
    @Subscribe
    public void onEventConditionChanged(FacilityFilterCondition eventAffairConditionEvent) {
        drawer_layout.closeDrawers();
    }

//    /**
//     * 收到新增筛选条件中的『确定』按钮点击事件，事件发出在：{@link com.augurit.agmobile.gzps.setting.view.myupload.MyAddFacilityListFragment}
//     *
//     * @param eventAffairConditionEvent
//     */
//    @Subscribe
//    public void onEventConditionChanged(UploadedFacilityFilterCondition eventAffairConditionEvent) {
//        drawer_layout.closeDrawers();
//    }
}
