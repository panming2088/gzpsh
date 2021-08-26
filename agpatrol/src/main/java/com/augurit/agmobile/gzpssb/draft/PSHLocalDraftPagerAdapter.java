package com.augurit.agmobile.gzpssb.draft;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.augurit.agmobile.gzps.workcation.view.EventDraftListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 创建人 ：liangsh
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.fragment
 * @createTime 创建时间 ：2017-11-21
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2017-11-21
 * @modifyMemo 修改备注：
 */

public class PSHLocalDraftPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles = new String[]{"巡检问题"
//            , "日常巡检"
//            , "数据上报"
            };
    private List<Fragment> mFragments = new ArrayList<>();
    private Context context;

    public PSHLocalDraftPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        mFragments.add(new PSHDraftListFragment());
//        mFragments.add(new JournalDraftListFragment());
//        mFragments.add(new FacilityDraftListFragment());
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
