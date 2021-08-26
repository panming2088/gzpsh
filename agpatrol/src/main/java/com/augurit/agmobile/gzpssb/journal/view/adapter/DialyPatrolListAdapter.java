package com.augurit.agmobile.gzpssb.journal.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournal;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.cache.img.amimageloader.AMImageLoader;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;

import java.util.Date;
import java.util.List;

/**
 * 设施纠错列表
 *
 * @author 创建人 ：liangshenghong
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.search.view
 * @createTime 创建时间 ：2017-03-20
 * @modifyBy 修改人 ：liangshenghong
 * @modifyTime 修改时间 ：2017-03-20
 * @modifyMemo 修改备注：
 */
public class DialyPatrolListAdapter extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder, PSHJournal> {

    private Context mContext;

    public DialyPatrolListAdapter(Context context, List<PSHJournal> mDataList) {
        super(mDataList);
        this.mContext = context;
    }

    public void addData(List<PSHJournal> searchResults) {
        mDataList.addAll(searchResults);
    }

    public void clear() {
        mDataList.clear();
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ModifiedIdentificationViewHolder(inflater.inflate(R.layout.item_psh_dialy_patrol, null));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, final int position, PSHJournal data) {
        if (holder instanceof ModifiedIdentificationViewHolder) {

            ModifiedIdentificationViewHolder viewHolder = (ModifiedIdentificationViewHolder) holder;

            List<Photo> photos = data.getPhotos();
            if(!ListUtil.isEmpty(photos)){
                String imgPath = photos.get(0).getThumbPath();
                if(!StringUtil.isEmpty(imgPath)){
                    AMImageLoader.loadStringRes(viewHolder.iv, imgPath);
                } else {
                    AMImageLoader.loadResId(viewHolder.iv, R.mipmap.patrol_ic_empty);
                }
            }
            //使用Glide进行加载图片
//            Glide.with(mContext)
//                    .load(photos.get(0).getThumbPath())
//                    .error(R.mipmap.patrol_ic_load_fail)
//                    .transform(new GlideRoundTransform(mContext))
//                    .into(viewHolder.iv);
            /**
             * 如果有正确地址就用正确地址，否则用原地址
             */
            if (!TextUtils.isEmpty(data.getAddr())) {
                viewHolder.tv_left_down.setText(data.getAddr());
            } else {
                viewHolder.tv_left_down.setText(data.getOriginAddr());
            }


            if (data.getCheckState() != null) {
                if (data.getCheckState().equals("1")) {
                    viewHolder.tv_right_up.setText("未审核");
                    viewHolder.tv_right_up.setTextColor(Color.RED);
                } else if (data.getCheckState().equals("2")) {
                    viewHolder.tv_right_up.setText("审核通过");
                    viewHolder.tv_right_up.setTextColor(Color.parseColor("#3EA500"));
                } else if (data.getCheckState().equals("3")) {
                    viewHolder.tv_right_up.setText("存在疑问");
                    viewHolder.tv_right_up.setTextColor(Color.parseColor("#FFFFC248"));
                }
            } else {
                viewHolder.tv_right_up.setText("");
            }

//            if (data.getUpdateTime() != null && data.getUpdateTime() > 0){
//                viewHolder.tv_right_down.setText(TimeUtil.getStringTimeMdHmChines(new Date(data.getUpdateTime())));
//            }else

            if (data.getRecordTime() != null) {
                viewHolder.tv_right_down.setText(TimeUtil.getStringTimeMdHmChines(new Date(data.getRecordTime())));
            }

            if (data.getId() != null) {
                viewHolder.tv_mark_id.setText("日常巡检编号：" + data.getId());
            }

            viewHolder.tv_person.setText("巡查人："+ StringUtil.getNotNullString(data.getWriterName(),""));

            viewHolder.tv_left_up.setText(data.getPshName());
        }
    }


    public static class ModifiedIdentificationViewHolder extends BaseRecyclerViewHolder {

        TextView tv_mark_id;
        ImageView iv;
        TextView tv_left_up;
        TextView tv_left_down;
        TextView tv_right_up;
        TextView tv_right_down;
        TextView tv_person;

        public ModifiedIdentificationViewHolder(View itemView) {
            super(itemView);
            iv = findView(R.id.search_result_item_iv);
            tv_left_up = (TextView) itemView.findViewById(R.id.tv_left_up);
            tv_left_down = (TextView) itemView.findViewById(R.id.tv_left_down);
            tv_right_up = (TextView) itemView.findViewById(R.id.tv_right_up);
            tv_right_down = (TextView) itemView.findViewById(R.id.tv_right_down);
            tv_mark_id = (TextView) itemView.findViewById(R.id.tv_mark_id);
            tv_person = (TextView) itemView.findViewById(R.id.tv_person);
        }
    }
}
