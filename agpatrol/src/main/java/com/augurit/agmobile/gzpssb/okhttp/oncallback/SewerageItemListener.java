package com.augurit.agmobile.gzpssb.okhttp.oncallback;

import com.augurit.agmobile.gzpssb.bean.BuildInfoBySGuid;
import com.augurit.agmobile.gzpssb.bean.SewerageInvestBean;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;

/**
 * Created by xiaoyu on 2018/4/9.
 */

public interface SewerageItemListener extends BaseOnlistener{

    void onSuccess(SewerageItemBean sewerageItemBean);
    void onInvestSuccess(SewerageInvestBean bean);
    void onGetBuildInfo(BuildInfoBySGuid buildInfoBySGuid);
}
