package com.augurit.agmobile.gzpssb.uploadevent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.constant.GzpsConstant;
import com.augurit.agmobile.gzps.uploadevent.model.EventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.am.fw.cache.img.amimageloader.AMImageLoader;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : taoerxiang
 * @data : 2017-11-11  12:04
 * @des :
 */

public class PSHEventListAdapter extends RecyclerView.Adapter<PSHEventListAdapter.MyViewHolder> {

    private int eventPState;
    private Context context;
    private List<PSHEventListItem> listData = new ArrayList<>();
    private OnRecyclerItemClickListener<PSHEventListItem> listener;

    public PSHEventListAdapter(List<PSHEventListItem> list, int eventPState, Context context) {
        this.listData = list;
        this.eventPState = eventPState;
        this.context = context;
    }

    public List<PSHEventListItem> getListData() {
        return listData;
    }

    public void setListData(List<PSHEventListItem> listData) {
        this.listData = listData;
    }

    public void refresh(List<PSHEventListItem> list) {
        this.listData = list;
        notifyDataSetChanged();
    }

    public void loadMore(List<PSHEventListItem> list) {
        try {
            if (listData == null) {
                listData = new ArrayList<>();
            }
            this.listData.addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    @Override
    public PSHEventListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.task_list_item, null);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PSHEventListItem eventListItem = listData.get(position);
        final int pos = position;
        String componentType = eventListItem.getPshmc();
        String eventType = getSpinnerValuesByMultiCode(eventListItem.getWtlx());
        if (!StringUtil.isEmpty(eventListItem.getThumPath())) {
            AMImageLoader.loadStringRes(holder.task_list_item_iv, eventListItem.getThumPath());
        } else {
            AMImageLoader.loadResId(holder.task_list_item_iv, R.drawable.bg_loading);
        }

        if(TextUtils.isEmpty(eventListItem.getWtly())){
            holder.ll_wtly.setVisibility(View.GONE);
        } else {
            holder.ll_wtly.setVisibility(View.VISIBLE);
            String wtly = "";
            if("0".equals(eventListItem.getWtly())) {
                wtly = "问题上报";
            } else if("1".equals(eventListItem.getWtly())) {
                wtly = "日常巡检";
            } else if("2".equals(eventListItem.getWtly())) {
                wtly = "排水单元监管";
            }
            holder.tv_wtly.setText(wtly);
        }

//        //0问题处理,1问题执法，2问题审核，3结束
//        if("0".equals(eventListItem.getState())){
//            holder.link.setText("问题处理");
//        }else if("1".equals(eventListItem.getState())){
//            holder.link.setText("问题执法");
//        }else if("2".equals(eventListItem.getState())){
//            holder.link.setText("问题审核");
//        }else{
//            holder.link.setText("结束");
//        }
//        holder.link.setText(eventListItem.getState());
        //0(整改中),1(执法中),2（待审核）,3(审核通过)
        if ("0".equals(eventListItem.getState())) {
            holder.link.setText("整改中");
        } else if ("1".equals(eventListItem.getState())) {
            holder.link.setText("执法中");
        } else if ("2".equals(eventListItem.getState())) {
            holder.link.setText("待审核");
        } else if ("3".equals(eventListItem.getState())) {
            holder.link.setText("审核通过");
        } else if("9".equals(eventListItem.getState())){
            holder.link.setText("待复核");
        } else {
            holder.link.setText("");
        }
        if (TextUtils.isEmpty(componentType) && TextUtils.isEmpty(eventType)) {
            holder.title_tv.setText("其他");
        } else if("other".equals(eventListItem.getSslx())){
            holder.title_tv.setText("其他" + "  " + eventType);
        }else if("lgjc".equals(eventListItem.getSslx())){
            holder.title_tv.setText("立管" + "  " + eventType);
        } else {
            holder.title_tv.setText(componentType + "  " + eventType);
        }
        holder.person_tv.setText(eventListItem.getSbr());
        holder.date_tv.setText(TimeUtil.getStringTimeYMDS(new Date(eventListItem.getSbsj())));
        holder.addr_tv.setText(eventListItem.getSzwz());
        if (eventPState == GzpsConstant.EVENT_P_STATE_HANDLING
                && GzpsConstant.EVENT_OPEN.equals(eventListItem.getState())) {
            holder.state_tv.setVisibility(View.VISIBLE);
            holder.state_tv.setText("待处理");
        } else {
            holder.state_tv.setVisibility(View.GONE);
            holder.state_tv.setText("");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, pos, eventListItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListUtil.isEmpty(listData) ? 0 : listData.size();
    }

    public String getSpinnerValueByCode(String code) {
        List<DictionaryItem> dictionaryItems = new TableDBService(context).getDictionaryByCode(code);
        if (ListUtil.isEmpty(dictionaryItems)) {
            return "";
        }
        DictionaryItem dictionaryItem = dictionaryItems.get(0);
        return dictionaryItem.getName();
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView task_list_item_iv;
        TextView title_tv;
        TextView link;
        TextView person_tv;
        TextView date_tv;
        TextView addr_tv;
        TextView state_tv;
        View ll_wtly;
        TextView tv_wtly;

        public MyViewHolder(View itemView) {
            super(itemView);
            task_list_item_iv = (ImageView) itemView.findViewById(R.id.task_list_item_iv);
            link = (TextView) itemView.findViewById(R.id.task_item_link);
            title_tv = (TextView) itemView.findViewById(R.id.task_item_title);
            person_tv = (TextView) itemView.findViewById(R.id.task_item_person);
            date_tv = (TextView) itemView.findViewById(R.id.task_item_date);
            addr_tv = (TextView) itemView.findViewById(R.id.task_item_addr);
            state_tv = (TextView) itemView.findViewById(R.id.task_item_status);
            ll_wtly = itemView.findViewById(R.id.ll_wtly);
            tv_wtly = itemView.findViewById(R.id.tv_wtly);
        }
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener<PSHEventListItem> listener) {
        this.listener = listener;
    }

}
