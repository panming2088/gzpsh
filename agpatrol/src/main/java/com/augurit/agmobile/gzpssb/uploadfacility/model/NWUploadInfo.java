package com.augurit.agmobile.gzpssb.uploadfacility.model;

import android.text.TextUtils;

import com.augurit.agmobile.gzps.uploadfacility.model.CompleteTableInfo;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.util.CompleteTableInfoUtil;
import com.augurit.agmobile.gzps.uploadfacility.util.UploadLayerFieldKeyConstant;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.mode.UploadDoorNoDetailBean;
import com.augurit.agmobile.mapengine.common.utils.GeometryUtil;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.am.fw.utils.StringUtil;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;

import java.util.Map;

/**
 * Created by xcl on 2017/12/7.
 */

public class NWUploadInfo {

    private String objectId;
    private String reportType;
    private String markId;
    private ModifiedFacility modifiedFacilities;
    private NWUploadedFacility uploadedFacilities;
    private CompleteTableInfo completeTableInfo;
    private Component component;

    private UploadDoorNoDetailBean uploadDoorNoDetailBean;

    public static NWUploadInfo createNWUploadInfo(Component component) {
        NWUploadInfo info = new NWUploadInfo();
        Graphic graphic = component.getGraphic();
        info.setComponent(component);
        Object o = graphic.getAttributes().get(UploadLayerFieldKeyConstant.REPORT_TYPE);
        Object markId = graphic.getAttributes().get(UploadLayerFieldKeyConstant.MARK_ID);
        String menpai = component.getLayerName();
        if(!TextUtils.isEmpty(menpai)){
            UploadDoorNoDetailBean uploadDoorNoDetailBean = getUploadDoorNoDetailBeanFromGraphic(graphic.getAttributes(), graphic.getGeometry());
                return info;
        }else if (o != null) {
            if (UploadLayerFieldKeyConstant.CORRECT_ERROR.equals(o.toString()) || UploadLayerFieldKeyConstant.CONFIRM.equals(o.toString())) {
                //纠错
                ModifiedFacility modifiedFacilityFromGraphic = getModifiedFacilityFromGraphic(graphic.getAttributes(), graphic.getGeometry());
                info.setModifiedFacilities(modifiedFacilityFromGraphic);
                info.setReportType(UploadLayerFieldKeyConstant.CORRECT_ERROR);
                info.setMarkId(objectToString(markId));
                CompleteTableInfo completeTableInfo = CompleteTableInfoUtil.getCompleteTableInfo(modifiedFacilityFromGraphic);
                info.setCompleteTableInfo(completeTableInfo);
               return  info;
            } else {
                //新增
                NWUploadedFacility uploadedFacilityFromGraphic = getUploadedFacilityFromGraphic(graphic.getAttributes(), graphic.getGeometry());
                info.setReportType(o.toString());
                info.setMarkId(objectToString(markId));
                info.setUploadedFacilities(uploadedFacilityFromGraphic);
                return  info;
           }
        }
        return info;
    }

    private static UploadDoorNoDetailBean getUploadDoorNoDetailBeanFromGraphic(Map<String, Object> attribute, Geometry geometry) {

        UploadDoorNoDetailBean infoBean = new UploadDoorNoDetailBean();
        infoBean.setObjectId(objectToString(attribute.get("OBJECTID")));
        infoBean.setsGuid(objectToString(attribute.get("S_GUID")));
        infoBean.setDzqc(objectToString(attribute.get("DZQC")));
        infoBean.setDzdm(objectToString(attribute.get("DZDM")));
        infoBean.setSsjlx(objectToString(attribute.get("SSJLX")));
        infoBean.setSssqcjwh(objectToString(attribute.get("SSSQCJWH")));
        infoBean.setSsxzqh(objectToString(attribute.get("SSXZQH")));
        if(TextUtils.isEmpty(objectToString(attribute.get("ISTATUE")))){
            infoBean.setIstatue(1);
        }else{
            infoBean.setIstatue(Integer.valueOf(objectToString(attribute.get("ISTATUE"))));
        }
        if(TextUtils.isEmpty(objectToString(attribute.get("ISTATUE")))){
            infoBean.setIsExsit(1);
        }else{
            infoBean.setIsExsit(Integer.valueOf(objectToString(attribute.get("ISEXIST"))));
        }
        infoBean.setCheckState(objectToString(attribute.get("CHECK_STATE")));
        infoBean.setX(objectToDouble(attribute.get("X")));
        infoBean.setY(objectToDouble(attribute.get("Y")));
        infoBean.setMpdzmc(objectToString(attribute.get("MPWZHM")));
        infoBean.setZxjd(objectToDouble(attribute.get("X")));
        infoBean.setZxwd(objectToDouble(attribute.get("Y")));
        infoBean.setMpwzhm(objectToString(attribute.get("MPWZHM")));
        return infoBean;
    }

    private static ModifiedFacility getModifiedFacilityFromGraphic(Map<String, Object> attribute, Geometry geometry) {
        ModifiedFacility modifiedFacility = new ModifiedFacility();
        modifiedFacility.setObjectId(objectToString(attribute.get("OBJECTID")));
        modifiedFacility.setOriginAddr(objectToString(attribute.get("ORIGIN_ADDR")));
        modifiedFacility.setAddr(objectToString(attribute.get("ADDR")));
        modifiedFacility.setRoad(objectToString(attribute.get("ROAD")));
        modifiedFacility.setAttrFive(objectToString(attribute.get("ATTR_FIVE")));
        modifiedFacility.setAttrFour(objectToString(attribute.get("ATTR_FOUR")));
        modifiedFacility.setAttrThree(objectToString(attribute.get("ATTR_THREE")));
        modifiedFacility.setAttrTwo(objectToString(attribute.get("ATTR_TWO")));
        modifiedFacility.setAttrOne(objectToString(attribute.get("ATTR_ONE")));
        modifiedFacility.setCorrectType(objectToString(attribute.get("CORRECT_TYPE")));
        modifiedFacility.setReportType(objectToString(attribute.get("REPORT_TYPE")));
        modifiedFacility.setDescription(objectToString(attribute.get("DESRIPTION")));
        modifiedFacility.setParentOrgName(objectToString(attribute.get("PARENT_ORG_NAME")));
        modifiedFacility.setDirectOrgName(objectToString(attribute.get("DIRECT_ORG_NAME")));
        modifiedFacility.setTeamOrgName(objectToString(attribute.get("TEAM_ORG_NAME")));
        modifiedFacility.setSuperviseOrgName(objectToString(attribute.get("SUPERVISE_ORG_NAME")));
        modifiedFacility.setLayerName(objectToString(attribute.get("LAYER_NAME")));
        modifiedFacility.setUsid(objectToString(attribute.get("US_ID")));
        //修改后的位置
        modifiedFacility.setX(objectToDouble(attribute.get("X")));
        modifiedFacility.setY(objectToDouble(attribute.get("Y")));
        //原设施位置
        //Point geometryCenter = GeometryUtil.getGeometryCenter(geometry);
        modifiedFacility.setOriginX(objectToDouble(attribute.get("ORGIN_X")));
        modifiedFacility.setOriginY(objectToDouble(attribute.get("ORGIN_Y")));
        modifiedFacility.setMarkPerson(objectToString(attribute.get("MARK_PERSON")));
        modifiedFacility.setMarkTime(objectToLong(attribute.get("MARK_TIME")));
        modifiedFacility.setUpdateTime(objectToLong(attribute.get("UPDATE_TIME")));
        modifiedFacility.setCheckState(objectToString(attribute.get("CHECK_STATE")));
        modifiedFacility.setId(objectToLong(attribute.get(UploadLayerFieldKeyConstant.MARK_ID)));

        //原设施信息
        modifiedFacility.setOriginRoad(objectToString(attribute.get("ORIGIN_ROAD")));
        modifiedFacility.setOriginAttrOne(objectToString(attribute.get("ORIGIN_ATTR_ONE")));
        modifiedFacility.setOriginAttrTwo(objectToString(attribute.get("ORIGIN_ATTR_TWO")));
        modifiedFacility.setOriginAttrThree(objectToString(attribute.get("ORIGIN_ATTR_THREE")));
        modifiedFacility.setOriginAttrFour(objectToString(attribute.get("ORIGIN_ATTR_FOUR")));
        modifiedFacility.setOriginAttrFive(objectToString(attribute.get("ORIGIN_ATTR_FIVE")));

        //设施问题
        modifiedFacility.setpCode(objectToString(attribute.get("PCODE")));
        modifiedFacility.setChildCode(objectToString(attribute.get("CHILD_CODE")));

        //管理状态
        modifiedFacility.setCityVillage(objectToString(attribute.get("CITY_VILLAGE")));
        return modifiedFacility;
    }
    public UploadDoorNoDetailBean getUploadDoorNoDetailBean() {
        return uploadDoorNoDetailBean;
    }

    public void setUploadDoorNoDetailBean(UploadDoorNoDetailBean uploadDoorNoDetailBean) {
        this.uploadDoorNoDetailBean = uploadDoorNoDetailBean;
    }

    private static NWUploadedFacility getUploadedFacilityFromGraphic(Map<String, Object> attribute, Geometry geometry) {
        NWUploadedFacility uploadedFacility = new NWUploadedFacility();
        uploadedFacility.setAddr(objectToString(attribute.get("ADDR")));
        uploadedFacility.setObjectId(objectToString(attribute.get("OBJECTID")));
        uploadedFacility.setRoad(objectToString(attribute.get("ROAD")));
//        uploadedFacility.setAttrFive(objectToString(attribute.get("ATTR_FIVE")));
//        uploadedFacility.setAttrFour(objectToString(attribute.get("ATTR_FOUR")));
//        uploadedFacility.setAttrThree(objectToString(attribute.get("ATTR_THREE")));
//        uploadedFacility.setAttrTwo(objectToString(attribute.get("ATTR_TWO")));
//        uploadedFacility.setAttrOne(objectToString(attribute.get("ATTR_ONE")));
        uploadedFacility.setDescription(objectToString(attribute.get("DESRIPTION")));
        uploadedFacility.setParentOrgName(objectToString(attribute.get("PARENT_ORG_NAME")));
        uploadedFacility.setDirectOrgName(objectToString(attribute.get("DIRECT_ORG_NAME")));
//        uploadedFacility.setTeamOrgName(objectToString(attribute.get("TEAM_ORG_NAME")));
//        uploadedFacility.setSuperviseOrgName(objectToString(attribute.get("SUPERVISE_ORG_NAME")));
        uploadedFacility.setComponentType(objectToString(attribute.get("LAYER_NAME")));
        uploadedFacility.setLayerName(objectToString(attribute.get("LAYER_NAME")));
        uploadedFacility.setCmbName(objectToString(attribute.get("CMB_NAME")));
        uploadedFacility.setYjComb(objectToString(attribute.get("YJ_COMB")));
        Point geometryCenter = GeometryUtil.getGeometryCenter(geometry);
        uploadedFacility.setX(geometryCenter.getX());
        uploadedFacility.setY(geometryCenter.getY());
        uploadedFacility.setMarkPerson(objectToString(attribute.get("MARK_PERSON")));
        uploadedFacility.setIsSpecial(objectToString(attribute.get("IS_SPECIAL")));
        uploadedFacility.setMarkTime(objectToLong(attribute.get("MARK_TIME")));
        uploadedFacility.setUpdateTime(objectToLong(attribute.get("UPDATE_TIME")));
        uploadedFacility.setCheckState(objectToString(attribute.get("CHECK_STATE")));
        uploadedFacility.setYjType(objectToString(attribute.get("YJ_TYPE")));
        uploadedFacility.setId(objectToLong(attribute.get(UploadLayerFieldKeyConstant.MARK_ID)));

//        //设施问题
//        uploadedFacility.setpCode(objectToString(attribute.get("PCODE")));
//        uploadedFacility.setChildCode(objectToString(attribute.get("CHILD_CODE")));
//        //管理状态
//        uploadedFacility.setCityVillage(objectToString(attribute.get("CITY_VILLAGE")));
        return uploadedFacility;
    }

    private static String objectToString(Object object) {
        if (object == null) {
            return "";
        }
        return StringUtil.getNotNullString(object.toString(), "");
    }

    private static double objectToDouble(Object object) {
        if (object == null) {
            return 0;
        }
        return Double.valueOf(object.toString());
    }

    private static long objectToLong(Object object) {
        if (object == null) {
            return -1L;
        }
        return Long.valueOf(object.toString());
    }

    public CompleteTableInfo getCompleteTableInfo() {
        return completeTableInfo;
    }

    public void setCompleteTableInfo(CompleteTableInfo completeTableInfo) {
        this.completeTableInfo = completeTableInfo;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public NWUploadedFacility getUploadedFacilities() {
        return uploadedFacilities;
    }

    public void setUploadedFacilities(NWUploadedFacility uploadedFacilities) {
        this.uploadedFacilities = uploadedFacilities;
    }

    public ModifiedFacility getModifiedFacilities() {
        return modifiedFacilities;
    }

    public void setModifiedFacilities(ModifiedFacility modifiedFacilities) {
        this.modifiedFacilities = modifiedFacilities;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }
}
