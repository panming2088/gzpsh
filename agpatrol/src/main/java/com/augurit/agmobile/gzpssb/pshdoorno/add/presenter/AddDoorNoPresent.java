package com.augurit.agmobile.gzpssb.pshdoorno.add.presenter;

import android.content.Context;

import com.augurit.agmobile.gzps.common.model.ResponseBody;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.DoorNoRespone;
import com.augurit.agmobile.gzpssb.pshdoorno.base.BasePersenter;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.UploadDoorNoBean;
import com.augurit.agmobile.gzpssb.pshdoorno.add.service.PSHUploadDoorNoService;
import com.augurit.agmobile.gzpssb.pshdoorno.base.IView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzpssb.uploaddoorno.presenter
 * @createTime 创建时间 ：2018-04-21
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2018-04-21
 * @modifyMemo 修改备注：
 */

public class AddDoorNoPresent<T> extends BasePersenter<IView> {
    IView mIView;

    public AddDoorNoPresent(IView view) {
        this.mIView = view;
        this.mIView.initData();
        this.mIView.initView();
    }


    public void submitData(Object o) {
        if (!mIView.checkParams()) return;
        mIView.showProgress();
        PSHUploadDoorNoService service = new PSHUploadDoorNoService((Context) mIView);
        Observable<DoorNoRespone> responseBodyObservable = service.addNewDoorNo((UploadDoorNoBean) o);
        responseBodyObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoorNoRespone>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mIView.disProgress(e);
                    }

                    @Override
                    public void onNext(DoorNoRespone responseBody) {
                        mIView.disProgress(responseBody);
                    }
                });
    }
}
