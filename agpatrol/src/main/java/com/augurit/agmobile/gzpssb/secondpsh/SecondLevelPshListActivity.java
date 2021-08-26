package com.augurit.agmobile.gzpssb.secondpsh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.UploadFacilitySumEvent;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.uploadfacility.view.condiction.UploadFilterConditionEvent;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;

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

public class SecondLevelPshListActivity extends BaseActivity {

    private TextView all_count, install_count, no_install_count;
    private DrawerLayout drawer_layout;
    private SecondLevelPshListFragment secondLevelPshListFragment;
    protected ProgressLinearLayout progress_linearlayout;
    protected boolean isIndustry;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewerage_secondlist);
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {

        SewerageItemBean.UnitListBean unitListBean = (SewerageItemBean.UnitListBean) getIntent().getSerializableExtra("unitListBean");
        ((TextView) findViewById(R.id.tv_title)).setText(unitListBean.getName());
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        findViewById(R.id.ll_search).setVisibility(View.VISIBLE);
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
//        new FacilityListFilterConditionView(this, "校核列表筛选", FacilityFilterCondition.MODIFIED_LIST, llModifiedFilterCondition);

        final ViewGroup llUploadFilterCondition = (ViewGroup) findViewById(R.id.ll_upload_filter_condition);
        final ViewGroup llPSHCondition = (ViewGroup) findViewById(R.id.ll_psh_event_condition);
//        new FacilityListFilterConditionView(this, "新增列表筛选", FacilityFilterCondition.NEW_ADDED_LIST, llUploadFilterCondition);

        EJPSHUploadConditionView ejpshUploadConditionView = new EJPSHUploadConditionView(llPSHCondition, this);

        secondLevelPshListFragment = new SecondLevelPshListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(com.augurit.agmobile.patrolcore.R.id.ly_content, secondLevelPshListFragment);
        fragmentTransaction.commit();
        llModifiedFilterCondition.setVisibility(View.GONE);
        llUploadFilterCondition.setVisibility(View.GONE);
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


    @Subscribe
    public void onPSHEventConditionChanged(UploadFilterConditionEvent eventAffairConditionEvent) {
        drawer_layout.closeDrawers();
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
            super.onBackPressed();
        }
    }

    /**
     * 收到校核筛选条件中的『确定』按钮点击事件，事件发出在：{@link com.augurit.agmobile.gzps.setting.view.myupload.MyCheckedFacilityListFragment}
     *
     * @param eventAffairConditionEvent
     */
    @Subscribe
    public void onEventConditionChanged(EJPSHUploadFilterConditionEvent eventAffairConditionEvent) {
        drawer_layout.closeDrawers();
    }
}
