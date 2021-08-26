package com.augurit.agmobile.gzpssb.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.augurit.agmobile.gzpssb.ItemFGSewerageDataHouse;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.adapter.BaseRecyleAdapter.OnRecycleitemOnClic;
import com.augurit.agmobile.gzpssb.bean.SewerageRoomClickItemBean;
import com.augurit.agmobile.gzpssb.event.RefreshDataListEvent;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.am.fw.utils.view.ToastUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyu on 2018/4/3.
 */

public class SewerageRoomCompanyAdapter extends BaseRecyleAdapter implements OnRecycleitemOnClic {
    private List<SewerageRoomClickItemBean.UnitListBean> unitListBeans1;
    private Context mContext;

    public SewerageRoomCompanyAdapter(Context context, List<?> data, Map<Integer, Integer> hashMap) {
        super(data, hashMap);
        mContext = context;
        setOnRecycleitemOnClic(this);
        unitListBeans1 = (List<SewerageRoomClickItemBean.UnitListBean>) data;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        SewerageRoomClickItemBean.UnitListBean unitListBean = unitListBeans1.get(position);
        ItemFGSewerageDataHouse itemFGSewerageDataHouse = (ItemFGSewerageDataHouse) holder.dataBinding;
        itemFGSewerageDataHouse.addressItemSeweragecompany1.setText(unitListBeans1.get(position).getName());
        itemFGSewerageDataHouse.roomtypeItemSeweragecompany1.setText("法人："+unitListBeans1.get(position).getLegalRepresent());
        if (unitListBean.getIsrecorded()==0) {
            itemFGSewerageDataHouse.tvRightUp.setText("已调查");
            itemFGSewerageDataHouse.tvRightUp.setTextColor(Color.parseColor("#3EA500"));
        }else{
            itemFGSewerageDataHouse.tvRightUp.setText("未调查");
            itemFGSewerageDataHouse.tvRightUp.setTextColor(Color.parseColor("#FFFFC248"));
        }
    }

    @Override
    public void onItemClic(View view, int position) {
        SewerageRoomClickItemBean.UnitListBean unitListBean = unitListBeans1.get(position);
        if(unitListBean.getIsrecorded() == 0){
            ToastUtil.shortToast(mContext,"已调查暂不能点进去");
            return;
        }
        MyApplication.refreshListType = RefreshDataListEvent.UPDATE_ROOM_UNIT_LIST1;
        Intent intent = new Intent( view.getContext(), SewerageTableActivity.class);
//        SewerageBeanManger.getInstance().setHouseIdFlag("1");
//        SewerageBeanManger.getInstance().setHouseId(MyApplication.ID);
//        SewerageBeanManger.getInstance().setUnitId(unitListBeans1.get(position).getId()+"");
        intent.putExtra("HouseIdFlag","1");
        intent.putExtra("HouseId",MyApplication.ID);
        intent.putExtra("UnitId",unitListBeans1.get(position).getId()+"");
        intent.putExtra("RoomclickunitListBeans",unitListBeans1.get(position));
//        ((Activity)view.getContext()).startActivityForResult(intent,100);
           view.getContext().startActivity(intent);


    }}
