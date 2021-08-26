package com.augurit.agmobile.gzpssb.uploadfacility.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 排水户挂接接户井实体类
 *
 * @PROJECT GZPSH
 * @USER Augurit
 * @CREATE 2021/3/31 10:24
 */
public class HangUpWellBean implements Parcelable {

    /**
     * pshY : 23.146429280000007
     * markPerson : 技术支持2
     * sort : null
     * gjwX : 113.264023673
     * gjwY : 23.147385981000014
     * pshX : 113.26367316999995
     * rowno : 1
     * id : 2797
     * pshType3 : 其他：xxddxdffhyyttfffffcxseergxshhhdtsgs
     * jhjAddr : null
     * pshAddr : vvgg
     * pshName : cs
     * jhjObjectId : 877419
     * markTime : 1617103313000
     * usid : null
     */

    private double pshY;
    private String markPerson;
    private String attrTwo;
    private double gjwX;
    private double gjwY;
    private double pshX;
    private int rowno;
    private int id;
    private String pshType3;
    private String jhjAddr;
    private String pshAddr;
    private String pshName;
    private String jhjObjectId;
    private long markTime;
    private String usid;

    protected HangUpWellBean(Parcel in) {
        pshY = in.readDouble();
        markPerson = in.readString();
        attrTwo = in.readString();
        gjwX = in.readDouble();
        gjwY = in.readDouble();
        pshX = in.readDouble();
        rowno = in.readInt();
        id = in.readInt();
        pshType3 = in.readString();
        jhjAddr = in.readString();
        pshAddr = in.readString();
        pshName = in.readString();
        jhjObjectId = in.readString();
        markTime = in.readLong();
        usid = in.readString();
    }

    public static final Creator<HangUpWellBean> CREATOR = new Creator<HangUpWellBean>() {
        @Override
        public HangUpWellBean createFromParcel(Parcel in) {
            return new HangUpWellBean(in);
        }

        @Override
        public HangUpWellBean[] newArray(int size) {
            return new HangUpWellBean[size];
        }
    };

    public double getPshY() {
        return pshY;
    }

    public void setPshY(double pshY) {
        this.pshY = pshY;
    }

    public String getMarkPerson() {
        return markPerson;
    }

    public void setMarkPerson(String markPerson) {
        this.markPerson = markPerson;
    }

    public String getAttrTwo() {
        return attrTwo;
    }

    public void setAttrTwo(String attrTwo) {
        this.attrTwo = attrTwo;
    }

    public double getGjwX() {
        return gjwX;
    }

    public void setGjwX(double gjwX) {
        this.gjwX = gjwX;
    }

    public double getGjwY() {
        return gjwY;
    }

    public void setGjwY(double gjwY) {
        this.gjwY = gjwY;
    }

    public double getPshX() {
        return pshX;
    }

    public void setPshX(double pshX) {
        this.pshX = pshX;
    }

    public int getRowno() {
        return rowno;
    }

    public void setRowno(int rowno) {
        this.rowno = rowno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPshType3() {
        return pshType3;
    }

    public void setPshType3(String pshType3) {
        this.pshType3 = pshType3;
    }

    public String getJhjAddr() {
        return jhjAddr;
    }

    public void setJhjAddr(String jhjAddr) {
        this.jhjAddr = jhjAddr;
    }

    public String getPshAddr() {
        return pshAddr;
    }

    public void setPshAddr(String pshAddr) {
        this.pshAddr = pshAddr;
    }

    public String getPshName() {
        return pshName;
    }

    public void setPshName(String pshName) {
        this.pshName = pshName;
    }

    public String getJhjObjectId() {
        return jhjObjectId;
    }

    public void setJhjObjectId(String jhjObjectId) {
        this.jhjObjectId = jhjObjectId;
    }

    public long getMarkTime() {
        return markTime;
    }

    public void setMarkTime(long markTime) {
        this.markTime = markTime;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(pshY);
        parcel.writeString(markPerson);
        parcel.writeString(attrTwo);
        parcel.writeDouble(gjwX);
        parcel.writeDouble(gjwY);
        parcel.writeDouble(pshX);
        parcel.writeInt(rowno);
        parcel.writeInt(id);
        parcel.writeString(pshType3);
        parcel.writeString(jhjAddr);
        parcel.writeString(pshAddr);
        parcel.writeString(pshName);
        parcel.writeString(jhjObjectId);
        parcel.writeLong(markTime);
        parcel.writeString(usid);
    }
}
