package com.augurit.agmobile.gzpssb.uploadfacility.dao;

import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzps.publicaffair.model.UploadFacilityDetail;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 设施新增API
 * <p>
 * Created by xcl on 2017/11/15.
 */

public interface PSHUploadFacilityApi {

    /**
     * 获取部件缺失列表
     *
     * @param pageNo
     * @param pageSize
     * @param userName
     * @return
     */
    @POST("rest/PshParts/getPartsNew")
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
    @POST("rest/PshParts/getPartsNewAttach")
    Observable<ServerAttachment> getPartsNewAttach(@Query("markId") long markId);

    /**
     * 获取上报详情
     * @param id
     * @param loginName
     * @return
     */
    @POST("rest/PshParts/toView?type=lack")
    Observable<UploadFacilityDetail> getUploadDetail(@Query("id")long id, @Query("loginName") String loginName);

}
