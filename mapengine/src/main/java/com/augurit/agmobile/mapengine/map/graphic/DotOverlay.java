package com.augurit.agmobile.mapengine.map.graphic;

import android.os.Parcel;
import android.os.Parcelable;

import com.augurit.agmobile.mapengine.map.geometry.LatLng;

/**
 * 点覆盖物
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.mapengine.map.graphic
 * @createTime 创建时间 ：2017-02-15
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2017-02-15
 * @modifyMemo 修改备注：
 */

public class DotOverlay extends Overlay {


    protected int pointWidth;
    protected int pointColor;

    private DotOverlay(){}



    public DotOverlay pointWidth(int pointWidth){
        this.pointWidth = pointWidth;
        return this;
    }

    public DotOverlay pointColor(int pointColor){
        this.pointColor = pointColor;
        return this;
    }

    public DotOverlay getThis() {
        return this;
    }

}
