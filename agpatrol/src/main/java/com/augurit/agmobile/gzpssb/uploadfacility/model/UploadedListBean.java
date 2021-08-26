package com.augurit.agmobile.gzpssb.uploadfacility.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 设施新增实体类
 *
 * @author 创建人 ：luobiao
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzpssb.uploadfacility.model
 * @createTime 创建时间 ：18/4/11
 * @modifyBy 修改人 ：luobiao
 * @modifyTime 修改时间 ：18/4/11
 * @modifyMemo 修改备注：
 */

public class UploadedListBean implements Parcelable {

    /**
     * id : 17
     * markPerson : 龙杰
     * parentOrgName : 天河区住房和建设水务局
     * name : null
     * dischargerType1 : null
     * dischargerType2 : null
     * area : 天河区
     * town : 搜狗岭
     * addr : 天河区***jiewojqel
     * ownerTele : null
     * state : 1
     * markTime : 1523266933000
     * imgPath : http://139.159.243.185:8080/img/DischargeFile/imgSmall/201804/20180409/jfs1svn8f_20171115200806_sewerageUser_img.jpg
     */

    private int id;
    private String markPerson;
    private String parentOrgName;
    private String name;
    private String dischargerType1;
    private String dischargerType2;
    private String dischargerType3;
    private String area;
    private String town;
    private String addr;
    private String ownerTele;
    private String state;
    private long markTime;
    private String imgPath;

    public void setId(int id) {
        this.id = id;
    }

    public void setMarkPerson(String markPerson) {
        this.markPerson = markPerson;
    }

    public void setParentOrgName(String parentOrgName) {
        this.parentOrgName = parentOrgName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDischargerType1(String dischargerType1) {
        this.dischargerType1 = dischargerType1;
    }

    public void setDischargerType2(String dischargerType2) {
        this.dischargerType2 = dischargerType2;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setOwnerTele(String ownerTele) {
        this.ownerTele = ownerTele;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setMarkTime(long markTime) {
        this.markTime = markTime;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getId() {
        return id;
    }

    public String getMarkPerson() {
        return markPerson;
    }

    public String getParentOrgName() {
        return parentOrgName;
    }

    public String getName() {
        return name;
    }

    public String getDischargerType1() {
        return dischargerType1;
    }

    public String getDischargerType2() {
        return dischargerType2;
    }

    public String getDischargerType3() {
        return dischargerType3;
    }

    public void setDischargerType3(String dischargerType3) {
        this.dischargerType3 = dischargerType3;
    }

    public String getArea() {
        return area;
    }

    public String getTown() {
        return town;
    }

    public String getAddr() {
        return addr;
    }

    public String getOwnerTele() {
        return ownerTele;
    }

    public String getState() {
        return state;
    }

    public long getMarkTime() {
        return markTime;
    }

    public String getImgPath() {
        return imgPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.markPerson);
        dest.writeString(this.parentOrgName);
        dest.writeString(this.name);
        dest.writeString(this.dischargerType1);
        dest.writeString(this.dischargerType2);
        dest.writeString(this.dischargerType3);
        dest.writeString(this.area);
        dest.writeString(this.town);
        dest.writeString(this.addr);
        dest.writeString(this.ownerTele);
        dest.writeString(this.state);
        dest.writeLong(this.markTime);
        dest.writeString(this.imgPath);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.markPerson = source.readString();
        this.parentOrgName = source.readString();
        this.name = source.readString();
        this.dischargerType1 = source.readString();
        this.dischargerType2 = source.readString();
        this.dischargerType3 = source.readString();
        this.area = source.readString();
        this.town = source.readString();
        this.addr = source.readString();
        this.ownerTele = source.readString();
        this.state = source.readString();
        this.markTime = source.readLong();
        this.imgPath = source.readString();
    }

    public UploadedListBean() {
    }

    protected UploadedListBean(Parcel in) {
        this.id = in.readInt();
        this.markPerson = in.readString();
        this.parentOrgName = in.readString();
        this.name = in.readString();
        this.dischargerType1 = in.readString();
        this.dischargerType2 = in.readString();
        this.dischargerType3 = in.readString();
        this.area = in.readString();
        this.town = in.readString();
        this.addr = in.readString();
        this.ownerTele = in.readString();
        this.state = in.readString();
        this.markTime = in.readLong();
        this.imgPath = in.readString();
    }

    public static final Parcelable.Creator<UploadedListBean> CREATOR = new Parcelable.Creator<UploadedListBean>() {
        @Override
        public UploadedListBean createFromParcel(Parcel source) {
            return new UploadedListBean(source);
        }

        @Override
        public UploadedListBean[] newArray(int size) {
            return new UploadedListBean[size];
        }
    };
}
