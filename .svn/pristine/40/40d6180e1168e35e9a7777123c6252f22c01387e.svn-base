package com.augurit.agmobile.gzpssb.fragment;

import android.view.View;

import com.augurit.agmobile.gzps.BR;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.adapter.SewerageRoomCompanyAdapter;
import com.augurit.agmobile.gzpssb.bean.SewerageRoomClickItemBean;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.okhttp.Iview.ISewerageRoomClickView;
import com.augurit.agmobile.gzpssb.okhttp.Presenter.SewerageRoomClickPersenter;
import com.augurit.agmobile.gzpssb.utils.SetColorUtil;
import com.augurit.agmobile.gzpssb.common.view.MyLinearLayoutManager;
import com.augurit.am.fw.utils.ListUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by xiaoyu on 2018/4/3.
 */

public class SewerageCompany1Fragment extends MyBaseFragment implements ISewerageRoomClickView {
    private FgSewerage1Binding fgSewerageData;

    private SewerageRoomClickPersenter sewerageRoomClickPersenter = new SewerageRoomClickPersenter(this);
    private ArrayList<SewerageRoomClickItemBean.UnitListBean> roomunitListBeans;
    private SewerageRoomCompanyAdapter sewerageRoomCompanyAdapter;
    private MyLinearLayoutManager linearLayoutManager;
    private int page = 1;

    public static SewerageCompany1Fragment newInstance() {
        SewerageCompany1Fragment sewerageCompanyFragment = new SewerageCompany1Fragment();

        return sewerageCompanyFragment;
    }


    @Override
    public int initview() {
        return R.layout.fg_sewerage1;
    }

    @Override
    protected void initData() {
        super.initData();
        fgSewerageData = getBind();
        fgSewerageData.setFgsewerageonclic(new SewerageCompanyFragmentClick());

        roomunitListBeans = new ArrayList<>();

        /**
         * 从房屋点击进来
         */
        linearLayoutManager = new MyLinearLayoutManager(getActivity());
        fgSewerageData.rvItemSererageCurrency.setLayoutManager(linearLayoutManager);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(R.layout.item_fg_sewerage, BR.RoomUnitList);
        sewerageRoomCompanyAdapter = new SewerageRoomCompanyAdapter(getContext(),roomunitListBeans, map);
        fgSewerageData.rvItemSererageCurrency.setAdapter(sewerageRoomCompanyAdapter);

        fgSewerageData.pbloadingItemSererageCurrency.showLoading();
        getRoomClickdata(1);
        fgSewerageData.rvItemSererageCurrency.setLoadingMoreEnabled(true);

        //上下拉监听，刷新总数rfresh_item为0，refresh_list_type为0
        fgSewerageData.rvItemSererageCurrency.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        sewerageRoomClickPersenter.getdata(MyApplication.ID, page, 10, 0, 0);
    }

    public void showLoadedEmpty1() {
        fgSewerageData.pbloadingItemSererageCurrency.showError("", "暂无数据", "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRoomClickdata(page);

            }
        });
    }

    @Override
    public void setSewerageRoomClickList(SewerageRoomClickItemBean sewerageRoomClickList) {
        fgSewerageData.allInstallCount.setText(sewerageRoomClickList.getUnit_total()+"");
        fgSewerageData.installCount.setText(sewerageRoomClickList.getUnit_survey()+"");
        fgSewerageData.noInstallCount.setText(sewerageRoomClickList.getUnit_no_survey()+"");

        if (ListUtil.isEmpty(sewerageRoomClickList.getUnit_list()) && page == 1) {
            showLoadedEmpty1();
            return;
        }
        if (ListUtil.isEmpty(sewerageRoomClickList.getUnit_list()) && page > 1) {
            fgSewerageData.rvItemSererageCurrency.setNoMore(true);
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
        fgSewerageData.pbloadingItemSererageCurrency.showContent();
        fgSewerageData.rvItemSererageCurrency.loadMoreComplete();
        fgSewerageData.rvItemSererageCurrency.refreshComplete();
        fgSewerageData.pbloadingItemSererageCurrency.showContent();
    }


    /**
     * tab标签点击
     */
    private class SewerageCompanyFragmentClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_fg_sewerage_all:
                    SetColorUtil.setBgColor(getActivity(),0,
                            fgSewerageData.llFgSewerageAll,fgSewerageData.llFgSewerageInvestigated,
                            fgSewerageData.llFgSewerageUninvestigated
                    );
                 //   showFragment(fragments.get(0));
                    break;
                case R.id.ll_fg_sewerage_investigated:
                    SetColorUtil.setBgColor(getActivity(),1,
                            fgSewerageData.llFgSewerageAll,fgSewerageData.llFgSewerageInvestigated,
                            fgSewerageData.llFgSewerageUninvestigated
                    );
                  //  showFragment(fragments.get(1));
                    break;
                case R.id.ll_fg_sewerage_uninvestigated:
                    SetColorUtil.setBgColor(getActivity(),2,
                            fgSewerageData.llFgSewerageAll,fgSewerageData.llFgSewerageInvestigated,
                            fgSewerageData.llFgSewerageUninvestigated
                    );
                  //  showFragment(fragments.get(2));
                    break;
//                case R.id.ll_fg_sewerage_newadd:
//                    SetColorUtil.setBgColor(getActivity(),3,
//                            fgSewerageData.llFgSewerageAll,fgSewerageData.llFgSewerageInvestigated,
//                            fgSewerageData.llFgSewerageUninvestigated,fgSewerageData.llFgSewerageNewadd
//                    );
//                    userInfo.clear();
//                    setSewerageData("新增");
//                    sewerageCompanyAdapter.notifyDataSetChanged();
//                    break;
            }
        }
    }


}
