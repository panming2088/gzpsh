package com.augurit.agmobile.gzpssb.worknews.service;

import android.content.Context;

import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.worknews.model.WorkNews;
import com.augurit.agmobile.gzpssb.common.PortSelectUtil;
import com.augurit.agmobile.gzpssb.worknews.dao.PSHWorkNewsApi;
import com.augurit.agmobile.patrolcore.common.table.dao.TableDataManager;
import com.augurit.agmobile.patrolcore.common.table.model.Project;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.fw.net.AMNetwork;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xcl on 2017/11/15.
 */

public class PSHWorkNewsService {

    private AMNetwork amNetwork;
    private PSHWorkNewsApi PSHWorkNewsApi;
    private Context mContext;
    private List<Project> projects;
    private TableDataManager tableDataManager;

    public PSHWorkNewsService(Context mContext) {
        this.mContext = mContext;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(PSHWorkNewsApi.class);
            this.PSHWorkNewsApi = (PSHWorkNewsApi) this.amNetwork.getServiceApi(PSHWorkNewsApi.class);
        }
    }

    /**
     * 获取最新的10条巡检动态
     * @return
     */
    public Observable<List<WorkNews>>  getWorkNews(){
//        String baseServerUrl = BaseInfoManager.getSupportUrl(mContext);
        String baseServerUrl =  PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(baseServerUrl);
        return PSHWorkNewsApi.getRecentTenWorkNews()
                .map(new Func1<Result2<List<WorkNews>>, List<WorkNews>>() {
                    @Override
                    public List<WorkNews> call(Result2<List<WorkNews>> listResult2) {
                        return listResult2.getData();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

}
