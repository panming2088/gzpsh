package com.augurit.agmobile.gzps.drainage_unit_monitor.service;

import android.content.Context;
import android.text.TextUtils;

import com.augurit.agmobile.gzps.common.model.Result;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.model.Result3;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.Data;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JBJ;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorInfoBean;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyJg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyJgjl;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyWtjc;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.SubmitJBJCheck;
import com.augurit.agmobile.gzps.uploadfacility.dao.ApprovalOpinionApi;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzpssb.jbjpsdy.model.PsdyJbj;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.model.Result4;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.AMNetwork;
import com.augurit.am.fw.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MonitorService {
    private Context mContext;

    private AMNetwork amNetwork;
    private Api api;

    public MonitorService(Context mContext) {
        this.mContext = mContext;
    }


    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.setReadTime(20000);
            this.amNetwork.setWriteTime(20000);
            this.amNetwork.setConnectTime(20000);
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(ApprovalOpinionApi.class);
            this.api = (Api) this.amNetwork.getServiceApi(Api.class);
        }
    }

    public Observable<Result3<PsdyJg, PsdyJgjl>> getPsdyJg(Long psdyId) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        return api.getPsdyJg(psdyId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Data> getPshZljc(Long psdyId) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        return api.getPshZljc(psdyId)
                .map(new Func1<Result2<Data>, Data>() {
                    @Override
                    public Data call(Result2<Data> result) {
                        if(result.getCode() == 200){
                            return result.getData();
                        } else {
                            return new Data();
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Result2> addPsdyJg(String loginName, String markPersonId, String markPerson, String markTime, String psdyId, String psdyName) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        SubmitJBJCheck data = new SubmitJBJCheck();
        data.setLoginName(loginName);
        data.setPsdyId(psdyId);
        data.setPsdyName(psdyName);
        return api.addPsdyJg(new Gson().toJson(data))
                .map(new Func1<Result2, Result2>() {
                    @Override
                    public Result2 call(Result2 result) {
                        return result;
//                        if(result.getCode() == 200){
//                            return result.getData();
//                        } else {
//                            return new PsdyJg();
//                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<JBJ>> getPsdyJgJbjList(Long psdyId, int pageNo, int pageSize, String keyNode) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        return api.getPsdyJgJbjList(psdyId, pageNo, pageSize, keyNode)
                .map(new Func1<Result2<List<JBJ>>, List<JBJ>>() {
                    @Override
                    public List<JBJ> call(Result2<List<JBJ>> result) {
                        if(result.getCode() == 200){
                            return result.getData();
                        } else {
                            return new ArrayList();
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> addPsdyZljc(String loginName, String psdyId, String psdyName, String gyqsyhjl,
                                             String aqyhjcjl, String kzaqpxjl, String yclssywjl) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        Data data = new Data();
        data.setLoginName(loginName);
        data.setPsdyId(psdyId);
        data.setPsdyName(psdyName);
        data.setGyqsyhjl(gyqsyhjl);
        data.setAqyhjcjl(aqyhjcjl);
        data.setKzaqpxjl(kzaqpxjl);
        data.setYclssywjl(yclssywjl);
        return api.addPsdyZljc(new Gson().toJson(data))
                .map(new Func1<Result2, String>() {
                    @Override
                    public String call(Result2 result) {
                        if(result.getCode() == 200){
                            return result.getMessage();
                        } else {
                            return "";
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取待在办列表
     *
     * @return
     */
    public Observable<List<PSHEventListItem>> getEventList(int pageNo, int pageSize, String type,
                                                           FacilityFilterCondition facilityFilterCondition,
                                                           String psdyId, String keywork) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        if (facilityFilterCondition == null) {
            return api.getEventList(user.getLoginName(), pageNo, pageSize, "yes",
//                "create",
//                "desc",
                    type, type, null, null, null, null, null, psdyId, keywork)
                    .map(new Func1<Result4<List<PSHEventListItem>>, List<PSHEventListItem>>() {
                        @Override
                        public List<PSHEventListItem> call(Result4<List<PSHEventListItem>> eventListResult) {
                            try {
                                List<PSHEventListItem> eventListItems = new ArrayList<>();
                                if (eventListResult.getCode() != 200) {
                                    return eventListItems;
                                }
                                return eventListResult.getRows();
                            } catch (Exception e) {
                                return null;
                            }
                        }
                    });
        } else {

            if (TextUtils.isEmpty(facilityFilterCondition.road)) {
                facilityFilterCondition.road = null;
            }

            if (TextUtils.isEmpty(facilityFilterCondition.address)) {
                facilityFilterCondition.address = null;
            }

            if (StringUtil.isEmpty(facilityFilterCondition.markId)) {
                facilityFilterCondition.markId = null;
            }

            return api.getEventList(user.getLoginName(), pageNo, pageSize, "yes",
//                "create",
//                "desc",
                    type, type, facilityFilterCondition.startTime, facilityFilterCondition.endTime,
                    facilityFilterCondition.markId, facilityFilterCondition.address,
                    facilityFilterCondition.road, psdyId, keywork)
                    .map(new Func1<Result4<List<PSHEventListItem>>, List<PSHEventListItem>>() {
                        @Override
                        public List<PSHEventListItem> call(Result4<List<PSHEventListItem>> eventListResult) {
                            try {
                                List<PSHEventListItem> eventListItems = new ArrayList<>();
                                if (eventListResult.getCode() != 200) {
                                    return eventListItems;
                                }
                                return eventListResult.getRows();
                            } catch (Exception e) {
                                return null;
                            }
                        }
                    });
        }
    }


    /**
     * 获取接驳井的连线信息
     */
    public Observable<List<PsdyJbj>> queryPsdyJbj(String psdyObjectId) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        return api.queryPsdyJbj("", "", psdyObjectId)
                .map(new Func1<Result2<List<PsdyJbj>>, List<PsdyJbj>>() {
                    @Override
                    public List<PsdyJbj> call(Result2<List<PsdyJbj>> result2) {
                        if(result2.getCode() == 200){
                            return result2.getData();
                        } else {
                            return new ArrayList<>();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 查询有无该排水单元记录
     */
    public Observable<PsdyWtjc> getPsdyWtjc(String psdyId, String type) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        return api.getPsdyWtjc(psdyId, type)
                .map(new Func1<Result2<PsdyWtjc>, PsdyWtjc>() {
                    @Override
                    public PsdyWtjc call(Result2<PsdyWtjc> result2) {
                        if(result2.getCode() == 200){
                            return result2.getData();
                        } else {
                            return new PsdyWtjc();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 新增问题检查记录
     */
    public Observable<Result2> addPsdyWtjc(String loginName, String psdyId, String psdyName, String type, String ywjc) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("loginName", loginName);
        jsonObject.addProperty("psdyId", psdyId);
        jsonObject.addProperty("psdyName", psdyName);
        jsonObject.addProperty("jclx", type);
        jsonObject.addProperty("ywjc", ywjc);
        return api.addPsdyWtjc(jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 新增监管
     */
    public Observable<Result2> addPsdyWtjc2(String loginName, String psdyId, String psdyName, String wellId, String wellType) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("loginName", loginName);
        jsonObject.addProperty("psdyId", psdyId);
        jsonObject.addProperty("psdyName", psdyName);
        jsonObject.addProperty("wellId", wellId);
        jsonObject.addProperty("wellType", wellType);
        return api.addPsdyWtjc(jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 排水单元编辑
     */
    public Observable<Result2> updatePsdySde(String psdyId, String sfywfl, String sfwcdb, String wcdbsj,
                                             String qsr, String qsr_lxfs, String whr, String whr_lxfs,
                                             String glr, String glr_lxfs, String jgr, String jgr_lxfs) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        return api.updatePsdySde(psdyId, sfywfl, sfwcdb, wcdbsj, qsr, qsr_lxfs, whr, whr_lxfs, glr, glr_lxfs, jgr, jgr_lxfs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
