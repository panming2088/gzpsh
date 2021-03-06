package com.augurit.agmobile.gzpssb.uploadevent.service;

import android.content.Context;
import android.text.TextUtils;

import com.augurit.agmobile.gzps.common.model.Result;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.componentmaintenance.service.ComponentService;
import com.augurit.agmobile.gzps.publicaffair.model.EventAffair;
import com.augurit.agmobile.gzps.uploadevent.dao.DataResult;
import com.augurit.agmobile.gzps.uploadevent.dao.GetPersonByOrgApiData;
import com.augurit.agmobile.gzps.uploadevent.model.Assigneers;
import com.augurit.agmobile.gzps.uploadevent.model.EventDetail;
import com.augurit.agmobile.gzps.uploadevent.model.EventListItem;
import com.augurit.agmobile.gzps.uploadevent.model.EventProcess;
import com.augurit.agmobile.gzps.uploadevent.model.NextLinkOrg;
import com.augurit.agmobile.gzps.uploadevent.model.OrgItem;
import com.augurit.agmobile.gzps.uploadevent.util.TaskParamUtil;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzpssb.uploadevent.dao.PSHUploadEventApi;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventCount;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventDetail;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventProcess;
import com.augurit.agmobile.gzpssb.uploadevent.model.PshEventAffair;
import com.augurit.agmobile.gzpssb.uploadevent.model.Result4;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.AMNetwork;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;

import org.apache.commons.collections4.MapUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xcl on 2017/11/11.
 */

public class PSHUploadEventService {

    private AMNetwork amNetwork;
    private PSHUploadEventApi mEventApi;
    private Context mContext;

    public PSHUploadEventService(Context mContext) {
        this.mContext = mContext;
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
//        String supportUrl = "http://192.168.43.139:8080/agsupport_swj/";
        //      supportUrl = "http://192.168.43.130:8080/agsupport_swj/";
//        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.setReadTime(20000);
            this.amNetwork.setWriteTime(20000);
            this.amNetwork.setConnectTime(20000);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(PSHUploadEventApi.class);
            this.mEventApi = (PSHUploadEventApi) this.amNetwork.getServiceApi(PSHUploadEventApi.class);
        }
    }

    public Observable<EventAffair> search(int pageNo, int pageSize, String district, String componentTypeCode, String eventTypeCode, String state) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.search(user.getLoginName(), pageNo, pageSize, null, district, componentTypeCode, eventTypeCode, state, null, null)
                .map(new Func1<Result2<EventAffair>, EventAffair>() {
                    @Override
                    public EventAffair call(Result2<EventAffair> responseBody) {
                        try {
                            if (responseBody.getCode() != 200) {
                                return null;
                            }
                            return responseBody.getData();
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });
    }

    public Observable<PshEventAffair> getProblemReport(int pageNo, int pageSize, String district, String componentTypeCode, String sslx, String eventTypeCode, String state, String psdyName) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.getProblemReport(user.getLoginName(), pageNo, pageSize, null, district, sslx, eventTypeCode, state, psdyName, null, null)
                .map(new Func1<Result2<PshEventAffair>, PshEventAffair>() {
                    @Override
                    public PshEventAffair call(Result2<PshEventAffair> responseBody) {
                        try {
                            if (responseBody.getCode() != 200) {
                                return null;
                            }
                            return responseBody.getData();
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });
    }

    public Observable<EventAffair> search(int pageNo, int pageSize, String keyword) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.search(user.getLoginName(), pageNo, pageSize, keyword, null, null, null, null, null, null)
                .map(new Func1<Result2<EventAffair>, EventAffair>() {
                    @Override
                    public EventAffair call(Result2<EventAffair> responseBody) {
                        try {
                            if (responseBody.getCode() != 200) {
                                return null;
                            }
                            return responseBody.getData();
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });
    }


    /**
     * ????????????ID????????????????????????
     */
    public Observable<EventAffair.EventAffairBean> getEventDetail(String sjid) {
        return mEventApi.getEventDetail(sjid)
                .map(new Func1<Result2<EventAffair.EventAffairBean>, EventAffair.EventAffairBean>() {
                    @Override
                    public EventAffair.EventAffairBean call(Result2<EventAffair.EventAffairBean> eventAffairBeanResult2) {
                        return eventAffairBeanResult2.getData();
                    }
                });
    }

    /**
     * ????????????
     *
     * @return
     */
    public Observable<Boolean> uploadEvent(String isSendMessage, String json, String assignee, String assigneeName, final HashMap<String, RequestBody> requestBody) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.uploadEvent(isSendMessage, user.getLoginName(), json, "isMobile", assignee, assigneeName, requestBody)
                .map(new Func1<Result<String>, Boolean>() {
                    @Override
                    public Boolean call(Result<String> responseBody) {
                        try {
                            return responseBody.isSuccess();
                        } catch (Exception e) {
                            return false;
                        }
                    }
                });
    }

    /**
     * ????????????
     *
     * @return
     */
    public Observable<Result2<String>> uploadWtsb(String json, final HashMap<String, RequestBody> requestBody) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.uploadWtsb(json, requestBody);
    }


    /**
     * ??????????????????
     *
     * @param reportId
     * @param deleteImgs
     * @param json
     * @param assignee
     * @param assigneeName
     * @param requestBody
     * @return
     */
    public Observable<Boolean> editEvent(long reportId, String deleteImgs,
                                         String json, String assignee, String assigneeName, String isSendMessage,
                                         final HashMap<String, RequestBody> requestBody) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();

        /*
        String url = "http://192.168.253.5:8080/agsupport_swj/";

        AMNetwork test = new AMNetwork(url);
        test.setReadTime(30000);
        test.addLogPrint();
        test.addRxjavaConverterFactory();
        test.build();
        test.registerApi(UploadEventApi.class);
        UploadEventApi testApi = (UploadEventApi) test.getServiceApi(UploadEventApi.class);
        */


        return mEventApi.editEvent(reportId, deleteImgs, user.getLoginName(), json, "isMobile", assignee, assigneeName, isSendMessage, requestBody)
                .map(new Func1<Result<String>, Boolean>() {
                    @Override
                    public Boolean call(Result<String> responseBody) {
                        try {
                            return responseBody.isSuccess();
                        } catch (Exception e) {
                            return false;
                        }

                    }
                });
    }


    /**
     * ??????????????????
     *
     * @param reportId
     * @param deleteImgs
     * @param json
     * @param assignee
     * @param assigneeName
     * @return
     */
    public Observable<Boolean> editEvent(long reportId, String deleteImgs,
                                         String json, String assignee, String assigneeName, String isSendMessage) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.editEvent(reportId, deleteImgs, user.getLoginName(), json, "isMobile", assignee, assigneeName, isSendMessage)
                .map(new Func1<Result<String>, Boolean>() {
                    @Override
                    public Boolean call(Result<String> responseBody) {
                        try {
                            return responseBody.isSuccess();
                        } catch (Exception e) {
                            return false;
                        }

                    }
                });
    }

    /**
     * ??????????????????????????????????????????
     *
     * @return
     */
    public Observable<List<Assigneers>> getTaskUserByloginName() {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.getTaskUserByloginName(user.getLoginName());
    }

    /**
     * ??????????????????????????????????????????
     *
     * @return
     */
    public Observable<Result<List<Assigneers>>> getNextActivityInfo(String taskInstDbid) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.getNextActivityInfo(user.getLoginName(), taskInstDbid);
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public Observable<List<EventListItem>> getDlcEventList(int pageNo, int pageSize) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        String jsonParam = TaskParamUtil.getEventJsonParam("GX_XCYH");
        String groupBy = "sbsj";
        String groupDir = "desc";
        String pageParam = TaskParamUtil.getEventPageParam("" + pageNo, "" + pageSize);

        return mEventApi.getDlcEventList(user.getLoginName(), groupBy, groupDir, jsonParam, pageParam, 82, "isMobile")
                .map(new Func1<Result<List<EventListItem>>, List<EventListItem>>() {
                    @Override
                    public List<EventListItem> call(Result<List<EventListItem>> eventListResult) {
                        try {
                            List<EventListItem> eventListItems = new ArrayList<>();
                            if (!eventListResult.isSuccess()) {
                                return eventListItems;
                            }
                            return eventListResult.getResult();
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public Observable<List<EventListItem>> getClzEventList(int pageNo, int pageSize) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        String jsonParam = TaskParamUtil.getEventJsonParam("GX_XCYH");
        String pageParam = TaskParamUtil.getEventPageParam("" + pageNo, "" + pageSize);

        return mEventApi.getClzEventList(user.getLoginName(), jsonParam, pageParam, 82, "isMobile")
                .map(new Func1<Result<List<EventListItem>>, List<EventListItem>>() {
                    @Override
                    public List<EventListItem> call(Result<List<EventListItem>> eventListResult) {
                        try {
                            List<EventListItem> eventListItems = new ArrayList<>();
                            if (!eventListResult.isSuccess()) {
                                return eventListItems;
                            }
                            return eventListResult.getResult();
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public Observable<List<EventListItem>> getDZbEventList(int pageNo, int pageSize) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        String jsonParam = TaskParamUtil.getEventJsonParam("GX_XCYH");
        String pageParam = TaskParamUtil.getEventPageParam("" + pageNo, "" + pageSize);

        return mEventApi.getDZbSummary(user.getLoginName(), jsonParam, pageParam, 82, "isMobile")
                .map(new Func1<Result<List<EventListItem>>, List<EventListItem>>() {
                    @Override
                    public List<EventListItem> call(Result<List<EventListItem>> eventListResult) {
                        try {
                            List<EventListItem> eventListItems = new ArrayList<>();
                            if (!eventListResult.isSuccess()) {
                                return eventListItems;
                            }
                            return eventListResult.getResult();
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public Observable<List<EventListItem>> getYbEventList(int pageNo, int pageSize) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        String jsonParam = TaskParamUtil.getEventJsonParam("GX_XCYH");
        String pageParam = TaskParamUtil.getEventPageParam("" + pageNo, "" + pageSize);

        return mEventApi.getYbSummary(user.getLoginName(), jsonParam, pageParam, 82, "isMobile")
                .map(new Func1<Result<List<EventListItem>>, List<EventListItem>>() {
                    @Override
                    public List<EventListItem> call(Result<List<EventListItem>> eventListResult) {
                        try {
                            List<EventListItem> eventListItems = new ArrayList<>();
                            if (!eventListResult.isSuccess()) {
                                return eventListItems;
                            }
                            return eventListResult.getResult();
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public Observable<List<EventListItem>> getDbjEventList(int pageNo, int pageSize) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        String jsonParam = TaskParamUtil.getEventJsonParam("GX_XCYH");
        String pageParam = TaskParamUtil.getEventPageParam("" + pageNo, "" + pageSize);

        return mEventApi.getDbjEventList(user.getLoginName(), jsonParam, pageParam, 82, "isMobile")
                .map(new Func1<Result<List<EventListItem>>, List<EventListItem>>() {
                    @Override
                    public List<EventListItem> call(Result<List<EventListItem>> eventListResult) {
                        try {
                            List<EventListItem> eventListItems = new ArrayList<>();
                            if (!eventListResult.isSuccess()) {
                                return eventListItems;
                            }
                            return eventListResult.getResult();
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public Observable<List<EventListItem>> getYbjEventList(int pageNo, int pageSize) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        String jsonParam = TaskParamUtil.getEventJsonParam("GX_XCYH");
        String pageParam = TaskParamUtil.getEventPageParam("" + pageNo, "" + pageSize);

        return mEventApi.getYbjEventList(user.getLoginName(), jsonParam, pageParam, 82, "isMobile")
                .map(new Func1<Result<List<EventListItem>>, List<EventListItem>>() {
                    @Override
                    public List<EventListItem> call(Result<List<EventListItem>> eventListResult) {
                        try {
                            List<EventListItem> eventListItems = new ArrayList<>();
                            if (!eventListResult.isSuccess()) {
                                return eventListItems;
                            }
                            return eventListResult.getResult();
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    public Observable<Result2<EventDetail>> getDetails(String taskInstDbid, String procInstDbId, String masterEntityKey) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.getEventDetails(user.getLoginName(), taskInstDbid, procInstDbId, masterEntityKey);

    }

    /**
     * ????????????
     *
     * @return
     */
    public Observable<Boolean> signEvent(String taskInstId) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.signEvent(user.getLoginName(),
                user.getUserName(),
                taskInstId)
                .map(new Func1<Result<String>, Boolean>() {
                    @Override
                    public Boolean call(Result<String> responseBody) {
                        return responseBody.isSuccess();
                    }
                });
    }

    /**
     * ???????????????????????????????????????
     *
     * @return
     */
    public Observable<List<EventProcess>> getEventHandlesAndJournals(String sjid, int pageNo, int pageSize) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.getEventHandlesAndJournals(sjid, pageNo, pageSize)
                .map(new Func1<Result2<List<EventProcess>>, List<EventProcess>>() {
                    @Override
                    public List<EventProcess> call(Result2<List<EventProcess>> responseBody) {
//                        String json = JsonUtil.getJson(responseBody);
                        return responseBody.getData();
                    }
                });
    }

    /**
     * ???????????????????????????????????????
     *
     * @return
     */
    public Observable<List<PSHEventProcess>> getSggcLogList(String sjid) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.getSggcLogList(sjid)
                .map(new Func1<Result2<List<PSHEventProcess>>, List<PSHEventProcess>>() {
                    @Override
                    public List<PSHEventProcess> call(Result2<List<PSHEventProcess>> responseBody) {
//                        String json = JsonUtil.getJson(responseBody);
                        return responseBody.getData();
                    }
                });
    }


    public Observable<List<NextLinkOrg>> getNextLinkOrg() {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.getEventOrg(user.getLoginName());
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public Observable<Boolean> wfSend(int wtsbId,
                                      int nextState,
                                      int state,
                                      Long sfzf,
                                      String content,
                                      List<Photo> photos) {
        String prefix = "";
        int i = 0;
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        if (!ListUtil.isEmpty(photos)) {
            for (Photo photo : photos) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    requestMap.put("file" + i + "\"; filename=\"" + prefix + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                }
            }

        }
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        if (!MapUtils.isEmpty(requestMap)) {
            return mEventApi.wfSend(user.getLoginName(),
                    wtsbId, nextState, state,
                    user.getUserName(), sfzf, content, requestMap)
                    .map(new Func1<Result2<String>, Boolean>() {
                        @Override
                        public Boolean call(Result2<String> responseBody) {
                            if (responseBody == null) {
                                return false;
                            }
                            if (responseBody.getCode() == 200) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    });
        } else {
            return mEventApi.wfSend(user.getLoginName(),
                    wtsbId, nextState, state,
                    user.getUserName(), sfzf, content)
                    .map(new Func1<Result2<String>, Boolean>() {
                        @Override
                        public Boolean call(Result2<String> responseBody) {
                            if (responseBody == null) {
                                return false;
                            }
                            if (responseBody.getCode() == 200) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    });
        }
    }

    public Observable<Boolean> saveJdFile(String taskInstDbid, List<Photo> photos) {
        String prefix = "";
        int i = 0;
        final HashMap<String, HashMap<String, RequestBody>> map = new HashMap<>();    //
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        if (!ListUtil.isEmpty(photos)) {
            for (Photo photo : photos) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    requestMap.put("file" + i + "\"; filename=\"" + prefix + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                }
            }

        }
        return mEventApi.saveJdFile(taskInstDbid, requestMap)
                .map(new Func1<ResponseBody, Boolean>() {
                    @Override
                    public Boolean call(ResponseBody responseBody) {
                        try {
                            if ("true".equals(responseBody.string())) {
                                return true;
                            } else {
                                return false;
                            }
                        } catch (Exception e) {
                            return false;
                        }
                    }
                });
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public Observable<String> wfReturn(String isSendMessage, String masterEntityKey, String taskInstId, String content) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.wfReturn(masterEntityKey, isSendMessage, user.getLoginName(), taskInstId, content)
                .map(new Func1<ResponseBody, String>() {
                    @Override
                    public String call(ResponseBody responseBody) {
                        return null;
                    }
                });
    }


    /**
     * ??????
     *
     * @param procInstId
     * @param
     * @return
     */
    public Observable<Result<String>> wfRetrive(String procInstId, String retrieveComments) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.wfRetrive(procInstId, user.getLoginName(), retrieveComments);
    }

    /**
     * ??????
     *
     * @param sjid
     * @param procInstId
     * @param taskInstDbid
     * @return
     */
    public Observable<Result<String>> wfDelete(String sjid, String procInstId, String taskInstDbid) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.wfDelete(sjid, procInstId, user.getLoginName(), taskInstDbid);
    }


    /**
     * ??????
     *
     * @return
     */
    public Observable<String> wfReassign(String taskInstId, String assignee, String assigneeName,
                                         String activityChineseName, String content, String activityName,
                                         String procInstId, String sjid, String isSendMessage) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.wfReassign(user.getLoginName(), taskInstId, assignee, assigneeName,
                activityChineseName, content, activityName, procInstId, sjid, isSendMessage)
                .map(new Func1<ResponseBody, String>() {
                    @Override
                    public String call(ResponseBody responseBody) {
                        return null;
                    }
                });
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public Observable<Boolean> uploadJournal(String content, List<Photo> photos, String sjid) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        String prefix = "";
        int i = 0;
        final HashMap<String, HashMap<String, RequestBody>> map = new HashMap<>();    //
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        if (!ListUtil.isEmpty(photos)) {
            for (Photo photo : photos) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    requestMap.put("file" + i + "\"; filename=\"" + prefix + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                }
            }

        }
        return mEventApi.uploadJournal(user.getLoginName(),
                user.getUserName(),
                content,
                sjid,
                requestMap)
                .map(new Func1<Result2<String>, Boolean>() {
                    @Override
                    public Boolean call(Result2<String> responseBody) {
                        if (responseBody.getCode() == 200) {
                            return true;
                        }
                        return false;
                    }
                });
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public Observable<Boolean> uploadAdvice(String advice, String sjid) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.uploadAdvice(user.getLoginName(), user.getUserName(),
                advice,
                sjid)
                .map(new Func1<Result2<String>, Boolean>() {
                    @Override
                    public Boolean call(Result2<String> responseBody) {
                        if (responseBody.getCode() == 200) {
                            return true;
                        } else {
                            return false;
                        }

                    }
                });
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public Observable<PSHEventCount> getEventCount() {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
//        String jsonParam = TaskParamUtil.getEventJsonParam("GX_XCYH");
        return mEventApi.getEventCount(user.getLoginName())
                .map(new Func1<Result2<PSHEventCount>, PSHEventCount>() {
                    @Override
                    public PSHEventCount call(Result2<PSHEventCount> eventCountResult) {
                        try {
                            if (eventCountResult.getCode() == 200) {
                                return eventCountResult.getData();
                            } else {
                                return null;
                            }
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public Observable<List<PSHEventListItem>> getEventList(int pageNo, int pageSize, String type, FacilityFilterCondition facilityFilterCondition) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        if (facilityFilterCondition == null) {
            return mEventApi.getEventList(user.getLoginName(), pageNo, pageSize, "yes",
//                "create",
//                "desc",
                    type, null, null, null, null, null)
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

            return mEventApi.getEventList(user.getLoginName(), pageNo, pageSize, "yes",
//                "create",
//                "desc",
                    type, facilityFilterCondition.startTime, facilityFilterCondition.endTime, facilityFilterCondition.markId, facilityFilterCondition.address, facilityFilterCondition.road)
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
     * ??????usid???layerUrl???????????????
     *
     * @param usid
     * @param url
     * @param callback
     */
    public void queryComponents(final String usid, final String url, final Callback2<Component> callback) {
        new ComponentService(mContext).queryComponents(usid, url, callback);
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    public Observable<PSHEventDetail> getDetails(int wtsbId, String type) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.getDetail(wtsbId, type)
                .map(new Func1<ResponseBody, PSHEventDetail>() {
                    @Override
                    public PSHEventDetail call(ResponseBody responseBody) {
                        try {
                            String str = responseBody.string();
                            return JsonUtil.getObject(str, PSHEventDetail.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });

    }


    /**
     * ??????usid???layerName???????????????
     *
     * @param usid
     * @param layerName
     * @param callback
     */
    public void queryComponentsWithLayerName(final String usid, final String layerName, final Callback2<Component> callback) {
        new ComponentService(mContext).queryComponentsWithLayerName(usid, layerName, callback);
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public Observable<List<OrgItem>> getOrgItemList() {
        return mEventApi.getOrgListForUploadEvent();
    }

    public Observable<GetPersonByOrgApiData> getPersonByOrgApiDataObservable(String code, String name) {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.getPersonByOrgCodeAndName(user.getLoginName(), code, name);
    }

    public Observable<DataResult> getJurisdictionByLoginName() {
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return mEventApi.getJurisdictionByLoginName(user.getLoginName());
    }

    /**
     * ????????????
     *
     * @param id
     * @return
     */
    public Observable<Result2<String>> deleteEvent(int id, String delReason) {
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        return mEventApi.deleteEvent(id, loginName, delReason)
                .subscribeOn(Schedulers.io());
    }

}
