package com.augurit.agmobile.gzpssb.pshstatistics.service;

import android.content.Context;

import com.augurit.agmobile.gzps.common.constant.LoginConstant;
import com.augurit.agmobile.gzps.statistic.model.SignInfo;
import com.augurit.agmobile.gzps.statistic.model.SignStatisticInfoBean;
import com.augurit.agmobile.gzpssb.pshstatistics.dao.NWSignStatisticApi;
import com.augurit.am.cmpt.common.base.RequestConstant;
import com.augurit.am.fw.net.AMNetwork;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by xcl on 2017/11/17.
 */

public class NWSignStatisticService {


    public static final int LOAD_ITEM_PER_PAGE = 15;

    private AMNetwork amNetwork;
    private NWSignStatisticApi uploadStatistic;
    private Context mContext;

    public NWSignStatisticService(Context mContext) {
        this.mContext = mContext;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(NWSignStatisticApi.class);
            this.uploadStatistic = (NWSignStatisticApi) this.amNetwork.getServiceApi(NWSignStatisticApi.class);
        }
    }


    /**
     * 获取农污所有昨天和今天的签到数据
     *
     * @return
     */
    public Observable<List<SignStatisticInfoBean>> getNwUploadNearTimeData(String org_name, int isPs) {
//        String url = BaseInfoManager.getSupportUrl(mContext);
        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZWS_AGSUPPORT + RequestConstant.URL_DIVIDER;
        initAMNetwork(url);
        return uploadStatistic.getNwSignNearTimeData(org_name, isPs)
                .subscribeOn(Schedulers.io());
    }



    /**
     * 获取某一个月的签到情况
     *
     * @return
     */
    public Observable<SignInfo> getNwSignInfo(String orgName, String yearMonth, int isPs) {
//        String url = BaseInfoManager.getSupportUrl(mContext);
        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZWS_AGSUPPORT + RequestConstant.URL_DIVIDER;
        initAMNetwork(url);
        return uploadStatistic.getNwDetailSignInfo(orgName, yearMonth, isPs)
                .subscribeOn(Schedulers.io());
    }
}
