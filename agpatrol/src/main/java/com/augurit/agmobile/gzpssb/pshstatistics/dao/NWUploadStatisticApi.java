package com.augurit.agmobile.gzpssb.pshstatistics.dao;


import com.augurit.agmobile.gzpssb.pshstatistics.model.NWUploadStats;
import com.augurit.agmobile.gzpssb.pshstatistics.model.NWUploadYTStats;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by luob on 2017/12/21.
 */

public interface NWUploadStatisticApi {

    @POST("rest/reces/toPartsCount")
    Observable<NWUploadStats> getNwUploadStatisticForDistric(@Query("parentOrgName") String parentOrgName, @Query("collectType") String reportType, @Query("startTime") long startTime, @Query("endTime") long endTime);

    @POST("rest/reces/toPastNowDay")
    Observable<NWUploadYTStats> getNwUploadNearTimeData(@Query("layerName") String layerName);


}
