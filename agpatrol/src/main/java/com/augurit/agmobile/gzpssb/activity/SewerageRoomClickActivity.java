package com.augurit.agmobile.gzpssb.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.SewerageActivityData;
import com.augurit.agmobile.gzpssb.bean.SewerageRoomClickItemBean;
import com.augurit.agmobile.gzpssb.event.RefreshDataListEvent;
import com.augurit.agmobile.gzpssb.fragment.SewerageCompanyRoomClickFragment;
import com.augurit.agmobile.gzpssb.fragment.SeweragePopulationFragment;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.okhttp.Iview.ISewerageRoomClickView;
import com.augurit.agmobile.gzpssb.okhttp.Presenter.SewerageRoomClickPersenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2018/4/3.
 */

public class SewerageRoomClickActivity extends BaseDataBindingActivity implements ISewerageRoomClickView {
    private SewerageActivityData sewerageActivityData;
    TabLayout tabLayout;
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private TextView tv;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;
    private SewerageRoomClickPersenter sewerageRoomClickPersenter = new SewerageRoomClickPersenter(this);
    private String id;
    private List<Integer> totalList = new ArrayList<>();

    @Override
    public int initview() {
        return R.layout.activity_sewerage;
    }

    @Override
    public void initdatabinding() {
        sewerageActivityData = getBind();
        tabLayout = sewerageActivityData.tabLayoutActivitySewerage;
        viewPager = sewerageActivityData.viewPageActivitySewerage;
        setRightText("筛选");
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
  /*      setDataTitle("排水户单位人口信息"); 2018-4-12 oyzb改*/
        setDataTitle("房屋信息");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        fragmentManager = getSupportFragmentManager();


        sewerageRoomClickPersenter.getdata(id, 1, 10, 0, 0);
        baseInfoData.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    /**
     * 初始化所有基fragment
     */
    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(SewerageCompanyRoomClickFragment.newInstance());
        fragments.add(SeweragePopulationFragment.newInstance());
        showFragment(fragments.get(0));
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
            transaction.add(R.id.viewPage_activity_sewerage, fragment, fragment.getClass().getName());
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
        @Subscribe
    public void onEventMainThread(RefreshDataListEvent event) {
        if (MyApplication.refreshListType == RefreshDataListEvent.UPDATE_ROOM_UNIT_LIST1){
            totalList.clear();
            sewerageRoomClickPersenter.getdata(id, 1, 10, 0, 0);
        }
    }
    @Override
    public void setSewerageRoomClickList(SewerageRoomClickItemBean sewerageRoomClickList) {

        totalList.add(sewerageRoomClickList.getUnit_total());
        totalList.add(sewerageRoomClickList.getPerson_total());
//        MyApplication.HOUSE_ID = sewerageRoomClickList.getHouse_id();
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), this, totalList);
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(myPagerAdapter.getTabView(i, false));
        }


        initFragment();
        String smallAddress = sewerageRoomClickList.getSmallAddress();
        String HouseNo = sewerageRoomClickList.getHouse_no() == null ? "" : sewerageRoomClickList.getHouse_no();
        sewerageActivityData.tvSewerageAddress.setText(smallAddress + HouseNo);
    }

    /**
     * 内部adapter
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = new String[]{"单位", "人口"};
        private Context context;
        private List<Integer> totalList;

        public MyPagerAdapter(FragmentManager fm, Context context, List<Integer> totalList) {
            super(fm);
            this.context = context;
            this.totalList = totalList;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SewerageCompanyRoomClickFragment();
                case 1:
                    return new SeweragePopulationFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //第一次的代码
            //return tabTitles[position];
            //第二次的代码
            /**
             Drawable image = context.getResources().getDrawable(imageResId[position]);
             image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
             SpannableString sb = new SpannableString(" " + tabTitles[position]);
             ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
             sb.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
             return sb;*/


            return null;
        }

        public View getTabView(int position, boolean select) {
            View view = LayoutInflater.from(context).inflate(R.layout.tablayout, null);
            view.setSelected(select);
            tv = (TextView) view.findViewById(R.id.tv_tab);
            tv.setText(tabTitles[position] + "(" + totalList.get(position) + ")");
            return view;
        }
    }

        @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
