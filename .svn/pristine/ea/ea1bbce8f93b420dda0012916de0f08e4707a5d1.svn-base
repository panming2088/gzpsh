package com.augurit.agmobile.gzpssb.journal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * 包名：com.augurit.agmobile.gzpssb.journal.model
 * 类描述：
 * 创建人：luobiao
 * 创建时间：2018/12/29 9:48
 * 修改人：luobiao
 * 修改时间：2018/12/29 9:48
 * 修改备注：
 */
public class PSHHouse implements Parcelable {

    /**
     * id : 10682
     * name : YY
     * operator : 特我
     * addr : 天河区新塘街高普路1027号
     * markPerson : 技术支持2
     * parentOrgName : 广州市水务局
     * dischargerType1 : 生活排污类
     * dischargerType2 : 机关企事业单位
     * area : 天河区
     * town : 新塘街
     * state : 1
     * markTime : 1545790021000
     * imgPath : http://139.159.243.185:8081/img/pshFile/imgSmall/201812/20181226/jq4jgtnq7_20181226100935_sewerageUser_img.jpg
     */

    private int id;
    private String name;
    private String objectId;
    private String operator;
    private String addr;
    private String markPerson;
    private String parentOrgName;
    private String dischargerType1;
    private String dischargerType2;
    private String dischargerType3;
    private String area;
    private String town;
    private String state;
    private Long markTime;
    private String imgPath;
    //一级排水户的名称
    private String mph;
    private String unitId;
    private double x;
    private double y;
    private String reportType;
    private String psdyName;
    private String psdyId;
    private String pshId;

    public String getPsdyName() {
        return psdyName;
    }

    public void setPsdyName(String psdyName) {
        this.psdyName = psdyName;
    }

    public String getPsdyId() {
        return psdyId;
    }

    public void setPsdyId(String psdyId) {
        this.psdyId = psdyId;
    }

    public String getMph() {
        return mph;
    }

    public void setMph(String mph) {
        this.mph = mph;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getAddr() {
        return addr;
    }

    public String getPshId() {
        return pshId;
    }

    public void setPshId(String pshId) {
        this.pshId = pshId;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMarkPerson() {
        return markPerson;
    }

    public void setMarkPerson(String markPerson) {
        this.markPerson = markPerson;
    }

    public String getParentOrgName() {
        return parentOrgName;
    }

    public void setParentOrgName(String parentOrgName) {
        this.parentOrgName = parentOrgName;
    }

    public String getDischargerType1() {
        return dischargerType1;
    }

    public void setDischargerType1(String dischargerType1) {
        this.dischargerType1 = dischargerType1;
    }

    public String getDischargerType2() {
        return dischargerType2;
    }

    public void setDischargerType2(String dischargerType2) {
        this.dischargerType2 = dischargerType2;
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

    public void setArea(String area) {
        this.area = area;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Long markTime) {
        this.markTime = markTime;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.objectId);
        dest.writeString(this.operator);
        dest.writeString(this.addr);
        dest.writeString(this.markPerson);
        dest.writeString(this.parentOrgName);
        dest.writeString(this.dischargerType1);
        dest.writeString(this.dischargerType2);
        dest.writeString(this.dischargerType3);
        dest.writeString(this.area);
        dest.writeString(this.town);
        dest.writeString(this.state);
        dest.writeValue(this.markTime);
        dest.writeString(this.imgPath);
        dest.writeString(this.mph);
        dest.writeString(this.unitId);
        dest.writeDouble(this.x);
        dest.writeDouble(this.y);
        dest.writeString(this.reportType);
        dest.writeString(this.psdyName);
        dest.writeString(this.psdyId);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.name = source.readString();
        this.objectId = source.readString();
        this.operator = source.readString();
        this.addr = source.readString();
        this.markPerson = source.readString();
        this.parentOrgName = source.readString();
        this.dischargerType1 = source.readString();
        this.dischargerType2 = source.readString();
        this.dischargerType3 = source.readString();
        this.area = source.readString();
        this.town = source.readString();
        this.state = source.readString();
        this.markTime = (Long) source.readValue(Long.class.getClassLoader());
        this.imgPath = source.readString();
        this.mph = source.readString();
        this.unitId = source.readString();
        this.x = source.readDouble();
        this.y = source.readDouble();
        this.reportType = source.readString();
        this.psdyName = source.readString();
        this.psdyId = source.readString();
    }

    public PSHHouse() {
    }

    protected PSHHouse(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.objectId = in.readString();
        this.operator = in.readString();
        this.addr = in.readString();
        this.markPerson = in.readString();
        this.parentOrgName = in.readString();
        this.dischargerType1 = in.readString();
        this.dischargerType2 = in.readString();
        this.dischargerType3 = in.readString();
        this.area = in.readString();
        this.town = in.readString();
        this.state = in.readString();
        this.markTime = (Long) in.readValue(Long.class.getClassLoader());
        this.imgPath = in.readString();
        this.mph = in.readString();
        this.unitId = in.readString();
        this.x = in.readDouble();
        this.y = in.readDouble();
        this.reportType = in.readString();
        this.psdyName = in.readString();
        this.psdyId = in.readString();
    }

    public static final Parcelable.Creator<PSHHouse> CREATOR = new Parcelable.Creator<PSHHouse>() {
        @Override
        public PSHHouse createFromParcel(Parcel source) {
            return new PSHHouse(source);
        }

        @Override
        public PSHHouse[] newArray(int size) {
            return new PSHHouse[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSHHouse)) return false;
        PSHHouse pshHouse = (PSHHouse) o;
        return Double.compare(pshHouse.getX(), getX()) == 0 &&
                Double.compare(pshHouse.getY(), getY()) == 0 &&
                Objects.equals(getName(), pshHouse.getName()) &&
                Objects.equals(getObjectId(), pshHouse.getObjectId()) &&
                Objects.equals(getOperator(), pshHouse.getOperator()) &&
                Objects.equals(getAddr(), pshHouse.getAddr()) &&
                Objects.equals(getMarkPerson(), pshHouse.getMarkPerson()) &&
                Objects.equals(getParentOrgName(), pshHouse.getParentOrgName()) &&
                Objects.equals(getDischargerType1(), pshHouse.getDischargerType1()) &&
                Objects.equals(getDischargerType2(), pshHouse.getDischargerType2()) &&
                Objects.equals(getDischargerType3(), pshHouse.getDischargerType3()) &&
                Objects.equals(getArea(), pshHouse.getArea()) &&
                Objects.equals(getTown(), pshHouse.getTown()) &&
                Objects.equals(getState(), pshHouse.getState()) &&
                Objects.equals(getMarkTime(), pshHouse.getMarkTime()) &&
                Objects.equals(getImgPath(), pshHouse.getImgPath()) &&
                Objects.equals(getMph(), pshHouse.getMph()) &&
                Objects.equals(getUnitId(), pshHouse.getUnitId()) &&
                Objects.equals(getReportType(), pshHouse.getReportType()) &&
                Objects.equals(getPsdyName(), pshHouse.getPsdyName()) &&
                Objects.equals(getPsdyId(), pshHouse.getPsdyId()) &&
                Objects.equals(getPshId(), pshHouse.getPshId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getObjectId(), getOperator(), getAddr(), getMarkPerson(), getParentOrgName(), getDischargerType1(), getDischargerType2(), getDischargerType3(), getArea(), getTown(), getState(), getMarkTime(), getImgPath(), getMph(), getUnitId(), getX(), getY(), getReportType(), getPsdyName(), getPsdyId(), getPshId());
    }
}
