package com.augurit.agmobile.gzps.common.tracerecord;

import android.view.View;

import com.augurit.am.fw.common.IView;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.tracerecord
 * @createTime 创建时间 ：2017-03-13
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2017-03-13
 * @modifyMemo 修改备注：
 */

public interface ITraceRecordView extends IView {

    void addTraceRecordViewToContainer();

    void showTraceRecordView();

    View getmRoot();
}
