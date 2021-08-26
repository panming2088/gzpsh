package com.augurit.agmobile.gzpssb.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.GVItemSewerageTableData;
import com.augurit.agmobile.gzpssb.bean.ItemSewerageItemInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyu on 2018/4/4.
 */

public class SewerageTableTwoChildAdapter extends BaseRecyleAdapter  implements BaseRecyleAdapter.OnRecycleitemOnClic {
    private List<ItemSewerageItemInfo> infos;
    private int selectPosition = 0;
    private GVItemSewerageTableData gvItemSewerageTableData;

    public SewerageTableTwoChildAdapter(List<?> data, Map<Integer, Integer> hashMap) {
        super(data, hashMap);
        infos = (List<ItemSewerageItemInfo>) data;
        setOnRecycleitemOnClic(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        gvItemSewerageTableData = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.gv_item_sewersgetable_two, parent, false);

        return new ViewHolder(gvItemSewerageTableData);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if(selectPosition==position){
            gvItemSewerageTableData.ivSelected.setVisibility(View.VISIBLE);
        }else{
            gvItemSewerageTableData.ivSelected.setVisibility(View.GONE);
        }


    }

    @Override
    public void onItemClic(View view, int position) {
        selectPosition=position;

        notifyDataSetChanged();
    }
}
