package com.augurit.agmobile.gzps.common.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liangsh on 2017/11/8.
 */

public class Result3<T, K> {

    private String message;
    private int code;
    @SerializedName(value = "data", alternate = { "result", "content" ,"rows", "id", "psdyJg"})
    private T data;
    @SerializedName(value = "data2", alternate = { "psdyJgjl"})
    private K data2;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public K getData2() {
        return data2;
    }

    public void setData2(K data2) {
        this.data2 = data2;
    }
}
