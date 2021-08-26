package com.augurit.agmobile.gzpssb.common;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * @PROJECT GZZHSW
 * @USER Augurit
 * @CREATE 2021/1/7 14:21
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments;
    private final List<String> mTitles;

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm, fragments, Collections.<String>emptyList());
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles == null || mTitles.isEmpty()) {
            return super.getPageTitle(position);
        }
        return mTitles.get(position);
    }
}
