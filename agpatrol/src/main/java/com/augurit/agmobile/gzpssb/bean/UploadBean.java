package com.augurit.agmobile.gzpssb.bean;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzpssb.bean
 * @createTime 创建时间 ：2018-08-29
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2018-08-29
 * @modifyMemo 修改备注：
 */

public class UploadBean {
  private int result_code;
  private String result_msg;

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }
}
