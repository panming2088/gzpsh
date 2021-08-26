package com.augurit.agmobile.gzpssb.fragment;

import android.content.Intent;
import android.view.View;

import com.augurit.agmobile.gzps.BR;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.adapter.SewerageRoomAdapter;
import com.augurit.agmobile.gzpssb.bean.SewerageInvestBean;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.okhttp.Iview.ISewerageItemView;
import com.augurit.agmobile.gzpssb.okhttp.Presenter.SewerageItemPresenter;
import com.augurit.agmobile.gzpssb.common.view.MyLinearLayoutManager;
import com.augurit.am.fw.utils.ListUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyu on 2018/4/3.
 */

public class SewerageRoomFragment extends MyBaseFragment implements ISewerageItemView{
    private MyLinearLayoutManager linearLayoutManager;
    private com.augurit.agmobile.gzpssb.fragment.FgSewerageNotitleBinding fgSewerageData;
    private SewerageRoomAdapter sewerageRoomAdapter;
    private SewerageItemPresenter sewerageItemPresenter=new SewerageItemPresenter(this);
    private List<SewerageItemBean.HouseListBean> houseListBeans;
    private int page = 1;

    private String smallAddress;
    public static SewerageRoomFragment newInstance() {
        SewerageRoomFragment sewerageRoomFragment = new SewerageRoomFragment();
        return sewerageRoomFragment;
    }


    @Override
    public int initview()  {
        return R.layout.fg_sewerage_notitle;
    }

    @Override
    protected void initData() {
        super.initData();
        fgSewerageData = getBind();

        /**
         * 加载adapter
         */
        houseListBeans = new ArrayList<>();
        linearLayoutManager = new MyLinearLayoutManager(getActivity());
        fgSewerageData.rvFgSewerage.setLayoutManager(linearLayoutManager);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(R.layout.item_fg_sewerage, BR.houseList);
        sewerageRoomAdapter = new SewerageRoomAdapter(houseListBeans, map);
        fgSewerageData.rvFgSewerage.setAdapter(sewerageRoomAdapter);
        fgSewerageData.pbloadingFgSewerage.showLoading();
        getdata(1);

        //上下拉监听，刷新总数rfresh_item为0，refresh_list_type为0
        fgSewerageData.rvFgSewerage.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getdata(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                getdata(page);
            }
        });

        fgSewerageData.setFgseweragenotitleonclic(new MyOnclick());
    }

    //refresh_list_type为0时，刷新两个列表，1刷新房屋列表，2刷新单位列表，refresh_item刷新总数0，刷新已调查，未调查2
    private void getdata(int page){
        sewerageItemPresenter.getdata(MyApplication.GUID,MyApplication.DZDM,
                page,10,0,0,null);
    }


    public void showLoadedEmpty() {
        fgSewerageData.pbloadingFgSewerage.showError("", "暂无数据", "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata(page);
            }
        });
    }

    private SewerageItemBean mSewerageItemBean;
    /**
     * 接口回调
     * @param sewerageItemList
     */
    @Override
    public void setSewerageItemList(SewerageItemBean sewerageItemList) {
        mSewerageItemBean = sewerageItemList;




        if(ListUtil.isEmpty(sewerageItemList.getHouse_list())&&page==1){
            showLoadedEmpty();
            return;
        }
        if (ListUtil.isEmpty(sewerageItemList.getHouse_list())&& page > 1){
            fgSewerageData.rvFgSewerage.setNoMore(true);
            return;
        }else{
//            MyApplication.HOUSE_ID = sewerageItemList.getHouse_list().get(0).getHousebuildId();
        }
        if(page==1){
            houseListBeans.clear();
            sewerageRoomAdapter.initData(sewerageItemList.getSmallAddress(),sewerageItemList.getHouse_list());
            sewerageRoomAdapter.notifyDataSetChanged();
        }else{
            sewerageRoomAdapter.initData(sewerageItemList.getSmallAddress(),sewerageItemList.getHouse_list());
            sewerageRoomAdapter.notifyDataSetChanged();
        }
        fgSewerageData.pbloadingFgSewerage.showContent();
        fgSewerageData.rvFgSewerage.loadMoreComplete();
        fgSewerageData.rvFgSewerage.refreshComplete();
        fgSewerageData.pbloadingFgSewerage.showContent();

    }

    @Override
    public void onInvestCode(SewerageInvestBean code) {

    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onCompelete() {

    }

    private class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_fg_sewerage_notitle_add:
                    Intent intent = new Intent( view.getContext(), SewerageTableActivity.class);
//                    SewerageBeanManger.getInstance().setDoorplateAddressCode(MyApplication.DZDM);
//                    SewerageBeanManger.getInstance().setHouseId(houseListBeans.get(0).getHousebuildId());
//                    SewerageBeanManger.getInstance().setHouseIdFlag("");
                    view.getContext().startActivity(intent);
                    break;
            }
        }
    }
}
