package com.augurit.agmobile.gzpssb.pshdoorno.doorlist.widge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.augurit.agmobile.gzps.R;
import com.augurit.am.cmpt.widget.bottomsheet.AnchorSheetBehavior;
import com.augurit.am.fw.utils.log.LogUtil;

/**
 * 上下拉抽屉布局(功能尚不完全，目前只考虑了展开状态的显示，展开状态设置底部View的高度即可，默认就是你设置那个view的高度多少，
 * 它展开之后就多少高度)
 * 使用:在xml文件下面  app:PullUpDrag_BottomBorderAnchorHeigth="30dp"添加这个，表示收缩状态时，默认的显示，
 * 其次是设置底部View的高度,底部View的高度是展开时底部View的整体高度
 * 然后一定要注意顺序！！注意顺序！顺序，重要的事说3遍，顺序变了的话，它显示可能就那么不尽人意了
 *
 * 实现逻辑:主要是依靠ViewDragHelper 获取手势操作，然后判断位置实现的，具体实现参照博客：https://www.jianshu.com/p/0e8ed99b4fb9
 * 参考博客：ViewDragHelper 类方法说明 https://blog.csdn.net/itermeng/article/details/52159637?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
 * 妈妈再也不用担心我自定义ViewGroup滑动View操作了
 * @author 创建人 ：ouyangzhibao
 * @version 1.0
 * @package 包名 ：com.viewdraghelpertest
 * @createTime 创建时间 ：2018/5/7
 * @modifyBy 修改人 ：ouyangzhibao
 * @modifyTime 修改时间 ：2018/5/7
 * @modifyMemo 修改备注：
 */
public class PullUpDragLayout extends ViewGroup {
    public static int EXPEND_SHEET=1;//完全展开状态
    public static int ANCHOR_SHEET=2;//伸缩状态

    private ViewDragHelper mViewDragHelper;//拖拽帮助类
    private View mBottomView;//底部内容View(bottomSheetLayout)
    private View mContentView;//内容View(bottomSheetLayout下面的那个布局)
    private int mBottomBorderHeigth = 20;//底部边界凸出的高度,默认是20
    private LayoutInflater mLayoutInflater;
    private Point mAutoBackBottomPos = new Point();
    private Point mAutoBackTopPos = new Point();
    private int mBoundTopY;
    private boolean isOpen;
    private OnStateListener mOnStateListener;
    private OnScrollChageListener mScrollChageListener;
    private boolean hasIntercepted=false;
    private int state=EXPEND_SHEET;//默认展开
    private boolean isExpend=false;//是否全部展开，默认为false
   /*Todo 3实现拖拽，要实现的功能需求：
   指定方向滑动
    边界检测、加速度检测
    手指抬起，自动展开/收缩
    点击Button，展开/关闭
    监听展开/关闭，上拉过程发生改变时回调*/
/*    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
       @Override
       public boolean tryCaptureView(View child, int pointerId) {
           return mBottomView == child;
       }


       //控制水平方向不滑动，上下滑动
       @Override
       public int clampViewPositionHorizontal(View child, int left, int dx) {
           final int leftBound = getPaddingLeft();
           final int rightBound = getWidth() - mBottomView.getWidth() - leftBound;
           final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
           return newLeft;
       }

       *//**
        * 决定拖拽的View在垂直方向上面移动到的位置
        * @param child
        * @param top
        * @param dy
        * @return
        *//*
       @Override
       public int clampViewPositionVertical(View child, int top, int dy) {
           int topBound = mContentView.getHeight() - mBottomView.getHeight();
           int bottomBound = mContentView.getHeight() - mBottomBorderHeigth;
           if(state==EXPEND_SHEET){
               return mBottomView.getMeasuredHeight();
           }else{
               return Math.min(bottomBound, Math.max(top, topBound));
           }

       }


       //手指释放的时候回调
       @Override
       public void onViewReleased(View releasedChild, float xvel, float yvel) {
           if (releasedChild == mBottomView) {
               if (yvel <0) { // 上拉
                   mViewDragHelper.settleCapturedViewAt(mAutoBackTopPos.x, mContentView.getMeasuredHeight()
                           - mBottomView.getMeasuredHeight());
                   isOpen = true;
                   if (mOnStateListener != null) mOnStateListener.open();
                   state=ANCHOR_SHEET;
               }else if(yvel==0){

               }else{
                   //下拉(设置展示为布局xml设置好的缩放高度)
                   //finalleft:距离左边的距离，finalTop距离顶端的距离
                   mViewDragHelper.settleCapturedViewAt(mAutoBackBottomPos.x, mContentView.getMeasuredHeight() - mBottomBorderHeigth);
                   isOpen = false;
                   isExpend=false;
                   if (mOnStateListener != null) mOnStateListener.close();
                   state=EXPEND_SHEET;
               }
             *//*  int top;

               int currentTop = releasedChild.getTop();

               mBoundTopY= Math.max(0, getMeasuredHeight() - mBottomBorderHeigth);
             int  mMinOffset = Math.max(0,  getMeasuredHeight() - mBottomView.getHeight());
             int mMaxOffset = Math.max(getMeasuredHeight()  - mBottomBorderHeigth, mMinOffset);
             int    anchorOffect = Math.max(0, getMeasuredHeight()  - mBottomBorderHeigth);
               if (yvel < 0 && currentTop < mBoundTopY) { // 上拉
                   top = mBottomBorderHeigth;
               } else if (yvel == 0.f) {
                   LogUtil.e("bottomsheet","yvel ==0 ------------------------------>");
                   if (Math.abs(currentTop - mMinOffset) < Math.abs(currentTop - anchorOffect)) {
                       top = mMinOffset;
                   } else {
                       top = anchorOffect;
                   }
               } else {
                   top = mMaxOffset;
               }
               mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);*//*
               invalidate();
           }
       }

       *//**
        *  * 当mBottomView的位置发生改变时回调
        *//*
       @Override
       public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
           if (changedView == mBottomView) {
               float startPosition = mContentView.getHeight() - mBottomView.getHeight();
               float endPosition = mContentView.getHeight() - mBottomBorderHeigth;
               float totalLength = endPosition - startPosition;
               float rate = 1 - ((top - startPosition) / totalLength);
               if (mScrollChageListener != null) {
                   mScrollChageListener.onScrollChange(rate);
               }
              *//* if(state==EXPEND_SHEET){
                   mViewDragHelper.settleCapturedViewAt(mAutoBackTopPos.x,mBottomView.getHeight());
               }*//*
           }
       }
   };*/

    public PullUpDragLayout(Context context) {
        super(context);
    }

    public PullUpDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initCustomAttrs(context, attrs);
    }

    public PullUpDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setOnStateListener(OnStateListener onStateListener) {
        mOnStateListener = onStateListener;
    }

    public void setScrollChageListener(OnScrollChageListener scrollChageListener) {
        mScrollChageListener = scrollChageListener;
    }

    private void init(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
     //   mViewDragHelper = ViewDragHelper.create(this, 1.0f, mCallback);
    }

    @Override
    public void computeScroll() {
     /*   if (mViewDragHelper.continueSettling(true)) { invalidate();
        }*/
    }

 /*也有问题，拦截导致布局卡顿,控件点击无法实现
 public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {
            if (inRangeOfView(mBottomView, ev)) {
                hasIntercepted = true;
            } else {
                hasIntercepted = false;
            }
         *//*   if(mViewDragHelper.shouldInterceptTouchEvent(ev)){
                hasIntercepted=true;
            }*//*

        return hasIntercepted;
    }*/

    /**
     * 判断当前滑动的View是否为sheetView
     *
     * @param view
     * @param ev
     * @return
     */
    private boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y || ev.getY() > (y + view.getHeight())) {
            return false;
        }else{
            if(x==0&&y==0){//强行判断当前点击的View不在里头(当sheetView消失的时候，即当前控件不在屏幕上时)
                return false;
            }else{
                return true;
            }

        }
    }

  /*  @Override
    public boolean onTouchEvent(MotionEvent event) {
  //      mViewDragHelper.processTouchEvent(event);
        return true;
    }*/

    /**
     * 切换底部View
     */
    public void toggleBottomView() {
        if (isOpen) {
            mViewDragHelper.smoothSlideViewTo(mBottomView, mAutoBackBottomPos.x, mAutoBackBottomPos.y);
            if (mOnStateListener != null) mOnStateListener.close();
        } else {
            mViewDragHelper.smoothSlideViewTo(mBottomView, mAutoBackTopPos.x, mAutoBackTopPos.y);
            if (mOnStateListener != null) mOnStateListener.open();
        }
        invalidate();
        isOpen = !isOpen;
    }

    /**
     * 初始化自定义属性，并实例化子View
     * @param context
     * @param attrs
     */
    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PullUpDragLayout);
        if (typedArray != null) {
            if (typedArray.hasValue(R.styleable.PullUpDragLayout_PullUpDrag_ContentView)) {
                inflateContentView(typedArray.getResourceId(R.styleable.PullUpDragLayout_PullUpDrag_ContentView, 0));
            }
            if (typedArray.hasValue(R.styleable.PullUpDragLayout_PullUpDrag_BottomView)) {
                inflateBottomView(typedArray.getResourceId(R.styleable.PullUpDragLayout_PullUpDrag_BottomView, 0));
            }
            if (typedArray.hasValue(R.styleable.PullUpDragLayout_PullUpDrag_BottomBorderAnchorHeigth)) {
                mBottomBorderHeigth = (int) typedArray.getDimension(R.styleable.PullUpDragLayout_PullUpDrag_BottomBorderAnchorHeigth, 20);
            }
            typedArray.recycle();
        }
    }

    private void inflateContentView(int resourceId) {
        mContentView = mLayoutInflater.inflate(resourceId, this, true);
    }


    // Todo 1.先从style文件中获取整体View的属性，整体View包括要弹出的bottoSheetLayout和其外部套着的View(内容View)

    private void inflateBottomView(int resourceId) {
        mBottomView = mLayoutInflater.inflate(resourceId, this, true);
    }

    //Todo 2、计算子View，还有子View在PullUpDragLayout怎么布局
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mContentView = getChildAt(0);
        mBottomView = getChildAt(1);
        //测量单个View，需要考虑当前ViewGroup的MeasureSpec和Padding,生成对应宽高的View
        measureChild(mBottomView, widthMeasureSpec, heightMeasureSpec);
        int bottomViewHeight = mBottomView.getMeasuredHeight();
        //测量单个View，需要考虑当前ViewGroup的MeasureSpec和Padding,生成对应宽高的View
        measureChild(mContentView, widthMeasureSpec, heightMeasureSpec);
        int contentHeight = mContentView.getMeasuredHeight();
        //设置当前View的大小
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), bottomViewHeight +contentHeight  + getPaddingBottom() + getPaddingTop());
    }

    /**
     * 排列子View的位置
     */
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        mContentView = getChildAt(0);
        mBottomView = getChildAt(1);
        mContentView.layout(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), mContentView.getMeasuredHeight());
        if(state==EXPEND_SHEET){
            //默认展开
            expendSheet();
            isExpend=true;
        }else{
            anchorSheet();
        }
        mAutoBackBottomPos.x = mBottomView.getLeft();
        mAutoBackBottomPos.y = mBottomView.getTop();

        mAutoBackTopPos.x = mBottomView.getLeft();
        mAutoBackTopPos.y = mContentView.getHeight() - mBottomView.getHeight();
        //mBoundTopY = mContentView.getHeight() - mBottomView.getHeight() / 2;
    }

    public void expendSheet(){

        mBottomView.layout(getPaddingLeft(), mContentView.getMeasuredHeight() - mBottomView.getMeasuredHeight(), getWidth() - getPaddingRight(),
                getMeasuredHeight() - mBottomBorderHeigth);
    }
    public void anchorSheet(){
        mBottomView.layout(getPaddingLeft(), mContentView.getMeasuredHeight() - mBottomBorderHeigth, getWidth() - getPaddingRight(),
                getMeasuredHeight() - mBottomBorderHeigth);
    }
    public void setState(int state){
        this.state=state;
    }

    public int getState() {
        return state;
    }

    public void dismiss(){
     //   mViewDragHelper.cancel();
    }

    public interface OnStateListener {
        void open();

        void close();
    }

    public interface OnScrollChageListener {
        void onScrollChange(float rate);
    }
}
