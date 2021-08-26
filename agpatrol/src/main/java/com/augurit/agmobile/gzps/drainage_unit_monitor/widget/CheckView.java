package com.augurit.agmobile.gzps.drainage_unit_monitor.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.drainage_unit_monitor.bottomsheet.AnchorSheetBehavior;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.DrainageUnitMonitorFragment;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.am.cmpt.widget.bottomsheet.IBottomSheetBehavior;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class CheckView {
    private Context context;
    private View view;
    private FrameLayout flTop;
    private Fragment fragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewGroup container;
    private PageAdapter adapter;
    private AnchorSheetBehavior behavior;
    private int top;

    public CheckView(Fragment fragment, ViewGroup container) {
        context = container.getContext();
        this.container = container;
        this.fragment = fragment;
        behavior = AnchorSheetBehavior.from(container);
        behavior.setAnchorHeight(DensityUtils.dp2px(context, 350));
        behavior.setPeekHeight(DensityUtils.dp2px(context, 36));
        behavior.setState(IBottomSheetBehavior.STATE_HIDDEN);
        initView();
    }

    private void initView(){
        view = LayoutInflater.from(context).inflate(R.layout.layout_drainage_monitor_detail, null);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        view.setLayoutParams(lp);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        flTop = (FrameLayout) view.findViewById(R.id.fl_top);
        setupViewPage();
        container.addView(view);
        flTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(behavior.getState() == IBottomSheetBehavior.STATE_ANCHOR){
                    behavior.setState(IBottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    behavior.setState(IBottomSheetBehavior.STATE_ANCHOR);
                }
            }
        });

        view.findViewById(R.id.iv_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrainageUnitMonitorFragment)fragment).handleBack();
            }
        });

        behavior.setBottomSheetCallback(new AnchorSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int oldState, int newState) {
                if(newState == IBottomSheetBehavior.STATE_ANCHOR){
                    top = bottomSheet.getTop();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                int height = ((ViewGroup)bottomSheet.getParent()).getHeight() - bottomSheet.getTop() - tabLayout.getBottom();
                setHeight(height);
            }
        });
    }

    private void setHeight(int height){
        ((JBJFragment)adapter.getItem(0)).setLeft(height);
        ((DMFragment)adapter.getItem(1)).setHeight(height);
        ((KGFragment)adapter.getItem(2)).setHeight(height);
        ((LGFragment)adapter.getItem(3)).setHeight(height);
        ((ZLFragment)adapter.getItem(4)).setHeight(height);
    }

    private void setupViewPage(){
        adapter = new PageAdapter(this.fragment.getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    ((JBJFragment)adapter.getItem(0)).refresh();
                } else if(position == 1){
                    ((DMFragment)adapter.getItem(1)).refresh();
                } else if(position == 2){
                    ((KGFragment)adapter.getItem(2)).refresh();
                } else if(position == 3){
                    ((LGFragment)adapter.getItem(3)).refresh();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public String getDMType(){
        return ((DMFragment)adapter.getItem(1)).getType();
    }

    public String getKGType(){
        return ((KGFragment)adapter.getItem(2)).getType();
    }

    public String getLGType(){
        return ((LGFragment)adapter.getItem(3)).getType();
    }

    public void show(Component component, long objectId, String name) {
        ((JBJFragment)adapter.getItem(0)).setId(objectId);
        ((JBJFragment)adapter.getItem(0)).setName(name);
        ((JBJFragment)adapter.getItem(0)).setComponent(component);
        ((DMFragment)adapter.getItem(1)).setComponent(component);
        ((DMFragment)adapter.getItem(1)).reset();
        ((KGFragment)adapter.getItem(2)).setComponent(component);
        ((KGFragment)adapter.getItem(2)).reset();
        ((LGFragment)adapter.getItem(3)).setComponent(component);
        ((LGFragment)adapter.getItem(3)).reset();
        ((ZLFragment)adapter.getItem(4)).setId(objectId);
        ((ZLFragment)adapter.getItem(4)).setName(name);
        viewPager.setCurrentItem(0);
        behavior.setState(IBottomSheetBehavior.STATE_ANCHOR);
    }

    public void hide(){
        behavior.setState(IBottomSheetBehavior.STATE_HIDDEN);
    }

    public boolean isVisible(){
        return behavior.getState() != IBottomSheetBehavior.STATE_HIDDEN;
    }

    private class PageAdapter extends FragmentStatePagerAdapter{
        private String[] titles = new String[]{"水质监测", "地面检查", "开盖检查", "立管检查", "资料检查"};
        private List<Fragment> mFragments = new ArrayList<>();
        public PageAdapter(FragmentManager fm) {
            super(fm);
            mFragments.add(new JBJFragment());
            mFragments.add(new DMFragment());
            mFragments.add(new KGFragment());
            mFragments.add(new LGFragment());
            mFragments.add(new ZLFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    public void startLocation(){
        behavior.setState(IBottomSheetBehavior.STATE_ANCHOR);
    }



    public int getTop(){
        return top;
    }
}
