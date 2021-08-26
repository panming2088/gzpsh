package com.augurit.agmobile.gzpssb.adapter;

import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import com.augurit.agmobile.gzpssb.ItemFGSewerageDataHouse;
import com.augurit.agmobile.gzpssb.activity.SewerageRoomClickActivity2;
import com.augurit.agmobile.gzpssb.adapter.BaseRecyleAdapter.OnRecycleitemOnClic;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.utils.SewerageUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyu on 2018/4/3.
 */

public class SewerageRoomAdapter extends BaseRecyleAdapter implements OnRecycleitemOnClic {
    private List<SewerageItemBean.HouseListBean> houseListBeans;


    private String smallAddress;
    public SewerageRoomAdapter(List<?> data, Map<Integer, Integer> hashMap) {
        super(data, hashMap);
        setOnRecycleitemOnClic(this);
        houseListBeans = (List<SewerageItemBean.HouseListBean>) data;
    }
    public void initData(String smallAddress,List<SewerageItemBean.HouseListBean> houseListBeans){
        this.smallAddress=smallAddress;

        this.houseListBeans.addAll(houseListBeans);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemFGSewerageDataHouse itemFGSewerageDataHouse = (ItemFGSewerageDataHouse) holder.dataBinding;


        String useway = houseListBeans.get(position).getUseWay();
        String roomType = SewerageUtil.getRoomType(useway);
        itemFGSewerageDataHouse.roomtypeItemSeweragecompany1.setText(roomType);

        itemFGSewerageDataHouse.addressItemSeweragecompany1.setMovementMethod(ScrollingMovementMethod.getInstance());
        Log.e("TAG","房套:"+smallAddress+houseListBeans.get(position).getRoomNo());
        itemFGSewerageDataHouse.addressItemSeweragecompany1.setText(smallAddress+houseListBeans.get(position).getRoomNo());
    }

    @Override
    public void onItemClic(View view, int position) {

       // Intent intent = new Intent(view.getContext(), SewerageRoomClickActivity.class);todo 暂时先这么弄，后面根据需求改回来
        Intent intent = new Intent(view.getContext(), SewerageRoomClickActivity2.class);
        intent.putExtra("id",houseListBeans.get(position).getId());
        MyApplication.ID=houseListBeans.get(position).getId();
        MyApplication.ENTRANCE = "roomclick";
        view.getContext().startActivity(intent);

    }


}
