package com.augurit.agmobile.gzpssb.secondpsh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfo;
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
public class SecondPshListAdapter extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder, SecondLevelPshInfo.SecondPshInfo> {

    private Context mContext;
    private onSwitchListener switchListener;

    public void setSwitchListener(onSwitchListener switchListener) {
        this.switchListener = switchListener;
    }

    public SecondPshListAdapter(Context context, List<SecondLevelPshInfo.SecondPshInfo> mDataList) {
        super(mDataList);
        this.mContext = context;
    }

    public void addData(List<SecondLevelPshInfo.SecondPshInfo> searchResults){
        mDataList.addAll(searchResults);
    }

    public void clear(){
        mDataList.clear();
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ModifiedIdentificationViewHolder(inflater.inflate(R.layout.item_second_psh, null));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, final int position, final SecondLevelPshInfo.SecondPshInfo data) {
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
            viewHolder.tv_left_up.setText(data.getEjname());
            viewHolder.tv_left_down.setText(data.getEjaddr());
            viewHolder.tv_right_down.setText(TimeUtil.getStringTimeYMDS(new Date(data.getMarkTime())));
            viewHolder.tv_right_up.setText("上报编号:"+data.getId()+"");
            viewHolder.tv_mark_id.setText(data.getPshtype3());
            viewHolder.btn_switch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(switchListener != null){
                        switchListener.onItemSwitchLister(data);
                    }
                }
            });
        }
    }

    public static class ModifiedIdentificationViewHolder extends BaseRecyclerViewHolder {

        ImageView iv;
        TextView tv_left_up;
        TextView tv_left_down;
        TextView tv_right_up;
        TextView tv_right_down;
        TextView tv_mark_id;
        Button btn_switch;

        public ModifiedIdentificationViewHolder(View itemView) {
            super(itemView);
            iv = findView(R.id.search_result_item_iv);
            tv_left_up = (TextView) itemView.findViewById(R.id.tv_left_up);
            tv_left_down = (TextView) itemView.findViewById(R.id.tv_left_down);
            tv_right_up = (TextView) itemView.findViewById(R.id.tv_right_up);
            tv_right_down = (TextView) itemView.findViewById(R.id.tv_right_down);
            tv_mark_id = (TextView) itemView.findViewById(R.id.tv_mark_id);
            btn_switch = (Button) itemView.findViewById(R.id.btn_zyjpsh);
        }
    }

    public interface onSwitchListener{
        void onItemSwitchLister(SecondLevelPshInfo.SecondPshInfo data);
    }
}
