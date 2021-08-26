package com.augurit.agmobile.gzps.track.view.presenter;

import android.content.Context;

import com.augurit.agmobile.gzps.common.model.StringResult;
import com.augurit.agmobile.gzps.track.model.Track;
import com.augurit.agmobile.gzps.track.service.TrackNetService;
import com.augurit.agmobile.gzps.track.service.TrackService;
import com.augurit.agmobile.gzps.track.util.OnTrackListener;
import com.augurit.agmobile.gzps.track.util.TrackConstant;
import com.augurit.agmobile.gzps.track.view.ITrackView;
import com.augurit.agmobile.mapengine.gpsstrace.model.GPSTrack;
import com.augurit.agmobile.mapengine.gpsstrace.service.ILocalGpsTrackService;
import com.augurit.agmobile.mapengine.gpsstrace.service.LocalTrackServiceImpl;
import com.augurit.agmobile.mapengine.gpsstrace.util.GPSTraceConstant;
import com.augurit.am.cmpt.common.Callback1;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.fw.utils.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.augurit.agmobile.gzps.track.service.TrackService.LOCATING;
import static com.augurit.agmobile.gzps.track.service.TrackService.NONE;
import static com.augurit.agmobile.gzps.track.service.TrackService.PAUSE;

/**
 * @author 创建人 ：liangshenghong
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.track.view.presenter
 * @createTime 创建时间 ：2017-06-14
 * @modifyBy 修改人 ：liangshenghong
 * @modifyTime 修改时间 ：2017-06-14
 * @modifyMemo 修改备注：
 */

public class TrackPresenter implements ITrackPresenter{
    private Context mContext;
    private ITrackView mTrackView;
    private Callback1 mBackListener;
    private TrackService mTrackService;
    private TrackNetService mTrackNetService;
    private LocalTrackServiceImpl mLocalTrackService;
    private OnTrackListener mOnTrackListener;

    private static final int pageNo = 1;
    private static final int pageSize = 10;

    public TrackPresenter(Context context, ITrackView trackView){
        this.mContext = context;
        this.mTrackView = trackView;
        this.mTrackView.setPresenter(this);
        mTrackService = TrackService.getInstance(mContext);
        mTrackNetService = new TrackNetService(mContext);
        mLocalTrackService = new LocalTrackServiceImpl();
//        mLocalTrackService.clearGpsTrackBeyondToday();
//        mLocalTrackService.deleteAllGPSTrack();
        mTrackNetService.initTrackConfig();    //初始化轨迹记录参数
        mOnTrackListener = new OnTrackListener() {
            @Override
            public void onTrack(GPSTrack gpsTrack) {
                mLocalTrackService.saveTrackPoint(gpsTrack);
//                saveGPSTrackToServer(gpsTrack);
            }

            @Override
            public void onTime(int second) {
                mTrackView.setTrackTime(second);
            }

            @Override
            public void onLength(double length) {
                mTrackView.setTrackLength(length);
            }
        };
        mTrackService.addLocationListener(mOnTrackListener);
        mTrackService.setTimeToUploadGPSTrackCallback(new Callback1() {
            @Override
            public void onCallback(Object o) {
                uploadAllGPSTrack();
            }
        });
        mTrackView.initState(mTrackService.getCurrentLocateState());
        if(mTrackService.getCurrentLocateState() == LOCATING
                || mTrackService.getCurrentLocateState() == PAUSE){
            mTrackView.setTrackTime(getTrackTime());
            mTrackView.setTrackLength(getTrackLength());
        }
        getAllTrack();
    }

    public void getAllTrack(){
        mLocalTrackService.getTrack(1, 100, new Callback2<ILocalGpsTrackService.GetTodayTrackResponse>() {
            @Override
            public void onSuccess(ILocalGpsTrackService.GetTodayTrackResponse getTodayTrackResponse) {
                List<List<GPSTrack>> temp = getTodayTrackResponse.getGPSTracks();
                if(temp == null){
                    mTrackView.setTrackHistory(null);
                    return;
                }
                List<List<GPSTrack>> tracks = new ArrayList<List<GPSTrack>>();
                for(List<GPSTrack> gpsTracks : temp){
                    if(gpsTracks.size() < TrackConstant.minPointAmount){
                        //轨迹点少于规定的最少点的不显示
                        continue;
                    }
                    if(gpsTracks.get(gpsTracks.size()-1).getRecordLength() < TrackConstant.minLength){
                        //轨迹长度小于规定的最短长度的不显示
                        continue;
                    }
                    //同一条轨迹内的所有点顺序排列
                    Collections.sort(gpsTracks, new Comparator<GPSTrack>() {
                        @Override
                        public int compare(GPSTrack o1, GPSTrack o2) {
                            if(o1.getRecordDate() > o2.getRecordDate()){
                                return 1;
                            }
                            return -1;
                        }
                    });
                    int time = (int)(gpsTracks.get(gpsTracks.size()-1).getRecordDate() - gpsTracks.get(0).getRecordDate()) / 1000 / 60;
                    if(time < TrackConstant.minTime){
                        //轨迹总时间少于规定的最短时间的不显示
                        continue;
                    }
                    tracks.add(gpsTracks);
                }
                //按轨迹最后一个点的时间倒序排列
                Collections.sort(tracks, new Comparator<List<GPSTrack>>() {
                    @Override
                    public int compare(List<GPSTrack> o1, List<GPSTrack> o2) {
                        if(o1.get(o1.size()-1).getRecordDate() > o2.get(o2.size()-1).getRecordDate()){
                            return -1;
                        }
                        return 1;
                    }
                });
                mTrackView.setTrackHistory(tracks);
            }

            @Override
            public void onFail(Exception error) {

            }
        });
    }

    public void loadMore(){
        mLocalTrackService.getTodaysTrack(new Callback2<ILocalGpsTrackService.GetTodayTrackResponse>() {
            @Override
            public void onSuccess(ILocalGpsTrackService.GetTodayTrackResponse getTodayTrackResponse) {
                List<List<GPSTrack>> temp = getTodayTrackResponse.getGPSTracks();
                List<List<GPSTrack>> tracks = new ArrayList<List<GPSTrack>>();
                for(List<GPSTrack> gpsTracks : temp){
                    if(gpsTracks.size() < TrackConstant.minPointAmount){
                        continue;
                    }
                    if(gpsTracks.get(gpsTracks.size()-1).getRecordLength() < TrackConstant.minLength){
                        continue;
                    }
                    Collections.sort(gpsTracks, new Comparator<GPSTrack>() {
                        @Override
                        public int compare(GPSTrack o1, GPSTrack o2) {
                            if(o1.getRecordDate() > o2.getRecordDate()){
                                return 1;
                            }
                            return -1;
                        }
                    });
                    int time = (int)(gpsTracks.get(gpsTracks.size()-1).getRecordDate() - gpsTracks.get(0).getRecordDate()) / 1000 / 60;
                    if(time < TrackConstant.minTime){
                        continue;
                    }
                    tracks.add(gpsTracks);
                }
                Collections.sort(tracks, new Comparator<List<GPSTrack>>() {
                    @Override
                    public int compare(List<GPSTrack> o1, List<GPSTrack> o2) {
                        if(o1.get(o1.size()-1).getRecordDate() > o2.get(o2.size()-1).getRecordDate()){
                            return -1;
                        }
                        return 1;
                    }
                });
                mTrackView.onLoadMore(tracks);
            }

            @Override
            public void onFail(Exception error) {

            }
        });
    }

    public void switchState(@TrackService.TrackLocateState int trackState){
//        mTrackService.setCurrentLocateState(trackState);
        switch (trackState){
            case LOCATING:
                mTrackService.startLocate();
                saveTrackToServer(mTrackService.getTrack());
                break;
            case PAUSE:
                mTrackService.pauseLocate();
                break;
            case NONE:
                List<GPSTrack> gpsTracks = mTrackService.getmGPSTracks();
                if(ListUtil.isEmpty(gpsTracks)
                        || gpsTracks.size() < TrackConstant.minPointAmount
//                        || getTrackLength() == 0
                        || getTrackLength() < TrackConstant.minLength
//                        || getTrackTime() == 0
                        || getTrackTime()/60 < TrackConstant.minTime){
                    mLocalTrackService.deleteGPSTrackSByTrackId("" + mTrackService.getCurrentTrackId());
                    deleteTrack(mTrackService.getTrack().getId());
                } else {
//                    mLocalTrackService.saveTrackPoint(gpsTracks);
                    GPSTrack lastestGPSTrack = gpsTracks.get(gpsTracks.size()-1);
                    lastestGPSTrack.setPointState(GPSTraceConstant.POINT_STAT_END);
                    lastestGPSTrack.setRecordDate(System.currentTimeMillis());
                    mLocalTrackService.saveTrackPoint(lastestGPSTrack);
                    mTrackService.saveTrack();
                    saveTrackToServer(mTrackService.getTrack());
                }
                mTrackService.stopLocate();
//                mTrackService.getmGPSTracks().clear();
//                getTodaysTrack();
                break;
            default:
                break;
        }
    }


    @Override
    public void locate() {
        if(mTrackService.getCurrentLocateState() == NONE){
            mTrackView.showTrackRecordOnMapView(null, TrackConstant.TRACK_LOCATE);
            return;
        }
        if(getGPSTracks().size() < 2){
            mTrackView.showMessage("当前轨迹点少于两个，无法查看轨迹！");
        } else {
            mTrackView.showTrackRecordOnMapView(null, TrackConstant.TRACK_FOLLOW);
        }
    }

    private void saveTrackToServer(Track track){
        mTrackNetService.saveTraceLine(track)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StringResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StringResult stringResult) {

                    }
                });
    }

    private void uploadAllGPSTrack(){
        new Thread() {
            public void run() {
                List<GPSTrack> needToUploadTrackList = mLocalTrackService.getUnUploadGPSTrack(mTrackService.getCurrentTrackId());
                for (GPSTrack gpsTrack : needToUploadTrackList) {
                    uploadGPSTrack(gpsTrack);
                }
            }
        }.start();
    }

    private void uploadGPSTrack(final GPSTrack gpsTrack){
        mTrackNetService.saveTracePoint(gpsTrack)
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<StringResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StringResult stringResult) {
                        if(stringResult.isSuccess()){
                            gpsTrack.setUploadState(1);
                            mLocalTrackService.saveTrackPoint(gpsTrack);
                        }
                    }
                });
    }

    private void deleteTrack(long trackId){
        mTrackNetService.deleteTraceLine(trackId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StringResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StringResult stringResult) {

                    }
                });
    }

    @Override
    public int getCurrentTrackState() {
        return mTrackService.getCurrentLocateState();
    }

    @Override
    public List<GPSTrack> getGPSTracks() {
        return mTrackService.getmGPSTracks();
    }

    @Override
    public double getTrackLength() {
        return mTrackService.getmTrackLength();
    }

    @Override
    public int getTrackTime() {
        return mTrackService.getmTrackTimeSecond();
    }

    /*public void onTrackTimeEvent(TrackTimeEvent trackTimeEvent){
        mTrackView.setTrackTime(trackTimeEvent.getSecond());
    }

    public void onTrackLengthEvent(TrackLengthEvent trackLengthEvent){
        mTrackView.setTrackLength(trackLengthEvent.getLength());
    }*/

    public void back(){
        if(mBackListener != null){
            mBackListener.onCallback(null);
        }
    }

    public void setBackListener(Callback1 backListener){
        this.mBackListener = backListener;
    }
}
