package com.augurit.agmobile.patrolcore.search.view;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.text.TextUtils;

import com.augurit.agmobile.mapengine.layermanage.view.ILayerPresenter;
import com.augurit.agmobile.patrolcore.common.table.dao.TableDataManager;
import com.augurit.agmobile.patrolcore.common.table.dao.remote.TableNetCallback;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.agmobile.patrolcore.common.table.model.Project;
import com.augurit.agmobile.patrolcore.common.table.model.TableChildItem;
import com.augurit.agmobile.patrolcore.common.table.model.TableItem;
import com.augurit.agmobile.patrolcore.common.table.util.ControlType;
import com.augurit.agmobile.patrolcore.search.model.CompleteUploadInfo;
import com.augurit.agmobile.patrolcore.search.model.SearchResult;
import com.augurit.agmobile.patrolcore.search.service.IPatrolSearchService;
import com.augurit.agmobile.patrolcore.search.util.PatrolSearchServiceProvider;
import com.augurit.agmobile.patrolcore.search.view.filterview.FilterItem;
import com.augurit.agmobile.patrolcore.selectlocation.util.PatrolLocationManager;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.loc.WGS84LocationTransform;
import com.augurit.am.cmpt.permission.PermissionsUtil;
import com.augurit.am.cmpt.permission.PermissionsUtil2;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.common.ValidateUtil;
import com.augurit.am.fw.utils.log.LogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * @author ????????? ???xuciluan
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.agpatrol.search
 * @createTime ???????????? ???2017-03-21
 * @modifyBy ????????? ???xuciluan
 * @modifyTime ???????????? ???2017-03-21
 * @modifyMemo ???????????????
 */

public class PatrolSearchPresenter implements IPatrolSearchPresenter {


    private PatrolLocationManager mLocationManager;

    protected IPatrolSearchView mISearchFragment;

    protected ILayerPresenter mLayerPresenter;

    protected IPatrolSearchService mIPatrolSearchService;

    protected AtomicInteger currPage = new AtomicInteger(1);

    protected  int LOAD_SIZE_PER_PAGE = 10; //???????????????????????????

    protected Map<String, String> mParams;  // ??????????????????Map

    protected String mKeywordFeild;      // ?????????????????????

    protected String mProjectIdCur;     // ?????????????????????ID

    protected TableDataManager mTableDataManager;

    /**
     * ????????????
     * @param fragment ???????????????
     * @param layerPresenter ????????????
     * @param searchService  ????????????
     */
    public PatrolSearchPresenter(IPatrolSearchView fragment, ILayerPresenter layerPresenter, IPatrolSearchService searchService){

        this.mISearchFragment = fragment;

        this.mLayerPresenter = layerPresenter;
        this.mISearchFragment.setPresenter(this);

        if (searchService == null){
            searchService = PatrolSearchServiceProvider.provideSearchService(mISearchFragment.getApplicationContext());
        }
        this.mIPatrolSearchService = searchService;
        mParams = new HashMap<>();

        mTableDataManager = new TableDataManager(mISearchFragment.getApplicationContext());
    }

    @Override
    public void loadLayer() {
        loadMap();
    }

//    @NeedPermission(permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION})
    @Override
    public void startLocate() {
        /*PermissionsUtil2.getInstance()
                .requestPermissions(
                        mISearchFragment.getActivity() ,
                        "????????????????????????????????????????????????????????????", 101,
                        new PermissionsUtil2.OnPermissionsCallback() {
                            @Override
                            public void onPermissionsGranted(List<String> perms) {
                                startLocateWithCheck();
                            }
                        },
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION);*/

        PermissionsUtil.getInstance()
                .requestPermissions(
                        mISearchFragment.getActivity() ,
                        new PermissionsUtil.OnPermissionsCallback() {
                            @Override
                            public void onPermissionsGranted(List<String> perms) {
                                startLocateWithCheck();
                            }

                            @Override
                            public void onPermissionsDenied(List<String> perms) {

                            }
                        },
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    private void startLocateWithCheck() {
        if (mLocationManager == null) {
            mLocationManager = new PatrolLocationManager(mISearchFragment.getApplicationContext(), mISearchFragment.getMapView());
            mLocationManager.setCoordinateSystem(new WGS84LocationTransform());
        }

        mLocationManager.startLocate(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mISearchFragment.showLocationCallout(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    @Override
    public void loadData() {
        currPage.set(1);
        mISearchFragment.showLoading();
        if (mProjectIdCur != null && !mProjectIdCur.isEmpty()) {
            mParams.put("projectId", mProjectIdCur);
        }
        mIPatrolSearchService.getHistory(1,LOAD_SIZE_PER_PAGE, mParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SearchResult>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException){
                            mISearchFragment.showLoadedError("?????????????????????...");
                            ToastUtil.shortToast(mISearchFragment.getApplicationContext(),"?????????????????????...");
                        }else {
                            mISearchFragment.showLoadedError("????????????");
                        }
                    }

                    @Override
                    public void onNext(List<SearchResult> searchResults) {
                            mISearchFragment.hideLoading();
                            if (ValidateUtil.isListNull(searchResults)){
                                mISearchFragment.showLoadedEmpty();
                            }else {
                                mISearchFragment.showUploadHistory(searchResults);
                            }
                        currPage.addAndGet(1); //??????
                    }
                });
    }

    /**
     * ?????????????????????
     */
    @Override
    public void initFilterView() {
        // ?????????????????? // TODO ???????????????????????????TaskFragment??????????????????????????????
        String serverUrl = BaseInfoManager.getBaseServerUrl(mISearchFragment.getApplicationContext());
        String userId =BaseInfoManager.getUserId(mISearchFragment.getApplicationContext());
        String  url = serverUrl +"rest/patrol/getAllProject?userId=" + userId;
        mTableDataManager.getAllProjectByNet(url, new TableNetCallback() {
            @Override
            public void onSuccess(Object data) {
                if(data == null){
                    return;
                }
                List<Project> projects = (List<Project>) data;
                if(ListUtil.isEmpty(projects)){
                    return;
                }
                try {
                    // ?????????????????????FilterView
                    FilterItem headItem = new FilterItem("", "projectId", "??????", ControlType.SPINNER, "");
                    LinkedHashMap<String, Object> spinnerDatas = new LinkedHashMap<String, Object>();
                    for (Project project : projects) {
                        spinnerDatas.put(project.getName(), project.getId());
                    }
                    headItem.setSpinnerDatas(spinnerDatas);
                    mISearchFragment.initFilterView(headItem, null, null);   // ?????????FilterView
                    // ??????????????????????????????????????????
                    String projectId = projects.get(0).getId();
                    getFilterConditions(projectId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {
                LogUtil.e("PartrolSearchPresenter", "??????????????????");
            }
        });
    }

    /**
     * ???????????????????????????????????????????????????
     * @param projectId ??????id
     */
    @Override
    public void getFilterConditions(String projectId) {
        String baseUrl = BaseInfoManager.getBaseServerUrl(mISearchFragment.getApplicationContext());
        String urlKeyword =  baseUrl + "rest/report/rptform";  // ????????????????????????????????????????????????TableNetService
        Observable.zip(getFilterConditionsObservable(projectId),    // ??????????????????????????????????????????
                mIPatrolSearchService.getKeywordFileds(urlKeyword, projectId), // ?????????????????????????????????????????????
                new Func2<List<FilterItem>, List<TableItem>, Object[]>() {
                    @Override
                    public Object[] call(List<FilterItem> filterItems, List<TableItem> tableItems) {
                        Object[] objects = new Object[2];
                        objects[0] = filterItems;
                        objects[1] = tableItems;
                        return objects;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object[]>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        LogUtil.e("????????????????????????");
                    }

                    @Override
                    public void onNext(Object[] objects) {
                        List<FilterItem> filterItems = (List<FilterItem>) objects[0];
                        List<TableItem> keywordItems = (List<TableItem>) objects[1];
                        mISearchFragment.initFilterView(null, filterItems, keywordItems);
                    }
                });
        // ????????????????????????
        mProjectIdCur = projectId; //todo 10.15 ??????ProjectId??????????????????id
        mProjectIdCur = SearchFragmentWithoutMap.PROJECT_ID;
        loadData();
    }

    /**
     * ????????????????????????????????????FilterItem
     */
    private Observable<List<FilterItem>> getFilterConditionsObservable(final String projectId) {
        return Observable.just(1)
                .map(new Func1<Integer, List<FilterItem>>() {
                    @Override
                    public List<FilterItem> call(Integer integer) {
                        List<DictionaryItem> dictionaries = mTableDataManager.getAllDictionariesFromDB();
                        // ??????????????????????????????
                        List<DictionaryItem> filterDics = new ArrayList<DictionaryItem>();
                        for (DictionaryItem item : dictionaries) {
                            if (item.getNote() != null &&
                                    item.getNote().contains(projectId)) {
                                int i;
                                for (i = 0; i < filterDics.size(); i++) {
                                    if (filterDics.get(i).getCode().compareTo(item.getCode()) > 0) {
                                        break;
                                    }
                                }
                                filterDics.add(i, item);
                            }
                        }
                        // ??????FilterItem
                        List<FilterItem> filterItems = new ArrayList<FilterItem>();
                        for (DictionaryItem filterDic : filterDics) {
                            FilterItem filterItem = new FilterItem(filterDic.getId(),
                                    filterDic.getValue(),
                                    filterDic.getName(),
                                    ControlType.SPINNER,    // ????????????Spinner??????
                                    filterDic.getPcode());
                            // ??????????????????
                            List<TableChildItem> tableChildItems = mTableDataManager.getTableChildItemsByTypeCode(filterDic.getPcode());
                            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
                            for (TableChildItem tableChildItem : tableChildItems) {
                                String key = tableChildItem.getName();
                                String value = tableChildItem.getValue();
                                if (value == null || value.isEmpty()) {
                                    value = key;
                                }
                                map.put(key, value);
                            }
                            filterItem.setSpinnerDatas(map);
                            filterItems.add(filterItem);
                        }
                        return filterItems;
                    }
                });
    }

    @Override
    public void loadImageUrl(final SearchResult searchResult) {
         mIPatrolSearchService.getPhotos(searchResult.getPatrolId())
                 .doOnSubscribe(new Action0() {
                     @Override
                     public void call() {

                     }
                 })
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Subscriber<List<Photo>>() {
                     @Override
                     public void onCompleted() {
                         mISearchFragment.hideLoadingCompleteInfo();
                     }

                     @Override
                     public void onError(Throwable e) {
                         mISearchFragment.hideLoadingCompleteInfo();
                         mISearchFragment.showLoadingCompleteInfoFailed();
                     }

                     @Override
                     public void onNext(List<Photo> photos) {
                         mISearchFragment.showLoadingCompleteInfo();
                         List<TableItem> tableItems = mIPatrolSearchService.convert(searchResult);
                         mISearchFragment.jumpToDetailedUploadInfoPage(tableItems,photos);
                     }
                 });
    }

    @Override
    public void setPerPageLoadDataNum(int perPageLoadDataNum) {
         LOAD_SIZE_PER_PAGE = perPageLoadDataNum;
    }

    /**
     * ????????????
     */
    @Override
    public void loadMore() {
        mIPatrolSearchService.getHistory(currPage.get(),LOAD_SIZE_PER_PAGE, mParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SearchResult>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException){
                            mISearchFragment.loadMoreFailed("?????????????????????");
                        }else {
                            mISearchFragment.loadMoreFailed("????????????");
                        }

                    }

                    @Override
                    public void onNext(List<SearchResult> searchResults) {
                            if (ValidateUtil.isListNull(searchResults) || searchResults.size() < LOAD_SIZE_PER_PAGE ){
                                mISearchFragment.loadMoreFinished(searchResults,true);
                            }else {
                                mISearchFragment.loadMoreFinished(searchResults,false);
                            }
                        currPage.addAndGet(1); //??????
                    }
                });
    }

    @Override
    public void jumpToDetailPage(final int position, SearchResult searchResult) {

        if(TextUtils.isEmpty(searchResult.getId())){
            ToastUtil.shortToast(mISearchFragment.getApplicationContext(),"????????????????????????????????????????????????");
            return;
        }

        mISearchFragment.showLoadingCompleteInfo();
        mIPatrolSearchService.getCompleteUploadInfoBySearchResult(searchResult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CompleteUploadInfo>() {
                    @Override
                    public void onCompleted() {
                        mISearchFragment.hideLoadingCompleteInfo();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("????????????-??????","??????????????????????????????????????? "+ e.getMessage());
                        mISearchFragment.hideLoadingCompleteInfo();
                        mISearchFragment.showLoadingCompleteInfoFailed();
                    }

                    @Override
                    public void onNext(CompleteUploadInfo completeUploadInfo) {
                        mISearchFragment.hideLoadingCompleteInfo();
                       // List<TableItem> tableItems = mIPatrolSearchService.getTableItemsByPosition(position);
                        mISearchFragment.jumpToDetailedUploadInfoPage(completeUploadInfo.getTableItems(),
                                completeUploadInfo.getPhotos());
                    }
                });


      /*  mIPatrolSearchService.getPhotos(searchResult.getPatrolId())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Photo>>() {
                    @Override
                    public void onCompleted() {
                        mISearchFragment.hideLoadingCompleteInfo();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mISearchFragment.hideLoadingCompleteInfo();
                        mISearchFragment.showLoadingCompleteInfoFailed();
                    }

                    @Override
                    public void onNext(List<Photo> photos) {
                        mISearchFragment.showLoadingCompleteInfo();
                        List<TableItem> tableItems = mIPatrolSearchService.getTableItemsByPosition(position);
                        mISearchFragment.jumpToDetailedUploadInfoPage(tableItems,photos);
                    }
                });*/

    }

    /**
     * ????????????????????????
     */
    @Override
    public void refreshData() {
        currPage.set(1);
        mIPatrolSearchService.getHistory(currPage.get(),LOAD_SIZE_PER_PAGE, mParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SearchResult>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException){
                            mISearchFragment.showLoadedError("?????????????????????");
                        }else {
                            mISearchFragment.showLoadedError("????????????");
                        }

                    }

                    @Override
                    public void onNext(List<SearchResult> searchResults) {
                        mISearchFragment.hideLoading();
                        mISearchFragment.refreshFinished(searchResults);
                        /*if (ValidateUtil.isListNull(searchResults)){
                            mISearchFragment.showLoadedEmpty();
                        }else {
                            mISearchFragment.showUploadHistory(searchResults);
                        }*/
                        currPage.addAndGet(1); //??????
                    }
                });
    }

    @Override
    public void search(String keyWord) {
        mParams = new HashMap<>();
        if (mKeywordFeild == null) mKeywordFeild = "poision";
        mParams.put(mKeywordFeild, keyWord);
        currPage.set(1);
        mISearchFragment.showLoading();
        mIPatrolSearchService.getHistory(1, LOAD_SIZE_PER_PAGE, mParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SearchResult>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException){
                            mISearchFragment.showLoadedError("?????????????????????...");
                            ToastUtil.shortToast(mISearchFragment.getApplicationContext(),"?????????????????????...");
                        }else {
                            mISearchFragment.showLoadedError("????????????");
                        }
                    }

                    @Override
                    public void onNext(List<SearchResult> searchResults) {
                        mISearchFragment.hideLoading();
                        if (ValidateUtil.isListNull(searchResults)){
                            mISearchFragment.showLoadedEmpty();
                        }else {
                            mISearchFragment.showUploadHistory(searchResults);
                        }
                        currPage.addAndGet(1); //??????
                    }
                });
    }

    /**
     * ????????????????????????
     * @param conditionMap ??????map
     */
    @Override
    public void setFilterParams(Map<String, String> conditionMap) {
        // ???????????????????????????????????????????????????
        String keyword = null;
        if (mKeywordFeild != null && mParams.containsKey(mKeywordFeild)) {
            keyword = mParams.get(mKeywordFeild);
        }
        mParams.clear();
        if (keyword != null) mParams.put(mKeywordFeild, keyword);
        for (Map.Entry<String, String> entry : conditionMap.entrySet()) {
            mParams.put(entry.getKey(), entry.getValue());
        }
        refreshData();
    }

    /**
     * ???????????????????????????
     * @param field ??????
     */
    @Override
    public void setKeywordField(String field) {
        mKeywordFeild = field;
    }

    /**
     * ????????????
     */
//    @NeedPermission(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    protected void loadMap() {
        /*PermissionsUtil2.getInstance()
                .requestPermissions(
                        mISearchFragment.getActivity(),
                        "????????????????????????????????????????????????????????????", 101,
                        new PermissionsUtil2.OnPermissionsCallback() {
                            @Override
                            public void onPermissionsGranted(List<String> perms) {
                                loadMapWithCheck();
                            }
                        },
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);*/

        PermissionsUtil.getInstance()
                .requestPermissions(
                        mISearchFragment.getActivity(),
                        new PermissionsUtil.OnPermissionsCallback() {
                            @Override
                            public void onPermissionsGranted(List<String> perms) {
                                loadMapWithCheck();
                            }

                            @Override
                            public void onPermissionsDenied(List<String> perms) {

                            }
                        },
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    protected void loadMapWithCheck() {
               /* ILayersService layersService = new LayerService2(mISearchFragment.getApplicationContext());
        ILayerView layerView = new LayerView(mISearchFragment.getApplicationContext(),mapview,null);
        ILayerPresenter layerPresenter = new LayerPresenter(layerView,layersService);*/
        if (mLayerPresenter != null){
            mLayerPresenter.loadLayer();
        }
    }
}
