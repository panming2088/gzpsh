package com.augurit.agmobile.gzpssb.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.SewerageActivityData;
//import com.augurit.agmobile.gzpssb.bean.AddressBean;
import com.augurit.agmobile.gzpssb.bean.DoorNOBean;
import com.augurit.agmobile.gzpssb.bean.SewerageInvestBean;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.condition.SewerageItemSearchCondition;
import com.augurit.agmobile.gzpssb.event.RefreshDataListEvent;
import com.augurit.agmobile.gzpssb.event.RefreshDoorData;
import com.augurit.agmobile.gzpssb.event.RefreshTopEvent;
import com.augurit.agmobile.gzpssb.fragment.SewerageCompanyFragment;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.okhttp.Iview.ISewerageItemView;
import com.augurit.agmobile.gzpssb.okhttp.Presenter.SewerageItemPresenter;
import com.augurit.agmobile.gzpssb.pshdoorno.add.service.PSHUploadDoorNoService;
import com.augurit.agmobile.gzpssb.utils.SetMaxLengthUtils;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiaoyu on 2018/4/3.
 * ps:SewerageItemPresenter.IGetSearchByCompanyName为筛选的接口，使用的时候直接实现，然后重写内部方法，直接获取内部参数即可
 */

public class SewerageActivity extends BaseDataBindingActivity implements ISewerageItemView{

    private SewerageActivityData sewerageActivityData;
    TabLayout tabLayout;
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private TextView tv;

    private ProgressDialog progressDialog;
    private TextView tv_submit;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;
    private SewerageItemPresenter sewerageItemPresenter = new SewerageItemPresenter(this);
    private int house_total;

    private List<Integer> totalList = new ArrayList<>();
    private String isExist;
    private String mName = "";
    private boolean isCreate = false;//防止因为多次创建执行多次执行刷新

    public static   boolean isSearch=false;

    private View view;
    @Override
    public int initview() {
        isCreate=true;
        return R.layout.activity_sewerage;  /*2018-4-12 ohyzb改*/

    }

    @Override
    public void initdatabinding() {
        sewerageActivityData = getBind();
        EventBus.getDefault().register(this);
        DoorNOBean doorBean = (DoorNOBean) getIntent().getSerializableExtra("doorBean");
        //增加判断，解决腾通讯bugly反馈的崩溃问题
        if(null != doorBean){
            isExist = doorBean.getIsExist();//add_type=0  两个列表接口加上这个参数  0是 自增的门牌   不传就默认是sbss的门牌
            if (doorBean.getS_guid() != null) {
                MyApplication.GUID = doorBean.getS_guid();
            }
            if (doorBean.getDzdm() != null) {
                MyApplication.DZDM = doorBean.getDzdm();
            }
        }
        tabLayout = sewerageActivityData.tabLayoutActivitySewerage;
        viewPager = sewerageActivityData.viewPageActivitySewerage;
        setRightText("筛选");

        SetMaxLengthUtils.setMaxLength(baseInfoData.etSearch,SetMaxLengthUtils.MAX_LENGTH_50);

    }


    @Override
    public void initData() {
      /*  setDataTitle("排水户信息"); 2018-4-12 oyzb改*/
        view = LayoutInflater.from(context).inflate(R.layout.tablayout, null);
        view.setSelected(false);
        tv = (TextView) view.findViewById(R.id.tv_tab);

        tv_submit = (TextView) findViewById(R.id.tv_submit);
        setDataTitle("门牌信息");
        fragmentManager = getSupportFragmentManager();
//        initFragment();
        if (MyApplication.doorBean != null) {
            if (MyApplication.doorBean.getISTATUE().equals("1")) {
                tv_submit.setBackgroundColor(Color.parseColor("#FFFFC248"));
                tv_submit.setText("未调查");
                tv_submit.setEnabled(false);
            } else if (MyApplication.doorBean.getISTATUE().equals("2")) {
                tv_submit.setBackground(getResources().getDrawable(R.drawable.sel_btn_invest));
                tv_submit.setText("完成调查");
            } else if (MyApplication.doorBean.getISTATUE().equals("3")) {
                tv_submit.setBackgroundColor(Color.parseColor("#3EA500"));
                tv_submit.setText("调查完毕");
                tv_submit.setEnabled(false);
            } else {
                tv_submit.setBackgroundColor(Color.parseColor("#FFFFC248"));
                tv_submit.setText("未调查");
                tv_submit.setEnabled(false);
            }
        }
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(myPagerAdapter);
        initEvent();
        sewerageItemPresenter.setIGetFail(new SewerageItemPresenter.IGetFail() {
            @Override
            public void onFail(String code) {
                //ToastUtil.shortToast(getContext(),code);

                //修改为只显示一个tab 排水户
//        totalList.add(sewerageItemList.getHouse_total());
                if(!"".equals(code)){
                    totalList.clear();
                    totalList.add(0);
                }
//        totalList.add(sewerageItemList.getPerson_total());

//        tv_submit.setVisibility(View.VISIBLE);

                /**
                 * 设置viewpager，tablayout
                 */

                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    tab.setCustomView(myPagerAdapter.getTabView(i, false,totalList));
                }

            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        isCreate=true;
        isSearch=false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isCreate&&!isSearch) {
            if (isCreate || !isSearch) {
                if (!TextUtils.isEmpty(isExist) && "1".equals(isExist)) {
                    getdata(1, mName, "0");
                } else {
                    getdata(1, mName, null);
                }
                isCreate = false;
            }
        }
    }

   private PSHUploadDoorNoService mPSHUploadDoorNoService;

    private void getdata(int page, String name, String add_type) {
        /*if(mPSHUploadDoorNoService == null ){
            mPSHUploadDoorNoService = new PSHUploadDoorNoService(this);
        }
        mPSHUploadDoorNoService.queryAdressInfo(MyApplication.GUID , add_type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddressBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                            totalList.clear();
                            totalList.add(0);
                        tabLayout.setupWithViewPager(viewPager);
                        tabLayout.setTabMode(TabLayout.MODE_FIXED);
                        for (int i = 0; i < tabLayout.getTabCount(); i++) {
                            TabLayout.Tab tab = tabLayout.getTabAt(i);
                            tab.setCustomView(myPagerAdapter.getTabView(i, false,totalList));
                        }

                    }

                    @Override
                    public void onNext(AddressBean addressBean) {
                        if(addressBean!=null && addressBean.getResult_code() == 200){
                            MyApplication.buildId = addressBean.getBuildId();
//                            totalList.clear();
//                            tabLayout.setupWithViewPager(viewPager);
//                            tabLayout.setTabMode(TabLayout.MODE_FIXED);
//                            for (int i = 0; i < tabLayout.getTabCount(); i++) {
//                                TabLayout.Tab tab = tabLayout.getTabAt(i);
//                                tab.setCustomView(myPagerAdapter.getTabView(i, false,totalList));
//                            }
                            sewerageActivityData.tvSewerageAddress.setText(addressBean.getSmallAddress());
                        }
                    }
                });
                */
    }

    private void initEvent() {
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.shortToast(context, "建设中");
                showAlertDialog();
            }
        });
        baseInfoData.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SewerageItemSearchCondition sewerageItemSearchCondition = new SewerageItemSearchCondition();
                sewerageItemSearchCondition.setCommpanyName(baseInfoData.etSearch.getText().toString().trim());
                if("".equals(baseInfoData.etSearch.getText().toString().trim())){
                    sewerageItemSearchCondition.setNull(true);
                }
                EventBus.getDefault().post(sewerageItemSearchCondition);
                baseInfoData.drawerLayout.closeDrawers();
            }
        });
        baseInfoData.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseInfoData.etSearch.setText("");

            }
        });
        baseInfoData.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void showAlertDialog() {

        DialogUtil.MessageBox(this, "提示", "确认完成调查？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String loginName = new LoginRouter(SewerageActivity.this, AMDatabase.getInstance()).getUser().getLoginName();
                if (MyApplication.doorBean != null) {
                    String add_type = (MyApplication.doorBean.getIsExist() != null && MyApplication.doorBean.getIsExist().equals("1")) ? "0" : null;
                    sewerageItemPresenter.investEnd(MyApplication.doorBean.getS_guid(), loginName, add_type);
                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }


//    /**
//     * 初始化所有基fragment
//     */
//    private void initFragment() {
//        fragments = new ArrayList<Fragment>();
////        fragments.add(SewerageRoomFragment.newInstance());
//        Bundle bundle = new Bundle();
//        bundle.putString("isExist", isExist);
//        fragments.add(SewerageCompanyFragment.newInstance(bundle));
//        //  fragments.add(SeweragePopulationFragment.newInstance());
//
//        showFragment(fragments.get(0));
//
//    }
//
//    /**
//     * 显示fragment
//     *
//     * @param fragment 要显示的fragment
//     */
//    private void showFragment(Fragment fragment) {
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        hideFragment(transaction);
//
//        if (!fragment.isAdded() && null == fragmentManager.findFragmentByTag(fragment.getClass().getName())) {
//            transaction.add(R.id.viewPage_activity_sewerage, fragment, fragment.getClass().getName());
//        } else {
//            transaction.show(fragment);
//        }
//        transaction.commit();
//    }
//
//    /**
//     * 隐藏其他fragment
//     *
//     * @param transaction 控制器
//     */
//    private void hideFragment(FragmentTransaction transaction) {
//        for (int i = 0; fragments.size() > i; i++) {
//            if (fragments.get(i).isVisible()) {
//                transaction.hide(fragments.get(i));
//            }
//        }
//    }


    @Override
    public void setSewerageItemList(SewerageItemBean sewerageItemList) {
        MyApplication.buildId = sewerageItemList.getBuildId();
//        house_total = sewerageItemList.getHouse_total();
        totalList.clear();
        //修改为只显示一个tab 排水户
//        totalList.add(sewerageItemList.getHouse_total());
        totalList.add(sewerageItemList.getUnit_total());
//        totalList.add(sewerageItemList.getPerson_total());

//        tv_submit.setVisibility(View.VISIBLE);

        /**
         * 设置viewpager，tablayout
         */

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(myPagerAdapter.getTabView(i, false,totalList));
        }


     /*   sewerageActivityData.tvSewerageAddress.setText("门牌地址："+sewerageItemList.getSmallAddress()+" ,房间数量："+house_total); 2018-4-12 oyzb 改*/
        sewerageActivityData.tvSewerageAddress.setText(sewerageItemList.getSmallAddress());

        MyApplication.SEWERAGEITEMBEAN = sewerageItemList;
//        if(MyApplication.refreshListType==1){
//            showFragment(fragments.get(1));
//        }
    }

    @Override
    public void onInvestCode(SewerageInvestBean code) {
        if (code.getResult_code() == 200) {
            ToastUtil.shortToast(context, "更改调查状态成功！");
            tv_submit.setBackgroundColor(Color.parseColor("#3EA500"));
            tv_submit.setText("调查完毕");
            tv_submit.setEnabled(false);
            tv_submit.setVisibility(View.VISIBLE);
            EventBus.getDefault().post(new RefreshDoorData());
        } else {
            ToastUtil.shortToast(context, code.getResult_msg());
        }
    }
    @Subscribe
    public void onReceivedSewerageItemSearchCondition(SewerageItemSearchCondition sewerageItemSearchCondition) {
        mName = sewerageItemSearchCondition.getCommpanyName();
        // getName.getName(mName);
        if (!TextUtils.isEmpty(isExist) && "1".equals(isExist)) {
            getdata(1, mName, "0");
        } else {
            getdata(1, mName, null);
        }
        isSearch=true;
    }
    @Subscribe
    public void onReceiveRefresh(Bundle bundle){
        if("1".equals(bundle.get("refresh"))){
            if (!TextUtils.isEmpty(isExist) && "1".equals(isExist)) {
                getdata(1, mName, "0");
            } else {
                getdata(1, mName, null);
            }
        }
    }

    @Override
    public void onLoadStart() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(SewerageActivity.this);
            progressDialog.setMessage("正在更改调查状态，请等待");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void onCompelete() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Subscribe
    public void onEventMainThread(RefreshDataListEvent event) {
        //刷新界面
        if (MyApplication.refreshListType == RefreshDataListEvent.UPDATE_MIAN_LIST) {
            totalList.clear();
        /*    if (!TextUtils.isEmpty(isExist) && "1".equals(isExist)) {
                sewerageItemPresenter.getdata(MyApplication.GUID, MyApplication.DZDM,
                        1, 10, 0, 0, "0");
            } else {
                sewerageItemPresenter.getdata(MyApplication.GUID, MyApplication.DZDM,
                        1, 10, 0, 0, null);
            }*/
            if (tv_submit != null) {
                tv_submit.setBackground(getResources().getDrawable(R.drawable.sel_btn_invest));
                tv_submit.setText("完成调查");
                tv_submit.setEnabled(true);
            }
        }
    }


    /**
     * 内部adapter
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {
        // private String[] tabTitles = new String[]{"房间","单位", "人口"};
//        private String[] tabTitles = new String[]{"房套", "单位"};
        private String[] tabTitles = new String[]{"排水户"};
        private Context context;
        private Bundle bundle;

        public MyPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
            bundle = new Bundle();
            bundle.putString("isExist", isExist);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SewerageCompanyFragment.newInstance(bundle);

             /*     case 0:
                    return new SewerageRoomFragment();
                case 1:
                    return new SewerageCompanyFragment();
              case 2:
                    return new SeweragePopulationFragment();*/
            }
            return null;
        }


        @Override
        public int getCount() {
            if (tabTitles.length == 0 || tabTitles == null)
                return 0;
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return null;
        }

        public View getTabView(int position, boolean select,List<Integer> totalList) {
     /*       tv.setText(tabTitles[position]); 2018-4-12 oyzb改*/
            tv.setText(tabTitles[position] + "(" + totalList.get(position) + ")");
            return view;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onReceivedHaveNetResfresh(Bundle bundle){
        if("1".equals(bundle.getString("HaveNetRefresh"))){
            if (!TextUtils.isEmpty(isExist) && "1".equals(isExist)) {
                getdata(1, mName, "0");
            } else {
                getdata(1, mName, null);
            }
        }
    }

    @Subscribe
    public void onResfreshTop(RefreshTopEvent event){
        sewerageActivityData.tvSewerageAddress.setText(event.getAddress());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SewerageCompanyFragment.isSearch = false;
        this.finish();

    }
}
