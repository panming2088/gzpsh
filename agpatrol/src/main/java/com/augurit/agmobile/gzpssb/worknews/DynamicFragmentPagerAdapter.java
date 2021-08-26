package com.augurit.agmobile.gzpssb.worknews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.augurit.agmobile.gzpssb.LoginRoleConstant;
import com.augurit.agmobile.gzpssb.LoginRoleManager;

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

public class DynamicFragmentPagerAdapter extends FragmentStatePagerAdapter {
    //  private String[] titles = new String[]{ "巡检动态", "农污动态"};
    private ArrayList<String> titles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private Context context;

    public DynamicFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        titles.add("巡检动态");
        mFragments.add(new PSHDynamicFragment());
//        String curLoginRole = LoginRoleManager.getCurLoginrRole();
//        if (curLoginRole.equals(LoginRoleConstant.LOGIN_LEADER)) {
//            mFragments.add(new PSDynamicFragment());
//            mFragments.add(new PSHDynamicFragment());
//
//            titles.add("排水巡检动态");
//            titles.add("排水户巡检动态");
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PS)) {
//            mFragments.add(new PSDynamicFragment());
//            titles.add("排水巡检动态");
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_WS)) {//目前先看排水户
//            mFragments.add(new PSDynamicFragment());
//            titles.add("排水巡检动态");
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH)) {
//            mFragments.add(new PSHDynamicFragment());
//            titles.add("排水户巡检动态");
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_WS)) {//目前先看排水户
//            mFragments.add(new PSHDynamicFragment());
//            titles.add("排水户巡检动态");
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_PS)) {
//            mFragments.add(new PSDynamicFragment());
//            mFragments.add(new PSHDynamicFragment());
//            titles.add("排水巡检动态");
//            titles.add("排水户巡检动态");
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PS_WS)) {//目前先看排水
//            mFragments.add(new PSDynamicFragment());
//            titles.add("排水巡检动态");
//        } else {
//            mFragments.add(new PSDynamicFragment());
//            mFragments.add(new PSHDynamicFragment());
//
//            titles.add("排水巡检动态");
//            titles.add("排水户巡检动态");
//        }


    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
