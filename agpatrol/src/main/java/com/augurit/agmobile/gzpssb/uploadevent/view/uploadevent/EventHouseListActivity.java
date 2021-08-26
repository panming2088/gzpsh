package com.augurit.agmobile.gzpssb.uploadevent.view.uploadevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.journal.view.PSHHouseListFragment;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.agmobile.patrolcore.selectlocation.view.IDrawerController;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.esri.core.geometry.Point;

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

public class EventHouseListActivity extends BaseActivity implements IDrawerController {
    protected ProgressLinearLayout progress_linearlayout;
    protected DrawerLayout drawer_layout;

    private EventHouseListFragment mQueryAddressMapFragment;

    private String componentName;
    private DetailAddress mDetailAddress;
    private Bundle bundle;
    private Point mPoint;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psh_house);
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((TextView)findViewById(R.id.tv_title)).setText("排水户列表");
        /*findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ((TextView)findViewById(R.id.tv_title)).setText("数据上报");


        findViewById(R.id.ll_search).setVisibility(View.VISIBLE);
        ImageView iv_open_search = (ImageView) findViewById(R.id.iv_open_search);
        iv_open_search.setImageResource(R.mipmap.ic_list);
        ((TextView)findViewById(R.id.tv_search)).setText("上报列表");
        findViewById(R.id.tv_search).setVisibility(View.VISIBLE);

        findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(PSHUploadFacilityMapActivity.this,DialyPatrolRecordListActivity.class);
                startActivity(intent);
            }
        });*/

        progress_linearlayout = (ProgressLinearLayout) findViewById(R.id.progress_linearlayout);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //设置右面的侧滑菜单只能通过编程来打开
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                GravityCompat.END);

        mDetailAddress = getIntent().getParcelableExtra("detailAddress");
        mPoint = (Point) getIntent().getSerializableExtra("point");
        bundle = new Bundle();
        if(mDetailAddress != null) {
            bundle.putParcelable("detailAddress", mDetailAddress);
        }
        bundle.putSerializable("point", mPoint);
        mQueryAddressMapFragment = EventHouseListFragment.newInstance(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(com.augurit.agmobile.patrolcore.R.id.ly_content, mQueryAddressMapFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume(){
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
                if (listener != null){
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
        if(!mQueryAddressMapFragment.isHidden()) {
            mQueryAddressMapFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionsUtil2.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/
}
