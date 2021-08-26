package com.augurit.agmobile.gzpssb.journal.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.event.PhotoEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityListFilterConditionView;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.UploadFacilitySumEvent;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.bean.SewerageRoomClickItemBean;
import com.augurit.agmobile.gzpssb.journal.model.ReBack;
import com.augurit.agmobile.gzpssb.journal.view.adapter.DialyPatrolRecordPagerAdapter;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;


/**
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.statistic
 * @createTime 创建时间 ：2017/8/15
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2017/8/15
 * @modifyMemo 修改备注：
 */

public class DialyPatrolRecordListActivity extends BaseActivity {

    private TextView all_count, install_count, no_install_count;
    private DrawerLayout drawer_layout;
    private DialyPatrolRecordPagerAdapter adapter;
    private PSHAffairDetail pshAffairDetail;
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
    private boolean isReEdit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploadstats);
        initArgument();
        initView();
        EventBus.getDefault().register(this);
    }

    private void initArgument() {
        pshAffairDetail = (PSHAffairDetail) getIntent().getSerializableExtra("pshAffair");
        fromPSHAffair = getIntent().getBooleanExtra("fromPSHAffair", false);
        fromMyUpload = getIntent().getBooleanExtra("fromMyUpload", false);
        isTempStorage = getIntent().getBooleanExtra("isTempStorage", false);
        isDialy = getIntent().getBooleanExtra("isDialy", false);
        isList = getIntent().getBooleanExtra("isList", false);
        isCancel = getIntent().getBooleanExtra("isCancel", false);
        isReEdit = getIntent().getBooleanExtra("isReEdit", false);
        isfromQuryAddressMapFragmnet = getIntent().getBooleanExtra("isfromQuryAddressMapFragmnet", false);
        isExist = getIntent().getStringExtra("isExist");
        unitListBeans = (SewerageItemBean.UnitListBean) getIntent().getSerializableExtra("unitListBeans");
        /**
         * 第二层单位点击进来的
         */
        roomclickunitListBeans = (SewerageRoomClickItemBean.UnitListBean) getIntent().getSerializableExtra("RoomclickunitListBeans");

    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText("巡检信息");
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new ReBack());
//                finish();
            }
        });
        all_count = (TextView) findViewById(R.id.all_install_count);
        install_count = (TextView) findViewById(R.id.install_count);
        no_install_count = (TextView) findViewById(R.id.no_install_count);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //设置右面的侧滑菜单只能通过编程来打开
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                GravityCompat.END);


        //筛选条件
        findViewById(R.id.ll_search).setVisibility(View.GONE);
        ImageView iv_open_search = ((ImageView) findViewById(R.id.iv_open_search));
        iv_open_search.setImageResource(R.mipmap.ic_filter);
        ((TextView) findViewById(R.id.tv_search)).setText("筛选");
        findViewById(R.id.tv_search).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });

        final ViewGroup llModifiedFilterCondition = (ViewGroup) findViewById(R.id.ll_modified_filter_condition);
        new FacilityListFilterConditionView(this, "校核列表筛选", FacilityFilterCondition.MODIFIED_LIST, llModifiedFilterCondition);

        final ViewGroup llUploadFilterCondition = (ViewGroup) findViewById(R.id.ll_upload_filter_condition);
        new FacilityListFilterConditionView(this, "新增列表筛选", FacilityFilterCondition.NEW_ADDED_LIST, llUploadFilterCondition);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);

        Bundle bundle = new Bundle();
        bundle.putSerializable("pshAffair",pshAffairDetail);
        bundle.putBoolean("fromPSHAffair",true);
        bundle.putBoolean("fromMyUpload",true);
        bundle.putBoolean("isDialy",false);
        bundle.putBoolean("isTempStorage", isTempStorage);
        bundle.putBoolean("isReEdit", isReEdit);
        bundle.putBoolean("isCancel", isCancel);
        adapter = new DialyPatrolRecordPagerAdapter(getSupportFragmentManager(),
                this,bundle);
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
        final TabLayout tabLayout = (TabLayout) findViewById(com.augurit.agmobile.gzps.R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EventBus.getDefault().post(new PhotoEvent(requestCode, resultCode, data));//第一个是要传递的数据   第二个参数是标记
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

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawers();
        } else {
//            super.onBackPressed();
            EventBus.getDefault().post(new ReBack());
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
