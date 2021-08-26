package com.augurit.agmobile.gzps.drainage_unit_monitor.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.augurit.agmobile.gzps.drainage_unit_monitor.bottomsheet.AnchorSheetBehavior;
import com.augurit.am.cmpt.widget.bottomsheet.IBottomSheetBehavior;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Behavior嵌套NestedScrollView嵌套滚动mView
 * mView未滚动到顶部,但NestedScrollView在顶部时,下拉mView时,BehaviorNestedScrollView实现::避免让Behavior消费下拉滚动事件
 *
 * @author 创建人 ：yanghaozhang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.common.scrollview
 * @createTime 创建时间 ：2021/1/19
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class BehaviorNestedScrollView extends NestedScrollView {

    private static final String TAG = "BehaviorNestedScrollVie";

    private static final boolean DEBUG_LOG = true;

    /**
     * View优先滚动
     */
    private final List<View> mPriorityList = new ArrayList<>();

    /**
     * View不嵌套滚动
     */
    private final List<View> mNoNestedList = new ArrayList<>();

    private AnchorSheetBehavior<View> mIdentifyBehavior;

    public BehaviorNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public BehaviorNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BehaviorNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (isNoNestedView(target)) {
            return;
        }
        if (!isPriorityConsume(target, dy)) {
            if (consumed == null) {
                consumed = new int[2];
            }
            if (!(dy < 0 && target.canScrollVertically(-1))) {
                // Behavior滚动
                super.onNestedPreScroll(target, dx, dy, consumed);
            }
            if (dy > 0) {
                // BehaviorNestedScrollView 滚动
                dy -= consumed[1];
                final int oldScrollY = getScrollY();
                scrollBy(0, dy);
                final int myConsumed = getScrollY() - oldScrollY;
                consumed[1] += myConsumed;
            }
        }

/*        if (!target.equals(mView)) {
            super.onNestedPreScroll(target, dx, dy, consumed);
        }*/
    }

    /**
     * 子视图的Fling
     */
    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        if (DEBUG_LOG) {
            Log.d(TAG, "onNestedPreFling() called with: target = [" + target + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
        }
        // 不让Behavior消费Fling
        if (isPriorityConsume(target, velocityY)) {
            return false;
        }
        if (isNoNestedView(target)) {
            return false;
        }
        // 在Up点击事件时,NestedScrollView.onInterceptTouchEvent()调用stopNestedScroll(),返回都为false
        boolean consumeFling = super.onNestedPreFling(target, velocityX, velocityY);
        if (!consumeFling && velocityY < 0) {
            // RecyclerView的优化,只要RecyclerView支持滚动,就不会传递Fling
            if (target instanceof RecyclerView && !target.canScrollVertically(-1)) {
                fling((int) velocityY);
                consumeFling = true;
            }
            // V4包的NestedScrollView的flingWithNestedDispatch()中,已经到达顶部但target.getScrollY()==1
            else if (target instanceof NestedScrollView && target.getScrollY() <= 1) {
                fling((int) velocityY);
                consumeFling = true;
            }
        } else if (!consumeFling && velocityY > 0) {
            // 如果NestedScrollView能向上滚动时
            if (canScrollVertically(1) && (mIdentifyBehavior == null || mIdentifyBehavior.getState() == IBottomSheetBehavior.STATE_EXPANDED)) {
                fling((int) velocityY);
                consumeFling = true;
            }
        }
        return consumeFling;
    }

    public void setIdentifyBehavior(AnchorSheetBehavior<View> identifyBehavior) {
        mIdentifyBehavior = identifyBehavior;
    }

    public boolean addPriorityView(View view) {
        Logger.d("addPriorityView");
        return mPriorityList.add(view);
    }

    public boolean addNoNestedView(View view) {
        Logger.d("addNoNestedView");
        return mNoNestedList.add(view);
    }

    /**
     * @return true: 由view优先消费滚动事件
     */
    private boolean isPriorityConsume(View view, float dy) {
        if (mPriorityList.contains(view)) {
            return (dy < 0 && view.canScrollVertically(-1)) || (dy > 0 && view.canScrollVertically(1));
        }
        return false;
    }

    /**
     * @return true: view自主滚动
     */
    private boolean isNoNestedView(View view) {
        return mNoNestedList.contains(view);
    }

    public void reset() {
        Logger.d("reset");
        mPriorityList.clear();
        mNoNestedList.clear();
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (!isNoNestedView(target)) {
            super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (isNoNestedView(target)) {
            return false;
        }
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }
}
