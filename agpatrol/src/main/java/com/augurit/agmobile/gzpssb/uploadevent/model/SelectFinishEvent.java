package com.augurit.agmobile.gzpssb.uploadevent.model;

import com.esri.core.geometry.Geometry;

/**
 * @author: liangsh
 * @createTime: 2021/4/5
 */
public class SelectFinishEvent {

    public Geometry mGeometry;
    public ProblemFeatureOrAddr featureOrAddr;

    public SelectFinishEvent(Geometry geometry, ProblemFeatureOrAddr featureOrAddr){
        this.mGeometry = geometry;
        this.featureOrAddr = featureOrAddr;
    }
}
