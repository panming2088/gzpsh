package com.augurit.agmobile.gzpssb.pshpublicaffair.service;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

import com.augurit.agmobile.gzps.uploadfacility.dao.UploadLayerApi;
import com.augurit.agmobile.gzps.uploadfacility.service.CorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.service.UploadFacilityService;
import com.augurit.agmobile.gzpssb.bean.DoorNOBean;
import com.augurit.agmobile.gzpssb.utils.SewerageLayerFieldKeyConstant;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.service.LayerServiceFactory;
import com.augurit.agmobile.patrolcore.layer.service.AgwebPatrolLayerService2;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.fw.net.AMNetwork;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.log.LogUtil;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.tasks.SpatialRelationship;
import com.esri.core.tasks.ags.query.Query;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * 查询数据上报的相关方法
 * Created by xcl on 2017/12/7.
 */

public class PSHDisplayDoorNoLayerService extends AgwebPatrolLayerService2{

    public PSHDisplayDoorNoLayerService(Context mContext) {
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
                        for (LayerInfo layerInfo : layerInfos){
                            if(layerInfo.getLayerName().contains(PatrolLayerPresenter.UPLOAD_LAYER_NAME)){
                                layerInfo.setLayerName(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER);
                            }
                            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.DISPLAY_DOOR_NO_LAYER)){
                                try {
//                                    info  = (LayerInfo) layerInfo.clone();
//                                    info.setLayerId(1116);
//                                    info.setLayerName("数据上报");
//                                    info.setLayerOrder(layerInfo.getLayerOrder() +10);
//                                    layerInfo.setLayerName(PatrolLayerPresenter.UPLOAD_LAYER_NAME);
//                                } catch (CloneNotSupportedException e) {
                                } catch (Exception e) {
                                    layerInfo.setLayerName(PatrolLayerPresenter.UPLOAD_LAYER_NAME);
//                                    e.printStackTrace();
//                                    info = new LayerInfo();
//                                    info.setLayerId(1116);
//                                    info.setDefaultVisibility(layerInfo.isDefaultVisible());
//                                    info.setDirTypeName("数据上报");
//                                    info.setChildLayer(layerInfo.getChildLayer());
//                                    info.setUrl(layerInfo.getUrl());
//                                    info.setOpacity(layerInfo.getOpacity());
//                                    info.setType(layerInfo.getType());
//                                    info.setLayerName("我的上报");
//                                    info.setLayerOrder(layerInfo.getLayerOrder() +10);
                                }
                                break;
                            }else{

                            }
                        }

                        if (info != null){
                            layerInfos.add(info);
                        }
                        return layerInfos;
                    }
                });
    }
}
