package com.augurit.agmobile.gzpssb.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.bean.SewerageRoomClickItemBean;
import com.augurit.agmobile.gzpssb.event.RefreshDataListEvent;

import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.utils.SetColorUtil;

import java.util.ArrayList;


/**
 * Created by xiaoyu on 2018/4/3.
 */

public class SewerageCompanyRoomClickFragment extends MyBaseFragment  {
    private FgSewerageBinding fgSewerageData;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;
//    private SewerageRoomClickPersenter sewerageRoomClickPersenter = new SewerageRoomClickPersenter(this);

    public static SewerageCompanyRoomClickFragment newInstance() {
        SewerageCompanyRoomClickFragment sewerageCompanyRoomClickFragment = new SewerageCompanyRoomClickFragment();
        return sewerageCompanyRoomClickFragment;
    }


    @Override
    public int initview() {
        return R.layout.fg_sewerage;
    }

    @Override
    protected void initData() {
        super.initData();
//        EventBus.getDefault().register(this);
        fgSewerageData = getBind();
        fragmentManager = getChildFragmentManager();
        initFragment();
//        fgSewerageData.setFgsewerageonclic(new SewerageCompanyFragmentClick());
        fgSewerageData.llFgSewerageAll.setOnClickListener(new SewerageCompanyFragmentClick());
        fgSewerageData.llFgSewerageInvestigated.setOnClickListener(new SewerageCompanyFragmentClick());
        fgSewerageData.llFgSewerageUninvestigated.setOnClickListener(new SewerageCompanyFragmentClick());
        fgSewerageData.btnAdd.setOnClickListener(new SewerageCompanyFragmentClick());
//        sewerageRoomClickPersenter.getdata(MyApplication.ID, 1, 10, 0, 0);
    }
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        EventBus.getDefault().unregister(this);
//    }
//    @Subscribe
//    public void onEventMainThread(RefreshDataListEvent event) {
//        if (event.getUpdateState()== RefreshDataListEvent.UPDATE_ROOM_UNIT_LIST1){
//            showFragment(fragments.get(1));//TODO 更新列表
//            sewerageRoomClickPersenter.getdata(MyApplication.ID, 1, 10, 0, 0);
//        }
//    }
    /**
     * 初始化所有基fragment
     */
    private void initFragment() {
//        EventBus.getDefault().register(this);
        fragments = new ArrayList<Fragment>();
        fragments.add(SewerageTotalRoomClickFragment.newInstance());
        fragments.add(SewerageInvesttigatedRoomClickFragment.newInstance());
        fragments.add(SewerageUnInvesttigatedRoomClickFragment.newInstance());
        showFragment(fragments.get(0));

//        EventBus.getDefault().post(new RefreshTotalUnitEvent(3));

    }

    /**
     * 显示fragment
     *
     * @param fragment 要显示的fragment
     */
    private void showFragment(Fragment fragment) {
        MyApplication.refreshListType = 0;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        if (!fragment.isAdded() && null == fragmentManager.findFragmentByTag(fragment.getClass().getName())) {
            transaction.add(R.id.ll_container, fragment, fragment.getClass().getName());
        } else {
            transaction.show(fragment);

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

    public void setViewData(SewerageRoomClickItemBean sewerageRoomClickList) {
        MyApplication.SEWERAGEROOMCLICKITEMBEAN =  sewerageRoomClickList;
        fgSewerageData.allInstallCount.setText(sewerageRoomClickList.getUnit_total()+"");
        fgSewerageData.installCount.setText(sewerageRoomClickList.getUnit_survey()+"");
        fgSewerageData.noInstallCount.setText(sewerageRoomClickList.getUnit_no_survey()+"");
    }

//    @Override
//    public void setSewerageRoomClickList(SewerageRoomClickItemBean sewerageRoomClickList) {
//
//        MyApplication.SEWERAGEROOMCLICKITEMBEAN =  sewerageRoomClickList;
//        fgSewerageData.allInstallCount.setText(sewerageRoomClickList.getUnit_total()+"");
//        fgSewerageData.installCount.setText(sewerageRoomClickList.getUnit_survey()+"");
//        fgSewerageData.noInstallCount.setText(sewerageRoomClickList.getUnit_no_survey()+"");
//
////        ((SewerageTotalRoomClickFragment)fragments.get(0)).getRoomClickdata(1);
////        ((SewerageTotalRoomClickFragment)fragments.get(0)).getRoomClickdata(1);
////        ((SewerageTotalRoomClickFragment)fragments.get(0)).getRoomClickdata(1);
//    }

    /**
     * tab标签点击
     */
    private class SewerageCompanyFragmentClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_fg_sewerage_all:
                    SetColorUtil.setBgColor(getActivity(),0,
                            fgSewerageData.llFgSewerageAll,fgSewerageData.llFgSewerageInvestigated,
                            fgSewerageData.llFgSewerageUninvestigated
                    );
                    showFragment(fragments.get(0));
                    break;
                case R.id.ll_fg_sewerage_investigated:
                    SetColorUtil.setBgColor(getActivity(),1,
                            fgSewerageData.llFgSewerageAll,fgSewerageData.llFgSewerageInvestigated,
                            fgSewerageData.llFgSewerageUninvestigated
                    );
                    showFragment(fragments.get(1));
                    break;
                case R.id.ll_fg_sewerage_uninvestigated:
                    SetColorUtil.setBgColor(getActivity(),2,
                            fgSewerageData.llFgSewerageAll,fgSewerageData.llFgSewerageInvestigated,
                            fgSewerageData.llFgSewerageUninvestigated
                    );
                    showFragment(fragments.get(2));
                    break;
                case R.id.btn_add:
                    MyApplication.refreshListType = RefreshDataListEvent.UPDATE_ROOM_UNIT_LIST1;
                    Intent intent = new Intent( view.getContext(), SewerageTableActivity.class);
//                    SewerageBeanManger.getInstance().setHouseIdFlag("1");
//                    SewerageBeanManger.getInstance().setHouseId(MyApplication.ID);
//                    SewerageBeanManger.getInstance().setUnitId("");
                    intent.putExtra("HouseIdFlag","1");
                    intent.putExtra("HouseId",MyApplication.ID);
                    intent.putExtra("UnitId","");

//                    ((Activity)view.getContext()).startActivityForResult(intent,100);
                    view.getContext().startActivity(intent);
                    break;
//                case R.id.ll_fg_sewerage_newadd:
//                    SetColorUtil.setBgColor(getActivity(),3,
//                            fgSewerageData.llFgSewerageAll,fgSewerageData.llFgSewerageInvestigated,
//                            fgSewerageData.llFgSewerageUninvestigated,fgSewerageData.llFgSewerageNewadd
//                    );
//                    userInfo.clear();
//                    setSewerageData("新增");
//                    sewerageCompanyAdapter.notifyDataSetChanged();
//                    break;
            }
        }
    }


}
