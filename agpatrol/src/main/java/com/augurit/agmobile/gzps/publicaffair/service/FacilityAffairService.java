package com.augurit.agmobile.gzps.publicaffair.service;

import android.content.Context;

import com.augurit.agmobile.gzps.common.constant.LoginConstant;
import com.augurit.agmobile.gzps.publicaffair.PublicAffairActivity;
import com.augurit.agmobile.gzps.publicaffair.dao.IFacilityAffairApi;
import com.augurit.agmobile.gzps.publicaffair.model.FacilityAffairResponseBody;
import com.augurit.agmobile.gzps.publicaffair.model.ModifyFacilityDetail;
import com.augurit.agmobile.gzps.publicaffair.model.UploadFacilityDetail;
import com.augurit.agmobile.gzps.publicaffair.util.ModifyFacilityUtil;
import com.augurit.agmobile.gzps.publicaffair.util.UploadFacilityUtil;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.common.base.RequestConstant;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.AMNetwork;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xcl on 2017/11/17.
 */

public class FacilityAffairService {


    public static final int LOAD_ITEM_PER_PAGE = 15;

    private AMNetwork amNetwork;
    private IFacilityAffairApi facilityAffair;
    private Context mContext;

    public FacilityAffairService(Context mContext) {
        this.mContext = mContext;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.setConnectTime(20000);
            this.amNetwork.setReadTime(20000);
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(IFacilityAffairApi.class);
            this.facilityAffair = (IFacilityAffairApi) this.amNetwork.getServiceApi(IFacilityAffairApi.class);
        }
    }

    /**
     * ????????????
     *
     * @param parentOrgName ???
     * @param layerName     ????????????????????????????????????????????????
     * @param type          ????????????/????????????
     * @return
     */
    public Observable<FacilityAffairResponseBody> getFacilityAffairList(int page,
                                                                        String layerName,
                                                                        String parentOrgName,
                                                                        String type,
                                                                        Long startTime,
                                                                        Long endTime) {
        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZPS_AGSUPPORT + RequestConstant.URL_DIVIDER;
//        String url = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(url);

        if (layerName != null && layerName.equals("??????")) {
            layerName = null;
        }


        if (parentOrgName != null && parentOrgName.equals("??????")) {
            parentOrgName = null;
        }

//        if (parentOrgName != null && parentOrgName.equals("????????????")) {
//            parentOrgName = null;
//        }

        /**
         * ?????????????????????????????????????????????????????????????????????????????????????????????
         */
        if (!ifCurrentUserBelongToCityUser()) {
            parentOrgName = getParentOrgOfCurrentUser();
        }

        if ("????????????".equals(parentOrgName)){
            parentOrgName = "??????????????????";
        }

        if (type != null && type.equals("??????")) {
            type = null;
        }

        if (type != null && type.equals(PublicAffairActivity.uploadTypeConditions[1])) {
            type = "lack";
        } else if (type != null && type.equals(PublicAffairActivity.uploadTypeConditions[2])) {
            type = "correct";
        }

        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();

        return facilityAffair.getFacilityAffairList(page,
                LOAD_ITEM_PER_PAGE,
                layerName,
                parentOrgName,
                type,
                startTime,
                endTime,
                loginName)
                .subscribeOn(Schedulers.io());
    }


    /**
     * ????????????????????????
     *
     * @param markId
     * @return
     */
    public Observable<ModifiedFacility> getModifiedDetail(long markId) {
        String url = BaseInfoManager.getSupportUrl(mContext);
//        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZPS_AGSUPPORT + RequestConstant.URL_DIVIDER;
        initAMNetwork(url);
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        return facilityAffair.getModifiedDetail(markId, loginName)
                .map(new Func1<ModifyFacilityDetail, ModifiedFacility>() {
                    @Override
                    public ModifiedFacility call(ModifyFacilityDetail modifyFacilityDetail) {
                        if (modifyFacilityDetail.getCode() != 200){
                            return null;
                        }
                        return ModifyFacilityUtil.getModifiedFacility(modifyFacilityDetail);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * ????????????????????????
     *
     * @param markId
     * @return
     */
    public Observable<UploadedFacility> getUploadDetail(long markId) {
//        String url = BaseInfoManager.getSupportUrl(mContext);
        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZPS_AGSUPPORT + RequestConstant.URL_DIVIDER;
        initAMNetwork(url);
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        return facilityAffair.getUploadDetail(markId, loginName)
                .map(new Func1<UploadFacilityDetail, UploadedFacility>() {
                    @Override
                    public UploadedFacility call(UploadFacilityDetail uploadFacilityDetail) {
                        if (uploadFacilityDetail.getCode() != 200){
                            return null;
                        }
                        return UploadFacilityUtil.getUploadedFacility(uploadFacilityDetail);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * ????????????????????????
     *
     * @param markId
     * @return
     */
    public Observable<UploadedFacility> getUploadDetail2(long markId) {
        String url = BaseInfoManager.getSupportUrl(mContext);
//        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZPS_AGSUPPORT + RequestConstant.URL_DIVIDER;
        initAMNetwork(url);
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        return facilityAffair.getUploadDetail(markId, loginName)
                .map(new Func1<UploadFacilityDetail, UploadedFacility>() {
                    @Override
                    public UploadedFacility call(UploadFacilityDetail uploadFacilityDetail) {
                        if (uploadFacilityDetail.getCode() != 200){
                            return null;
                        }
                        return UploadFacilityUtil.getUploadedFacility(uploadFacilityDetail);
                    }
                })
                .subscribeOn(Schedulers.io());
    }


    /**
     * ???????????????????????????????????????
     *
     * @return
     */
    public boolean ifCurrentUserBelongToCityUser() {
        String userOrg = BaseInfoManager.getUserOrg(mContext);
        return userOrg.contains("???");
    }

    public String getParentOrgOfCurrentUser() {
        if (ifCurrentUserBelongToCityUser()) {
            return null;
        }
        return BaseInfoManager.getUserOrg(mContext);
    }
}
