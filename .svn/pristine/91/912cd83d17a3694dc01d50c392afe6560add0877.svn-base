package com.augurit.agmobile.gzpssb.pshstatistics.service;

import android.content.Context;

import com.augurit.agmobile.gzps.common.constant.LoginConstant;
import com.augurit.agmobile.gzps.statistic.model.SignInfo;
import com.augurit.agmobile.gzps.statistic.model.SignStatisticInfoBean;
import com.augurit.agmobile.gzpssb.common.PortSelectUtil;
import com.augurit.agmobile.gzpssb.pshstatistics.dao.PSHSignStatisticApi;
import com.augurit.am.cmpt.common.base.RequestConstant;
import com.augurit.am.fw.net.AMNetwork;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by xcl on 2017/11/17.
 */

public class PSHSignStatisticService {


    public static final int LOAD_ITEM_PER_PAGE = 15;

    private AMNetwork amNetwork;
    private PSHSignStatisticApi uploadStatistic;
    private Context mContext;

    public PSHSignStatisticService(Context mContext) {
        this.mContext = mContext;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(PSHSignStatisticApi.class);
            this.uploadStatistic = (PSHSignStatisticApi) this.amNetwork.getServiceApi(PSHSignStatisticApi.class);
        }
    }


    /**
     * 获取农污所有昨天和今天的签到数据
     *
     * @return
     */
    public Observable<List<SignStatisticInfoBean>> getPSHUploadNearTimeData(String org_name, int isPs) {
//        String url = BaseInfoManager.getSupportUrl(mContext);
        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZPSH_AGSUPPORT + RequestConstant.URL_DIVIDER;
//        String url = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(url);
        return uploadStatistic.getNwSignNearTimeData(org_name, isPs)
                .subscribeOn(Schedulers.io());
    }



    /**
     * 获取某一个月的签到情况
     *
     * @return
     */
    public Observable<SignInfo> getPSHSignInfo(String orgName, String yearMonth, int isPs) {
//        String url = BaseInfoManager.getSupportUrl(mContext);
//        String url = RequestConstant.HTTP_REQUEST + LoginConstant.GZPSH_AGSUPPORT + RequestConstant.URL_DIVIDER;
        String url = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(url);
        return uploadStatistic.getNwDetailSignInfo(orgName, yearMonth, isPs)
                .subscribeOn(Schedulers.io());
    }
}
