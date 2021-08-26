package com.augurit.agmobile.gzpssb.pshpublicaffair.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.augurit.agmobile.gzpssb.pshpublicaffair.PSHPublicAffairFragment;
import com.augurit.agmobile.gzpssb.pshpublicaffair.PSHPublicAffairFragment1;
import com.augurit.agmobile.gzpssb.pshpublicaffair.PSPublicAffairFragment;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.yviewpager.YFragmentStatePagerAdapter;

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

public class PublicAffairPagerAdapter extends YFragmentStatePagerAdapter {
    private String[] titles = new String[]{ "排水事务", "排水户事务"};
    private List<Fragment> mFragments = new ArrayList<>();
    private Context context;

    public PublicAffairPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        mFragments.add(new PSPublicAffairFragment());
        mFragments.add(new PSHPublicAffairFragment1());
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
