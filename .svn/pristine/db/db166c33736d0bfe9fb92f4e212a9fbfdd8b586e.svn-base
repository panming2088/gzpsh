package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.OnRefreshEvent;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.util.ListUtil;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.RefreshJBJEvent;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjJgListBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorArg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorInfoBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.JbjMonitorService;
import com.augurit.agmobile.gzps.uploadevent.model.EventDetail;
import com.augurit.agmobile.gzpssb.monitor.model.WellMonitorInfo;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventDetail;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventProcess;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemBean;
import com.augurit.agmobile.gzpssb.uploadevent.service.PSHUploadEventService;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventflow.PSHEventProcessView;
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
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author: liangsh
 * @createTime: 2021/5/7
 */
public class JbjMonitorFragment extends Fragment {

    public static final String INSIDE_CHECK_DICT = "A208";
    public static final String OUTSIDE_CHECK_DICT = "A209";

    private View root;

    private TextView tv_jjc_title;
    private TakePhotoItemProblem photo_check;
    private TextItemProblem text_item_jbjtype;
    private ProblemTypeView problem_type_sfys;
    private ProblemTypeView problem_type_sfds;
    private TextItemProblem text_item_gdgj;
    private TextItemProblem text_item_ad;
    private TextItemProblem text_item_cod;
    private TextItemProblem text_item_rwsll;
    private TimePickerItem text_item_jcsj;
    private ViewGroup ll_inside_check;
    private ViewGroup ll_outside_check;
    private TextFieldItemProblem problem_desc_item;
    private ViewGroup ll_event_process;
    private ViewGroup ll_advice;
    private View btn_commit;

    private JbjMonitorArg args;

    private double reportX;       //上报者当前定位X坐标
    private double reportY;   //上报者当前定位Y坐标
    private String reportAddr;      //上报者当前定位地址

    private HashMap<DictionaryItem, ProblemTypeView> dictViewMap;

    private JbjMonitorInfoBean jbjMonitorInfoBean;
    private List<Photo> allPhotoList;

    private PSHEventDetail.FormBean mEventDetail;

    private ProgressDialog pd;

    public static JbjMonitorFragment getInstance(JbjMonitorArg arg) {
        JbjMonitorFragment fragment = new JbjMonitorFragment();
        Bundle data = new Bundle();
        data.putSerializable("data", arg);
        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_jbj_monitor, null);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        args = (JbjMonitorArg) getArguments().getSerializable("data");

        startLocate();

        pd = new ProgressDialog(getActivity());

        tv_jjc_title = root.findViewById(R.id.tv_jjc_title);
        photo_check = root.findViewById(R.id.photo_check);
        text_item_jbjtype = root.findViewById(R.id.text_item_jbjtype);
        problem_type_sfys = root.findViewById(R.id.problem_type_sfys);
        problem_type_sfds = root.findViewById(R.id.problem_type_sfds);
        text_item_gdgj = root.findViewById(R.id.text_item_gdgj);
        text_item_ad = root.findViewById(R.id.text_item_ad);
        text_item_cod = root.findViewById(R.id.text_item_cod);
        text_item_rwsll = root.findViewById(R.id.text_item_rwsll);
        text_item_jcsj = root.findViewById(R.id.text_item_jcsj);
        ll_inside_check = root.findViewById(R.id.ll_inside_check);
        ll_outside_check = root.findViewById(R.id.ll_outside_check);
        problem_desc_item = root.findViewById(R.id.problem_desc_item);
        ll_event_process = root.findViewById(R.id.ll_event_process1);
        ll_advice = root.findViewById(R.id.ll_advice1);
        btn_commit = root.findViewById(R.id.btn_commit);

        tv_jjc_title.setText(args.subtype + "监测");
        text_item_jbjtype.setTextViewName(args.subtype + "类型");
        problem_type_sfds.setText(args.subtype + "是否被堵塞");
        text_item_gdgj.setTextViewName(args.subtype + "管道管径");

        text_item_jbjtype.setEditable(false);
        text_item_jbjtype.setText(args.jbjType);

        if ("雨水".equals(args.jbjType)) {
            problem_type_sfys.setVisibility(View.VISIBLE);
        }

        problem_type_sfys.setMax(3);
        problem_type_sfds.setMax(3);

        text_item_gdgj.setInputTypeAboutNum();
        text_item_ad.setInputTypeAboutNum();
        text_item_cod.setInputTypeAboutNum();
        text_item_rwsll.setInputTypeAboutNum();

        dictViewMap = new HashMap<>();
        TableDBService dbService = new TableDBService(getContext());
        List<DictionaryItem> dicts = dbService.getDictionaryByTypecodeInDB(INSIDE_CHECK_DICT);
        if (ListUtil.isNotEmpty(dicts)) {
            for (DictionaryItem item : dicts) {
                ProblemTypeView view = new ProblemTypeView(getContext());
                view.setText(item.getName());
                view.setTextColor(Color.parseColor("#707376"));
                view.setMax(3);
                ll_inside_check.addView(view);
                dictViewMap.put(item, view);
            }
        }
        List<DictionaryItem> dicts2 = dbService.getDictionaryByTypecodeInDB(OUTSIDE_CHECK_DICT);
        if (ListUtil.isNotEmpty(dicts2)) {
            for (DictionaryItem item : dicts2) {
                ProblemTypeView view = new ProblemTypeView(getContext());
                view.setText(item.getName());
                view.setTextColor(Color.parseColor("#707376"));
                view.setMax(3);
                ll_outside_check.addView(view);
                dictViewMap.put(item, view);
            }
        }

        root.findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo_check.clear();
                problem_type_sfys.clear();
                problem_type_sfys.setChecked(false);
                problem_type_sfds.clear();
                problem_type_sfds.setChecked(false);
                text_item_gdgj.setText("");
                text_item_ad.setText("");
                text_item_cod.setText("");
                text_item_rwsll.setText("");
                text_item_jcsj.setText("");
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
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });

        if (args.jbjMonitorInfoBean != null) {
            initData();
            /*getEventDetail();
            final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载数据");
            root.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initData();
                    dialog.dismiss();
                }
            }, 1000);*/
        }
    }

    private void initData() {
        JbjMonitorInfoBean.JcData jcData = args.jbjMonitorInfoBean.getJcData();
        JbjMonitorInfoBean.WtData wtData = args.jbjMonitorInfoBean.getWtData();

        if (jcData != null) {
            if (ListUtil.isNotEmpty(jcData.jbjJcAttachment)) {
                List<Photo> photoList = new ArrayList<>();
                for (JbjJgListBean.Attachment att : jcData.jbjJcAttachment) {
                    Photo photo = new Photo();
                    photo.setPhotoPath(att.getAttPath());
                    photoList.add(photo);
                }
                photo_check.setSelectedPhotos(photoList);
            }
            text_item_jbjtype.setText(jcData.getJbjType());
            if ("1".equals(jcData.getQtsfys())) {
                problem_type_sfys.setChecked(true);
                if (ListUtil.isNotEmpty(jcData.sldJcAttachment)) {
                    List<Photo> photoList = new ArrayList<>();
                    for (JbjJgListBean.Attachment att : jcData.sldJcAttachment) {
                        Photo photo = new Photo();
                        photo.setPhotoPath(att.getAttPath());
                        photoList.add(photo);
                    }
                    problem_type_sfys.setPhotos(photoList);
                }
            }
            if ("1".equals(jcData.getSfds())) {
                problem_type_sfds.setChecked(true);
                if (ListUtil.isNotEmpty(jcData.sfdsJcAttachment)) {
                    List<Photo> photoList = new ArrayList<>();
                    for (JbjJgListBean.Attachment att : jcData.sfdsJcAttachment) {
                        Photo photo = new Photo();
                        photo.setPhotoPath(att.getAttPath());
                        photoList.add(photo);
                    }
                    problem_type_sfds.setPhotos(photoList);
                }
            }
            if (!TextUtils.isEmpty(jcData.getGdgj())) {
                text_item_gdgj.setText(jcData.getGdgj());
            }
            if (!TextUtils.isEmpty(jcData.getAd())) {
                text_item_ad.setText(jcData.getAd());
            }
            if (!TextUtils.isEmpty(jcData.getCod())) {
                text_item_cod.setText(jcData.getCod());
            }
            if (!TextUtils.isEmpty(jcData.getRwsll())) {
                text_item_rwsll.setText(jcData.getRwsll());
            }
            if (jcData.getJcsj() != null) {
                text_item_jcsj.setText(TimeUtil.getStringTimeYMDFromDate(new Date(jcData.getJcsj())));
            }
        }
        if (wtData != null) {
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
            problem_desc_item.setText(wtData.getWtms());
        }

        if (args.readOnly) {
            photo_check.setReadOnly();
            photo_check.setAddPhotoEnable(false);
            problem_type_sfys.setEnable(false);
            problem_type_sfds.setEnable(false);
            text_item_gdgj.setEditable(false);
            text_item_ad.setEditable(false);
            text_item_cod.setEditable(false);
            text_item_rwsll.setEditable(false);
            text_item_jcsj.setReadOnly();
            problem_desc_item.setEnableEdit(false);
            if (ListUtil.isNotEmpty(dictViewMap)) {
                for (ProblemTypeView view : dictViewMap.values()) {
                    view.setEnable(false);
                }
            }
            root.findViewById(R.id.ll_bottom).setVisibility(View.GONE);
        }
    }

    private boolean getData() {
        jbjMonitorInfoBean = new JbjMonitorInfoBean();
        boolean hasJcData = false;
        boolean hasWtData = false;
        List<Photo> checkPhotoList = photo_check.getSelectedPhotos();
        if (!ListUtil.isEmpty(checkPhotoList)) {
//            ToastUtil.shortToast(getContext(), "请添加监测照片");
//            return false;
            hasJcData = true;
        }

        List<Photo> sfysPhotoList = problem_type_sfys.getPhotos();
        if (problem_type_sfys.isChecked()) {
            if (ListUtil.isEmpty(sfysPhotoList)) {
                ToastUtil.shortToast(getContext(), "请添加晴天是否有水流动的照片");
                return false;
            }
            hasJcData = true;
        }

        List<Photo> sfdsPhotoList = problem_type_sfds.getPhotos();
        if (problem_type_sfds.isChecked()) {
            if (ListUtil.isEmpty(sfdsPhotoList)) {
                ToastUtil.shortToast(getContext(), "请添加接驳井是否被堵塞的照片");
                return false;
            }
            hasJcData = true;
        }

        String gdgj = text_item_gdgj.getText();
        if (!TextUtils.isEmpty(gdgj)) {
//            ToastUtil.shortToast(getContext(), "请填写" + text_item_gdgj.getTextViewName());
//            return false;
            hasJcData = true;
        }
        String ad = text_item_ad.getText();
        if (!TextUtils.isEmpty(ad)) {
//            ToastUtil.shortToast(getContext(), "请填写" + text_item_ad.getTextViewName());
//            return false;
            hasJcData = true;
        }
        String cod = text_item_cod.getText();
        if (!TextUtils.isEmpty(cod)) {
//            ToastUtil.shortToast(getContext(), "请填写" + text_item_cod.getTextViewName());
//            return false;
            hasJcData = true;
        }
        String rwsll = text_item_rwsll.getText();
        if (!TextUtils.isEmpty(rwsll)) {
//            ToastUtil.shortToast(getContext(), "请填写" + text_item_rwsll.getTextViewName());
//            return false;
            hasJcData = true;
        }
        String jcsj = text_item_jcsj.getText();
        if (hasJcData && TextUtils.isEmpty(jcsj)) {
            ToastUtil.shortToast(getContext(), "请选择" + text_item_jcsj.getTextViewName());
            return false;
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
                        return false;
                    } else {
                        hasWtData = true;
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
        String desc = problem_desc_item.getText();
        if (hasWtData && TextUtils.isEmpty(desc)) {
            ToastUtil.shortToast(getContext(), "请填写问题描述");
            return false;
        }
        if (!TextUtils.isEmpty(desc) && !hasWtData) {
            ToastUtil.shortToast(getContext(), "当前填写了问题描述，请选择问题类型");
            return false;
        }

        allPhotoList = new ArrayList<>();

        for (Photo photo : checkPhotoList) {
            String path = photo.getLocalPath();
            String pathWithoutExtension = path.substring(0, path.lastIndexOf("."));
            String resultPath = pathWithoutExtension + "_jbjJc_img." + AMFileOpUtil.getFileExtension(path);
            try {
                AMFileOpUtil.copyFile(path, resultPath);
                photo.setLocalPath(resultPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        allPhotoList.addAll(checkPhotoList);
        List<Photo> checkthumbPhotoList = photo_check.getThumbnailPhotos();
        for (Photo photo : checkthumbPhotoList) {
            String path = photo.getLocalPath();
            String pathWithoutExtension = path.substring(0, path.lastIndexOf("."));
            String resultPath = pathWithoutExtension + "_jbjJcthumbnail_img." + AMFileOpUtil.getFileExtension(path);
            try {
                AMFileOpUtil.copyFile(path, resultPath);
                photo.setLocalPath(resultPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        allPhotoList.addAll(checkthumbPhotoList);

        for (Photo photo : sfysPhotoList) {
            String path = photo.getLocalPath();
            String pathWithoutExtension = path.substring(0, path.lastIndexOf("."));
            String resultPath = pathWithoutExtension + "_sldJc_img." + AMFileOpUtil.getFileExtension(path);
            try {
                AMFileOpUtil.copyFile(path, resultPath);
                photo.setLocalPath(resultPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        allPhotoList.addAll(sfysPhotoList);
        List<Photo> sfysthumbPhotoList = problem_type_sfys.getThumbnailPhotos();
        for (Photo photo : sfysthumbPhotoList) {
            String path = photo.getLocalPath();
            String pathWithoutExtension = path.substring(0, path.lastIndexOf("."));
            String resultPath = pathWithoutExtension + "_sldJcthumbnail_img." + AMFileOpUtil.getFileExtension(path);
            try {
                AMFileOpUtil.copyFile(path, resultPath);
                photo.setLocalPath(resultPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        allPhotoList.addAll(sfysthumbPhotoList);

        for (Photo photo : sfdsPhotoList) {
            String path = photo.getLocalPath();
            String pathWithoutExtension = path.substring(0, path.lastIndexOf("."));
            String resultPath = pathWithoutExtension + "_sfdsJc_img." + AMFileOpUtil.getFileExtension(path);
            try {
                AMFileOpUtil.copyFile(path, resultPath);
                photo.setLocalPath(resultPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        allPhotoList.addAll(sfdsPhotoList);
        List<Photo> sfdsthumbPhotoList = problem_type_sfds.getThumbnailPhotos();
        for (Photo photo : sfdsthumbPhotoList) {
            String path = photo.getLocalPath();
            String pathWithoutExtension = path.substring(0, path.lastIndexOf("."));
            String resultPath = pathWithoutExtension + "_sfdsJcthumbnail_img." + AMFileOpUtil.getFileExtension(path);
            try {
                AMFileOpUtil.copyFile(path, resultPath);
                photo.setLocalPath(resultPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        allPhotoList.addAll(sfdsthumbPhotoList);

        allPhotoList.addAll(problemTypePhotoList);
        allPhotoList.addAll(problemTypeThumbPhotoList);

        initMonitorInfoBean(hasJcData, hasWtData);
        if (hasJcData) {
            JbjMonitorInfoBean.JcData jcData = jbjMonitorInfoBean.getJcData();
            jcData.jcSfwk = "1";
            jcData.setQtsfys(problem_type_sfys.isChecked() ? "1" : "0");
            jcData.setRwsll(rwsll);
            jcData.setSfds(problem_type_sfds.isChecked() ? "1" : "0");
            jcData.setGdgj(gdgj);
            jcData.setAd(ad);
            if (!TextUtils.isEmpty(cod)) {
                jcData.setCod(cod);
            }
            long time = System.currentTimeMillis();
            try {
                time = TimeUtil.getDateTimeYMDM(jcsj).getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            jcData.setJcsj(time);
        }
        if (hasWtData) {
            JbjMonitorInfoBean.WtData wtData = jbjMonitorInfoBean.getWtData();
            wtData.wtSfwk = "1";
            wtData.setPshmc(args.subtype + "-" + args.jbjType);
            wtData.setWtlx(problemType);
            wtData.setWtms(desc);
        }

        if (!hasJcData && !hasWtData) {
            ToastUtil.shortToast(getActivity(), "请填写数据");
            return false;
        } else {
            return true;
        }
    }

    private void initMonitorInfoBean(boolean hasJcData, boolean hasWtData) {
        JbjMonitorInfoBean.JgData jgData = new JbjMonitorInfoBean.JgData();
        User user = new LoginRouter(getContext(), AMDatabase.getInstance()).getUser();
        jgData.setLoginName(user.getLoginName());
        jgData.setPsdyId(args.psdyId);
        jgData.setPsdyName(args.psdyName);
        jgData.setWellId(args.jbjObjectId);
        jgData.setWellType(args.wellType);
        jbjMonitorInfoBean.setJgData(jgData);

        JbjMonitorInfoBean.JcData jcData = new JbjMonitorInfoBean.JcData();
        if (hasJcData) {
            jcData.setUsid(args.usid);
            jcData.setJbjObjectId(args.jbjObjectId);
            jcData.setJbjType(args.jbjType);
            if (!StringUtil.isEmpty(args.X) && !"null".equals(args.X)) {
                jcData.setJbjX(Double.parseDouble(args.X));
            }
            if (!StringUtil.isEmpty(args.Y) && !"null".equals(args.Y)) {
                jcData.setJbjY(Double.parseDouble(args.Y));
            }
        }
        jbjMonitorInfoBean.setJcData(jcData);

        JbjMonitorInfoBean.WtData wtData = new JbjMonitorInfoBean.WtData();
        if (hasWtData) {
            wtData.setLoginname(user.getLoginName());
            wtData.setSBR(user.getUserName());
            wtData.setSslx("jbjJg");
            wtData.setPsdyId(args.psdyId);
            wtData.setPsdyName(args.psdyName);
            wtData.setReportType(args.reportType);
            wtData.setX(args.X);
            wtData.setY(args.Y);
            wtData.setSzwz(args.addr);
            wtData.setReportaddr(reportAddr);
            wtData.setReportx(StringUtil.valueOf(reportX));
            wtData.setReporty(StringUtil.valueOf(reportY));
        }
        jbjMonitorInfoBean.setWtData(wtData);

    }

    private void commit() {
        if (!getData()) {
            return;
        } else {
            if (pd == null) {
                pd = new ProgressDialog(getContext());
            }
            if (!pd.isShowing()) {
                pd.setMessage("数据正在保存...");
                pd.setCancelable(false);
                pd.show();
            }
            new JbjMonitorService(getContext()).addJbjMonitor(jbjMonitorInfoBean, allPhotoList)
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
                                EventBus.getDefault().post(new OnRefreshEvent());
                                EventBus.getDefault().post(new RefreshJBJEvent(Integer.parseInt(args.jbjObjectId)));
                                getActivity().finish();
                            } else {
                                ToastUtil.shortToast(getContext(), responseBody.getMessage());
                            }
                        }
                    });
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

    private void getEventDetail() {
        if (args.jbjMonitorInfoBean.getWtData() == null) {
            ll_event_process.setVisibility(View.GONE);
            return;
        }
        ll_event_process.setVisibility(View.VISIBLE);
        PSHUploadEventService uploadEventService = new PSHUploadEventService(getContext().getApplicationContext());
        uploadEventService.getDetails(Integer.parseInt(args.jbjMonitorInfoBean.getWtData().id), "sb")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PSHEventDetail>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogUtil.MessageBoxCannotCancel(getContext(), null, "请求数据出错！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((Activity) getActivity()).finish();
                            }
                        });
                        /*pb_loading.showError("加载失败", "", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                initData();
                            }
                        });*/
                    }

                    @Override
                    public void onNext(PSHEventDetail result) {
                        String json = JsonUtil.getJson(result);
                        if (result == null
                                || result.getCode() != 200
                                || result.getForm() == null) {
                            DialogUtil.MessageBoxCannotCancel(getContext(), null, "请求数据出错！", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((Activity) getActivity()).finish();
                                }
                            });
                            return;
                        }
                        mEventDetail = result.getForm();
                        getEventHandlesAndJournals(String.valueOf(mEventDetail.getId()));
                    }
                });
    }

    /**
     * 获取处理情况、施工日志及评论意见
     */
    private void getEventHandlesAndJournals(String sjid) {
        new PSHUploadEventService(getContext()).getSggcLogList(sjid + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PSHEventProcess>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                    }

                    @Override
                    public void onNext(List<PSHEventProcess> eventProcessList) {
                        if (!com.augurit.am.fw.utils.ListUtil.isEmpty(eventProcessList)) {
                            ll_advice.removeAllViews();
                            ll_event_process.removeAllViews();
                            ll_event_process.setVisibility(View.VISIBLE);
                            ll_advice.setVisibility(View.VISIBLE);

                            List<PSHEventProcess> realEventProcess = new ArrayList<>();
                            for (PSHEventProcess eventProcess : eventProcessList) {
                                if (!StringUtil.isEmpty(eventProcess.getLx())
                                        && eventProcess.getLx().equals("100")) {
                                    EventDetail.OpinionBean opinion = new EventDetail.OpinionBean();
                                    opinion.setOpinion(eventProcess.getContent());
                                    opinion.setUserName(eventProcess.getUsername());
                                    opinion.setTime(eventProcess.getAppTime());
                                    //显示领导插话（意见）列表
                                    com.augurit.agmobile.gzps.uploadevent.view.eventflow.AdviceView adviceView
                                            = new com.augurit.agmobile.gzps.uploadevent.view.eventflow.AdviceView(getContext());
                                    adviceView.initView(opinion);
                                    adviceView.addTo(ll_advice);
                                } else {
                                    realEventProcess.add(eventProcess);
                                }
                            }

                            if ("true".equals(mEventDetail.getIsbyself())) {
                                //如果是自行处理，不显示处理过程
                                return;
                            }

                            int index = 0;
                            boolean isFinished = false;
                            /*if(GzpsConstant.LINK_FH.equals(mEventDetail.getCurNode())
                                    && mEventDetail.getEventState().contains("已完成")){
                                isFinished = true;
                            }*/
                            if (StringUtil.isEmpty(mEventDetail.getState())
                                    || "3".equals(mEventDetail.getState())) {
                                isFinished = true;
                            }
                            for (PSHEventProcess eventProcess : realEventProcess) {

//                                if (!isFinished && (index == realEventProcess.size() - 1)) {
//                                    continue;
//                                }
                                PSHEventProcessView eventProcessView = new PSHEventProcessView(getContext());
                                eventProcessView.initView(eventProcess, isFinished, index, realEventProcess.size());
                                eventProcessView.addTo(ll_event_process);

                                index++;
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationUtil.unregister(getContext());
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        photo_check.onActivityResult(requestCode, resultCode, data);
        problem_type_sfys.onActivityResult(requestCode, resultCode, data);
        problem_type_sfds.onActivityResult(requestCode, resultCode, data);
        if (ListUtil.isNotEmpty(dictViewMap)) {
            for (ProblemTypeView view : dictViewMap.values()) {
                view.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
