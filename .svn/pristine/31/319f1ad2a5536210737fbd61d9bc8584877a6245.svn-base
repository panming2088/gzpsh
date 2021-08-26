package com.augurit.agmobile.gzpssb.pshstatistics.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.NoScrollViewPager;
import com.augurit.agmobile.gzpssb.pshstatistics.adapter.UploadStatiscPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by sdb on 2018/4-11
 */

public class UploadStatsFragment extends Fragment {

    private NoScrollViewPager viewpager;
    private int curPos = 0;
    private TextView mPsTv;
    private TextView mNwTv;
    private TextView mPshTv;
    private LinearLayout mLlStats;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_uploadstats, null);
        curPos =  getArguments().getInt("curPos",0);
        EventBus.getDefault().register(this);

        viewpager = (NoScrollViewPager) view.findViewById(R.id.viewpager);
        UploadStatiscPagerAdapter adapter = new UploadStatiscPagerAdapter(getChildFragmentManager(), getContext());
        viewpager.setAdapter(adapter);

        mLlStats = (LinearLayout) view.findViewById(R.id.ll_stats);
        mPsTv = (TextView) view.findViewById(R.id.ps_tv);
        mNwTv = (TextView) view.findViewById(R.id.nw_tv);
        mPshTv = (TextView) view.findViewById(R.id.psh_tv);
        view.findViewById(R.id.ps_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewpager.setCurrentItem(0);

                mPsTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPsTv.setTextColor(Color.WHITE);
                mNwTv.setBackgroundColor(Color.WHITE);
                mNwTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                mPshTv.setBackgroundColor(Color.WHITE);
                mPshTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        view.findViewById(R.id.nw_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(1);
                mNwTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mNwTv.setTextColor(Color.WHITE);
                mPsTv.setBackgroundColor(Color.WHITE);
                mPsTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                mPshTv.setBackgroundColor(Color.WHITE);
                mPshTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        view.findViewById(R.id.psh_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(2);
                mPsTv.setBackgroundColor(Color.WHITE);
                mPsTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                mNwTv.setBackgroundColor(Color.WHITE);
                mNwTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                mPshTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPshTv.setTextColor(Color.WHITE);
            }
        });

        mPsTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mPsTv.setTextColor(Color.WHITE);
        mNwTv.setVisibility(View.GONE);
        mPshTv.setVisibility(View.GONE);
        mPsTv.setVisibility(View.GONE);
        mLlStats.setVisibility(View.GONE);

        setCurPageView();

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Integer position) {
        curPos = position;
        setCurPageView();
    }

    private void setCurPageView() {
        if (curPos == 0) {
            viewpager.setCurrentItem(0);
//            mPsTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            mPsTv.setTextColor(Color.WHITE);
//            mNwTv.setBackgroundColor(Color.WHITE);
//            mNwTv.setTextColor(getResources().getColor(R.color.colorPrimary));
//            mPsTv.setVisibility(View.VISIBLE);
//            mNwTv.setVisibility(View.VISIBLE);
//            mLlStats.setVisibility(View.VISIBLE);
            mPsTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mPsTv.setTextColor(Color.WHITE);
            mNwTv.setVisibility(View.GONE);
            mPsTv.setVisibility(View.GONE);
            mPshTv.setVisibility(View.GONE);
            mLlStats.setVisibility(View.GONE);
        } else if (curPos == 1) {
            viewpager.setCurrentItem(0);
            mPsTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mPsTv.setTextColor(Color.WHITE);
            mNwTv.setVisibility(View.GONE);
            mPsTv.setVisibility(View.GONE);
            mPshTv.setVisibility(View.GONE);
            mLlStats.setVisibility(View.GONE);
        } else if (curPos == 2) {
            viewpager.setCurrentItem(1);
            mNwTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mNwTv.setTextColor(Color.WHITE);
            mPsTv.setVisibility(View.GONE);
            mNwTv.setVisibility(View.GONE);
            mPshTv.setVisibility(View.GONE);
            mLlStats.setVisibility(View.GONE);
        }
        else if (curPos == 3) {
            viewpager.setCurrentItem(2);
            mPshTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mPshTv.setTextColor(Color.WHITE);
            mPsTv.setVisibility(View.GONE);
            mNwTv.setVisibility(View.GONE);
            mPshTv.setVisibility(View.GONE);
            mLlStats.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


}
