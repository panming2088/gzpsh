package com.augurit.agmobile.gzpssb.secondpsh.service;

import android.content.Context;
import android.graphics.Bitmap;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzps.common.widget.FileBean;
import com.augurit.agmobile.gzps.uploadevent.util.PhotoUploadType;
import com.augurit.agmobile.gzpssb.bean.SewerageInfoBean;
import com.augurit.agmobile.gzpssb.common.PortSelectUtil;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfo;
import com.augurit.agmobile.gzpssb.pshstatistics.dao.PSHSignStatisticApi;
import com.augurit.agmobile.gzpssb.secondpsh.dao.SecondLevelPshApi;
import com.augurit.agmobile.mapengine.common.utils.FilePathUtil;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.AMNetwork;
import com.augurit.am.fw.utils.AMFileUtil;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xcl on 2017/11/17.
 */

public class SecondLevelPshService {


    public static final int LOAD_ITEM_PER_PAGE = 15;

    private AMNetwork amNetwork;
    private SecondLevelPshApi uploadStatistic;
    private Context mContext;

    public SecondLevelPshService(Context mContext) {
        this.mContext = mContext;
    }

    private void initAMNetwork(String serverUrl) {
        if (amNetwork == null) {
            this.amNetwork = new AMNetwork(serverUrl);
            this.amNetwork.addLogPrint();
            this.amNetwork.addRxjavaConverterFactory();
            this.amNetwork.build();
            this.amNetwork.registerApi(PSHSignStatisticApi.class);
            this.uploadStatistic = (SecondLevelPshApi) this.amNetwork.getServiceApi(SecondLevelPshApi.class);
        }
    }



    /**
     * 获取二级排水户列表
     *
     * @return
     */
    public Observable<SecondLevelPshInfo> getSecondLevelPshList(String unitId, int page, int pageSize, String bigType, String smallType, Long startDate, Long endDate,Long uploadid ,String address, String orgname ) {
        String url = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(url);
        return uploadStatistic.getSecondLevelPshList(unitId,page,pageSize,null,null,bigType,startDate,endDate,uploadid,address,orgname)
                .subscribeOn(Schedulers.io());
    }

    /**
     *
     * @param page
     * @return
     */
    public Observable<SecondLevelPshInfo> getCollectList(int page, String checkState,
                                                                      String parentOrgName,
                                                                      String bigType,
                                                                      String smallType,
                                                                      Long startTime,
                                                                      Long endTime, Long uploadid,
                                                                      String address,
                                                                      String orgname) {

//        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        String url = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(url);

        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();


        return uploadStatistic.getCollectList(page,
                LOAD_ITEM_PER_PAGE,
                checkState,
                parentOrgName,
                bigType,
                smallType,
                startTime,
                endTime,
                uploadid,
                address,
                orgname,
                loginName);
//                    checkState,
//                    uploadedFacilityFilterCondition.startTime,
//                    uploadedFacilityFilterCondition.endTime,
//                    uploadedFacilityFilterCondition.road,
//                    uploadedFacilityFilterCondition.address,
//                    uploadedFacilityFilterCondition.facilityType,
//                    uploadedFacilityFilterCondition.markId,
//                    uploadedFacilityFilterCondition.attrFive);

    }
    /**
     * 删除二级排水户信息
     * @param id
     * @return
     */
    public Observable<ResponseBody> toDeleteCollect(String id) {
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        return uploadStatistic.toDelete(
                id, loginName);
    }
    /**
     * 新增/编辑二级排水户信息
     * @return
     */
    public Observable<ResponseBody> addSecondPsh(SecondLevelPshInfo.SecondPshInfo data1, SecondLevelPshInfo.SecondPshInfo info) {
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        data1.setLoginName(loginName);
        String json = null;
            json = JsonUtil.getJson(data1);
        return uploadStatistic.upDateSecondPsh(json);
    }

    public Observable<ResponseBody> addSecondPsh(SewerageInfoBean bean) {

//        String port = LoginConstant.pshUploadPort;//默认的端口
//        TableDBService dbService = new TableDBService(mContext);
//        List<DictionaryItem> dictionaryItems = dbService.getDictionaryByTypecodeInDB("A181");//数据字典端口
//        if (!ListUtil.isEmpty(dictionaryItems)) {
//            List<String> ports = new ArrayList<>();
//            for (int i = 0; i < dictionaryItems.size(); i++) {
//                DictionaryItem dictionaryItem = dictionaryItems.get(i);
//                if (!StringUtil.isEmpty(dictionaryItem.getName())
//                        && ValidateUtils.isInteger(dictionaryItem.getName())) {
//                    ports.add(":" + dictionaryItem.getName());
//                }
//            }
//            if (!ListUtil.isEmpty(ports)) {
//                port = ports.get(new Random().nextInt(ports.size()));
//            }
//        }
//        String supportUrl = LoginConstant.UPLOAD_AGSUPPORT + port + LoginConstant.UPLOAD_POSTFIX + "/";//TODO

        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        List<SewerageInfoBean.WellBeen> wellBeen = bean.getWellBeen();
        if(!ListUtil.isEmpty(wellBeen)){
            for(SewerageInfoBean.WellBeen wellBeen1:wellBeen){
                if(wellBeen1.getId() !=null && wellBeen1.getId()<1){
                    wellBeen1.setId(null);
                }
            }
            bean.setWellBeen(wellBeen);
        }
        return upload(bean);
    }


    /**
     * 提交排水户信息
     * @param bean
     * @return
     */
    public Observable<ResponseBody> upload(SewerageInfoBean bean) {
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        bean.setLoginName(loginName);
//        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
//        initAMNetwork(supportUrl);
        String str =  JsonUtil.getJson(bean);

        HashMap<String, RequestBody> requestMap = new HashMap<>();
        if (ListUtil.isEmpty(bean.getPhotos0()) && ListUtil.isEmpty(bean.getPhotos1()) && ListUtil.isEmpty(bean.getPhotos2())
                && ListUtil.isEmpty(bean.getPhotos3()) && ListUtil.isEmpty(bean.getPhotos4())&& ListUtil.isEmpty(bean.getPhotos5()) && ListUtil.isEmpty(bean.getThumbnailPhotos0())
                && ListUtil.isEmpty(bean.getThumbnailPhotos1()) && ListUtil.isEmpty(bean.getThumbnailPhotos2()) && ListUtil.isEmpty(bean.getThumbnailPhotos3())
                && ListUtil.isEmpty(bean.getThumbnailPhotos4())&& ListUtil.isEmpty(bean.getThumbnailPhotos5())&& ListUtil.isEmpty(bean.getFiles())
                && ListUtil.isEmpty(bean.getPhotos7()) && ListUtil.isEmpty(bean.getThumbnailPhotos7()) ) {
            return uploadStatistic.addSecondPsh(
                    str,
                    requestMap);
        }
        HashMap<String, RequestBody> requestMap1 = getParams(bean.getPhotos0(),"_sewerageUser_");
        HashMap<String, RequestBody> requestMap2 = getParams(bean.getPhotos1(),"_hasCert1_");
        HashMap<String, RequestBody> requestMap3 = getParams(bean.getPhotos2(),"_hasCert2_");
        HashMap<String, RequestBody> requestMap4 = getParams(bean.getPhotos3(),"_hasCert3_");
        HashMap<String, RequestBody> requestMap5 = getParams(bean.getPhotos4(),"_hasCert4_");
        HashMap<String, RequestBody> requestMap11 = getParams(bean.getPhotos5(),"_hasCert5_");
        HashMap<String, RequestBody> requestMap13 = getFileParams(bean,"_hasCert6_");
        HashMap<String, RequestBody> requestMap14 = getParams(bean.getPhotos7(),"_hasCert7_");

        HashMap<String, RequestBody> requestMap6 = getParams(bean.getThumbnailPhotos0(),"_sewerageUserthumbnail_");
        HashMap<String, RequestBody> requestMap7 = getParams(bean.getThumbnailPhotos1(),"_hasCert1thumbnail_");
        HashMap<String, RequestBody> requestMap8 = getParams(bean.getThumbnailPhotos2(),"_hasCert2thumbnail_");
        HashMap<String, RequestBody> requestMap9 = getParams(bean.getThumbnailPhotos3(),"_hasCert3thumbnail_");
        HashMap<String, RequestBody> requestMap10 = getParams(bean.getThumbnailPhotos4(),"_hasCert4thumbnail_");
        HashMap<String, RequestBody> requestMap12 = getParams(bean.getThumbnailPhotos5(),"_hasCert5thumbnail_");
        HashMap<String, RequestBody> requestMap15 = getParams(bean.getThumbnailPhotos7(),"_hasCert7thumbnail_");


        requestMap.putAll(requestMap1);
        requestMap.putAll(requestMap2);
        requestMap.putAll(requestMap3);
        requestMap.putAll(requestMap4);
        requestMap.putAll(requestMap5);
        requestMap.putAll(requestMap6);
        requestMap.putAll(requestMap7);
        requestMap.putAll(requestMap8);
        requestMap.putAll(requestMap9);
        requestMap.putAll(requestMap10);
        requestMap.putAll(requestMap11);
        requestMap.putAll(requestMap12);
        requestMap.putAll(requestMap13);
        requestMap.putAll(requestMap14);
        requestMap.putAll(requestMap15);

        return uploadStatistic.addSecondPsh(
                str,
                requestMap);

    }

    private HashMap<String, RequestBody> getParams(List<Photo> photos, String tag) {
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        int i = 0;
        if (!ListUtil.isEmpty(photos)) {
            for (Photo photo : photos) {
                if (photo.getLocalPath() != null) {
                    File file = new File(photo.getLocalPath());
                    String name = photo.getPhotoName();
//                    if(name.indexOf("_xc_")!=-1){
//                        name = name.replace("_xc_","");
//                    }
                    requestMap.put("file" + i + "\"; filename=\"" + name.replaceFirst("_", tag), RequestBody.create(MediaType.parse("image/*"), file));
                    i++;
                } else {
                    File file = getFile(photo.getPhotoPath());
                    String name = photo.getPhotoName();
                    if (file != null) {
                        requestMap.put("file" + i + "\"; filename=\"" + name.replaceFirst("_", tag), RequestBody.create(MediaType.parse("image/*"), file));
                        i++;
                    }
                }
            }
        }
        return requestMap;
    }

    /**
     * 编辑排水户信息
     * @param bean
     * @return
     */
    public Observable<ResponseBody> toUpdateCollect(SewerageInfoBean bean) {
        String loginName = new LoginRouter(mContext, AMDatabase.getInstance()).getUser().getLoginName();
        bean.setLoginName(loginName);
//        String supportUrl = BaseInfoManager.getSupportUrl(mContext);
        String supportUrl = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(supportUrl);
        String str = JsonUtil.getJson(bean);
        if (ListUtil.isEmpty(bean.getPhotos0()) && ListUtil.isEmpty(bean.getPhotos1()) && ListUtil.isEmpty(bean.getPhotos2())
                && ListUtil.isEmpty(bean.getPhotos3()) && ListUtil.isEmpty(bean.getPhotos4())&& ListUtil.isEmpty(bean.getPhotos5()) && ListUtil.isEmpty(bean.getThumbnailPhotos0())
                && ListUtil.isEmpty(bean.getThumbnailPhotos1()) && ListUtil.isEmpty(bean.getThumbnailPhotos2()) && ListUtil.isEmpty(bean.getThumbnailPhotos3())
                && ListUtil.isEmpty(bean.getThumbnailPhotos4())&& ListUtil.isEmpty(bean.getThumbnailPhotos5())&& ListUtil.isEmpty(bean.getFiles())
                && ListUtil.isEmpty(bean.getPhotos7()) && ListUtil.isEmpty(bean.getThumbnailPhotos7()) ) {
            if(bean.isIndustry()){
                return  uploadStatistic.upDateSecondPsh(str);
            }else{
                return  uploadStatistic.upDateSecondPsh(str);
            }
        }else{

            HashMap<String, RequestBody> requestMap = new HashMap<>();

            HashMap<String, RequestBody> requestMap1 = getParams(bean.getPhotos0(), "_sewerageUser_");
            HashMap<String, RequestBody> requestMap2 = getParams(bean.getPhotos1(), "_hasCert1_");
            HashMap<String, RequestBody> requestMap3 = getParams(bean.getPhotos2(), "_hasCert2_");
            HashMap<String, RequestBody> requestMap4 = getParams(bean.getPhotos3(), "_hasCert3_");
            HashMap<String, RequestBody> requestMap5 = getParams(bean.getPhotos4(), "_hasCert4_");
            HashMap<String, RequestBody> requestMap11 = getParams(bean.getPhotos5(), "_hasCert5_");
            HashMap<String, RequestBody> requestMap14 = getParams(bean.getPhotos7(), "_hasCert7_");
            HashMap<String, RequestBody> requestMap13 = getFileParams(bean, "_hasCert6_");
            HashMap<String, RequestBody> requestMap6 = getParams(bean.getThumbnailPhotos0(), "_sewerageUserthumbnail_");
            HashMap<String, RequestBody> requestMap7 = getParams(bean.getThumbnailPhotos1(), "_hasCert1thumbnail_");
            HashMap<String, RequestBody> requestMap8 = getParams(bean.getThumbnailPhotos2(), "_hasCert2thumbnail_");
            HashMap<String, RequestBody> requestMap9 = getParams(bean.getThumbnailPhotos3(), "_hasCert3thumbnail_");
            HashMap<String, RequestBody> requestMap10 = getParams(bean.getThumbnailPhotos4(), "_hasCert4thumbnail_");
            HashMap<String, RequestBody> requestMap12 = getParams(bean.getThumbnailPhotos5(), "_hasCert5thumbnail_");
            HashMap<String, RequestBody> requestMap15 = getParams(bean.getThumbnailPhotos7(), "_hasCert7thumbnail_");

            requestMap.putAll(requestMap1);
            requestMap.putAll(requestMap2);
            requestMap.putAll(requestMap3);
            requestMap.putAll(requestMap4);
            requestMap.putAll(requestMap5);
            requestMap.putAll(requestMap6);
            requestMap.putAll(requestMap7);
            requestMap.putAll(requestMap8);
            requestMap.putAll(requestMap9);
            requestMap.putAll(requestMap10);
            requestMap.putAll(requestMap11);
            requestMap.putAll(requestMap12);
            requestMap.putAll(requestMap13);
            requestMap.putAll(requestMap14);
            requestMap.putAll(requestMap15);

            if(bean.isIndustry()){
                return  uploadStatistic.upDateSecondPsh(str,requestMap);
            }else{
                return uploadStatistic.upDateSecondPsh(str,requestMap);
            }
        }
    }

    //pdf文件的上传
    private HashMap<String, RequestBody> getFileParams(SewerageInfoBean bean, String tag) {
        List<FileBean> fileBeans = bean.getFiles();
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        int i = 0;
        if (!ListUtil.isEmpty(fileBeans)) {
            for (FileBean fileBean : fileBeans) {
                if(fileBean.getId()>0 && !bean.isDraft()){
                    continue;
                }
                if (fileBean.getPath() != null) {
                    File file = new File(fileBean.getPath());
                    String name = fileBean.getName();
                    if(name.indexOf(PhotoUploadType.UPLOAD_FOR_FILES)!=-1){
                        name =  name.replace(PhotoUploadType.UPLOAD_FOR_FILES,"");
                    }
                    requestMap.put("file" + i + "\"; filename=\"" + name.replaceFirst("_", tag), RequestBody.create(MediaType.parse("pdf/*"), file));
                    i++;
                }else{
                    File file = getPdfFile(fileBean.getPath());
                    String name = fileBean.getName();
                    if (file != null) {
                        requestMap.put("file" + i + "\"; filename=\"" + name.replaceFirst("_", tag), RequestBody.create(MediaType.parse("pdf/*"), file));
                        i++;
                    }
                }
            }
        }
        return requestMap;
    }

    public File getFile(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        String tempFile = new FilePathUtil(mContext).getSavePath() + "/images/" + System.currentTimeMillis() + ".jpg";
        File targetFile = new File(tempFile);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            AMFileUtil.saveInputStreamToFile(is, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return targetFile;
    }

    public File getPdfFile(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        String tempFile = new FilePathUtil(mContext).getSavePath() + "/images/" + System.currentTimeMillis() + ".pdf";
        File targetFile = new File(tempFile);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            AMFileUtil.saveInputStreamToFile(is, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return targetFile;
    }

    public Observable<String> getPshList(String yjPshId, int pageNo, int pageSize){
        String url = PortSelectUtil.getAgSupportPortBaseURL(mContext);
        initAMNetwork(url);
        return uploadStatistic.getPshList(yjPshId, pageNo, pageSize)
                .map(new Func1<okhttp3.ResponseBody, String>() {
                    @Override
                    public String call(okhttp3.ResponseBody responseBody) {
                        try {
                            return responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return "";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
