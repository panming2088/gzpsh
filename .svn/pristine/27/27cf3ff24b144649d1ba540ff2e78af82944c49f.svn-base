package com.augurit.agmobile.gzps.drainage_unit_monitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.LocationEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JBJ;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class JBJMonitorAdapter extends RecyclerView.Adapter<JBJMonitorAdapter.ViewHolder> {
    private Context context;
    private List<JBJ> mList = new ArrayList<>();
    private JBJ curLocationJBJ = null;

    private OnRecyclerItemClickListener<JBJ> onRecyclerItemClickListener;


    public JBJMonitorAdapter(Context context){
        this.context = context;
    }

    public void setList(List<JBJ> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addList(List<JBJ> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public JBJ getItem(int position){
        if(position < 0 || position >= mList.size()){
            return null;
        } else {
            return mList.get(position);
        }
    }

    public void setMonitored(long objectId){
        for (JBJ jbj : mList) {
            if(jbj.getObjectid() == objectId){
                jbj.setSfjg(1);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_monitor_jbj, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final JBJ item = mList.get(position);
        holder.tvSeq.setText((position + 1) + "");
        holder.tvType.setText(item.getSubtype() + "-" + item.getSort());
        holder.ivLocation.setOnClickListener(onClickListener);
        holder.ivLocation.setTag(item);
        holder.ivLocation.setImageResource(item.isSelected() ? R.mipmap.ic_location_p : R.mipmap.ic_location);
        if(item.getSfjg() == 0){
            holder.tvMonitorType.setText("未监管");
            holder.tvMonitorType.setTextColor(Color.parseColor("#FF9F00"));
            holder.tvMonitorType.setBackgroundResource(R.drawable.bg_rect_round_orange);
        } else {
            holder.tvMonitorType.setText("已监管");
            holder.tvMonitorType.setTextColor(Color.parseColor("#22AC38"));
            holder.tvMonitorType.setBackgroundResource(R.drawable.bg_rect_round_green);
        }
        holder.tvMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRecyclerItemClickListener != null){
                    onRecyclerItemClickListener.onItemClick(v, holder.getAdapterPosition(), item);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener<JBJ> onRecyclerItemClickListener){
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LocationEvent event = new LocationEvent();
            JBJ item = (JBJ)v.getTag();
            event.x = item.getX();
            event.y = item.getY();
            EventBus.getDefault().post(event);
            if(item != curLocationJBJ) {
                if (curLocationJBJ != null) {
                    curLocationJBJ.setSelected(false);
                }
                curLocationJBJ = item;
                curLocationJBJ.setSelected(true);
                notifyDataSetChanged();
            }

        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSeq;
        private TextView tvType;
        private TextView tvMonitorType;
        private ImageView ivLocation;
        private TextView tvMonitor;


        public ViewHolder(View itemView) {
            super(itemView);
            tvSeq = (TextView) itemView.findViewById(R.id.tv_seq);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);
            tvMonitorType = (TextView) itemView.findViewById(R.id.tv_monitor_type);
            ivLocation = (ImageView) itemView.findViewById(R.id.iv_location);
            tvMonitor = (TextView) itemView.findViewById(R.id.tv_monitor);
        }
    }
}
