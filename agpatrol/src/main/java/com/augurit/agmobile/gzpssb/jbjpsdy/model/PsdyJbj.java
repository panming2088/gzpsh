package com.augurit.agmobile.gzpssb.jbjpsdy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.augurit.am.fw.utils.JsonUtil;

public class PsdyJbj implements Parcelable {
//              "id": 1,
//              "jbjX": null,
//              "jbjY": null,
//              "psdyObjectId": null,
//              "jbjObjectId": "1",
//              "psdyX": null,
//              "psdyY": null,
//              "usid": null
 private String id;
 private String pshId;
 private double jbjX;
 private double gjwX;
 private double pshX;
 private double jbjY;
 private double gjwY;
 private double pshY;
 private String psdyObjectId;
 private String jbjObjectId;
 private String jhjObjectId;
 private String pshGjlx;
 private double psdyX;
 private double psdyY;
 private String usid;
 private String loginName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPshId() {
        return pshId;
    }

    public void setPshId(String pshId) {
        this.pshId = pshId;
    }

    public double getJbjX() {
        return jbjX;
    }

    public void setJbjX(double jbjX) {
        this.jbjX = jbjX;
    }

    public double getGjwX() {
        return gjwX;
    }

    public void setGjwX(double gjwX) {
        this.gjwX = gjwX;
    }

    public double getPshX() {
        return pshX;
    }

    public void setPshX(double pshX) {
        this.pshX = pshX;
    }

    public double getJbjY() {
        return jbjY;
    }

    public void setJbjY(double jbjY) {
        this.jbjY = jbjY;
    }

    public double getGjwY() {
        return gjwY;
    }

    public void setGjwY(double gjwY) {
        this.gjwY = gjwY;
    }

    public double getPshY() {
        return pshY;
    }

    public void setPshY(double pshY) {
        this.pshY = pshY;
    }

    public String getPsdyObjectId() {
        return psdyObjectId;
    }

    public void setPsdyObjectId(String psdyObjectId) {
        this.psdyObjectId = psdyObjectId;
    }

    public String getJbjObjectId() {
        return jbjObjectId;
    }

    public void setJbjObjectId(String jbjObjectId) {
        this.jbjObjectId = jbjObjectId;
    }

    public String getJhjObjectId() {
        return jhjObjectId;
    }

    public void setJhjObjectId(String jhjObjectId) {
        this.jhjObjectId = jhjObjectId;
    }

    public String getPshGjlx() {
        return pshGjlx;
    }

    public void setPshGjlx(String pshGjlx) {
        this.pshGjlx = pshGjlx;
    }

    public double getPsdyX() {
        return psdyX;
    }

    public void setPsdyX(double psdyX) {
        this.psdyX = psdyX;
    }

    public double getPsdyY() {
        return psdyY;
    }

    public void setPsdyY(double psdyY) {
        this.psdyY = psdyY;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.pshId);
        dest.writeDouble(this.jbjX);
        dest.writeDouble(this.gjwX);
        dest.writeDouble(this.pshX);
        dest.writeDouble(this.jbjY);
        dest.writeDouble(this.gjwY);
        dest.writeDouble(this.pshY);
        dest.writeString(this.psdyObjectId);
        dest.writeString(this.jbjObjectId);
        dest.writeString(this.jhjObjectId);
        dest.writeString(this.pshGjlx);
        dest.writeDouble(this.psdyX);
        dest.writeDouble(this.psdyY);
        dest.writeString(this.usid);
        dest.writeString(this.loginName);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.pshId = source.readString();
        this.jbjX = source.readDouble();
        this.gjwX = source.readDouble();
        this.pshX = source.readDouble();
        this.jbjY = source.readDouble();
        this.gjwY = source.readDouble();
        this.pshY = source.readDouble();
        this.psdyObjectId = source.readString();
        this.jbjObjectId = source.readString();
        this.jhjObjectId = source.readString();
        this.pshGjlx = source.readString();
        this.psdyX = source.readDouble();
        this.psdyY = source.readDouble();
        this.usid = source.readString();
        this.loginName = source.readString();
    }

    public PsdyJbj() {
    }

    protected PsdyJbj(Parcel in) {
        this.id = in.readString();
        this.pshId = in.readString();
        this.jbjX = in.readDouble();
        this.gjwX = in.readDouble();
        this.pshX = in.readDouble();
        this.jbjY = in.readDouble();
        this.gjwY = in.readDouble();
        this.pshY = in.readDouble();
        this.psdyObjectId = in.readString();
        this.jbjObjectId = in.readString();
        this.jhjObjectId = in.readString();
        this.pshGjlx = in.readString();
        this.psdyX = in.readDouble();
        this.psdyY = in.readDouble();
        this.usid = in.readString();
        this.loginName = in.readString();
    }

    public static final Parcelable.Creator<PsdyJbj> CREATOR = new Parcelable.Creator<PsdyJbj>() {
        @Override
        public PsdyJbj createFromParcel(Parcel source) {
            return new PsdyJbj(source);
        }

        @Override
        public PsdyJbj[] newArray(int size) {
            return new PsdyJbj[size];
        }
    };

    @Override
    public String toString() {
        return JsonUtil.getJson(this);
    }
}
