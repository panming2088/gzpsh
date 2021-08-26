package com.augurit.agmobile.gzps.uploadfacility.model;

/**
 * Created by xcl on 2017/11/30.
 */

public class FacilityAttributeModel {

    private String layerName;
    private String attrOne;
    private String attrTwo;
    private String attrThree;
    private String attrFour;
    private String attrFive;

    /**
     * 属性是否允许删除  当属性填充不完整时为false;
     */
    private boolean ifAllowUpload;

    /**
     * 不允许上报的原因
     */
    private String notAllowUploadReason;

    public String getAttrFour() {
        return attrFour;
    }

    public void setAttrFour(String attrFour) {
        this.attrFour = attrFour;
    }

    public String getAttrOne() {
        return attrOne;
    }

    public void setAttrOne(String attrOne) {
        this.attrOne = attrOne;
    }

    public String getAttrTwo() {
        return attrTwo;
    }

    public void setAttrTwo(String attrTwo) {
        this.attrTwo = attrTwo;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getAttrThree() {
        return attrThree;
    }

    public void setAttrThree(String attrThree) {
        this.attrThree = attrThree;
    }

    public String getAttrFive() {
        return attrFive;
    }

    public void setAttrFive(String attrFive) {
        this.attrFive = attrFive;
    }

    public boolean isIfAllowUpload() {
        return ifAllowUpload;
    }

    public void setIfAllowUpload(boolean ifAllowUpload) {
        this.ifAllowUpload = ifAllowUpload;
    }

    public String getNotAllowUploadReason() {
        return notAllowUploadReason;
    }

    public void setNotAllowUploadReason(String notAllowUploadReason) {
        this.notAllowUploadReason = notAllowUploadReason;
    }
}
