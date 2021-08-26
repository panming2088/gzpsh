package com.augurit.agmobile.gzpssb.journal.dao;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournal;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournalDetail;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;

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
 * 包名：com.augurit.agmobile.gzpssb.journal.dao
 * 类描述：排水户新增日志接口
 * 创建人：luobiao
 * 创建时间：2018/12/19 16:56
 * 修改人：luobiao
 * 修改时间：2018/12/19 16:56
 * 修改备注：
 */
public interface DialyPatrolApi {
    /**
     * 新增巡查日志（带附件）
     *
     * @return
     */
    @Multipart
    @POST("rest/pshDiary/addPshDiary")
    Observable<ResponseBody> addPshDiary(
            @Query("json") String json,
            @PartMap() HashMap<String, RequestBody> requestBody);

    /**
     * 新增巡查日志（不带附件）
     *
     * @return
     */
    @POST("rest/pshDiary/addPshDiary")
    Observable<ResponseBody> addPshDiary(
            @Query("json") String json);

    /**
     * 获取巡查日志列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @POST("rest/pshDiary/getPshDiary")
    Observable<Result3<List<PSHJournal>>> getDiayr(@Query("pageNo") int pageNo,
                                                   @Query("pageSize") int pageSize,
                                                   @Query("loginName") String loginName,
                                                   @Query("startTime") Long startTime,
                                                   @Query("endTime") Long endTime,
                                                   @Query("road") String road,
                                                   @Query("addr") String addr,
                                                   @Query("pshName") String layerName,
                                                   @Query("objectId") String markId,
                                                   @Query("id") String id);

    /**
     * 获取巡查日志列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @POST("rest/pshDiary/getPublicDiary")
    Observable<Result3<List<PSHJournal>>> getPublicDiary(@Query("pageNo") int pageNo,
                                                   @Query("pageSize") int pageSize,
                                                   @Query("startTime") Long startTime,
                                                   @Query("endTime") Long endTime,
                                                   @Query("kjArea") String parentOrgName);

    /**
     * 获取附件列表接口
     *
     * @param markId 部件标识id
     * @return
     */
    @POST("rest/pshDiary/getPshDiaryAttach")
    Observable<ServerAttachment> getDiayrAttach(@Query("markId") Long markId);


    /**
     * @param id
     * @param loginName
     * @param phoneBrand 手机型号
     * @return
     */
    @POST("rest/pshDiary/deletePshDiary")
    Observable<Result2<String>> deleteDiary(@Query("id") Long id,
                                            @Query("loginName") String loginName,
                                            @Query("phoneBrand") String phoneBrand);

    /**
     * 新增巡查日志（带附件）
     *
     * @return
     */
    @Multipart
    @POST("rest/pshDiary/editPshDiary")
    Observable<ResponseBody> toEditDiary(
            @Query("json") String json,
            @Query("attachment") String attachment,
            @PartMap() HashMap<String, RequestBody> requestBody);

    /**
     * 新增巡查日志（不带附件）
     *
     * @return
     */
    @POST("rest/pshDiary/editPshDiary")
    Observable<ResponseBody> toEditDiary(
            @Query("json") String json,
            @Query("attachment") String attachment);

    /**
     * 获取日常巡检详情
     *
     * @param id
     * @return
     */
    @POST("rest/pshDiary/toPshDiaryView")
    Observable<PSHJournalDetail> toDiaryView(@Query("id") Long id);

    /**
     * 获取排水户列表
     *
     * @param sGuid
     * @return
     */
    @POST("rest/pshDiary/getPshBySGuid")
    Observable<Result3<List<PSHHouse>>> getPshBySGuid(@Query("sGuid") String sGuid);

    @POST("rest/discharge/toCollectView/{id}")
    Observable<PSHAffairDetail> getPSHUnitDetail(@Path("id") Long affairId,@Query("loginName") String loginName);

    /**
     * 获取巡查日志列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @POST("rest/pshDiary/getPshDiaryLog")
    Observable<Result3<List<PSHJournal>>> getPshDiaryLog(@Query("pageNo") int pageNo,
                                                   @Query("pageSize") int pageSize,
                                                   @Query("loginName") String loginName,
                                                   @Query("startTime") Long startTime,
                                                   @Query("endTime") Long endTime,
                                                   @Query("pshId") int pshId);

    /**
     * 获取问题上报列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @POST("rest/pshDiary/getPshWtsbByPshId")
    Observable<Result2<List<PSHEventListItem>>> getPshWtsbByPshId(@Query("pageNo") int pageNo,
                                                                 @Query("pageSize") int pageSize,
                                                                 @Query("loginName") String loginName,
                                                                 @Query("startTime") Long startTime,
                                                                 @Query("endTime") Long endTime,
                                                                 @Query("pshId") int pshId);
}
