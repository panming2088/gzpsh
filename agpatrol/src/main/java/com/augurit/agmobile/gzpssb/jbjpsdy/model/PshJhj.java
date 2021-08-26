package com.augurit.agmobile.gzpssb.jbjpsdy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.augurit.am.fw.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class PshJhj extends PsdyJbj {
    @Override
    public String toString() {
        final JSONObject jsob = new JSONObject();
        try {
            jsob.put("loginName",getLoginName());
            jsob.put("description","");
            jsob.put("usid",getUsid());
            jsob.put("jhjObjectId",getJhjObjectId());
            jsob.put("pshId",getPshId());
            jsob.put("gjwX",getGjwX());
            jsob.put("gjwY",getGjwY());
            jsob.put("pshX",getPshX());
            jsob.put("pshY",getPshY());
            jsob.put("pshGjlx",getPshGjlx());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsob.toString();
    }
}
