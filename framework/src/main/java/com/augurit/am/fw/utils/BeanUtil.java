/**
 * ACCJava - ACC Java Development Platform
 * Copyright (c) 2014, AfirSraftGarrier, afirsraftgarrier@qq.com
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.augurit.am.fw.utils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Rapid attributes replication for bean.
 *
 * @author QiuYue
 */
public final class BeanUtil {
    private BeanUtil() {
    }

    public static void copyProperties(Object from, Object to) {
        try {
            copyPropertiesExclude(from, to, null);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void copyPropertiesExclude(Object from, Object to,
                                             String[] excludsArray) throws Exception {
        List<String> excludesList = null;
        if (!ListUtil.isEmpty(excludsArray)) {
            excludesList = Arrays.asList(excludsArray);
        }
        Method[] fromMethods = from.getClass().getDeclaredMethods();
        Method[] toMethods = to.getClass().getDeclaredMethods();
        Method fromMethod = null, toMethod = null;
        String fromMethodName = null, toMethodName = null;
        for (int i = 0; i < fromMethods.length; i++) {
            fromMethod = fromMethods[i];
            fromMethodName = fromMethod.getName();
            if (!fromMethodName.contains("get"))
                continue;
            if (excludesList != null
                    && excludesList.contains(fromMethodName.substring(3)
                    .toLowerCase())) {
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if (toMethod == null)
                continue;
            Object value = fromMethod.invoke(from);
            if (value == null)
                continue;
            if (value instanceof Collection) {
                Collection newValue = (Collection) value;
                if (newValue.size() <= 0)
                    continue;
            }
            toMethod.invoke(to, value);
        }
    }

    public static void copyPropertiesInclude(Object from, Object to,
                                             String[] includsArray) throws Exception {
        List<String> includesList = null;
        if (!ListUtil.isEmpty(includsArray)) {
            includesList = Arrays.asList(includsArray);
        } else {
            return;
        }
        Method[] fromMethods = from.getClass().getDeclaredMethods();
        Method[] toMethods = to.getClass().getDeclaredMethods();
        Method fromMethod = null, toMethod = null;
        String fromMethodName = null, toMethodName = null;
        for (int i = 0; i < fromMethods.length; i++) {
            fromMethod = fromMethods[i];
            fromMethodName = fromMethod.getName();
            if (!fromMethodName.contains("get"))
                continue;
            // String str = fromMethodName.substring(3);
            if (includesList != null
                    && !includesList.contains(fromMethodName.substring(3)
                    .toLowerCase())) {
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if (toMethod == null)
                continue;
            Object value = fromMethod.invoke(from);
            if (value == null)
                continue;
            if (value instanceof Collection) {
                Collection newValue = (Collection) value;
                if (newValue.size() <= 0)
                    continue;
            }
            toMethod.invoke(to, value);
        }
    }

    public static Method findMethodByName(Method[] methods, String name) {
        for (int j = 0; j < methods.length; j++) {
            if (methods[j].getName().equals(name))
                return methods[j];
        }
        return null;
    }
}