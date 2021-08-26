package com.augurit.agmobile.mapengine.layerimport.view.presenter;

import android.view.ViewGroup;

import com.augurit.agmobile.mapengine.layerimport.model.Document;
import com.augurit.agmobile.mapengine.layerimport.model.ShpLayerItem;
import com.augurit.am.cmpt.common.Callback1;

import java.util.ArrayList;

/**
 * 描述：图层导入Presenter接口
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.mapengine.layerimport.view.presenter
 * @createTime 创建时间 ：2017-02-10
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2017-02-10
 * @modifyMemo 修改备注：
 */
public interface ILayerImportPresenter {

    void selectedShape(ArrayList<Document> docs);

    void removeLayer(int position);

    void setOnLayerCheckedOnMapTouchListener(ShpLayerItem shpLayerItem);

    void startLayerImport(ViewGroup topBarContainer,
                          ViewGroup toolViewContainer,
                          ViewGroup bottomSheetContainer,
                          ViewGroup candidateContainer);

    void back();

    void setBackListener(Callback1 callback);

    void addShp();
}
