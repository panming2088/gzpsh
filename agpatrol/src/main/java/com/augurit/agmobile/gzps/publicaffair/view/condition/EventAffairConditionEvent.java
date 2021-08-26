package com.augurit.agmobile.gzps.publicaffair.view.condition;

/**
 * Created by liangsh on 2017/11/20.
 */

public class EventAffairConditionEvent {

    private String district;
    public String sslx;
    private String componentTypeCode;
    private String eventTypeCode;
    public String psdyName;

    public EventAffairConditionEvent(String district, String componentTypeCode, String eventTypeCode){
        this.district = district;
        this.componentTypeCode = componentTypeCode;
        this.eventTypeCode = eventTypeCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getComponentTypeCode() {
        return componentTypeCode;
    }

    public void setComponentTypeCode(String componentTypeCode) {
        this.componentTypeCode = componentTypeCode;
    }

    public String getEventTypeCode() {
        return eventTypeCode;
    }

    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }

}
