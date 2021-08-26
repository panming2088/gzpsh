package com.augurit.agmobile.gzpssb.uploadevent.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.util.ListUtil;
import com.augurit.agmobile.gzpssb.journal.model.PSHCountByType;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;

import java.util.List;

/**
 * @author: liangsh
 * @createTime: 2021/5/24
 */
public class PSHEventSelectTypeAdapter extends RecyclerView.Adapter<PSHEventSelectTypeAdapter.Holder> {

    private List<PSHCountByType> typeList;

    private OnRecyclerItemClickListener<PSHCountByType> onRecyclerItemClickListener;

    private int mSelectedPosition = -1;

    public void notifyDataSetChanged(List<PSHCountByType> typeList) {
        this.typeList = typeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_event_psh_facility_type, null));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final int pos = position;
        final PSHCountByType type = typeList.get(position);
        holder.tv_name.setText(type.type);
        holder.tv_num.setText(type.count+"");
        if (mSelectedPosition == pos) {
            holder.tv_name.setSelected(true);
            holder.tv_num.setSelected(true);
            holder.tv_name.setTextColor(Color.WHITE);
        } else {
            holder.tv_name.setSelected(false);
            holder.tv_num.setSelected(false);
            holder.tv_name.setTextColor(Color.BLACK);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(v, pos, type);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ListUtil.isNotEmpty(typeList)) {
            return typeList.size();
        }
        return 0;
    }

    public void setSelectedPosition(int selectedPosition) {
        mSelectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener<PSHCountByType> onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_num;

        public Holder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_num = itemView.findViewById(R.id.tv_num);
        }
    }
}
