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

package com.augurit.agmobile.gzpssb.fire;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.OnInitLayerUrlFinishEvent;
import com.augurit.agmobile.gzps.common.constant.LayerUrlConstant;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.selectcomponent.LimitedLayerAdapter;
import com.augurit.agmobile.gzps.common.widget.SpinnerTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.componentmaintenance.ComponetListAdapter;
import com.augurit.agmobile.gzps.componentmaintenance.SelectComponentEvent;
import com.augurit.agmobile.gzps.componentmaintenance.model.QueryFeatureSet;
import com.augurit.agmobile.gzps.componentmaintenance.service.ComponentService;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentFieldKeyConstant;
import com.augurit.agmobile.gzps.measure.view.MapMeasureView;
import com.augurit.agmobile.gzps.uploadfacility.model.CheckUploadRecordResult;
import com.augurit.agmobile.gzps.uploadfacility.model.CompleteTableInfo;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadInfo;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.uploadfacility.service.CorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.view.UploadFacilitySuccessEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.MyModifiedFacilityTableViewManager;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.RefreshMyModificationListEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.RefreshMyUploadList;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.UploadFacilityTableViewManager;
import com.augurit.agmobile.gzpssb.common.legend.PSHImageLegendView;
import com.augurit.agmobile.gzpssb.common.view.TakePhotoTableItem3;
import com.augurit.agmobile.gzpssb.fire.model.GroundfireBean;
import com.augurit.agmobile.gzpssb.fire.service.PSHFireDetailLayerService;
import com.augurit.agmobile.gzpssb.fire.service.PSHUploadFireService;
import com.augurit.agmobile.gzpssb.fire.service.SewerageFireService;
import com.augurit.agmobile.gzpssb.fire.util.Attachment2PhotoUtil;
import com.augurit.agmobile.gzpssb.seweragewell.model.WellPhoto;
import com.augurit.agmobile.gzpssb.seweragewell.presenter.PSHWelllayerPresenter;
import com.augurit.agmobile.gzpssb.uploadfacility.model.NWUploadInfo;
import com.augurit.agmobile.gzpssb.uploadfacility.model.NWUploadedFacility;
import com.augurit.agmobile.gzpssb.uploadfacility.model.PipeBean;
import com.augurit.agmobile.gzpssb.uploadfacility.service.SewerageMyUploadService;
import com.augurit.agmobile.gzpssb.uploadfacility.view.NumberItemTableItem;
import com.augurit.agmobile.gzpssb.uploadfacility.view.tranship.PipeTableViewManager;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
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
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.agmobile.patrolcore.common.table.model.TableItem;
import com.augurit.agmobile.patrolcore.common.widget.LocationButton;
import com.augurit.agmobile.patrolcore.editmap.widget.LocationMarker;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerView2;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.agmobile.patrolcore.selectlocation.service.SelectLocationService;
import com.augurit.agmobile.patrolcore.selectlocation.util.PatrolLocationManager;
import com.augurit.agmobile.patrolcore.selectlocation.view.IDrawerController;
import com.augurit.am.cmpt.common.Callback1;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.common.Callback4;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.cmpt.widget.bottomsheet.AnchorSheetBehavior;
import com.augurit.am.cmpt.widget.bottomsheet.IBottomSheetBehavior;
import com.augurit.am.cmpt.widget.searchview.util.Util;
import com.augurit.am.cmpt.widget.spinner.AMSpinner;
import com.augurit.am.fw.utils.DensityUtil;
import com.augurit.am.fw.utils.DrawableUtil;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
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
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.Symbol;
import com.esri.core.tasks.geocode.Locator;
import com.google.common.base.FinalizablePhantomReference;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.augurit.am.cmpt.widget.bottomsheet.AnchorSheetBehavior.STATE_ANCHOR;
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
public class PSHUploadFireMapFragment extends Fragment implements View.OnClickListener {
    private PipeBean originPipe;
    private double mLineLength = -1;
    private List<String> attribute = Arrays.asList("窨井", "雨水口", "排水口", "排水管道", "排水沟渠", "示意连线", "接驳连线");
    private static final String KEY_MAP_STATE = "com.esri.MapState";
    private PipeBean currentStream;
    private List<PipeBean> mAllPipeBeans = new ArrayList<>();
    private List<PipeBean> mTempPipeBeans = new ArrayList<>();
    private List<NWUploadInfo> mUploadInfos = new ArrayList<>();
    private List<Component> mNWUploadInfos = new ArrayList<>();
    private GroundfireBean bean;
    private String mGjCode;
    private LocationMarker locationMarker;
    private List<DictionaryItem> mA163;
    private TableDBService mDbService;
    private NumberItemTableItem sp_gxcd;
    private Button btn_cancel2;
    private Component componentCenter;
    private List<DictionaryItem> mA185;
    private SpinnerTableItem sp_ywlx;
    private SpinnerTableItem sp_lx;
    private Button btn_reedit;
    private SpinnerTableItem sp_gj;
    private NWUploadedFacility facilityCenter;
    private SewerageMyUploadService myUploadService;
    private PSHUploadFireService pshUploadFireService;
    /**
     * 是否处于编辑模式
     */
    private TextView tv_query_tip;
    private boolean ifInEditMode = false;
    private boolean ifAddMode = false;
    //    /**
//     * 是否执行了放大缩小操作
//     */
//    private boolean hasZoomBefore = false;
    private boolean isDrawStream = false;
    /**
     * 上次选择的位置
     */
    private Point mLastSelectedLocation = null;
    private PatrolLocationManager mLocationManager;
    /**
     * 绘制位置的图层
     */
    private GraphicsLayer mGLayerFroDrawLocation;
    private SewerageFireService sewerageFireService;
    /**
     * 是否是第一次定位，如果是，那么居中；否则，不做任何操作；
     */
    private boolean ifFirstLocate = true;
    private LocationButton locationButton;
    private LegendPresenter legendPresenter;
    private ViewGroup ll_component_list;
    private View ll_layer_url_init_fail;
    private ViewGroup ll_topbar_container; //顶部工具容器
    private ViewGroup ll_tool_container;   //左边工具容器
    View mView;

    MapView mMapView;

    private MapMeasureView mMapMeasureView;

    private ILayerView layerView;

    private PatrolLayerPresenter layerPresenter;
    private boolean loadLayersSuccess = true;

    private TextView show_all_layer;
    private GridView gridView;
    private LimitedLayerAdapter layerAdapter;
    //顶部图层列表中当前选中的设施类型对应的务URL
    private String currComponentUrl = LayerUrlConstant.newComponentUrls[0];
    private TextView tv_approval_opinion_list;
    ProgressDialog pd;
    ViewGroup map_bottom_sheet;
    ViewGroup dis_map_bottom_sheet;
    AnchorSheetBehavior mBehavior;
    AnchorSheetBehavior mDisBehavior;
    private ComponetListAdapter componetListAdapter;
    //点查后设施的简要信息布局
    private View component_intro;
    private View component_detail_ll;
    //点查后设施的详细信息布局，用了TableViewManager
    private ViewGroup component_detail_container;
    //    private ViewGroup tableItemContainer;
//    private ViewGroup approvalOpinionContainer;
    private View btn_upload;
    private ArrayList<TableItem> tableItems = null;
    private ArrayList<Photo> photoList = new ArrayList<>();
    private String projectId;
    private TableViewManager tableViewManager;
    //点查后的设施结果
    private List<Component> mComponentQueryResult = new ArrayList<>();
    private boolean hasComponent = false;
    private int currIndex = 0;
    private View btn_prev;
    private View btn_next;
    private boolean ifFirstAdd = true;
    private boolean ifFirstEdit = true;
    private View layoutBottom;
    private Context mContext;
    private String mFirstAttribute = "";
    private String mSecondAttribute = "";
    ViewGroup pipe_view;
    private View btn_edit_pipe;
    private View btn_pipe_cancel;
    private TextView mTitleTv;
    private TextView mDateTv;
    private TextView mSortTv;
    private TextView mSubtypeTv;
    private TextView mField1Tv;
    private TextView mField2Tv;
    private TextView mField3Tv;
    private View mBtn_container;
    private TextView mAddrTv;
    private View mLine2_gw;
    private TextView mTv_gc_begin;
    private TextView mTv_gc_end;
    private TextView mTv_ms_begin;
    private TextView mTv_ms_end;
    private TextView mTv_gj;
    private TextView mTv_cz;
    private View mLl_gq;
    private TextView mTv_tycd;
    private TextView mTv_pd;
    private ViewGroup map_bottom_sheet_gw;
    private View mLine3;
    private View mLine4;
    /**
     * 地图默认的事件处理
     */
    private SelectLocationTouchListener defaultMapOnTouchListener;
    private View btn_add;
    private View btn_cancel;
    private SelectLocationTouchListener addModeSelectLocationTouchListener;
    private View.OnClickListener addModeCalloutSureButtonClickListener;
    //    private View btn_edit;
//    private View btn_edit_cancel;
    private SelectLocationTouchListener editModeSelectLocationTouchListener;
    private View.OnClickListener editModeCalloutSureButtonClickListener;
    private ProgressDialog progressDialog;
    private int bottomHeight;
    private int bottomMargin;
    //    private View btn_dis_prev;
//    private View btn_dis_next;
    private int mStatus = 0;//0 寻找接驳井 1，去找另外一个门牌或者接驳井 2，选中第二个设施（门牌或者接驳井）3，取消
    private boolean isClick = false;
    private List<String> mAttribute = Arrays.asList("污水", "雨水", "雨污合流", "污水管", "雨水管");
    private View mStream_line1_gw;
    private View mTv_delete_gw;
    private TextView mTv_sure_gw;
    private TextView mTv_error_correct_gw;
    private View mStream_line_gw;
    private TextView mTv_sure;
    private boolean ifInCheckMode = false;
    private boolean ifInPipeMode = false;
    private String mTitle = "选择关联设施点或井";
    private ViewGroup nextAndPrevContainer;

    private AnchorSheetBehavior<ViewGroup> mBehavior_gw;
    /**
     * 数据上报图层是否显示
     */
    private boolean ifMyUploadLayerVisible = false;
    private boolean ifWellVisible = false;
    private ComponentService componentService;

    private DetailAddress componentDetailAddress = null;

    /**
     * 上报地图分布图层
     */
    private TextView tv_distribute_error_correct;
    private TextView tv_distribute_sure;
    private List<UploadedFacility> mUploadedFacilitys;
    private Component mCurrentComponent;
    private TextView tv_error_correct;
    //    private View btnReEdit;
//    private View btnDelete;
//    private ViewGroup ll_next_and_prev_container;
    private ViewGroup ll_table_item_container;
    private ModifiedFacility mCurrentModifiedFacility;
    private UploadedFacility mCurrentUploadedFacility;
    private CompleteTableInfo mCurrentCompleteTableInfo;
    private ViewGroup dis_detail_ll;
    private ViewGroup dis_detail_container;
    private View myUploadLayerBtn;
    private View uploadLayerBtn;
    private TextView tv_check_hint;
    private View ll_check_hint;
    //private View pb_load_check_hint;
    private TextView tv_check_person;
    private TextView tv_check_phone;

    private CompassView mCompassView;
    private View btn_check;
    private View btn_check_cancel;
    private GraphicsLayer mArrowGLayer;
    private boolean UpOrDownStream = false;
    private ViewGroup ll_topbar_container2; //顶部工具容器
    private GraphicsLayer mStreamGLayer;
    private View btn_prev_gw;
    private View btn_next_gw;
    private SewerageFireService componentMaintenanceService;
    //第一次和第二次选中的连线点信息
    private NWUploadInfo secondLXInfo;
    private NWUploadInfo firstLXInfo;

    public static PSHUploadFireMapFragment getInstance(Bundle data) {
        PSHUploadFireMapFragment addComponentFragment2 = new PSHUploadFireMapFragment();
        addComponentFragment2.setArguments(data);
        return addComponentFragment2;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fire_uploadmap, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDbService = new TableDBService(mContext);
        mView = view;
        mView.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        tv_query_tip = (TextView) view.findViewById(R.id.tv_query_tip);
        btn_prev_gw = view.findViewById(R.id.prev_gw);
        btn_next_gw = view.findViewById(R.id.next_gw);
        ((TextView) mView.findViewById(R.id.tv_title)).setText("地上式消防栓上报");
        btn_check = view.findViewById(R.id.btn_check);
        btn_check_cancel = view.findViewById(R.id.btn_check_cancel);
        mView.findViewById(R.id.ll_search).setVisibility(View.VISIBLE);
        ImageView iv_open_search = (ImageView) mView.findViewById(R.id.iv_open_search);
        iv_open_search.setImageResource(R.mipmap.ic_list);
        ((TextView) mView.findViewById(R.id.tv_search)).setText("上报列表");
        mView.findViewById(R.id.tv_search).setVisibility(View.VISIBLE);
        map_bottom_sheet_gw = (ViewGroup) view.findViewById(R.id.map_bottom_sheet_gw);
        mView.findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PSHUploadFireListActivity.class);
                startActivity(intent);
//                ToastUtil.shortToast(mContext, "建设中");

            }
        });
        myUploadService = new SewerageMyUploadService(mContext);
        ll_topbar_container = (ViewGroup) view.findViewById(R.id.ll_topbar_container);
        ll_tool_container = (ViewGroup) view.findViewById(R.id.ll_tool_container);
        pipe_view = (ViewGroup) view.findViewById(R.id.pipe_view);
//        再次编辑按钮
//        btnReEdit = view.findViewById(R.id.btn_reedit);
//        btnReEdit.setVisibility(View.GONE);
        //TODO  删除按钮
//        btnDelete = view.findViewById(R.id.btn_delete1);
//        btnDelete.setVisibility(View.GONE);
        // Find MapView and add feature layers
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.setMapBackground(Color.WHITE, Color.TRANSPARENT, 0f, 0f);//设置地图背景色、去掉网格线
        /**
         * 比例尺
         */
        final MapScaleView scaleView = (MapScaleView) view.findViewById(R.id.scale_view);
        ViewGroup ll_rotate_container = (ViewGroup) view.findViewById(R.id.ll_rotate_container);
//        MapRotateView mapRotateVew = new MapRotateView(mContext, mMapView, ll_rotate_container);

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

                        if (locationButton != null) {
                            locationButton.followLocation();
                        }

                        if (myUploadLayerBtn != null) {
                            myUploadLayerBtn.performClick();
                        }

                        if (uploadLayerBtn != null) {
                            uploadLayerBtn.performClick();
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

        //定位图标
        locationMarker = (LocationMarker) view.findViewById(R.id.locationMarker);

        locationButton = (LocationButton) view.findViewById(R.id.locationButton);
//        RotateManager mapRotateManager = mapRotateVew.getMapRotateManager();
//        locationButton.setRotaManage(mapRotateManager);
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
        mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
        /**
         * 候选列表容器
         */
        ll_component_list = (ViewGroup) view.findViewById(R.id.ll_component_list);
//        tableItemContainer = (ViewGroup) view.findViewById(R.id.ll_table_item_container);
//        approvalOpinionContainer = (ViewGroup) view.findViewById(R.id.ll_approval_opinion_container);
        /**
         * 默认使用窨井
         */
        locationMarker.changeIcon(R.mipmap.ic_add_facility_2);
        show_all_layer = (TextView) view.findViewById(R.id.show_all_layer);
        show_all_layer.setOnClickListener(this);
        gridView = (GridView) view.findViewById(R.id.gridview);
        layerAdapter = new LimitedLayerAdapter(getContext());
        gridView.setAdapter(layerAdapter);
        layerAdapter.setOnItemClickListener(new OnRecyclerItemClickListener<String>() {
            @Override
            public void onItemClick(View view, int position, String selectedData) {
                currComponentUrl = selectedData;
                initGLayer();
                hideBottomSheet();
            }

            @Override
            public void onItemLongClick(View view, int position, String selectedData) {

            }
        });

        view.findViewById(R.id.btn_layer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) mContext;
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
        layoutBottom = view.findViewById(R.id.ll_bottm);
        map_bottom_sheet = (ViewGroup) view.findViewById(R.id.map_bottom_sheet);
        dis_map_bottom_sheet = (ViewGroup) view.findViewById(R.id.dis_map_bottom_sheet);
        layoutBottom.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = layoutBottom.getHeight();
            }
        });
        mDisBehavior = AnchorSheetBehavior.from(dis_map_bottom_sheet);
        mBehavior = AnchorSheetBehavior.from(map_bottom_sheet);
        mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);
        ll_topbar_container2 = (ViewGroup) view.findViewById(R.id.ll_topbar_container2);
        btn_prev = view.findViewById(R.id.prev_fire);
        btn_next = view.findViewById(R.id.next_fire);
        btn_prev_gw = view.findViewById(R.id.prev_gw);
        btn_next_gw = view.findViewById(R.id.next_gw);
        component_intro = view.findViewById(R.id.intro);
        component_detail_ll = view.findViewById(R.id.detail_ll);
        component_detail_container = (ViewGroup) view.findViewById(R.id.detail_container);
        dis_detail_ll = (ViewGroup) view.findViewById(R.id.dis_detail_ll);
        dis_detail_container = (ViewGroup) view.findViewById(R.id.dis_detail_container);
        btn_upload = view.findViewById(R.id.btn_upload);
        mDisBehavior.setAnchorHeight(DensityUtil.dp2px(getContext(), 230));
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

        btn_prev_gw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currIndex--;
                if (currIndex < 0) {
                    btn_prev_gw.setVisibility(View.GONE);
                    return;
                }
                dis_detail_container.removeAllViews();
                ll_table_item_container.removeAllViews();
                resetStatus(true);
                mCurrentCompleteTableInfo = mUploadInfos.get(currIndex).getCompleteTableInfo();
                if (mCurrentCompleteTableInfo != null) {
                    tv_distribute_error_correct.setVisibility(View.VISIBLE);
                }
                if (mUploadInfos.get(currIndex).getModifiedFacilities() != null) {
                    showBottomSheet(mUploadInfos.get(currIndex).getModifiedFacilities());
                } else if (mUploadInfos.get(currIndex).getUploadedFacilities() != null) {
//                    showBottomSheet(mUploadInfos.get(currIndex).getUploadedFacilities());
                }

                if (currIndex == 0) {
                    btn_prev_gw.setVisibility(View.GONE);
                }
                if (mUploadInfos.size() > 1) {
                    btn_next_gw.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_next_gw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currIndex++;
                if (currIndex > mUploadInfos.size()) {
                    btn_next_gw.setVisibility(View.GONE);
                    return;
                }
                dis_detail_container.removeAllViews();
                ll_table_item_container.removeAllViews();
                resetStatus(true);
                mCurrentCompleteTableInfo = mUploadInfos.get(currIndex).getCompleteTableInfo();
                if (mCurrentCompleteTableInfo != null) {
                    tv_distribute_error_correct.setVisibility(View.VISIBLE);
                }
                if (mUploadInfos.get(currIndex).getModifiedFacilities() != null) {
                    showBottomSheet(mUploadInfos.get(currIndex).getModifiedFacilities());
                } else if (mUploadInfos.get(currIndex).getUploadedFacilities() != null) {
//                    showBottomSheet(mUploadInfos.get(currIndex).getUploadedFacilities());
                }
                if (currIndex == (mUploadInfos.size() - 1)) {
                    btn_next_gw.setVisibility(View.GONE);
                }
                if (currIndex > 0) {
                    btn_prev_gw.setVisibility(View.VISIBLE);
                }
            }
        });
//        btn_upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tableViewManager.uploadEdit();
//            }
//        });

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

        btn_add = view.findViewById(R.id.btn_add);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                mMapView.getCallout().hide();
                btn_add.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.GONE);
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapMeasureView.stopMeasure();
                boolean locate = false;
                firstLXInfo = null;
                secondLXInfo = null;
//                if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
//                    locate = true;
//                    btn_edit_cancel.performClick();
//                }
                if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                    btn_check_cancel.performClick();
                }
                if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
                    btn_pipe_cancel.performClick();
                }
                if (ifFirstAdd) {
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "移动地图选择新增设施的位置");
                    ifFirstAdd = false;
                }

                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_add_facility_2);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                btn_add.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.VISIBLE);
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
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLXInfo = null;
                secondLXInfo = null;
                mStatus = 0;
                isClick = false;
                ifInEditMode = false;
                ifInCheckMode = true;
                ifInPipeMode = false;
                boolean locate = false;
                if (btn_cancel.getVisibility() == View.VISIBLE) {
                    btn_cancel.performClick();
                }
                if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
                    btn_pipe_cancel.performClick();
                }

                btn_check.setVisibility(View.GONE);
                btn_check_cancel.setVisibility(View.VISIBLE);
                ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "移动地图选择窨井");
                addTopBarView(mTitle);
                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_check_related);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                hideBottomSheet();
                initGLayer();
                initArrowGLayer();
                initStreamGLayer();
                setSearchFacilityListener();
                //下面的代码不能在调用 setSearchFacilityListener 方法前调用，
                // 因为editModeSelectLocationTouchListener 和 editModeCalloutSureButtonClickListener 未初始化
                if (locate) {
                    editModeSelectLocationTouchListener.locate();
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().animatedHide();
                    }
                    editModeCalloutSureButtonClickListener.onClick(null);
                }

            }
        });
        btn_check_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLXInfo = null;
                secondLXInfo = null;
                ifInCheckMode = false;
                btn_check.setVisibility(View.VISIBLE);
                btn_check_cancel.setVisibility(View.GONE);
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                mMapView.getCallout().hide();
                hideBottomSheet();
                mStatus = 0;
                setMode(0);
                if (mStatus == 0) {
                    initGLayer();
                    initArrowGLayer();
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                }
            }
        });


        btn_edit_pipe = view.findViewById(R.id.btn_edit_pipe);
        btn_pipe_cancel = view.findViewById(R.id.btn_pipe_cancel);
        btn_edit_pipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLXInfo = null;
                secondLXInfo = null;
                mStatus = 0;
                isClick = false;
                ifInEditMode = false;
                ifInCheckMode = false;
                ifInPipeMode = true;
                boolean locate = false;
                if (btn_cancel.getVisibility() == View.VISIBLE) {
                    btn_cancel.performClick();
                }
                if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                    btn_check_cancel.performClick();
                }

                if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                    btn_check_cancel.performClick();
                }

                btn_edit_pipe.setVisibility(View.GONE);
                btn_pipe_cancel.setVisibility(View.VISIBLE);
                ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "在地图上点击排水管线的位置");
                addTopBarView(mTitle);
                hideBottomSheet();
                initGLayer();
                initArrowGLayer();
                initStreamGLayer();
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                //下面的代码不能在调用 setSearchFacilityListener 方法前调用，
                // 因为editModeSelectLocationTouchListener 和 editModeCalloutSureButtonClickListener 未初始化
                if (locate) {
                    editModeSelectLocationTouchListener.locate();
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().animatedHide();
                    }
                    editModeCalloutSureButtonClickListener.onClick(null);
                }
            }
        });
        btn_pipe_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifInPipeMode = false;
                btn_edit_pipe.setVisibility(View.VISIBLE);
                btn_pipe_cancel.setVisibility(View.GONE);
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                mMapView.getCallout().hide();
                hideBottomSheet();
                mStatus = 0;
                setMode(0);
                if (mStatus == 0) {
                    initGLayer();
                    initArrowGLayer();
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                }
            }
        });

//        btn_edit = view.findViewById(R.id.btn_edit);
//        btn_edit_cancel = view.findViewById(R.id.btn_edit_cancel);
//        btn_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMapMeasureView.stopMeasure();
//                boolean locate = false;
//                if (btn_cancel.getVisibility() == View.VISIBLE) {
//                    locate = true;
//                    btn_cancel.performClick();
//                }
//                ifInEditMode = true;
//                btn_edit.setVisibility(View.GONE);
//                btn_edit_cancel.setVisibility(View.VISIBLE);
//                if (ifFirstEdit) {
//                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "移动地图选择设施的位置");
//                    ifFirstEdit = false;
//                }
//
//                if (locationMarker != null) {
//                    locationMarker.changeIcon(R.mipmap.ic_check_data_2);
//                    locationMarker.setVisibility(View.VISIBLE);
//                }
//                hideBottomSheet();
//                initGLayer();
//                setSearchFacilityListener();
//                //下面的代码不能在调用 setSearchFacilityListener 方法前调用，
//                // 因为editModeSelectLocationTouchListener 和 editModeCalloutSureButtonClickListener 未初始化
//                if (locate) {
//                    editModeSelectLocationTouchListener.locate();
//                    if (mMapView.getCallout().isShowing()) {
//                        mMapView.getCallout().animatedHide();
//                    }
//                    editModeCalloutSureButtonClickListener.onClick(null);
//                }
//
//            }
//        });
//        btn_edit_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ifInEditMode = false;
//                btn_edit.setVisibility(View.VISIBLE);
//                btn_edit_cancel.setVisibility(View.GONE);
//                if (locationMarker != null) {
//                    locationMarker.setVisibility(View.GONE);
//                }
//
//                mMapView.getCallout().hide();
//                hideBottomSheet();
//                initGLayer();
//                //1
//                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
//            }
//        });
        mBehavior_gw = AnchorSheetBehavior.from(map_bottom_sheet_gw);
        mBehavior_gw.setAnchorHeight(DensityUtil.dp2px(mContext, 230));
        //我的上报图层按钮
        myUploadLayerBtn = view.findViewById(R.id.ll_my_upload_layer);
        final TextView tv_my_upload_layer = (TextView) view.findViewById(R.id.tv_my_upload_layer);
        final SwitchCompat myUploadIv = (SwitchCompat) view.findViewById(R.id.iv_my_upload_layer);
        myUploadLayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ifMyUploadLayerVisible) {
                    myUploadIv.setChecked(false);
                    //myUploadIv.setImageResource(R.drawable.ic_invisible);
                    tv_my_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.invisible_state_text_color, null));
                    ifMyUploadLayerVisible = false;
                } else {
                    myUploadIv.setChecked(true);
                    //  myUploadIv.setImageResource(R.drawable.ic_visible);
                    tv_my_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
                    ifMyUploadLayerVisible = true;
                }

                if (layerPresenter != null) {
                    layerPresenter.changeLayerVisibility(PatrolLayerPresenter.MY_UPLOAD_LAYER_NAME, ifMyUploadLayerVisible);
                }
            }
        });

        //数据上报图层按钮
        uploadLayerBtn = view.findViewById(R.id.ll_upload_layer);
        final TextView tv_upload_layer = (TextView) view.findViewById(R.id.tv_upload_layer);
        final SwitchCompat uploadIv = (SwitchCompat) view.findViewById(R.id.iv_upload_layer);
        uploadLayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ifWellVisible) {
                    uploadIv.setChecked(false);
                    //myUploadIv.setImageResource(R.drawable.ic_invisible);
                    tv_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.invisible_state_text_color, null));
                    ifWellVisible = false;
                } else {
                    uploadIv.setChecked(true);
                    //  myUploadIv.setImageResource(R.drawable.ic_visible);
                    tv_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
                    ifWellVisible = true;
                }

                if (layerPresenter != null) {
                    layerPresenter.changeLayerVisibility(PatrolLayerPresenter.FIRE_LAYER, ifWellVisible);
                }
            }
        });
        //注册当图层可见度发生改变时的回调
        if (layerPresenter != null) {
            layerPresenter.registerLayerVisibilityChangedListener(new PatrolLayerPresenter.OnLayerVisibilityChangedListener() {
                @Override
                public void changed(boolean visible, LayerInfo layerInfo) {
                    if (!visible && layerInfo.getLayerName().equals(PatrolLayerPresenter.FIRE_LAYER)) {
                        //不可见
                        //  myUploadIv.setImageResource(R.drawable.ic_upload_data_normal2);
                        uploadIv.setChecked(false);
                        tv_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.invisible_state_text_color, null));
                        ifWellVisible = false;
                    } else if (visible && layerInfo.getLayerName().equals(PatrolLayerPresenter.FIRE_LAYER)) {
                        //可见
                        // myUploadIv.setImageResource(R.drawable.ic_upload_data_pressed);
                        uploadIv.setChecked(true);
                        tv_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
                        ifWellVisible = true;
                    } else if (!visible && layerInfo.getLayerName().contains(PatrolLayerPresenter.MY_UPLOAD_LAYER_NAME)) {
                        //不可见
                        //  myUploadIv.setImageResource(R.drawable.ic_upload_data_normal2);
                        myUploadIv.setChecked(false);
                        tv_my_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.invisible_state_text_color, null));
                        ifMyUploadLayerVisible = false;
                    } else if (visible && layerInfo.getLayerName().contains(PatrolLayerPresenter.MY_UPLOAD_LAYER_NAME)) {
                        //可见
                        // myUploadIv.setImageResource(R.drawable.ic_upload_data_pressed);
                        myUploadIv.setChecked(true);
                        tv_my_upload_layer.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
                        ifMyUploadLayerVisible = true;
                    }
                }
            });
        }
        initBottomSheetView();
    }

    private void initGwView(ViewGroup view) {
//        /**
//         * 是否有人上报过提醒文本
//         */
//        tv_check_hint_gw = (TextView) view.findViewById(R.id.tv_check_hint_gw);
//        ll_check_hint_gw = view.findViewById(R.id.ll_check_hint_gw);
//        tv_check_phone_gw = (TextView) view.findViewById(R.id.tv_check_phone_gw);
//        tv_check_person_gw = (TextView) view.findViewById(R.id.tv_check_person_gw);
//        btn_prev_gw = view.findViewById(R.id.prev_gw);
//        btn_next_gw = view.findViewById(R.id.next_gw);
//        component_intro_gw = view.findViewById(R.id.intro_gw);
//        component_detail_ll_gw = view.findViewById(R.id.detail_ll_gw);
//        component_detail_container_gw = (ViewGroup) view.findViewById(R.id.detail_container_gw);
//        btn_upload_gw = view.findViewById(R.id.btn_upload_gw);
//        RxView.clicks(btn_prev_gw)
//                .throttleFirst(500, TimeUnit.MILLISECONDS)
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        currIndex--;
//                        if (currIndex < 0) {
//                            btn_prev_gw.setVisibility(View.GONE);
//                            return;
//                        }
//                        if (ifInEditMode || ifInCheckMode) {
//                            showYjBottomSheetAndJust(mUploadInfos.get(currIndex).getUploadedFacilities());
//                            if (mUploadInfos.size() > 1) {
//                                btn_next_gw.setVisibility(View.VISIBLE);
//                            }
//                        } else if (ifInPipeMode) {
//                            Object obj = mNWUploadInfos.get(currIndex);
//                            if (obj instanceof Component) {
//                                Component nwUploadInfo = (Component) obj;
//                                showBottomSheetForCheck(nwUploadInfo);
//                            }
//                            if (mNWUploadInfos.size() > 1) {
//                                btn_next_gw.setVisibility(View.VISIBLE);
//                            }
//                        }
//                        if (currIndex == 0) {
//                            btn_prev_gw.setVisibility(View.GONE);
//                        }
//
//                    }
//                });
//        RxView.clicks(btn_next_gw)
//                .throttleFirst(500, TimeUnit.MILLISECONDS)
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        currIndex++;
//                        if (ifInEditMode || ifInCheckMode) {
//                            if (currIndex > mUploadInfos.size()) {
//                                btn_next_gw.setVisibility(View.GONE);
//                                return;
//                            }
//                            showYjBottomSheetAndJust(mUploadInfos.get(currIndex).getUploadedFacilities());
//                            if (currIndex == (mUploadInfos.size() - 1)) {
//                                btn_next_gw.setVisibility(View.GONE);
//                            }
//                        } else if (ifInPipeMode) {
//                            if (currIndex > mNWUploadInfos.size()) {
//                                btn_next_gw.setVisibility(View.GONE);
//                                return;
//                            }
//                            Object obj = mNWUploadInfos.get(currIndex);
//                            if (obj instanceof Component) {
//                                Component nwUploadInfo = (Component) obj;
//                                showBottomSheetForCheck(nwUploadInfo);
//                            }
//                            if (currIndex == (mNWUploadInfos.size() - 1)) {
//                                btn_next_gw.setVisibility(View.GONE);
//                            }
//                        }
//                        if (currIndex > 0) {
//                            btn_prev_gw.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
    }

    private void showOnBottomSheet(List<UploadInfo> uploadInfos) {
        if (uploadInfos.size() > 1) {
            nextAndPrevContainer.setVisibility(View.VISIBLE);
            btn_next.setVisibility(View.VISIBLE);
        }
        if (mMapView.getCallout().isShowing()) {
            mMapView.getCallout().animatedHide();
        }

        component_detail_container.removeAllViews();
        ll_table_item_container.removeAllViews();
        //隐藏marker
        locationMarker.setVisibility(View.GONE);
        initGLayer();
        Geometry geometry = null;
        mCurrentCompleteTableInfo = uploadInfos.get(0).getCompleteTableInfo();
        if (uploadInfos.get(0).getUploadedFacilities() != null) {
            geometry = new Point(uploadInfos.get(0).getUploadedFacilities().getX(), uploadInfos.get(0).getUploadedFacilities().getY());
            showBottomSheet(uploadInfos.get(0).getUploadedFacilities());
        } else if (uploadInfos.get(0).getModifiedFacilities() != null) {
            geometry = new Point(uploadInfos.get(0).getModifiedFacilities().getOriginX(), uploadInfos.get(0).getModifiedFacilities().getOriginY());
            showBottomSheet(uploadInfos.get(0).getModifiedFacilities());
        }
        if (geometry != null) {
            drawGeometry(geometry, mGLayer, true, true);
        }
    }

    private void setMode(int status) {
        mStatus = status;
        if (0 == status) {
            ll_topbar_container.setVisibility(View.GONE);
            pipe_view.setVisibility(View.GONE);
            ll_topbar_container2.setVisibility(View.GONE);
            if (locationMarker != null) {
                locationMarker.setVisibility(View.GONE);
            }
            initStreamGLayer();
            initGLayer();
            hideBottomSheet();
            mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
        } else if (1 == status || UpOrDownStream) {
            ll_topbar_container.setVisibility(View.VISIBLE);
            ll_topbar_container2.setVisibility(View.VISIBLE);
        } else if (2 == mStatus) {
            UpOrDownStream = false;
            ll_topbar_container.setVisibility(View.VISIBLE);
        }
    }

    private void initArrowGLayer() {
        if (mArrowGLayer == null) {
            mArrowGLayer = new GraphicsLayer();
            mMapView.addLayer(mArrowGLayer);
        } else {
            mArrowGLayer.removeAll();
        }
    }

    /**
     * 新增数据
     * 加载设施信息，显示中底部BottomSheet中
     */
    private void showBottomSheet(final UploadedFacility uploadedFacility) {
        mCurrentModifiedFacility = null;
        //initGLayer();
        if (uploadedFacility == null) {
            return;
        }
        mCurrentUploadedFacility = uploadedFacility;

        /**
         * 上报信息按钮
         */

        if (uploadedFacility.getIsBinding() == 1 && mCurrentCompleteTableInfo != null) {
            tv_distribute_error_correct.setVisibility(View.VISIBLE);
        } else {
            tv_distribute_error_correct.setVisibility(View.GONE);
        }
        dis_map_bottom_sheet.setVisibility(View.VISIBLE);
        dis_detail_container.setVisibility(View.VISIBLE);

        if (dis_detail_container.getChildCount() == 0) {
            initGLayer();
            Geometry geometry = new Point(uploadedFacility.getX(), uploadedFacility.getY());
            drawGeometry(geometry, mGLayer, true, true);
            UploadFacilityTableViewManager modifiedIdentificationTableViewManager = new UploadFacilityTableViewManager(getContext(),
                    uploadedFacility);
            modifiedIdentificationTableViewManager.addTo(dis_detail_container);
            if (mDisBehavior.getState() == STATE_COLLAPSED || mDisBehavior.getState() == com.augurit.am.cmpt.widget.bottomsheet.BottomSheetBehavior.STATE_HIDDEN) {
                dis_detail_container.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDisBehavior.setState(STATE_ANCHOR);
                    }
                }, 200);
            }
        }

        //component_detail_container.removeAllViews();

        dis_detail_ll.setVisibility(View.VISIBLE);
    }

    /**
     * 关联窨井标题栏
     */
    public void addTopBarView(String title) {
        View topbar_view = View.inflate(mContext, R.layout.pipe_topbar_view, null);
        topbar_view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        View btn_back = topbar_view.findViewById(R.id.btn_back);
        if (StringUtil.isEmpty(title)) {
            ((TextView) topbar_view.findViewById(R.id.scv)).setText("");
        } else {
            ((TextView) topbar_view.findViewById(R.id.scv)).setText(title);
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
        topbar_view.findViewById(R.id.container).setOnClickListener(null);  // 拦截测量时的顶栏点击事件，避免点到地图上
        ll_topbar_container.removeAllViews();
        ll_topbar_container.addView(topbar_view);
    }

    private void exit() {
//        if (2 == mStatus && UpOrDownStream) {
//            UpOrDownStream = false;
//            mStatus = 2;
//            setMode(mStatus);
//        } else if (1 == mStatus || 2 == mStatus) {
//            setMode(0);
//            if (locationMarker != null) {
//                locationMarker.setVisibility(View.GONE);
//            }
//            if (btn_check_cancel.getVisibility() == View.VISIBLE) {
//                btn_check_cancel.performClick();
//            }
//            if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
//                btn_pipe_cancel.performClick();
//            }
//            mMapView.getCallout().hide();
//            hideBottomSheet();
//            initGLayer();
//            initStreamGLayer();
//            initArrowGLayer();
//            mAllPipeBeans.clear();
//            mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
//        } else if (0 == mStatus) {
//            ((Activity) mContext).finish();
//        }
    }

    /**
     * 设施部件
     * 加载设施信息，显示中底部BottomSheet中
     */
    private void showBottomSheet(final CompleteTableInfo CompleteTableInfo) {
        //initGLayer();
        mCurrentCompleteTableInfo = CompleteTableInfo;
        ll_table_item_container.removeAllViews();

        //        addrTv.setVisibility(View.GONE);
        TextView tv_errorinfo = (TextView) map_bottom_sheet.findViewById(R.id.tv_errorinfo);
        TextView tv_parent_org_name = (TextView) map_bottom_sheet.findViewById(R.id.tv_parent_org_name);

        String name = String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.NAME));


        String subtype = String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.SUBTYPE));
        String usid = String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.USID));


//        String title = StringUtil.getNotNullString(type, "") + "(" + usid + ")";
//        titleTv.setText(title);

//        String date = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.UPDATEDATE));
//        String formatDate = "";
//        try {
//            formatDate = TimeUtil.getStringTimeYMD(new Date(Long.valueOf(date)));
//        } catch (Exception e) {
//
//        }
//        dateTv.setText(StringUtil.getNotNullString(formatDate, ""));

        String sort = String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.SORT));

        int color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);

        if (sort.contains("雨污合流")) {
            color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);
        } else if (sort.contains("雨水")) {
            color = ResourcesCompat.getColor(getResources(), R.color.progress_line_green, null);
        } else if (sort.contains("污水")) {
            color = ResourcesCompat.getColor(getResources(), R.color.agmobile_red, null);
        }
        TextItemTableItem sortTv = new TextItemTableItem(getContext());
        sortTv.setTextViewName("类别");
        sortTv.setText(StringUtil.getNotNullString(sort, ""));
        sortTv.setReadOnly();

        /**
         * 如果是雨水口，显示特性：方形
         */
        String layertype = "";
        if (mCurrentModifiedFacility != null) {
            layertype = mCurrentModifiedFacility.getLayerName();
        } else if (mCurrentUploadedFacility != null) {
            layertype = mCurrentUploadedFacility.getLayerName();
        }
        TextItemTableItem ssTv = new TextItemTableItem(getContext());
        ssTv.setTextViewName("设施");
        ssTv.setText(StringUtil.getNotNullString(layertype, "") + "(" + usid + ")");
        ssTv.setReadOnly();
        ll_table_item_container.addView(ssTv);
        if (layertype.equals("雨水口")) {
            String feature = String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.FEATURE));
            sortTv.setText(StringUtil.getNotNullString(feature, ""));
        }
//
        ll_table_item_container.addView(sortTv);
        /**
         * 修改属性三
         */
//        if ("雨水口".equals(type)) {
//            String style = String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.STYLE));
//            subtypeTv.setText(StringUtil.getNotNullString(style, ""));
//        } else {
//            subtypeTv.setText(StringUtil.getNotNullString(subtype, ""));
//        }
//
//        if ("雨水口".equals(type)) {
//            field3Tv.setVisibility(View.GONE);
//        } else {
//            field3Tv.setVisibility(View.VISIBLE);
//        }
        String field3 = "";
        TextItemTableItem csortTv = new TextItemTableItem(getContext());
        if (layertype.equals("窨井")) {
//            field3 = "井盖材质: " + String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.MATERIAL));
            csortTv.setTextViewName("井盖材质");
            csortTv.setText(String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.MATERIAL)));
            csortTv.setReadOnly();
        } else if (layertype.equals("排放口")) {
            field3 = "排放去向: " + String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.RIVER));
            csortTv.setTextViewName("排放去向");
            csortTv.setText(String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.RIVER)));
            csortTv.setReadOnly();
        }
        ll_table_item_container.addView(csortTv);


        final String address = String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.ADDR));
        TextItemTableItem addressTv = new TextItemTableItem(getContext());
        addressTv.setTextViewName("设施位置");
        addressTv.setText(StringUtil.getNotNullString(address, ""));
        addressTv.setReadOnly();
        ll_table_item_container.addView(addressTv);
//        addrTv.setText("设施位置" + "：" + StringUtil.getNotNullString(address, ""));

        //已挂牌编号
        TextItemTableItem bianhaoTv = new TextItemTableItem(getContext());
        bianhaoTv.setTextViewName("已挂牌编号");
        bianhaoTv.setReadOnly();
        String codeValue = String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.CODE));
        codeValue = codeValue.trim();
        if (layertype.equals("窨井")) {
            if (!codeValue.isEmpty()) {
                bianhaoTv.setText(StringUtil.getNotNullString(codeValue, ""));
            } else {
//                bianhaoTv.setText("无");
                bianhaoTv.setText("");
            }
            ll_table_item_container.addView(bianhaoTv);
        }
        String parentOrg = String.valueOf(CompleteTableInfo.getAttrs().get(ComponentFieldKeyConstant.OWNERDEPT));
        TextItemTableItem quanshuTv = new TextItemTableItem(getContext());
        quanshuTv.setTextViewName("权属单位");
        quanshuTv.setText(StringUtil.getNotNullString(parentOrg, ""));
        quanshuTv.setReadOnly();
        ll_table_item_container.addView(quanshuTv);
    }


    /**
     * 纠错数据
     * 加载设施信息，显示中底部BottomSheet中
     */
    private void showBottomSheet(final ModifiedFacility modifiedFacility) {
        //initGLayer();
        if (modifiedFacility == null) {
            return;
        }

        dis_detail_container.setVisibility(View.VISIBLE);

        if (dis_detail_container.getChildCount() == 0) {
            if (mCurrentCompleteTableInfo != null) {
                tv_distribute_error_correct.setVisibility(View.VISIBLE);
            } else {
                tv_distribute_error_correct.setVisibility(View.GONE);
            }
            mCurrentModifiedFacility = modifiedFacility;
            Geometry geometry = new Point(modifiedFacility.getOriginX(), modifiedFacility.getOriginY());
            initGLayer();
            drawGeometry(geometry, mGLayer, true, true);
            dis_map_bottom_sheet.setVisibility(View.VISIBLE);
            //component_detail_container.removeAllViews();
            MyModifiedFacilityTableViewManager modifiedIdentificationTableViewManager = new MyModifiedFacilityTableViewManager(getContext(),
                    modifiedFacility, true);
            modifiedIdentificationTableViewManager.addTo(dis_detail_container);
            modifiedIdentificationTableViewManager.setReadOnly(modifiedFacility, null);
            if (mDisBehavior.getState() == STATE_COLLAPSED || mDisBehavior.getState() == com.augurit.am.cmpt.widget.bottomsheet.BottomSheetBehavior.STATE_HIDDEN) {
                dis_detail_container.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDisBehavior.setState(com.augurit.am.cmpt.widget.bottomsheet.BottomSheetBehavior.STATE_ANCHOR);
                    }
                }, 200);
            }

        }


        dis_detail_ll.setVisibility(View.VISIBLE);
    }

    private void initBottomSheetView() {
        initFacilityView();
        nextAndPrevContainer = (ViewGroup) dis_map_bottom_sheet.findViewById(R.id.ll_next_and_prev_container);
        ll_table_item_container = (ViewGroup) dis_map_bottom_sheet.findViewById(R.id.ll_table_item_container);
        // pb_distribute = (ProgressBar) map_bottom_sheet.findViewById(R.id.pb_distribute);

        //上报信息
        tv_distribute_sure = (TextView) dis_map_bottom_sheet.findViewById(R.id.tv_distribute_sure);
        tv_distribute_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStatus(true);
                dis_detail_container.setVisibility(View.VISIBLE);
                ll_table_item_container.setVisibility(View.GONE);
                showBottomSheet(mCurrentModifiedFacility);

            }
        });


        //原部件按钮
        tv_distribute_error_correct = (TextView) dis_map_bottom_sheet.findViewById(R.id.tv_distribute_error_correct);
        tv_distribute_error_correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStatus(false);
                dis_detail_container.setVisibility(View.GONE);
                ll_table_item_container.setVisibility(View.VISIBLE);
                if (mCurrentCompleteTableInfo != null) {
                    showBottomSheet(mCurrentCompleteTableInfo);
                }
            }
        });

        /**
         * 是否有人上报过提醒文本
         */
        tv_check_hint = (TextView) map_bottom_sheet.findViewById(R.id.tv_check_hint);
        ll_check_hint = map_bottom_sheet.findViewById(R.id.ll_check_hint);
        tv_check_phone = (TextView) map_bottom_sheet.findViewById(R.id.tv_check_phone);
        tv_check_person = (TextView) map_bottom_sheet.findViewById(R.id.tv_check_person);
    }

    private void resetStatus(boolean reset) {
        if (reset) {
            tv_distribute_sure.setBackground(getResources().getDrawable(R.drawable.round_blue_rectangle));
            tv_distribute_sure.setTextColor(getResources().getColor(R.color.agmobile_white));
            tv_distribute_error_correct.setBackground(getResources().getDrawable(R.drawable.round_grey_rectangle));
            tv_distribute_error_correct.setTextColor(getResources().getColor(R.color.agmobile_blue));
        } else {
            tv_distribute_sure.setBackground(getResources().getDrawable(R.drawable.round_grey_rectangle));
            tv_distribute_sure.setTextColor(getResources().getColor(R.color.agmobile_blue));
            tv_distribute_error_correct.setBackground(getResources().getDrawable(R.drawable.round_blue_rectangle));
            tv_distribute_error_correct.setTextColor(getResources().getColor(R.color.agmobile_white));
        }
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
        layerPresenter = new PSHWelllayerPresenter(layerView, new PSHFireDetailLayerService(mContext.getApplicationContext()));
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
                if (btn_cancel.getVisibility() == View.VISIBLE) {
                    btn_cancel.performClick();
                }
//                if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
//                    btn_edit_cancel.performClick();
//                }
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
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });
    }


    private void showCallout(DetailAddress address, Point point, final View.OnClickListener calloutSureButtonClickListener) {
        if (address != null) {
            final Point geometry = point;
            final Callout callout = mMapView.getCallout();
            View view = View.inflate(mContext, R.layout.view_addfacility_callout, null);
            ((TextView) view.findViewById(com.augurit.agmobile.patrolcore.R.id.tv_listcallout_title)).setText(address.getDetailAddress());
            view.findViewById(com.augurit.agmobile.patrolcore.R.id.btn_select_device).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            callout.setStyle(com.augurit.agmobile.patrolcore.R.xml.editmap_callout_style);
            callout.setContent(view);
            if (point == null) {
                point = mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2 - 140);
            }
            callout.show(point);
        }

    }


    /**
     * 请求百度地址
     *
     * @param point
     * @param callback1
     */
    private void requestLocation(Point point, SpatialReference spatialReference, final Callback1<String> callback1) {
        SelectLocationService selectLocationService = new SelectLocationService(mContext, Locator.createOnlineLocator());
        selectLocationService.parseLocation(new LatLng(point.getY(), point.getX()), spatialReference)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DetailAddress>() {
                    @Override
                    public void call(DetailAddress detailAddress) {
                        if (callback1 != null) {
                            callback1.onCallback(detailAddress.getDetailAddress());
                        }
                    }
                });
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

    private Point getMapCenterPoint() {
        //获取当前的位置
        return mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2);
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

    private boolean hasLoadLayerBefore = false;

    /**
     * 显示图层列表
     */
    public void showLayerList() {

//        if (hasLoadLayerBefore) {
//            return;
//        }

        if (layerPresenter != null) {
            layerPresenter.showLayerList();
            hasLoadLayerBefore = true;
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
        if (map_bottom_sheet.getVisibility() == View.GONE && dis_map_bottom_sheet.getVisibility() == View.GONE) {
            ((Activity) mContext).finish();
            return;
        }
        if (mBehavior != null
                && map_bottom_sheet != null) {
            if (mBehavior.getState() == STATE_EXPANDED) {
                mBehavior.setState(STATE_COLLAPSED);
            } else if (mBehavior.getState() == STATE_COLLAPSED
                    || mBehavior.getState() == AnchorSheetBehavior.STATE_ANCHOR) {
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
     * @return
     */
    private SelectLocationTouchListener getDefaultMapOnTouchListener() {
        if (defaultMapOnTouchListener == null) {
            defaultMapOnTouchListener = new DefaultTouchListener(mContext, mMapView, locationMarker, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                        ((DefaultTouchListener) defaultMapOnTouchListener).query(x, y);
                    }
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

        private void query(final double x, final double y) {
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
            final Geometry geometry = GeometryEngine.buffer(point, mMapView.getSpatialReference(), 40 * mMapView.getResolution(), null);
            componentMaintenanceService = new SewerageFireService(mContext.getApplicationContext());
            if (ifInEditMode || ifInCheckMode) {
                componentMaintenanceService.queryUnitComponents(geometry, new Callback2<List<Component>>() {
                    @Override
                    public void onSuccess(List<Component> components) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                        if (ListUtil.isEmpty(components)) {
                            mMapView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.shortToast(getActivity(), "没有找到接驳井");
//                                       Point point1 = new Point(x,y);
//                                        showCalloutMessage("没有找到井", point1, null);
                                }
                            }, 200);
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
                    }
                });
            } else if (ifInPipeMode) {
                //编辑管线
                componentMaintenanceService.queryComponentsForPipeAndYJ(geometry, new Callback2<List<Component>>() {
                    @Override
                    public void onSuccess(List<Component> components) {
                        mNWUploadInfos.clear();
                        mNWUploadInfos.addAll(components);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                        if (ListUtil.isEmpty(components)) {
                            mMapView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.shortToast(getActivity(), "没有找到管线");
//                                       Point point1 = new Point(x,y);
//                                        showCalloutMessage("没有找到井", point1, null);
                                }
                            }, 200);
                            return;
                        }

                        if (components.size() == 1) {
//                            Object object = components.get(0);
//                            if (object instanceof NWUploadInfo) {
//                                NWUploadInfo nwUploadInfo = (NWUploadInfo) object;
//                                if (nwUploadInfo.getUploadedFacilities() == null && nwUploadInfo.getModifiedFacilities() == null) {
//                                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "查无数据！");
//                                    return;
//                                }
//                            }
                        }
                        showGWOnBottomSheet(components, true);
                    }

                    @Override
                    public void onFail(Exception error) {
                        ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, error.getLocalizedMessage());
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        mMapView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showCalloutMessage("没有找到管线", null, null);
                            }
                        }, 200);
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
//                                            EventBus.getDefault().post(new SelectComponentFinishEvent2(null, detailAddress, mMapView.getScale()));
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
//                                        EventBus.getDefault().post(new SelectComponentFinishEvent2(null, detailAddress, mMapView.getScale()));
                                        getActivity().finish();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }

        private void showGWOnBottomSheet(List<Component> uploadInfos, boolean isPipe) {
            if (uploadInfos.size() > 1) {
                nextAndPrevContainer.setVisibility(View.VISIBLE);
                btn_next_gw.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.VISIBLE);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mMapView.getCallout().animatedHide();
                }
            }, 600);


            component_detail_container.removeAllViews();
//            tableItemContainer.removeAllViews();
//            approvalOpinionContainer.removeAllViews();

            //隐藏marker
            locationMarker.setVisibility(View.GONE);
            initGLayer();
            Geometry geometry = null;
            Object object = uploadInfos.get(0);
//            if (object instanceof NWUploadInfo) {
//                NWUploadInfo temp = (NWUploadInfo) object;
//                if (temp.getUploadedFacilities() != null) {
//                    geometry = new Point(temp.getUploadedFacilities().getX(), temp.getUploadedFacilities().getY());
//                    showBottomSheet(temp.getUploadedFacilities(), temp.getCompleteTableInfo());
//                } else if (temp.getModifiedFacilities() != null) {
//
//                    showBottomSheet(temp.getModifiedFacilities(), temp.getCompleteTableInfo());
//                }
//            } else
            if (object instanceof Component) {
                if (isPipe) {
                    showBottomSheetForCheck((Component) object);
                } else {
                    showBottomGW((Component) object);
                }
            }
//        if (geometry != null) {
//            drawGeometry(geometry, mGLayer, true, true);
//        }
        }

        private void showBottomGW(Component component) {
            mCurrentModifiedFacility = null;
            mCurrentUploadedFacility = null;
            mCurrentComponent = component;
            originPipe = createStreamBean(component);
//            btnDelete.setVisibility(View.VISIBLE);
//            btnReEdit.setVisibility(View.VISIBLE);
            tv_error_correct.setVisibility(View.GONE);
//        tv_sure.setVisibility(View.GONE);
            tv_approval_opinion_list.setVisibility(View.GONE);
            map_bottom_sheet.setVisibility(View.VISIBLE);
            component_detail_container.setVisibility(View.VISIBLE);
            String checkState = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("CHECK_STATE"), "");
//            if (checkState != null) {
//                if (checkState.equals("1")) {
//                    btnDelete.setVisibility(View.VISIBLE);
//                    btnReEdit.setVisibility(View.VISIBLE);
//                } else if (checkState.equals("2")) {
//                    btnDelete.setVisibility(View.GONE);
//                    btnReEdit.setVisibility(View.GONE);
//                } else if (checkState.equals("3")) {
//                    btnDelete.setVisibility(View.GONE);
//                    btnReEdit.setVisibility(View.VISIBLE);
//                }
//            }
            if (component_detail_container.getChildCount() == 0) {
                initGLayer();
//            drawGeometry(component.getGraphic().getGeometry(), mGLayer, false, true);
                Geometry geometry = component.getGraphic().getGeometry();
                drawGeometry(geometry, mGLayer, true, true);
                PipeTableViewManager modifiedIdentificationTableViewManager = null;
                modifiedIdentificationTableViewManager = new PipeTableViewManager(mContext,
                        component);

                modifiedIdentificationTableViewManager.addTo(component_detail_container);
                if (mBehavior.getState() == IBottomSheetBehavior.STATE_COLLAPSED) {
                    component_detail_container.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBehavior.setState(IBottomSheetBehavior.STATE_ANCHOR);
                        }
                    }, 200);
                }
            }

            //component_detail_container.removeAllViews();

            component_detail_ll.setVisibility(View.VISIBLE);
        }

        private void showBottomSheetForCheck(final Component component) {
            if (0 == mStatus || isClick) {
                componentCenter = component;
                originPipe = createStreamBean(component);
            }
//        ll_check_hint.setVisibility(View.GONE);
            final String layerName = component.getLayerName();
            if (!attribute.contains(layerName)) {
                hideBottomSheet();
                return;
            }
            if ("排水管道".equals(layerName)) {
                checkIfHasBeenUploadBefore(component, 2);
            }
            if (mStatus == 0 || isClick) {
                mGLayer.removeAll();
                drawGeometry(component.getGraphic().getGeometry(), mGLayer, false, true);
                mFirstAttribute = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.TYPE), "");
                if (!mAttribute.contains(mFirstAttribute)) {
                    mFirstAttribute = "";
                }
            } else if (mStatus == 1 || mStatus == 2) {
                initStreamGLayer();
                drawGeometry(component.getGraphic().getGeometry(), mStreamGLayer, false, true);
                mSecondAttribute = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.ATTR_TWO), "");
                if (!mAttribute.contains(mSecondAttribute)) {
                    mSecondAttribute = "";
                }
            }
            String errorInfo = null;
            Object oErrorInfo = component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ERROR_INFO);

            if (oErrorInfo != null) {
                errorInfo = oErrorInfo.toString();
            }

//        TextView code = (TextView) map_bottom_sheet.findViewById(R.id.code);
//            String subtype = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.GRADE), "");
            mAddrTv.setVisibility(View.GONE);
            if ("排水管道".equals(layerName) || "示意连线".equals(layerName) || "接驳连线".equals(layerName)) {//管道就把值加载出来
                mLine2_gw.setVisibility(View.GONE);
                mTv_cz.setVisibility(View.GONE);
                mLine3.setVisibility(View.VISIBLE);
                mLine4.setVisibility(View.VISIBLE);
                mLl_gq.setVisibility(View.GONE);
                mSortTv.setVisibility(View.VISIBLE);
                if (layerName.contains("中心城区")) {
                    initValue(component, mField3Tv, "管线长度：", ComponentFieldKeyConstant.LENGTH, "m");
                } else {
                    initValue(component, mField3Tv, "管线长度：", ComponentFieldKeyConstant.PIPE_LENGTH, "m");
                }
//                subtype = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.GRADE), "");
                initValue(component, mTv_gc_begin, "起始管底高程：", ComponentFieldKeyConstant.BEG_H, "m");
                initValue(component, mTv_gc_end, "终止管底高程：", ComponentFieldKeyConstant.END_H, "m");
                initValue(component, mTv_ms_begin, "起点管底埋深：", ComponentFieldKeyConstant.BEGCEN_DEE, "m");
                initValue(component, mTv_ms_end, "终点管底埋深：", ComponentFieldKeyConstant.ENDCEN_DEE, "m");
                initValue(component, mTv_gj, "管径：", ComponentFieldKeyConstant.PIPE_GJ, true);
            } else if ("排水沟渠".equals(layerName)) {
                mLine3.setVisibility(View.VISIBLE);
                mLine4.setVisibility(View.VISIBLE);
                mLl_gq.setVisibility(View.VISIBLE);
                mBtn_container.setVisibility(View.GONE);
                mAddrTv.setVisibility(View.VISIBLE);
                mTv_cz.setVisibility(View.VISIBLE);
                mSortTv.setVisibility(View.VISIBLE);
//                subtype = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SUBTYPE), "");
                initValue(component, mTv_gc_begin, "起始渠底高程：", ComponentFieldKeyConstant.BEG_H, "m");
                initValue(component, mTv_gc_end, "终止渠底高程：", ComponentFieldKeyConstant.END_H, "m");
                initValue(component, mTv_ms_begin, "起点管底埋深：", ComponentFieldKeyConstant.BEGCIN_DEEP, "m");
                initValue(component, mTv_ms_end, "终点管底埋深：", ComponentFieldKeyConstant.ENDCIN_DEEP, "m");
                initValue(component, mTv_gj, "宽度：", ComponentFieldKeyConstant.WIDTH, "m");
                initValue(component, mTv_cz, "净高：", ComponentFieldKeyConstant.HEIGHT, "m");
                initValue(component, mTv_tycd, "投影长度：", ComponentFieldKeyConstant.LENGTH, "m");
                initValue(component, mTv_pd, "坡度：", ComponentFieldKeyConstant.IP, "m");
            } else {
                mLine3.setVisibility(View.GONE);
                mLine4.setVisibility(View.GONE);
                mAddrTv.setVisibility(View.GONE);
                mLl_gq.setVisibility(View.GONE);
                mBtn_container.setVisibility(View.VISIBLE);
                mTv_gc_begin.setText("");
                mTv_gc_end.setText("");
                mTv_ms_begin.setText("");
                mTv_ms_end.setText("");
                mTv_gj.setText("");
            }
            TextView tv_errorinfo = (TextView) map_bottom_sheet_gw.findViewById(R.id.tv_errorinfo_gw);
//        TextView tv_parent_org_name = (TextView) map_bottom_sheet.findViewById(R.id.tv_parent_org_name);

            String name = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.NAME));

            String type = component.getLayerName();

            String usid = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.USID));
            String objectId = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.OBJECTID));
            if (StringUtil.isEmpty(usid) || "null".equals(usid)) {
                usid = objectId;
            }
            final String title = StringUtil.getNotNullString(type, "") + "(" + usid + ")";
            mTitleTv.setText(title);

            String date = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.UPDATEDATE));
            String formatDate = "";
            try {
                formatDate = TimeUtil.getStringTimeYMD(new Date(Long.valueOf(date)));
            } catch (Exception e) {

            }
            String sort = "";
            if ("排水管道".equals(layerName)) {
                sort = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SORT));
            } else if ("示意连线".equals(layerName) || "接驳连线".equals(layerName)) {
                sort = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.TYPE));
            } else {
                sort = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_TWO));
            }
            int color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);

            if (sort.contains("雨污合流")) {
                color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);
            } else if (sort.contains("雨水")) {
                color = ResourcesCompat.getColor(getResources(), R.color.progress_line_green, null);
            } else if (sort.contains("污水")) {
                color = ResourcesCompat.getColor(getResources(), R.color.agmobile_red, null);
            }
            mSortTv.setTextColor(color);
            mSortTv.setText(StringUtil.getNotNullString(sort, ""));
//            mSubtypeTv.setText(StringUtil.getNotNullString(subtype, ""));

            //已挂牌编号
            String codeValue = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_FIVE));
            if (!StringUtil.isEmpty(codeValue)) {
                codeValue = codeValue.trim();
            }
            final String address = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ADDR));
            mAddrTv.setText("所在道路" + "：" + StringUtil.getNotNullString(address, ""));
            tv_errorinfo.setVisibility(View.GONE);

//删除按钮
            final String dataType = StringUtil.getNotNullString(component.getGraphic().getAttributes().get("DATA_TYPE"), "");
            mTv_delete_gw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("排水管道".equals(layerName) || "示意连线".equals(layerName) || "接驳连线".equals(layerName)) {
                        if ("3".equals(dataType)) {
                            ToastUtil.shortToast(mContext, "该管线已删除！");
                            return;
                        }
                        DialogUtil.MessageBox(mContext, "提示", "是否确定要删除这条记录？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                currentStream = createStreamBean(component);
                                deletePipe();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    }
                }
            });

            /**
             * 上下游按钮
             */
            mTv_sure_gw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (1 == mStatus) {
                        locationMarker.setVisibility(View.VISIBLE);
                        initStreamGLayer();
                        hideBottomSheet();
                    } else {
                        locationMarker.setVisibility(View.VISIBLE);
                        initGLayer();
                        hideBottomSheet();
                    }
                }
            });


            /**
             * 纠错按钮
             */
            mTv_error_correct_gw.setText("   取消   ");
            mTv_error_correct_gw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isDrawStream = false;
                    if ("排水管道".equals(layerName) || "接驳连线".equals(layerName) || "示意连线".equals(layerName)) {
                        if ("3".equals(dataType)) {
                            ToastUtil.shortToast(mContext, "该管线已删除！");
                            return;
                        }
                        isDrawStream = true;
                        mStatus = 1;
                        currentStream = createStreamBean(component);
                        mAllPipeBeans.clear();
                        mAllPipeBeans.add(currentStream);
                        drawStream(componentCenter, null, mAllPipeBeans, getResources().getColor(R.color.agmobile_red));
                        showComponentPopWindow(component, layerName, false);
                    }
//                    else if (mStatus == 0) {
//                        mStatus = 1;
//                        setMode(mStatus);
//                        ToastUtil.shortToast(mContext, "请移动地图选择关联井");
//                        locationMarker.changeIcon(R.mipmap.ic_check_stream);
//                        if (locationMarker != null) {
//                            locationMarker.changeIcon(R.mipmap.ic_check_related);
//                            locationMarker.setVisibility(View.VISIBLE);
//                        }
//                        tv_query_tip.setText("请移动地图选择关联井");
//                        initStreamGLayer();
//                        hideBottomSheet();
//                        setSearchFacilityListener();
//                    } else if (mStatus == 1) {
//                        isDrawStream = true;
//                        currentStream = createStreamBean(component);
//                        showComponentPopWindow(component, layerName, true);
//                    } else {
//                        mStatus = 2;
//                        setMode(mStatus);
//                        if (btn_check_cancel.getVisibility() == View.VISIBLE) {
//                            btn_check_cancel.performClick();
//                        }
//                        hideBottomSheet();
//                    }
                }
            });
            if ("排水管道".equals(layerName) || "示意连线".equals(layerName) || "接驳连线".equals(layerName)) {
                mStream_line1_gw.setVisibility(View.VISIBLE);
                mTv_delete_gw.setVisibility(View.VISIBLE);
                mStream_line_gw.setVisibility(View.GONE);
                mTv_sure_gw.setVisibility(View.GONE);
                mTv_error_correct_gw.setVisibility(View.VISIBLE);
                mTv_error_correct_gw.setText("确认完善属性");
            } else if (mStatus == 0) {
                mStream_line1_gw.setVisibility(View.GONE);
                mTv_delete_gw.setVisibility(View.GONE);
                mStream_line_gw.setVisibility(View.VISIBLE);
                mTv_sure_gw.setText("   取消   ");
                mTv_sure.setVisibility(View.VISIBLE);
                mTv_error_correct_gw.setText("选择关联井");
            } else if (mStatus == 1 || mStatus == 2) {
                mStream_line1_gw.setVisibility(View.GONE);
                mTv_delete_gw.setVisibility(View.GONE);
                mStream_line_gw.setVisibility(View.VISIBLE);
                mTv_sure_gw.setText("   取消   ");
                mTv_sure_gw.setVisibility(View.VISIBLE);
                mTv_error_correct_gw.setText("设置管线信息");
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    map_bottom_sheet_gw.setVisibility(View.VISIBLE);
                    mBehavior_gw.setState(AnchorSheetBehavior.STATE_EXPANDED);
                }
            }, 200);
        }

        private void deletePipe() {
            pd = new ProgressDialog(getContext());
            pd.setMessage("删除中.....");
            pd.show();
            String dataJson = JsonUtil.getJson(currentStream);
            //TODO
            myUploadService.deletePipe(dataJson)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result2<String>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            ToastUtil.iconShortToast(mContext, R.mipmap.ic_alert_yellow, "删除失败，请稍后重试");
                        }

                        @Override
                        public void onNext(Result2<String> s) {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            if (s.getCode() == 200) {
                                refreshMap();
                                if (mStatus == 1) {
                                    initStreamGLayer();
                                } else if (mStatus == 0) {
                                    initGLayer();
                                }
                                hideBottomSheet();
//                            locationMarker.setVisibility(View.VISIBLE);
                                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                                hideBottomSheet();
//                                EventBus.getDefault().post(new RefreshMyModificationListEvent());
                            } else {
//                            CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
//                                    BaseInfoManager.getUserName(mContext) + "原因是：" + s.getMessage()));
                                Toast.makeText(mContext, s.getMessage(), Toast.LENGTH_SHORT).show();
                            }

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
            int disvisibility = dis_map_bottom_sheet.getVisibility();
            initGLayer();
            hideBottomSheet();
            if (visibility == View.VISIBLE || disvisibility == View.VISIBLE) {
                return;
            }
            final Point point = mMapView.toMapPoint(e.getX(), e.getY());
            Geometry geometry = GeometryEngine.buffer(point, mMapView.getSpatialReference(), 40 * mMapView.getResolution(), null);
            double scale = mMapView.getScale();
            if (scale < LayerUrlConstant.MIN_QUERY_SCALE) {
                if (ifInEditMode || ifInPipeMode) {
                    query(point.getX(), point.getY());
                } else if (ifInPipeMode) {
                    //TODO 管线查询模块代码
                    componentMaintenanceService.queryComponentsForPipeAndYJ(geometry, new Callback2<List<Component>>() {
                        @Override
                        public void onSuccess(List<Component> components) {
                            mNWUploadInfos = components;
                            if (pd != null) {
                                pd.dismiss();
                            }
                            if (ListUtil.isEmpty(components)) {
                                ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                                return;
                            }
                            mComponentQueryResult = new ArrayList<Component>();
                            mComponentQueryResult.addAll(components);
                            if (ListUtil.isEmpty(mComponentQueryResult)) {
                                initGLayer();
                                if (pd != null) {
                                    pd.dismiss();
                                }
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
                            ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                        }
                    });


                } else {
                    queryComponet(point.getX(), point.getY());
                }
                //屏蔽“接驳井”菜单里面井点查中的“校核上报”（不能影响排水对应的功能）
//                query(point.getX(), point.getY());
            }
        }
    }

    private void queryComponet(double x, double y) {
        pd = new ProgressDialog(mContext);
        pd.setMessage("正在查询信息...");
        mUploadInfos.clear();
        pd.show();
        mUploadInfos.clear();
        nextAndPrevContainer.setVisibility(View.GONE);
        btn_next.setVisibility(View.GONE);
        btn_prev.setVisibility(View.GONE);
        btn_prev_gw.setVisibility(View.GONE);
        btn_next_gw.setVisibility(View.GONE);
        currIndex = 0;
        //        final Point point = mMapView.toMapPoint(x, y);
        final Point point = new Point(x, y);

        mCurrentModifiedFacility = null;
        mCurrentUploadedFacility = null;
        mCurrentCompleteTableInfo = null;
        hasComponent = false;
//        tv_error_correct.setVisibility(View.VISIBLE);
        resetStatus(true);
        initComponentService();
        final Geometry geometry = GeometryEngine.buffer(point, mMapView.getSpatialReference(), 40 * mMapView.getResolution(), null);
        componentMaintenanceService.queryUnitComponents(geometry, new Callback2<List<Component>>() {
            @Override
            public void onSuccess(List<Component> components) {
                if (pd != null) {
                    pd.dismiss();
                }
                if (ListUtil.isEmpty(components)) {
                    ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                    return;
                }
                mComponentQueryResult = new ArrayList<Component>();
                mComponentQueryResult.addAll(components);
                if (ListUtil.isEmpty(mComponentQueryResult)) {
                    initGLayer();
                    if (pd != null) {
                        pd.dismiss();
                    }
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
                ToastUtil.shortToast(getContext(), "该地点未查询到设施");
            }
        });
    }

    private void initValue(Component component, TextView tv_gc_begin, String name, String key, boolean isCode) {
        tv_gc_begin.setVisibility(View.VISIBLE);
        String value = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(key), "");
        String gjName = "";
        if (StringUtil.isEmpty(value)) {
            tv_gc_begin.setText(name);
            return;
        }
        if (isCode) {
            for (DictionaryItem dictionaryItem : mA185) {
                if (dictionaryItem.getCode().equals(value)) {
                    gjName = dictionaryItem.getName();
                }
            }
            if (!StringUtil.isEmpty(gjName)) {
                value = gjName;
            }
        }
        String format = name + "：" + value;
        tv_gc_begin.setText(format);
    }

    private void initValue(Component component, TextView tv_gc_begin, String name, String key, String unit) {
        tv_gc_begin.setVisibility(View.VISIBLE);
        String value = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(key), "");
        if (StringUtil.isEmpty(value)) {
            tv_gc_begin.setText(name);
            return;
        }
        String format = name + formatToSecond(value, unit, 2);
        tv_gc_begin.setText(format);
    }

    private void initValue(TextView tv_gc_begin, String key, String value) {
        tv_gc_begin.setVisibility(View.VISIBLE);
        if (StringUtil.isEmpty(value)) {
            tv_gc_begin.setText(key);
            return;
        }
        String format = key + value;
        tv_gc_begin.setText(format);
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
        mUploadInfos.clear();
        mComponentQueryResult.clear();
        btn_next.setVisibility(View.GONE);
        btn_prev.setVisibility(View.GONE);
        currIndex = 0;
        nextAndPrevContainer.setVisibility(View.GONE);
        btn_prev_gw.setVisibility(View.GONE);
        btn_next_gw.setVisibility(View.GONE);
        final Point point = new Point(x, y);
        mCurrentUploadedFacility = null;
        mCurrentCompleteTableInfo = null;
        hasComponent = false;
//        tv_error_correct.setVisibility(View.VISIBLE);
//        final List<LayerInfo> layerInfoList = new ArrayList<>();
//        for (String url : LayerUrlConstant.newComponentUrls) {
//            LayerInfo layerInfo = new LayerInfo();
//            layerInfo.setUrl(url);
//            layerInfoList.add(layerInfo);
//        }
        resetStatus(true);
        Geometry geometry = GeometryEngine.buffer(point, mMapView.getSpatialReference(), 40 * mMapView.getResolution(), null);
        initComponentService();
        if (ifInEditMode || ifInCheckMode) {
            componentMaintenanceService.queryDoorAndWellComponentsNew(geometry, new Callback2<List<Component>>() {
                @Override
                public void onSuccess(List<Component> components) {
                    if (pd != null) {
                        pd.dismiss();
                    }
                    if (ListUtil.isEmpty(components)) {
                        ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                        return;
                    }
                    mComponentQueryResult = new ArrayList<Component>();
                    mComponentQueryResult.addAll(components);
                    if (ListUtil.isEmpty(mComponentQueryResult)) {
                        initGLayer();
                        if (pd != null) {
                            pd.dismiss();
                        }
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
                    ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                }
            });


        } else {
            componentMaintenanceService.queryUnitComponents(geometry, new Callback2<List<Component>>() {
                @Override
                public void onSuccess(List<Component> components) {
                    if (pd != null) {
                        pd.dismiss();
                    }
                    if (ListUtil.isEmpty(components)) {
                        ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                        return;
                    }
                    mComponentQueryResult = new ArrayList<Component>();
                    mComponentQueryResult.addAll(components);
                    if (ListUtil.isEmpty(mComponentQueryResult)) {
                        initGLayer();
                        if (pd != null) {
                            pd.dismiss();
                        }
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
                    ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                }
            });
        }

    }

    private void showComponentPopWindow(final Component component, final String layerName, boolean isLine) {
        String pipeType = "";
        String direction = "";
        String pipeGJ = "";
        String gjName = "";
        String length = "";
        if (ListUtil.isEmpty(mA163)) {
            mA163 = mDbService.getDictionaryByTypecodeInDB("A163");
        }
        if (component != null && component.getGraphic() != null) {
            pipeType = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("SORT"), "");
            direction = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("DIRECTION"), "");
            pipeGJ = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("PIPE_GJ"), "");
            length = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("PIPE_LENGTH"), "");
        }
        final Map<String, Object> spinnerData = new LinkedHashMap<>();
        List<String> allowValues = new ArrayList<>();
        for (DictionaryItem dictionaryItem : mA163) {
            allowValues.add(dictionaryItem.getName());
            spinnerData.put(dictionaryItem.getName(), dictionaryItem);
        }
        final Map<String, Object> spinnerData1 = new LinkedHashMap<>();
        spinnerData1.put("正向", "正向");
        spinnerData1.put("反向", "反向");
        if (ListUtil.isEmpty(mA185)) {
            mA185 = mDbService.getDictionaryByTypecodeInDB("A185");
            Collections.sort(mA185, new Comparator<DictionaryItem>() {
                @Override
                public int compare(DictionaryItem o1, DictionaryItem o2) {
                    String code = o1.getCode();
                    String target = code;
                    if (code.length() > 1) {
                        target = code.replaceAll("[^(0-9)]", "");//去掉所有字母
                    }
                    String code2 = o2.getCode();
                    String target2 = code;
                    if (code2.length() > 1) {
                        target2 = code2.replaceAll("[^(0-9)]", "");//去掉所有字母
                    }
                    int num1 = Integer.valueOf(target);
                    int num2 = Integer.valueOf(target2);
                    int result = 0;
                    if (num1 > num2) {
                        result = 1;
                    }
                    if (num1 < num2) {
                        result = -1;
                    }
                    return result;
                }
            });
        }
        final Map<String, Object> gjData = new LinkedHashMap<>();
        List<String> gjValues = new ArrayList<>();
        for (DictionaryItem dictionaryItem : mA185) {
            if (dictionaryItem.getCode().equals(pipeGJ)) {
                gjName = dictionaryItem.getName();
            }
            gjValues.add(dictionaryItem.getName());
            gjData.put(dictionaryItem.getName(), dictionaryItem);
        }
        btn_reedit = (Button) mView.findViewById(R.id.btn_save2);
        sp_gxcd = (NumberItemTableItem) mView.findViewById(R.id.sp_gxcd);
        if (layerName.contains("排水管道") && StringUtil.isEmpty(length)) {
            mLineLength = getLineLength(originPipe, true);
        } else if (!StringUtil.isEmpty(length)) {
            mLineLength = Double.valueOf(length);
        } else {
            mLineLength = getLineLength(originPipe, currentStream);
        }
        if (mLineLength != -1) {
            sp_gxcd.setText(formatToSecond(mLineLength + "", "", 2));
        } else {
            sp_gxcd.setText("");
        }
        btn_cancel2 = (Button) mView.findViewById(R.id.btn_cancel2);
        sp_ywlx = (SpinnerTableItem) mView.findViewById(R.id.sp_ywlx);
        sp_ywlx.setSpinnerData(spinnerData);
        if (!StringUtil.isEmpty(pipeType)) {
            sp_ywlx.selectItem(pipeType);
        }
        if (isLine && !StringUtil.isEmpty(mFirstAttribute)) {
            sp_ywlx.selectItem(mFirstAttribute);
        }
        sp_lx = (SpinnerTableItem) mView.findViewById(R.id.sp_lx);
        sp_lx.setSpinnerData(spinnerData1);
        sp_lx.setOnSpinnerChangeListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                if (mStatus == 1 || mStatus == 2) {
                    if (position == 0) {
                        currentStream.setDirection("正向");
                    } else {
                        currentStream.setDirection("反向");
                    }
                    mTempPipeBeans.clear();
                    mTempPipeBeans.add(currentStream);
                    drawStream(component, null, mTempPipeBeans, getResources().getColor(R.color.agmobile_red));
                }
            }
        });
        if (!StringUtil.isEmpty(direction)) {
            sp_lx.selectItem(direction);
        } else {
            sp_lx.selectItem(0);
        }
        sp_gj = (SpinnerTableItem) mView.findViewById(R.id.sp_gj);
        sp_gj.setOnSpinnerChangeListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                DictionaryItem dictionaryItem = (DictionaryItem) item;
                mGjCode = dictionaryItem.getCode();
            }
        });
        sp_gj.setSpinnerData(gjData);
        if (!StringUtil.isEmpty(gjName)) {
            sp_gj.selectItem(gjName);
        } else {
            sp_gj.selectItem(0);
        }
        btn_reedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpOrDownStream = false;
                if (component != null) {
                    if (ifInPipeMode) {
                        currentStream.setPipeType(sp_ywlx.getText());
                        currentStream.setDirection(sp_lx.getText());
                        currentStream.setPipeGj(mGjCode);
                        List<PipeBean> children = new ArrayList<>();
                        if (StringUtil.isEmpty(sp_gxcd.getText().trim())) {
                            ToastUtil.shortToast(mContext, "管线长度不能为空");
                            return;
                        }
                        mLineLength = Double.valueOf(sp_gxcd.getText().trim());
                        //提交
                        //修改排水管道属性
                        originPipe.setPipeType(sp_ywlx.getText());
                        originPipe.setDirection(sp_lx.getText());
                        originPipe.setPipeGj(mGjCode);
                        originPipe.setChildren(children);
                        updateLinePipe(pipe_view, originPipe);

                    } else if (mStatus == 1) {
                        currentStream.setPipeType(sp_ywlx.getText());
                        currentStream.setDirection(sp_lx.getText());
                        currentStream.setPipeGj(mGjCode);
                        List<PipeBean> children = new ArrayList<>();
                        if (StringUtil.isEmpty(sp_gxcd.getText().trim())) {
                            ToastUtil.shortToast(mContext, "管线长度不能为空");
                            return;
                        }
                        mLineLength = Double.valueOf(sp_gxcd.getText().trim());
                        //提交
                        if ("排水管道".equals(layerName)) {//修改排水管道属性
                            originPipe.setPipeType(sp_ywlx.getText());
                            originPipe.setDirection(sp_lx.getText());
                            originPipe.setPipeGj(mGjCode);
                            originPipe.setChildren(children);
                            updateLinePipe(pipe_view, originPipe);
                        } else {
                            //新增排水管道
                            children.add(currentStream);
                            originPipe.setChildren(children);
                            mSecondAttribute = sp_ywlx.getText();
                            showDialog(pipe_view, originPipe);
//                            upload(pipe_view, originPipe);
                        }
                        return;
                    } else if (mStatus == 2) {
                        currentStream.setPipeType(sp_ywlx.getText());
                        currentStream.setDirection(sp_lx.getText());
                        currentStream.setPipeGj(mGjCode);
                        mAllPipeBeans.add(currentStream);
                        setMode(mStatus);
                    }
                    initArrowGLayer();
                    hideBottomSheet();
                    drawStream(componentCenter, null, mAllPipeBeans, getResources().getColor(R.color.agmobile_red));
                }
            }
        });
        btn_cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initArrowGLayer();
                isDrawStream = false;
                pipe_view.setVisibility(View.GONE);
                if ("排水管道".equals(layerName)) {
                    mStatus = 0;
//                    initGLayer();
                }
            }
        });
        pipe_view.setVisibility(View.VISIBLE);
    }

    @NonNull
    private void initComponentService() {
        if (componentService == null) {
            componentService = new ComponentService(mContext.getApplicationContext());
        }
        if (componentMaintenanceService == null) {
            componentMaintenanceService = new SewerageFireService(getContext());
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
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(getContext(), drawable);// xjx 改为兼容api19的方式获取drawable
        pictureMarkerSymbol.setOffsetY(16);
        return pictureMarkerSymbol;
    }

    /*
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
 */
    private void initStreamGLayer() {
        if (mStreamGLayer == null) {
            mStreamGLayer = new GraphicsLayer();
            mMapView.addLayer(mStreamGLayer);
        } else {
            mStreamGLayer.removeAll();
        }
    }

    private void hideBottomSheet() {
        map_bottom_sheet.setVisibility(View.GONE);
        dis_map_bottom_sheet.setVisibility(View.GONE);
        map_bottom_sheet_gw.setVisibility(View.GONE);
        final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) locationButton
                .getLayoutParams();
        lp.bottomMargin = bottomMargin;
        locationButton.setLayoutParams(lp);
    }

    private void showComponentsOnBottomSheet(List<Component> componentQueryResult) {
        if (componentQueryResult.size() > 1) {
            btn_next.setVisibility(View.VISIBLE);
        }
        if (mMapView.getCallout().isShowing()) {
            mMapView.getCallout().animatedHide();
        }

        //隐藏marker
        locationMarker.setVisibility(View.GONE);
        //initGLayer();
        showBottomSheet(mComponentQueryResult.get(0));
    }

    private void drawUpStream(Component component, int color) {
        List<Component> upStreams = component.getUpStream();
        if (ListUtil.isEmpty(upStreams)) {
            return;
        }

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
                drawArrow(GeometryUtil.getGeometryCenter(polyline), angle, color, false);
            }

            List<Component> upStream1 = upStream.getUpStream();
            if (!ListUtil.isEmpty(upStream1)) {
                drawUpStream(upStream, color);
            }
        }
    }

    private void drawDownStream(Component component, int color) {
        List<Component> downStreams = component.getDownStream();
        if (ListUtil.isEmpty(downStreams)) {
            return;
        }
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
                drawArrow(GeometryUtil.getGeometryCenter(polyline), angle, color, true);
            }

            List<Component> upStream1 = downStream.getDownStream();
            if (!ListUtil.isEmpty(upStream1)) {
                drawDownStream(downStream, color);
            }
        }
    }

    private PipeBean createStreamBean(Component component) {
        String layerName = component.getLayerName();
        PipeBean pipeBean = new PipeBean();
        pipeBean.setComponentType(layerName);
        pipeBean.setId(StringUtil.getNotNullString(component.getGraphic().getAttributes().get("REPORT_ID"), ""));
        pipeBean.setLackType(StringUtil.getNotNullString(component.getGraphic().getAttributes().get("DATA_TYPE"), ""));
        pipeBean.setObjectId(String.valueOf(component.getGraphic().getAttributes().get("OBJECTID")));
        pipeBean.setOldPipeType(StringUtil.getNotNullString(component.getGraphic().getAttributes().get("SORT"), ""));
        String direction = StringUtil.getNotNullString(component.getGraphic().getAttributes().get("DIRECTION"), "");
        String pipeGj = StringUtil.getNotNullString(component.getGraphic().getAttributes().get("PIPE_GJ"), "");
        if (!StringUtil.isEmpty(direction)) {
            pipeBean.setOldDirection(direction);
            pipeBean.setDirection(direction);
        } else {
            pipeBean.setOldDirection("");
            pipeBean.setDirection("正向");
        }
        pipeBean.setPipeGj(pipeGj);
        if (component.getGraphic().getGeometry() instanceof Point) {
            pipeBean.setX(((Point) component.getGraphic().getGeometry()).getX());
            pipeBean.setY(((Point) component.getGraphic().getGeometry()).getY());
        } else if (component.getGraphic().getGeometry() instanceof Polyline) {
            Polyline polyline = (Polyline) component.getGraphic().getGeometry();
            Point startPoint = polyline.getPoint(0);
            Point endPoint = polyline.getPoint(polyline.getPointCount() - 1);
            pipeBean.setX(startPoint.getX());
            pipeBean.setY(startPoint.getY());
            pipeBean.setEndX(endPoint.getX());
            pipeBean.setEndY(endPoint.getY());
        }
        return pipeBean;
    }

    /**
     * 绘制箭头，请确保调用前已经调用了{@link #initGLayer()}方法
     *
     * @param geometryCenter   箭头摆放的位置
     * @param ifDrawRightArrow 是否绘制的是右箭头，true绘制右箭头，false绘制左箭头
     */
    private void drawArrow(Point geometryCenter, double angle, int color, boolean ifDrawRightArrow) {
        initGLayer();
        PictureMarkerSymbol symbol = null;
        if (ifDrawRightArrow) {
            Drawable tintDrawable = DrawableUtil.tintDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_forward_red_24dp, null), ColorStateList.valueOf(color));
            symbol = new PictureMarkerSymbol(mContext, tintDrawable);
        } else {
            Drawable tintDrawable = DrawableUtil.tintDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_red_24dp, null), ColorStateList.valueOf(color));
            symbol = new PictureMarkerSymbol(mContext, tintDrawable);
        }
        symbol.setAngle((float) angle);
        Graphic graphic = new Graphic(geometryCenter, symbol);
        mGLayer.addGraphic(graphic);
    }


    /**
     * 加载设施信息，显示中底部BottomSheet中
     */
    private void showBottomSheet(final Component component) {


//        String errorInfo = null;
//        Object oErrorInfo = component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ERROR_INFO);
//
//        if (oErrorInfo != null) {
//            errorInfo = oErrorInfo.toString();
//        }
//        TextView tv_cancel_choose = (TextView) map_bottom_sheet.findViewById(R.id.tv_cancel_choose);
//        TextView tv_choose_other = (TextView) map_bottom_sheet.findViewById(R.id.tv_choose_other);
//        View lineView =  map_bottom_sheet.findViewById(R.id.stream_line_gw);
//        if (0 == mStatus || isClick) {
//            componentCenter = component;
//            originPipe = createStreamBean(component);
//        }
//        NWUploadInfo  info  = NWUploadInfo.createNWUploadInfo(component);
//        NWUploadedFacility uploadedFacility = new NWUploadedFacility();
//        uploadedFacility.setX(objectToDouble(component.getGraphic().getAttributes().get("X")));
//        uploadedFacility.setY(objectToDouble(component.getGraphic().getAttributes().get("Y")));
//        uploadedFacility.setYjAttrTwo(objectToString(component.getGraphic().getAttributes().get("雨污类别")));
//        uploadedFacility.setLayerName(objectToString(component.getGraphic().getAttributes().get("LAYER_NAME")));
//        uploadedFacility.setObjectId(objectToString(component.getGraphic().getAttributes().get("OBJECTID")));
//        String layername1 ;//=  objectToString(component.getGraphic().getAttributes().get("LAYER_NAME"));
//        uploadedFacility.setReportType(objectToString(component.getGraphic().getAttributes().get("REPORT_TYPE")));
//        if ("cmb".equals(uploadedFacility.getReportType())) {
//            layername1 = "设施点";
//            uploadedFacility.setLayerName("设施点");
//            uploadedFacility.setCollectType("设施点");
//        } else {
//            layername1 = "井";
//            uploadedFacility.setLayerName("井");
//            uploadedFacility.setCollectType("井");
//        }
//        if(ifInCheckMode || ifInPipeMode) {
//            if (0 == mStatus) {
//                facilityCenter = uploadedFacility;
//                originPipe = createStreamForFacility(uploadedFacility);
//            }
//            if (firstLXInfo == null) {
//                if (ifInCheckMode) {
//                    if (mStatus == 0)
//                        mStatus = 1;
//                }
//                if (mStatus == 1) {
//                    tv_cancel_choose.setVisibility(View.VISIBLE);
//                    tv_cancel_choose.setText("取消");
//                    tv_choose_other.setVisibility(View.VISIBLE);
//                    tv_choose_other.setText("选择关联井或者门牌");
//                    lineView.setVisibility(View.VISIBLE);
//                    tv_cancel_choose.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mStatus = 0;
//                            hideBottomSheet();
//                            setMode(mStatus);
//                        }
//                    });
//
//                    tv_choose_other.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //记录起始设施的信息。
//                            setMode(1);
//                            if (locationMarker != null) {
//                                locationMarker.changeIcon(R.mipmap.ic_check_related);
//                                locationMarker.setVisibility(View.VISIBLE);
//                            }
//                            NWUploadInfo info = NWUploadInfo.createNWUploadInfo(component);
//                            originPipe = createStreamForFacility(info);
//                            firstLXInfo = info;
//                            hideBottomSheet();
//                        }
//                    });
//                }
//            } else {
//                //已经 选中了第一个连线点
//                tv_cancel_choose.setVisibility(View.VISIBLE);
//                tv_cancel_choose.setText("取消");
//                tv_choose_other.setVisibility(View.VISIBLE);
//                tv_choose_other.setText("设置管线信息");
//                lineView.setVisibility(View.VISIBLE);
//                tv_cancel_choose.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mStatus = 1;
//                        hideBottomSheet();
//                        setMode(mStatus);
//                    }
//                });
//
//                tv_choose_other.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (ifInEditMode) {
////                        if ("delete".equals(reportType)) {
////                            ToastUtil.shortToast(mContext, "该窨井已删除！");
////                            return;
////                        }
//                            btn_check.setVisibility(View.VISIBLE);
//                            btn_check_cancel.setVisibility(View.GONE);
//                            if (mCurrentUploadedFacility != null) {
////                            Intent intent = new Intent(mContext, NWReEditUploadFacilityActivity.class);
////                            Bundle bundle = new Bundle();
////                            bundle.putParcelable("data", mCurrentUploadedFacility);
////                            intent.putExtras(bundle);
////                            startActivity(intent);
//                            }
//                        } else if (ifInCheckMode) {
//                            if (mStatus == 0) {
//                                mStatus = 1;
//                                setMode(mStatus);
//                                ToastUtil.shortToast(mContext, "请移动地图选择关联井");
//                                locationMarker.changeIcon(R.mipmap.ic_check_stream);
//                                if (locationMarker != null) {
//                                    locationMarker.changeIcon(R.mipmap.ic_check_related);
//                                    locationMarker.setVisibility(View.VISIBLE);
//                                }
//                                tv_query_tip.setText("请移动地图选择关联井");
//                                initStreamGLayer();
//                                hideBottomSheet();
//                                setSearchFacilityListener();
//                            } else if (mStatus == 1) {
////                            mStatus = 2;
//                                isDrawStream = true;
//                                secondLXInfo= NWUploadInfo.createNWUploadInfo(component);
//                                NWUploadInfo info = NWUploadInfo.createNWUploadInfo(component);
//                                currentStream = createStreamForFacility(info);
//                                if (!StringUtil.isEmpty(originPipe.getObjectId()) && originPipe.getObjectId().equals(objectToString(component.getGraphic().getAttributes().get("OBJECTID")))) {
//                                    ToastUtil.longToast(mContext, "管线起点和终点不能为同一设施！");
//                                    return;
//                                }
//                                NWUploadedFacility uploadedFacility = new NWUploadedFacility();
//                                uploadedFacility.setX(objectToDouble(component.getGraphic().getAttributes().get("X")));
//                                uploadedFacility.setY(objectToDouble(component.getGraphic().getAttributes().get("Y")));
//                                uploadedFacility.setYjAttrTwo(objectToString(component.getGraphic().getAttributes().get("雨污类别")));
//                                uploadedFacility.setLayerName(objectToString(component.getGraphic().getAttributes().get("LAYER_NAME")));
//                                String layername1;//=  objectToString(component.getGraphic().getAttributes().get("LAYER_NAME"));
//                                uploadedFacility.setReportType(objectToString(component.getGraphic().getAttributes().get("REPORT_TYPE")));
//                                if ("cmb".equals(uploadedFacility.getReportType())) {
//                                    layername1 = "设施点";
//                                    uploadedFacility.setLayerName("设施点");
//                                    uploadedFacility.setCollectType("设施点");
//                                } else {
//                                    layername1 = "井";
//                                    uploadedFacility.setLayerName("井");
//                                    uploadedFacility.setCollectType("井");
//                                }
//                                showPopWindow(uploadedFacility, layername1, true);
//                            } else {
//                                mStatus = 2;
//                                setMode(mStatus);
//                                if (btn_check_cancel.getVisibility() == View.VISIBLE) {
//                                    btn_check_cancel.performClick();
//                                }
//                                hideBottomSheet();
//                            }
//                        }
//
//
//                    }
//                });
//            }
//        }
//        TextView titleTv = (TextView) map_bottom_sheet.findViewById(R.id.title);
//        TextView dateTv = (TextView) map_bottom_sheet.findViewById(R.id.date);
//        TextView sortTv = (TextView) map_bottom_sheet.findViewById(R.id.sort);
//        TextView subtypeTv = (TextView) map_bottom_sheet.findViewById(R.id.subtype);
//        TextView field1Tv = (TextView) map_bottom_sheet.findViewById(R.id.field1);
//        TextView field2Tv = (TextView) map_bottom_sheet.findViewById(R.id.field2);
//        TextView field3Tv = (TextView) map_bottom_sheet.findViewById(R.id.field3);
//        TextView addrTv = (TextView) map_bottom_sheet.findViewById(R.id.addr);
//        TextView code = (TextView) map_bottom_sheet.findViewById(R.id.code);
////        addrTv.setVisibility(View.GONE);
//        TextView tv_errorinfo = (TextView) map_bottom_sheet.findViewById(R.id.tv_errorinfo);
//        TextView tv_parent_org_name = (TextView) map_bottom_sheet.findViewById(R.id.tv_parent_org_name);
//        LinearLayout ll_photo = (LinearLayout) map_bottom_sheet.findViewById(R.id.ll_photo);
//        TakePhotoTableItem3 view_photos = (TakePhotoTableItem3) map_bottom_sheet.findViewById(R.id.view_photos);
//        view_photos.setReadOnly();
//        view_photos.setEnabled(false);
//        view_photos.setImageIsShow(false);
//        ll_photo.setVisibility(View.VISIBLE);
//        String menpaiLayerName =  component.getLayerName();
//        boolean isMenPai = !TextUtils.isEmpty(menpaiLayerName);
//        if(isMenPai){
//            ll_photo.setVisibility(View.GONE);
//        }else{
//            observeWellPhotos(component.getObjectId(), view_photos, ll_photo);
//        }
//
//        String  name = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.NAME));
//
//        String type = String.valueOf(component.getGraphic().getAttributes().get("LAYER_NAME"));
////        String subtype = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SUBTYPE));
//        String subtype = String.valueOf(component.getGraphic().getAttributes().get("ATTR_ONE"));
//        String usid = String.valueOf(component.getGraphic().getAttributes().get("OBJECTID"));
//
//        String title = StringUtil.getNotNullString(isMenPai? menpaiLayerName :type, "") + "(" + usid + ")";
//        titleTv.setText(title);
//
//        String date = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.UPDATEDATE));
//        String formatDate = "";
//        try {
//            formatDate = TimeUtil.getStringTimeYMD(new Date(Long.valueOf(date)));
//        } catch (Exception e) {
//
//        }
//        dateTv.setText(StringUtil.getNotNullString(formatDate, ""));
//
////      String sort = String.valueOf(component.getGraphic().getAttributes().get("ORIGIN_ATTR_TWO"));
//        String sort = String.valueOf(component.getGraphic().getAttributes().get("ATTR_TWO"));
//        int color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);
//
//        if (sort.contains("雨污合流")) {
//            color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);
//        } else if (sort.contains("雨水")) {
//            color = ResourcesCompat.getColor(getResources(), R.color.progress_line_green, null);
//        } else if (sort.contains("污水")) {
//            color = ResourcesCompat.getColor(getResources(), R.color.agmobile_red, null);
//        }
//        sortTv.setTextColor(color);
////
////        if (sort.contains("雨污合流")) {
////            sortTv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null));
////        } else if (sort.contains("雨水")) {
////            sortTv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.progress_line_green, null));
////        } else if (sort.contains("污水")) {
////            sortTv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.agmobile_red, null));
////        }
//
//        sortTv.setText(StringUtil.getNotNullString(isMenPai? String.valueOf(component.getGraphic().getAttributes().get("SSXZQH")):sort, ""));
//
//        String listFields = ComponentFieldKeyConstant.getListFieldsByLayerName(component.getLayerName());
//        String[] listFieldArray = listFields.split(",");
//        if (!StringUtil.isEmpty(listFields)
//                && listFieldArray.length == 3) {
//            String field1_1 = listFieldArray[0].split(":")[0];
//            String field1_2 = listFieldArray[0].split(":")[1];
//            String field1 = String.valueOf(component.getGraphic().getAttributes().get(field1_2));
//            field1Tv.setText(field1_1 + "：" + StringUtil.getNotNullString(field1, ""));
//
//            String field2_1 = listFieldArray[1].split(":")[0];
//            String field2_2 = listFieldArray[1].split(":")[1];
//            String field2 = String.valueOf(component.getGraphic().getAttributes().get(field2_2));
//            field2Tv.setText(field2_1 + "：" + StringUtil.getNotNullString(field2, ""));
//
//            String field3_1 = listFieldArray[2].split(":")[0];
//            String field3_2 = listFieldArray[2].split(":")[1];
//            String field3 = String.valueOf(component.getGraphic().getAttributes().get(field3_2));
//            field3Tv.setText(field3_1 + "：" + StringUtil.getNotNullString(field3, ""));
//        }
//        String layerName = String.valueOf(component.getGraphic().getAttributes().get("LAYER_NAME"));
//        /**
//         * 如果是雨水口，显示特性：方形
//         */
//        if (layerName.equals("雨水口")) {
//            String feature = String.valueOf(component.getGraphic().getAttributes().get("ATTR_ONE"));
//            sortTv.setText(StringUtil.getNotNullString(feature, ""));
//        }
//        if ("雨水口".equals(type)) {
//            String style = String.valueOf(component.getGraphic().getAttributes().get("ATTR_ONE"));
//            subtypeTv.setText(StringUtil.getNotNullString(style, ""));
//            subtypeTv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.dust_grey, null));
//        } else {
//            subtypeTv.setText(StringUtil.getNotNullString(subtype, ""));
//        }
//
//        if ("雨水口".equals(type)) {
//            field3Tv.setVisibility(View.GONE);
//        } else {
//            field3Tv.setVisibility(View.VISIBLE);
//        }
//
//
//        //已挂牌编号
//        String codeValue = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.CODE));
//        codeValue = codeValue.trim();
//        if (layerName.equals("窨井")) {
//            code.setVisibility(View.VISIBLE);
//            if (!codeValue.isEmpty()) {
//                code.setText("已挂牌编码" + ":" + StringUtil.getNotNullString(codeValue, ""));
//            } else {
//                code.setText("已挂牌编码" + ":" + "");
//            }
//        } else {
//            code.setVisibility(View.GONE);
//        }
//        if(isMenPai){
//            code.setVisibility(View.VISIBLE);
//            code.setText("是否是工业排水户" + ":" + ("是".equals(component.getGraphic().getAttributes().get("SSGYPSH"))? "是":"否"));
//        }
//        /**
//         * 修改属性三
//         */
//        String field3 = "";
//        if (layerName.equals("窨井")) {
//            field3 = "井盖材质: " + String.valueOf(component.getGraphic().getAttributes().get("ATTR_THREE"));
//        } else if (layerName.equals("排放口")) {
//            field3 = "排放去向: " + String.valueOf(component.getGraphic().getAttributes().get("ATTR_THREE"));
//        }
//        field3Tv.setText(isMenPai? "所在道路: " + String.valueOf(component.getGraphic().getAttributes().get("SSJLX")):field3);
//
//        final String address = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ADDR));
//        addrTv.setText(isMenPai? "门牌位置" + "："+String.valueOf(component.getGraphic().getAttributes().get("DZQC")):
//                "设施位置" + "：" + StringUtil.getNotNullString(address, ""));
//
//        /*
//        if (errorInfo == null) {
//            tv_errorinfo.setVisibility(View.GONE);
//        } else {
//            tv_errorinfo.setVisibility(View.VISIBLE);
//            tv_errorinfo.setText(errorInfo + "?");
//        }
//        */
//        tv_errorinfo.setVisibility(View.GONE);
//
//
//        String parentOrg = String.valueOf(component.getGraphic().getAttributes().get("PARENT_ORG_NAME"));
//        tv_parent_org_name.setText(isMenPai? "门牌编号" + "："+String.valueOf(component.getGraphic().getAttributes().get("MPWZHM"))
//                :"权属单位：" + StringUtil.getNotNullString(parentOrg, ""));
//        ////   showBottomSheetContent(component_intro.getId());
        map_bottom_sheet.setVisibility(View.VISIBLE);
        TextView titleTv = (TextView) map_bottom_sheet.findViewById(R.id.title);
        TextView dateTv = (TextView) map_bottom_sheet.findViewById(R.id.date);
        TextView sortTv = (TextView) map_bottom_sheet.findViewById(R.id.sort);
        TextView subtypeTv = (TextView) map_bottom_sheet.findViewById(R.id.subtype);
        TextView field1Tv = (TextView) map_bottom_sheet.findViewById(R.id.field1);
        TextView field2Tv = (TextView) map_bottom_sheet.findViewById(R.id.field2);
        TextView field3Tv = (TextView) map_bottom_sheet.findViewById(R.id.field3);
        TextView addrTv = (TextView) map_bottom_sheet.findViewById(R.id.addr);
//        TextView code = (TextView) map_bottom_sheet.findViewById(R.id.code);
        TextView delete = (TextView) map_bottom_sheet.findViewById(R.id.tv_delete);
        TextView modify = (TextView) map_bottom_sheet.findViewById(R.id.tv_choose_other);
        delete.setVisibility(View.VISIBLE);
        modify.setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.stream_line_gw).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.attribute_ll).setVisibility(View.VISIBLE);
//        addrTv.setVisibility(View.GONE);
        TextView tv_errorinfo = (TextView) map_bottom_sheet.findViewById(R.id.tv_errorinfo);
        TextView tv_parent_org_name = (TextView) map_bottom_sheet.findViewById(R.id.tv_parent_org_name);
        LinearLayout ll_photo = (LinearLayout) map_bottom_sheet.findViewById(R.id.ll_photo);
        TakePhotoTableItem3 view_photos = (TakePhotoTableItem3) map_bottom_sheet.findViewById(R.id.view_photos);
        view_photos.setReadOnly();
        view_photos.setEnabled(false);
        view_photos.setImageIsShow(false);
        ll_photo.setVisibility(View.VISIBLE);

        bean = changeFireBean(component);
        titleTv.setText(component.getLayerName()+"("+bean.getObjectId()+")");
        addrTv.setText("地址:"+bean.getAddr());
//        code.setText("编号:"+bean.getObjectId()+"");
        tv_errorinfo.setText("上报人:"+ (TextUtils.isEmpty(bean.getMarkPerson())
                ||"null".equals(bean.getMarkPerson()) ?"":bean.getMarkPerson()));
        sortTv.setText("是否完好:"+bean.getSfwh());
        subtypeTv.setText("是否漏水:"+bean.getSfls());
        tv_parent_org_name.setText("所在位置:"+bean.getSzwz());
        field1Tv.setText("镇街:"+bean.getTown());
        field2Tv.setText("居委会:"+bean.getVillage());
        final String markId = bean.getMarkId();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(markId);
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PSHUploadNewFireActivity.class);
                //统一参数的处理
                bean.setSfwh("是".equals(bean.getSfwh())?"1":"0");
                bean.setSfls("是".equals(bean.getSfls())?"1":"0");
                bean.setSzwz("排水单元内部".equals(bean.getSzwz())?"1":"0");
                intent.putExtra("groundfireBean",bean);
                intent.putExtra("isFromMap",true);
                startActivity(intent);
                hideBottomSheet();
            }
        });


        observeFirePhotos(component.getObjectId(), view_photos, ll_photo);
        dis_detail_container.postDelayed(new Runnable() {
            @Override
            public void run() {
//                mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);
                mDisBehavior.setState(com.augurit.am.cmpt.widget.bottomsheet.BottomSheetBehavior.STATE_ANCHOR);
            }
        }, 200);
    }

    private GroundfireBean changeFireBean(Component component) {
        GroundfireBean bean = new GroundfireBean();
        Map<String, Object> attributes = component.getGraphic().getAttributes();

        bean.setAddr(attributes.get("ADDR")+"");
        bean.setObjectId(Long.valueOf(attributes.get("OBJECTID")+""));
        bean.setMarkPerson(attributes.get("MARK_PERSON")+"");
        bean.setMarkPersonId(attributes.get("MARK_PERSON_ID")+"");
        bean.setSfwh("1".equals(attributes.get("SFWH") + "") ? "是" : "否");
        bean.setSfls ("1".equals(attributes.get("SFLS") + "") ? "是" : "否");
        bean.setSzwz("1".equals(attributes.get("SZWZ") + "") ? "排水单元内部" : "公共区域");
        bean.setTown(attributes.get("TOWN") + "");
        bean.setArea(attributes.get("KJAREA") + "");
        bean.setRoad(attributes.get("ROAD") + "");
        bean.setVillage(attributes.get("VILLAGE") + "");
        bean.setMarkId(attributes.get("MARK_ID") + "");
        bean.setX(Double.valueOf(attributes.get("X")+""));
        bean.setY(Double.valueOf(attributes.get("Y")+""));
        if(attributes.get("MARK_TIME") == null){
            bean.setMarkTime(333333l);
        }else{
            bean.setMarkTime(Long.valueOf(attributes.get("MARK_TIME")+""));
        }
        return  bean;
    }

    private void showDeleteDialog(final String markId) {
        DialogUtil.MessageBox(mContext, "提醒", "是否确定要删除？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete(markId);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    private void delete(String markId) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("正在删除，请等待");
            progressDialog.setCancelable(false);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        pshUploadFireService = new PSHUploadFireService(mContext);
        pshUploadFireService.toDeleteFire(markId+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        showToast("删除失败");
                        CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
                                BaseInfoManager.getUserName(mContext) + "原因是：" + e.getLocalizedMessage()));
                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (responseBody.getCode() == 200) {
                            showToast("删除成功");
                        } else {
                            showToast("删除失败，原因是：" + responseBody.getMessage());
                            CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
                                    BaseInfoManager.getUserName(mContext) + "原因是：" + responseBody.getMessage()));
                        }
                    }
                });
    }
    private PipeBean createStreamForFacility(NWUploadInfo firstLXInfo) {
        Component component = firstLXInfo.getComponent();
        Map<String, Object> attribute = component.getGraphic().getAttributes();
        String layerName   = objectToString(attribute.get("LAYER_NAME"));
        PipeBean pipeBean = new PipeBean();
        pipeBean.setComponentType(layerName);
        pipeBean.setLackType(StringUtil.getNotNullString(objectToString(attribute.get("LAYER_NAME")), ""));
        pipeBean.setObjectId(objectToString(attribute.get("OBJECTID")));
//        pipeBean.setOldPipeType(StringUtil.getNotNullString(facility.getYjAttrTwo(), ""));
        String direction = "";
        if (!StringUtil.isEmpty(direction)) {
            pipeBean.setOldDirection(direction);
            pipeBean.setDirection(direction);
        } else {
            pipeBean.setOldDirection("");
            pipeBean.setDirection("正向");
        }
        pipeBean.setX(objectToDouble(attribute.get("X")));
        pipeBean.setY(objectToDouble(attribute.get("Y")));
        return pipeBean;

    }

    private void showPopWindow(final NWUploadedFacility facility, final String layerName, boolean isLine) {
        String pipeType = "";
        String direction = "";
        String pipeGJ = "";
        String gjName = "";
        String length ="";
        if(ListUtil.isEmpty(mA163)) {
            mA163 = mDbService.getDictionaryByTypecodeInDB("A163");
        }
        if (facility != null) {
            pipeType = StringUtil.getNotNullString(facility.getYjAttrTwo(), "");
            direction = "";
        }
        final Map<String, Object> spinnerData = new LinkedHashMap<>();
        List<String> allowValues = new ArrayList<>();
        for (DictionaryItem dictionaryItem : mA163) {
            allowValues.add(dictionaryItem.getName());
            spinnerData.put(dictionaryItem.getName(), dictionaryItem);
        }
        if (ListUtil.isEmpty(mA185)) {
            mA185 = mDbService.getDictionaryByTypecodeInDB("A185");
            Collections.sort(mA185, new Comparator<DictionaryItem>() {
                @Override
                public int compare(DictionaryItem o1, DictionaryItem o2) {
                    String code = o1.getCode();
                    String target = code;
                    if (code.length() > 1) {
                        target = code.replaceAll("[^(0-9)]", "");//去掉所有字母
                    }
                    String code2 = o2.getCode();
                    String target2 = code;
                    if (code2.length() > 1) {
                        target2 = code2.replaceAll("[^(0-9)]", "");//去掉所有字母
                    }
                    int num1 = Integer.valueOf(target);
                    int num2 = Integer.valueOf(target2);
                    int result = 0;
                    if (num1 > num2) {
                        result = 1;
                    }
                    if (num1 < num2) {
                        result = -1;
                    }
                    return result;
                }
            });
        }
        final Map<String, Object> gjData = new LinkedHashMap<>();
        List<String> gjValues = new ArrayList<>();
        for (DictionaryItem dictionaryItem : mA185) {
            if(dictionaryItem.getCode().equals(pipeGJ)){
                gjName = dictionaryItem.getName();
            }
            gjValues.add(dictionaryItem.getName());
            gjData.put(dictionaryItem.getName(), dictionaryItem);
        }
        final Map<String, Object> spinnerData1 = new LinkedHashMap<>();
        spinnerData1.put("正向", "正向");
        spinnerData1.put("反向", "反向");
        btn_reedit = (Button) mView.findViewById(R.id.btn_save2);
        sp_gxcd = (NumberItemTableItem) mView.findViewById(R.id.sp_gxcd);
        if (layerName.contains("排水管道") && StringUtil.isEmpty(length)) {
            mLineLength = getLineLength(originPipe, true);
        } else if (!StringUtil.isEmpty(length)) {
            mLineLength = Double.valueOf(length);
        } else{
            mLineLength = getLineLength(originPipe, currentStream);
        }
        if (mLineLength != -1) {
            sp_gxcd.setText(formatToSecond(mLineLength+"","",2));
        } else {
            sp_gxcd.setText("");
        }
        btn_cancel2 = (Button) mView.findViewById(R.id.btn_cancel2);
        sp_ywlx = (SpinnerTableItem) mView.findViewById(R.id.sp_ywlx);
        sp_ywlx.setSpinnerData(spinnerData);
        if (!StringUtil.isEmpty(pipeType)) {
            sp_ywlx.selectItem(pipeType);
        }
        if (isLine && !StringUtil.isEmpty(mFirstAttribute)) {
            sp_ywlx.selectItem(mFirstAttribute);
        }
        sp_lx = (SpinnerTableItem) mView.findViewById(R.id.sp_lx);
        sp_lx.setSpinnerData(spinnerData1);
        sp_lx.setOnSpinnerChangeListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                if (mStatus == 1 || mStatus == 2) {
                    if (position == 0) {
                        currentStream.setDirection("正向");
                    } else {
                        currentStream.setDirection("反向");
                    }
                    mTempPipeBeans.clear();
                    mTempPipeBeans.add(currentStream);
                    drawStream(componentCenter, facilityCenter, mTempPipeBeans, getResources().getColor(R.color.agmobile_red));
                }
            }
        });
        if (!StringUtil.isEmpty(direction)) {
            sp_lx.selectItem(direction);
        } else {
            sp_lx.selectItem(0);
        }
        sp_gj = (SpinnerTableItem) mView.findViewById(R.id.sp_gj);
        sp_gj.setOnSpinnerChangeListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                DictionaryItem dictionaryItem = (DictionaryItem) item;
                mGjCode = dictionaryItem.getCode();
            }
        });
        sp_gj.setSpinnerData(gjData);
        if(!StringUtil.isEmpty(gjName)){
            sp_gj.selectItem(gjName);
        }else{
            sp_gj.selectItem(0);
        }
        btn_reedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpOrDownStream = false;
                if (facility != null) {
                    if (mStatus == 1) {
                        currentStream.setPipeType(sp_ywlx.getText());
                        currentStream.setDirection(sp_lx.getText());
                        currentStream.setPipeGj(mGjCode);
                        List<PipeBean> children = new ArrayList<>();
                        if (StringUtil.isEmpty(sp_gxcd.getText().trim())) {
                            ToastUtil.shortToast(mContext, "管线长度不能为空");
                            return;
                        }
                        mLineLength = Double.valueOf(sp_gxcd.getText().trim());
                        //提交
//                        if ("排水管道".equals(layerName)) {//修改排水管道属性
//                            originPipe.setPipeType(sp_ywlx.getText());
//                            originPipe.setDirection(sp_lx.getText());
//                            originPipe.setPipeGj(mGjCode);
//                            originPipe.setChildren(children);
//                            updateLinePipe(pipe_view, originPipe);
//                        } else {
                            //新增排水管道
                            children.add(currentStream);
                            originPipe.setChildren(children);
                            mSecondAttribute = sp_ywlx.getText();
                            showDialog(pipe_view, originPipe);
                            upload(pipe_view, originPipe);
//                        }
                        return;
                    } else if (mStatus == 2) {
                        currentStream.setPipeType(sp_ywlx.getText());
                        currentStream.setDirection(sp_lx.getText());
                        currentStream.setPipeGj(mGjCode);
                        mAllPipeBeans.add(currentStream);
                        setMode(mStatus);
                    }
                    initArrowGLayer();
                    hideBottomSheet();
                    drawStream(componentCenter, facility, mAllPipeBeans, getResources().getColor(R.color.agmobile_red));
                }
            }
        });
        btn_cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initArrowGLayer();
                isDrawStream = false;
                pipe_view.setVisibility(View.GONE);
                if ("排水管道".equals(layerName)) {
                    mStatus = 0;
//                    initGLayer();
                }
            }
        });
        pipe_view.setVisibility(View.VISIBLE);
    }
    private double getLineLength(PipeBean startStream, PipeBean endStream) {
        Polyline mLine = new Polyline();
        if (startStream != null && endStream != null) {
            mLine.startPath(new Point(startStream.getX(), startStream.getY()));
            mLine.lineTo(endStream.getX(), endStream.getY());
            return GeometryEngine.geodesicLength(mLine, mMapView.getSpatialReference(), new LinearUnit(LinearUnit.Code.METER));
        } else {
            return 0;
        }
    }

    private double getLineLength(PipeBean currentStream, boolean isUpdate) {
        Polyline mLine = new Polyline();
        if (currentStream != null) {
            mLine.startPath(new Point(currentStream.getX(), currentStream.getY()));
            if (isUpdate) {
                mLine.lineTo(currentStream.getEndX(), currentStream.getEndY());
                return GeometryEngine.geodesicLength(mLine, mMapView.getSpatialReference(), new LinearUnit(LinearUnit.Code.METER));
            }
            List<PipeBean> pipeBeans = currentStream.getChildren();
            if (!ListUtil.isEmpty(pipeBeans)) {
                PipeBean pipeBean = pipeBeans.get(0);
                mLine.lineTo(pipeBean.getX(), pipeBean.getY());
                return GeometryEngine.geodesicLength(mLine, mMapView.getSpatialReference(), new LinearUnit(LinearUnit.Code.METER));
            } else {
                return 0;
            }
        }
        return 0;
    }

    private String formatToSecond(String value, String unit,int digit) {
        if (StringUtil.isEmpty(value)) {
            return "";
        }
        Double num = Double.valueOf(value);
        BigDecimal bd = new BigDecimal(num);
        num = bd.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
        return num + "" + unit;
    }

    /**
     * 新增排水管道
     *
     * @param dialog
     * @param currentStream
     */
    private void updateLinePipe(final ViewGroup dialog, PipeBean currentStream) {
        pd = new ProgressDialog(getContext());
        pd.setMessage("正在提交，请稍后...");
        pd.show();
        currentStream.setLineLength(mLineLength);
        String dataJson = JsonUtil.getJson(currentStream);
        myUploadService.updateLinePipe(dataJson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        ToastUtil.shortToast(mContext, "提交失败");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (responseBody.getCode() == 200) {
                            mStatus = 0;
                            //提交文件
                            isDrawStream = false;
                            initStreamGLayer();
                            initArrowGLayer();
                            initGLayer();
                            refreshMap();
                            hideBottomSheet();
                            ToastUtil.shortToast(mContext, "提交成功");
                            dialog.setVisibility(View.GONE);
                        } else {
                            ToastUtil.shortToast(mContext, "保存失败，原因是：" + responseBody.getMessage());
                        }
                    }
                });
    }
    private void showDialog(final ViewGroup dialog1, final PipeBean currentStream) {
        String message = "";
        if (!StringUtil.isEmpty(mFirstAttribute) && mFirstAttribute.equals(mSecondAttribute)) {
            upload(dialog1, currentStream);
            return;
        } else if (!StringUtil.isEmpty(mFirstAttribute)) {
            String name = StringUtil.isEmpty(mSecondAttribute) ? "空" : mSecondAttribute;
            message = "起点为" + mFirstAttribute + "窨井，连线雨污类别为" + name + "，确定连线吗？";
        } else {
            message = "起点窨井雨污类别为空，确定连线吗？";
        }
        DialogUtil.MessageBox(mContext, "提示", message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                upload(dialog1, currentStream);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
    /**
     * 新增排水管道
     *
     * @param dialog
     * @param currentStream
     */
    private void upload(final ViewGroup dialog, PipeBean currentStream) {
        pd = new ProgressDialog(getContext());
        pd.setMessage("正在提交...");
        pd.show();

        currentStream.setLineLength(mLineLength);
        String dataJson = JsonUtil.getJson(currentStream);
        myUploadService.addLinePipe(dataJson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        ToastUtil.shortToast(mContext, "提交失败");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }

                        firstLXInfo = secondLXInfo;
                        secondLXInfo = null;
                        if (responseBody.getCode() == 200) {
                            ToastUtil.shortToast(mContext, "提交成功");
                            refreshMap();
                            isDrawStream = false;
                            tv_query_tip.setText("继续关联设施点、井或取消");
                            initStreamGLayer();
                            initArrowGLayer();
                            hideBottomSheet();
                            dialog.setVisibility(View.GONE);
                        } else {
                            ToastUtil.shortToast(mContext, "保存失败，原因是：" + responseBody.getMessage());
                        }
                    }
                });
    }



    /**
     * 刷新地图
     */
    public void refreshMap() {
        Layer[] layers = mMapView.getLayers();
        for (Layer layer : layers) {
            if (layer instanceof ArcGISDynamicMapServiceLayer) {
                ((ArcGISDynamicMapServiceLayer) layer).refresh();
            }
        }
    }
    private void drawStream(Component component, NWUploadedFacility uploadedFacility, List<PipeBean> pipeBeans, int color) {
        initArrowGLayer();
        if (ListUtil.isEmpty(pipeBeans)) {
            return;
        }
        if (uploadedFacility != null) {
            Point componentCenter = new Point(uploadedFacility.getX(), uploadedFacility.getY());
            for (PipeBean upStream : pipeBeans) {
                Polyline polyline = new Polyline();
                Point geometryCenter = new Point(upStream.getX(), upStream.getY());
                if (geometryCenter != null) {
                    polyline.startPath(geometryCenter);
                    polyline.lineTo(componentCenter);
                    drawGeometry(polyline, mArrowGLayer, new SimpleLineSymbol(color, 3), false, false);
                    //如果上游，那么此时箭头方向是指向当前点击的部件
                    //上游点
                    Point point = mMapView.toScreenPoint(geometryCenter);
                    //当前点
                    Point point2 = mMapView.toScreenPoint(componentCenter);
                    double angle = GeometryUtil.getAngle(point.getX(), point.getY(), point2.getX(), point2.getY());
                    if ("反向".equals(upStream.getDirection())) {
                        drawArrow(GeometryUtil.getGeometryCenter(polyline), angle, color, false);
                    } else {
                        drawArrow(GeometryUtil.getGeometryCenter(polyline), angle, color, true);
                    }
                }
            }
        } else {
            drawPipeline(component, pipeBeans, color);
        }
    }

    private void drawPipeline(Component component, List<PipeBean> pipeBeans, int color) {
        initArrowGLayer();
        if (ListUtil.isEmpty(pipeBeans) || component.getGraphic() == null || component.getGraphic().getGeometry() == null) {
            return;
        }
        PipeBean pipeBean = pipeBeans.get(0);
        Polyline polyline = (Polyline) component.getGraphic().getGeometry();
        Point startPoint = mMapView.toScreenPoint(polyline.getPoint(0));
        Point endPoint = mMapView.toScreenPoint(polyline.getPoint(polyline.getPointCount() - 1));
        double angle = GeometryUtil.getAngle(endPoint.getX(), endPoint.getY(), startPoint.getX(), startPoint.getY());
        if ("反向".equals(pipeBean.getDirection())) {
            drawArrow(GeometryUtil.getGeometryCenter(polyline), angle, color, false);
        } else {
            drawArrow(GeometryUtil.getGeometryCenter(polyline), angle, color, true);
        }
    }

    private PipeBean createStreamForFacility(NWUploadedFacility facility) {

        String layerName = facility.getLayerName();
        PipeBean pipeBean = new PipeBean();
        pipeBean.setComponentType(layerName);
//        pipeBean.setId(StringUtil.getNotNullString(facility.getId(), ""));
        pipeBean.setLackType(StringUtil.getNotNullString(facility.getComponentType(), ""));
        pipeBean.setObjectId(facility.getObjectId());
        pipeBean.setOldPipeType(StringUtil.getNotNullString(facility.getYjAttrTwo(), ""));
        String direction = "";
        if (!StringUtil.isEmpty(direction)) {
            pipeBean.setOldDirection(direction);
            pipeBean.setDirection(direction);
        } else {
            pipeBean.setOldDirection("");
            pipeBean.setDirection("正向");
        }
        pipeBean.setX(facility.getX());
        pipeBean.setY(facility.getY());
        return pipeBean;
    }

    private void observeFirePhotos(Integer objectId, final TakePhotoTableItem3 view_photos, final LinearLayout ll_photo) {

        if (pshUploadFireService == null) {
            pshUploadFireService = new PSHUploadFireService(getActivity());
        }
        pshUploadFireService.getFirePhotos(objectId + "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WellPhoto>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError",e.toString());
                    }

                    @Override
                    public void onNext(WellPhoto wellPhoto) {
                        if (wellPhoto == null || wellPhoto.getCode() != 200) {
                            ToastUtil.shortToast(getActivity(), "获取地上式消防栓图片失败");
                            return;
                        }
                        List<WellPhoto.RowsBean> rows = wellPhoto.getRows();
                        if (!ListUtil.isEmpty(rows)) {
                            final List<Photo> welPhotos = new ArrayList<>();
                            for (WellPhoto.RowsBean rowsPhoto : rows) {
                                Photo photo = new Photo();
                                photo.setPhotoName(rowsPhoto.getAttName());
                                photo.setHasBeenUp(1);
                                photo.setPhotoPath(rowsPhoto.getAttPath());
                                photo.setThumbPath(rowsPhoto.getThumPath());
                                photo.setField1("photo");
                                photo.setId(TextUtils.isEmpty(rowsPhoto.getId())?1l:Long.valueOf(rowsPhoto.getId()));
                                if(Attachment2PhotoUtil.FIRE_TYPE.equals(rowsPhoto.getAttType())){
                                    welPhotos.add(photo);
                                }
                            }
                            bean.setPhotos8(welPhotos);
                            view_photos.setSelectedPhotos(welPhotos);
                            ViewGroup.LayoutParams layoutParams = map_bottom_sheet.getLayoutParams();
                            if (layoutParams != null) {
                                layoutParams.height = Util.dpToPx(340);
                                map_bottom_sheet.setLayoutParams(layoutParams);
                            }
                        } else {
                            ll_photo.setVisibility(View.GONE);
                            ViewGroup.LayoutParams layoutParams = map_bottom_sheet.getLayoutParams();
                            if (layoutParams != null) {
                                layoutParams.height = Util.dpToPx(210);
                                map_bottom_sheet.setLayoutParams(layoutParams);
                            }
                        }
                    }
                });

    }

    private Toast toast;
    public void showToast(String toastString) {
        if (toast == null || toast.getView().findViewById(R.id.tv_toast_text) == null) {
            initToast(toastString);
        } else {
            ((TextView) toast.getView().findViewById(com.augurit.am.fw.R.id.tv_toast_text)).setText(toastString);
        }
        //显示提示
        toast.show();
    }

    public void initToast(String toastString) {
        //使用带图标的Toast提示显示
        toast = new Toast(mContext);
        //设置Tosat的属性，如显示时间
        toast.setDuration(Toast.LENGTH_SHORT);
        View view1 = View.inflate(mContext, com.augurit.am.fw.R.layout.view_toast, null);
        ((TextView) view1.findViewById(com.augurit.am.fw.R.id.tv_toast_text)).setText(toastString);
        ((ImageView) view1.findViewById(com.augurit.am.fw.R.id.iv_icon)).setImageResource(R.mipmap.ic_alert_yellow);
        //将布局管理器添加进Toast
        toast.setView(view1);
    }
    /**
     * 检测是否有同区的人已经上报过(校核过)
     *
     * @param component
     */
    private void checkIfHasBeenUploadBefore(Component component, int type) {
        if (component.getGraphic() != null && component.getGraphic().getAttributes() != null
                && component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.OBJECTID) != null) {
            //通过usid去请求接口是否已经有同区的人报过
            String objectId = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.OBJECTID), "");
            CorrectFacilityService correctFacilityService = new CorrectFacilityService(mContext.getApplicationContext());
//            correctFacilityService.getGwyxtLastRecord(objectId, type)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<CheckResult>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            //查询失败，允许上报，但是在上报前要再查询一次是否有人校核过
//                            //  map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.VISIBLE);
//                            if (tv_check_person_gw != null) {
//                                tv_check_person_gw.setText("");
//                            }
//                            if (ll_check_hint_gw != null) {
//                                ll_check_hint_gw.setVisibility(View.GONE);
//                            }
//                        }
//
//                        @Override
//                        public void onNext(CheckResult stringResult2) {
//                            if (stringResult2 == null || stringResult2.getCode() != 200) {
//                                //说明没有人上报过，显示“校核”按钮
//                                //  map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.VISIBLE);
//                                if (tv_check_person_gw == null) {
//                                    return;
//                                }
//                                ll_check_hint_gw.setVisibility(View.GONE);
//                                status = "2";
//                                person = "";
//                            } else {
//                                String checkPersonName = stringResult2.getMarkPerson();
//                                if (BaseInfoManager.getUserName(mContext).equals(checkPersonName)) {
//                                    checkPersonName = "您";
//                                }
//                                ll_check_hint_gw.setVisibility(View.VISIBLE);
//                                tv_check_person_gw.setVisibility(View.VISIBLE);
//                                String date = TimeUtil.getStringTimeYMD(new Date(stringResult2.getMarkTime()));
//                                tv_check_person_gw.setText(checkPersonName + "已经在" + date);
//                                person = checkPersonName;
//                                tv_check_phone_gw.setVisibility(View.VISIBLE);
//                                String handle = "";
//                                if ("0".equals(stringResult2.getHandle())) {
//                                    status = "0";
//                                    handle = "校核";
//                                } else if ("1".equals(stringResult2.getHandle())) {
//                                    status = "1";
//                                    handle = "新增";
//                                } else if ("3".equals(stringResult2.getHandle())) {
//                                    status = "3";
//                                    handle = "删除";
//                                }
//                                tv_check_phone_gw.setText(handle);
//                                //已经校核过该设施，该设施共被校核" + checkPerson.getTotal() + "次"
//                                tv_check_hint_gw.setText("过该设施");
//                            }
//                        }
//                    });
        }
    }
    /**
     * 检测是否有同区的人已经上报过(校核过)
     *
     * @param component
     */
    private void checkIfHasBeenUploadBefore(Component component) {
        if (component.getGraphic() != null && component.getGraphic().getAttributes() != null
                && component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.USID) != null) {
            //通过usid去请求接口是否已经有同区的人报过
            String usid = component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.USID).toString();
            CorrectFacilityService correctFacilityService = new CorrectFacilityService(mContext.getApplicationContext());
            correctFacilityService.checkIfTheFacilityHasUploadedBefore(usid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result2<CheckUploadRecordResult>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            //查询失败，允许上报，但是在上报前要再查询一次是否有人校核过
                            //  map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.VISIBLE);
                            tv_check_person.setText("");
                            ll_check_hint.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNext(Result2<CheckUploadRecordResult> stringResult2) {
                            final CheckUploadRecordResult checkPerson = stringResult2.getData();
                            if (checkPerson == null) {
                                //说明没有人上报过，显示“校核”按钮
                                //  map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.VISIBLE);
                                ll_check_hint.setVisibility(View.GONE);
                            } else {
                                String checkPersonName = checkPerson.getUserName();
                                if (BaseInfoManager.getLoginName(mContext).equals(checkPerson.getPhone())) {
                                    checkPersonName = "您";
                                }
                                ll_check_hint.setVisibility(View.VISIBLE);
                                if (checkPersonName.equals("您") || TextUtils.isEmpty(checkPerson.getPhone())) {
                                    tv_check_person.setText(checkPersonName);
                                    tv_check_phone.setVisibility(View.GONE);
                                    map_bottom_sheet.findViewById(R.id.iv_phone).setVisibility(View.GONE);
                                } else {
                                    map_bottom_sheet.findViewById(R.id.iv_phone).setVisibility(View.VISIBLE);
                                    tv_check_person.setText(checkPersonName + "(");
                                    tv_check_phone.setText(checkPerson.getPhone() + ")");
                                    tv_check_person.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + checkPerson.getPhone()));
                                            startActivity(intent);
                                        }
                                    });
                                    tv_check_phone.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + checkPerson.getPhone()));
                                            startActivity(intent);
                                        }
                                    });
                                }
                                tv_check_hint.setText("已经校核过该设施，该设施共被校核" + checkPerson.getTotal() + "次");
                            }
                        }
                    });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectComponent(SelectComponentEvent selectComponentEvent) {
        Component component = selectComponentEvent.getComponent();
        currComponentUrl = component.getLayerUrl();
        layerAdapter.notifyDataSetChanged(LayerUrlConstant.getIndexByUnknowsLayerUrl(currComponentUrl));
        showBottomSheet(component);
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

                    //恢复点击事件
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                    //隐藏marker
                    locationMarker.setVisibility(View.GONE);

                    LocationInfo locationInfo = addModeSelectLocationTouchListener.getLoationInfo();
                    if (locationInfo.getDetailAddress() == null || locationInfo.getPoint() == null) {
                        ToastUtil.shortToast(mContext, "请重新选择位置");
                        return;
                    }

                    btn_add.setVisibility(View.VISIBLE);
                    btn_cancel.setVisibility(View.GONE);
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());

                    Intent intent = new Intent(mContext, PSHUploadNewFireActivity.class);
                    intent.putExtra("detailAddress", locationInfo.getDetailAddress());
                    intent.putExtra("x", locationInfo.getPoint().getX());
                    intent.putExtra("y", locationInfo.getPoint().getY());
                    startActivity(intent);
                }
            };
        }

        /**
         * 气泡中“确定”按钮点击事件
         */
        addModeSelectLocationTouchListener.setCalloutSureButtonClickListener(addModeCalloutSureButtonClickListener);
        mMapView.setOnTouchListener(addModeSelectLocationTouchListener);
    }

    private void showYJOnBottomSheet() {
//        if (uploadInfos.size() > 1) {
//            nextAndPrevContainer.setVisibility(View.VISIBLE);
//            btn_next_gw.setVisibility(View.VISIBLE);
//        }
//        if (mMapView.getCallout().isShowing()) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mMapView.getCallout().animatedHide();
//                }
//            }, 300);
//
//        }
//        //隐藏marker
//        locationMarker.setVisibility(View.GONE);
//        if (uploadInfos.get(0).getUploadedFacilities() != null) {
//            showYjBottomSheetAndJust(uploadInfos.get(0).getUploadedFacilities());
//        }
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
//                    initGLayer();
                    if (mStatus == 1) {
                        initStreamGLayer();
                    } else if (mStatus == 0) {
                        initGLayer();
                    }
                    hideBottomSheet();
                    return true;
                }
            };
        }

        if (editModeCalloutSureButtonClickListener == null) {
            //查询设施模式下，点击callout中的确定按钮时的回调
            editModeCalloutSureButtonClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

    @Subscribe
    public void onRefreshMyUploadListEvent(RefreshMyUploadList refreshMyUploadList) {
//        if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
//            btn_edit_cancel.performClick();
//        }
        if (btn_cancel.getVisibility() == View.VISIBLE) {
            btn_cancel.performClick();
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
//        if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
//            btn_edit_cancel.performClick();
//        }
        if (btn_cancel.getVisibility() == View.VISIBLE) {
            btn_cancel.performClick();
        }
        Layer[] layers = mMapView.getLayers();
        for (Layer layer : layers) {
            if (layer instanceof ArcGISDynamicMapServiceLayer) {
                ((ArcGISDynamicMapServiceLayer) layer).refresh();
            }
        }
    }

    private void changeLayerUrlInitFailState() {
        if (LayerUrlConstant.ifLayerUrlInitSuccess()
                && loadLayersSuccess
                && ll_layer_url_init_fail != null) {
            ll_layer_url_init_fail.setVisibility(View.GONE);
        }
        if ((!LayerUrlConstant.ifLayerUrlInitSuccess()
                || !loadLayersSuccess)
                && ll_layer_url_init_fail != null) {
            ll_layer_url_init_fail.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 网络切换时，若设施URL为null，会重新初始化设施URL，然后发送OnInitLayerUrlFinishEvent事件
     *
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

    private long objectToLong(Object object) {
        if (object == null) {
            return -1L;
        }
        return Long.valueOf(object.toString());
    }

    /**
     * 初始化简要信息界面
     */
    private void initFacilityView() {
        mTitleTv = (TextView) map_bottom_sheet_gw.findViewById(R.id.title_gw);
        mDateTv = (TextView) map_bottom_sheet_gw.findViewById(R.id.date_gw);
        mSortTv = (TextView) map_bottom_sheet_gw.findViewById(R.id.sort_gw);
        mSubtypeTv = (TextView) map_bottom_sheet_gw.findViewById(R.id.subtype_gw);
        mField1Tv = (TextView) map_bottom_sheet_gw.findViewById(R.id.field1_gw);
        mField2Tv = (TextView) map_bottom_sheet_gw.findViewById(R.id.field2_gw);
        mField3Tv = (TextView) map_bottom_sheet_gw.findViewById(R.id.field3_gw);

        mBtn_container = map_bottom_sheet_gw.findViewById(R.id.btn_container_gw);
        mAddrTv = (TextView) map_bottom_sheet_gw.findViewById(R.id.addr_gw);
        mTv_gc_begin = (TextView) map_bottom_sheet_gw.findViewById(R.id.tv_gc_begin_gw);
        mTv_gc_end = (TextView) map_bottom_sheet_gw.findViewById(R.id.tv_gc_end_gw);
        mTv_ms_begin = (TextView) map_bottom_sheet_gw.findViewById(R.id.tv_ms_begin_gw);
        mTv_ms_end = (TextView) map_bottom_sheet_gw.findViewById(R.id.tv_ms_end_gw);
        mTv_gj = (TextView) map_bottom_sheet_gw.findViewById(R.id.tv_gj_gw);
        mTv_cz = (TextView) map_bottom_sheet_gw.findViewById(R.id.tv_cz_gw);
        mLl_gq = map_bottom_sheet_gw.findViewById(R.id.ll_gq_gw);
        mTv_tycd = (TextView) map_bottom_sheet_gw.findViewById(R.id.tv_tycd_gw);
        mTv_pd = (TextView) map_bottom_sheet_gw.findViewById(R.id.tv_pd_gw);
        mLine3 = map_bottom_sheet_gw.findViewById(R.id.line3_gw);
        mLine4 = map_bottom_sheet_gw.findViewById(R.id.line4_gw);
        mStream_line1_gw = map_bottom_sheet_gw.findViewById(R.id.stream_line1_gw);
        mTv_delete_gw = map_bottom_sheet_gw.findViewById(R.id.tv_delete_gw);
        mTv_sure_gw = (TextView)map_bottom_sheet_gw.findViewById(R.id.tv_sure_gw);
        mTv_error_correct_gw = (TextView) map_bottom_sheet_gw.findViewById(R.id.tv_error_correct_gw);
        mStream_line_gw = map_bottom_sheet_gw.findViewById(R.id.stream_line_gw);
        mTv_sure = (TextView) map_bottom_sheet.findViewById(R.id.tv_sure);
        mLine2_gw =  map_bottom_sheet_gw.findViewById(R.id.line2_gw);//分割线;
        mTv_sure = (TextView) map_bottom_sheet.findViewById(R.id.tv_sure);
//        mLine_gw =  map_bottom_sheet_gw.findViewById(R.id.line_gw);
    }
}
