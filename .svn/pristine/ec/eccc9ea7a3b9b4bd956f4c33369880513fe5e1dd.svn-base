package com.augurit.agmobile.gzpssb.pshdoorno.doorlist.service;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.augurit.agmobile.gzps.uploadfacility.dao.UploadLayerApi;
import com.augurit.agmobile.gzps.uploadfacility.service.CorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.service.UploadFacilityService;
import com.augurit.agmobile.gzpssb.bean.DoorNOBean;
import com.augurit.agmobile.gzpssb.bean.DrainageUnit;
import com.augurit.agmobile.gzpssb.bean.DrainageUserBean;
import com.augurit.agmobile.gzpssb.bean.HangUpWellInfoBean;
import com.augurit.agmobile.gzpssb.bean.UploadBean;
import com.augurit.agmobile.gzpssb.common.PortSelectUtil;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemFeatureOrAddr;
import com.augurit.agmobile.gzpssb.utils.SewerageLayerFieldKeyConstant;
import com.augurit.agmobile.mapengine.common.utils.GeometryUtil;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.service.LayerServiceFactory;
import com.augurit.agmobile.patrolcore.layer.service.AgwebPatrolLayerService2;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.AMNetwork;
import com.augurit.am.fw.net.util.SharedPreferencesUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.FuncN;
import rx.schedulers.Schedulers;

import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_DIALY;
import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_DIALY2;
import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_JIEBOJING;
import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_JIEHUJING;
import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_USER;
import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_USER2;

/**
 * 查询数据上报的相关方法
 * Created by xcl on 2017/12/7.
 */

public class SewerageLayerService extends AgwebPatrolLayerService2 {

    private AMNetwork amNetwork;
    private UploadLayerApi uploadLayerApi;
    private Query query;
    private CorrectFacilityService correctFacilityService;
    private UploadFacilityService uploadFacilityService;
    private Context mContext;
    private boolean isAdd = false;

    public SewerageLayerService(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    public Observable<List<LayerInfo>> getLayerInfo() {
        return super.getSortedLayerInfos()
                .map(new Func1<List<LayerInfo>, List<LayerInfo>>() {
                    @Override
                    public List<LayerInfo> call(List<LayerInfo> layerInfos) {
                        //手动加多一个图层
                        LayerInfo info = null;

                        for (LayerInfo layerInfo : layerInfos) {
                            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.UPLOAD_LAYER_NAME)) {
                                layerInfo.setLayerName(PatrolLayerPresenter.LOCAL_UPLOAD_LAYER_NAME);
                            }
                            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.DOOR_NO_LAYER)) {
                                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
                                sharedPreferencesUtil.setString(PatrolLayerPresenter.DOOR_NO_LAYER, layerInfo.getUrl());
                                isAdd = true;
                            }
                            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.TYPICAL_DOOR_NO_LAYER)) {
                                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
                                sharedPreferencesUtil.setString(PatrolLayerPresenter.TYPICAL_DOOR_NO_LAYER, layerInfo.getUrl());
                            }
                            if (layerInfo.getLayerName().equals(DRAINAGE_USER)) {
                                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
                                sharedPreferencesUtil.setString(DRAINAGE_USER, layerInfo.getUrl());
                            }
                            if (layerInfo.getLayerName().equals(DRAINAGE_USER2)) {
                                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
                                sharedPreferencesUtil.setString(DRAINAGE_USER2, layerInfo.getUrl());
                            }

                            if (layerInfo.getLayerName().equals(DRAINAGE_DIALY)) {
                                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
                                sharedPreferencesUtil.setString(DRAINAGE_DIALY, layerInfo.getUrl());
                            }
                            if (layerInfo.getLayerName().equals(DRAINAGE_DIALY2)) {
                                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
                                sharedPreferencesUtil.setString(DRAINAGE_DIALY2, layerInfo.getUrl());
                            }
                            if (layerInfo.getLayerName().equals(DRAINAGE_JIEBOJING)) {
                                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
                                sharedPreferencesUtil.setString(DRAINAGE_JIEBOJING, layerInfo.getUrl());
                            }
                            if (layerInfo.getLayerName().equals(DRAINAGE_JIEHUJING)) {
                                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
                                sharedPreferencesUtil.setString(DRAINAGE_JIEHUJING, layerInfo.getUrl());
                            }
                            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.LOCAL_UPLOAD_LAYER_NAME)) {
                                try {
                                    info = (LayerInfo) layerInfo.clone();
                                    info.setLayerId(1116);
                                    info.setLayerName("我的上报");
                                    info.setLayerOrder(layerInfo.getLayerOrder() - 1);
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
                                    info.setLayerOrder(layerInfo.getLayerOrder() - 1);
                                }
                            }
//                            if (info != null && isAdd) {
//                                break;
//                            }

                        }

                        if (info != null) {
                            layerInfos.add(info);
                        }
                        return layerInfos;
                    }
                });
    }

    /**
     * 点击地图时进行查询上报或者新增的信息
     *
     * @param point
     * @param spatialReference
     * @param resolution
     * @param callback2
     */
    public void queryDoorDataInfo(Point point, SpatialReference spatialReference, double resolution,
                                  final Callback2<List<DoorNOBean>> callback2) {

        Geometry geometry = GeometryEngine.buffer(point, spatialReference, 60 * resolution, null);
        if (query == null) {
            callback2.onFail(new Exception("查询参数为空"));
            return;
        }
        query.setGeometry(geometry);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String url = sharedPreferencesUtil.getString(PatrolLayerPresenter.DOOR_NO_LAYER, "") + "/0";

        if ("/0".equals(url)) {
            url = getDoorNOLayerUrl() + "/0";
        }
        if ("/0".equals(url)) {
            callback2.onFail(new Exception("无法查询到我的上报图层的url"));
            return;
        }

        final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                url, ArcGISFeatureLayer.MODE.SNAPSHOT);
        layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
            @Override
            public void onCallback(FeatureSet featureSet) {
                final ArrayList<DoorNOBean> doorBeans = new ArrayList<DoorNOBean>();
                Point point = null;
                //判断是新增还是纠错
                if (featureSet.getGraphics().length != 0) {
                    Graphic[] graphics = featureSet.getGraphics();
                    DoorNOBean doorNOBean = null;
                    for (Graphic graphic : graphics) {
                        doorNOBean = new DoorNOBean();
                        doorNOBean.setAddress(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DZQC)));
                        doorNOBean.setS_guid(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.S_GUID)));
                        doorNOBean.setStree(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.MPWZHM)));
                        doorNOBean.setDzdm(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DZDM)));
                        doorNOBean.setPSDY_OID(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.PSDY_OID)));
                        doorNOBean.setISTATUE(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ISTATUE)));
                        doorNOBean.setPSDY_NAME(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.PSDY_NAME)));
                        doorNOBean.setIsExist(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ISEXIST)));
                        if (graphic.getGeometry() != null) {
                            point = (Point) graphic.getGeometry();
                            doorNOBean.setX(objectToDouble(point.getX()));
                            doorNOBean.setY(objectToDouble(point.getY()));
                        }

                        doorBeans.add(doorNOBean);
                    }

                    if (ListUtil.isEmpty(doorBeans)) {
                        callback2.onSuccess(new ArrayList<DoorNOBean>());
                    } else {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback2.onSuccess(doorBeans);
                            }
                        });
                    }

                } else {
                    callback2.onSuccess(new ArrayList<DoorNOBean>());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback2.onFail(new Exception(throwable));
            }
        });
    }

    /**
     * 点击地图时进行查询接户井的信息
     *
     * @param point
     * @param spatialReference
     * @param resolution
     * @param callback2
     */
    public void queryHangUpWellInfo(Point point, SpatialReference spatialReference, double resolution,
                                    final Callback2<List<HangUpWellInfoBean>> callback2) {

        Geometry geometry = GeometryEngine.buffer(point, spatialReference, 60 * resolution, null);
        if (query == null) {
            callback2.onFail(new Exception("查询参数为空"));
            return;
        }
        query.setGeometry(geometry);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String url = sharedPreferencesUtil.getString(DRAINAGE_JIEHUJING, "") + "/0";

        if ("/0".equals(url)) {
            url = getHangUpWellLayerUrl() + "/0";
        }
        if ("/0".equals(url)) {
            callback2.onFail(new Exception("无法查询到接户井图层的url"));
            return;
        }

        final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                url, ArcGISFeatureLayer.MODE.SNAPSHOT);
        layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
            @Override
            public void onCallback(FeatureSet featureSet) {
                final ArrayList<HangUpWellInfoBean> hangUpWellBeans = new ArrayList<>();
                Point point;
                //判断是新增还是纠错
                if (featureSet.getGraphics().length != 0) {
                    Graphic[] graphics = featureSet.getGraphics();
                    HangUpWellInfoBean hangUpWellBean = null;
                    for (Graphic graphic : graphics) {
                        hangUpWellBean = new HangUpWellInfoBean();
                        final Map<String, Object> attributes = graphic.getAttributes();
                        hangUpWellBean.setObjectId(objectToString(attributes.get(SewerageLayerFieldKeyConstant.OBJECTID)));
                        hangUpWellBean.setUsid(objectToString(attributes.get(SewerageLayerFieldKeyConstant.USID)));
                        if (graphic.getGeometry() != null) {
                            point = (Point) graphic.getGeometry();
                            hangUpWellBean.setX(objectToDouble(point.getX()));
                            hangUpWellBean.setY(objectToDouble(point.getY()));
                        }
                        hangUpWellBeans.add(hangUpWellBean);
                    }

                    if (ListUtil.isEmpty(hangUpWellBeans)) {
                        callback2.onSuccess(new ArrayList<HangUpWellInfoBean>());
                    } else {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback2.onSuccess(hangUpWellBeans);
                            }
                        });
                    }

                } else {
                    callback2.onSuccess(new ArrayList<HangUpWellInfoBean>());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback2.onFail(new Exception(throwable));
            }
        });
    }

    /**
     * 点击地图时进行查询上报或者新增的信息
     *
     * @param point
     * @param spatialReference
     * @param resolution
     * @param callback2
     */
    public void queryPshDataInfo(Point point, SpatialReference spatialReference, double resolution,
                                 final Callback2<List<PSHHouse>> callback2) {

        Geometry geometry = GeometryEngine.buffer(point, spatialReference, 60 * resolution, null);
        if (query == null) {
            callback2.onFail(new Exception("查询参数为空"));
            return;
        }
        query.setGeometry(geometry);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String url = sharedPreferencesUtil.getString(DRAINAGE_USER, "") + "/0";

        if ("/0".equals(url)) {
            url = getPshLayerUrl() + "/0";
        }
        if ("/0".equals(url)) {
            callback2.onFail(new Exception("无法查询到我的上报图层的url"));
            return;
        }

        final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                url, ArcGISFeatureLayer.MODE.SNAPSHOT);
        layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
            @Override
            public void onCallback(FeatureSet featureSet) {
                final ArrayList<PSHHouse> doorBeans = new ArrayList<PSHHouse>();
                Point point = null;
                //判断是新增还是纠错
                if (featureSet.getGraphics().length != 0) {
                    Graphic[] graphics = featureSet.getGraphics();
                    PSHHouse doorNOBean = null;
                    for (Graphic graphic : graphics) {
                        doorNOBean = new PSHHouse();
                        doorNOBean.setAddr(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ADDR)));
                        doorNOBean.setId(objectToInt(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ID)));
                        doorNOBean.setObjectId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.OBJECTID)));
                        doorNOBean.setMph(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.MPH)));
                        doorNOBean.setUnitId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.UNIT_ID)));
                        doorNOBean.setName(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.NAME)));
                        doorNOBean.setState(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.STATE)));
                        doorNOBean.setDischargerType3(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DISCHARGER_TYPE3)));
                        doorNOBean.setReportType(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.REPORT_TYPE)));
                        doorNOBean.setPsdyName(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.PSDY_NAME)));
                        doorNOBean.setPsdyId(objectToString(graphic.getAttributes().get("PSDY_ID")));
                        if (graphic.getGeometry() != null) {
                            point = (Point) graphic.getGeometry();
                            doorNOBean.setX(objectToDouble(point.getX()));
                            doorNOBean.setY(objectToDouble(point.getY()));
                        }

                        doorBeans.add(doorNOBean);
                    }

                    if (ListUtil.isEmpty(doorBeans)) {
                        callback2.onSuccess(new ArrayList<PSHHouse>());
                    } else {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback2.onSuccess(doorBeans);
                            }
                        });
                    }

                } else {
                    callback2.onSuccess(new ArrayList<PSHHouse>());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback2.onFail(new Exception(throwable));
            }
        });
    }

    /**
     * 点击地图时进行查询上报或者新增的信息
     *
     * @param point
     * @param spatialReference
     * @param resolution
     * @param callback2
     */
    public void queryPshDialyDataInfo(Point point, SpatialReference spatialReference, double resolution,
                                 final Callback2<List<PSHHouse>> callback2) {

        Geometry geometry = GeometryEngine.buffer(point, spatialReference, 60 * resolution, null);
        if (query == null) {
            callback2.onFail(new Exception("查询参数为空"));
            return;
        }
        query.setGeometry(geometry);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String url = sharedPreferencesUtil.getString(DRAINAGE_DIALY, "") + "/0";
//        String url = sharedPreferencesUtil.getString(DRAINAGE_USER, "") + "/0";

        if ("/0".equals(url)) {
            url = getPshLayerUrl() + "/0";
        }
        if ("/0".equals(url)) {
            callback2.onFail(new Exception("无法查询到我的上报图层的url"));
            return;
        }

        final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                url, ArcGISFeatureLayer.MODE.SNAPSHOT);
        layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
            @Override
            public void onCallback(FeatureSet featureSet) {
                final ArrayList<PSHHouse> doorBeans = new ArrayList<PSHHouse>();
                Point point = null;
                //判断是新增还是纠错
                if (featureSet.getGraphics().length != 0) {
                    Graphic[] graphics = featureSet.getGraphics();
                    PSHHouse doorNOBean = null;
                    for (Graphic graphic : graphics) {
                        doorNOBean = new PSHHouse();
                        doorNOBean.setAddr(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ADDR)));
                        doorNOBean.setId(objectToInt(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ID)));
                        doorNOBean.setObjectId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.OBJECTID)));
                        doorNOBean.setMph(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.MPH)));
                        doorNOBean.setUnitId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.UNIT_ID)));
                        doorNOBean.setName(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.NAME)));
                        doorNOBean.setState(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.STATE)));
                        doorNOBean.setDischargerType3(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DISCHARGER_TYPE3)));
                        doorNOBean.setReportType(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.REPORT_TYPE)));
                        doorNOBean.setPsdyName(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.PSDY_NAME)));
                        doorNOBean.setPsdyId(objectToString(graphic.getAttributes().get("PSDY_ID")));
                        if (graphic.getGeometry() != null) {
                            point = (Point) graphic.getGeometry();
                            doorNOBean.setX(objectToDouble(point.getX()));
                            doorNOBean.setY(objectToDouble(point.getY()));
                        }

                        doorBeans.add(doorNOBean);
                    }

                    if (ListUtil.isEmpty(doorBeans)) {
                        callback2.onSuccess(new ArrayList<PSHHouse>());
                    } else {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback2.onSuccess(doorBeans);
                            }
                        });
                    }

                } else {
                    callback2.onSuccess(new ArrayList<PSHHouse>());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback2.onFail(new Exception(throwable));
            }
        });
    }

    /**
     * @param point
     * @param spatialReference
     * @param resolution
     * @param selectType       //0选择所有（排水户、井，空地）；1排水户、空地；2井、空地；3空地
     * @param callback2
     */
    public void queryLayers(Point point, SpatialReference spatialReference, double resolution,
                            int selectType,
                            final Callback2<List<ProblemFeatureOrAddr>> callback2) {
        Geometry geometry = GeometryEngine.buffer(point, spatialReference, 60 * resolution, null);
        if (query == null) {
            callback2.onFail(new Exception("查询参数为空"));
            return;
        }
        query.setGeometry(geometry);

        ArrayList<Observable<ArrayList<ProblemFeatureOrAddr>>> observables = new ArrayList<>();
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String pshUrl = sharedPreferencesUtil.getString(DRAINAGE_USER, "") + "/0";
//        String pshUrl2 = sharedPreferencesUtil.getString(DRAINAGE_USER, "") + "/1";
        String pshUrl2 = sharedPreferencesUtil.getString(DRAINAGE_USER2, "") + "/0";
        String jhjUrl = sharedPreferencesUtil.getString(DRAINAGE_JIEHUJING, "") + "/0";
        String jbjUrl = sharedPreferencesUtil.getString(DRAINAGE_JIEBOJING, "") + "/0";
        String jbjUrl2 = sharedPreferencesUtil.getString(DRAINAGE_JIEBOJING, "") + "/2";
        String jbjUrl4 = sharedPreferencesUtil.getString(DRAINAGE_JIEBOJING, "") + "/4";
        if (selectType == 1) {
            observables.add(getQueryObservable(pshUrl, "psh"));
            observables.add(getQueryObservable(pshUrl2, "psh"));
        } else if (selectType == 2) {
            observables.add(getQueryObservable(jhjUrl, "jhj"));
            observables.add(getQueryObservable(jbjUrl, "jbj"));
            observables.add(getQueryObservable(jbjUrl2, "jbj"));
            observables.add(getQueryObservable(jbjUrl4, "jbj"));
        } else {
            observables.add(getQueryObservable(pshUrl, "psh"));
            observables.add(getQueryObservable(pshUrl2, "psh"));
            observables.add(getQueryObservable(jhjUrl, "jhj"));
            observables.add(getQueryObservable(jbjUrl, "jbj"));
            observables.add(getQueryObservable(jbjUrl2, "jbj"));
            observables.add(getQueryObservable(jbjUrl4, "jbj"));
        }

        Observable.zip(observables, new FuncN<ArrayList<ProblemFeatureOrAddr>>() {
            @Override
            public ArrayList<ProblemFeatureOrAddr> call(Object... args) {
                if (args.length == 0) {
                    return new ArrayList<>();
                }
                ArrayList<ProblemFeatureOrAddr> data = new ArrayList<>();
                for (Object o : args) {
                    if (o != null) {
                        data.addAll((ArrayList<ProblemFeatureOrAddr>) o);
                    }
                }
                return data;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ProblemFeatureOrAddr>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback2.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(ArrayList<ProblemFeatureOrAddr> pshHouses) {
                        callback2.onSuccess(pshHouses);
                    }
                });
    }

    private Observable<ArrayList<ProblemFeatureOrAddr>> getQueryObservable(final String url, final String sslx) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<ProblemFeatureOrAddr>>() {
            @Override
            public void call(final Subscriber<? super ArrayList<ProblemFeatureOrAddr>> subscriber) {
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        final ArrayList<ProblemFeatureOrAddr> doorBeans = new ArrayList<ProblemFeatureOrAddr>();
                        Point point = null;
                        //判断是新增还是纠错
                        if (featureSet.getGraphics().length != 0) {
                            Graphic[] graphics = featureSet.getGraphics();
                            ProblemFeatureOrAddr doorNOBean = null;
                            for (Graphic graphic : graphics) {
                                doorNOBean = new ProblemFeatureOrAddr();
                                doorNOBean.setAddr(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ADDR)));
                                if ("psh".equals(sslx)) {
                                    doorNOBean.setId(objectToInt(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ID)));
                                } else {
                                    doorNOBean.setId(objectToInt(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.MARK_ID)));
                                }
                                doorNOBean.setObjectId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.OBJECTID)));
                                doorNOBean.setMph(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.MPH)));
                                doorNOBean.setUnitId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.UNIT_ID)));
                                doorNOBean.setName(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.NAME)));
                                doorNOBean.setState(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.STATE)));
                                doorNOBean.setDischargerType3(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DISCHARGER_TYPE3)));
                                doorNOBean.setReportType(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.REPORT_TYPE)));
                                doorNOBean.setAttrOne(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ATTR_ONE)));
                                doorNOBean.setAttrTwo(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ATTR_TWO)));
                                if (TextUtils.isEmpty(doorNOBean.getAttrOne())) {
                                    doorNOBean.setAttrOne(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.SUBTYPE)));
                                }
                                if (TextUtils.isEmpty(doorNOBean.getAttrTwo())) {
                                    doorNOBean.setAttrTwo(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.SORT)));
                                }
                                doorNOBean.setLayerUrl(url);
                                doorNOBean.setSslx(sslx);
                                if (TextUtils.isEmpty(doorNOBean.getName())) {
                                    doorNOBean.setName(doorNOBean.getAttrOne() + "-" + doorNOBean.getAttrTwo());
                                }
                                if (graphic.getGeometry() != null) {
                                    point = (Point) graphic.getGeometry();
                                    doorNOBean.setX(objectToDouble(point.getX()));
                                    doorNOBean.setY(objectToDouble(point.getY()));
                                }

                                doorBeans.add(doorNOBean);
                            }
                        }
                        subscriber.onNext(doorBeans);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onNext(new ArrayList<ProblemFeatureOrAddr>());
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    /**
     * @param point
     * @param spatialReference
     * @param resolution
     * @param callback2
     */
    public void queryPshDataInfo2(Point point, SpatialReference spatialReference, double resolution,
                                 final Callback2<List<PSHHouse>> callback2) {
        Geometry geometry = GeometryEngine.buffer(point, spatialReference, 60 * resolution, null);
        if (query == null) {
            callback2.onFail(new Exception("查询参数为空"));
            return;
        }
        query.setGeometry(geometry);

        ArrayList<Observable<ArrayList<PSHHouse>>> observables = new ArrayList<>();
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String pshUrl = sharedPreferencesUtil.getString(DRAINAGE_DIALY, "") + "/0";
        String pshUrl3 = sharedPreferencesUtil.getString(DRAINAGE_DIALY, "") + "/1";
//        String pshUrl2 = sharedPreferencesUtil.getString(DRAINAGE_DIALY2, "") + "/1";
        String pshUrl2 = sharedPreferencesUtil.getString(DRAINAGE_DIALY2, "") + "/0";
        String pshUrl4 = sharedPreferencesUtil.getString(DRAINAGE_DIALY2, "") + "/1";
        observables.add(getPshQueryObservable(pshUrl));
        observables.add(getPshQueryObservable(pshUrl3));
        observables.add(getPshQueryObservable(pshUrl2));
        observables.add(getPshQueryObservable(pshUrl4));

        Observable.zip(observables, new FuncN<ArrayList<PSHHouse>>() {
            @Override
            public ArrayList<PSHHouse> call(Object... args) {
                if (args.length == 0) {
                    return new ArrayList<>();
                }
                ArrayList<PSHHouse> data = new ArrayList<>();
                int i = 0;
                for (Object o : args) {
                    if (o != null) {
                        if(i != 0){
                            ArrayList<PSHHouse> list = (ArrayList<PSHHouse>) o;
                            for(PSHHouse pshHouse:list){
                                if(data.contains(pshHouse)){
                                    continue;
                                }
                                data.add(pshHouse);
                            }
                        }else {
                            data.addAll((ArrayList<PSHHouse>) o);
                        }
                        i++;
                    }
                }
                return data;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<PSHHouse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback2.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(ArrayList<PSHHouse> pshHouses) {
                        callback2.onSuccess(pshHouses);
                    }
                });
    }

    private Observable<ArrayList<PSHHouse>> getPshQueryObservable(final String url) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<PSHHouse>>() {
            @Override
            public void call(final Subscriber<? super ArrayList<PSHHouse>> subscriber) {
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        final ArrayList<PSHHouse> doorBeans = new ArrayList<PSHHouse>();
                        final ArrayList<String> keys = new ArrayList<String>();
                        Point point = null;
                        //判断是新增还是纠错
                        if (featureSet.getGraphics().length != 0) {
                            Graphic[] graphics = featureSet.getGraphics();
                            PSHHouse doorNOBean = null;
                            for (Graphic graphic : graphics) {
                                doorNOBean = new PSHHouse();
                                doorNOBean.setAddr(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ADDR)));
                                doorNOBean.setId(objectToInt(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ID)));
                                doorNOBean.setObjectId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.OBJECTID)));
                                doorNOBean.setMph(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.MPH)));
                                doorNOBean.setUnitId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.UNIT_ID)));
                                doorNOBean.setName(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.NAME)));
                                doorNOBean.setState(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.STATE)));
                                doorNOBean.setDischargerType3(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DISCHARGER_TYPE3)));
                                doorNOBean.setReportType(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.REPORT_TYPE)));
                                doorNOBean.setPsdyName(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.PSDY_NAME)));
                                doorNOBean.setPsdyId(objectToString(graphic.getAttributes().get("PSDY_ID")));
                                String PSH_USID = objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.PSH_USID));
                                doorNOBean.setPshId(PSH_USID);
                                if (graphic.getGeometry() != null) {
                                    point = (Point) graphic.getGeometry();
                                    doorNOBean.setX(objectToDouble(point.getX()));
                                    doorNOBean.setY(objectToDouble(point.getY()));
                                }

                                doorBeans.add(doorNOBean);
                            }
                        }
                        subscriber.onNext(doorBeans);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onNext(new ArrayList<PSHHouse>());
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    /**
     * 点击地图时进行查询排水户信息
     *
     * @param point
     * @param spatialReference
     * @param resolution
     * @param callback2
     */
    public void queryDrainageUserInfo(Point point, SpatialReference spatialReference, double resolution,
                                      final Callback2<List<DrainageUserBean>> callback2) {

        Geometry geometry = GeometryEngine.buffer(point, spatialReference, 60 * resolution, null);
        if (query == null) {
            callback2.onFail(new Exception("查询参数为空"));
            return;
        }
        query.setGeometry(geometry);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String url = sharedPreferencesUtil.getString(DRAINAGE_USER, "") + "/0";

        if ("/0".equals(url)) {
            url = getDrainageUserLayerUrl() + "/0";
        }
        if ("/0".equals(url)) {
            callback2.onFail(new Exception("无法查询到排水户图层的url"));
            return;
        }

        final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                url, ArcGISFeatureLayer.MODE.SNAPSHOT);
        layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
            @Override
            public void onCallback(FeatureSet featureSet) {
                final ArrayList<DrainageUserBean> drainageUserBeans = new ArrayList<>();
                //判断是新增还是纠错
                if (featureSet.getGraphics().length != 0) {
                    Graphic[] graphics = featureSet.getGraphics();
                    DrainageUserBean drainageUserBean = null;
                    for (Graphic graphic : graphics) {
                        Map<String, Object> attributes = graphic.getAttributes();
                        String id = objectToString(attributes.get("ID"));
                        String addr = objectToString(attributes.get("ADDR"));
                        String town = objectToString(attributes.get("TOWN"));
                        String name = objectToString(attributes.get("NAME"));
                        String type = objectToString(attributes.get("DISCHARGER_TYPE3"));
                        String mph = objectToString(attributes.get("MPH"));
                        String usid = objectToString(attributes.get("UNIT_ID"));
                        String state = objectToString(attributes.get("STATE"));
                        String psdyName = objectToString(attributes.get("PSDY_NAME"));
                        String psdyId = objectToString(attributes.get("PSDY_ID"));
                        drainageUserBean = new DrainageUserBean(id);
                        drainageUserBean.setName(name);
                        drainageUserBean.setAddress(addr);
                        drainageUserBean.setTown(town);
                        drainageUserBean.setType(type);
                        drainageUserBean.setMph(mph);
                        drainageUserBean.setUnitId(usid);
                        drainageUserBean.setState(state);
                        drainageUserBean.setPsdyName(psdyName);
                        drainageUserBean.setPsdyId(psdyId);
                        if (graphic.getGeometry() != null) {
                            Point point = (Point) graphic.getGeometry();
                            drainageUserBean.setX(objectToDouble(point.getX()));
                            drainageUserBean.setY(objectToDouble(point.getY()));
                        }
                        drainageUserBeans.add(drainageUserBean);
                    }

                    if (ListUtil.isEmpty(drainageUserBeans)) {
                        callback2.onSuccess(Collections.<DrainageUserBean>emptyList());
                    } else {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback2.onSuccess(drainageUserBeans);
                            }
                        });
                    }
                } else {
                    callback2.onSuccess(Collections.<DrainageUserBean>emptyList());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback2.onFail(new Exception(throwable));
            }
        });
    }

    /**
     * @param point
     * @param spatialReference
     * @param resolution
     * @param callback2
     */
    public void queryDrainageUserInfo2(Point point, SpatialReference spatialReference, double resolution,
                                  final Callback2<List<DrainageUserBean>> callback2) {
        Geometry geometry = GeometryEngine.buffer(point, spatialReference, 60 * resolution, null);
        if (query == null) {
            callback2.onFail(new Exception("查询参数为空"));
            return;
        }
        query.setGeometry(geometry);

        ArrayList<Observable<ArrayList<DrainageUserBean>>> observables = new ArrayList<>();
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String pshUrl = sharedPreferencesUtil.getString(DRAINAGE_USER, "") + "/0";
        String pshUrl2 = sharedPreferencesUtil.getString(DRAINAGE_USER2, "") + "/0";
        observables.add(getDrainageUserObservable(pshUrl));
        observables.add(getDrainageUserObservable(pshUrl2));

        Observable.zip(observables, new FuncN<ArrayList<DrainageUserBean>>() {
            @Override
            public ArrayList<DrainageUserBean> call(Object... args) {
                if (args.length == 0) {
                    return new ArrayList<>();
                }
                ArrayList<DrainageUserBean> data = new ArrayList<>();
                int i = 0;
                for (Object o : args) {
                    if (o != null) {
                        if(i != 0){
                            ArrayList<DrainageUserBean> list = (ArrayList<DrainageUserBean>) o;
                            for(DrainageUserBean pshHouse:list){
                                if(data.contains(pshHouse)){
                                    continue;
                                }
                                data.add(pshHouse);
                            }
                        }else {
                            data.addAll((ArrayList<DrainageUserBean>) o);
                        }
                        i++;
                    }
                }
                return data;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<DrainageUserBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback2.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(ArrayList<DrainageUserBean> pshHouses) {
                        callback2.onSuccess(pshHouses);
                    }
                });
    }

    private Observable<ArrayList<DrainageUserBean>> getDrainageUserObservable(final String url) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<DrainageUserBean>>() {
            @Override
            public void call(final Subscriber<? super ArrayList<DrainageUserBean>> subscriber) {
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        final ArrayList<DrainageUserBean> drainageUserBeans = new ArrayList<>();
                        //判断是新增还是纠错
                        if (featureSet.getGraphics().length != 0) {
                            Graphic[] graphics = featureSet.getGraphics();
                            DrainageUserBean drainageUserBean = null;
                            for (Graphic graphic : graphics) {
                                Map<String, Object> attributes = graphic.getAttributes();
                                String id = objectToString(attributes.get("ID"));
                                String addr = objectToString(attributes.get("ADDR"));
                                String town = objectToString(attributes.get("TOWN"));
                                String name = objectToString(attributes.get("NAME"));
                                String type = objectToString(attributes.get("DISCHARGER_TYPE3"));
                                String mph = objectToString(attributes.get("MPH"));
                                String usid = objectToString(attributes.get("UNIT_ID"));
                                String state = objectToString(attributes.get("STATE"));
                                String psdyName = objectToString(attributes.get("PSDY_NAME"));
                                String psdyId = objectToString(attributes.get("PSDY_ID"));
                                drainageUserBean = new DrainageUserBean(id);
                                drainageUserBean.setName(name);
                                drainageUserBean.setAddress(addr);
                                drainageUserBean.setTown(town);
                                drainageUserBean.setType(type);
                                drainageUserBean.setMph(mph);
                                drainageUserBean.setUnitId(usid);
                                drainageUserBean.setState(state);
                                drainageUserBean.setPsdyName(psdyName);
                                drainageUserBean.setPsdyId(psdyId);
                                if (graphic.getGeometry() != null) {
                                    Point point = (Point) graphic.getGeometry();
                                    drainageUserBean.setX(objectToDouble(point.getX()));
                                    drainageUserBean.setY(objectToDouble(point.getY()));
                                }
                                drainageUserBeans.add(drainageUserBean);
                            }
                        }
                        subscriber.onNext(drainageUserBeans);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onNext(new ArrayList<DrainageUserBean>());
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    /**
     * 点击地图时进行查询排水单元信息
     *
     * @param point
     * @param spatialReference
     * @param resolution
     * @param callback2
     */
    public void queryDrainageUnitInfo(Point point, SpatialReference spatialReference, double resolution,
                                      final Callback2<List<DrainageUnit>> callback2) {

        final Geometry geometry = GeometryEngine.buffer(point, spatialReference, 200 * resolution, null);
        if (query == null) {
            callback2.onFail(new Exception("查询参数为空"));
            return;
        }
        query.setGeometry(geometry);
//        query.setReturnGeometry(false);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String url = sharedPreferencesUtil.getString(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER, "") + "/0";

        if ("/0".equals(url)) {
            url = getDrainageUnitLayerUrl() + "/0";
        }
        if ("/0".equals(url)) {
            callback2.onFail(new Exception("无法查询到排水单元图层的url"));
            return;
        }

        final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                url, ArcGISFeatureLayer.MODE.SNAPSHOT);
        layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
            @Override
            public void onCallback(FeatureSet featureSet) {
                final ArrayList<DrainageUnit> drainageUserBeans = new ArrayList<>();
                //判断是新增还是纠错
                if (featureSet.getGraphics().length != 0) {
                    Graphic[] graphics = featureSet.getGraphics();
                    DrainageUnit drainageUnitBean = null;
                    for (Graphic graphic : graphics) {
                        Map<String, Object> attributes = graphic.getAttributes();
                        String objectId = objectToString(attributes.get("OBJECTID"));
                        String name = objectToString(attributes.get("单元名称"));
                        String area = objectToString(attributes.get("所属区"));
                        String addr = objectToString(attributes.get("地址DZ"));
                        drainageUnitBean = new DrainageUnit();
                        drainageUnitBean.setName(name);
                        drainageUnitBean.setObjectId(objectId);
                        drainageUnitBean.setArea(area);
                        drainageUnitBean.setAddr(addr);
                        final Geometry geometry = graphic.getGeometry();
                        drainageUnitBean.setGeometry(geometry);
                        Point geometryCenter = GeometryUtil.getGeometryCenter(geometry);
                        drainageUnitBean.setX(geometryCenter.getX());
                        drainageUnitBean.setY(geometryCenter.getY());
                        drainageUserBeans.add(drainageUnitBean);
                    }

                    if (ListUtil.isEmpty(drainageUserBeans)) {
                        callback2.onSuccess(Collections.<DrainageUnit>emptyList());
                    } else {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback2.onSuccess(drainageUserBeans);
                            }
                        });
                    }
                } else {
                    callback2.onSuccess(Collections.<DrainageUnit>emptyList());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback2.onFail(new Exception(throwable));
            }
        });
    }

    /**
     * 点击地图时进行查询排水单元信息
     *
     * @param callback2
     */
    public void queryDrainageUnitInfo(final Callback2<List<DrainageUnit>> callback2) {

        query.setReturnGeometry(true);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String url = sharedPreferencesUtil.getString(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER, "") + "/0";

        if ("/0".equals(url)) {
            url = getDrainageUnitLayerUrl() + "/0";
        }
        if ("/0".equals(url)) {
            callback2.onFail(new Exception("无法查询到排水单元图层的url"));
            return;
        }

        final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                url, ArcGISFeatureLayer.MODE.SNAPSHOT);
        layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
            @Override
            public void onCallback(FeatureSet featureSet) {
                final ArrayList<DrainageUnit> drainageUserBeans = new ArrayList<>();
                //判断是新增还是纠错
                if (featureSet.getGraphics().length != 0) {
                    Graphic[] graphics = featureSet.getGraphics();
                    DrainageUnit drainageUnitBean = null;
                    for (Graphic graphic : graphics) {
                        Map<String, Object> attributes = graphic.getAttributes();
                        String objectId = objectToString(attributes.get("OBJECTID"));
                        String name = objectToString(attributes.get("单元名称"));
                        String area = objectToString(attributes.get("所属区"));
                        String addr = objectToString(attributes.get("地址DZ"));
                        drainageUnitBean = new DrainageUnit();
                        drainageUnitBean.setName(name);
                        drainageUnitBean.setObjectId(objectId);
                        drainageUnitBean.setArea(area);
                        drainageUnitBean.setAddr(addr);
                        final Geometry geometry = graphic.getGeometry();
                        drainageUnitBean.setGeometry(geometry);
                        Point geometryCenter = GeometryUtil.getGeometryCenter(geometry);
                        drainageUnitBean.setX(geometryCenter.getX());
                        drainageUnitBean.setY(geometryCenter.getY());
                        drainageUserBeans.add(drainageUnitBean);
                    }

                    if (ListUtil.isEmpty(drainageUserBeans)) {
                        callback2.onSuccess(Collections.<DrainageUnit>emptyList());
                    } else {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback2.onSuccess(drainageUserBeans);
                            }
                        });
                    }
                } else {
                    callback2.onSuccess(Collections.<DrainageUnit>emptyList());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback2.onFail(new Exception(throwable));
            }
        });
    }


    /**
     * 点击地图查询典型排水户的图层信息
     *
     * @param point
     * @param spatialReference
     * @param resolution
     * @param callback2
     */
    public void queryTypicalDoorDataInfo(Point point, SpatialReference spatialReference, double resolution,
                                         final Callback2<List<DoorNOBean>> callback2) {

        Geometry geometry = GeometryEngine.buffer(point, spatialReference, 60 * resolution, null);
        if (query == null) {
            callback2.onFail(new Exception("查询参数为空"));
            return;
        }
        query.setGeometry(geometry);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String url = sharedPreferencesUtil.getString(PatrolLayerPresenter.TYPICAL_DOOR_NO_LAYER, "") + "/0";

        if ("/0".equals(url)) {
            url = getTypicalDoorNOLayerUrl() + "/0";
        }
        if ("/0".equals(url)) {
            callback2.onFail(new Exception("无法查询到我的上报图层的url"));
            return;
        }

        final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                url, ArcGISFeatureLayer.MODE.SNAPSHOT);
        layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
            @Override
            public void onCallback(FeatureSet featureSet) {
                final ArrayList<DoorNOBean> doorBeans = new ArrayList<DoorNOBean>();
                Point point = null;
                //判断是新增还是纠错
                if (featureSet.getGraphics().length != 0) {
                    Graphic[] graphics = featureSet.getGraphics();
                    DoorNOBean doorNOBean = null;
                    for (Graphic graphic : graphics) {
                        doorNOBean = new DoorNOBean();
                        doorNOBean.setAddress(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DZQC)));
                        doorNOBean.setS_guid(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.S_GUID)));
                        doorNOBean.setStree(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.MPWZHM)));
                        doorNOBean.setDzdm(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DZDM)));
                        doorNOBean.setPSDY_OID(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.PSDY_OID)));
                        doorNOBean.setISTATUE(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ISTATUE)));
                        doorNOBean.setPSDY_NAME(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.PSDY_NAME)));
                        doorNOBean.setIsExist(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ISEXIST)));
                        if (graphic.getGeometry() != null) {
                            point = (Point) graphic.getGeometry();
                            doorNOBean.setX(objectToDouble(point.getX()));
                            doorNOBean.setY(objectToDouble(point.getY()));
                        }

                        doorBeans.add(doorNOBean);
                    }

                    if (ListUtil.isEmpty(doorBeans)) {
                        callback2.onSuccess(new ArrayList<DoorNOBean>());
                    } else {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback2.onSuccess(doorBeans);
                            }
                        });
                    }

                } else {
                    callback2.onSuccess(new ArrayList<DoorNOBean>());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback2.onFail(new Exception(throwable));
            }
        });
    }

    public void setQueryByWhere(String where) {
        query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setSpatialRelationship(SpatialRelationship.INTERSECTS);
        if (where != null) {
            query.setWhere(where);
        }
    }

//    private void queryAttachmentInfo(final List<DoorNOBean> doorNOBeans, final Callback2<List<DoorNOBean>> callback2) {
//        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
//        initAMNetwork(supportUrl);
//        Observable.from(doorNOBeans)
//                .flatMap(new Func1<DoorNOBean, Observable<DoorNOBean>>() {
//                    //获取上报信息
//                    @Override
//                    public Observable<DoorNOBean> call(final DoorNOBean doorNOBean) {
//                        //如果markId为空，无法请求附件
//                        if (TextUtils.isEmpty(doorNOBean.getMarkId())) {
//                            return Observable.fromCallable(new Func0<DoorNOBean>() {
//                                @Override
//                                public UploadInfo call() {
//                                    return doorNOBean;
//                                }
//                            });
//                        } else {
//                            return getAttachment(doorNOBean);
//                        }
//
//                    }
//                })
//                .filter(new Func1<DoorNOBean, Boolean>() {
//                    @Override
//                    public Boolean call(DoorNOBean doorNOBean) {
//                        return doorNOBean.getModifiedFacilities() != null || doorNOBean.getUploadedFacilities() != null;
//                    }
//                })
//                .toList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<DoorNOBean>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callback2.onSuccess(doorNOBeans);
//                    }
//
//                    @Override
//                    public void onNext(List<DoorNOBean> doorNOBeans) {
//                        callback2.onSuccess(doorNOBeans);
//                    }
//                });
//    }


    /**
     * 获取门牌号图层的Url
     *
     * @return
     */
    @Nullable
    private String getDoorNOLayerUrl() {
        String url = "";
        try {
            List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
            if (null != layerInfosFromLocal) {
                for (LayerInfo layerInfo : layerInfosFromLocal) {
                    if (layerInfo.getLayerName().contains(PatrolLayerPresenter.DOOR_NO_LAYER)) {
                        url = layerInfo.getUrl();
                    }
                }
            }
        } catch (Exception e) {
            return url;
        }
        return url;
    }


    /**
     * 获取门牌号图层的Url
     *
     * @return
     */
    @Nullable
    private String getHangUpWellLayerUrl() {
        String url = "";
        try {
            List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
            if (null != layerInfosFromLocal) {
                for (LayerInfo layerInfo : layerInfosFromLocal) {
                    if (layerInfo.getLayerName().contains(DRAINAGE_JIEHUJING)) {
                        url = layerInfo.getUrl();
                    }
                }
            }
        } catch (Exception e) {
            return url;
        }
        return url;
    }

    /**
     * 获取门牌号图层的Url
     *
     * @return
     */
    @Nullable
    private String getPshLayerUrl() {
        String url = "";
        try {
            List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
            if (null != layerInfosFromLocal) {
                for (LayerInfo layerInfo : layerInfosFromLocal) {
                    if (layerInfo.getLayerName().contains(DRAINAGE_USER)) {
                        url = layerInfo.getUrl();
                    }
                }
            }
        } catch (Exception e) {
            return url;
        }
        return url;
    }


    /**
     * 获取门牌号图层的Url
     *
     * @return
     */
    @Nullable
    private String getDrainageUserLayerUrl() {
        String url = "";
        try {
            List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
            if (null != layerInfosFromLocal) {
                for (LayerInfo layerInfo : layerInfosFromLocal) {
                    if (layerInfo.getLayerName().contains(DRAINAGE_USER)) {
                        url = layerInfo.getUrl();
                    }
                }
            }
        } catch (Exception e) {
            return url;
        }
        return url;
    }


    /**
     * 获取门牌号图层的Url
     *
     * @return
     */
    @Nullable
    private String getDrainageUnitLayerUrl() {
        String url = "";
        try {
            List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
            if (null != layerInfosFromLocal) {
                for (LayerInfo layerInfo : layerInfosFromLocal) {
                    if (layerInfo.getLayerName().contains(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER)) {
                        url = layerInfo.getUrl();
                    }
                }
            }
        } catch (Exception e) {
            return url;
        }
        return url;
    }

    /**
     * 获取典型排水户图层的Url
     *
     * @return
     */
    @Nullable
    private String getTypicalDoorNOLayerUrl() {
        String url = "";
        try {
            List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
            if (null != layerInfosFromLocal) {
                for (LayerInfo layerInfo : layerInfosFromLocal) {
                    if (layerInfo.getLayerName().contains(PatrolLayerPresenter.TYPICAL_DOOR_NO_LAYER)) {
                        url = layerInfo.getUrl();
                    }
                }
            }
        } catch (Exception e) {
            return url;
        }
        return url;
    }


    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(UploadLayerApi.class);
            this.uploadLayerApi = (UploadLayerApi) this.amNetwork.getServiceApi(UploadLayerApi.class);
        }
    }

    public Observable<UploadBean> upLoadWrongDoor(String sGuid) {
//        String url = BaseInfoManager.getSupportUrl(mContext);
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        return uploadLayerApi.upLoadWrongDoor(sGuid, loginName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void initCorrectFacilityService() {
        if (correctFacilityService == null) {
            correctFacilityService = new CorrectFacilityService(mContext);
        }
    }


    private void initUploadFacilityService() {
        if (uploadFacilityService == null) {
            uploadFacilityService = new UploadFacilityService(mContext);
        }
    }

    private String objectToString(Object object) {
        if (object == null) {
            return "";
        }
        return StringUtil.getNotNullString(object.toString(), "");
    }

    private double objectToDouble(Object object) {
        if (object == null) {
            return 0;
        }
        return Double.valueOf(object.toString());
    }

    private Integer objectToInt(Object object) {
        if (object == null) {
            return 0;
        }
        return Integer.valueOf(object.toString());
    }

    private long objectToLong(Object object) {
        if (object == null) {
            return -1L;
        }
        return Long.valueOf(object.toString());
    }

    /**
     * 点击地图时进行查询排水户信息
     *
     * @param keyword
     * @return
     */
    public Observable<ArrayList<DrainageUserBean>> queryPshOneForKeyword(final String url, final String keyword) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<DrainageUserBean>>() {
            @Override
            public void call(final Subscriber<? super ArrayList<DrainageUserBean>> subscriber) {
                final Query query = getQueryPshByKey(keyword);

                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        final ArrayList<DrainageUserBean> drainageUserBeans = new ArrayList<>();
                        final ArrayList<String> keys = new ArrayList<>();
                        //判断是新增还是纠错
                        if (featureSet.getGraphics().length != 0) {
                            Graphic[] graphics = featureSet.getGraphics();
                            DrainageUserBean drainageUserBean = null;
                            for (Graphic graphic : graphics) {
                                String PSH_USID = objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.PSH_USID));
                                if(keys.contains(PSH_USID)){
                                    continue;
                                }
                                keys.add(PSH_USID);
                                Map<String, Object> attributes = graphic.getAttributes();
                                String id = objectToString(attributes.get("ID"));
                                String addr = objectToString(attributes.get("ADDR"));
                                String town = objectToString(attributes.get("TOWN"));
                                String name = objectToString(attributes.get("NAME"));
                                String type = objectToString(attributes.get("DISCHARGER_TYPE3"));
                                String state = objectToString(attributes.get("STATE"));
                                String psdyName = objectToString(attributes.get("PSDY_NAME"));
                                String psdyId = objectToString(attributes.get("PSDY_ID"));
                                drainageUserBean = new DrainageUserBean(id);
                                drainageUserBean.setName(name);
                                drainageUserBean.setAddress(addr);
                                drainageUserBean.setTown(town);
                                drainageUserBean.setType(type);
                                drainageUserBean.setState(state);
                                drainageUserBean.setPsdyId(psdyId);
                                drainageUserBean.setPsdyName(psdyName);
                                if (graphic.getGeometry() != null) {
                                    Point point = (Point) graphic.getGeometry();
                                    drainageUserBean.setX(objectToDouble(point.getX()));
                                    drainageUserBean.setY(objectToDouble(point.getY()));
                                }
                                drainageUserBeans.add(drainageUserBean);
                            }
                        }
                        subscriber.onNext(drainageUserBeans);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onNext(new ArrayList<DrainageUserBean>());
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    /**
     * @param keyword
     * @param callback2
     */
    public void queryPshForKeyword2(String keyword,
                                  final Callback2<List<DrainageUserBean>> callback2) {
        ArrayList<Observable<ArrayList<DrainageUserBean>>> observables = new ArrayList<>();
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String pshUrl = sharedPreferencesUtil.getString(DRAINAGE_USER, "") + "/0";
        String pshUrl2 = sharedPreferencesUtil.getString(DRAINAGE_USER2, "") + "/0";
//        String pshUrl2 = sharedPreferencesUtil.getString(DRAINAGE_DIALY, "") + "/1";
        observables.add(queryPshOneForKeyword(pshUrl,keyword));
        observables.add(queryPshOneForKeyword(pshUrl2,keyword));

        Observable.zip(observables, new FuncN<ArrayList<DrainageUserBean>>() {
            @Override
            public ArrayList<DrainageUserBean> call(Object... args) {
                if (args.length == 0) {
                    return new ArrayList<>();
                }
                ArrayList<DrainageUserBean> data = new ArrayList<>();
                for (Object o : args) {
                    if (o != null) {
                        data.addAll((ArrayList<DrainageUserBean>) o);
                    }
                }
                return data;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<DrainageUserBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback2.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(ArrayList<DrainageUserBean> pshHouses) {
                        callback2.onSuccess(pshHouses);
                    }
                });
    }

    /**
     * 点击地图时进行查询排水户信息
     *
     * @param keyword
     * @param callback2
     */
    public void queryPshForKeyword(String keyword, final Callback2<List<DrainageUserBean>> callback2) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String url = sharedPreferencesUtil.getString(DRAINAGE_USER, "") + "/0";

        if ("/0".equals(url)) {
            url = getDrainageUserLayerUrl() + "/0";
        }
        if ("/0".equals(url)) {
            callback2.onFail(new Exception("无法查询到排水户图层的url"));
            return;
        }
        final Query query = getQueryPshByKey(keyword);

        final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                url, ArcGISFeatureLayer.MODE.SNAPSHOT);
        layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
            @Override
            public void onCallback(FeatureSet featureSet) {
                final ArrayList<DrainageUserBean> drainageUserBeans = new ArrayList<>();
                //判断是新增还是纠错
                if (featureSet.getGraphics().length != 0) {
                    Graphic[] graphics = featureSet.getGraphics();
                    DrainageUserBean drainageUserBean = null;
                    for (Graphic graphic : graphics) {
                        Map<String, Object> attributes = graphic.getAttributes();
                        String id = objectToString(attributes.get("ID"));
                        String addr = objectToString(attributes.get("ADDR"));
                        String town = objectToString(attributes.get("TOWN"));
                        String name = objectToString(attributes.get("NAME"));
                        String type = objectToString(attributes.get("DISCHARGER_TYPE3"));
                        String state = objectToString(attributes.get("STATE"));
                        String psdyName = objectToString(attributes.get("PSDY_NAME"));
                        String psdyId = objectToString(attributes.get("PSDY_ID"));
                        drainageUserBean = new DrainageUserBean(id);
                        drainageUserBean.setName(name);
                        drainageUserBean.setAddress(addr);
                        drainageUserBean.setTown(town);
                        drainageUserBean.setType(type);
                        drainageUserBean.setState(state);
                        drainageUserBean.setPsdyId(psdyId);
                        drainageUserBean.setPsdyName(psdyName);
                        if (graphic.getGeometry() != null) {
                            Point point = (Point) graphic.getGeometry();
                            drainageUserBean.setX(objectToDouble(point.getX()));
                            drainageUserBean.setY(objectToDouble(point.getY()));
                        }
                        drainageUserBeans.add(drainageUserBean);
                    }

                    if (ListUtil.isEmpty(drainageUserBeans)) {
                        callback2.onSuccess(Collections.<DrainageUserBean>emptyList());
                    } else {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback2.onSuccess(drainageUserBeans);
                            }
                        });
                    }
                } else {
                    callback2.onSuccess(Collections.<DrainageUserBean>emptyList());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback2.onFail(new Exception(throwable));
            }
        });
    }

    private Query getQueryPshByKey(String keyword) {

        StringBuilder sb = new StringBuilder();
        sb.append("(NAME like '%" + keyword + "%' or ");
        sb.append("ADDR like '%" + keyword + "%') ");
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setWhere(sb.toString());
        return query;
    }
}
