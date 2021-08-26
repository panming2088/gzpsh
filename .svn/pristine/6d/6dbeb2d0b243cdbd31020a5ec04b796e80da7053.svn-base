package com.augurit.agmobile.gzpssb.uploadevent.dao;

import com.augurit.agmobile.gzps.common.model.Result;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.publicaffair.model.EventAffair;
import com.augurit.agmobile.gzps.uploadevent.dao.DataResult;
import com.augurit.agmobile.gzps.uploadevent.dao.GetPersonByOrgApiData;
import com.augurit.agmobile.gzps.uploadevent.model.Assigneers;
import com.augurit.agmobile.gzps.uploadevent.model.EventDetail;
import com.augurit.agmobile.gzps.uploadevent.model.EventListItem;
import com.augurit.agmobile.gzps.uploadevent.model.EventProcess;
import com.augurit.agmobile.gzps.uploadevent.model.NextLinkOrg;
import com.augurit.agmobile.gzps.uploadevent.model.OrgItem;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventCount;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventDetail;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventProcess;
import com.augurit.agmobile.gzpssb.uploadevent.model.PshEventAffair;
import com.augurit.agmobile.gzpssb.uploadevent.model.Result4;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xcl on 2017/11/11.
 */

public interface PSHUploadEventApi {


    /**
     * 获取上报事件列表
     *
     * @param loginName 当前登陆名（必填）（市级人员可看全部，各区只能看所在区）
     * @param pageNo
     * @param pageSize
     * @param keyword   关键字（上报人、地址）
     * @param xzqh      行政区划（市级人员才有）
     * @param sslx      设施类型，传数据字典编码
     * @param wtlx      问题类型，传数据字典编码
     * @param state     事件状态：是否已办结（0查处理中，1查已办结，不传则查全部）
     * @param startTime 时间戳
     * @param endTime   时间戳
     * @return
     */
    @GET("rest/asiWorkflow/getProblemReport")
    Observable<Result2<EventAffair>> search(@Query("loginName") String loginName,
                                            @Query("pageNo") int pageNo,
                                            @Query("pageSize") int pageSize,
                                            @Query("keyWord") String keyword,
                                            @Query("xzqh") String xzqh,
                                            @Query("sslx") String sslx,
                                            @Query("wtlx") String wtlx,
                                            @Query("state") String state,
                                            @Query("startTime") String startTime,
                                            @Query("endTime") String endTime);

    @POST("rest/pshWtsb/getProblemReport")
    Observable<Result2<PshEventAffair>> getProblemReport(@Query("loginName") String loginName,
                                                         @Query("pageNo") int pageNo,
                                                         @Query("pageSize") int pageSize,
                                                         @Query("keyWord") String keyword,
                                                         @Query("xzqh") String xzqh,
                                                         @Query("sslx") String sslx,
                                                         @Query("wtlx") String wtlx,
                                                         @Query("state") String state,
                                                         @Query("psdyName") String psdyName,
                                                         @Query("startTime") String startTime,
                                                         @Query("endTime") String endTime);


    /**
     * 根据事件ID获取事件上报详情，参数sjid要经过加密处理，加密接口见GzpsService的AESEncoode
     */
    @GET("rest/asiWorkflow/getReportDetail")
    Observable<Result2<EventAffair.EventAffairBean>> getEventDetail(@Query("sjid") String sjid);

    /**
     * 事件上报
     *
     * @return
     */
    @Multipart
    @POST("rest/asiWorkflow/wfBusSave")
    Observable<Result<String>> uploadEvent(@Query("isSendMessage") String isSendMessage,
                                           @Query("loginName") String loginName,
                                           @Query("json") String params,
                                           @Query("type") String type,
                                           @Query("instance.assignee") String assignee,
                                           @Query("instance.assigneeName") String assigneeName,
                                           @PartMap() HashMap<String, RequestBody> requestBody);

    /**
     * 事件上报
     *
     * @return
     */
    @Multipart
    @POST("rest/pshWtsb/add")
    Observable<Result2<String>> uploadWtsb(@Query("json") String params,
                                           @PartMap() HashMap<String, RequestBody> requestBody);


    /**
     * 编辑事件问题
     *
     * @return
     */
    @Multipart
    @POST("rest/asiWorkflow/updateWtsb")
    Observable<Result<String>> editEvent(
            @Query("reportId") long reportId,
            @Query("deleteImgs") String deleteImgs,
            @Query("loginName") String loginName,
            @Query("json") String params,
            @Query("type") String type,
            @Query("instance.assignee") String assignee,
            @Query("instance.assigneeName") String assigneeName,
            @Query("isSendMessage") String isSendMessage,
            @PartMap() HashMap<String, RequestBody> requestBody);

    /**
     * 编辑事件问题
     *
     * @return
     */
    @POST("rest/asiWorkflow/updateWtsb")
    Observable<Result<String>> editEvent(
            @Query("reportId") long reportId,
            @Query("deleteImgs") String deleteImgs,
            @Query("loginName") String loginName,
            @Query("json") String params,
            @Query("type") String type,
            @Query("instance.assignee") String assignee,
            @Query("instance.assigneeName") String assigneeName,
            @Query("isSendMessage") String isSendMessage
    );


    /**
     * 问题上报时获取下一环节处理人
     *
     * @param loginName
     * @return
     */
    @GET("rest/asiWorkflow/getTaskUserByloginName")
    Observable<List<Assigneers>> getTaskUserByloginName(@Query("loginName") String loginName);

    /**
     * 任务派发环节退回后的问题上报、任务处置、任务复核、巡查员复核环节提交时获取下一环节处理人
     *
     * @param loginName
     * @return
     */
    @GET("rest/app/getNextActivityInfo")
    Observable<Result<List<Assigneers>>> getNextActivityInfo(@Query("loginName") String loginName,
                                                             @Query("taskInstDbid") String taskInstDbid);

    /**
     * 获取待领取列表
     *
     * @param loginName
     * @return
     */
    @GET("rest/asiWorkflow/getCanSummary")
    Observable<Result<List<EventListItem>>> getDlcEventList(@Query("loginName") String loginName,
                                                            @Query("groupBy") String groupBy,
                                                            @Query("groupDir") String groupDir,
                                                            @Query("json") String json,
                                                            @Query("page") String page,
                                                            @Query("viewId") int viewId,
                                                            @Query("type") String type);

    /**
     * 获取待在办列表
     *
     * @param loginName
     * @return
     */
    @GET("rest/asiWorkflow/getDZbSummary")
    Observable<Result<List<EventListItem>>> getDZbSummary(@Query("loginName") String loginName,
                                                          @Query("json") String json,
                                                          @Query("page") String page,
                                                          @Query("viewId") int viewId,
                                                          @Query("type") String type);

    /**
     * 获取处理中列表
     *
     * @param loginName
     * @return
     */
    @GET("rest/asiWorkflow/getClzSummary")
    Observable<Result<List<EventListItem>>> getClzEventList(@Query("loginName") String loginName,
                                                            @Query("json") String json,
                                                            @Query("page") String page,
                                                            @Query("viewId") int viewId,
                                                            @Query("type") String type);

    /**
     * 获取已办列表
     *
     * @param loginName
     * @return
     */
    @GET("rest/asiWorkflow/getYbSummary")
    Observable<Result<List<EventListItem>>> getYbSummary(@Query("loginName") String loginName,
                                                         @Query("json") String json,
                                                         @Query("page") String page,
                                                         @Query("viewId") int viewId,
                                                         @Query("type") String type);

    /**
     * 获取待办结列表
     *
     * @param loginName
     * @return
     */
    @GET("rest/asiWorkflow/getDbjSummary")
    Observable<Result<List<EventListItem>>> getDbjEventList(@Query("loginName") String loginName,
                                                            @Query("json") String json,
                                                            @Query("page") String page,
                                                            @Query("viewId") int viewId,
                                                            @Query("type") String type);

    /**
     * 获取已办结列表
     *
     * @param loginName
     * @return
     */
    @GET("rest/asiWorkflow/getBjSummary")
    Observable<Result<List<EventListItem>>> getYbjEventList(@Query("loginName") String loginName,
                                                            @Query("json") String json,
                                                            @Query("page") String page,
                                                            @Query("viewId") int viewId,
                                                            @Query("type") String type);

    /**
     * 获取问题上报列表（待受理、处理中、已办结、已上报）
     *
     * @param loginName
     * @return
     */
    @POST("rest/pshWtsb/getWtsbListByType")
    Observable<Result4<List<PSHEventListItem>>> getEventList(@Query("loginName") String loginName,
                                                             @Query("pageNo") int pageNo,
                                                             @Query("pageSize") int pageSize,
                                                             @Query("isMobile") String isMobile,
//                                                            @Query("groupBy") String groupBy,
//                                                            @Query("groupDir") String groupDir,
                                                             @Query("type") String type,
                                                             @Query("startTime") Long startTime,
                                                             @Query("endTime") Long endTime,
                                                             @Query("sbr") String sbr,
                                                             @Query("szwz") String szwz,
                                                             @Query("pshmc") String pshName);

    /**
     * 获取事件详情
     *
     * @param loginName
     * @return
     */
    @POST("rest/asiWorkflow/getDetail")
    Observable<Result2<EventDetail>> getEventDetails(@Query("loginName") String loginName,
                                                     @Query("taskInstDbid") String TaskInstDbid,
                                                     @Query("procInstDbId") String procInstDbId,
                                                     @Query("masterEntityKey") String masterEntityKey);

    /**
     * 获取事件详情
     *
     * @param wtsbId
     * @param type
     * @return wtsbId=8&type=dcl
     */
    @POST("rest/pshWtsb/getDetail")
    Observable<ResponseBody> getDetail(@Query("wtsbId") int wtsbId,
                                         @Query("type") String type);

    /**
     * 领取事件
     *
     * @return
     */
    @GET("rest/asiWorkflow/takeTasks")
    Observable<Result<String>> signEvent(@Query("loginUserName") String loginName,
                                         @Query("userName") String userName,
                                         @Query("taskInstDbids") String taskInstId);

    /**
     * 获取事件处理情况及施工日志，参数sjid要经过加密处理，加密接口见GzpsService的AESEncoode
     *
     * @return
     */
    @GET("rest/asiWorkflow/getTraceRecordAndSggcLogList")
    Observable<Result2<List<EventProcess>>> getEventHandlesAndJournals(@Query("sjid") String sjid,
                                                                       @Query("pageNo") int pageNo,
                                                                       @Query("pageSize") int pageSize);

    /**
     * 获取事件处理情况及施工日志，参数sjid要经过加密处理，加密接口见GzpsService的AESEncoode
     *
     * @return
     */
    @POST("rest/pshWtsb/getSggcLogList")
    Observable<Result2<List<PSHEventProcess>>> getSggcLogList(@Query("wtsbId") String wtsbId);

    /**
     * 任务分发环节获取当前用户所在的机构下的养护班组
     *
     * @param loginName
     * @return
     */
    @GET("rest/app/getbzOrg")
    Observable<List<NextLinkOrg>> getEventOrg(@Query("loginName") String loginName);

    /**
     * 推送到一下环节
     *
     * @return
     */
    @Multipart
    @POST("rest/pshWtsb/wfSend")
    Observable<Result2<String>> wfSend(@Query("loginName") String loginName,
                                       @Query("wtsbId") int wtsbId,
                                       @Query("nextState") int nextState,
                                       @Query("state") int state,
                                       @Query("userName") String userName,
                                       @Query("sfzf") Long sfzf,
                                       @Query("content") String content,
                                       @PartMap() HashMap<String, RequestBody> requestBody);

    @POST("rest/pshWtsb/wfSend")
    Observable<Result2<String>> wfSend(@Query("loginName") String loginName,
                                       @Query("wtsbId") int wtsbId,
                                       @Query("nextState") int nextState,
                                       @Query("state") int state,
                                       @Query("userName") String userName,
                                       @Query("sfzf") Long sfzf,
                                       @Query("content") String content);


    /**
     * 保存节点附件，推送到下一节点时用
     *
     * @param taskInstDbid
     * @param requestBody
     * @return
     */
    @Multipart
    @POST("rest/asiWorkflow/saveJdFile")
    Observable<ResponseBody> saveJdFile(@Query("taskInstDbid") String taskInstDbid,
                                        @PartMap() HashMap<String, RequestBody> requestBody);

    /**
     * 回退到上一环节
     *
     * @return
     */
    @GET("rest/asiWorkflow/returnPrevTask")
    Observable<ResponseBody> wfReturn(@Query("masterEntityKey") String masterEntityKey,
                                      @Query("isSendMessage") String isSendMessage,
                                      @Query("loginName") String loginName,
                                      @Query("taskInstDbid") String taskInstId,
                                      @Query("backComments") String content);


    /**
     * 撤回下一环节
     *
     * @param procInstId
     * @param loginName
     * @return
     */
    @GET("rest/asiWorkflow/retrieveTask")
    Observable<Result<String>> wfRetrive(
            @Query("instance.procInstId") String procInstId,
            @Query("loginName") String loginName,
            @Query("retrieveComments") String retrieveComments);


    /**
     * 删掉当前
     *
     * @param sjid
     * @param procInstId
     * @param loginName
     * @param taskInstDbid
     * @return
     */
    @GET("rest/asiWorkflow/deleteProcessInstance")
    Observable<Result<String>> wfDelete(@Query("sjid") String sjid,
                                        @Query("instance.procInstId") String procInstId,
                                        @Query("loginName") String loginName,
                                        @Query("instance.taskInstDbid") String taskInstDbid);


    /**
     * 转派
     *
     * @param loginName
     * @param taskInstId
     * @param assignee            接收者CODE
     * @param assigneeName        接收者姓名
     * @param activityChineseName 当前环节中文名
     * @param reassignComments    转派意见
     * @return
     */
    @GET("rest/asiWorkflow/wfReassign")
    Observable<ResponseBody> wfReassign(@Query("loginName") String loginName,
                                        @Query("instance.taskInstDbid") String taskInstId,
                                        @Query("instance.assignee") String assignee,
                                        @Query("instance.assigneeName") String assigneeName,
                                        @Query("instance.activityChineseName") String activityChineseName,
                                        @Query("instance.reassignComments") String reassignComments,

                                        @Query("instance.activityName") String activityName,
                                        @Query("instance.procInstId") String procInstId,
                                        @Query("instance.masterEntityKey") String sjid,
                                        @Query("isSendMessage") String isSendMessage
    );

    /**
     * 上传施工日志
     *
     * @return
     */
    @Multipart
    @POST("rest/asiWorkflow/sggcLogSave")
    Observable<Result2<String>> uploadJournal(@Query("loginName") String loginName,
                                              @Query("userName") String userName,
                                              @Query("content") String content,
                                              @Query("sjid") String sjid,
                                              @PartMap() HashMap<String, RequestBody> requestBody);

    /**
     * 上传督办意见
     *
     * @return
     */
    @POST("rest/asiWorkflow/sggcContentSave")
    Observable<Result2<String>> uploadAdvice(@Query("loginName") String loginName,
                                             @Query("userName") String userName,
                                             @Query("content") String content,
                                             @Query("sjid") String sjid);


    /**
     * 获取当前待办和已办的数量
     */
    @POST("rest/pshWtsb/getWtsbAllTypeNums")
    Observable<Result2<PSHEventCount>> getEventCount(@Query("loginName") String loginName);

    /**
     * 获取组织机构列表
     * 问题上报如果是Rg或者Rm 需要此操作
     *
     * @return
     */
    @GET("rest/asiWorkflow/getAllOrg")
    Observable<List<OrgItem>> getOrgListForUploadEvent();

    /**
     * 通过组织机构名称和组织名称获取该机构下的人名列表,以供选择
     * <p>
     * 问题上报如果是Rg或者Rm 需要此操作
     *
     * @param code
     * @param name
     * @return
     */
    @GET("rest/asiWorkflow/getUsersByorgCode")
    Observable<GetPersonByOrgApiData> getPersonByOrgCodeAndName(@Query("loginName") String loginName,
                                                                @Query("orgCode") String code,
                                                                @Query("orgName") String name
    );

    /**
     * 通过组织机构名称和组织名称获取该机构下的人名列表,以供选择
     * <p>
     * 问题上报如果是Rg或者Rm 需要此操作
     *
     * @param loginName
     * @return
     */
    @POST("rest/pshWtsb/sfPsgs")
    Observable<DataResult> getJurisdictionByLoginName(@Query("loginName") String loginName);

    /**
     * 删除问题上报
     *
     * @param id
     * @return
     */
    @POST("rest/pshWtsb/delete")
    Observable<Result2<String>> deleteEvent(
            @Query("id") int id,
            @Query("loginName") String loginName,
            @Query("delReason") String delReson
    );

}
