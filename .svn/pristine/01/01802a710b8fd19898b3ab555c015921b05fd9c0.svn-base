package com.augurit.agmobile.gzpssb.fragment;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.OnInitLayerUrlFinishEvent;
import com.augurit.agmobile.gzps.common.constant.LayerUrlConstant;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.widget.CheckBoxItem;
import com.augurit.agmobile.gzps.common.widget.SpinnerTableItem;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.componentmaintenance.SelectComponentEvent;
import com.augurit.agmobile.gzps.componentmaintenance.service.ComponentService;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentFieldKeyConstant;
import com.augurit.agmobile.gzps.measure.view.MapMeasureView;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.uploadfacility.service.CorrectFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.service.DeleteFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.service.UploadFacilityService;
import com.augurit.agmobile.gzps.uploadfacility.service.UploadLayerService;
import com.augurit.agmobile.gzps.uploadfacility.util.UploadLayerFieldKeyConstant;
import com.augurit.agmobile.gzps.uploadfacility.view.UploadFacilitySuccessEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.RefreshMyModificationListEvent;
import com.augurit.agmobile.gzps.uploadfacility.view.uploadnewfacility.RefreshMyUploadList;
import com.augurit.agmobile.gzpssb.activity.SewerageActivity;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.bean.BuildInfoBySGuid;
import com.augurit.agmobile.gzpssb.bean.DoorNOBean;
import com.augurit.agmobile.gzpssb.bean.DrainageUnit;
import com.augurit.agmobile.gzpssb.bean.DrainageUnitListBean;
import com.augurit.agmobile.gzpssb.bean.DrainageUserBean;
import com.augurit.agmobile.gzpssb.bean.HangUpWellInfoBean;
import com.augurit.agmobile.gzpssb.bean.QueryIdBeanResult;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.bean.UploadBean;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.common.legend.PSHImageLegendView;
import com.augurit.agmobile.gzpssb.common.search.IOnSearchClickListener;
import com.augurit.agmobile.gzpssb.common.search.SearchFragment;
import com.augurit.agmobile.gzpssb.common.view.DrainageUserListBottomSheetView;
import com.augurit.agmobile.gzpssb.event.RefreshDataListEvent;
import com.augurit.agmobile.gzpssb.event.RefreshDoorData;
import com.augurit.agmobile.gzpssb.event.RefreshPsdyData;
import com.augurit.agmobile.gzpssb.fire.PSHUploadNewFireActivity;
import com.augurit.agmobile.gzpssb.jbjpsdy.JbjPsdySewerageUserListAdapter;
import com.augurit.agmobile.gzpssb.jbjpsdy.JbjPsdySewerageUserListNewView;
import com.augurit.agmobile.gzpssb.jbjpsdy.SewerageUserEntity;
import com.augurit.agmobile.gzpssb.jbjpsdy.model.CheckResult;
import com.augurit.agmobile.gzpssb.jbjpsdy.model.DoubtBean;
import com.augurit.agmobile.gzpssb.jbjpsdy.model.PsdyJbj;
import com.augurit.agmobile.gzpssb.jbjpsdy.model.PshJhj;
import com.augurit.agmobile.gzpssb.jbjpsdy.service.PsdyLayerFactory;
import com.augurit.agmobile.gzpssb.jbjpsdy.view.MapDrawQuestionView;
import com.augurit.agmobile.gzpssb.jbjpsdy.view.PipeStreamView;
import com.augurit.agmobile.gzpssb.jhj.view.CorrectOrConfirimWellActivity;
import com.augurit.agmobile.gzpssb.jhj.view.WellNewFacilityActivity;
import com.augurit.agmobile.gzpssb.jhj.view.reeditfacility.ReEditWellFacilityActivity;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.monitor.view.WellMonitorListActivity;
import com.augurit.agmobile.gzpssb.okhttp.Presenter.SewerageItemPresenter;
import com.augurit.agmobile.gzpssb.pshdoorno.add.view.PSHUploadNewDoorNoActivity;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.service.PSHMyUploadDoorNoService;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.service.SelectDoorNOTouchListener;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.service.SewerageLayerService;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.affairdetail.PSHAffairDetailContract;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.affairdetail.PSHAffairDetailPresenter;
import com.augurit.agmobile.gzpssb.secondpsh.SecondLevelPshListActivity;
import com.augurit.agmobile.gzpssb.seweragewell.presenter.PSHSeweragelayerPresenter;
import com.augurit.agmobile.gzpssb.uploadfacility.model.PipeBean;
import com.augurit.agmobile.gzpssb.uploadfacility.view.NumberItemTableItem;
import com.augurit.agmobile.gzpssb.uploadfacility.view.myuploadlist.SewerageMyUploadActivity;
import com.augurit.agmobile.mapengine.common.utils.GeometryUtil;
import com.augurit.agmobile.mapengine.common.widget.compassview.CompassView;
import com.augurit.agmobile.mapengine.common.widget.scaleview.MapScaleView;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.service.LayerServiceFactory;
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
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.login.service.LoginService;
import com.augurit.am.cmpt.widget.bottomsheet.AnchorSheetBehavior;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.cmpt.widget.spinner.AMSpinner;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.DeviceUtil;
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
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.Symbol;
import com.esri.core.tasks.geocode.Locator;
import com.jakewharton.rxbinding.view.RxView;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.augurit.agmobile.gzps.R.id.subtype;
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
public class QueryAddressMapFragment extends Fragment implements SewerageItemPresenter.IGetRoomInfo, PSHAffairDetailContract.View {
    private ProgressLinearLayout pb_loading;
    private View item1;
    private View item2;
    private static final String KEY_MAP_STATE = "com.esri.MapState";
    View mView;
    MapView mMapView;
    ProgressDialog pd;
    ViewGroup dis_map_bottom_sheet, dis_map_bottom_sheet_psh_unit;
    AnchorSheetBehavior mDisBehavior, mPshUnitDisBehavior;
    GraphicsLayer mGLayer = null;
    GraphicsLayer mGraphicSelectLayer = null;
    GraphicsLayer mSelectGLayer = null;
    private GraphicsLayer mStreamGLayer;
    private GraphicsLayer mArrowGLayer;
    Graphic graphic = null;
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
    private ViewGroup ll_topbar_container2; //顶部工具容器
    private TextView tv_query_tip;
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
    private int currIndex = 0, currIndexUnit;
    private boolean ifFirstAdd = true;
    private boolean ifFirstEdit = true;
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
    private View btn_add_fire;
    private View btn_cancel_fire;
    private int bottomMargin;
    private View btn_dis_prev, btn_dis_prev_psh_unit;
    private View btn_dis_next, btn_dis_next_psh_unit;
    /**
     * 数据上报图层是否显示
     */
    private boolean ifMyUploadLayerVisible = false;
    private ComponentService componentService;
    private SewerageLayerService mSewerageLayerService;
    private ViewGroup ll_next_and_prev_container, ll_next_and_prev_pshunit_container;
    private DoorNOBean mCurrentDoorNOBean;
    private PSHHouse mCurrentDoorNOBean2;
    private DrainageUnit mCurrentDrainageUnit;
    private List<DoorNOBean> mDoorNOBeans = new ArrayList<>();
    private List<PSHHouse> mDoorNOBeans2 = new ArrayList<>();
    private List<String> mHeaderStrs = new ArrayList<>();
    private List<DrainageUserBean> mDrainageUserBeans = new ArrayList<>();
    private List<DrainageUnit> mDrainageUnits = new ArrayList<>();
    private CompassView mCompassView;
    private SelectLocationTouchListener addModeSelectLocationTouchListener;
    private SelectLocationTouchListener addModeDoorSelectLocationTouchListener;
    private SewerageItemPresenter sewerageItemPresenter = new SewerageItemPresenter(null);
    private TextView tv_address;
    private SearchFragment searchFragment;
    private EditText et_search_keyword;
    private PSHMyUploadDoorNoService mUploadDoorNoService;
    private static final String SEARCH_PSH_NO = "SEARCH_PSH_NO";//搜索门牌
    private static final String SEARCH_PSH_UNIT = "SEARCH_PSH_UNIT";//搜索排水单元
    private String searchMode = SEARCH_PSH_NO;
    private PSHAffairDetailPresenter pshAffairDetailPresenter;

    /**
     * 排水户列表
     */
    private DrainageUserListBottomSheetView drainageUserListView;
    private BottomSheetBehavior<DrainageUserListBottomSheetView> drainageUserListBehavoir;
    private List<LayerInfo> mLayerInfosFromLocal;
    private int maxHeight = 0;
    private int headerHeight = 0;
    private int itemHeight = 0;

    private DetailAddress mLastSelectedAddress = null;
    private boolean isShowAddress = false;
    private SelectLocationTouchListener addModeSelectPshLocationTouchListener;
    private View.OnClickListener addPshCalloutSureButtonClickListener;
    private View ll_fire;

    private SelectLocationTouchListener hangUpWellLocationTouchListener;
    private View.OnClickListener hangUpWellCalloutSureButtonClickListener;
    private DrainageUserBean currDrainageUserBean;
    private CorrectFacilityService correctFacilityService;

    private int mStatus = 0;//0正常模式，1上下游，2修改排水管道，3多个上下游
    private List<PsdyJbj> mPsdyJbjs = new ArrayList<>();

    //点查后的设施结果
    private List<Component> mComponentQueryResult = new ArrayList<>();
    private View btn_prev;
    private View btn_next;
    private boolean isJHJWell = false;
    private boolean ifInDoubtMode = false;
    private boolean ifInGuaJie = false;
    private boolean ifInLine = false;
    private boolean ifInDY = false;
    private boolean whileTake = true;
    private boolean isLoading = false;

    /**
     * 是否处于井连线模式
     */
    private boolean ifInCheckMode = false;

    /**
     * 是否处于修改管模式
     */
    private boolean ifInPipeMode = false;
    private boolean isClick = false;
    private boolean isFirst = true;
    private SewerageLayerService layersService;

    /**
     * 是否处于排水户模式
     */
    private boolean ifInSwerageUserMode = false;
    /**
     * 是否处于编辑模式
     */
    private boolean ifInEditMode = false;
    private Component componentCenter;
    private PipeBean originPipe;

    private TextView tvResponsible;
    ViewGroup map_bottom_sheet;
    private TextView tv_check_hint;
    private View ll_check_hint;
    private TextView tv_check_person;
    private TextView tv_check_phone;
    private List<String> attribute = Arrays.asList("窨井(中心城区)", "窨井", "雨水口", "雨水口(中心城区)", "排水口", "排水口(中心城区)", "排水管道", "排水管道(中心城区)", "排水沟渠", "排水沟渠(中心城区)", "存疑区域");
    private List<String> mAttribute = Arrays.asList("污水", "雨水", "雨污合流");
    private String mFirstAttribute = "";
    private String mSecondAttribute = "";
    private View ll_gj;
    private PipeBean currentStream;
    private boolean isDrawStream = false;
    private List<PipeBean> mAllPipeBeans = new ArrayList<>();
    private List<PipeBean> mTempPipeBeans = new ArrayList<>();
    private PipeStreamView mPipeStreamView;
    private ViewGroup mReviewContainter;
    private boolean UpOrDownStream = false;
    private View btn_check;
    private View btn_check_cancel;
    private View btn_edit_zhiyi;
    private View btn_zhiyi_cancel;
    private View btn_add;
    private View btn_cancel;
    private View btn_edit;
    private View btn_edit_cancel;
    private View btn_edit_pipe;
    private View btn_pipe_cancel;
    private View btn_edit_dy;
    private View btn_dy_cancel;
    private View btn_check_situation;
    private View btn_situation_cancel;
    private View btn_add_jhj;
    private View btn_jhj_cancel;
    private boolean userCanEdit = true;
    AnchorSheetBehavior mBehavior;
    private Component currentComponent;
    private String status = "2";
    private String person = "";
    private DetailAddress componentDetailAddress = null;

    /**
     * 是否处于修改井模式
     */
    private boolean ifInWellMode = false;
    private GraphicsLayer mGeometryMapGLayer;
    private String mTitle = "选择关联窨井";

    private SelectLocationTouchListener editModeSelectLocationTouchListener;
    private View.OnClickListener editModeCalloutSureButtonClickListener;
    private MapDrawQuestionView mMapDrawQuestionView;
    /**
     * 是否是接驳井
     */
    private boolean ifInJbWell = false;
    private ImageView btnSwerageUser; // 排水户：点击排水单元，查看排水户信息
    private ImageView btnDrainageUser; // 排水户：直接点击排水户，挂接接户井
    /**
     * 是否处于排水户模式
     */
    private boolean ifInDrainageUserMode = false;
    private ViewGroup check_legend_view;
    private CorrectFacilityService mIdentificationService;
    private UploadFacilityService mUploadFacilityService;
    private Button btn_reedit;
    private NumberItemTableItem sp_gxcd;
    private double mLineLength = -1;
    private Button btn_cancel2;
    private SpinnerTableItem sp_ywlx;
    private SpinnerTableItem sp_lx;
    private SpinnerTableItem sp_gj;
    private String mGjCode;
    private CheckBoxItem cb_isFacilityOfRedLine;
    ViewGroup pipe_view;
    private ProgressDialog mDialog;
    private UploadLayerService mUploadLayerService;
    private FrameLayout llInfo;
    private PsdyLayerFactory mPsdyLayerFactory;
    private int mIndex = -1;
    private JbjPsdySewerageUserListNewView mSewerageUserListView;
    private BottomSheetBehavior<JbjPsdySewerageUserListNewView> mSwerageUserListBehavoir;
    private float mMapViewMinTranslationY = 0f;
    private float mMapViewMaxTranslationY = 0f;
    private Component currentDYComponent;

    /**
     * 是否处于删除排水户和接户井连线模式
     */
    private boolean ifInDeletePshAndJhjLineMode = false;
    private DeleteFacilityService deleteFacilityService;
    private boolean mIsAdd;
    private ViewGroup mJhjview;
    private ViewGroup ll_next_and_prev_container5;
    /**
     * 管井信息
     */
    private LinearLayout llResponsible;
    private ImageView ivBack, ivResponsible;

    private TextView tv_house_Property;
    private TextView tv_right_up_tip;
    private TextView tv_right_up;
    private AnchorSheetBehavior mJhjBehavoir;
    private List<PshJhj> pshJhjs = new ArrayList<>();

    public static QueryAddressMapFragment getInstance(Bundle data) {
        QueryAddressMapFragment addComponentFragment2 = new QueryAddressMapFragment();
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
        return inflater.inflate(R.layout.fragment_query_address_map, null);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        startgetPosition();
        mView.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_topbar_container2.getVisibility() == View.VISIBLE) {
                    hideBottomSheet();
                    initGLayer();
                    initSelectGLayer();
                    initGraphicSelectGLayer();
                    //隐藏callout
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().animatedHide();
                    }
                    //恢复点击事件
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                    //隐藏marker
                    locationMarker.setVisibility(View.GONE);
                    return;
                }
                ((Activity) mContext).finish();
            }
        });
        pb_loading = (ProgressLinearLayout) mView.findViewById(R.id.pb_loading);
        item1 = mView.findViewById(R.id.ll_item1);
        item2 = mView.findViewById(R.id.tv_floor);

        check_legend_view = (ViewGroup) view.findViewById(R.id.check_legend_view);

        mView.findViewById(R.id.ll_search_menpai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMode = SEARCH_PSH_NO;
                searchFragment.setHintText("搜索排水户名称、地址");
                searchFragment.show(getFragmentManager(), SEARCH_PSH_NO);

            }
        });
        mView.findViewById(R.id.ll_query_psh_unit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMode = SEARCH_PSH_UNIT;
                searchFragment.setHintText("搜索排水单元");
                searchFragment.show(getFragmentManager(), SEARCH_PSH_UNIT);
            }
        });

        searchFragment = SearchFragment.newInstance();
//        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
//            @Override
//            public void OnSearchClick(String keyword) {
////                et_search_keyword = (EditText) searchFragment.getView().findViewById(R.id.et_search_keyword);
//                //这里处理逻辑
//                if (!StringUtil.isEmpty(keyword)) {
//                    if(searchMode.equals(SEARCH_PSH_NO)){
//                        queryDoorNo(keyword);
//                    }else if(searchMode.equals(SEARCH_PSH_UNIT)){
//                        queryDoorPshUnit(keyword);
//                    }
//                }else{
//                    ToastUtil.shortToast(mContext,"请输入搜索文字");
//                }
//            }
//        });
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                if (!StringUtil.isEmpty(keyword)) {
                    if (searchMode.equals(SEARCH_PSH_NO)) {
                        queryDrainageUsersForKeyword(keyword);
                    } else if (searchMode.equals(SEARCH_PSH_UNIT)) {
                        queryDoorPshUnit(keyword);
                    }
                } else {
                    ToastUtil.shortToast(mContext, "请输入搜索文字");
                }
            }
        });
        ((TextView) mView.findViewById(R.id.tv_title)).setText("排水户管理");
        btn_add_pai = mView.findViewById(R.id.btn_add_door);
        btn_cancel_pai = mView.findViewById(R.id.btn_edit_door_cancel);
        ll_fire = mView.findViewById(R.id.ll_fire);
        btn_add_fire = mView.findViewById(R.id.btn_add_fire);
        btn_cancel_fire = mView.findViewById(R.id.btn_edit_fire_cancel);
        mView.findViewById(R.id.ll_search).setVisibility(View.VISIBLE);
        ImageView iv_open_search = (ImageView) mView.findViewById(R.id.iv_open_search);
        iv_open_search.setImageResource(R.mipmap.ic_list);
        ((TextView) mView.findViewById(R.id.tv_search)).setText("上报列表");
        mView.findViewById(R.id.tv_search).setVisibility(View.VISIBLE);

        mView.findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SewerageMyUploadActivity.class);
                resetAll();
                if (btn_cancel_well.getVisibility() == View.VISIBLE) {
                    btn_cancel_well.performClick();
                }
                if (btn_cancel_hu.getVisibility() == View.VISIBLE) {
                    btn_cancel_hu.performClick();
                }
                startActivity(intent);
            }
        });

        ll_topbar_container = (ViewGroup) view.findViewById(R.id.ll_topbar_container);
        ll_topbar_container2 = (ViewGroup) view.findViewById(R.id.ll_topbar_container2);
        ll_tool_container = (ViewGroup) view.findViewById(R.id.ll_tool_container);
        tv_query_tip = (TextView) view.findViewById(R.id.tv_query_tip);


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
                        if (locationButton != null) {
                            locationButton.followLocation();
                        }
                        if (layerPresenter != null) {
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER_BZ, true);
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2, true);
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DOOR_NO_LAYER, false);
                            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_JIEBOJING, true);

                            LayerInfo dyLayerInfo = getDYLayerInfo();
                            if (dyLayerInfo != null) {
                                layerPresenter.onClickOpacityButton(dyLayerInfo.getLayerId(), dyLayerInfo, 0.5f);
                            }

                            if (layerPresenter instanceof PSHSeweragelayerPresenter) {
                                ((PSHSeweragelayerPresenter) layerPresenter).setDrainageUnitLayerInfoAlpha();
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
        mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
        /**
         * 默认使用窨井
         */
        locationMarker.changeIcon(R.mipmap.ic_add_facility_2);
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
        dis_map_bottom_sheet_psh_unit = (ViewGroup) view.findViewById(R.id.dis_map_bottom_sheet_psh_unit);
        mDisBehavior = AnchorSheetBehavior.from(dis_map_bottom_sheet);
        mPshUnitDisBehavior = AnchorSheetBehavior.from(dis_map_bottom_sheet_psh_unit);

        mDisBehavior.setState(com.augurit.am.cmpt.widget.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
        btn_dis_prev = view.findViewById(R.id.dis_prev);
        btn_dis_next = view.findViewById(R.id.dis_next);
        btn_dis_prev_psh_unit = view.findViewById(R.id.dis_prev_psh_unit);
        btn_dis_next_psh_unit = view.findViewById(R.id.dis_next_psh_unit);
        //排水单元上一个
        btn_dis_prev_psh_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currIndexUnit--;
                if (mDrainageUnits.size() > 1) {
                    btn_dis_next_psh_unit.setVisibility(View.VISIBLE);
                }

                if (currIndexUnit < 0) {
                    btn_dis_prev_psh_unit.setVisibility(View.GONE);
                    return;
                }
                mCurrentDrainageUnit = mDrainageUnits.get(currIndexUnit);
                //todo
//                showBottomSheet();
                if (currIndexUnit == 0) {
                    btn_dis_prev_psh_unit.setVisibility(View.GONE);
                }

            }
        });
        //排水单元下一个
        btn_dis_next_psh_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currIndexUnit++;
                if (currIndexUnit < 0) currIndexUnit = 0;
                if (currIndexUnit > 0) {
                    btn_dis_prev_psh_unit.setVisibility(View.VISIBLE);
                }

                if (currIndexUnit > mDrainageUnits.size()) {
                    btn_dis_next_psh_unit.setVisibility(View.GONE);
                    return;
                }
                mCurrentDrainageUnit = mDrainageUnits.get(currIndexUnit);
                //todo
//                showBottomSheet();
                if (currIndexUnit == (mDoorNOBeans.size() - 1)) {
                    btn_dis_next_psh_unit.setVisibility(View.GONE);
                }

            }
        });

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
                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(currIndex).getS_guid());
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
                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(currIndex).getS_guid());
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
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });

        btn_add_hu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetAll();
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
                if (btn_cancel_fire.getVisibility() == View.VISIBLE) {
                    btn_cancel_fire.performClick();
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
                setAddNewPshFacilityListener();
                //下面的代码不能在调用 setAddNewFacilityListener 方法前调用，
                // 因为 addModeSelectLocationTouchListener 和 addModeCalloutSureButtonClickListener 未初始化
                if (locate && mSelectDoorNOTouchListener != null) {
                    mSelectDoorNOTouchListener.locate();
                }
            }
        });

        btn_add_well = view.findViewById(R.id.btn_edit);
        btn_cancel_well = view.findViewById(R.id.btn_edit_cancel);
        btn_add_well.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAll();
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
                if (btn_cancel_fire.getVisibility() == View.VISIBLE) {
                    btn_cancel_fire.performClick();
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
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
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
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
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
                if (btn_cancel_fire.getVisibility() == View.VISIBLE) {
                    btn_cancel_fire.performClick();
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

        //消防栓
        btn_add_fire.setOnClickListener(new View.OnClickListener() {
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
                if (btn_cancel_fire.getVisibility() == View.VISIBLE) {
                    btn_cancel_fire.performClick();
                }
                if (ifFirstAdd) {
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "移动地图选择新增地上式消防栓的位置");
                    ifFirstAdd = false;
                }

                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_add_facility_2);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                btn_add_fire.setVisibility(View.GONE);
                btn_cancel_fire.setVisibility(View.VISIBLE);
                hideBottomSheet();
                initGLayer();
                setAddNewFireFacilityListener();
                //下面的代码不能在调用 setAddNewFacilityListener 方法前调用，
                // 因为 addModeSelectLocationTouchListener 和 addModeCalloutSureButtonClickListener 未初始化
                if (locate) {
                    addModeSelectLocationTouchListener.locate();
                }

            }
        });
        btn_cancel_fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_add_fire.setVisibility(View.VISIBLE);
                btn_cancel_fire.setVisibility(View.GONE);
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                mMapView.getCallout().hide();
                hideBottomSheet();
                initGLayer();
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });
        map_bottom_sheet = (ViewGroup) view.findViewById(R.id.map_bottom_sheet);
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

        drainageUserListView = view.findViewById(R.id.drainageUserListView);
        drainageUserListView.setOnHeaderAndItemClickListener(new DrainageUserListBottomSheetView.OnHeaderAndItemClickListener() {
            @Override
            public void onHeaderClick(int position) {
                // 标题点击事件
                String header = mHeaderStrs.get(position);
                filterItemsByHeader(header);
            }

            @Override
            public void onItemClick(Object object, int position) {
                // Item点击事件
                if (object instanceof DrainageUserBean) {
                    initGLayer();
                    drawGeometry(new Point(((DrainageUserBean) object).getX(), ((DrainageUserBean) object).getY()), mGLayer, true, true);
                    queryDetailById(((DrainageUserBean) object).getId());
                }
                resetAll();
            }

            @Override
            public void onItemInspection(Object object, int position) {
                DrainageUserBean drainageUserBean = (DrainageUserBean) object;
                PSHHouse pshHouse = DrainageUserBean.cover2PshHouse(drainageUserBean);
                Intent intent = new Intent(mContext, SecondLevelPshListActivity.class);
                SewerageItemBean.UnitListBean unitListBean = new SewerageItemBean.UnitListBean();
                unitListBean.setId(Integer.valueOf(pshHouse.getId()));
                unitListBean.setAddr(pshHouse.getAddr());
                unitListBean.setMph(pshHouse.getMph());
                unitListBean.setName(pshHouse.getName());
                unitListBean.setPsdyName(drainageUserBean.getPsdyName());
                unitListBean.setPsdyId(drainageUserBean.getPsdyId());
                intent.putExtra("unitListBean", unitListBean);
                mContext.startActivity(intent);
            }

            @Override
            public void onItemHangUpWell(Object object, int position) {
                // 挂接接户井
                if (object instanceof DrainageUserBean) {
                    currDrainageUserBean = (DrainageUserBean) object;
                    hangUpWell(object, position);
                }
            }

            @Override
            public void onItemDeleteLine(Object object, int position) {
                // 删除接驳井和接户井连线
                // 先搜索所有的连线
                // 接着再选择并删除井连线
                queryAllHangUpContents(((DrainageUserBean) object).getId());
                addTopBarView("删除接驳井连线");
                mStatus = 6;
                setMode(mStatus);
                ToastUtil.shortToast(mContext, "请选择连线");
                locationMarker.setVisibility(View.GONE);
                tv_query_tip.setText("请选择要删除的接驳井连线");
//                                        initStreamGLayer();
                hideBottomSheet();
                mGLayer.clearSelection();
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });
        drainageUserListBehavoir = BottomSheetBehavior.from(drainageUserListView);
//        view.getRootView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                ViewGroup.LayoutParams layoutParams = drainageUserListView.getLayoutParams();
//                int parentHeight = ((ViewGroup) drainageUserListView.getParent()).getMeasuredHeight();
//                maxHeight = parentHeight * 3 / 5;
//                layoutParams.height = parentHeight * 3 / 5;
//                drainageUserListView.setLayoutParams(layoutParams);
//                drainageUserListView.getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });

        pshAffairDetailPresenter = new PSHAffairDetailPresenter(this, mContext);

        btn_prev = view.findViewById(R.id.prev);
        btn_next = view.findViewById(R.id.next);
        RxView.clicks(btn_prev)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        isJHJWell = false;
                        ifInDY = false;
                        if (whileTake && !isLoading) {
                            currIndex--;
                            if (currIndex < 0) {
                                btn_prev.setVisibility(View.GONE);
                                return;
                            }
                            String layerName = mComponentQueryResult.get(currIndex).getLayerName();
                            if (ifInCheckMode || ifInPipeMode || mStatus == 1 || mStatus == 2 || (isClick && "排水管道".equals(layerName))) {
                                showBottomSheetForCheck(mComponentQueryResult.get(currIndex));
                            } else {
                                showBottomSheet(mComponentQueryResult.get(currIndex));
                            }
                            if (currIndex == 0) {
                                btn_prev.setVisibility(View.GONE);
                            }
                            if (mComponentQueryResult.size() > 1) {
                                btn_next.setVisibility(View.VISIBLE);
                            }
                        } else if (isLoading && whileTake) {
                            whileTake = false;
                        }
                    }
                });
        RxView.clicks(btn_next)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        isJHJWell = false;
                        ifInDY = false;
                        if (whileTake && !isLoading) {
                            currIndex++;
                            if (currIndex > mComponentQueryResult.size()) {
                                btn_next.setVisibility(View.GONE);
                                return;
                            }
                            String layerName = mComponentQueryResult.get(currIndex).getLayerName();
                            if (ifInCheckMode || ifInPipeMode || mStatus == 1 || mStatus == 2 || (isClick && "排水管道".equals(layerName))) {
                                showBottomSheetForCheck(mComponentQueryResult.get(currIndex));
                            } else {
                                showBottomSheet(mComponentQueryResult.get(currIndex));
                            }
                            if (currIndex == (mComponentQueryResult.size() - 1)) {
                                btn_next.setVisibility(View.GONE);
                            }
                            if (currIndex > 0) {
                                btn_prev.setVisibility(View.VISIBLE);
                            }
                        } else if (isLoading && whileTake) {
                            whileTake = false;
                        }
                    }
                });
        mReviewContainter = (ViewGroup) view.findViewById(R.id.ll_toview_containter);
        btn_check = view.findViewById(R.id.btn_check);
        btn_check_cancel = view.findViewById(R.id.btn_check_cancel);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifInGuaJie) {
                    exit();
                }
                mMapMeasureView.stopMeasure();
                mStatus = 0;
                isClick = false;
                ifInEditMode = false;
                ifInCheckMode = true;
                ifInPipeMode = false;
                ifInDoubtMode = false;
                ifInGuaJie = false;
                ifInDY = false;
                boolean locate = false;
                if (ll_topbar_container2 != null) {
                    ll_topbar_container2.setVisibility(View.GONE);
                }
                if (btn_zhiyi_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_zhiyi_cancel.performClick();
                }
                if (btn_cancel.getVisibility() == View.VISIBLE) {
                    btn_cancel.performClick();
                }
                if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
                    btn_edit_cancel.performClick();
                }
                if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
                    btn_pipe_cancel.performClick();
                }
                if (btn_dy_cancel.getVisibility() == View.VISIBLE) {
                    btn_dy_cancel.performClick();
                }
                if (btn_situation_cancel.getVisibility() == View.VISIBLE) {
                    btn_situation_cancel.performClick();
                }
                btn_check.setVisibility(View.GONE);
                btn_check_cancel.setVisibility(View.VISIBLE);
                ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "移动地图选择窨井");
                addTopBarView(mTitle);
                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_check_related);
                    locationMarker.setVisibility(View.VISIBLE);
                }
//                showAttributeForStream(false);
                hideBottomSheet();
                initGLayer();
                initArrowGLayer();
                initStreamGLayer();
                initGraphicSelectGLayer();
                setSearchFacilityListener();
                //下面的代码不能在调用 setSearchFacilityListener 方法前调用，
                // 因为editModeSelectLocationTouchListener 和 editModeCalloutSureButtonClickListener 未初始化
                if (locate && editModeSelectLocationTouchListener != null) {
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
        btn_edit_zhiyi = view.findViewById(R.id.btn_edit_zhiyi);
        btn_zhiyi_cancel = view.findViewById(R.id.btn_zhiyi_cancel);
        btn_zhiyi_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifInDoubtMode = false;
                ifInGuaJie = false;
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                mMapDrawQuestionView.stopDraw();
                mMapView.getCallout().hide();
                btn_edit_zhiyi.setVisibility(View.VISIBLE);
                btn_zhiyi_cancel.setVisibility(View.GONE);
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });

        btn_edit_zhiyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifInGuaJie) {
                    exit();
                }
                ifInCheckMode = false;
                ifInPipeMode = false;
                ifInEditMode = false;
                ifInDoubtMode = true;
                ifInGuaJie = false;
                ifInDY = false;
                mMapMeasureView.stopMeasure();
                if (ll_topbar_container2 != null) {
                    ll_topbar_container2.setVisibility(View.GONE);
                }
                boolean locate = false;
                if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_edit_cancel.performClick();
                }
                if (btn_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_cancel.performClick();
                }
                if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_check_cancel.performClick();
                }
                if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_pipe_cancel.performClick();
                }
                if (btn_dy_cancel.getVisibility() == View.VISIBLE) {
                    btn_dy_cancel.performClick();
                }
                if (btn_situation_cancel.getVisibility() == View.VISIBLE) {
                    btn_situation_cancel.performClick();
                }
                ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "请点击地图设置存疑范围");

                if (locationButton != null && locationButton.ifLocating()) {
                    locationButton.setStateNormal();
                }
                btn_edit_zhiyi.setVisibility(View.GONE);
                btn_zhiyi_cancel.setVisibility(View.VISIBLE);
//                showAttributeForStream(false);
                hideBottomSheet();
                initGLayer();
                initArrowGLayer();
                initStreamGLayer();
                initGraphicSelectGLayer();
                mMapDrawQuestionView.startDraw();
            }
        });
        btn_add = view.findViewById(R.id.btn_add_useless);
        btn_cancel = view.findViewById(R.id.btn_cancel_useless);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                ifInJbWell = false;
                mMapView.getCallout().hide();
                btn_add.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.GONE);
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifInGuaJie) {
                    exit();
                }
                ifInJbWell = true;
                ifInCheckMode = false;
                ifInPipeMode = false;
                ifInEditMode = false;
                ifInDoubtMode = false;
                ifInGuaJie = false;
                ifInDY = false;
                if (ifInSwerageUserMode) {
                    btnSwerageUser.performClick();
                }
                if (ifInDrainageUserMode) {
                    btnDrainageUser.performClick();
                }
                mMapMeasureView.stopMeasure();
                boolean locate = false;
                if (btn_zhiyi_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_zhiyi_cancel.performClick();
                }
                if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_edit_cancel.performClick();
                }
                if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_check_cancel.performClick();
                }
                if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_pipe_cancel.performClick();
                }
                if (btn_dy_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_dy_cancel.performClick();
                }
                if (btn_situation_cancel.getVisibility() == View.VISIBLE) {
                    btn_situation_cancel.performClick();
                }
                if (btn_jhj_cancel.getVisibility() == View.VISIBLE) {
                    btn_jhj_cancel.performClick();
                }
                if (ifFirstAdd) {
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "移动地图选择新增设施的位置");
                    ifFirstAdd = false;
                }
                if (ll_topbar_container2 != null) {
                    ll_topbar_container2.setVisibility(View.GONE);
                }

                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_add_facility_2);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                btn_add.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.VISIBLE);
//                showAttributeForStream(false);
                hideBottomSheet();
                initGLayer();
                initArrowGLayer();
                initStreamGLayer();
                initGraphicSelectGLayer();
                setAddNewFacilityListener();
                //下面的代码不能在调用 setAddNewFacilityListener 方法前调用，
                // 因为 addModeSelectLocationTouchListener 和 addModeCalloutSureButtonClickListener 未初始化
                if (locate) {
                    addModeSelectLocationTouchListener.locate();
                }
            }
        });
        btn_edit = view.findViewById(R.id.btn_edit_useless);
        btn_edit_cancel = view.findViewById(R.id.btn_edit_cancel_useless);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifInGuaJie) {
                    exit();
                }
                mMapMeasureView.stopMeasure();
                boolean locate = false;
                if (ll_topbar_container2 != null) {
                    ll_topbar_container2.setVisibility(View.GONE);
                }
                if (btn_cancel.getVisibility() == View.VISIBLE) {
                    btn_cancel.performClick();
                }
                if (btn_zhiyi_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_zhiyi_cancel.performClick();
                }
                if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                    btn_check_cancel.performClick();
                }
                if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
                    btn_pipe_cancel.performClick();
                }
                if (btn_dy_cancel.getVisibility() == View.VISIBLE) {
                    btn_dy_cancel.performClick();
                }
                if (btn_situation_cancel.getVisibility() == View.VISIBLE) {
                    btn_situation_cancel.performClick();
                }
                ifInEditMode = true;
                ifInCheckMode = false;
                ifInPipeMode = false;
                ifInDoubtMode = false;
                ifInGuaJie = false;
                btn_edit.setVisibility(View.GONE);
                btn_edit_cancel.setVisibility(View.VISIBLE);

                ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "在地图上点击管井的位置");
                ifFirstEdit = false;

//                if (locationMarker != null) {
//                    locationMarker.changeIcon(R.mipmap.ic_check_data_2);
//                    locationMarker.setVisibility(View.VISIBLE);
//                }
//                showAttributeForStream(false);
                hideBottomSheet();
                initGLayer();
                initArrowGLayer();
                initStreamGLayer();
                initGraphicSelectGLayer();
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                //下面的代码不能在调用 setSearchFacilityListener 方法前调用，
                // 因为editModeSelectLocationTouchListener 和 editModeCalloutSureButtonClickListener 未初始化
                if (locate && editModeSelectLocationTouchListener != null) {
                    editModeSelectLocationTouchListener.locate();
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().animatedHide();
                    }
                    editModeCalloutSureButtonClickListener.onClick(null);
                }

            }
        });
        btn_edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifInEditMode = false;
                btn_edit.setVisibility(View.VISIBLE);
                btn_edit_cancel.setVisibility(View.GONE);
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                mMapView.getCallout().hide();
                hideBottomSheet();
                initGLayer();
                //1
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });
        btn_edit_pipe = view.findViewById(R.id.btn_edit_pipe);
        btn_pipe_cancel = view.findViewById(R.id.btn_pipe_cancel);
        btn_edit_pipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifInGuaJie) {
                    exit();
                }
                mMapMeasureView.stopMeasure();
                mStatus = 0;
                isClick = false;
                ifInEditMode = false;
                ifInCheckMode = false;
                ifInPipeMode = true;
                ifInDoubtMode = false;
                ifInGuaJie = false;
                ifInDY = false;
                boolean locate = false;
                if (ll_topbar_container2 != null) {
                    ll_topbar_container2.setVisibility(View.GONE);
                }
                if (btn_zhiyi_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_zhiyi_cancel.performClick();
                }
                if (btn_cancel.getVisibility() == View.VISIBLE) {
                    btn_cancel.performClick();
                }
                if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
                    btn_edit_cancel.performClick();
                }

                if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                    btn_check_cancel.performClick();
                }
                if (btn_dy_cancel.getVisibility() == View.VISIBLE) {
                    btn_dy_cancel.performClick();
                }
                if (btn_situation_cancel.getVisibility() == View.VISIBLE) {
                    btn_situation_cancel.performClick();
                }
                btn_edit_pipe.setVisibility(View.GONE);
                btn_pipe_cancel.setVisibility(View.VISIBLE);
                ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "在地图上点击排水管线的位置");
                addTopBarView(mTitle);
//                if (locationMarker != null) {
//                    locationMarker.changeIcon(R.mipmap.ic_check_data_2);
//                    locationMarker.setVisibility(View.VISIBLE);
//                }
//                showAttributeForStream(false);
                hideBottomSheet();
                initGLayer();
                initArrowGLayer();
                initStreamGLayer();
                initGraphicSelectGLayer();
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                //下面的代码不能在调用 setSearchFacilityListener 方法前调用，
                // 因为editModeSelectLocationTouchListener 和 editModeCalloutSureButtonClickListener 未初始化
                if (locate && editModeSelectLocationTouchListener != null) {
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
        btn_edit_dy = view.findViewById(R.id.btn_edit_dy);
        btn_dy_cancel = view.findViewById(R.id.btn_dy_cancel);
        btn_dy_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifInDY = false;
                btn_edit_dy.setVisibility(View.VISIBLE);
                btn_dy_cancel.setVisibility(View.GONE);
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                mMapView.getCallout().hide();
                initGraphicSelectGLayer();
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

        btn_edit_dy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifInGuaJie) {
                    exit();
                }
                ifInCheckMode = false;
                ifInPipeMode = false;
                ifInEditMode = false;
                ifInDoubtMode = false;
                ifInGuaJie = false;
                if (ifInSwerageUserMode) {
                    btnSwerageUser.performClick();
                }
                if (ifInDrainageUserMode) {
                    btnDrainageUser.performClick();
                }
                ifInDY = true;
                mMapMeasureView.stopMeasure();
                boolean locate = false;
                if (ll_topbar_container2 != null) {
                    ll_topbar_container2.setVisibility(View.GONE);
                }
                if (btn_dy_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_dy_cancel.performClick();
                }
                if (btn_cancel.getVisibility() == View.VISIBLE) {
                    btn_cancel.performClick();
                }
                if (btn_jhj_cancel.getVisibility() == View.VISIBLE) {
                    btn_jhj_cancel.performClick();
                }
                if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
                    btn_edit_cancel.performClick();
                }
                if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
                    btn_pipe_cancel.performClick();
                }
                if (btn_zhiyi_cancel.getVisibility() == View.VISIBLE) {
                    btn_zhiyi_cancel.performClick();
                }
                if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                    btn_check_cancel.performClick();
                }
                if (btn_situation_cancel.getVisibility() == View.VISIBLE) {
                    btn_situation_cancel.performClick();
                }
                btn_edit_dy.setVisibility(View.GONE);
                btn_dy_cancel.setVisibility(View.VISIBLE);
                ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "在地图上点击排水单元");
                addTopBarView(mTitle);
//                showAttributeForStream(false);
                hideBottomSheet();
                initGLayer();
                initArrowGLayer();
                initStreamGLayer();
                initGraphicSelectGLayer();
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                //下面的代码不能在调用 setSearchFacilityListener 方法前调用，
                // 因为editModeSelectLocationTouchListener 和 editModeCalloutSureButtonClickListener 未初始化
                if (locate && editModeSelectLocationTouchListener != null) {
                    editModeSelectLocationTouchListener.locate();
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().animatedHide();
                    }
                    editModeCalloutSureButtonClickListener.onClick(null);
                }
            }
        });
        btn_check_situation = view.findViewById(R.id.btn_check_situation);
        btn_situation_cancel = view.findViewById(R.id.btn_situation_cancel);
        btn_check_situation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_legend_view.setVisibility(View.VISIBLE);
                switchLayerVisibility(true);
                mMapMeasureView.stopMeasure();
                mStatus = 0;
                isClick = false;
                ifInEditMode = false;
                ifInCheckMode = false;
                ifInPipeMode = false;
                ifInDoubtMode = false;
                ifInGuaJie = false;
                ifInDY = false;
                boolean locate = false;
                if (ll_topbar_container2 != null) {
                    ll_topbar_container2.setVisibility(View.GONE);
                }
                if (btn_zhiyi_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_zhiyi_cancel.performClick();
                }
                if (btn_check.getVisibility() == View.VISIBLE) {
                    btn_check_cancel.performClick();
                }
                if (btn_cancel.getVisibility() == View.VISIBLE) {
                    btn_cancel.performClick();
                }
                if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
                    btn_edit_cancel.performClick();
                }
                if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
                    btn_pipe_cancel.performClick();
                }
                if (btn_dy_cancel.getVisibility() == View.VISIBLE) {
                    btn_dy_cancel.performClick();
                }
                if (ifInSwerageUserMode) {
                    btnSwerageUser.performClick();
                }
                btn_check_situation.setVisibility(View.GONE);
                btn_situation_cancel.setVisibility(View.VISIBLE);
//                showAttributeForStream(false);
                hideBottomSheet();
                initGLayer();
                initArrowGLayer();
                initStreamGLayer();
                initGraphicSelectGLayer();
                //下面的代码不能在调用 setSearchFacilityListener 方法前调用，
                // 因为editModeSelectLocationTouchListener 和 editModeCalloutSureButtonClickListener 未初始化
                if (locate && editModeSelectLocationTouchListener != null) {
                    editModeSelectLocationTouchListener.locate();
                    if (mMapView.getCallout().isShowing()) {
                        mMapView.getCallout().animatedHide();
                    }
                    editModeCalloutSureButtonClickListener.onClick(null);
                }

            }
        });
        btn_situation_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifInCheckMode = false;
                switchLayerVisibility(false);
                layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_JCQK, false);
                check_legend_view.setVisibility(View.GONE);
                btn_check_situation.setVisibility(View.VISIBLE);
                btn_situation_cancel.setVisibility(View.GONE);
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                mMapView.getCallout().hide();
                hideBottomSheet();
            }
        });

        btnSwerageUser = (ImageView) view.findViewById(R.id.btn_swerage_user);
        btnSwerageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 排水户按钮点击
                if (v.isSelected()) {
                    ifInSwerageUserMode = false;
                    dismissSewerageUserBottomSheet();
                    removePsdyMph();
                    mMapView.getCallout().hide();
                    initGraphicSelectGLayer();
                    layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_USER, true);
                    hideBottomSheet();
                    mStatus = 0;
                    setMode(0);
                    if (mStatus == 0) {
                        initGLayer();
                        initArrowGLayer();
                        mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                    }
                } else {
                    layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_USER, false);
                    if (ifInGuaJie) {
                        exit();
                    }
                    ifInCheckMode = false;
                    ifInPipeMode = false;
                    ifInEditMode = false;
                    ifInDoubtMode = false;
                    ifInGuaJie = false;
                    ifInDY = false;
                    ifInSwerageUserMode = true;
                    mMapMeasureView.stopMeasure();
                    boolean locate = false;
                    if (ll_topbar_container2 != null) {
                        ll_topbar_container2.setVisibility(View.GONE);
                    }
                    if (btn_dy_cancel.getVisibility() == View.VISIBLE) {
                        locate = true;
                        btn_dy_cancel.performClick();
                    }
                    if (btn_cancel.getVisibility() == View.VISIBLE) {
                        btn_cancel.performClick();
                    }
                    if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
                        btn_edit_cancel.performClick();
                    }
                    if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
                        btn_pipe_cancel.performClick();
                    }
                    if (btn_jhj_cancel.getVisibility() == View.VISIBLE) {
                        btn_jhj_cancel.performClick();
                    }
                    if (btn_zhiyi_cancel.getVisibility() == View.VISIBLE) {
                        btn_zhiyi_cancel.performClick();
                    }
                    if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                        btn_check_cancel.performClick();
                    }
                    if (btn_situation_cancel.getVisibility() == View.VISIBLE) {
                        btn_situation_cancel.performClick();
                    }
                    if (ifInDrainageUserMode) {
                        btnDrainageUser.performClick();
                    }
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "在地图上点击排水单元");
//                    showAttributeForStream(false);
                    hideBottomSheet();
                    initGLayer();
                    initArrowGLayer();
                    initStreamGLayer();
                    initGraphicSelectGLayer();
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                    //下面的代码不能在调用 setSearchFacilityListener 方法前调用，
                    // 因为editModeSelectLocationTouchListener 和 editModeCalloutSureButtonClickListener 未初始化
                    if (locate && editModeSelectLocationTouchListener != null) {
                        editModeSelectLocationTouchListener.locate();
                        if (mMapView.getCallout().isShowing()) {
                            mMapView.getCallout().animatedHide();
                        }
                        editModeCalloutSureButtonClickListener.onClick(null);
                    }
                }
                v.setSelected(!v.isSelected());
            }
        });

        btnDrainageUser = (ImageView) view.findViewById(R.id.btn_drainage_user);
        btnDrainageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 排水户按钮点击
                if (v.isSelected()) {
                    // 取消的逻辑
                    ifInDrainageUserMode = false;
                    dismissDrainageUserBottomSheet();
                    removePsdyMph();
                    mMapView.getCallout().hide();
                    initGraphicSelectGLayer();
                    hideBottomSheet();
                    mStatus = 0;
                    setMode(0);
                    if (mStatus == 0) {
                        initGLayer();
                        initArrowGLayer();
                        mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                    }
                    if (ll_topbar_container2.getVisibility() == View.VISIBLE && "请移动地图选择接户井".equals(tv_query_tip.getText())) {
                        //隐藏callout
                        if (mMapView.getCallout().isShowing()) {
                            mMapView.getCallout().animatedHide();
                        }
                        //隐藏marker
                        locationMarker.setVisibility(View.GONE);
                        ll_topbar_container2.setVisibility(View.GONE);
                    }
                } else {
                    // 选择的逻辑
                    if (ifInGuaJie) {
                        exit();
                    }
                    ifInCheckMode = false;
                    ifInPipeMode = false;
                    ifInEditMode = false;
                    ifInDoubtMode = false;
                    ifInGuaJie = false;
                    ifInDY = false;
                    ifInDrainageUserMode = true;
                    mMapMeasureView.stopMeasure();
                    boolean locate = false;
                    if (ll_topbar_container2 != null) {
                        ll_topbar_container2.setVisibility(View.GONE);
                    }
                    if (btn_dy_cancel.getVisibility() == View.VISIBLE) {
                        locate = true;
                        btn_dy_cancel.performClick();
                    }
                    if (btn_cancel.getVisibility() == View.VISIBLE) {
                        btn_cancel.performClick();
                    }
                    if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
                        btn_edit_cancel.performClick();
                    }
                    if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
                        btn_pipe_cancel.performClick();
                    }
                    if (btn_jhj_cancel.getVisibility() == View.VISIBLE) {
                        btn_jhj_cancel.performClick();
                    }
                    if (btn_zhiyi_cancel.getVisibility() == View.VISIBLE) {
                        btn_zhiyi_cancel.performClick();
                    }
                    if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                        btn_check_cancel.performClick();
                    }
                    if (btn_situation_cancel.getVisibility() == View.VISIBLE) {
                        btn_situation_cancel.performClick();
                    }
                    if (ifInSwerageUserMode) {
                        btnSwerageUser.performClick();
                    }
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "在地图上点击排水户");
//                    showAttributeForStream(false);
                    hideBottomSheet();
                    initGLayer();
                    initArrowGLayer();
                    initStreamGLayer();
                    initGraphicSelectGLayer();
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                    //下面的代码不能在调用 setSearchFacilityListener 方法前调用，
                    // 因为editModeSelectLocationTouchListener 和 editModeCalloutSureButtonClickListener 未初始化
                    if (locate && editModeSelectLocationTouchListener != null) {
                        editModeSelectLocationTouchListener.locate();
                        if (mMapView.getCallout().isShowing()) {
                            mMapView.getCallout().animatedHide();
                        }
                        editModeCalloutSureButtonClickListener.onClick(null);
                    }
                }
                v.setSelected(!v.isSelected());
            }
        });
        //接户井
        btn_add_jhj = view.findViewById(R.id.btn_add_jhj);
        btn_jhj_cancel = view.findViewById(R.id.btn_jhj_cancel);
        btn_jhj_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationMarker != null) {
                    locationMarker.setVisibility(View.GONE);
                }
                mMapView.getCallout().hide();
                btn_add_jhj.setVisibility(View.VISIBLE);
                btn_jhj_cancel.setVisibility(View.GONE);
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            }
        });

        btn_add_jhj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifInGuaJie) {
                    exit();
                }
                ifInJbWell = false;
                ifInCheckMode = false;
                ifInPipeMode = false;
                ifInEditMode = false;
                ifInDoubtMode = false;
                ifInGuaJie = false;
                ifInDY = false;
                if (ifInSwerageUserMode) {
                    btnSwerageUser.performClick();
                }
                if (ifInDrainageUserMode) {
                    btnDrainageUser.performClick();
                }
                mMapMeasureView.stopMeasure();
                boolean locate = false;
                if (btn_zhiyi_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_zhiyi_cancel.performClick();
                }
                if (btn_edit_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_edit_cancel.performClick();
                }
                if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_check_cancel.performClick();
                }
                if (btn_pipe_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_pipe_cancel.performClick();
                }
                if (btn_cancel.getVisibility() == View.VISIBLE) {
                    btn_cancel.performClick();
                }
                if (btn_dy_cancel.getVisibility() == View.VISIBLE) {
                    locate = true;
                    btn_dy_cancel.performClick();
                }
                if (btn_situation_cancel.getVisibility() == View.VISIBLE) {
                    btn_situation_cancel.performClick();
                }
                if (ifFirstAdd) {
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "移动地图选择新增设施的位置");
                    ifFirstAdd = false;
                }
                if (ll_topbar_container2 != null) {
                    ll_topbar_container2.setVisibility(View.GONE);
                }

                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_add_facility_2);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                btn_add_jhj.setVisibility(View.GONE);
                btn_jhj_cancel.setVisibility(View.VISIBLE);
//                showAttributeForStream(false);
                hideBottomSheet();
                initGLayer();
                initArrowGLayer();
                initStreamGLayer();
                initGraphicSelectGLayer();
                setAddNewFacilityListener();
                //下面的代码不能在调用 setAddNewFacilityListener 方法前调用，
                // 因为 addModeSelectLocationTouchListener 和 addModeCalloutSureButtonClickListener 未初始化
                if (locate) {
                    addModeSelectLocationTouchListener.locate();
                }
            }
        });
        mBehavior = AnchorSheetBehavior.from(map_bottom_sheet);
        mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);

        mUploadFacilityService = new UploadFacilityService(mContext);
        correctFacilityService = new CorrectFacilityService(getContext());
        pipe_view = (ViewGroup) view.findViewById(R.id.pipe_view);

        initPipeInfo();

        mJhjview = view.findViewById(R.id.jhjview);
        mJhjBehavoir = AnchorSheetBehavior.from(mJhjview);

        mSewerageUserListView = view.findViewById(R.id.swerageUserListView);
        mSewerageUserListView.setOnItemClickListener(new JbjPsdySewerageUserListAdapter.OnSewerageUserListItemClickListener() {
            @Override
            public void onItemClick(View v, RecyclerView.ViewHolder holder, SewerageUserEntity entity) {
                // 在这里处理Item点击事件
                pshAffairDetailPresenter.getPSHAffairDetail(Integer.parseInt(entity.getId()));
                // 在这里定位到地图上
                double latitude = entity.getLatitude();
                double longitude = entity.getLongitude();
                Point geometry = new Point(longitude, latitude);
                mMapView.centerAt(geometry, true);
                initGraphicSelectGLayer();
                drawGeometry(geometry, mGraphicSelectLayer, true, true);
            }

            @Override
            public void onLocation(View v, RecyclerView.ViewHolder holder, SewerageUserEntity entity) {
                // 在这里定位到地图上
                double latitude = entity.getLatitude();
                double longitude = entity.getLongitude();
                Point geometry = new Point(longitude, latitude);
                mMapView.centerAt(geometry, true);
                initGraphicSelectGLayer();
                drawGeometry(geometry, mGraphicSelectLayer, true, true);
            }
        });
        mSewerageUserListView.post(new Runnable() {
            @Override
            public void run() {
//                ViewGroup.LayoutParams layoutParams = mSewerageUserListView.getLayoutParams();
                int parentHeight = ((ViewGroup) mSewerageUserListView.getParent()).getMeasuredHeight();
//                layoutParams.height = parentHeight * 3 / 5;
//                mSewerageUserListView.setLayoutParams(layoutParams);
                float titleBarHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
//                mMapViewMinTranslationY = parentHeight * (1f / 5 - 1f / 2);
                mMapViewMinTranslationY = parentHeight * (1f / 5 - 1f / 2) + titleBarHeight / 2;
                mMapViewMaxTranslationY = mMapView.getTranslationY();
                mSwerageUserListBehavoir = BottomSheetBehavior.from(mSewerageUserListView);
                mSwerageUserListBehavoir.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        if (slideOffset <= 0) {
                            mMapView.setTranslationY(mMapViewMaxTranslationY);
                        } else if (slideOffset >= 1) {
                            mMapView.setTranslationY(mMapViewMinTranslationY);
                        } else {
                            float targetY = mMapViewMaxTranslationY - (mMapViewMaxTranslationY - mMapViewMinTranslationY) * slideOffset;
                            mMapView.setTranslationY(targetY);
                        }
                    }
                });
            }
        });

        initJhjBottomSheetView();

        //权限控制
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        String role = user.getRoleCode();
        if (role.contains("PSH_DSSXFX")) {//包含地上消防
            ll_fire.setVisibility(View.VISIBLE);
        } else {
            ll_fire.setVisibility(View.GONE);
        }
    }

    private void queryDoorPshUnit(final String keyword) {
        if (pd == null) {
            pd = new ProgressDialog(getContext());
        }
        pd.setMessage("正在搜索排水单元...");
        if (!pd.isShowing()) {
            pd.show();
        }
        currIndexUnit = 0;
        mDrainageUnits.clear();
        ll_next_and_prev_pshunit_container.setVisibility(View.INVISIBLE);
        mCurrentDrainageUnit = null;

        initComponentService();
        initmUploadDoorNoService();
        if (true) {
            mUploadDoorNoService.queryPshUnitObjectIdByKeyword(keyword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<QueryIdBeanResult>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            queryPshUnitByObjectIds(keyword, new ArrayList<String>());
                        }

                        @Override
                        public void onNext(QueryIdBeanResult result) {

                            if (result.getCode() == 200) {
                                queryPshUnitByObjectIds(keyword, result.getRows());
                            } else {
                                queryPshUnitByObjectIds(keyword, new ArrayList<String>());
                            }
                        }
                    });
        }

    }

    private void queryPshUnitByObjectIds(String keyword, List<String> rows) {
        componentService.queryDrainageUnitByKeyWord(keyword, rows, new Callback2<List<DrainageUnit>>() {
            @Override
            public void onSuccess(List<DrainageUnit> components) {
                if (pd != null) {
                    pd.dismiss();
                }
                if (ListUtil.isEmpty(components)) {
                    ToastUtil.shortToast(getContext(), "未查询到排水单元");
                    return;
                }
                mDrainageUnits.addAll(components);
                if (ListUtil.isEmpty(mDrainageUnits)) {
                    initGLayer();
                    if (pd != null) {
                        pd.dismiss();
                    }
                    ToastUtil.shortToast(getContext(), "未查询到排水单元");
                    return;
                }
//                    tv_address.setText(StringUtil.getNotNullString(mDrainageUnits.get(0).getStree(), ""));
                pb_loading.showLoading();
                item1.setVisibility(View.GONE);
                item2.setVisibility(View.GONE);
//                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(0).getS_guid());
                showOnBottomSheet(mDrainageUnits.get(0));
            }

            @Override
            public void onFail(Exception error) {
                if (pd != null) {
                    pd.dismiss();
                }
                ToastUtil.shortToast(getContext(), "未查询到该排水单元");
            }
        });

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
        componentService.queryDoorNoComponentsForObjectId(keyword, objectIds, new Callback2<List<DoorNOBean>>() {
            @Override
            public void onSuccess(List<DoorNOBean> components) {
                if (pd != null) {
                    pd.dismiss();
                }
                if (ListUtil.isEmpty(components)) {
                    ToastUtil.shortToast(getContext(), "未查询到该门牌号");
                    return;
                }
//                    mDoorNOBeans = new ArrayList<DoorNOBean>();
                mDoorNOBeans.addAll(components);
                if (ListUtil.isEmpty(mDoorNOBeans)) {
                    initGLayer();
                    if (pd != null) {
                        pd.dismiss();
                    }
                    ToastUtil.shortToast(getContext(), "未查询到该门牌号");
                    return;
                }
                tv_address.setText(StringUtil.getNotNullString(mDoorNOBeans.get(0).getStree(), ""));
                pb_loading.showLoading();
                item1.setVisibility(View.GONE);
                item2.setVisibility(View.GONE);
                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(0).getS_guid());
            }

            @Override
            public void onFail(Exception error) {
                if (pd != null) {
                    pd.dismiss();
                }
                ToastUtil.shortToast(getContext(), "未查询到该门牌数据");
            }
        });
    }

    private void initmUploadDoorNoService() {
        if (mUploadDoorNoService == null) {
            mUploadDoorNoService = new PSHMyUploadDoorNoService(mContext);
        }
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
                    startActivityForResult(intent, 124);
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

    //显示排水单元
    private void showOnBottomSheet(DrainageUnit drainageUnit) {
        if (mDrainageUnits.size() > 1) {
            ll_next_and_prev_pshunit_container.setVisibility(View.VISIBLE);
        }
        //隐藏marker
//        locationMarker.setVisibility(View.GONE);
        initGLayer();
        Geometry geometry = null;
        if (drainageUnit != null) {
            geometry = new Point(drainageUnit.getX(), drainageUnit.getY());
            dis_map_bottom_sheet_psh_unit.setVisibility(View.VISIBLE);
            dis_map_bottom_sheet.setVisibility(View.GONE);
            mPshUnitDisBehavior.setState(com.augurit.am.cmpt.widget.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
            //initGLayer();
            mCurrentDrainageUnit = drainageUnit;
            initGLayer();
            Point centerPt = new Point(drainageUnit.getX(), drainageUnit.getY());
            if (centerPt != null) {
                drawGeometry(centerPt, mGLayer, true, true);
            }
            //todo
//            mView.findViewById(R.id.)


            getView().findViewById(R.id.door_detail_btn).
                    setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
            getView().findViewById(R.id.wrong_door_btn).
                    setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
        }
        if (geometry != null) {
            drawGeometry(geometry, mGLayer, true, true);
        }
    }

    private void showOnBottomSheet(List<DoorNOBean> beans, BuildInfoBySGuid.DataBean buildInfoBySGuids) {
        if (beans.size() > 1) {
            ll_next_and_prev_container.setVisibility(View.VISIBLE);
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
    private void showBottomSheet(final DoorNOBean doorNOBean, BuildInfoBySGuid.DataBean buildInfoBySGuid) {
        dis_map_bottom_sheet_psh_unit.setVisibility(View.GONE);
        dis_map_bottom_sheet.setVisibility(View.VISIBLE);
        if (mDoorNOBeans.size() > 1) {
            ll_next_and_prev_container.setVisibility(View.VISIBLE);
        }
        mDisBehavior.setState(com.augurit.am.cmpt.widget.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
        //initGLayer();
        mCurrentDoorNOBean = doorNOBean;
        initGLayer();
        Point centerPt = new Point(doorNOBean.getX(), doorNOBean.getY());
        if (centerPt != null) {
            drawGeometry(centerPt, mGLayer, true, true);
        }
        initBottomSheetText(doorNOBean, buildInfoBySGuid);
        getView().findViewById(R.id.door_detail_btn).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_add_hu.setVisibility(View.VISIBLE);
                        btn_cancel_hu.setVisibility(View.GONE);
                        if (locationMarker != null) {
                            locationMarker.setVisibility(View.GONE);
                        }
                        Intent intent = new Intent(mContext, SewerageActivity.class);
                        intent.putExtra("doorBean", mCurrentDoorNOBean);
                        MyApplication.doorBean = mCurrentDoorNOBean;
                        hideCallout();
                        startActivity(intent);
                    }
                });


        getView().findViewById(R.id.wrong_door_btn).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DialogUtil.MessageBox(getActivity(), "提示", "是否确定标识此门牌不存在？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (pd == null) {
                                    pd = new ProgressDialog(getContext());
                                }
                                pd.setMessage("正在提交...");
                                if (!pd.isShowing()) {
                                    pd.show();
                                }

                                mSewerageLayerService.upLoadWrongDoor(mCurrentDoorNOBean.getS_guid()).subscribe(new Subscriber<UploadBean>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        if (pd != null) {
                                            pd.dismiss();
                                        }
                                        ToastUtil.shortToast(getActivity(), "提交失败");
                                    }

                                    @Override
                                    public void onNext(UploadBean result3) {
                                        if (pd != null) {
                                            pd.dismiss();
                                        }

                                        if (result3.getResult_code() == 200) {
                                            ToastUtil.shortToast(getActivity(), "提交成功");
                                            hideBottomSheet();
                                            btn_add_hu.setVisibility(View.VISIBLE);
                                            btn_cancel_hu.setVisibility(View.GONE);
                                            if (locationMarker != null) {
                                                locationMarker.setVisibility(View.GONE);
                                            }
                                            hideCallout();
                                        } else {
                                            ToastUtil.shortToast(getActivity(), "提交失败");
                                        }
                                    }
                                });

                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }
                });
    }

    /**
     * bottomSheetLayout的显示样式
     *
     * @param doorNOBean
     * @param buildInfoBySGuid
     */
    private void initBottomSheetText(final DoorNOBean doorNOBean, BuildInfoBySGuid.DataBean buildInfoBySGuid) {
        tv_address.setText(StringUtil.getNotNullString(doorNOBean.getStree(), ""));
        TextView tv_house_Property = (TextView) mView.findViewById(R.id.tv_house_Property);
        TextView tv_floor = (TextView) mView.findViewById(R.id.tv_floor);

        StringBuilder propertySB = new StringBuilder();
        StringBuilder floorSB = new StringBuilder();
        String houseProperty = buildInfoBySGuid.getHouseProperty();
        String structure = buildInfoBySGuid.getStructure();
        String houseType = buildInfoBySGuid.getHouseType();
        String houseUse = buildInfoBySGuid.getHouseUse();

        boolean empty1 = TextUtils.isEmpty(houseProperty) || TextUtils.isEmpty(houseProperty.trim());
        boolean empty2 = TextUtils.isEmpty(structure) || TextUtils.isEmpty(structure.trim());
        boolean empty3 = TextUtils.isEmpty(houseType) || TextUtils.isEmpty(houseType.trim());
        boolean empty4 = TextUtils.isEmpty(houseUse) || TextUtils.isEmpty(houseUse.trim());

        propertySB.append(empty1 ? "" : houseProperty).append("|")
                .append(empty2 ? "" : structure).append("|")
                .append(empty3 ? "" : houseType).append("|")
                .append(empty4 ? "" : houseUse).append("|");

        String str = propertySB.toString();
        str = str.replaceAll("\\|\\|\\|\\|", "\\|").replaceAll("\\|\\|\\|", "\\|").replaceAll("\\|\\|", "\\|");
        //去掉结尾|
        str = str.lastIndexOf("|") == str.length() - 1 ? str.substring(0, str.length() - 1) : str;
        //去掉开头|
        str = str.indexOf("|") == 0 ? str.substring(1) : str;
        tv_house_Property.setText(str.replaceAll("\\|", "\\ | "));

        String floor = buildInfoBySGuid.getFloor();
        String sets = buildInfoBySGuid.getSets();

        boolean empty5 = TextUtils.isEmpty(floor) || TextUtils.isEmpty(floor.trim());
        boolean empty6 = TextUtils.isEmpty(sets) || TextUtils.isEmpty(sets.trim());

        floorSB.append(empty5 ? "" : "总层数:" + floor).append("|")
                .append(empty6 ? "" : "总套数:" + sets).append("|");

        String str1 = floorSB.toString();
        str1 = str1.replaceAll("\\|\\|\\|", "\\|").replaceAll("\\|\\|", "\\|");
        str1 = str1.lastIndexOf("|") == str1.length() - 1 ? str1.substring(0, str1.length() - 1) : str1;
        tv_floor.setText(str1.replaceAll("\\|", "\\ | "));
    }

    /**
     * bottomSheetLayout的显示样式
     *
     * @param doorNOBean
     * @param buildInfoBySGuid
     */
    private void initBottomSheetText(final PSHHouse doorNOBean, BuildInfoBySGuid.DataBean buildInfoBySGuid) {
        tv_address.setText("排水户名称：" + StringUtil.getNotNullString(doorNOBean.getName(), ""));
        tv_right_up_tip.setVisibility(View.VISIBLE);
        tv_right_up.setVisibility(View.VISIBLE);
        tv_right_up.setText(StringUtil.getNotNullString(doorNOBean.getName(), ""));
        tv_house_Property.setText("行业类型：" + StringUtil.getNotNullString(doorNOBean.getDischargerType3(), ""));
        if ("2".equals(doorNOBean.getState())) {
            tv_right_up.setText("已审核");
            tv_right_up.setTextColor(Color.parseColor("#3EA500"));
        } else if ("1".equals(doorNOBean.getState())) {
            tv_right_up.setText("未审核");
            tv_right_up.setTextColor(Color.parseColor("#FFFF0000"));
        } else if ("0".equals(doorNOBean.getState())) {
            tv_right_up.setText("已注销");
            tv_right_up.setTextColor(Color.parseColor("#b1afab"));
        } else if ("3".equals(doorNOBean.getState())) {
            tv_right_up.setText("存在疑问");
            tv_right_up.setTextColor(Color.parseColor("#FFFFC248"));
        } else if ("4".equals(doorNOBean.getState())) {
            tv_right_up.setText("暂存");
            tv_right_up.setTextColor(Color.parseColor("#FFFFC248"));
        } else if ("5".equals(doorNOBean.getState())) {
            tv_right_up.setText("编辑");
            tv_right_up.setTextColor(Color.parseColor("#FFFFC248"));
        }
    }

    private void initBottomSheetView() {
        ll_next_and_prev_container = (ViewGroup) dis_map_bottom_sheet.findViewById(R.id.ll_next_and_prev_container);
        ll_next_and_prev_pshunit_container = (ViewGroup) dis_map_bottom_sheet_psh_unit.findViewById(R.id.ll_next_and_prev_container);
        tv_address = (TextView) dis_map_bottom_sheet.findViewById(R.id.tv_address);
        /**
         * 是否有人上报过提醒文本
         */
        tv_check_hint = (TextView) map_bottom_sheet.findViewById(R.id.tv_check_hint);
        ll_check_hint = map_bottom_sheet.findViewById(R.id.ll_check_hint);
        tv_check_phone = (TextView) map_bottom_sheet.findViewById(R.id.tv_check_phone);
        tv_check_person = (TextView) map_bottom_sheet.findViewById(R.id.tv_check_person);
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
        layersService = new SewerageLayerService(mContext.getApplicationContext());
        layerPresenter = new PSHSeweragelayerPresenter(layerView, layersService);
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
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
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

        if (ll_topbar_container2.getVisibility() == View.VISIBLE) {
            hideBottomSheet();
            initGLayer();
            initGraphicSelectGLayer();
            initSelectGLayer();
            //隐藏callout
            if (mMapView.getCallout().isShowing()) {
                mMapView.getCallout().animatedHide();
            }
            //恢复点击事件
            mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
            //隐藏marker
            locationMarker.setVisibility(View.GONE);
            return;
        }

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
        pd.setMessage("正在查询门牌...");
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
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.iconLongToast(getActivity(), R.mipmap.ic_alert_yellow, "请选择排水户！");
                        }
                    });
                    return;
                }
                mDoorNOBeans.clear();
                mDoorNOBeans.addAll(doorNOBeans);
                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(0).getS_guid());
            }

            @Override
            public void onFail(Exception error) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
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
     * 点击地图后查询接户井设施
     *
     * @param x
     * @param y
     */
    private void queryHangUpWell(final double x, final double y) {
        if (pd == null) {
            pd = new ProgressDialog(getContext());
        }
        pd.setMessage("正在查询接户井...");
        if (!pd.isShowing()) {
            pd.show();
        }

        ll_next_and_prev_container.setVisibility(View.INVISIBLE);
        currIndex = 0;
        final Point point = new Point(x, y);
        mCurrentDoorNOBean = null;
        mSewerageLayerService.setQueryByWhere("1=1");
        mSewerageLayerService.queryHangUpWellInfo(point, mMapView.getSpatialReference(), mMapView.getResolution(), new Callback2<List<HangUpWellInfoBean>>() {
            @Override
            public void onSuccess(List<HangUpWellInfoBean> hangUpWellInfoBeans) {
                if (ListUtil.isEmpty(hangUpWellInfoBeans)) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            initGLayer();
                            ToastUtil.iconLongToast(getActivity(), R.mipmap.ic_alert_yellow, "请选择接户井！");
                        }
                    });
                    pd.dismiss();
                    return;
                }
                pd.dismiss();
                // 开始调用接户井挂接
                final HangUpWellInfoBean hangUpWellInfoBean = hangUpWellInfoBeans.get(0);
                mMapView.centerAt(hangUpWellInfoBean.getY(), hangUpWellInfoBean.getX(), true);
                hangUpWellLocationTouchListener.showCalloutMessage("确定选择当前接户井", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (correctFacilityService == null) {
                            correctFacilityService = new CorrectFacilityService(getContext());
                        }
                        final PsdyJbj psdyJbj = new PshJhj();
                        psdyJbj.setUsid(hangUpWellInfoBean.getUsid());
                        psdyJbj.setJhjObjectId(hangUpWellInfoBean.getObjectId());
                        psdyJbj.setPshId(currDrainageUserBean.getId());
                        psdyJbj.setGjwX(hangUpWellInfoBean.getX());
                        psdyJbj.setGjwY(hangUpWellInfoBean.getY());
                        psdyJbj.setPshX(currDrainageUserBean.getX());
                        psdyJbj.setPshY(currDrainageUserBean.getY());
                        psdyJbj.setPshGjlx("1");
                        pd.setMessage("正在挂接接户井，请稍后...");
                        pd.show();
                        correctFacilityService.addPshJhj(psdyJbj)
                                .observeOn(Schedulers.io())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Action1<ResponseBody>() {

                                    @Override
                                    public void call(ResponseBody responseBody) {
                                        if (responseBody.getCode() == 200) {
                                            // 成功
                                            toast("挂接成功");
                                            // TODO 绘制一下横线
                                            drawPshJhjLine(psdyJbj);
                                        } else {
                                            // 失败
                                            toast(responseBody.getMessage());
                                        }
                                        pd.dismiss();
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                hideBottomSheet();
//                                                initGLayer();
//                                            }
//                                        });
                                    }
                                }, new Action1<Throwable>() {

                                    @Override
                                    public void call(Throwable throwable) {
                                        throwable.printStackTrace();
                                        // 失败
                                        toast("挂接失败");
                                        pd.dismiss();
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                hideBottomSheet();
//                                                initGLayer();
//                                            }
//                                        });
                                    }
                                });
                    }
                });
            }

            @Override
            public void onFail(Exception error) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        initGLayer();
                        ToastUtil.iconLongToast(getContext(), R.mipmap.ic_alert_yellow, "请选择接户井！");
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
    private void queryDrainageUsers(final double x, final double y) {
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
        mSewerageLayerService.queryDrainageUserInfo(point, mMapView.getSpatialReference(), mMapView.getResolution(), new Callback2<List<DrainageUserBean>>() {
            @Override
            public void onSuccess(List<DrainageUserBean> drainageUserBeans) {
                pd.dismiss();
                /*if (ListUtil.isEmpty(drainageUserBeans) || (drainageUserBeans.size() == 1 && drainageUserBeans.get(0).getAddress() == null)) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.iconLongToast(getActivity(), R.mipmap.ic_alert_yellow, "请选择门牌！");
                        }
                    });
                    return;
                }*/
                mHeaderStrs.clear();
                mDrainageUserBeans.clear();
                if (drainageUserBeans == null || drainageUserBeans.size() == 0) {
                    dismissDrainageUserBottomSheet();
                    return;
                }
                mDrainageUserBeans.addAll(drainageUserBeans);
//                if (drainageUserBeans.size() == 1) {
//                    // TODO 直接跳转到详情
//                    // 不对，应该要先根据id查询详情，然后再跳转
//                    queryDetailById(drainageUserBeans.get(0).getId());
//                } else {
                // TODO 显示选择列表
                drainageUserListView.setDatas(mDrainageUserBeans);
                final HashMap<String, Integer> map = new HashMap<>(mDrainageUserBeans.size());
//                    map.add(new Pair<>("全部", mDrainageUserBeans.size()));
                for (DrainageUserBean drainageUserBean : drainageUserBeans) {
                    final String type = drainageUserBean.getType();
                    if (type.contains("其他") && map.containsKey("其他")) {
                        map.put("其他", map.get("其他") + 1);
                    } else if (type.contains("其他")) {
                        map.put("其他", 1);
                    } else if (map.containsKey(type)) {
                        map.put(type, map.get(type) + 1);
                    } else {
//                            mHeaderStrs.add(type);
                        map.put(type, 1);
                    }
                }
                final List<Pair<String, String>> headers = new ArrayList<>(map.size() + 1);
                headers.add(new Pair<>("全部", String.valueOf(mDrainageUserBeans.size())));
                mHeaderStrs.add("全部");
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    headers.add(new Pair<>(entry.getKey(), String.valueOf(entry.getValue())));
                    mHeaderStrs.add(entry.getKey());
                }
                showDrainageUserBottomSheet();
                drainageUserListView.setHeaders(headers);
                drainageUserListView.setDatas(mDrainageUserBeans);
//                }
//                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(0).getS_guid());
            }

            @Override
            public void onFail(Exception error) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.iconLongToast(getContext(), R.mipmap.ic_alert_yellow, "查询错误！");
                    }
                });
                pd.dismiss();
            }
        });
    }

    /**
     * 点击地图后查询排水单元
     *
     * @param x
     * @param y
     */
    private void queryDrainageUnit(final double x, final double y) {
        final Point point = new Point(x, y);
        mSewerageLayerService.setQueryByWhere("1=1");
        mSewerageLayerService.queryDrainageUnitInfo(point, mMapView.getSpatialReference(), mMapView.getResolution(), new Callback2<List<DrainageUnit>>() {
            @Override
            public void onSuccess(final List<DrainageUnit> drainageUserBeans) {
                mView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new DrainageUnitListBean(drainageUserBeans));
                    }
                }, 300);
            }

            @Override
            public void onFail(Exception error) {
                mView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new DrainageUnitListBean(Collections.<DrainageUnit>emptyList()));
                    }
                }, 300);
            }
        });
    }

    @NonNull
    private void initComponentService() {
        if (componentService == null) {
            componentService = new ComponentService(mContext.getApplicationContext());
        }
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


    /**
     * 绘制箭头，请确保调用前已经调用了{@link #initGLayer()}方法
     *
     * @param geometryCenter   箭头摆放的位置
     * @param ifDrawRightArrow 是否绘制的是右箭头，true绘制右箭头，false绘制左箭头
     */
    private void drawArrow(Point geometryCenter, double angle, int color, boolean ifDrawRightArrow) {

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
        mArrowGLayer.addGraphic(graphic);
    }

    private void drawPipeline(Component component, List<PipeBean> pipeBeans, int color) {
        initArrowGLayer();
        if (ListUtil.isEmpty(pipeBeans) || component.getGraphic().getGeometry() == null) {
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

    private void initGLayer() {
        if (mGLayer == null) {
            mGLayer = new GraphicsLayer();
            mMapView.addLayer(mGLayer);
        } else {
            mGLayer.removeAll();
        }
    }

    private void initGraphicSelectGLayer() {
        if (mGraphicSelectLayer == null) {
            mGraphicSelectLayer = new GraphicsLayer();
            mMapView.addLayer(mGraphicSelectLayer);
        } else {
            mGraphicSelectLayer.removeAll();
        }
    }

    private void initSelectGLayer() {
        if (mSelectGLayer == null) {
            mSelectGLayer = new GraphicsLayer();
            mMapView.addLayer(mSelectGLayer);
        } else {
            mSelectGLayer.removeAll();
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
        map_bottom_sheet.setVisibility(View.GONE);
        dis_map_bottom_sheet.setVisibility(View.GONE);
        mBehavior.setState(STATE_EXPANDED);
        mJhjview.setVisibility(View.GONE);
        mJhjBehavoir.setState(STATE_EXPANDED);
        locationButton.post(new Runnable() {
            @Override
            public void run() {
                final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) locationButton
                        .getLayoutParams();
                lp.bottomMargin = bottomMargin;
                locationButton.setLayoutParams(lp);
            }
        });

        if (drainageUserListView != null && drainageUserListView.getVisibility() == View.VISIBLE) {
            dismissDrainageUserBottomSheet();
            return;
        }

        if (ll_topbar_container2 != null) {
            ll_topbar_container2.setVisibility(View.GONE);
        }

        mMapView.getCallout().hide();
    }

    private void hideMarkerAndResetMapTouchEvent() {
        //恢复点击事件
        mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
        //隐藏marker
        locationMarker.setVisibility(View.GONE);
    }

    private void resetAll() {
        ifInGuaJie = false;
        ifInLine = false;
        ifInDY = false;
        ifInDeletePshAndJhjLineMode = false;
        setMode(0);
        hideCallout();
        initGLayer();
        initArrowGLayer();
        initSelectGLayer();
        initStreamGLayer();
        initGraphicSelectGLayer();
        hideBottomSheet();
        hideMarkerAndResetMapTouchEvent();
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

        if (EventBus.getDefault().isRegistered(this)) {
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
                    Intent intent = new Intent(mContext, WellNewFacilityActivity.class);
//                    Intent intent = new Intent(mContext, PSHUploadNewFacilityActivity.class);
                    intent.putExtra("detailAddress", locationInfo.getDetailAddress());
                    intent.putExtra("x", locationInfo.getPoint().getX());
                    intent.putExtra("y", locationInfo.getPoint().getY());
                    intent.putExtra("type", "接户井");
                    startActivity(intent);
                    hideCallout();
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

    private void setAddNewFireFacilityListener() {
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
                    Intent intent = new Intent(mContext, PSHUploadNewFireActivity.class);
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
                        ToastUtil.shortToast(mContext, "请移动到有门牌的地方！");
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
                        queryDistribute(point.getX(), point.getY());
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
        mCurrentDoorNOBean.setISTATUE("3");
        if (!ListUtil.isEmpty(mDoorNOBeans) && mDoorNOBeans.size() > currIndex) {
            mDoorNOBeans.get(currIndex).setISTATUE("3");
        }
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
            if (mCurrentDoorNOBean != null) {
                mCurrentDoorNOBean.setISTATUE("2");
            }
            if (!ListUtil.isEmpty(mDoorNOBeans) && mDoorNOBeans.size() > currIndex) {
                mDoorNOBeans.get(currIndex).setISTATUE("2");
            }

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
        resetAll();
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

    @Override
    public void onGetPSHAffairDetail(PSHAffairDetail pshAffairDetail) {
        if (pshAffairDetail == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), SewerageTableActivity.class);
        intent.putExtra("pshAffair", pshAffairDetail);
        intent.putExtra("fromMyUpload", true);
//            intent.putExtra("isTempStorage", !TextUtils.isEmpty(checkState) && "4".equals(checkState));
//        intent.putExtra("isTempStorage", "4".equals(pshAffairDetail.getData().getState()));
        intent.putExtra("isCancel", "0".equals(pshAffairDetail.getData().getState()) || "4".equals(pshAffairDetail.getData().getState()));
//        intent.putExtra("isCancel", false);
        intent.putExtra("isList", false);
        startActivity(intent);
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
            if (mStatus == 6) {
                handleTapJbj(e);
            } else {
                handleTap(e);
            }
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
            ifInLine = false;
            ifInGuaJie = false;
            ifInDY = false;
            if (locationMarker != null) {
                locationMarker.setVisibility(View.GONE);
            }
            if (mMapView.getCallout().isShowing()) {
                mMapView.getCallout().hide();
            }
            removePsdyMph();
            if (pipe_view != null && pipe_view.getVisibility() == View.VISIBLE) {
                return;
            }
            int visibility = map_bottom_sheet.getVisibility();
            int disvisibility = dis_map_bottom_sheet.getVisibility();
            initGLayer();
            initGraphicSelectGLayer();
            hideBottomSheet();
            if (visibility == View.VISIBLE || disvisibility == View.VISIBLE) {
                return;
            }

            if (mSewerageUserListView.getVisibility() == View.VISIBLE) {
                dismissSewerageUserBottomSheet();
                layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_USER, true);
                return;
            }

            if (mJhjview.getVisibility() == View.VISIBLE) {
                dismissJhjBottomSheet();
                return;
            }
            if (drainageUserListView != null && drainageUserListView.getVisibility() == View.VISIBLE) {
                dismissDrainageUserBottomSheet();
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
//                queryDistribute(point.getX(), point.getY());
//                queryDrainageUsers(point.getX(), point.getY());
                query(point.getX(), point.getY());
            }
        }
    }

    private void handleTapJbj(MotionEvent e) {
        double scale = mMapView.getScale();
        List<Graphic> list = new ArrayList<>();
        if (scale < LayerUrlConstant.MIN_QUERY_SCALE) {
            Point point = mMapView.toMapPoint(e.getX(), e.getY());
            int[] graphicIDs1 = mGraphicSelectLayer.getGraphicIDs(e.getX(), e.getY(), 15);
            if (graphicIDs1.length > 0 && mGraphicSelectLayer.isVisible()) {
                for (int id : graphicIDs1) {
                    list.add(mGraphicSelectLayer.getGraphic(id));
                }
                final Graphic graphic = mGraphicSelectLayer.getGraphic(graphicIDs1[0]);
                initSelectGLayer();
                drawSelectGeometry(graphic.getGeometry(), mSelectGLayer, null, true, false);
                final String id = StringUtil.getNotNullString(graphic.getAttributeValue("Id"), "");
                DialogUtil.MessageBox(getContext(), "提示", "是否要删除该连线？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Observable<ResponseBody> responseBodyObservable = correctFacilityService.deletePshGj(id);
                                responseBodyObservable.subscribeOn(Schedulers.io())
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
                                                initSelectGLayer();
                                                CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
                                                        BaseInfoManager.getUserName(mContext) + "原因是：" + e.getLocalizedMessage()));
                                                ToastUtil.shortToast(mContext, "删除失败");
                                            }

                                            @Override
                                            public void onNext(ResponseBody responseBody) {
                                                if (pd != null && pd.isShowing()) {
                                                    pd.dismiss();
                                                }
                                                initSelectGLayer();
                                                if (responseBody.getCode() == 200) {
                                                    mGraphicSelectLayer.removeGraphic(graphic.getUid());
                                                    for (PsdyJbj psdyJbj : mPsdyJbjs) {
                                                        if (id.equals(psdyJbj.getId())) {
                                                            mPsdyJbjs.remove(psdyJbj);
                                                            break;
                                                        }
                                                    }
                                                    ToastUtil.shortToast(mContext, "删除成功");
                                                } else {
                                                    CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
                                                            BaseInfoManager.getUserName(mContext) + "原因是：" + responseBody.getMessage()));
                                                    ToastUtil.shortToast(mContext, "删除失败，原因是：" + responseBody.getMessage());
                                                }
                                            }
                                        });
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                );
            }
        }
    }

    /**
     * 在地图上画图形
     *
     * @param graphicsLayer
     * @param ifRemoveAll   在添加前是否移除掉所有
     * @param geometry
     */
    private void drawSelectGeometry(Geometry geometry, GraphicsLayer graphicsLayer, Symbol designatedSymbol, boolean ifRemoveAll, boolean ifCenter) {

        if (graphicsLayer == null) {
            return;
        }
        Symbol symbol = designatedSymbol;
        if (symbol == null) {
            switch (geometry.getType()) {
                case LINE:
                case POLYLINE:
                    symbol = new SimpleLineSymbol(0xff00ffff, 6);
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
            }
        }

        if (ifCenter) {
            mMapView.centerAt(GeometryUtil.getGeometryCenter(geometry), true);
        }
    }


    /**
     * 展示排水户列表
     */
    private void showDrainageUserBottomSheet() {
        // 调整高度
        adjustDrainageUserListViewHeight();
        drainageUserListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                drainageUserListView.setVisibility(View.VISIBLE);
                drainageUserListBehavoir.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }, 300);
    }

    /**
     * 隐藏排水户列表
     */
    private void dismissDrainageUserBottomSheet() {
        drainageUserListBehavoir.setState(BottomSheetBehavior.STATE_COLLAPSED);
        drainageUserListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                drainageUserListView.setVisibility(View.INVISIBLE);
            }
        }, 300);
    }

    private void queryDetailById(String id) {
        // 在这里处理Item点击事件
        pshAffairDetailPresenter.getPSHAffairDetail(Integer.parseInt(id));
    }

    private void filterItemsByHeader(String header) {
        if ("全部".equals(header)) {
            drainageUserListView.setDatas(mDrainageUserBeans);
        } else {
            final List<DrainageUserBean> filterDatas = new ArrayList<>(mDrainageUserBeans.size());
            for (DrainageUserBean mDrainageUserBean : mDrainageUserBeans) {
                if (mDrainageUserBean.getType().contains("其他") && header.equals("其他")) {
                    filterDatas.add(mDrainageUserBean);
                } else if (header.equals(mDrainageUserBean.getType())) {
                    filterDatas.add(mDrainageUserBean);
                }
            }
            drainageUserListView.setDatas(filterDatas);
        }
    }

    private LayerInfo getDYLayerInfo() {
        if (ListUtil.isEmpty(mLayerInfosFromLocal)) {
            mLayerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
        }
        if (ListUtil.isEmpty(mLayerInfosFromLocal)) {
            return null;
        }
        for (LayerInfo layerInfo : mLayerInfosFromLocal) {
            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER2)) {
                return layerInfo;
            }
        }
        return null;
    }

    private void setAddNewPshFacilityListener() {
        if (addModeSelectPshLocationTouchListener == null) {
            addModeSelectPshLocationTouchListener = new SelectLocationTouchListener(mContext,
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

        if (addPshCalloutSureButtonClickListener == null) {
            addPshCalloutSureButtonClickListener = new View.OnClickListener() {
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

                    LocationInfo locationInfo = addModeSelectPshLocationTouchListener.getLoationInfo();
                    if (locationInfo.getDetailAddress() == null || locationInfo.getPoint() == null) {
                        ToastUtil.shortToast(mContext, "请重新选择位置");
                        return;
                    }

                    btn_add_hu.setVisibility(View.VISIBLE);
                    btn_cancel_hu.setVisibility(View.GONE);
                    if (locationMarker != null) {
                        locationMarker.setVisibility(View.GONE);
                    }
                    Intent intent = new Intent(getContext(), SewerageTableActivity.class);
//                    SewerageBeanManger.getInstance().setHouseIdFlag("0");
//                    SewerageBeanManger.getInstance().setHouseId(ParamsInstance.getInstance().getBuildId());
//                    SewerageBeanManger.getInstance().setUnitId("");
                    MyApplication.SEWERAGEITEMBEAN = null;
                    MyApplication.GUID = null;
                    MyApplication.X = locationInfo.getDetailAddress().getX();
                    MyApplication.Y = locationInfo.getDetailAddress().getY();
                    intent.putExtra("isExist", false);
                    intent.putExtra("HouseIdFlag", "0");
                    intent.putExtra("isAllowSaveLocalDraft", false);
//                    intent.putExtra("HouseId", MyApplication.buildId);
                    intent.putExtra("UnitId", "");
                    intent.putExtra("adderss", locationInfo.getDetailAddress());
                    intent.putExtra("isAllowSaveLocalDraft", true);
                    hideCallout();
                    startActivity(intent);

                    locationMarker.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // TODO 开始检索周边排水单元
                            final Point mapCenterPoint = addModeSelectPshLocationTouchListener.getMapCenterPoint();
                            queryDrainageUnit(mapCenterPoint.getX(), mapCenterPoint.getY());
                        }
                    }, 300);
                }
            };
        }

        /**
         * 气泡中“确定”按钮点击事件
         */
        addModeSelectPshLocationTouchListener.setCalloutSureButtonClickListener(addPshCalloutSureButtonClickListener);
        mMapView.setOnTouchListener(addModeSelectPshLocationTouchListener);
    }

    private void setHangUpWellListener() {
        if (hangUpWellLocationTouchListener == null) {
            hangUpWellLocationTouchListener = new SelectLocationTouchListener(mContext,
                    mMapView, locationMarker, false, null) {
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

        if (hangUpWellCalloutSureButtonClickListener == null) {
            hangUpWellCalloutSureButtonClickListener = new View.OnClickListener() {
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

                    Point mapCenterPoint = hangUpWellLocationTouchListener.getMapCenterPoint();
                    // 查询当前位置的接户井
                    queryHangUpWell(mapCenterPoint.getX(), mapCenterPoint.getY());
                }
            };
        }

        /**
         * 气泡中“确定”按钮点击事件
         */
        hangUpWellLocationTouchListener.setCalloutSureButtonClickListener(hangUpWellCalloutSureButtonClickListener);
        mMapView.setOnTouchListener(hangUpWellLocationTouchListener);
    }

    /**
     * 点击地图后查询设施
     *
     * @param keyword
     */
    private void queryDrainageUsersForKeyword(String keyword) {
        if (pd == null) {
            pd = new ProgressDialog(getContext());
        }
        pd.setMessage("正在查询排水户...");
        if (!pd.isShowing()) {
            pd.show();
        }

        ll_next_and_prev_container.setVisibility(View.INVISIBLE);
        currIndex = 0;
        mCurrentDoorNOBean = null;
        mSewerageLayerService.queryPshForKeyword2(keyword, new Callback2<List<DrainageUserBean>>() {
            @Override
            public void onSuccess(List<DrainageUserBean> drainageUserBeans) {
                pd.dismiss();
                mHeaderStrs.clear();
                mDrainageUserBeans.clear();
                if (drainageUserBeans == null || drainageUserBeans.size() == 0) {
                    dismissDrainageUserBottomSheet();
                    return;
                }
                mDrainageUserBeans.addAll(drainageUserBeans);
//                if (drainageUserBeans.size() >= 1) {
//                    // TODO 直接跳转到详情
//                    // 不对，应该要先根据id查询详情，然后再跳转
//                    queryDetailById(drainageUserBeans.get(0).getId());
//                } else {
                // TODO 显示选择列表
                drainageUserListView.setDatas(mDrainageUserBeans);
                final HashMap<String, Integer> map = new HashMap<>(mDrainageUserBeans.size());
//                    map.add(new Pair<>("全部", mDrainageUserBeans.size()));
                for (DrainageUserBean drainageUserBean : drainageUserBeans) {
                    final String type = drainageUserBean.getType();
                    if (type.contains("其他") && map.containsKey("其他")) {
                        map.put("其他", map.get("其他") + 1);
                    } else if (type.contains("其他")) {
                        map.put("其他", 1);
                    } else if (map.containsKey(type)) {
                        map.put(type, map.get(type) + 1);
                    } else {
//                            mHeaderStrs.add(type);
                        map.put(type, 1);
                    }
                }
                final List<Pair<String, String>> headers = new ArrayList<>(map.size() + 1);
                headers.add(new Pair<>("全部", String.valueOf(mDrainageUserBeans.size())));
                mHeaderStrs.add("全部");
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    headers.add(new Pair<>(entry.getKey(), String.valueOf(entry.getValue())));
                    mHeaderStrs.add(entry.getKey());
                }
                showDrainageUserBottomSheet();
                drainageUserListView.setHeaders(headers);
                drainageUserListView.setDatas(mDrainageUserBeans);
//                }
//                sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(0).getS_guid());
            }

            @Override
            public void onFail(Exception error) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.iconLongToast(getContext(), R.mipmap.ic_alert_yellow, "查询错误！");
                    }
                });
                pd.dismiss();
            }
        });
    }

    /**
     * 挂接接户井
     */
    private void hangUpWell(Object object, int position) {
        // 先是隐藏bottomsheet
        hideBottomSheet();
        initGLayer();
        // 然后定位到指定位置
        drawGeometry(new Point(((DrainageUserBean) object).getX(), ((DrainageUserBean) object).getY()), mGLayer, true, true);
        // 然后显示提示语
        if (ll_topbar_container2 != null) {
            tv_query_tip.setText("请移动地图选择接户井");
            ll_topbar_container2.setVisibility(View.VISIBLE);
        }
        // 接着显示那个callout，选择接户井
        mMapMeasureView.stopMeasure();

        if (locationMarker != null) {
            locationMarker.changeIcon(R.mipmap.ic_check_related);
            locationMarker.setVisibility(View.VISIBLE);
        }
        setHangUpWellListener();
        // TODO 然后，应该还得去查询所有相关的挂接物，并显示连线
        queryAllHangUpContents(((DrainageUserBean) object).getId());
    }

    private void queryAllHangUpContents(String pshId) {
        if (pd == null) {
            pd = new ProgressDialog(getContext());
        }
        pd.setMessage("正在查询已挂接的接户井，请稍后...");
        if (!pd.isShowing()) {
            pd.show();
        }
        ll_next_and_prev_container.setVisibility(View.INVISIBLE);
        if (correctFacilityService == null) {
            correctFacilityService = new CorrectFacilityService(getContext());
        }
        correctFacilityService.queryPshGj(null, null, pshId, null)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Result2<List<PshJhj>>>() {
                    @Override
                    public void call(Result2<List<PshJhj>> listResult2) {
                        if (listResult2.getCode() == 200 && listResult2.getData() != null && !listResult2.getData().isEmpty()) {
                            // 查询成功b
                            drawPshJhjLines(listResult2.getData());
                        } else {
                        }
                        pd.dismiss();
                    }
                }, new Action1<Throwable>() {

                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        pd.dismiss();
                    }
                });
    }

    private Handler mainThreadHandler;

    private void runOnUiThread(Runnable runnable) {
        if (mainThreadHandler == null) {
            mainThreadHandler = new Handler(Looper.getMainLooper());
        }
        mainThreadHandler.post(runnable);
    }

    private void toast(final String msg) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private <T extends PsdyJbj> void drawPshJhjLines(List<T> data) {
        if (ListUtil.isEmpty(data)) return;
        initGraphicSelectGLayer();
        for (int j = 0; j < data.size(); j++) {
            drawPshJhjLine(data.get(j));
        }
    }

    private void drawPshJhjLine(PsdyJbj data) {
        if (data == null) return;
        Polyline polyline = null;
        PsdyJbj psdyJbj = null;
        Point startP = null;
        Point endP = null;
//        initGLayer();
        polyline = new Polyline();
        psdyJbj = data;
        startP = new Point();
        startP.setXY(psdyJbj.getPshX(), psdyJbj.getPshY());
        endP = new Point();
        endP.setXY(psdyJbj.getGjwX(), psdyJbj.getGjwY());
        polyline.startPath(startP);
        polyline.lineTo(endP);
        drawGeometry(psdyJbj, polyline, mGraphicSelectLayer, new SimpleLineSymbol(Color.BLUE, 2, SimpleLineSymbol.STYLE.DASH), false, false);
    }

    private void adjustDrainageUserListViewHeight() {
        if (headerHeight == 0) {
            headerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getContext().getResources().getDisplayMetrics());
        }
        if (itemHeight == 0) {
            itemHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getContext().getResources().getDisplayMetrics());
        }
        ViewGroup.LayoutParams layoutParams = drainageUserListView.getLayoutParams();
        layoutParams.height = 0;
        drainageUserListView.setLayoutParams(layoutParams);
        final int initItemHeight;
        if (mDrainageUserBeans.size() < 3) {
            initItemHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            initItemHeight = (int) (itemHeight * 2.5 + headerHeight);
        }
        layoutParams = drainageUserListView.getLayoutParams();
        layoutParams.height = initItemHeight;
        drainageUserListView.setLayoutParams(layoutParams);
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
        ifInGuaJie = false;
        ifInLine = false;
        ifInDY = false;
        ifInDeletePshAndJhjLineMode = false;
        if (locationMarker != null) {
            locationMarker.setVisibility(View.GONE);
        }
        if (1 == mStatus || 2 == mStatus || 5 == mStatus || 6 == mStatus || 7 == mStatus) {
            setMode(0);
            mMapView.getCallout().hide();
            hideBottomSheet();
            initGLayer();
            initGraphicSelectGLayer();
            initSelectGLayer();
            initArrowGLayer();
            initStreamGLayer();
            mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
        } else if (0 == mStatus) {
            ((Activity) mContext).finish();
        }
    }

    private void setMode(int status) {
        mStatus = status;
        if (0 == status) {
            ll_topbar_container.setVisibility(View.GONE);
            ll_topbar_container2.setVisibility(View.GONE);
            if (locationMarker != null) {
                locationMarker.setVisibility(View.GONE);
            }
            initGLayer();
            hideBottomSheet();
            mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
        } else if (1 == status) {
            ll_topbar_container.setVisibility(View.VISIBLE);
            ll_topbar_container2.setVisibility(View.VISIBLE);
        } else if (2 == mStatus) {
            ll_topbar_container.setVisibility(View.VISIBLE);
        } else if (status == 5 || status == 6 || status == 7) {
            ll_topbar_container.setVisibility(View.VISIBLE);
            ll_topbar_container2.setVisibility(View.VISIBLE);
        }
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
        isFirst = true;
        final Point point = new Point(x, y);
        final List<LayerInfo> layerInfoList = new ArrayList<>();
        for (String url : LayerUrlConstant.newComponentUrls) {
            LayerInfo layerInfo = new LayerInfo();
            layerInfo.setUrl(url);
            layerInfoList.add(layerInfo);
        }
        List<LayerInfo> visibleLayerInfos = layersService.getVisibleLayerInfos();//获取可见图层列表，用于只查询可见图层
        Geometry geometry = GeometryEngine.buffer(point, mMapView.getSpatialReference(), 40 * mMapView.getResolution(), null);

        initComponentService();
        if (ifInDY && mStatus == 7) {
            componentService.queryComponentsForJBJ(geometry, new Callback2<List<Component>>() {
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
            }, visibleLayerInfos);
        } else if (ifInGuaJie || ifInDY || ifInSwerageUserMode) {
            componentService.queryComponentsForPSDY(geometry, new Callback2<List<Component>>() {
                @Override
                public void onSuccess(List<Component> components) {
                    if (pd != null) {
                        pd.dismiss();
                    }
                    if (ListUtil.isEmpty(components)) {
                        ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                        return;
                    }
                    final Component component = components.get(0);
                    if (ifInGuaJie) {
                        final String name = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("单元名称"), "");
//                    String name = StringUtil.getNotNullString(components.get(0).getGraphic().getAttributeValue("排水单元名"),"");
                        initSelectGLayer();
                        drawGeometryForGD(component.getGraphic().getGeometry(), mSelectGLayer, false, true);
                        showDYCallout(name, null, component, null);

                    } else if (ifInDY || ifInSwerageUserMode) {
                        mComponentQueryResult = new ArrayList<Component>();
                        mComponentQueryResult.addAll(components);
                        showComponentsOnBottomSheet(mComponentQueryResult);
                    }
                }

                @Override
                public void onFail(Exception error) {
                    if (pd != null) {
                        pd.dismiss();
                    }
                    ToastUtil.shortToast(getContext(), "该地点未查询到设施");
                }
            });
        } else if (ifInDoubtMode) {
//            componentService.queryComponentsForDoubt(geometry, new Callback2<List<Component>>() {
//                @Override
//                public void onSuccess(List<Component> components) {
//                    if (pd != null) {
//                        pd.dismiss();
//                    }
//                    if (ListUtil.isEmpty(components)) {
//                        ToastUtil.shortToast(getContext(), "该地点未查询到设施");
//                        return;
//                    }
//                    mComponentQueryResult = new ArrayList<Component>();
//                    mComponentQueryResult.addAll(components);
//                    if (ListUtil.isEmpty(mComponentQueryResult)) {
//                        initGLayer();
//                        if (pd != null) {
//                            pd.dismiss();
//                        }
//                        ToastUtil.shortToast(getContext(), "该地点未查询到设施");
//                        return;
//                    }
//                    showComponentsOnBottomSheet(mComponentQueryResult);
//                }
//
//                @Override
//                public void onFail(Exception error) {
//                    if (pd != null) {
//                        pd.dismiss();
//                    }
//                    ToastUtil.shortToast(getContext(), "该地点未查询到设施");
//                }
//            });
        } else if (ifInPipeMode && mStatus == 0) {
//            componentService.queryComponentsUpload(geometry, new Callback2<List<Component>>() {
//            componentService.queryComponentsForPipe(geometry, new Callback2<List<Component>>() {
//                @Override
//                public void onSuccess(List<Component> components) {
//                    if (pd != null) {
//                        pd.dismiss();
//                    }
//                    if (ListUtil.isEmpty(components)) {
//                        ToastUtil.shortToast(getContext(), "该地点未查询到设施");
//                        return;
//                    }
//                    mComponentQueryResult = new ArrayList<Component>();
//                    mComponentQueryResult.addAll(components);
//                    if (ListUtil.isEmpty(mComponentQueryResult)) {
//                        initGLayer();
//                        if (pd != null) {
//                            pd.dismiss();
//                        }
//                        ToastUtil.shortToast(getContext(), "该地点未查询到设施");
//                        return;
//                    }
//                    showComponentsOnBottomSheet(mComponentQueryResult);
//                }
//
//                @Override
//                public void onFail(Exception error) {
//                    if (pd != null) {
//                        pd.dismiss();
//                    }
//                    ToastUtil.shortToast(getContext(), "该地点未查询到设施");
//                }
//            }, visibleLayerInfos);
        } else if (ifInCheckMode || ifInEditMode || mStatus == 1 || mStatus == 2) {
            componentService.queryComponentsForWell(geometry, new Callback2<List<Component>>() {
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
            }, visibleLayerInfos);
        } else {
            componentService.queryComponentsForPshAndJhjAndJbj(geometry, new Callback2<List<Component>>() {
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
            }, visibleLayerInfos, true);
//            componentService.queryPrimaryComponentsIdentify(geometry, mMapView, null);
        }

    }

    private void showBottomSheetForCheck(final Component component) {
        ifInLine = false;
        if (0 == mStatus || isClick) {
            componentCenter = component;
            originPipe = createStreamBean(component);
        }
        tvResponsible.setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.tv_gjdy).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_delete).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_line_del).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_jc).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_line_jc).setVisibility(View.GONE);
        ll_check_hint.setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.ll_no_ysk).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.ll_gc).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.tv_no_data).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.ll_name_phone).setVisibility(View.GONE);
        final String layerName = component.getLayerName();
        if (layerName == null || !attribute.contains(layerName)) {
            hideBottomSheet();
            return;
        }
        if (layerName.contains("排水管道")) {
            if (!layerName.contains("中心城区")) {
                checkIfHasBeenUploadBefore(component, 2);
            } else {
                checkIfHasBeenUploadForComponent(component);
            }
        } else if (layerName.contains("窨井") || layerName.contains("雨水口") || layerName.contains("排水口")) {
            if (!layerName.contains("中心城区")) {
                setResponsible(component, 1);
            }
        }
        if (mStatus == 0 || isClick) {
            mGLayer.removeAll();
            drawGeometryForGD(component.getGraphic().getGeometry(), mGLayer, false, true);
            mFirstAttribute = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.ATTR_TWO), "");
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
        Object oErrorInfo = component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ERROR_INFO);
        TextView titleTv = (TextView) map_bottom_sheet.findViewById(R.id.title);
        TextView dateTv = (TextView) map_bottom_sheet.findViewById(R.id.date);
        TextView sortTv = (TextView) map_bottom_sheet.findViewById(R.id.sort);
        TextView subtypeTv = (TextView) map_bottom_sheet.findViewById(subtype);
        TextView field1Tv = (TextView) map_bottom_sheet.findViewById(R.id.field1);
        TextView field2Tv = (TextView) map_bottom_sheet.findViewById(R.id.field2);
        TextView field3Tv = (TextView) map_bottom_sheet.findViewById(R.id.field3);

        View btn_container = map_bottom_sheet.findViewById(R.id.btn_container);//按钮控件容器
        TextView addrTv = (TextView) map_bottom_sheet.findViewById(R.id.addr);//地址
        TextView tv_gc_begin = (TextView) map_bottom_sheet.findViewById(R.id.tv_gc_begin);//起始高程
        TextView tv_gc_end = (TextView) map_bottom_sheet.findViewById(R.id.tv_gc_end);//终止高程
        tv_gc_end.setVisibility(View.VISIBLE);//终止高程
        TextView tv_ms_begin = (TextView) map_bottom_sheet.findViewById(R.id.tv_ms_begin);//起始埋深
        TextView tv_ms_end = (TextView) map_bottom_sheet.findViewById(R.id.tv_ms_end);//终点埋深
        TextView tv_gj = (TextView) map_bottom_sheet.findViewById(R.id.tv_gj);//管径
        ll_gj = map_bottom_sheet.findViewById(R.id.ll_gj);
        ll_gj.setVisibility(View.VISIBLE);
        TextView tv_cz = (TextView) map_bottom_sheet.findViewById(R.id.tv_cz);//净高
        View ll_gq = map_bottom_sheet.findViewById(R.id.ll_gq);//净高
        TextView tv_tycd = (TextView) map_bottom_sheet.findViewById(R.id.tv_tycd);//投影长度
        TextView tv_pd = (TextView) map_bottom_sheet.findViewById(R.id.tv_pd);//坡度
        View line2 = map_bottom_sheet.findViewById(R.id.line2);//分割线
        View line = map_bottom_sheet.findViewById(R.id.line);//分割线
        View line3 = map_bottom_sheet.findViewById(R.id.line3);//分割线
        View line4 = map_bottom_sheet.findViewById(R.id.line4);//分割线
        final TextView tv_zhiyi = (TextView) map_bottom_sheet.findViewById(R.id.tv_zhiyi);//质疑按钮
        View stream_line3 = map_bottom_sheet.findViewById(R.id.stream_line3);//质疑分割线
        tv_zhiyi.setVisibility(View.GONE);
        stream_line3.setVisibility(View.GONE);
//        TextView code = (TextView) map_bottom_sheet.findViewById(R.id.code);
        map_bottom_sheet.findViewById(R.id.ll_no_ysk).setVisibility(View.VISIBLE);
        //左边和右边的详情按钮
        TextView tv_detail_left = (TextView) map_bottom_sheet.findViewById(R.id.tv_detail_left);
        TextView tv_detail_right = (TextView) map_bottom_sheet.findViewById(R.id.tv_detail_right);
        View stream_line4 = map_bottom_sheet.findViewById(R.id.stream_line4);
        tv_detail_left.setVisibility(View.GONE);
        tv_detail_right.setVisibility(View.GONE);
        stream_line4.setVisibility(View.GONE);
        sortTv.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        field3Tv.setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.ll_msd).setVisibility(View.VISIBLE);//删除和查看详情的分割线
        String subtype = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.GRADE), "");
        final String DOUBT_STATUS = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.DOUBT_STATUS));
        String checkState = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.CHECK_STATE), "");
        if (layerName.contains("排水管道")) {//管道就把值加载出来
            line3.setVisibility(View.VISIBLE);
            field3Tv.setVisibility(View.VISIBLE);
            line4.setVisibility(View.VISIBLE);
            line2.setVisibility(View.GONE);
            tv_zhiyi.setVisibility(View.VISIBLE);
            stream_line3.setVisibility(View.VISIBLE);
            btn_container.setVisibility(View.VISIBLE);
            if ("-1".equals(checkState) || StringUtil.isEmpty(checkState)) {
                tv_zhiyi.setVisibility(View.GONE);
                stream_line3.setVisibility(View.VISIBLE);
            }
            addrTv.setVisibility(View.GONE);
            if (layerName.contains("中心城区")) {
                btn_container.setVisibility(View.GONE);
                initValue(component, field3Tv, "管线长度：", ComponentFieldKeyConstant.LENGTH);
            } else {
                initValue(component, field3Tv, "管线长度：", ComponentFieldKeyConstant.LINE_LENGTH);
            }
            ll_gq.setVisibility(View.GONE);
            tv_cz.setVisibility(View.GONE);
            subtype = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.GRADE), "");
            initValue(component, tv_gc_begin, "起始管底高程：", ComponentFieldKeyConstant.BEG_H);
            initValue(component, tv_gc_end, "终止管底高程：", ComponentFieldKeyConstant.END_H);
            initValue(component, tv_ms_begin, "起点管底埋深：", ComponentFieldKeyConstant.BEGCEN_DEE);
            initValue(component, tv_ms_end, "终点管底埋深：", ComponentFieldKeyConstant.ENDCEN_DEE);
            initValue(component, tv_gj, "管径：", ComponentFieldKeyConstant.PIPE_GJ, 0, "");

        } else if (layerName.contains("排水沟渠")) {
            if ("-1".equals(checkState) || StringUtil.isEmpty(checkState)) {
                tv_zhiyi.setVisibility(View.GONE);
                stream_line3.setVisibility(View.VISIBLE);
            }
            line2.setVisibility(View.GONE);
            line3.setVisibility(View.VISIBLE);
            line4.setVisibility(View.VISIBLE);
            ll_gq.setVisibility(View.VISIBLE);
            btn_container.setVisibility(View.GONE);
            addrTv.setVisibility(View.VISIBLE);
            tv_cz.setVisibility(View.VISIBLE);
            subtype = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SUBTYPE), "");
            initValue(component, tv_gc_begin, "起始渠底高程：", ComponentFieldKeyConstant.BEG_H);
            initValue(component, tv_gc_end, "终止渠底高程：", ComponentFieldKeyConstant.END_H);
            initValue(component, tv_ms_begin, "起点管底埋深：", ComponentFieldKeyConstant.BEGCIN_DEEP);
            initValue(component, tv_ms_end, "终点管底埋深：", ComponentFieldKeyConstant.ENDCIN_DEEP);
            initValue(component, tv_gj, "宽度：", ComponentFieldKeyConstant.WIDTH);
            initValue(component, tv_cz, "净高：", ComponentFieldKeyConstant.HEIGHT);
            initValue(component, tv_tycd, "投影长度：", ComponentFieldKeyConstant.LENGTH);
            initValue(component, tv_pd, "坡度：", ComponentFieldKeyConstant.IP);
        } else if (layerName.contains("窨井")) {
            line2.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
            line4.setVisibility(View.GONE);
            ll_gq.setVisibility(View.GONE);
            tv_gc_end.setVisibility(View.GONE);
            field3Tv.setVisibility(View.GONE);
            tv_gc_end.setText("");
            btn_container.setVisibility(View.VISIBLE);
            subtype = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_ONE));
            if (StringUtil.isEmpty(subtype) || "null".equals(subtype)) {
                subtype = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SUBTYPE));
            }
            if (layerName.contains("中心城区")) {
                addrTv.setVisibility(View.VISIBLE);
                tv_cz.setVisibility(View.GONE);
                tv_ms_end.setText("");
                initValue(component, tv_gj, "井盖大小：", ComponentFieldKeyConstant.COVER_SIZE, 2, "mm");
                initValue(component, tv_gc_begin, "地面高程：", ComponentFieldKeyConstant.SUR_H);
                initValue(component, tv_ms_begin, "井底高程：", ComponentFieldKeyConstant.BOTTOM_H);
//                tv_detail_left.setVisibility(View.GONE);
//                tv_detail_right.setVisibility(View.VISIBLE);
            } else {
//                tv_detail_left.setVisibility(View.VISIBLE);
//                stream_line4.setVisibility(View.VISIBLE);
//                tv_detail_right.setVisibility(View.GONE);
                addrTv.setVisibility(View.VISIBLE);
                tv_cz.setVisibility(View.GONE);
                tv_ms_end.setText("");
                tv_gj.setText("");
                initValue(component, tv_gc_begin, "已有挂牌号：", ComponentFieldKeyConstant.ATTR_FIVE, 0, "");
                initValue(component, tv_gj, "井盖大小：", ComponentFieldKeyConstant.COVER_SIZE, 2, "mm");
                ll_gj.setVisibility(View.GONE);
                initValue(component, tv_ms_begin, "所在道路：", ComponentFieldKeyConstant.ROAD, 0, "");
                initValue(component, addrTv, "设施位置：", ComponentFieldKeyConstant.ADDR, 0, "");
            }

        } else if (layerName.contains("排水口")) {
//            tv_detail_left.setVisibility(View.VISIBLE);
//            tv_detail_right.setVisibility(View.GONE);
            ll_gj.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
            line4.setVisibility(View.GONE);
            ll_gq.setVisibility(View.GONE);
            sortTv.setVisibility(View.GONE);
            tv_gc_end.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
            btn_container.setVisibility(View.VISIBLE);
            addrTv.setVisibility(View.VISIBLE);
            tv_cz.setVisibility(View.GONE);
            tv_ms_end.setText("");
            tv_gj.setText("");
//            subtypeTv.setText("河涌名称：" + component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_ONE));

            if (layerName.contains("中心城区")) {
                btn_container.setVisibility(View.GONE);
                initValue(component, tv_gc_begin, "排水口岸别：", ComponentFieldKeyConstant.ATTR_FOUR, 0, "");
                initValue(component, subtypeTv, "河涌名称：", ComponentFieldKeyConstant.RIVERNAME, 0, "");
                initValue(component, tv_ms_begin, "所在道路：", ComponentFieldKeyConstant.LANE_WAY, 0, "");
                initValue(component, addrTv, "设施位置：", ComponentFieldKeyConstant.ADDR, 0, "");
            } else {
                btn_container.setVisibility(View.VISIBLE);
                initValue(component, tv_gc_begin, "排水口岸别：", ComponentFieldKeyConstant.ATTR_FOUR, 0, "");
                initValue(component, subtypeTv, "河涌名称：", ComponentFieldKeyConstant.ATTR_ONE, 0, "");
                initValue(component, tv_ms_begin, "所在道路：", ComponentFieldKeyConstant.ROAD, 0, "");
                initValue(component, addrTv, "设施位置：", ComponentFieldKeyConstant.ADDR, 0, "");
            }
        } else if (layerName.contains("雨水口")) {
//            tv_detail_left.setVisibility(View.VISIBLE);
//            tv_detail_right.setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.ll_no_ysk).setVisibility(View.GONE);
            if (layerName.contains("中心城区")) {
                btn_container.setVisibility(View.GONE);
            } else {
                btn_container.setVisibility(View.VISIBLE);
            }
        } else {
            line2.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
            line4.setVisibility(View.GONE);
            addrTv.setVisibility(View.GONE);
            ll_gq.setVisibility(View.GONE);
            btn_container.setVisibility(View.VISIBLE);
            tv_gc_begin.setText("");
            tv_gc_end.setText("");
            tv_ms_begin.setText("");
            tv_ms_end.setText("");
            tv_gj.setText("");
        }
        if ("-1".equals(checkState)) {
            tv_zhiyi.setText("取消存疑");
        } else {
            tv_zhiyi.setText(" 存疑 ");
        }
        final String finalLayerName = layerName;
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, JournalsDetailListActivity.class);
//                intent.putExtra("component", component);
//                startActivityForResult(intent, 123);
//                if (StringUtil.isEmpty(component.getLayerName())) {
//                    if (!StringUtil.isEmpty(finalLayerName)) {
//                        component.setLayerName(finalLayerName);
//                    }
//                }
            }
        };

        tv_detail_left.setOnClickListener(listener);
        tv_detail_right.setOnClickListener(listener);

        TextView tv_errorinfo = (TextView) map_bottom_sheet.findViewById(R.id.tv_errorinfo);
        String type = component.getLayerName();

//        String usid = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.USID));
        final String objectId = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.OBJECTID));
//        if (StringUtil.isEmpty(usid) || "null".equals(usid)) {
//            usid = objectId;
//        }
        String title = StringUtil.getNotNullString(getLayerName(type), "");
        if (layerName.contains("排水口")) {
            title = StringUtil.getNotNullString(getLayerName(type), "") + "(" + objectId + ")";
        }
        titleTv.setText(title);

        String date = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.UPDATEDATE));
        String formatDate = "";
        try {
            formatDate = TimeUtil.getStringTimeYMD(new Date(Long.valueOf(date)));
        } catch (Exception e) {

        }
        dateTv.setText(StringUtil.getNotNullString(formatDate, ""));
        String sort = "";
        if (layerName.contains("排水管道")) {
            sort = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SORT));
        } else {
            sort = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_TWO));
            if (StringUtil.isEmpty(sort) || "null".equals(sort)) {
                sort = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SORT));
            }
        }
        int color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);

        if (sort.contains("雨污合流")) {
            color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);
        } else if (sort.contains("雨水")) {
            color = ResourcesCompat.getColor(getResources(), R.color.progress_line_green, null);
        } else if (sort.contains("污水")) {
            color = ResourcesCompat.getColor(getResources(), R.color.agmobile_red, null);
        }
        sortTv.setTextColor(color);

        sortTv.setText(StringUtil.getNotNullString(sort, ""));


        /**
         * 如果是雨水口，显示特性：方形
         */
        if (layerName.contains("雨水口")) {
            String feature = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_ONE));
            sortTv.setText(StringUtil.getNotNullString(feature, ""));
        }
        if (layerName.contains("雨水口")) {
            String style = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_THREE));
            subtypeTv.setText(StringUtil.getNotNullString(style, ""));
            subtypeTv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.dust_grey, null));
        } else if (!"排水口(中心城区)".equals(type) && !"排水口".equals(layerName)) {
            subtypeTv.setText(StringUtil.getNotNullString(subtype, ""));
        }

        //已挂牌编号
        String codeValue = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_FIVE));
        if (!StringUtil.isEmpty(codeValue)) {
            codeValue = codeValue.trim();
        }
        /**
         * 修改属性三
         */
        String field3 = "";
        if (layerName.contains("窨井")) {
            String cz = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_THREE), "");
            if (StringUtil.isEmpty(cz)) {
                field3 = "井盖材质: " + StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.COVER_MATERIAL), "");
            }
        } else if (layerName.contains("排水口")) {
            field3 = "排放去向: " + String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.RIVER));
            field3Tv.setText(field3);
        } else if (layerName.contains("排水管道")) {
            field3 = "管道材质: " + StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.MATERIAL1), "");
        } else if (layerName.contains("排水沟渠")) {
            field3 = "沟渠类型: " + StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SUBTYPE), "");
            field3Tv.setText(field3);
        }
//        field3Tv.setVisibility(View.GONE);
//        tv_parent_org_name.setVisibility(View.GONE);
        final String address = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ADDR));
        addrTv.setText("所在道路" + "：" + StringUtil.getNotNullString(address, ""));

        /*
        if (errorInfo == null) {
            tv_errorinfo.setVisibility(View.GONE);
        } else {
            tv_errorinfo.setVisibility(View.VISIBLE);
            tv_errorinfo.setText(errorInfo + "?");
        }
        */
        tv_errorinfo.setVisibility(View.GONE);


//删除按钮
        final String dataType = StringUtil.getNotNullString(component.getGraphic().getAttributes().get("DATA_TYPE"), "");
        map_bottom_sheet.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("排水管道".equals(layerName)) {
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
        map_bottom_sheet.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
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
        ((TextView) map_bottom_sheet.findViewById(R.id.tv_error_correct)).setText("取消");
        final String finalTitle = title;
        map_bottom_sheet.findViewById(R.id.tv_error_correct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDrawStream = false;
                if ("排水管道".equals(layerName)) {
                    if ("3".equals(dataType)) {
                        ToastUtil.shortToast(mContext, "该管线已删除！");
                        return;
                    }
                    isDrawStream = true;
                    mStatus = 1;
                    currentStream = createStreamBean(component);
                    mAllPipeBeans.clear();
                    mAllPipeBeans.add(currentStream);
                    drawStream(componentCenter, mAllPipeBeans, getResources().getColor(R.color.agmobile_red));
                    showPopWindow(component, layerName, false);
                } else if (mStatus == 0) {
                    mStatus = 1;
                    setMode(mStatus);
                    ToastUtil.shortToast(mContext, "请移动地图选择关联井");
                    locationMarker.changeIcon(R.mipmap.ic_check_stream);
                    if (locationMarker != null) {
                        locationMarker.changeIcon(R.mipmap.ic_check_related);
                        locationMarker.setVisibility(View.VISIBLE);
                    }
                    tv_query_tip.setText("请移动地图选择关联井");
                    initStreamGLayer();
                    hideBottomSheet();
                    setSearchFacilityListener();
                } else if (mStatus == 1) {
                    isDrawStream = true;
                    currentStream = createStreamBean(component);
//                    showDialog(component, layerName);
                    if (!StringUtil.isEmpty(originPipe.getObjectId()) && originPipe.getObjectId().equals(objectId)) {
                        ToastUtil.longToast(mContext, "管线起点和终点不能为同一管井！");
                        return;
                    }
                    showPopWindow(component, layerName, true);
                } else {
                    mStatus = 2;
                    setMode(mStatus);
                    if (mPipeStreamView == null) {
                        mPipeStreamView = new PipeStreamView(mMapView, mReviewContainter, mContext);
                        mPipeStreamView.setOnAddStreamListener(new PipeStreamView.onAddStreamListener() {
                            @Override
                            public void onSelectStream(boolean isUpOrDownStream) {
                                UpOrDownStream = isUpOrDownStream;
                                setMode(mStatus);
                                ToastUtil.shortToast(mContext, "请选择上下游窨井");
                                if (locationMarker != null) {
                                    locationMarker.changeIcon(R.mipmap.ic_check_stream);
                                    locationMarker.setVisibility(View.VISIBLE);
                                }
                                hideBottomSheet();
                                initStreamGLayer();
//                                showAttributeForStream(false);
                                setSearchFacilityListener();
                            }

                            @Override
                            public void onDeleteStream(List<PipeBean> upStreams) {
                                mAllPipeBeans.clear();
                                mAllPipeBeans.addAll(upStreams);
                                drawStream(componentCenter, mAllPipeBeans, getResources().getColor(R.color.agmobile_red));
                            }
                        });
                    }
                    mPipeStreamView.setTitle(finalTitle);
//                    showAttributeForStream(true);
                    if (btn_check_cancel.getVisibility() == View.VISIBLE) {
                        btn_check_cancel.performClick();
                    }
                    hideBottomSheet();
                }
            }
        });

        ///质疑的提交
        tv_zhiyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cunyi = tv_zhiyi.getText().toString().trim();
                if (cunyi.equals("取消存疑")) {
                    DialogUtil.MessageBox(mContext, "提示", "确定取消该设施的存疑状态？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String id = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.DOUBT_ID), "");
                            cancelDoubt(id, tv_zhiyi);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                } else {
                    showDoubtDialog(component, component.getGraphic().getGeometry(), tv_zhiyi);
                }
            }
        });
        if (!userCanEdit) {
            btn_container.setVisibility(View.GONE);
        } else if (layerName.contains("排水管道")) {
            map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_sure).setVisibility(View.GONE);
            ((TextView) map_bottom_sheet.findViewById(R.id.tv_error_correct)).setText("确认完善属性");
            if ("排水管道(中心城区)".equals(layerName) || "-1".equals(checkState)) {
                map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
            } else {
                map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
            }
        } else if (mStatus == 0) {
            map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.stream_line2).setVisibility(View.GONE);
            ((TextView) map_bottom_sheet.findViewById(R.id.tv_sure)).setText("   取消   ");
            map_bottom_sheet.findViewById(R.id.tv_sure).setVisibility(View.VISIBLE);
            map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
            ((TextView) map_bottom_sheet.findViewById(R.id.tv_error_correct)).setText("选择关联井");
        } else if (mStatus == 1 || mStatus == 2) {
            map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.stream_line2).setVisibility(View.GONE);
            ((TextView) map_bottom_sheet.findViewById(R.id.tv_sure)).setText("   取消   ");
            map_bottom_sheet.findViewById(R.id.tv_sure).setVisibility(View.VISIBLE);
            map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
            ((TextView) map_bottom_sheet.findViewById(R.id.tv_error_correct)).setText("设置管线信息");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                map_bottom_sheet.setVisibility(View.VISIBLE);
                mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);
                resetInfoLayout();
            }
        }, 300);
    }

    /**
     * 加载设施信息，显示中底部BottomSheet中
     */
    private void showBottomSheet(final Component component) {
        currentComponent = component;
//        ifInLine = false;
        tvResponsible.setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.reason).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.ll_no_ysk).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.ll_gc).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.tv_gjdy).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_swerage_user).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line_swerage_user).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_delete).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_line_del).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_jc).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_line_jc).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_no_data).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.ll_name_phone).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_check_drainage_user).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line_check_drainage_user).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.link_to_jhj).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line_link_to_jhj).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tvLevel2).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line_tvLevel2).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tvDetail).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_right_up).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_right_up_tip).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.sort).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.line).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.subtype).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.ll_gj).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.ll_msd).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.ll_gq).setVisibility(View.VISIBLE);
        initGLayer();
        initSelectGLayer();
        initGraphicSelectGLayer();
        boolean isOutWell = false;
        if (!TextUtils.isEmpty(component.getLayerName()) &&
                (component.getLayerUrl().indexOf("/jbWell/MapServer") != -1 || component.getLayerUrl().indexOf("/jhWellCes/MapServer") != -1 || component.getLayerUrl().indexOf("/jhWellZs/MapServer") != -1) && component.getLayerUrl().endsWith("/0")) {
            isOutWell = true;
        }
        // map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
        // pb_load_check_hint.setVisibility(View.VISIBLE);
        if ("接驳井.".equals(component.getLayerName())/* && (component.getLayerUrl().endsWith("/2") || component.getLayerUrl().endsWith("/4"))*/) {
            //外围区接驳井
            if (ifInDY && mStatus == 7) {
                initSelectGLayer();
                drawGeometryForGD(component.getGraphic().getGeometry(), mSelectGLayer, false, true);
            } else {
                initGLayer();
                drawGeometryForGD(component.getGraphic().getGeometry(), mGLayer, false, true);
            }
//            drawGeometry(component.getGraphic().getGeometry(), mGeometryMapGLayer, false, false);
            showViewForCentralWell(component);
            return;
        } else if ("广州排水单元".equals(component.getLayerName())) {
            initGLayer();
            drawGeometryForGD(component.getGraphic().getGeometry(), mGLayer, false, true);
//            drawGeometry(component.getGraphic().getGeometry(), mGeometryMapGLayer, false, false);
            //显示排水单元里面的门牌号
            if (ifInSwerageUserMode) {
                removePsdyMph();
                addPsdyMph(component);
                hideBottomSheet();
                showSewerageUserBottomSheet(component);
            } else {
                showViewForPSDY(component);
            }
            return;
        } else if ("排水户".equals(component.getLayerName())) {
            initGLayer();
            drawGeometryForGD(component.getGraphic().getGeometry(), mGLayer, false, true);
            showViewForPSH(component);
        } else {
            status = "2";
            person = "";
            tv_check_person.setText("");
            ll_check_hint.setVisibility(View.GONE);
            String layerName = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.LAYER_NAME), "");
            final TextView tv_cant_hook = (TextView) map_bottom_sheet.findViewById(R.id.tv_not_hook);
            tv_cant_hook.setVisibility(View.GONE);
            if (StringUtil.isEmpty(layerName)) {
                layerName = component.getLayerName();
            }

            if ("道路".equals(layerName)) {
                initGLayer();
                drawGeometryForGD(component.getGraphic().getGeometry(), mGLayer, false, true);
                showSimpleBottomView(component);
                return;
            }
            //检测是否有同区的人已经上报过(校核过)
            if ("窨井".equals(layerName) || "雨水口".equals(layerName) || "排水口".equals(layerName)) {
                checkIfHasBeenUploadBefore(component, 1);
                if (component.getGraphic().getGeometry() instanceof Point) {
                    final Point point = (Point) component.getGraphic().getGeometry();
                    componentDetailAddress = null;
                    requestLocation2(point, mMapView.getSpatialReference(), new Callback1<DetailAddress>() {
                        @Override
                        public void onCallback(DetailAddress detailAddress) {
                            detailAddress.setX(point.getX());
                            detailAddress.setY(point.getY());
                            componentDetailAddress = detailAddress;
                        }
                    });
                }
            } else {
                checkIfHasBeenUploadForComponent(component);
            }

            if (mStatus == 0) {
                initGLayer();
                drawGeometryForGD(component.getGraphic().getGeometry(), mGLayer, false, true);
            } else if (mStatus == 1 || mStatus == 2) {
                initStreamGLayer();
                drawGeometry(component.getGraphic().getGeometry(), mStreamGLayer, false, true);
            } else if (mStatus == 7) {
                initSelectGLayer();
                drawGeometry(component.getGraphic().getGeometry(), mSelectGLayer, false, true);
            }
            if (layerName == null || !attribute.contains(layerName)) {
                hideBottomSheet();
                return;
            }
            String errorInfo = null;
            Object oErrorInfo = component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ERROR_INFO);

            if (oErrorInfo != null) {
                errorInfo = oErrorInfo.toString();
            }

            final TextView psdy_delete = (TextView) map_bottom_sheet.findViewById(R.id.psdy_delete);
            psdy_delete.setVisibility(View.GONE);
            final View psdy_line_del = map_bottom_sheet.findViewById(R.id.psdy_line_del);
            psdy_line_del.setVisibility(View.GONE);
            TextView titleTv = (TextView) map_bottom_sheet.findViewById(R.id.title);
            TextView dateTv = (TextView) map_bottom_sheet.findViewById(R.id.date);
            TextView sortTv = (TextView) map_bottom_sheet.findViewById(R.id.sort);
            sortTv.setVisibility(View.VISIBLE);
            TextView subtypeTv = (TextView) map_bottom_sheet.findViewById(R.id.subtype);
            TextView field1Tv = (TextView) map_bottom_sheet.findViewById(R.id.field1);
            TextView field2Tv = (TextView) map_bottom_sheet.findViewById(R.id.field2);
            TextView field3Tv = (TextView) map_bottom_sheet.findViewById(R.id.field3);
            TextView addrTv = (TextView) map_bottom_sheet.findViewById(R.id.addr);
//          TextView code = (TextView) map_bottom_sheet.findViewById(R.id.code);
            View btn_container = map_bottom_sheet.findViewById(R.id.btn_container);//按钮控件容器
            TextView tv_gc_begin = (TextView) map_bottom_sheet.findViewById(R.id.tv_gc_begin);//起始高程
            TextView tv_gc_end = (TextView) map_bottom_sheet.findViewById(R.id.tv_gc_end);//终止高程
            tv_gc_end.setVisibility(View.VISIBLE);
            TextView tv_ms_begin = (TextView) map_bottom_sheet.findViewById(R.id.tv_ms_begin);//起始埋深
            TextView tv_ms_end = (TextView) map_bottom_sheet.findViewById(R.id.tv_ms_end);//终点埋深
            TextView tv_gj = (TextView) map_bottom_sheet.findViewById(R.id.tv_gj);//管径
            View ll_gj = map_bottom_sheet.findViewById(R.id.ll_gj);//管径
            TextView tv_cz = (TextView) map_bottom_sheet.findViewById(R.id.tv_cz);//净高
            View ll_gq = map_bottom_sheet.findViewById(R.id.ll_gq);//净高
            TextView tv_tycd = (TextView) map_bottom_sheet.findViewById(R.id.tv_tycd);//投影长度
            TextView tv_pd = (TextView) map_bottom_sheet.findViewById(R.id.tv_pd);//坡度
            View line = map_bottom_sheet.findViewById(R.id.line);
            View line2 = map_bottom_sheet.findViewById(R.id.line2);//分割线
            View line3 = map_bottom_sheet.findViewById(R.id.line3);//分割线
            View line4 = map_bottom_sheet.findViewById(R.id.line4);//分割线
            line4.setVisibility(View.GONE);
            final TextView tv_zhiyi = (TextView) map_bottom_sheet.findViewById(R.id.tv_zhiyi);//质疑按钮
            View stream_line3 = map_bottom_sheet.findViewById(R.id.stream_line3);//质疑分割线
            View stream_line4 = map_bottom_sheet.findViewById(R.id.stream_line4);//删除和查看详情的分割线
            View stream_line1 = map_bottom_sheet.findViewById(R.id.stream_line1);//删除和查看详情的分割线
            View ll_ms = map_bottom_sheet.findViewById(R.id.ll_msd);//删除和查看详情的分割线
            stream_line4.setVisibility(View.GONE);
            stream_line1.setVisibility(View.GONE);
            tv_zhiyi.setVisibility(View.GONE);
            stream_line3.setVisibility(View.GONE);
//        TextView code = (TextView) map_bottom_sheet.findViewById(R.id.code);
            //左边和右边的详情按钮
            TextView tv_detail_left = (TextView) map_bottom_sheet.findViewById(R.id.tv_detail_left);
            TextView tv_detail_right = (TextView) map_bottom_sheet.findViewById(R.id.tv_detail_right);

            initValue(component, addrTv, "所在道路：", ComponentFieldKeyConstant.ADDR, 0, "");
            line.setVisibility(View.VISIBLE);
            field3Tv.setVisibility(View.GONE);
            ll_gj.setVisibility(View.VISIBLE);
            ll_ms.setVisibility(View.VISIBLE);
            final String DOUBT_STATUS = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.DOUBT_STATUS));
            String checkState = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.CHECK_STATE), "");

            map_bottom_sheet.findViewById(R.id.ll_no_ysk).setVisibility(View.VISIBLE);
            if (layerName.contains("排水管道")) {//管道就把值加载出来
                btn_container.setVisibility(View.VISIBLE);
                addrTv.setVisibility(View.GONE);
                if (layerName.contains("中心城区")) {
                    btn_container.setVisibility(View.GONE);
                    initValue(component, field3Tv, "管线长度：", ComponentFieldKeyConstant.LENGTH);
                } else {
                    if ("-1".equals(checkState) || StringUtil.isEmpty(checkState)) {
                        tv_zhiyi.setVisibility(View.GONE);
                        stream_line3.setVisibility(View.GONE);
                    }
                    initValue(component, field3Tv, "管线长度：", ComponentFieldKeyConstant.LINE_LENGTH);
                }
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.VISIBLE);
                line4.setVisibility(View.VISIBLE);
                ll_gq.setVisibility(View.GONE);
                initValue(component, tv_gc_begin, "起始管底高程：", ComponentFieldKeyConstant.BEG_H);
                initValue(component, tv_gc_end, "终止管底高程：", ComponentFieldKeyConstant.END_H);
                initValue(component, tv_ms_begin, "起点管底埋深：", ComponentFieldKeyConstant.BEGCEN_DEE);
                initValue(component, tv_ms_end, "终点管底埋深：", ComponentFieldKeyConstant.ENDCEN_DEE);
                initValue(component, tv_gj, "管径：", ComponentFieldKeyConstant.PIPE_GJ, 0, "");
            } else if (layerName.contains("排水沟渠")) {
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.VISIBLE);
                line4.setVisibility(View.VISIBLE);
                ll_gq.setVisibility(View.VISIBLE);
                btn_container.setVisibility(View.GONE);
                addrTv.setVisibility(View.VISIBLE);
                tv_cz.setVisibility(View.VISIBLE);
                if ("-1".equals(checkState) || StringUtil.isEmpty(checkState)) {
                    tv_zhiyi.setVisibility(View.GONE);
                    stream_line3.setVisibility(View.GONE);
                }
                initValue(component, tv_gc_begin, "起始渠底高程：", ComponentFieldKeyConstant.BEG_H);
                initValue(component, tv_gc_end, "终止渠底高程：", ComponentFieldKeyConstant.END_H);
                initValue(component, tv_ms_begin, "起点管底埋深：", ComponentFieldKeyConstant.BEGCIN_DEEP);
                initValue(component, tv_ms_end, "终点管底埋深：", ComponentFieldKeyConstant.ENDCIN_DEEP);
                initValue(component, tv_gj, "宽度：", ComponentFieldKeyConstant.WIDTH);
                initValue(component, tv_cz, "净高：", ComponentFieldKeyConstant.HEIGHT);
                initValue(component, tv_tycd, "投影长度：", ComponentFieldKeyConstant.LENGTH);
                initValue(component, tv_pd, "坡度：", ComponentFieldKeyConstant.IP);
            } else if (layerName.contains("窨井")) {

                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.GONE);
                ll_gq.setVisibility(View.GONE);
                stream_line4.setVisibility(View.GONE);
                tv_gc_end.setVisibility(View.GONE);
                field3Tv.setVisibility(View.GONE);
                tv_gc_end.setText("");
                btn_container.setVisibility(View.VISIBLE);
                btn_container.setVisibility(View.VISIBLE);
                if (layerName.contains("中心城区")) {
                    btn_container.setVisibility(View.GONE);
                    addrTv.setVisibility(View.VISIBLE);
                    tv_cz.setVisibility(View.GONE);
                    tv_ms_end.setText("");
                    initValue(component, tv_gj, "井盖大小：", ComponentFieldKeyConstant.COVER_SIZE, 2, "mm");
                    initValue(component, tv_gc_begin, "地面高程：", ComponentFieldKeyConstant.SUR_H);
                    initValue(component, tv_ms_begin, "井底高程：", ComponentFieldKeyConstant.BOTTOM_H);
                    tv_detail_left.setVisibility(View.GONE);
                    tv_detail_right.setVisibility(View.GONE);
                } else {
                    if ("-1".equals(checkState) || StringUtil.isEmpty(checkState)) {
                        tv_zhiyi.setVisibility(View.GONE);
                        stream_line3.setVisibility(View.GONE);
                    }
                    tv_detail_left.setVisibility(View.GONE);
                    line4.setVisibility(View.GONE);
                    tv_detail_right.setVisibility(View.GONE);
                    btn_container.setVisibility(View.VISIBLE);
                    addrTv.setVisibility(View.VISIBLE);
                    tv_cz.setVisibility(View.GONE);
                    tv_ms_end.setVisibility(View.GONE);
                    tv_gj.setText("");
                    if (isOutWell) {
                        initValue(component, tv_gc_begin, "管理单位：", "SUPERVISE_ORG_NAME", 0, "");
                    } else {
                        initValue(component, tv_gc_begin, "已有挂牌号：", ComponentFieldKeyConstant.ATTR_FIVE, 0, "");
                    }
                    initValue(component, tv_gj, "井盖大小：", ComponentFieldKeyConstant.COVER_SIZE, 2, "mm");
                    ll_gj.setVisibility(View.GONE);
                    initValue(component, tv_ms_begin, "所在道路：", ComponentFieldKeyConstant.ROAD, 0, "");
                    initValue(component, addrTv, "设施位置：", ComponentFieldKeyConstant.ADDR, 0, "");
                }

            } else if (layerName.contains("排水口")) {
                tv_detail_left.setVisibility(View.GONE);
                stream_line4.setVisibility(View.GONE);
                line4.setVisibility(View.VISIBLE);
                tv_detail_right.setVisibility(View.GONE);
                ll_gj.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.GONE);
                ll_gq.setVisibility(View.GONE);
                sortTv.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                tv_gc_end.setVisibility(View.GONE);
                btn_container.setVisibility(View.VISIBLE);
                addrTv.setVisibility(View.VISIBLE);
                tv_cz.setVisibility(View.GONE);
                tv_ms_end.setText("");
                tv_gj.setText("");
                if ("-1".equals(checkState) || StringUtil.isEmpty(checkState)) {
                    tv_zhiyi.setVisibility(View.GONE);
                    stream_line3.setVisibility(View.GONE);
                }
                if (layerName.contains("中心城区")) {
                    btn_container.setVisibility(View.GONE);
                    initValue(component, tv_gc_begin, "排水口岸别：", ComponentFieldKeyConstant.ATTR_FOUR, 0, "");
                    initValue(component, subtypeTv, "河涌名称：", ComponentFieldKeyConstant.RIVERNAME, 0, "");
                    initValue(component, tv_ms_begin, "所在道路：", ComponentFieldKeyConstant.LANE_WAY, 0, "");
                    initValue(component, addrTv, "设施位置：", ComponentFieldKeyConstant.ADDR, 0, "");
                } else {
                    btn_container.setVisibility(View.VISIBLE);
                    initValue(component, tv_gc_begin, "排水口岸别：", ComponentFieldKeyConstant.ATTR_FOUR, 0, "");
                    initValue(component, subtypeTv, "河涌名称：", ComponentFieldKeyConstant.ATTR_ONE, 0, "");
                    initValue(component, tv_ms_begin, "所在道路：", ComponentFieldKeyConstant.ROAD, 0, "");
                    initValue(component, addrTv, "设施位置：", ComponentFieldKeyConstant.ADDR, 0, "");
                }
            } else if (layerName.contains("雨水口")) {
                stream_line4.setVisibility(View.VISIBLE);
                if ("-1".equals(checkState) || StringUtil.isEmpty(checkState)) {
                    tv_zhiyi.setVisibility(View.GONE);
                    stream_line3.setVisibility(View.GONE);
                }
                map_bottom_sheet.findViewById(R.id.ll_no_ysk).setVisibility(View.GONE);
                tv_detail_left.setVisibility(View.GONE);
                tv_detail_right.setVisibility(View.GONE);
                line4.setVisibility(View.VISIBLE);
                if (layerName.contains("中心城区")) {
                    btn_container.setVisibility(View.GONE);
                } else {
                    btn_container.setVisibility(View.VISIBLE);
                }
            } else if (layerName.contains("存疑区域")) {
                ll_ms.setVisibility(View.GONE);
                tv_detail_left.setVisibility(View.GONE);
                stream_line4.setVisibility(View.GONE);
                line4.setVisibility(View.VISIBLE);
                tv_detail_right.setVisibility(View.GONE);
                ll_gj.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.GONE);
                ll_gq.setVisibility(View.GONE);
                sortTv.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                if ("2".equals(DOUBT_STATUS)) {
                    btn_container.setVisibility(View.GONE);
                } else {
                    btn_container.setVisibility(View.VISIBLE);
                }
                addrTv.setVisibility(View.VISIBLE);
                tv_zhiyi.setVisibility(View.GONE);
                stream_line3.setVisibility(View.GONE);
                tv_cz.setVisibility(View.GONE);
                tv_ms_begin.setVisibility(View.GONE);
                tv_gc_end.setVisibility(View.GONE);
                tv_ms_end.setText("");
                tv_gj.setText("");
//            subtypeTv.setText("河涌名称：" + component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_ONE));
                initValue(component, tv_gc_begin, "所在单位：", ComponentFieldKeyConstant.MEMO2, 0, "");
                initValue(component, subtypeTv, "上报人：", ComponentFieldKeyConstant.LRR, 0, "");
                initValue(component, addrTv, "存疑原因：", ComponentFieldKeyConstant.MEMO, 0, "");
            } else {
                btn_container.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.GONE);
                ll_gq.setVisibility(View.GONE);
                addrTv.setVisibility(View.GONE);
                field3Tv.setText("");
                tv_cz.setText("");
                tv_gc_begin.setText("");
                tv_gc_end.setText("");
                tv_ms_begin.setText("");
                tv_ms_end.setText("");
                tv_gj.setText("");
            }

            if ("-1".equals(checkState)) {
                tv_zhiyi.setText("取消存疑");
            } else {
                tv_zhiyi.setText(" 存疑 ");
            }

            final String finalLayerName = layerName;
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, JournalsDetailListActivity.class);
//                    intent.putExtra("component", component);
//                    startActivityForResult(intent, 123);
//                    if (StringUtil.isEmpty(component.getLayerName())) {
//                        if (!StringUtil.isEmpty(finalLayerName)) {
//                            component.setLayerName(finalLayerName);
//                        }
//                    }
                }
//
            };
            tv_detail_left.setOnClickListener(listener);
            tv_detail_right.setOnClickListener(listener);

            TextView tv_errorinfo = (TextView) map_bottom_sheet.findViewById(R.id.tv_errorinfo);
            final String type = layerName;

            String subtype = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_ONE));
            if (StringUtil.isEmpty(subtype) || "null".equals(subtype)) {
                subtype = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SUBTYPE));
            }
            String usid = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.USID));
            String objectId = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.OBJECTID));

            String title = StringUtil.getNotNullString(getLayerName(type), "");

            if (layerName.contains("排水口")) {
                title = StringUtil.getNotNullString(getLayerName(type), "") + "(" + objectId + ")";
            }
            titleTv.setText(title);
            String date = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.UPDATEDATE));
            String formatDate = "";
            try {
                formatDate = TimeUtil.getStringTimeYMD(new Date(Long.valueOf(date)));
            } catch (Exception e) {

            }
            dateTv.setText(StringUtil.getNotNullString(formatDate, ""));
            String sort = "";
            if (layerName.contains("排水管道")) {
                sort = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SORT));
            } else {
                sort = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_TWO));
                if (StringUtil.isEmpty(sort) || "null".equals(sort)) {
                    sort = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.SORT));
                }
            }
            int color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);

            if (sort.contains("雨污合流")) {
                color = ResourcesCompat.getColor(getResources(), R.color.mark_light_purple, null);
            } else if (sort.contains("雨水")) {
                color = ResourcesCompat.getColor(getResources(), R.color.progress_line_green, null);
            } else if (sort.contains("污水")) {
                color = ResourcesCompat.getColor(getResources(), R.color.agmobile_red, null);
            }
            sortTv.setTextColor(color);

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
            if ("雨水口".equals(layerName)) {
                String feature = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_ONE));
                sortTv.setText(StringUtil.getNotNullString(feature, ""));
            }
            if ("雨水口".equals(type)) {
                String style = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_THREE));
                subtypeTv.setText(StringUtil.getNotNullString(style, ""));
                subtypeTv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.dust_grey, null));
            } else if (!"排水口(中心城区)".equals(type) && !"排水口".equals(type) && !"存疑区域".equals(type)) {
                subtypeTv.setText(StringUtil.getNotNullString(subtype, ""));
            }


            //已挂牌编号
            String codeValue = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_FIVE));
            if (!StringUtil.isEmpty(codeValue)) {
                codeValue = codeValue.trim();
            }
            /**
             * 修改属性三
             */
            String field3 = "";
            if (layerName.contains("窨井")) {
                String cz = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_THREE), "");
                if (StringUtil.isEmpty(cz)) {
                    field3 = "井盖材质: " + StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.COVER_MATERIAL), "");
                }
                field3Tv.setText(field3);
            }

//        tv_parent_org_name.setVisibility(View.GONE);
        /*
        if (errorInfo == null) {
            tv_errorinfo.setVisibility(View.GONE);
        } else {
            tv_errorinfo.setVisibility(View.VISIBLE);
            tv_errorinfo.setText(errorInfo + "?");
        }
        */
            tv_errorinfo.setVisibility(View.GONE);


            String parentOrg = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.ATTR_FOUR));
//        tv_parent_org_name.setText("权属单位：" + StringUtil.getNotNullString(parentOrg, ""));
            ////   showBottomSheetContent(component_intro.getId());

            View treamLine = map_bottom_sheet.findViewById(R.id.stream_line);

            final String reportType = StringUtil.getNotNullString(component.getGraphic().getAttributes().get("REPORT_TYPE"), "");
            /**
             * 确认按钮
             */
            map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_sure).setVisibility(View.GONE);
            final String finalLayerName1 = layerName;
            map_bottom_sheet.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("delete".equals(reportType)) {
                        ToastUtil.shortToast(mContext, "该井已被删除，正在审核中，请勿重复操作！");
                        return;
                    }
                    String message = "";
                    if (ifInLine && !ListUtil.isEmpty(mPsdyJbjs)) {
                        message = "该接驳井已挂接" + mPsdyJbjs.size() + "个排水单元，是否确定删除？";
                    } else if (isJHJWell && !ListUtil.isEmpty(pshJhjs)) {
                        message = "该接户井已挂接" + pshJhjs.size() + "个排水户，是否确定删除？";
                    } else {
                        message = "是否确定要删除该" + (isJHJWell ? "接户井" : "接驳井") + "？";
                    }
                    DialogUtil.MessageBox(mContext, "提示", message, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Object o = component.getGraphic().getAttributes().get(UploadLayerFieldKeyConstant.REPORT_TYPE);

                            Long markId = objectToLong(component.getGraphic().getAttributes().get(UploadLayerFieldKeyConstant.MARK_ID));
                            if (o != null) {
                                if (UploadLayerFieldKeyConstant.CORRECT_ERROR.equals(o.toString()) || UploadLayerFieldKeyConstant.CONFIRM.equals(o.toString())) {
                                    //纠错
                                    ModifiedFacility modifiedFacilityFromGraphic = getModifiedFacilityFromGraphic(component.getGraphic().getAttributes(), component.getGraphic().getGeometry());
                                    deleteModify(modifiedFacilityFromGraphic);
                                } else {
                                    //新增
                                    deleteFacility(markId, component);
                                }
                            } else {
                                ModifiedFacility modifiedFacilityFromGraphic = getModifiedFacilityFromGraphic(component.getGraphic().getAttributes(), component.getGraphic().getGeometry());
                                deleteModify(modifiedFacilityFromGraphic);
                            }
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
            });

            map_bottom_sheet.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ifInWellMode = true;
                    if (mGeometryMapGLayer == null) {
                        mGeometryMapGLayer = new GraphicsLayer();
                        mMapView.addLayer(mGeometryMapGLayer);
                    }
//                    if (mGeometryMapOnTouchListener == null) {
//                        mGeometryMapOnTouchListener = new GeometryDragOnTouchListener(mContext, mMapView, mGeometryMapGLayer);
//                    }
//                    mMapView.setOnTouchListener(mGeometryMapOnTouchListener);
//                    mGeometryMapOnTouchListener.setOperationState(GeometryDragOnTouchListener.OperationState.STATE_DRAW_OVER);
                    drawGeometry(component.getGraphic().getGeometry(), mGeometryMapGLayer, false, false);
                    mGLayer.removeAll();
                    mTitle = "移动窨井位置";
                    ll_topbar_container2.setVisibility(View.VISIBLE);
                    addTopBarView(mTitle);
                    tv_query_tip.setText("长按窨井图标移动位置");
                    ll_topbar_container.setVisibility(View.VISIBLE);
                    hideBottomSheet();
                    return;
                }
            });
            /**
             * 纠错按钮
             */
            ((TextView) map_bottom_sheet.findViewById(R.id.tv_error_correct)).setText(" 修改 ");
            map_bottom_sheet.findViewById(R.id.tv_error_correct).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (type.equals("存疑区域")) {
                        DialogUtil.MessageBox(mContext, "提示", "确定取消该设施的存疑状态？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.DOUBT_ID), "");
                                cancelDoubt(id, tv_zhiyi);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        return;
                    }
                    if ("delete".equals(reportType)) {
                        ToastUtil.shortToast(mContext, "该窨井已删除！");
                        return;
                    }

                    Object o = component.getGraphic().getAttributes().get(UploadLayerFieldKeyConstant.REPORT_TYPE);
                    if (o != null) {
                        if (UploadLayerFieldKeyConstant.ADD.equals(o.toString())) {
                            //新增
                            UploadedFacility uploadedFacility = getUploadedFacilityFromGraphic(component.getGraphic().getAttributes(), component.getGraphic().getGeometry());
                            Intent intent = new Intent(mContext, ReEditWellFacilityActivity.class);
                            intent.putExtra("isLoad", true);
                            intent.putExtra("data", (Parcelable) uploadedFacility);
                            startActivity(intent);
                            return;
                        }
                    }
                    if ("0".equals(status)) {
                        //说明有人上报过，那么要提示
                        DialogUtil.MessageBox(mContext, "提醒", "        " + person + "已经校核过该设施，请确定是否仍要校核该设施？",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        btn_check.setVisibility(View.VISIBLE);
                                        btn_check_cancel.setVisibility(View.GONE);
                                        Intent intent = new Intent(mContext, CorrectOrConfirimWellActivity.class);
                                        if (StringUtil.isEmpty(component.getLayerName())) {
                                            if (!StringUtil.isEmpty(type)) {
                                                component.setLayerName(type);
                                            }
                                        }
                                        intent.putExtra("component", component);
                                        //从此处进入校核界面的话允许用户修改窨井类型
                                        intent.putExtra("isAllowEditWellType", true);
                                        startActivityForResult(intent, 123);
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                    } else {
                        btn_check.setVisibility(View.VISIBLE);
                        btn_check_cancel.setVisibility(View.GONE);
                        Intent intent = new Intent(mContext, CorrectOrConfirimWellActivity.class);
                        if (StringUtil.isEmpty(component.getLayerName())) {
                            if (!StringUtil.isEmpty(type)) {
                                component.setLayerName(type);
                            }
                        }
                        intent.putExtra("component", component);
                        //从此处进入校核界面的话允许用户修改窨井类型
                        intent.putExtra("isAllowEditWellType", true);
                        startActivityForResult(intent, 123);
                    }

                }
            });

            ///质疑的提交
            tv_zhiyi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cunyi = tv_zhiyi.getText().toString().trim();
                    if (cunyi.equals("取消存疑")) {
                        DialogUtil.MessageBox(mContext, "提示", "确定取消该设施的存疑状态？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.DOUBT_ID), "");
                                cancelDoubt(id, tv_zhiyi);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    } else {
                        showDoubtDialog(component, component.getGraphic().getGeometry(), tv_zhiyi);
                    }

                }
            });
            //删除连线
            psdy_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addTopBarView("删除接驳井连线");
                    mStatus = 6;
                    setMode(mStatus);
                    ToastUtil.shortToast(mContext, "请选择连线");
//                                        locationMarker.changeIcon(R.mipmap.ic_check_stream);
//                                        if (locationMarker != null) {
//                                            locationMarker.changeIcon(R.mipmap.ic_check_related);
//                                            locationMarker.setVisibility(View.VISIBLE);
//                                        }
                    locationMarker.setVisibility(View.GONE);
                    tv_query_tip.setText("请选择要删除的接驳井连线");
//                                        initStreamGLayer();
                    hideBottomSheet();
                    mGLayer.clearSelection();
                    mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                }
            });
            if (isOutWell && !ifInDY) {
                String attrOne = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.ATTR_ONE), "");
                if ("接户井".equals(attrOne)) {
                    isJHJWell = true;
                } else {
                    isJHJWell = false;
                }
                if (mIdentificationService == null) {
                    mIdentificationService = new CorrectFacilityService(mContext.getApplicationContext());
                }
                if (isJHJWell) {
                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_gjdy)).setText("挂接排水户");
                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_gjdy)).setVisibility(View.VISIBLE);
                    map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.VISIBLE);
                } else {
                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_gjdy)).setText("挂接单元");
                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_gjdy)).setVisibility(View.GONE);
                    map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.GONE);
                }
                String usids = null;
                if (!StringUtil.isEmpty(usid) && !"null".equals(usid)) {
                    usids = usid;
                }
                map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.VISIBLE);
                mPsdyJbjs.clear();
                String pshId = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("OBJECTID"), "");
                if (isJHJWell) {
                    queryJhjAllHangUpContents(psdy_delete, pshId);
                } else {
                    mIdentificationService.queryPsdyJbj(usids, objectId, null)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Result2<List<PsdyJbj>>>() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    if (pd != null && pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                    ToastUtil.shortToast(mContext, "获取连线信息失败");
                                }

                                @Override
                                public void onNext(Result2<List<PsdyJbj>> responseBody) {
                                    if (pd != null && pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                    if (responseBody.getCode() == 200) {
                                        if (!ListUtil.isEmpty(responseBody.getData())) {
                                            ifInLine = true;
                                            psdy_delete.setVisibility(View.GONE);
                                            psdy_line_del.setVisibility(View.GONE);
                                            drawPsdyLines(responseBody.getData());
                                            List<PsdyJbj> tempList = responseBody.getData();
                                            if (mPsdyJbjs == null) mPsdyJbjs = new ArrayList<>();
                                            mPsdyJbjs.clear();
                                            ;
                                            mPsdyJbjs.addAll(tempList);
                                        }
                                    } else {
                                        ToastUtil.shortToast(mContext, "获取连线信息失败");
                                    }
                                }
                            });
                }
            } else {
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_gjdy)).setText("确认挂接");
                map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
                psdy_line_del.setVisibility(View.GONE);
            }

            //单元挂接
            map_bottom_sheet.findViewById(R.id.tv_gjdy).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tin;
                    if (!ifInDY) {
                        ifInGuaJie = true;
//                        addTopBarView("选择排水单元挂接");
                        if (isJHJWell) {
                            addTopBarView("选择排水户挂接");
                            tin = "请移动地图选择排水户";
                        } else {
                            addTopBarView("选择排水单元挂接");
                            tin = "请移动地图选择排水单元";
                        }
                        mStatus = 5;
                        setMode(mStatus);
                        ToastUtil.shortToast(mContext, tin);
                        locationMarker.changeIcon(R.mipmap.ic_check_stream);
                        if (locationMarker != null) {
                            locationMarker.changeIcon(R.mipmap.ic_check_related);
                            locationMarker.setVisibility(View.VISIBLE);
                        }
                        tv_query_tip.setText(tin);
                        initStreamGLayer();
                        hideBottomSheet();
                        setSearchFacilityListener();
                    } else {
                        showJBJDialog(null, component);
                    }
                }
            });


            if (!userCanEdit && (layerName.contains("窨井") || layerName.contains("雨水口") || layerName.contains("排水口"))) {
                tv_zhiyi.setVisibility(View.GONE);
                stream_line3.setVisibility(View.GONE);
                tv_detail_left.setVisibility(View.GONE);
                stream_line4.setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
                stream_line1.setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.tv_sure).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line2).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
                tv_detail_right.setVisibility(View.GONE);
            } else if (!userCanEdit) {
                btn_container.setVisibility(View.GONE);
            } else if (layerName.contains("窨井") || layerName.contains("雨水口") || layerName.contains("排水口")) {
                map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.VISIBLE);
                map_bottom_sheet.findViewById(R.id.stream_line2).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_delete)).setVisibility(View.VISIBLE);
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_sure)).setVisibility(View.GONE);
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_sure)).setText("修改位置");
                if (isOutWell) {
                    map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.VISIBLE);
                } else {
                    map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
                }
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_error_correct)).setText(" 修改 ");
                if ("-1".equals(checkState)) {
                    map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
                    map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
                }
            } else if (layerName.equals("存疑区域")) {
                map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line2).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_delete)).setVisibility(View.GONE);
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_sure)).setVisibility(View.GONE);
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_sure)).setText("修改位置");
                map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.VISIBLE);
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_error_correct)).setText("取消存疑");
            } else {
                map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.tv_sure).setVisibility(View.GONE);
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_error_correct)).setText(" 修改 ");
            }
        }
        View jcView = map_bottom_sheet.findViewById(R.id.psdy_jc);
        View jcLineView = map_bottom_sheet.findViewById(R.id.psdy_line_jc);
        if (isOutWell) {

            map_bottom_sheet.findViewById(R.id.btn_container).setVisibility(View.VISIBLE);
            if (ifInDY && mStatus == 7) {
                jcView.setVisibility(View.GONE);
                jcLineView.setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line2).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line4).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_delete)).setVisibility(View.GONE);
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_sure)).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.tv_zhiyi).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.tv_detail_right).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.tv_detail_left).setVisibility(View.GONE);
                map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.INVISIBLE);
            } else {
                jcView.setVisibility(View.VISIBLE);
                jcLineView.setVisibility(View.VISIBLE);
                map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.VISIBLE);
            }
            map_bottom_sheet.findViewById(R.id.tv_gjdy).setVisibility(isJHJWell ? View.VISIBLE : View.GONE);
            map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(isJHJWell ? View.VISIBLE : View.GONE);

            jcView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WellMonitorListActivity.class);
                    intent.putExtra("objectid", String.valueOf(component.getGraphic().getAttributeValue("OBJECTID")));
                    intent.putExtra("usid", "");
                    intent.putExtra("type", String.valueOf(component.getGraphic().getAttributeValue("ATTR_TWO")));
                    intent.putExtra("X", String.valueOf(component.getGraphic().getAttributeValue("X")));
                    intent.putExtra("Y", String.valueOf(component.getGraphic().getAttributeValue("Y")));
                    startActivity(intent);
                }
            });

        } else {
            map_bottom_sheet.findViewById(R.id.tv_gjdy).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.GONE);
            jcView.setVisibility(View.GONE);
            jcLineView.setVisibility(View.GONE);
        }
        ////// showBottomSheetContent(component_intro.getId());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                map_bottom_sheet.setVisibility(View.VISIBLE);
                mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);
                resetInfoLayout();
            }
        }, 300);
    }

    private void showComponentsOnBottomSheet(List<Component> componentQueryResult) {
        if (componentQueryResult.size() > 1) {
            btn_next.setVisibility(View.VISIBLE);
        }
        mMapView.getCallout().hide();
        //隐藏marker
        locationMarker.setVisibility(View.GONE);
        //initGLayer();
        String layerName = mComponentQueryResult.get(0).getLayerName();
        if (ifInCheckMode || ifInPipeMode || mStatus == 1 || mStatus == 2 || (isClick && ("排水管道".equals(layerName) || "排水管道(中心城区)".equals(layerName)))) {
            showBottomSheetForCheck(mComponentQueryResult.get(0));
        } else {
            showBottomSheet(mComponentQueryResult.get(0));
        }
    }

    /**
     * 在地图上画图形
     *
     * @param graphicsLayer
     * @param ifRemoveAll   在添加前是否移除掉所有
     * @param geometry
     */
    private void drawGeometryForGD(final Geometry geometry, GraphicsLayer graphicsLayer, boolean ifRemoveAll, final boolean ifCenter) {

        if (graphicsLayer == null || geometry == null) {
            return;
        }
        Symbol symbol = null;
        SimpleFillSymbol simpleFillSymbol = null;
        switch (geometry.getType()) {
            case LINE:
            case POLYLINE:
                symbol = new SimpleLineSymbol(getResources().getColor(R.color.agmobile_black), 5);
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
//        Geometry newGeometry =  null;
//        if(geometry instanceof Point){
//            if(((Point) geometry).getX()>ComponentService.BIG_POINT){
//                newGeometry = GeometryEngine.project(geometry,SpatialReference.create(ComponentService.HUANGPU_WIKI),SpatialReference.create(ComponentService.CHENGQU_WIKI));
//            }else{
//                newGeometry = geometry;
//            }
//        }else if(geometry instanceof Polyline) {
//            if(((Polyline) geometry).getPoint(0).getX() > ComponentService.BIG_POINT) {
//                newGeometry = GeometryEngine.project(geometry, SpatialReference.create(ComponentService.HUANGPU_WIKI), SpatialReference.create(ComponentService.CHENGQU_WIKI));
//            }else{
//                newGeometry = geometry;
//            }
//        }else if(geometry instanceof Polygon){
//            if(((Polygon) geometry).getPoint(0).getX() > ComponentService.BIG_POINT) {
//                newGeometry = GeometryEngine.project(geometry, SpatialReference.create(ComponentService.HUANGPU_WIKI), SpatialReference.create(ComponentService.CHENGQU_WIKI));
//            }else{
//                newGeometry = geometry;
//            }
//        }else if(geometry instanceof Line){
//            if(((Line) geometry).getStartX() > ComponentService.BIG_POINT) {
//                newGeometry = GeometryEngine.project(geometry, SpatialReference.create(ComponentService.HUANGPU_WIKI), SpatialReference.create(ComponentService.CHENGQU_WIKI));
//            }else{
//                newGeometry = geometry;
//            }
//        }

        if (symbol != null) {
            Graphic graphic = new Graphic(geometry, symbol);
            graphicsLayer.addGraphic(graphic);
            if (geometry.getType() == Geometry.Type.POLYLINE) { //如果是线，那么这个就是要传递给服务端的结果
                this.graphic = graphic;
                if (!isFirst) {
                    countDownAnimation(this.graphic, graphicsLayer);
                }
            }
        }
        if (simpleFillSymbol != null) {
            Graphic graphic = new Graphic(geometry, simpleFillSymbol);
            graphicsLayer.addGraphic(graphic);
            if (geometry.getType() == Geometry.Type.POLYLINE) { //如果是线，那么这个就是要传递给服务端的结果
                this.graphic = graphic;
            }
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

        isFirst = false;
    }


    private void showDYCallout(final String name, Point point, final Component component, final View.OnClickListener calloutSureButtonClickListener) {
        if (name != null) {
            final Point geometry = point;
            final Callout callout = mMapView.getCallout();
            View view = View.inflate(mContext, R.layout.view_psdy_callout, null);
            ((TextView) view.findViewById(com.augurit.agmobile.patrolcore.R.id.tv_listcallout_title)).setText(name);
            view.findViewById(com.augurit.agmobile.patrolcore.R.id.btn_select_device).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPsdyDialog(name, component);
                }
            });
            callout.setStyle(com.augurit.agmobile.patrolcore.R.xml.editmap_callout_style);
            callout.setContent(view);
//            if (point == null) {
//                point = mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2 - 140);
//            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Point point1 = mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2 - 140);
                    callout.show(point1);
                }
            }, 800);

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
        String pipeLenght = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.LINE_LENGTH), "");
        if (!StringUtil.isEmpty(direction)) {
            pipeBean.setOldDirection(direction);
            pipeBean.setDirection(direction);
        } else {
            pipeBean.setOldDirection("");
            pipeBean.setDirection("正向");
        }
        pipeBean.setPipeGj(pipeGj);

        if (!StringUtil.isEmpty(pipeLenght)) {
            pipeBean.setLineLength(Double.valueOf(pipeLenght));
        }
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
            correctFacilityService.getGwyxtLastRecord(objectId, type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<CheckResult>() {
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

                        @SuppressLint("WrongViewCast")
                        @Override
                        public void onNext(CheckResult stringResult2) {
                            if (stringResult2 == null || stringResult2.getCode() != 200) {
                                //说明没有人上报过，显示“校核”按钮
                                //  map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.VISIBLE);
                                ll_check_hint.setVisibility(View.GONE);
                                status = "2";
                                person = "";
                            } else {
                                String checkPersonName = stringResult2.getMarkPerson();
                                if (BaseInfoManager.getUserName(mContext).equals(checkPersonName)) {
                                    checkPersonName = "您";
                                }
                                ll_check_hint.setVisibility(View.VISIBLE);
                                tv_check_person.setVisibility(View.VISIBLE);
                                String date = TimeUtil.getStringTimeYMD(new Date(stringResult2.getMarkTime()));
                                tv_check_person.setText(checkPersonName + "已经在" + date);
                                person = checkPersonName;
                                tv_check_phone.setVisibility(View.VISIBLE);
                                String handle = "";
                                if ("0".equals(stringResult2.getHandle())) {
                                    status = "0";
                                    handle = "校核";
                                } else if ("1".equals(stringResult2.getHandle())) {
                                    status = "1";
                                    handle = "新增";
                                } else if ("3".equals(stringResult2.getHandle())) {
                                    status = "3";
                                    handle = "删除";
                                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_error_correct)).setVisibility(View.GONE);
                                    map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
                                    map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
                                    map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
                                    map_bottom_sheet.findViewById(R.id.stream_line2).setVisibility(View.GONE);
                                }
                                tv_check_phone.setText(handle);
                                //已经校核过该设施，该设施共被校核" + checkPerson.getTotal() + "次"
                                tv_check_hint.setText("过该设施");

                                // 设置负责人
                                if (TextUtils.isEmpty(stringResult2.getGyZrr())) {
                                    map_bottom_sheet.findViewById(R.id.ll_name_phone).setVisibility(View.GONE);
                                    map_bottom_sheet.findViewById(R.id.tv_no_data).setVisibility(View.VISIBLE);
                                } else {
                                    map_bottom_sheet.findViewById(R.id.ll_name_phone).setVisibility(View.VISIBLE);
                                    map_bottom_sheet.findViewById(R.id.tv_no_data).setVisibility(View.GONE);
                                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_name)).setText("管养负责人:" + stringResult2.getGyZrr());
                                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_phone)).setText("电话:" + stringResult2.getGyZrrLxdh());
                                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_name2)).setText("监管负责人:" + stringResult2.getJgZrr());
                                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_phone2)).setText("电话:" + stringResult2.getJgZrrLxdh());
                                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_name3)).setText("属地水务部门负责人:" + stringResult2.getSwbmZrr());
                                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_phone3)).setText("电话:" + stringResult2.getSwbmZrrLxdh());
                                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_name4)).setText("技术负责人:" + stringResult2.getJsZrr());
                                    ((TextView) map_bottom_sheet.findViewById(R.id.tv_phone4)).setText("电话:" + stringResult2.getJsZrrLxdh());
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 检测是否有同区的人已经上报过(校核过)
     *
     * @param component
     */
    private void checkIfHasBeenUploadForComponent(Component component) {
        if (component.getGraphic() != null && component.getGraphic().getAttributes() != null
                && component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.OBJECTID) != null) {
            String checkPersonName = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.OPERATER), "");
            String time = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.SYNCTIME), "");
            if (StringUtil.isEmpty(checkPersonName) || StringUtil.isEmpty(time)) {
                //说明没有人上报过，显示“校核”按钮
                //  map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.VISIBLE);
                ll_check_hint.setVisibility(View.GONE);
                status = "2";
                person = "";
            } else {
                if (BaseInfoManager.getUserName(mContext).equals(checkPersonName)) {
                    checkPersonName = "您";
                }
                ll_check_hint.setVisibility(View.VISIBLE);
                tv_check_person.setVisibility(View.VISIBLE);
                String date = TimeUtil.getStringTimeYMD(new Date(Long.valueOf(time)));
                tv_check_person.setText(checkPersonName + "已经在" + date);
                person = checkPersonName;
                tv_check_phone.setVisibility(View.VISIBLE);
                String handle = "更新";
                tv_check_phone.setText(handle);
                tv_check_hint.setText("过该设施");
            }
        }

    }

    /**
     * 设置责任人
     *
     * @param component
     */
    private void setResponsible(Component component, int type) {
        Log.d("检测责任人", "setResponsible 被调用");
        if (component.getGraphic() != null && component.getGraphic().getAttributes() != null
                && component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.OBJECTID) != null) {
            //通过usid去请求接口是否已经有同区的人报过
            String objectId = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.OBJECTID), "");
            CorrectFacilityService correctFacilityService = new CorrectFacilityService(mContext.getApplicationContext());
            correctFacilityService.getGwyxtLastRecord(objectId, type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<CheckResult>() {

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(CheckResult checkResult) {
                            // 设置负责人 当GyZrr没有值时三个责任人都没有数据
                            if (TextUtils.isEmpty(checkResult.getGyZrr())) {
                                map_bottom_sheet.findViewById(R.id.ll_name_phone).setVisibility(View.GONE);
                                map_bottom_sheet.findViewById(R.id.tv_no_data).setVisibility(View.VISIBLE);
                            } else {
                                map_bottom_sheet.findViewById(R.id.ll_name_phone).setVisibility(View.VISIBLE);
                                map_bottom_sheet.findViewById(R.id.tv_no_data).setVisibility(View.GONE);
                                ((TextView) map_bottom_sheet.findViewById(R.id.tv_name)).setText("管养负责人:" + checkResult.getGyZrr());
                                ((TextView) map_bottom_sheet.findViewById(R.id.tv_phone)).setText("电话:" + checkResult.getGyZrrLxdh());
                                ((TextView) map_bottom_sheet.findViewById(R.id.tv_name2)).setText("监管负责人:" + checkResult.getJgZrr());
                                ((TextView) map_bottom_sheet.findViewById(R.id.tv_phone2)).setText("电话:" + checkResult.getJgZrrLxdh());
                                ((TextView) map_bottom_sheet.findViewById(R.id.tv_name3)).setText("属地水务部门负责人:" + checkResult.getSwbmZrr());
                                ((TextView) map_bottom_sheet.findViewById(R.id.tv_phone3)).setText("电话:" + checkResult.getSwbmZrrLxdh());
                                ((TextView) map_bottom_sheet.findViewById(R.id.tv_name4)).setText("技术负责人:" + checkResult.getJsZrr());
                                ((TextView) map_bottom_sheet.findViewById(R.id.tv_phone4)).setText("电话:" + checkResult.getJsZrrLxdh());
                            }
                        }
                    });
        }
    }

    private void initStreamGLayer() {
        if (mStreamGLayer == null) {
            mStreamGLayer = new GraphicsLayer();
            mMapView.addLayer(mStreamGLayer);
        } else {
            mStreamGLayer.removeAll();
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

    private void initValue(Component component, TextView tv_gc_begin, String name, String key) {
        if (tv_gc_begin != null) {
            tv_gc_begin.setVisibility(View.VISIBLE);
        }
        String value = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(key), "");
        if (StringUtil.isEmpty(value)) {
            tv_gc_begin.setText(name);
            return;
        }
        String format = name + formatToSecond(value, "m", 2);
        tv_gc_begin.setText(format);
    }

    private void initValue(Component component, TextView tv_gc_begin, String name, String key, int newScale, String unit) {
        if (tv_gc_begin != null) {
            tv_gc_begin.setVisibility(View.VISIBLE);
        }
        String value = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(key), "");
        String gjName = "";
        if (!StringUtil.isEmpty(value) && ComponentFieldKeyConstant.PIPE_GJ.equals(key)) {
            TableDBService dbService = new TableDBService(mContext);
            List<DictionaryItem> a205 = dbService.getDictionaryByTypecodeInDB("A205");
            for (DictionaryItem dictionaryItem : a205) {
                if (dictionaryItem.getCode().equals(value)) {
                    gjName = dictionaryItem.getName();
                }
            }
        }
        if (StringUtil.isEmpty(value)) {
            tv_gc_begin.setText(name);
            return;
        }
        if (ComponentFieldKeyConstant.PIPE_GJ.equals(key)) {
            tv_gc_begin.setText(name + gjName + unit);
            return;
        } else if (newScale == 0) {
            tv_gc_begin.setText(name + value + unit);
            return;
        }
        String format = name + formatToSecond(value, unit, newScale);
        tv_gc_begin.setText(format);
    }

    private String formatToSecond(String value, String unit, int newScale) {
        if (StringUtil.isEmpty(value)) {
            return "";
        }
        Double num = Double.valueOf(value);
        BigDecimal bd = new BigDecimal(num);
        num = bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return num + "" + unit;
    }

    /**
     * 去掉中心城区的设施名称
     *
     * @param oldLayerName
     * @return
     */
    private String getLayerName(String oldLayerName) {
        if (!attribute.contains(oldLayerName))
            return "";
        if (oldLayerName.contains("窨井")) {
            return "窨井";
        } else if (oldLayerName.contains("排水管道")) {
            return "排水管道";
        } else if (oldLayerName.contains("雨水口")) {
            return "雨水口";
        } else if (oldLayerName.contains("排水口")) {
            return "排水口";
        } else if (oldLayerName.contains("排水沟渠")) {
            return "排水沟渠";
        } else if (oldLayerName.equals("存疑区域")) {
            return "存疑区域";
        } else {
            return "";
        }
    }

    private void deletePipe() {
        pd = new ProgressDialog(getContext());
        pd.setMessage("删除中.....");
        pd.show();
        String dataJson = JsonUtil.getJson(currentStream);
        mUploadFacilityService.deletePipe(dataJson)
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
                            resetAll();
                            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new RefreshMyModificationListEvent());
                        } else {
//                            CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
//                                    BaseInfoManager.getUserName(mContext) + "原因是：" + s.getMessage()));
                            Toast.makeText(mContext, s.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void drawStream(Component component, List<PipeBean> pipeBeans, int color) {
        initArrowGLayer();
        if (ListUtil.isEmpty(pipeBeans)) {
            return;
        }
        if (component.getGraphic().getGeometry() instanceof Point) {
            Point componentCenter = GeometryUtil.getGeometryCenter(component.getGraphic().getGeometry());
            for (PipeBean upStream : pipeBeans) {
                Polyline polyline = new Polyline();
                Point geometryCenter = new Point(upStream.getX(), upStream.getY());
                if (geometryCenter != null) {
                    polyline.startPath(geometryCenter);
                    polyline.lineTo(GeometryUtil.getGeometryCenter(component.getGraphic().getGeometry()));
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

    private void showPopWindow(final Component component, final String layerName, boolean isLine) {
        String pipeType = "";
        String direction = "";
        String pipeGJ = "";
        String gjName = "";
        String length = "";
        boolean isFacilityOfRedLine = false;
        TableDBService dbService = new TableDBService(mContext);
        List<DictionaryItem> a163 = dbService.getDictionaryByTypecodeInDB("A163");
        if (component != null && component.getGraphic() != null) {
            pipeType = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("SORT"), "");
            direction = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("DIRECTION"), "");
            pipeGJ = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("PIPE_GJ"), "");
            length = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("LINE_LENGTH"), "");
            isFacilityOfRedLine = "是".equals(StringUtil.getNotNullString(component.getGraphic().getAttributeValue("sfpsdyhxn".toUpperCase()), "否"));
        }
        final Map<String, Object> spinnerData = new LinkedHashMap<>();
        List<String> allowValues = new ArrayList<>();
        for (DictionaryItem dictionaryItem : a163) {
            allowValues.add(dictionaryItem.getName());
            spinnerData.put(dictionaryItem.getName(), dictionaryItem);
        }
        final Map<String, Object> spinnerData1 = new LinkedHashMap<>();
        spinnerData1.put("正向", "正向");
        spinnerData1.put("反向", "反向");
        List<DictionaryItem> a205 = dbService.getDictionaryByTypecodeInDB("A205");
        Collections.sort(a205, new Comparator<DictionaryItem>() {
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

        final Map<String, Object> gjData = new LinkedHashMap<>();
        List<String> gjValues = new ArrayList<>();
        for (DictionaryItem dictionaryItem : a205) {
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
                    drawStream(componentCenter, mTempPipeBeans, getResources().getColor(R.color.agmobile_red));
                }
            }
        });

        sp_gj = (SpinnerTableItem) mView.findViewById(R.id.sp_gj);
        sp_gj.setOnSpinnerChangeListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                DictionaryItem dictionaryItem = (DictionaryItem) item;
                mGjCode = dictionaryItem.getCode();
            }
        });
        sp_gj.setSpinnerData(gjData);
        if (!StringUtil.isEmpty(direction)) {
            sp_lx.selectItem(direction);
        } else {
            sp_lx.selectItem(0);
        }
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
                    if (mStatus == 1) {
                        currentStream.setPipeType(sp_ywlx.getText());
                        currentStream.setDirection(sp_lx.getText());
                        currentStream.setPipeGj(mGjCode);
                        List<PipeBean> children = new ArrayList<>();
                        currentStream.setSfpsdyhxn(cb_isFacilityOfRedLine.isCheck() ? "是" : "否");
                        if (StringUtil.isEmpty(sp_gxcd.getText().trim())) {
                            ToastUtil.shortToast(mContext, "管线长度不能为空");
                            return;
                        }
                        mLineLength = Double.valueOf(sp_gxcd.getText().trim());
                        //提交
                        if ("排水管道".equals(layerName)) {//修改排水管道属性
                            currentStream.setPipeType(sp_ywlx.getText());
                            currentStream.setDirection(sp_lx.getText());
                            currentStream.setPipeGj(mGjCode);
                            currentStream.setChildren(children);
                            updateLinePipe(pipe_view, currentStream);
                        } else {
                            //新增排水管道
                            children.add(currentStream);
                            originPipe.setChildren(children);
                            mSecondAttribute = sp_ywlx.getText();
                            showDialog(pipe_view, originPipe);
                        }
                        return;
                    } else if (mStatus == 2) {
                        currentStream.setPipeType(sp_ywlx.getText());
                        currentStream.setDirection(sp_lx.getText());
                        currentStream.setPipeGj(mGjCode);
                        currentStream.setSfpsdyhxn(cb_isFacilityOfRedLine.isCheck() ? "是" : "否");
                        mAllPipeBeans.add(currentStream);
                        mPipeStreamView.addData(mAllPipeBeans);
                        setMode(mStatus);
//                        showAttributeForStream(true);
                    }
                    initArrowGLayer();
                    hideBottomSheet();
                    drawStream(componentCenter, mAllPipeBeans, getResources().getColor(R.color.agmobile_red));
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
        cb_isFacilityOfRedLine = mView.findViewById(R.id.cb_isFacilityOfRedLine);
        if (cb_isFacilityOfRedLine != null) {
            cb_isFacilityOfRedLine.setCheck(isFacilityOfRedLine);
        }
        pipe_view.setVisibility(View.VISIBLE);
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
                    if (isLoading) {
                        whileTake = false;
                    }
                    if (isDrawStream) {
                        return true;
                    }
                    locationMarker.setVisibility(View.VISIBLE);
                    if (mStatus == 1) {
                        initStreamGLayer();
                    } else if (mStatus == 0) {
                        initGLayer();
                        initSelectGLayer();
                        initGraphicSelectGLayer();
                    } else if (mStatus == 5 || mStatus == 7) {
                        initSelectGLayer();
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
                        isClick = false;
                        if (isJHJWell) {
                            queryPshInfo(x, y);
                        } else {
                            query(x, y);
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

    private void cancelDoubt(String id, final TextView tv_zhiyi) {
        mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("取消存疑中.....");
        mDialog.show();
        if (mUploadLayerService == null) {
            mUploadFacilityService = new UploadFacilityService(mContext);
        }
        mUploadFacilityService.partsDoubt(id)
                //获取列表（不包含附件）
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result2<String>>() {
                    @Override
                    public void onCompleted() {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        ToastUtil.iconShortToast(mContext, R.mipmap.ic_alert_yellow, "存疑取消失败，请稍后重试");
                    }

                    @Override
                    public void onNext(Result2<String> s) {
                        if (s.getCode() == 200) {
//                            if(tv_zhiyi != null){
//                                tv_zhiyi.setText("   存疑   ");
//                            }
                            initGLayer();
                            hideBottomSheet();
                            refreshMap();
                            Toast.makeText(mContext, "存疑取消成功", Toast.LENGTH_SHORT).show();
                        } else {
//                            CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
//                                    BaseInfoManager.getUserName(mContext) + "原因是：" + s.getMessage()));
                            Toast.makeText(mContext, s.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void showDoubtDialog(final Component component, final Geometry geometry, final TextView tv_zhiyi) {
        View view = View.inflate(mContext, R.layout.activity_dialog_view, null);
        final TextFieldTableItem fieldTableItem = (TextFieldTableItem) view.findViewById(R.id.textitem_description);
        final AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("请填写存疑原因")
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (StringUtil.isEmpty(fieldTableItem.getText().trim())) {
                            ToastUtil.shortToast(mContext, "请填写存疑原因");
                            return;
                        }
                        DoubtBean doubtBean = createDoubt(component, geometry, fieldTableItem.getText().trim());
                        uploadDoubt(component, doubtBean, dialog, tv_zhiyi);
                    }
                });
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void resetInfoLayout() {
        ((LinearLayout.LayoutParams) llInfo.getLayoutParams()).leftMargin = 0;
        llInfo.requestLayout();
    }

    //中心城区接驳井
    private void showViewForCentralWell(final Component component) {
        if (mPsdyJbjs == null) mPsdyJbjs = new ArrayList<>();
        if (mIdentificationService == null) {
            mIdentificationService = new CorrectFacilityService(mContext.getApplicationContext());
        }
        final TextView psdy_delete = (TextView) map_bottom_sheet.findViewById(R.id.psdy_delete);
        psdy_delete.setVisibility(View.GONE);
        final TextView tv_cant_hook = (TextView) map_bottom_sheet.findViewById(R.id.tv_not_hook);
        tv_cant_hook.setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_line_del).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_jc).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.ll_gc).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_gjdy).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.line2).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line3).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line4).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line5).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line2).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line3).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line4).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_zhiyi).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_detail_left).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_detail_right).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_sure).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.ll_check_hint).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_errorinfo).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_errorinfo).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_ms_end).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_tycd).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_pd).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.btn_container).setVisibility(View.VISIBLE);


        Map<String, Object> attributes = component.getGraphic().getAttributes();
        TextView title = (TextView) map_bottom_sheet.findViewById(R.id.title);
        title.setText(String.valueOf(component.getLayerName()).replace(".", ""));
        TextView tv_error_correct = (TextView) map_bottom_sheet.findViewById(R.id.tv_error_correct);
        map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.VISIBLE);
        tv_error_correct.setVisibility(View.GONE);
        TextView sort = (TextView) map_bottom_sheet.findViewById(R.id.sort);
        sort.setVisibility(View.VISIBLE);
//        sort.setText(String.valueOf(attributes.get("SORT")));
        initValue(component, sort, "", ComponentFieldKeyConstant.SORT, 0, "");

        TextView subtype = (TextView) map_bottom_sheet.findViewById(R.id.subtype);
        initValue(component, subtype, "", ComponentFieldKeyConstant.SUBTYPE, 0, "");
//        // 接驳井不显示 挂接单元 和 删除挂接 功能
//        final int show = "接驳井".equals(subtype.getText().toString()) ? View.GONE : View.VISIBLE;
//        map_bottom_sheet.findViewById(R.id.tv_gjdy).setVisibility(show);
//        map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(show);

        map_bottom_sheet.findViewById(R.id.line).setVisibility(View.VISIBLE);
//        subtype.setText("接驳井");
        TextView field3Tv = (TextView) map_bottom_sheet.findViewById(R.id.field3);
        TextView tv_cz = (TextView) map_bottom_sheet.findViewById(R.id.tv_cz);
        field3Tv.setVisibility(View.VISIBLE);
        tv_cz.setVisibility(View.GONE);
//        field3Tv.setText("材质："+String.valueOf(attributes.get("MATERIAL")));
        initValue(component, field3Tv, "材质：", ComponentFieldKeyConstant.MATERIAL, 0, "");
        // 这个 “材质” 不要了
        field3Tv.setVisibility(View.GONE);
        TextView tv_gj = (TextView) map_bottom_sheet.findViewById(R.id.tv_gj);
        tv_gj.setVisibility(View.VISIBLE);
//        tv_gj.setText("管理单位："+String.valueOf(attributes.get("MANAGEDEPT")));
        initValue(component, tv_gj, "管理单位：", "MANAGEDEPT", 0, "");
        TextView tv_ms_begin = (TextView) map_bottom_sheet.findViewById(R.id.tv_ms_begin);
        tv_ms_begin.setVisibility(View.VISIBLE);
        TextView addr = (TextView) map_bottom_sheet.findViewById(R.id.addr);
        addr.setVisibility(View.VISIBLE);
        initValue(component, addr, "所在位置：", ComponentFieldKeyConstant.ADDR, 0, "");
        initValue(component, tv_ms_begin, "所在道路：", ComponentFieldKeyConstant.LANE_WAY, 0, "");
//        addr.setText(String.valueOf(attributes.get("ADDR")));
//        TextView tv_gc_begin = (TextView) map_bottom_sheet.findViewById(R.id.tv_gc_begin);
//        tv_gc_begin.setVisibility(View.VISIBLE);
//        TextView tv_gc_end = (TextView) map_bottom_sheet.findViewById(R.id.tv_gc_end);
//        tv_gc_end.setVisibility(View.VISIBLE);
//        map_bottom_sheet.findViewById(R.id.addr).setVisibility(View.GONE);
//        map_bottom_sheet.findViewById(R.id.subtype).setVisibility(View.GONE);
//        map_bottom_sheet.findViewById(R.id.tv_gj).setVisibility(View.GONE);
//        map_bottom_sheet.findViewById(R.id.tv_cz).setVisibility(View.GONE);
//        map_bottom_sheet.findViewById(R.id.tv_gc_begin).setVisibility(View.GONE);
//        map_bottom_sheet.findViewById(R.id.tv_gc_end).setVisibility(View.GONE);
//        btn_next.setVisibility(View.VISIBLE);
//        btn_prev.setVisibility(View.VISIBLE);
        if (!ifInDY) {
            String attrOne = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.SUBTYPE), "");
            if ("接户井".equals(attrOne)) {
                isJHJWell = true;
            } else {
                isJHJWell = false;
            }
            String usId = attributes.get("USID") + "";
            String objectId = attributes.get("OBJECTID") + "";
//            if (show == View.VISIBLE) {
            getGjInfo(psdy_delete, usId, objectId, null);
//            } else {
//                getGjInfo(null, usId, objectId, null);
//            }
            if (isJHJWell) {
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_gjdy)).setText("挂接排水户");
            } else {
                ((TextView) map_bottom_sheet.findViewById(R.id.tv_gjdy)).setText("挂接单元");
            }

        } else {
            map_bottom_sheet.findViewById(R.id.psdy_jc).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.ll_gc).setVisibility(View.GONE);
            ((TextView) map_bottom_sheet.findViewById(R.id.tv_gjdy)).setText("确认挂接");
            map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
        }
        map_bottom_sheet.findViewById(R.id.tv_gjdy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tin;
                if (!ifInDY) {
                    ifInGuaJie = true;
                    if (isJHJWell) {
                        addTopBarView("选择排水户挂接");
                        tin = "请移动地图选择排水户";
                    } else {
                        addTopBarView("选择排水单元挂接");
                        tin = "请移动地图选择排水单元";
                    }
//                    addTopBarView("选择排水单元挂接");
                    mStatus = 5;
                    setMode(mStatus);
                    ToastUtil.shortToast(mContext, tin);
                    locationMarker.changeIcon(R.mipmap.ic_check_stream);
                    if (locationMarker != null) {
                        locationMarker.changeIcon(R.mipmap.ic_check_related);
                        locationMarker.setVisibility(View.VISIBLE);
                    }
                    tv_query_tip.setText(tin);
                    initStreamGLayer();
                    hideBottomSheet();
                    setSearchFacilityListener();
                } else {
                    showJBJDialog(null, component);
                }
            }
        });
        map_bottom_sheet.findViewById(R.id.psdy_jc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent   intent = new Intent(getActivity(), WellMonitorActivity.class);
//                intent.putExtra("objectid",String.valueOf(component.getGraphic().getAttributeValue("OBJECTID")));
//                intent.putExtra("usid",String.valueOf(component.getGraphic().getAttributeValue("USID")));
//                intent.putExtra("type",String.valueOf(component.getGraphic().getAttributeValue("SORT")));
//                intent.putExtra("X",String.valueOf(component.getGraphic().getAttributeValue("X")));
//                intent.putExtra("Y",String.valueOf(component.getGraphic().getAttributeValue("Y")));
//                startActivity(intent);
                Intent intent = new Intent(getActivity(), WellMonitorListActivity.class);
                intent.putExtra("objectid", String.valueOf(component.getGraphic().getAttributeValue("OBJECTID")));
                intent.putExtra("usid", String.valueOf(component.getGraphic().getAttributeValue("USID")));
                intent.putExtra("type", String.valueOf(component.getGraphic().getAttributeValue("SORT")));
                intent.putExtra("X", String.valueOf(component.getGraphic().getAttributeValue("X")));
                intent.putExtra("Y", String.valueOf(component.getGraphic().getAttributeValue("Y")));
                startActivity(intent);
            }
        });
        final String type = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("LAYER_NAME"), "");
        map_bottom_sheet.findViewById(R.id.tv_error_correct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Object o = component.getGraphic().getAttributes().get(UploadLayerFieldKeyConstant.REPORT_TYPE);
//                if (o != null) {
//                    if (UploadLayerFieldKeyConstant.ADD.equals(o.toString())) {
                //新增
                Object o = component.getGraphic().getAttributes().get(UploadLayerFieldKeyConstant.REPORT_TYPE);
                if (o != null) {
                    if (UploadLayerFieldKeyConstant.ADD.equals(o.toString())) {
                        //新增
                        UploadedFacility uploadedFacility = getUploadedFacilityFromGraphic(component.getGraphic().getAttributes(), component.getGraphic().getGeometry());
                        Intent intent = new Intent(mContext, ReEditWellFacilityActivity.class);
                        intent.putExtra("isLoad", true);
                        intent.putExtra("data", (Parcelable) uploadedFacility);
                        startActivity(intent);
                        return;
                    }
                }
                if ("0".equals(status)) {
                    //说明有人上报过，那么要提示
                    DialogUtil.MessageBox(mContext, "提醒", "        " + person + "已经校核过该设施，请确定是否仍要校核该设施？",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    btn_check.setVisibility(View.VISIBLE);
                                    btn_check_cancel.setVisibility(View.GONE);
                                    Intent intent = new Intent(mContext, CorrectOrConfirimWellActivity.class);
                                    if (StringUtil.isEmpty(component.getLayerName())) {
                                        if (!StringUtil.isEmpty(type)) {
                                            component.setLayerName(type);
                                        }
                                    }
                                    intent.putExtra("component", component);
                                    //从此处进入校核界面的话允许用户修改窨井类型
                                    intent.putExtra("isAllowEditWellType", true);
                                    startActivityForResult(intent, 123);
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                } else {
                    btn_check.setVisibility(View.VISIBLE);
                    btn_check_cancel.setVisibility(View.GONE);
                    Intent intent = new Intent(mContext, CorrectOrConfirimWellActivity.class);
                    if (StringUtil.isEmpty(component.getLayerName())) {
                        if (!StringUtil.isEmpty(type)) {
                            component.setLayerName(type);
                        }
                    }
                    intent.putExtra("component", component);
                    //从此处进入校核界面的话允许用户修改窨井类型
                    intent.putExtra("isAllowEditWellType", true);
                    startActivityForResult(intent, 123);
                }
//                    }
//                }
//                if ("0".equals(status)) {
//                    //说明有人上报过，那么要提示
//                    DialogUtil.MessageBox(mContext, "提醒", "        " + person + "已经校核过该设施，请确定是否仍要校核该设施？",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    btn_check.setVisibility(View.VISIBLE);
//                                    btn_check_cancel.setVisibility(View.GONE);
//                                    Intent intent = new Intent(mContext, CorrectOrConfirimWellActivity.class);
//                                    if (StringUtil.isEmpty(component.getLayerName())) {
//                                        component.setLayerName("窨井");
//                                    }
//                                    intent.putExtra("component", component);
//                                    //从此处进入校核界面的话允许用户修改窨井类型
//                                    intent.putExtra("isAllowEditWellType", true);
//                                    startActivityForResult(intent, 123);
//                                }
//                            }, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            });
//                } else {
//                    btn_check.setVisibility(View.VISIBLE);
//                    btn_check_cancel.setVisibility(View.GONE);
//                    Intent intent = new Intent(mContext, CorrectOrConfirimWellFacilityActivity.class);
//                    if (StringUtil.isEmpty(component.getLayerName())) {
//                        component.setLayerName("窨井");
//                    }
//                    intent.putExtra("component", component);
//                    //从此处进入校核界面的话允许用户修改窨井类型
//                    intent.putExtra("isAllowEditWellType", true);
//                    startActivityForResult(intent, 123);
//                }
//
//            }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                map_bottom_sheet.setVisibility(View.VISIBLE);
                mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);
                resetInfoLayout();
            }
        }, 300);

    }

    /**
     * 生成筛选后的门牌号图层，并加载到地图上去
     *
     * @param component
     */
    private void addPsdyMph(Component component) {
        synchronized (this) {
            String objectId = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("OBJECTID"), "");
            if (mPsdyLayerFactory == null) {
                mPsdyLayerFactory = new PsdyLayerFactory(objectId, mContext);
            } else {
                mPsdyLayerFactory.setObjectId(objectId);
            }
            LayerInfo pshMp = getPshMpInfo();
            Layer layer = mPsdyLayerFactory.getLayer(mContext, pshMp);
            mIndex = mMapView.addLayer(layer);
        }
    }

    /**
     * 清空门牌号图层
     */
    private void removePsdyMph() {
        synchronized (this) {
            if (mIndex != -1) {
                mMapView.removeLayer(mIndex);
                mIndex = -1;
            }
        }

    }

    /**
     * 展示排水户列表
     */
    private void showSewerageUserBottomSheet(Component component) {
        mSewerageUserListView.setCurrentComponent(component);
//        mSewerageUserListView.setVisibility(View.VISIBLE);
//        mSwerageUserListBehavoir.setState(BottomSheetBehavior.STATE_EXPANDED);
        mSewerageUserListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSewerageUserListView.setVisibility(View.VISIBLE);
                mSwerageUserListBehavoir.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }, 300);
    }

    /**
     * 隐藏排水户列表
     */
    private void dismissSewerageUserBottomSheet() {
        mSwerageUserListBehavoir.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mSewerageUserListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSewerageUserListView.setVisibility(View.INVISIBLE);
//                ViewGroup.LayoutParams layoutParams = mSewerageUserListView.getLayoutParams();
//                layoutParams.height = 0;
//                mSewerageUserListView.setLayoutParams(layoutParams);
            }
        }, 300);
    }

    //排水单元
    private void showViewForPSDY(final Component component) {
        ifInDY = true;
        currentDYComponent = component;
        tvResponsible.setVisibility(View.GONE);
        if (mPsdyJbjs == null) mPsdyJbjs = new ArrayList<>();
        if (mIdentificationService == null) {
            mIdentificationService = new CorrectFacilityService(mContext.getApplicationContext());
        }
        final TextView psdy_delete = (TextView) map_bottom_sheet.findViewById(R.id.psdy_delete);
        psdy_delete.setVisibility(View.GONE);
        View psdy_line_del = map_bottom_sheet.findViewById(R.id.psdy_line_del);
        psdy_line_del.setVisibility(View.GONE);

        //屏蔽监测按钮
        map_bottom_sheet.findViewById(R.id.psdy_jc).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_line_jc).setVisibility(View.GONE);
        ((TextView) map_bottom_sheet.findViewById(R.id.tv_gjdy)).setText(" 挂接井 ");
        map_bottom_sheet.findViewById(R.id.tv_gjdy).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.tv_swerage_user).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line_swerage_user).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.INVISIBLE);
        map_bottom_sheet.findViewById(R.id.ll_gc).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line2).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line3).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line4).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line5).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line2).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line3).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line4).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_zhiyi).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_detail_left).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_sure).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.ll_check_hint).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_errorinfo).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_errorinfo).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_ms_end).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_tycd).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_pd).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.btn_container).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.tv_check_drainage_user).setVisibility(View.VISIBLE);
//        map_bottom_sheet.findViewById(R.id.line_check_drainage_user).setVisibility(View.VISIBLE);

        Map<String, Object> attributes = component.getGraphic().getAttributes();
        TextView title = (TextView) map_bottom_sheet.findViewById(R.id.title);
        //无法挂接按钮
        final TextView tv_not_hook = (TextView) map_bottom_sheet.findViewById(R.id.tv_not_hook);
        tv_not_hook.setVisibility(View.VISIBLE);
        final TextView tv_reason = (TextView) map_bottom_sheet.findViewById(R.id.reason);
        final String type = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.WFGJ), "");
        final String reason = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.WFGJ_REASON), "");
        if ("1".equals(type)) {
            tv_not_hook.setText("  取消无法挂接  ");
            tv_reason.setVisibility(View.VISIBLE);
            tv_not_hook.setBackgroundResource(R.drawable.sel_btn_delete);
        } else {
            tv_not_hook.setText("无法挂接");
            tv_reason.setVisibility(View.GONE);
            tv_not_hook.setBackgroundResource(R.drawable.round_blue_rectangle);
        }
//        if(StringUtil.isEmpty(reason)){
//            tv_reason.setVisibility(View.GONE);
//        }else{
//
//        }
        title.setText("排水单元");
        TextView tv_error_correct = (TextView) map_bottom_sheet.findViewById(R.id.tv_error_correct);
        tv_error_correct.setVisibility(View.GONE);
        TextView sort = (TextView) map_bottom_sheet.findViewById(R.id.sort);
        sort.setVisibility(View.GONE);
//        sort.setText(String.valueOf(attributes.get("SORT")));
        initValue(component, sort, "", ComponentFieldKeyConstant.SORT, 0, "");
        TextView subtype = (TextView) map_bottom_sheet.findViewById(R.id.subtype);
        subtype.setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.line).setVisibility(View.GONE);
        initValue(component, subtype, "单元名称：", "单元名称", 0, "");
        TextView field3Tv = (TextView) map_bottom_sheet.findViewById(R.id.field3);
        TextView tv_cz = (TextView) map_bottom_sheet.findViewById(R.id.tv_cz);
        field3Tv.setVisibility(View.GONE);
        tv_cz.setVisibility(View.GONE);
        TextView tv_gj = (TextView) map_bottom_sheet.findViewById(R.id.tv_gj);
        tv_gj.setVisibility(View.VISIBLE);
//        tv_gj.setText("管理单位："+String.valueOf(attributes.get("MANAGEDEPT")));
        initValue(component, tv_gj, "类型：", "类型LX", 0, "");
        TextView tv_ms_begin = (TextView) map_bottom_sheet.findViewById(R.id.tv_ms_begin);
        tv_ms_begin.setVisibility(View.VISIBLE);
        initValue(component, tv_ms_begin, "所属区：", "所属区", 0, "");
        TextView addr = (TextView) map_bottom_sheet.findViewById(R.id.addr);
        addr.setVisibility(View.VISIBLE);
        initValue(component, addr, "地址：", "地址DZ", 0, "");
        if ("1".equals(type)) {
            initValue(component, tv_reason, "无法挂接原因：", ComponentFieldKeyConstant.WFGJ_REASON, 0, "");
        }
        String objectId = attributes.get("OBJECTID") + "";
        // 加上这个判断，如果是在查询排水户的情况下，不显示 删除挂接 按钮
        if (!ifInSwerageUserMode) {
            getGjInfo(psdy_delete, null, null, objectId);
            tv_not_hook.setVisibility(View.VISIBLE);
        } else {
            tv_not_hook.setVisibility(View.GONE);
        }

        map_bottom_sheet.findViewById(R.id.tv_gjdy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTopBarView("选择接驳井挂接");
                mStatus = 7;
                setMode(mStatus);
                ToastUtil.shortToast(mContext, "请移动地图选择接驳井");
                locationMarker.changeIcon(R.mipmap.ic_check_stream);
                if (locationMarker != null) {
                    locationMarker.changeIcon(R.mipmap.ic_check_related);
                    locationMarker.setVisibility(View.VISIBLE);
                }
                tv_query_tip.setText("请移动地图选择接驳井");
                initStreamGLayer();
                hideBottomSheet();
                setSearchFacilityListener();
            }
        });

        tv_not_hook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(type)) {
                    //取消无法挂接
                    DialogUtil.MessageBox(mContext, "提示", "确定取消该排水单元的无法挂接状态？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String id = StringUtil.getNotNullString(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.OBJECTID), "");
                            String reason = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.WFGJ_REASON), "");
                            cancelNotHook(id, reason);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                } else {
                    //提交无法挂接原因
                    showNotHookDialog(component, component.getGraphic().getGeometry(), tv_not_hook);
                }
            }
        });

        map_bottom_sheet.findViewById(R.id.tv_check_drainage_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 查看排水户列表
                layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_USER, false);
                removePsdyMph();
                addPsdyMph(component);
                hideBottomSheet();
                showSewerageUserBottomSheet(component);
            }
        });

        showSewerageUserBtnOnly(component);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                map_bottom_sheet.setVisibility(View.VISIBLE);
                mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);
                resetInfoLayout();
            }
        }, 300);

    }

    //排水单元
    private void showViewForPSH(final Component component) {
        currentDYComponent = component;
        map_bottom_sheet.findViewById(R.id.ll_check_hint).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.psdy_delete).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.psdy_line_del).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.link_to_jhj).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.line_link_to_jhj).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.tvLevel2).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.line_tvLevel2).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.tvDetail).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.tv_right_up_tip).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.tv_right_up).setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.sort).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.ll_gj).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.ll_gc).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.ll_msd).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.ll_gq).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_responsible).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_not_hook).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_errorinfo).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);

        final Graphic graphic = component.getGraphic();
        final DrainageUserBean bean = DrainageUserBean.parseFromGraphic(graphic);
        currDrainageUserBean = bean;
        ((TextView) map_bottom_sheet.findViewById(R.id.title)).setText("排水户名称：" + bean.getName());
        final TextView status = (TextView) map_bottom_sheet.findViewById(R.id.tv_right_up);
        status.setVisibility(View.VISIBLE);
        if ("2".equals(bean.getState())) {
            status.setText("已审核");
            status.setTextColor(Color.parseColor("#3EA500"));
        } else if ("1".equals(bean.getState())) {
            status.setText("未审核");
            status.setTextColor(Color.parseColor("#FFFF0000"));
        } else if ("0".equals(bean.getState())) {
            status.setText("已注销");
            status.setTextColor(Color.parseColor("#b1afab"));
        } else if ("3".equals(bean.getState())) {
            status.setText("存在疑问");
            status.setTextColor(Color.parseColor("#FFFFC248"));
        } else if ("4".equals(bean.getState())) {
            status.setText("暂存");
            status.setTextColor(Color.parseColor("#FFFFC248"));
        } else if ("5".equals(bean.getState())) {
            status.setText("编辑");
            status.setTextColor(Color.parseColor("#FFFFC248"));
        }

        ((TextView) map_bottom_sheet.findViewById(R.id.subtype)).setText("行业类别：" + bean.getType());
        ((TextView) map_bottom_sheet.findViewById(R.id.addr)).setText(bean.getAddress());

        map_bottom_sheet.findViewById(R.id.psdy_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 删除接驳井和接户井连线
                // 先搜索所有的连线
                // 接着再选择并删除井连线
//                queryAllHangUpContents(currDrainageUserBean.getId());
                addTopBarView("删除接驳井连线");
                mStatus = 6;
                setMode(mStatus);
                ToastUtil.shortToast(mContext, "请选择连线");
                locationMarker.setVisibility(View.GONE);
                tv_query_tip.setText("请选择要删除的接驳井连线");
//                                        initStreamGLayer();
                hideBottomSheet();
                mGLayer.clearSelection();
                mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                ifInDeletePshAndJhjLineMode = true;
            }
        });
        map_bottom_sheet.findViewById(R.id.link_to_jhj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hangUpWell(currDrainageUserBean, -1);
            }
        });
        map_bottom_sheet.findViewById(R.id.tvLevel2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrainageUserBean drainageUserBean = currDrainageUserBean;
                PSHHouse pshHouse = DrainageUserBean.cover2PshHouse(drainageUserBean);
                Intent intent = new Intent(mContext, SecondLevelPshListActivity.class);
                SewerageItemBean.UnitListBean unitListBean = new SewerageItemBean.UnitListBean();
                unitListBean.setId(Integer.valueOf(pshHouse.getId()));
                unitListBean.setMph(pshHouse.getMph());
                unitListBean.setAddr(pshHouse.getAddr());
                unitListBean.setName(pshHouse.getName());
                unitListBean.setPsdyName(drainageUserBean.getPsdyName());
                unitListBean.setPsdyId(drainageUserBean.getPsdyId());
                unitListBean.setX(pshHouse.getX());
                unitListBean.setY(pshHouse.getY());
                intent.putExtra("unitListBean", unitListBean);
                mContext.startActivity(intent);
            }
        });
        map_bottom_sheet.findViewById(R.id.tvDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initGLayer();
                drawGeometry(new Point(bean.getX(), bean.getY()), mGLayer, true, true);
                queryDetailById(bean.getId());
//                resetAll();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                map_bottom_sheet.setVisibility(View.VISIBLE);
                mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);
                resetInfoLayout();
            }
        }, 300);

        queryAllHangUpContents(bean.getId());

    }

    private void showSimpleBottomView(Component component) {
        TextView title = (TextView) map_bottom_sheet.findViewById(R.id.title);
        title.setText(String.valueOf(component.getGraphic().getAttributes().get("NAME")));
        if (tvResponsible != null) {
            tvResponsible.setVisibility(View.GONE);
        }
        map_bottom_sheet.findViewById(R.id.line).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line2).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line3).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line4).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.line5).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line2).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line3).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line4).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_zhiyi).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_detail_left).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_sure).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.ll_check_hint).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_errorinfo).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.sort).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.subtype).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.field3).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_gj).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_cz).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_gc_begin).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_gc_end).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_ms_begin).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_ms_end).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_tycd).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.tv_pd).setVisibility(View.GONE);
        map_bottom_sheet.findViewById(R.id.addr).setVisibility(View.GONE);
        btn_next.setVisibility(View.VISIBLE);
        btn_prev.setVisibility(View.VISIBLE);
        map_bottom_sheet.findViewById(R.id.btn_container).setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                map_bottom_sheet.setVisibility(View.VISIBLE);
                mBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);
                resetInfoLayout();
            }
        }, 300);

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

    private Long objectToLong(Object object) {
        if (object == null) {
            return null;
        }
        return Long.valueOf(object.toString());
    }

    /**
     * 将compone转换成校核实体类
     *
     * @param attribute
     * @param geometry
     * @return
     */
    private ModifiedFacility getModifiedFacilityFromGraphic(Map<String, Object> attribute, Geometry geometry) {
        ModifiedFacility modifiedFacility = new ModifiedFacility();
        modifiedFacility.setObjectId(objectToString(attribute.get("OBJECTID")));
        modifiedFacility.setOriginAddr(objectToString(attribute.get("ORIGIN_ADDR")));
        modifiedFacility.setAddr(objectToString(attribute.get("ADDR")));
        modifiedFacility.setRoad(objectToString(attribute.get("ROAD")));
        modifiedFacility.setAttrFive(objectToString(attribute.get("ATTR_FIVE")));
        modifiedFacility.setAttrFour(objectToString(attribute.get("ATTR_FOUR")));
        modifiedFacility.setAttrThree(objectToString(attribute.get("ATTR_THREE")));
        modifiedFacility.setAttrTwo(objectToString(attribute.get("ATTR_TWO")));
        modifiedFacility.setAttrOne(objectToString(attribute.get("ATTR_ONE")));
        modifiedFacility.setCorrectType(objectToString(attribute.get("CORRECT_TYPE")));
        modifiedFacility.setReportType("delete");
        modifiedFacility.setDescription(objectToString(attribute.get("DESRIPTION")));
        modifiedFacility.setParentOrgName(objectToString(attribute.get("PARENT_ORG_NAME")));
        modifiedFacility.setDirectOrgName(objectToString(attribute.get("DIRECT_ORG_NAME")));
        modifiedFacility.setTeamOrgName(objectToString(attribute.get("TEAM_ORG_NAME")));
        modifiedFacility.setSuperviseOrgName(objectToString(attribute.get("SUPERVISE_ORG_NAME")));
        modifiedFacility.setLayerName(objectToString(attribute.get("LAYER_NAME")));
        modifiedFacility.setUsid(objectToString(attribute.get("US_ID")));
        //修改后的位置
        modifiedFacility.setX(((Point) geometry).getX());
        modifiedFacility.setY(((Point) geometry).getY());
        //原设施位置
        //Point geometryCenter = GeometryUtil.getGeometryCenter(geometry);
        modifiedFacility.setOriginX(objectToDouble(attribute.get("ORGIN_X")));
        modifiedFacility.setOriginY(objectToDouble(attribute.get("ORGIN_Y")));
        modifiedFacility.setMarkPerson(objectToString(attribute.get("MARK_PERSON")));
        modifiedFacility.setMarkTime(objectToLong(attribute.get("MARK_TIME")));
        modifiedFacility.setUpdateTime(objectToLong(attribute.get("UPDATE_TIME")));
        modifiedFacility.setCheckState(objectToString(attribute.get("CHECK_STATE")));
        modifiedFacility.setId(objectToLong(attribute.get(UploadLayerFieldKeyConstant.MARK_ID)));

        //原设施信息
        modifiedFacility.setOriginRoad(objectToString(attribute.get("ORIGIN_ROAD")));
        modifiedFacility.setOriginAttrOne(objectToString(attribute.get("ORIGIN_ATTR_ONE")));
        modifiedFacility.setOriginAttrTwo(objectToString(attribute.get("ORIGIN_ATTR_TWO")));
        modifiedFacility.setOriginAttrThree(objectToString(attribute.get("ORIGIN_ATTR_THREE")));
        modifiedFacility.setOriginAttrFour(objectToString(attribute.get("ORIGIN_ATTR_FOUR")));
        modifiedFacility.setOriginAttrFive(objectToString(attribute.get("ORIGIN_ATTR_FIVE")));

        //设施问题
        modifiedFacility.setpCode(objectToString(attribute.get("PCODE")));
        modifiedFacility.setChildCode(objectToString(attribute.get("CHILD_CODE")));

        //管理状态
        modifiedFacility.setCityVillage(objectToString(attribute.get("CITY_VILLAGE")));
        modifiedFacility.setAttrSix(objectToString(attribute.get("ATTR_SIX")));
        modifiedFacility.setAttrSeven(objectToString(attribute.get("ATTR_SEVEN")));
        modifiedFacility.setRiverx(objectToDouble(attribute.get("RIVERX")));
        modifiedFacility.setRivery(objectToDouble(attribute.get("RIVERY")));
        return modifiedFacility;
    }

    private void deleteModify(final ModifiedFacility modifiedFacility) {
        //数据纠错
        if (mIdentificationService == null) {
            mIdentificationService = new CorrectFacilityService(mContext.getApplicationContext());
        }
        if (pd == null) {
            pd = new ProgressDialog(mContext);
        }
        pd.setMessage("删除中.....");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        mIdentificationService.upload(modifiedFacility)
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
                        CrashReport.postCatchedException(new Exception("核准上报失败，上报用户是：" +
                                BaseInfoManager.getUserName(mContext) + "原因是：" + e.getLocalizedMessage()));
                        ToastUtil.shortToast(mContext, "删除失败");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (responseBody.getCode() == 200) {
                            initGLayer();
                            hideBottomSheet();
                            resetAll();
                            ToastUtil.shortToast(mContext, "删除成功");
//                            uploadJournal(modifiedFacility);
                        } else {
                            CrashReport.postCatchedException(new Exception("核准上报失败，上报用户是：" +
                                    BaseInfoManager.getUserName(mContext) + "原因是：" + responseBody.getMessage()));
                            ToastUtil.shortToast(mContext, "删除失败，原因是：" + responseBody.getMessage());
                        }
                    }
                });
    }

    /**
     * 删除设施
     */
    private void deleteFacility(Long id, final Component component) {
        initDeleteFacilityService();
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("删除中.....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        deleteFacilityService.deleteFacility(UploadLayerFieldKeyConstant.ADD, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result2<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
                                BaseInfoManager.getUserName(mContext) + "原因是：" + e.getLocalizedMessage()));
                        ToastUtil.iconShortToast(mContext, R.mipmap.ic_alert_yellow, "删除失败，请稍后重试");
                    }

                    @Override
                    public void onNext(Result2<String> s) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (s.getCode() == 200) {
                            //清空界面
                            resetAll();
                            initGLayer();
                            hideBottomSheet();
                            refreshMap();
                            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
//                            ModifiedFacility modifiedFacilityFromGraphic = getModifiedFacilityFromGraphic(component.getGraphic().getAttributes(), component.getGraphic().getGeometry());
//                            uploadJournal(modifiedFacilityFromGraphic);
                        } else {
                            CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
                                    BaseInfoManager.getUserName(mContext) + "原因是：" + s.getMessage()));
                            Toast.makeText(mContext, s.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    /**
     * 将compone转换成新增实体类
     *
     * @param attribute
     * @param geometry
     * @return
     */
    private UploadedFacility getUploadedFacilityFromGraphic(Map<String, Object> attribute, Geometry geometry) {
        UploadedFacility uploadedFacility = new UploadedFacility();
        uploadedFacility.setObjectId(objectToString(attribute.get("OBJECTID")));
        uploadedFacility.setAddr(objectToString(attribute.get("ADDR")));
        uploadedFacility.setRoad(objectToString(attribute.get("ROAD")));
        uploadedFacility.setAttrFive(objectToString(attribute.get("ATTR_FIVE")));
        uploadedFacility.setAttrFour(objectToString(attribute.get("ATTR_FOUR")));
        uploadedFacility.setGpbh(objectToString(attribute.get("gpbh")));
        uploadedFacility.setAttrThree(objectToString(attribute.get("ATTR_THREE")));
        uploadedFacility.setAttrTwo(objectToString(attribute.get("ATTR_TWO")));
        uploadedFacility.setAttrOne(objectToString(attribute.get("ATTR_ONE")));
        uploadedFacility.setDescription(objectToString(attribute.get("DESRIPTION")));
        uploadedFacility.setParentOrgName(objectToString(attribute.get("PARENT_ORG_NAME")));
        uploadedFacility.setDirectOrgName(objectToString(attribute.get("DIRECT_ORG_NAME")));
        uploadedFacility.setTeamOrgName(objectToString(attribute.get("TEAM_ORG_NAME")));
        uploadedFacility.setSuperviseOrgName(objectToString(attribute.get("SUPERVISE_ORG_NAME")));
        uploadedFacility.setComponentType(objectToString(attribute.get("LAYER_NAME")));
        uploadedFacility.setLayerName(objectToString(attribute.get("LAYER_NAME")));
        Point geometryCenter = GeometryUtil.getGeometryCenter(geometry);
        uploadedFacility.setX(geometryCenter.getX());
        uploadedFacility.setY(geometryCenter.getY());
        uploadedFacility.setMarkPerson(objectToString(attribute.get("MARK_PERSON")));
        uploadedFacility.setMarkTime(objectToLong(attribute.get("MARK_TIME")));
        uploadedFacility.setUpdateTime(objectToLong(attribute.get("UPDATE_TIME")));
        uploadedFacility.setCheckState(objectToString(attribute.get("CHECK_STATE")));
        uploadedFacility.setId(objectToLong(attribute.get(UploadLayerFieldKeyConstant.MARK_ID)));

        //设施问题
        uploadedFacility.setpCode(objectToString(attribute.get("PCODE")));
        uploadedFacility.setChildCode(objectToString(attribute.get("CHILD_CODE")));
        //管理状态
        uploadedFacility.setCityVillage(objectToString(attribute.get("CITY_VILLAGE")));
        uploadedFacility.setAttrSix(objectToString(attribute.get("ATTR_SIX")));
        uploadedFacility.setAttrSeven(objectToString(attribute.get("ATTR_SEVEN")));
        uploadedFacility.setRiverx(objectToDouble(attribute.get("RIVERX")));
        uploadedFacility.setRivery(objectToDouble(attribute.get("RIVERY")));
        return uploadedFacility;
    }

    /**
     * 改变检测情况图层的显示状态
     *
     * @param b
     */
    private void switchLayerVisibility(boolean b) {
        if (layerPresenter != null) {
            layerPresenter.changeLayerVisibility(PatrolLayerPresenter.DRAINAGE_JCQK, b);
        }
    }

    private void queryJhjAllHangUpContents(final TextView psdy_delete, String pshId) {
//        if (pd == null) {
//            pd = new ProgressDialog(getContext());
//        }
//        pd.setMessage("正在查询已挂接的接户井，请稍后...");
//        if (!pd.isShowing()) {
//            pd.show();
//        }
        if (mIdentificationService == null) {
            mIdentificationService = new CorrectFacilityService(getContext());
        }
        pshJhjs.clear();
        mIdentificationService.queryPshGj(null, pshId, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result2<List<PshJhj>>>() {
                    @Override
                    public void call(Result2<List<PshJhj>> listResult2) {
                        if (listResult2.getCode() == 200 && listResult2.getData() != null && !listResult2.getData().isEmpty()) {
                            // 查询成功
                            map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.VISIBLE);
                            mPsdyJbjs.addAll(listResult2.getData());
                            if (!ifInDY) {
                                map_bottom_sheet.findViewById(R.id.psdy_line_del).setVisibility(View.VISIBLE);
                            }
                            if (psdy_delete != null) {
                                psdy_delete.setVisibility(View.VISIBLE);
                                map_bottom_sheet.findViewById(R.id.psdy_line_del).setVisibility(View.VISIBLE);
                                psdy_delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        addTopBarView("删除接驳井连线");
                                        ifInDeletePshAndJhjLineMode = true;
                                        mStatus = 6;
                                        setMode(mStatus);
                                        ToastUtil.shortToast(mContext, "请选择连线");
//                                        locationMarker.changeIcon(R.mipmap.ic_check_stream);
//                                        if (locationMarker != null) {
//                                            locationMarker.changeIcon(R.mipmap.ic_check_related);
//                                            locationMarker.setVisibility(View.VISIBLE);
//                                        }
                                        locationMarker.setVisibility(View.GONE);
                                        tv_query_tip.setText("请选择要删除的接驳井连线");
//                                        initStreamGLayer();
                                        hideBottomSheet();
                                        mGLayer.clearSelection();
                                        mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                                    }
                                });
                            }
                            pshJhjs.addAll(listResult2.getData());
                            drawPshJhjLines(listResult2.getData());
                        } else {
                        }
//                        pd.dismiss();
                    }
                }, new Action1<Throwable>() {

                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
//                        pd.dismiss();
                    }
                });
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
     * 排水单元挂接到接驳井
     *
     * @param name
     * @param component
     */
    private void showJBJDialog(String name, final Component component) {
        DialogUtil.MessageBox(mContext, "提示", "确认挂接到该接驳井？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String usid = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("USID"), "");
                String objectId = StringUtil.getNotNullString(currentDYComponent.getGraphic().getAttributeValue("OBJECTID"), "");
                String psdytId = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("OBJECTID"), "");
                Point centerPoint = (Point) component.getGraphic().getGeometry();
                double gjx = centerPoint.getX();
                double gjy = centerPoint.getY();
                Point point = GeometryUtil.getPolygonCenterPoint((Polygon) currentDYComponent.getGraphic().getGeometry());

                PsdyJbj psdyJbj = new PsdyJbj();
                psdyJbj.setUsid(usid);
                psdyJbj.setJbjObjectId(String.valueOf(psdytId));
                psdyJbj.setPsdyObjectId(String.valueOf(objectId));
                psdyJbj.setJbjX(gjx);
                psdyJbj.setJbjY(gjy);
                psdyJbj.setPsdyX(point.getX());
                psdyJbj.setPsdyY(point.getY());
                uploadPsdys(psdyJbj);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    /**
     * 倒数计时管线闪烁
     *
     * @param graphic
     * @param graphicsLayer
     */
    public void countDownAnimation(final Graphic graphic, final GraphicsLayer graphicsLayer) {
        isLoading = true;
        mIsAdd = true;
        countdown(4)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        //执行向里收缩的动画
                        graphicsLayer.removeAll();

                        graphicsLayer.addGraphic(graphic);

                        whileTake = true;
                        isLoading = false;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (mIsAdd) {
                            graphicsLayer.removeAll();
                            graphicsLayer.addGraphic(graphic);
                        } else {
                            graphicsLayer.removeAll();
                        }
                        mIsAdd = !mIsAdd;
                    }
                });
    }

    private void showPsdyDialog(String name, final Component component) {
        DialogUtil.MessageBox(mContext, "提示", "确认挂接到" + name + "排水单元？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String usid = StringUtil.getNotNullString(currentComponent.getGraphic().getAttributeValue("USID"), "");
                String objectId = StringUtil.getNotNullString(currentComponent.getGraphic().getAttributeValue("OBJECTID"), "");
                String psdytId = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("OBJECTID"), "");
                double gjx = ((Point) currentComponent.getGraphic().getGeometry()).getX();
                double gjy = ((Point) currentComponent.getGraphic().getGeometry()).getY();
                Point point = GeometryUtil.getPolygonCenterPoint((Polygon) component.getGraphic().getGeometry());

                PsdyJbj psdyJbj = new PsdyJbj();
                psdyJbj.setUsid(usid);
                psdyJbj.setJbjObjectId(String.valueOf(objectId));
                psdyJbj.setPsdyObjectId(String.valueOf(psdytId));
                psdyJbj.setJbjX(gjx);
                psdyJbj.setJbjY(gjy);
                psdyJbj.setPsdyX(point.getX());
                psdyJbj.setPsdyY(point.getY());
                uploadPsdys(psdyJbj);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
        mUploadFacilityService.updateLinePipe(dataJson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        mLineLength = -1;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
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

        ll_next_and_prev_container5.setVisibility(View.INVISIBLE);
        btn_dis_next.setVisibility(View.GONE);
        btn_dis_prev.setVisibility(View.GONE);
        currIndex = 0;
        final Point point = new Point(x, y);
        mCurrentDoorNOBean = null;
        mSewerageLayerService.setQueryByWhere("1=1");
        if (true) {
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
                    mDoorNOBeans2.clear();
                    mDoorNOBeans2.addAll(doorNOBeans);
                    if (locationMarker != null) {
                        locationMarker.setVisibility(View.GONE);
                    }
//                    hideCallout();
                    showOnBottomSheets(mDoorNOBeans2);
//                    sewerageItemPresenter.getBuildInfBySGuid(mDoorNOBeans.get(0).getS_guid());
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
    }

    /**
     * 将component转换成DoubtBean实体
     *
     * @param component
     * @param geometry
     * @param description
     * @return
     */
    private DoubtBean createDoubt(Component component, Geometry geometry, String description) {
        DoubtBean doubtBean = new DoubtBean();
        if (component != null) {
            String layerName = component.getLayerName();
            if (layerName.contains("排水管道")) {
                doubtBean.setDoubtType(2);
            } else {
                doubtBean.setDoubtType(1);
            }
            doubtBean.setLayerName(getLayerName(layerName));
            String objectId = String.valueOf(component.getGraphic().getAttributes().get(ComponentFieldKeyConstant.OBJECTID));
            doubtBean.setObjectId(objectId);
        } else {
            doubtBean.setDoubtType(3);
            doubtBean.setLayerName("存疑区域");
            doubtBean.setRings(createRingsForGeometry(geometry));
        }
        doubtBean.setDescription(description);
        return doubtBean;
    }

    private void uploadDoubt(final Component component, DoubtBean doubtBean, final AlertDialog dialog, final TextView tv_zhiyi) {
        if (pd == null) {
            pd = new ProgressDialog(mContext);
            pd.setMessage("正在提交，请等待");
            pd.setCancelable(false);
        }

        if (!pd.isShowing()) {
            pd.show();
        }
        mUploadFacilityService.partsDoubt(doubtBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        ToastUtil.shortToast(mContext, "提交失败");
                        CrashReport.postCatchedException(new Exception("新增存疑失败，上报用户是：" +
                                BaseInfoManager.getUserName(mContext) + "原因是：" + e.getLocalizedMessage()));
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (responseBody.getCode() == 200) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            if (mMapDrawQuestionView != null && component == null) {
                                mMapDrawQuestionView.clear();
                            }
                            initGLayer();
                            hideBottomSheet();
                            refreshMap();
//                            if(tv_zhiyi != null){
//                                tv_zhiyi.setText(" 取消存疑 ");
//                            }
                            ToastUtil.shortToast(mContext, "存疑提交成功");
                        } else {
                            ToastUtil.shortToast(mContext, "保存失败，原因是：" + responseBody.getMessage());
                            CrashReport.postCatchedException(new Exception("新增存疑失败，上报用户是：" +
                                    BaseInfoManager.getUserName(mContext) + "原因是：" + responseBody.getMessage()));
                        }
                    }
                });
    }


    /**
     * 初始化管井信息
     */
    private void initPipeInfo() {
        llInfo = (FrameLayout) map_bottom_sheet.findViewById(R.id.fl_info);
        llResponsible = (LinearLayout) map_bottom_sheet.findViewById(R.id.ll_responsible);
        tvResponsible = (TextView) map_bottom_sheet.findViewById(R.id.tv_responsible);
        ivBack = (ImageView) map_bottom_sheet.findViewById(R.id.iv_back);
        ivResponsible = (ImageView) map_bottom_sheet.findViewById(R.id.iv_responsible);
        LinearLayout.LayoutParams lpInfo = new LinearLayout.LayoutParams(DeviceUtil.getScreenWidth(mContext),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llInfo.setLayoutParams(lpInfo);
        LinearLayout.LayoutParams lpResponsible = new LinearLayout.LayoutParams(DeviceUtil.getScreenWidth(mContext),
                ViewGroup.LayoutParams.MATCH_PARENT);
        llResponsible.setLayoutParams(lpResponsible);
        tvResponsible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoAnimator(llInfo, false);
            }
        });
        ivResponsible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoAnimator(llInfo, false);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoAnimator(llInfo, true);
            }
        });
        resetInfoLayout();
    }

    /**
     * 获取挂接信息
     *
     * @param psdy_delete
     * @param usId
     * @param objectId
     * @param o
     */
    private void getGjInfo(final TextView psdy_delete, String usId, String objectId, String o) {
        mPsdyJbjs.clear();
        mIdentificationService.queryPsdyJbj(usId, objectId, o)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result2<List<PsdyJbj>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        ToastUtil.shortToast(mContext, "获取连线信息失败");
                    }

                    @Override
                    public void onNext(Result2<List<PsdyJbj>> responseBody) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (responseBody.getCode() == 200) {
                            if (!ListUtil.isEmpty(responseBody.getData())) {
                                ifInLine = true;
                                map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.VISIBLE);
                                if (!ifInDY) {
                                    map_bottom_sheet.findViewById(R.id.psdy_line_del).setVisibility(View.VISIBLE);
                                }
                                if (psdy_delete != null) {
                                    psdy_delete.setVisibility(View.VISIBLE);
                                    map_bottom_sheet.findViewById(R.id.psdy_line_del).setVisibility(View.VISIBLE);
                                    psdy_delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            addTopBarView("删除接驳井连线");
                                            mStatus = 6;
                                            setMode(mStatus);
                                            ToastUtil.shortToast(mContext, "请选择连线");
//                                        locationMarker.changeIcon(R.mipmap.ic_check_stream);
//                                        if (locationMarker != null) {
//                                            locationMarker.changeIcon(R.mipmap.ic_check_related);
//                                            locationMarker.setVisibility(View.VISIBLE);
//                                        }
                                            locationMarker.setVisibility(View.GONE);
                                            tv_query_tip.setText("请选择要删除的接驳井连线");
//                                        initStreamGLayer();
                                            hideBottomSheet();
                                            mGLayer.clearSelection();
                                            mMapView.setOnTouchListener(getDefaultMapOnTouchListener());
                                        }
                                    });
                                }
                                drawPsdyLines(responseBody.getData());
                                List<PsdyJbj> tempList = responseBody.getData();
                                mPsdyJbjs.clear();
                                mPsdyJbjs.addAll(tempList);
                            }
                        } else {
                            ToastUtil.shortToast(mContext, "获取连线信息失败");
                        }
                    }
                });
    }

    private LayerInfo getPshMpInfo() {
        mLayerInfosFromLocal = LayerServiceFactory.provideLayerService(mContext).getLayerInfosFromLocal();
        if (ListUtil.isEmpty(mLayerInfosFromLocal)) {
            return null;
        }
        for (LayerInfo layerInfo : mLayerInfosFromLocal) {
            if (layerInfo.getLayerName().equals(PatrolLayerPresenter.DRAINAGE_USER)) {
                layerInfo.setLayerName(PatrolLayerPresenter.DRAINAGE_USER);
                layerInfo.setLayerId(9999);
                layerInfo.setDefaultVisibility(true);
                return layerInfo;
            }
        }
        return null;
    }

    private void cancelNotHook(String id, String reason) {
        mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("取消无法挂接状态中.....");
        mDialog.show();
        if (mUploadLayerService == null) {
            mUploadFacilityService = new UploadFacilityService(mContext);
        }
        mUploadFacilityService.partsNotHook(id, 0, reason)
                //获取列表（不包含附件）
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        ToastUtil.iconShortToast(mContext, R.mipmap.ic_alert_yellow, "取消无法挂接状态失败，请稍后重试");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (responseBody.getCode() == 200) {
                            initGLayer();
                            hideBottomSheet();
                            refreshMap();
                            Toast.makeText(mContext, "取消无法挂接状态成功", Toast.LENGTH_SHORT).show();
                        } else {
//                            CrashReport.postCatchedException(new Exception("删除失败，上报用户是：" +
//                                    BaseInfoManager.getUserName(mContext) + "原因是：" + s.getMessage()));
                            Toast.makeText(mContext, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void showNotHookDialog(final Component component, final Geometry geometry, final TextView tv_zhiyi) {
        View view = View.inflate(mContext, R.layout.activity_dialog_view, null);
        String type = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.WFGJ_REASON), "");
        final TextFieldTableItem fieldTableItem = (TextFieldTableItem) view.findViewById(R.id.textitem_description);
        fieldTableItem.setText(type);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("请填写无法挂接原因")
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        TextView title = new TextView(mContext);
        title.setGravity(Gravity.CENTER);
        title.requestLayout();
        title.setText("请填写无法挂接原因");
//        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        title.setTextSize(18);
        builder.setCustomTitle(title);
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (StringUtil.isEmpty(fieldTableItem.getText().trim())) {
                            ToastUtil.shortToast(mContext, "请填写无法挂接原因");
                            return;
                        }
                        uploadNotHookReason(component, fieldTableItem.getText().trim(), dialog, tv_zhiyi);
                    }
                });
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        LinearLayout.MarginLayoutParams marginLayoutParams = (LinearLayout.MarginLayoutParams) title.getLayoutParams();
        marginLayoutParams.setMargins(8, 45, 8, 30);
        title.requestLayout();
    }

    private void showSewerageUserBtnOnly(final Component component) {

        if (ifInSwerageUserMode) {
            map_bottom_sheet.findViewById(R.id.tv_gjdy).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.line_gjdy).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.psdy_delete).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.psdy_line_del).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.psdy_jc).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.psdy_line_jc).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_zhiyi).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.stream_line3).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_detail_left).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.stream_line4).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_delete).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.stream_line1).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_sure).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.stream_line2).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_error_correct).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.stream_line).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_detail_right).setVisibility(View.GONE);
            map_bottom_sheet.findViewById(R.id.tv_swerage_user).setVisibility(View.VISIBLE);
            map_bottom_sheet.findViewById(R.id.line_swerage_user).setVisibility(View.VISIBLE);
            map_bottom_sheet.findViewById(R.id.tv_swerage_user).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 隐藏其他的，然后显示弹窗
                    hideBottomSheet();
                    showSewerageUserBottomSheet(component);
                }
            });
            //显示排水单元里面的门牌号
            removePsdyMph();
            addPsdyMph(component);
        }
//        TextView tv = new TextView(getContext());
//        tv.setTextSize();
    }

    /**
     * 提交日常巡检
     *
     * @param modifiedFacility
     */
    private void uploadJournal(ModifiedFacility modifiedFacility) {
        modifiedFacility.setId(null);
        User user = new LoginService(mContext, AMDatabase.getInstance()).getUser();
        String userName = user.getUserName();
        long currentTimeMillis = System.currentTimeMillis();

        modifiedFacility.setMarkTime(currentTimeMillis);
        modifiedFacility.setMarkPerson(userName);
        modifiedFacility.setMarkPersonId(user.getId());
        //数据纠错
//        JournalService identificationService = new JournalService(mContext.getApplicationContext());
//        identificationService.upload(modifiedFacility)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ResponseBody>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        CrashReport.postCatchedException(new Exception("巡检上报失败，上报用户是：" +
//                                BaseInfoManager.getUserName(mContext) + "原因是：" + e.getLocalizedMessage()));
//                        ToastUtil.shortToast(mContext, "删除成功，巡检提交失败");
//                        //ToastUtil.shortToast(CorrectOrConfirimFacilityActivity.this, "提交失败");
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        if (responseBody.getCode() == 200) {
//                            ToastUtil.shortToast(mContext, "删除成功");
//                        } else {
//                            CrashReport.postCatchedException(new Exception("核准上报失败，上报用户是：" +
//                                    BaseInfoManager.getUserName(mContext) + "原因是：" + responseBody.getMessage()));
//                            ToastUtil.shortToast(mContext, "删除成功，巡检提交失败，原因是：" + responseBody.getMessage());
//                            //ToastUtil.shortToast(CorrectOrConfirimFacilityActivity.this, "保存失败，原因是：" + responseBody.getMessage());
//                        }
//                    }
//                });
    }

    private void initDeleteFacilityService() {
        if (deleteFacilityService == null) {
            deleteFacilityService = new DeleteFacilityService(mContext);
        }
    }

    private void uploadPsdys(final PsdyJbj psdyJbj) {
        if (mIdentificationService == null) {
            mIdentificationService = new CorrectFacilityService(mContext.getApplicationContext());
        }

        mIdentificationService.addPsdyJbj(psdyJbj)
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
                        CrashReport.postCatchedException(new Exception("连线失败，上报用户是：" +
                                BaseInfoManager.getUserName(mContext) + "原因是：" + e.getLocalizedMessage()));
                        ToastUtil.shortToast(mContext, "连线失败失败");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        final Callout callout = mMapView.getCallout();
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (responseBody.getCode() == 200) {
                            callout.hide();
                            initSelectGLayer();
                            hideBottomSheet();
                            if (locationMarker != null) {
                                locationMarker.setVisibility(View.VISIBLE);
                            }
                            ifInLine = true;
                            mPsdyJbjs.add(psdyJbj);
                            initGraphicSelectGLayer();
                            drawPsdyLines(mPsdyJbjs);
                            ToastUtil.shortToast(mContext, "提交成功");
                        } else {
                            CrashReport.postCatchedException(new Exception("连线失败，上报用户是：" +
                                    BaseInfoManager.getUserName(mContext) + "原因是：" + responseBody.getMessage()));
                            ToastUtil.shortToast(mContext, "连线失败，原因是：" + responseBody.getMessage());
                        }
                    }
                });
    }

    /**
     * 倒计时
     *
     * @param time
     * @return
     */
    public Observable<Integer> countdown(int time) {
        if (time < 1) time = 1;
        final int countTime = time;
        return Observable.interval(0, 350, TimeUnit.MILLISECONDS)
                .takeWhile(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return whileTake;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long increaseTime) {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1);

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
        mUploadFacilityService.addLinePipe(dataJson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        mLineLength = -1;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
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
                            ToastUtil.shortToast(mContext, "提交成功");
                            refreshMap();
//                            mStatus = 0;
//                            //提交文件
//                            setMode(mStatus);
//                            mAllPipeBeans.clear();
//                            initArrowGLayer();
                            locationMarker.setVisibility(View.VISIBLE);
                            isDrawStream = false;
                            tv_query_tip.setText("继续关联窨井或取消");
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

    private void initJhjBottomSheetView() {
        ll_next_and_prev_container5 = (ViewGroup) mJhjview.findViewById(R.id.ll_next_and_prev_container5);
        tv_address = (TextView) mJhjview.findViewById(R.id.tv_address);
        tv_house_Property = (TextView) mJhjview.findViewById(R.id.tv_house_Property);
        tv_right_up_tip = (TextView) mJhjview.findViewById(R.id.tv_right_up_tip);
        tv_right_up = (TextView) mJhjview.findViewById(R.id.tv_right_up);
    }

    private void showOnBottomSheets(List<PSHHouse> beans) {
        if (beans.size() > 1) {
            ll_next_and_prev_container5.setVisibility(View.VISIBLE);
            btn_dis_next.setVisibility(View.VISIBLE);
        }

        //隐藏marker
//        locationMarker.setVisibility(View.GONE);
        initGLayer();
        Geometry geometry = null;
        mCurrentDoorNOBean2 = beans.get(0);
        if (beans.get(0) != null) {
            geometry = new Point(beans.get(0).getX(), beans.get(0).getY());
            showBottomSheet(beans.get(0));
        }
        if (geometry != null) {
            drawGeometry(geometry, mGLayer, true, true);
        }
    }

    /**
     * 设施部件
     * 加载设施信息，显示中底部BottomSheet中
     *
     * @param doorNOBean
     */
    private void showBottomSheet(final PSHHouse doorNOBean) {

        mJhjview.setVisibility(View.VISIBLE);
        if (mDoorNOBeans.size() > 1) {
            ll_next_and_prev_container5.setVisibility(View.VISIBLE);
        }
        mJhjBehavoir.setState(com.augurit.am.cmpt.widget.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
        //initGLayer();
        mCurrentDoorNOBean2 = doorNOBean;
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
        initBottomSheetText(doorNOBean, null);

        final TextView tvConfirm = ((TextView) mJhjview.findViewById(R.id.door_detail_btn));
        tvConfirm.setText(" 确认挂接 ");
        tvConfirm.setVisibility(View.VISIBLE);
        tvConfirm.findViewById(R.id.door_detail_btn).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPshGjDialog(doorNOBean);

                    }
                });

    }

    private String createRingsForGeometry(Geometry geometry) {
        String rings = "";
        if (geometry instanceof Polygon) {
            Polygon polygon = (Polygon) geometry;
            int count = polygon.getPointCount();
            for (int i = 0; i < count; i++) {
                Point point = polygon.getPoint(i);
                rings += ";" + point.getX() + "," + point.getY();
            }
        }
        if (!StringUtil.isEmpty(rings)) {
            rings = rings.substring(1);
        }
        return rings;
    }

    private void uploadNotHookReason(final Component component, String reason, final AlertDialog dialog, final TextView tv_zhiyi) {
        if (pd == null) {
            pd = new ProgressDialog(mContext);
            pd.setMessage("正在提交，请等待");
            pd.setCancelable(false);
        }

        if (!pd.isShowing()) {
            pd.show();
        }
        String objectId = StringUtil.getNotNullString(component.getGraphic().getAttributeValue(ComponentFieldKeyConstant.OBJECTID), "");
        mUploadFacilityService.partsNotHook(objectId, 1, reason)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        ToastUtil.shortToast(mContext, "提交失败");
                        CrashReport.postCatchedException(new Exception("新增无法挂接原因失败，上报用户是：" +
                                BaseInfoManager.getUserName(mContext) + "原因是：" + e.getLocalizedMessage()));
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (responseBody.getCode() == 200) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            if (mMapDrawQuestionView != null && component == null) {
                                mMapDrawQuestionView.clear();
                            }
                            initGLayer();
                            hideBottomSheet();
                            refreshMap();
//                            if(tv_zhiyi != null){
//                                tv_zhiyi.setText(" 取消存疑 ");
//                            }
                            ToastUtil.shortToast(mContext, "提交成功");
                        } else {
                            ToastUtil.shortToast(mContext, "保存失败，原因是：" + responseBody.getMessage());
                            CrashReport.postCatchedException(new Exception("新增无法挂接原因失败，上报用户是：" +
                                    BaseInfoManager.getUserName(mContext) + "原因是：" + responseBody.getMessage()));
                        }
                    }
                });
    }

    private void infoAnimator(final View view, boolean reverse) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(reverse ? view.getWidth() : 0, reverse ? 0 : view.getWidth());
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int magin = -(int) valueAnimator.getAnimatedValue();
                ((LinearLayout.LayoutParams) view.getLayoutParams()).leftMargin = magin;
                view.requestLayout();
            }
        });
        valueAnimator.start();
    }

    /**
     * 排水单元挂接到接驳井
     *
     * @param pshHouse
     */
    private void showPshGjDialog(final PSHHouse pshHouse) {
        DialogUtil.MessageBox(mContext, "提示", "确认挂接到该排水户？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String usid = StringUtil.getNotNullString(currentComponent.getGraphic().getAttributeValue("USID"), "");
                String objectId = pshHouse.getObjectId();
                String psdytId = StringUtil.getNotNullString(currentComponent.getGraphic().getAttributeValue("OBJECTID"), "");
                Point centerPoint = (Point) currentComponent.getGraphic().getGeometry();
                double gjx = centerPoint.getX();
                double gjy = centerPoint.getY();

                PsdyJbj psdyJbj = new PsdyJbj();
                psdyJbj.setUsid(usid);
                psdyJbj.setPshId(String.valueOf(pshHouse.getId()));
                psdyJbj.setJhjObjectId(String.valueOf(psdytId));
                psdyJbj.setGjwX(gjx);
                psdyJbj.setGjwY(gjy);
                psdyJbj.setPshGjlx("1");
                psdyJbj.setPshX(pshHouse.getX());
                psdyJbj.setPshY(pshHouse.getY());
                uploadPshJhj(psdyJbj);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void uploadPshJhj(final PsdyJbj psdyJbj) {
        if (mIdentificationService == null) {
            mIdentificationService = new CorrectFacilityService(mContext.getApplicationContext());
        }

        mIdentificationService.addPshJhj(psdyJbj)
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
                        CrashReport.postCatchedException(new Exception("连线失败，上报用户是：" +
                                BaseInfoManager.getUserName(mContext) + "原因是：" + e.getLocalizedMessage()));
                        ToastUtil.shortToast(mContext, "连线失败失败");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        final Callout callout = mMapView.getCallout();
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (responseBody.getCode() == 200) {
                            callout.hide();
                            initSelectGLayer();
                            hideBottomSheet();
                            if (locationMarker != null) {
                                locationMarker.setVisibility(View.VISIBLE);
                            }
                            ifInLine = true;
                            mPsdyJbjs.add(psdyJbj);
                            initGraphicSelectGLayer();
                            drawPshJhjLines(mPsdyJbjs);
                            ToastUtil.shortToast(mContext, "提交成功");
                        } else {
                            CrashReport.postCatchedException(new Exception("连线失败，上报用户是：" +
                                    BaseInfoManager.getUserName(mContext) + "原因是：" + responseBody.getMessage()));
                            ToastUtil.shortToast(mContext, "连线失败，原因是：" + responseBody.getMessage());
                        }
                    }
                });
    }

    /**
     * 隐藏排水户界面
     */
    private void dismissJhjBottomSheet() {
        mJhjview.setVisibility(View.INVISIBLE);
        mJhjBehavoir.setState(AnchorSheetBehavior.STATE_COLLAPSED);
    }

    @Subscribe
    public void onRecePsdyEvent(final RefreshPsdyData data) {
        if (data != null) {
            locationMarker.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO 开始检索周边排水单元
                    queryDrainageUnit(data.getX(), data.getY());
                }
            }, 300);
        }
    }
}
