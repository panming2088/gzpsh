package com.augurit.agmobile.patrolcore.layer.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.augurit.agmobile.mapengine.common.agmobilelayer.AGTiledMapSeriviceLayer;
import com.augurit.agmobile.mapengine.common.agmobilelayer.MoreLevelTdtLayer;
import com.augurit.agmobile.mapengine.common.agmobilelayer.TileLayer;
import com.augurit.agmobile.mapengine.common.agmobilelayer.util.XORDecryptTileCacheReader;
import com.augurit.agmobile.mapengine.common.utils.FilePathUtil;
import com.augurit.agmobile.mapengine.common.utils.GeodatabaseManager;
import com.augurit.agmobile.mapengine.common.utils.ShapefileManager;
import com.augurit.agmobile.mapengine.layermanage.dao.LocalLayerStorageDao;
import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.util.EncryptTileCacheHelper;
import com.augurit.agmobile.mapengine.layermanage.util.ExternalSDCardTileCachePath;
import com.augurit.agmobile.mapengine.layermanage.util.FutureThreadPool;
import com.augurit.agmobile.mapengine.layermanage.util.ILayerFactory;
import com.augurit.agmobile.mapengine.layermanage.util.NonEncryptTileCacheHelper;
import com.augurit.agmobile.mapengine.project.ProjectDataManager;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.log.util.NetUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.log.LogUtil;
import com.esri.android.map.Layer;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISLocalTiledLayer;
import com.esri.android.map.ogc.WMSLayer;
import com.esri.core.ags.MapServiceInfo;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter.UPLOAD_LAYER_NAME;

/**
 * Created by augur on 17/7/17.
 */

public class PatrolLayerFactory implements ILayerFactory {

    @NonNull
    private static Layer getTdtLayer(Context context, String layerId, String url) {
        Layer layer;
        FilePathUtil savePathManager2 = new FilePathUtil(context);
        String savePath2 = savePathManager2.getSavePath();
        layer = new MoreLevelTdtLayer(Integer.parseInt(layerId), url, 6, new NonEncryptTileCacheHelper(context, layerId), true);
        return layer;
    }

    protected static boolean urlIsReachable(final String url) {

        FutureTask<Boolean> futureTask = FutureThreadPool.getInstance().executeTask(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return isConnect(url);
            }
        });
        try {
            return futureTask.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ?????????????????????URL??????????????????????????????,
     * ??????????????????????????? 3 ???, ?????? 3 ??????????????????????????????????????????
     *
     * @param urlStr ??????URL????????????
     * @return URL
     */
    public static synchronized boolean isConnect(String urlStr) {
        int counts = 0;
        if (urlStr == null || urlStr.length() <= 0) {
            return false;
        }
        while (counts < 3) {
            //    long start = 0;
            try {
                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                int state = con.getResponseCode();
                if (state == 200) {
                    return true;
                }
                break;
            } catch (Exception ex) {
                counts++;
                LogUtil.d("????????? " + counts + " ??????" + urlStr + "--?????????");
                continue;
            }
        }
        return false;
    }

    /**
     * ??????????????????
     *
     * @param context
     * @param layerInfo
     * @return
     */
    @Override
    public Layer getLayer(Context context, LayerInfo layerInfo) {
        Layer layer = null;
        int layerId = layerInfo.getLayerId();
        String layerTable = layerInfo.getLayerTable();
        String url = layerInfo.getUrl();
        String currentProjectId = ProjectDataManager.getInstance().getCurrentProjectId(context);
        boolean ifNetworkAvailable = NetUtil.isConnected(context);
        switch (layerInfo.getType()) {
            case FeatureServer:
                String bundlePath2 = new FilePathUtil(context).getSavePath() + "/tile/" + layerInfo.getLayerName() + "/Layers";
                if (new File(bundlePath2).exists()) {
                    try {
                        XORDecryptTileCacheReader tileCacheReader = new XORDecryptTileCacheReader(bundlePath2);
                        layer = new TileLayer(bundlePath2, tileCacheReader);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (layer == null) {
                    //featureServer????????????geodatabase???????????????
                    layer = GeodatabaseManager.getInstance(context).getFeatureLayer(layerTable); //???geodatabase???????????????
                }

                if (layer == null) {
                    layer = ShapefileManager.getFeatureLayer(context, layerTable); //???shapeFile????????????
                }

                if (layer == null && ifNetworkAvailable) {
                    //???????????????????????????????????????
                    layer = new ArcGISDynamicMapServiceLayer(url.replace("FeatureServer", "MapServer"));
                }
                break;

            case DynamicLayer:
                String bundlePath;
                layer = getDynamicLayer(context, layerInfo, layer, url, ifNetworkAvailable);
                break;
            case TileLayer:
                String externalSDCardPath = ExternalSDCardTileCachePath.getExternalStoragePath(context, true);
                String tpkPath = externalSDCardPath + "/" + layerTable + ".tpk";
                String prefixTpkPath = externalSDCardPath + "/VECTOR_" + layerTable + ".tpk";
                bundlePath = externalSDCardPath + "/" + layerTable + "/v101/Layers";
                String prefixBundlePath = externalSDCardPath + "/VECTOR_" + layerTable + "/v101/Layers";
                String tilePath = null;
                if (externalSDCardPath != null) {
                    if (new File(tpkPath).exists()) {
                        tilePath = tpkPath;
                    } else if (new File(prefixTpkPath).exists()) {
                        tilePath = prefixTpkPath;
                    } else if (new File(bundlePath).exists()) {
                        tilePath = bundlePath;
                    } else if (new File(prefixBundlePath).exists()) {
                        tilePath = prefixBundlePath;
                    }
                }
                bundlePath = new FilePathUtil(context).getSavePath() + "/tile/" + layerInfo.getLayerName() + "/Layers";
                if (tilePath != null) {
                    ArcGISLocalTiledLayer arcGISLocalTiledLayer = new ArcGISLocalTiledLayer(tilePath);
                    layer = arcGISLocalTiledLayer;
                } else if (new File(bundlePath).exists()) {
                    try {
                        XORDecryptTileCacheReader tileCacheReader = new XORDecryptTileCacheReader(bundlePath);
                        layer = new TileLayer(bundlePath, tileCacheReader);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                    if (ifNetworkAvailable && urlIsReachable(url.replace("FeatureServer", "MapServer"))) {
                        AGTiledMapSeriviceLayer agTiledMapSeriviceLayer
                                = new AGTiledMapSeriviceLayer(layerId, url.replace("FeatureServer", "MapServer"),
                                new EncryptTileCacheHelper(context, String.valueOf(layerId)), false, context);
                        agTiledMapSeriviceLayer.setExternalSDCardTileCachePath(
                                new ExternalSDCardTileCachePath(context, String.valueOf(layerId), layerTable));
                        layer = agTiledMapSeriviceLayer;
                        MapServiceInfo mapServiceInfo = LocalLayerStorageDao.getMapServiceInfo(context, layerId, currentProjectId, url);
                        if (mapServiceInfo != null) {
                            ((AGTiledMapSeriviceLayer) layer).setMapServiceInfo(mapServiceInfo);
                        }
                        ((AGTiledMapSeriviceLayer) layer).initLayer();
                    }
                }
                break;

            case WFSLayer:
            case FeatureLayer:
                //???geodatabase???????????????
                layer = GeodatabaseManager.getInstance(context).getFeatureLayer(layerTable);

                if (layer == null) {
                    layer = ShapefileManager.getFeatureLayer(context, layerTable); //???shapeFile????????????
                }

                if (layer == null && ifNetworkAvailable) {
                    //????????????
                    layer = new ArcGISFeatureLayer(url.replace("FeatureServer", "MapServer"), null, ArcGISFeatureLayer.MODE.SNAPSHOT);
                }

                break;
            case WMSLayer:
                String wmsBundlePath = new FilePathUtil(context).getSavePath() + "/tile/" + layerInfo.getLayerName() + "/Layers";
                if (new File(wmsBundlePath).exists()) {
                    try {
                        XORDecryptTileCacheReader tileCacheReader = new XORDecryptTileCacheReader(wmsBundlePath);
                        layer = new TileLayer(wmsBundlePath, tileCacheReader);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (layer == null && ifNetworkAvailable) {
                    layer = new WMSLayer(url);
                }
                break;
            case TianDiTu:
                layer = getTdtLayer(context, String.valueOf(layerId), url);
                break;
            case WMTSLayer:
                break;
            default:
                break;
        }
        return layer;
    }

    @Nullable
    protected Layer getDynamicLayer(Context context, LayerInfo layerInfo, Layer layer, String url, boolean ifNetworkAvailable) {
        String bundlePath = new FilePathUtil(context).getSavePath() + "/tile/" + layerInfo.getLayerName() + "/Layers";
        if (new File(bundlePath).exists()) {
            try {
                XORDecryptTileCacheReader tileCacheReader = new XORDecryptTileCacheReader(bundlePath);
                layer = new TileLayer(bundlePath, tileCacheReader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (layer == null && ifNetworkAvailable && urlIsReachable(url.replace("FeatureServer", "MapServer"))) { //???????????????????????????????????????
            layer = new ArcGISDynamicMapServiceLayer(url.replace("FeatureServer", "MapServer"));
            //???????????????????????????????????????
            /**
             * ???????????????LoginService?????????????????????java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
             */
            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.MY_UPLOAD_LAYER_NAME)) {
                String id = new LoginRouter(context, AMDatabase.getInstance()).getUser().getId();
                List<LayerInfo> childLayer = layerInfo.getChildLayer();
                if (!ListUtil.isEmpty(childLayer) && !TextUtils.isEmpty(id)) {
                    //??????????????????
                    HashMap<Integer, String> layerDefs = new HashMap<Integer, String>();
                    for (LayerInfo child : childLayer) {
                        layerDefs.put(child.getLayerId(), "USER_ID = " + id );
                    }
                    ((ArcGISDynamicMapServiceLayer) layer).setLayerDefinitions(layerDefs);
                }
            }

            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.LOCAL_UPLOAD_LAYER_NAME)) {
                String id = new LoginRouter(context, AMDatabase.getInstance()).getUser().getId();
                String district = BaseInfoManager.getUserOrg(context);
                if(district.contains("??????")){
                    district = "??????";
                }
                String where = "";
                if (!district.contains("???")) {
                    if (TextUtils.isEmpty(where)) {
                        where += " PARENT_ORG_NAME like '%" + district + "%'";
                    } else {
                        where += "and PARENT_ORG_NAME like '%" + district + "%'";
                    }
                }
//                if (TextUtils.isEmpty(where)) {
//                    where += " \"USER_ID\" <> " + id ;
//                } else {
//                    where += " and \"USER_ID\" <> " + id;
//                }
                List<LayerInfo> childLayer = layerInfo.getChildLayer();
                if (!ListUtil.isEmpty(childLayer) && !TextUtils.isEmpty(where)) {
                    //??????????????????
                    HashMap<Integer, String> layerDefs = new HashMap<Integer, String>();
                    for (LayerInfo child : childLayer) {
                        layerDefs.put(child.getLayerId(), where);
                    }
                    ((ArcGISDynamicMapServiceLayer) layer).setLayerDefinitions(layerDefs);
                }
            }

            if (layerInfo.getLayerName().contains(UPLOAD_LAYER_NAME)) {
                String district = BaseInfoManager.getUserOrg(context);
                if(district.contains("??????")){
                    district = "??????";
                }
                String where = "";
                if (!district.contains("???")) {
                    if (TextUtils.isEmpty(where)) {
                        where += " PARENT_ORG_NAME like '%" + district + "%'";
                    } else {
                        where += "and PARENT_ORG_NAME like '%" + district + "%'";
                    }
                }
                List<LayerInfo> childLayer = layerInfo.getChildLayer();
                if (!ListUtil.isEmpty(childLayer) && !TextUtils.isEmpty(where)) {
                    //??????????????????
                    HashMap<Integer, String> layerDefs = new HashMap<Integer, String>();
                    for (LayerInfo child : childLayer) {
                        layerDefs.put(child.getLayerId(), where);
                    }
                    ((ArcGISDynamicMapServiceLayer) layer).setLayerDefinitions(layerDefs);
                }
            }
            /**
             * ??????????????????????????????
             */
            if (layerInfo.getLayerName().contains(PatrolLayerPresenter.COMPONENT_LAYER)) {
                String userOrg = BaseInfoManager.getUserOrg(context);
                //??????????????????????????????
                if (userOrg.contains("???")){
                    return layer;
                }
                String where = null;
                //????????????????????????????????????????????? + ?????????
                if (userOrg.contains("????????????")){
                    where = "DISTRICT Like '%??????%' or DISTRICT Like '%??????%' or DISTRICT Like '%??????%' or DISTRICT Like '%??????%' " +
                            "or DISTRICT Like '%??????%' or DISTRICT Like '%??????%' or DISTRICT Like '%??????%'";
                }else {
                    where = "DISTRICT like '%" + userOrg + "%'";
                }
                List<LayerInfo> childLayer = layerInfo.getChildLayer();
                if (!ListUtil.isEmpty(childLayer) && !TextUtils.isEmpty(userOrg)) {
                    //??????????????????
                    HashMap<Integer, String> layerDefs = new HashMap<Integer, String>();
                    for (LayerInfo child : childLayer) {
                        if (child.getLayerName().contains("??????")) {
                            layerDefs.put(child.getLayerId(), where);
                        }
                    }
                    ((ArcGISDynamicMapServiceLayer) layer).setLayerDefinitions(layerDefs);
                }
            }
        }
        return layer;
    }
}
