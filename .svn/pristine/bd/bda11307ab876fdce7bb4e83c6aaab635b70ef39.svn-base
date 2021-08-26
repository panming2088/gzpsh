package com.augurit.agmobile.gzpssb.adapter;

import android.view.View;

import com.augurit.agmobile.gzpssb.GVItemSewerageTableData;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyu on 2018/4/8.
 */

public class SewerageTableThreeAddAdapter extends BaseRecyleAdapter {
    private int selectPosition = 0;


    public SewerageTableThreeAddAdapter(List<?> data, Map<Integer, Integer> hashMap) {
        super(data, hashMap);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        GVItemSewerageTableData gvItemSewerageTableData = (GVItemSewerageTableData) holder.dataBinding;
        if (selectPosition==position) {
            gvItemSewerageTableData.ivSelected.setVisibility(View.VISIBLE);
        } else {
            gvItemSewerageTableData.ivSelected.setVisibility(View.GONE);
        }

        System.out.println("===position=====" + position);
    }

    boolean isadds = false;

    public void setIsadds(boolean isadds) {
        this.isadds = isadds;
    }

    public void onItemClic(View view, int position) {

        selectPosition = position;

        notifyItemChanged(position);
    }

}