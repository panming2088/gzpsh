package com.augurit.agmobile.gzpssb.uploadfacility.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by augur on 18/3/5.
 */

public class AttachmentEntity implements Parcelable {
    /**
     * affId : 27
     * attName : jedwkpxpv_20180305151834_img.jpg
     * attPath : http://139.159.243.185:8085/img/nw/cjImgimg/201803/20180305/jedwkpxpv_20180305151834_img.jpg
     * attTime : 1520234314000
     * id : 9
     * mime : image/*
     * thumPath : http://139.159.243.185:8085/img/nw/cjImgimgSmall/201803/20180305/jedwkpxpv_20180305151834_img.jpg
     */

    private int affId;
    private String attName;
    private String attPath;
    private long attTime;
    private int id;
    private String mime;
    private String thumPath;

    public void setAffId(int affId) {
        this.affId = affId;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public void setAttPath(String attPath) {
        this.attPath = attPath;
    }

    public void setAttTime(long attTime) {
        this.attTime = attTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public void setThumPath(String thumPath) {
        this.thumPath = thumPath;
    }

    public int getAffId() {
        return affId;
    }

    public String getAttName() {
        return attName;
    }

    public String getAttPath() {
        return attPath;
    }

    public long getAttTime() {
        return attTime;
    }

    public int getId() {
        return id;
    }

    public String getMime() {
        return mime;
    }

    public String getThumPath() {
        return thumPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.affId);
        dest.writeString(this.attName);
        dest.writeString(this.attPath);
        dest.writeLong(this.attTime);
        dest.writeInt(this.id);
        dest.writeString(this.mime);
        dest.writeString(this.thumPath);
    }

    public AttachmentEntity() {
    }

    protected AttachmentEntity(Parcel in) {
        this.affId = in.readInt();
        this.attName = in.readString();
        this.attPath = in.readString();
        this.attTime = in.readLong();
        this.id = in.readInt();
        this.mime = in.readString();
        this.thumPath = in.readString();
    }

    public static final Creator<AttachmentEntity> CREATOR = new Creator<AttachmentEntity>() {
        public AttachmentEntity createFromParcel(Parcel source) {
            return new AttachmentEntity(source);
        }

        public AttachmentEntity[] newArray(int size) {
            return new AttachmentEntity[size];
        }
    };
}
