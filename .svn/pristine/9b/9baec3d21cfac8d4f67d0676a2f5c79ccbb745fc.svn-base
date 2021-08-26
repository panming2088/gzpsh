package com.augurit.agmobile.gzpssb.fire.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.util.ListUtil;
import com.augurit.agmobile.gzpssb.fire.model.GroundfireBean;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.mode.UploadDoorNoDetailBean;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.agmobile.patrolcore.search.view.GlideRoundTransform;
import com.augurit.am.fw.utils.TimeUtil;
import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

/**
 * 设施新增列表Adapter
 *
 * @author 创建人 ：liangshenghong
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.search.view
 * @createTime 创建时间 ：2017-03-20
 * @modifyBy 修改人 ：liangshenghong
 * @modifyTime 修改时间 ：2017-03-20
 * @modifyMemo 修改备注：
 */
public class MyUploadedFireListAdapter extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder, GroundfireBean> {

    private Context mContext;

    public MyUploadedFireListAdapter(Context context, List<GroundfireBean> mDataList) {
        super(mDataList);
        this.mContext = context;
    }

    public void addData(List<GroundfireBean> searchResults) {
        mDataList.addAll(searchResults);
    }

    public void clear() {
        mDataList.clear();
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ModifiedIdentificationViewHolder(inflater.inflate(R.layout.item_ground_fire, null));
    }


    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, final int position, GroundfireBean data) {
        if (holder instanceof ModifiedIdentificationViewHolder) {
            ModifiedIdentificationViewHolder viewHolder = (ModifiedIdentificationViewHolder) holder;
//            String imgPath = data.getImgPath();
            String imgPath  = null;
           if (ListUtil.isEmpty(data.getHasCert8Attachment()) || TextUtils.isEmpty(data.getHasCert8Attachment().get(0).getAttPath())){
                viewHolder.iv.setImageDrawable(viewHolder.itemView.getResources().getDrawable(R.mipmap.patrol_ic_empty));
            }else {
               imgPath = data.getHasCert8Attachment().get(0).getAttPath();
               if(mContext != null && !(mContext instanceof Activity && ((Activity) mContext).isDestroyed())) {
                   //使用Glide进行加载图片
                   Glide.with(mContext)
                           .load(imgPath)
                           .error(R.mipmap.patrol_ic_load_fail)
                           .transform(new GlideRoundTransform(mContext))
                           .into(viewHolder.iv);
               }
            }

            if (!TextUtils.isEmpty(data.getName())) {
                viewHolder.tv_left_up.setText("设施名称:"+data.getName());
            } else {
                viewHolder.tv_left_down.setText("设施名称:");
            }
            viewHolder.tv_left_down.setText("地址:"+data.getAddr());
            if (data.getMarkTime() != 0) {
                viewHolder.tv_right_down.setText(TimeUtil.getStringTimeMdHmChines(new Date(Long.valueOf(data.getMarkTime()))));
            }
            viewHolder.tv_mark_id.setText("上报编号:" + data.getId());
        }
    }


    public static class ModifiedIdentificationViewHolder extends BaseRecyclerViewHolder {

        ImageView iv;
        TextView tv_left_up;
        TextView tv_left_down;
        TextView tv_right_up;
        TextView tv_right_down;
        TextView tv_mark_id;

        public ModifiedIdentificationViewHolder(View itemView) {
            super(itemView);
            iv = findView(R.id.search_result_item_iv);
            tv_left_up = (TextView) itemView.findViewById(R.id.tv_left_up);
            tv_left_down = (TextView) itemView.findViewById(R.id.tv_left_down);
            tv_right_up = (TextView) itemView.findViewById(R.id.tv_right_up);
            tv_right_down = (TextView) itemView.findViewById(R.id.tv_right_down);
            tv_mark_id = (TextView) itemView.findViewById(R.id.tv_mark_id);
        }
    }
}
