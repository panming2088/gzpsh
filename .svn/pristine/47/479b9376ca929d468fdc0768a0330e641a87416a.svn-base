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

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.util.ListUtil;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.SaveCheckEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjJgListBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorArg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorInfoBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.JbjMonitorService;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemBean;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemFeatureOrAddr;
import com.augurit.agmobile.mapengine.map.geometry.LatLng;
import com.augurit.agmobile.patrolcore.baiduapi.BaiduApiService;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.agmobile.patrolcore.selectlocation.model.BaiduGeocodeResult;
import com.augurit.am.cmpt.loc.util.LocationUtil;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.AMFileOpUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.ToastUtil;

import org.greenrobot.eventbus.EventBus;

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
 * @createTime: 2021/5/21
 */
public class MonitorEventFragment extends Fragment {

    public static final String INSIDE_CHECK_DICT = "A208";
    public static final String OUTSIDE_CHECK_DICT = "A209";
    //    private final String KD_EVENT_TYPE_DICT = "A213"; //空地问题类型
    private static final String RISER_PIPE_PROBLEM_TYPE = "A214";


    private View root;
    private SelectFacilityView select_facility_view;
    private ViewGroup ll_eventtype;
    private TextFieldTableItem problem_desc_item;
    private View ll_bottom;

    private JbjMonitorArg args;
    private int type = 1; //1地面检查，2开盖检查，3立管检查

    private HashMap<DictionaryItem, ProblemTypeView> dictViewMap;

    private double reportX;       //上报者当前定位X坐标
    private double reportY;   //上报者当前定位Y坐标
    private String reportAddr;      //上报者当前定位地址

    private ProgressDialog pd;

    public static MonitorEventFragment getInstance(JbjMonitorArg args) {
        MonitorEventFragment fragment = new MonitorEventFragment();
        Bundle data = new Bundle();
        data.putSerializable("data", args);
        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_monitor_event, null);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        args = (JbjMonitorArg) getArguments().getSerializable("data");
        type = args.checkType;

        select_facility_view = root.findViewById(R.id.select_facility_view);
        select_facility_view.setRequired(true);
        ll_eventtype = root.findViewById(R.id.ll_eventtype);
        problem_desc_item = root.findViewById(R.id.problem_desc_item);
        problem_desc_item.setRequireTag();
        ll_bottom = root.findViewById(R.id.ll_bottom);
        root.findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_facility_view.clear();
                problem_desc_item.setText("");
                if (ListUtil.isNotEmpty(dictViewMap)) {
                    for (DictionaryItem dict : dictViewMap.keySet()) {
                        ProblemTypeView view = dictViewMap.get(dict);
                        view.clear();
//                        view.setChecked(false);
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

        if (type == 1 || type == 2) {
            select_facility_view.setSelectType(2);
        } else {
            select_facility_view.setSelectType(3);
        }

        select_facility_view.setReadOnly(args.readOnly);

        String dictCode = INSIDE_CHECK_DICT;
        if (type == 1) {
            dictCode = INSIDE_CHECK_DICT;
        } else if (type == 2) {
            dictCode = OUTSIDE_CHECK_DICT;
        } else if (type == 3) {
            dictCode = RISER_PIPE_PROBLEM_TYPE;
        }
        initEventType(dictCode);
        if (!args.readOnly) {
            startLocate();
        }
        initData();
    }

    protected void initEventType(String dictCode) {
        TableDBService dbService = new TableDBService(getContext().getApplicationContext());
        final List<DictionaryItem> dicts = dbService.getDictionaryByTypecodeInDB(dictCode);
        ll_eventtype.removeAllViews();
        if (ListUtil.isNotEmpty(dicts)) {
            dictViewMap = new HashMap<>();
            for (DictionaryItem item : dicts) {
                ProblemTypeView view = new ProblemTypeView(getActivity());
                view.setText(item.getName());
                view.setMax(3);
                ll_eventtype.addView(view);
                dictViewMap.put(item, view);
            }
        }
    }

    private void initData() {
        if (args.wtData != null) {
            JbjMonitorInfoBean.WtData wtData = args.wtData;
            ProblemFeatureOrAddr featureOrAddr = new ProblemFeatureOrAddr();
            featureOrAddr.setAddr(wtData.getSzwz());
            featureOrAddr.setX(Double.parseDouble(wtData.getX()));
            featureOrAddr.setY(Double.parseDouble(wtData.getY()));
            select_facility_view.setValue(featureOrAddr);
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
            select_facility_view.setReadOnly(true);
            if (ListUtil.isNotEmpty(dictViewMap)) {
                for (DictionaryItem dict : dictViewMap.keySet()) {
                    ProblemTypeView view = dictViewMap.get(dict);
                    view.setEnable(false);
                }
            }
            problem_desc_item.setEnableEdit(false);
            ll_bottom.setVisibility(View.GONE);
        }
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
        BaiduApiService baiduApiService = new BaiduApiService(getContext().getApplicationContext());
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

    private void commit() {
        ProblemFeatureOrAddr featureOrAddr = select_facility_view.getValue();
        if (featureOrAddr == null || featureOrAddr.getX() == 0 || featureOrAddr.getY() == 0) {
            ToastUtil.shortToast(getContext(), "请选择设施或问题地点");
            return;
        }
        String problemAddr = featureOrAddr.getAddr();
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

        User user = new LoginRouter(getContext().getApplicationContext(), AMDatabase.getInstance()).getUser();
        ProblemBean wtData = new ProblemBean();
        wtData.setLoginname(user.getLoginName());
        wtData.setSBR(user.getUserName());
        String pshmc = "";
        String sslx = "";
        if (type == 1 || type == 2) {
            if (TextUtils.isEmpty(featureOrAddr.getSslx()) || "other".equals(featureOrAddr.getSslx())) {
                pshmc = "其他";
            } else {
                pshmc = featureOrAddr.getAttrOne() + "-" + featureOrAddr.getAttrTwo();
                wtData.setPshid((long) featureOrAddr.getId());
            }
            if (type == 1) {
                sslx = "dmjc";
            } else if (type == 2) {
                sslx = "kgjc";
            }
        } else if (type == 3) {
            pshmc = "立管";
            sslx = "lgjc";
        }
        wtData.setPshmc(pshmc);
        wtData.setSslx(sslx);
        wtData.setPsdyId(args.psdyId);
        wtData.setPsdyName(args.psdyName);
        wtData.setReportType(featureOrAddr.getReportType());
        wtData.setX(StringUtil.valueOf(featureOrAddr.getX()));
        wtData.setY(StringUtil.valueOf(featureOrAddr.getY()));
        wtData.setSzwz(problemAddr);
        wtData.setReportaddr(reportAddr);
        wtData.setReportx(StringUtil.valueOf(reportX));
        wtData.setReporty(StringUtil.valueOf(reportY));

        wtData.setWtlx(problemType);
        wtData.setWtms(desc);
        wtData.setWtly("2");

        problemTypePhotoList.addAll(problemTypeThumbPhotoList);

        if (pd == null) {
            pd = new ProgressDialog(getContext());
        }
        if (!pd.isShowing()) {
            pd.setMessage("数据正在保存...");
            pd.setCancelable(false);
            pd.show();
        }

        new JbjMonitorService(getContext().getApplicationContext()).addPshLgjc(wtData, problemTypePhotoList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        pd.dismiss();
                        ToastUtil.shortToast(getActivity(), "保存失败，请重试");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        pd.dismiss();
                        if (responseBody.getCode() == 200) {
                            ToastUtil.shortToast(getActivity(), "保存成功");
                            EventBus.getDefault().post(new SaveCheckEvent(type - 1));
                            getActivity().finish();
                        } else {
                            ToastUtil.shortToast(getActivity(), responseBody.getMessage());
                        }
                    }
                });
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
