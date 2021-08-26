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

package com.augurit.agmobile.gzpssb.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.addcomponent.LayerAdapter;
import com.augurit.agmobile.gzps.addcomponent.model.ComponentMap;
import com.augurit.agmobile.gzps.common.OnInitLayerUrlFinishEvent;
import com.augurit.agmobile.gzps.common.constant.LayerUrlConstant;
import com.augurit.agmobile.gzps.common.selectcomponent.SelectComponentFinishEvent2;
import com.augurit.agmobile.gzps.common.util.MapIconUtil;
import com.augurit.agmobile.gzps.componentmaintenance.ComponetListAdapter;
import com.augurit.agmobile.gzps.componentmaintenance.SelectComponentEvent;
import com.augurit.agmobile.gzps.componentmaintenance.model.QueryFeatureSet;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentFieldKeyConstant;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzpssb.bean.DoorNOBean;
import com.augurit.agmobile.gzpssb.bean.SewerageInfoBean;
import com.augurit.agmobile.gzpssb.common.legend.PSHImageLegendView;
import com.augurit.agmobile.gzpssb.common.view.TakePhotoTableItem3;
import com.augurit.agmobile.gzpssb.event.SelectComponentFinishEvent;
import com.augurit.agmobile.gzpssb.jhj.view.WellNewFacilityActivity;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.seweragewell.model.WellPhoto;
import com.augurit.agmobile.gzpssb.seweragewell.presenter.PSHWelllayerPresenter;
import com.augurit.agmobile.gzpssb.seweragewell.service.SewerageWellService;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.agmobile.mapengine.common.utils.FilePathUtil;
import com.augurit.agmobile.mapengine.common.utils.GeometryUtil;
import com.augurit.agmobile.mapengine.common.widget.scaleview.MapScaleView;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.service.LayersService;
import com.augurit.agmobile.mapengine.layermanage.view.ILayerView;
import com.augurit.agmobile.mapengine.legend.service.OnlineLegendService;
import com.augurit.agmobile.mapengine.legend.view.ILegendView;
import com.augurit.agmobile.patrolcore.common.SelectLocationTouchListener;
import com.augurit.agmobile.patrolcore.common.file.model.FileResult;
import com.augurit.agmobile.patrolcore.common.file.service.FileService;
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
import com.augurit.agmobile.patrolcore.editmap.widget.LocationMarker;
import com.augurit.agmobile.patrolcore.layer.service.EditLayerService;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerView2;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.agmobile.patrolcore.selectlocation.service.SelectLocationService;
import com.augurit.agmobile.patrolcore.selectlocation.util.PatrolLocationManager;
import com.augurit.agmobile.patrolcore.selectlocation.view.IDrawerController;
import com.augurit.agmobile.patrolcore.upload.view.ReEditTableActivity;
import com.augurit.am.cmpt.common.Callback1;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.cmpt.widget.bottomsheet.AnchorSheetBehavior;
import com.augurit.am.cmpt.widget.searchview.util.Util;
import com.augurit.am.fw.utils.AMFileUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
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

import static com.augurit.agmobile.gzps.R.id.subtype;
import static com.augurit.am.cmpt.widget.bottomsheet.IBottomSheetBehavior.STATE_COLLAPSED;

/**
 * 地图上进行选择已修改的部件进行标识（采用bottomsheet）
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.common.selectcomponent
 * @createTime 创建时间 ：17/11/6
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/11/6
 * @modifyMemo 修改备注：
 */

public class SewerageSelectWellFragment extends Fragment implements View.OnClickListener {

    private static final String KEY_MAP_STATE = "com.esri.MapState";

    private LocationMarker locationMarker;
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
    private View ll_layer_url_init_fail;

    private Context mContext;

    View mView;

    MapView mMapView;

    private ILayerView layerView;

    private PatrolLayerPresenter layerPresenter;
    private boolean loadLayersSuccess = true;

    List<ComponentMap> componentMapList = new ArrayList<>();

    boolean mClosingTheApp = false;

    String curComponentName = "";

    private TextView show_all_layer;
    private GridView gridView;
    private LayerAdapter layerAdapter;
    private String currComponentUrl = LayerUrlConstant.newComponentUrls[0];

    ProgressDialog pd;
    ViewGroup map_bottom_sheet;
    AnchorSheetBehavior mBehavior;
    private ComponetListAdapter componetListAdapter;
    private View component_intro;
    private View component_detail_ll;
    private ViewGroup component_detail_container;
    private View btn_upload;
    private ArrayList<TableItem> tableItems = null;
    private ArrayList<Photo> photoList = new ArrayList<>();
    private String projectId;
    private TableViewManager tableViewManager;
    private List<Component> mOrgComponentQueryResult = new ArrayList<>();
    private List<Component> mComponentQueryResult = new ArrayList<>();
    private int currIndex = 0;
    private View btn_prev;
    private View btn_next;
    private DetailAddress mCurrentAddress;

    private View ll_add;
    private View ll_add_to_cancel;
    //原位置图层
    private GraphicsLayer mOriginGraphicLayer;
    private GraphicsLayer mDoorGraphicLayer;

    /**
     * 地图默认的事件处理
     */
    private DefaultTouchListener defaultMapOnTouchListener;
    private ProgressDialog progressDialog;
    private SewerageInfoBean.WellBeen mfromBean;
    private View ll_reset;
    private View ll_reset_door;
    private Geometry orgGeometry;
    private DoorNOBean doorNOBean;
    private Geometry orgMenPaiGeometry;
    private Point doorPoint;
    private Geometry geometry;
    private boolean isReset = false;
    private PSHAffairDetail pshAffairDetail;
    private boolean EDIT_MODE;
    private boolean addWell;
    private SewerageWellService sewerageWellService;
    private View ll_add_well, btn_edit, btn_edit_cancel;
    private boolean ifFirstAdd = true;
    /**
     * 是否处于编辑模式
     */
    private boolean ifInEditMode = false;
    private View.OnClickListener addModeCalloutSureButtonClickListener;
    private SelectLocationTouchListener addModeSelectLocationTouchListener;

    public static SewerageSelectWellFragment getInstance(Bundle data) {
        SewerageSelectWellFragment addComponentFragment2 = new SewerageSelectWellFragment();
        addComponentFragment2.setArguments(data);
        return addComponentFragment2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(mContext, R.layout.fragment_psh_select_component2, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mView = view;

        if (getArguments() != null) {
            curComponentName = getArguments().getString("componentName");
            EDIT_MODE = getArguments().getBoolean("editmode", false);
            addWell = getArguments().getBoolean("addWell", false);

        }


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
                        if (getArguments() != null && getArguments().getDouble("initScale") != 0) {
                            double scale = getArguments().getDouble("initScale");
                            mMapView.setScale(PatrolLayerPresenter.initScale);
                            scaleView.setScale(PatrolLayerPresenter.initScale);
                        } else {
                            if (PatrolLayerPresenter.initScale != -1 && PatrolLayerPresenter.initScale != 0) {
                                mMapView.setScale(PatrolLayerPresenter.initScale);
                                scaleView.setScale(PatrolLayerPresenter.initScale);
                            }
                        }
                        mMapView.setScale(mMapView.getMaxScale());
                        scaleView.setScale(mMapView.getMaxScale());
                        /**
                         * 判断是否有位置传递过来
                         */
                        if (getArguments() != null && getArguments().getSerializable("doorBean") != null) {
                            doorNOBean = (DoorNOBean) getArguments().getSerializable("doorBean");
                            doorPoint = new Point(doorNOBean.getX(), doorNOBean.getY());
                            mMapView.centerAt(doorPoint, true);
                            highLightDoorOriginLocation(doorPoint);
                        }
                        if (getArguments() != null && getArguments().getSerializable("pshAffair") != null) {
                            pshAffairDetail = (PSHAffairDetail) getArguments().getSerializable("pshAffair");
                            if (pshAffairDetail.getData() != null && !TextUtils.isEmpty(pshAffairDetail.getData().getDoorplateAddressCode())) {
                                queryMenPaiForId(pshAffairDetail.getData().getDoorplateAddressCode());
                            } else {
                                Point point = new Point(pshAffairDetail.getData().getX(), pshAffairDetail.getData().getY());
                                highLightDoorOriginLocation(point);
                                orgMenPaiGeometry = point;
                                mMapView.centerAt(point, true);
                                mMapView.setScale(mMapView.getMaxScale());
                                scaleView.setScale(mMapView.getMaxScale());
                            }
                        }
                        if (getArguments() != null && getArguments().getSerializable("geometry") != null) {
                            geometry = (Geometry) getArguments().getSerializable("geometry");
                            mMapView.centerAt(GeometryUtil.getGeometryCenter(geometry), true);
                            highLightFacilityOriginLocation(geometry);
                        }
                        if (getArguments() != null && getArguments().getSerializable("wellInfo") != null) {
                            mfromBean = (SewerageInfoBean.WellBeen) getArguments().getSerializable("wellInfo");
                            locationMarker.setVisibility(View.GONE);
                            if (!TextUtils.isEmpty(mfromBean.getWellId())) {
                                queryForId(mfromBean.getWellId());
                            }
                        } else {
                            //默认定位到水务局附近
//                            Point point = new Point(PatrolLayerPresenter.longitude, PatrolLayerPresenter.latitude);
//                            mMapView.centerAt(point, true);
                            if (locationButton != null && doorPoint == null) {
                                locationButton.followLocation();
                            }
                            // startLocate();
                        }

                        if (EDIT_MODE) {
                            locationMarker.setVisibility(View.VISIBLE);
                        }

                        mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
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
        /**
         * 候选列表容器
         */
        ll_component_list = (ViewGroup) view.findViewById(R.id.ll_component_list);

        /**
         * 默认使用窨井
         */
        //locationMarker.changeIcon(MapIconUtil.getResId(LayerUrlConstant.componentNames[0]));
        locationMarker.setVisibility(View.VISIBLE);

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
        mBehavior = AnchorSheetBehavior.from(map_bottom_sheet);
        mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);

        btn_prev = view.findViewById(R.id.prev);
        btn_next = view.findViewById(R.id.next);
        component_intro = view.findViewById(R.id.intro);
        component_detail_ll = view.findViewById(R.id.detail_ll);
        component_detail_container = (ViewGroup) view.findViewById(R.id.detail_container);
        btn_upload = view.findViewById(R.id.btn_upload);
        ll_add_well = view.findViewById(R.id.ll_add_well);
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_edit_cancel = view.findViewById(R.id.btn_edit_cancel);

        if (EDIT_MODE) {
            ll_add_well.setVisibility(View.VISIBLE);
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapView.getCallout().hide();
//                mMapMeasureView.stopMeasure();
                boolean locate = false;
                if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_edit_cancel.performClick();
                }
                ifInEditMode = true;
                btn_edit.setVisibility(View.GONE);
                btn_edit_cancel.setVisibility(View.VISIBLE);
                if (ifFirstAdd) {
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "移动地图选择设施的位置");
                    ifFirstAdd = false;
                }

                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_add_facility_2);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                hideBottomSheet();
                initGLayer();

                setAddNewFacilityListener();
                //下面的代码不能在调用 setAddNewFacilityListener 方法前调用，
                // 因为 addModeSelectLocationTouchListener 和 addModeCalloutSureButtonClickListener 未初始化
                if (locate) {
                    addModeSelectLocationTouchListener.locate();
                }

            }
        });

        btn_edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifInEditMode = false;
                btn_edit.setVisibility(View.VISIBLE);
                btn_edit_cancel.setVisibility(View.GONE);
                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_select_location);
                }

                mMapView.getCallout().hide();
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());

                hideBottomSheet();
                initGLayer();
            }
        });

        /*
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                onChangedBottomSheetState(newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        */
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
        //隐藏提交按钮
        btn_upload.setVisibility(View.GONE);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 改成确定按钮
                //tableViewManager.uploadEdit();
            }
        });

        //修改标题
        ((TextView) view.findViewById(R.id.tv_title)).setText("接驳井选择");
        view.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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


        ll_add_to_cancel = view.findViewById(R.id.ll_add_to_cancel);
        ll_add_to_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                mMapView.getCallout().hide();
                ll_add.setVisibility(View.VISIBLE);
                ll_add_to_cancel.setVisibility(View.GONE);
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });

        ll_add = view.findViewById(R.id.ll_add);
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                if(btn_edit_cancel.getVisibility() == View.VISIBLE){
                    btn_edit_cancel.performClick();
                }
                if (ifFirstAdd) {
                    ToastUtil.iconLongToast(getActivity(),R.mipmap.ic_alert_yellow,"移动地图选择新增设施的位置");
                    ifFirstAdd = false;
                }
                */

                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_add_facility);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                ll_add.setVisibility(View.GONE);
                ll_add_to_cancel.setVisibility(View.VISIBLE);
                hideBottomSheet();
                initGLayer();
                //  setAddNewFacilityListener();
            }
        });

        ll_add.setVisibility(View.GONE);
        ll_add_to_cancel.setVisibility(View.GONE);

        ll_reset = view.findViewById(R.id.ll_reset);
        ll_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isReset = true;
                locationMarker.setVisibility(View.GONE);
                if (mMapView.getCallout().isShowing()) {
                    mMapView.getCallout().hide();
                }
                if (geometry != null) {
                    mMapView.centerAt((Point) geometry, true);
                } else if (mfromBean != null) {
                    btn_next.setVisibility(View.GONE);
                    btn_prev.setVisibility(View.GONE);
                    currIndex = 0;
                    component_detail_container.removeAllViews();
                    if (mOrgComponentQueryResult != null && orgGeometry != null) {
                        mMapView.centerAt((Point) orgGeometry, true);
                        showComponentsOnBottomSheet(mOrgComponentQueryResult);
                    } else if (mfromBean != null) {
                        queryForId(mfromBean.getWellId());
                    }
                }
                if (mfromBean == null || EDIT_MODE) {
                    mMapView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            locationMarker.setVisibility(View.VISIBLE);
                        }
                    }, 300);
                }

            }
        });


        ll_reset_door = view.findViewById(R.id.ll_reset_door);
        ll_reset_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationMarker.setVisibility(View.GONE);
                if (mMapView.getCallout().isShowing()) {
                    mMapView.getCallout().hide();
                }
                hideBottomSheet();
//                if (doorNOBean != null && doorPoint != null) {
//                    mMapView.centerAt(doorPoint, true);
//                } else if (orgMenPaiGeometry != null) {

                if (orgMenPaiGeometry != null) {
                    mMapView.centerAt((Point) orgMenPaiGeometry, true);
                } else if (pshAffairDetail != null && pshAffairDetail.getData() != null) {
                    queryMenPaiForId(pshAffairDetail.getData().getDoorplateAddressCode());
                }
                if (mfromBean == null || EDIT_MODE) {
                    mMapView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            locationMarker.setVisibility(View.VISIBLE);
                        }
                    }, 300);
                }
            }
        });

    }

    private void setAddNewFacilityListener() {
        if (addModeSelectLocationTouchListener == null) {
            addModeSelectLocationTouchListener = new SelectLocationTouchListener(mContext,
                    mMapView, locationMarker, null) {
                @Override
                public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
                    if (locationButton != null && locationButton.ifLocating()) {
                        //5
                        locationButton.setStateNormal();
                    }
                    return super.onDragPointerMove(from, to);
                }
            };
        }

        if (addModeCalloutSureButtonClickListener == null) {
            addModeCalloutSureButtonClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //隐藏callout
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().animatedHide();
                    }

//                    //恢复点击事件
//                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
//                    //隐藏marker
//                    locationMarker.setVisibility(View.GONE);

                    LocationInfo locationInfo = addModeSelectLocationTouchListener.getLoationInfo();
                    if (locationInfo.getDetailAddress() == null || locationInfo.getPoint() == null) {
                        ToastUtil.shortToast(mContext, "请重新选择位置");
                        return;
                    }

                    Intent intent = new Intent(mContext, WellNewFacilityActivity.class);
                    intent.putExtra("detailAddress", locationInfo.getDetailAddress());
                    intent.putExtra("x", locationInfo.getPoint().getX());
                    intent.putExtra("y", locationInfo.getPoint().getY());
                    intent.putExtra("type", "接驳井");
                    startActivity(intent);

                    btn_edit.setVisibility(View.VISIBLE);
                    btn_edit_cancel.setVisibility(View.GONE);
                    if (locationMarker != null) {
                        locationMarker.changeIcon(R.mipmap.ic_select_location);
                    }

                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                }
            };
        }

        /**
         * 气泡中“确定”按钮点击事件
         */
        addModeSelectLocationTouchListener.setCalloutSureButtonClickListener(addModeCalloutSureButtonClickListener);

        mMapView.setOnTouchListener(addModeSelectLocationTouchListener);
    }

    private void initLegendPresenter() {
        if (legendPresenter == null) {
            ILegendView legendView = new PSHImageLegendView(mContext);
            legendPresenter = new LegendPresenter(legendView, new OnlineLegendService(mContext));
        }

    }

    public void loadMap() {
        IDrawerController drawerController = (IDrawerController) getActivity();
        layerView = new PatrolLayerView2(mContext, mMapView, drawerController.getDrawerContainer());
        layerPresenter = new PSHWelllayerPresenter(layerView);
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
     * 高亮设施原来的位置
     *
     * @param mDestinationOrLastTimeSelectLocation
     */
    private void highLightFacilityOriginLocation(Geometry mDestinationOrLastTimeSelectLocation) {
        if (mOriginGraphicLayer == null) {
            mOriginGraphicLayer = new GraphicsLayer();
            mMapView.addLayer(mOriginGraphicLayer);
        }
        mOriginGraphicLayer.removeAll();
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(getContext(),
                getContext().getResources().getDrawable(R.drawable.old_map_point_jing));
        pictureMarkerSymbol.setOffsetY(16);
        Graphic graphic = new Graphic(mDestinationOrLastTimeSelectLocation, pictureMarkerSymbol);
        mOriginGraphicLayer.addGraphic(graphic);
        mOriginGraphicLayer.setSelectedGraphics(new int[]{graphic.getUid()}, true);
    }


    /**
     * 高亮设施原来的位置
     *
     * @param mDestinationOrLastTimeSelectLocation
     */
    private void highLightDoorOriginLocation(Geometry mDestinationOrLastTimeSelectLocation) {
        if (mDoorGraphicLayer == null) {
            mDoorGraphicLayer = new GraphicsLayer();
            mMapView.addLayer(mDoorGraphicLayer);
        }
        mDoorGraphicLayer.removeAll();
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(getContext(),
                getContext().getResources().getDrawable(R.mipmap.ic_select_location_orange));
        pictureMarkerSymbol.setOffsetY(16);
        Graphic graphic = new Graphic(mDestinationOrLastTimeSelectLocation, pictureMarkerSymbol);
        mDoorGraphicLayer.addGraphic(graphic);
        mDoorGraphicLayer.setSelectedGraphics(new int[]{graphic.getUid()}, true);
    }

    /**
     * 开启定位
     */
    public void startLocate() {
        if (mLocationManager == null) {
            mLocationManager = new PatrolLocationManager(mContext, mMapView);
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
                    ToastUtil.shortToast(mContext, "当前位置不在地图范围内");
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
            if (mBehavior.getState() == AnchorSheetBehavior.STATE_EXPANDED) {
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_MAP_STATE, mMapView.retainState());
    }


//
//    private void showCallout(String address, Point point, final View.OnClickListener calloutSureButtonClickListener) {
//        final Point orgGeometry = point;
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
            defaultMapOnTouchListener = new DefaultTouchListener(mContext, mMapView, locationMarker, false, new View.OnClickListener() {

                /**
                 * callout的确定点击事件
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    isReset = false;
                    if (mfromBean == null || EDIT_MODE) {
                        double scale = mMapView.getScale();
                        if (scale > LayerUrlConstant.MIN_QUERY_SCALE) {
                            ToastUtil.shortToast(mContext, "请先放大到可以看到设施的级别");
                            return;
                        }

                        LocationInfo locationInfo = defaultMapOnTouchListener.getLoationInfo();
                        if (locationInfo.getDetailAddress() == null && locationInfo.getPoint() == null) {
                            ToastUtil.shortToast(mContext, "请重新选择位置");
                            return;
                        }

                        double x = locationInfo.getPoint().getX();
                        double y = locationInfo.getPoint().getY();

                        if (scale < LayerUrlConstant.MIN_QUERY_SCALE) {
                            defaultMapOnTouchListener.query(x, y);
                        }
                    }
                }
            });

        }

        return defaultMapOnTouchListener;
    }


    private void queryForId(String id) {
        mOrgComponentQueryResult.clear();
        btn_next.setVisibility(View.GONE);
        btn_prev.setVisibility(View.GONE);
        currIndex = 0;
        final List<LayerInfo> layerInfoList = new ArrayList<>();
        for (String url : LayerUrlConstant.newComponentUrls) {
            LayerInfo layerInfo = new LayerInfo();
            layerInfo.setUrl(url);
            layerInfoList.add(layerInfo);
        }
        SewerageWellService componentMaintenanceService = new SewerageWellService(mContext.getApplicationContext());
        if (true) {
            componentMaintenanceService.queryWellComponents(id, new Callback2<List<Component>>() {
                @Override
                public void onSuccess(List<Component> components) {
                    if (ListUtil.isEmpty(components)) {
                        return;
                    }
                    mOrgComponentQueryResult = new ArrayList<Component>();
                    mOrgComponentQueryResult.addAll(components);
                    if (mOrgComponentQueryResult.get(0).getGraphic() != null) {
                        orgGeometry = mOrgComponentQueryResult.get(0).getGraphic().getGeometry();
                        highLightFacilityOriginLocation(orgGeometry);
                        mMapView.centerAt((Point) orgGeometry, true);
                        showComponentsOnBottomSheet(mOrgComponentQueryResult);
                    }
                }

                @Override
                public void onFail(Exception error) {

                }
            });
        }
    }

    private void queryMenPaiForId(String id) {
        mOrgComponentQueryResult.clear();
        btn_next.setVisibility(View.GONE);
        btn_prev.setVisibility(View.GONE);
        currIndex = 0;
        final List<LayerInfo> layerInfoList = new ArrayList<>();
        for (String url : LayerUrlConstant.newComponentUrls) {
            LayerInfo layerInfo = new LayerInfo();
            layerInfo.setUrl(url);
            layerInfoList.add(layerInfo);
        }
        SewerageWellService componentMaintenanceService = new SewerageWellService(mContext.getApplicationContext());
        if (true) {
            componentMaintenanceService.queryMenPaiComponents(id, new Callback2<List<Component>>() {
                @Override
                public void onSuccess(List<Component> components) {
                    if (ListUtil.isEmpty(components)) {
                        return;
                    }
                    if (components.get(0).getGraphic() != null) {
                        orgMenPaiGeometry = components.get(0).getGraphic().getGeometry();
                        highLightDoorOriginLocation(orgMenPaiGeometry);
                    }
                }

                @Override
                public void onFail(Exception error) {

                }
            });
        }
    }

    /**
     * The MapView's touch listener.
     */
    private class DefaultTouchListener extends SelectLocationTouchListener {

        private ProgressDialog progressDialog;
        private SelectLocationService selectLocationService;
        private View.OnClickListener calloutSureButtonClickListener;

        public DefaultTouchListener(Context context, MapView view, LocationMarker locationMarker, View.OnClickListener calloutSureButtonClickListener) {
            super(context, view, locationMarker, calloutSureButtonClickListener);
        }

        public DefaultTouchListener(Context context, MapView view, LocationMarker locationMarker,
                                    boolean ifshowCallout, View.OnClickListener calloutSureButtonClickListener) {
            super(context, view, locationMarker, ifshowCallout, calloutSureButtonClickListener);
            this.calloutSureButtonClickListener = calloutSureButtonClickListener;
        }


        @Override
        public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
            if (locationButton != null && locationButton.ifLocating()) {
                locationButton.setStateNormal();
            }
            hideBottomSheet();
            return super.onDragPointerMove(from, to);
        }

        @Override
        public boolean onSingleTap(final MotionEvent e) {
//            initGLayer();
            hideBottomSheet();
            if (mfromBean != null && !EDIT_MODE) {
                handleTap(e);
            }

            return true;
        }

        /**
         * //         * @param latitude
         * //         * @param longitude
         * //
         */
//        public void requestLocation(final double latitude, final double longitude) {
//
//            if (selectLocationService == null) {
//                selectLocationService = new SelectLocationService(mContext, Locator.createOnlineLocator());
//            }
//
//            selectLocationService.parseLocation(new LatLng(latitude, longitude), mMapView.getSpatialReference())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<DetailAddress>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onNext(DetailAddress detailAddress) {
//                            String address = detailAddress.getDetailAddress();
//                            mCurrentAddress = detailAddress;
//                            mCurrentAddress.setX(longitude);
//                            mCurrentAddress.setY(latitude);
//                            showCallout(new Point(longitude, latitude), detailAddress);
//                        }
//                    });
//        }
        private void query(double x, double y) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("正在查询中.....");
            }

            progressDialog.show();

            mComponentQueryResult.clear();
            btn_next.setVisibility(View.GONE);
            btn_prev.setVisibility(View.GONE);
            currIndex = 0;
            final Point point = new Point(x, y);
            final List<LayerInfo> layerInfoList = new ArrayList<>();
            for (String url : LayerUrlConstant.newComponentUrls) {
                LayerInfo layerInfo = new LayerInfo();
                layerInfo.setUrl(url);
                layerInfoList.add(layerInfo);
            }
            Geometry geometry = GeometryEngine.buffer(point, mMapView.getSpatialReference(), 40 * mMapView.getResolution(), null);
            SewerageWellService componentMaintenanceService = new SewerageWellService(mContext.getApplicationContext());
            if (true) {
                componentMaintenanceService.queryUnitComponents(geometry, new Callback2<List<Component>>() {
                    @Override
                    public void onSuccess(List<Component> components) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                        if (ListUtil.isEmpty(components)) {
                            if (mfromBean == null || EDIT_MODE) {
                                mMapView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCalloutMessage("没有找到井", null, null);
                                    }
                                }, 200);
                            }
//                            /**
//                             * 当查询失败的时候进行查询地址
//                             */
//                            requestLocation(point, mMapView.getSpatialReference(), new Callback1<String>() {
//                                @Override
//                                public void onCallback(String s) {
//                                    showCalloutMessage(s, null, new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            DetailAddress detailAddress = defaultMapOnTouchListener.getLoationInfo().getDetailAddress();
//                                            EventBus.getDefault().post(new SelectComponentFinishEvent2(null, detailAddress, mMapView.getScale()));
//                                            getActivity().finish();
//                                        }
//                                    });
//                                }
//                            });
                            return;
                        }
                        mComponentQueryResult = new ArrayList<Component>();
                        mComponentQueryResult.addAll(components);
                        initGLayer();
                        if (mComponentQueryResult.get(0).getGraphic() != null) {
                            drawGeometry(mComponentQueryResult.get(0).getGraphic().getGeometry(), mGLayer, true, true);
                            showComponentsOnBottomSheet(mComponentQueryResult);
                        }
                    }

                    @Override
                    public void onFail(Exception error) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        mMapView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showCalloutMessage("没有找到井", null, null);
                            }
                        }, 200);
//                        /**
//                         * 当查询失败的时候进行查询地址
//                         */
//                        requestLocation(point, mMapView.getSpatialReference(), new Callback1<String>() {
//                            @Override
//                            public void onCallback(String s) {
//                                showCalloutMessage(s, null, new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        DetailAddress detailAddress = defaultMapOnTouchListener.getLoationInfo().getDetailAddress();
//                                        EventBus.getDefault().post(new SelectComponentFinishEvent2(null, detailAddress, mMapView.getScale()));
//                                        getActivity().finish();
//                                    }
//                                });
//                            }
//                        });
                    }
                });
            } else {
                String oldLayerUrl = LayerUrlConstant.getLayerUrlByLayerName(LayerUrlConstant.getLayerNameByNewLayerUrl(currComponentUrl));
                componentMaintenanceService.queryComponents(geometry, oldLayerUrl, currComponentUrl, new Callback2<List<QueryFeatureSet>>() {
                    @Override
                    public void onSuccess(List<QueryFeatureSet> queryFeatureSetList) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                        if (ListUtil.isEmpty(queryFeatureSetList)) {
                            /**
                             * 当查询失败的时候进行查询地址
                             */
                            requestLocation(point, mMapView.getSpatialReference(), new Callback1<String>() {
                                @Override
                                public void onCallback(String s) {
                                    showCalloutMessage(s, null, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            DetailAddress detailAddress = defaultMapOnTouchListener.getLoationInfo().getDetailAddress();
                                            EventBus.getDefault().post(new SelectComponentFinishEvent2(null, detailAddress, mMapView.getScale()));
                                            getActivity().finish();
                                        }
                                    });
                                }
                            });
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
//                                component.setFieldAlias(featureSet.getFieldAliases());
//                                component.setFields(featureSet.getFields());
                                component.setGraphic(graphic);
                                Object o = graphic.getAttributes().get(featureSet.getObjectIdFieldName());
                                if (o != null && o instanceof Integer) {
                                    component.setObjectId((Integer) o); //按照道理objectId一定是integer的
                                }
                                mComponentQueryResult.add(component);
                            }
                        }

                        showComponentsOnBottomSheet(mComponentQueryResult);
                    }

                    @Override
                    public void onFail(Exception error) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        /**
                         * 当查询失败的时候进行查询地址
                         */
                        requestLocation(point, mMapView.getSpatialReference(), new Callback1<String>() {
                            @Override
                            public void onCallback(String s) {
                                showCalloutMessage(s, null, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        DetailAddress detailAddress = defaultMapOnTouchListener.getLoationInfo().getDetailAddress();
                                        EventBus.getDefault().post(new SelectComponentFinishEvent2(null, detailAddress, mMapView.getScale()));
                                        getActivity().finish();
                                    }
                                });
                            }
                        });
                    }
                });
            }
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
            final Point point = mMapView.toMapPoint(e.getX(), e.getY());
            //requestLocation(point.getY(), point.getX());
            double scale = mMapView.getScale();
            if (scale < LayerUrlConstant.MIN_QUERY_SCALE) {
                final Point mapPoint = mMapView.toMapPoint(e.getX(), e.getY());
                query(mapPoint.getX(), mapPoint.getY());
            }

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

    @NonNull
    private Symbol getPointSymbol() {
        Drawable drawable = ResourcesCompat.getDrawable(mContext.getResources(), com.augurit.agmobile.patrolcore.R.mipmap.ic_select_location, null);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(mContext, drawable);
        pictureMarkerSymbol.setOffsetY(16);
        return pictureMarkerSymbol;    // xjx 改为兼容api19的方式获取drawable
    }

    /*
    private void onChangedBottomSheetState(int state) {
        if (state == STATE_EXPANDED) {
            if (component_intro.getVisibility() == View.VISIBLE) {
                showBottomSheetContent(component_detail_container.getId());
                showDetail();
            }

        } else if (state == STATE_COLLAPSED) {
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
    */


    private void hideBottomSheet() {
        map_bottom_sheet.setVisibility(View.GONE);
        if (mGLayer != null) {
            mGLayer.removeAll();
        }
    }

    private void showComponentsOnBottomSheet(List<Component> componentQueryResult) {
        if (componentQueryResult.size() > 1) {
            btn_next.setVisibility(View.VISIBLE);
        }
        //initGLayer();
        //drawGeometry(componentQueryResult.get(0).getGraphic().getGeometry(), mGLayer, true, true);
        if (componentQueryResult.size() < 1) return;
        showBottomSheet(componentQueryResult.get(0));

    }

    private void showCallout(final Component component) {
//        //显示callout
        String usid = String.valueOf(component.getGraphic().getAttributes().get("OBJECTID"));
//        String type = LayerUrlConstant.getLayerNameByUnknownLayerUrl(component.getLayerUrl());
        String type = String.valueOf(component.getGraphic().getAttributes().get("LAYER_NAME"));
        String title = StringUtil.getNotNullString(type, "") + "(" + StringUtil.getNotNullString(usid, "") + ")";
//        Callout callout = mMapView.getCallout();
//        View view = View.inflate(getActivity(), com.augurit.agmobile.patrolcore.R.layout.callout_select_device, null);
//        ((TextView) view.findViewById(com.augurit.agmobile.patrolcore.R.id.tv_listcallout_title)).setText(title);
//
//        view.findViewById(com.augurit.agmobile.patrolcore.R.id.btn_select_device).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new SelectComponentFinishEvent2(component, mCurrentAddress, mMapView.getScale()));
//                getActivity().finish();
//            }
//        });
//        callout.setContent(view);
//        callout.show(GeometryUtil.getGeometryCenter(component.getGraphic().getGeometry()));
        defaultMapOnTouchListener.showCalloutMessage(title, null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleResult(component);

            }
        });
    }

    private void handleResult(final Component component) {

        if (null != component) {
            if ("雨水".equals(component.getGraphic().getAttributes().get("ATTR_TWO"))) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("确定接驳井为雨水井?")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EventBus.getDefault().post(new SelectComponentFinishEvent(component, mCurrentAddress, mMapView.getScale()));
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                    hideBottomSheet();
//                                defaultMapOnTouchListener.showCalloutMessage("",null,null);
                            }
                        })
                        .show();

            } else {
                EventBus.getDefault().post(new SelectComponentFinishEvent(component, mCurrentAddress, mMapView.getScale()));
                getActivity().finish();
            }
        }
    }

//    private void showCallout(Point point, final DetailAddress detailAddress) {
//        //显示callout
//        String name = detailAddress.getDetailAddress();
//        // String type = LayerUrlConstant.getLayerNameByUnknownLayerUrl(component.getLayerUrl());
//        // String title = StringUtil.getNotNullString(name, "") + "  " + StringUtil.getNotNullString(type, "");
//        Callout callout = mMapView.getCallout();
//        View view = View.inflate(getActivity(), com.augurit.agmobile.patrolcore.R.layout.editmap_callout, null);
//        ((TextView) view.findViewById(com.augurit.agmobile.patrolcore.R.id.tv_listcallout_title)).setText(name);
//
//        view.findViewById(com.augurit.agmobile.patrolcore.R.id.btn_select_device).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new SelectComponentFinishEvent2(null, detailAddress, mMapView.getScale()));
//                getActivity().finish();
//            }
//        });
//        callout.setContent(view);
//        callout.show(point);
//    }

    @Deprecated
    private void showBottomSheet2(final Component component) {

        showCallout(component);

        //  initGLayer();
        //  drawGeometry(component.getGraphic().getGeometry(), mGLayer, true, true);

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
        TextView code = (TextView) map_bottom_sheet.findViewById(R.id.code);


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


        //已挂牌编号
        String codeValue = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.CODE));
        codeValue = codeValue.trim();
        if (component.getLayerName().equals("窨井")) {
            if (!codeValue.isEmpty()) {
                code.setText("已挂牌编码" + ":" + StringUtil.getNotNullString(codeValue, ""));
            } else {
//                code.setText("已挂牌编码" + ":" +"无");
                code.setText("已挂牌编码" + ":" + "");
            }
        } else {
            code.setVisibility(View.GONE);
        }

        // if (errorInfo == null) {
        tv_errorinfo.setVisibility(View.GONE);
        //  } else {
        //       tv_errorinfo.setVisibility(View.VISIBLE);
        //       tv_errorinfo.setText(errorInfo);
        //  }
        ////  showBottomSheetContent(component_intro.getId());
        map_bottom_sheet.setVisibility(View.VISIBLE);
        mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);
        // loadCompleteDataAsync(component);
    }

    private void showBottomSheet(final Component component) {
        if (!isReset && (mfromBean == null || EDIT_MODE)) {
            map_bottom_sheet.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showCallout(component);
                }
            }, 300);
        }

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
        TextView code = (TextView) map_bottom_sheet.findViewById(R.id.code);
//        addrTv.setVisibility(View.GONE);
        TextView tv_errorinfo = (TextView) map_bottom_sheet.findViewById(R.id.tv_errorinfo);
        TextView tv_parent_org_name = (TextView) map_bottom_sheet.findViewById(R.id.tv_parent_org_name);
        LinearLayout ll_photo = (LinearLayout) map_bottom_sheet.findViewById(R.id.ll_photo);
        TakePhotoTableItem3 view_photos = (TakePhotoTableItem3) map_bottom_sheet.findViewById(R.id.view_photos);
        view_photos.setReadOnly();
        view_photos.setEnabled(false);
        view_photos.setImageIsShow(false);
        if (mfromBean != null || EDIT_MODE || pshAffairDetail != null || addWell) {
            ll_photo.setVisibility(View.VISIBLE);
            observeWellPhotos(component.getObjectId(), view_photos, ll_photo);
            ViewGroup.LayoutParams layoutParams = map_bottom_sheet.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.height = Util.dpToPx(320);
                map_bottom_sheet.setLayoutParams(layoutParams);
            }
        }


        String name = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.NAME));

        String type = String.valueOf(component.getGraphic().getAttributes().get("LAYER_NAME"));


//        String subtype = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SUBTYPE));
        String subtype = String.valueOf(component.getGraphic().getAttributes().get("ATTR_ONE"));
        String usid = String.valueOf(component.getGraphic().getAttributes().get("OBJECTID"));

        String title = StringUtil.getNotNullString(type, "") + "(" + usid + ")";
        titleTv.setText(title);

        String date = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.UPDATEDATE));
        String formatDate = "";
        try {
            formatDate = TimeUtil.getStringTimeYMD(new Date(Long.valueOf(date)));
        } catch (Exception e) {

        }
        dateTv.setText(StringUtil.getNotNullString(formatDate, ""));

//        String sort = String.valueOf(component.getGraphic().getAttributes().get("ORIGIN_ATTR_TWO"));
        String sort = String.valueOf(component.getGraphic().getAttributes().get("ATTR_TWO"));
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
        String layerName = String.valueOf(component.getGraphic().getAttributes().get("LAYER_NAME"));
        /**
         * 如果是雨水口，显示特性：方形
         */
        if (layerName.equals("雨水口")) {
            String feature = String.valueOf(component.getGraphic().getAttributes().get("ATTR_ONE"));
            sortTv.setText(StringUtil.getNotNullString(feature, ""));
        }
        if ("雨水口".equals(type)) {
            String style = String.valueOf(component.getGraphic().getAttributes().get("ATTR_ONE"));
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


        //已挂牌编号
        String codeValue = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.CODE));
        codeValue = codeValue.trim();
        if (layerName.equals("窨井")) {
            code.setVisibility(View.VISIBLE);
            if (!codeValue.isEmpty()) {
                code.setText("已挂牌编码" + ":" + StringUtil.getNotNullString(codeValue, ""));
            } else {
                code.setText("已挂牌编码" + ":" + "");
            }
        } else {
            code.setVisibility(View.GONE);
        }

        /**
         * 修改属性三
         */
        String field3 = "";
        if (layerName.equals("窨井")) {
            field3 = "井盖材质: " + String.valueOf(component.getGraphic().getAttributes().get("ATTR_THREE"));
        } else if (layerName.equals("排放口")) {
            field3 = "排放去向: " + String.valueOf(component.getGraphic().getAttributes().get("ATTR_THREE"));
        }
        field3Tv.setText(field3);


        final String address = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ADDR));
        addrTv.setText("设施位置" + "：" + StringUtil.getNotNullString(address, ""));

        /*
        if (errorInfo == null) {
            tv_errorinfo.setVisibility(View.GONE);
        } else {
            tv_errorinfo.setVisibility(View.VISIBLE);
            tv_errorinfo.setText(errorInfo + "?");
        }
        */
        tv_errorinfo.setVisibility(View.GONE);


        String parentOrg = String.valueOf(component.getGraphic().getAttributes().get("PARENT_ORG_NAME"));
        tv_parent_org_name.setText("权属单位：" + StringUtil.getNotNullString(parentOrg, ""));
        ////   showBottomSheetContent(component_intro.getId());
        map_bottom_sheet.setVisibility(View.VISIBLE);
        mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);
        // loadCompleteDataAsync(component);
    }

    private void observeWellPhotos(Integer objectId, final TakePhotoTableItem3 view_photos, final LinearLayout ll_photo) {
        if (sewerageWellService == null) {
            sewerageWellService = new SewerageWellService(getActivity());
        }
        sewerageWellService.getWellPhotos(objectId + "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WellPhoto>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WellPhoto wellPhoto) {
                        if (wellPhoto == null || wellPhoto.getCode() != 200) {
                            ToastUtil.shortToast(getActivity(), "获取接驳井图片失败");
                            return;
                        }
                        List<WellPhoto.RowsBean> rows = wellPhoto.getRows();
                        if (!ListUtil.isEmpty(rows)) {
                            List<Photo> welPhotos = new ArrayList<>();
                            for (WellPhoto.RowsBean rowsPhoto : rows) {
                                Photo photo = new Photo();
                                photo.setPhotoName(rowsPhoto.getAttName());
                                photo.setHasBeenUp(1);
                                photo.setPhotoPath(rowsPhoto.getAttPath());
                                photo.setThumbPath(rowsPhoto.getThumPath());
                                photo.setField1("photo");
                                welPhotos.add(photo);
                            }
                            view_photos.setSelectedPhotos(welPhotos);
                            view_photos.setImageIsShow(true);
                        } else {
                            ll_photo.setVisibility(View.GONE);
                        }
                    }
                });

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
                            false, TableState.READING, tableItems,
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
            /**
             * 不显示查看位置按钮
             */
            tableViewManager.setDontShowCheckLocationButton();
        }

    }


    /*
    private void loadCompleteDataAsync(final Component component) {
        tableItems = null;
        projectId = null;
        tableViewManager = null;
        component_detail_container.removeAllViews();
        photoList.clear();
        final TableDataManager tableDataManager = new TableDataManager(mContext.getApplicationContext());
        List<Project> projects = tableDataManager.getProjectFromDB();
        Project project = null;
        for (Project p : projects) {
            if (p.getName().equals(component.getLayerName())) {
                project = p;
            }
        }
        if (project == null) {
            ToastUtil.shortToast(mContext, "加载详细信息失败！");
            return;
        }
        projectId = project.getId();
        String getFormStructureUrl = BaseInfoManager.getBaseServerUrl(mContext) + "rest/report/rptform";
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
                    TableViewManager.orgGeometry = component.getGraphic().getGeometry();
                    TableViewManager.featueLayerUrl = component.getLayerUrl();
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
    */

    private void loadCompleteData(final Component component) {
        pd = new ProgressDialog(mContext);
        pd.setMessage("正在加载详细信息...");
        pd.show();
        final TableDataManager tableDataManager = new TableDataManager(mContext.getApplicationContext());
        List<Project> projects = tableDataManager.getProjectFromDB();
        Project project = null;
        for (Project p : projects) {
            if (p.getName().equals(component.getLayerName())) {
                project = p;
            }
        }
        if (project == null) {
            ToastUtil.shortToast(mContext, "加载详细信息失败！");
            return;
        }
        final String projectId = project.getId();
        String getFormStructureUrl = BaseInfoManager.getBaseServerUrl(mContext) + "rest/report/rptform";
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
                    geometry = component.getGraphic().getGeometry();
                    TableViewManager.featueLayerUrl = component.getLayerUrl();
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
                ToastUtil.shortToast(mContext, "加载详细信息失败!");
            }
        });
    }

    private void queryAttachmentInfosAsync(String layerUrl, final Graphic graphic, final ArrayList<TableItem> completeTableItems, final String projectId) {
        final ArcGISFeatureLayer arcGISFeatureLayer = new ArcGISFeatureLayer(layerUrl, ArcGISFeatureLayer.MODE.SNAPSHOT);
        arcGISFeatureLayer.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (status == STATUS.INITIALIZED) {
                    if (mContext == null) {
                        return;
                    }
                    Object oid = graphic.getAttributes().get(arcGISFeatureLayer.getObjectIdField());
                    if (oid == null) {
                        return;
                    }
                    final int objectid = Integer.valueOf(oid.toString());
                    FileService fileService = new FileService(mContext);
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
                                        String tempFile = new FilePathUtil(mContext).getSavePath() + "/images/" + attachmentInfo.getName() + ".jpg";
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
                            ToastUtil.shortToast(mContext, "加载详细信息失败!");
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
        layerAdapter.notifyDataSetChanged(LayerUrlConstant.getIndexByUnknowsLayerUrl(currComponentUrl));
        showBottomSheet(component);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewUpload(UploadedFacility uploadedFacility) {
        if (uploadedFacility != null) {

            Layer[] layers = mMapView.getLayers();
            for (Layer layer : layers) {
                if (layer instanceof ArcGISDynamicMapServiceLayer) {
                    ((ArcGISDynamicMapServiceLayer) layer).refresh();
                }
            }

            double x = uploadedFacility.getX();
            double y = uploadedFacility.getY();
            queryWellInstant(x, y);

        }


    }

    //新增井返回后立即查询井 并返回关联井界面
    public void queryWellInstant(double x, double y) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("正在查询中.....");
        }

        progressDialog.show();

        mComponentQueryResult.clear();
        btn_next.setVisibility(View.GONE);
        btn_prev.setVisibility(View.GONE);
        currIndex = 0;
        final Point point = new Point(x, y);
        final List<LayerInfo> layerInfoList = new ArrayList<>();
        for (String url : LayerUrlConstant.newComponentUrls) {
            LayerInfo layerInfo = new LayerInfo();
            layerInfo.setUrl(url);
            layerInfoList.add(layerInfo);
        }
        Geometry geometry = GeometryEngine.buffer(point, mMapView.getSpatialReference(), 40 * mMapView.getResolution(), null);
        SewerageWellService componentMaintenanceService = new SewerageWellService(mContext.getApplicationContext());
        componentMaintenanceService.queryUnitComponents(geometry, new Callback2<List<Component>>() {
            @Override
            public void onSuccess(List<Component> components) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                if (ListUtil.isEmpty(components)) {
                    if (mfromBean == null || EDIT_MODE) {
                        mMapView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.shortToast(mContext, "查询关联井失败，请手动关联");
                            }
                        }, 200);
                    }
                    return;
                }

                EventBus.getDefault().post(new SelectComponentFinishEvent(components.get(0), mCurrentAddress, mMapView.getScale()));
                getActivity().finish();

            }

            @Override
            public void onFail(Exception error) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                mMapView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.shortToast(mContext, "查询关联井失败，请手动关联");
                    }
                }, 200);
            }
        });
    }

    private void changeLayerUrlInitFailState() {
        /*if(LayerUrlConstant.ifLayerUrlInitSuccess()
                && loadLayersSuccess
                && ll_layer_url_init_fail != null){
            ll_layer_url_init_fail.setVisibility(View.GONE);
        }
        if((!LayerUrlConstant.ifLayerUrlInitSuccess()
                || !loadLayersSuccess)
                && ll_layer_url_init_fail != null){
            ll_layer_url_init_fail.setVisibility(View.VISIBLE);
        }*/
        //去掉LayerUrlConstant.ifLayerUrlInitSuccess()，
        // 因为排水在ifLayerUrlInitSuccess写死判断了排水专有的图层，
        //即没有排水的图层会一直提示"网络信号弱..."
        if (loadLayersSuccess
                && ll_layer_url_init_fail != null) {
            ll_layer_url_init_fail.setVisibility(View.GONE);
        }
        if (!loadLayersSuccess
                && ll_layer_url_init_fail != null) {
            ll_layer_url_init_fail.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 网络切换时，若设施URL为null，会重新初始化设施URL，然后发送OnInitLayerUrlFinishEvent事件
     *
     * @param onInitLayerUrlFinishEvent
     */
    @Subscribe
    public void onInitLayerUrlFinished(OnInitLayerUrlFinishEvent onInitLayerUrlFinishEvent) {
//        changeLayerUrlInitFailState();
        if (!loadLayersSuccess && layerPresenter != null) {
            layerPresenter.loadLayer(new Callback2<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    loadLayersSuccess = true;
//                    changeLayerUrlInitFailState();
                }

                @Override
                public void onFail(Exception error) {
                    loadLayersSuccess = false;
//                    changeLayerUrlInitFailState();
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
        if (EventBus.getDefault().isRegistered(this)) {
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
