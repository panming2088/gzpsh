package com.augurit.agmobile.patrolcore.common.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.mapengine.common.baidutransform.pointer.BDPointer;
import com.augurit.agmobile.mapengine.common.baidutransform.pointer.WGSPointer;
import com.augurit.agmobile.mapengine.common.widget.rotateview.RotateManager;
import com.augurit.agmobile.mapengine.common.widget.scaleview.MapScaleView;
import com.augurit.agmobile.mapengine.map.geometry.LatLng;
import com.augurit.agmobile.patrolcore.R;
import com.augurit.agmobile.patrolcore.common.model.LocationInfo;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.agmobile.patrolcore.selectlocation.service.SelectLocationService;
import com.augurit.agmobile.patrolcore.selectlocation.util.PatrolLocationManager;
import com.augurit.am.cmpt.common.Callback1;
import com.augurit.am.cmpt.permission.PermissionsUtil;
import com.augurit.am.fw.utils.log.LogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.tasks.geocode.Locator;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * ???????????????????????????????????????
 * Created by xcl on 2017/10/27.
 */

public class LocationButton extends LinearLayout {
    //??????Graphic???id
    private int locationGraphicId = -1;
    private MapView mapView;
    private boolean ifUseArcGis = false;
    private boolean ifUseBaidu = true;
    private PatrolLocationManager mLocationManager;
    /**
     * ????????????????????????
     */
    private boolean isFirstLocate = true;
    private boolean isOnceLocation = true;

    /**
     * ???????????????????????????"????????????"???callout
     */
    private boolean ifShowCallout = false;
    private GraphicsLayer mGLayerFroDrawLocation;

    /**
     * ????????????????????????
     */
    private boolean ifInFollowMode = false;

    private boolean rotateEnable = false;  //????????????????????????

    private OnStateChangedListener mOnStateChangedListener;

    /**
     * ?????????????????????????????????
     */
    private LocationInfo mLastLocation;
    private ImageView iv_location;

    private boolean ismapview;

    /**
     * ???????????????????????????????????????????????????????????????????????????
     */
    private boolean ifAlwaysCeterToLocation = false;

    /**
     * ????????????????????????????????????????????????????????????
     */
    private boolean ifDrawLocation = true;
    /**
     * ????????????
     */
    private LocationClient mLocClient;
    private BDLocationListener mBDLocationListener;

    private BDLocation mPreBDLocation;
    private float mPreBDRadius;//???????????????????????????
    private final static int MIN_DISTANCE = 2;
    private final static int MIN_RADIUS = 100;
    private MapScaleView mapScaleView;

    private RotateManager mapRotateManager;

    public LocationButton(Context context) {
        super(context);
        mapRotateManager = RotateManager.getInstance(context.getApplicationContext());
        mapRotateManager.addOnRotateChangedListener(this, new RotateManager.OnRotateChangedListener() {
            @Override
            public void onRotateChanged(float direction) {
                if(mapView != null
                        && mLastLocation != null
                        && inState == State.rotate){
                    mapView.setRotationAngle(direction, new Point(mLastLocation.getPoint().getX(), mLastLocation.getPoint().getY()), true);
                }
            }
        });
    }

    public LocationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_location_button, this, true);
        iv_location = (ImageView) findViewById(R.id.iv_location);
        findViewById(R.id.ll_location).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                followLocation();
            }
        });
        mapRotateManager = RotateManager.getInstance(context.getApplicationContext());
        mapRotateManager.addOnRotateChangedListener(this, new RotateManager.OnRotateChangedListener() {
            @Override
            public void onRotateChanged(float direction) {
                if(mapView != null
                        && mLastLocation != null
                        && inState == State.rotate){
                    mapView.setRotationAngle(direction, new Point(mLastLocation.getPoint().getX(), mLastLocation.getPoint().getY()), true);
                }
            }
        });
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }
    public void setScaleView(MapScaleView mapScaleView){
        this.mapScaleView = mapScaleView;
    }

    public void setOnceLocation(boolean onceLocation) {
        isOnceLocation = onceLocation;
    }

    public void setIfAlwaysCeterToLocation(boolean ifAlwaysCeterToLocation) {
        this.ifAlwaysCeterToLocation = ifAlwaysCeterToLocation;
    }

    public enum State {
        follow(R.mipmap.common_ic_location_follow2),   //??????
        normal(R.mipmap.common_ic_target),    //?????????
        rotate(R.mipmap.fd_blue_point),;   //????????????

        State(int resid) {
            this.resid = resid;
        }

        public int getResid() {
            return resid;
        }

        private int resid;
    }

    State inState = State.normal;

    public void setState() {
        switch (inState) {
            case follow:
                inState = State.rotate;
                break;
            case normal:
                // ????????????
                inState = State.follow;
                checkLocationPermission();
                mapView.setScale(PatrolLayerPresenter.initScale);
                if(mapScaleView != null) {
                    mapScaleView.setScale(PatrolLayerPresenter.initScale);
                }
                break;
            case rotate:
                inState = State.normal;
                isFirstLocate = true;
                stopOrPauseLocate();
                break;
        }

        iv_location.setImageResource(inState.getResid());

        if(mOnStateChangedListener != null){
            mOnStateChangedListener.onStateChanged(inState);
        }
    }

    public void setStateWithoutRotate() {
        switch (inState) {
            case follow:
                inState = State.normal;
                isFirstLocate = true;
                stopOrPauseLocate();
                break;
            case normal:
                // ????????????
                inState = State.follow;
                checkLocationPermission();
                mapView.setScale(PatrolLayerPresenter.initScale);
                if(mapScaleView != null) {
                    mapScaleView.setScale(PatrolLayerPresenter.initScale);
                }
                break;
            default:
                break;
        }

        iv_location.setImageResource(inState.getResid());

        if(mOnStateChangedListener != null){
            mOnStateChangedListener.onStateChanged(inState);
        }
    }

    /**
     * ????????????????????????
     */
    public void followLocation() {
        //?????????  ?????????
        LogUtil.d("followLocation=======================");
        if(rotateEnable){
            setState();
        } else {
            setStateWithoutRotate();
        }

        return;
        //---------
    /*    if (ifInFollowMode) {
            stopOrPauseLocate();
            ifInFollowMode = false;
            iv_location.setImageResource(R.mipmap.common_ic_target);
        } else if (!ifInFollowMode) {
            ifInFollowMode = true;
            isFirstLocate = true;
            checkLocationPermission();
            iv_location.setImageResource(R.mipmap.common_ic_location_follow);
        }*/
    }

    public void setRotateEnable(boolean rotateEnable) {
        this.rotateEnable = rotateEnable;
    }

    public void setStateNormal(){
        inState = State.normal;
        isFirstLocate = true;
        stopOrPauseLocate();

        iv_location.setImageResource(inState.getResid());
        if(mOnStateChangedListener != null){
            mOnStateChangedListener.onStateChanged(inState);
        }
    }

    /**
     * ???????????????????????????
     */
    public void checkLocationPermission() {
        PermissionsUtil.getInstance()
                .requestPermissions(
                        (Activity) getContext(),
                        true,
                        new PermissionsUtil.OnPermissionsCallback() {
                            @Override
                            public void onPermissionsGranted(List<String> perms) {
                                startOrResumeLocate();
                            }

                            @Override
                            public void onPermissionsDenied(List<String> perms) {
                                stopOrPauseLocate();

                                //---------------point
//                                setState();
//                                ifInFollowMode = false;
                                inState = State.normal;
                                iv_location.setImageResource(R.mipmap.common_ic_target);
                                ToastUtil.shortToast(getContext(), "???????????????????????????????????????????????????");
                            }
                        },
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }


    /**
     * ????????????
     */
    public void stopOrPauseLocate() {
        if (ifUseArcGis && mLocationManager != null) {
            mLocationManager.pause();
        } else if (ifUseBaidu && mLocClient != null) {
            mLocClient.stop();
        } else {
            stopLocate(false);
        }
        //  ifInFollowMode = false;
        inState = State.normal;
        iv_location.setImageResource(R.mipmap.common_ic_target);
    }


    public void startOrResumeLocate() {
        if (ifUseArcGis && mLocationManager != null) {
            mLocationManager.resume();
        } else if (ifUseBaidu) {
            initBaiduLocation();
            startLocateByBaidu();
        } else {
            startLocate();
        }
        //   ifInFollowMode = true;
        inState = State.follow;
        isFirstLocate = true;
        iv_location.setImageResource(R.mipmap.common_ic_location_follow);
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    public boolean ifLocating() {
        return inState != State.normal;
    }

    /**
     * ????????????????????????????????????????????????84?????????
     */
    public void setUseArcgisLocation() {
        ifUseArcGis = true;
    }

    public void setIfShowCallout(boolean ifShowCallout) {
        this.ifShowCallout = ifShowCallout;
    }

    /**
     * ????????????
     */
    public void startLocate() {
        if (mLocationManager == null) {
            if (ifUseArcGis && mapView != null) {
                mLocationManager = new PatrolLocationManager(getContext(), mapView);
                mLocationManager.setUseArcGisForLocation();
            } else {
                mLocationManager = new PatrolLocationManager(getContext(), null);
            }
            PatrolLocationManager.setDefaultMinDistance(0);
            PatrolLocationManager.setDefaultMinTime(10000);
        }
        if (mLocationManager != null) {
            mLocationManager.startLocate(new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {

//                    if (mGLayerFroDrawLocation != null){
//                        mGLayerFroDrawLocation.removeAll();
//                    }

                    /**
                     * ????????????arcgis????????????????????????????????????????????????????????????
                     */
                    if (!ifUseArcGis && mapView != null && ifDrawLocation) {
                        drawLocationOnMap(location);
                    }

                    final Point point = new Point(location.getLongitude(), location.getLatitude());
                    if (mapView == null || mapView.getMaxExtent() == null || mapView.getSpatialReference() == null) {
                        LocationInfo locationInfo = new LocationInfo();
                        locationInfo.setLocation(location);
                        locationInfo.setPoint(point);
                        mLastLocation = locationInfo;
                        return;
                    }

                    if (GeometryEngine.contains(mapView.getMaxExtent(), point, mapView.getSpatialReference())) {
                        if (ifShowCallout) {
                            Callout callout = mapView.getCallout();
                            TextView textView = new TextView(getContext());
                            textView.setText("??????????????????");
                            callout.setContent(textView);
                            callout.show(point); //????????????
                        }

                        if (ifAlwaysCeterToLocation) {
                            mapView.centerAt(point, true);
                        } else if (isFirstLocate) {
                            mapView.centerAt(point, true);
                        }
                        isFirstLocate = false;
                        if (isOnceLocation) {
                            isOnceLocation = false;
                            followLocation();
                        }
                        requestLocation(point, mapView.getSpatialReference(), new Callback1<DetailAddress>() {
                            @Override
                            public void onCallback(DetailAddress detailAddress) {
                                LocationInfo locationInfo = new LocationInfo();
                                locationInfo.setDetailAddress(detailAddress);
                                locationInfo.setLocation(location);
                                locationInfo.setPoint(point);
                                mLastLocation = locationInfo;
                            }
                        });
                    } else {
                        ToastUtil.shortToast(getContext(), "?????????????????????????????????");
                        LocationInfo locationInfo = new LocationInfo();
                        locationInfo.setLocation(location);
                        locationInfo.setPoint(point);
                        mLastLocation = locationInfo;
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

    }

    public void initBaiduLocation() {
        if (mLocClient == null) {
            /**
             * LocationClientOption ????????????????????????SDK??????????????????
             */
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true); //??????GPRS
            option.setAddrType("all");//???????????????????????????????????????
            option.setCoorType("bd09ll");//???????????????????????????????????????,?????????gcj02
            option.setPriority(LocationClientOption.GpsFirst); // ??????GPS??????
            option.setScanSpan(3000); //??????????????????????????????????????????3s
            option.disableCache(true);//??????????????????
            mLocClient = new LocationClient(getContext().getApplicationContext());
            mBDLocationListener = new BDLocationListener(this);
            mLocClient.registerLocationListener(mBDLocationListener);
            //??????????????????
            mLocClient.setLocOption(option);
        }
    }


    public void startLocateByBaidu() {
        if (mPreBDLocation != null) {
            BDPointer bdPointer = new BDPointer(mPreBDLocation.getLatitude(), mPreBDLocation.getLongitude());
            WGSPointer wgsPointer = bdPointer.toWGSPointer();

            Location location = new Location("baidu");
            location.setLatitude(wgsPointer.getLatitude());
            location.setLongitude(wgsPointer.getLongtitude());

            LocationInfo locationInfo = new LocationInfo();
            locationInfo.setLocation(location);
            Point point = new Point(location.getLongitude(), location.getLatitude());
            locationInfo.setPoint(point);

            DetailAddress detailAddress = new DetailAddress();
            detailAddress.setDetailAddress(mPreBDLocation.getAddrStr());
            detailAddress.setStreet(mPreBDLocation.getAddress().street);
            locationInfo.setDetailAddress(detailAddress);

            mLastLocation = locationInfo;
            drawLocationOnMap(location);
            //?????????????????????????????????????????????
//            if (GeometryEngine.contains(mapView.getMaxExtent(),point,mapView.getSpatialReference())){
//                mapView.centerAt(point, true);
//            }else {
//                ToastUtil.shortToast(getContext(),"????????????????????????????????????");
//            }
            mapView.centerAt(point, true);

        }
        mLocClient.start();//???????????????????????????
        if (mLocClient != null && mLocClient.isStarted()) {
            mLocClient.requestLocation();
        }
    }

    /**
     * ??????????????????????????????
     */
    private void stopLocate(boolean ifRemoveLocationPoint) {
        if (mLocationManager != null) {
            mLocationManager.stopLocate();
        }
        if (ifRemoveLocationPoint) {
            if (mGLayerFroDrawLocation != null) {
                mGLayerFroDrawLocation.removeAll();
            }
        }
    }

    /**
     * ??????????????????????????????
     */
    private void stopBaiduLocate(boolean ifRemoveLocationPoint) {
        if (mLocClient != null) {
            mLocClient.stop();
        }
        if (ifRemoveLocationPoint) {
            if (mGLayerFroDrawLocation != null) {
                mGLayerFroDrawLocation.removeAll();
            }
        }
    }


    /**
     * ?????????????????????arcgis?????????????????????????????????
     *
     * @param point     ????????????????????????Location?????????parseLocation????????????????????????arcgis?????????????????????
     * @param callback1
     */
    public void requestLocation(Point point, SpatialReference spatialReference, final Callback1<DetailAddress> callback1) {
        SelectLocationService selectLocationService = new SelectLocationService(getContext().getApplicationContext(),
                Locator.createOnlineLocator());
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
     * ??????????????????????????????
     *
     * @param location
     * @return
     */
    protected void drawLocationOnMap(Location location) {
        if (mGLayerFroDrawLocation == null) {
            mGLayerFroDrawLocation = new GraphicsLayer();
            mapView.addLayer(mGLayerFroDrawLocation);
        }
        Point point = new Point(location.getLongitude(), location.getLatitude());
        if (locationGraphicId != -1) {
            //??????????????????
            // Graphic graphic = new Graphic(point, pictureMarkerSymbol);
            mGLayerFroDrawLocation.updateGraphic(locationGraphicId, point);
        } else {
            //???????????????
            PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(getContext(),
                    getContext().getResources().getDrawable(R.mipmap.patrol_location_symbol));
            mGLayerFroDrawLocation.removeAll();
            Graphic graphic = new Graphic(point, pictureMarkerSymbol);
            locationGraphicId = mGLayerFroDrawLocation.addGraphic(graphic);
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLocate(true);
        stopBaiduLocate(true);
        if (mapView != null && !mapView.isRecycled()) {
            try {
                mapView.removeLayer(mGLayerFroDrawLocation);
                mapView.recycle();
                mapRotateManager.removeListener(this);
            } catch (Exception e) {

            }
        }
    }

    public LocationInfo getLastLocation() {
        return mLastLocation;
    }

    public void setIfDrawLocation(boolean ifDrawLocation) {
        this.ifDrawLocation = ifDrawLocation;
    }

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener){
        this.mOnStateChangedListener = onStateChangedListener;
    }


    static class BDLocationListener extends BDAbstractLocationListener {

        private WeakReference<LocationButton> locationButton;

        public BDLocationListener(LocationButton locationButton) {
            this.locationButton = new WeakReference<>(locationButton);
        }

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            boolean isGps = false;
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                //GPS??????
                        /*
                        if(bdLocation.getSpeed() >0){

                        }

                        ToastUtil.shortToast(getContext(),"???????????????GPS??????");
                        */
                isGps = true;

            }//else if(bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
            //????????????
            //   ToastUtil.shortToast(getContext(),"???????????????????????????");

            // bdLocation.getRadius()
            //  isGps = false;
            // }

            if (locationButton.get() == null) {
                return;
            }

            if (locationButton.get().mPreBDLocation == null) {
                locationButton.get().mPreBDLocation = bdLocation;
                locationButton.get().mPreBDRadius = bdLocation.getRadius();
            } else {
                BDPointer pre = new BDPointer(locationButton.get().mPreBDLocation.getLatitude(), locationButton.get().mPreBDLocation.getLongitude());
                BDPointer cur = new BDPointer(bdLocation.getLatitude(), bdLocation.getLongitude());
                float curRadius = bdLocation.getRadius();

                if (cur.distance(pre) < MIN_DISTANCE) {
                    //  ToastUtil.shortToast(getContext(),"????????????????????????,?????????");
                    return;
                }

                        /*
                        if(curRadius > mPreBDRadius){
                            ToastUtil.longToast(getContext(),"?????????????????????????????????,?????????!");
                            return;
                        }
                        */
                //Gps?????????????????????
                if (isGps && curRadius > MIN_RADIUS) {
                    // ToastUtil.shortToast(getContext(),"?????????????????????100,?????????!");
                    return;
                }

                locationButton.get().mPreBDLocation = bdLocation;
                locationButton.get().mPreBDRadius = bdLocation.getRadius();
            }


            BDPointer bdPointer = new BDPointer(bdLocation.getLatitude(), bdLocation.getLongitude());
            WGSPointer wgsPointer = bdPointer.toWGSPointer();

            Location location = new Location("baidu");
            location.setLatitude(wgsPointer.getLatitude());
            location.setLongitude(wgsPointer.getLongtitude());

            LocationInfo locationInfo = new LocationInfo();
            locationInfo.setLocation(location);
            Point point = new Point(location.getLongitude(), location.getLatitude());
            locationInfo.setPoint(point);

            DetailAddress detailAddress = new DetailAddress();
            detailAddress.setDetailAddress(bdLocation.getAddrStr());
            detailAddress.setStreet(bdLocation.getAddress().street);
            locationInfo.setDetailAddress(detailAddress);

            locationButton.get().mLastLocation = locationInfo;
            locationButton.get().drawLocationOnMap(location);
            //?????????????????????????????????????????????
//                    if (GeometryEngine.contains(mapView.getMaxExtent(),point,mapView.getSpatialReference())){
//                        mapView.centerAt(point, true);
//                    }else {
//                        ToastUtil.shortToast(getContext(),"????????????????????????????????????");
//                    }
            locationButton.get().mapView.centerAt(point, true);
        }
    }

    public interface OnStateChangedListener{
        void onStateChanged(State state);
    }
}
