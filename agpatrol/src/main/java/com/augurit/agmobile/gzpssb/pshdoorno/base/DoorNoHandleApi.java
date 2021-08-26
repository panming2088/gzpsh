package com.augurit.agmobile.gzpssb.pshdoorno.base;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
//import com.augurit.agmobile.gzpssb.bean.AddressBean;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.DoorNoRespone;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface DoorNoHandleApi {

    /**
     * 新增门牌号码/编辑后提交接口
     * 提交接口字段Gsid有值
     * @param json
     * @return
     */
    @POST("rest/pshSbssInfRest/sbssMp")
    Observable<DoorNoRespone>  addNewDoorNo(@Query("json") String json);

    /**
     * 获取地址、门牌信息
     */

//    @POST("rest/pshSbssInfRest/getMpDczt")
//    Observable<AddressBean>  queryAdressInfo(@Query("id") String id ,@Query("add_type") String add_type);

}
