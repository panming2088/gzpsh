package com.augurit.agmobile.gzpssb.journal.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.util.ListUtil;
import com.augurit.agmobile.gzps.common.widget.FileBean;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.bean.SewerageInfoBean;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * @author: liangsh
 * @createTime: 2021/5/17
 */
public class DialyPatrolPshListBottomAdapter extends RecyclerView.Adapter<DialyPatrolPshListBottomAdapter.Holder> {

    private List<PSHHouse> pshHouseList;

    private int mSelectedPosistion = -1;

    private OnRecyclerItemClickListener<PSHHouse> onRecyclerItemClickListener;
    private OnRecyclerItemClickListener<PSHHouse> btn1ClickListener;
    private OnRecyclerItemClickListener<PSHHouse> btn2ClickListener;
    private OnRecyclerItemClickListener<PSHHouse> btn3ClickListener;


    public void notifyDataSetChanged(List<PSHHouse> pshHouseList){
        this.pshHouseList = pshHouseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialy_patrol_psh, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        final PSHHouse pshHouse = pshHouseList.get(position);
        final int pos = position;
        holder.tv_address.setText("排水户名称：" + StringUtil.getNotNullString(pshHouse.getName(), ""));
//        holder.tv_right_up.setText(StringUtil.getNotNullString(pshHouse.getName(), ""));
        holder.tv_house_Property.setText("行业类别：" + StringUtil.getNotNullString(pshHouse.getDischargerType3(), ""));
        holder.tv_right_up_tip.setVisibility(View.VISIBLE);
        holder.tv_right_up.setVisibility(View.VISIBLE);
        holder.door_detail_btn2.setVisibility(View.VISIBLE);
        holder.door_detail_btn.setVisibility(View.VISIBLE);
        if ("2".equals(pshHouse.getState())) {
            holder.tv_right_up.setText("已审核");
            holder.tv_right_up.setTextColor(Color.parseColor("#3EA500"));
        } else if ("1".equals(pshHouse.getState())) {
            holder.tv_right_up.setText("未审核");
            holder.tv_right_up.setTextColor(Color.parseColor("#FFFF0000"));
        } else if ("0".equals(pshHouse.getState())) {
            holder.tv_right_up.setText("已注销");
            holder.door_detail_btn2.setVisibility(View.GONE);
            holder.door_detail_btn.setVisibility(View.GONE);
            holder.tv_right_up.setTextColor(Color.parseColor("#b1afab"));
        } else if ("3".equals(pshHouse.getState())) {
            holder.tv_right_up.setText("存在疑问");
            holder.tv_right_up.setTextColor(Color.parseColor("#FFFFC248"));
        } else if ("4".equals(pshHouse.getState())) {
            holder.tv_right_up.setText("暂存");
            holder.tv_right_up.setTextColor(Color.parseColor("#FFFFC248"));
        } else if ("5".equals(pshHouse.getState())) {
            holder.tv_right_up.setText("编辑");
            holder.tv_right_up.setTextColor(Color.parseColor("#FFFFC248"));
        }
        holder.door_detail_btn.setText("巡检");
//        holder.door_detail_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(btn1ClickListener != null){
//                            btn1ClickListener.onItemClick(view, pos, pshHouse);
//                        }
//                    }
//                });
        RxView.clicks(holder.door_detail_btn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if(btn1ClickListener != null){
                            btn1ClickListener.onItemClick(holder.door_detail_btn, pos, pshHouse);
                        }
                    }
                });
        RxView.clicks(holder.door_detail_btn2)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if(btn2ClickListener != null){
                            btn2ClickListener.onItemClick(holder.door_detail_btn2, pos, pshHouse);
                        }
                    }
                });
        holder.door_detail_btn3.setVisibility(View.VISIBLE);
        RxView.clicks(holder.door_detail_btn3)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if(btn3ClickListener != null){
                            btn3ClickListener.onItemClick(holder.door_detail_btn3, pos, pshHouse);
                        }
                    }
                });
        RxView.clicks(holder.itemView)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        setSelectedPosistion(pos);
                        if(onRecyclerItemClickListener != null){
                            onRecyclerItemClickListener.onItemClick(holder.itemView, pos, pshHouse);
                        }
                    }
                });
//        holder.door_detail_btn2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(btn2ClickListener != null){
//                            btn2ClickListener.onItemClick(view, pos, pshHouse);
//                        }
//                    }
//                });
//        holder.door_detail_btn3.setVisibility(View.VISIBLE);
//        holder.door_detail_btn3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(btn3ClickListener != null){
//                            btn3ClickListener.onItemClick(view, pos, pshHouse);
//                        }
//                    }
//                });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setSelectedPosistion(pos);
//                if(onRecyclerItemClickListener != null){
//                    onRecyclerItemClickListener.onItemClick(v, pos, pshHouse);
//                }
//            }
//        });
        if(mSelectedPosistion == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F4F7FE"));
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        if (ListUtil.isNotEmpty(pshHouseList)) {
            return pshHouseList.size();
        }
        return 0;
    }

    public void setSelectedPosistion(int selectedPosistion){
        mSelectedPosistion = selectedPosistion;
        notifyDataSetChanged();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener<PSHHouse> onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public void setBtn1ClickListener(OnRecyclerItemClickListener<PSHHouse> btn1ClickListener) {
        this.btn1ClickListener = btn1ClickListener;
    }

    public void setBtn2ClickListener(OnRecyclerItemClickListener<PSHHouse> btn2ClickListener) {
        this.btn2ClickListener = btn2ClickListener;
    }

    public void setBtn3ClickListener(OnRecyclerItemClickListener<PSHHouse> btn3ClickListener) {
        this.btn3ClickListener = btn3ClickListener;
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView door_detail_btn3;
        TextView door_detail_btn2;
        TextView door_detail_btn;
        TextView tv_address;
        TextView tv_right_up_tip;
        TextView tv_right_up;
        TextView tv_house_Property;

        public Holder(View itemView) {
            super(itemView);
            door_detail_btn3 = itemView.findViewById(R.id.door_detail_btn3);
            door_detail_btn2 = itemView.findViewById(R.id.door_detail_btn2);
            door_detail_btn = itemView.findViewById(R.id.door_detail_btn);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_right_up_tip = itemView.findViewById(R.id.tv_right_up_tip);
            tv_right_up = itemView.findViewById(R.id.tv_right_up);
            tv_house_Property = itemView.findViewById(R.id.tv_house_Property);
        }
    }
}
