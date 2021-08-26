/* Copyright 2015 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.augurit.agmobile.gzps.addcomponent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.OnInitLayerUrlFinishEvent;
import com.augurit.agmobile.gzps.common.constant.LayerUrlConstant;
import com.augurit.agmobile.gzps.common.util.MapIconUtil;
import com.augurit.agmobile.gzps.componentmaintenance.ComponetListAdapter;
import com.augurit.agmobile.gzps.componentmaintenance.SelectComponentEvent;
import com.augurit.agmobile.gzps.componentmaintenance.model.QueryFeatureSet;
import com.augurit.agmobile.gzps.componentmaintenance.service.ComponentService;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentFieldKeyConstant;
import com.augurit.agmobile.gzps.layer.IncludeNewComponentLayerPresenter;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.agmobile.mapengine.common.utils.FilePathUtil;
import com.augurit.agmobile.mapengine.common.utils.GeometryUtil;
import com.augurit.agmobile.mapengine.common.widget.callout.attribute.AMFindResult;
import com.augurit.agmobile.mapengine.common.widget.scaleview.MapScaleView;
import com.augurit.agmobile.mapengine.identify.service.IIdentifyService;
import com.augurit.agmobile.mapengine.identify.util.IdentifyServiceFactory;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.service.LayersService;
import com.augurit.agmobile.mapengine.layermanage.view.ILayerView;
import com.augurit.agmobile.mapengine.legend.service.OnlineLegendService;
import com.augurit.agmobile.mapengine.legend.view.ILegendView;
import com.augurit.agmobile.patrolcore.common.SelectLocationTouchListener;
import com.augurit.agmobile.patrolcore.common.file.model.FileResult;
import com.augurit.agmobile.patrolcore.common.file.service.FileService;
import com.augurit.agmobile.patrolcore.common.legend.ImageLegendView;
import com.augurit.agmobile.patrolcore.common.legend.LegendPresenter;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.agmobile.patrolcore.common.model.LocationInfo;
import com.augurit.agmobile.patrolcore.common.table.TableViewManager;
import com.augurit.agmobile.patrolcore.common.table.dao.TableDataManager;
import com.augurit.agmobile.patrolcore.common.table.dao.remote.TableItems;
import com.augurit.agmobile.patrolcore.common.table.dao.remote.TableNetCallback;
import com.augurit.agmobile.patrolcore.common.table.model.Project;
import com.augurit.agmobile.patrolcore.common.table.model.ProjectTable;
import com.augurit.agmobile.patrolcore.common.table.model.TableItem;
import com.augurit.agmobile.patrolcore.common.table.util.TableState;
import com.augurit.agmobile.patrolcore.common.widget.LocationButton;
import com.augurit.agmobile.patrolcore.editmap.EditLineReEditStateMapListener;
import com.augurit.agmobile.patrolcore.editmap.OnGraphicChangedListener;
import com.augurit.agmobile.patrolcore.editmap.widget.LocationMarker;
import com.augurit.agmobile.patrolcore.layer.service.EditLayerService;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerView2;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.agmobile.patrolcore.selectlocation.util.PatrolLocationManager;
import com.augurit.agmobile.patrolcore.selectlocation.view.IDrawerController;
import com.augurit.agmobile.patrolcore.upload.view.ReEditTableActivity;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.utils.AMFileUtil;
import com.augurit.am.fw.utils.DrawableUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.AttachmentInfo;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.Symbol;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static com.augurit.agmobile.gzps.R.id.iv_my_upload_layer;
import static com.augurit.agmobile.gzps.R.id.subtype;


public class AddComponentFragment2 extends Fragment implements View.OnClickListener {

    private static final String KEY_MAP_STATE = "com.esri.MapState";

    private LocationMarker locationMarker;
//    /**
//     * 是否执行了放大缩小操作
//     */
//    private boolean hasZoomBefore = false;
//
    /**
     * 上次选择的位置
     */
    private Point mLastSelectedLocation = null;
    private PatrolLocationManager mLocationManager;
    /**
     * 绘制位置的图层
     */
    private GraphicsLayer mGLayerFroDrawLocation;

    /**
     * 是否是第一次定位，如果是，那么居中；否则，不做任何操作；
     */
    private boolean ifFirstLocate = true;
    private LocationButton locationButton;
    private LegendPresenter legendPresenter;
    private ViewGroup ll_component_list;


    View mView;

    MapView mMapView;

    private ILayerView layerView;

    private PatrolLayerPresenter layerPresenter;
    private boolean loadLayersSuccess = true;

    private View ll_layer_url_init_fail;
    private TextView show_all_layer;
    private GridView gridView;
    private LayerAdapter layerAdapter;
    //顶部图层列表中当前选中的设施类型对应的务URL
    private String currComponentUrl = LayerUrlConstant.newComponentUrls[0];

    private boolean ifFirstEdit = true; //是否第一次点击编辑按钮
    private View btn_edit;
    private View btn_edit_cancel;
    private SelectLocationTouchListener editModeSelectLocationTouchListener;
    private View.OnClickListener editModeCalloutSureButtonClickListener;
    private ComponentService componentService;

    ProgressDialog pd;
    ViewGroup map_bottom_sheet;
    BottomSheetBehavior mBehavior;
    private ComponetListAdapter componetListAdapter;
    //点查后设施的简要信息布局
    private View component_intro;
    private View component_detail_ll;
    //点查后设施的详细信息布局，用了TableViewManager
    private ViewGroup component_detail_container;
    private View btn_upload;
    private ArrayList<TableItem> tableItems = null;
    private ArrayList<Photo> photoList = new ArrayList<>();
    private String projectId;
    private TableViewManager tableViewManager;
    //点查后的设施结果
    private List<Component> mComponentQueryResult = new ArrayList<>();
    private int currIndex = 0;
    private View btn_prev;
    private View btn_next;

    /**
     * 地图默认的事件处理
     */
    private SelectLocationTouchListener defaultMapOnTouchListener;


    public static AddComponentFragment2 getInstance(Bundle data) {
        AddComponentFragment2 addComponentFragment2 = new AddComponentFragment2();
        addComponentFragment2.setArguments(data);
        return addComponentFragment2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_addcomponent2, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mView = view;


        // Find MapView and add feature layers
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.setMapBackground(Color.WHITE, Color.TRANSPARENT, 0f, 0f);//设置地图背景色、去掉网格线
        /**
         * 比例尺
         */
        final MapScaleView scaleView = (MapScaleView) view.findViewById(R.id.scale_view);
        scaleView.setMapView(mMapView);
        // Set listeners on MapView
        mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onStatusChanged(final Object source, final STATUS status) {
                if (STATUS.INITIALIZED == status) {
                    if (source instanceof MapView) {
                        //todo 暂时写死
                        //mMapView.setScale(3029.5773243397634);
                        if (PatrolLayerPresenter.initScale != -1 && PatrolLayerPresenter.initScale != 0){
                            mMapView.setScale(PatrolLayerPresenter.initScale);
                            scaleView.setScale(PatrolLayerPresenter.initScale);
                        }
                        Point point = new Point(113.34321911832133, 23.118568190325902);
                        mMapView.centerAt(point, true);
                        //startLocate();
                    }
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

        ll_layer_url_init_fail = view.findViewById(R.id.ll_layer_url_init_fail);
        onInitLayerUrlFinished(null);

        locationMarker = (LocationMarker) view.findViewById(R.id.locationMarker);
        locationButton = (LocationButton) view.findViewById(R.id.locationButton);
        locationButton.setIfShowCallout(false);
        locationButton.setMapView(mMapView);
        locationButton.setIfDrawLocation(false);
        mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
        /**
         * 候选列表容器
         */
        ll_component_list = (ViewGroup) view.findViewById(R.id.ll_component_list);

        /**
         * 默认使用窨井
         */
        locationMarker.changeIcon(MapIconUtil.getResId(LayerUrlConstant.componentNames[0]));
        show_all_layer = (TextView) view.findViewById(R.id.show_all_layer);
        show_all_layer.setOnClickListener(this);
        gridView = (GridView) view.findViewById(R.id.gridview);
        layerAdapter = new LayerAdapter(getContext());
        gridView.setAdapter(layerAdapter);
        layerAdapter.setOnItemClickListener(new OnRecyclerItemClickListener<String>() {
            @Override
            public void onItemClick(View view, int position, String selectedData) {
                currComponentUrl = selectedData;
                /**
                 * 切换选点图标
                 */
                if (locationMarker != null && position < LayerUrlConstant.componentNames.length) {
                    locationMarker.changeIcon(MapIconUtil.getResId(LayerUrlConstant.componentNames[position]));
                }
            }

            @Override
            public void onItemLongClick(View view, int position, String selectedData) {

            }
        });

        view.findViewById(R.id.btn_layer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity instanceof IDrawerController) {
                    IDrawerController drawerController = (IDrawerController) activity;
                    drawerController.openDrawer(new IDrawerController.OnDrawerOpenListener() {
                        @Override
                        public void onOpened(View drawerView) {
                            showLayerList();
                            double scale = mMapView.getScale();
                        }
                    });
                }
            }
        });

        map_bottom_sheet = (ViewGroup) view.findViewById(R.id.map_bottom_sheet);
        mBehavior = BottomSheetBehavior.from(map_bottom_sheet);
        btn_prev = view.findViewById(R.id.prev);
        btn_next = view.findViewById(R.id.next);
        component_intro = view.findViewById(R.id.intro);
        component_detail_ll = view.findViewById(R.id.detail_ll);
        component_detail_container = (ViewGroup) view.findViewById(R.id.detail_container);
        btn_upload = view.findViewById(R.id.btn_upload);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                onChangedBottomSheetState(newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currIndex--;
                if (currIndex < 0) {
                    btn_prev.setVisibility(View.GONE);
                    return;
                }
                showBottomSheet(mComponentQueryResult.get(currIndex));
                if (currIndex == 0) {
                    btn_prev.setVisibility(View.GONE);
                }
                if (mComponentQueryResult.size() > 1) {
                    btn_next.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currIndex++;
                if (currIndex > mComponentQueryResult.size()) {
                    btn_next.setVisibility(View.GONE);
                    return;
                }
                showBottomSheet(mComponentQueryResult.get(currIndex));
                if (currIndex == (mComponentQueryResult.size() - 1)) {
                    btn_next.setVisibility(View.GONE);
                }
                if (currIndex > 0) {
                    btn_prev.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableViewManager.uploadEdit();
            }
        });
        /*mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @IBottomSheetBehavior.State int newState) {
                if(newState == STATE_EXPANDED){
                    component_intro.setVisibility(View.GONE);
                    component_detail_ll.setVisibility(View.VISIBLE);
                } else if(newState == STATE_COLLAPSED){
                    component_intro.setVisibility(View.VISIBLE);
                    component_detail_ll.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });*/
        loadMap();


        /**
         * 图例
         */
        View ll_legend = view.findViewById(R.id.ll_legend);
        ll_legend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLegendPresenter();
                legendPresenter.showLegends();
            }
        });


        //水滴图标在地图中间的模式进行点查
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_edit_cancel = view.findViewById(R.id.btn_edit_cancel);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_edit.setVisibility(View.GONE);
                btn_edit_cancel.setVisibility(View.VISIBLE);
                if (ifFirstEdit) {
                    ToastUtil.iconLongToast(getActivity(), R.mipmap.ic_alert_yellow, "移动地图选择设施的位置");
                    ifFirstEdit = false;
                }

                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_check_data);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                hideBottomSheet();
                initGLayer();
                setSearchFacilityListener();
                //下面的代码不能在调用 setSearchFacilityListener 方法前调用，
                // 因为editModeSelectLocationTouchListener 和 editModeCalloutSureButtonClickListener 未初始化
                /*if(locate) {
                    editModeSelectLocationTouchListener.locate();
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().animatedHide();
                    }
                    editModeCalloutSureButtonClickListener.onClick(null);
                }*/
            }
        });
        btn_edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_edit.setVisibility(View.VISIBLE);
                btn_edit_cancel.setVisibility(View.GONE);
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }

                mMapView.getCallout().hide();
                hideBottomSheet();
                initGLayer();
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });
    }

    private void initLegendPresenter() {
        if (legendPresenter == null) {
            ILegendView legendView = new ImageLegendView(getActivity());
            legendPresenter = new LegendPresenter(legendView, new OnlineLegendService(getActivity()));
        }

    }

    public void loadMap() {
        IDrawerController drawerController = (IDrawerController) getActivity();
        layerView = new PatrolLayerView2(getActivity(), mMapView, drawerController.getDrawerContainer());
        layerPresenter = new IncludeNewComponentLayerPresenter(layerView);
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

    /**
     * 开启定位
     */
    public void startLocate() {
        if (mLocationManager == null) {
            mLocationManager = new PatrolLocationManager(getActivity(), mMapView);
        }
        this.mLocationManager.setMapView(mMapView);
        this.mLocationManager.setUseArcGisForLocation();
        mLocationManager.startLocate(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // drawLocationOnMap(location);
                final Point point = new Point(location.getLongitude(), location.getLatitude());
                //mLastSelectedLocation = point;
                if (mMapView.getMaxExtent() == null || mMapView.getSpatialReference() == null) {
                    return;
                }
                if (GeometryEngine.contains(mMapView.getMaxExtent(), point, mMapView.getSpatialReference())) {
                    if (ifFirstLocate) {
                        ifFirstLocate = false;
                        mMapView.centerAt(point, true);
                    }

                } else {
                    ToastUtil.shortToast(getActivity(), "当前位置不在地图范围内");
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

    }


    /**
     * 在地图上绘制当前位置
     *
     * @param location
     * @return
     */
    private void drawLocationOnMap(Location location) {
        if (mGLayerFroDrawLocation == null) {
            mGLayerFroDrawLocation = new GraphicsLayer();
            mMapView.addLayer(mGLayerFroDrawLocation);
        }
        Point point = new Point(location.getLongitude(), location.getLatitude());
        mGLayerFroDrawLocation.removeAll();
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(getActivity(),
                getActivity().getResources().getDrawable(com.augurit.agmobile.patrolcore.R.mipmap.patrol_location_symbol));
        Graphic graphic = new Graphic(new Point(location.getLongitude(), location.getLatitude()), pictureMarkerSymbol);
        mGLayerFroDrawLocation.addGraphic(graphic);
    }

    /**
     * 显示图层列表
     */
    public void showLayerList() {
        if (layerPresenter != null) {
            layerPresenter.showLayerList();
        }
    }


    @Override
    public void onClick(View view) {
        // Handle presses on the action bar items
        switch (view.getId()) {
            case R.id.show_all_layer:

                break;
            default:
        }
    }

    public void onBackPressed() {
        if (map_bottom_sheet.getVisibility() == View.GONE) {
            getActivity().finish();
            return;
        }
        if (mBehavior != null
                && map_bottom_sheet != null) {
            if (mBehavior.getState() == STATE_EXPANDED) {
                mBehavior.setState(STATE_COLLAPSED);
            } else if (mBehavior.getState() == STATE_COLLAPSED) {
                initGLayer();
                map_bottom_sheet.setVisibility(View.GONE);
                tableViewManager = null;
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                if (mMapView.getCallout().isShowing()) {
                    mMapView.getCallout().hide();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.unpause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_MAP_STATE, mMapView.retainState());
    }



//
//    private void showCallout(String address, Point point, final View.OnClickListener calloutSureButtonClickListener) {
//        final Point geometry = point;
//        final Callout callout = mMapView.getCallout();
//        View view = View.inflate(getActivity(), com.augurit.agmobile.patrolcore.R.layout.editmap_callout, null);
//        ((TextView) view.findViewById(com.augurit.agmobile.patrolcore.R.id.tv_listcallout_title)).setText(address);
//        view.findViewById(com.augurit.agmobile.patrolcore.R.id.btn_select_device).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (calloutSureButtonClickListener != null){
//                    calloutSureButtonClickListener.onClick(view);
//                }
//
////                Point geo = mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2,
////                        locationMarker.getY() + locationMarker.getHeight() / 2 - 140);
////                goAttrEdit(geo);
//            }
//        });
//        callout.setStyle(com.augurit.agmobile.patrolcore.R.xml.editmap_callout_style);
//        callout.setContent(view);
//        if (point == null) {
//            point = mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2 - 140);
//        }
//        callout.show(point);
//    }
//
//
//    /**
//     * 请求百度地址
//     *
//     * @param point
//     * @param callback1
//     */
//    private void requestLocation(Point point, SpatialReference spatialReference, final Callback1<String> callback1) {
//        SelectLocationService selectLocationService = new SelectLocationService(getActivity(), Locator.createOnlineLocator());
//        selectLocationService.parseLocation(new LatLng(point.getY(), point.getX()), spatialReference)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<DetailAddress>() {
//                    @Override
//                    public void call(DetailAddress detailAddress) {
//                        if (callback1 != null) {
//                            callback1.onCallback(detailAddress.getDetailAddress());
//                        }
//                    }
//                });
//    }

    private Point getMapCenterPoint() {
        //获取当前的位置
        return mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2);
    }



    /**
     * @return
     */
    private MapOnTouchListener getDefaultMapOnTouchListener() {
        if (defaultMapOnTouchListener == null) {
            defaultMapOnTouchListener = new DefaultTouchListener(getActivity(), mMapView, locationMarker, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            /**
             * 注册位置发生改变时的回调
             */
            defaultMapOnTouchListener.registerLocationChangedListener(new SelectLocationTouchListener.OnSelectedLocationChangedListener() {
                @Override
                public void onLocationChanged(Point newLocation) {
                    mLastSelectedLocation = newLocation;
                }

                @Override
                public void onAddressChanged(DetailAddress detailAddress) {

                }
            });
        }
        return defaultMapOnTouchListener;
    }


    /**
     * The MapView's touch listener.
     */
    private class DefaultTouchListener extends SelectLocationTouchListener {

        public DefaultTouchListener(Context context, MapView view, LocationMarker locationMarker, View.OnClickListener calloutSureButtonClickListener) {
            super(context, view, locationMarker, calloutSureButtonClickListener);
        }
//        MapView mapView;
//        /**
//         * callout中确定按钮的点击事件
//         */
//        private View.OnClickListener calloutSureButtonClickListener;
//
//        private boolean mShouldRequestNewAddress = true;
//
//        public DefaultTouchListener(Context context, MapView view, View.OnClickListener calloutSureButtonClickListener) {
//            super(context, view);
//            mapView = view;
//            this.calloutSureButtonClickListener = calloutSureButtonClickListener;
//        }
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            LogUtil.d("onTouch");
//            if (locationMarker.getVisibility() == View.GONE) {
//                return super.onTouch(v, event);
//            }
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_MOVE:
//                    if (mMapView.getCallout().isShowing()) {
//                        mMapView.getCallout().hide();
//                    }
//                    locationMarker.startUpAnimation(null);
//                    LogUtil.d("ACTION_DOWN");
//                    break;
//                case MotionEvent.ACTION_UP:
//                    centerToNewAddress(calloutSureButtonClickListener);
//                    /**
//                     * 如果之前执行了放大缩小操作，此时不需要重新获取位置
//                     */
////                    if (hasZoomBefore){
////                        hasZoomBefore = false;
////                        locationMarker.startDownAnimation(null);
////                    }else {
////                        centerToNewAddress();
////                    }
//                    break;
//                case MotionEvent.ACTION_CANCEL:
//                    centerToNewAddress(calloutSureButtonClickListener);
//                    break;
//                default:
//                    break;
//            }
//            return super.onTouch(v, event);
//        }
//
//        private void centerToNewAddress(final View.OnClickListener calloutSureButtonClickListener) {
//            locationMarker.startDownAnimation(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    Point point = getMapCenterPoint();
//                    mLastSelectedLocation = point;
//                    showCallout("定位中.....", null, calloutSureButtonClickListener);
//                    requestLocation(point, mMapView.getSpatialReference(), new Callback1<String>() {
//                        @Override
//                        public void onCallback(String s) {
//                            showCallout(s, null, calloutSureButtonClickListener);
//                        }
//                    });
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//        }
//
//
//        @Override
//        public boolean onPinchPointersDown(MotionEvent event) {
//            // LogUtil.d("onPinchPointersDown");
//            mShouldRequestNewAddress = false;
//            return super.onPinchPointersDown(event);
//        }
//
//        @Override
//        public boolean onPinchPointersMove(MotionEvent event) {
//            LogUtil.d("onPinchPointersMove");
//            return super.onPinchPointersMove(event);
//        }
//
//        @Override
//        public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
//            LogUtil.d("onDragPointerMove");
//            return super.onDragPointerMove(from, to);
//        }
//
//        @Override
//        public boolean onPinchPointersUp(MotionEvent event) {
//            LogUtil.d("onPinchPointersUp");
//            if (mLastSelectedLocation != null && locationMarker.getVisibility() == View.VISIBLE) {
//                /**
//                 * 注：以下两句话的顺序不可改变
//                 */
//                mMapView.centerAt(mLastSelectedLocation, true);
//            }
//            mShouldRequestNewAddress = true;
//            hasZoomBefore = true;
//            return super.onPinchPointersUp(event);
//        }
//
//        @Override
//        public boolean onDoubleTap(MotionEvent point) {
//            if (mLastSelectedLocation != null && locationMarker.getVisibility() == View.VISIBLE) {
//                /**
//                 * 注：以下两句话的顺序不可改变
//                 */
//                mMapView.centerAt(mLastSelectedLocation, true);
//                requestLocation(mLastSelectedLocation, mMapView.getSpatialReference(), new Callback1<String>() {
//                    @Override
//                    public void onCallback(String s) {
//                        showCallout(s, null, calloutSureButtonClickListener);
//                    }
//                });
//            }
//            mShouldRequestNewAddress = true;
//            hasZoomBefore = true;
//            return super.onDoubleTap(point);
//        }
//
//
//        @Override
//        public boolean onLongPressUp(MotionEvent point) {
////            handleTap(point);
//
//            super.onLongPressUp(point);
//            return true;
//        }

        @Override
        public boolean onSingleTap(final MotionEvent e) {
//            initGLayer();
//            hideBottomSheet();
            handleTap(e);
            return true;
        }


        /**
         * 点击地图后查询设施
         * @param e
         */
        private void query(MotionEvent e) {
            mComponentQueryResult.clear();
            btn_next.setVisibility(View.GONE);
            btn_prev.setVisibility(View.GONE);
            currIndex = 0;
            final Point point = mMapView.toMapPoint(e.getX(), e.getY());
            final List<LayerInfo> layerInfoList = new ArrayList<>();
            for (String url : LayerUrlConstant.newComponentUrls) {
                LayerInfo layerInfo = new LayerInfo();
                layerInfo.setUrl(url);
                layerInfoList.add(layerInfo);
            }
            Geometry geometry = GeometryEngine.buffer(point, mMapView.getSpatialReference(), 40 * mMapView.getResolution(), null);
            ComponentService componentMaintenanceService = new ComponentService(getActivity().getApplicationContext());

            if(true){
                componentMaintenanceService.queryPrimaryComponents(geometry, new Callback2<List<Component>>() {
                    @Override
                    public void onSuccess(List<Component> components) {
                        mComponentQueryResult = new ArrayList<Component>();
                        mComponentQueryResult.addAll(components);
                        if(ListUtil.isEmpty(mComponentQueryResult)){
                            initGLayer();
                            return;
                        }
                        showComponentsOnBottomSheet(mComponentQueryResult);
                    }

                    @Override
                    public void onFail(Exception error) {

                    }
                });
                return;
            }

            final String oldLayerUrl = LayerUrlConstant.getLayerUrlByLayerName(LayerUrlConstant.getLayerNameByNewLayerUrl(currComponentUrl));
            componentMaintenanceService.queryComponents(geometry, oldLayerUrl, currComponentUrl, new Callback2<List<QueryFeatureSet>>() {
                @Override
                public void onSuccess(List<QueryFeatureSet> queryFeatureSetList) {
                    if (ListUtil.isEmpty(queryFeatureSetList)) {
                        return;
                    }
                    mComponentQueryResult = new ArrayList<Component>();
                    for(QueryFeatureSet queryFeatureSet : queryFeatureSetList){
                        FeatureSet featureSet = queryFeatureSet.getFeatureSet();
                        Graphic[] graphics = featureSet.getGraphics();
                        if (graphics == null
                                || graphics.length <= 0) {
                            continue;
                        }

                        for (Graphic graphic : graphics) {
                            Component component = new Component();
                            component.setLayerUrl(queryFeatureSet.getLayerUrl());
                            component.setLayerName(queryFeatureSet.getLayerName());
                            component.setDisplayFieldName(featureSet.getDisplayFieldName());
//                            component.setFieldAlias(featureSet.getFieldAliases());
//                            component.setFields(featureSet.getFields());
                            component.setGraphic(graphic);
                            Object o = graphic.getAttributes().get(featureSet.getObjectIdFieldName());
                            if (o != null && o instanceof Integer) {
                                component.setObjectId((Integer) o); //按照道理objectId一定是integer的
                            }
                            mComponentQueryResult.add(component);
                        }
                    }
                    if(ListUtil.isEmpty(mComponentQueryResult)){
                        initGLayer();
                        return;
                    }
                    showComponentsOnBottomSheet(mComponentQueryResult);
                    /*componentService.queryUpStreamAndDownStreams(oldLayerUrl, mComponentQueryResult)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<List<Component>>() {
                                @Override
                                public void onCompleted() {
                                    if (pd != null) {
                                        pd.dismiss();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    if (pd != null) {
                                        pd.dismiss();
                                    }
                                    showComponentsOnBottomSheet(mComponentQueryResult);
                                }

                                @Override
                                public void onNext(List<Component> components) {
                                    mComponentQueryResult = components;
                                    showComponentsOnBottomSheet(mComponentQueryResult);
                                }
                            });*/
//                    showComponentsOnBottomSheet(mComponentQueryResult);
                }

                @Override
                public void onFail(Exception error) {

                }
            });
            /*componentMaintenanceService.queryComponents(geometry, currComponentUrl, new Callback2<QueryFeatureSet>() {
                @Override
                public void onSuccess(QueryFeatureSet queryFeatureSet) {
                    if (queryFeatureSet == null) {
                        return;
                    }
                    FeatureSet featureSet = queryFeatureSet.getFeatureSet();
                    Graphic[] graphics = featureSet.getGraphics();
                    if (graphics == null
                            || graphics.length <= 0) {
                        return;
                    }
                    mComponentQueryResult = new ArrayList<Component>();
                    for (Graphic graphic : graphics) {
                        Component component = new Component();
                        component.setLayerUrl(queryFeatureSet.getLayerUrl());
                        component.setLayerName(queryFeatureSet.getLayerName());
                        component.setDisplayFieldName(featureSet.getDisplayFieldName());
                        component.setFieldAlias(featureSet.getFieldAliases());
                        component.setFields(featureSet.getFields());
                        component.setGraphic(graphic);
                        Object o = graphic.getAttributes().get(featureSet.getObjectIdFieldName());
                        if (o != null && o instanceof Integer) {
                            component.setObjectId((Integer) o); //按照道理objectId一定是integer的
                        }
                        mComponentQueryResult.add(component);
                    }

                    showComponentsOnBottomSheet(mComponentQueryResult);
                }

                @Override
                public void onFail(Exception error) {

                }
            });*/

        }

        private void identify(MotionEvent e) {
            /*final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("查询周围部件中...");
            progressDialog.show();*/
            Point point = mMapView.toMapPoint(e.getX(), e.getY());
            LayerInfo layerInfo = new LayerInfo();
            layerInfo.setUrl(LayerUrlConstant.mapServerUrl);
            ArrayList<LayerInfo> childs = new ArrayList<>();
            LayerInfo child1 = new LayerInfo();
            child1.setLayerId(0);
            childs.add(child1);
            LayerInfo child2 = new LayerInfo();
            child2.setLayerId(1);
            childs.add(child2);
            LayerInfo child3 = new LayerInfo();
            child3.setLayerId(2);
            childs.add(child3);
            LayerInfo child4 = new LayerInfo();
            child4.setLayerId(3);
            childs.add(child4);
            LayerInfo child5 = new LayerInfo();
            child5.setLayerId(4);
            childs.add(child5);
            LayerInfo child6 = new LayerInfo();
            child6.setLayerId(5);
            childs.add(child6);
            LayerInfo child7 = new LayerInfo();
            child7.setLayerId(6);
            childs.add(child7);
            LayerInfo child8 = new LayerInfo();
            child8.setLayerId(7);
            childs.add(child8);
            layerInfo.setChildLayer(childs);
            List<LayerInfo> layerInfoList = new ArrayList<>();
            layerInfoList.add(layerInfo);
            IIdentifyService identifyService = IdentifyServiceFactory.provideLayerService();
            identifyService.selectedFeature(getActivity(), mMapView,
                    layerInfoList, point, 25, new Callback2<AMFindResult[]>() {
                        @Override
                        public void onSuccess(final AMFindResult[] amFindResults) {
//                            progressDialog.dismiss();

                            List<AMFindResult> findResults = new ArrayList<AMFindResult>();
                            if (amFindResults != null && amFindResults.length >= 1) {
                                //过滤掉不是点的部件
                                for (AMFindResult findResult : amFindResults) {
//                                    if (findResult.getGeometry().getType() == Geometry.Type.POINT) {
                                    findResults.add(findResult);
//                                    }
                                }
                            }

                            if (findResults.size() >= 1) {
                                AMFindResult findResult = findResults.get(0);
                                initGLayer();
                                drawGeometry(findResults.get(0).getGeometry(), mGLayer, true, true);
                                Component component = new Component();
                                component.setLayerUrl(LayerUrlConstant.getNewLayerUrlByLayerName(findResult.getLayerName()));
                                component.setLayerName(findResult.getLayerName());
                                component.setDisplayFieldName(findResult.getDisplayFieldName());
                                component.setGraphic(new Graphic(findResult.getGeometry(), null, findResult.getAttributes()));
                                loadCompleteData(component);
                            }
                        }

                        @Override
                        public void onFail(Exception error) {
                        }
                    });
        }

        /***
         * Handle a tap on the map (or the end of a magnifier long-press event).
         *
         * @param e The point that was tapped.
         */
        private void handleTap(final MotionEvent e) {
            if (locationMarker != null) {
                locationMarker.setVisibility(View.GONE);
            }
            if (mMapView.getCallout().isShowing()) {
                mMapView.getCallout().hide();
            }
            int visibility = map_bottom_sheet.getVisibility();
            initGLayer();
            hideBottomSheet();
            if (visibility == View.VISIBLE) {
                return;
            }
            double scale = mMapView.getScale();
            if(scale < LayerUrlConstant.MIN_QUERY_SCALE){
                final Point point = mMapView.toMapPoint(e.getX(), e.getY());
                AddComponentFragment2.this.query(point.getX(), point.getY());
//                query(e);
            }


        }

    }


    private void setSearchFacilityListener() {
        if(editModeSelectLocationTouchListener == null) {
            editModeSelectLocationTouchListener = new SelectLocationTouchListener(getActivity(),
                    mMapView, locationMarker, false, null) {
                @Override
                public boolean onDragPointerUp(MotionEvent from, MotionEvent to) {
                    if (locationButton != null && locationButton.ifLocating()) {
                        locationButton.setStateNormal();
                    }
//                if(map_bottom_sheet.getVisibility() != View.VISIBLE){
//                    if (!mMapView.getCallout().isShowing()) {
//                        mMapView.getCallout().show();
//                    }
//                    if(locationMarker.getVisibility() != View.VISIBLE){
//                        locationMarker.setVisibility(View.VISIBLE);
//                    }
//                }

                    return super.onDragPointerMove(from, to);
                }

                @Override
                public boolean onSingleTap(MotionEvent point) {
//                if (!mMapView.getCallout().isShowing()) {
//                    mMapView.getCallout().show();
//                }
                    locationMarker.setVisibility(View.VISIBLE);
                    initGLayer();
                    hideBottomSheet();
                    return true;
                }
            };
        }

        if(editModeCalloutSureButtonClickListener == null) {
            //查询设施模式下，点击callout中的确定按钮时的回调
            editModeCalloutSureButtonClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if(locationMarker != null){
                        locationMarker.setVisibility(View.GONE);
                    }*/
                    double scale = mMapView.getScale();
                    if (scale > LayerUrlConstant.MIN_QUERY_SCALE) {
                        ToastUtil.shortToast(getContext(), "请先放大到可以看到设施的级别");
                        return;
                    }

                    LocationInfo locationInfo = editModeSelectLocationTouchListener.getLoationInfo();
                    if (locationInfo.getDetailAddress() == null && locationInfo.getPoint() == null) {
                        ToastUtil.shortToast(getActivity(), "请重新选择位置");
                        return;
                    }

                    double x = locationInfo.getPoint().getX();
                    double y = locationInfo.getPoint().getY();

                    if (scale < LayerUrlConstant.MIN_QUERY_SCALE) {
                        query(x, y);
                    }


                }
            };
        }

        /**
         * 气泡中“确定”按钮点击事件
         */
        editModeSelectLocationTouchListener.setCalloutSureButtonClickListener(editModeCalloutSureButtonClickListener);
        mMapView.setOnTouchListener(editModeSelectLocationTouchListener);
    }

    /**
     * 点击地图后查询设施
     *
     * @param x
     * @param y
     */
    private void query(double x, double y) {
        pd = new ProgressDialog(getContext());
        pd.setMessage("正在查询设施...");
        pd.show();
        mComponentQueryResult.clear();
        btn_next.setVisibility(View.GONE);
        btn_prev.setVisibility(View.GONE);
        currIndex = 0;
//        final Point point = mMapView.toMapPoint(x, y);
        final Point point = new Point(x, y);
        final List<LayerInfo> layerInfoList = new ArrayList<>();
        for (String url : LayerUrlConstant.newComponentUrls) {
            LayerInfo layerInfo = new LayerInfo();
            layerInfo.setUrl(url);
            layerInfoList.add(layerInfo);
        }
        Geometry geometry = GeometryEngine.buffer(point, mMapView.getSpatialReference(), 40 * mMapView.getResolution(), null);

        initComponentService();
        if(true){
            componentService.queryPrimaryComponents(geometry, new Callback2<List<Component>>() {
                @Override
                public void onSuccess(List<Component> components) {
                    mComponentQueryResult = new ArrayList<Component>();
                    mComponentQueryResult.addAll(components);
                    if (pd != null) {
                        pd.dismiss();
                    }
                    if (ListUtil.isEmpty(mComponentQueryResult)) {
                        initGLayer();
                        ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                        return;
                    }
                    showComponentsOnBottomSheet(mComponentQueryResult);
                }

                @Override
                public void onFail(Exception error) {
                    if (pd != null) {
                        pd.dismiss();
                    }
                }
            });
            return;
        }
        final String oldLayerUrl = LayerUrlConstant.getLayerUrlByLayerName(LayerUrlConstant.getLayerNameByNewLayerUrl(currComponentUrl));
        componentService.queryComponents(geometry, oldLayerUrl, currComponentUrl, new Callback2<List<QueryFeatureSet>>() {
            @Override
            public void onSuccess(List<QueryFeatureSet> queryFeatureSetList) {
                if (ListUtil.isEmpty(queryFeatureSetList)) {
                    return;
                }
                mComponentQueryResult = new ArrayList<Component>();
                for (QueryFeatureSet queryFeatureSet : queryFeatureSetList) {
                    FeatureSet featureSet = queryFeatureSet.getFeatureSet();
                    Graphic[] graphics = featureSet.getGraphics();
                    if (graphics == null
                            || graphics.length <= 0) {
                        continue;
                    }

                    for (Graphic graphic : graphics) {
                        Component component = new Component();
                        component.setLayerUrl(queryFeatureSet.getLayerUrl());
                        component.setLayerName(queryFeatureSet.getLayerName());
                        component.setDisplayFieldName(featureSet.getDisplayFieldName());
//                        component.setFieldAlias(featureSet.getFieldAliases());
//                        component.setFields(featureSet.getFields());
                        component.setGraphic(graphic);
                        Object o = graphic.getAttributes().get(featureSet.getObjectIdFieldName());
                        if (o != null && o instanceof Integer) {
                            component.setObjectId((Integer) o); //按照道理objectId一定是integer的
                        }
                        mComponentQueryResult.add(component);
                    }
                }

                if (ListUtil.isEmpty(mComponentQueryResult)) {
                    initGLayer();
                    if (pd != null) {
                        pd.dismiss();
                    }
                    ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                    return;
                }
                if (pd != null) {
                    pd.dismiss();
                }
                showComponentsOnBottomSheet(mComponentQueryResult);
                /*componentService.queryUpStreamAndDownStreams(oldLayerUrl, mComponentQueryResult)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<Component>>() {
                            @Override
                            public void onCompleted() {
                                if (pd != null) {
                                    pd.dismiss();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (pd != null) {
                                    pd.dismiss();
                                }
                                showComponentsOnBottomSheet(mComponentQueryResult);
                            }

                            @Override
                            public void onNext(List<Component> components) {
                                mComponentQueryResult = components;
                                showComponentsOnBottomSheet(mComponentQueryResult);
                            }
                        });*/

            }

            @Override
            public void onFail(Exception error) {
                if (pd != null) {
                    pd.dismiss();
                }
                ToastUtil.shortToast(getContext(), "该地点未查询到设施");
            }
        });

    }

    @NonNull
    private void initComponentService() {
        if (componentService == null) {
            componentService = new ComponentService(getActivity().getApplicationContext());
        }
    }

    GraphicsLayer mGLayer = null;
    Graphic graphic = null;

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
                symbol = new SimpleLineSymbol(Color.RED, 7);
                break;
            case POINT:
//                symbol = new SimpleMarkerSymbol(Color.RED, 25, SimpleMarkerSymbol.STYLE.CIRCLE);
                symbol = getPointSymbol();
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
            if (geometry.getType() == Geometry.Type.POLYLINE) { //如果是线，那么这个就是要传递给服务端的结果
                this.graphic = graphic;
            }
        }

        if (ifCenter) {
            mMapView.centerAt(GeometryUtil.getGeometryCenter(geometry), true);
        }
    }


    /**
     * 在地图上画图形
     *
     * @param graphicsLayer
     * @param ifRemoveAll   在添加前是否移除掉所有
     * @param geometry
     */
    private int drawGeometry(Geometry geometry, Symbol symbol, GraphicsLayer graphicsLayer, boolean ifRemoveAll, boolean ifCenter) {

        if (graphicsLayer == null) {
            return -1;
        }

        if (ifRemoveAll) {
            graphicsLayer.removeAll();
        }

        if (ifCenter) {
            mMapView.centerAt(GeometryUtil.getGeometryCenter(geometry), true);
        }

        if (symbol != null) {
            Graphic graphic = new Graphic(geometry, symbol);
            if (geometry.getType() == Geometry.Type.POLYLINE) { //如果是线，那么这个就是要传递给服务端的结果
                this.graphic = graphic;
            }
            return graphicsLayer.addGraphic(graphic);
        }
        return -1;

    }

    /**
     * 在地图上画图形
     *
     * @param graphicsLayer
     * @param ifRemoveAll   在添加前是否移除掉所有
     * @param geometry
     */
    private void drawGeometry(Geometry geometry, GraphicsLayer graphicsLayer, Symbol designatedSymbol, boolean ifRemoveAll, boolean ifCenter) {

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
            Graphic graphic = new Graphic(geometry, symbol);
            graphicsLayer.addGraphic(graphic);
            if (geometry.getType() == Geometry.Type.POLYLINE) { //如果是线，那么这个就是要传递给服务端的结果
                this.graphic = graphic;
            }
        }

        if (ifCenter) {
            mMapView.centerAt(GeometryUtil.getGeometryCenter(geometry), true);
        }
    }

    @NonNull
    private Symbol getPointSymbol() {
        Drawable drawable = ResourcesCompat.getDrawable(getActivity().getResources(), com.augurit.agmobile.patrolcore.R.mipmap.ic_select_location, null);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(getContext(), drawable);// xjx 改为兼容api19的方式获取drawable
        pictureMarkerSymbol.setOffsetY(16);
        return pictureMarkerSymbol;
    }

    private void onChangedBottomSheetState(int state) {
        if (state == STATE_EXPANDED) {
            //BottomSheet展开时显示设施详情
            if (component_intro.getVisibility() == View.VISIBLE) {
                showBottomSheetContent(component_detail_container.getId());
                showDetail();
            }

        } else if (state == STATE_COLLAPSED) {
            //BottomSheet展开时显示设施简要信息
            showBottomSheetContent(component_intro.getId());

        }
    }

    private void showBottomSheetContent(int viewId) {
        if (viewId == component_intro.getId()) {
            component_intro.setVisibility(View.VISIBLE);
            component_detail_container.setVisibility(View.GONE);
        } else if (viewId == component_detail_container.getId()) {
            component_intro.setVisibility(View.GONE);
            component_detail_container.setVisibility(View.VISIBLE);
        }
    }


    private void hideBottomSheet() {
        map_bottom_sheet.setVisibility(View.GONE);
    }

    private void showComponentsOnBottomSheet(List<Component> componentQueryResult) {
        if (componentQueryResult.size() > 1) {
            btn_next.setVisibility(View.VISIBLE);
        }
//        initGLayer();
//        drawGeometry(componentQueryResult.get(0).getGraphic().getGeometry(), mGLayer, true, true);
        if (mMapView.getCallout().isShowing()) {
            mMapView.getCallout().animatedHide();
        }

        //隐藏marker
        locationMarker.setVisibility(View.GONE);
        showBottomSheet(mComponentQueryResult.get(0));

    }

    /**
     * 加载设施信息，显示中底部BottomSheet中
     */
    @Deprecated
    private void showBottomSheet2(final Component component) {
        initGLayer();
        //绘制下游
        if (!ListUtil.isEmpty(component.getDownStream())) {
            drawDownStream(component, component.getEmissionRepresentativeColor(getActivity()));
        }
        //绘制上游
        if (!ListUtil.isEmpty(component.getUpStream())) {
            drawUpStream(component, component.getEmissionRepresentativeColor(getActivity()));
        }

        drawGeometry(component.getGraphic().getGeometry(), mGLayer, false, true);

        String errorInfo = null;
        Object oErrorInfo = component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ERROR_INFO);

        if (oErrorInfo != null) {
            errorInfo = oErrorInfo.toString();
        }

        TextView titleTv = (TextView) map_bottom_sheet.findViewById(R.id.title);
        TextView dateTv = (TextView) map_bottom_sheet.findViewById(R.id.date);
        TextView sortTv = (TextView) map_bottom_sheet.findViewById(R.id.sort);
        TextView subtypeTv = (TextView) map_bottom_sheet.findViewById(R.id.subtype);
        TextView field1Tv = (TextView) map_bottom_sheet.findViewById(R.id.field1);
        TextView field2Tv = (TextView) map_bottom_sheet.findViewById(R.id.field2);
        TextView field3Tv = (TextView) map_bottom_sheet.findViewById(R.id.field3);
        TextView addrTv = (TextView) map_bottom_sheet.findViewById(R.id.addr);
//        addrTv.setVisibility(View.GONE);
        TextView tv_errorinfo = (TextView) map_bottom_sheet.findViewById(R.id.tv_errorinfo);

        String name = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.NAME));

        String type = LayerUrlConstant.getLayerNameByUnknownLayerUrl(component.getLayerUrl());

        String sort = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SORT));

        String subtype = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SUBTYPE));

        String title = StringUtil.getNotNullString(name, "") + "  " + StringUtil.getNotNullString(type, "");
        titleTv.setText(title);

        String date = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.UPDATEDATE));
        String formatDate = "";
        try {
            formatDate = TimeUtil.getStringTimeYMD(new Date(Long.valueOf(date)));
        } catch (Exception e) {

        }
        dateTv.setText(StringUtil.getNotNullString(formatDate, ""));

        sortTv.setText(StringUtil.getNotNullString(sort, ""));

        subtypeTv.setText(StringUtil.getNotNullString(subtype, ""));

        String listFields = ComponentFieldKeyConstant.getListFieldsByLayerName(component.getLayerName());
        String[] listFieldArray = listFields.split(",");
        if (!StringUtil.isEmpty(listFields)
                && listFieldArray.length == 3) {
            String field1_1 = listFieldArray[0].split(":")[0];
            String field1_2 = listFieldArray[0].split(":")[1];
            String field1 = String.valueOf(component.getGraphic().getAttributes().get(field1_2));
            field1Tv.setText(field1_1 + "：" + StringUtil.getNotNullString(field1, ""));

            String field2_1 = listFieldArray[1].split(":")[0];
            String field2_2 = listFieldArray[1].split(":")[1];
            String field2 = String.valueOf(component.getGraphic().getAttributes().get(field2_2));
            field2Tv.setText(field2_1 + "：" + StringUtil.getNotNullString(field2, ""));

            String field3_1 = listFieldArray[2].split(":")[0];
            String field3_2 = listFieldArray[2].split(":")[1];
            String field3 = String.valueOf(component.getGraphic().getAttributes().get(field3_2));
            field3Tv.setText(field3_1 + "：" + StringUtil.getNotNullString(field3, ""));
        }

        String address = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ADDR));
        addrTv.setText(StringUtil.getNotNullString(address, ""));


        if (errorInfo == null) {
            tv_errorinfo.setVisibility(View.GONE);
        } else {
            tv_errorinfo.setVisibility(View.VISIBLE);
            tv_errorinfo.setText(errorInfo);
        }
        showBottomSheetContent(component_intro.getId());
        map_bottom_sheet.setVisibility(View.VISIBLE);
        mBehavior.setState(STATE_COLLAPSED);
        loadCompleteDataAsync(component);
    }

    /**
     * 加载设施信息，显示中底部BottomSheet中
     */
    private void showBottomSheet(final Component component) {
        initGLayer();
        //绘制下游
        if (!ListUtil.isEmpty(component.getDownStream())) {
            drawDownStream(component, component.getEmissionRepresentativeColor(getActivity()));
        }
        //绘制上游
        if (!ListUtil.isEmpty(component.getUpStream())) {
            drawUpStream(component, component.getEmissionRepresentativeColor(getActivity()));
        }

        drawGeometry(component.getGraphic().getGeometry(), mGLayer, false, true);

        String errorInfo = null;
        Object oErrorInfo = component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ERROR_INFO);

        if (oErrorInfo != null) {
            errorInfo = oErrorInfo.toString();
        }

        TextView titleTv = (TextView) map_bottom_sheet.findViewById(R.id.title);
        TextView dateTv = (TextView) map_bottom_sheet.findViewById(R.id.date);
        TextView sortTv = (TextView) map_bottom_sheet.findViewById(R.id.sort);
        TextView subtypeTv = (TextView) map_bottom_sheet.findViewById(subtype);
        TextView field1Tv = (TextView) map_bottom_sheet.findViewById(R.id.field1);
        TextView field2Tv = (TextView) map_bottom_sheet.findViewById(R.id.field2);
        TextView field3Tv = (TextView) map_bottom_sheet.findViewById(R.id.field3);
        TextView addrTv = (TextView) map_bottom_sheet.findViewById(R.id.addr);
//        addrTv.setVisibility(View.GONE);
        TextView tv_errorinfo = (TextView) map_bottom_sheet.findViewById(R.id.tv_errorinfo);
        TextView tv_parent_org_name = (TextView) map_bottom_sheet.findViewById(R.id.tv_parent_org_name);

        String name = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.NAME));

        String type = LayerUrlConstant.getLayerNameByUnknownLayerUrl(component.getLayerUrl());


        String subtype = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SUBTYPE));
        String usid = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.USID));

        String title = StringUtil.getNotNullString(type, "") + "(" + usid + ")";
        titleTv.setText(title);

        String date = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.UPDATEDATE));
        String formatDate = "";
        try {
            formatDate = TimeUtil.getStringTimeYMD(new Date(Long.valueOf(date)));
        } catch (Exception e) {

        }
        dateTv.setText(StringUtil.getNotNullString(formatDate, ""));

        String sort = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SORT));

        int color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);

        if (sort.contains("雨污合流")) {
            color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);
        } else if (sort.contains("雨水")) {
            color = ResourcesCompat.getColor(getResources(), R.color.progress_line_green, null);
        } else if (sort.contains("污水")) {
            color = ResourcesCompat.getColor(getResources(), R.color.agmobile_red, null);
        }
        sortTv.setTextColor(color);
//
//        if (sort.contains("雨污合流")) {
//            sortTv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null));
//        } else if (sort.contains("雨水")) {
//            sortTv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.progress_line_green, null));
//        } else if (sort.contains("污水")) {
//            sortTv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.agmobile_red, null));
//        }

        sortTv.setText(StringUtil.getNotNullString(sort, ""));


        String listFields = ComponentFieldKeyConstant.getListFieldsByLayerName(component.getLayerName());
        String[] listFieldArray = listFields.split(",");
        if (!StringUtil.isEmpty(listFields)
                && listFieldArray.length == 3) {
            String field1_1 = listFieldArray[0].split(":")[0];
            String field1_2 = listFieldArray[0].split(":")[1];
            String field1 = String.valueOf(component.getGraphic().getAttributes().get(field1_2));
            field1Tv.setText(field1_1 + "：" + StringUtil.getNotNullString(field1, ""));

            String field2_1 = listFieldArray[1].split(":")[0];
            String field2_2 = listFieldArray[1].split(":")[1];
            String field2 = String.valueOf(component.getGraphic().getAttributes().get(field2_2));
            field2Tv.setText(field2_1 + "：" + StringUtil.getNotNullString(field2, ""));

            String field3_1 = listFieldArray[2].split(":")[0];
            String field3_2 = listFieldArray[2].split(":")[1];
            String field3 = String.valueOf(component.getGraphic().getAttributes().get(field3_2));
            field3Tv.setText(field3_1 + "：" + StringUtil.getNotNullString(field3, ""));
        }

        /**
         * 如果是雨水口，显示特性：方形
         */
        if (component.getLayerName().equals("雨水口")) {
            String feature = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.FEATURE));
            sortTv.setText(StringUtil.getNotNullString(feature, ""));
        }
        if ("雨水口".equals(type)) {
            String style = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.STYLE));
            subtypeTv.setText(StringUtil.getNotNullString(style, ""));
            subtypeTv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.dust_grey, null));
        } else {
            subtypeTv.setText(StringUtil.getNotNullString(subtype, ""));
        }

        if ("雨水口".equals(type)) {
            field3Tv.setVisibility(View.GONE);
        } else {
            field3Tv.setVisibility(View.VISIBLE);
        }

        /**
         * 修改属性三
         */
        String field3 = "";
        if (component.getLayerName().equals("窨井")) {
            field3 = "井盖材质: " + String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.MATERIAL));
        } else if (component.getLayerName().equals("排放口")) {
            field3 = "排放去向: " + String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.RIVER));
        }
        field3Tv.setText(field3);


        final String address = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ADDR));
        addrTv.setText("设施位置" + "：" + StringUtil.getNotNullString(address, ""));


        if (errorInfo == null) {
            tv_errorinfo.setVisibility(View.GONE);
        } else {
            tv_errorinfo.setVisibility(View.VISIBLE);
            tv_errorinfo.setText(errorInfo + "?");
        }


        String parentOrg = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.OWNERDEPT));
        tv_parent_org_name.setText("权属单位：" + StringUtil.getNotNullString(parentOrg, ""));

        showBottomSheetContent(component_intro.getId());
        map_bottom_sheet.setVisibility(View.VISIBLE);
        mBehavior.setState(STATE_COLLAPSED);
        loadCompleteDataAsync(component);
    }

    private void drawUpStream(Component component, int color) {
        List<Component> upStreams = component.getUpStream();
        Point componentCenter = GeometryUtil.getGeometryCenter(component.getGraphic().getGeometry());
        for (Component upStream : upStreams) {
            Polyline polyline = new Polyline();
            Point geometryCenter = GeometryUtil.getGeometryCenter(upStream.getGraphic().getGeometry());
            if (geometryCenter != null) {
                polyline.startPath(geometryCenter);
                polyline.lineTo(GeometryUtil.getGeometryCenter(component.getGraphic().getGeometry()));
                drawGeometry(polyline, mGLayer, new SimpleLineSymbol(color, 5), false, false);
                //如果上游，那么此时箭头方向是指向当前点击的部件
                //上游点
                Point point = mMapView.toScreenPoint(geometryCenter);
                //当前点
                Point point2 = mMapView.toScreenPoint(componentCenter);
                double angle = GeometryUtil.getAngle(point.getX(), point.getY(), point2.getX(), point2.getY());
                drawArrow(GeometryUtil.getGeometryCenter(polyline), angle, color,false);
            }
        }
    }

    private void drawDownStream(Component component, int color) {
        List<Component> downStreams = component.getDownStream();
        Point componentCenter = GeometryUtil.getGeometryCenter(component.getGraphic().getGeometry());
        for (Component downStream : downStreams) {
            Polyline polyline = new Polyline();
            Point geometryCenter = GeometryUtil.getGeometryCenter(downStream.getGraphic().getGeometry());
            if (geometryCenter != null) {
                polyline.startPath(geometryCenter);
                polyline.lineTo(GeometryUtil.getGeometryCenter(component.getGraphic().getGeometry()));
                drawGeometry(polyline, mGLayer, new SimpleLineSymbol(color, 5), false, false);
                //如果下游，那么此时箭头方向是指向当前的下游部件
                //下游点
                Point point = mMapView.toScreenPoint(geometryCenter);
                //当前点
                Point point2 = mMapView.toScreenPoint(componentCenter);
                double angle = GeometryUtil.getAngle(point.getX(), point.getY(), point2.getX(), point2.getY());
                drawArrow(GeometryUtil.getGeometryCenter(polyline), angle,color, true);
            }
        }
    }

    /**
     * 绘制箭头，请确保调用前已经调用了{@link #initGLayer()}方法
     *
     * @param geometryCenter   箭头摆放的位置
     * @param ifDrawRightArrow 是否绘制的是右箭头，true绘制右箭头，false绘制左箭头
     */
    private void drawArrow(Point geometryCenter, double angle, int color,boolean ifDrawRightArrow) {

        PictureMarkerSymbol symbol = null;
        if (ifDrawRightArrow) {
            Drawable tintDrawable = DrawableUtil.tintDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_forward_red_24dp, null), ColorStateList.valueOf(color));
            symbol = new PictureMarkerSymbol(getActivity(), tintDrawable);
        } else {
            Drawable tintDrawable = DrawableUtil.tintDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_red_24dp, null), ColorStateList.valueOf(color));
            symbol = new PictureMarkerSymbol(getActivity(), tintDrawable);
        }
        symbol.setAngle((float) angle);
        Graphic graphic = new Graphic(geometryCenter, symbol);
        mGLayer.addGraphic(graphic);
    }

    private void showDetail() {
        if (tableItems == null
                || projectId == null) {
            return;
        }
        if (tableViewManager == null) {
//            component_detail_container.removeAllViews();
            //动态表单实例
            tableViewManager =
                    new TableViewManager(getActivity(), component_detail_container,
                            false, TableState.REEDITNG, tableItems,
                            photoList, projectId, null, null);
            tableViewManager.setUploadEditCallback(new Callback2() {
                @Override
                public void onSuccess(Object o) {
                    mBehavior.setState(STATE_COLLAPSED);
                    map_bottom_sheet.setVisibility(View.GONE);
                    if (locationMarker != null) {
                        locationMarker.setVisibility(View.GONE);
                    }
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().hide();
                    }
                }

                @Override
                public void onFail(Exception error) {

                }
            });

           // tableViewManager.changeSelectLocationItemView(new NoMapSelectLocationEditStateTableItemView2(getActivity()));

            tableViewManager.setOnMapItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBehavior != null) {
                        //       mBehavior.setState(STATE_COLLAPSED);
                        map_bottom_sheet.setVisibility(View.GONE);
                        //         mMapView.setScale(mMapView.getMinScale());
                        TableItem tableX = tableViewManager.getTableItemByField1("X");
                        TableItem tableY = tableViewManager.getTableItemByField1("Y");
                        if (tableX == null || tableY == null) {
                            /**
                             * 判断是否是线段
                             */
                            if (TableViewManager.geometry != null &&
                                    TableViewManager.geometry.getType() == Geometry.Type.POLYLINE) {
                                //如果是线段
                                editLine((Polyline) TableViewManager.geometry);
                                return;
                            } else {
                                return;
                            }

                        }


                        /**
                         * 如果x,y没有值，但是geometry变量有值，手动赋值，否则什么都不做
                         */
                        if (tableX.getValue() == null || tableY.getValue() == null) {
                            if (TableViewManager.geometry != null && TableViewManager.geometry.getType() == Geometry.Type.POINT) {
                                tableX.setValue(((Point) TableViewManager.geometry).getX() + "");
                                tableY.setValue(((Point) TableViewManager.geometry).getY() + "");
                            } else {
                                return;
                            }
                        }

                        try {
                            double x = Double.valueOf(tableX.getValue());
                            double y = Double.valueOf(tableY.getValue());
                            Point point = new Point(x, y);
                            mMapView.centerAt(point, true);
                            if (locationMarker != null) {
                                locationMarker.setVisibility(View.VISIBLE);
                                locationMarker.bounce();

                                SelectLocationTouchListener selectLocationTouchListener = new SelectLocationTouchListener(getActivity(), mMapView, locationMarker, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //隐藏callout
                                        if (mMapView.getCallout().isShowing()) {
                                            mMapView.getCallout().animatedHide();
                                        }

                                        //点击确定后，弹出底部弹框
                                        mBehavior.setState(STATE_EXPANDED);
                                        map_bottom_sheet.setVisibility(View.VISIBLE);
                                        showDetail();
                                        if (tableViewManager != null) {
                                            //将新的位置传递给TableViewManager
                                            TableViewManager.geometry = mLastSelectedLocation;
                                            EditText x = tableViewManager.getEditTextViewByField1Type("X");
                                            TableItem tableX = tableViewManager.getTableItemByField1("X");
                                            if (x != null && mLastSelectedLocation != null) {
                                                x.setText(mLastSelectedLocation.getX() + "");
                                            }
                                            /**
                                             * 这里对tableItem重新赋值的原因是：在{@link AddComponentFragment2#showDetail()}方法中是通过tableItem来进行获取到当前部件的位置的，当修改过一次后，
                                             * 应该重新更新位置，这样下次定位图标才会放置在上次选择的位置
                                             */
                                            if (tableX != null) {
                                                tableX.setValue(mLastSelectedLocation.getX() + "");
                                            }

                                            EditText y = tableViewManager.getEditTextViewByField1Type("Y");
                                            TableItem tableY = tableViewManager.getTableItemByField1("Y");
                                            if (y != null && mLastSelectedLocation != null) {
                                                y.setText(mLastSelectedLocation.getY() + "");
                                            }
                                            if (tableY != null && mLastSelectedLocation != null) {
                                                tableY.setValue(mLastSelectedLocation.getY() + "");
                                            }
                                        }

                                        //在地图上重新画点
                                        Drawable drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.mipmap.common_ic_location_orange, null);
                                        Symbol symbol = new PictureMarkerSymbol(getContext(), drawable);
                                        drawGeometry(mLastSelectedLocation, symbol, mGLayer, true, true);

                                        //恢复点击事件
                                        mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                                        //隐藏marker
                                        locationMarker.setVisibility(View.GONE);

                                    }
                                });

                                selectLocationTouchListener.registerLocationChangedListener(new SelectLocationTouchListener.OnSelectedLocationChangedListener() {
                                    @Override
                                    public void onLocationChanged(Point newLocation) {
                                        mLastSelectedLocation = newLocation;
                                    }

                                    @Override
                                    public void onAddressChanged(DetailAddress detailAddress) {
                                        if (tableViewManager != null) {
                                            tableViewManager.setAddress(detailAddress.getDetailAddress());
                                        }
                                    }
                                });

                                mMapView.setOnTouchListener(selectLocationTouchListener);
                            }
                        } catch (NumberFormatException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            });
        }

    }

    /**
     * 编辑线段
     *
     * @param polyline
     */

    private void editLine(final Polyline polyline) {

        //移除掉原有的线段，重新画
        mGLayer.removeAll();
        final EditLineReEditStateMapListener editLineReEditStateMapListener = new EditLineReEditStateMapListener(getActivity(), mMapView, polyline, 0, ll_component_list) {
            @Override
            public boolean onDoubleTap(MotionEvent point) {

                Graphic currentGraphic = this.getCurrentGraphic();
                mGLayer.addGraphic(currentGraphic);
                this.clearAllGrapic();
                map_bottom_sheet.setVisibility(View.VISIBLE);
                mBehavior.setState(STATE_EXPANDED);
                showDetail();
                //恢复点击事件
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());

                return true;
            }
        };
        editLineReEditStateMapListener.setOnGraphicChangedListener(new OnGraphicChangedListener() {
            @Override
            public void onGraphicChanged(Graphic graphic) {

            }

            @Override
            public void onAddressChanged(DetailAddress detailAddress) {
                if (tableViewManager != null && detailAddress != null) {
                    tableViewManager.setAddress(detailAddress.getDetailAddress());
                }
            }


            @Override
            public void onGraphicClear() {

            }
        });
        mMapView.setOnTouchListener(editLineReEditStateMapListener);
    }


    /**
     * 加载设施详细信息，转换为TableViewManager所需的格式，即List<TableItem>
     * @param component
     */
    private void loadCompleteDataAsync(final Component component) {
        tableItems = null;
        projectId = null;
        tableViewManager = null;
        component_detail_container.removeAllViews();
        photoList.clear();
        final TableDataManager tableDataManager = new TableDataManager(getActivity().getApplicationContext());
        List<Project> projects = tableDataManager.getProjectFromDB();
        Project project = null;
        for (Project p : projects) {
            if (p.getName().equals(component.getLayerName())) {
                project = p;
            }
        }
        if (project == null) {
            ToastUtil.shortToast(getContext(), "加载详细信息失败！");
            return;
        }
        projectId = project.getId();
        String getFormStructureUrl = BaseInfoManager.getBaseServerUrl(getContext()) + "rest/report/rptform";
        tableDataManager.getTableItemsFromNet(project.getId(), getFormStructureUrl, new TableNetCallback() {
            @Override
            public void onSuccess(Object data) {
                TableItems tmp = (TableItems) data;
                if (tmp.getResult() != null) {
                    tableItems = new ArrayList<TableItem>();
                    tableItems.addAll(tmp.getResult());
                    //   tableDataManager.setTableItemsToDB(tableItems);
                    //缓存在数据库中
                    Realm realm = Realm.getDefaultInstance();
                    ProjectTable projectTable = new ProjectTable();
                    projectTable.setId(projectId);
                    realm.beginTransaction();
                    projectTable.setTableItems(new RealmList<TableItem>(tableItems.toArray(new TableItem[tableItems.size()])));
                    realm.commitTransaction();
                    tableDataManager.setProjectTableToDB(projectTable);


                    ArrayList<TableItem> completeTableItems = EditLayerService.getCompleteTableItem(component.getGraphic(), tableItems);


                    TableViewManager.isEditingFeatureLayer = true;
                    TableViewManager.graphic = component.getGraphic();
                    TableViewManager.geometry = component.getGraphic().getGeometry();
//                    TableViewManager.featueLayerUrl = component.getLayerUrl();
                    TableViewManager.featueLayerUrl = LayerUrlConstant.getNewLayerUrlByUnknownLayerUrl(component.getLayerUrl());
                    if (false) {
                        Intent intent = new Intent(getActivity(), ReEditTableActivity.class);
                        intent.putExtra("tableitems", completeTableItems);
                        intent.putExtra("projectId", projectId);
                        startActivity(intent);
                    } else {
                        queryAttachmentInfosAsync(component.getLayerUrl(), component.getGraphic(), completeTableItems, projectId);
                    }
                } else {
                }
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    private void loadCompleteData(final Component component) {
        pd = new ProgressDialog(getContext());
        pd.setMessage("正在加载详细信息...");
        pd.show();
        final TableDataManager tableDataManager = new TableDataManager(getActivity().getApplicationContext());
        List<Project> projects = tableDataManager.getProjectFromDB();
        Project project = null;
        for (Project p : projects) {
            if (p.getName().equals(component.getLayerName())) {
                project = p;
            }
        }
        if (project == null) {
            ToastUtil.shortToast(getContext(), "加载详细信息失败！");
            return;
        }
        final String projectId = project.getId();
        String getFormStructureUrl = BaseInfoManager.getBaseServerUrl(getContext()) + "rest/report/rptform";
        tableDataManager.getTableItemsFromNet(project.getId(), getFormStructureUrl, new TableNetCallback() {
            @Override
            public void onSuccess(Object data) {
                TableItems tmp = (TableItems) data;
                if (tmp.getResult() != null) {
                    tableItems = new ArrayList<TableItem>();
                    tableItems.addAll(tmp.getResult());
                    //   tableDataManager.setTableItemsToDB(tableItems);
                    //缓存在数据库中
                    Realm realm = Realm.getDefaultInstance();
                    ProjectTable projectTable = new ProjectTable();
                    projectTable.setId(projectId);
                    realm.beginTransaction();
                    projectTable.setTableItems(new RealmList<TableItem>(tableItems.toArray(new TableItem[tableItems.size()])));
                    realm.commitTransaction();
                    tableDataManager.setProjectTableToDB(projectTable);
                    ArrayList<TableItem> completeTableItems = EditLayerService.getCompleteTableItem(component.getGraphic(), tableItems);
                    TableViewManager.isEditingFeatureLayer = true;
                    TableViewManager.graphic = component.getGraphic();
                    TableViewManager.geometry = component.getGraphic().getGeometry();
//                    TableViewManager.featueLayerUrl = component.getLayerUrl();
                    TableViewManager.featueLayerUrl = LayerUrlConstant.getNewLayerUrlByUnknownLayerUrl(component.getLayerUrl());
                    if (false) {
                        Intent intent = new Intent(getActivity(), ReEditTableActivity.class);
                        intent.putExtra("tableitems", completeTableItems);
                        intent.putExtra("projectId", projectId);
                        startActivity(intent);
                    } else {
                        queryAttachmentInfos(component.getLayerUrl(), component.getGraphic(), completeTableItems, projectId);
                    }
                } else {
                }
//                pd.dismiss();
            }

            @Override
            public void onError(String msg) {
                pd.dismiss();
                ToastUtil.shortToast(getContext(), "加载详细信息失败!");
            }
        });
    }

    /**
     * 加载设施的附件
     * @param layerUrl
     * @param graphic
     * @param completeTableItems
     * @param projectId
     */
    private void queryAttachmentInfosAsync(String layerUrl, final Graphic graphic, final ArrayList<TableItem> completeTableItems, final String projectId) {
        final ArcGISFeatureLayer arcGISFeatureLayer = new ArcGISFeatureLayer(layerUrl, ArcGISFeatureLayer.MODE.SNAPSHOT);
        arcGISFeatureLayer.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                final int objectid = Integer.valueOf(graphic.getAttributes().get(arcGISFeatureLayer.getObjectIdField()).toString());
                FileService fileService = new FileService(getContext());
                fileService.getList(arcGISFeatureLayer.getName(), objectid + "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<FileResult>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onNext(List<FileResult> fileResults) {
                                if (ListUtil.isEmpty(fileResults)) {
                                    return;
                                }
                                Map<String, Integer> map = new HashMap<>();
                                for (FileResult fileResult : fileResults) {
                                    if (map.containsKey(fileResult.getAttachName())) {
                                        continue;
                                    }
                                    if (!fileResult.getType().contains("image")) {
                                        continue;
                                    }
                                    Photo photo = new Photo();
                                    photo.setPhotoPath(fileResult.getFilePath());
                                    photo.setField1("photo");
                                    photo.setHasBeenUp(1);
                                    photoList.add(photo);
                                    map.put(fileResult.getAttachName(), fileResult.getId());
                                }
                            }
                        });
            }
        });
    }

    private void queryAttachmentInfosAsync2(String layerUrl, final Graphic graphic, final ArrayList<TableItem> completeTableItems, final String projectId) {
        final ArcGISFeatureLayer arcGISFeatureLayer = new ArcGISFeatureLayer(layerUrl, ArcGISFeatureLayer.MODE.ONDEMAND);
        arcGISFeatureLayer.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (status == STATUS.INITIALIZED) {
                    final int objectid = Integer.valueOf(graphic.getAttributes().get(arcGISFeatureLayer.getObjectIdField()).toString());
                    arcGISFeatureLayer.queryAttachmentInfos(objectid, new CallbackListener<AttachmentInfo[]>() {
                        @Override
                        public void onCallback(AttachmentInfo[] attachmentInfos) {
//                            final ArrayList<Photo> photoList = new ArrayList<Photo>();
                            Intent intent = new Intent(getActivity(), ReEditTableActivity.class);
                            intent.putExtra("tableitems", completeTableItems);
                            intent.putExtra("projectId", projectId);
                            if (attachmentInfos == null
                                    || attachmentInfos.length == 0) {

                            } else {
                                Map<String, Long> map = new HashMap<>();
                                for (AttachmentInfo attachmentInfo : attachmentInfos) {
                                    if (map.containsKey(attachmentInfo.getName())) {
                                        continue;
                                    }
                                    if (!attachmentInfo.getContentType().contains("image")) {
                                        continue;
                                    }
                                    try {
                                        InputStream inputStream = arcGISFeatureLayer.retrieveAttachment(objectid,
                                                Integer.valueOf(String.valueOf(attachmentInfo.getId())));
                                        String tempFile = new FilePathUtil(getContext()).getSavePath() + "/images/" + attachmentInfo.getName() + ".jpg";
                                        File targetFile = new File(tempFile);
                                        if (!targetFile.getParentFile().exists()) {
                                            targetFile.getParentFile().mkdirs();
                                        }
                                        if (!targetFile.exists()) {
                                            targetFile.createNewFile();
                                        }
                                        AMFileUtil.saveInputStreamToFile(inputStream, targetFile);
                                        inputStream.close();
                                        Photo photo = new Photo();
                                        photo.setPhotoPath("file://" + tempFile);
                                        photo.setLocalPath(tempFile);
                                        photo.setField1("photo");
                                        photo.setHasBeenUp(1);
                                        photoList.add(photo);
                                        map.put(attachmentInfo.getName(), attachmentInfo.getId());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (!ListUtil.isEmpty(photoList)) {
                                    intent.putExtra("photos", photoList);
                                }
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    /*component_detail_container.removeAllViews();
                                    //动态表单实例
                                    TableViewManager tableViewManager =
                                            new TableViewManager(getActivity(), component_detail_container,
                                                    false, TableState.REEDITNG, tableItems,
                                                    photoList, projectId, null, null);*/
                                }
                            });

                        }

                        @Override
                        public void onError(Throwable throwable) {
                        }
                    });
                }
            }
        });
    }


    private void queryAttachmentInfos(String layerUrl, final Graphic graphic, final ArrayList<TableItem> completeTableItems, final String projectId) {
        final ArcGISFeatureLayer arcGISFeatureLayer = new ArcGISFeatureLayer(layerUrl, ArcGISFeatureLayer.MODE.ONDEMAND);
        arcGISFeatureLayer.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (status == STATUS.INITIALIZED) {
                    final int objectid = Integer.valueOf(graphic.getAttributes().get(arcGISFeatureLayer.getObjectIdField()).toString());
                    arcGISFeatureLayer.queryAttachmentInfos(objectid, new CallbackListener<AttachmentInfo[]>() {
                        @Override
                        public void onCallback(AttachmentInfo[] attachmentInfos) {
                            Intent intent = new Intent(getActivity(), ReEditTableActivity.class);
                            intent.putExtra("tableitems", completeTableItems);
                            intent.putExtra("projectId", projectId);
                            if (attachmentInfos == null
                                    || attachmentInfos.length == 0) {

                            } else {
                                ArrayList<Photo> photoList = new ArrayList<Photo>();
                                Map<String, Long> map = new HashMap<>();
                                for (AttachmentInfo attachmentInfo : attachmentInfos) {
                                    if (map.containsKey(attachmentInfo.getName())) {
                                        continue;
                                    }
                                    if (!attachmentInfo.getContentType().contains("image")) {
                                        continue;
                                    }
                                    try {
                                        InputStream inputStream = arcGISFeatureLayer.retrieveAttachment(objectid,
                                                Integer.valueOf(String.valueOf(attachmentInfo.getId())));
                                        String tempFile = new FilePathUtil(getContext()).getSavePath() + "/images/" + attachmentInfo.getName() + ".jpg";
                                        File targetFile = new File(tempFile);
                                        if (!targetFile.getParentFile().exists()) {
                                            targetFile.getParentFile().mkdirs();
                                        }
                                        if (!targetFile.exists()) {
                                            targetFile.createNewFile();
                                        }
//
                                        AMFileUtil.saveInputStreamToFile(inputStream, targetFile);
                                        inputStream.close();
//                                        outputStream.close();
                                        Photo photo = new Photo();
                                        photo.setPhotoPath("file://" + tempFile);
                                        photo.setLocalPath(tempFile);
                                        photo.setField1("photo");
                                        photo.setHasBeenUp(1);
                                        photoList.add(photo);
                                        map.put(attachmentInfo.getName(), attachmentInfo.getId());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (!ListUtil.isEmpty(photoList)) {
                                    intent.putExtra("photos", photoList);
                                }
                            }
                            pd.dismiss();
                            startActivity(intent);
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            pd.dismiss();
                            ToastUtil.shortToast(getContext(), "加载详细信息失败!");
                        }
                    });
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectComponent(SelectComponentEvent selectComponentEvent) {
        Component component = selectComponentEvent.getComponent();
        currComponentUrl = component.getLayerUrl();
        if (LayerUrlConstant.getIndexByUnknowsLayerUrl(currComponentUrl) != -1){
            layerAdapter.notifyDataSetChanged(LayerUrlConstant.getIndexByUnknowsLayerUrl(currComponentUrl));
        }
        showBottomSheet(component);
    }

    private void changeLayerUrlInitFailState(){
        if(LayerUrlConstant.ifLayerUrlInitSuccess()
                && loadLayersSuccess
                && ll_layer_url_init_fail != null){
            ll_layer_url_init_fail.setVisibility(View.GONE);
        }
        if((!LayerUrlConstant.ifLayerUrlInitSuccess()
                || !loadLayersSuccess)
                && ll_layer_url_init_fail != null){
            ll_layer_url_init_fail.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 网络切换时，若设施URL为null，会重新初始化设施URL，然后发送OnInitLayerUrlFinishEvent事件
     * @param onInitLayerUrlFinishEvent
     */
    @Subscribe
    public void onInitLayerUrlFinished(OnInitLayerUrlFinishEvent onInitLayerUrlFinishEvent){
        changeLayerUrlInitFailState();
        if(!loadLayersSuccess && layerPresenter != null){
            layerPresenter.loadLayer(new Callback2<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    loadLayersSuccess = true;
                    changeLayerUrlInitFailState();
                }

                @Override
                public void onFail(Exception error) {
                    loadLayersSuccess = false;
                    changeLayerUrlInitFailState();
                }
            });
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (tableViewManager != null) {
            tableViewManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.recycle();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /**
         * 退出时停止定位
         */
        if (mLocationManager != null) {
            mLocationManager.stopLocate();
        }

        LayersService.clearInstance();
    }
}
