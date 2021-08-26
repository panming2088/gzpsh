package com.augurit.agmobile.gzps.uploadfacility.model;

import android.text.TextUtils;

import com.augurit.agmobile.gzps.uploadfacility.util.YinjingAttributeViewUtil;
import com.augurit.agmobile.gzps.uploadfacility.util.YushuikouAttributeViewUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 雨水口属性
 * Created by xcl on 2017/12/4.
 */

public class YuShuiKouAttributes {

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
     * 属性一允许的值
     */
    private List<String> attributeOneAllowValues;

    /**
     * 属性三允许的值
     */
    private List<String> attributeThreeAllowValues;

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

    /**
     * 错误原因
     */
    private String notAllowUploadReason;

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
        this.notAllowUploadReason = null;
        ifAllowUpload = true;


        Map<String,List<String>> allowValues = new LinkedHashMap<>(3);
        allowValues.put(YushuikouAttributeViewUtil.ATTRIBUTE_ONE, attributeOneAllowValues);
        allowValues.put(YushuikouAttributeViewUtil.ATTRIBUTE_THREE, attributeThreeAllowValues);

        Map<String,String> acturalValues = new HashMap<>(3);
        acturalValues.put(YushuikouAttributeViewUtil.ATTRIBUTE_ONE, attributeOne);
        acturalValues.put(YushuikouAttributeViewUtil.ATTRIBUTE_THREE, attributeThree);


        for (String key : allowValues.keySet()){
            if (!ifAllowUpload){
                break;
            }
            List<String> allowValue = allowValues.get(key);
            String currentAttribute = acturalValues.get(key);
            //判断属性的值是否在允许范围内
            if(!ListUtil.isEmpty(allowValue)
                    && !StringUtil.isEmpty(currentAttribute)) {
                boolean attrValid = false;
                for (String value : allowValue) {
                    if (currentAttribute.equals(value)) {
                        attrValid = true;
                        break;
                    }
                }
                if (!attrValid) {
                    ifAllowUpload = false;
                    invalidAttributeName = key;
                    this.notAllowUploadReason = "不正确，请重新选择！";
                }
            }
        }


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
        if(StringUtil.isEmpty(this.notAllowUploadReason)){
            return this.invalidAttributeName + "不能为空";
        } else {
            return this.invalidAttributeName + this.notAllowUploadReason;
        }
    }

    public List<String> getAttributeOneAllowValues() {
        return attributeOneAllowValues;
    }

    public void setAttributeOneAllowValues(List<String> attributeOneAllowValues) {
        this.attributeOneAllowValues = attributeOneAllowValues;
    }

    public List<String> getAttributeThreeAllowValues() {
        return attributeThreeAllowValues;
    }

    public void setAttributeThreeAllowValues(List<String> attributeThreeAllowValues) {
        this.attributeThreeAllowValues = attributeThreeAllowValues;
    }
}
