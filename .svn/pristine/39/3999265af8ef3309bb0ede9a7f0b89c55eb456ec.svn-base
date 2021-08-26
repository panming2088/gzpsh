package com.augurit.agmobile.gzpssb.uploadevent.model;

import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.agmobile.patrolcore.selectlocation.model.DetailAddress;
import com.esri.core.geometry.Geometry;

/**
 *
 * 选择部件结束，用于标识模块
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.common.selectcomponent
 * @createTime 创建时间 ：17/11/3
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/11/3
 * @modifyMemo 修改备注：
 */

public class SelectHouseFinishEvent {

    private Geometry mGeometry;
    private PSHHouse mPSHHouse;

    public SelectHouseFinishEvent(Geometry geometry, PSHHouse PSHHouse) {
        mGeometry = geometry;
        mPSHHouse = PSHHouse;
    }

    public Geometry getGeometry() {
        return mGeometry;
    }

    public void setGeometry(Geometry geometry) {
        mGeometry = geometry;
    }

    public PSHHouse getPSHHouse() {
        return mPSHHouse;
    }

    public void setPSHHouse(PSHHouse PSHHouse) {
        mPSHHouse = PSHHouse;
    }
}
