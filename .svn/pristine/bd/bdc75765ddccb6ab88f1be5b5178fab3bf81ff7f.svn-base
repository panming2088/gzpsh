package com.augurit.agmobile.gzpssb.uploadevent.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.util.ListUtil;
import com.augurit.agmobile.gzpssb.journal.model.PSHCountByType;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.PshList;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfo;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.agmobile.patrolcore.search.view.GlideRoundTransform;
import com.augurit.am.fw.utils.TimeUtil;
import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

/**
 * @author: liangsh
 * @createTime: 2021/5/24
 */
public class PSHEventSelectAdapter extends RecyclerView.Adapter<PSHEventSelectAdapter.Holder> {

    private List<PshList.PshInfo> pshInfoList;

    private OnRecyclerItemClickListener<PshList.PshInfo> onRecyclerItemClickListener;

    public void notifyDataSetChanged(List<PshList.PshInfo> pshInfoList) {
        this.pshInfoList = pshInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_event_psh_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final int pos = position;
        final PshList.PshInfo pshInfo = pshInfoList.get(position);
        holder.tv_name.setText(pshInfo.getName());
        if("0".equals(pshInfo.getPshDj())){
            holder.tv_psh_level.setText("一级");
        } else {
            holder.tv_psh_level.setText("二级");
        }
        holder.tv_type.setText(pshInfo.getType());
        holder.tv_addr.setText(pshInfo.getAddr());
        holder.tv_sbr.setText(pshInfo.getMarkPerson());
        holder.tv_sbsj.setText(TimeUtil.getStringTimeYMDS(new Date(pshInfo.getMarkTime())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(v, pos, pshInfo);
                }
            }
        });
        if(holder.itemView.getContext() != null && !(holder.itemView.getContext() instanceof Activity && ((Activity) holder.itemView.getContext()).isDestroyed())){
            Glide.with(holder.itemView.getContext())
                    .load(pshInfo.getImagePath())
                    .error(R.mipmap.patrol_ic_load_fail)
//                    .transform(new GlideRoundTransform(mContext))
                    .into(holder.iv_photo);
        }
    }

    @Override
    public int getItemCount() {
        if (ListUtil.isNotEmpty(pshInfoList)) {
            return pshInfoList.size();
        }
        return 0;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener<PshList.PshInfo> onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    static class Holder extends RecyclerView.ViewHolder {

        ImageView iv_photo;
        TextView tv_name;
        TextView tv_psh_level;
        TextView tv_type;
        TextView tv_addr;
        TextView tv_sbr;
        TextView tv_sbsj;

        public Holder(View itemView) {
            super(itemView);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_psh_level = itemView.findViewById(R.id.tv_psh_level);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_addr = itemView.findViewById(R.id.tv_addr);
            tv_sbr = itemView.findViewById(R.id.tv_sbr);
            tv_sbsj = itemView.findViewById(R.id.tv_sbsj);
        }
    }
}
