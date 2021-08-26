package com.augurit.agmobile.gzpssb.journal.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.secondpsh.SecondLevelPshDetailActivity;
import com.augurit.agmobile.gzpssb.secondpsh.SecondLevelPshListActivity;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.agmobile.patrolcore.search.view.GlideRoundTransform;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.bumptech.glide.Glide;

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
public class PSHHouseListAdapter extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder, PSHHouse> {

    private Context mContext;
    private OnClickHouseListener onRecycleitemOnClic;

    public void setOnRecycleitemOnClic(OnClickHouseListener onRecycleitemOnClic) {
        this.onRecycleitemOnClic = onRecycleitemOnClic;
    }

    public PSHHouseListAdapter(Context context, List<PSHHouse> mDataList) {
        super(mDataList);
        this.mContext = context;
    }

    public void addData(List<PSHHouse> searchResults) {
        mDataList.addAll(searchResults);
    }

    public void clear() {
        mDataList.clear();
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ModifiedIdentificationViewHolder(inflater.inflate(R.layout.item_house_list, null));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, final int position, final PSHHouse data) {
        if (holder instanceof ModifiedIdentificationViewHolder) {

            ModifiedIdentificationViewHolder viewHolder = (ModifiedIdentificationViewHolder) holder;

            String strPth = data.getImgPath();

            if(mContext != null && !(mContext instanceof Activity && ((Activity) mContext).isDestroyed())) {
                //使用Glide进行加载图片
                Glide.with(mContext)
                        .load(strPth)
                        .error(R.mipmap.patrol_ic_load_fail)
                        .transform(new GlideRoundTransform(mContext))
                        .into(viewHolder.iv);
            }
            /**
             * 如果有正确地址就用正确地址，否则用原地址
             */
            if (!TextUtils.isEmpty(data.getAddr())) {
                viewHolder.tv_left_down.setText(data.getAddr());
            }
//            viewHolder.psh_type.setText("生活排污类".equals(data.getDischargerType1())?"一般排水户":"典型排水户");
            if ("1".equals(data.getState()) || "5".equals(data.getState())) {
                viewHolder.tv_right_up.setText("未审核");
                viewHolder.tv_right_up.setTextColor(mContext.getResources().getColor(R.color.agmobile_red));
                viewHolder.btn_right_bottom.setVisibility(View.VISIBLE);
//                viewHolder.btn_right_bottom2.setVisibility(View.VISIBLE);
            }else if("2".equals(data.getState())){
                viewHolder.tv_right_up.setText("审核通过");
                viewHolder.tv_right_up.setTextColor(Color.parseColor("#3EA500"));
                viewHolder.btn_right_bottom.setVisibility(View.VISIBLE);
//                viewHolder.btn_right_bottom2.setVisibility(View.VISIBLE);
            }else if("0".equals(data.getState())){
                viewHolder.tv_right_up.setText("已注销");
                viewHolder.tv_right_up.setTextColor(Color.parseColor("#b1afab"));
                viewHolder.btn_right_bottom.setVisibility(View.GONE);
//                viewHolder.btn_right_bottom2.setVisibility(View.GONE);
            }else if("3".equals(data.getState())){
                viewHolder.tv_right_up.setText("存在疑问");
                viewHolder.tv_right_up.setTextColor(Color.parseColor("#FFFFC248"));
                viewHolder.btn_right_bottom.setVisibility(View.VISIBLE);
//                viewHolder.btn_right_bottom2.setVisibility(View.GONE);
            }else if("4".equals(data.getState())){
                viewHolder.tv_right_up.setText("暂存");
                viewHolder.tv_right_up.setTextColor(mContext.getResources().getColor(R.color.agmobile_blue));
                viewHolder.btn_right_bottom.setVisibility(View.VISIBLE);
//                viewHolder.btn_right_bottom2.setVisibility(View.GONE);
            }
            else{
                viewHolder.tv_right_up.setText("");
            }


//            if (data.getUpdateTime() != null && data.getUpdateTime() > 0){
//                viewHolder.tv_right_down.setText(TimeUtil.getStringTimeMdHmChines(new Date(data.getUpdateTime())));
//            }else

            if (data.getMarkTime() != null) {
                viewHolder.tv_right_down.setText(TimeUtil.getStringTimeMdHmChines(new Date(data.getMarkTime())));
            }

            if (!StringUtil.isEmpty(data.getOperator())) {
                viewHolder.tv_mark_id.setText("法人代表：" + data.getOperator());
            }

            viewHolder.tv_left_up.setText(data.getName()+("生活排污类".equals(data.getDischargerType1())?"(一般排水户)":"(典型排水户)"));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onRecycleitemOnClic != null){
                        onRecycleitemOnClic.onItemLister(data);
                    }
                }
            });

            viewHolder.btn_right_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onRecycleitemOnClic != null){
                        onRecycleitemOnClic.onInspectionListenter(data);
                    }
                }
            });

            viewHolder.btn_psh_ej.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SecondLevelPshListActivity.class);
                    SewerageItemBean.UnitListBean unitListBean = new SewerageItemBean.UnitListBean();
                    unitListBean.setId(Integer.valueOf(data.getUnitId()));
                    unitListBean.setAddr(data.getMph());
                    unitListBean.setName(data.getName());
                    intent.putExtra("unitListBean",unitListBean);
                    mContext.startActivity(intent);
                }
            });
//            viewHolder.btn_right_bottom2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(onRecycleitemOnClic != null){
//                        onRecycleitemOnClic.onItemRecordListener(data);
//                    }
//                }
//            });
        }
    }


    public static class ModifiedIdentificationViewHolder extends BaseRecyclerViewHolder {

        TextView tv_mark_id;
        ImageView iv;
        TextView tv_left_up;
        TextView tv_left_down;
        TextView tv_right_up;
        TextView tv_right_down;
        Button btn_right_bottom;
        Button  btn_psh_ej;
//        Button btn_right_bottom2;

        public ModifiedIdentificationViewHolder(View itemView) {
            super(itemView);
            iv = findView(R.id.search_result_item_iv);
            tv_left_up = (TextView) itemView.findViewById(R.id.tv_left_up);
            tv_left_down = (TextView) itemView.findViewById(R.id.tv_left_down);
            tv_right_up = (TextView) itemView.findViewById(R.id.tv_right_up);
            tv_right_down = (TextView) itemView.findViewById(R.id.tv_right_down);
            tv_mark_id = (TextView) itemView.findViewById(R.id.tv_mark_id);
            btn_right_bottom = (Button) itemView.findViewById(R.id.btn_right_bottom);
            btn_psh_ej = (Button) itemView.findViewById(R.id.btn_psh_ej);
//            btn_right_bottom2 = (Button) itemView.findViewById(R.id.btn_right_bottom2);
        }
    }

    public interface OnClickHouseListener{
        void onItemLister(PSHHouse pshHouse);
        void onItemRecordListener(PSHHouse pshHouse);
        void onInspectionListenter(PSHHouse pshHouse);
    }
}
