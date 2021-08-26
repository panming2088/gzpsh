package com.augurit.agmobile.gzpssb.journal.service;

import android.content.Context;
import android.text.TextUtils;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzps.publicaffair.service.FacilityAffairService;
import com.augurit.agmobile.gzps.publicaffair.view.condition.FacilityAffairFilterConditionEvent;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzpssb.common.PortSelectUtil;
import com.augurit.agmobile.gzpssb.journal.dao.DialyPatrolApi;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournal;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournalDetail;
import com.augurit.agmobile.gzpssb.journal.util.DialyPatrolUtil;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.AMNetwork;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.PhoneUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.log.LogUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 包名：com.augurit.agmobile.gzpssb.journal.service
 * 类描述：
 * 创建人：PC23
 * 创建时间：2018/12/19 16:55
 * 修改人：PC23
 * 修改时间：2018/12/19 16:55
 * 修改备注：
 */
public class DialyPatrolService {
    private Context mContext;
    private AMNetwork amNetwork;
    private DialyPatrolApi journalApi;

    public DialyPatrolService(Context mContext) {
        this.mContext = mContext;
    }


    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(DialyPatrolApi.class);
            this.journalApi = (DialyPatrolApi) this.amNetwork.getServiceApi(DialyPatrolApi.class);
        }
    }


    /**
     * 修正部件标识
     *
     * @param modifiedIdentification
     * @return
     */
    public Observable<ResponseBody> upload(final PSHJournal modifiedIdentification) {

//        /**
//         * 再次编辑不需要校核是否有人上报过
//         */
        return uploadModification(modifiedIdentification);
    }

    private Observable<ResponseBody> uploadModification(PSHJournal modifiedFacility) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
//        ToastUtil.longToast(mContext, supportUrl);
        initAMNetwork(supportUrl);

        String prefix = "";
        int i = 0;
        List<Photo> attachments = modifiedFacility.getPhotos();
        final HashMap<String, HashMap<String, RequestBody>> map = new HashMap<>();    //
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        if (!ListUtil.isEmpty(attachments)) {
            for (Photo photo : attachments) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    requestMap.put("file" + i + "\"; filename=\"" + prefix + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                }
            }

        }

        List<Photo> thumbnail = modifiedFacility.getThumbnailPhotos();
        if (!ListUtil.isEmpty(thumbnail)) {
            for (Photo photo : thumbnail) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    requestMap.put("file" + i + "\"; filename=\"" + prefix + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                }
            }

        }

        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        String loginName = user.getLoginName();
        String userName = user.getUserName();

        String json = JsonUtil.getJson(modifiedFacility);
        Observable<ResponseBody> observable;

        if (!ListUtil.isEmpty(attachments)) {
            observable = journalApi.addPshDiary(json
                    , requestMap);
        } else {

            observable = journalApi.addPshDiary(json);
        }
        return observable
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取我的日常巡检列表
     *
     * @return
     */
    public Observable<Result3<List<PSHJournal>>> getMyModifications(int page, int pageSize,
                                                                    FacilityFilterCondition facilityFilterCondition) {

        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);

        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        String userName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getUserName();
        if (facilityFilterCondition == null) {
            return journalApi.getDiayr(page, pageSize, loginName,
                    null, null, null, null, null, null, null);
        } else {

            if (facilityFilterCondition.facilityType != null && facilityFilterCondition.facilityType.equals("全部")) {
                facilityFilterCondition.facilityType = null;
            }

            /**
             * 只有当是窨井时，才有已挂牌编号
             */
            if (TextUtils.isEmpty(facilityFilterCondition.attrFive)) {
                facilityFilterCondition.attrFive = null;
            }

            if (TextUtils.isEmpty(facilityFilterCondition.road)) {
                facilityFilterCondition.road = null;
            }

            if (TextUtils.isEmpty(facilityFilterCondition.address)) {
                facilityFilterCondition.address = null;
            }

            if (StringUtil.isEmpty(facilityFilterCondition.markId)) {
                facilityFilterCondition.markId = null;
            }

            return journalApi.getDiayr(page,
                    pageSize,
                    loginName,
                    facilityFilterCondition.startTime,
                    facilityFilterCondition.endTime,
                    facilityFilterCondition.road,
                    facilityFilterCondition.address,
                    facilityFilterCondition.attrFive,
                    facilityFilterCondition.facilityType,
                    facilityFilterCondition.markId);
        }
    }

    /**
     * 获取我的日常巡检列表
     *
     * @return
     */
    public Observable<Result3<List<PSHJournal>>> getPublicDiary(int page, int pageSize,
                                                                FacilityAffairFilterConditionEvent facilityFilterCondition) {

        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        String district = null;
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        String userName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getUserName();
        if (facilityFilterCondition == null) {
            FacilityAffairService facilityAffairService = new FacilityAffairService(mContext.getApplicationContext());
            boolean b = facilityAffairService.ifCurrentUserBelongToCityUser();
            if (!b) {
                district = facilityAffairService.getParentOrgOfCurrentUser();
            }
            return journalApi.getPublicDiary(page, pageSize, null, null, district);
        } else {
            district = facilityFilterCondition.getQueryDistrict();
            if (district != null && district.equals("全部")) {
                district = null;
            }

            return journalApi.getPublicDiary(page,
                    pageSize,
                    facilityFilterCondition.getStartTime(),
                    facilityFilterCondition.getEndTime(),
                    district);
        }
    }

    /**
     * 获取附件列表
     *
     * @param markId 部件标识id
     * @return
     */
    public Observable<ServerAttachment> getMyModificationAttachments(Long markId) {

        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);

        return journalApi.getDiayrAttach(markId)
                .subscribeOn(Schedulers.io())
                .onErrorReturn(new Func1<Throwable, ServerAttachment>() {
                    @Override
                    public ServerAttachment call(Throwable throwable) {
                        ServerAttachment serverIdentificationAttachment = new ServerAttachment();
                        serverIdentificationAttachment.setCode(500);
                        serverIdentificationAttachment.setMessage("500 - 系统内部错误");
                        return serverIdentificationAttachment;
                    }
                });
    }

    /**
     * 删除设施
     *
     * @param markId
     * @return
     */
    public Observable<Result2<String>> deleteJournal(Long markId) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        String phoneBrand = PhoneUtil.getDeviceBrand() + ":" + PhoneUtil.getSystemModel();
        LogUtil.d("okhttp", "phoneBrand:" + phoneBrand);
        return journalApi.deleteDiary(markId, loginName, phoneBrand)
                .subscribeOn(Schedulers.io());
    }

    /**
     * 日志再次编辑
     *
     * @param modifiedFacility
     * @return
     */
    public Observable<ResponseBody> editJournal(PSHJournal modifiedFacility) {
        //String supportUrl = BaseInfoManager.getSupportUrl(mContext);
//        String port = LoginConstant.UPLOAD_PORT[new Random().nextInt(LoginConstant.UPLOAD_PORT.length)];
        String port = ":8080";
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
//        ToastUtil.longToast(mContext, supportUrl);
        initAMNetwork(supportUrl);

        String prefix = "";
        int i = 0;
        List<Photo> attachments = modifiedFacility.getPhotos();
        final HashMap<String, HashMap<String, RequestBody>> map = new HashMap<>();    //
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        if (!ListUtil.isEmpty(attachments)) {
            for (Photo photo : attachments) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    requestMap.put("file" + i + "\"; filename=\"" + prefix + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                }
            }

        }

        List<Photo> thumbnail = modifiedFacility.getThumbnailPhotos();
        if (!ListUtil.isEmpty(thumbnail)) {
            for (Photo photo : thumbnail) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    requestMap.put("file" + i + "\"; filename=\"" + prefix + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                }
            }

        }

        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        String loginName = user.getLoginName();
        String userName = user.getUserName();
        String json = JsonUtil.getJson(modifiedFacility);
        Observable<ResponseBody> observable;

        if (!ListUtil.isEmpty(attachments)) {
            observable = journalApi.toEditDiary(json, modifiedFacility.getAttachment()
                    , requestMap);
        } else {

            observable = journalApi.toEditDiary(json, modifiedFacility.getAttachment());
        }
        return observable
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取设施纠错详情
     *
     * @param markId
     * @return
     */
    public Observable<PSHJournal> getDialyPatrolDetail(long markId) {
        String url = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(url);
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        return journalApi.toDiaryView(markId)
                .map(new Func1<PSHJournalDetail, PSHJournal>() {
                    @Override
                    public PSHJournal call(PSHJournalDetail modifyFacilityDetail) {
                        if (modifyFacilityDetail.getCode() != 200) {
                            return null;
                        }
                        return DialyPatrolUtil.getModifiedFacility(modifyFacilityDetail);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取附件列表
     *
     * @param sGuid 部件标识id
     * @return
     */
    public Observable<Result3<List<PSHHouse>>> getPshBySGuid(String sGuid) {

        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);

        return journalApi.getPshBySGuid(sGuid)
                .subscribeOn(Schedulers.io());
    }


    public Observable<PSHAffairDetail> getPSHUnitDetail(Long unitId) {
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        return journalApi.getPSHUnitDetail(unitId,loginName)
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取我的日常巡检列表
     *
     * @return
     */
    public Observable<Result3<List<PSHJournal>>> getPshDiaryLog(int id, int page, int pageSize,
                                                                FacilityFilterCondition facilityFilterCondition) {

        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);

        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        if (facilityFilterCondition == null) {
            return journalApi.getPshDiaryLog(page,
                    pageSize,
                    loginName,
                    null,
                    null,
                    id);
        } else {

            if (facilityFilterCondition.facilityType != null && facilityFilterCondition.facilityType.equals("全部")) {
                facilityFilterCondition.facilityType = null;
            }

            /**
             * 只有当是窨井时，才有已挂牌编号
             */
            if (TextUtils.isEmpty(facilityFilterCondition.attrFive)) {
                facilityFilterCondition.attrFive = null;
            }

            if (TextUtils.isEmpty(facilityFilterCondition.road)) {
                facilityFilterCondition.road = null;
            }

            if (TextUtils.isEmpty(facilityFilterCondition.address)) {
                facilityFilterCondition.address = null;
            }

            if (StringUtil.isEmpty(facilityFilterCondition.markId)) {
                facilityFilterCondition.markId = null;
            }

            return journalApi.getPshDiaryLog(page,
                    pageSize,
                    loginName,
                    facilityFilterCondition.startTime,
                    facilityFilterCondition.endTime,
                    id);
        }
    }

    /**
     * 问题上报列表
     *
     * @param pageNo
     * @param pageSize
     * @param pshId
     * @return
     */
    public Observable<List<PSHEventListItem>> search(int pageNo, int pageSize, int pshId) {
        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        return journalApi.getPshWtsbByPshId(pageNo, pageSize, user.getLoginName(), null, null, pshId)
                .map(new Func1<Result2<List<PSHEventListItem>>, List<PSHEventListItem>>() {
                    @Override
                    public List<PSHEventListItem> call(Result2<List<PSHEventListItem>> responseBody) {
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
}
