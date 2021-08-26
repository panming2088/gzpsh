package com.augurit.agmobile.gzpssb.uploadfacility.dao;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzpssb.uploadfacility.model.HangUpWellBean;
import com.augurit.agmobile.gzpssb.uploadfacility.model.UploadedListBean;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 设施新增API
 * <p>
 * Created by xcl on 2017/11/15.
 */

public interface SewerageMyUploadApi {

    /**
     * 我的上报列表
     *
     * @param pageNo
     * @param pageSize
     * @param loginName
     * @return
     */
    @POST("rest/discharge/getCollectList")
    Observable<Result3<List<UploadedListBean>>> getCollectList(@Query("pageNo") int pageNo,
                                                               @Query("pageSize") int pageSize,
                                                               @Query("state") String state,
                                                               @Query("parentOrgName") String parentOrgName,
                                                               @Query("dischargerType1") String bigtype,
                                                               @Query("dischargerType2") String smalltype,
                                                               @Query("dischargerType3") String type,
                                                               @Query("startTime") Long startTime,
                                                               @Query("endTime") Long endTime,
                                                               @Query("id") Long uploadid,
                                                               @Query("addr") String address,
                                                               @Query("name") String orgname,
                                                               @Query("loginName") String loginName);

    /**
     * 工业排水户
     *
     * @param pageNo
     * @param pageSize
     * @param loginName
     * @return
     */
    @POST("rest/discharge/searchGypshAll")
    Observable<Result3<List<UploadedListBean>>> getGypshList(@Query("pageNo") int pageNo,
                                                             @Query("pageSize") int pageSize,
                                                             @Query("drs") int drs,
                                                             @Query("kjArea") String parentOrgName,
                                                             @Query("dischargerType1") String bigtype,
                                                             @Query("dischargerType2") String smalltype,
                                                             @Query("dischargerType3") String type,
                                                             @Query("startTime") Long startTime,
                                                             @Query("endTime") Long endTime,
                                                             @Query("id") Long uploadid,
                                                             @Query("addr") String address,
                                                             @Query("name") String orgname,
                                                             @Query("loginName") String loginName);

    /**
     * 部件缺失（新增）的附件获取接口
     *
     * @param markId 部件标识id
     * @return
     */
    @POST("rest/parts/getPartsNewAttach")
    Observable<ServerAttachment> getPartsNewAttach(@Query("markId") long markId);

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
     * @param loginName
     * @param datasJson
     * @return
     */
    @POST("rest/parts/deleteLinePipe")
    Observable<Result2<String>> deletePipe(@Query("loginName") String loginName,
                                           @Query("datas") String datasJson);

    @POST("rest/pshJhjgjRest/getPshGjList")
    Observable<Result2<List<HangUpWellBean>>> getHookUpWellBeans(@Query(("loginName")) String loginName,
                                                                 @Query("pageNo") int pageNo,
                                                                 @Query("pageSize") int pageSize,
                                                                 @Query("startTime") String startTime,
                                                                 @Query("endTime") String endTime,
                                                                 @Query("gjlx") String gjlx,
                                                                 @Query("sfMy") String sfMy,
                                                                 @Query("jhjObjectId") String jhjObjectId,
                                                                 @Query("jhjAddr") String jhjAddr,
                                                                 @Query("jhjType") String jhjType);

}
