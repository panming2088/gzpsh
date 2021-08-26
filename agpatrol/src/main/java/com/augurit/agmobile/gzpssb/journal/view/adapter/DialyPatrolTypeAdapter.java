package com.augurit.agmobile.gzpssb.journal.view.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.TypedValue;
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
 * @createTime: 2021/5/18
 */
public class DialyPatrolTypeAdapter extends RecyclerView.Adapter<DialyPatrolTypeAdapter.Holder> {

    private List<PSHCountByType> pshCountByTypeList;

    private OnRecyclerItemClickListener<PSHCountByType> onRecyclerItemClickListener;

    private int mSelectedPosition = -1;

    public void notifyDataSetChanged(List<PSHCountByType> pshCountByTypeList) {
        this.pshCountByTypeList = pshCountByTypeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialy_patrol_psh_type, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final PSHCountByType data = pshCountByTypeList.get(position);
        final int pos = position;
        holder.tv_type.setText(data.type);
        holder.tv_count.setText(data.count + "");
        if (mSelectedPosition == position) {
            holder.tv_type.setTextColor(Color.BLACK);
            holder.tv_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.tv_count.setTextColor(Color.BLACK);
            holder.tv_count.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            TextPaint paint = holder.tv_type.getPaint();
            paint.setFakeBoldText(true);
            paint = holder.tv_count.getPaint();
            paint.setFakeBoldText(true);
        } else {
            holder.tv_type.setTextColor(Color.GRAY);
            holder.tv_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_count.setTextColor(Color.GRAY);
            holder.tv_count.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            TextPaint paint = holder.tv_type.getPaint();
            paint.setFakeBoldText(false);
            paint = holder.tv_count.getPaint();
            paint.setFakeBoldText(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(v, pos, data);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ListUtil.isNotEmpty(pshCountByTypeList)) {
            return pshCountByTypeList.size();
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

        TextView tv_type;
        TextView tv_count;

        public Holder(View itemView) {
            super(itemView);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_count = itemView.findViewById(R.id.tv_count);
        }
    }
}
