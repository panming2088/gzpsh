package com.augurit.agmobile.gzps.uploadfacility.dao;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 设施新增API
 * <p>
 * Created by xcl on 2017/11/15.
 */

public interface UploadFacilityApi {

    /**
     * 获取部件缺失列表
     *
     * @param pageNo
     * @param pageSize
     * @param userName
     * @return
     */
    @POST("rest/parts/getPartsNew")
    Observable<Result2<List<UploadedFacility>>> getPartsNew(@Query("pageNo") int pageNo,
                                                            @Query("pageSize") int pageSize,
                                                            @Query("markPersonId") String userName,
                                                            @Query("checkState") String checkState);


    /**
     * 部件缺失（新增）的附件获取接口
     *
     * @param markId 部件标识id
     * @return
     */
    @POST("rest/parts/getPartsNewAttach")
    Observable<ServerAttachment> getPartsNewAttach(@Query("markId") long markId);


    /**
     * @param loginName
     * @param datasJson
     * @return
     */
    @POST("rest/parts/deleteLinePipe")
    Observable<Result2<String>> deletePipe(@Query("loginName") String loginName,
                                           @Query("datas") String datasJson);

    /**
     * 对上报管线数据的修改
     */
    @POST("rest/parts/updateReportLinePipe")
    Observable<ResponseBody> updateReportLinePipe(@Query("id") String id, @Query("loginName") String loginName, @Query("datas") String dataJson);
    /**
     * 新增管线
     */
    @POST("rest/parts/addLinePipe")
    Observable<ResponseBody> addLinePipe(@Query("loginName") String loginName
            , @Query("datas") String dataJson);

    /**
     * 更新管线
     */
    @POST("rest/parts/updateLinePipe")
    Observable<ResponseBody> updateLinePipe(@Query("loginName") String loginName, @Query("datas") String dataJson);

    /**
     * 新增和取消无法挂接
     * @param objectId 设施的objectId
     * @param wfgj
     * @param wfgj_reason
     * @return
     */
    @POST("rest/psdyJbjRest/updatePsdyState")
    Observable<ResponseBody> partsNotHook(@Query("loginName") String loginName,@Query("objectid") String objectId, @Query("wfgj") int wfgj, @Query("wfgj_reason") String wfgj_reason);

    /**
     * 新增存疑
     * @param objectId 设施的objectId
     * @param doubtType 存疑类型：1点，2线，3面
     * @param loginName 登录名
     * @param description 原因
     * @param layerName 设施名称（画面的就是“面”）
     * @return
     */
    @POST("rest/parts/partsDoubt")
    Observable<ResponseBody> partsDoubt(@Query("objectId") String objectId, @Query("doubtType") int doubtType,@Query("loginName") String loginName, @Query("description") String description,@Query("layerName") String layerName,@Query("rings") String rings);

    /**
     * @param id
     * @param loginName 登录名
     * @return
     */
    @POST("rest/parts/partsDoubt")
    Observable<Result2<String>> partsDoubt(@Query("id") String id,
                                           @Query("loginName") String loginName);

}
