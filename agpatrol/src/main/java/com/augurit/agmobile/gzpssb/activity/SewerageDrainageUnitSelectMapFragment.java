package com.augurit.agmobile.gzpssb.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.OnInitLayerUrlFinishEvent;
import com.augurit.agmobile.gzps.common.constant.LayerUrlConstant;
import com.augurit.agmobile.gzps.componentmaintenance.SelectComponentEvent;
import com.augurit.agmobile.gzps.componentmaintenance.service.ComponentService;
import com.augurit.agmobile.gzps.measure.view.MapMeasureView;
import com.augurit.agmobile.gzps.uploadfacility.view.UploadFacilitySuccessEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.RefreshMyUploadList;
import com.augurit.agmobile.gzpssb.bean.BuildInfoBySGuid;
import com.augurit.agmobile.gzpssb.bean.DoorNOBean;
import com.augurit.agmobile.gzpssb.bean.DrainageUnit;
import com.augurit.agmobile.gzpssb.bean.DrainageUnitListBean;
import com.augurit.agmobile.gzpssb.bean.QueryIdBeanResult;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.common.callback.OnRecyclerItemClickListener;
import com.augurit.agmobile.gzpssb.common.legend.PSHImageLegendView;
import com.augurit.agmobile.gzpssb.event.RefreshDataListEvent;
import com.augurit.agmobile.gzpssb.event.RefreshDoorData;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.okhttp.Presenter.SewerageItemPresenter;
import com.augurit.agmobile.gzpssb.pshdoorno.add.view.PSHUploadNewDoorNoActivity;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.service.PSHMyUploadDoorNoService;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.service.SelectDoorNOTouchListener;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.service.SewerageLayerService;
import com.augurit.agmobile.gzpssb.seweragewell.presenter.DrainageUnitSeweragelayerPresenter;
import com.augurit.agmobile.gzpssb.seweragewell.presenter.PSHSeweragelayerPresenter;
import com.augurit.agmobile.gzpssb.uploadevent.model.SelectHouseFinishEvent;
import com.augurit.agmobile.gzpssb.uploadfacility.view.tranship.PSHUploadNewFacilityActivity;
import com.augurit.agmobile.mapengine.common.utils.GeometryUtil;
import com.augurit.agmobile.mapengine.common.widget.compassview.CompassView;
import com.augurit.agmobile.mapengine.common.widget.scaleview.MapScaleView;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.service.LayersService;
import com.augurit.agmobile.mapengine.layermanage.view.ILayerView;
import com.augurit.agmobile.mapengine.legend.service.OnlineLegendService;
import com.augurit.agmobile.mapengine.legend.view.ILegendView;
import com.augurit.agmobile.mapengine.map.geometry.LatLng;
import com.augurit.agmobile.patrolcore.common.SelectLocationTouchListener;
import com.augurit.agmobile.patrolcore.common.legend.LegendPresenter;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.agmobile.patrolcore.common.model.LocationInfo;
import com.augurit.agmobile.patrolcore.common.table.TableViewManager;
import com.augurit.agmobile.patrolcore.common.widget.LocationButton;
import com.augurit.agmobile.patrolcore.editmap.widget.LocationMarker;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerView2;
import com.augurit.agmobile.patrolcore.locate.BaiduLocationManager;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.agmobile.patrolcore.selectlocation.service.SelectLocationService;
import com.augurit.agmobile.patrolcore.selectlocation.util.PatrolLocationManager;
import com.augurit.agmobile.patrolcore.selectlocation.view.IDrawerController;
import com.augurit.am.cmpt.common.Callback1;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.common.Callback4;
import com.augurit.am.cmpt.widget.bottomsheet.AnchorSheetBehavior;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.Symbol;
import com.esri.core.tasks.geocode.Locator;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2;
import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.DRAINAGE_UNIT_LAYER_BZ;
import static com.augurit.am.cmpt.widget.bottomsheet.AnchorSheetBehavior.STATE_COLLAPSED;
import static com.augurit.am.cmpt.widget.bottomsheet.AnchorSheetBehavior.STATE_EXPANDED;

/**
 * 工作台的数据上报和主界面的数据上报功能
 *
 * @author 创建人 ：liangshenghong
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.map
 * @createTime 创建时间 ：2017-10-14
 * @modifyBy 修改人 ：liangshenghong
 * @modifyTime 修改时间 ：2017-10-14
 * @modifyMemo 修改备注：
 */
public class SewerageDrainageUnitSelectMapFragment extends Fragment implements SewerageItemPresenter.IGetRoomInfo {

    private static final String KEY_ID = "ID";
    private CoordinatorLayout root;
    private ProgressLinearLayout pb_loading;
    private View item1;
    private View item2;
    private static final String KEY_MAP_STATE = "com.esri.MapState";
    View mView;
    MapView mMapView;
    ProgressDialog pd;
    ViewGroup dis_map_bottom_sheet;
    AnchorSheetBehavior mDisBehavior;
    /**
     * 这个图层是用来显示初始被选中的位置的
     */
    GraphicsLayer mLastAreaLayer = null;
    GraphicsLayer mGLayer = null;
    private LocationMarker locationMarker;
    private PatrolLocationManager mLocationManager;
//    private View myUploadLayerBtn;
    /**
     * 是否是第一次定位，如果是，那么居中；否则，不做任何操作；
     */
    private boolean ifFirstLocate = true;
    private LocationButton locationButton;
    private LegendPresenter legendPresenter;
    private View ll_layer_url_init_fail;
    private ViewGroup ll_topbar_container; //顶部工具容器
    private ViewGroup ll_tool_container;   //左边工具容器
    private MapMeasureView mMapMeasureView;
    private ILayerView layerView;
    private PatrolLayerPresenter layerPresenter;
    private boolean loadLayersSuccess = true;
    //    private GridView gridView;
//    private LimitedLayerAdapter layerAdapter;
    //顶部图层列表中当前选中的设施类型对应的务URL
    private String currComponentUrl = LayerUrlConstant.newComponentUrls[0];
    //点查后设施的简要信息布局
    private TableViewManager tableViewManager;
    private int currIndex = 0;
    private boolean ifFirstAdd = true;
    private Context mContext;
    /**
     * 地图默认的事件处理
     */
    private SelectLocationTouchListener defaultMapOnTouchListener;
    private SelectDoorNOTouchListener mSelectDoorNOTouchListener;
    private View.OnClickListener addModeCalloutSureButtonClickListener;
    private View.OnClickListener addModeCalloutOKButtonClickListener;
    private View.OnClickListener addWellCalloutSureButtonClickListener;
    private View btn_add_hu;
    private View btn_cancel_hu;
    private View.OnClickListener addDoorCalloutSureButtonClickListener;
    private View btn_add_well;
    private View btn_cancel_well;
    private View btn_add_pai;
    private View btn_cancel_pai;
    private int bottomMargin;
    private View btn_dis_prev;
    private View btn_dis_next;
    /**
     * 数据上报图层是否显示
     */
    private boolean ifMyUploadLayerVisible = false;
    private ComponentService componentService;
    private SewerageLayerService mSewerageLayerService;
    private ViewGroup ll_next_and_prev_container;
    private PSHHouse mCurrentDoorNOBean;
    private List<PSHHouse> mDoorNOBeans = new ArrayList<>();
    private CompassView mCompassView;
    private SelectLocationTouchListener addModeSelectLocationTouchListener;
    private SelectLocationTouchListener addModeDoorSelectLocationTouchListener;
    private SewerageItemPresenter sewerageItemPresenter = new SewerageItemPresenter(null);
    private TextView tv_address;
    private SearchFragment searchFragment;
    private DetailAddress componentDetailAddress;
    private SelectLocationTouchListener editModeSelectLocationTouchListener;
    private View.OnClickListener editModeCalloutSureButtonClickListener;
    private GraphicsLayer mOriginGraphicLayer;
    private PSHMyUploadDoorNoService mUploadDoorNoService;

    private RecyclerView rvDatas;
    private SimpleRecyclerViewAdapter adptDatas;
    private View popContentView;
    private PopupWindow popList;
    private boolean isSelected = false;

    public static SewerageDrainageUnitSelectMapFragment getInstance(Bundle data) {
        SewerageDrainageUnitSelectMapFragment addComponentFragment2 = new SewerageDrainageUnitSelectMapFragment();
        addComponentFragment2.setArguments(data);
        return addComponentFragment2;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mContext = context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mContext = activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sewerageItemPresenter.setIGetRoomInfoListener(this);
        return inflater.inflate(R.layout.fragment_dialy_patrol_map, null);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mView = view;
        startgetPosition();
        root = mView.findViewById(R.id.root);
        mView.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        pb_loading = (ProgressLinearLayout) mView.findViewById(R.id.pb_loading);
        item1 = mView.findViewById(R.id.ll_item1);
        item2 = mView.findViewById(R.id.tv_floor);
        mView.findViewById(R.id.ll_inspection).setVisibility(View.GONE);


        mView.findViewById(R.id.ll_search_obj1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFragment.show(getFragmentManager(), SearchFragment.TAG);
            }
        });
        searchFragment = SearchFragment.newInstance();
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                //这里处理逻辑
                if (!StringUtil.isEmpty(keyword)) {
                    queryResultByPsh(keyword);
                }
            }
        });

        ((TextView) mView.findViewById(R.id.tv_title)).setText("排水单元选择");
        btn_add_pai = mView.findViewById(R.id.btn_add_door);
        btn_cancel_pai = mView.findViewById(R.id.btn_edit_door_cancel);

        ll_topbar_container = (ViewGroup) view.findViewById(R.id.ll_topbar_container);
        ll_tool_container = (ViewGroup) view.findViewById(R.id.ll_tool_container);

        // Find MapView and add feature layers
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.setMapBackground(Color.WHITE, Color.TRANSPARENT, 0f, 0f);//设置地图背景色、去掉网格线
        /**
         * 比例尺
         */
        final MapScaleView scaleView = (MapScaleView) view.findViewById(R.id.scale_view);

        ViewGroup ll_compass_container = (ViewGroup) view.findViewById(R.id.ll_compass_container);
        mCompassView = new CompassView(mContext, ll_compass_container, mMapView);


        scaleView.setMapView(mMapView);
        // Set listeners on MapView
        mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onStatusChanged(final Object source, final STATUS status) {
                if (STATUS.INITIALIZED == status) {
                    if (source instanceof MapView) {
                        //todo 暂时写死
                        if (PatrolLayerPresenter.initScale != -1 && PatrolLayerPresenter.initScale != 0) {
                            mMapView.setScale(PatrolLayerPresenter.initScale);
                            scaleView.setScale(PatrolLayerPresenter.initScale);
                        }

                        if (PatrolLayerPresenter.longitude != 0 && PatrolLayerPresenter.latitude != 0) {
                            Point point = new Point(PatrolLayerPresenter.longitude, PatrolLayerPresenter.latitude);
                            mMapView.centerAt(point, true);
                        }
                        if (layerPresenter != null) {
                            layerPresenter.changeLayerVisibility(DRAINAGE_UNIT_LAYER2, true);
                            layerPresenter.changeLayerVisibility(DRAINAGE_UNIT_LAYER_BZ, true);
                        }
                        if (layerPresenter instanceof DrainageUnitSeweragelayerPresenter) {
                            ((DrainageUnitSeweragelayerPresenter) layerPresenter).setLayerAlpha();
                        }

                        if (locationButton != null) {
                            locationButton.followLocation();
                        }
                        /*Geometry geometry = (Geometry) getArguments().getSerializable("geometry");
                        if (geometry != null) {
                            highLightFacilityOriginLocation(geometry);
                            if (locationButton != null) {
                                locationButton.setStateNormal();
                            }
                            mMapView.centerAt((Point) geometry, true);
                        }*/
                        setSearchFacilityListener();

                        Bundle arguments = getArguments();
                        if (arguments != null && arguments.containsKey(KEY_ID)) {
                            String psdyId = arguments.getString(KEY_ID);
                            if (!TextUtils.isEmpty(psdyId)) {
                                // 去查询排水单元的相关信息
                                queryDrainageUnit(psdyId);
                            }
                        }
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

        //上报点查界面和分布图层分布点查界面
        mSewerageLayerService = new SewerageLayerService(getContext());

        //定位图标
        locationMarker = (LocationMarker) view.findViewById(R.id.locationMarker);
        locationMarker.setVisibility(View.VISIBLE);
        locationButton = (LocationButton) view.findViewById(R.id.locationButton);
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
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) locationButton
                .getLayoutParams();
        bottomMargin = lp.bottomMargin;
//        mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
        setSearchFacilityListener();
        /**
         * 默认使用窨井
         */
//        locationMarker.changeIcon(R.mipmap.ic_add_facility_2);
//        gridView = (GridView) view.findViewById(R.id.gridview);
//        layerAdapter = new LimitedLayerAdapter(getContext());
//        gridView.setAdapter(layerAdapter);
//        layerAdapter.setOnItemClickListener(new OnRecyclerItemClickListener<String>() {
//            @Override
//            public void onItemClick(View view, int position, String selectedData) {
//                currComponentUrl = selectedData;
//                initGLayer();
//                hideBottomSheet();
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position, String selectedData) {
//
//            }
//        });

        view.findViewById(R.id.btn_layer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDisBehavior != null
                        && dis_map_bottom_sheet != null) {
                    if (mDisBehavior.getState() == STATE_EXPANDED) {
                        mDisBehavior.setState(STATE_COLLAPSED);
                        dis_map_bottom_sheet.setVisibility(View.GONE);
                    }
                }
                Activity activity = (Activity) mContext;
                if (activity instanceof IDrawerController) {
                    IDrawerController drawerController = (IDrawerController) activity;
                    drawerController.openDrawer(new IDrawerController.OnDrawerOpenListener() {
                        @Override
                        public void onOpened(View drawerView) {
                            showLayerList();
                        }
                    });
                }
            }
        });
        dis_map_bottom_sheet = (ViewGroup) view.findViewById(R.id.dis_map_bottom_sheet);

        mDisBehavior = AnchorSheetBehavior.from(dis_map_bottom_sheet);

        mDisBehavior.setState(com.augurit.am.cmpt.widget.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
        btn_dis_prev = view.findViewById(R.id.dis_prev);
        btn_dis_next = view.findViewById(R.id.dis_next);

        btn_dis_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currIndex--;
                if (mDoorNOBeans.size() > 1) {
                    btn_dis_next.setVisibility(View.VISIBLE);
                }

                if (currIndex < 0) {
                    btn_dis_prev.setVisibility(View.GONE);
                    return;
                }
                mCurrentDoorNOBean = mDoorNOBeans.get(currIndex);
                showBottomSheet(mCurrentDoorNOBean, null);
//                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(currIndex).getS_guid());
                if (currIndex == 0) {
                    btn_dis_prev.setVisibility(View.GONE);
                }

            }
        });
        btn_dis_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currIndex++;
                if (currIndex < 0) currIndex = 0;
                if (currIndex > 0) {
                    btn_dis_prev.setVisibility(View.VISIBLE);
                }

                if (currIndex > mDoorNOBeans.size()) {
                    btn_dis_next.setVisibility(View.GONE);
                    return;
                }
                mCurrentDoorNOBean = mDoorNOBeans.get(currIndex);
//                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(currIndex).getS_guid());
                showBottomSheet(mCurrentDoorNOBean, null);
                if (currIndex == (mDoorNOBeans.size() - 1)) {
                    btn_dis_next.setVisibility(View.GONE);
                }

            }
        });

        loadMap();
        /**
         * 图例
         */
        View ll_legend = view.findViewById(R.id.ll_legend);
        ll_legend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLegendPresenter();
                if (layerPresenter != null) {
                    legendPresenter.showLegends(layerPresenter.getService().getVisibleLayerInfos());
                } else {
                    legendPresenter.showLegends();
                }

            }
        });

        btn_add_hu = view.findViewById(R.id.btn_add);
        btn_cancel_hu = view.findViewById(R.id.btn_cancel);
        btn_cancel_hu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                if (btn_cancel_well != null) {
                    btn_cancel_well.performClick();
                }
                mMapView.getCallout().hide();
                btn_add_hu.setVisibility(View.VISIBLE);
                btn_cancel_hu.setVisibility(View.GONE);
                initGLayer();
                hideBottomSheet();
//                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });

        btn_add_hu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapMeasureView.stopMeasure();
                boolean locate = false;
                if (btn_cancel_well.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_cancel_well.performClick();
                }
                if (btn_cancel_pai.getVisibility() == View.VISIBLE) {
                    btn_cancel_pai.performClick();
                }
                if (btn_cancel_hu.getVisibility() == View.VISIBLE) {
                    btn_cancel_hu.performClick();
                }
                if (ifFirstAdd) {
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "移动地图选择新增排水户的位置");
                    ifFirstAdd = false;
                }

                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_add_facility_2);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                btn_add_hu.setVisibility(View.GONE);
                btn_cancel_hu.setVisibility(View.VISIBLE);
                hideBottomSheet();
                initGLayer();
                setAddNewFacilityListener();
                //下面的代码不能在调用 setAddNewFacilityListener 方法前调用，
                // 因为 addModeSelectLocationTouchListener 和 addModeCalloutSureButtonClickListener 未初始化
                if (locate) {
                    mSelectDoorNOTouchListener.locate();
                }
            }
        });

        btn_add_well = view.findViewById(R.id.btn_edit);
        btn_cancel_well = view.findViewById(R.id.btn_edit_cancel);
        btn_add_well.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapMeasureView.stopMeasure();
                boolean locate = false;
                if (btn_cancel_well.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_cancel_well.performClick();
                }
                if (btn_cancel_pai.getVisibility() == View.VISIBLE) {
                    btn_cancel_pai.performClick();
                }
                if (btn_cancel_hu.getVisibility() == View.VISIBLE) {
                    btn_cancel_hu.performClick();
                }
                if (ifFirstAdd) {
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "移动地图选择新增井的位置");
                    ifFirstAdd = false;
                }

                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_add_facility_2);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                btn_add_well.setVisibility(View.GONE);
                btn_cancel_well.setVisibility(View.VISIBLE);
                hideBottomSheet();
                initGLayer();
                setAddNewWellFacilityListener();
                //下面的代码不能在调用 setAddNewFacilityListener 方法前调用，
                // 因为 addModeSelectLocationTouchListener 和 addModeCalloutSureButtonClickListener 未初始化
                if (locate) {
                    addModeSelectLocationTouchListener.locate();
                }

            }
        });
        btn_cancel_well.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_add_well.setVisibility(View.VISIBLE);
                btn_cancel_well.setVisibility(View.GONE);
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }

                mMapView.getCallout().hide();
                hideBottomSheet();
                initGLayer();
                //1
//                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });

        //我的上报图层按钮
        final TextView tv_my_upload_layer = (TextView) view.findViewById(R.id.tv_my_upload_layer);
        final SwitchCompat myUploadIv = (SwitchCompat) view.findViewById(R.id.iv_my_upload_layer);
//        myUploadLayerBtn = view.findViewById(R.id.ll_my_upload_layer);
//        myUploadLayerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (mDisBehavior != null
//                        && dis_map_bottom_sheet != null) {
//                    if (mDisBehavior.getState() == STATE_EXPANDED) {
//                        mDisBehavior.setState(STATE_COLLAPSED);
//                        dis_map_bottom_sheet.setVisibility(View.GONE);
//                    }
//                }
//                if (ifMyUploadLayerVisible) {
//                    myUploadIv.setChecked(false);
//                    tv_my_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.invisible_state_text_color, null));
//                    ifMyUploadLayerVisible = false;
//                } else {
//                    myUploadIv.setChecked(true);
//                    tv_my_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
//                    ifMyUploadLayerVisible = true;
//                }
//
//                if (layerPresenter != null) {
//                    layerPresenter.changeLayerVisibility(PatrolLayerPresenter.MY_UPLOAD_LAYER_NAME, ifMyUploadLayerVisible);
//                }
//            }
//        });

        //数据上报图层按钮
        final TextView tv_upload_layer = (TextView) view.findViewById(R.id.tv_upload_layer);
        final SwitchCompat uploadIv = (SwitchCompat) view.findViewById(R.id.iv_upload_layer);
        //注册当图层可见度发生改变时的回调
        if (layerPresenter != null) {
            layerPresenter.registerLayerVisibilityChangedListener(new PatrolLayerPresenter.OnLayerVisibilityChangedListener() {
                @Override
                public void changed(boolean visible, LayerInfo layerInfo) {
                    if (!visible && layerInfo.getLayerName().contains(PatrolLayerPresenter.LOCAL_UPLOAD_LAYER_NAME)) {
                        //不可见
                        //  myUploadIv.setImageResource(R.drawable.ic_upload_data_normal2);
                        uploadIv.setChecked(false);
                        tv_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.invisible_state_text_color, null));
                    } else if (visible && layerInfo.getLayerName().contains(PatrolLayerPresenter.LOCAL_UPLOAD_LAYER_NAME)) {
                        //可见
                        uploadIv.setChecked(true);
                        tv_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
                    } else if (!visible && layerInfo.getLayerName().contains(PatrolLayerPresenter.MY_UPLOAD_LAYER_NAME)) {
                        //不可见
                        myUploadIv.setChecked(false);
                        tv_my_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.invisible_state_text_color, null));
                        ifMyUploadLayerVisible = false;
                    } else if (visible && layerInfo.getLayerName().contains(PatrolLayerPresenter.MY_UPLOAD_LAYER_NAME)) {
                        //可见
                        myUploadIv.setChecked(true);
                        tv_my_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
                        ifMyUploadLayerVisible = true;
                    }
                }
            });
        }

        btn_cancel_pai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                mMapView.getCallout().hide();
                btn_add_pai.setVisibility(View.VISIBLE);
                btn_cancel_pai.setVisibility(View.GONE);
                initGLayer();
//                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });

        btn_add_pai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapMeasureView.stopMeasure();
                boolean locate = false;
                if (btn_cancel_pai.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_cancel_pai.performClick();
                }
                if (btn_cancel_well.getVisibility() == View.VISIBLE) {
                    btn_cancel_well.performClick();
                }
                if (btn_cancel_hu.getVisibility() == View.VISIBLE) {
                    btn_cancel_hu.performClick();
                }
                if (ifFirstAdd) {
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "移动地图选择新增设施的位置");
                    ifFirstAdd = false;
                }

                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_add_facility_2);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                btn_add_pai.setVisibility(View.GONE);
                btn_cancel_pai.setVisibility(View.VISIBLE);
                hideBottomSheet();
                initGLayer();
                setAddNewDoorNoListener();
                //下面的代码不能在调用 setAddNewFacilityListener 方法前调用，
                // 因为 addModeSelectLocationTouchListener 和 addModeCalloutSureButtonClickListener 未初始化
                if (locate) {
                    addModeSelectLocationTouchListener.locate();
                }

            }
        });
        initBottomSheetView();

        sewerageItemPresenter.setIGetFail(new SewerageItemPresenter.IGetFail() {
            @Override
            public void onFail(String code) {
                if (pd != null) {
                    pd.dismiss();
                }
                ToastUtil.shortToast(getContext(), code);
            }
        });

        initPopList();
    }

    private void setSearchFacilityListener() {
        if (editModeSelectLocationTouchListener == null) {
            editModeSelectLocationTouchListener = new SelectLocationTouchListener(mContext,
                    mMapView, locationMarker, false, null) {
                @Override
                public boolean onDragPointerUp(MotionEvent from, MotionEvent to) {
                    if (locationButton != null && locationButton.ifLocating()) {
                        //4---1
                        locationButton.setStateNormal();
                    }
                    isSelected = false;
                    return super.onDragPointerMove(from, to);
                }

                @Override
                public boolean onSingleTap(MotionEvent point) {
                    locationMarker.setVisibility(View.VISIBLE);
                    initGLayer();
                    hideBottomSheet();
                    isSelected = false;
                    return true;
                }
            };
        }

        if (editModeCalloutSureButtonClickListener == null) {
            //查询设施模式下，点击callout中的确定按钮时的回调
            editModeCalloutSureButtonClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSelected) {
                        // 已选中状态，点击确定直接跳转
                        final DrainageUnitListBean bean = new DrainageUnitListBean(Collections.singletonList(adptDatas.getSelectedItem()));
                        EventBus.getDefault().post(bean);
                        onBackPressed();
                    } else {
                        double scale = mMapView.getScale();
                        if (scale > LayerUrlConstant.MIN_QUERY_SCALE) {
                            ToastUtil.shortToast(getContext(), "请先放大到可以看到设施的级别");
                            return;
                        }

                        LocationInfo locationInfo = editModeSelectLocationTouchListener.getLoationInfo();
                        if (locationInfo.getDetailAddress() == null && locationInfo.getPoint() == null) {
                            ToastUtil.shortToast(mContext, "请重新选择位置");
                            return;
                        }

                        double x = locationInfo.getPoint().getX();
                        double y = locationInfo.getPoint().getY();

                        if (scale < LayerUrlConstant.MIN_QUERY_SCALE) {
//                        queryPshInfo(x, y);
                            queryDrainageUnitInfo(x, y);
                        }
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
                getContext().getResources().getDrawable(R.drawable.old_map_point));
        pictureMarkerSymbol.setOffsetY(16);
        Graphic graphic = new Graphic(mDestinationOrLastTimeSelectLocation, pictureMarkerSymbol);
        mOriginGraphicLayer.addGraphic(graphic);
        mOriginGraphicLayer.setSelectedGraphics(new int[]{graphic.getUid()}, true);
    }

    private void queryDoorNo(final String keyword) {
        if (pd == null) {
            pd = new ProgressDialog(getContext());
        }
        pd.setMessage("正在查询排水户...");
        if (!pd.isShowing()) {
            pd.show();
        }
        currIndex = 0;
        mDoorNOBeans.clear();
        ll_next_and_prev_container.setVisibility(View.INVISIBLE);
        mCurrentDoorNOBean = null;

        initComponentService();
        initmUploadDoorNoService();
        if (true) {
            mUploadDoorNoService.queryObjectIdByKeyword(keyword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<QueryIdBeanResult>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            queryResultByObjectIds(keyword, new ArrayList<String>());
                        }

                        @Override
                        public void onNext(QueryIdBeanResult result) {

                            if (result.getCode() == 200) {
                                queryResultByObjectIds(keyword, result.getRows());
                            } else {
                                queryResultByObjectIds(keyword, new ArrayList<String>());
                            }
                        }
                    });
        }
    }

    private void queryResultByObjectIds(String keyword, List<String> objectIds) {
        componentService.queryPshComponentsForObjectId(keyword, objectIds, new Callback2<List<PSHHouse>>() {
            @Override
            public void onSuccess(List<PSHHouse> components) {
                if (pd != null) {
                    pd.dismiss();
                }
                if (ListUtil.isEmpty(components)) {
                    ToastUtil.shortToast(getContext(), "未查询到该排水户");
                    return;
                }
//                    mDoorNOBeans = new ArrayList<DoorNOBean>();
                mDoorNOBeans.addAll(components);
                if (ListUtil.isEmpty(mDoorNOBeans)) {
                    initGLayer();
                    if (pd != null) {
                        pd.dismiss();
                    }
                    ToastUtil.shortToast(getContext(), "未查询到该排水户");
                    return;
                }
                tv_address.setText(StringUtil.getNotNullString(mDoorNOBeans.get(0).getAddr(), ""));
                pb_loading.showLoading();
                item1.setVisibility(View.GONE);
                item2.setVisibility(View.GONE);
//                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(0).getS_guid());
            }

            @Override
            public void onFail(Exception error) {
                if (pd != null) {
                    pd.dismiss();
                }
                ToastUtil.shortToast(getContext(), "未查询到该排水户数据");
            }
        });
    }

    private void queryResultByPsh(String keyword) {
        ll_next_and_prev_container.setVisibility(View.INVISIBLE);
        btn_dis_next.setVisibility(View.GONE);
        btn_dis_prev.setVisibility(View.GONE);
        currIndex = 0;
        initComponentService();
        componentService.queryPshComponentsForWord(keyword, new Callback2<List<PSHHouse>>() {
            @Override
            public void onSuccess(List<PSHHouse> components) {
                if (pd != null) {
                    pd.dismiss();
                }
                if (ListUtil.isEmpty(components)) {
                    ToastUtil.shortToast(getContext(), "未查询到该排水户");
                    return;
                }
//                    mDoorNOBeans = new ArrayList<DoorNOBean>();
                mDoorNOBeans.clear();
                mDoorNOBeans.addAll(components);
                if (ListUtil.isEmpty(mDoorNOBeans)) {
                    initGLayer();
                    if (pd != null) {
                        pd.dismiss();
                    }
                    ToastUtil.shortToast(getContext(), "未查询到该排水户");
                    return;
                }
                tv_address.setText(StringUtil.getNotNullString(mDoorNOBeans.get(0).getAddr(), ""));
                item1.setVisibility(View.GONE);
                item2.setVisibility(View.GONE);
                showOnBottomSheet(mDoorNOBeans, null);
//                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(0).getS_guid());
            }

            @Override
            public void onFail(Exception error) {
                if (pd != null) {
                    pd.dismiss();
                }
                ToastUtil.shortToast(getContext(), "未查询到该排水户");
            }
        });
    }

    private void setAddNewDoorNoListener() {
        if (addModeDoorSelectLocationTouchListener == null) {
            addModeDoorSelectLocationTouchListener = new SelectLocationTouchListener(mContext,
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

        if (addDoorCalloutSureButtonClickListener == null) {
            addDoorCalloutSureButtonClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //隐藏callout
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().animatedHide();
                    }

                    //恢复点击事件
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                    //隐藏marker
                    locationMarker.setVisibility(View.GONE);

                    LocationInfo locationInfo = addModeDoorSelectLocationTouchListener.getLoationInfo();
                    if (locationInfo.getDetailAddress() == null || locationInfo.getPoint() == null) {
                        ToastUtil.shortToast(mContext, "请重新选择位置");
                        return;
                    }

                    btn_add_pai.setVisibility(View.VISIBLE);
                    btn_cancel_pai.setVisibility(View.GONE);
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());

                    Intent intent = new Intent(mContext, PSHUploadNewDoorNoActivity.class);
                    intent.putExtra("detailAddress", locationInfo.getDetailAddress());
                    intent.putExtra("x", locationInfo.getPoint().getX());
                    intent.putExtra("y", locationInfo.getPoint().getY());
                    intent.putExtra("isfromQuryAddressMapFragmnet", true);
                    hideCallout();
                    getActivity().startActivityForResult(intent, 124);
                }
            };
        }

        /**
         * 气泡中“确定”按钮点击事件
         */
        addModeDoorSelectLocationTouchListener.setCalloutSureButtonClickListener(addDoorCalloutSureButtonClickListener);
        mMapView.setOnTouchListener(addModeDoorSelectLocationTouchListener);
    }

    //手动隐藏
    private void hideCallout() {
        Callout callout = mMapView.getCallout();
        if (null != callout && callout.isShowing()) {
            mMapView.getCallout().hide();
        }
    }


    private void startgetPosition() {
        final BaiduLocationManager baiduLocationManager = new BaiduLocationManager(getActivity());
        baiduLocationManager.startLocate(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LocationInfo lastLocation = baiduLocationManager.getLastLocation();
                if (lastLocation != null) {
                    baiduLocationManager.stopLocate();
                    MyApplication.X = lastLocation.getPoint().getX();
                    MyApplication.Y = lastLocation.getPoint().getY();
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

    private void showOnBottomSheet(List<PSHHouse> beans, BuildInfoBySGuid.DataBean buildInfoBySGuids) {
        if (beans.size() > 1) {
            ll_next_and_prev_container.setVisibility(View.VISIBLE);
            btn_dis_next.setVisibility(View.VISIBLE);
        }

        //隐藏marker
//        locationMarker.setVisibility(View.GONE);
        initGLayer();
        Geometry geometry = null;
        mCurrentDoorNOBean = beans.get(0);
        if (beans.get(0) != null) {
            geometry = new Point(beans.get(0).getX(), beans.get(0).getY());
            showBottomSheet(beans.get(0), buildInfoBySGuids != null ? buildInfoBySGuids : null);
        }
        if (geometry != null) {
            drawGeometry(geometry, mGLayer, true, true);
        }
    }

    /**
     * 设施部件
     * 加载设施信息，显示中底部BottomSheet中
     */
    private void showBottomSheet(final PSHHouse doorNOBean, BuildInfoBySGuid.DataBean buildInfoBySGuid) {

        dis_map_bottom_sheet.setVisibility(View.VISIBLE);
        if (mDoorNOBeans.size() > 1) {
            ll_next_and_prev_container.setVisibility(View.VISIBLE);
        }
        mDisBehavior.setState(com.augurit.am.cmpt.widget.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
        //initGLayer();
        mCurrentDoorNOBean = doorNOBean;
        initGLayer();
        final Point centerPt = new Point(doorNOBean.getX(), doorNOBean.getY());
        if (centerPt != null) {
            drawGeometry(centerPt, mGLayer, true, true);
        }
        requestLocation2(centerPt, mMapView.getSpatialReference(), new Callback1<DetailAddress>() {
            @Override
            public void onCallback(DetailAddress detailAddress) {
                detailAddress.setX(centerPt.getX());
                detailAddress.setY(centerPt.getY());
                componentDetailAddress = detailAddress;
            }
        });
        initBottomSheetText(doorNOBean, buildInfoBySGuid);
        getView().findViewById(R.id.door_detail_btn).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        btn_add_hu.setVisibility(View.VISIBLE);
//                        btn_cancel_hu.setVisibility(View.GONE);
//                        if (locationMarker != null) {
//                            locationMarker.setVisibility(View.GONE);
//                        }
//                        Intent intent = new Intent(mContext, EventHouseListActivity.class);
//                        if (componentDetailAddress == null) {
//                            intent.putExtra("detailAddress", componentDetailAddress);
//                        }
//                        intent.putExtra("point", centerPt);
//                        hideCallout();
//                        startActivity(intent);
                        Point point = new Point(doorNOBean.getX(), doorNOBean.getY());
                        EventBus.getDefault().post(new SelectHouseFinishEvent(point, doorNOBean));
                        ((Activity) mContext).finish();
                    }
                });
    }

    /**
     * bottomSheetLayout的显示样式
     *
     * @param doorNOBean
     * @param buildInfoBySGuid
     */
    private void initBottomSheetText(final PSHHouse doorNOBean, BuildInfoBySGuid.DataBean buildInfoBySGuid) {
        tv_address.setText(StringUtil.getNotNullString(doorNOBean.getName(), ""));
        TextView tv_house_Property = (TextView) mView.findViewById(R.id.tv_house_Property);
        TextView tv_floor = (TextView) mView.findViewById(R.id.tv_floor);

        StringBuilder propertySB = new StringBuilder();
        StringBuilder floorSB = new StringBuilder();
//        String houseProperty = buildInfoBySGuid.getHouseProperty();
//        String structure = buildInfoBySGuid.getStructure();
//        String houseType = buildInfoBySGuid.getHouseType();
//        String houseUse = buildInfoBySGuid.getHouseUse();
//
//        boolean empty1 = TextUtils.isEmpty(houseProperty) || TextUtils.isEmpty(houseProperty.trim());
//        boolean empty2 = TextUtils.isEmpty(structure) || TextUtils.isEmpty(structure.trim());
//        boolean empty3 = TextUtils.isEmpty(houseType) || TextUtils.isEmpty(houseType.trim());
//        boolean empty4 = TextUtils.isEmpty(houseUse) || TextUtils.isEmpty(houseUse.trim());
//
//        propertySB.append(empty1 ? "" : houseProperty).append("|")
//                .append(empty2 ? "" : structure).append("|")
//                .append(empty3 ? "" : houseType).append("|")
//                .append(empty4 ? "" : houseUse).append("|");
//
//        String str = propertySB.toString();
//        str = str.replaceAll("\\|\\|\\|\\|", "\\|").replaceAll("\\|\\|\\|", "\\|").replaceAll("\\|\\|", "\\|");
//        //去掉结尾|
//        str = str.lastIndexOf("|") == str.length() - 1 ? str.substring(0, str.length() - 1) : str;
//        //去掉开头|
//        str = str.indexOf("|") == 0 ? str.substring(1) : str;
//        tv_house_Property.setText(str.replaceAll("\\|", "\\ | "));
//
//        String floor = buildInfoBySGuid.getFloor();
//        String sets = buildInfoBySGuid.getSets();
//
//        boolean empty5 = TextUtils.isEmpty(floor) || TextUtils.isEmpty(floor.trim());
//        boolean empty6 = TextUtils.isEmpty(sets) || TextUtils.isEmpty(sets.trim());
//
//        floorSB.append(empty5 ? "" : "总层数:" + floor).append("|")
//                .append(empty6 ? "" : "总套数:" + sets).append("|");
//
//        String str1 = floorSB.toString();
//        str1 = str1.replaceAll("\\|\\|\\|", "\\|").replaceAll("\\|\\|", "\\|");
//        str1 = str1.lastIndexOf("|") == str1.length() - 1 ? str1.substring(0, str1.length() - 1) : str1;
//        tv_floor.setText(str1.replaceAll("\\|", "\\ | "));
    }

    private void initBottomSheetView() {
        ll_next_and_prev_container = (ViewGroup) dis_map_bottom_sheet.findViewById(R.id.ll_next_and_prev_container);
        tv_address = (TextView) dis_map_bottom_sheet.findViewById(R.id.tv_address);
    }

    private void initLegendPresenter() {
        if (legendPresenter == null) {
            ILegendView legendView = new PSHImageLegendView(mContext);
            legendPresenter = new LegendPresenter(legendView, new OnlineLegendService(mContext));
        }

    }

    public void loadMap() {
        final IDrawerController drawerController = (IDrawerController) mContext;
        PatrolLayerView2 patrolLayerView2 = new PatrolLayerView2(mContext, mMapView, drawerController.getDrawerContainer());
        layerView = patrolLayerView2;
        layerPresenter = new DrainageUnitSeweragelayerPresenter(layerView, new SewerageLayerService(mContext.getApplicationContext()));
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


        ViewGroup ll_measure_container = patrolLayerView2.getBottomLayout();
        mMapMeasureView = new MapMeasureView(mContext, mMapView, ll_measure_container,
                ll_topbar_container, ll_tool_container);
        mMapMeasureView.setOnStartCallback(new Callback4() {
            @Override
            public void before() {
                if (btn_cancel_hu.getVisibility() == View.VISIBLE) {
                    btn_cancel_hu.performClick();
                }
                if (btn_cancel_well.getVisibility() == View.VISIBLE) {
                    btn_cancel_well.performClick();
                }
            }

            @Override
            public void after() {
                drawerController.closeDrawer();
            }
        });
        mMapMeasureView.setOnStopCallback(new Callback4() {
            @Override
            public void before() {

            }

            @Override
            public void after() {
//                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });
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
                final Point point = new Point(location.getLongitude(), location.getLatitude());
                MyApplication.X = point.getX();
                MyApplication.Y = point.getY();
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

    public void onBackPressed() {
        if (dis_map_bottom_sheet.getVisibility() == View.GONE) {
            ((Activity) mContext).finish();
            return;
        }

        if (mDisBehavior != null
                && dis_map_bottom_sheet != null) {
            if (mDisBehavior.getState() == STATE_EXPANDED) {
                mDisBehavior.setState(STATE_COLLAPSED);
            } else if (mDisBehavior.getState() == STATE_COLLAPSED
                    || mDisBehavior.getState() == AnchorSheetBehavior.STATE_ANCHOR) {
                initGLayer();
                dis_map_bottom_sheet.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapMeasureView.stopMeasure();
        mMapView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.unpause();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_MAP_STATE, mMapView.retainState());
    }

    /**
     * 请求百度地址
     *
     * @param point
     * @param callback1
     */
    private void requestLocation2(Point point, SpatialReference spatialReference, final Callback1<DetailAddress> callback1) {
        SelectLocationService selectLocationService = new SelectLocationService(mContext, Locator.createOnlineLocator());
        selectLocationService.parseLocation(new LatLng(point.getY(), point.getX()), spatialReference)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DetailAddress>() {
                    @Override
                    public void call(DetailAddress detailAddress) {
                        if (callback1 != null) {
                            callback1.onCallback(detailAddress);
                        }
                    }
                });
    }

    /**
     * @return
     */
    private SelectLocationTouchListener getDefaultMapOnTouchListener() {
        if (defaultMapOnTouchListener == null) {
            defaultMapOnTouchListener = new DefaultTouchListener(mContext, mMapView, locationMarker, new View.OnClickListener() {
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
                }

                @Override
                public void onAddressChanged(DetailAddress detailAddress) {

                }
            });
        }
        return defaultMapOnTouchListener;
    }

    @Override
    public void getBuildInfBySGuid(BuildInfoBySGuid buildInfoBySGuid) {
        item1.setVisibility(View.VISIBLE);
        item2.setVisibility(View.VISIBLE);
        pb_loading.showContent();

        if (mDoorNOBeans.size() > 1) {
            ll_next_and_prev_container.setVisibility(View.VISIBLE);
            if (null != buildInfoBySGuid && buildInfoBySGuid.getCode() == 200 && buildInfoBySGuid.getData() != null) {
                showBottomSheet(mDoorNOBeans.get(currIndex), buildInfoBySGuid.getData());
            } else {
                //显示默认
                showBottomSheet(mDoorNOBeans.get(currIndex), null);
            }
        } else {
            if (buildInfoBySGuid.getCode() == 200 && buildInfoBySGuid.getData() != null) {
                showOnBottomSheet(mDoorNOBeans, buildInfoBySGuid.getData());
            } else {
                //显示默认
                showOnBottomSheet(mDoorNOBeans, null);
            }
        }
    }

    /**
     * 点击地图后查询设施
     *
     * @param x
     * @param y
     */
    private void queryDistribute(final double x, final double y) {
        if (pd == null) {
            pd = new ProgressDialog(getContext());
        }
        pd.setMessage("正在查询排水户...");
        if (!pd.isShowing()) {
            pd.show();
        }

        ll_next_and_prev_container.setVisibility(View.INVISIBLE);
        currIndex = 0;
        final Point point = new Point(x, y);
        mCurrentDoorNOBean = null;
        mSewerageLayerService.setQueryByWhere("1=1");
        mSewerageLayerService.queryDoorDataInfo(point, mMapView.getSpatialReference(), mMapView.getResolution(), new Callback2<List<DoorNOBean>>() {
            @Override
            public void onSuccess(List<DoorNOBean> doorNOBeans) {
                pd.dismiss();
                if (ListUtil.isEmpty(doorNOBeans) || (doorNOBeans.size() == 1 && doorNOBeans.get(0).getDzdm() == null && doorNOBeans.get(0).getAddress() == null)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.iconLongToast(getActivity(), R.mipmap.ic_alert_yellow, "请选择排水户！");
                        }
                    });
                    return;
                }
                mDoorNOBeans.clear();
//                mDoorNOBeans.addAll(doorNOBeans);
//                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(0).getS_guid());
                locationMarker.setVisibility(View.GONE);
            }

            @Override
            public void onFail(Exception error) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.iconLongToast(getContext(), R.mipmap.ic_alert_yellow, "请选择排水户！");
                    }
                });
                pd.dismiss();
            }
        });
    }

    /**
     * 点击地图后查询设施
     *
     * @param x
     * @param y
     */
    private void queryPshInfo(final double x, final double y) {
        if (pd == null) {
            pd = new ProgressDialog(getContext());
        }
        pd.setMessage("正在查询排水户...");
        if (!pd.isShowing()) {
            pd.show();
        }

        ll_next_and_prev_container.setVisibility(View.INVISIBLE);
        btn_dis_next.setVisibility(View.GONE);
        btn_dis_prev.setVisibility(View.GONE);
        currIndex = 0;
        final Point point = new Point(x, y);
        mCurrentDoorNOBean = null;
        mSewerageLayerService.setQueryByWhere("1=1");
        mSewerageLayerService.queryPshDataInfo(point, mMapView.getSpatialReference(), mMapView.getResolution(), new Callback2<List<PSHHouse>>() {
            @Override
            public void onSuccess(List<PSHHouse> doorNOBeans) {
                pd.dismiss();
                if (ListUtil.isEmpty(doorNOBeans)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.iconLongToast(getActivity(), R.mipmap.ic_alert_yellow, "请选择排水户！");
                        }
                    });
                    return;
                }
                mDoorNOBeans.clear();
                mDoorNOBeans.addAll(doorNOBeans);
                locationMarker.setVisibility(View.GONE);
                showOnBottomSheet(mDoorNOBeans, null);
            }

            @Override
            public void onFail(Exception error) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.iconLongToast(getContext(), R.mipmap.ic_alert_yellow, "请选择排水户！");
                    }
                });
                pd.dismiss();
            }
        });
    }

    /**
     * 点击地图后查询设施
     *
     * @param x
     * @param y
     */
    private void queryDrainageUnitInfo(final double x, final double y) {
        if (pd == null) {
            pd = new ProgressDialog(getContext());
        }
        pd.setMessage("正在查询排水单元...");
        if (!pd.isShowing()) {
            pd.show();
        }

        ll_next_and_prev_container.setVisibility(View.INVISIBLE);
        btn_dis_next.setVisibility(View.GONE);
        btn_dis_prev.setVisibility(View.GONE);
        currIndex = 0;
        final Point point = new Point(x, y);
        mCurrentDoorNOBean = null;
        mSewerageLayerService.setQueryByWhere("1=1");
        mSewerageLayerService.queryDrainageUnitInfo(point, mMapView.getSpatialReference(), mMapView.getResolution(), new Callback2<List<DrainageUnit>>() {
            @Override
            public void onSuccess(List<DrainageUnit> doorNOBeans) {
                pd.dismiss();
                if (ListUtil.isEmpty(doorNOBeans)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.iconLongToast(getActivity(), R.mipmap.ic_alert_yellow, "请选择排水单元！");
                        }
                    });
                    return;
                }
                final DrainageUnitListBean bean = new DrainageUnitListBean(doorNOBeans);
                if (doorNOBeans.size() == 1) {
                    locationMarker.setVisibility(View.GONE);
                    Collections.reverse(doorNOBeans);
                    EventBus.getDefault().post(bean);
                    onBackPressed();
                } else {
                    // TODO 弹出选择列表，手动选择
                    final DrainageUnit drainageUnit = doorNOBeans.get(0);
                    adptDatas.update(doorNOBeans);
                    isSelected = true;
                    center2Map(drainageUnit);
                    showPop();
                }
            }

            @Override
            public void onFail(Exception error) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.iconLongToast(getContext(), R.mipmap.ic_alert_yellow, "请选择排水单元！");
                    }
                });
                pd.dismiss();
            }
        });
    }

    @NonNull
    private void initComponentService() {
        if (componentService == null) {
            componentService = new ComponentService(mContext.getApplicationContext());
        }
    }

    private void initmUploadDoorNoService() {
        if (mUploadDoorNoService == null) {
            mUploadDoorNoService = new PSHMyUploadDoorNoService(mContext);
        }
    }

    /**
     * 在地图上画图形
     *
     * @param graphicsLayer
     * @param ifRemoveAll   在添加前是否移除掉所有
     * @param geometry
     */
    private void drawGeometry(final Geometry geometry, GraphicsLayer graphicsLayer, boolean ifRemoveAll, final boolean ifCenter) {

        if (graphicsLayer == null || geometry == null) {
            return;
        }
        Symbol symbol = null;
        SimpleFillSymbol simpleFillSymbol = null;
        switch (geometry.getType()) {
            case LINE:
            case POLYLINE:
                symbol = new SimpleLineSymbol(Color.RED, 5);
                break;
            case POLYGON:
                simpleFillSymbol = new SimpleFillSymbol(Color.RED, SimpleFillSymbol.STYLE.SOLID);
                simpleFillSymbol.setAlpha(100);
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
        }
        if (simpleFillSymbol != null) {
            Graphic graphic = new Graphic(geometry, simpleFillSymbol);
            graphicsLayer.addGraphic(graphic);
        }
        final SimpleFillSymbol finalSimpleFillSymbol = simpleFillSymbol;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ifCenter && finalSimpleFillSymbol == null) {
                    mMapView.centerAt(GeometryUtil.getGeometryCenter(geometry), true);
                } else {
                    mMapView.setExtent(geometry);
                }
            }
        }, 200);

    }

    private void initGLayer() {
        if (mGLayer == null) {
            mGLayer = new GraphicsLayer();
            mMapView.addLayer(mGLayer);
        } else {
            mGLayer.removeAll();
        }
    }


    @NonNull
    private Symbol getPointSymbol() {
        Drawable drawable = ResourcesCompat.getDrawable(mContext.getResources(), com.augurit.agmobile.patrolcore.R.mipmap.ic_select_location, null);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(getContext(), drawable);// xjx 改为兼容api19的方式获取drawable
        pictureMarkerSymbol.setOffsetY(16);
        return pictureMarkerSymbol;
    }

    private void hideBottomSheet() {
        dis_map_bottom_sheet.setVisibility(View.GONE);
        final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) locationButton
                .getLayoutParams();
        lp.bottomMargin = bottomMargin;
        locationButton.setLayoutParams(lp);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectComponent(SelectComponentEvent selectComponentEvent) {
        Component component = selectComponentEvent.getComponent();
        currComponentUrl = component.getLayerUrl();
//        layerAdapter.notifyDataSetChanged(LayerUrlConstant.getIndexByUnknowsLayerUrl(currComponentUrl));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (tableViewManager != null) {
            tableViewManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == 123
                && resultCode == 123) {
            initGLayer();
            hideBottomSheet();
        }

        Layer[] layers = mMapView.getLayers();
        for (Layer layer : layers) {
            if (layer instanceof ArcGISDynamicMapServiceLayer) {
                ((ArcGISDynamicMapServiceLayer) layer).refresh();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.recycle();
        if (layerPresenter != null) {
            layerPresenter.destroy();
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

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        LayersService.clearInstance();
    }

    private void setAddNewWellFacilityListener() {
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

        if (addWellCalloutSureButtonClickListener == null) {
            addWellCalloutSureButtonClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //隐藏callout
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().animatedHide();
                    }

                    //恢复点击事件
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                    //隐藏marker
                    locationMarker.setVisibility(View.GONE);

                    LocationInfo locationInfo = addModeSelectLocationTouchListener.getLoationInfo();
                    if (locationInfo.getDetailAddress() == null || locationInfo.getPoint() == null) {
                        ToastUtil.shortToast(mContext, "请重新选择位置");
                        return;
                    }

                    btn_add_hu.setVisibility(View.VISIBLE);
                    btn_cancel_hu.setVisibility(View.GONE);
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                    Intent intent = new Intent(mContext, PSHUploadNewFacilityActivity.class);
                    intent.putExtra("detailAddress", locationInfo.getDetailAddress());
                    intent.putExtra("x", locationInfo.getPoint().getX());
                    intent.putExtra("y", locationInfo.getPoint().getY());
                    hideCallout();
                    startActivity(intent);
                    btn_add_well.setVisibility(View.VISIBLE);
                    btn_cancel_well.setVisibility(View.GONE);
                }
            };
        }

        /**
         * 气泡中“确定”按钮点击事件
         */
        addModeSelectLocationTouchListener.setCalloutSureButtonClickListener(addWellCalloutSureButtonClickListener);
        mMapView.setOnTouchListener(addModeSelectLocationTouchListener);
    }

    private void setAddNewFacilityListener() {
        if (mSelectDoorNOTouchListener == null) {
            mSelectDoorNOTouchListener = new SelectDoorNOTouchListener(mContext,
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

                    //恢复点击事件
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                    //隐藏marker
                    locationMarker.setVisibility(View.GONE);

                    DoorNOBean bean = mSelectDoorNOTouchListener.getDoorBean();
                    if (bean == null) {
                        ToastUtil.shortToast(mContext, "请移动到有排水户的地方！");
                        return;
                    }

                    btn_add_hu.setVisibility(View.VISIBLE);
                    btn_cancel_hu.setVisibility(View.GONE);
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                    Intent intent = new Intent(mContext, SewerageActivity.class);
                    intent.putExtra("doorBean", bean);
                    hideCallout();
                    startActivity(intent);
                }
            };
        }

        if (addModeCalloutOKButtonClickListener == null) {
            addModeCalloutOKButtonClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().hide();
                    }
                    initGLayer();
                    hideBottomSheet();
                    double scale = mMapView.getScale();
                    if (scale < LayerUrlConstant.MIN_QUERY_SCALE) {
                        Point point = mSelectDoorNOTouchListener.getLastSelectLocation();
                        queryPshInfo(point.getX(), point.getY());
                    }
                }
            };
        }

        /**
         * 气泡中“确定”按钮点击事件
         */
        mSelectDoorNOTouchListener.setCalloutSureButtonClickListener(addModeCalloutSureButtonClickListener);
        mSelectDoorNOTouchListener.setCalloutOKButtonClickListener(addModeCalloutOKButtonClickListener);
        mMapView.setOnTouchListener(mSelectDoorNOTouchListener);
    }


    @Subscribe
    public void onRefreshDoorDataEvent(RefreshDoorData doorData) {
//        mCurrentDoorNOBean.setISTATUE("3");
//        if (!ListUtil.isEmpty(mDoorNOBeans) && mDoorNOBeans.size() > currIndex) {
//            mDoorNOBeans.get(currIndex).setISTATUE("3");
//        }
        Layer[] layers = mMapView.getLayers();
        for (Layer layer : layers) {
            if (layer instanceof ArcGISDynamicMapServiceLayer) {
                ((ArcGISDynamicMapServiceLayer) layer).refresh();
            }
        }

    }

    @Subscribe
    public void onEventMainThread(RefreshDataListEvent event) {
        if (MyApplication.refreshListType == RefreshDataListEvent.UPDATE_MIAN_LIST) {
//            if (mCurrentDoorNOBean != null) {
//                mCurrentDoorNOBean.setISTATUE("2");
//            }
//            if (!ListUtil.isEmpty(mDoorNOBeans) && mDoorNOBeans.size() > currIndex) {
//                mDoorNOBeans.get(currIndex).setISTATUE("2");
//            }

            Layer[] layers = mMapView.getLayers();
            for (Layer layer : layers) {
                if (layer instanceof ArcGISDynamicMapServiceLayer) {
                    ((ArcGISDynamicMapServiceLayer) layer).refresh();
                }
            }
        }

    }

    @Subscribe
    public void onRefreshMyUploadListEvent(RefreshMyUploadList refreshMyUploadList) {
        if (btn_cancel_well.getVisibility() == View.VISIBLE) {
            btn_cancel_well.performClick();
        }

        if (btn_cancel_hu.getVisibility() == View.VISIBLE) {
            btn_cancel_hu.performClick();
        }
        Layer[] layers = mMapView.getLayers();
        for (Layer layer : layers) {
            if (layer instanceof ArcGISDynamicMapServiceLayer) {
                ((ArcGISDynamicMapServiceLayer) layer).refresh();
            }
        }
    }

    @Subscribe
    public void onReceivedUploadFacilityEvent(UploadFacilitySuccessEvent uploadFacilitySuccessEvent) {
        initGLayer();
        hideBottomSheet();
        if (btn_cancel_well.getVisibility() == View.VISIBLE) {
            btn_cancel_well.performClick();
        }
        if (btn_cancel_hu.getVisibility() == View.VISIBLE) {
            btn_cancel_hu.performClick();
        }
        Layer[] layers = mMapView.getLayers();
        for (Layer layer : layers) {
            if (layer instanceof ArcGISDynamicMapServiceLayer) {
                ((ArcGISDynamicMapServiceLayer) layer).refresh();
            }
        }
    }

    private void changeLayerUrlInitFailState() {
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
        changeLayerUrlInitFailState();
        if (!loadLayersSuccess && layerPresenter != null) {
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

    /**
     * The MapView's touch listener.
     */
    private class DefaultTouchListener extends SelectLocationTouchListener {

        public DefaultTouchListener(Context context, MapView view, LocationMarker locationMarker, View.OnClickListener calloutSureButtonClickListener) {
            super(context, view, locationMarker, calloutSureButtonClickListener);
        }

        @Override
        public boolean onSingleTap(final MotionEvent e) {
            handleTap(e);
            return true;
        }

        @Override
        public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
            if (locationButton != null && locationButton.ifLocating()) {
                //
                locationButton.setStateNormal();
            }
            return super.onDragPointerMove(from, to);
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
            int disvisibility = dis_map_bottom_sheet.getVisibility();
            initGLayer();
            hideBottomSheet();
            if (disvisibility == View.VISIBLE) {
                return;
            }
            double scale = mMapView.getScale();
            if (scale < LayerUrlConstant.MIN_QUERY_SCALE) {
                final Point point = mMapView.toMapPoint(e.getX(), e.getY());
//                if(ifMyUploadLayerVisible){
                if (mMapView.getCallout().isShowing()) {
                    mMapView.getCallout().animatedHide();
                }
                //隐藏marker
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                queryPshInfo(point.getX(), point.getY());
            }
        }
    }

    /**
     * 选择位置或设施后的事件回调
     *
     * @param selectHouseFinishEvent
     */
    @Subscribe
    public void onReceivedFinishedSelectEvent2(SelectHouseFinishEvent selectHouseFinishEvent) {
        ((Activity) mContext).finish();
    }

    private void initPopList() {
        adptDatas = new SimpleRecyclerViewAdapter();

        popContentView = getLayoutInflater().inflate(R.layout.drainage_view_reservoir_patrol_selected_location_pop, root, false);
        rvDatas = popContentView.findViewById(R.id.rvDatas);
        rvDatas.setAdapter(adptDatas);
        rvDatas.addOnItemTouchListener(new OnRecyclerItemClickListener(rvDatas) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                final int position = viewHolder.getLayoutPosition();
                if (position == adptDatas.getSelected()) {
                    // 点击同一个，不处理
                    return;
                }
                isSelected = true;
                adptDatas.setSelected(position);
                center2Map(adptDatas.getItem(position));
            }
        });
        rvDatas.setItemAnimator(null);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        popList = new PopupWindow(popContentView, (int) (displayMetrics.widthPixels * 0.95f), ViewGroup.LayoutParams.WRAP_CONTENT);
        popList.setOutsideTouchable(true);
        popList.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popList.setFocusable(false);
        popList.setTouchable(true);
        popList.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                adptDatas.reset();
                initGLayer();
            }
        });
    }

    private void showPop() {
        if (popList != null) {
            popList.showAtLocation(root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    private void center2Map(final DrainageUnit drainageUnit, GraphicsLayer graphicsLayer) {
        initGLayer();
        drawGeometry(drainageUnit.getGeometry(), graphicsLayer, true, true);
        mMapView.postDelayed(new Runnable() {
            @Override
            public void run() {
                editModeSelectLocationTouchListener.showCalloutMessage(drainageUnit.getName() + "", null, editModeCalloutSureButtonClickListener);
            }
        }, 1000);
    }

    private void center2Map(final DrainageUnit drainageUnit) {
        center2Map(drainageUnit, mGLayer);
    }

    private void resetCurrCallParameters() {
//        currMsg = "";
//        currOnClickListener = null;
    }

    // ==============================自定义类==============================

    private static class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<DrainageUnit> mDatas = new ArrayList<>();
        private int selectedIndex = -1;

        private int selectedTextSize = 15;
        private int unselectedTextSize = 13;
        private int selectedColor;
        private int unselectedColor;

        public SimpleRecyclerViewAdapter() {
            selectedColor = Color.parseColor("#55C0C0C0");
            unselectedColor = Color.parseColor("#FFFFFF");
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(createItemView(parent.getContext())) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder.itemView instanceof LinearLayout) {
                final LinearLayout root = (LinearLayout) holder.itemView;
                final DrainageUnit drainageUnit = mDatas.get(position);
                if (root.getChildCount() > 3) {
                    final TextView name = (TextView) root.getChildAt(0);
                    final TextView area = (TextView) root.getChildAt(1);
                    final TextView addr = (TextView) root.getChildAt(2);
                    final View divider = root.getChildAt(3);
                    name.setText("单元名称: " + drainageUnit.getName());
                    area.setText("所属区域: " + drainageUnit.getArea());
                    addr.setText("地址: " + (TextUtils.isEmpty(drainageUnit.getAddr()) ? "" : drainageUnit.getAddr()));
                    divider.setVisibility(position == mDatas.size() - 1 ? View.INVISIBLE : View.VISIBLE);
                }
                root.setBackgroundColor(position == selectedIndex ? selectedColor : unselectedColor);
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        // ==============================自定义方法==============================

        public void update(List<DrainageUnit> newDatas) {
            final int initItemCount = mDatas.size();
            mDatas.clear();
            if (newDatas == null || newDatas.size() == 0) {
                notifyItemRangeRemoved(0, initItemCount);
            } else {
                selectedIndex = 0;
                mDatas.addAll(newDatas);
                notifyItemRangeChanged(0, mDatas.size());
            }
        }

        public void setSelected(int position) {
            if (position != selectedIndex) {
                final int lastSelectedIndex = selectedIndex;
                selectedIndex = position;
                notifyItemChanged(lastSelectedIndex);
                notifyItemChanged(selectedIndex);
            }
        }

        public int getSelected() {
            return selectedIndex;
        }

        public void reset() {
            selectedIndex = -1;
        }

        public DrainageUnit getItem(int position) {
            if (position >= 0 && position < mDatas.size()) {
                return mDatas.get(position);
            } else {
                return null;
            }
        }

        public DrainageUnit getSelectedItem() {
            return getItem(getSelected());
        }

        private View createItemView(Context context) {
            final LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            final TextView name = new TextView(context);
            name.setGravity(Gravity.CENTER_VERTICAL);
            int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
            name.setPadding(i, i, 0, i);
            name.setTextSize(14);
            name.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.addView(name);
            final TextView area = new TextView(context);
            area.setGravity(Gravity.CENTER_VERTICAL);
            area.setTextSize(13);
            area.setPadding(i, i, 0, i);
            area.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.addView(area);
            final TextView addr = new TextView(context);
            addr.setGravity(Gravity.CENTER_VERTICAL);
            addr.setTextSize(13);
            addr.setPadding(i, i, 0, i);
            addr.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.addView(addr);
            final View divider = new View(context);
            divider.setBackgroundColor(Color.parseColor("#C0C0C0"));
            divider.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, i / 10));
            ll.addView(divider);
            return ll;
        }
    }

    /**
     * 点击地图后查询排水单元
     */
    private void queryDrainageUnit(String id) {
        mSewerageLayerService.setQueryByWhere("OBJECTID='" + id + "'");
        mSewerageLayerService.queryDrainageUnitInfo(new Callback2<List<DrainageUnit>>() {
            @Override
            public void onSuccess(List<DrainageUnit> drainageUserBeans) {
                // TODO 弹出选择列表，手动选择
                final DrainageUnit drainageUnit = drainageUserBeans.get(0);
                isSelected = true;
                mLastAreaLayer = new GraphicsLayer();
                mMapView.addLayer(mLastAreaLayer);
                center2Map(drainageUnit, mLastAreaLayer);
            }

            @Override
            public void onFail(Exception error) {
                isSelected = false;
            }
        });
    }
}
