package com.augurit.agmobile.gzps.uploadfacility.util;

import android.support.annotation.NonNull;

import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentFieldKeyConstant;
import com.augurit.agmobile.gzps.uploadfacility.model.CompleteTableInfo;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;

import java.util.Map;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.uploadfacility.util
 * @createTime 创建时间 ：17/12/27
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/12/27
 * @modifyMemo 修改备注：
 */

public class CompleteTableInfoUtil {

    @NonNull
    public static CompleteTableInfo getCompleteTableInfo(ModifiedFacility modifiedFacility) {
        CompleteTableInfo completeTableInfo = new CompleteTableInfo();
        Map<String, Object> originValue = null;
        if ("窨井".equals(modifiedFacility.getLayerName())) {
            originValue = YinjingAttributeViewUtil.getOriginValue(modifiedFacility);
            completeTableInfo.setAttrs(originValue);
        } else if ("雨水口".equals(modifiedFacility.getLayerName())) {
            originValue = YushuikouAttributeViewUtil.getOriginValue(modifiedFacility);
            completeTableInfo.setAttrs(originValue);
        } else if ("排放口".equals(modifiedFacility.getLayerName())) {
            originValue = PaifangKouAttributeViewUtil.getOriginValue(modifiedFacility);
            completeTableInfo.setAttrs(originValue);
        }

        if (originValue != null) {
            originValue.put(ComponentFieldKeyConstant.ADDR, modifiedFacility.getOriginAddr());
            originValue.put(ComponentFieldKeyConstant.ROAD, modifiedFacility.getOriginRoad());
            originValue.put(ComponentFieldKeyConstant.USID, modifiedFacility.getUsid());
        }

        return completeTableInfo;
    }
}
