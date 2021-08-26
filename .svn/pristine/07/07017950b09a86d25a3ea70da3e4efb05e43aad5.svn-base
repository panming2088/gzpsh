package com.augurit.agmobile.gzpssb.uploadevent.view.uploadevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.journal.view.DialyPatrolMapFragment;
import com.augurit.agmobile.patrolcore.selectlocation.view.IDrawerController;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.esri.core.geometry.Geometry;

/**
 * 工作台的数据上报和主界面的数据上报功能
 * (数据即为各种设施)
 *
 * @author 创建人 ：liangshenghong
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.map
 * @createTime 创建时间 ：2017-10-14
 * @modifyBy 修改人 ：liangshenghong
 * @modifyTime 修改时间 ：2017-10-14
 * @modifyMemo 修改备注：
 */

public class EventSelectMapActivity extends BaseActivity implements IDrawerController {
    protected ProgressLinearLayout progress_linearlayout;
    protected DrawerLayout drawer_layout;

    private EventSelectMapFragment mQueryAddressMapFragment;

    private String componentName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadmap);
        String title = getIntent().getStringExtra("title");
        Geometry geometry = (Geometry) getIntent().getSerializableExtra("geometry");
        int selectType = getIntent().getIntExtra("selectType", 0);
        progress_linearlayout = (ProgressLinearLayout) findViewById(R.id.progress_linearlayout);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //设置右面的侧滑菜单只能通过编程来打开
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                GravityCompat.END);

        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(title)) {
            bundle.putString("title", title);
        }
        bundle.putSerializable("geometry", geometry);
        bundle.putInt("selectType", selectType);
        mQueryAddressMapFragment = EventSelectMapFragment.getInstance(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(com.augurit.agmobile.patrolcore.R.id.ly_content, mQueryAddressMapFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void openDrawer(final OnDrawerOpenListener listener) {
        drawer_layout.openDrawer(Gravity.RIGHT);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                GravityCompat.END);    //解除锁定
        drawer_layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
//                progress_linearlayout.showLoading();
                if (listener != null) {
                    listener.onOpened(drawerView);
                }
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
    public void closeDrawer() {
        drawer_layout.closeDrawer(Gravity.RIGHT);
    }

    @Override
    public ViewGroup getDrawerContainer() {
        return progress_linearlayout;
    }


    /**
     * 主要用于拍照、打开照片、地图浏览等返回Activity的刷新操作
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (!mQueryAddressMapFragment.isHidden()) {
            mQueryAddressMapFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (!mQueryAddressMapFragment.isHidden()) {
            mQueryAddressMapFragment.onBackPressed();
        } else {
            finish();
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionsUtil2.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/
}
