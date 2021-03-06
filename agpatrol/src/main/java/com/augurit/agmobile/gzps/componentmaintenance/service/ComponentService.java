package com.augurit.agmobile.gzps.componentmaintenance.service;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.augurit.agmobile.gzps.common.constant.LayerUrlConstant;
import com.augurit.agmobile.gzps.componentmaintenance.model.QueryFeatureSet;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentFieldKeyConstant;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentTypeConstant;
import com.augurit.agmobile.gzpssb.bean.DoorNOBean;
import com.augurit.agmobile.gzpssb.bean.DrainageUnit;
import com.augurit.agmobile.gzpssb.bean.DrainageUserBean;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemFeatureOrAddr;
import com.augurit.agmobile.gzpssb.utils.SewerageLayerFieldKeyConstant;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.service.LayerServiceFactory;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.service.LoginService;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.util.SharedPreferencesUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.log.LogUtil;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.tasks.SpatialRelationship;
import com.esri.core.tasks.ags.query.Query;
import com.esri.core.tasks.identify.IdentifyParameters;
import com.esri.core.tasks.identify.IdentifyResult;
import com.esri.core.tasks.identify.IdentifyTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.FuncN;
import rx.schedulers.Schedulers;

import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_DIALY;
import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_DIALY2;
import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_USER;
import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_USER2;

/**
 * @author ????????? ???xuciluan
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.gzps.componentmaintenance
 * @createTime ???????????? ???17/10/14
 * @modifyBy ????????? ???xuciluan
 * @modifyTime ???????????? ???17/10/14
 * @modifyMemo ???????????????
 */

public class ComponentService {


    private Context mContext;

    public ComponentService(Context context) {
        this.mContext = context;
    }

    private List<String> layerNames = Arrays.asList("??????", "?????????", "?????????");

    /**
     * ??????????????????
     *
     * @param componentType ??????????????????????????????{@link ComponentTypeConstant#NEW_ADDED}??????{@link ComponentTypeConstant#OLD_COMPONENT}
     */
    public Observable<List<QueryFeatureSet>> queryAllComponents2(final String componentType) {
        final Query query = getQueryByFlagAndUserName(componentType, new LoginService(mContext, AMDatabase.getInstance()).getUser().getUserName());
        List<Observable<QueryFeatureSet>> observableList = new ArrayList<>();
        for (String layerUrl : LayerUrlConstant.newComponentUrls) {
            Observable<QueryFeatureSet> observable = getObservable(layerUrl, query);
            observableList.add(observable);
        }
        return Observable.zip(observableList, new FuncN<List<QueryFeatureSet>>() {
            @Override
            public List<QueryFeatureSet> call(Object... objects) {
                if (objects.length == 0) {
                    return null;
                }
                List<QueryFeatureSet> queryFeatureSetList = new ArrayList<QueryFeatureSet>();
                for (Object object : objects) {
                    if (object == null) {
                        continue;
                    }
                    QueryFeatureSet queryFeatureSet = (QueryFeatureSet) object;
                    queryFeatureSetList.add(queryFeatureSet);
                }
                return queryFeatureSetList;
            }
        }).subscribeOn(Schedulers.io());
    }

//    /**
//     * ??????????????????
//     * @param componentType ??????????????????????????????{@link ComponentTypeConstant#NEW_ADDED}??????{@link ComponentTypeConstant#OLD_COMPONENT}
//     */
//    public Observable<QueryFeatureSet> queryAllComponents(final String componentType) {
//        final Query query = getQueryByFlagAndUserName(componentType, new LoginService(mContext, AMDatabase.getInstance()).getUser().getUserName());
//
//        return Observable.from(LayerUrlConstant.newComponentUrls)
//                .flatMap(new Func1<String, Observable<QueryFeatureSet>>() {
//                    @Override
//                    public Observable<QueryFeatureSet> call(String s) {
//                        return getObservable(s,query);
//                    }
//                })
//                .map(new Func1<QueryFeatureSet, QueryFeatureSet>() { //??????????????????????????????
//                    @Override
//                    public QueryFeatureSet call(QueryFeatureSet featureSet) {
//
//                        //todo ???????????????????????????????????????
//                        List<Graphic> result = new ArrayList<Graphic>();
//                        if(featureSet == null){
//                            return null;
//                        }
//                        Graphic[] graphics = featureSet.getFeatureSet().getGraphics();
//                        if (graphics!= null && graphics.length >0){
//                            for (Graphic graphic : graphics){
//                                //????????????
//                                if (ComponentTypeConstant.NEW_ADDED.equals(componentType)){
//                                    if (NEW_ADDED_COMPONENT_VALUE.equals(graphic.getAttributes().get(ComponentTypeConstant.COMPONENT_TYPE_KEY))){
//                                        result.add(graphic);
//                                    }
//                                }
//                                //????????????
//                                else {
//                                    if (OLD_COMPONENT_VALUE2.equals(graphic.getAttributes().get(ComponentTypeConstant.COMPONENT_TYPE_KEY))){
//                                        result.add(graphic);
//                                    }
//                                }
//
//                            }
//
//                            //??????
//                            Graphic[] array = result.toArray(new Graphic[0]);
//                            featureSet.getFeatureSet().setGraphics(array);
//                        }
//                        return featureSet;
//                    }
//                })
//                .subscribeOn(Schedulers.io());
//    }


    /**
     * ??????????????????
     *
     * @return
     */
    @Nullable
    private String getUploadLayerUrl() {
        String url = "";
        List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
        //????????????bugly?????????????????????,????????????
        if (ListUtil.isEmpty(layerInfosFromLocal)) {
            return "http://139.159.243.230:6080/arcgis/rest/services/PAISHUIHU/menpaihao_ms/MapServer";
        }

        for (LayerInfo layerInfo : layerInfosFromLocal) {
            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.DOOR_NO_LAYER)) {
                url = layerInfo.getUrl();
            }
        }
        return url;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @Nullable
    private String getPshLayerUrl() {
        String url = "";
        List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
        //????????????bugly?????????????????????,????????????
        if (ListUtil.isEmpty(layerInfosFromLocal)) {
            return "http://139.159.243.185:6080/arcgis/rest/services/PAISHUIHU/pshCes/MapServer";
        }

        for (LayerInfo layerInfo : layerInfosFromLocal) {
            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.DRAINAGE_USER)) {
                url = layerInfo.getUrl();
            }
        }
        return url;
    }

    /**
     * ????????????????????????????????????(??????)
     */
    public void queryDoorNoComponentsForObjectId(final String keyword, List<String> objectIds, final Callback2<List<DoorNOBean>> callback) {
        Observable<List<DoorNOBean>> menpai = queryComponentsForKeyword(keyword, objectIds, getUploadLayerUrl() + "/0", PatrolLayerPresenter.DOOR_NO_LAYER);

        menpai.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoorNOBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<DoorNOBean> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    /**
     * ????????????????????????????????????(??????)
     */
    public void queryPshComponentsForObjectId(final String keyword, List<String> objectIds, final Callback2<List<PSHHouse>> callback) {
        Observable<List<PSHHouse>> menpai = queryPshForKeyword(keyword, objectIds, getPshLayerUrl() + "/0", PatrolLayerPresenter.DRAINAGE_USER);

        menpai.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PSHHouse>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<PSHHouse> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    /**
     * ????????????????????????????????????(??????)
     */
    public void queryPshComponentsForWord(final String keyword,final Callback2<List<PSHHouse>> callback) {
        Observable<List<PSHHouse>> menpai = queryPshForKeyword(keyword, getPshLayerUrl() + "/0", PatrolLayerPresenter.DRAINAGE_USER);

        menpai.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PSHHouse>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<PSHHouse> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    /**
     * ????????????????????????????????????(??????)
     */
    public void queryPshComponentsForWord2(final String keyword,final Callback2<List<PSHHouse>> callback) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String url = sharedPreferencesUtil.getString(DRAINAGE_DIALY, "") + "/0";
//        String url = sharedPreferencesUtil.getString(DRAINAGE_USER, "") + "/0";

        if ("/0".equals(url)) {
            url = getPshLayerUrl() + "/0";
        }
        Observable<List<PSHHouse>> menpai = queryPshForKeyword(keyword, url, PatrolLayerPresenter.DRAINAGE_USER);

        menpai.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PSHHouse>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<PSHHouse> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    /**
     * ????????????????????????????????????(??????)
     */
    public void queryPshComponentsForWord3(final String keyword,final Callback2<List<PSHHouse>> callback) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String pshUrl = sharedPreferencesUtil.getString(DRAINAGE_DIALY, "") + "/0";
        String pshUrl3 = sharedPreferencesUtil.getString(DRAINAGE_DIALY, "") + "/1";
        String pshUrl2 = sharedPreferencesUtil.getString(DRAINAGE_DIALY2, "") + "/0";
        String pshUrl4 = sharedPreferencesUtil.getString(DRAINAGE_DIALY2, "") + "/1";
        Observable<List<PSHHouse>> psh1 = queryPshForKeyword2(keyword, pshUrl, PatrolLayerPresenter.DRAINAGE_USER);
        Observable<List<PSHHouse>> psh3 = queryPshForKeyword2(keyword, pshUrl3, PatrolLayerPresenter.DRAINAGE_USER);
        Observable<List<PSHHouse>> psh2 = queryPshForKeyword2(keyword, pshUrl2, PatrolLayerPresenter.DRAINAGE_USER);
        Observable<List<PSHHouse>> psh4 = queryPshForKeyword2(keyword, pshUrl4, PatrolLayerPresenter.DRAINAGE_USER);
        ArrayList<Observable<List<PSHHouse>>> observables = new ArrayList<>();
        observables.add(psh1);
        observables.add(psh3);
        observables.add(psh2);
        observables.add(psh4);

        Observable.zip(observables, new FuncN<List<PSHHouse>>() {
            @Override
            public List<PSHHouse> call(Object... args) {
                if (args.length == 0) {
                    return new ArrayList<>();
                }
                List<PSHHouse> data = new ArrayList<>();
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
                .subscribe(new Subscriber<List<PSHHouse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<PSHHouse> pshHouses) {
                        callback.onSuccess(pshHouses);
                    }
                });
    }

    /**
     * ????????????????????????????????????(??????)
     */
    public void queryComponentsForWord(final String keyword,final Callback2<List<ProblemFeatureOrAddr>> callback) {
        ArrayList<Observable<List<ProblemFeatureOrAddr>>> observables = new ArrayList<>();
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String pshUrl = sharedPreferencesUtil.getString(DRAINAGE_USER, "") + "/0";
        String pshUrl2 = sharedPreferencesUtil.getString(DRAINAGE_USER2, "") + "/0";
        Observable<List<ProblemFeatureOrAddr>> menpai = queryForKeyword(keyword, pshUrl, PatrolLayerPresenter.DRAINAGE_USER);
        Observable<List<ProblemFeatureOrAddr>> menpai2 = queryForKeyword(keyword, pshUrl2, PatrolLayerPresenter.DRAINAGE_USER);
        observables.add(menpai);
        observables.add(menpai2);
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
//        menpai.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ProblemFeatureOrAddr>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<ProblemFeatureOrAddr> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }


    /**
     * ??????????????????
     */
    public void queryDrainageUnitByKeyWord(final String keyword, List<String> objectIds, final Callback2<List<DrainageUnit>> callback) {
        Observable<List<DrainageUnit>> menpai = queryPshUnitComponentsForKeyword(keyword, objectIds, getUploadLayerUrl() + "/0", PatrolLayerPresenter.DRAINAGE_UNIT_LAYER);

        menpai.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DrainageUnit>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<DrainageUnit> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }


    /**
     * ??????layerUrl???????????????????????????????????????????????????
     * ??????????????????
     *
     * @param keyword
     * @param url
     * @return
     */
    public Observable<List<DrainageUnit>> queryPshUnitComponentsForKeyword(final String keyword, List<String> objectIds, final String url, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<DrainageUnit>>() {
            @Override
            public void call(final Subscriber<? super List<DrainageUnit>> subscriber) {
                final Query query = getQueryByObjectId(keyword, new ArrayList<String>());
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        final ArrayList<DrainageUnit> drainageUnits = new ArrayList<DrainageUnit>();
                        Point point = null;
                        //???????????????????????????
                        if (featureSet.getGraphics().length != 0) {
                            Graphic[] graphics = featureSet.getGraphics();
                            //TODO
                            DrainageUnit drainageUnit = null;
                            for (Graphic graphic : graphics) {
                                drainageUnit = new DrainageUnit();
                                drainageUnit.setObjectId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.PSDY_NAME)));
                                if (graphic.getGeometry() != null) {
                                    point = (Point) graphic.getGeometry();
                                    drainageUnit.setX(objectToDouble(point.getX()));
                                    drainageUnit.setY(objectToDouble(point.getY()));
                                }
                                drainageUnits.add(drainageUnit);
                            }
                            subscriber.onNext(drainageUnits);
                            subscriber.onCompleted();

                        } else {
                            subscriber.onNext(drainageUnits);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }

//    /**
//     * ??????layerName????????????????????????????????????????????????????????????????????????????????????
//     *
//     * @param keyword
//     * @param layerName
//     * @return
//     */
//    public Observable<List<Component>> queryDoorNoWithLayerNameAndObjectId(final String keyword, final String layerName) {
//        final String newComponentLayerUrl = LayerUrlConstant.getNewLayerUrlByLayerName(layerName);
//        final String oldComponentLayerUrl = LayerUrlConstant.getLayerUrlByLayerName(layerName);
//        return Observable.zip(queryComponentsForKeyword(keyword, newComponentLayerUrl,layerName), queryComponentsForKeyword(keyword, oldComponentLayerUrl,layerName), new Func2<List<Component>, List<Component>, List<Component>>() {
//            @Override
//            public List<Component> call(List<Component> componentList, List<Component> componentList2) {
//                componentList.addAll(componentList2);
//                return componentList;
//            }
//        });
//    }

    /**
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param keyword
     * @param objectIds
     * @param url
     * @return
     */
    public Observable<List<DoorNOBean>> queryComponentsForKeyword(final String keyword, final List<String> objectIds, final String url, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<DoorNOBean>>() {
            @Override
            public void call(final Subscriber<? super List<DoorNOBean>> subscriber) {
                final Query query = getQueryByObjectId(keyword, objectIds);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        final ArrayList<DoorNOBean> doorBeans = new ArrayList<DoorNOBean>();
                        Point point = null;
                        //???????????????????????????
                        if (featureSet.getGraphics().length != 0) {
                            Graphic[] graphics = featureSet.getGraphics();
                            DoorNOBean doorNOBean = null;
                            for (Graphic graphic : graphics) {
                                doorNOBean = new DoorNOBean();
                                doorNOBean.setAddress(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DZQC)));
                                ;
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

                            subscriber.onNext(doorBeans);
                            subscriber.onCompleted();

                        } else {
                            subscriber.onNext(doorBeans);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }

    /**
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param keyword
     * @param objectIds
     * @param url
     * @return
     */
    public Observable<List<PSHHouse>> queryPshForKeyword(final String keyword, final List<String> objectIds, final String url, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<PSHHouse>>() {
            @Override
            public void call(final Subscriber<? super List<PSHHouse>> subscriber) {
                final Query query = getQueryByObjectId(keyword, objectIds);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        final ArrayList<PSHHouse> doorBeans = new ArrayList<PSHHouse>();
                        Point point = null;
                        //???????????????????????????
                        if (featureSet.getGraphics().length != 0) {
                            Graphic[] graphics = featureSet.getGraphics();
                            PSHHouse doorNOBean = null;
                            for (Graphic graphic : graphics) {
                                doorNOBean = new PSHHouse();
                                doorNOBean.setAddr(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ADDR)));
                                doorNOBean.setId(objectToInt(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ID)));
                                doorNOBean.setMph(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.MPH)));
                                doorNOBean.setUnitId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.UNIT_ID)));
                                doorNOBean.setName(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.NAME)));
                                if (graphic.getGeometry() != null) {
                                    point = (Point) graphic.getGeometry();
                                    doorNOBean.setX(objectToDouble(point.getX()));
                                    doorNOBean.setY(objectToDouble(point.getY()));
                                }
                                doorBeans.add(doorNOBean);
                            }

                            subscriber.onNext(doorBeans);
                            subscriber.onCompleted();

                        } else {
                            subscriber.onNext(doorBeans);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }

    /**
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param keyword
     * @param url
     * @return
     */
    public Observable<List<PSHHouse>> queryPshForKeyword(final String keyword, final String url, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<PSHHouse>>() {
            @Override
            public void call(final Subscriber<? super List<PSHHouse>> subscriber) {
                final Query query = getQueryPshByKey(keyword);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        final ArrayList<PSHHouse> doorBeans = new ArrayList<PSHHouse>();
                        Point point = null;
                        //???????????????????????????
                        if (featureSet.getGraphics().length != 0) {
                            Graphic[] graphics = featureSet.getGraphics();
                            PSHHouse doorNOBean = null;
                            for (Graphic graphic : graphics) {
                                doorNOBean = new PSHHouse();
                                doorNOBean.setAddr(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ADDR)));
                                doorNOBean.setId(objectToInt(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ID)));
                                doorNOBean.setMph(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.MPH)));
                                doorNOBean.setUnitId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.UNIT_ID)));
                                doorNOBean.setName(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.NAME)));
                                doorNOBean.setState(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.STATE)));
                                doorNOBean.setDischargerType3(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DISCHARGER_TYPE3)));
                                if (graphic.getGeometry() != null) {
                                    point = (Point) graphic.getGeometry();
                                    doorNOBean.setX(objectToDouble(point.getX()));
                                    doorNOBean.setY(objectToDouble(point.getY()));
                                }
                                doorBeans.add(doorNOBean);
                            }

                            subscriber.onNext(doorBeans);
                            subscriber.onCompleted();

                        } else {
                            subscriber.onNext(doorBeans);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }

    /**
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param keyword
     * @param url
     * @return
     */
    public Observable<List<PSHHouse>> queryPshForKeyword2(final String keyword, final String url, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<PSHHouse>>() {
            @Override
            public void call(final Subscriber<? super List<PSHHouse>> subscriber) {
                final Query query = getQueryPshByKey(keyword);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        final ArrayList<PSHHouse> doorBeans = new ArrayList<PSHHouse>();
                        final ArrayList<String> keys = new ArrayList<String>();
                        Point point = null;
                        //???????????????????????????
                        if (featureSet.getGraphics().length != 0) {
                            Graphic[] graphics = featureSet.getGraphics();
                            PSHHouse doorNOBean = null;
                            for (Graphic graphic : graphics) {
                                String PSH_USID = objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.PSH_USID));
                                doorNOBean = new PSHHouse();
                                doorNOBean.setPshId(PSH_USID);
                                doorNOBean.setAddr(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ADDR)));
                                doorNOBean.setId(objectToInt(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ID)));
                                doorNOBean.setMph(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.MPH)));
                                doorNOBean.setUnitId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.UNIT_ID)));
                                doorNOBean.setName(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.NAME)));
                                doorNOBean.setState(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.STATE)));
                                doorNOBean.setDischargerType3(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DISCHARGER_TYPE3)));
                                if (graphic.getGeometry() != null) {
                                    point = (Point) graphic.getGeometry();
                                    doorNOBean.setX(objectToDouble(point.getX()));
                                    doorNOBean.setY(objectToDouble(point.getY()));
                                }
                                doorBeans.add(doorNOBean);
                            }

                            subscriber.onNext(doorBeans);
                            subscriber.onCompleted();

                        } else {
                            subscriber.onNext(doorBeans);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }

    /**
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param keyword
     * @param url
     * @return
     */
    public Observable<List<ProblemFeatureOrAddr>> queryForKeyword(final String keyword, final String url, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<ProblemFeatureOrAddr>>() {
            @Override
            public void call(final Subscriber<? super List<ProblemFeatureOrAddr>> subscriber) {
                final Query query = getQueryPshByKey(keyword);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        final ArrayList<ProblemFeatureOrAddr> doorBeans = new ArrayList<>();
                        Point point = null;
                        //???????????????????????????
                        if (featureSet.getGraphics().length != 0) {
                            Graphic[] graphics = featureSet.getGraphics();
                            ProblemFeatureOrAddr doorNOBean = null;
                            for (Graphic graphic : graphics) {
                                doorNOBean = new ProblemFeatureOrAddr();
                                doorNOBean.setAddr(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ADDR)));
                                doorNOBean.setId(objectToInt(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.ID)));
                                doorNOBean.setMph(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.MPH)));
                                doorNOBean.setUnitId(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.UNIT_ID)));
                                doorNOBean.setName(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.NAME)));
                                doorNOBean.setState(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.STATE)));
                                doorNOBean.setDischargerType3(objectToString(graphic.getAttributes().get(SewerageLayerFieldKeyConstant.DISCHARGER_TYPE3)));
                                doorNOBean.setSslx("psh");
                                if (graphic.getGeometry() != null) {
                                    point = (Point) graphic.getGeometry();
                                    doorNOBean.setX(objectToDouble(point.getX()));
                                    doorNOBean.setY(objectToDouble(point.getY()));
                                }
                                doorBeans.add(doorNOBean);
                            }

                            subscriber.onNext(doorBeans);
                            subscriber.onCompleted();

                        } else {
                            subscriber.onNext(doorBeans);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }

    private Query getQueryByObjectId(String keyword, List<String> objectIds) {

        User user = new LoginService(mContext.getApplicationContext(), AMDatabase.getInstance()).getUser();
        StringBuilder sb = new StringBuilder();
//        sb.append("(DZQC like '%" + keyword + "%' or ");
//        sb.append("MPWZHM like '%" + keyword + "%') ");
        if (!ListUtil.isEmpty(objectIds)) {
//            sb.append(" or " );
//                for (String id:objectIds){
//                    sb.append("( OBJECTID = " +id +" ) or");
//                }
//                sb.delete(sb.lastIndexOf("or"),sb.length());
            sb.append(" OBJECTID  in  (");
            for (String id : objectIds) {
                sb.append(" " + id + " , ");
            }
            sb.delete(sb.lastIndexOf(","), sb.length());
            sb.append(" ) ");
        }

        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setWhere(sb.toString());
        return query;
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

    private Query getQueryPsdyByKey(String keyword) {

        StringBuilder sb = new StringBuilder();
        sb.append("(???????????? like '%" + keyword + "%' or ");
        sb.append("??????DZ like '%" + keyword + "%') ");
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setWhere(sb.toString());
        return query;
    }

    private Query getQueryAll() {
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setWhere("1=1");
        return query;
    }

    private Query getQueryByFlag(String flag) {
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setWhere("FLAG_=" + flag);
        return query;
    }

    private Query getQueryByFlagAndUserName(String flag, String userName) {
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setWhere("FLAG_='" + flag + "' and REPAIR_COM='" + userName + "'");
        return query;
    }

    private Query getQueryByGeometry(Geometry geometry) {
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setSpatialRelationship(SpatialRelationship.INTERSECTS);
        query.setGeometry(geometry);
        return query;
    }


    /**
     * ????????????????????????????????????
     */
    public void queryPrimaryComponents(final Geometry geometry, final Callback2<List<Component>> callback) {
        Observable<List<Component>> yinjin = queryComponentsWithLayerNameAndGeometry(geometry, "??????");
        Observable<List<Component>> yushuikou = queryComponentsWithLayerNameAndGeometry(geometry, "?????????");
        Observable<List<Component>> paifangkou = queryComponentsWithLayerNameAndGeometry(geometry, "?????????");
        List<Observable<List<Component>>> observables = new ArrayList<>();
        observables.add(yinjin);
        observables.add(yushuikou);
        observables.add(paifangkou);
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }


    /**
     * identify??????????????????????????????????????????
     */
    public void identifyPrimaryComponents(final Geometry geometry, final MapView mapView, final Callback2<List<Component>> callback) {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                int yinjinLayerId = LayerUrlConstant.componentIds[LayerUrlConstant.getIndexlByLayerName("??????")];
                int yushuikouLayerId = LayerUrlConstant.componentIds[LayerUrlConstant.getIndexlByLayerName("?????????")];
                int paifangkouLayerId = LayerUrlConstant.componentIds[LayerUrlConstant.getIndexlByLayerName("?????????")];
                IdentifyParameters parameters = new IdentifyParameters();
                parameters.setGeometry(geometry);
                Envelope extent = new Envelope();
                mapView.getExtent().queryEnvelope(extent);
                parameters.setMapExtent(extent);
                parameters.setMapWidth(mapView.getWidth());
                parameters.setMapHeight(mapView.getHeight());
                parameters.setSpatialReference(mapView.getSpatialReference());
                parameters.setDPI(98);  // TODO ????????????????????????
                parameters.setLayers(new int[]{yinjinLayerId, yushuikouLayerId, paifangkouLayerId});
                parameters.setReturnGeometry(true);
                parameters.setTolerance(20);//??????20
                parameters.setLayerMode(IdentifyParameters.ALL_LAYERS);
                IdentifyTask identifyTask = new IdentifyTask(LayerUrlConstant.mapServerUrl);
                try {
                    IdentifyResult[] identifyResults = identifyTask.execute(parameters);
                    if (identifyResults != null && identifyResults.length > 0) {
                        for (IdentifyResult identifyResult : identifyResults) {
                            Component component = new Component();
                            component.setDisplayFieldName(identifyResult.getDisplayFieldName());
                            component.setLayerName(identifyResult.getLayerName());
                            String layerUrl =
                                    LayerUrlConstant.mapServerUrl
                                            + LayerUrlConstant.componentIds[
                                            LayerUrlConstant.getIndexlByLayerName(identifyResult.getLayerName())];
                            component.setLayerUrl(layerUrl);
                            Graphic graphic = new Graphic(identifyResult.getGeometry(), null, identifyResult.getAttributes());
                            component.setGraphic(graphic);
                            Object o = graphic.getAttributes().get(ComponentFieldKeyConstant.OBJECTID);
                            if (o != null && o instanceof Integer)
                                component.setObjectId((Integer) o); //????????????objectId?????????integer???
                        }
                    }
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
    }


    /**
     * ?????????????????????????????????
     */
    public void querySecondComponents(final Geometry geometry, final Callback2<List<Component>> callback) {
        Observable<List<Component>> yinjin = queryComponentsWithLayerNameAndGeometry(geometry, "??????");
        Observable<List<Component>> yushuikou = queryComponentsWithLayerNameAndGeometry(geometry, "??????");
        Observable<List<Component>> paifangkou = queryComponentsWithLayerNameAndGeometry(geometry, "?????????");
        List<Observable<List<Component>>> observables = new ArrayList<>();
        observables.add(yinjin);
        observables.add(yushuikou);
        observables.add(paifangkou);
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    if (o == null) {
                        continue;
                    }
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }


    /**
     * ????????????????????????
     */
    public void queryAllComponentsExceptLine(final Geometry geometry, final Callback2<List<Component>> callback) {
        Observable<List<Component>> yinjin = queryComponentsWithLayerNameAndGeometry(geometry, "??????");
        Observable<List<Component>> yushuikou = queryComponentsWithLayerNameAndGeometry(geometry, "?????????");
        Observable<List<Component>> paifangkou = queryComponentsWithLayerNameAndGeometry(geometry, "?????????");
        List<Observable<List<Component>>> observables = new ArrayList<>();
        observables.add(yinjin);
        observables.add(yushuikou);
        observables.add(paifangkou);

        Observable<List<Component>> paiment = queryComponentsWithLayerNameAndGeometry(geometry, "??????");
        Observable<List<Component>> fament = queryComponentsWithLayerNameAndGeometry(geometry, "??????");
        Observable<List<Component>> yiliuyan = queryComponentsWithLayerNameAndGeometry(geometry, "?????????");
        observables.add(paiment);
        observables.add(fament);
        observables.add(yiliuyan);

        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }


    /**
     * ??????????????????????????????
     *
     * @param geometry ????????????
     * @param url      ?????????URL
     * @param newUrl   ?????????URL
     * @param callback ???????????????
     */
    public void queryComponents(final Geometry geometry, final String url, final String newUrl, final Callback2<List<QueryFeatureSet>> callback) {
        final List<QueryFeatureSet> queryFeatureSetList = new ArrayList<>();
        Observable.create(new Observable.OnSubscribe<List<QueryFeatureSet>>() {
            @Override
            public void call(final Subscriber<? super List<QueryFeatureSet>> subscriber) {
                final Query query = getQueryByGeometry(geometry);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        newUrl, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        QueryFeatureSet queryFeatureSet = new QueryFeatureSet();
                        queryFeatureSet.setLayerUrl(newUrl);
                        queryFeatureSet.setFeatureSet(featureSet);
                        for (int i = 0; i < LayerUrlConstant.newComponentUrls.length; ++i) {
                            if (newUrl.equals(LayerUrlConstant.newComponentUrls[i])) {
                                queryFeatureSet.setLayerName(LayerUrlConstant.componentNames[i]);
                                break;
                            }
                        }
                        queryFeatureSetList.add(queryFeatureSet);

                        final ArcGISFeatureLayer newlayer = new ArcGISFeatureLayer(
                                url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                        newlayer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                            @Override
                            public void onCallback(FeatureSet featureSet) {

                                LogUtil.d("??????????????????????????????" + featureSet.getGraphics().length);
                                QueryFeatureSet queryFeatureSet = new QueryFeatureSet();
                                queryFeatureSet.setLayerUrl(url);
                                queryFeatureSet.setFeatureSet(featureSet);
                                for (int i = 0; i < LayerUrlConstant.componentUrls.length; ++i) {
                                    if (url.equals(LayerUrlConstant.componentUrls[i])) {
                                        queryFeatureSet.setLayerName(LayerUrlConstant.componentNames[i]);
                                        break;
                                    }
                                }
                                queryFeatureSetList.add(queryFeatureSet);
                                subscriber.onNext(queryFeatureSetList);
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                subscriber.onError(throwable);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<QueryFeatureSet>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFail(new Exception(throwable));
                    }

                    @Override
                    public void onNext(List<QueryFeatureSet> queryFeatureSetList) {
                        callback.onSuccess(queryFeatureSetList);
                    }
                });


    }

    /**
     * ??????????????????????????????
     *
     * @param geometry ????????????
     * @param url      ??????URL
     * @param callback ???????????????
     */
    public void queryComponents(final Geometry geometry, final String url, final Callback2<QueryFeatureSet> callback) {
        Observable.create(new Observable.OnSubscribe<QueryFeatureSet>() {
            @Override
            public void call(final Subscriber<? super QueryFeatureSet> subscriber) {
                final Query query = getQueryByGeometry(geometry);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {

                        LogUtil.d("??????????????????????????????" + featureSet.getGraphics().length);
                        QueryFeatureSet queryFeatureSet = new QueryFeatureSet();
                        queryFeatureSet.setLayerUrl(url);
                        queryFeatureSet.setFeatureSet(featureSet);
                        for (int i = 0; i < LayerUrlConstant.componentUrls.length; ++i) {
                            if (url.equals(LayerUrlConstant.componentUrls[i])) {
                                queryFeatureSet.setLayerName(LayerUrlConstant.componentNames[i]);
                                break;
                            }
                        }
                        subscriber.onNext(queryFeatureSet);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<QueryFeatureSet>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFail(new Exception(throwable));
                    }

                    @Override
                    public void onNext(QueryFeatureSet queryFeatureSet) {
                        callback.onSuccess(queryFeatureSet);
                    }
                });


    }


    /**
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param geometry
     * @param url
     */
    public Observable<List<Component>> queryComponents(final Geometry geometry, final String url) {
        return Observable.create(new Observable.OnSubscribe<List<Component>>() {
            @Override
            public void call(final Subscriber<? super List<Component>> subscriber) {
                final Query query = getQueryByGeometry(geometry);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        if (ListUtil.isEmpty(featureSet.getGraphics())) {
                            subscriber.onNext(new ArrayList<Component>());
                            subscriber.onCompleted();
                            return;
                        }
                        List<Component> componentList = new ArrayList<>();
                        for (Graphic graphic : featureSet.getGraphics()) {
                            Component component = new Component();
                            component.setLayerUrl(url);
                            component.setLayerName(layer.getName());
                            component.setDisplayFieldName(featureSet.getDisplayFieldName());
//                            component.setFieldAlias(featureSet.getFieldAliases());
//                            component.setFields(featureSet.getFields());
                            component.setGraphic(graphic);
//                            component.setFieldAlias(featureSet.getFieldAliases());
                            Object o = graphic.getAttributes().get(featureSet.getObjectIdFieldName());
                            if (o != null && o instanceof Integer) {
                                component.setObjectId((Integer) o); //????????????objectId?????????integer???
                            }
                            componentList.add(component);
                        }
                        subscriber.onNext(componentList);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }


    /**
     * ??????layerName????????????????????????????????????????????????????????????????????????????????????
     *
     * @param geometry
     * @param layerName
     */
    public Observable<List<Component>> queryComponentsWithLayerNameAndGeometry(final Geometry geometry, final String layerName) {
        final String newComponentLayerUrl = LayerUrlConstant.getNewLayerUrlByLayerName(layerName);
        final String oldComponentLayerUrl = LayerUrlConstant.getLayerUrlByLayerName(layerName);
        return Observable.zip(queryComponents(geometry, newComponentLayerUrl), queryComponents(geometry, oldComponentLayerUrl), new Func2<List<Component>, List<Component>, List<Component>>() {
            @Override
            public List<Component> call(List<Component> componentList, List<Component> componentList2) {
                componentList.addAll(componentList2);
                return componentList;
            }
        });
    }


    /**
     * ??????usid???layerUrl?????????????????????????????????
     *
     * @param usid
     * @param url
     * @param callback
     */
    public void queryComponents(final String usid, final String url, final Callback2<Component> callback) {
        Observable.create(new Observable.OnSubscribe<Component>() {
            @Override
            public void call(final Subscriber<? super Component> subscriber) {
                final Query query = getQueryByUSId(usid);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        if (ListUtil.isEmpty(featureSet.getGraphics())) {
                            subscriber.onError(new Exception("????????????"));
                            return;
                        }
                        Component component = new Component();
                        component.setLayerUrl(url);
                        component.setLayerName(layer.getName());
                        component.setDisplayFieldName(featureSet.getDisplayFieldName());
//                        component.setFieldAlias(featureSet.getFieldAliases());
//                        component.setFields(featureSet.getFields());
                        component.setGraphic(featureSet.getGraphics()[0]);
//                        component.setFieldAlias(featureSet.getFieldAliases());
                        Object o = featureSet.getGraphics()[0].getAttributes().get(featureSet.getObjectIdFieldName());
                        if (o != null && o instanceof Integer) {
                            component.setObjectId((Integer) o); //????????????objectId?????????integer???
                        }
                        subscriber.onNext(component);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Component>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFail(new Exception(throwable));
                    }

                    @Override
                    public void onNext(Component queryFeatureSetList) {
                        callback.onSuccess(queryFeatureSetList);
                    }
                });
    }


    /**
     * ??????usid???layerName??????????????????????????????????????????????????????????????????
     *
     * @param usid
     * @param layerName
     * @param callback
     */
    public void queryComponentsWithLayerName(final String usid, final String layerName, final Callback2<Component> callback) {
        Observable.create(new Observable.OnSubscribe<Component>() {
            @Override
            public void call(final Subscriber<? super Component> subscriber) {
                final Query query = getQueryByUSId(usid);
                final String newComponentLayerUrl = LayerUrlConstant.getNewLayerUrlByLayerName(layerName);
                final String oldComponentLayerUrl = LayerUrlConstant.getLayerUrlByLayerName(layerName);
                queryComponents(usid, newComponentLayerUrl, new Callback2<Component>() {
                    @Override
                    public void onSuccess(Component component) {
                        callback.onSuccess(component);
                    }

                    @Override
                    public void onFail(Exception error) {
                        queryComponents(usid, oldComponentLayerUrl, callback);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Component>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFail(new Exception(throwable));
                    }

                    @Override
                    public void onNext(Component queryFeatureSetList) {
                        callback.onSuccess(queryFeatureSetList);
                    }
                });
    }


    private Query getQueryByUSId(String usid) {
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setSpatialRelationship(SpatialRelationship.INTERSECTS);
        query.setWhere("USID = '" + usid + "'");
        return query;
    }

    private Observable<QueryFeatureSet> getObservable(final String url, final Query query) {
        return Observable.create(new Observable.OnSubscribe<QueryFeatureSet>() {
            @Override
            public void call(final Subscriber<? super QueryFeatureSet> subscriber) {

                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);

//                if (!layer.isInitialized()) {
//                    LogUtil.e("????????????????????????");
//                    subscriber.onError(new Exception("???????????????????????????"));
//                    return;
//                }
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {

                        LogUtil.d("??????????????????????????????" + featureSet.getGraphics().length);

                        if (!TextUtils.isEmpty(LayerUrlConstant.getLayerNameByUnknownLayerUrl(url))) {
                            QueryFeatureSet queryFeatureSet = new QueryFeatureSet();
                            queryFeatureSet.setLayerUrl(url);
                            queryFeatureSet.setFeatureSet(featureSet);
                            queryFeatureSet.setLayerName(LayerUrlConstant.getLayerNameByUnknownLayerUrl(url));
                            subscriber.onNext(queryFeatureSet);
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(null);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogUtil.e("???????????????????????????");
                        subscriber.onError(throwable);
                    }
                });
            }
        }).onErrorReturn(new Func1<Throwable, QueryFeatureSet>() {
            @Override
            public QueryFeatureSet call(Throwable throwable) {
                return null;
            }
        })
                .subscribeOn(Schedulers.io());
    }


    /**
     * @param attr
     * @return
     */
    public String getErrorInfo(Map<String, Object> attr) {
        Set<Map.Entry<String, Object>> entries = attr.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if ("errorinfo".equalsIgnoreCase(entry.getKey())) {
                return String.valueOf(entry.getValue());
            }
        }
        return null;
    }

    /**
     * ??????????????????????????????(2017.12.14 ???????????????)
     *
     * @param url
     * @param components
     * @return
     */
    public Observable<List<Component>> queryUpStreamAndDownStreams(final String url, final List<Component> components) {
//        return Observable.from(components)
//                .flatMap(new Func1<Component, Observable<Component>>() {
//                    @Override
//                    public Observable<Component> call(Component component) {
//                        return queryUpStreamAndDownStream(url, component);
//                    }
//                })
//                .toList()
//                .subscribeOn(Schedulers.io());
        return Observable.fromCallable(new Callable<List<Component>>() {
            @Override
            public List<Component> call() throws Exception {
                return components;
            }
        });
    }

    private Query getQueryByUSID(String upStreamUsid, String downStreamUsid) {
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setSpatialRelationship(SpatialRelationship.INTERSECTS);
        StringBuilder stringBuilder = new StringBuilder();

        if (!TextUtils.isEmpty(upStreamUsid)) {
            String[] split = upStreamUsid.split(";");
            for (int i = 0; i < split.length; i++) {
                if (i == split.length - 1) {
                    stringBuilder.append("USID='" + split[i].split(",")[0] + "' or " + DOWN_STREAM + " like '%" + split[i].split(",")[0] + "%'");
                } else {
                    stringBuilder.append("USID='" + split[i].split(",")[0] + "' or " + DOWN_STREAM + "like '%" + split[i].split(",")[0] + "%' or ");
                }
            }
        }

        if (!TextUtils.isEmpty(upStreamUsid) && !TextUtils.isEmpty(downStreamUsid)) {
            stringBuilder.append(" or ");
        }

        if (!TextUtils.isEmpty(downStreamUsid)) {
            String[] split = downStreamUsid.split(";");
            for (int i = 0; i < split.length; i++) {
                if (i == split.length - 1) {
                    stringBuilder.append("USID='" + split[i].split(",")[0] + "' or " + UP_STREAM + " like '%" + split[i].split(",")[0] + "%'");
                } else {
                    stringBuilder.append("USID='" + split[i].split(",")[0] + "' or " + UP_STREAM + " like '%" + split[i].split(",")[0] + "%' or ");
                }
            }
        }

        //"USID='" + upStreamUsid + "' or USID='" + downStreamUsid + "'"
        query.setWhere(stringBuilder.toString());
        return query;
    }

    private static final String UP_STREAM = "UpStream";
    private static final String DOWN_STREAM = "DownStream";


    /**
     * ??????????????????????????????
     *
     * @param url
     * @param component
     * @return
     */
    public Observable<Component> queryUpStreamAndDownStream(final String url, final Component component) {

        return Observable.create(new Observable.OnSubscribe<Component>() {
            @Override
            public void call(final Subscriber<? super Component> subscriber) {

                final Query query = getQueryCondition(component);

                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);

                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {

                        LogUtil.d("??????????????????????????????" + featureSet.getGraphics().length);
                        QueryFeatureSet queryFeatureSet = new QueryFeatureSet();
                        queryFeatureSet.setLayerUrl(url);
                        queryFeatureSet.setFeatureSet(featureSet);
                        for (int i = 0; i < LayerUrlConstant.componentUrls.length; ++i) {
                            if (url.equals(LayerUrlConstant.componentUrls[i])) {
                                queryFeatureSet.setLayerName(LayerUrlConstant.componentNames[i]);
                                break;
                            }
                        }
                        //???FeatureSet??????component
                        List<Component> componentFromFeatureSet = getComponentFromFeatureSet(queryFeatureSet);
                        if (!ListUtil.isEmpty(componentFromFeatureSet)) {
                            //????????????????????????????????????
                            completeUpStreamAndDownStreamInfo(component, componentFromFeatureSet);
                        }

                        subscriber.onNext(component);
                        subscriber.onCompleted();
                    }

                    /**
                     * ???????????????????????????????????????????????????
                     * @param component ???????????????
                     * @param componentFromFeatureSet ?????????????????????
                     */
                    private void completeUpStreamAndDownStreamInfo(Component component, List<Component> componentFromFeatureSet) {

                        List<Component> notDirectStreams = new ArrayList<>();
                        List<Component> upStreams = new ArrayList<>();
                        List<Component> downStreams = new ArrayList<>();
                        String upStream = getUpStream(component);
                        String downStream = getDownStream(component);
                        String[] upStreamList = upStream.split(";");
                        String[] downStreamList = downStream.split(";");

                        for (Component stream : componentFromFeatureSet) {

                            Object usid = stream.getGraphic().getAttributes().get(ComponentFieldKeyConstant.USID);
                            //???????????????????????????
                            if (usid != null && upStream.contains(usid.toString())) {
                                for (String up : upStreamList) {
                                    if (up.contains(usid.toString())) {
                                        String emissionType = up.split(",")[1];
                                        component.setEmissionType(emissionType);
                                        break;
                                    }
                                }
                                upStreams.add(stream);
                            } else if (usid != null && downStream.contains(usid.toString())) {
                                for (String down : downStreamList) {
                                    if (down.contains(usid.toString())) {
                                        String emissionType = down.split(",")[1];
                                        component.setEmissionType(emissionType);
                                        break;
                                    }
                                }
                                downStreams.add(stream);
                            } else {
                                notDirectStreams.add(stream);
                            }
                        }
                        component.setUpStream(upStreams);
                        component.setDownStream(downStreams);

                        completeNotDirectStream(component.getUpStream(), notDirectStreams);
                        completeNotDirectStream(component.getDownStream(), notDirectStreams);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        }).subscribeOn(Schedulers.io());
    }

    private void completeNotDirectStream(List<Component> componentUpStream, List<Component> notDirectStreams) {

        //????????????
        //List<Component> componentUpStream = component.getUpStream();
        if (ListUtil.isEmpty(componentUpStream)) {
            return;
        }

        for (Component com : componentUpStream) {

            String up1 = getUpStream(com);
            String down1 = getDownStream(com);

            String[] upStreamList = up1.split(";");
            String[] downStreamList = down1.split(";");

            List<Component> upStreams2 = new ArrayList<>();
            List<Component> downStreams2 = new ArrayList<>();

            for (Component notDirect : notDirectStreams) {
                Object usid = notDirect.getGraphic().getAttributes().get(ComponentFieldKeyConstant.USID);
                //???????????????????????????
                if (usid != null && up1.contains(usid.toString())) {
                    for (String up : upStreamList) {
                        if (up.contains(usid.toString())) {
                            String emissionType = up1.split(",")[1];
                            notDirect.setEmissionType(emissionType);
                            break;
                        }
                    }
                    upStreams2.add(notDirect);
                } else if (usid != null && down1.contains(usid.toString())) {
                    for (String down : downStreamList) {
                        if (down.contains(usid.toString())) {
                            String emissionType = down.split(",")[1];
                            notDirect.setEmissionType(emissionType);
                            break;
                        }
                    }
                    downStreams2.add(notDirect);
                }
            }
            com.setUpStream(upStreams2);
            com.setDownStream(downStreams2);
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param components
     * @return
     */
    private Query getQueryCondition(Component components) {
        String upStreamStr = getUpStream(components);
        String downStreamStr = getDownStream(components);

        return getQueryByUSID(upStreamStr, downStreamStr);
    }

    /**
     * ????????????
     *
     * @param components
     * @return
     */
    private String getDownStream(Component components) {
        String downStreamStr = "";
        Object downStream = components.getGraphic().getAttributes().get(DOWN_STREAM);
        if (downStream != null) {
            downStreamStr = downStream.toString();
        }
        return downStreamStr;
    }

    /**
     * ????????????
     *
     * @param components
     * @return
     */
    private String getUpStream(Component components) {
        Object upStream = components.getGraphic().getAttributes().get(UP_STREAM);

        String upStreamStr = "";


        if (upStream != null) {
            upStreamStr = upStream.toString();
        }
        return upStreamStr;
    }

    /**
     * ???QueryFeatureSet??????List<Component>
     *
     * @param queryFeatureSet
     * @return
     */
    public List<Component> getComponentFromFeatureSet(QueryFeatureSet queryFeatureSet) {
        if (queryFeatureSet == null) {
            return null;
        }
        FeatureSet featureSet = queryFeatureSet.getFeatureSet();
        Graphic[] graphics = featureSet.getGraphics();
        if (graphics == null
                || graphics.length <= 0) {
            return null;
        }

        List<Component> components = new ArrayList<>();
        for (Graphic graphic : graphics) {
            Component component = new Component();
            component.setLayerUrl(queryFeatureSet.getLayerUrl());
            component.setLayerName(queryFeatureSet.getLayerName());
            component.setDisplayFieldName(featureSet.getDisplayFieldName());
//            component.setFieldAlias(featureSet.getFieldAliases());
//            component.setFields(featureSet.getFields());
            component.setGraphic(graphic);
            Object o = graphic.getAttributes().get(featureSet.getObjectIdFieldName());
            if (o != null && o instanceof Integer) {
                component.setObjectId((Integer) o); //????????????objectId?????????integer???
            }
            components.add(component);
        }

        return components;
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

    /**
     * ?????????????????????,??????????????????
     */
    public void queryComponentsForJBJ(final Geometry geometry, final Callback2<List<Component>> callback, final List<LayerInfo> visibleLayerInfos) {
//        Observable<List<Component>> yushuikou = queryComponentsPSGD(geometry, "????????????");
//        Observable<List<Component>> psgd = queryComponentsPSGD(geometry, "????????????");
//        Observable<List<Component>> psgjz = queryComponentsPSGD(geometry, "????????????(????????????)", "??????(????????????)", 0);
//        Observable<List<Component>> psgdz = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 0);
//        Observable<List<Component>> psgdz1 = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 1);
//        Observable<List<Component>> cunyimian = queryComponentsPSGD(geometry, PatrolLayerPresenter.RIVER_DOUBT_LAYER_NAME, PatrolLayerPresenter.RIVER_DOUBT_LAYER_NAME, 0);
//        List<Observable<List<Component>>> observables = new ArrayList<>();
//        observables.add(yushuikou);
//        observables.add(psgjz);
//        observables.add(psgd);
//        observables.add(psgdz);
//        observables.add(psgdz1);
//        observables.add(cunyimian);

        List<Observable<List<Component>>> observables = new ArrayList<>();
        for (LayerInfo visibleLayerInfo : visibleLayerInfos) {
            if ("?????????.".equals(visibleLayerInfo.getLayerName())) {
                Observable<List<Component>> jbj1 = queryComponentsPSGD(geometry, "?????????.", "?????????.", 0);
                Observable<List<Component>> jbj2 = queryComponentsPSGD(geometry, "?????????.", "?????????.", 2);
                Observable<List<Component>> jbj3 = queryComponentsPSGD(geometry, "?????????.", "?????????.", 4);
                observables.add(jbj1);
                observables.add(jbj2);
                observables.add(jbj3);
            }
        }
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    public Observable<List<Component>> queryComponentsPSGD(final Geometry geometry, final String layerName, final String name, int layerId) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String newComponentLayerUrl = sharedPreferencesUtil.getString(layerName, "");
        if (StringUtil.isEmpty(newComponentLayerUrl)) {
            newComponentLayerUrl = getLayerUrlForName(layerName);
        }
        if (StringUtil.isEmpty(newComponentLayerUrl)) {
            return null;
        }
        newComponentLayerUrl += "/" + layerId;
        return queryComponents(geometry, newComponentLayerUrl, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * ??????????????????????????????URL
     *
     * @param name
     * @return
     */
    private String getLayerUrlForName(String name) {
        List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
        if (ListUtil.isEmpty(layerInfosFromLocal)) {
            return null;
        }
        for (LayerInfo layerInfo : layerInfosFromLocal) {
            if (layerInfo.getLayerName().equals(name)) {
                return layerInfo.getUrl();
            }
        }
        return null;
    }

    /**
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param geometry
     * @param url
     */
    public Observable<List<Component>> queryComponents(final Geometry geometry, final String url, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<Component>>() {
            @Override
            public void call(final Subscriber<? super List<Component>> subscriber) {
                Geometry newGeometry = null;
//                if(url.indexOf("/hppsgs/hppsgsWell")!=-1 || url.indexOf("hppsgs/hppsgsPipeCanal")!=-1){
//                    newGeometry = GeometryEngine.project(geometry,SpatialReference.create(CHENGQU_WIKI),SpatialReference.create(HUANGPU_WIKI));
//            }
                final Query query = getQueryByGeometry(newGeometry == null ? geometry : newGeometry);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        if (ListUtil.isEmpty(featureSet.getGraphics())) {
                            subscriber.onNext(new ArrayList<Component>());
                            subscriber.onCompleted();
                            return;
                        }
                        List<Component> componentList = new ArrayList<>();
                        for (Graphic graphic : featureSet.getGraphics()) {
                            Component component = new Component();
                            component.setLayerUrl(url);
                            if (!StringUtil.isEmpty(layer.getName()) && layerNames.contains(layer.getName())) {
                                component.setLayerName(layer.getName());
                            } else {
                                component.setLayerName(String.valueOf(graphic.getAttributes().get("LAYER_NAME")));
                            }
                            if (StringUtil.isEmpty(component.getLayerName()) || "null".equals(component.getLayerName())) {
                                component.setLayerName(layerName);
                            }
                            component.setDisplayFieldName(featureSet.getDisplayFieldName());
//                            component.setFieldAlias(featureSet.getFieldAliases());
//                            component.setFields(featureSet.getFields());
                            component.setGraphic(graphic);
//                            component.setFieldAlias(featureSet.getFieldAliases());
                            Object o = graphic.getAttributes().get(featureSet.getObjectIdFieldName());
                            if (o != null && o instanceof Integer) {
                                component.setObjectId((Integer) o); //????????????objectId?????????integer???
                            }
                            componentList.add(component);
                        }
                        subscriber.onNext(componentList);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }

    /**
     * ??????????????????
     */
    public void queryComponentsForPSDY(final Geometry geometry, final Callback2<List<Component>> callback) {
//        Observable<List<Component>> yinjin = queryComponentsForUpload(geometry);
        Observable<List<Component>> yushuikou0 = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, 0);
//        Observable<List<Component>> yushuikou1 = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, 1);
//        Observable<List<Component>> yushuikou2 = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, 2);
//        Observable<List<Component>> yushuikou3 = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, 3);
//        Observable<List<Component>> yushuikou4 = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, 4);
//        Observable<List<Component>> yushuikou5 = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, 5);
        List<Observable<List<Component>>> observables = new ArrayList<>();
//        observables.add(yinjin);
        observables.add(yushuikou0);
//        observables.add(yushuikou1);
//        observables.add(yushuikou2);
//        observables.add(yushuikou3);
//        observables.add(yushuikou4);
//        observables.add(yushuikou5);
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    /**
     * ??????????????????
     */
    public void queryComponentsForWell(final Geometry geometry, final Callback2<List<Component>> callback) {
//        Observable<List<Component>> yinjin = queryComponentsForUpload(geometry);
        Observable<List<Component>> yushuikou = queryComponentsPSGD(geometry, "????????????");
        Observable<List<Component>> psgjz = queryComponentsPSGD(geometry, "????????????(????????????)", "??????(????????????)", 0);
        Observable<List<Component>> psgjz1 = queryComponentsPSGD(geometry, "????????????(????????????)", "??????(????????????)", 0);
        Observable<List<Component>> psgjz2 = queryComponentsPSGD(geometry, "????????????(????????????)", "?????????(????????????)", 1);
        Observable<List<Component>> psgjz3 = queryComponentsPSGD(geometry, "????????????(????????????)", "?????????(????????????)", 2);
        List<Observable<List<Component>>> observables = new ArrayList<>();
//        observables.add(yinjin);
        observables.add(yushuikou);
        observables.add(psgjz);
        observables.add(psgjz1);
        observables.add(psgjz2);
        observables.add(psgjz3);
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    public Observable<List<Component>> queryComponentsPSGD(final Geometry geometry, final String layerName) {
        String newComponentLayerUrl = getLayerUrlForName(layerName);
        if (StringUtil.isEmpty(newComponentLayerUrl)) {
            return null;
        }
        newComponentLayerUrl += "/0";
        return queryComponents(geometry, newComponentLayerUrl, layerName);
    }

    /**
     * ???????????????????????????????????????
     */
    public void queryComponentsForWell(final Geometry geometry, final Callback2<List<Component>> callback, final List<LayerInfo> visibleLayerInfos) {
////        Observable<List<Component>> yinjin = queryComponentsForUpload(geometry);
//        Observable<List<Component>> yushuikou = queryComponentsPSGD(geometry, "????????????");
//        Observable<List<Component>> psgjz = queryComponentsPSGD(geometry, "????????????(????????????)", "??????(????????????)", 0);
//        List<Observable<List<Component>>> observables = new ArrayList<>();
////        observables.add(yinjin);
//        observables.add(yushuikou);
//        observables.add(psgjz);
        List<Observable<List<Component>>> observables = new ArrayList<>();
        for (LayerInfo visibleLayerInfo : visibleLayerInfos) {
            if ("????????????".equals(visibleLayerInfo.getLayerName())) {
                Observable<List<Component>> yushuikou = queryComponentsPSGD(geometry, "????????????");
                observables.add(yushuikou);
            } else if ("????????????(????????????)".equals(visibleLayerInfo.getLayerName())) {
                Observable<List<Component>> psgjz = queryComponentsPSGD(geometry, "????????????(????????????)", "??????(????????????)", 0);
                Observable<List<Component>> psgjz1 = queryComponentsHP(geometry, "????????????(????????????)", "??????(????????????)", 0);
                Observable<List<Component>> psgjz2 = queryComponentsHP(geometry, "????????????(????????????)", "?????????(????????????)", 1);
                Observable<List<Component>> psgjz3 = queryComponentsHP(geometry, "????????????(????????????)", "?????????(????????????)", 2);
                observables.add(psgjz);
                observables.add(psgjz1);
                observables.add(psgjz2);
                observables.add(psgjz3);
            }
        }
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    public Observable<List<Component>> queryComponentsHP(final Geometry geometry, final String layerName, final String name, int layerId) {
        String newComponentLayerUrl = getLayerUrlForName(layerName);
        if (StringUtil.isEmpty(newComponentLayerUrl)) {
            return null;
        }
        newComponentLayerUrl += "/" + layerId;
        return queryHPComponents(geometry, newComponentLayerUrl, name);
    }

    /**
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param geometry
     * @param url
     */
    public Observable<List<Component>> queryHPComponents(final Geometry geometry, final String url, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<Component>>() {
            @Override
            public void call(final Subscriber<? super List<Component>> subscriber) {
                Geometry newGeometry = null;
//                if(url.indexOf("/hppsgs/hppsgsWell")!=-1 || url.indexOf("hppsgs/hppsgsPipeCanal")!=-1){
//                    newGeometry = GeometryEngine.project(geometry,SpatialReference.create(CHENGQU_WIKI),SpatialReference.create(HUANGPU_WIKI));
//            }
                final Query query = getQueryByGeometry(newGeometry == null ? geometry : newGeometry);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        if (ListUtil.isEmpty(featureSet.getGraphics())) {
                            subscriber.onNext(new ArrayList<Component>());
                            subscriber.onCompleted();
                            return;
                        }
                        List<Component> componentList = new ArrayList<>();
                        for (Graphic graphic : featureSet.getGraphics()) {
                            Component component = new Component();
                            component.setLayerUrl(url);
                            if (!StringUtil.isEmpty(layerName) && layerNames.contains(layer.getName())) {
                                component.setLayerName(layerName);
                            } else if (!StringUtil.isEmpty(layer.getName())) {
                                component.setLayerName(layer.getName());
                            } else {
                                component.setLayerName(String.valueOf(graphic.getAttributes().get("LAYER_NAME")));
                            }

                            component.setDisplayFieldName(featureSet.getDisplayFieldName());
//                            component.setFieldAlias(featureSet.getFieldAliases());
//                            component.setFields(featureSet.getFields());
                            component.setGraphic(graphic);
//                            component.setFieldAlias(featureSet.getFieldAliases());
                            Object o = graphic.getAttributes().get(featureSet.getObjectIdFieldName());
                            if (o != null && o instanceof Integer) {
                                component.setObjectId((Integer) o); //????????????objectId?????????integer???
                            }
                            componentList.add(component);
                        }
                        subscriber.onNext(componentList);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }

    /**
     * ?????????????????????
     */
    public void queryComponentsForAll(final Geometry geometry, final Callback2<List<Component>> callback) {
        Observable<List<Component>> yushuikou = queryComponentsPSGD(geometry, "????????????");
        Observable<List<Component>> psgd = queryComponentsPSGD(geometry, "????????????");
        Observable<List<Component>> psgjz = queryComponentsPSGD(geometry, "????????????(????????????)", "??????(????????????)", 0);
        Observable<List<Component>> psgdz = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 0);
        Observable<List<Component>> psgdz1 = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 1);
        Observable<List<Component>> psgdhp1 = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 0);
        Observable<List<Component>> psgdhp2 = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 1);
        Observable<List<Component>> psgjhp = queryComponentsPSGD(geometry, "????????????(????????????)", "??????(????????????)", 0);
//        Observable<List<Component>> cunyimian = queryComponentsPSGD(geometry, PatrolLayerPresenter.RIVER_DOUBT_LAYER_NAME, PatrolLayerPresenter.RIVER_DOUBT_LAYER_NAME, 0);
        List<Observable<List<Component>>> observables = new ArrayList<>();
        observables.add(yushuikou);
        observables.add(psgjz);
        observables.add(psgjhp);

        observables.add(psgd);
        observables.add(psgdz);
        observables.add(psgdz1);
        observables.add(psgdhp1);
        observables.add(psgdhp2);
//        if(cunyimian != null) {
//            observables.add(cunyimian);
//        }
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    /**
     * ?????????????????????,??????????????????
     */
    public void queryComponentsForAll(final Geometry geometry, final Callback2<List<Component>> callback, final List<LayerInfo> visibleLayerInfos, boolean canSearchJb) {
//        Observable<List<Component>> yushuikou = queryComponentsPSGD(geometry, "????????????");
//        Observable<List<Component>> psgd = queryComponentsPSGD(geometry, "????????????");
//        Observable<List<Component>> psgjz = queryComponentsPSGD(geometry, "????????????(????????????)", "??????(????????????)", 0);
//        Observable<List<Component>> psgdz = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 0);
//        Observable<List<Component>> psgdz1 = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 1);
//        Observable<List<Component>> cunyimian = queryComponentsPSGD(geometry, PatrolLayerPresenter.RIVER_DOUBT_LAYER_NAME, PatrolLayerPresenter.RIVER_DOUBT_LAYER_NAME, 0);
//        List<Observable<List<Component>>> observables = new ArrayList<>();
//        observables.add(yushuikou);
//        observables.add(psgjz);
//        observables.add(psgd);
//        observables.add(psgdz);
//        observables.add(psgdz1);
//        observables.add(cunyimian);

        List<Observable<List<Component>>> observables = new ArrayList<>();
        Observable<List<Component>> jbj1 = null;
        Observable<List<Component>> jbj2 = null;
        Observable<List<Component>> jbj3 = null;
        Observable<List<Component>> jbj4 = null;
        Observable<List<Component>> yushuikou = null;
        Observable<List<Component>> psgjz = null;
        Observable<List<Component>> psgjz1 = null;
        Observable<List<Component>> psgjz2 = null;
        Observable<List<Component>> psgjz3 = null;
        Observable<List<Component>> yushuikou2 = null;
        Observable<List<Component>> psgdz = null;
        Observable<List<Component>> psgdz1 = null;
        Observable<List<Component>> psgdz2 = null;
        Observable<List<Component>> psgdz3 = null;
        Observable<List<Component>> gjjd = null;
        Observable<List<Component>> psdy = null;
        Observable<List<Component>> psh = null;
        Observable<List<Component>> psh2 = null;

        for (LayerInfo visibleLayerInfo : visibleLayerInfos) {
            if ("?????????.".equals(visibleLayerInfo.getLayerName())) {
                jbj1 = queryComponentsPSGD(geometry, "?????????.", "?????????.", 0);
                jbj2 = queryComponentsPSGD(geometry, "?????????.", "?????????.", 2);
                jbj3 = queryComponentsPSGD(geometry, "?????????.", "?????????.", 4);
            } else if("?????????".equals(visibleLayerInfo.getLayerName())){
                jbj4 = queryComponentsPSGD(geometry, "?????????", "?????????.", 0);
            } else if ("????????????".equals(visibleLayerInfo.getLayerName())) {
                yushuikou = queryComponentsPSGD(geometry, "????????????");
            } else if ("????????????(????????????)".equals(visibleLayerInfo.getLayerName())) {
                psgjz = queryComponentsPSGD(geometry, "????????????(????????????)", "??????(????????????)", 0);
                psgjz1 = queryComponentsHP(geometry, "????????????(????????????)", "??????(????????????)", 0);
                psgjz2 = queryComponentsHP(geometry, "????????????(????????????)", "?????????(????????????)", 1);
                psgjz3 = queryComponentsHP(geometry, "????????????(????????????)", "?????????(????????????)", 2);
            } else if ("????????????".equals(visibleLayerInfo.getLayerName())) {
                yushuikou2 = queryComponentsPSGD(geometry, "????????????");
            } else if ("????????????(????????????)".equals(visibleLayerInfo.getLayerName())) {
                psgdz = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 0);
                psgdz1 = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 1);
                psgdz2 = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 0);
                psgdz3 = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 1);
            } else if ("????????????".equals(visibleLayerInfo.getLayerName())) {
                gjjd = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_GJJD);
            } else if("??????????????????".equals(visibleLayerInfo.getLayerName())){
                psdy = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, 0);
            } else if("?????????".equals(visibleLayerInfo.getLayerName())){
                psh = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_USER, PatrolLayerPresenter.DRAINAGE_USER, 0);
//                psh2 = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_USER, PatrolLayerPresenter.DRAINAGE_USER, 1);
                psh2 = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_USER2, PatrolLayerPresenter.DRAINAGE_USER, 0);
            }
        }
        /**
         * ???????????????????????????????????????????????????????????????????????????
         */
        if (yushuikou == null && gjjd != null) {
            observables.add(gjjd);
        }
        if (jbj1 != null && canSearchJb) {
            observables.add(jbj1);
        }
        if (jbj2 != null && canSearchJb) {
            observables.add(jbj2);
        }
        if (jbj3 != null && canSearchJb) {
            observables.add(jbj3);
        }
        if (jbj4 != null && canSearchJb) {
            observables.add(jbj4);
        }
//        if(psdy != null){
//            observables.add(psdy);
//        }
        if (yushuikou != null) {
            observables.add(yushuikou);
        }
        if (psgjz != null) {
            observables.add(psgjz);
        }
        if (psgjz1 != null) {
            observables.add(psgjz1);
        }
        if (psgjz2 != null) {
            observables.add(psgjz2);
        }
        if (psgjz3 != null) {
            observables.add(psgjz3);
        }
        if (yushuikou2 != null) {
            observables.add(yushuikou2);
        }
        if (psh != null) {
            observables.add(psh);
        }
        if (psh2 != null) {
            observables.add(psh2);
        }
        if (psgdz != null) {
            observables.add(psgdz);
        }
        if (psgdz1 != null) {
            observables.add(psgdz1);
        }
        if (psgdz2 != null) {
            observables.add(psgdz2);
        }
        if (psgdz3 != null) {
            observables.add(psgdz3);
        }
        if (psdy != null) {
            observables.add(psdy);
        }
        if (observables.isEmpty()) {
            callback.onSuccess(Collections.<Component>emptyList());
            return;
        }
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    /**
     * ?????????????????????,??????????????????
     */
    public void queryComponentsForPshAndJhjAndJbj(final Geometry geometry, final Callback2<List<Component>> callback, final List<LayerInfo> visibleLayerInfos, boolean canSearchJb) {
        List<Observable<List<Component>>> observables = new ArrayList<>();
        Observable<List<Component>> jbj1 = null;
        Observable<List<Component>> jbj2 = null;
        Observable<List<Component>> jbj3 = null;
        Observable<List<Component>> jbj4 = null;
        Observable<List<Component>> yushuikou = null;
        Observable<List<Component>> psgjz = null;
        Observable<List<Component>> psgjz1 = null;
        Observable<List<Component>> psgjz2 = null;
        Observable<List<Component>> psgjz3 = null;
        Observable<List<Component>> yushuikou2 = null;
        Observable<List<Component>> psgdz = null;
        Observable<List<Component>> psgdz1 = null;
        Observable<List<Component>> psgdz2 = null;
        Observable<List<Component>> psgdz3 = null;
        Observable<List<Component>> gjjd = null;
        Observable<List<Component>> psdy = null;
        Observable<List<Component>> psh = null;
        Observable<List<Component>> psh2 = null;

        for (LayerInfo visibleLayerInfo : visibleLayerInfos) {
            if ("?????????.".equals(visibleLayerInfo.getLayerName())) {
                jbj1 = queryComponentsPSGD(geometry, "?????????.", "?????????.", 0);
                jbj2 = queryComponentsPSGD(geometry, "?????????.", "?????????.", 2);
                jbj3 = queryComponentsPSGD(geometry, "?????????.", "?????????.", 4);
            } else if("?????????".equals(visibleLayerInfo.getLayerName())){
                jbj4 = queryComponentsPSGD(geometry, "?????????", "?????????.", 0);
            } /*else if ("????????????".equals(visibleLayerInfo.getLayerName())) {
                yushuikou = queryComponentsPSGD(geometry, "????????????");
            } else if ("????????????(????????????)".equals(visibleLayerInfo.getLayerName())) {
                psgjz = queryComponentsPSGD(geometry, "????????????(????????????)", "??????(????????????)", 0);
                psgjz1 = queryComponentsHP(geometry, "????????????(????????????)", "??????(????????????)", 0);
                psgjz2 = queryComponentsHP(geometry, "????????????(????????????)", "?????????(????????????)", 1);
                psgjz3 = queryComponentsHP(geometry, "????????????(????????????)", "?????????(????????????)", 2);
            } else if ("????????????".equals(visibleLayerInfo.getLayerName())) {
                yushuikou2 = queryComponentsPSGD(geometry, "????????????");
            } else if ("????????????(????????????)".equals(visibleLayerInfo.getLayerName())) {
                psgdz = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 0);
                psgdz1 = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 1);
                psgdz2 = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 0);
                psgdz3 = queryComponentsPSGD(geometry, "????????????(????????????)", "????????????(????????????)", 1);
            } else if ("????????????".equals(visibleLayerInfo.getLayerName())) {
                gjjd = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_GJJD);
            } else if("??????????????????".equals(visibleLayerInfo.getLayerName())){
                psdy = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, 0);
            } */else if("?????????".equals(visibleLayerInfo.getLayerName())){
                psh = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_USER, PatrolLayerPresenter.DRAINAGE_USER, 0);
//                psh2 = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_USER2, PatrolLayerPresenter.DRAINAGE_USER, 1);
            }else if( PatrolLayerPresenter.DRAINAGE_USER2.equals(visibleLayerInfo.getLayerName())){
                psh2 = queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_USER2, PatrolLayerPresenter.DRAINAGE_USER, 0);
            }
        }
        /**
         * ???????????????????????????????????????????????????????????????????????????
         */
        if (yushuikou == null && gjjd != null) {
            observables.add(gjjd);
        }
        if (jbj1 != null && canSearchJb) {
            observables.add(jbj1);
        }
        if (jbj2 != null && canSearchJb) {
            observables.add(jbj2);
        }
        if (jbj3 != null && canSearchJb) {
            observables.add(jbj3);
        }
        if (jbj4 != null && canSearchJb) {
            observables.add(jbj4);
        }
//        if(psdy != null){
//            observables.add(psdy);
//        }
        if (yushuikou != null) {
            observables.add(yushuikou);
        }
        if (psgjz != null) {
            observables.add(psgjz);
        }
        if (psgjz1 != null) {
            observables.add(psgjz1);
        }
        if (psgjz2 != null) {
            observables.add(psgjz2);
        }
        if (psgjz3 != null) {
            observables.add(psgjz3);
        }
        if (yushuikou2 != null) {
            observables.add(yushuikou2);
        }
        if (psh != null) {
            observables.add(psh);
        }
        if (psh2 != null) {
            observables.add(psh2);
        }
        if (psgdz != null) {
            observables.add(psgdz);
        }
        if (psgdz1 != null) {
            observables.add(psgdz1);
        }
        if (psgdz2 != null) {
            observables.add(psgdz2);
        }
        if (psgdz3 != null) {
            observables.add(psgdz3);
        }
        if (psdy != null) {
            observables.add(psdy);
        }
        if (observables.isEmpty()) {
            callback.onSuccess(Collections.<Component>emptyList());
            return;
        }
        queryComponentsForSpecial(geometry, callback, visibleLayerInfos, observables, canSearchJb);
    }

    /**
     * ?????????????????????,??????????????????
     */
    public void queryComponentsForSpecial(final Geometry geometry, final Callback2<List<Component>> callback, final List<LayerInfo> visibleLayerInfos, List<Observable<List<Component>>> observables, boolean canSearchJb) {
        if (observables.isEmpty()) {
            callback.onSuccess(Collections.<Component>emptyList());
            return;
        }
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    /**
     * ????????????????????????????????????(??????)
     */
    public void queryAllRoadByKeyword(final String objectId, final Callback2<List<Component>> callback) {
        String road1 = getLayerUrlForName("??????") + "/0";
        String road2 = getLayerUrlForName("??????") + "/1";
        String road3 = getLayerUrlForName("??????") + "/2";
        Observable<List<Component>> daolu1 = queryAddressComponentsForName(objectId, road1, "??????");
        Observable<List<Component>> daolu2 = queryAddressComponentsForName(objectId, road2, "??????");
        Observable<List<Component>> daolu3 = queryAddressComponentsForName(objectId, road3, "??????");
        List<Observable<List<Component>>> observables = new ArrayList<>();
        observables.add(daolu1);
        observables.add(daolu2);
        observables.add(daolu3);
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    /**
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param name
     * @param url
     * @return
     */
    public Observable<List<Component>> queryAddressComponentsForName(final String name, final String url, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<Component>>() {
            @Override
            public void call(final Subscriber<? super List<Component>> subscriber) {
                final Query query = getQueryByNameLike(name);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        if (ListUtil.isEmpty(featureSet.getGraphics())) {
                            subscriber.onNext(new ArrayList<Component>());
                            subscriber.onCompleted();
                            return;
                        }
                        List<Component> componentList = new ArrayList<>();
                        for (Graphic graphic : featureSet.getGraphics()) {
                            Component component = new Component();
                            component.setLayerUrl(url);
                            if (StringUtil.isEmpty(layer.getName())) {
                                component.setLayerName(layer.getName());
                            } else {
                                component.setLayerName(layerName);
                            }
                            if (StringUtil.isEmpty(component.getLayerName())) {
                                component.setLayerName(layerName);
                            }
                            component.setDisplayFieldName(featureSet.getDisplayFieldName());
//                            component.setFieldAlias(featureSet.getFieldAliases());
//                            component.setFields(featureSet.getFields());
                            component.setGraphic(graphic);
//                            component.setFieldAlias(featureSet.getFieldAliases());
                            Object o = graphic.getAttributes().get(featureSet.getObjectIdFieldName());
                            if (o != null && o instanceof Integer) {
                                component.setObjectId((Integer) o); //????????????objectId?????????integer???
                            }
                            componentList.add(component);
                        }
                        subscriber.onNext(componentList);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onNext(new ArrayList<Component>());
                        subscriber.onCompleted();
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }

    private Query getQueryByNameLike(String name) {
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setWhere("NAME like '%" + name + "%'");
        return query;
    }

    /**
     * ????????????????????????????????????(??????)
     */
    public void queryPrimaryPipeForObjectId(final String objectId, final Callback2<List<Component>> callback) {
        final String oldComponentLayerUrl = getLayerUrlByLayName(PatrolLayerPresenter.DRAINAGE_PIPELINE) + "/0";
        Observable<List<Component>> yinjin = queryComponentsForObjectId(objectId, oldComponentLayerUrl, "??????");
//        Observable<List<Component>> yinjin = queryComponentsWithLayerNameAndObjectId(objectId, "??????");
//        Observable<List<Component>> yushuikou = queryComponentsWithLayerNameAndObjectId(objectId, "?????????");
//        Observable<List<Component>> paifangkou = queryComponentsWithLayerNameAndObjectId(objectId, "?????????");
        List<Observable<List<Component>>> observables = new ArrayList<>();
        observables.add(yinjin);
//        observables.add(yushuikou);
//        observables.add(paifangkou);
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    private String getLayerUrlByLayName(String layerName) {
        String url = "";
        List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
        if (ListUtil.isEmpty(layerInfosFromLocal)) {
            return url;
        }
        for (LayerInfo layerInfo : layerInfosFromLocal) {
            if (layerInfo.getLayerName().contains(layerName)) {
                url = layerInfo.getUrl();
            }
        }
        return url;
    }

    private String getLayerUrlByLayName2(String layerName) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        String url = sharedPreferencesUtil.getString(layerName, "");
        if (StringUtil.isEmpty(url)) {
            url = getLayerUrlForName(layerName);
        }
        if (StringUtil.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param objectId
     * @param url
     * @return
     */
    public Observable<List<Component>> queryComponentsForObjectId(final String objectId, final String url, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<Component>>() {
            @Override
            public void call(final Subscriber<? super List<Component>> subscriber) {
                final Query query = getQueryByObjectId(objectId);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        if (ListUtil.isEmpty(featureSet.getGraphics())) {
                            subscriber.onNext(new ArrayList<Component>());
                            subscriber.onCompleted();
                            return;
                        }
                        List<Component> componentList = new ArrayList<>();
                        for (Graphic graphic : featureSet.getGraphics()) {
                            Component component = new Component();
                            component.setLayerUrl(url);
                            if (StringUtil.isEmpty(layer.getName())) {
                                component.setLayerName(layer.getName());
                            } else {
                                component.setLayerName(layerName);
                            }
                            component.setDisplayFieldName(featureSet.getDisplayFieldName());
//                            component.setFieldAlias(featureSet.getFieldAliases());
//                            component.setFields(featureSet.getFields());
                            component.setGraphic(graphic);
//                            component.setFieldAlias(featureSet.getFieldAliases());
                            Object o = graphic.getAttributes().get(featureSet.getObjectIdFieldName());
                            if (o != null && o instanceof Integer) {
                                component.setObjectId((Integer) o); //????????????objectId?????????integer???
                            }
                            componentList.add(component);
                        }
                        subscriber.onNext(componentList);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }

    private Query getQueryByObjectId(String objectId) {
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
//        query.setWhere("OBJECTID=" + objectId +" or NAME='"+objectId+"'");
        query.setWhere("OBJECTID='" + objectId + "'");
        return query;
    }


    /**
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param url
     * @param query
     * @param layerName
     * @return
     */
    public Observable<List<Component>> queryComponentsForKeyword(final String url, final Query query, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<Component>>() {
            @Override
            public void call(final Subscriber<? super List<Component>> subscriber) {
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        if (ListUtil.isEmpty(featureSet.getGraphics())) {
                            subscriber.onNext(new ArrayList<Component>());
                            subscriber.onCompleted();
                            return;
                        }
                        List<Component> componentList = new ArrayList<>();
                        for (Graphic graphic : featureSet.getGraphics()) {
                            Component component = new Component();
                            component.setLayerUrl(url);
                            if (StringUtil.isEmpty(layer.getName())) {
                                component.setLayerName(layer.getName());
                            } else {
                                component.setLayerName(layerName);
                            }
                            component.setDisplayFieldName(featureSet.getDisplayFieldName());
//                            component.setFieldAlias(featureSet.getFieldAliases());
//                            component.setFields(featureSet.getFields());
                            component.setGraphic(graphic);
//                            component.setFieldAlias(featureSet.getFieldAliases());
                            Object o = graphic.getAttributes().get(featureSet.getObjectIdFieldName());
                            if (o != null && o instanceof Integer) {
                                component.setObjectId((Integer) o); //????????????objectId?????????integer???
                            }
                            componentList.add(component);
                        }
                        subscriber.onNext(componentList);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io());
    }

    /**
     * ????????????????????????????????????(??????)
     */
    public void queryPshPsdyName(final String keyword, final Callback2<List<Component>> callback) {
        final String oldComponentLayerUrl = getLayerUrlByLayName2(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2) + "/0";
        Observable<List<Component>> psdy = queryComponentsForKeyword(oldComponentLayerUrl, getQueryPsdyByKey(keyword), PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2);
        final String pshUrl = getLayerUrlByLayName2(PatrolLayerPresenter.DRAINAGE_USER) + "/0";
        final String pshUrl2 = getLayerUrlByLayName2(PatrolLayerPresenter.DRAINAGE_USER2) + "/0";
        Observable<List<Component>> psh = queryComponentsForKeyword(pshUrl, getQueryPshByKey(keyword), PatrolLayerPresenter.DRAINAGE_USER);
        Observable<List<Component>> psh2 = queryComponentsForKeyword(pshUrl2, getQueryPshByKey(keyword), PatrolLayerPresenter.DRAINAGE_USER);
//        Observable<List<Component>> yinjin = queryComponentsWithLayerNameAndObjectId(objectId, "??????");
//        Observable<List<Component>> yushuikou = queryComponentsWithLayerNameAndObjectId(objectId, "?????????");
//        Observable<List<Component>> paifangkou = queryComponentsWithLayerNameAndObjectId(objectId, "?????????");
        List<Observable<List<Component>>> observables = new ArrayList<>();
        observables.add(psh);
        observables.add(psh2);
        observables.add(psdy);
//        observables.add(yushuikou);
//        observables.add(paifangkou);
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    /**
     * ??????????????????
     */
    public void queryPshPsdy(final String keyword, final Callback2<List<Component>> callback) {
        final String oldComponentLayerUrl = getLayerUrlByLayName2(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2) + "/0";
        Observable<List<Component>> psdy = queryComponentsForKeyword(oldComponentLayerUrl, getQueryPsdyByKey(keyword), PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2);
        final String pshUrl = getLayerUrlByLayName2(PatrolLayerPresenter.DRAINAGE_USER) + "/0";
        List<Observable<List<Component>>> observables = new ArrayList<>();
        observables.add(psdy);
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }

    /**
     * ??????????????????
     */
    public void queryPshPsdyById(final String objectId, final Callback2<List<Component>> callback) {
        final String oldComponentLayerUrl = getLayerUrlByLayName2(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2) + "/0";
        StringBuilder sb = new StringBuilder();
        sb.append("(OBJECTID = " + objectId + ") ");
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setWhere(sb.toString());
        Observable<List<Component>> psdy = queryComponentsForKeyword(oldComponentLayerUrl, query, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2);
        final String pshUrl = getLayerUrlByLayName2(PatrolLayerPresenter.DRAINAGE_USER) + "/0";
        List<Observable<List<Component>>> observables = new ArrayList<>();
        observables.add(psdy);
        Observable.zip(observables, new FuncN<List<Component>>() {
            @Override
            public List<Component> call(Object... args) {
                if (args.length == 0) {
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for (Object o : args) {
                    List<Component> components = (List<Component>) o;
                    componentList.addAll(components);
                }
                return componentList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Component>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(new Exception(e));
                    }

                    @Override
                    public void onNext(List<Component> componentList) {
                        callback.onSuccess(componentList);
                    }
                });
    }
}
