package com.augurit.agmobile.patrolcore.selectdevice.utils;

import android.content.Context;

import com.augurit.agmobile.mapengine.layermanage.service.ILayersService;
import com.augurit.agmobile.mapengine.layermanage.service.LayersService;
import com.augurit.agmobile.patrolcore.selectdevice.service.ISelectDeviceService;
import com.augurit.agmobile.patrolcore.selectdevice.service.SelectDeviceServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.patrolcore.selectdevice.utils
 * @createTime 创建时间 ：17/7/26
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/7/26
 * @modifyMemo 修改备注：
 */

public class SelectDeviceServiceFactory {

    public static final String DEFAULT_TAG = "default_service_provider";

    private static Map<String,ISelectDeviceService> sProviderMap = new HashMap<>();

    public static void injectLayerService(ISelectDeviceService layersService){
        if (sProviderMap == null){
            sProviderMap = new HashMap<>();
        }
        sProviderMap.put(DEFAULT_TAG,layersService);
    }

    public static void injectLayerService(String tag,ISelectDeviceService layersService){
        if (sProviderMap == null){
            sProviderMap = new HashMap<>();
        }
        sProviderMap.put(tag,layersService);
    }

    public static ISelectDeviceService provideLayerService(Context context){
        if (sProviderMap.get(DEFAULT_TAG) == null){
            return new SelectDeviceServiceImpl(context);
        }
        return sProviderMap.get(DEFAULT_TAG);
    }

    public static ISelectDeviceService provideLayerService(Context context,String tag){
        if (sProviderMap.get(tag) == null){
            return new SelectDeviceServiceImpl(context);
        }
        return sProviderMap.get(tag);
    }

    public static void clearInstance(){
        if (sProviderMap != null){
            sProviderMap.clear();
            sProviderMap = null;
        }
    }
}
