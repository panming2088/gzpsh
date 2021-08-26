package com.augurit.agmobile.gzps.drainage_unit_monitor.view;


import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.util.ListUtil;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.RefreshLGEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjJgListBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorArg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorInfoBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.JbjMonitorService;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemBean;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemFeatureOrAddr;
import com.augurit.agmobile.gzpssb.uploadevent.model.SelectFinishEvent;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.PshSelectLocationActivity;
import com.augurit.agmobile.gzpssb.uploadevent.view.uploadevent.EventSelectMapActivity;
import com.augurit.agmobile.mapengine.map.geometry.LatLng;
import com.augurit.agmobile.patrolcore.baiduapi.BaiduApiService;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.selectlocation.model.BaiduGeocodeResult;
import com.augurit.agmobile.patrolcore.selectlocation.util.SelectLocationConstant;
import com.augurit.am.cmpt.loc.util.LocationUtil;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.AMFileOpUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author: liangsh
 * @createTime: 2021/5/6
 */
public class RiserPipeProblemFragment extends Fragment {

    private static final String RISER_PIPE_PROBLEM_TYPE = "A214";

    private View root;

    private TextView tv_addr;
    private ViewGroup ll_problem_type;
    private TextFieldTableItem problem_desc_item;

    private HashMap<DictionaryItem, ProblemTypeView> dictViewMap;

    private JbjMonitorArg args;

    private double problemX = 0;
    private double problemY = 0;

    private double reportX;       //上报者当前定位X坐标
    private double reportY;   //上报者当前定位Y坐标
    private String reportAddr;      //上报者当前定位地址

    private ProgressDialog pd;

    public static RiserPipeProblemFragment getInstance(JbjMonitorArg arg) {
        RiserPipeProblemFragment fragment = new RiserPipeProblemFragment();
        Bundle data = new Bundle();
        data.putSerializable("data", arg);
        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(initview(), null);
        EventBus.getDefault().register(this);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public int initview() {
        return R.layout.fragment_riser_pipe_problem;
    }

    protected void init() {
        args = (JbjMonitorArg) getArguments().getSerializable("data");

        startLocate();

        tv_addr = root.findViewById(R.id.tv_addr);
        ll_problem_type = root.findViewById(R.id.ll_problem_type);
        problem_desc_item = root.findViewById(R.id.problem_desc_item);

        TableDBService dbService = new TableDBService(getContext());
        final List<DictionaryItem> dicts = dbService.getDictionaryByTypecodeInDB(RISER_PIPE_PROBLEM_TYPE);
        if (ListUtil.isNotEmpty(dicts)) {
            dictViewMap = new HashMap<>();
            for (DictionaryItem item : dicts) {
                ProblemTypeView view = new ProblemTypeView(getContext());
                view.setText(item.getName());
                view.setMax(3);
                ll_problem_type.addView(view);
                dictViewMap.put(item, view);
            }
        }

        root.findViewById(R.id.iv_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (args.readOnly) {
                    if(args.wtData == null){
                       return;
                    }
                    String x = String.valueOf(args.wtData.getX());
                    String y = String.valueOf(args.wtData.getY());
                    String addr = args.wtData.getSzwz();
                    if (StringUtil.isEmpty(x)
                            || StringUtil.isEmpty(y)) {
                        ToastUtil.shortToast(getContext(), "地址信息缺失");
                        return;
                    }
                    Intent intent = new Intent(getContext(), PshSelectLocationActivity.class);
                    intent.putExtra(SelectLocationConstant.IF_READ_ONLY, true);
                    intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_LOCATION, new LatLng(Double.valueOf(y),
                            Double.valueOf(x)));
                    intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_ADDRESS, addr);
                    intent.putExtra(SelectLocationConstant.INITIAL_SCALE, PatrolLayerPresenter.initScale);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(getActivity(), EventSelectMapActivity.class);
                intent.putExtra("title", "立管定位");
                intent.putExtra("onlyAddress", true);
                if(problemX != 0 && problemY != 0) {
                    Point point = new Point();
                    point.setX(problemX);
                    point.setY(problemY);
                    intent.putExtra("geometry", point);
                }
                getActivity().startActivity(intent);
            }
        });
        root.findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_addr.setText("");
                problemX = 0;
                problemY = 0;
                problem_desc_item.setText("");
                if (ListUtil.isNotEmpty(dictViewMap)) {
                    for (DictionaryItem dict : dictViewMap.keySet()) {
                        ProblemTypeView view = dictViewMap.get(dict);
                        view.clear();
                        view.setChecked(false);
                    }
                }
            }
        });
        root.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });

        if (args.wtData != null) {
            final ProgressDialog pd = ProgressDialog.show(getActivity(), "提示", "正在加载数据");
            root.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initData();
                    pd.dismiss();
                }
            }, 1000);
        }
    }

    private void initData() {
        if (args.wtData != null) {
            JbjMonitorInfoBean.WtData wtData = args.wtData;
            tv_addr.setText(wtData.getSzwz());
            problem_desc_item.setText(wtData.getWtms());
            Map<String, List<JbjJgListBean.Attachment>> wtPhotoListMap = wtData.attachments;
            if (ListUtil.isNotEmpty(dictViewMap) && !TextUtils.isEmpty(wtData.getWtlx())) {
                for (DictionaryItem dict : dictViewMap.keySet()) {
                    ProblemTypeView view = dictViewMap.get(dict);
                    if (wtData.getWtlx().contains(dict.getCode())) {
                        view.setChecked(true);
                        if (wtPhotoListMap != null && wtPhotoListMap.containsKey(dict.getCode())) {
                            List<JbjJgListBean.Attachment> attachmentList = wtPhotoListMap.get(dict.getCode());
                            if (ListUtil.isNotEmpty(attachmentList)) {
                                List<Photo> photoList = new ArrayList<>();
                                for (JbjJgListBean.Attachment att : attachmentList) {
                                    Photo photo = new Photo();
                                    photo.setPhotoPath(att.getAttPath());
                                    photoList.add(photo);
                                }
                                view.setPhotos(photoList);
                            }
                        }
                    }
                }
            }
        }
        if (args.readOnly) {
            tv_addr.setEnabled(false);
            problem_desc_item.setEnableEdit(false);
            if (ListUtil.isNotEmpty(dictViewMap)) {
                for (ProblemTypeView view : dictViewMap.values()) {
                    view.setEnable(false);
                }
            }
            root.findViewById(R.id.ll_bottom).setVisibility(View.GONE);
        }
    }

    private void commit() {
        if (problemX == 0 || problemY == 0) {
            ToastUtil.shortToast(getContext(), "请选择问题地点");
            return;
        }
        String problemAddr = tv_addr.getText().toString();
        if (TextUtils.isEmpty(problemAddr)) {
            ToastUtil.shortToast(getContext(), "请填写问题地址");
            return;
        }
        String problemType = "";
        List<Photo> problemTypePhotoList = new ArrayList<>();
        List<Photo> problemTypeThumbPhotoList = new ArrayList<>();
        if (ListUtil.isNotEmpty(dictViewMap)) {
            for (DictionaryItem dict : dictViewMap.keySet()) {
                ProblemTypeView view = dictViewMap.get(dict);
                if (view.isChecked()) {
                    if (ListUtil.isEmpty(view.getPhotos())) {
                        ToastUtil.shortToast(getContext(), "请选择" + dict.getName() + "的照片");
                        return;
                    } else {
                        problemType = problemType + dict.getCode() + ",";
                        List<Photo> problemPhotos = view.getPhotos();
                        for (Photo photo : problemPhotos) {
                            String path = photo.getLocalPath();
                            String pathWithoutExtension = path.substring(0, path.lastIndexOf("."));
                            String resultPath = pathWithoutExtension + "_" + dict.getCode() + "_img." + AMFileOpUtil.getFileExtension(path);
                            try {
                                AMFileOpUtil.copyFile(path, resultPath);
                                photo.setLocalPath(resultPath);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            problemTypePhotoList.add(photo);
                        }
                        for (Photo photo : view.getThumbnailPhotos()) {
                            String path = photo.getLocalPath();
                            String pathWithoutExtension = path.substring(0, path.lastIndexOf("."));
                            String resultPath = pathWithoutExtension + "_" + dict.getCode() + "thumbnail_img." + AMFileOpUtil.getFileExtension(path);
                            try {
                                AMFileOpUtil.copyFile(path, resultPath);
                                photo.setLocalPath(resultPath);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            problemTypeThumbPhotoList.add(photo);
                        }
                    }
                }
            }
            if (problemType.length() > 1) {
                problemType = problemType.substring(0, problemType.length() - 1);
            }
        }
        if (ListUtil.isEmpty(problemTypePhotoList)) {
            ToastUtil.shortToast(getContext(), "请选择问题类型");
            return;
        }
        String desc = problem_desc_item.getText();
        if (TextUtils.isEmpty(desc)) {
            ToastUtil.shortToast(getContext(), "请填写问题描述");
            return;
        }

        User user = new LoginRouter(getContext(), AMDatabase.getInstance()).getUser();
        ProblemBean wtData = new ProblemBean();
        wtData.setLoginname(user.getLoginName());
        wtData.setSBR(user.getUserName());
        wtData.setPshmc("立管");
        wtData.setSslx("lgjc");
        wtData.setPsdyId(args.psdyId);
        wtData.setPsdyName(args.psdyName);
        wtData.setReportType(args.reportType);
        wtData.setX(StringUtil.valueOf(problemX));
        wtData.setY(StringUtil.valueOf(problemY));
        wtData.setSzwz(problemAddr);
        wtData.setReportaddr(reportAddr);
        wtData.setReportx(StringUtil.valueOf(reportX));
        wtData.setReporty(StringUtil.valueOf(reportY));

        wtData.setWtlx(problemType);
        wtData.setWtms(desc);

        problemTypePhotoList.addAll(problemTypeThumbPhotoList);

        if (pd == null) {
            pd = new ProgressDialog(getContext());
        }
        if (!pd.isShowing()) {
            pd.setMessage("数据正在保存...");
            pd.setCancelable(false);
            pd.show();
        }

        new JbjMonitorService(getContext()).addPshLgjc(wtData, problemTypePhotoList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        pd.dismiss();
                        ToastUtil.shortToast(getContext(), "保存失败，请重试");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        pd.dismiss();
                        if (responseBody.getCode() == 200) {
                            ToastUtil.shortToast(getContext(), "保存成功");
                            EventBus.getDefault().post(new RefreshLGEvent());
                            getActivity().finish();
                        } else {
                            ToastUtil.shortToast(getContext(), responseBody.getMessage());
                        }
                    }
                });
    }

    /**
     * 定位上报者当前所在位置
     */
    private void startLocate() {
        LocationUtil.register(getContext(), 1000, 0, new LocationUtil.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {

            }

            @Override
            public void onLocationChanged(Location location) {
//                lat = location.getLongitude() + "";
//                lng = location.getLatitude() + "";
                requestAddress(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
    }

    /**
     * 根据经纬度获取详细地址
     *
     * @param location
     */
    private void requestAddress(final Location location) {
        BaiduApiService baiduApiService = new BaiduApiService(getContext());
        baiduApiService.parseLocation(new LatLng(location.getLatitude(), location.getLongitude()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaiduGeocodeResult>() {
                    @Override
                    public void call(BaiduGeocodeResult baiduGeocodeResult) {
//                        addrItem.setText(baiduGeocodeResult.getDetailAddress());
//                        roadItem.setText(baiduGeocodeResult.getResult().getAddressComponent().getStreet
//                                ());
                        if (baiduGeocodeResult != null) {
                            reportAddr = baiduGeocodeResult.getDetailAddress();
                            reportX = location.getLongitude();
                            reportY = location.getLatitude();
                        }

                    }
                });
    }

    /**
     * 选择位置或设施后的事件回调
     *
     * @param event
     */
    @Subscribe
    public void onReceivedFinishedSelectEvent2(SelectFinishEvent event) {
        if (event != null) {
            ProblemFeatureOrAddr featureOrAddr = event.featureOrAddr;
            tv_addr.setText(featureOrAddr.getAddr());
            Geometry geometry = event.mGeometry;
            if (geometry instanceof Point) {
                Point point = (Point) geometry;
                problemX = point.getX();
                problemY = point.getY();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationUtil.unregister(getContext());
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (ListUtil.isNotEmpty(dictViewMap)) {
            for (ProblemTypeView view : dictViewMap.values()) {
                view.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
