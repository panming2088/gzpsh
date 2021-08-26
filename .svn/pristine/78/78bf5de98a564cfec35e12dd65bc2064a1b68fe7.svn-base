package com.augurit.agmobile.gzpssb.okhttp.Presenter;

import com.augurit.agmobile.gzpssb.bean.SewerageRoomClickItemBean;
import com.augurit.agmobile.gzpssb.okhttp.Factory.IBizFactory;
import com.augurit.agmobile.gzpssb.okhttp.Iview.ISewerageRoomClickView;
import com.augurit.agmobile.gzpssb.okhttp.Moudle.ISewerageRoomClickBiz;
import com.augurit.agmobile.gzpssb.okhttp.oncallback.SewerageRoomClickListener;

/**
 * Created by xiaoyu on 2018/4/9.
 */

public class SewerageRoomClickPersenter {
    private ISewerageRoomClickView iSewerageRoomClickView;
    private ISewerageRoomClickBiz iSewerageRoomClickBiz;

    public SewerageRoomClickPersenter(ISewerageRoomClickView iSewerageRoomClickView) {
        this.iSewerageRoomClickView = iSewerageRoomClickView;
        iSewerageRoomClickBiz = IBizFactory.getSewerageRoomClickBiz();
    }

    public void getdata(String id,int page,int count,int refresh_item,int refresh_list_type){

        iSewerageRoomClickBiz.getdata(new SewerageRoomClickListener() {
            @Override
            public void onSuccess(SewerageRoomClickItemBean sewerageRoomClickItemBean) {
                iSewerageRoomClickView.setSewerageRoomClickList(sewerageRoomClickItemBean);
            }

            @Override
            public void onFailure(String code) {

            }
        },id,page,count,refresh_item,refresh_list_type);
    }
}
