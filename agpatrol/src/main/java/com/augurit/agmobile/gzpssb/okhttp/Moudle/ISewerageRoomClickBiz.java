package com.augurit.agmobile.gzpssb.okhttp.Moudle;

import com.augurit.agmobile.gzpssb.okhttp.oncallback.SewerageRoomClickListener;

/**
 * Created by xiaoyu on 2018/4/9.
 */

public interface ISewerageRoomClickBiz {

    void getdata(SewerageRoomClickListener sewerageRoomClickListener, String id, int page, int count, int refresh_item, int refresh_list_type);
}
