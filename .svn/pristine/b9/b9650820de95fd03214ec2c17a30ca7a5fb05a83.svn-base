package com.augurit.agmobile.gzpssb.pshstatistics.dao;

import com.augurit.agmobile.gzps.statistic.model.SignInfo;
import com.augurit.agmobile.gzps.statistic.model.SignStatisticInfoBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by luob on 2017/12/21.
 */

public interface PSHSignStatisticApi {

    @GET("rest/pshDailySign/getStatisticsInfo")
    Observable<List<SignStatisticInfoBean>> getNwSignNearTimeData(@Query("orgName") String org_name, @Query("sysflag") int sysflag);

    @GET("rest/pshDailySign/getDetailSignInfo/{orgName}/{yearMonth}")
    Observable<SignInfo> getNwDetailSignInfo(@Path("orgName") String orgName, @Path("yearMonth") String yearMonth, @Query("sysflag") int sysflag);
}
