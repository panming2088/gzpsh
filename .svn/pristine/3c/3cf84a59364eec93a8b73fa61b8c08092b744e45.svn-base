package com.augurit.agmobile.gzpssb.journal.view;

import android.text.TextUtils;

import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 窨井属性
 * Created by xcl on 2017/12/4.
 */

public class DialyPatrolAttributes {


    /**
     * 属性是否可以为空
     */
    public static final boolean ATTRIBUTE_ONE_CAN_NULL = false;
    public static final boolean ATTRIBUTE_TWO_CAN_NULL = false;
    public static final boolean ATTRIBUTE_THREE_CAN_NULL = false;
    public static final boolean ATTRIBUTE_FOUR_CAN_NULL = false;
    public static final boolean ATTRIBUTE_FIVE_CAN_NULL = false;

    private String attributeOne;
    private String attributeTwo;
    private String attributeThree;
    private String attributeFour;
    private String attributeFive;

    /**
     * 属性一允许的值
     */
    private List<String> attributeOneAllowValues;

    /**
     * 属性二允许的值
     */
    private List<String> attributeTwoAllowValues;

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

    public String getAttributeTwo() {
        return attributeTwo;
    }

    public void setAttributeTwo(String attributeTwo) {
        this.attributeTwo = attributeTwo;
    }

    public String getAttributeThree() {
        return attributeThree;
    }

    public void setAttributeThree(String attributeThree) {
        this.attributeThree = attributeThree;
    }

    public String getAttributeFour() {
        return attributeFour;
    }

    public void setAttributeFour(String attributeFour) {
        this.attributeFour = attributeFour;
    }

    public String getAttributeFive() {
        return attributeFive;
    }

    public void setAttributeFive(String attributeFive) {
        this.attributeFive = attributeFive;
    }

    public List<String> getAttributeOneAllowValues() {
        return attributeOneAllowValues;
    }

    public void setAttributeOneAllowValues(List<String> attributeOneAllowValues) {
        this.attributeOneAllowValues = attributeOneAllowValues;
    }

    public void setAttributeTwoAllowValues(List<String> attributeTwoAllowValues) {
        this.attributeTwoAllowValues = attributeTwoAllowValues;
    }

    public void setAttributeThreeAllowValues(List<String> attributeThreeAllowValues) {
        this.attributeThreeAllowValues = attributeThreeAllowValues;
    }

    /**
     * 属性是否允许上报  当属性填充不完整时为false;
     * @return
     */
    public boolean ifAllowUpload() {
        this.notAllowUploadReason = null;
        this.ifAllowUpload = true;

        Map<String,List<String>> allowValues = new LinkedHashMap<>(3);
        allowValues.put(DialyPatrolAttributeViewUtil.ATTRIBUTE_ONE, attributeOneAllowValues);
        allowValues.put(DialyPatrolAttributeViewUtil.ATTRIBUTE_TWO, attributeTwoAllowValues);
        allowValues.put(DialyPatrolAttributeViewUtil.ATTRIBUTE_THREE, attributeThreeAllowValues);

        Map<String,String> acturalValues = new HashMap<>(3);
        acturalValues.put(DialyPatrolAttributeViewUtil.ATTRIBUTE_ONE, attributeOne);
        acturalValues.put(DialyPatrolAttributeViewUtil.ATTRIBUTE_TWO, attributeTwo);
        acturalValues.put(DialyPatrolAttributeViewUtil.ATTRIBUTE_THREE, attributeThree);


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

//        //判断属性一的值是否在允许范围内
//        if(!ListUtil.isEmpty(attributeOneAllowValues)
//                && !StringUtil.isEmpty(attributeOne)) {
//            boolean attrOneValid = false;
//            for (String value : attributeOneAllowValues) {
//                if (this.attributeOne.equals(value)) {
//                    attrOneValid = true;
//                    break;
//                }
//            }
//            if (!attrOneValid) {
//                ifAllowUpload = false;
//                invalidAttributeName = DialyPatrolAttributeViewUtil.ATTRIBUTE_ONE;
//                this.notAllowUploadReason = "不正确，请重新选择！";
//            }
//        }

        /**
         * 判断该字段是否允许为空，如果为空，进行非空判断
         */
        if (!ATTRIBUTE_ONE_CAN_NULL && ifAllowUpload){
            if (attributeOne == null || TextUtils.isEmpty(this.attributeOne.replace(" ",""))){
                ifAllowUpload = false;
                this.invalidAttributeName = DialyPatrolAttributeViewUtil.ATTRIBUTE_ONE;
            }
        }

        if (!ATTRIBUTE_TWO_CAN_NULL && ifAllowUpload){
            if (attributeTwo == null || TextUtils.isEmpty(this.attributeTwo.replace(" ",""))){
                ifAllowUpload = false;
                this.invalidAttributeName = DialyPatrolAttributeViewUtil.ATTRIBUTE_TWO;
            }
        }

        if (!ATTRIBUTE_THREE_CAN_NULL && ifAllowUpload){
            if (attributeThree == null || TextUtils.isEmpty(this.attributeThree.replace(" ",""))){
                ifAllowUpload = false;
                this.invalidAttributeName = DialyPatrolAttributeViewUtil.ATTRIBUTE_THREE;
            }
        }

        if (!ATTRIBUTE_FOUR_CAN_NULL && ifAllowUpload){
            if (attributeFour == null || TextUtils.isEmpty(this.attributeFour.replace(" ",""))){
                ifAllowUpload = false;
                this.invalidAttributeName = DialyPatrolAttributeViewUtil.ATTRIBUTE_FOUR;
            }
        }

        if (!ATTRIBUTE_FIVE_CAN_NULL && ifAllowUpload){
            if (attributeFive == null || TextUtils.isEmpty(this.attributeFive.replace(" ",""))){
                ifAllowUpload = false;
                this.invalidAttributeName = DialyPatrolAttributeViewUtil.ATTRIBUTE_FIVE;
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
}
