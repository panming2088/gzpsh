package com.augurit.agmobile.gzpssb.okhttp.Iview;

import com.augurit.agmobile.gzpssb.bean.SewerageInvestBean;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;

/**
 * Created by xiaoyu on 2018/4/9.
 */

public interface ISewerageItemView {
    void setSewerageItemList(SewerageItemBean sewerageItemList);
    void onInvestCode(SewerageInvestBean bean);
    void onLoadStart();
    void onCompelete();

}
