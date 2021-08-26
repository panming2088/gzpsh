package com.augurit.agmobile.gzps.drainage_unit_monitor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.am.fw.cache.img.amimageloader.AMImageLoader;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.augurit.agmobile.gzps.uploadevent.util.CodeToStringUtils.getSpinnerValueByCode;

public class LGAdapter extends RecyclerView.Adapter<LGAdapter.ViewHolder> {
    private Context context;
    private List<PSHEventListItem> mList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public LGAdapter(Context context){
        this.context = context;
    }

    public void setList(List<PSHEventListItem> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addList(List<PSHEventListItem> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_monitor_lg, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PSHEventListItem item = mList.get(position);
        String componentType = item.getPshmc();
        String eventType = getSpinnerValuesByMultiCode(item.getWtlx());
        if (!StringUtil.isEmpty(item.getThumPath())) {
            AMImageLoader.loadStringRes(holder.ivPic, item.getThumPath());
        } else {
            AMImageLoader.loadResId(holder.ivPic, R.drawable.bg_loading);
        }

        //0(整改中),1(执法中),2（待审核）,3(审核通过)
        if ("0".equals(item.getState())) {
            holder.tvState.setText("办理状态：整改中");
        } else if ("1".equals(item.getState())) {
            holder.tvState.setText("办理状态：执法中");
        } else if ("2".equals(item.getState())) {
            holder.tvState.setText("办理状态：待审核");
        } else if ("3".equals(item.getState())) {
            holder.tvState.setText("办理状态：审核通过");
        } else {
            holder.tvState.setText("");
        }

        holder.tvAddress.setText("地址：" + item.getSzwz());
        holder.tvReporter.setText("上报人：" + item.getSbr());
        holder.tvReportTime.setText("上报时间：" + TimeUtil.getStringTimeYMDS(new Date(item.getSbsj())));

        holder.itemView.setOnClickListener(onClickListener);
        holder.itemView.setTag(position);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer)v.getTag();
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(v, mList.get(position), position);
            }
        }
    };

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 获取多个数据字典值，编码以英文逗号分隔开
     *
     * @param codes
     * @return
     */
    private String getSpinnerValuesByMultiCode(String codes) {
        if (StringUtil.isEmpty(codes)) {
            return "";
        }
        String[] codeArray = codes.split(",");
        String value = "";
        for (String code : codeArray) {
            value = value + "，" + getSpinnerValueByCode(code);
        }
        value = value.substring(1);
        return value;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivPic;
        private TextView tvAddress;
        private TextView tvReporter;
        private TextView tvState;
        private TextView tvReportTime;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvReporter = (TextView) itemView.findViewById(R.id.tv_reporter);
            tvState = (TextView) itemView.findViewById(R.id.tv_state);
            tvReportTime = (TextView) itemView.findViewById(R.id.tv_report_time);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, PSHEventListItem data, int position) ;
    }
}
