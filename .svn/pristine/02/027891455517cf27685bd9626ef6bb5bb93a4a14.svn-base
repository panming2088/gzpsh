package com.augurit.agmobile.gzps.drainage_unit_monitor.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import retrofit2.http.PUT;

public class AutoLoadRecyclerView extends RecyclerView {
    // 最后一个完全可见项的位置
    private int lastCompletelyVisibleItemPosition;

    private static final int STATE_IDLE = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_NO_MORE = 2;
    private int state = STATE_IDLE;
    private AutoLoadListener loadMoreListener;

    public AutoLoadRecyclerView(Context context) {
        this(context, null);
    }

    public AutoLoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int visibleItemCount = getLayoutManager().getChildCount();
                int totalItemCount = getLayoutManager().getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (state == STATE_IDLE && visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1
                        && loadMoreListener != null) {
                        loadMoreListener.loadMore();
                        state = STATE_LOADING;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            }
        });
    }

    public void complete(){
        state = STATE_IDLE;
    }

    public void noMore(){
        state = STATE_NO_MORE;
    }

    public void refresh(){
        if(loadMoreListener != null){
            loadMoreListener.refresh();
            state = STATE_LOADING;
        }
    }

    public void setAutoLoadListenerListener(AutoLoadListener l){
        loadMoreListener = l;
    }

    public interface AutoLoadListener {
        void refresh();
        void loadMore();
    }

}
