package com.augurit.agmobile.gzpssb.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.augurit.agmobile.gzps.common.model.Result;
import com.augurit.agmobile.gzpssb.ItemFGSewerageDataHouse;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.adapter.BaseRecyleAdapter.OnRecycleitemOnClic;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.event.RefreshDataListEvent;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.DoorNoRespone;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfo;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.UploadDoorNoBean;
import com.augurit.agmobile.gzpssb.pshdoorno.add.service.PSHUploadDoorNoService;
import com.augurit.agmobile.gzpssb.secondpsh.SecondLevelPshListActivity;
import com.augurit.agmobile.gzpssb.secondpsh.service.SecondLevelPshService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xiaoyu on 2018/4/3.
 */

public class SewerageCompanyAdapter extends BaseRecyleAdapter implements OnRecycleitemOnClic {
    private List<SewerageItemBean.UnitListBean> unitListBeans;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SewerageCompanyAdapter(Context context, List<?> data, Map<Integer, Integer> hashMap) {
        super(data, hashMap);
        this.mContext = context;
        setOnRecycleitemOnClic(this);
        unitListBeans = (List<SewerageItemBean.UnitListBean>) data;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final SewerageItemBean.UnitListBean unitListBean = unitListBeans.get(position);
        ItemFGSewerageDataHouse itemFGSewerageDataHouse = (ItemFGSewerageDataHouse) holder.dataBinding;
        itemFGSewerageDataHouse.addressItemSeweragecompany1.setText(unitListBeans.get(position).getName());
        itemFGSewerageDataHouse.roomtypeItemSeweragecompany1.setText("法人："+unitListBeans.get(position).getLegalRepresent());
        if (unitListBean.getIsrecorded()==0) {
            itemFGSewerageDataHouse.tvRightUp.setText("已调查");
            itemFGSewerageDataHouse.tvRightUp.setTextColor(Color.parseColor("#3EA500"));
        }else if(unitListBean.getIsrecorded()==1){
            itemFGSewerageDataHouse.tvRightUp.setText("未调查");
            itemFGSewerageDataHouse.tvRightUp.setTextColor(Color.parseColor("#FFFFC248"));
        }else{
            itemFGSewerageDataHouse.tvRightUp.setText("已注销");
            itemFGSewerageDataHouse.tvRightUp.setTextColor(Color.parseColor("#b1afab"));
        }
//        itemFGSewerageDataHouse.childItemSeweragecompany1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SecondLevelPshService service = new SecondLevelPshService(mContext);
//                service.getSecondLevelPshInfoById(unitListBean.getHouseId())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<SecondLevelPshInfo>() {
//                            @Override
//                            public void onCompleted() {
//                            }
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//                            @Override
//                            public void onNext(SecondLevelPshInfo listResult) {
//                                Intent intent = new Intent(mContext, SecondLevelPshListActivity.class);
//                                intent.putExtra("data", listResult);
//                            }
//                        });
//
//            }
//        });

        itemFGSewerageDataHouse.childItemSeweragecompany1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                Intent intent = new Intent(mContext, SecondLevelPshListActivity.class);
                                intent.putExtra("unitListBean", unitListBean);
                                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public void onItemClic(View view, int position) {
        SewerageItemBean.UnitListBean unitListBean = unitListBeans.get(position);
        if(unitListBean.getIsrecorded() == 0 || unitListBean.getIsrecorded() == 2){
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(unitListBean.getId());
            }
//            ToastUtil.shortToast(mContext,"已调查暂不能点进去");
            return;
        }
        MyApplication.refreshListType = RefreshDataListEvent.UPDATE_MIAN_LIST;
        Intent intent = new Intent( view.getContext(), SewerageTableActivity.class);
        intent.putExtra("unitListBeans", unitListBeans.get(position));
        if(unitListBeans.get(position).getIsexist()==1){
            //情景1，挂在房栋下面
            intent.putExtra("HouseIdFlag","0");
            intent.putExtra("HouseId",MyApplication.buildId);
//            SewerageBeanManger.getInstance().setHouseIdFlag("0");
//            SewerageBeanManger.getInstance().setHouseId(ParamsInstance.getInstance().getBuildId());
        }else if(unitListBeans.get(position).getIsexist()==0){
            //情景1.1，挂在房套下面
            intent.putExtra("HouseIdFlag","1");
            intent.putExtra("HouseId",unitListBeans.get(position).getHouseId());
//            SewerageBeanManger.getInstance().setHouseIdFlag("1");
//            SewerageBeanManger.getInstance().setHouseId(unitListBeans.get(position).getHouseId());
        }
        intent.putExtra("UnitId",unitListBeans.get(position).getId()+"");
//        SewerageBeanManger.getInstance().setUnitId(unitListBeans.get(position).getId()+"");
        view.getContext().startActivity(intent);

    }

    public interface OnItemClickListener{
        void onItemClick(int id);
    }
}