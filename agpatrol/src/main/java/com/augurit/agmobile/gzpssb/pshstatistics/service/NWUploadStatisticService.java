package com.augurit.agmobile.gzpssb.pshstatistics.service;

import android.content.Context;

import com.augurit.agmobile.gzps.common.constant.LoginConstant;
import com.augurit.agmobile.gzpssb.pshstatistics.dao.NWUploadStatisticApi;
import com.augurit.agmobile.gzpssb.pshstatistics.model.NWUploadStats;
import com.augurit.agmobile.gzpssb.pshstatistics.model.NWUploadYTStats;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.common.base.RequestConstant;
import com.augurit.am.fw.net.AMNetwork;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by xcl on 2017/11/17.
 */

public class NWUploadStatisticService {


    public static final int LOAD_ITEM_PER_PAGE = 15;

    private AMNetwork amNetwork;
    private NWUploadStatisticApi uploadStatistic;
    private Context mContext;

    public NWUploadStatisticService(Context mContext) {
        this.mContext = mContext;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(NWUploadStatisticApi.class);
            this.uploadStatistic = (NWUploadStatisticApi) this.amNetwork.getServiceApi(NWUploadStatisticApi.class);
        }
    }
    /**
     * 获取农污所有昨天和今天的上报数据
     *
     * @return
     */
    public Observable<NWUploadYTStats> getNwUploadNearTimeData(String layerName) {
//        String url = BaseInfoManager.getSupportUrl(mContext);
        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZWS_AGSUPPORT + RequestConstant.URL_DIVIDER;
        initAMNetwork(url);
        return uploadStatistic.getNwUploadNearTimeData(layerName)
                .subscribeOn(Schedulers.io());
    }


    /**
     * 获取上报统计信息
     *
     * @param parentOrgName
     * @param startTime
     * @param endTime
     * @return
     */
    public Observable<NWUploadStats> getNwUploadStatisticForDistric(String parentOrgName, String reportType, long startTime, long endTime) {
        if (parentOrgName != null && parentOrgName.equals("全市")) {
            parentOrgName = null;
        }
        if (reportType != null && reportType.equals("全部")) {
            reportType = null;
        }
//        String url = BaseInfoManager.getSupportUrl(mContext);
        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZWS_AGSUPPORT + RequestConstant.URL_DIVIDER;
        initAMNetwork(url);
        return uploadStatistic.getNwUploadStatisticForDistric(parentOrgName,reportType, startTime,endTime)
                .subscribeOn(Schedulers.io());
    }


    /**
     * 判断当前用户是否是市级用户
     *
     * @return
     */
    public boolean ifCurrentUserBelongToCityUser() {
        String userOrg = BaseInfoManager.getUserOrg(mContext);
        return userOrg.contains("市");
    }

    public String getParentOrgOfCurrentUser() {
        if (ifCurrentUserBelongToCityUser()) {
            return null;
        }
        return BaseInfoManager.getUserOrg(mContext);
    }
}
