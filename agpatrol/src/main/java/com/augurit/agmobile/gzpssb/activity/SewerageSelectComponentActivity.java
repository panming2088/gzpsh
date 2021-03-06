package com.augurit.agmobile.gzpssb.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.fragment.SewerageSelectComponentFragment;
import com.augurit.agmobile.patrolcore.selectlocation.view.IDrawerController;
import com.esri.core.geometry.Geometry;

/**
 * 选择部件或者位置界面
 *
 * @author 创建人 ：luobiao
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps
 * @createTime 创建时间 ：18/4/10
 * @modifyBy 修改人 ：luobiao
 * @modifyTime 修改时间 ：18/4/10
 * @modifyMemo 修改备注：
 */

public class SewerageSelectComponentActivity extends BaseActivity implements IDrawerController {
    private DrawerLayout drawer_layout;
    private ViewGroup progress_linearlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcomponent);


        Geometry geometry = (Geometry) getIntent().getSerializableExtra("geometry");
        double initScale = getIntent().getDoubleExtra("initScale", 0);

//        SelectModifiedComponentFragment selectLocationFragment = SelectModifiedComponentFragment
//                .newInstance(geometry, initScale);
        Bundle bundle = new Bundle();
        bundle.putSerializable("geometry",geometry);
        bundle.putDouble("initScale",initScale);
        SewerageSelectComponentFragment selectLocationFragment = SewerageSelectComponentFragment.getInstance(bundle);
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(com.augurit.agmobile.patrolcore.R.id.ly_content, selectLocationFragment);
//        fragmentTransaction.commit();

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(com.augurit.agmobile.patrolcore.R.id.ly_content, selectLocationFragment);
        fragmentTransaction.commit();
        progress_linearlayout = (ViewGroup) findViewById(com.augurit.agmobile.patrolcore.R.id.progress_linearlayout);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //设置右面的侧滑菜单只能通过编程来打开
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                GravityCompat.END);
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

    }

    @Override
    public ViewGroup getDrawerContainer() {
        return progress_linearlayout;
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionsUtil2.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/
}
