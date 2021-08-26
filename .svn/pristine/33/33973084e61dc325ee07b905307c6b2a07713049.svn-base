package com.augurit.agmobile.gzpssb.jbjpsdy;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.jbjpsdy.model.JbjPsdySewerageUserListHeaderBean;
import com.augurit.agmobile.gzpssb.jbjpsdy.service.JbjPsdyService;
import com.augurit.agmobile.gzpssb.jbjpsdy.widget.JbjPsdySewerageUserListHeaderNewView;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @PROJECT GZPS
 * @USER Augurit
 * @CREATE 2020/11/9 11:15
 */
public class JbjPsdySewerageUserListNewView extends LinearLayout {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_SETTING_DATA = 2;

    public static final int PAGE_SIZE = 10;

    private JbjPsdySewerageUserListHeaderNewView mHeader;
    private XRecyclerView mRvSwerageUserList;
    private JbjPsdySewerageUserListAdapter mAdptSwerageUserList;
    private JbjPsdySewerageUserListAdapter.OnSewerageUserListItemClickListener mOnItemClickListener;

    //    private View mEmptyView;
    private ProgressLinearLayout mProgressBar;

    private Component mCurrComponent;
    private JbjPsdyService mRequestService;

    private final ArrayList<JbjPsdySewerageUserListHeaderBean> mHeaderBeans = new ArrayList<>(14);

    private final ArrayList<ArrayList<SewerageUserEntity>> mAllSewerageUsers = new ArrayList<>(14);
    private ArrayList<SewerageUserEntity> mShowSewerageUsers;
    private final List<Integer> mAllSewerageUserFirstVisiblePositions = new ArrayList<>(14);
    private int mCurrSelectedTypedIndex = -1;

    private int mCurrState = STATE_NORMAL;

    /**
     * 控制列表跳转到指定位置
     * 一般只有在
     */
    private boolean mIsNeedChangePosition = false;

    /**
     * 正在加载中的数据的类型，避免切换了以后，数据还在加载中，导致错乱。
     */
    private boolean mIsFirstLoadData = true;
    private View mBottomView;

    public JbjPsdySewerageUserListNewView(Context context) {
        super(context);
        init(context);
    }

    public JbjPsdySewerageUserListNewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JbjPsdySewerageUserListNewView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setCurrentComponent(Component component) {
//        if (mCurrComponent == component) {
//            return;
//        }
        mCurrComponent = component;
        reset();
        // 接下来应该要开始进行数据查询了
        // 不用了，上面默认了会重新点击“全部”Tab，会在回调那里去重新获取数据的。
        loadDataByType();
    }

    private void reset() {
        mAllSewerageUsers.clear();
        mAdptSwerageUserList.update(null);
        mShowSewerageUsers = null;
        mIsFirstLoadData = true;
        // emmm.....这个要不要重置为false呢。。。按理说，这个在数据请求结束完毕以后，会自动置为false的。
//        mIsLoading = false;
        mAllSewerageUserFirstVisiblePositions.clear();
        mCurrSelectedTypedIndex = -1;
        mHeader.reset();
    }

    public void setOnItemClickListener(JbjPsdySewerageUserListAdapter.OnSewerageUserListItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 处理请求接口返回的结果
     */
    public void handleSewerageUserResultEntity(SewerageUserResultNewEntity resultEntity) {
        if (mCurrSelectedTypedIndex == -1) {
            // 第一次加载
            final List<Pair<String, Integer>> typeCounts = resultEntity.getTypeCounts();
            mHeaderBeans.clear();
            mAllSewerageUsers.clear();
            adjustDrainageUserListViewHeight(0);
            if (typeCounts != null && typeCounts.size() > 0) {
                for (Pair<String, Integer> typeCount : typeCounts) {
                    mHeaderBeans.add(new JbjPsdySewerageUserListHeaderBean(typeCount.first, typeCount.first, typeCount.second));
                    mAllSewerageUsers.add(new ArrayList<SewerageUserEntity>());
                }
                mHeader.setHeaderDatas(mHeaderBeans);
                mShowSewerageUsers = mAllSewerageUsers.get(0);
                mCurrSelectedTypedIndex = 0;

                // "全部"对应的值
                Integer second = typeCounts.get(0).second;
                adjustDrainageUserListViewHeight(second);
            } else {
                mShowSewerageUsers = new ArrayList<>(0);
            }
        }
        mShowSewerageUsers.addAll(resultEntity.getSewerageUserEntities());
//        if (mCurrSelectedTypedIndex == mHeader.getSelectedType()) {
//            mAdptSwerageUserList.update(mShowSewerageUsers);
        showDataOfSelectedType();
//        }
        mCurrState = STATE_NORMAL;
        if (mShowSewerageUsers == null || mShowSewerageUsers.isEmpty()) {
            showLoadedEmpty();
        } else {
            mProgressBar.showContent();
//            mAdptSwerageUserList.update(mShowSewerageUsers);
        }
//        mHeader.setSelectable(true);
//        mEmptyView.setVisibility((mShowSewerageUsers == null || mShowSewerageUsers.isEmpty()) ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 显示当前选择类型的排水户数据
     */
    public void showDataOfSelectedType() {
        if (mShowSewerageUsers == null) {
            return;
        }
        if (mShowSewerageUsers.isEmpty()) {
            showLoadedEmpty();
//            mEmptyView.setVisibility(View.VISIBLE);
            loadDataByType();
        } else {
//            mEmptyView.setVisibility(View.INVISIBLE);
            mProgressBar.showContent();
            mRvSwerageUserList.reset();
            mAdptSwerageUserList.update(mShowSewerageUsers);
            if (mIsNeedChangePosition) {
                mIsNeedChangePosition = false;
                int firstVisibleVisiblePosition = getFirstVisibleVisiblePosition();
                if (firstVisibleVisiblePosition < 0 || firstVisibleVisiblePosition >= mShowSewerageUsers.size()) {
                    return;
                }
                mRvSwerageUserList.scrollToPosition(firstVisibleVisiblePosition);
            }
        }
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.drainage_view_jbjpsdy_sewerage_user_list_new, this);
        mBottomView = View.inflate(context, R.layout.drainage_bottom_list_view, null);
        mRequestService = new JbjPsdyService(context);
        initView();

//        loadAllData();
    }

    private void initView() {
        mHeader = findViewById(R.id.tabHeader);
        mHeader.setOnHeaderSelectedListener(new JbjPsdySewerageUserListHeaderNewView.BaseOnHeaderSelectedListener() {
            @Override
            public void onHeaderSelected(int index) {
                mCurrSelectedTypedIndex = index;
                mShowSewerageUsers = mAllSewerageUsers.get(index);
                mIsNeedChangePosition = true;
//                loadDataByType();
                showDataOfSelectedType();
            }

        });

        mRvSwerageUserList = (XRecyclerView) findViewById(R.id.rvList);
        mRvSwerageUserList.setNestedScrollingEnabled(true);
        mRvSwerageUserList.setLoadingMoreEnabled(true);
        mRvSwerageUserList.setPullRefreshEnabled(false);
//        mRvSwerageUserList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mRvSwerageUserList.setHasFixedSize(true);
        mRvSwerageUserList.getItemAnimator().setChangeDuration(0);
        mRvSwerageUserList.getItemAnimator().setRemoveDuration(0);
        mRvSwerageUserList.getItemAnimator().setMoveDuration(0);
        mRvSwerageUserList.getItemAnimator().setAddDuration(0);
        mRvSwerageUserList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                // 保存当前类型的滑动位置
                RecyclerView.LayoutManager layoutManager = mRvSwerageUserList.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    int firstVisiblelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    setFirstVisiblePosition(firstVisiblelyVisibleItemPosition);
                }

                // 停止/启动图片加载，避免滑动状态下，还对图片进行加载。
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    mAdptSwerageUserList.startLoadImg();
                    // 启动图片加载时，已加载完的图片，是不会再次加载的，所以，在这里刷新一下可视区域内所有的数据。
//                    if (layoutManager instanceof LinearLayoutManager) {
//                        int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
//                        int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
//                        int itemCount = lastVisibleItemPosition - firstVisibleItemPosition;
//                        if (firstVisibleItemPosition >= 0) {
//                            mAdptSwerageUserList.notifyItemRangeChanged(firstVisibleItemPosition, itemCount + 1);
//                        }
//                    }
                } else {
                    mAdptSwerageUserList.stopLoadImg();
                }
            }

//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                RecyclerView.LayoutManager layoutManager = mRvSwerageUserList.getLayoutManager();
//                if (layoutManager instanceof LinearLayoutManager) {
//                    int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
//                    if (lastCompletelyVisibleItemPosition >= mShowSewerageUsers.size() - 1) {
//                        // 到达倒数最后一个的时候，才开始加载下一页数据
//                        loadDataByType();
//                    }
//                }
//            }
        });
        mAdptSwerageUserList = new JbjPsdySewerageUserListAdapter(getContext(), new ArrayList<SewerageUserEntity>());
        mRvSwerageUserList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvSwerageUserList.setAdapter(mAdptSwerageUserList);
        mAdptSwerageUserList.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                SewerageUserEntity entity = mAdptSwerageUserList.getDataList().get(position - 1);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, null, entity);
                }
            }
        });
        mAdptSwerageUserList.setOnPshItemClickListener(new JbjPsdySewerageUserListAdapter.OnSewerageUserListItemClickListener() {
            @Override
            public void onItemClick(View v, RecyclerView.ViewHolder holder, SewerageUserEntity entity) {
//                if (mOnItemClickListener != null) {
//                    mOnItemClickListener.onItemClick(v, holder, entity);
//                }
            }

            @Override
            public void onLocation(View v, RecyclerView.ViewHolder holder, SewerageUserEntity entity) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onLocation(v, holder, entity);
                }
            }
        });

//        mEmptyView = findViewById(R.id.emptyView);
//        ((TextView) findViewById(R.id.text_title)).setText("查无数据");
////        findViewById(R.id.text_content).setVisibility(View.GONE);
//        Button mBtnRefresh = findViewById(R.id.button_retry);
//        mBtnRefresh.setVisibility(View.VISIBLE);
//        mBtnRefresh.setText("刷新");
//        mBtnRefresh.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadDataByType();
//            }
//        });
        mProgressBar = findViewById(R.id.progressbar);
        mRvSwerageUserList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                loadDataByType();
            }
        });
    }

    private void loadAllData() {
        mRequestService.getNewAllSewerageUsers(1, 10, String.valueOf(mCurrComponent.getObjectId()));
    }

    /**
     * 根据不同类型加载数据
     */
    private void loadDataByType() {
        if (mCurrState != STATE_NORMAL) {
            return;
        }
        String type = null;
        if (mCurrSelectedTypedIndex != -1) {
            // 默认不用检查这个
            final JbjPsdySewerageUserListHeaderBean headerBean = mHeaderBeans.get(mCurrSelectedTypedIndex);
            type = headerBean.getText();
            if (mShowSewerageUsers == null) return;
            if (!mIsFirstLoadData && mShowSewerageUsers.size() >= headerBean.getValue()) {
                // 如果不是首次加载，且已获取所有数据的最大值，则不再获取数据
                mRvSwerageUserList.setNoMore(true);
                mAdptSwerageUserList.update(mShowSewerageUsers);
                mRvSwerageUserList.loadMoreComplete();
                return;
            }
        }
        mHeader.setSelectable(false);
        mRvSwerageUserList.setLoadingMoreEnabled(true);
        mCurrState = STATE_LOADING;
        final int pageNo;
        if (mShowSewerageUsers == null) {
            pageNo = 1;
        } else {
            pageNo = getPageNo(mShowSewerageUsers.size());
        }
        if (pageNo == 1) {
            mProgressBar.showLoading();
        }
        mRequestService.getNewSewerageUsersByType(pageNo, PAGE_SIZE, String.valueOf(mCurrComponent.getObjectId()), type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SewerageUserResultNewEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showLoadedError(e.getMessage());
                        mHeader.setSelectable(true);
                        mCurrState = STATE_NORMAL;
                        if (pageNo == 1) {
                            showLoadedError(e.getLocalizedMessage());
                        } else {
                            ToastUtil.shortToast(getContext().getApplicationContext(), "加载更多失败");
                        }
                    }

                    @Override
                    public void onNext(SewerageUserResultNewEntity sewerageUserResultEntity) {
                        if (mIsFirstLoadData) {
                            mIsFirstLoadData = false;
                        }
                        // 设置数据
                        mCurrState = STATE_SETTING_DATA;
                        if (sewerageUserResultEntity == null || ListUtil.isEmpty(sewerageUserResultEntity.getSewerageUserEntities()) && pageNo > 1) {
                            mRvSwerageUserList.setNoMore(true);
                            return;
                        }
                        mHeader.setSelectable(true);
                        mRvSwerageUserList.setNoMore(false);
                        handleSewerageUserResultEntity(sewerageUserResultEntity);
                    }
                });
    }

    private int getPageNo(int currTotal) {
//        return currTotal == 0 ? 1 : (int) Math.ceil(currTotal / PAGE_SIZE);
        return currTotal / PAGE_SIZE + 1;
    }

    private void setFirstVisiblePosition(int position) {
//        switch (mHeader.getSelectedType()) {
//            case SewerageUserEntity.TYPE_CATERING_TRADE:
//                mCateringTradeSewerageFirstVisiblePosition = position;
//                break;
//            case SewerageUserEntity.TYPE_PRECIPITATE:
//                mPrecipitateSewerageUserFirstVisiblePosition = position;
//                break;
//            case SewerageUserEntity.TYPE_DANGER:
//                mDangerSewerageUserFirstVisiblePosition = position;
//                break;
//            case SewerageUserEntity.TYPE_LIFE:
//                mLifeSewerageUserFirstVisiblePosition = position;
//                break;
//            case SewerageUserEntity.TYPE_UNKNOW:
//            default:
//                mAllSewerageUserFirstVisiblePosition = position;
//                break;
//        }
    }

    private int getFirstVisibleVisiblePosition() {
        int position = -1;
//        switch (mHeader.getSelectedType()) {
//            case SewerageUserEntity.TYPE_CATERING_TRADE:
//                position = mCateringTradeSewerageFirstVisiblePosition;
//                break;
//            case SewerageUserEntity.TYPE_PRECIPITATE:
//                position = mPrecipitateSewerageUserFirstVisiblePosition;
//                break;
//            case SewerageUserEntity.TYPE_DANGER:
//                position = mDangerSewerageUserFirstVisiblePosition;
//                break;
//            case SewerageUserEntity.TYPE_LIFE:
//                position = mLifeSewerageUserFirstVisiblePosition;
//                break;
//            case SewerageUserEntity.TYPE_UNKNOW:
//            default:
//                position = mAllSewerageUserFirstVisiblePosition;
//                break;
//        }
        return position;
    }

    public void showLoadedEmpty() {
        mProgressBar.showError("", "暂无数据", "刷新", new OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDataByType();
            }
        });
    }

    public void showLoadedError(String errorReason) {
        mProgressBar.showError("获取数据失败", errorReason, "刷新", new OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDataByType();
            }
        });
    }

    private int headerHeight = 0;
    private int itemHeight = 0;
    private int minHeight = 0;

    private void adjustDrainageUserListViewHeight(int itemCount) {
        if (headerHeight == 0) {
            headerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getContext().getResources().getDisplayMetrics());
        }
        if (itemHeight == 0) {
            itemHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getContext().getResources().getDisplayMetrics());
        }
        if (minHeight == 0) {
            minHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getContext().getResources().getDisplayMetrics());
        }
        final int initItemHeight;
        if (itemCount <= 0) {
//            initItemHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
            initItemHeight = minHeight;
        } else if (itemCount < 3) {
            initItemHeight = (int)(itemHeight * itemCount + headerHeight);
        } else {
            initItemHeight = (int) (itemHeight * 2.5 + headerHeight);
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = initItemHeight;
        setLayoutParams(layoutParams);
    }
}
