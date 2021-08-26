package com.augurit.agmobile.gzps.drainage_unit_monitor.bottomsheet;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

/**
 * @author 创建人 ：yanghaozhang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.common.behavior
 * @createTime 创建时间 ：2021/1/11
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class DrainageAnchorSheetBehavior<V extends View> extends AnchorSheetBehavior<V> {

    public ViewDragHelper.Callback mDelegateCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mDragCallback.tryCaptureView(child, pointerId);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mDragCallback.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            mDragCallback.onViewDragStateChanged(state);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int currentTop = releasedChild.getTop();
            anchorOffect = mParentHeight - mAnchorHeight;
            // 向下滑,且currentTop在(mMinOffset,anchorOffect)之间
            if (yvel > 0 && currentTop < anchorOffect && currentTop > mMinOffset) {
                setState(STATE_ANCHOR);
            } else if (yvel == 0f) {
                int needPostState = -1;
                if (currentTop < anchorOffect) {
                    if (Math.abs(currentTop - mMinOffset) < Math.abs(currentTop - anchorOffect)) {
                        setState(STATE_EXPANDED);
                        if (currentTop == mMinOffset) {
                            needPostState = STATE_EXPANDED;
                        }
                    } else {
                        setState(STATE_ANCHOR);
                        if (currentTop == anchorOffect) {
                            needPostState = STATE_ANCHOR;
                        }
                    }
                } else {
                    if (Math.abs(currentTop - mMaxOffset) < Math.abs(currentTop - anchorOffect)) {
                        setState(STATE_COLLAPSED);
                        if (currentTop == mMaxOffset) {
                            needPostState = STATE_COLLAPSED;
                        }
                    } else {
                        setState(STATE_ANCHOR);
                        if (currentTop == anchorOffect) {
                            needPostState = STATE_ANCHOR;
                        }
                    }
                }
                /**
                 * 1,如果当前top == setState(STATE_ANCHOR)对应的高度,也需要执行onStateChanged
                 * 2,需要执行setStateInternal,避免state一直处于STATE_SETTLING状态
                 */
                if (needPostState > -1) {
                    if (mViewRef != null && mViewRef.get() != null) {
                        final int finalNeedPostState = needPostState;
                        mViewRef.get().post(new Runnable() {
                            @Override
                            public void run() {
                                setStateInternal(finalNeedPostState);
                            }
                        });
                    } else {
                        setStateInternal(needPostState);
                    }
                }
            } else {
                mDragCallback.onViewReleased(releasedChild, xvel, yvel);
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return mDragCallback.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return mDragCallback.clampViewPositionHorizontal(child, left, dx);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragCallback.getViewVerticalDragRange(child);
        }
    };

    public DrainageAnchorSheetBehavior() {
    }

    public DrainageAnchorSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 1,使用mDelegateCallback,修复向下滑直接从STATE_EXPANDED改变到STATE_COLLAPSED
     * 2,修改每次都调用parent.onLayoutChild(child, layoutDirection) 实现子View的改变能实时更新
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        // First let the parent lay it out
        if (mState != STATE_DRAGGING && mState != STATE_SETTLING) {
            if (ViewCompat.getFitsSystemWindows(parent) &&
                    !ViewCompat.getFitsSystemWindows(child)) {
                ViewCompat.setFitsSystemWindows(child, true);
            }
//            parent.onLayoutChild(child, layoutDirection);
        }
        int savedTop = child.getTop();
        // First let the parent lay it out
        parent.onLayoutChild(child, layoutDirection);
        // Offset the bottom sheet
        mParentHeight = parent.getHeight();
        mMinOffset = Math.max(0, mParentHeight - child.getHeight());
        mMaxOffset = Math.max(mParentHeight - mPeekHeight, mMinOffset);
        if (mState == STATE_EXPANDED) {
            ViewCompat.offsetTopAndBottom(child, mMinOffset);
        } else if (mHideable && mState == STATE_HIDDEN) {
            ViewCompat.offsetTopAndBottom(child, mParentHeight);
        } else if (mState == STATE_COLLAPSED) {
            ViewCompat.offsetTopAndBottom(child, mMaxOffset);
        } else if (mState == STATE_ANCHOR) {
            ViewCompat.offsetTopAndBottom(child, mParentHeight - mAnchorHeight);
        } else if (mState == STATE_DRAGGING || mState == STATE_SETTLING) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.getTop());
        }
        if (mViewDragHelper == null) {
            mViewDragHelper = ViewDragHelper.create(parent, mDelegateCallback);
        }
        mViewRef = new WeakReference<>(child);
        if (!mNestedScrollingChildAssigned) {
            mNestedScrollingChildRef = new WeakReference<>(findScrollingChild(child));
        }
        return true;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
        if (child.getTop() == mMinOffset) {
            setStateInternal(STATE_EXPANDED);
            return;
        }
        if (target != mNestedScrollingChildRef.get() || !mNestedScrolled) {
            return;
        }
        int currentTop = child.getTop();
        anchorOffect = mParentHeight - mAnchorHeight;
        // 向下滑,且currentTop在(mMinOffset,anchorOffect)之间
        if (mLastNestedScrollDy < 0) {
            if (currentTop < anchorOffect) {
                setState(STATE_ANCHOR);
            } else {
                setState(STATE_COLLAPSED);
            }
        }
        // 修复使用mAnchorHeight而不是anchorOffect的问题
        else if (mLastNestedScrollDy > 0) {
            if (currentTop < anchorOffect) {
                setState(STATE_EXPANDED);
            } else {
                setState(STATE_ANCHOR);
            }
        } else if (mLastNestedScrollDy == 0) {
            if (currentTop < anchorOffect) {
                if (Math.abs(currentTop - mMinOffset) < Math.abs(currentTop - anchorOffect)) {
                    setState(STATE_EXPANDED);
                } else {
                    setState(STATE_ANCHOR);
                }
            } else {
                if (Math.abs(currentTop - mMaxOffset) < Math.abs(currentTop - anchorOffect)) {
                    setState(STATE_COLLAPSED);
                } else {
                    setState(STATE_ANCHOR);
                }
            }
        }
    }

    @Override
    public void setAnchorHeight(int anchorHeight) {
        int oldHeight = mAnchorHeight;
        super.setAnchorHeight(anchorHeight);
        anchorOffect = mParentHeight - anchorHeight;
        if (oldHeight == anchorHeight) {
            return;
        }
        if (mViewRef != null && mState == STATE_ANCHOR && mViewRef.get() != null) {
            V child = mViewRef.get();
            // 默认执行动画
            boolean animate = true;
            if (animate) {
                int top = anchorOffect;
                setStateInternal(STATE_SETTLING);
                if (mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
                    ViewCompat.postOnAnimation(child, new SettleRunnable(child, STATE_ANCHOR));
                }
            } else {
                mViewRef.get().requestLayout();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (mViewDragHelper == null) {
            mViewDragHelper = ViewDragHelper.create(parent, mDelegateCallback);
        }
        return super.onInterceptTouchEvent(parent, child, event);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (mViewDragHelper == null) {
            mViewDragHelper = ViewDragHelper.create(parent, mDelegateCallback);
        }
        return super.onTouchEvent(parent, child, event);
    }

    /**
     * @param top
     */
    @Override
    public void dispatchOnSlide(int top) {
        View bottomSheet = mViewRef.get();
        float percent = (mMaxOffset - top) * 1f / (mMaxOffset - anchorOffect);
        percent = Math.min(percent, 1);
        if (mCallback != null) {
            mCallback.onSlide(bottomSheet, percent);
        }
    }

    @Override
    public void setPeekHeight(int peekHeight) {
        if (mPeekHeight != peekHeight) {
            mPeekHeight = Math.max(0, peekHeight);
            mMaxOffset = mParentHeight - peekHeight;
            if (mViewRef != null && mState == STATE_COLLAPSED && mViewRef.get() != null) {
                V child = mViewRef.get();
                // 默认执行动画
                boolean animate = true;
                if (animate) {
                    int top = mMaxOffset;
                    setStateInternal(STATE_SETTLING);
                    if (mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
                        ViewCompat.postOnAnimation(child, new SettleRunnable(child, STATE_COLLAPSED));
                    }
                } else {
                    mViewRef.get().requestLayout();
                }
            }
        }
    }

    public void setState(@AnchorSheetBehavior.State int state) {
        // state不变,但peekHeight发生改变时,移动到新的位置
        if (mViewRef != null && mViewRef.get() != null && state == mState) {
            if (state == STATE_COLLAPSED
                    || state == STATE_EXPANDED
                    || state == STATE_ANCHOR
                    || (mHideable && state == STATE_HIDDEN)) {
                V child = mViewRef.get();
                int top;
                if (state == STATE_COLLAPSED) {
                    top = mMaxOffset;
                } else if (state == STATE_EXPANDED) {
                    top = mMinOffset;
                } else if (mHideable && state == STATE_HIDDEN) {
                    top = mParentHeight;
                } else if (state == STATE_ANCHOR) {
                    top = mParentHeight - mAnchorHeight;
                } else {
                    throw new IllegalArgumentException("Illegal state argument: " + state);
                }

                if (top != child.getTop()) {
                    setStateInternal(STATE_SETTLING);
                    if (mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
                        ViewCompat.postOnAnimation(child, new SettleRunnable(child, state));
                        ViewCompat.postOnAnimation(child, new SettleRunnable(child, state));
                    }
                    return;
                }
            }
        }

        super.setState(state);
    }

    public class SettleRunnable implements Runnable {

        private final View mView;

        @AnchorSheetBehavior.State
        private final int mTargetState;

        SettleRunnable(View view, @AnchorSheetBehavior.State int targetState) {
            mView = view;
            mTargetState = targetState;
        }

        @Override
        public void run() {
            if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(mView, this);
            } else {
                setStateInternal(mTargetState);
            }
        }
    }
}
