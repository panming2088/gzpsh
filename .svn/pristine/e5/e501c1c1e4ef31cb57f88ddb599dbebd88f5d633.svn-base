package com.augurit.agmobile.gzpssb.uploadfacility.dao;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
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

public interface PSHMyUploadWellApi {

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param userName
     * @param checkState
     * @param startTime
     * @param endTime
     * @param road
     * @param addr
     * @param layerName
     * @param markId
     * @return
     */
    @POST("rest/PshParts/getPartsNew")
    Observable<Result3<List<UploadedFacility>>> getPartsNew(@Query("pageNo") int pageNo,
                                                            @Query("pageSize") int pageSize,
                                                            @Query("markPersonId") String userName,
                                                            @Query("checkState") String checkState,
                                                            @Query("startTime") Long startTime,
                                                            @Query("endTime") Long endTime,
                                                            @Query("road") String road,
                                                            @Query("addr") String addr,
                                                            @Query("layerName") String layerName,
                                                            @Query("objectId") String markId,
                                                            @Query("attrFive") String attrFive);


    /**
     * 部件缺失（新增）的附件获取接口
     *
     * @param markId 部件标识id
     * @return
     */
    @POST("rest/PshParts/getPartsNewAttach")
    Observable<ServerAttachment> getPartsNewAttach(@Query("markId") long markId);


    /**
     * 部件的缺失（带附件）
     *
     * @return
     */
    @Multipart
    @POST("rest/PshParts/partsNew")
    Observable<ResponseBody> partsNew(
            @Query("id") Long markId,
            @Query("loginName") String loginName,
            @Query("markPersonId") String markPersonId,
            @Query("markPerson") String markPerson,
//            @Query("markTime") Long markTime,
            @Query("description") String description,
            @Query("x") double x,
            @Query("y") double y,
            @Query("addr") String addr,
            @Query("componentType") String componentType,
            @Query("attrOne") String attrOne,
            @Query("attrTwo") String attrTwo,
            @Query("attrThree") String attrThree,
            @Query("attrFour") String attrFour,
            @Query("attrFive") String attrFive,
            @Query("road") String road,
            @Query("layerId") int layerId,
            @Query("layerName") String layerName,
            @Query("usid") String usid,
            @Query("isBinding") int isBinding,
            @Query("attachment") String deletedPhotos,
            @Query("userX") double userX,
            @Query("userY") double userY,
            @Query("userAddr") String userAddr,
            @PartMap() HashMap<String, RequestBody> requestBody);


    /**
     * 部件的缺失（不带附件）
     *
     * @return
     */
    @POST("rest/PshParts/partsNew")
    Observable<ResponseBody> partsNew(
            @Query("id") Long markId,
            @Query("loginName") String loginName,
            @Query("markPersonId") String markPersonId,
            @Query("markPerson") String markPerson,
//            @Query("markTime") Long markTime,
            @Query("description") String description,
            @Query("x") double x,
            @Query("y") double y,
            @Query("addr") String addr,
            @Query("componentType") String componentType,
            @Query("attrOne") String attrOne,
            @Query("attrTwo") String attrTwo,
            @Query("attrThree") String attrThree,
            @Query("attrFour") String attrFour,
            @Query("attrFive") String attrFive,
            @Query("road") String road,
            @Query("layerId") int layerId,
            @Query("layerName") String layerName,
            @Query("usid") String usid,
            @Query("isBinding") int isBinding,
            @Query("userX") double userX,
            @Query("userY") double userY,
            @Query("userAddr") String userAddr,
            @Query("attachment") String deletedPhotos);
}
