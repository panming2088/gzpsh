package com.augurit.agmobile.gzpssb.uploadfacility.dao;


import com.augurit.agmobile.gzps.common.model.Result2;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xucil on 2017/12/27.
 */

public interface PSHDeleteFacilityApi {

    /**
     *
     * @param reportType modify / add : 纠错/新增
     * @param reportId
     * @param loginName
     * @param phoneBrand 手机型号
     * @return
     */
    @POST("rest/PshParts/deleteReport")
    Observable<Result2<String>> deleteFacility(@Query("reportType") String reportType,
                                               @Query("reportId") Long reportId,
                                               @Query("loginName") String loginName,
                                               @Query("phoneBrand") String phoneBrand);
}
