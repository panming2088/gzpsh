package com.augurit.agmobile.gzps.statistic.service;

import android.content.Context;

import com.augurit.agmobile.gzps.common.constant.LoginConstant;
import com.augurit.agmobile.gzps.publicaffair.dao.IFacilityAffairApi;
import com.augurit.agmobile.gzps.statistic.dao.SignStatisticApi;
import com.augurit.agmobile.gzps.statistic.model.SignInfo;
import com.augurit.agmobile.gzps.statistic.model.SignStatisticInfoBean;
import com.augurit.agmobile.gzpssb.common.PortSelectUtil;
import com.augurit.am.cmpt.common.base.RequestConstant;
import com.augurit.am.fw.net.AMNetwork;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by xcl on 2017/11/17.
 */

public class SignStatisticService {


    public static final int LOAD_ITEM_PER_PAGE = 15;

    private AMNetwork amNetwork;
    private SignStatisticApi uploadStatistic;
    private Context mContext;

    public SignStatisticService(Context mContext) {
        this.mContext = mContext;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(IFacilityAffairApi.class);
            this.uploadStatistic = (SignStatisticApi) this.amNetwork.getServiceApi(SignStatisticApi.class);
        }
    }

    /**
     * 获取所有昨天和今天的签到数据
     *
     * @return
     */
    public Observable<List<SignStatisticInfoBean>> getUploadNearTimeData(String org_name) {
//        String url = BaseInfoManager.getSupportUrl(mContext);
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        return uploadStatistic.getSignNearTimeData(org_name)
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取某一个月的签到情况
     *
     * @return
     */
    public Observable<SignInfo> getSignInfo(String orgName, String yearMonth) {
//        String url = BaseInfoManager.getSupportUrl(mContext);
        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZPS_AGSUPPORT + RequestConstant.URL_DIVIDER;
        initAMNetwork(url);
        return uploadStatistic.getDetailSignInfo(orgName,yearMonth)
                .subscribeOn(Schedulers.io());
    }
}
