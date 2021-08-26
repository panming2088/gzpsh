package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.componentmaintenance.service.ComponentService;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.CleanLocationMarkEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.LocationEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.RefreshUserInfoView;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.MonitorService;
import com.augurit.agmobile.gzps.drainage_unit_monitor.widget.CheckView;
import com.augurit.agmobile.gzps.drainage_unit_monitor.widget.UnitInfoView;
import com.augurit.agmobile.gzps.layer.PatrolLayerPresenter4JbjPsdy;
import com.augurit.agmobile.gzps.layer.PatrolLayerView3;
import com.augurit.agmobile.gzps.measure.view.MapMeasureView;
import com.augurit.agmobile.gzps.uploadfacility.service.UploadLayerService;
import com.augurit.agmobile.gzpssb.common.legend.PSHImageLegendView;
import com.augurit.agmobile.gzpssb.common.search.IOnSearchClickListener;
import com.augurit.agmobile.gzpssb.common.search.SearchFragment;
import com.augurit.agmobile.gzpssb.jbjpsdy.model.PsdyJbj;
import com.augurit.agmobile.gzpssb.jbjpsdy.view.MapDrawQuestionView;
import com.augurit.agmobile.mapengine.common.utils.GeometryUtil;
import com.augurit.agmobile.mapengine.common.widget.compassview.CompassView;
import com.augurit.agmobile.mapengine.common.widget.scaleview.MapScaleView;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.service.LayerServiceFactory;
import com.augurit.agmobile.mapengine.legend.service.OnlineLegendService;
import com.augurit.agmobile.mapengine.legend.view.ILegendView;
import com.augurit.agmobile.patrolcore.common.SelectLocationTouchListener;
import com.augurit.agmobile.patrolcore.common.legend.LegendPresenter;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.agmobile.patrolcore.common.widget.LocationButton;
import com.augurit.agmobile.patrolcore.editmap.widget.LocationMarker;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.selectlocation.view.IDrawerController;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.Symbol;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.esri.core.geometry.Geometry.Type.POLYGON;

public class DrainageUnitMonitorFragment extends Fragment {
    private MapView mMapView;
    private MapScaleView scaleView;
    private CompassView mCompassView;
    private LocationButton locationButton;
    private LocationMarker locationMarker;

    private PatrolLayerPresenter4JbjPsdy layerPresenter;
    private Context mContext;
    private PatrolLayerView3 patrolLayerView3;
    private UploadLayerService layersService;
    private boolean loadLayersSuccess = true;

    private MapMeasureView mMapMeasureView;
    private MapDrawQuestionView mMapDrawQuestionView;

    private boolean hasLoadLayerBefore = false;
    private LegendPresenter legendPresenter;
    private FrameLayout map_bottom_sheet;
    private FrameLayout map_bottom_sheet2;

    private GraphicsLayer mGLayer = null;
    private GraphicsLayer mGraphicSelectLayer = null;
    private ProgressDialog pd;
    private ComponentService componentService;
    private MonitorService service;

    private UnitInfoView unitInfoView;
    public CheckView checkView;

    private SearchFragment searchFragment;
    private float x, y;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drainage_monitor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        service = new MonitorService(mContext);
        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clean(CleanLocationMarkEvent event){
        initGLayer();
        initGraphicSelectGLayer();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUserInfoView(RefreshUserInfoView event){
        pd = new ProgressDialog(getContext());
        pd.setMessage("正在查询...");
        pd.show();
        componentService.queryPshPsdyById(event.component.getObjectId() + "", new Callback2<List<Component>>() {
            @Override
            public void onSuccess(List<Component> components) {
                if (ListUtil.isEmpty(components)) {
                    ToastUtil.shortToast(getContext(), "该地点未查询到排水单元");
                    pd.dismiss();
                    return;
                }
                Component component = components.get(0);

                pd.dismiss();
                unitInfoView.show(component);
            }

            @Override
            public void onFail(Exception error) {
                if (pd != null) {
                    pd.dismiss();
                }
                ToastUtil.shortToast(getContext(), "没有数据");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void location(LocationEvent event){
        Point targetPoint = new Point(event.x, event.y);
        int shift = (((ViewGroup)map_bottom_sheet2.getParent()).getHeight() - checkView.getTop()) / 2;
//        mMapView.setTranslationY(-shift);
        mMapView.setScale(1078.527112478145f, false);
        scaleView.setScale(1078.527112478145f);
        double y = event.y - (shift * mMapView.getResolution());
        mMapView.centerAt(new Point(event.x, y), true);
//        mMapView.centerAt(targetPoint, true);
        drawGeometry(targetPoint, mGLayer, true, false);
        checkView.startLocation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void initView(){
        map_bottom_sheet =  getView().findViewById(R.id.map_bottom_sheet);
        map_bottom_sheet2 =  getView().findViewById(R.id.map_bottom_sheet2);
        unitInfoView = new UnitInfoView(this, map_bottom_sheet,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unitInfoView.hide();
                        checkView.show((Component)v.getTag(R.id.tag_3), (Long)v.getTag(R.id.tag_1), (String)v.getTag(R.id.tag_2));
                    }
                });
        checkView = new CheckView(this, map_bottom_sheet2);
//        unitInfoView.show();
        getView().findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        ((TextView)getView().findViewById(R.id.tv_title)).setText("排水单元监管");

        getView().findViewById(R.id.ll_search_obj1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchFragment.isAdded()
                        && !searchFragment.isVisible()
                        && !searchFragment.isRemoving()) {
                    searchFragment.show(getFragmentManager(), DrainageUnitMonitorFragment.class.getSimpleName());
                }
            }
        });
        searchFragment = SearchFragment.newInstance();
        searchFragment.setTAG(DrainageUnitMonitorFragment.class.getSimpleName());
        searchFragment.setHintText("输入单元名称");
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                if (!StringUtil.isEmpty(keyword)) {
                    if(unitInfoView.isVisible()){
                        unitInfoView.hide();
                    } else if(checkView.isVisible()){
                        checkView.hide();
                    }
                    queryForObjctByName(keyword);
                }
            }
        });

        getView().findViewById(R.id.btn_layer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) mContext;
                if (activity instanceof IDrawerController) {
                    IDrawerController drawerController = (IDrawerController) activity;
                    drawerController.openDrawer(new IDrawerController.OnDrawerOpenListener() {
                        @Override
                        public void onOpened(View drawerView) {
                            if (layerPresenter != null) {
                                layerPresenter.showLayerList();
                                hasLoadLayerBefore = true;
                            }
                        }
                    });
                }
            }
        });

        getView().findViewById(R.id.ll_legend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (legendPresenter == null) {
                    ILegendView legendView = new PSHImageLegendView(mContext);
                    legendPresenter = new LegendPresenter(legendView, new OnlineLegendService(mContext));
                }
                if (layerPresenter != null) {
                    legendPresenter.showLegends(layerPresenter.getService().getVisibleLayerInfos());
                } else {
                    legendPresenter.showLegends();
                }
            }
        });
        initMap();
    }

    private void initData(){
        componentService = new ComponentService(mContext.getApplicationContext());
    }

    private void queryForObjctByName(String name) {
        pd = new ProgressDialog(getContext());
        pd.setMessage("正在查询...");
        pd.show();
        componentService.queryPshPsdy(name, new Callback2<List<Component>>() {
            @Override
            public void onSuccess(List<Component> components) {
                if (ListUtil.isEmpty(components)) {
                    ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                    pd.dismiss();
                    return;
                }
                Component component = components.get(0);
                drawGeometry(component.getGraphic().getGeometry(), mGLayer, true, true);

                pd.dismiss();
                unitInfoView.show(component);
            }

            @Override
            public void onFail(Exception error) {
                if (pd != null) {
                    pd.dismiss();
                }
                ToastUtil.shortToast(getContext(), "没有数据");
            }
        });
    }

    private void initMap(){
        mMapView = (MapView) getView().findViewById(R.id.map);
//        mMapView.addLayer(mGLayer);
        mMapView.setMapBackground(Color.WHITE, Color.TRANSPARENT, 0f, 0f);//设置地图背景色、去掉网格线
        scaleView = (MapScaleView) getView().findViewById(R.id.scale_view);
        ViewGroup ll_rotate_container = (ViewGroup) getView().findViewById(R.id.ll_rotate_container);
//        MapRotateView mapRotateVew = new MapRotateView(mContext, mMapView, ll_rotate_container);

        ViewGroup ll_compass_container = (ViewGroup) getView().findViewById(R.id.ll_compass_container);
        mCompassView = new CompassView(getContext(), ll_compass_container, mMapView);
        scaleView.setMapView(mMapView);
        //定位图标
        locationMarker = (LocationMarker) getView().findViewById(R.id.locationMarker);
//        locationMarker.setVisibility(View.VISIBLE);
        locationButton = (LocationButton) getView().findViewById(R.id.locationButton);
        locationButton.setRotateEnable(true);
        locationButton.setIfShowCallout(false);
        locationButton.setMapView(mMapView);
        locationButton.setScaleView(scaleView);
        locationButton.setOnceLocation(false);
        locationButton.setIfAlwaysCeterToLocation(true);
        locationButton.setOnStateChangedListener(new LocationButton.OnStateChangedListener() {
            @Override
            public void onStateChanged(LocationButton.State state) {

                if (mCompassView != null) {
                    if (state == LocationButton.State.rotate) {
                        mCompassView.setVisible(true);
                        mCompassView.setRotatable(true);
                    } else {
                        mCompassView.setRotatable(false);
                    }
                }
            }
        });
        loadMap();
        mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void onStatusChanged(Object source, STATUS status) {
                if (STATUS.INITIALIZED == status) {
                    if (source instanceof MapView) {
                        if (PatrolLayerPresenter4JbjPsdy.initScale != -1 && PatrolLayerPresenter4JbjPsdy.initScale != 0) {
                            mMapView.setScale(PatrolLayerPresenter4JbjPsdy.initScale);
                            scaleView.setScale(PatrolLayerPresenter4JbjPsdy.initScale);
                        }

                        if (PatrolLayerPresenter4JbjPsdy.longitude != 0 && PatrolLayerPresenter4JbjPsdy.latitude != 0) {
                            Point point = new Point(PatrolLayerPresenter4JbjPsdy.longitude, PatrolLayerPresenter4JbjPsdy.latitude);
                            mMapView.centerAt(point, true);
                        }
                        mMapView.setMaxScale(30);

                        if (locationButton != null) {
                            locationButton.followLocation();
                        }

                        if (layerPresenter != null) {
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter4JbjPsdy.RIVER_FLOW_LAYER_NAME, false);
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter4JbjPsdy.DRAINAGE_PIPELINE, false);
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, true);
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER_BZ, true);
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_JIEBOJING, false);
//                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter4JbjPsdy.DRAINAGE_UNIT_LAYER2, true);

                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_WELL, false);
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DOOR_NO_LAYER, false);
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_USER, false);
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_USER2, false);
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_JIEHUJING, false);
                            layerPresenter.changeLayerVisibility("排水管井(中心城区)", false);
                            layerPresenter.changeLayerVisibility("排水管道(中心城区)", false);

                            List<LayerInfo> layerInfoList = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
                            if(layerInfoList != null) {
                                for (LayerInfo layerInfo : layerInfoList) {
                                    if (layerInfo.getLayerName().contains(PatrolLayerPresenter4JbjPsdy.DRAINAGE_UNIT_LAYER2)) {
                                        layerPresenter.onClickOpacityButton(layerInfo.getLayerId(), layerInfo, 0.5f);
                                    }
                                }
                            }
                        }
                    }
                } else if(STATUS.INITIALIZATION_FAILED == status || STATUS.LAYER_LOADING_FAILED == status){
                    ToastUtil.shortToast(getContext(), "图层加载失败");
                    getActivity().finish();
                }
            }
        });

        mMapView.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {

            }

            @Override
            public void postAction(float v, float v1, double v2) {
                scaleView.refreshScaleView();
            }
        });
        mMapView.setOnTouchListener(new DefaultTouchListener(mContext, mMapView, locationMarker, null));
    }

    public void loadMap() {
        final IDrawerController drawerController = (IDrawerController) mContext;
        patrolLayerView3 = new PatrolLayerView3(mContext, mMapView, drawerController.getDrawerContainer());
        layersService = new UploadLayerService(mContext.getApplicationContext());
        layerPresenter = new PatrolLayerPresenter4JbjPsdy(patrolLayerView3, layersService);
        layerPresenter.loadLayer(new Callback2<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                loadLayersSuccess = true;
            }

            @Override
            public void onFail(Exception error) {
                loadLayersSuccess = false;
            }
        });
    }

    private class DefaultTouchListener extends SelectLocationTouchListener {

        public DefaultTouchListener(Context context, MapView view, LocationMarker locationMarker, View.OnClickListener calloutSureButtonClickListener) {
            super(context, view, locationMarker, calloutSureButtonClickListener);
        }

        @Override
        public boolean onSingleTap(final MotionEvent e) {
            initGLayer();
            initGraphicSelectGLayer();
            if(unitInfoView.isVisible()){
                unitInfoView.hide();
            } else if(checkView.isVisible()){
                checkView.hide();
            } else {
                query(e.getX(), e.getY());
            }
            return true;
        }
    }
    private void initGLayer() {
        if (mGLayer == null) {
            mGLayer = new GraphicsLayer();
            mMapView.addLayer(mGLayer);
        } else {
            mGLayer.removeAll();
        }
    }

    /**
     * 在地图上画图形
     *
     * @param graphicsLayer
     * @param ifRemoveAll   在添加前是否移除掉所有
     * @param geometry
     */
    private void drawGeometry(Geometry geometry, GraphicsLayer graphicsLayer, boolean ifRemoveAll, boolean ifCenter) {

        if (graphicsLayer == null) {
            return;
        }
        Symbol symbol = null;
        switch (geometry.getType()) {
            case LINE:
            case POLYLINE:
                symbol = new SimpleLineSymbol(Color.RED, 5);
                break;
            case POINT:
//                symbol = new SimpleMarkerSymbol(Color.RED, 25, SimpleMarkerSymbol.STYLE.CIRCLE);
                symbol = getPointSymbol();
                break;
            case POLYGON:
                symbol = new SimpleFillSymbol(Color.RED, SimpleFillSymbol.STYLE.SOLID).setAlpha(100);
                break;
            default:
                break;
        }

        if (ifRemoveAll) {
            graphicsLayer.removeAll();
        }

        if (symbol != null) {
            Graphic graphic = new Graphic(geometry, symbol);
            graphicsLayer.addGraphic(graphic);

        }

        if (ifCenter) {
            if(geometry.getType() == POLYGON){
                mMapView.setExtent(geometry, 0, true);
            } else {
                mMapView.centerAt(GeometryUtil.getGeometryCenter(geometry), true);
            }
        }
    }

    private Symbol getPointSymbol() {
        Drawable drawable = ResourcesCompat.getDrawable(mContext.getResources(), com.augurit.agmobile.patrolcore.R.mipmap.ic_select_location, null);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(getContext(), drawable);// xjx 改为兼容api19的方式获取drawable
        pictureMarkerSymbol.setOffsetY(16);
        return pictureMarkerSymbol;
    }

    private void query(float x, float y) {
        final Point point = mMapView.toMapPoint(x, y);
        Geometry geometry = GeometryEngine.buffer(point, mMapView.getSpatialReference(), 40 * mMapView.getResolution(), null);
        Observable<List<Component>> observable =  componentService.queryComponentsPSGD(geometry, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, 0);
        if(observable == null){
            ToastUtil.shortToast(getContext(), "请求失败");
            return;
        }

        pd = new ProgressDialog(getContext());
        pd.setMessage("正在查询设施...");
        pd.show();
        observable.subscribe(new Observer<List<Component>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        pd.dismiss();

                    }

                    @Override
                    public void onNext(List<Component> components) {
                        if (ListUtil.isEmpty(components)) {
                            ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                            pd.dismiss();
                            return;
                        }
                        Component component = components.get(0);
                        getGJInfo(component.getObjectId() + "");
                        drawGeometry(component.getGraphic().getGeometry(), mGLayer, true, true);

                        layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, true);
                        layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_JIEBOJING, true);
                        pd.dismiss();
                        unitInfoView.show(component);
                    }
                });
    }

    public boolean handleBack(){
        if(unitInfoView.isVisible()){
            initGLayer();
            initGraphicSelectGLayer();
            unitInfoView.hide();
            return true;
        } else if(checkView.isVisible()){
            unitInfoView.reshow();
            checkView.hide();
            return true;
        } else {
            return false;
        }
    }

    private void getGJInfo(String psdyId){
        service.queryPsdyJbj(psdyId)
                .subscribe(new Subscriber<List<PsdyJbj>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<PsdyJbj> psdyJbjs) {
                        drawPsdyLines(psdyJbjs);
                    }
                });
    }

    private void initGraphicSelectGLayer() {
        if (mGraphicSelectLayer == null) {
            mGraphicSelectLayer = new GraphicsLayer();
            mMapView.addLayer(mGraphicSelectLayer);
        } else {
            mGraphicSelectLayer.removeAll();
        }
    }

    private void drawPsdyLines(List<PsdyJbj> data) {
        if (ListUtil.isEmpty(data)) return;
        Polyline polyline = null;
        PsdyJbj psdyJbj = null;
        Point startP = null;
        Point endP = null;
        initGraphicSelectGLayer();
        for (int j = 0; j < data.size(); j++) {
            polyline = new Polyline();
            psdyJbj = data.get(j);
            startP = new Point();
            startP.setXY(psdyJbj.getJbjX(), psdyJbj.getJbjY());
            endP = new Point();
            endP.setXY(psdyJbj.getPsdyX(), psdyJbj.getPsdyY());
            polyline.startPath(startP);
            polyline.lineTo(endP);
            drawGeometry(psdyJbj, polyline, mGraphicSelectLayer, new SimpleLineSymbol(Color.BLUE, 2, SimpleLineSymbol.STYLE.DASH), false, false);
        }
    }

    /**
     * 在地图上画图形
     *
     * @param graphicsLayer
     * @param ifRemoveAll   在添加前是否移除掉所有
     * @param geometry
     */
    private void drawGeometry(PsdyJbj psdyJbj, Geometry geometry, GraphicsLayer graphicsLayer, Symbol designatedSymbol, boolean ifRemoveAll, boolean ifCenter) {

        if (graphicsLayer == null) {
            return;
        }
        Symbol symbol = designatedSymbol;
        if (symbol == null) {
            switch (geometry.getType()) {
                case LINE:
                case POLYLINE:
                    symbol = new SimpleLineSymbol(Color.RED, 5);
                    break;
                case POINT:
//                symbol = new SimpleMarkerSymbol(Color.RED, 25, SimpleMarkerSymbol.STYLE.CIRCLE);
                    symbol = getPointSymbol();
                    break;
                default:
                    break;
            }
        }


        if (ifRemoveAll) {
            graphicsLayer.removeAll();
        }

        if (symbol != null) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Id", psdyJbj.getId());
            Graphic graphic = new Graphic(geometry, symbol, attributes);
            graphicsLayer.addGraphic(graphic);
        }

        if (ifCenter) {
            mMapView.centerAt(GeometryUtil.getGeometryCenter(geometry), true);
        }
    }
}
