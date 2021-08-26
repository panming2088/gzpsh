package com.augurit.agmobile.gzpssb.seweragewell.presenter;

import android.support.annotation.NonNull;

import com.augurit.agmobile.mapengine.layermanage.model.LayerInfo;
import com.augurit.agmobile.mapengine.layermanage.service.ILayersService;
import com.augurit.agmobile.mapengine.layermanage.view.ILayerView;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;

import java.util.Iterator;
import java.util.List;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzpssb.view
 * @createTime 创建时间 ：2018-05-03
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2018-05-03
 * @modifyMemo 修改备注：
 */

public class PSHDialyPatrollayerPresenter extends PatrolLayerPresenter {

    private LayerInfo drainageUnitLayerInfo;

    @NonNull
    @Override
    protected List<LayerInfo> filterLayerNoNeedToAdd(List<LayerInfo> layerInfos) {
        Iterator<LayerInfo> iterator = layerInfos.iterator();
        while (iterator.hasNext()) {
            LayerInfo next = iterator.next();
            if (!mILayersService.ifActiveLayer(next) || next.getLayerName().contains("_新")
                    ||UPLOAD_LAYER_NAME.equals(next.getLayerName())|| LOCAL_UPLOAD_LAYER_NAME .equals(next.getLayerName())
                    ||DRAINAGE_USER.equals(next.getLayerName())|| DRAINAGE_USER2 .equals(next.getLayerName())
                    ||MY_UPLOAD_LAYER_NAME.equals(next.getLayerName()) || next.getLayerName().contains(PatrolLayerPresenter.COMPONENT_LAYER)
                    ||DRAINAGE_UNIT_LAYER.equals(next.getLayerName()) ||next.getLayerName().contains(PatrolLayerPresenter.DISPLAY_DOOR_NO_LAYER)) {
                iterator.remove();
            } else {
                if (DRAINAGE_UNIT_LAYER2.equals(next.getLayerName())) {
                    drainageUnitLayerInfo = next;
                }
            }
        }

        return layerInfos;
    }

    public PSHDialyPatrollayerPresenter(ILayerView layerView, ILayersService layersService) {
        super(layerView, layersService);
    }

    public void setDrainageUnitLayerInfoAlpha() {
        if (drainageUnitLayerInfo != null) {
            onClickOpacityButton(drainageUnitLayerInfo.getLayerId(), drainageUnitLayerInfo, 0.5f);
        }
    }
}
