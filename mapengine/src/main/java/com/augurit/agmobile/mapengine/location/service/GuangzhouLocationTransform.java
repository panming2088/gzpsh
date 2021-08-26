package com.augurit.agmobile.mapengine.location.service;

import android.location.Location;

import com.augurit.am.cmpt.coordt.model.Coordinate;
import com.augurit.am.cmpt.loc.ILocationTransform;

/**
 * WGS84转广州坐标
 * @author 创建人 ：xuciluan
 * @package 包名 ：com.augurit.am.map.arcgis.loc.impl
 * @createTime 创建时间 ：2016-11-17
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：2016-11-17
 */

public class GuangzhouLocationTransform implements ILocationTransform {
    @Override
    public Coordinate changeWGS84ToCurrentLocation(Location location) {
        return null;
    }
}
