package com.augurit.agmobile.gzpssb.journal.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.util.ListUtil;
import com.augurit.agmobile.gzpssb.journal.model.PSHCountByType;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.journal.view.adapter.DialyPatrolPshListBottomAdapter;
import com.augurit.agmobile.gzpssb.journal.view.adapter.DialyPatrolTypeAdapter;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author: liangsh
 * @createTime: 2021/5/18
 */
public class DialyPatrolPshListBottomView extends LinearLayout {

    private RecyclerView rv_type;
    private RecyclerView rv_psh_list;

    private DialyPatrolTypeAdapter typeAdapter;
    private DialyPatrolPshListBottomAdapter pshAdapter;

    private LinkedHashMap<String, List<PSHHouse>> pshTypeMap;

    private List<PSHHouse> all;

    public DialyPatrolPshListBottomView(Context context) {
        this(context, null);
    }

    public DialyPatrolPshListBottomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialyPatrolPshListBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.dialy_patrol_psh_list_by_type_layout, this);
        rv_type = findViewById(R.id.rv_type);
        rv_psh_list = findViewById(R.id.rv_psh_list);

        rv_type.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_psh_list.setLayoutManager(new LinearLayoutManager(getContext()));

        typeAdapter = new DialyPatrolTypeAdapter();
        rv_type.setAdapter(typeAdapter);
        typeAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<PSHCountByType>() {
            @Override
            public void onItemClick(View view, int position, PSHCountByType selectedData) {
                if (selectedData.type.equals("全部")) {
                    pshAdapter.notifyDataSetChanged(all);
                } else {
                    pshAdapter.notifyDataSetChanged(pshTypeMap.get(selectedData.type));
                }
                typeAdapter.setSelectedPosition(position);
                pshAdapter.setSelectedPosistion(-1);
            }

            @Override
            public void onItemLongClick(View view, int position, PSHCountByType selectedData) {

            }
        });

        pshAdapter = new DialyPatrolPshListBottomAdapter();
        rv_psh_list.setAdapter(pshAdapter);
        rv_psh_list.setNestedScrollingEnabled(true);
        rv_psh_list.setHasFixedSize(true);
        rv_psh_list.getItemAnimator().setChangeDuration(0);
        rv_psh_list.getItemAnimator().setRemoveDuration(0);
        rv_psh_list.getItemAnimator().setMoveDuration(0);
        rv_psh_list.getItemAnimator().setAddDuration(0);
    }

    public void setData(List<PSHHouse> pshHouseList) {
        if (ListUtil.isEmpty(pshHouseList)) {
            return;
        }
        all = pshHouseList;
        pshTypeMap = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> pshTypeCountMap = new LinkedHashMap<>();
        pshTypeCountMap.put("全部", pshHouseList.size());
        int otherCount = 0;
        for (PSHHouse pshHouse : pshHouseList) {
            String type = pshHouse.getDischargerType3();
            if (type.startsWith("其他")) {
                type = "其他";
            }
            if (!pshTypeMap.containsKey(type)) {
                pshTypeMap.put(type, new ArrayList<PSHHouse>());
            }
            pshTypeMap.get(type).add(pshHouse);
            if (type.startsWith("其他")) {
                otherCount++;
            } else {
                if (!pshTypeCountMap.containsKey(type)) {
                    pshTypeCountMap.put(type, 0);
                }
                int count = pshTypeCountMap.get(type) + 1;
                pshTypeCountMap.put(type, count);
            }
        }
        if (otherCount > 0) {
            pshTypeCountMap.put("其他", otherCount);
        }

        ArrayList<PSHCountByType> pshCountByTypes = new ArrayList<>();
        for (String type : pshTypeCountMap.keySet()) {
            PSHCountByType pshCountByType = new PSHCountByType();
            pshCountByType.type = type;
            pshCountByType.count = pshTypeCountMap.get(type);
            pshCountByTypes.add(pshCountByType);
        }
        typeAdapter.notifyDataSetChanged(pshCountByTypes);
        pshAdapter.notifyDataSetChanged(pshHouseList);
        typeAdapter.setSelectedPosition(0);
        adjustDrainageUserListViewHeight(all.size());
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener<PSHHouse> onRecyclerItemClickListener) {
        pshAdapter.setOnRecyclerItemClickListener(onRecyclerItemClickListener);
    }

    public void setBtn1ClickListener(OnRecyclerItemClickListener<PSHHouse> btn1ClickListener) {
        pshAdapter.setBtn1ClickListener(btn1ClickListener);
    }

    public void setBtn2ClickListener(OnRecyclerItemClickListener<PSHHouse> btn2ClickListener) {
        pshAdapter.setBtn2ClickListener(btn2ClickListener);
    }

    public void setBtn3ClickListener(OnRecyclerItemClickListener<PSHHouse> btn3ClickListener) {
        pshAdapter.setBtn3ClickListener(btn3ClickListener);
    }

    private int headerHeight = 0;
    private int itemHeight = 0;
    private int minHeight = 0;

    private void adjustDrainageUserListViewHeight(int itemCount) {
        if (headerHeight == 0) {
            headerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getContext().getResources().getDisplayMetrics());
        }
        if (itemHeight == 0) {
            itemHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getContext().getResources().getDisplayMetrics());
        }
        if (minHeight == 0) {
            minHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getContext().getResources().getDisplayMetrics());
        }
        final int initItemHeight;
        if (itemCount <= 0) {
//            initItemHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
            initItemHeight = minHeight;
        } else if (itemCount < 3) {
            initItemHeight = (int) (itemHeight * itemCount + headerHeight);
        } else {
            initItemHeight = (int) (itemHeight * 2.5 + headerHeight);
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = initItemHeight;
        setLayoutParams(layoutParams);
    }

}
