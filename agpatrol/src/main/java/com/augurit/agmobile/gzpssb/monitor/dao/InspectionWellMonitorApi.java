package com.augurit.agmobile.gzpssb.monitor.dao;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzpssb.monitor.model.InspectionWellMonitorInfo;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 窨井监测API
 * Created by xcl on 2017/11/15.
 */
public interface InspectionWellMonitorApi {

    /**
     * 获取监测数据详情
     *
     * @param jbjObjectId=1 &usid=
     *                      &pageSize=10
     *                      &pageNo=1
     *                      &loginName=Augur2
     */
    @POST("rest/psdyJbjRest/getJbjJcList")
    Observable<Result3<List<InspectionWellMonitorInfo>>> getWellMonitors1(@Query("pageNo") int pageNo,
                                                                          @Query("pageSize") int pageSize,
                                                                          @Query("loginName") String loginName,
                                                                          @Query("usid") String usid,
                                                                          @Query("jbjObjectId") String jbjObjectId,
                                                                          @Query("sfgjjd") String sfgjjd);

    @POST("rest/psdyJbjRest/getJbjJcList")
    Observable<Result3<List<InspectionWellMonitorInfo>>> getWellMonitors2(@Query("pageNo") int pageNo,
                                                                          @Query("pageSize") int pageSize,
                                                                          @Query("loginName") String loginName,
                                                                          @Query("usid") String usid,
                                                                          @Query("sfgjjd") String sfgjjd);

    @POST("rest/psdyJbjRest/getJbjJcList")
    Observable<Result3<List<InspectionWellMonitorInfo>>> getWellMonitors3(@Query("pageNo") int pageNo,
                                                                          @Query("pageSize") int pageSize,
                                                                          @Query("loginName") String loginName,
                                                                          @Query("jbjObjectId") String jbjObjectId,
                                                                          @Query("sfgjjd") String sfgjjd);

    /**
     * 删除接驳井上报的监测信息
     *
     * @return
     */
    @POST("rest/psdyJbjRest/deleteJbjJc?")
    Observable<ResponseBody> deleteJbjJc(@Query("id") long id,
                                         @Query("loginName") String loginName);


    /**
     * 新增监测信息
     *
     * @return
     */
    @Multipart
    @POST("rest/psdyJbjRest/addJbjJc")
    Observable<ResponseBody> addJbjJc(@Query("data") String dataJson, @PartMap() HashMap<String, RequestBody> requestBody);

    /**
     * 新增监测信息不带附件
     *
     * @return
     */
    @POST("rest/psdyJbjRest/addJbjJc")
    Observable<ResponseBody> addJbjJc(@Query("data") String dataJson);

    /**
     * 根据id获取监测信息
     *
     * @return
     */
    @POST("rest/psdyJbjRest/getJbjJcDetail")
    Observable<Result2<InspectionWellMonitorInfo>> getWellMonitorById(@Query("id") Long id, @Query("sfgjjd") String sfgjjd);
}