package com.augurit.agmobile.gzpssb.pshstatistics.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.pshstatistics.view.PSHUploadStatsFragment;

import java.util.HashSet;
import java.util.List;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzpssb.pshstatistics.adapter
 * @createTime 创建时间 ：2018-05-07
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2018-05-07
 * @modifyMemo 修改备注：
 */

public class MyGridViewAdapter1 extends BaseAdapter {
    private Context mContext;
    public MyGridViewAdapter1(Context context) {
        this.mContext = context;
    }
    private HashSet<Integer> selectedPositions = new HashSet<Integer>();// 选中的位置
    private PSHUploadStatsFragment.OnItemListener onItemListener;
    private List<String> arrayList;

    public String getSelectedPositions() {
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<4;i++){
            sb.append(selectedPositions.contains(i)?"1":"0");
        }
        return sb.toString();
    }

    public void addOrDeleleItem(int position) {
        if(selectedPositions.contains(position)){
            selectedPositions.remove(position);
        }else{
            selectedPositions.add(position);
        }
        notifyDataSetChanged();
    }
    public void setSelectedPositions(HashSet<Integer> selectedPositions) {
        this.selectedPositions = selectedPositions;
    }

    public void setOnItemListener(PSHUploadStatsFragment.OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public void setArrayList(List<String> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        if (arrayList != null && arrayList.size() > position) {
            return arrayList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (arrayList != null && arrayList.size() > position) {
            return position;
        }
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.gridview_item_layout, null);
        TextView area_name = (TextView) view.findViewById(R.id.tv_area_name);
        area_name.setText(arrayList.get(position));
        ImageView iv = (ImageView) view.findViewById(R.id.iv_selected);
        if (selectedPositions.contains(position)) {
            area_name.setBackground(mContext.getResources().getDrawable(R.drawable.corner_color_primary3));
            area_name.setTextColor(ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimary, null));
            iv.setVisibility(View.VISIBLE);
        } else {
            area_name.setBackground(mContext.getResources().getDrawable(R.drawable.corner_color_write));
            area_name.setTextColor(Color.BLACK);
            iv.setVisibility(View.GONE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemListener != null) {
                    onItemListener.onClick(position);
                }
            }
        });
        return view;
    }
}
