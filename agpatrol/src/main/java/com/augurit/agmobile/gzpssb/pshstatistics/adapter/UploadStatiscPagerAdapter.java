package com.augurit.agmobile.gzpssb.pshstatistics.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.augurit.agmobile.gzpssb.pshstatistics.view.NWUploadStatsFragment;
import com.augurit.agmobile.gzpssb.pshstatistics.view.PSHUploadStatsFragment;
import com.augurit.agmobile.gzpssb.pshstatistics.view.PSUploadStatsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 创建人 ：guokunhu
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.fragment
 * @createTime 创建时间 ：2017-03-13
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2017-03-13
 * @modifyMemo 修改备注：
 */

public class UploadStatiscPagerAdapter extends FragmentStatePagerAdapter {
    //    private List<Fragment> mFragments = new ArrayList<>();
    private final SparseArray<Fragment> mFragments = new SparseArray<>(3);

    public UploadStatiscPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
//        mFragments.add(new PSUploadStatsFragment());
//        mFragments.add(new NWUploadStatsFragment());
//        mFragments.add(new PSHUploadStatsFragment());

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new PSUploadStatsFragment();
                break;
            case 1:
                fragment = new NWUploadStatsFragment();
                break;
            case 2:
                fragment = new PSHUploadStatsFragment();
                break;
        }
        return fragment;
//        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles.get(position);
//    }
}
