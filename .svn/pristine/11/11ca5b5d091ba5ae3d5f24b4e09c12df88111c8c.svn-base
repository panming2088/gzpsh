package com.augurit.agmobile.gzpssb.pshstatistics.service;

import android.content.Context;

import com.augurit.agmobile.gzps.common.constant.LoginConstant;
import com.augurit.agmobile.gzpssb.common.PortSelectUtil;
import com.augurit.agmobile.gzpssb.pshstatistics.dao.PSHUploadStatisticApi;
import com.augurit.agmobile.gzpssb.pshstatistics.model.PSHUploadStatisticBean;
import com.augurit.agmobile.gzpssb.pshstatistics.model.PSHUploadYTStatisticBean;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.common.base.RequestConstant;
import com.augurit.am.fw.net.AMNetwork;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by xcl on 2017/11/17.
 */

public class PSHUploadStatisticService {


    public static final int LOAD_ITEM_PER_PAGE = 15;

    private AMNetwork amNetwork;
    private PSHUploadStatisticApi uploadStatistic;
    private Context mContext;

    public PSHUploadStatisticService(Context mContext) {
        this.mContext = mContext;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(PSHUploadStatisticApi.class);
            this.uploadStatistic = (PSHUploadStatisticApi) this.amNetwork.getServiceApi(PSHUploadStatisticApi.class);
        }
    }

    /**
     * 获取农污所有昨天和今天的上报数据
     *
     * @return
     */
    public Observable<PSHUploadYTStatisticBean> getPSHUploadNearTimeData(String layerName) {
//        String url = BaseInfoManager.getSupportUrl(mContext);
//        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZPSH_AGSUPPORT + RequestConstant.URL_DIVIDER;
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        return uploadStatistic.getPSHUploadNearTimeData(layerName)
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
    public Observable<PSHUploadStatisticBean> getPSHUploadStatisticForDistric(String parentOrgName, String reportType, long startTime, long endTime, String certificateType) {
        if (parentOrgName != null && parentOrgName.equals("全市")) {
            parentOrgName = null;
        }
        if (reportType != null && reportType.equals("全部")) {
            reportType = null;
        }
//        String url = BaseInfoManager.getSupportUrl(mContext);
//        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZPSH_AGSUPPORT + RequestConstant.URL_DIVIDER;
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        return uploadStatistic.getPSHUploadStatisticForDistric(parentOrgName, reportType, startTime, endTime, certificateType.charAt(0)=='1'?"1":"", certificateType.charAt(1)=='1'?"1":"", certificateType.charAt(2)=='1'?"1":"", certificateType.charAt(3)=='1'?"1":"")
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
