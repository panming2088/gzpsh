package com.augurit.agmobile.gzps.drainage_unit_monitor.service;

import android.content.Context;
import android.text.TextUtils;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorInfoBean;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzpssb.monitor.dao.WellMonitorApi;
import com.augurit.agmobile.gzpssb.monitor.model.WellMonitorInfo;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemBean;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.AMNetwork;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

public class JbjMonitorService {

    public static final int TIMEOUT = 60;  //网络超时时间（秒）

    private AMNetwork amNetwork;

    private Context mContext;

    private JbjMonitorApi api;

    public JbjMonitorService(Context mContext) {
        this.mContext = mContext;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.setReadTime(TIMEOUT * 1000);
            this.amNetwork.setWriteTime(TIMEOUT * 1000);
            this.amNetwork.setConnectTime(TIMEOUT * 1000);
            this.amNetwork.build();
            this.amNetwork.registerApi(JbjMonitorApi.class);
            this.api = (JbjMonitorApi) this.amNetwork.getServiceApi(JbjMonitorApi.class);
        }
    }

    /**
     * 获取我的监管列表
     *
     * @return
     */
    public Observable<String> getJbjJgList(int page, int pageSize, String usid, String jbjObjectId) {

        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);

        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        return api.getJbjJgList(page, pageSize, loginName, usid, jbjObjectId)
                .map(new Func1<okhttp3.ResponseBody, String>() {
                    @Override
                    public String call(okhttp3.ResponseBody responseBody) {
                        try {
                            return responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                });
    }

    /**
     * 获取监管详情
     *
     * @return
     */
    public Observable<String> getJbjJgDetail(String jgId) {

        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        return api.getJbjJgDetail(jgId, loginName)
                .map(new Func1<okhttp3.ResponseBody, String>() {
                    @Override
                    public String call(okhttp3.ResponseBody responseBody) {
                        try {
                            return responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                });
    }

    /**
     * 新增监测信息
     */
    public Observable<ResponseBody> addJbjMonitor(JbjMonitorInfoBean jbjMonitorInfoBean, List<Photo> photoList) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        String jgData = JsonUtil.getJson(jbjMonitorInfoBean.getJgData());
        String jcData = null;
        String wtData = null;
        if (jbjMonitorInfoBean.getJcData() != null) {
            jcData = JsonUtil.getJson(jbjMonitorInfoBean.getJcData());
        }
        if (jbjMonitorInfoBean.getWtData() != null) {
            wtData = JsonUtil.getJson(jbjMonitorInfoBean.getWtData());
        }
        if (ListUtil.isEmpty(photoList)) {
            return api.addJbjJg(jgData, jcData, wtData)
                    .map(new Func1<okhttp3.ResponseBody, ResponseBody>() {
                        @Override
                        public ResponseBody call(okhttp3.ResponseBody responseBody) {
                            try {
                                String reslut = responseBody.string();
                                return JsonUtil.getObject(reslut, new TypeToken<ResponseBody>() {
                                }.getType());
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                    });
        }
        int i = 0;
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        for (Photo photo : photoList) {
            if (photo.getLocalPath() != null) {
                File file = new File(photo.getLocalPath());
                requestMap.put("file" + i + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                i++;
            }
        }
        return api.addJbjJg(jgData, jcData, wtData, requestMap)
                .map(new Func1<okhttp3.ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(okhttp3.ResponseBody responseBody) {
                        try {
                            String reslut = responseBody.string();
                            return JsonUtil.getObject(reslut, new TypeToken<ResponseBody>() {
                            }.getType());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                });
    }

    /**
     * 新增立管检查
     */
    public Observable<ResponseBody> addPshLgjc(ProblemBean problemBean, List<Photo> photoList) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        if (ListUtil.isEmpty(photoList)) {
            return api.addPshLgjc(JsonUtil.getJson(problemBean))
                    .map(new Func1<okhttp3.ResponseBody, ResponseBody>() {
                        @Override
                        public ResponseBody call(okhttp3.ResponseBody responseBody) {
                            try {
                                String reslut = responseBody.string();
                                return JsonUtil.getObject(reslut, new TypeToken<ResponseBody>() {
                                }.getType());
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                    });
        }
        int i = 0;
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        for (Photo photo : photoList) {
            if (photo.getLocalPath() != null) {
                File file = new File(photo.getLocalPath());
                requestMap.put("file" + i + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                i++;
            }
        }
        return api.addPshLgjc(JsonUtil.getJson(problemBean), requestMap)
                .map(new Func1<okhttp3.ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(okhttp3.ResponseBody responseBody) {
                        try {
                            String reslut = responseBody.string();
                            return JsonUtil.getObject(reslut, new TypeToken<ResponseBody>() {
                            }.getType());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                });
    }

    /**
     * 获取立管检查详情
     *
     * @param wtsbId 问题上报id
     * @param type   流转id值
     * @return
     */
    public Observable<okhttp3.ResponseBody> getLgjcDetail(String wtsbId, String type) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        return api.getLgjcDetail(wtsbId, type, loginName);

    }
}
