package com.augurit.agmobile.gzpssb.uploadfacility.view.myuploadlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.uploadfacility.model.HangUpWellBean;
import com.augurit.agmobile.gzpssb.uploadfacility.service.SewerageHangUpWellService;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 排水户关联接户井页面
 *
 * @PROJECT GZPSH
 * @USER Augurit
 * @CREATE 2021/3/31 10:00
 */
public class SewerageHangUpWellListFragment extends Fragment {

    private XRecyclerView rvDatas;
    private SimpleAdapter adptDatas;

    private SewerageHangUpWellService service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rvDatas = new XRecyclerView(getContext());
        rvDatas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDatas.setItemAnimator(null);
        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rvDatas.setLayoutParams(params);
        adptDatas = new SimpleAdapter();
        rvDatas.setAdapter(adptDatas);
        rvDatas.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
        return rvDatas;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        service = new SewerageHangUpWellService(getContext());
    }

    // ==============================自定义方法==============================

    private void requestDatas() {
//        service.getHookUpWellBeans()
    }

    // ==============================自定义类==============================

    private static class SimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<HangUpWellBean> datas = new ArrayList<>();

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        // ==============================自定义方法==============================

        public void update(List<HangUpWellBean> newDatas) {
            final int initItemCoun = getItemCount();
            datas.clear();
            if (newDatas == null || newDatas.isEmpty()) {
                notifyItemRangeRemoved(0, initItemCoun);
            } else {
                datas.addAll(newDatas);
                notifyItemRangeChanged(0, datas.size());
            }
        }

        // ==============================自定义类==============================

        private static class SimpleViewHolder extends RecyclerView.ViewHolder {

            private TextView name;
            private TextView type;
            private TextView drainageUnitName;
            private TextView drainageUnitType;
            private TextView time;
            private TextView addr;

            public SimpleViewHolder(View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.tv_left_up);
                type = itemView.findViewById(R.id.tv_right_up);
                drainageUnitName = itemView.findViewById(R.id.tv_hook_name);
                drainageUnitType = itemView.findViewById(R.id.tv_left_down);
                time = itemView.findViewById(R.id.tv_right_down);
                addr = itemView.findViewById(R.id.tv_mark_id);
            }

            public void setData() {

            }
        }
    }
}
