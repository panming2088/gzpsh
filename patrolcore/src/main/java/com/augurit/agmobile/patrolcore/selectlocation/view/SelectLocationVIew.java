package com.augurit.agmobile.patrolcore.selectlocation.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.bluetooth.BluetoothDataManager;
import com.augurit.agmobile.bluetooth.DataCallback;
import com.augurit.agmobile.bluetooth.model.LocationData;
import com.augurit.agmobile.bluetooth.model.MeasureData;
import com.augurit.agmobile.mapengine.common.GeographyInfoManager;
import com.augurit.agmobile.mapengine.layermanage.view.ILayerPresenter;
import com.augurit.agmobile.mapengine.layermanage.view.ILayerView;
import com.augurit.agmobile.mapengine.layermanage.view.LayerView;
import com.augurit.agmobile.mapengine.location.ILocationManager;
import com.augurit.agmobile.mapengine.map.geometry.LatLng;
import com.augurit.agmobile.patrolcore.R;
import com.augurit.agmobile.patrolcore.common.MapLocateEvent;
import com.augurit.agmobile.patrolcore.common.widget.LocationButton;
import com.augurit.agmobile.patrolcore.layer.service.PatrolLayerService;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerView;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerView2;
import com.augurit.agmobile.patrolcore.layerdownload.activity.LayerDownloadActivity;
import com.augurit.agmobile.patrolcore.selectlocation.model.Ease;
import com.augurit.agmobile.patrolcore.selectlocation.model.EasingInterpolator;
import com.augurit.agmobile.patrolcore.selectlocation.model.OnSelectLocationFinishEvent;
import com.augurit.agmobile.patrolcore.selectlocation.util.SelectLocationConstant;
import com.augurit.am.fw.common.BaseView;
import com.augurit.am.fw.utils.DeviceUtil;
import com.augurit.am.fw.utils.log.LogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.Symbol;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * @author ????????? ???xuciluan
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.patrolcore.selectlocation.view
 * @createTime ???????????? ???17/7/27
 * @modifyBy ????????? ???xuciluan
 * @modifyTime ???????????? ???17/7/27
 * @modifyMemo ???????????????
 */

public class SelectLocationVIew extends BaseView<ISelectLocationPresenter> implements ISelectLocationView {

    public static final String TAG = "????????????--??????";

    private TextView mTv_address;
    private ImageView mIv_location;
    private TextView mTv_address_poi;
    private MapView mMapView;
    private TextView mBtn_finish_select_point; //"????????????"
    private TextView mBtn_go_to_destination;  //"????????????"
    private View mLl_target;    // ???????????????
    private TextView mTv_title;
    private ViewGroup mContainer; //??????
    //private ImageButton ib_location;
    private View btn_back; //????????????
    private View ib_zoom_in;
    private View ib_zoom_out;
    private View tv_map_manage;


    /**
     * ????????????????????????
     */
    protected boolean mIfReadOnly;
    /**
     * ???????????????????????????
     */
    protected LatLng mDestinationOrLastTimeSelectLocation;
    /**
     * ?????????????????????
     */
    protected String mLastSelectedAddress;
    /**
     * ????????????????????????
     */
    protected boolean ifInFollowMode = false;

    /**
     * ????????????????????????
     */
    private boolean isFirstLocate = true;
    /**
     * ???????????????????????????
     */
    private double mInitialScale = -1;

    private GraphicsLayer mGraphicsLayer;   // ???????????????
    private int mTargetGraphicId = -1;   // ?????????graphic Id
    private GraphicsLayer mOfflineGraphicsLayer;   // ????????????
    /**
     * ???????????????
     */
    protected ILocationManager mLocationManager;
    private GraphicsLayer mGLayerFroDrawLocation;

    /**
     * ???????????????????????????????????????
     */
    protected boolean ifStartLocateWhenMapLoaded = true;

    protected IFinishSelectLocationListener mFinishSelectLocationListener;
    private View mRoot;
    private ILayerPresenter layerPresenter;
    private LocationButton locationButton;
    private ILayerView layerView;

    /**
     * ????????????
     *
     * @param context
     * @param container
     * @param locationManager
     */
    public SelectLocationVIew(Context context, ViewGroup container, ILocationManager locationManager) {
        super(context);
        EventBus.getDefault().register(this);//??????
        this.mContainer = container;
        this.mLocationManager = locationManager;
        initView();
        initListener();
    }

    @Override
    public void setFinishSelectLocationListener(IFinishSelectLocationListener finishSelectLocationListener) {
        this.mFinishSelectLocationListener = finishSelectLocationListener;
    }

    @Override
    public void turnOffStartLocateWhenMapLoaded() {
        ifStartLocateWhenMapLoaded = false;
    }

    private void initListener() {
//        //????????????
//        ib_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                followLocation(ib_location);
//            }
//        });

        //????????????
        mLl_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateTarget();
            }
        });

        //????????????
        mBtn_finish_select_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mGLayerFroDrawLocation != null) {
                    mGLayerFroDrawLocation.removeAll();//??????????????????????????????????????????
                    mMapView.getCallout().hide(); //??????callout????????????????????????
                }

                //?????????????????????
                float x = DeviceUtil.getScreenWidth(mContext) / 2;
                float y = DeviceUtil.getScreenHeight(mContext) / 2 - mIv_location.getHeight() / 3;

                Point point = mMapView.toMapPoint(x, y);

                if (point != null) {
                    LogUtil.d(TAG, "???????????????????????????" + point.getX() + ":" + point.getY());
                } else {
                    LogUtil.e(TAG, "??????????????????????????????");
                }

                /**
                 * ???????????????{@link MapTableItemPresenter#onReceivedSelectLocationFinishEvent(OnSelectLocationFinishEvent)}}
                 */
                OnSelectLocationFinishEvent onSelectLocationFinishEvent = new OnSelectLocationFinishEvent(new LatLng(point.getY(), point.getX()), mMapView.getScale(), mTv_address.getText().toString());
                EventBus.getDefault().post(onSelectLocationFinishEvent);

                if (mFinishSelectLocationListener != null) {
                    mFinishSelectLocationListener.onFinish(onSelectLocationFinishEvent);
                }
            }
        });


        mBtn_go_to_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.shortToast(mContext, "????????????????????????");
            }
        });

        mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (status == STATUS.INITIALIZED && isFirstLocate) { //????????????????????????????????????
                    //TODO ??????????????????????????????????????????
//                    GeographyInfoManager.init(mMapView);
                    //??????????????????????????????????????????
                    drawDestinationOnMap(mIv_location, mMapView);
                    addDrawLocationGraphicLayer();

                    if (ifStartLocateWhenMapLoaded && mDestinationOrLastTimeSelectLocation == null) {
                        startLocate();
                    }

                    if (mInitialScale == -1 || mInitialScale == 0) {
                        mMapView.setScale(PatrolLayerPresenter.initScale);
                    }

                    if (!TextUtils.isEmpty(PatrolLayerService.MAP_EXTENT)) {
                        String[] extents = PatrolLayerService.MAP_EXTENT.split(",");
                        double[] longExtents = new double[extents.length + 1];
                        for (int j = 0; j < extents.length; j++) {
                            longExtents[j] = Double.valueOf(extents[j]);
                        }
                        Envelope envelope = new Envelope(longExtents[0], longExtents[1], longExtents[2], longExtents[3]);
                        mMapView.setMaxExtent(envelope);
                    }

                } else if (status == STATUS.INITIALIZED) {
                    //TODO ??????????????????????????????????????????
//                    GeographyInfoManager.init(mMapView);
                }
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFinishSelectLocationListener != null) {
                    mFinishSelectLocationListener.onFinish(null);
                }
            }
        });

        //??????????????????
        ib_zoom_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapView.zoomin();

            }
        });

        ib_zoom_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapView.zoomout();
            }
        });

        //????????????
        tv_map_manage = mRoot.findViewById(R.id.tv_map_manager);
        tv_map_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //???????????????????????????
                GeographyInfoManager.getInstance().setExtent(mMapView.getExtent());
                Intent intent = new Intent(mContext, LayerDownloadActivity.class);
                mContext.startActivity(intent);
            }
        });

    }


    private void addDrawLocationGraphicLayer() {
        mGLayerFroDrawLocation = new GraphicsLayer();
        mMapView.addLayer(mGLayerFroDrawLocation);
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param iv_location
     * @param mapView
     */
    private void drawDestinationOnMap(ImageView iv_location, MapView mapView) {

        if (mDestinationOrLastTimeSelectLocation != null) {
            Point point = new Point(mDestinationOrLastTimeSelectLocation.getLongitude(), mDestinationOrLastTimeSelectLocation.getLatitude());

            if (mIfReadOnly) { //???????????????????????????(?????????)????????????????????????????????????????????????????????????
                iv_location.setVisibility(View.GONE);
                GraphicsLayer graphicsLayer = new GraphicsLayer();
                mapView.addLayer(graphicsLayer);
                Drawable drawable = ResourcesCompat.getDrawable(mContext.getResources(), R.mipmap.ic_select_location, null);
                Symbol symbol = new PictureMarkerSymbol(mContext, drawable);    // xjx ????????????api19???????????????drawable
                Graphic graphic = new Graphic(point, symbol);
                graphicsLayer.addGraphic(graphic);
                mMapView.centerAt(point, true);
            } else { //????????????????????????????????????
                Point mapPoint = new Point(mDestinationOrLastTimeSelectLocation.getLongitude(),
                        mDestinationOrLastTimeSelectLocation.getLatitude());
                mMapView.centerAt(mapPoint, true);
                //     mMapView.setScale(mMapView.getMaxScale());
            }
            zoomToBestScale();
            if (!TextUtils.isEmpty(mLastSelectedAddress)) {
                mTv_address.setText(mLastSelectedAddress);
            }
        } else {
            // 2017-09-22 ?????????????????? ????????????????????????????????????
            zoomToBestScale();
        }
    }

    /**
     * ?????????????????????
     */
    private void zoomToBestScale() {

        if (mInitialScale != -1) {
            mMapView.setScale(mInitialScale);
        }
    }


    /**
     * ???????????????
     */
    private void locateTarget() {
        final BluetoothDataManager manager = BluetoothDataManager.getInstance(mContext);
        if (manager.getConnectedDevice() == null) {
            ToastUtil.shortToast(mContext, "????????????????????????");
            return;
        }
        // ????????????
        ToastUtil.shortToast(mContext, "?????????????????????????????????????????????");
        manager.requestMeasure(new DataCallback<MeasureData>() {
            @Override
            public void onCallback(MeasureData measureData) {
                // ???????????????
                manager.requestLocation(LocationData.POINT_TARGET, LocationData.DATA_BLH, new DataCallback<LocationData>() {
                    @Override
                    public void onCallback(LocationData locationData) {
                        // ?????????????????????
                        ToastUtil.shortToast(mContext, "??????????????????");
                        Location location = new Location("BluetoothProvider");
                        location.setLatitude(locationData.getLatitude());
                        location.setLongitude(locationData.getLongitude());
                        if (mLocationManager != null) {
                            mLocationManager.setMapView(mMapView);
                            location = mLocationManager.changeWGS84ToCurrentCoordinate(location);
                            drawTargetOnMap(location);
                        }

                    }

                    @Override
                    public void onError(int i, Exception e) {
                        e.printStackTrace();
                        LogUtil.e("SelectLocationActivity", "?????????????????????????????????:" + i);
                        ToastUtil.shortToast(mContext, "?????????????????????");
                    }
                });
            }

            @Override
            public void onError(int i, Exception e) {
                e.printStackTrace();
                LogUtil.e("SelectLocationActivity", "????????????????????????:" + i);
                ToastUtil.shortToast(mContext, "????????????????????????");
            }
        });
    }


    /**
     * ?????????????????????
     *
     * @param target ?????????location
     */
    private void drawTargetOnMap(Location target) {
        if (mGraphicsLayer == null) {
            mGraphicsLayer = new GraphicsLayer();
            mMapView.addLayer(mGraphicsLayer);
        }
        Point point = new Point(target.getLongitude(), target.getLatitude());
        if (mTargetGraphicId == -1) {
            int color = ResourcesCompat.getColor(mContext.getResources(), R.color.agmobile_blue, null);
            SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(color, 15, SimpleMarkerSymbol.STYLE.X);
            Graphic graphic = new Graphic(point, symbol);
            mTargetGraphicId = mGraphicsLayer.addGraphic(graphic);
        } else {
            mGraphicsLayer.updateGraphic(mTargetGraphicId, point);
        }
        mMapView.centerAt(point, true);
    }

    /**
     * ??????????????????
     *
     * @param iv_location
     */
    protected void followLocation(ImageButton iv_location) {
        if (ifInFollowMode) {
            stopLocate();
            ifInFollowMode = false;
            iv_location.setImageResource(R.mipmap.common_ic_target);
        } else {
            ifInFollowMode = true;
            isFirstLocate = false;
            startLocate();
            iv_location.setImageResource(R.mipmap.common_ic_location_follow);
        }
    }

    public void startLocate() {
        if (locationButton != null) {
            locationButton.followLocation();
        }
    }


//    /**
//     * ????????????
//     */
//    public void startLocate() {
//
//        if (mLocationManager != null){
//            this.mLocationManager.setMapView(mMapView);
//            mLocationManager.startLocate(new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    // LogUtil.e(TAG,"????????????????????????"+location.getLatitude() +"-->"+ location.getLongitude());
//                    // Point point = drawLocationOnMap(location); //??????????????????
//                   /* if (mLocationManager.getMapView() == null){
//                        drawLocationOnMap(location);
//                    }*/
//                    drawLocationOnMap(location);
//                    Point point = new Point(location.getLongitude(),location.getLatitude());
//                    if (mMapView.getMaxExtent() == null || mMapView.getSpatialReference() == null){
//                        return;
//                    }
//                    if (GeometryEngine.contains(mMapView.getMaxExtent(),point,mMapView.getSpatialReference())){
//                        Callout callout = mMapView.getCallout();
//                        TextView textView = new TextView(mContext);
//                        textView.setText("??????????????????");
//                        callout.setContent(textView);
//                        callout.show(point); //????????????
//                        if (isFirstLocate){
//                            // mapView.setScale(mapView.getMaxScale());
//                            isFirstLocate = false;
//                            if (mDestinationOrLastTimeSelectLocation == null){//?????????????????????????????????????????????????????????????????????????????????
//                                if (mPresenter != null){
//                                    mPresenter.requestLocation(location.getLongitude(),location.getLatitude(),mMapView.getSpatialReference());
//                                }
//                                //requestAddress(location.getLatitude(),location.getLongitude());
//                                mMapView.centerAt(point,true);
//                            }
//                            stopLocate(); //?????????????????????????????????????????????????????????????????????????????????
//                        }
//                    }else {
//                        ToastUtil.shortToast(mContext,"?????????????????????????????????");
//                        stopLocate(); //?????????????????????????????????????????????????????????????????????????????????
//                    }
//
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String provider) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String provider) {
//
//                }
//            });
//        }
//    }

    /**
     * ??????????????????????????????
     *
     * @param location
     * @return
     */
    private void drawLocationOnMap(Location location) {
        Point point = new Point(location.getLongitude(), location.getLatitude());
        mGLayerFroDrawLocation.removeAll();
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(mContext,
                mContext.getResources().getDrawable(R.mipmap.patrol_location_symbol));
        Graphic graphic = new Graphic(new Point(location.getLongitude(), location.getLatitude()), pictureMarkerSymbol);
        mGLayerFroDrawLocation.addGraphic(graphic);
    }

    /**
     * ????????????
     */
    @Override
    public void stopLocate() {
        if (mLocationManager != null) {
            mLocationManager.stopLocate();
        }
    }

    @Override
    public void addViewToContaner() {
        mContainer.removeAllViews();
        mContainer.addView(mRoot);
    }


    protected void initView() {

        mRoot = View.inflate(mContext, R.layout.activity_map_patrol, null);

        mTv_address = (TextView) mRoot.findViewById(R.id.tv_map_address);
        mTv_address_poi = (TextView) mRoot.findViewById(R.id.tv_map_address_poi);

        mTv_title = (TextView) mRoot.findViewById(R.id.tv_title);
        //????????????
        mIv_location = (ImageView) mRoot.findViewById(R.id.iv_location);

        mMapView = (MapView) mRoot.findViewById(R.id.mapview);
        mMapView.setMapBackground(Color.WHITE, Color.TRANSPARENT, 0f, 0f);//???????????????????????????????????????

        //????????????????????????
        mBtn_go_to_destination = (TextView) mRoot.findViewById(R.id.btn_go_to_destination);

        //????????????????????????
        mBtn_finish_select_point = (TextView) mRoot.findViewById(R.id.btn_finish_select_point);

        mLl_target = mRoot.findViewById(R.id.ll_btn_target);

        if (!SelectLocationConstant.ifLocateByBlueTooth) {
            mLl_target.setVisibility(View.GONE);
        }

        locationButton = (LocationButton) mRoot.findViewById(R.id.locationButton);
        locationButton.setMapView(mMapView);

        // ib_location = (ImageButton) mRoot.findViewById(R.id.btn_location);

        btn_back = mRoot.findViewById(R.id.btn_back);

        ib_zoom_in = mRoot.findViewById(R.id.ib_zoom_in);

        ib_zoom_out = mRoot.findViewById(R.id.ib_zoom_out);
    }


    /**
     * ????????????
     */
    @Override
    public void loadMap() {
        layerView = new PatrolLayerView2(mContext, mMapView, null);
        mMapView.setOnTouchListener(new MapOnTouchListener(mContext, mMapView) {

            private boolean shouldBounce = false; //??????????????????

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        shouldBounce = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (shouldBounce) {
                            doBounceAnimation(mIv_location);
                        }
                        shouldBounce = false;
                        Point point = mMapView.toMapPoint(mIv_location.getX(), mIv_location.getY());
                        if (!mIfReadOnly && point != null) {
                            if (mPresenter != null) {
                                mPresenter.requestLocation(point.getX(), point.getY(), mMapView.getSpatialReference());
                            }
                        }
                        break;
                    default:
                        break;
                }
                return super.onTouch(v, event);
            }

            @Override
            public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
                if (locationButton != null && locationButton.ifLocating()) {
                    locationButton.followLocation();
                }
                return super.onDragPointerMove(from, to);
            }
        });
        layerPresenter = new PatrolLayerPresenter(layerView);
        layerPresenter.loadLayer();
    }


    /**
     * ??????????????????
     *
     * @param targetView
     */
    private void doBounceAnimation(View targetView) {
        if (targetView.getVisibility() == View.VISIBLE) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationY", 0, -25, 0);
            animator.setInterpolator(new EasingInterpolator(Ease.ELASTIC_IN_OUT));
            animator.setDuration(1000);
            animator.start();
        }
    }


    @Override
    public void showAddress(String address) {

        mTv_address.setText(address);

        mTv_address_poi.setText(address);
    }


    /**
     * ????????????????????????????????????
     */
    @Override
    public void showAddressNotFound() {

        mTv_address.setText("????????????");
        mTv_address_poi.setText("????????????");
    }

    @Override
    public void setIfReadOnly(boolean mIfReadOnly) {
        this.mIfReadOnly = mIfReadOnly;
        if (mIfReadOnly) {
            //?????????????????????
            mBtn_finish_select_point.setVisibility(View.GONE);
            mBtn_go_to_destination.setVisibility(View.GONE);
            mIv_location.setVisibility(View.GONE);
            tv_map_manage.setVisibility(View.GONE);
            mTv_title.setText("??????????????????");
        }
    }

    @Override
    public void setDestinationOrLastTimeSelectLocation(LatLng mDestinationOrLastTimeSelectLocation) {
        this.mDestinationOrLastTimeSelectLocation = mDestinationOrLastTimeSelectLocation;
    }

    @Override
    public void setLastSelectedAddress(String mLastSelectedAddress) {
        this.mLastSelectedAddress = mLastSelectedAddress;
    }

    @Override
    public void setInitialScale(double mInitialScale) {
        this.mInitialScale = mInitialScale;
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMapLocateEvent(MapLocateEvent event) {
        //????????????
        if (mMapView != null && event.getEvelope() != null) {
//            if(mOfflineGraphicsLayer == null){
//                mOfflineGraphicsLayer = new GraphicsLayer();
//                mMapView.addLayer(mOfflineGraphicsLayer);
//            }
//            mOfflineGraphicsLayer.removeAll();
//            SimpleLineSymbol lineSymbol = new SimpleLineSymbol(Color.parseColor("#FFFF00"),
//                    2, SimpleLineSymbol.STYLE.SOLID);
//            Graphic graphicBuffer = new Graphic(event.getEvelope(), lineSymbol);
//            mOfflineGraphicsLayer.addGraphic(graphicBuffer);
            mMapView.setExtent(event.getEvelope());
        }
    }

    @Override
    public void showLayerList(ViewGroup container) {

        if (layerView != null) {
            layerView.setContainer(container);
        }

        if (layerPresenter != null) {
            layerPresenter.showLayerList();
        }
//        PatrolLayerView layerView = new PatrolLayerView(mContext,mMapView,container);
//        PatrolLayerPresenter layerPresenter = new PatrolLayerPresenter(layerView);
//        layerPresenter.showLayerList();
    }


}
