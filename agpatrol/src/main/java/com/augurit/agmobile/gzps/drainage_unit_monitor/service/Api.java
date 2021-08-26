package com.augurit.agmobile.gzps.drainage_unit_monitor.service;

import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.model.Result3;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.Data;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.JBJ;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyJg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyJgjl;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyWtjc;
import com.augurit.agmobile.gzpssb.jbjpsdy.model.PsdyJbj;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventListItem;
import com.augurit.agmobile.gzpssb.uploadevent.model.Result4;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {
    /**
     * 查询排水单元监管信息
     * @return
     */
    @POST("rest/pshJg/getPsdyJg")
    Observable<Result3<PsdyJg, PsdyJgjl>> getPsdyJg(@Query("psdyId") Long psdyId);

    /**
     *  获取资料检查详情
     * @return
     */
    @POST("rest/pshJg/getPshZljc")
    Observable<Result2<Data>> getPshZljc(@Query("id") Long id);
    /**
     * 查询接驳井列表
     * @return
     */
    @POST("rest/pshJg/getPsdyJgJbjList")
    Observable<Result2<List<JBJ>>> getPsdyJgJbjList(@Query("psdyId") Long psdyId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("keyNode") String keyNode);

    /**
     * 排水单元-资料检查表
     * @return
     */
    @POST("rest/pshJg/addPsdyZljc")
    Observable<Result2> addPsdyZljc(@Query("data") String data);

    /**
     * 新增排水单元监管
     * @return
     */
    @POST("rest/pshJg/addPsdyJg")
    Observable<Result2> addPsdyJg(@Query("data") String data);

    /**
     * 获取问题上报列表（待受理、处理中、已办结、已上报）
     *
     * @param loginName
     * @return
     */
    @POST("rest/pshJg/getLgjcListByType")
    Observable<Result4<List<PSHEventListItem>>> getEventList(@Query("loginName") String loginName,
                                                             @Query("pageNo") int pageNo,
                                                             @Query("pageSize") int pageSize,
                                                             @Query("isMobile") String isMobile,
//                                                            @Query("groupBy") String groupBy,
//                                                            @Query("groupDir") String groupDir,
                                                             @Query("jgType") String jgType,
                                                             @Query("type") String type,
                                                             @Query("startTime") Long startTime,
                                                             @Query("endTime") Long endTime,
                                                             @Query("sbr") String sbr,
                                                             @Query("szwz") String szwz,
                                                             @Query("pshmc") String pshName,
                                                             @Query("psdyId") String psdyId,
                                                             @Query("gjz") String gjz);

    /**
     *查询排水单元连线
     * @return
     */
    @POST("rest/psdyJbjRest/getPsdyJbj")
    Observable<Result2<List<PsdyJbj>>> queryPsdyJbj(@Query("usid")String usid, @Query("jbjObjectId")String jbjObjectId, @Query("psdyObjectId")String psdyObjectId);

    /**
     *查询有无该排水单元记录
     * @return
     */
    @POST("rest/pshJg/getPsdyWtjc")
    Observable<Result2<PsdyWtjc>> getPsdyWtjc(@Query("psdyId")String psdyId, @Query("jclx")String jclx);

    /**
     *新增问题检查记录
     * @return
     */
    @POST("rest/pshJg/addPsdyWtjc")
    Observable<Result2> addPsdyWtjc(@Query("data")String data);
    /**
     *排水单元编辑
     * @return
     */
    @POST("rest/pshJg/updatePsdySde")
    Observable<Result2> updatePsdySde(@Query("psdyId")String psdyId,    // 单元ID
                                      @Query("sfywfl")String sfywfl,    // 是否雨污分流
                                      @Query("sfwcdb")String sfwcdb,    // 是否完成达标创建
                                      @Query("wcdbsj")String wcdbsj,    // 完成达标时间
                                      @Query("qsr")String qsr,          // 权属人
                                      @Query("qsr_lxfs")String qsr_lxfs,// 权属人联系方式
                                      @Query("whr")String whr,          // 维护人
                                      @Query("whr_lxfs")String whr_lxfs,// 维护人联系方式
                                      @Query("glr")String glr,          // 管理人
                                      @Query("glr_lxfs")String glr_lxfs,// 管理人联系方式
                                      @Query("jgr")String jgr,          // 监管人
                                      @Query("jgr_lxfs")String jgr_lxfs // 监管人
    );
}
