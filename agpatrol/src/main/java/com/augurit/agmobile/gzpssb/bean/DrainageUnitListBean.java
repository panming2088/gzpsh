package com.augurit.agmobile.gzpssb.bean;

import com.augurit.agmobile.mapengine.common.widget.callout.attribute.AMFindResult;

import java.util.List;

/**
 * 排水单元列表Bean
 *
 * @PROJECT GZPSH
 * @USER Augurit
 * @CREATE 2021/3/29 10:42
 */
public class DrainageUnitListBean {
    final public List<DrainageUnit> drainageList;
    public DrainageUnitListBean(List<DrainageUnit> drainageList) {
        this.drainageList = drainageList;
    }
}
