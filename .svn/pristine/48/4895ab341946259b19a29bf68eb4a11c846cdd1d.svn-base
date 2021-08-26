package com.augurit.agmobile.gzpssb.uploadfacility.dao;

import com.augurit.agmobile.gzps.common.model.ResponseBody;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 新增上报接口（上报的端口号变了，为了不影响其他接口，单独抽出）
 * Created by xucil on 2018/1/17.
 */

public interface PSHUploadFacilityApi2 {
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
            @Query("pcode") String pCode,
            @Query("childCode") String childCodes,
            @Query("cityVillage") String uploadPlace,
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
            @Query("attachment") String deletedPhotos,
            @Query("pcode") String pCode,
            @Query("childCode") String childCodes,
            @Query("cityVillage") String uploadPlace);
}
