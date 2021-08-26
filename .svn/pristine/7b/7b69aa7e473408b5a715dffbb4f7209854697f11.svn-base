package com.augurit.agmobile.gzpssb.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.ItemFGDrainageDataHouse;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.adapter.BaseRecyleAdapter.OnRecycleitemOnClic;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.event.RefreshDataListEvent;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyu on 2018/4/3.
 */

public class SewerageDrainageAdapter extends BaseRecyleAdapter implements OnRecycleitemOnClic {
    private List<SewerageItemBean.UnitListBean> unitListBeans;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SewerageDrainageAdapter(Context context, List<?> data, Map<Integer, Integer> hashMap) {
        super(data, hashMap);
        this.mContext = context;
        setOnRecycleitemOnClic(this);
        unitListBeans = (List<SewerageItemBean.UnitListBean>) data;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        SewerageItemBean.UnitListBean unitListBean = unitListBeans.get(position);
        final ItemFGDrainageDataHouse itemFGSewerageDataHouse = (ItemFGDrainageDataHouse) holder.dataBinding;
        itemFGSewerageDataHouse.addressItemSeweragecompany1.setText(unitListBeans.get(position).getName());
        itemFGSewerageDataHouse.roomtypeItemSeweragecompany1.setText("法人："+unitListBeans.get(position).getLegalRepresent());
        if (unitListBean.getIsrecorded()==0) {
            itemFGSewerageDataHouse.tvRightUp.setText("已调查");
            itemFGSewerageDataHouse.tvRightUp.setTextColor(Color.parseColor("#3EA500"));
            itemFGSewerageDataHouse.btnRightBottom.setVisibility(View.VISIBLE);
        }else if(unitListBean.getIsrecorded()==1){
            itemFGSewerageDataHouse.tvRightUp.setText("未调查");
            itemFGSewerageDataHouse.tvRightUp.setTextColor(Color.parseColor("#FFFFC248"));
            itemFGSewerageDataHouse.btnRightBottom.setVisibility(View.VISIBLE);
        }else{
            itemFGSewerageDataHouse.tvRightUp.setText("已注销");
            itemFGSewerageDataHouse.tvRightUp.setTextColor(Color.parseColor("#b1afab"));
            itemFGSewerageDataHouse.btnRightBottom.setVisibility(View.GONE);
        }
        itemFGSewerageDataHouse.btnRightBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRecycleitemOnClic != null){
                    SewerageItemBean.UnitListBean unitListBean = unitListBeans.get(position);
                    if(onItemClickListener != null){
                        onItemClickListener.onItemPatrolClick(unitListBean);
                    }
                }
            }
        });
    }
    @Override
    public void onItemClic(View view, int position) {
        SewerageItemBean.UnitListBean unitListBean = unitListBeans.get(position);
        if(view.getId() == R.id.btn_right_bottom){

        }
        if(unitListBean.getIsrecorded() == 0){
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(unitListBean.getId());
            }
//            ToastUtil.shortToast(mContext,"已调查暂不能点进去");
            return;
        }
        MyApplication.refreshListType = RefreshDataListEvent.UPDATE_MIAN_LIST;
        Intent intent = new Intent( view.getContext(), SewerageTableActivity.class);
        intent.putExtra("unitListBeans", unitListBeans.get(position));
        if(unitListBeans.get(position).getIsexist()==1){
            //情景1，挂在房栋下面
            intent.putExtra("HouseIdFlag","0");
            intent.putExtra("HouseId",MyApplication.buildId);
//            SewerageBeanManger.getInstance().setHouseIdFlag("0");
//            SewerageBeanManger.getInstance().setHouseId(ParamsInstance.getInstance().getBuildId());
        }else if(unitListBeans.get(position).getIsexist()==0){
            //情景1.1，挂在房套下面
            intent.putExtra("HouseIdFlag","1");
            intent.putExtra("HouseId",unitListBeans.get(position).getHouseId());
//            SewerageBeanManger.getInstance().setHouseIdFlag("1");
//            SewerageBeanManger.getInstance().setHouseId(unitListBeans.get(position).getHouseId());
        }
        intent.putExtra("UnitId",unitListBeans.get(position).getId()+"");
//        SewerageBeanManger.getInstance().setUnitId(unitListBeans.get(position).getId()+"");
        view.getContext().startActivity(intent);

    }

    public interface OnItemClickListener{
        void onItemClick(int id);
        void onItemPatrolClick(SewerageItemBean.UnitListBean data);
    }
}