package com.augurit.agmobile.gzpssb.uploadevent.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.augurit.agmobile.gzpssb.journal.view.detail.SewerageTableFragment;
import com.augurit.agmobile.gzpssb.journal.view.dialypatrollist.DialyPatrolRecordListFragment;
import com.augurit.agmobile.gzpssb.journal.view.dialypatrollist.ProblemRecordListFragment;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.FacilityDetailFragment;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.PSHEventDetailFragment;

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

public class PSHUploadEventPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles = new String[]{"问题详情","排水户信息"};
    private List<Fragment> mFragments = new ArrayList<>();
    private Context context;

    public PSHUploadEventPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        mFragments.add(new PSHEventDetailFragment());
        mFragments.add(new SewerageTableFragment());

    }

    public PSHUploadEventPagerAdapter(FragmentManager fm, Context context, Bundle bundle) {
        super(fm);
        this.context = context;
        mFragments.add(new PSHEventDetailFragment());
        if (bundle.getSerializable("pshAffair") != null) {
            mFragments.add(SewerageTableFragment.newInstance(bundle));
            titles = new String[]{"问题详情", "排水户信息"};
        } else if (bundle.getParcelable("modifiedFacility") != null
                || bundle.getParcelable("uploadedFacility") != null) {
            mFragments.add(FacilityDetailFragment.newInstance(bundle));
            titles = new String[]{"问题详情", "设施信息"};
        } else {
            titles = new String[]{"问题详情"};
        }
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
