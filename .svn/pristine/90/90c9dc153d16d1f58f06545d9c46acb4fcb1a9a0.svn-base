package com.augurit.agmobile.gzps.drainage_unit_monitor.service;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JbjMonitorInfoBean;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzpssb.monitor.model.WellMonitorInfo;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

public interface JbjMonitorApi {

    /**
     * 监管列表
     *
     * @return
     */
    @POST("rest/pshJg/getJbjJgList")
    Observable<okhttp3.ResponseBody> getJbjJgList(@Query("pageNo") int pageNo,
                                                  @Query("pageSize") int pageSize,
                                                  @Query("loginName") String loginName,
                                                  @Query("usid") String usid,
                                                  @Query("jbjObjectId") String jbjObjectId);

    /**
     * 监管详情
     *
     * @return
     */
    @POST("rest/pshJg/getFormByJgid")
    Observable<okhttp3.ResponseBody> getJbjJgDetail(@Query("jgId") String jgId,
                                                    @Query("loginName") String loginName);

    /**
     * 新增监管信息
     *
     * @return
     */
    @Multipart
    @POST("rest/pshJg/addPshJbjjg")
    Observable<okhttp3.ResponseBody> addJbjJg(@Query("jgData") String jgData,
                                              @Query("jcData") String jcData,
                                              @Query("wtData") String wtData,
                                              @PartMap() HashMap<String, RequestBody> requestBody);

    /**
     * 新增监管信息
     *
     * @return
     */
    @POST("rest/pshJg/addPshJbjjg")
    Observable<okhttp3.ResponseBody> addJbjJg(@Query("jgData") String jgData,
                                              @Query("jcData") String jcData,
                                              @Query("wtData") String wtData);

    /**
     * 新增立管检查
     *
     * @return
     */
    @POST("rest/pshJg/addPshLgjc")
    Observable<okhttp3.ResponseBody> addPshLgjc(@Query("wtData") String dataJson);

    /**
     * 新增立管检查
     *
     * @return
     */
    @Multipart
    @POST("rest/pshJg/addPshLgjc")
    Observable<okhttp3.ResponseBody> addPshLgjc(@Query("json") String dataJson, @PartMap() HashMap<String, RequestBody> requestBody);


    /**
     * 获取立管检查详情
     * @param wtsbId 问题上报id
     * @param type 流转id值
     * @return
     */
    @POST("rest/pshJg/getLgjcDetail")
    Observable<okhttp3.ResponseBody> getLgjcDetail(@Query("wtsbId") String wtsbId,
                                                   @Query("type") String type,
                                                   @Query("loginName") String loginName);
}
