package com.augurit.agmobile.gzpssb.pshstatistics.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.augurit.agmobile.gzpssb.pshstatistics.view.SignTodayFragment;
import com.augurit.agmobile.gzpssb.pshstatistics.view.SignYesterdayFragment;

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

public class SignFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles = new String[]{"今日签到", "昨日签到"};
    private List<Fragment> mFragments = new ArrayList<>();
    private Context context;

    public SignFragmentPagerAdapter(FragmentManager fm, Context context, int curPos) {
        super(fm);
        this.context = context;
        Bundle bundle = new Bundle();
        bundle.putInt("curPos", curPos);
        mFragments.add(SignTodayFragment.newInstance(bundle));
        mFragments.add(SignYesterdayFragment.newInstance(bundle));
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
