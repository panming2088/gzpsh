package com.augurit.agmobile.gzps.statistic.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.augurit.agmobile.gzpssb.pshstatistics.view.*;
import com.augurit.agmobile.gzpssb.pshstatistics.view.AppInstalledStatsFragment;
import com.augurit.agmobile.gzpssb.pshstatistics.view.SignInStatsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.fragment
 * @createTime 创建时间 ：2017-03-13
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2017-03-13
 * @modifyMemo 修改备注：
 */

public class StatisticsFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles = new String[]{ "安装统计", "上报统计", "签到统计"};
    private String[] titles1 = new String[]{"摸查统计", "上报统计", "安装统计" , "签到统计"};
    private List<Fragment> mFragments = new ArrayList<>();
    private Context context;
    private boolean mIsPSh;
    AppInstalledStatsFragment appInstalledStatsFragment;
    UploadStatsFragment uploadStatsFragment;
    TaskQueryFragment taskQueryFragment;
    SignInStatsFragment signInStatsFragment;

    public StatisticsFragmentPagerAdapter(FragmentManager fm, Context context, int curSelect, boolean isPsh) {
        super(fm);
        this.context = context;
        this.mIsPSh = isPsh;
        Bundle bundle = new Bundle();
        bundle.putInt("curPos",curSelect);
        appInstalledStatsFragment = new AppInstalledStatsFragment();
        uploadStatsFragment = new UploadStatsFragment();
        signInStatsFragment = new SignInStatsFragment();
        taskQueryFragment = new TaskQueryFragment();
        appInstalledStatsFragment.setArguments(bundle);
        uploadStatsFragment.setArguments(bundle);
        signInStatsFragment.setArguments(bundle);
        taskQueryFragment.setArguments(bundle);

        if(mIsPSh){
            mFragments.add(taskQueryFragment);
            mFragments.add(uploadStatsFragment);
            mFragments.add(appInstalledStatsFragment);
            mFragments.add(signInStatsFragment);
        }else{
            mFragments.add(appInstalledStatsFragment);
            mFragments.add(uploadStatsFragment);
            if(mIsPSh) mFragments.add(taskQueryFragment);
            mFragments.add(signInStatsFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mIsPSh ? titles1.length : titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mIsPSh ? titles1[position] : titles[position];
    }
}
