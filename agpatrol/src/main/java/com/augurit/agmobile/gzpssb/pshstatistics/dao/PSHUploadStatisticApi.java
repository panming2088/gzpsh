package com.augurit.agmobile.gzpssb.pshstatistics.dao;


import com.augurit.agmobile.gzpssb.pshstatistics.model.PSHUploadStatisticBean;
import com.augurit.agmobile.gzpssb.pshstatistics.model.PSHUploadYTStatisticBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by luob on 2017/12/21.
 */

public interface PSHUploadStatisticApi {

    @POST("rest/discharge/toPartsCount")
    Observable<PSHUploadStatisticBean> getPSHUploadStatisticForDistric(@Query("parentOrgName") String parentOrgName, @Query("dischargerType3") String reportType, @Query("startTime") long startTime, @Query("endTime") long endTime,
    @Query("hasCert1") String hasCert1,@Query("hasCert2") String hasCert2,@Query("hasCert3") String hasCert3,@Query("hasCert4") String hasCert4);

    @POST("rest/discharge/toPastNowDay")
    Observable<PSHUploadYTStatisticBean> getPSHUploadNearTimeData(@Query("dischargerType3") String layerName);


}
