package com.augurit.agmobile.gzpssb.fragment;

import android.os.Bundle;
import android.view.View;

import com.augurit.agmobile.gzps.BR;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.ItemSererageCurrencyData;
import com.augurit.agmobile.gzpssb.adapter.SewerageCompanyAdapter;
import com.augurit.agmobile.gzpssb.bean.SewerageInvestBean;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.okhttp.Iview.ISewerageItemView;
import com.augurit.agmobile.gzpssb.okhttp.Presenter.SewerageItemPresenter;
import com.augurit.agmobile.gzpssb.common.view.MyLinearLayoutManager;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyu on 2018/4/9.
 */

public class SewerageUnInvestigatedFragment extends MyBaseFragment implements ISewerageItemView {

    private SewerageItemPresenter sewerageItemPresenter = new SewerageItemPresenter(this);
    private ItemSererageCurrencyData itemSererageCurrencyData;
    //    private String id = "GZPCS3707201304210000000008577203";
//    private String dzdm = "440105002010500000000000000012";
    private List<SewerageItemBean.UnitListBean> unitListBeans;
    private SewerageCompanyAdapter sewerageCompanyAdapter;
    private MyLinearLayoutManager linearLayoutManager;
    private int page = 1;
    private String mName = "";
    private String add_type;
    private boolean isNull = false;
    public static SewerageUnInvestigatedFragment newInstance(Bundle bundle) {
        SewerageUnInvestigatedFragment sewerageUnInvestigatedFragment = new SewerageUnInvestigatedFragment();
        sewerageUnInvestigatedFragment.setArguments(bundle);
        return sewerageUnInvestigatedFragment;
    }

    /*   @Override
       public void onDestroyView() {
           super.onDestroyView();
           if (EventBus.getDefault().isRegistered(this)) {
               EventBus.getDefault().unregister(this);
           }
       }*/
    @Override
    public int initview() {
        return R.layout.item_sewerage_currency;

    }

    /**
     * @param mName  筛选条件
     * @param isNull 当前筛选条件name为“”的时候，true
     */
    public void getName(String mName, boolean isNull) {
        this.mName = mName;
        this.isNull = isNull;
        page = 1;
        getdata(page, mName);

    }
    @Override
    protected void initData() {
        super.initData();
        itemSererageCurrencyData = getBind();

        add_type = getArguments().getString("add_type");
        // EventBus.getDefault().register(this);

        unitListBeans = new ArrayList<>();
        linearLayoutManager = new MyLinearLayoutManager(getActivity());
        itemSererageCurrencyData.rvItemSererageCurrency.setLayoutManager(linearLayoutManager);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(R.layout.item_fg_sewerage, BR.unitlist);
        sewerageCompanyAdapter = new SewerageCompanyAdapter(getActivity(), unitListBeans, map);
        itemSererageCurrencyData.rvItemSererageCurrency.setAdapter(sewerageCompanyAdapter);


        itemSererageCurrencyData.pbloadingItemSererageCurrency.showLoading();
        getdata(1, mName);
        itemSererageCurrencyData.rvItemSererageCurrency.setLoadingMoreEnabled(true);

        //上下拉监听，刷新总数rfresh_item为0，refresh_list_type为0
        itemSererageCurrencyData.rvItemSererageCurrency.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getdata(page, mName);
            }

            @Override
            public void onLoadMore() {
                page++;
                getdata(page, mName);
            }
        });
        sewerageItemPresenter.setIGetFail(new SewerageItemPresenter.IGetFail() {
            @Override
            public void onFail(String code) {
                if(  itemSererageCurrencyData.pbloadingItemSererageCurrency!=null){
                    itemSererageCurrencyData.pbloadingItemSererageCurrency.showContent();
                    showFail();
                }
                if(!"".equals(code)){
                    ToastUtil.shortToast(getContext(),code);
                }
            }
        });

    }

    private void getdata(int page, String name) {
        sewerageItemPresenter.getdata(MyApplication.GUID, MyApplication.DZDM,
                page, 10, 2, 2, name, add_type);
    }

    public void showLoadedEmpty() {
        itemSererageCurrencyData.pbloadingItemSererageCurrency.showError("", "暂无数据", "刷新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata(page, mName);
            }
        });
    }

    private void showFail() {
        itemSererageCurrencyData.pbloadingItemSererageCurrency.showError("", "获取数据失败", "重新获取", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata(page, mName);
                Bundle bundle=new Bundle();
                bundle.putString("HaveNetRefresh",SewerageCompanyFragment.REFRESH_UNINVESTIGATEDFRAGMENT);
                EventBus.getDefault().post(bundle);
            }
        });
    }


        @Override
    public void setSewerageItemList(SewerageItemBean sewerageItemList) {
        if (ListUtil.isEmpty(sewerageItemList.getUnit_list()) && page == 1) {
            showLoadedEmpty();
            return;
        }
        if (ListUtil.isEmpty(sewerageItemList.getUnit_list()) && page > 1) {
            itemSererageCurrencyData.rvItemSererageCurrency.setNoMore(true);
            return;
        }
        if (page == 1) {
            unitListBeans.clear();
            unitListBeans.addAll(sewerageItemList.getUnit_list());
            sewerageCompanyAdapter.notifyDataSetChanged();
        } else {
       /*     if(!TextUtils.isEmpty(mName)){
                //是筛选的话，清空当前数据，显示筛选数据
                if(ListUtil.isEmpty(sewerageItemList.getUnit_list()) ){
                    showLoadedEmpty();
                    return;
                }
            }else{*/
            if (isNull) {
                unitListBeans.clear();
                unitListBeans.addAll(sewerageItemList.getUnit_list());
                isNull = false;
            }
            unitListBeans.addAll(sewerageItemList.getUnit_list());

            sewerageCompanyAdapter.notifyDataSetChanged();
        }
        itemSererageCurrencyData.pbloadingItemSererageCurrency.showContent();
        itemSererageCurrencyData.rvItemSererageCurrency.loadMoreComplete();
        itemSererageCurrencyData.rvItemSererageCurrency.refreshComplete();
        //   itemSererageCurrencyData.pbloadingItemSererageCurrency.showContent();
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
}