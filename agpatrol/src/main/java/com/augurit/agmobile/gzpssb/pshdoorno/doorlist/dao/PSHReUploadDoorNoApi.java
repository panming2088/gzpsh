package com.augurit.agmobile.gzpssb.pshdoorno.doorlist.dao;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.mode.UploadDoorNoDetailBean;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 设施新增API
 * <p>
 * Created by xcl on 2017/11/15.
 */

public interface PSHReUploadDoorNoApi {

    /**
     *获取我的门牌上报列表
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
    Observable<ResponseBody> getUploadDoorNoList(@Query("pageNo") int pageNo,
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


}
