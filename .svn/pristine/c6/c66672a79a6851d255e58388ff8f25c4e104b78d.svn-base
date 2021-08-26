package com.augurit.agmobile.gzpssb.jbjpsdy;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.augurit.agmobile.gzpssb.jbjpsdy.view.MyHookListFragment;
import com.augurit.agmobile.gzpssb.jbjpsdy.view.MyJbjMonitorListFragment;
import com.augurit.agmobile.gzpssb.jhj.view.uploadlist.MyCheckedWellListFragment;
import com.augurit.agmobile.gzpssb.jhj.view.uploadlist.MyWellAddListFragment;
import com.augurit.agmobile.gzpssb.uploadfacility.view.myuploadlist.SewerageHangUpWellListNewFragment;

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

public class MyJbjListPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles = new String[]{"窨井新增", "窨井校核","接驳信息", "窨井监测", "接户信息"
    };
    private List<Fragment> mFragments = new ArrayList<>();
    private Context context;

    public MyJbjListPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        mFragments.add(new MyWellAddListFragment());
        mFragments.add(new MyCheckedWellListFragment());
        mFragments.add(new MyHookListFragment());
        mFragments.add(new MyJbjMonitorListFragment());
        mFragments.add(new SewerageHangUpWellListNewFragment());
        //        mFragments.add(new MyDeletePipeListFragment());
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
