package com.augurit.agmobile.gzpssb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.patrolcore.editmap.widget.LocationMarker;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.am.fw.net.util.SharedPreferencesUtil;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geometry.Point;

/**
 * 数据新增校核地图界面
 *
 * @author 创建人 ：luobiao
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.uploadfacility.view
 * @createTime 创建时间 ：2018/9/3
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class SewerageMapForSelectedDrainageUnitActivity extends BaseActivity {

    public static final int REQUEST_CODE = 20000;

    public static final String KEY_ADDRESS = "ADDRESS";
    public static final String KEY_LAT = "LAT";
    public static final String KEY_LNG = "LNG";
    public static final String KEY_SELECT_MODE = "SELECT_MODE";
    public static final String KEY_SELECTABLE = "SELECTABLE";
    public static final String KEY_TITLE = "TITLE";

    private ViewGroup root;
    private MapView mapView;
    private LocationMarker locationMarker;

    /**
     * 是否从地图点击设施点进行查询
     */
    private ProgressBar mProgressBar;
    private View viewContant;

    private boolean initFinished = true;

    protected boolean isSearching = false;

    private boolean selectable = true;
    private String title = "";

    private double lat = -1;
    private double lng = -1;

    private double currLat = -1;
    private double currLng = -1;
    private DetailAddress currDetailAddress = null;
    private String currMsg = "";
    private View.OnClickListener currOnClickListener;

    private boolean isFirstFixedLocation = true;

    private GraphicsLayer mGLayer;

//    private ArcGISDynamicMapServiceLayer mHouseNumberLayer;
//
//    private RecyclerView rvDatas;
//    private SimpleRecyclerViewAdapter adptDatas;
//    private List<DetailAddress> listDatas;
//    private View popContentView;
//    private PopupWindow popList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservoirl_patrol_map_for_selected_location);

        initArgument();

        initData();
        initView();
    }

    // ==============================回调/监听==============================

//    /**
//     * 开始请求门牌号
//     */
//    private final View.OnClickListener startRequestHouseNumberListener = v -> {
//        showCalloutMessage("查询中...", null, null);
//        requestHouseNumber(currLng, currLat);
//    };
//
//    private final View.OnClickListener onAddressSelectedListener = v -> onSelectAddress(currDetailAddress);

    // ==============================自定义方法==============================

//    public static void start(Context context, double lat, double lng) {
//        start(context, lat, lng, MODE_LOCATION, true);
//    }
//
//    public static void start(Context context, double lat, double lng, @SelectModeDef int mode) {
//        start(context, lat, lng, mode, true);
//    }
//
//    public static void start(Context context, double lat, double lng, @SelectModeDef int mode, boolean selectable) {
//        start(context, lat, lng, mode, selectable, "");
//    }
//
//    public static void start(Context context, double lat, double lng, @SelectModeDef int mode, boolean selectable, String title) {
//        final Intent intent = new Intent(context, ReservoirPatrolMapForSelectedLocationActivity.class);
//        if (lat != -1 && lng != -1) {
//            intent.putExtra(KEY_LAT, lat);
//            intent.putExtra(KEY_LNG, lng);
//        }
//        intent.putExtra(KEY_SELECT_MODE, mode);
//        intent.putExtra(KEY_SELECTABLE, selectable);
//        intent.putExtra(KEY_TITLE, title);
//        if (context instanceof Activity) {
//            ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
//        } else {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        }
//    }
//
//    public static DetailAddress getDetailAddress(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//            if (data != null && data.hasExtra(KEY_ADDRESS)) {
//                Serializable serializableExtra = data.getSerializableExtra(KEY_ADDRESS);
//                if (serializableExtra instanceof DetailAddress) {
//                    return (DetailAddress) serializableExtra;
//                }
//            }
//        }
//        return null;
//    }
//
    private void initArgument() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(KEY_LAT)) {
                try {
                    lat = intent.getDoubleExtra(KEY_LAT, -1);
                    currLat = lat;
                } catch (Exception ignored) {
                }
            }
            if (intent.hasExtra(KEY_LNG)) {
                try {
                    lng = intent.getDoubleExtra(KEY_LNG, -1);
                    currLng = lng;
                } catch (Exception ignored) {
                }
            }
            if (intent.hasExtra(KEY_TITLE)) {
                try {
                    title = intent.getStringExtra(KEY_TITLE);
                } catch (Exception ignored) {
                }
            }
            if (intent.hasExtra(KEY_SELECTABLE)) {
                try {
                    selectable = intent.getBooleanExtra(KEY_SELECTABLE, true);
                } catch (Exception ignored) {
                }
            }
        }

        if (TextUtils.isEmpty(title)) {
            title = "选择关联排水单元";
        }
    }

    private void initData() {
//        adptDatas = new SimpleRecyclerViewAdapter();
//        listDatas = new ArrayList<>();
    }

    private void initView() {
        root = findViewById(R.id.cl_base);
        mProgressBar = findViewById(R.id.map_progress);
        viewContant = findViewById(R.id.viewContant);
        locationMarker = findViewById(R.id.locationMarker);
        locationMarker.setVisibility(View.INVISIBLE);

        mapView = findViewById(R.id.map_view);
        initMap();

//        initPopList();
    }

    private void initMap() {
//        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(this);
//        String url = sharedPreferencesUtil.getString(PatrolLayerPresenter.DRAINAGE_UNIT_LAYER, "") + "/0";
//
//        if ("/0".equals(url)) {
//            url = getDrainageUnitLayerUrl() + "/0";
//        }
////        mapView.addLayer(new ArcGISTiledMapServiceLayer(GUANGZHOU_MAP_VEC_WGS84));
//        mapView.addLayer(new ArcGISTiledMapServiceLayer(url));
//        if (selectMode == MODE_HOUSE_NUMBER) {
//            mHouseNumberLayer = new ArcGISDynamicMapServiceLayer(PSH_MPH_URL);
//            mapView.addLayer(mHouseNumberLayer);
//        }
//        mapView.setMapBackground(Color.WHITE, Color.WHITE, 0, 0);
//        mapView.setOnPanListener(new OnPanListener() {
//            @Override
//            public void prePointerMove(float v, float v1, float v2, float v3) {
//                hideCallout();
////                startUpAnimation();
//                if (selectable) {
//                    locationMarker.startUpAnimation(null);
//                }
//            }
//
//            @Override
//            public void postPointerMove(float v, float v1, float v2, float v3) {
//            }
//
//            @Override
//            public void prePointerUp(float v, float v1, float v2, float v3) {
////                startDownAnimation();
//            }
//
//            @Override
//            public void postPointerUp(float v, float v1, float v2, float v3) {
////                startDownAnimation();
//                if (selectable) {
//                    locationMarker.startDownAnimation(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            final Point mapCenterPoint = getMapCenterPoint();
//                            currLng = mapCenterPoint.getX();
//                            currLat = mapCenterPoint.getY();
//                            switch (selectMode) {
//                                case MODE_LOCATION:
//                                    showCalloutMessage("查询中...", null, null);
//                                    requestAddress(currLng, currLat);
//                                    break;
//                                case MODE_HOUSE_NUMBER:
//                                    showCalloutMessage("查询该地点的门牌号", null, startRequestHouseNumberListener);
//                                    break;
//                            }
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//                }
//            }
//        });
//        mapView.setOnStatusChangedListener((OnStatusChangedListener) (o, status) -> {
//
//            if (status == OnStatusChangedListener.STATUS.INITIALIZED) {
//                if (viewContant != null) {
//                    ObjectAnimator.ofFloat(viewContant, "alpha", 1f, 0f).setDuration(500).start();
//                    viewContant.postDelayed(() -> {
//                        viewContant.setVisibility(View.GONE);
//                        mProgressBar.setVisibility(View.INVISIBLE);
//                    }, 500);
//                }
//            }
//
//            if (status == OnStatusChangedListener.STATUS.LAYER_LOADED) {
////                mapView.setScale(10000, true);
////                setScale(scale);
////                if (need2SetLocationLater) {
////                    setLocation(lat, lng);
////                }
//                // 因为这个状态，会根据图层的个数而调用多次，因此只需要加载一次即可
//                if (lat != -1 && lng != -1 && isFirstFixedLocation) {
//                    isFirstFixedLocation = false;
//                    initGLayer();
//                    Point point = new Point(lng, lat);
//                    highLight(point, mGLayer);
//                    mapView.centerAt(point, true);
//                    locationMarker.setVisibility(selectable ? View.VISIBLE : View.GONE);
//                }
//            }
//
//            if (status == OnStatusChangedListener.STATUS.LAYER_LOADING_FAILED) {
//                Log.e(ReservoirPatrolMapForSelectedLocationActivity.class.getSimpleName(), "有图层加载失败啦！！！！！！！！！！！");
//            }
//        });
    }

    private void initPopList() {
//        popContentView = getLayoutInflater().inflate(R.layout.drainage_view_reservoir_patrol_selected_location_pop, root, false);
//        rvDatas = popContentView.findViewById(R.id.rvDatas);
//        rvDatas.setAdapter(adptDatas);
//        rvDatas.addOnItemTouchListener(new OnRecyclerItemClickListener(rvDatas) {
//            @Override
//            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
//                final int position = viewHolder.getLayoutPosition();
//                if (position == adptDatas.getSelected()) {
//                    // 点击同一个，不处理
//                    return;
//                }
//                currDetailAddress = listDatas.get(position);
//                adptDatas.setSelected(position);
//                center2Map();
//            }
//        });
//        rvDatas.setItemAnimator(null);
//
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        popList = new PopupWindow(popContentView, (int) (displayMetrics.widthPixels * 0.95f), ViewGroup.LayoutParams.WRAP_CONTENT);
//        popList.setOutsideTouchable(true);
//        popList.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        popList.setFocusable(true);
//        popList.setOnDismissListener(() -> adptDatas.reset());
    }

    private void showPop() {
//        popList.showAtLocation(root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * @param message                        要显示的文本
     * @param point                          callout显示的位置，为null时取locationMarker的上方位置
     * @param calloutSureButtonClickListener "确定"按钮点击事件，为null时不显示“确定”按钮
     */
    private void showCalloutMessage(String message, Point point, final View.OnClickListener calloutSureButtonClickListener) {
//        if (TextUtils.isEmpty(message)) {
//            message = "查无位置信息";
//        }
//        final Callout callout = mapView.getCallout();
//        View view = View.inflate(this, R.layout.view_map_identify_move_by_marker_callout, null);
//        TextView title = view.findViewById(R.id.tv_listcallout_title);
//        title.setText(message);
//        View sureBtn = view.findViewById(R.id.btn_select_device);
//        sureBtn.setOnClickListener(view1 -> {
//            if (mapView.getCallout().isShowing()) {
//                mapView.getCallout().hide();
//            }
//            if (calloutSureButtonClickListener != null) {
//                calloutSureButtonClickListener.onClick(view1);
//            }
//        });
//        if (calloutSureButtonClickListener == null) {
//            view.findViewById(R.id.divider).setVisibility(View.GONE);
//            sureBtn.setVisibility(View.GONE);
//        }
//        callout.setStyle(R.xml.editmap_callout_style);
//        callout.setContent(view);
//        if (point == null) {
//            point = mapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() * 1.0f / 2,
//                    locationMarker.getIvLocation().getTop());
//        }
//        callout.show(point);
    }

    private void hideCallout() {
//        final Callout callout = mapView.getCallout();
//        if (callout.isShowing()) {
//            callout.hide();
//        }
    }

    private Point getMapCenterPoint() {
        //获取当前的位置
        return mapView.getCenter();
    }

    /**
     * 查询门牌号
     */
    private void requestHouseNumber(double longitude, double latidute) {
//        final Query query = new Query();
//        query.setGeometry(GeometryEngine.buffer(new Point(longitude, latidute), mapView.getSpatialReference(), 40 * mapView.getResolution(), null));
//        query.setOutFields(new String[]{"MPWZHM"});
//        query.setWhere("1=1");
//        query.setSpatialRelationship(SpatialRelationship.INTERSECTS);
//        final FeatureQueryService service = new FeatureQueryService();
//        Disposable subscribe = service.queryFeatures(query, mHouseNumberLayer.getUrl() + "/0")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(list -> {
//                    if (list != null && list.size() > 0) {
//                        if (list.size() == 1) {
//                            final AMFindResult amFindResult = list.get(0);
//                            currDetailAddress = convert2DetailAddress(amFindResult);
//                            center2Map();
//                        } else {
//                            // 这里应该展开一个列表让客户自己选
//                            final List<String> names = new ArrayList<>(list.size());
//                            listDatas.clear();
//                            for (AMFindResult amFindResult : list) {
//                                DetailAddress address = convert2DetailAddress(amFindResult);
//                                listDatas.add(address);
//                                names.add(address.getDetailAddress());
//                            }
//                            adptDatas.update(names);
//                            showPop();
//                            showCalloutMessage("从列表中选择一个门牌号", null, null);
//                            hideCallout();
//                        }
//                    } else {
//                        showCalloutMessage("该地点没有查询到门牌号", null, null);
//                    }
//                }, exception -> {
//                    exception.printStackTrace();
//                    showCalloutMessage("该地点没有查询到门牌号", null, null);
//                });
//
    }

    public void onSelectAddress(DetailAddress detailAddress) {
//        //todo 跳转数据上报页面
//        final Intent intent = new Intent();
//        intent.putExtra(KEY_ADDRESS, detailAddress);
//        setResult(RESULT_OK, intent);
//        ReservoirPatrolMapForSelectedLocationActivity.this.finish();
    }

    protected void initGLayer() {
//        if (mGLayer == null) {
//            //  mGLayer = new GraphicsLayer(GraphicsLayer.RenderingMode.STATIC);
//            mGLayer = new GraphicsLayer();
////            mGLayer.setSelectionColor(Color.BLUE);
////            mGLayer.setSelectionColorWidth(3);
//            mapView.addLayer(mGLayer);
//        }
//    }
//
//    /**
//     * 高亮
//     *
//     * @param geometry 要高亮的图形
//     */
//    private void highLight(Geometry geometry, GraphicsLayer mGLayer) {
//
//        mGLayer.removeAll();
//        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(mapView.getContext(),
//                mapView.getContext().getResources().getDrawable(R.drawable.widget_map_ic_origin_location));
//        pictureMarkerSymbol.setOffsetY(17);
//        Graphic graphic = new Graphic(geometry, pictureMarkerSymbol, null);
//        mGLayer.addGraphic(graphic);
//    }
//
//    private void center2Map() {
//        mapView.centerAt(currDetailAddress.getY(), currDetailAddress.getX(), true);
//        currMsg = currDetailAddress.getDetailAddress();
//        currOnClickListener = onAddressSelectedListener;
//        mapView.postDelayed(() -> {
//            showCalloutMessage(currMsg, null, currOnClickListener);
//            resetCurrCallParameters();
//        }, 200);
//    }
//
//    private DetailAddress convert2DetailAddress(AMFindResult result) {
//        final DetailAddress address = new DetailAddress();
//        final Geometry geometry = result.getGeometry();
//        if (geometry instanceof Point) {
//            address.setX(((Point) geometry).getX());
//            address.setY(((Point) geometry).getY());
//        }
//        String mpwzhm = AMFindResultHelper.getStringAttributeValue(result, "MPWZHM", "");
//        address.setDetailAddress(mpwzhm);
//        return address;
    }

    private void resetCurrCallParameters() {
        currMsg = "";
        currOnClickListener = null;
    }

    // ==============================自定义类==============================

//    private static class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//        private final List<String> mDatas = new ArrayList<>();
//        private int selectedIndex = -1;
//
//        private int selectedTextSize = 15;
//        private int unselectedTextSize = 13;
//        private int selectedColor;
//        private int unselectedColor;
//
//        public SimpleRecyclerViewAdapter() {
//            selectedColor = ColorExKt.toColor("#1A7AFE", Color.WHITE);
//            unselectedColor = ColorExKt.toColor("#333333", Color.WHITE);
//        }
//
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return new RecyclerView.ViewHolder(createItemView(parent.getContext())) {
//            };
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//            if (holder.itemView instanceof TextView) {
//                final TextView itemView = (TextView) holder.itemView;
//                itemView.setText(mDatas.get(position));
//                final int textSize = position == selectedIndex ? selectedTextSize : unselectedTextSize;
//                final int textColor = position == selectedIndex ? selectedColor : unselectedColor;
//                itemView.setTextSize(textSize);
//                itemView.setTextColor(textColor);
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return mDatas.size();
//        }
//
//        // ==============================自定义方法==============================
//
//        public void update(List<String> newDatas) {
//            final int initItemCount = mDatas.size();
//            mDatas.clear();
//            if (newDatas == null || newDatas.size() == 0) {
//                notifyItemRangeRemoved(0, initItemCount);
//            } else {
//                mDatas.addAll(newDatas);
//                notifyItemRangeChanged(0, mDatas.size());
//            }
//        }
//
//        public void setSelected(int position) {
//            if (position != selectedIndex) {
//                final int lastSelectedIndex = selectedIndex;
//                selectedIndex = position;
//                notifyItemChanged(lastSelectedIndex);
//                notifyItemChanged(selectedIndex);
//            }
//        }
//
//        public int getSelected() {
//            return selectedIndex;
//        }
//
//        public void reset() {
//            selectedIndex = -1;
//        }
//
//        private View createItemView(Context context) {
//            final TextView tv = new TextView(context);
//            tv.setGravity(Gravity.CENTER_VERTICAL);
////            tv.setTextSize(13);
////            tv.setTextColor(unselectedColor);
//            int i = DimensionExKt.dp2px(10);
//            tv.setPadding(i, i, 0, i);
//            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            return tv;
//        }
//    }

}
