package com.augurit.agmobile.gzpssb.jbjpsdy.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.gzpssb.common.callback.OnRecyclerItemClickListener;
import com.augurit.agmobile.gzpssb.jbjpsdy.model.JbjPsdySewerageUserListHeaderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @PROJECT GZPSH
 * @USER Augurit
 * @CREATE 2021/3/26 11:40
 */
public class JbjPsdySewerageUserListHeaderNewView extends RecyclerView {

    private HeaderAdapter adpt;
    private OnHeaderSelectedListener onHeaderSelectedListener;

    private boolean selectable = true;

    public JbjPsdySewerageUserListHeaderNewView(Context context) {
        super(context);
        init(context);
    }

    public JbjPsdySewerageUserListHeaderNewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JbjPsdySewerageUserListHeaderNewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // ==============================自定义方法==============================

    public void setHeaderDatas(List<JbjPsdySewerageUserListHeaderBean> headers) {
        adpt.setDatas(headers);
    }

    public void setOnHeaderSelectedListener(OnHeaderSelectedListener listener) {
        onHeaderSelectedListener = listener;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public void reset() {
        setHeaderDatas(null);
    }

    private void init(Context context) {
        initData();
        initView(context);

//        setTestData();
    }

    private void initData() {
        adpt = new HeaderAdapter();
    }

    private void initView(Context context) {
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        setAdapter(adpt);
        setItemAnimator(null);
        addOnItemTouchListener(new OnRecyclerItemClickListener(this) {

            @Override
            public void onItemClick(ViewHolder viewHolder) {
                if (!selectable) {
                    return;
                }
                final int selectedIndex = viewHolder.getLayoutPosition();
                adpt.setSelectedIndex(selectedIndex);
                if (onHeaderSelectedListener != null) {
                    onHeaderSelectedListener.onHeaderSelected(selectedIndex);
                }
            }
        });
    }

    private void setTestData() {
        final List<JbjPsdySewerageUserListHeaderBean> testDatas = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            testDatas.add(new JbjPsdySewerageUserListHeaderBean("标题" + i, "bt" + i, i));
        }
        setHeaderDatas(testDatas);
    }

    // ==============================自定义类==============================

    private static class HeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<JbjPsdySewerageUserListHeaderBean> datas = new ArrayList<>(10);
        private int currSelectedIndex = -1;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HeaderViewHolder(new JbjPsdySewerageUserListHeaderItemView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (holder instanceof HeaderViewHolder) {
                ((HeaderViewHolder) holder).setData(datas.get(position), currSelectedIndex == position);
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        // ==============================自定义方法==============================

        public void setDatas(List<JbjPsdySewerageUserListHeaderBean> newDatas) {
            final int initItemCount = getItemCount();
            datas.clear();
            if (newDatas == null || newDatas.isEmpty()) {
                notifyItemRangeRemoved(0, initItemCount);
            } else {
                currSelectedIndex = 0;
                datas.addAll(newDatas);
                notifyItemRangeInserted(0, datas.size());
            }
        }

        public void setSelectedIndex(int index) {
            if (currSelectedIndex == index) {
                return;
            }
            final int lastSelectedIndex = currSelectedIndex;
            currSelectedIndex = index;
            notifyItemChanged(lastSelectedIndex);
            notifyItemChanged(currSelectedIndex);
        }

        // ==============================自定义类==============================

        private static class HeaderViewHolder extends RecyclerView.ViewHolder {

            public HeaderViewHolder(View itemView) {
                super(itemView);
            }

            public void setData(JbjPsdySewerageUserListHeaderBean data, boolean isChecked) {
                if (itemView instanceof JbjPsdySewerageUserListHeaderItemView) {
                    ((JbjPsdySewerageUserListHeaderItemView) itemView).setData(data.getText(), data.getValue());
                }
                itemView.setSelected(isChecked);
            }
        }
    }

    public interface OnHeaderSelectedListener {
        public void onHeaderSelected(int index);
    }

    public static abstract class BaseOnHeaderSelectedListener implements OnHeaderSelectedListener {

    }
}
