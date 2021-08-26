package com.augurit.agmobile.gzpssb.uploadfacility.service;

import android.content.Context;

import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.uploadfacility.view.myuploadlist.FacilityFilterCondition;
import com.augurit.agmobile.gzpssb.common.PortSelectUtil;
import com.augurit.agmobile.gzpssb.uploadfacility.dao.SewerageMyUploadApi;
import com.augurit.agmobile.gzpssb.uploadfacility.model.HangUpWellBean;
import com.augurit.agmobile.patrolcore.common.table.dao.TableDataManager;
import com.augurit.agmobile.patrolcore.common.table.model.Project;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.AMNetwork;

import java.util.List;

import rx.Observable;

/**
 * 排水户关联接户井Service
 * Created by xcl on 2017/11/14.
 */

public class SewerageHangUpWellService {

    public static final int LOAD_ITEM_PER_PAGE = 15;

    private AMNetwork amNetwork;
    private SewerageMyUploadApi identificationApi;
    private Context mContext;
    private List<Project> projects;
    private TableDataManager tableDataManager;

    public SewerageHangUpWellService(Context mContext) {
        this.mContext = mContext;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.setReadTime(20000);
            this.amNetwork.setWriteTime(20000);
            this.amNetwork.setConnectTime(20000);
            this.amNetwork.build();
            this.amNetwork.registerApi(SewerageMyUploadApi.class);
            this.identificationApi = (SewerageMyUploadApi) this.amNetwork.getServiceApi(SewerageMyUploadApi.class);
        }
    }


    /**
     * 获取我的上报列表
     *
     * @return
     */
    public Observable<Result2<List<HangUpWellBean>>> getHookUpWellBeans(int pageNo, int pageSize, String gjlx, FacilityFilterCondition condition) {

        String url = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(url);

        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        return identificationApi.getHookUpWellBeans(loginName, pageNo, pageSize, String.valueOf(condition.startTime), String.valueOf(condition.endTime), gjlx,
                "1", condition.markId, condition.address, "全部".equals(condition.facilityType) ? "" : condition.facilityType);

    }
}
