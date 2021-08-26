package com.augurit.agmobile.gzpssb.pshpublicaffair.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHEventBean;
import com.augurit.am.fw.cache.img.amimageloader.AMImageLoader;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * com.augurit.agmobile.gzws.uploadcheck.view
 * Created by sdb on 2018/3/20  18:19.
 * Desc：
 */

public class PSHAffairAdapter extends RecyclerView.Adapter<PSHAffairAdapter.NWCheckHolder> {
    private Context mContext;
    private List<PSHEventBean.DataBean> list = new ArrayList<>();

    public PSHAffairAdapter(Context context) {
        this.mContext = context;
    }

    public void addData(List<PSHEventBean.DataBean> searchResults) {
        list.addAll(searchResults);
    }

    public void notifyDataSetChanged(List<PSHEventBean.DataBean> eventList) {
        this.list = eventList;
        notifyDataSetChanged();
    }

    @Override
    public NWCheckHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PSHAffairAdapter.NWCheckHolder(LayoutInflater.from(mContext).inflate(R.layout.psh_affair_item, null));
    }

    @Override
    public void onBindViewHolder(NWCheckHolder holder, final int position) {
        final PSHEventBean.DataBean dataBean = list.get(position);
        String imgPath = dataBean.getImgPath();
        if (!StringUtil.isEmpty(imgPath)) {
            AMImageLoader.loadStringRes(holder.task_list_item_iv, imgPath);
        } else {
            AMImageLoader.loadResId(holder.task_list_item_iv, R.mipmap.patrol_ic_empty);
        }

        holder.task_item_link.setText(dataBean.getName()+"("+
                (TextUtils.isEmpty(dataBean.getDischargerType3())?"":dataBean.getDischargerType3())+")");
        holder.task_item_addr.setText(TextUtils.isEmpty(dataBean.getAddr())?"":dataBean.getAddr());
        holder.task_item_org.setText(TextUtils.isEmpty(dataBean.getParentOrgName())?"":dataBean.getParentOrgName());
        holder.task_item_person.setText(TextUtils.isEmpty(dataBean.getMarkPerson())?"":dataBean.getMarkPerson());
        holder.task_item_date.setText(TimeUtil.getStringTimeYMDS(new Date(dataBean.getMarkTime())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pshAffairClickListener!=null){
                    pshAffairClickListener.onClickAffair(dataBean.getId());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if (ListUtil.isEmpty(list)) {
            return 0;
        }
        return list.size();
    }

    class NWCheckHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView task_item_link, task_item_addr, task_item_org, task_item_person, task_item_date;
        ImageView task_list_item_iv;

        public NWCheckHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            task_list_item_iv = (ImageView) itemView.findViewById(R.id.iv_image);
            task_item_link = (TextView) itemView.findViewById(R.id.tv_description);
            task_item_addr = (TextView) itemView.findViewById(R.id.tv_address);
            task_item_org = (TextView) itemView.findViewById(R.id.tv_district);
            task_item_person = (TextView) itemView.findViewById(R.id.tv_upload_person);
            task_item_date = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    public interface PshAffairClickListener{
        void onClickAffair(int affairId);
    }

    public PshAffairClickListener  pshAffairClickListener;
    public void setPshAffairClickListener(PshAffairClickListener  pshAffairClickListener){
        this.pshAffairClickListener = pshAffairClickListener;
    }
}
