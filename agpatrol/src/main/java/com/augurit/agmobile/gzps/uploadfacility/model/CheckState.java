package com.augurit.agmobile.gzps.uploadfacility.model;

import android.graphics.Color;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * 审核状态
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.uploadfacility.model
 * @createTime 创建时间 ：17/12/20
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/12/20
 * @modifyMemo 修改备注：
 */

public class CheckState {

    public static final String UNCHECK = "1"; //未审核

    public static final String SUCCESS = "2"; //审核通过

    public static final String IN_DOUBT = "3";//有疑问

    public static final String UNCHECK1 = "1";//审核中
    public static final String UNCHECK2 = "2";//审核中
    public static final String IN_DOUBT1 = "3";//有疑问
    public static final String IN_DOUBT2 = "4";//有疑问
    public static final String SUCCESS2 = "5"; //审核通过
}
