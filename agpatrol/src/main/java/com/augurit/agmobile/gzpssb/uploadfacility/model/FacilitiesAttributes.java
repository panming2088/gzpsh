package com.augurit.agmobile.gzpssb.uploadfacility.model;

import android.text.TextUtils;

import com.augurit.agmobile.gzps.uploadfacility.util.YushuikouAttributeViewUtil;

/**
 * 雨水口属性
 * Created by xcl on 2017/12/4.
 */

public class FacilitiesAttributes {

    /**
     * 属性是否可以为空
     */
    public static final boolean ATTRIBUTE_ONE_CAN_NULL = false;
    public static final boolean ATTRIBUTE_THREE_CAN_NULL = false;
    public static final boolean ATTRIBUTE_FOUR_CAN_NULL = false;


    private String attributeOne;

    private String attributeThree;
    private String attributeFour;
    /**
     * 属性是否修改过
     */
    private boolean hasModified;

    /**
     * 属性是否允许删除  当属性填充不完整时为false;
     */
    private boolean ifAllowUpload;

    /**
     * 填写错误的属性名称
     */
    private String invalidAttributeName;

    public boolean isHasModified() {
        return hasModified;
    }

    public void setHasModified(boolean hasModified) {
        this.hasModified = hasModified;
    }

    public String getAttributeOne() {
        return attributeOne;
    }

    public void setAttributeOne(String attributeOne) {
        this.attributeOne = attributeOne;
    }

    public String getAttributeFour() {
        return attributeFour;
    }

    public void setAttributeFour(String attributeFour) {
        this.attributeFour = attributeFour;
    }

    public String getAttributeThree() {
        return attributeThree;
    }

    public void setAttributeThree(String attributeThree) {
        this.attributeThree = attributeThree;
    }

    /**
     * 属性是否允许上报  当属性填充不完整时为false;
     * @return
     */
    public boolean ifAllowUpload() {
        ifAllowUpload = true;
        /**
         * 判断该字段是否允许为空，如果为空，进行非空判断
         */
        if (!ATTRIBUTE_ONE_CAN_NULL){
            if (attributeOne == null || TextUtils.isEmpty(this.attributeOne.replace(" ",""))){
                ifAllowUpload = false;
                this.invalidAttributeName = YushuikouAttributeViewUtil.ATTRIBUTE_ONE;
            }
        }

        if (!ATTRIBUTE_THREE_CAN_NULL && ifAllowUpload){
            if (attributeThree == null || TextUtils.isEmpty(this.attributeThree.replace(" ",""))){
                ifAllowUpload = false;
                this.invalidAttributeName = YushuikouAttributeViewUtil.ATTRIBUTE_THREE;
            }
        }

        if (!ATTRIBUTE_FOUR_CAN_NULL && ifAllowUpload){
            if (attributeFour == null || TextUtils.isEmpty(this.attributeFour.replace(" ",""))){
                ifAllowUpload = false;
                this.invalidAttributeName = YushuikouAttributeViewUtil.ATTRIBUTE_FOUR;
            }
        }
        return ifAllowUpload;
    }

    public String getNotAllowUploadReason(){
        if (ifAllowUpload){
            return "";
        }
        return this.invalidAttributeName + "不能为空";
    }
}
