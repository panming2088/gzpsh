package com.augurit.agmobile.gzps.common.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.yinglan.alphatabs.OnTabChangedListner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.widget
 * @createTime 创建时间 ：2017-03-31
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2017-03-31
 * @modifyMemo 修改备注：
 */


public class PatrolAlphaTabsIndicator extends LinearLayout {

    private ViewPager mViewPager;
    private OnTabChangedListner mListner;
    private List<PatrolAlphaTagView> mTabViews;
    private boolean ISINIT;
    /**
     * 子View的数量
     */
    private int mTabCounts;
    /**
     * 当前的条目索引
     */
    private int mCurrentItem = 0;

    public PatrolAlphaTabsIndicator(Context context) {
        this(context, null);
    }

    public PatrolAlphaTabsIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PatrolAlphaTabsIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        post(new Runnable() {
            @Override
            public void run() {
                isInit();
            }
        });
    }

    public void setViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
        init();
    }

    public void setOnTabChangedListner(OnTabChangedListner listner) {
        this.mListner = listner;
        isInit();
    }

    public PatrolAlphaTagView getCurrentItemView() {
        isInit();
        return mTabViews.get(mCurrentItem);
    }

    public PatrolAlphaTagView getTabView(int p) {
        isInit();
        return mTabViews.get(p);
    }

    public void removeAllBadge() {
        isInit();
        for (PatrolAlphaTagView alphaTabView : mTabViews) {
            alphaTabView.removeShow();
        }
    }

    private void isInit() {
        if (!ISINIT) {
            init();
        }
    }

    private void init() {
        ISINIT = true;
        mTabViews = new ArrayList<>();
        int childCount = getChildCount();
        // int childCount = 1;
        // mTabCounts = getChildCount();
        int tabIndex = 0;
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i) instanceof PatrolAlphaTagView) {
                PatrolAlphaTagView tabView = (PatrolAlphaTagView) getChildAt(i);
                mTabViews.add(tabView);
                //设置点击监听
                tabView.setOnClickListener(new MyOnClickListener(tabIndex));
                tabIndex++;
            } else {
                // throw new IllegalArgumentException("TabIndicator的子View必须是TabView");
            }
        }
        mTabCounts = mTabViews.size();

        if (null != mViewPager) {
            if (null == mViewPager.getAdapter()) {
                throw new NullPointerException("viewpager的adapter为null");
            }
            if (mViewPager.getAdapter().getCount() != mTabViews.size()) {
                throw new IllegalArgumentException("Tabview的数量必须和ViewPager条目数量一致");
            }
            //todo 取消滑动
            //对ViewPager添加监听
            // mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        }

        mTabViews.get(mCurrentItem).setIconAlpha(1.0f);
    }

//    private class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            //滑动时的透明度动画
//            if (positionOffset > 0) {
//                mTabViews.get(position).setIconAlpha(1 - positionOffset);
//                mTabViews.get(position + 1).setIconAlpha(positionOffset);
//            }
//            //滑动时保存当前按钮索引
//            mCurrentItem = position;
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            super.onPageSelected(position);
//            resetState();
//            mTabViews.get(position).setIconAlpha(1.0f);
//            mCurrentItem = position;
//        }
//    }

    private class MyOnClickListener implements OnClickListener {

        private int currentIndex;

        public MyOnClickListener(int i) {
            this.currentIndex = i;
        }

        @Override
        public void onClick(View v) {

            if ((enableFalsePos+"").indexOf(currentIndex+"") == -1) {
                //点击前先重置所有按钮的状态
                resetState();
                mTabViews.get(currentIndex).setIconAlpha(1.0f);
                if (null != mListner) {
                    mListner.onTabSelected(currentIndex);
                }
                if (null != mViewPager) {
                    //不能使用平滑滚动，否者颜色改变会乱
                    mViewPager.setCurrentItem(currentIndex, false);
                }
                //点击是保存当前按钮索引
                mCurrentItem = currentIndex;
            } else {
                if (tabClickListener != null) {
                    tabClickListener.onTabClick(currentIndex);
                }
            }
        }
    }

    public int enableFalsePos = -1;

    public void setTabEnableFalse(int position, TabClickListener tabClickListener) {
        this.tabClickListener = tabClickListener;
        this.enableFalsePos = position;
    }

    public TabClickListener tabClickListener;

    public interface TabClickListener {
        void onTabClick(int position);
    }

    /**
     * 重置所有按钮的状态
     */
    private void resetState() {
        for (int i = 0; i < mTabCounts; i++) {
            mTabViews.get(i).setIconAlpha(0);
        }
    }

    private static final String STATE_INSTANCE = "instance_state";
    private static final String STATE_ITEM = "state_item";

    /**
     * @return 当View被销毁的时候，保存数据
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_ITEM, mCurrentItem);
        return bundle;
    }

    /**
     * @param state 用于恢复数据使用
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentItem = bundle.getInt(STATE_ITEM);
            //重置所有按钮状态
            resetState();
            //恢复点击的条目颜色
            mTabViews.get(mCurrentItem).setIconAlpha(1.0f);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
        } else {
            super.onRestoreInstanceState(state);
        }
    }
}