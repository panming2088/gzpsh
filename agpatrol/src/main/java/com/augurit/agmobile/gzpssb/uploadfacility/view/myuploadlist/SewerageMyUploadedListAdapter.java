package com.augurit.agmobile.gzpssb.uploadfacility.view.myuploadlist;

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
import com.augurit.agmobile.gzpssb.uploadfacility.model.UploadedListBean;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.agmobile.patrolcore.search.view.GlideRoundTransform;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

/**
 *
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
public class SewerageMyUploadedListAdapter extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder, UploadedListBean> {

    private Context mContext;

    public SewerageMyUploadedListAdapter(Context context, List<UploadedListBean> mDataList) {
        super(mDataList);
        this.mContext = context;
    }

    public void addData(List<UploadedListBean> searchResults){
        mDataList.addAll(searchResults);
    }

    public void clear(){
        mDataList.clear();
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ModifiedIdentificationViewHolder(inflater.inflate(R.layout.item_psh_modified_identification, null));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, final int position, UploadedListBean data) {
        if (holder instanceof ModifiedIdentificationViewHolder) {
            ModifiedIdentificationViewHolder viewHolder = (ModifiedIdentificationViewHolder) holder;

            String imgPath = data.getImgPath();
//            List<Photo> photos = data.getImgPath();
            if (ListUtil.isEmpty(imgPath)){
                viewHolder.iv.setImageDrawable(viewHolder.itemView.getResources().getDrawable(R.mipmap.patrol_ic_empty));
            }else {
                if(mContext != null && !(mContext instanceof Activity && ((Activity) mContext).isDestroyed())) {
                    //使用Glide进行加载图片
                    Glide.with(mContext)
                            .load(imgPath)
                            .error(R.mipmap.patrol_ic_load_fail)
                            .transform(new GlideRoundTransform(mContext))
                            .into(viewHolder.iv);
                }
            }

            if (!TextUtils.isEmpty(data.getAddr())) {
                viewHolder.tv_left_down.setText(data.getAddr());
            }else {
                viewHolder.tv_left_down.setText("");
            }


            if (data.getState() != null) {
                if (data.getState().equals("0")) {
                    viewHolder.tv_right_up.setText("已注销");
                    viewHolder.tv_right_up.setTextColor(Color.parseColor("#b1afab"));
                }else if (data.getState().equals("1") || data.getState().equals("5")) {
                    viewHolder.tv_right_up.setText("未审核");
                    viewHolder.tv_right_up.setTextColor(Color.RED);
                } else if (data.getState().equals("2")) {
                    viewHolder.tv_right_up.setText("审核通过");
                    viewHolder.tv_right_up.setTextColor(Color.parseColor("#3EA500"));
                } else if (data.getState().equals("3")) {
                    viewHolder.tv_right_up.setText("存在疑问");
                    viewHolder.tv_right_up.setTextColor(Color.parseColor("#FFFFC248"));
                } else if (data.getState().equals("4")) {
                    viewHolder.tv_right_up.setText("暂存");
                    viewHolder.tv_right_up.setTextColor(Color.parseColor("#48b4ff"));
                }

            }else{
                viewHolder.tv_right_up.setText("");
            }

//            if (data.getUpdateTime() != null && data.getUpdateTime() > 0){
//                viewHolder.tv_right_down.setText(TimeUtil.getStringTimeMdHmChines(new Date(data.getUpdateTime())));
//            }else
            if (data.getMarkTime() != -1) {
                viewHolder.tv_right_down.setText(TimeUtil.getStringTimeMdHmChines(new Date(data.getMarkTime())));
            }

            if (data.getId() != -1) {
                viewHolder.tv_mark_id.setText("上报编号："  + data.getId());
            }
            if(data.getName() != null) {
                viewHolder.tv_left_up.setText(String.valueOf(data.getName()+"("+data.getDischargerType3()+")"));
            }
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
