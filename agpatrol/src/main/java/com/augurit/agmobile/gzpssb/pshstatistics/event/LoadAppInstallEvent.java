package com.augurit.agmobile.gzpssb.pshstatistics.event;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzpssb.pshstatistics.event
 * @createTime 创建时间 ：2018-09-10
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2018-09-10
 * @modifyMemo 修改备注：
 */

public class LoadAppInstallEvent {
    public boolean isLoad() {
        return isLoad;
    }

    public LoadAppInstallEvent(boolean isLoad) {
        this.isLoad = isLoad;
    }

    public void setLoad(boolean load) {

        isLoad = load;
    }

    private boolean isLoad;

}
