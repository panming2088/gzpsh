package com.augurit.agmobile.gzpssb.pshdoorno.doorlist.service;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.augurit.agmobile.gzpssb.bean.DoorNOBean;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.service.SewerageLayerService;
import com.augurit.agmobile.patrolcore.R;
import com.augurit.agmobile.patrolcore.editmap.widget.LocationMarker;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.esri.android.map.Callout;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;

import java.util.List;

/**
 * 模仿百度选点时的地图事件处理
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.mapengine.common
 * @createTime 创建时间 ：17/10/31
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/10/31
 * @modifyMemo 修改备注：
 */

public class SelectDoorNOTouchListener extends MapOnTouchListener {


    protected MapView mMapView;
    protected Context mContext;
    /**
     * callout中确定按钮的点击事件
     */
    protected View.OnClickListener calloutSureButtonClickListener;
    protected View.OnClickListener calloutOKButtonClickListener;

    protected LocationMarker locationMarker;

    protected boolean enableGeocoding = true;

    protected boolean mShouldRequestNewAddress = true;
    protected SewerageLayerService mSewerageLayerService;
    /**
     * 是否执行了放大缩小操作
     */
    protected boolean hasZoomBefore = false;
    /**
     * 上次选择的位置
     */
    protected Point mLastSelectedLocation = null;

    private String mLastSelectedAddress = null;

    public void setBean(DoorNOBean bean) {
        this.bean = bean;
    }

    private DoorNOBean bean;

    interface Callback<DoorNOBean> {
        void onCallback(List<DoorNOBean> beans);
    }

    /**
     * 当位置发生移动时回调的监听
     */
    protected OnSelectedLocationChangedListener onSelectedLocationChangedListener;

    public SelectDoorNOTouchListener(Context context, MapView view, LocationMarker locationMarker, boolean enableGeocoding, View.OnClickListener calloutSureButtonClickListener) {
        super(context, view);
        this.mContext = context;
        this.mMapView = view;
        this.calloutSureButtonClickListener = calloutSureButtonClickListener;
        this.locationMarker = locationMarker;
        this.enableGeocoding = enableGeocoding;
    }

    public SelectDoorNOTouchListener(Context context, MapView view, LocationMarker locationMarker, View.OnClickListener calloutSureButtonClickListener) {
        super(context, view);
        this.mContext = context;
        this.mMapView = view;
        this.calloutSureButtonClickListener = calloutSureButtonClickListener;
        this.locationMarker = locationMarker;
    }

    public void setCalloutSureButtonClickListener(View.OnClickListener calloutSureButtonClickListener) {
        this.calloutSureButtonClickListener = calloutSureButtonClickListener;
    }

    public void setCalloutOKButtonClickListener(View.OnClickListener calloutOKButtonClickListener) {
        this.calloutOKButtonClickListener = calloutOKButtonClickListener;
    }

    private Point getMapCenterPoint() {
        //获取当前的位置
        return mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //LogUtil.d("onTouch");
        if (locationMarker != null && locationMarker.getVisibility() == View.GONE) {
            return super.onTouch(v, event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mMapView.getCallout().isShowing()) {
                    mMapView.getCallout().hide();
                }
                locationMarker.startUpAnimation(null);
                // LogUtil.d("ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                centerToNewAddress(calloutSureButtonClickListener);
                /**
                 * 如果之前执行了放大缩小操作，此时不需要重新获取位置
                 */
                break;
            case MotionEvent.ACTION_CANCEL:
                centerToNewAddress(calloutSureButtonClickListener);
                break;
            default:
                break;
        }
        return super.onTouch(v, event);
    }

    public void centerToNewAddress(final View.OnClickListener calloutSureButtonClickListener) {
        locationMarker.startDownAnimation(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                locate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void locate() {
        Point point = getMapCenterPoint();
        if (point != null) {
            setLastSelectedLocation(point);
            mLastSelectedLocation = point;
            showCallout(point);


        }

    }


    @Override
    public boolean onPinchPointersDown(MotionEvent event) {
        if (event == null) {
            return true;
        }
        // LogUtil.d("onPinchPointersDown");
        mShouldRequestNewAddress = false;
        return super.onPinchPointersDown(event);
    }

    @Override
    public boolean onPinchPointersMove(MotionEvent event) {
        if (event == null) {
            return true;
        }
        //LogUtil.d("onPinchPointersMove");
        return super.onPinchPointersMove(event);
    }

    @Override
    public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
        //LogUtil.d("onDragPointerMove");
        if (from == null || to == null) {
            return true;
        }
        return super.onDragPointerMove(from, to);
    }

    @Override
    public boolean onPinchPointersUp(MotionEvent event) {
        // LogUtil.d("onPinchPointersUp");
        if (event == null) {
            return true;
        }
        if (mLastSelectedLocation != null && locationMarker.getVisibility() == View.VISIBLE) {
            /**
             * 注：以下两句话的顺序不可改变
             */
            mMapView.centerAt(mLastSelectedLocation, true);
        }
        mShouldRequestNewAddress = true;
        hasZoomBefore = true;
        return super.onPinchPointersUp(event);
    }

    @Override
    public boolean onDoubleTap(MotionEvent point) {
        if (mLastSelectedLocation != null && locationMarker.getVisibility() == View.VISIBLE) {
            /**
             * 注：以下两句话的顺序不可改变
             */
            mMapView.centerAt(mLastSelectedLocation, true);
            requestLocation(mLastSelectedLocation, mMapView.getSpatialReference(), new Callback<DoorNOBean>() {
                @Override
                public void onCallback(List<DoorNOBean> doorNOBeans) {
                    String str = null;
                    if (null == doorNOBeans || doorNOBeans.size()==0) str = "查找门牌号失败";
                    if (doorNOBeans != null) str = doorNOBeans.get(0).getAddress();
                    showCallout(str, null, calloutSureButtonClickListener);
                }

//                @Override
//                public void onCallback(String s) {
//                    showCallout(s, null, calloutSureButtonClickListener);
//                }
            });
        }
        mShouldRequestNewAddress = true;
        hasZoomBefore = true;
        return super.onDoubleTap(point);
    }


    @Override
    public boolean onLongPressUp(MotionEvent point) {
//            handleTap(point);
        super.onLongPressUp(point);
        return true;
    }

    //点击确定后再查询地址
    protected void showCallout(final Point point) {
        final Point geometry = point;
        final Callout callout = mMapView.getCallout();
        View view = View.inflate(mContext, R.layout.editmap_callout, null);
        TextView title = (TextView) view.findViewById(R.id.tv_listcallout_title);
        View divider = view.findViewById(R.id.divider);
        title.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
        title.setText("点击确定按钮开始查找");
        view.findViewById(R.id.btn_select_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calloutOKButtonClickListener != null){
                    calloutOKButtonClickListener.onClick(view);
                    return;
                }
                String showText = "";
                if (enableGeocoding) {
                    showText = "搜索中......";
                } else {
                    showText = "";
                }

                Point  geometry1 = mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2 - 140);
                showCallout(showText, geometry1, calloutSureButtonClickListener);
                if (enableGeocoding) {
                    requestLocation(point, mMapView.getSpatialReference(), new Callback<DoorNOBean>() {
                        @Override
                        public void onCallback(List<DoorNOBean> beans) {
                            String str = "";
                            if (null == beans) {
                                str = "查询失败";
                                mLastSelectedAddress = null;
                                setBean(null);
                            }else if (beans.size() == 0) {
                                str = "没有找到门牌号";
                                mLastSelectedAddress = null;
                                setBean(null);
                            } else {
                                mLastSelectedAddress = str = beans.get(0).getAddress();
                                setBean(beans.get(0));
                            }
                            Point  geometry2 = mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2 - 140);
                            showCallout(str, geometry2, calloutSureButtonClickListener);
                        }
//                    @Override
//                    public void onCallback(String s) {
//                        showCallout(s, null, calloutSureButtonClickListener);
//                    }
                    });
                }
//                if (mLastSelectedAddress == null && enableGeocoding) {
//                    ToastUtil.shortToast(mContext.getApplicationContext(), "请等待定位完成再点击确定");
//                    return;
//                }
//
//                if (mMapView.getCallout().isShowing()) {
//                    mMapView.getCallout().animatedHide();
//                }
//                if (calloutSureButtonClickListener != null) {
//                    calloutSureButtonClickListener.onClick(view);
//                }
            }
        });
        callout.setStyle(R.xml.editmap_callout_style);
        callout.setContent(view);
        Point point1 = null;
        if (point1 == null) {
            point1 = mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2 - 140);
        }else{
            point1 = point;
        }
        callout.show(point1);
    }

    protected void showCallout(String address, Point point, final View.OnClickListener calloutSureButtonClickListener) {
        final Point geometry = point;
        final Callout callout = mMapView.getCallout();
        View view = View.inflate(mContext, R.layout.editmap_callout, null);
        TextView title = (TextView) view.findViewById(R.id.tv_listcallout_title);
        View divider = view.findViewById(R.id.divider);
        if (!enableGeocoding) {
            title.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        } else {
            title.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
            title.setText(address);
        }
        view.findViewById(R.id.btn_select_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLastSelectedAddress == null || enableGeocoding) {
//                if (mLastSelectedAddress == null && enableGeocoding) {
                    ToastUtil.shortToast(mContext.getApplicationContext(), "请等待定位完成再点击确定");
                    return;
                }

                if (mMapView.getCallout().isShowing()) {
                    mMapView.getCallout().animatedHide();
                }
                if (calloutSureButtonClickListener != null) {
                    calloutSureButtonClickListener.onClick(view);
                }
            }
        });
        callout.setStyle(R.xml.editmap_callout_style);
        callout.setContent(view);
        if (point == null) {
            point = mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2 - 140);
        }
        callout.show(point);
    }

    /**
     * 跟{@link #showCallout(String, Point, View.OnClickListener)}的区别在于:不需要判断{@link #enableGeocoding}变量,
     * 也就是说,一定会显示文本
     *
     * @param message                        要显示的文本
     * @param point                          callout显示的位置,可以为Null
     * @param calloutSureButtonClickListener "确定"按钮点击事件
     */
    public void showCalloutMessage(String message, Point point, final View.OnClickListener calloutSureButtonClickListener) {
        if (TextUtils.isEmpty(message)) {
            message = "查无位置信息";
        }

        final Point geometry = point;
        final Callout callout = mMapView.getCallout();
        View view = View.inflate(mContext, R.layout.editmap_callout, null);
        TextView title = (TextView) view.findViewById(R.id.tv_listcallout_title);
        View divider = view.findViewById(R.id.divider);
        title.setText(message);
        view.findViewById(R.id.btn_select_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mMapView.getCallout().isShowing()) {
                    mMapView.getCallout().animatedHide();
                }
                if (calloutSureButtonClickListener != null) {
                    calloutSureButtonClickListener.onClick(view);
                }
            }
        });
        callout.setStyle(R.xml.editmap_callout_style);
        callout.setContent(view);
        if (point == null) {
            point = mMapView.toMapPoint(locationMarker.getX() + locationMarker.getWidth() / 2, locationMarker.getY() + locationMarker.getHeight() / 2 - 140);
        }
        callout.show(point);
    }

    /**
     * 请求百度地址
     *
     * @param point
     * @param
     */
    protected void requestLocation(Point point, SpatialReference spatialReference, final Callback<DoorNOBean> callBack) {
        if (point != null) {
            if (mSewerageLayerService == null)
                mSewerageLayerService = new SewerageLayerService(mContext);
            mSewerageLayerService.setQueryByWhere("1=1");
            mSewerageLayerService.queryDoorDataInfo(point, mMapView.getSpatialReference(), mMapView.getResolution(), new Callback2<List<DoorNOBean>>() {
                @Override
                public void onSuccess(List<DoorNOBean> doorNOBeans) {
                    if (ListUtil.isEmpty(doorNOBeans) || (doorNOBeans.size() == 1 && doorNOBeans.get(0).getDzdm() == null && doorNOBeans.get(0).getAddress() == null)) {
                        callBack.onCallback(doorNOBeans);
                        return;
                    }

                    callBack.onCallback(doorNOBeans);
//                    mDoorNOBeans = doorNOBeans;
//                    showOnBottomSheet(doorNOBeans);
                }

                @Override
                public void onFail(Exception error) {

                    callBack.onCallback(null);
                }
            });


//            SelectLocationService selectLocationService = new SelectLocationService(mContext, Locator.createOnlineLocator());
//            selectLocationService.parseLocation(new LatLng(point.getY(), point.getX()), spatialReference)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Action1<DetailAddress>() {
//                        @Override
//                        public void call(DetailAddress detailAddress) {
//
//                            mLastSelectedAddress = detailAddress;
//                            if (callback1 != null) {
//                                callback1.onCallback(detailAddress.getDetailAddress());
//                            }
//                            if (onSelectedLocationChangedListener != null) {
//                                onSelectedLocationChangedListener.onAddressChanged(detailAddress);
//                            }
//                        }
//                    });
        }

    }

    public Point getLastSelectLocation() {
        return mLastSelectedLocation;
    }

    //    public LocationInfo getLoationInfo() {
//        LocationInfo locationInfo = new LocationInfo();
//        locationInfo.setPoint(mLastSelectedLocation);
//        locationInfo.setDetailAddress(mLastSelectedAddress);
//        return locationInfo;
//    }
    public DoorNOBean getDoorBean() {
        return bean;
    }

    private void setLastSelectedLocation(Point mLastSelectedLocation) {
        this.mLastSelectedLocation = mLastSelectedLocation;
        if (onSelectedLocationChangedListener != null) {
            onSelectedLocationChangedListener.onLocationChanged(mLastSelectedLocation);
        }
    }

    public void registerLocationChangedListener(OnSelectedLocationChangedListener onSelectedLocationChangedListener) {
        this.onSelectedLocationChangedListener = onSelectedLocationChangedListener;
    }


    public void destroy() {
        mMapView = null;
        mContext = null;
    }

    /**
     * 当位置发生移动时回调的监听
     */
    public interface OnSelectedLocationChangedListener {
        void onLocationChanged(Point newLocation);

        void onAddressChanged(DetailAddress detailAddress);
    }
}

