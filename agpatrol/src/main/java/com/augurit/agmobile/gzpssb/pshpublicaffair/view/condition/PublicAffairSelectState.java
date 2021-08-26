package com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition;

/**
 * com.augurit.agmobile.gzws.nwpublicaffair.view.condition
 * Created by sdb on 2018/3/15  15:09.
 * Desc：用于EventBus传输的对象
 */

public class PublicAffairSelectState {
    private int curSelect;//当前选择的是排水还是农污 0排水 1农污
    private int curPos;//当前选择问题上报列表还是数据上报列表 0问题 1数据


    public PublicAffairSelectState(int curSelect, int curPos) {
        this.curSelect = curSelect;
        this.curPos = curPos;
    }

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        this.curPos = curPos;
    }

    public int getCurSelect() {
        return curSelect;
    }

    public void setCurSelect(int curSelect) {
        this.curSelect = curSelect;
    }
}
