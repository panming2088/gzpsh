package com.augurit.agmobile.gzpssb.uploadfacility.service;

import android.content.Context;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.patrolcore.layer.service.AgwebPatrolLayerService2;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

/**
 * 查询数据上报的相关方法
 * Created by xcl on 2017/12/7.
 */

public class PSHWellDetailLayerService extends AgwebPatrolLayerService2{

    public PSHWellDetailLayerService(Context mContext) {
       super(mContext);
    }

    @Override
    public Observable<List<LayerInfo>> getLayerInfo() {
        return super.getSortedLayerInfos()
                .map(new Func1<List<LayerInfo>, List<LayerInfo>>() {
                    @Override
                    public List<LayerInfo> call(List<LayerInfo> layerInfos) {
                        //手动加多一个图层
                        LayerInfo info = null;
                        LayerInfo removeInfo = null;
                        for (LayerInfo layerInfo : layerInfos){
                                if(layerInfo.getLayerName().equals(PatrolLayerPresenter.UPLOAD_WELL_LAYER_NAME)){
//                                    layerInfo.setDefaultVisibility(false);
                                try {
                                    info  = (LayerInfo) layerInfo.clone();
                                    info.setLayerId(1116);
                                    info.setLayerName("我的上报");
                                    info.setLayerOrder(layerInfo.getLayerOrder() + 10);
                                } catch (CloneNotSupportedException e) {
                                    e.printStackTrace();
                                    info = new LayerInfo();
                                    info.setLayerId(1116);
                                    info.setDefaultVisibility(layerInfo.isDefaultVisible());
                                    info.setDirTypeName("我的上报");
                                    info.setChildLayer(layerInfo.getChildLayer());
                                    info.setUrl(layerInfo.getUrl());
                                    info.setOpacity(layerInfo.getOpacity());
                                    info.setType(layerInfo.getType());
                                    info.setLayerName("我的上报");
                                    info.setLayerOrder(layerInfo.getLayerOrder() + 10);
                                }
//                                break;
                            }else if(layerInfo.getLayerName().contains(PatrolLayerPresenter.MY_UPLOAD_LAYER_NAME)){
                                    //去掉原来的我的上报图层
                                    info.setLayerName(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER);
                                }
                        }

                        if (info != null){
                            layerInfos.add(info);
                        }

                        if(removeInfo !=null){
                            layerInfos.remove(removeInfo);
                        }
                        return layerInfos;
                    }
                });
    }
}
