package com.augurit.agmobile.gzpssb.journal.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.augurit.agmobile.gzpssb.journal.view.detail.SewerageTableFragment;
import com.augurit.agmobile.gzpssb.journal.view.dialypatrollist.DialyPatrolRecordListFragment;
import com.augurit.agmobile.gzpssb.journal.view.dialypatrollist.ProblemRecordListFragment;

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

public class DialyPatrolRecordPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles = new String[]{"排水户信息","日常巡查列表","问题上报列表"};
    private List<Fragment> mFragments = new ArrayList<>();
    private Context context;

    public DialyPatrolRecordPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        mFragments.add(new SewerageTableFragment());
        mFragments.add(new DialyPatrolRecordListFragment());
        mFragments.add(new ProblemRecordListFragment());
    }

    public DialyPatrolRecordPagerAdapter(FragmentManager fm, Context context,Bundle bundle) {
        super(fm);
        this.context = context;
        mFragments.add(SewerageTableFragment.newInstance(bundle));
        mFragments.add(new DialyPatrolRecordListFragment());
        mFragments.add(new ProblemRecordListFragment());
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
