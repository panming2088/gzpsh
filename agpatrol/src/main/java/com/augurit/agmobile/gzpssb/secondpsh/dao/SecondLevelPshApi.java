package com.augurit.agmobile.gzpssb.secondpsh.dao;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfo;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

public interface SecondLevelPshApi {

    /**
     * 新增二级排水户
     */
    @Multipart
    @POST("rest/pshej/toAddPshej")
    Observable<ResponseBody> addSecondPsh(@Query("json") String json,
                                          @PartMap() HashMap<String, RequestBody> requestBody);

    /**
     * 修改二级排水户
     */
    @POST("rest/pshej/toUpdatePshej")
    Observable<ResponseBody> upDateSecondPsh(@Query("json") String json);
    /**
     * 修改二级排水户
     */
    @Multipart
    @POST("rest/pshej/toUpdatePshej")
    Observable<ResponseBody> upDateSecondPsh(@Query("json") String json,@PartMap() HashMap<String, RequestBody> requestBody);


    /**
     * 获取二级排水户列表列表
     */
    @POST("rest/pshej/getEjpsh")
    Observable<SecondLevelPshInfo> getSecondLevelPshList(@Query("unitId") String unitId,
                                                         @Query("pageNo") int pageNo,
                                                         @Query("pageSize") int pageSize,
                                                         @Query("pshtype1") String bigtype,
                                                         @Query("pshtype2") String smalltype,
                                                         @Query("pshtype3") String type,
                                                         @Query("startTime") Long startTime,
                                                         @Query("endTime") Long endTime,
                                                         @Query("id") Long uploadid,
                                                         @Query("ejaddr") String address,
                                                         @Query("ejname") String orgname
    );


    /**
     * * 获取排水户列表
     *
     * @param pageNo
     * @param pageSize
     * @param loginName
     * @return
     */
    @POST("rest/discharge/getCollectList")
    Observable<SecondLevelPshInfo> getCollectList(@Query("pageNo") int pageNo,
                                                  @Query("pageSize") int pageSize,
                                                  @Query("state") String state,
                                                  @Query("parentOrgName") String parentOrgName,
                                                  @Query("dischargerType1") String bigtype,
                                                  @Query("dischargerType2") String smalltype,
                                                  @Query("startTime") Long startTime,
                                                  @Query("endTime") Long endTime,
                                                  @Query("id") Long uploadid,
                                                  @Query("addr") String address,
                                                  @Query("name") String orgname,
                                                  @Query("loginName") String loginName);

    /**
     * 排水户信息删除
     */
    @POST("rest/pshej/deletePshej")
    Observable<ResponseBody> toDelete(@Query("id") String id, @Query("loginName") String loginName);


    /**
     * 根据一级排水户ID获取一级排水户及二级排水户信息列表
     * @param yjPshId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @POST("rest/pshej/getAllpsh")
    Observable<okhttp3.ResponseBody> getPshList(@Query("YjId") String yjPshId,
                                                @Query("pageNo") int pageNo,
                                                @Query("pageSize") int pageSize);
}
