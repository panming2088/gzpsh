package com.augurit.agmobile.gzps.publicaffair.dao;

import com.augurit.agmobile.gzps.publicaffair.model.FacilityAffairResponseBody;
import com.augurit.agmobile.gzps.publicaffair.model.ModifyFacilityDetail;
import com.augurit.agmobile.gzps.publicaffair.model.UploadFacilityDetail;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xcl on 2017/11/17.
 */

public interface IFacilityAffairApi {

    @POST("rest/parts/searchCorrOrLack")
    Observable<FacilityAffairResponseBody> getFacilityAffairList(@Query("pageNo") int page,
                                                                 @Query("pageSize") int pageSize,
                                                                 @Query("layerName") String layerName,
                                                                 @Query("parentOrgName") String parentOrgName,
                                                                 @Query("type") String type,
                                                                 @Query("startTime") Long startTime,
                                                                 @Query("endTime") Long endTime,
                                                                 @Query("loginName") String loginName);


    @POST("rest/parts/toView?type=correct")
    Observable<ModifyFacilityDetail> getModifiedDetail(@Query("id")long id,@Query("loginName") String loginName);

    @POST("rest/parts/toView?type=lack")
    Observable<UploadFacilityDetail> getUploadDetail(@Query("id")long id,@Query("loginName") String loginName);
}
