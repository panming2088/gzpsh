package com.augurit.agmobile.gzpssb.fire.service;

import android.content.Context;
import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.setting.view.myupload.Result3;
import com.augurit.agmobile.gzpssb.common.PortSelectUtil;
import com.augurit.agmobile.gzpssb.fire.dao.PSHUploadFireApi;
import com.augurit.agmobile.gzpssb.fire.model.GroundfireBean;
import com.augurit.agmobile.gzpssb.seweragewell.model.WellPhoto;
import com.augurit.agmobile.gzpssb.uploadfacility.dao.PSHUploadFacilityApi;
import com.augurit.agmobile.patrolcore.common.table.dao.TableDataManager;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.AMNetwork;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * 设施新增Service
 * Created by xcl on 2017/11/14.
 */

public class PSHUploadFireService {

    public static final int LOAD_ITEM_PER_PAGE = 15;

    public static final int TIMEOUT = 60;  //网络超时时间（秒）
    private AMNetwork amNetwork;
    private PSHUploadFireApi pshUploadFireApi;
    private Context mContext;
    private TableDataManager tableDataManager;
    public PSHUploadFireService(Context mContext) {
        this.mContext = mContext;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.setReadTime(TIMEOUT * 1000);
            this.amNetwork.setWriteTime(TIMEOUT * 1000);
            this.amNetwork.setConnectTime(TIMEOUT * 1000);
            this.amNetwork.build();
            this.amNetwork.registerApi(PSHUploadFireApi.class);
            this.pshUploadFireApi = (PSHUploadFireApi) this.amNetwork.getServiceApi(PSHUploadFireApi.class);
        }
    }
    private void initAMNetworkWithRandomURL(String serverUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS).
                readTimeout(TIMEOUT, TimeUnit.SECONDS).
                writeTimeout(TIMEOUT, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                //使用自定义的mGsonConverterFactory
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(serverUrl)
                .build();
        pshUploadFireApi = retrofit.create(PSHUploadFireApi.class);
    }
    private HashMap<String, RequestBody> getParams(List<Photo> photos, String tag) {
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        int i = 0;
        if (!ListUtil.isEmpty(photos)) {
            for (Photo photo : photos) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    String name = photo.getPhotoName();
                    requestMap.put("file" + i + "\"; filename=\"" + name.replaceFirst("_", tag), RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                }
            }
        }
        return requestMap;
    }
    public Observable<ResponseBody> uploadGroundFire(GroundfireBean bean) {
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetworkWithRandomURL(supportUrl);
        return uploadFire(bean);
    }

    /**
     * 提交排水户信息
     */
    public Observable<ResponseBody> uploadFire(GroundfireBean bean) {
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        bean.setLoginName(loginName);
        bean.setMarkPerson(loginName);
        String str =  JsonUtil.getJson(bean);
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        HashMap<String, RequestBody> requestMap1 = getParams(bean.getPhotos8(),"_hasCert8_");
        HashMap<String, RequestBody> requestMap2 = getParams(bean.getThumbnailPhotos8(),"_hasCert8thumbnail_");
        requestMap.putAll(requestMap1);
        requestMap.putAll(requestMap2);
        return pshUploadFireApi.uploadFire(
                str,
                requestMap);

    }

    /**
     * 编辑排水户信息
     * @param bean
     * @return
     */
    public Observable<ResponseBody> toUpdateFire(GroundfireBean bean) {
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        bean.setLoginName(loginName);
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        String str = JsonUtil.getJson(bean);
        if (ListUtil.isEmpty(bean.getPhotos8()) && ListUtil.isEmpty(bean.getThumbnailPhotos8())) {
                return  pshUploadFireApi.toUpdateFire(str);

        }else{
            HashMap<String, RequestBody> requestMap = new HashMap<>();
            HashMap<String, RequestBody> requestMap1 = getParams(bean.getPhotos8(), "_hasCert8_");
            HashMap<String, RequestBody> requestMap2 = getParams(bean.getThumbnailPhotos8(), "_hasCert8thumbnail_");
            requestMap.putAll(requestMap1);
            requestMap.putAll(requestMap2);
                return  pshUploadFireApi.toUpdateFire(str,requestMap);
            }
    }

    /**
     * 删除排水户信息
     * @param id
     * @return
     */
    public Observable<ResponseBody> toDeleteFire(String objectId) {
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
//        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        return pshUploadFireApi.toDeleteFire(
                objectId, loginName);
    }

    /**
     * 获取我的上报列表
     *
     * @return
     */
    public Observable<Result3<List<GroundfireBean>>> getFireList(int page, String area,
     String sswh,String sfls,String szwz, String addr,String name,  Long startTime,Long endTime,Long uploadid ) {
        String url = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        initAMNetwork(url);
        return pshUploadFireApi.getFireList(page,
                10,
                area,
                sswh,
                sfls,
                szwz,
                addr,
                name,
                loginName,
                startTime,
                endTime,
              uploadid);

    }

    /**
     * 根据objectid获取地上式消防栓图片
     */
    public Observable<WellPhoto> getFirePhotos(String objectid) {
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        return pshUploadFireApi.getFirePhotos(objectid);
    }
}
