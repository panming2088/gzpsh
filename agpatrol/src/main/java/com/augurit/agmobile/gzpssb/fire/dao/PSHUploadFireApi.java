package com.augurit.agmobile.gzpssb.fire.dao;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzpssb.fire.model.GroundfireBean;
import com.augurit.agmobile.gzpssb.seweragewell.model.WellPhoto;

import java.util.HashMap;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 设施新增API
 * <p>
 * Created by xcl on 2017/11/15.
 */

public interface PSHUploadFireApi {

    /**
     * 地上式消防栓上报
     */
    @Multipart
    @POST("rest/pshxfs/toAddPshXfs")
    Observable<ResponseBody> uploadFire(
            @Query("json") String json,
            @PartMap() HashMap<String, RequestBody> requestBody);

    /**
     * 地上式消防栓编辑
     */
    @POST("rest/pshxfs/toUpdateXfs")
    Observable<ResponseBody> toUpdateFire(
            @Query("json") String json);

    /**
     * 地上式消防栓编辑
     */
    @Multipart
    @POST("rest/pshxfs/toUpdateXfs")
    Observable<ResponseBody> toUpdateFire(
            @Query("json") String json,
            @PartMap() HashMap<String, RequestBody> requestBody);


    /**
     * 地上式消防栓删除
     */
    @POST("rest/pshxfs/deleteXfs")
    Observable<ResponseBody> toDeleteFire(@Query("markId") String id, @Query("loginName") String loginName);



    /**
     * 查看详情
     */
    @POST("rest/pshxfs/getXfsDetail")
    Observable<ResponseBody> getFireDetail(@Path("id") String id);


    /**
     * 获取地市消防双列表
     */
    @POST("rest/pshxfs/getCollectList")
    Observable<Result3<List<GroundfireBean>>> getFireList(@Query("pageNo")int pageNo,
                                                          @Query("pageSize")int pageSize,
                                                          @Query("area")String area,
                                                          @Query("sfwh")String sfwh,
                                                          @Query("sfls")String sfls,
                                                          @Query("szwz")String szwz,
                                                          @Query("addr")String addr,
                                                          @Query("markPerson")String name,
                                                          @Query("loginName")String loginName,
                                                          @Query("startTime")Long startTime,
                                                          @Query("endTime")Long endTime,
                                                          @Query("id")Long id);


    /**
     * 注销排水户
     * @param id
     * @return
     */
    @POST("rest/discharge/pshCancle")
    Observable<ResponseBody> toCancelCollect(String id, String loginName);


    /**
     * 获取地上式消防栓图片
     */
    @POST("rest/pshxfs/getImgsByObjId")
    Observable<WellPhoto> getFirePhotos(@Query("objectId") String objectId);
}
