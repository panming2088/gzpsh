package com.augurit.agmobile.gzpssb.pshpublicaffair.view.affairdetail;

import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;

/**
 * com.augurit.agmobile.gzpssb.pshpublicaffair.view.affairdetail
 * Created by sdb on 2018/4/12  16:08.
 * Desc：
 */

public class PSHAffairDetailContract {
    public interface  View{
        void onGetPSHAffairDetail(PSHAffairDetail pshAffairDetail);
    }
    public  interface Presenter{
        void getPSHAffairDetail(int affaridId);
        void getPSHUnitDetail(int unitId);
    }
}
