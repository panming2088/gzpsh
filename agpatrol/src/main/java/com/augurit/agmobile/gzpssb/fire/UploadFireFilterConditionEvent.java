package com.augurit.agmobile.gzpssb.fire;

/**
 * com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition
 * Created by sdb on 2018/4/11  20:04.
 * Desc：
 */

public class UploadFireFilterConditionEvent {
    private String area = null;
    private String sfwh = null;
    private String sfls = null;
    private String szwz = null;
    private String addr = null;
    private String name = null;
    private Long startTime;
    private Long endTime,uploadid;

    public UploadFireFilterConditionEvent( String area, String sfwh,
                                          String sfls, String szwz, String addr, String name, Long startTime, Long endTime,  Long uploadid) {
        this.area = area;
        this.sfwh = sfwh;
        this.sfls = sfls;
        this.szwz = szwz;
        this.addr = addr;
        this.name = name;
        this.uploadid = uploadid;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSfwh() {
        return sfwh;
    }

    public void setSfwh(String sfwh) {
        this.sfwh = sfwh;
    }

    public String getSfls() {
        return sfls;
    }

    public void setSfls(String sfls) {
        this.sfls = sfls;
    }

    public String getSzwz() {
        return szwz;
    }

    public void setSzwz(String szwz) {
        this.szwz = szwz;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getUploadid() {
        return uploadid;
    }

    public void setUploadid(Long uploadid) {
        this.uploadid = uploadid;
    }
}
