package com.augurit.agmobile.gzps.track.dao;

import android.content.Context;

import com.augurit.agmobile.gzps.common.model.Result;
import com.augurit.agmobile.gzps.common.model.StringResult;
import com.augurit.agmobile.gzps.track.model.Track;
import com.augurit.agmobile.gzps.track.model.TrackConfig;
import com.augurit.agmobile.gzps.track.model.TrackList;
import com.augurit.agmobile.mapengine.gpsstrace.model.GPSTrack;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.fw.net.AMNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author 创建人 ：liangshenghong
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.track.dao
 * @createTime 创建时间 ：2017-08-14
 * @modifyBy 修改人 ：liangshenghong
 * @modifyTime 修改时间 ：2017-08-14
 * @modifyMemo 修改备注：
 */

public class RemoteTrackRestDao {

    private AMNetwork amNetwork;
    private TrackApi mTrackApi;

    public RemoteTrackRestDao(Context context){
        String serverUrl = BaseInfoManager.getBaseServerUrl(context);
//        String serverUrl = "http://192.168.31.7:8080/agweb/";
        this.amNetwork = new AMNetwork(serverUrl);
        this.amNetwork.build();
        this.amNetwork.registerApi(TrackApi.class);
        this.mTrackApi = (TrackApi) this.amNetwork.getServiceApi(TrackApi.class);
    }

    public TrackConfig getTraceConfig(){
        TrackConfig trackConfig = null;
        try {
            Call<TrackConfig> call = mTrackApi.getTraceConfig();
            Response<TrackConfig> response =  call.execute();
            trackConfig = response.body();
        } catch (Exception e) {
            return null;
        }
        return trackConfig;
    }

    public StringResult saveTracePoint(GPSTrack gpsTrack){
        StringResult result = null;
        try {
            Call<StringResult> call = mTrackApi.saveTracePoint(gpsTrack.getId(),
                    gpsTrack.getTrackId(),
                    gpsTrack.getTrackName(),
                    gpsTrack.getUserId(),
                    gpsTrack.getLongitude(),
                    gpsTrack.getLatitude(),
                    gpsTrack.getRecordDate(),
                    gpsTrack.getRecordLength(),
                    gpsTrack.getPointState());
            Response<StringResult> response =  call.execute();
            result = response.body();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public TrackList getTraceLinesByUserId(String userId, int pageNo, int pageSize){
        TrackList trackList = null;
        try {
            Call<TrackList> call = mTrackApi.getTraceLinesByUserId(userId, pageNo, pageSize);
            Response<TrackList> response =  call.execute();
            trackList = response.body();
        } catch (Exception e) {
            return null;
        }
        return trackList;
    }

    public Result<List<GPSTrack>> getTracePointsByTraceId(long trackId){
        Result<List<GPSTrack>> result = null;
        try {
            Call<Result<List<GPSTrack>>> call = mTrackApi.getTracePointsByTraceId(trackId);
            Response<Result<List<GPSTrack>>> response =  call.execute();
            result = response.body();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public StringResult saveTraceLine(Track track){
        StringResult result = null;
        try {
            int state = track.getEndTime() == 0 ? 0 : 1;
            Call<StringResult> call = mTrackApi.saveTraceLine(track.getId(),
                    track.getTrackName(),
                    track.getUserId(),
                    track.getStartTime(),
                    track.getEndTime(),
                    track.getRecordLength(),
                    state);
            Response<StringResult> response =  call.execute();
            result = response.body();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public StringResult deleteTraceLine(long trackId){
        StringResult result = null;
        try {
            Call<StringResult> call = mTrackApi.deleteTraceLine(trackId);
            Response<StringResult> response =  call.execute();
            result = response.body();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}
