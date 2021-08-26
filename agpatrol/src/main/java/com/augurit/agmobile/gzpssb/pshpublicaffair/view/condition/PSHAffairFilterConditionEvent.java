package com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition;

/**
 * com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition
 * Created by sdb on 2018/4/11  20:04.
 * Desc：
 */

public class PSHAffairFilterConditionEvent {

    private String distrct = null;
    private String bigType = null;
    private String smallType = null;

    private Long startTime;

    private Long endTime;

    public PSHAffairFilterConditionEvent(String distrct,String bigType, String smallType,
                                         Long startTime, Long endTime) {
        this.distrct = distrct;
        this.bigType = bigType;
        this.smallType = smallType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDistrct() {
        return distrct;
    }

    public void setDistrct(String distrct) {
        this.distrct = distrct;
    }

    public String getBigType() {
        return bigType;
    }

    public void setBigType(String bigType) {
        this.bigType = bigType;
    }

    public String getSmallType() {
        return smallType;
    }

    public void setSmallType(String smallType) {
        this.smallType = smallType;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
