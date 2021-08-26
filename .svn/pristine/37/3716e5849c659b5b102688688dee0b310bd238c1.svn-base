package com.augurit.agmobile.gzpssb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.adapter.SewerageCompanyAdapter;
import com.augurit.agmobile.gzpssb.bean.SewerageInvestBean;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.condition.SewerageItemSearchCondition;
import com.augurit.agmobile.gzpssb.event.BackPressEvent;
import com.augurit.agmobile.gzpssb.event.RefreshDataListEvent;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.okhttp.Iview.ISewerageItemView;
import com.augurit.agmobile.gzpssb.okhttp.Presenter.SewerageItemPresenter;
import com.augurit.agmobile.gzpssb.utils.SetColorUtil;
import com.augurit.agmobile.gzpssb.common.view.MyLinearLayoutManager;
import com.augurit.am.fw.utils.ListUtil;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;


/**
 * Created by xiaoyu on 2018/4/3.
 */

public class SewerageCompanyFragment extends MyBaseFragment implements ISewerageItemView, SewerageTotalFragment.GetTotalDataListener {
    private MyLinearLayoutManager linearLayoutManager;
    private com.augurit.agmobile.gzpssb.fragment.FgSewerageBinding fgSewerageData;
    private SewerageCompanyAdapter sewerageCompanyAdapter;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;
    private String name;
    private String id;
    private int page = 1;
    private String mName = "";
    private SewerageItemPresenter sewerageItemPresenter = new SewerageItemPresenter(this);
    private SewerageItemBean mSewerageItemBean;
    private String isExist;


    public final static String REFRESH_TOTAL_FRAGMENT = "1";
    public final static String REFRESH_INVESTIGATED_FRAGMENT = "2";
    public final static String REFRESH_UNINVESTIGATEDFRAGMENT = "3";
    private boolean isNull;
    private boolean isCreate = false;//防止因为多次创建执行多次执行刷新
    private boolean isNeedRefresh = true;//從排水戶上報界面，按下後退按鈕進來的
    public static boolean isSearch=false;
    // private GetName getName;
    public static SewerageCompanyFragment newInstance(Bundle bundle) {
        SewerageCompanyFragment sewerageCompanyFragment = new SewerageCompanyFragment();
        sewerageCompanyFragment.setArguments(bundle);
        return sewerageCompanyFragment;
    }

    private void setEntrance(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public int initview() {
        isCreate = true;
        return R.layout.fg_sewerage;
    }
    //    @Override
//    public void onDetach() {
//        super.onDetach();
//        EventBus.getDefault().unregister(this);
//    }

    //    @Subscribe
//    public void onEventMainThread(RefreshDataListEvent event) {
//        if (event.getUpdateState()== RefreshDataListEvent.UPDATE_MIAN_LIST){
//            showFragment(fragments.get(1));//TODO 更新列表
//        }
//    }
    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        fgSewerageData = getBind();
        fragmentManager = getChildFragmentManager();
        fgSewerageData.llFgSewerageAll.setOnClickListener(new SewerageCompanyFragmentClick());
        fgSewerageData.llFgSewerageInvestigated.setOnClickListener(new SewerageCompanyFragmentClick());
        fgSewerageData.llFgSewerageUninvestigated.setOnClickListener(new SewerageCompanyFragmentClick());
       // fgSewerageData.btnAdd.setOnClickListener(new SewerageCompanyFragmentClick());
        RxView.clicks(fgSewerageData.btnAdd)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        MyApplication.refreshListType = RefreshDataListEvent.UPDATE_MIAN_LIST;
                        Intent intent = new Intent(getContext(), SewerageTableActivity.class);
//                    SewerageBeanManger.getInstance().setHouseIdFlag("0");
//                    SewerageBeanManger.getInstance().setHouseId(ParamsInstance.getInstance().getBuildId());
//                    SewerageBeanManger.getInstance().setUnitId("");
                        intent.putExtra("isExist", isExist);
                        intent.putExtra("HouseIdFlag", "0");
                        intent.putExtra("HouseId", MyApplication.buildId);
                        intent.putExtra("UnitId", "");
                        intent.putExtra("isAllowSaveLocalDraft",true);
                        getContext().startActivity(intent);
                    }
                });

//        fgSewerageData.setFgsewerageonclic(new SewerageCompanyFragmentClick());
        // userInfo = new ArrayList<>();//初始化在set值之前
        //  setSewerageData("总数");

//        /**
//         * 加载adapter
//         */
//        linearLayoutManager = new MyLinearLayoutManager(getActivity());
//        fgSewerageData.rvFgSewerage.setLayoutManager(linearLayoutManager);
//        Map<Integer, Integer> map = new HashMap<>();
//        map.put(R.layout.item_fg_seweragercompany, BR.itemTest);
//        sewerageCompanyAdapter = new SewerageCompanyAdapter(userInfo, map);
//        fgSewerageData.rvFgSewerage.setAdapter(sewerageCompanyAdapter);
        isExist = getArguments().getString("isExist");

        initFragment();

        sewerageItemPresenter.setIGetFail(new SewerageItemPresenter.IGetFail() {
            @Override
            public void onFail(String code) {
                fgSewerageData.btnAdd.setVisibility(View.GONE);
                fgSewerageData.allInstallCount.setText("0");
                fgSewerageData.installCount.setText("0");
                fgSewerageData.noInstallCount.setText("0");
                //ToastUtil.shortToast(getContext(),code);
            }
        });


    }

    private void getdata(int page, String name, String add_type) {
        sewerageItemPresenter.getdata(MyApplication.GUID, MyApplication.DZDM,
                page, 10, 0, 2, name, add_type);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCreate&&!isSearch) {
//            if (!TextUtils.isEmpty(isExist) && "1".equals(isExist)) {
//                getdata(page, mName, "0");
//            } else {
//                getdata(page, mName, null);
//            }

            if(isNeedRefresh){
                //重新刷新其他fg数据
                ((SewerageTotalFragment) fragments.get(0)).getName(mName, isNull);
                //TODO 暂时屏蔽掉
//            ((SewerageInvestigatedFragment) fragments.get(1)).getName(mName, isNull);
//            ((SewerageUnInvestigatedFragment) fragments.get(2)).getName(mName, isNull);

            }else{
                isNeedRefresh = true;
            }
            isCreate = false;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        isCreate=true;
        isSearch=false;
    }

    /**
     * 初始化所有基fragment
     */
    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(isExist) && "1".equals(isExist)) {
            bundle.putString("add_type", "0");
        }
        fragments.add(SewerageTotalFragment.newInstance(bundle,this));
        //TODO 暂时屏蔽掉
        fragments.add(SewerageInvestigatedFragment.newInstance(bundle));
        fragments.add(SewerageUnInvestigatedFragment.newInstance(bundle));
        clearFragmentManager();
        showFragment(fragments.get(0));
    }

    /**
     * 清空所有fragment
     */
    private void clearFragmentManager() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragments) {
            Fragment fragment1 = fragmentManager.findFragmentByTag(fragment.getClass().getName());
            if (fragment1 != null) {
                transaction.remove(fragment1);
            }
        }
        transaction.commit();
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

    @Override
    public void setSewerageItemList(SewerageItemBean sewerageItemList) {
        fgSewerageData.btnAdd.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(mName)){
            MyApplication.SEWERAGEITEMBEAN = sewerageItemList;
        }
        mSewerageItemBean = sewerageItemList;
        if (ListUtil.isEmpty(sewerageItemList.getUnit_list())) {
            fgSewerageData.allInstallCount.setText("0");
            fgSewerageData.installCount.setText("0");
            fgSewerageData.noInstallCount.setText("0");
        } else {
            fgSewerageData.allInstallCount.setText(sewerageItemList.getUnit_total() + "");
            fgSewerageData.installCount.setText(sewerageItemList.getUnit_survey() + "");
            fgSewerageData.noInstallCount.setText(sewerageItemList.getUnit_no_survey() + "");
        }

    }


    @Override
    public void onInvestCode(SewerageInvestBean bean) {

    }

    @Subscribe
    public void onReceivedSewerageItemSearchCondition(SewerageItemSearchCondition sewerageItemSearchCondition) {
//        page = 1;
        mName = sewerageItemSearchCondition.getCommpanyName();
        isNull = sewerageItemSearchCondition.isNull();
//        if (!TextUtils.isEmpty(isExist) && "1".equals(isExist)) {
//            getdata(page, mName, "0");
//        } else {
//            getdata(page, mName, null);
//        }
        ((SewerageTotalFragment) fragments.get(0)).getName(mName, isNull);
        //暫時屏蔽掉 TODO
        ((SewerageInvestigatedFragment) fragments.get(1)).getName(mName, isNull);
        ((SewerageUnInvestigatedFragment) fragments.get(2)).getName(mName, isNull);
        isSearch=true;
    }
    @Subscribe
    public void onReceiveRefresh(Bundle bundle){
        if("1".equals(bundle.get("refresh"))){
//            if (!TextUtils.isEmpty(isExist) && "1".equals(isExist)) {
//                getdata(page, mName, "0");
//            } else {
//                getdata(page, mName, null);
//            }
            ((SewerageTotalFragment) fragments.get(0)).getName(mName, isNull);
            ((SewerageInvestigatedFragment) fragments.get(1)).getName(mName, isNull);
            ((SewerageUnInvestigatedFragment) fragments.get(2)).getName(mName, isNull);
            isNeedRefresh = false;
        }else{
            isNeedRefresh = false;
        }
    }

    @Override
    public void onLoadStart() {

    }


    @Override
    public void onCompelete() {

    }



    @Override
    public void getTotalDataSuccess(Bundle bundle) {
        if(bundle!=null){
    boolean isHasData = bundle.getBoolean("hasData");
            fgSewerageData.btnAdd.setVisibility(View.VISIBLE);
            if(isHasData){
    fgSewerageData.allInstallCount.setText(bundle.getInt("getUnit_total",0)+"");
    fgSewerageData.installCount.setText(bundle.getInt("getUnit_survey",0)+"");
    fgSewerageData.noInstallCount.setText(bundle.getInt("getUnit_no_survey",0)+"");
            }else{
    fgSewerageData.allInstallCount.setText("0");
    fgSewerageData.installCount.setText("0");
    fgSewerageData.noInstallCount.setText("0");
        }

        }else{
            fgSewerageData.allInstallCount.setText("0");
            fgSewerageData.installCount.setText("0");
            fgSewerageData.noInstallCount.setText("0");
        }
    }

    @Override
    public void getTotalDataFail() {
        fgSewerageData.btnAdd.setVisibility(View.GONE);
    }

    /**
     * tab标签点击
     */
    private class SewerageCompanyFragmentClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_fg_sewerage_all:
                    SetColorUtil.setBgColor(getActivity(), 0,
                            fgSewerageData.llFgSewerageAll, fgSewerageData.llFgSewerageInvestigated,
                            fgSewerageData.llFgSewerageUninvestigated
                    );
                    showFragment(fragments.get(0));
                    break;
                case R.id.ll_fg_sewerage_investigated:
                    SetColorUtil.setBgColor(getActivity(), 1,
                            fgSewerageData.llFgSewerageAll, fgSewerageData.llFgSewerageInvestigated,
                            fgSewerageData.llFgSewerageUninvestigated
                    );
                    showFragment(fragments.get(1));
                    break;
                case R.id.ll_fg_sewerage_uninvestigated:
                    SetColorUtil.setBgColor(getActivity(), 2,
                            fgSewerageData.llFgSewerageAll, fgSewerageData.llFgSewerageInvestigated,
                            fgSewerageData.llFgSewerageUninvestigated
                    );
                    showFragment(fragments.get(2));

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


    @Subscribe
    public void onReceivedBackPress(BackPressEvent event){
        isNeedRefresh = false;
    }

    @Subscribe
    public void onReceivedHaveNetResfresh(Bundle bundle){
        String tage = bundle.getString("HaveNetRefresh");

        if(REFRESH_TOTAL_FRAGMENT.equals(tage)){
//            if (!TextUtils.isEmpty(isExist) && "1".equals(isExist)) {
//                getdata(1, mName, "0");
//            } else {
//                getdata(1, mName, null);
//            }
            //重新刷新其他fg数据
            ((SewerageTotalFragment) fragments.get(0)).getName(mName, isNull);
        }else if(REFRESH_INVESTIGATED_FRAGMENT.equals(tage)){
             ((SewerageInvestigatedFragment) fragments.get(1)).getName(mName, isNull);
        } else if(REFRESH_UNINVESTIGATEDFRAGMENT.equals(tage)){
              ((SewerageUnInvestigatedFragment) fragments.get(2)).getName(mName, isNull);
        }

    }

}
