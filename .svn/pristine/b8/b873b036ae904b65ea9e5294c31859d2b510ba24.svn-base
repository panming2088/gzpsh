package com.augurit.agmobile.gzpssb.draft;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzpssb.bean.SewerageInfoBean;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.cache.img.amimageloader.AMImageLoader;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;

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
public class PSHUploadDraftListAdapter extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder, SewerageInfoBean> {

    private Context mContext;

    public PSHUploadDraftListAdapter(Context context, List<SewerageInfoBean> mDataList) {
        super(mDataList);
        this.mContext = context;
    }

    public void addData(List<SewerageInfoBean> searchResults){
        mDataList.clear();
        mDataList.addAll(searchResults);
    }

    public void clear(){
        mDataList.clear();
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ModifiedIdentificationViewHolder(inflater.inflate(R.layout.item_psh_draft, null));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, final int position, SewerageInfoBean data) {
        if (holder instanceof ModifiedIdentificationViewHolder) {
            ModifiedIdentificationViewHolder viewHolder = (ModifiedIdentificationViewHolder) holder;

            List<Photo> photos = data.getPhotos0();
            if (!ListUtil.isEmpty(photos)) {
                String filePath = photos.get(0).getLocalPath();
                if (!StringUtil.isEmpty(filePath)) {
                    AMImageLoader.loadStringRes(viewHolder.iv, filePath);
                }

            } else {
                AMImageLoader.loadResId(viewHolder.iv, R.drawable.bg_loading);
            }

            if (!TextUtils.isEmpty(data.getAddr())) {
                viewHolder.tv_left_down.setText(data.getAddr());
            } else {
                viewHolder.tv_left_down.setText("");
            }
            if (data.getName() != null) {
                viewHolder.tv_left_up.setText(String.valueOf(data.getName()
                        + (TextUtils.isEmpty(data.getDischargerType2()) ? "": "(" + data.getDischargerType2() + ")")));
            }
            if (data.getSaveTime() != 0) {
                viewHolder.tv_right_down.setText(TimeUtil.getStringTimeMdHmChines(new Date(data.getSaveTime())));
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
