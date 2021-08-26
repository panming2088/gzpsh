package com.augurit.agmobile.gzps.uploadfacility.service;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzps.uploadfacility.dao.ApprovalOpinionApi;
import com.augurit.agmobile.gzps.uploadfacility.dao.UploadLayerApi;
import com.augurit.agmobile.gzps.uploadfacility.model.ApprovalOpinion;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.ServerCorrectErrorData;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadInfo;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.uploadfacility.util.UploadLayerFieldKeyConstant;
import com.augurit.agmobile.mapengine.common.utils.GeometryUtil;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.service.LayerServiceFactory;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.net.AMNetwork;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.log.LogUtil;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.tasks.SpatialRelationship;
import com.esri.core.tasks.ags.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 审核意见相关方法
 * Created by xcl on 2017/12/7.
 */

public class ApprovalOpinionService {

    private Context mContext;

    private AMNetwork amNetwork;
    private ApprovalOpinionApi uploadLayerApi;

    public ApprovalOpinionService(Context mContext) {
        this.mContext = mContext;
    }


    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(ApprovalOpinionApi.class);
            this.uploadLayerApi = (ApprovalOpinionApi) this.amNetwork.getServiceApi(ApprovalOpinionApi.class);
        }
    }

    /**
     * 获取审核意见列表
     * @param markId
     * @param reportTypeChinese
     * @return
     */
    public Observable<List<ApprovalOpinion>> getOpinions(Long markId, String reportTypeChinese) {

        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);

        String reportType = null;
        if (reportTypeChinese.contains("确认") || reportTypeChinese.equals(UploadLayerFieldKeyConstant.CONFIRM)) {
            reportType = UploadLayerFieldKeyConstant.CONFIRM;
        } else if (reportTypeChinese.contains("新增")  || reportTypeChinese.equals(UploadLayerFieldKeyConstant.ADD)) {
            reportType = UploadLayerFieldKeyConstant.ADD;
        } else if (reportTypeChinese.contains("纠错") || reportTypeChinese.equals(UploadLayerFieldKeyConstant.CORRECT_ERROR)) {
            reportType = UploadLayerFieldKeyConstant.CORRECT_ERROR;
        }

        return uploadLayerApi.getOpinion(markId, reportType)
                .map(new Func1<Result2<List<ApprovalOpinion>>, List<ApprovalOpinion>>() {
                    @Override
                    public List<ApprovalOpinion> call(Result2<List<ApprovalOpinion>> listResult2) {
                        return listResult2.getData();
                    }
                }).subscribeOn(Schedulers.io());
//                .onErrorReturn(new Func1<Throwable, List<ApprovalOpinion>>() {
//                    @Override
//                    public List<ApprovalOpinion> call(Throwable throwable) {
//                        return new ArrayList<>();
//                    }
//                });
    }

    /**
     * 获取审核意见列表
     * @param markId
     * @return
     */
    public Observable<List<ApprovalOpinion>> getlinePipeCheckRecord(Long markId) {

        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        initAMNetwork(supportUrl);

        return uploadLayerApi.getlinePipeCheckRecord(markId)
                .map(new Func1<Result2<List<ApprovalOpinion>>, List<ApprovalOpinion>>() {
                    @Override
                    public List<ApprovalOpinion> call(Result2<List<ApprovalOpinion>> listResult2) {
                        return listResult2.getData();
                    }
                }).subscribeOn(Schedulers.io());
    }
}
