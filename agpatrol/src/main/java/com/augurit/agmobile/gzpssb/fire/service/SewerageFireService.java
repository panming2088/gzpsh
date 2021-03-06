package com.augurit.agmobile.gzpssb.fire.service;

import android.content.Context;
import android.text.TextUtils;

import com.augurit.agmobile.gzps.common.constant.LayerUrlConstant;
import com.augurit.agmobile.gzps.componentmaintenance.model.QueryFeatureSet;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentFieldKeyConstant;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentTypeConstant;
import com.augurit.agmobile.gzpssb.common.PortSelectUtil;
import com.augurit.agmobile.gzpssb.fire.dao.PSHUploadFireApi;
import com.augurit.agmobile.gzpssb.pshstatistics.dao.NWSignStatisticApi;
import com.augurit.agmobile.gzpssb.seweragewell.dao.SewerageWellApi;
import com.augurit.agmobile.gzpssb.seweragewell.model.WellPhoto;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.service.LayerServiceFactory;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.login.service.LoginService;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.AMNetwork;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.log.LogUtil;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.tasks.SpatialRelationship;
import com.esri.core.tasks.ags.query.Query;
import com.esri.core.tasks.identify.IdentifyParameters;
import com.esri.core.tasks.identify.IdentifyResult;
import com.esri.core.tasks.identify.IdentifyTask;

import java.util.ArrayList;
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

import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.JIE_BO_LIANXIAN;

/**
 * @author ????????? ???luobiao
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.gzps.componentmaintenance
 * @createTime ???????????? ???18/4/10
 * @modifyBy ????????? ???luobiao
 * @modifyTime ???????????? ???18/4/10
 * @modifyMemo ???????????????
 */

public class SewerageFireService {
    private Context mContext;

    private AMNetwork amNetwork;
    private PSHUploadFireApi pshUploadFireApi;

    public SewerageFireService(Context context) {
        this.mContext = context;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(NWSignStatisticApi.class);
            this.pshUploadFireApi = (PSHUploadFireApi) this.amNetwork.getServiceApi(SewerageWellApi.class);
        }
    }

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

    private Query getQueryByID(String id) {
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setWhere("OBJECTID='" + id + "'");
        return query;
    }

    private Query getQueryMenPaiByID(String id) {
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setWhere("S_GUID='" + id + "'");
        return query;
    }


    /**
     * ????????????????????????????????????
     */
    public void queryPrimaryComponents(final Geometry geometry, final Callback2<List<Component>> callback){
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
                if(args.length == 0){
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for(Object o : args){
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
     * ????????????????????????????????????
     */
    public void queryUnitComponents(final Geometry geometry, final Callback2<List<Component>> callback){
        String url = getFireLayerUrl() + "/0";
//        String url = "http://139.159.243.185:6080/arcgis/rest/services/PAISHUIHU/dssxfs/MapServer/"+ "/0";
        queryComponents(geometry,url,PatrolLayerPresenter.FIRE_LAYER)
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
     * ????????????
     */
    public void queryComponentsForPipeAndYJ(final Geometry geometry, final Callback2<List<Component>> callback){
        String url = getJBWellLayerUrl() + "/0";
        queryComponents(geometry,url,JIE_BO_LIANXIAN)
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
     * ???????????????????????????????????????????????????????????????
     */
    public void queryDoorAndWellComponentsNew(final Geometry geometry, final Callback2<List<Component>> callback){
        String url = getFireLayerUrl() + "/0";
        List<Observable<List<Component>>> observables = new ArrayList<>();
        String url1 = getMenPaiLayerUrl() + "/0";
        Observable<List<Component>> jiebojing = queryComponents(geometry,url,"");
        Observable<List<Component>> menpai = queryComponents(geometry,url1,"");
        observables.add(jiebojing);
        observables.add(menpai);
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




    private String getFireLayerUrl() {
        String url = "";
        List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
        for (LayerInfo layerInfo : layerInfosFromLocal) {
            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.FIRE_LAYER)) {
                url = layerInfo.getUrl();
            }
        }
        return url;
    }

    //????????????????????????
    private String getJBWellLayerUrl() {
        String url = "";
        List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
        for (LayerInfo layerInfo : layerInfosFromLocal) {
            if (layerInfo.getLayerName().contains(JIE_BO_LIANXIAN)) {
                url = layerInfo.getUrl();
            }
        }
        return url;
    }

    /**
     * ?????????????????????
     * @return
     */
    private String getMenPaiLayerUrl() {
        String url = "";
        List<LayerInfo> layerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
        for (LayerInfo layerInfo : layerInfosFromLocal) {
            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.DOOR_NO_LAYER)) {
                url = layerInfo.getUrl();
            }
        }
        return url;
    }

    /**
     * identify??????????????????????????????????????????
     */
    public void identifyPrimaryComponents(final Geometry geometry, final MapView mapView, final Callback2<List<Component>> callback){
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
                    if(identifyResults != null && identifyResults.length > 0){
                        for(IdentifyResult identifyResult : identifyResults){
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
    public void querySecondComponents(final Geometry geometry, final Callback2<List<Component>> callback){
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
                if(args.length == 0){
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for(Object o : args){
                    if(o == null){
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
    public void queryAllComponentsExceptLine(final Geometry geometry, final Callback2<List<Component>> callback){
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
                if(args.length == 0){
                    return null;
                }
                List<Component> componentList = new ArrayList<>();
                for(Object o : args){
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
     * @param geometry ????????????
     * @param url  ?????????URL
     * @param newUrl ?????????URL
     * @param callback???????????????
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
     * @param geometry ????????????
     * @param url  ??????URL
     * @param callback???????????????
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
    public Observable<List<Component>> queryComponents(final Geometry geometry, final String url, final String layerName) {
        return Observable.create(new Observable.OnSubscribe<List<Component>>() {
            @Override
            public void call(final Subscriber<? super List<Component>> subscriber) {
                final Query query = getQueryByGeometry(geometry);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        if(ListUtil.isEmpty(featureSet.getGraphics())){
                            subscriber.onNext(new ArrayList<Component>());
                            subscriber.onCompleted();
                            return;
                        }
                        List<Component> componentList = new ArrayList<>();
                        for(Graphic graphic : featureSet.getGraphics()) {
                            Component component = new Component();
                            component.setLayerUrl(url);
                            component.setLayerName(TextUtils.isEmpty(layer.getName())? layerName:layer.getName());
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
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param id
     * @param url
     * @return
     */
    public Observable<List<Component>> queryComponents(final String id, final String url) {
        return Observable.create(new Observable.OnSubscribe<List<Component>>() {
            @Override
            public void call(final Subscriber<? super List<Component>> subscriber) {
                final Query query = getQueryByID(id);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        if(ListUtil.isEmpty(featureSet.getGraphics())){
                            subscriber.onNext(new ArrayList<Component>());
                            subscriber.onCompleted();
                            return;
                        }
                        List<Component> componentList = new ArrayList<>();
                        for(Graphic graphic : featureSet.getGraphics()) {
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
     * ??????layerUrl???????????????????????????????????????????????????
     *
     * @param id
     * @param url
     * @return
     */
    public Observable<List<Component>> queryMenPaiComponents(final String id, final String url) {
        return Observable.create(new Observable.OnSubscribe<List<Component>>() {
            @Override
            public void call(final Subscriber<? super List<Component>> subscriber) {
                final Query query = getQueryMenPaiByID(id);
                final ArcGISFeatureLayer layer = new ArcGISFeatureLayer(
                        url, ArcGISFeatureLayer.MODE.SNAPSHOT);
                layer.queryFeatures(query, new CallbackListener<FeatureSet>() {
                    @Override
                    public void onCallback(FeatureSet featureSet) {
                        if(ListUtil.isEmpty(featureSet.getGraphics())){
                            subscriber.onNext(new ArrayList<Component>());
                            subscriber.onCompleted();
                            return;
                        }
                        List<Component> componentList = new ArrayList<>();
                        for(Graphic graphic : featureSet.getGraphics()) {
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
        return Observable.zip(queryComponents(geometry, newComponentLayerUrl,""), queryComponents(geometry, oldComponentLayerUrl,""), new Func2<List<Component>, List<Component>, List<Component>>() {
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
                        if(ListUtil.isEmpty(featureSet.getGraphics())){
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

                        if (!TextUtils.isEmpty(LayerUrlConstant.getLayerNameByUnknownLayerUrl(url))){
                            QueryFeatureSet queryFeatureSet = new QueryFeatureSet();
                            queryFeatureSet.setLayerUrl(url);
                            queryFeatureSet.setFeatureSet(featureSet);
                            queryFeatureSet.setLayerName(LayerUrlConstant.getLayerNameByUnknownLayerUrl(url));
                            subscriber.onNext(queryFeatureSet);
                            subscriber.onCompleted();
                        }else {
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

}
