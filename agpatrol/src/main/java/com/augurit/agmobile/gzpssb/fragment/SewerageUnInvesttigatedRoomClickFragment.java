package com.augurit.agmobile.gzpssb.fragment;


import com.augurit.agmobile.gzps.BR;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.ItemSererageCurrencyData;
import com.augurit.agmobile.gzpssb.adapter.SewerageRoomCompanyAdapter;
import com.augurit.agmobile.gzpssb.bean.SewerageRoomClickItemBean;
import com.augurit.agmobile.gzpssb.event.RefreshDataListEvent;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.okhttp.Iview.ISewerageRoomClickView;
import com.augurit.agmobile.gzpssb.okhttp.Presenter.SewerageRoomClickPersenter;
import com.augurit.agmobile.gzpssb.common.view.MyLinearLayoutManager;
import com.augurit.am.fw.utils.ListUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyu on 2018/4/9.
 * 总数Fragment
 */

public class SewerageUnInvesttigatedRoomClickFragment extends MyBaseFragment implements ISewerageRoomClickView {

    private SewerageRoomClickPersenter sewerageRoomClickPersenter = new SewerageRoomClickPersenter(this);
    private ItemSererageCurrencyData itemSererageCurrencyData;
    private List<SewerageRoomClickItemBean.UnitListBean> roomunitListBeans;
    private SewerageRoomCompanyAdapter sewerageRoomCompanyAdapter;
    private MyLinearLayoutManager linearLayoutManager;
    private int page = 1;

    public static SewerageUnInvesttigatedRoomClickFragment newInstance() {
        SewerageUnInvesttigatedRoomClickFragment sewerageTotalFragment = new SewerageUnInvesttigatedRoomClickFragment();
        return sewerageTotalFragment;
    }


    @Override
    public int initview() {
        return R.layout.item_sewerage_currency;

    }
    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(RefreshDataListEvent event) {
        if (MyApplication.refreshListType == RefreshDataListEvent.UPDATE_ROOM_UNIT_LIST1) {
//            page =1;
            getRoomClickdata(1);
        }
    }
    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        itemSererageCurrencyData = getBind();

        roomunitListBeans = new ArrayList<>();

        /**
         * 从房屋点击进来
         */
        linearLayoutManager = new MyLinearLayoutManager(getActivity());
        itemSererageCurrencyData.rvItemSererageCurrency.setLayoutManager(linearLayoutManager);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(R.layout.item_fg_sewerage, BR.RoomUnitList);
        sewerageRoomCompanyAdapter = new SewerageRoomCompanyAdapter(getContext(),roomunitListBeans, map);
        itemSererageCurrencyData.rvItemSererageCurrency.setAdapter(sewerageRoomCompanyAdapter);
        getRoomClickdata(1);
      //  itemSererageCurrencyData.pbloadingItemSererageCurrency.showLoading();
        itemSererageCurrencyData.rvItemSererageCurrency.setLoadingMoreEnabled(true);

        //上下拉监听，刷新总数rfresh_item为0，refresh_list_type为0
        itemSererageCurrencyData.rvItemSererageCurrency.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getRoomClickdata(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                getRoomClickdata(page);
            }
        });

    }


    public void getRoomClickdata(int page) {
        sewerageRoomClickPersenter.getdata(MyApplication.ID, page, 10, 2, 0);
    }

    public void showLoadedEmpty1() {
     /*   itemSererageCurrencyData.pbloadingItemSererageCurrency.showError("", "暂无数据", "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRoomClickdata(page);
            }
        });*/
    }

    @Override
    public void setSewerageRoomClickList(SewerageRoomClickItemBean sewerageRoomClickList) {
        if (ListUtil.isEmpty(sewerageRoomClickList.getUnit_list()) && page == 1) {
            showLoadedEmpty1();
            return;
        }
        if (ListUtil.isEmpty(sewerageRoomClickList.getUnit_list()) && page > 1) {
            itemSererageCurrencyData.rvItemSererageCurrency.setNoMore(true);
            return;
        }
        if (page == 1) {
            roomunitListBeans.clear();
            roomunitListBeans.addAll(sewerageRoomClickList.getUnit_list());
            sewerageRoomCompanyAdapter.notifyDataSetChanged();
        } else {
            roomunitListBeans.addAll(sewerageRoomClickList.getUnit_list());
            sewerageRoomCompanyAdapter.notifyDataSetChanged();
        }
      //  itemSererageCurrencyData.pbloadingItemSererageCurrency.showContent();
        itemSererageCurrencyData.rvItemSererageCurrency.loadMoreComplete();
        itemSererageCurrencyData.rvItemSererageCurrency.refreshComplete();
     //   itemSererageCurrencyData.pbloadingItemSererageCurrency.showContent();
    }



}
