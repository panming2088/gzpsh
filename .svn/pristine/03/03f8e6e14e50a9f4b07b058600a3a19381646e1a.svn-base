package com.augurit.agmobile.gzps.track.model;

import com.augurit.agmobile.mapengine.gpsstrace.model.GPSTrack;
import com.augurit.am.fw.db.liteorm.db.annotation.Ignore;
import com.augurit.am.fw.db.liteorm.db.annotation.PrimaryKey;
import com.augurit.am.fw.db.liteorm.db.enums.AssignType;

import java.util.List;

/**
 * @author 创建人 ：liangshenghong
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.track.model
 * @createTime 创建时间 ：2017-07-31
 * @modifyBy 修改人 ：liangshenghong
 * @modifyTime 修改时间 ：2017-07-31
 * @modifyMemo 修改备注：
 */

public class Track {

    @PrimaryKey(AssignType.BY_MYSELF)
    private long id;
    private String trackName;   //当前的轨迹名称
    private String userId;
    private long startTime;     //开始时间戳
    private long endTime;       //结束时间戳
    private double recordLength;        //轨迹长度
    @Ignore
    private List<GPSTrack> gpsTrackList;  //轨迹点

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public double getRecordLength() {
        return recordLength;
    }

    public void setRecordLength(double recordLength) {
        this.recordLength = recordLength;
    }

    public List<GPSTrack> getGpsTrackList() {
        return gpsTrackList;
    }

    public void setGpsTrackList(List<GPSTrack> gpsTrackList) {
        this.gpsTrackList = gpsTrackList;
    }
}
