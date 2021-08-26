package com.augurit.agmobile.mapengine.common.agmobilelayer;

import android.text.TextUtils;
import android.util.Log;

import com.augurit.agmobile.mapengine.common.agmobilelayer.model.AGWMTSLayerInfo;
import com.augurit.agmobile.mapengine.common.agmobilelayer.model.CapabilitiesBean;
import com.augurit.agmobile.mapengine.common.agmobilelayer.model.ExceptionMsg;
import com.augurit.agmobile.mapengine.common.agmobilelayer.service.ILayerDownloader;
import com.augurit.agmobile.mapengine.common.agmobilelayer.service.TiandiTuDownloader;
import com.augurit.agmobile.mapengine.common.agmobilelayer.util.ITileCacheHelper;
import com.augurit.agmobile.mapengine.common.agmobilelayer.util.PullParseXmlUtil;
import com.augurit.am.fw.utils.AMFileUtil;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.esri.android.map.TiledServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.io.EsriSecurityException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author 创建人 ：weiqiuyue
 * @package 包名 ：com.augurit.agmobile.mapengine.common.agmobilelayer
 * @createTime 创建时间 ：2017-08-11
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：2017-08-11 15:13
 */
public class AGWMTSLayer extends TiledServiceLayer {

    protected static final String REQUEST_CAPABILITIES_URL = "?SERVICE=WMTS&REQUEST=GetCapabilities";
    protected static final String REQUEST_TILE_URL_KVP = "%s?SERVICE=WMTS&REQUEST=GetTile&VERSION=%s&STYLE=%s&LAYER=%s&TILEMATRIXSET=%s&FORMAT=%s&TILEMATRIX=%d&TILEROW=%d&TILECOL=%d";
    //	http://<wmts-url>/tile/<wmts-version>/<layer>/<style>/<tilematrixset>/<tilematrix>/<tilerow>/<tilecol>.<format>
    protected static final String REQUEST_TILE_URL_REST = "%s/tile/%s/%s/%s/%s/%d/%d/%d.png";
    protected static final String WMTS_CAPABILITIES = "WMTSCapabilities";
    protected Point mOrgin;
    protected Envelope mFullExtent;
    /**
     * 处理缓存的类，通过实现buildOffLineCachePath方法可以自定义存储路径或者说存储规则，如果不提供BaseTileCachePathHelper，默认没有缓存功能
     */
    protected ITileCacheHelper mCacheHelper;
    protected String mBaseUrl;
    protected AGWMTSLayerInfo mWMTSLayerInfo;
    protected boolean mInitLayer;
    protected int mLayerId;
    private int tryTimes = 0;

    public AGWMTSLayer(String url) {
        this(0, url, null, true);
    }

    public AGWMTSLayer(int layerId, String url, ITileCacheHelper cachePathHelper, boolean initLayer) {
        super(url);
        this.mLayerId = layerId;
        this.mBaseUrl = url;
        this.mCacheHelper = cachePathHelper;
        this.mInitLayer = initLayer;
        //        getCapabilities();
    }

    protected void getCapabilities() {
        if (this.mCacheHelper != null) {//如果支持缓存，sd卡中会保存有对应的CAPABILITIES文件
            try {
                String result = getCapabilitiesFromLocal();
                if (TextUtils.isEmpty(result)) {
                    readFromNet();
                } else {
//                    Gson gson = new Gson();
//                    JsonReader reader = new JsonReader(new StringReader(result));
//                    reader.setLenient(true);
//                    CapabilitiesBean capabilitiesBean = gson.fromJson(reader,CapabilitiesBean.class);
                   CapabilitiesBean capabilitiesBean = JsonUtil.getObject(result, CapabilitiesBean.class);
                    if (capabilitiesBean == null) {
                        Log.e("AGWMTSLayer", ExceptionMsg.NOT_FOUND);
                    }else if (ListUtil.isEmpty(capabilitiesBean.getScales())){
                        readFromNet();
                    }else {
                        initTdtLayerInfo(capabilitiesBean);
                    }
                }
            } catch (IOException e) {
                readFromNet();
            }
        } else {
            readFromNet();
        }
    }

    private void readFromNet() {
        getServiceExecutor().submit(new Runnable() {
            @Override
            public void run() {
                String capabilitiesUrl = mBaseUrl + REQUEST_CAPABILITIES_URL;
                new TiandiTuDownloader().loadMessageFromUrl(capabilitiesUrl, new ILayerDownloader.OnLoadDataListenr() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onSuccess(String result) {
                        if (TextUtils.isEmpty(result)) {
                            return;
                        }
                        CapabilitiesBean capabilitiesBean = PullParseXmlUtil.pullParseXML(result);

                        if (capabilitiesBean == null || ListUtil.isEmpty(capabilitiesBean.getScales())
                                || capabilitiesBean.getSpatialReference() == null ) {
                            //如果下载的数据不全，那么再次尝试
                            if(tryTimes <= 1){
                                tryTimes ++ ;
                                Log.e("AGWMTSLayer","再次尝试");
                                readFromNet();
                            }else {
                                if (capabilitiesBean == null){
                                    Log.e("AGWMTSLayer", ExceptionMsg.NOT_FOUND);
                                }else {
                                    Log.e("AGWMTSLayer", ExceptionMsg.SCALE_NOT_FOUND);
                                }
                                return;
                            }
                        }

                        if (mCacheHelper != null) {//如果支持缓存，保存CAPABILITIES
                           // Gson gson = new Gson();
                           // JsonReader reader = new JsonReader(new StringReader(result));
                           // reader.setLenient(true);
                           // String json = gson.toJson(reader);
                            String json = JsonUtil.getJson(capabilitiesBean);
                            String path = mCacheHelper.getCacheFolderPath() + "/" + WMTS_CAPABILITIES + ".txt";
                            File file = new File(path);
                            try {
                                AMFileUtil.saveStringToFile(json, Charset.defaultCharset(), file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        tryTimes = 0;
                        initTdtLayerInfo(capabilitiesBean);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });

    }

    public String getCapabilitiesFromLocal() throws IOException {
        String path = this.mCacheHelper.getCacheFolderPath() + "/" + WMTS_CAPABILITIES + ".txt";
        File file = new File(path);
        return AMFileUtil.readStringToFile(file, Charset.defaultCharset());
    }

    private void initTdtLayerInfo(CapabilitiesBean capabilitiesBean) {
        this.setOrginFullExtent(capabilitiesBean);
        initWMTSLayerInfo(capabilitiesBean);
        if (this.mInitLayer) {
            try {
                this.getServiceExecutor().submit(new Runnable() {
                    public void run() {
                        initLayer();
                    }
                });
            } catch (RejectedExecutionException e) {

            }
        }
    }

    protected void initWMTSLayerInfo(CapabilitiesBean capabilitiesBean) {
        mWMTSLayerInfo = new AGWMTSLayerInfo(mBaseUrl, capabilitiesBean);
    }

    protected void setOrginFullExtent(CapabilitiesBean capabilitiesBean) {
        if (this.mOrgin != null) {
            capabilitiesBean.setTopLeftCorner(this.mOrgin);
        }
        if (this.mFullExtent != null) {
            capabilitiesBean.setLowerCornerPoint(new Point(this.mFullExtent.getXMin(), this.mFullExtent.getYMin()));
            capabilitiesBean.setUpperCornerPoint(new Point(this.mFullExtent.getXMax(), this.mFullExtent.getYMax()));
        }
    }

    @Override
    protected void initLayer() {
        if (this.getID() == 0L) {
            this.nativeHandle = this.create();
            this.changeStatus(OnStatusChangedListener.STATUS.INITIALIZATION_FAILED);
        } else {
            try {
                this.setDefaultSpatialReference(mWMTSLayerInfo.getSpatialReference());
                this.setFullExtent(new Envelope(mWMTSLayerInfo.getxMin(), mWMTSLayerInfo.getyMin(),
                        mWMTSLayerInfo.getxMax(), mWMTSLayerInfo.getyMax()));

                this.setTileInfo(new TileInfo(mWMTSLayerInfo.getOrigin(), mWMTSLayerInfo
                        .getScales(), mWMTSLayerInfo.getResolutions(), mWMTSLayerInfo
                        .getScales().length, mWMTSLayerInfo.getDpi(), mWMTSLayerInfo
                        .getTileSize(), mWMTSLayerInfo.getTileSize()));

                this.setRenderNativeResolution(true);
                super.initLayer();
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof EsriSecurityException) {
                    this.changeStatus(OnStatusChangedListener.STATUS.fromInt(((EsriSecurityException) e).getCode()));
                } else {
                    this.changeStatus(OnStatusChangedListener.STATUS.INITIALIZATION_FAILED);
                }
            }
        }
    }

    @Override
    protected byte[] getTile(int level, int col, int rol) throws Exception {
        if (getCurrentLevel() != level) {
            return null;
        }
        //生成地图图片的URL
        String url = buildWMTSUrl(level, col, rol);
        if (this.mCacheHelper != null) {
            //读取缓存文件
            byte[] bytes = mCacheHelper.getOfflineCacheFile(level, col, rol);
            if (bytes == null) {
                bytes = getByteFromUrl(url);
                mCacheHelper.addOfflineCacheFile(bytes, level, col, rol);
            }
            return bytes;
        } else {
            return getByteFromUrl(url);
        }
    }

    public byte[] getByteFromUrl(String url) throws Exception {
        return com.esri.core.internal.io.handler.a.a(url, null, null, null);
    }

    public String buildWMTSUrl(int level, int col, int row) {
        String url = null;
        level += mWMTSLayerInfo.getMinZoomLevel();
        if (validLevel(level)) {
            AGWMTSLayerInfo.WMTSInfo wmtsInfo = mWMTSLayerInfo.getWmtsInfo();
            url = wmtsInfo.getUrl();
            //// TODO: 2017/10/25 【更新到研发版本】 {s}导致日志一直打印url错误
            /*int domainsLength = mWMTSLayerInfo.getDomainsLength();
            if (domainsLength > 0) {
                int idx = (int) (Math.random() * (double) domainsLength);
                url = url.replace("{s}", idx + "");
            }*/
            if (mWMTSLayerInfo.getWmtsInfo().getServiceMode() == AGWMTSLayerInfo.ServiceMode.KVP) {
                //将数据填充到定义好的规则中
                url = String.format(Locale.CHINA, REQUEST_TILE_URL_KVP, url, wmtsInfo.getVersion(), wmtsInfo.getStyle(), wmtsInfo.getLayer(),
                        wmtsInfo.getTileMatrixSet(), wmtsInfo.getFormat(),
                        level, row, col);
            } else if (mWMTSLayerInfo.getWmtsInfo().getServiceMode() == AGWMTSLayerInfo.ServiceMode.RESTful) {
                //	http://<wmts-url>/tile/<wmts-version>/<layer>/<style>/<tilematrixset>/<tilematrix>/<tilerow>/<tilecol>.<format>
                url = String.format(Locale.CHINA, REQUEST_TILE_URL_REST, url, wmtsInfo.getVersion(), wmtsInfo.getLayer(),
                        wmtsInfo.getStyle(), wmtsInfo.getTileMatrixSet(),
                        level, row, col);
            }
        }
        return url;
    }

    private boolean validLevel(int zoom) {
        boolean result = false;
        //如果当前的放大比例大于最小比例小于最大比例
        if (mWMTSLayerInfo.getMinZoomLevel() <= zoom && zoom <= mWMTSLayerInfo.getMaxZoomLevel()) {
            result = true;
        }
        return result;
    }

    public AGWMTSLayerInfo getWMTSLayerInfo() {
        return mWMTSLayerInfo;
    }

    public int getLayerId() {
        return mLayerId;
    }
}
