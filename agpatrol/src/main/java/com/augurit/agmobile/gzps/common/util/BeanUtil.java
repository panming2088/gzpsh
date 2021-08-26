package com.augurit.agmobile.gzps.common.util;

import com.augurit.am.fw.utils.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @author: liangsh
 * @createTime: 2021/5/12
 */
public class BeanUtil {

    public static Object copy(Object object, Type type){
        String str = JsonUtil.getJson(object);
        return JsonUtil.getObject(str, type);
    }

    public static <T> T copy(T object){
        String str = JsonUtil.getJson(object);
        return JsonUtil.getObject(str, new TypeToken<T>(){}.getType());
    }
}
