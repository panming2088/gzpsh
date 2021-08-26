package com.augurit.agmobile.gzps.drainage_unit_monitor.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.util.ListUtil;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjJgListBean;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.am.fw.utils.TimeUtil;
import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

/**
 * @author: liangsh
 * @createTime: 2021/5/8
 */
public class JbjMonitorListAdapter extends RecyclerView.Adapter<JbjMonitorListAdapter.Holder> {

    private List<JbjJgListBean> data;

    private OnRecyclerItemClickListener<JbjJgListBean> onRecyclerItemClickListener;

    private boolean isRain = false; //雨水
    private String subtype;

    public JbjMonitorListAdapter(boolean isRain, String subtype) {
        this.isRain = isRain;
        this.subtype = subtype;
    }

    public void notifyDataSetChanged(List<JbjJgListBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<JbjJgListBean> data) {
        if (this.data == null) {
            this.data = data;
        } else {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jbj_monitor_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final JbjMonitorListAdapter.Holder holder, int position) {
        final JbjJgListBean jgData = data.get(position);

        if(jgData.getState() != null) {
            holder.tv_state.setVisibility(View.VISIBLE);
            if (jgData.getState().equals("3")) {
                holder.tv_state.setText("已办结");
                holder.tv_state.setEnabled(true);
            } else {
                holder.tv_state.setText("处理中");
                holder.tv_state.setEnabled(false);
            }
        } else {
            holder.tv_state.setVisibility(View.GONE);
        }


        if (isRain) {
            holder.tv_key1.setText("晴天是否有水流动：");
            if (!TextUtils.isEmpty(jgData.getQtsfys()) && jgData.getQtsfys().equals("1")) {
                holder.tv_value1.setText("是");
            } else {
                holder.tv_value1.setText("否");
            }
            holder.tv_key2.setText(subtype + "是否被堵塞：");
            if (!TextUtils.isEmpty(jgData.getSfds()) && jgData.getSfds().equals("1")) {
                holder.tv_value2.setText("是");
            } else {
                holder.tv_value2.setText("否");
            }
            holder.tv_key3.setText("氨氮浓度：");
            if (!TextUtils.isEmpty(jgData.getAd())) {
                holder.tv_value3.setText(jgData.getAd() + "mg/L");
            } else {
                holder.tv_value3.setText("");
            }
            holder.tv_key4.setText("COD浓度：");
            if (!TextUtils.isEmpty(jgData.getCod())) {
                holder.tv_value4.setText(jgData.getCod() + "mg/L");
            } else {
                holder.tv_value4.setText("");
            }
        } else {
            holder.tv_key1.setText(subtype + "是否被堵塞：");
            if (!TextUtils.isEmpty(jgData.getSfds()) && jgData.getSfds().equals("1")) {
                holder.tv_value1.setText("是");
            } else {
                holder.tv_value1.setText("否");
            }
            holder.tv_key2.setText(subtype + "管道管径：");
            if (!TextUtils.isEmpty(jgData.getGdgj())) {
                holder.tv_value2.setText(jgData.getGdgj() + "mm");
            } else {
                holder.tv_value2.setText("");
            }
            holder.tv_key3.setText("氨氮浓度：");
            if (!TextUtils.isEmpty(jgData.getAd())) {
                holder.tv_value3.setText(jgData.getAd() + "mg/L");
            } else {
                holder.tv_value3.setText("");
            }
            holder.tv_key4.setText("日污水流量：");
            if (!TextUtils.isEmpty(jgData.getRwsll())) {
                holder.tv_value4.setText(jgData.getRwsll() + "吨");
            } else {
                holder.tv_value4.setText("");
            }
        }

        holder.tv_jgsj.setText(TimeUtil.getStringTimeYMDSFromDate(new Date(jgData.getJgsj())));

        String imageUrl = "";
        if(ListUtil.isNotEmpty(jgData.getAttachments())){
            JbjJgListBean.Attachment attachment = jgData.getAttachments().get(0);
            if(!TextUtils.isEmpty(attachment.getThumPath())){
                imageUrl = attachment.getThumPath();
            }
            if(TextUtils.isEmpty(imageUrl)){
                imageUrl = attachment.getAttPath();
            }
        }
        if(TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(jgData.getImgPath())){
            imageUrl = jgData.getImgPath();
        }
        if(TextUtils.isEmpty(imageUrl)) {
            holder.iv_photo.setVisibility(View.GONE);
            holder.tv_photo_empty.setVisibility(View.VISIBLE);
        } else {
            holder.iv_photo.setVisibility(View.VISIBLE);
            holder.tv_photo_empty.setVisibility(View.GONE);
            Context context = holder.itemView.getContext();
            if (context != null && !(context instanceof Activity && ((Activity) context).isDestroyed())) {
                Glide.with(holder.itemView.getContext()).load(imageUrl)
                        .placeholder(com.augurit.am.cmpt.R.drawable.common_ic_load_pic)
                        .error(com.augurit.am.cmpt.R.drawable.common_ic_load_pic_error1)
                        .centerCrop()
                        .into(holder.iv_photo);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(v, holder.getAdapterPosition(), jgData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ListUtil.isNotEmpty(data)) {
            return data.size();
        }
        return 0;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener<JbjJgListBean> onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView tv_jgsj;
        TextView tv_state;
        ImageView iv_photo;
        TextView tv_photo_empty;
        TextView tv_key1;
        TextView tv_value1;
        TextView tv_key2;
        TextView tv_value2;
        TextView tv_key3;
        TextView tv_value3;
        TextView tv_key4;
        TextView tv_value4;

        Holder(View itemView) {
            super(itemView);
            tv_jgsj = itemView.findViewById(R.id.tv_jgsj);
            tv_state = itemView.findViewById(R.id.tv_state);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            tv_photo_empty = itemView.findViewById(R.id.tv_photo_empty);
            tv_key1 = itemView.findViewById(R.id.tv_key1);
            tv_value1 = itemView.findViewById(R.id.tv_value1);
            tv_key2 = itemView.findViewById(R.id.tv_key2);
            tv_value2 = itemView.findViewById(R.id.tv_value2);
            tv_key3 = itemView.findViewById(R.id.tv_key3);
            tv_value3 = itemView.findViewById(R.id.tv_value3);
            tv_key4 = itemView.findViewById(R.id.tv_key4);
            tv_value4 = itemView.findViewById(R.id.tv_value4);
        }
    }
}
