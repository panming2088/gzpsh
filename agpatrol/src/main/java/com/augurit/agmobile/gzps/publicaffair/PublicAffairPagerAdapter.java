package com.augurit.agmobile.gzps.publicaffair;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.augurit.agmobile.gzps.publicaffair.view.eventaffair.EventAffairFragment;
import com.augurit.agmobile.gzps.publicaffair.view.facilityaffair.FacilityAffairFragment;

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

public class PublicAffairPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles = new String[]{ "问题上报列表", "数据上报列表"};
    private List<Fragment> mFragments = new ArrayList<>();
    private Context context;

    public PublicAffairPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        mFragments.add(new EventAffairFragment());
        mFragments.add(new FacilityAffairFragment());
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
