package com.augurit.agmobile.gzpssb.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.augurit.agmobile.gzpssb.journal.model.PSHHouse;
import com.augurit.am.fw.utils.StringUtil;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;

import java.util.Map;
import java.util.Objects;

/**
 * 排水户实体类
 *
 * @PROJECT GZPSH
 * @USER Augurit
 * @CREATE 2021/3/2 14:59
 */
public class DrainageUserBean implements Parcelable {
    final String id;

    /**
     * 地址
     */
    private String address;
    /**
     * 状态
     */
    private String state;
    /**
     * 法人
     */
    private String name;
    /**
     * 层级
     */
    private String level;
    /**
     * 所在城镇
     */
    private String town;
    /**
     * 分类
     */
    private String type;

    private double x;
    private double y;

    private String mph;
    private String unitId;
    private String psdyName;
    private String psdyId;

    public DrainageUserBean(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getMph() {
        return mph;
    }

    public void setMph(String mph) {
        this.mph = mph;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrainageUserBean bean = (DrainageUserBean) o;
        return Double.compare(bean.x, x) == 0 &&
                Double.compare(bean.y, y) == 0 &&
                Objects.equals(id, bean.id) &&
                Objects.equals(address, bean.address) &&
                Objects.equals(state, bean.state) &&
                Objects.equals(name, bean.name) &&
                Objects.equals(level, bean.level) &&
                Objects.equals(town, bean.town) &&
                Objects.equals(type, bean.type) &&
                Objects.equals(mph, bean.mph) &&
                Objects.equals(unitId, bean.unitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, state, name, level, town, type, x, y, mph, unitId);
    }

    public static PSHHouse cover2PshHouse(DrainageUserBean bean){
        PSHHouse pshHouse = new PSHHouse();
        pshHouse.setId(Integer.valueOf(bean.id));
        pshHouse.setName(bean.getName());
        pshHouse.setAddr(bean.getAddress());
        pshHouse.setMph(bean.getMph());
        pshHouse.setUnitId(bean.getUnitId());
        pshHouse.setX(bean.getX());
        pshHouse.setY(bean.getY());
        return pshHouse;
    }

    public static DrainageUserBean parseFromGraphic(Graphic graphic) {
        Map<String, Object> attributes = graphic.getAttributes();
        String id = objectToString(attributes.get("ID"));
        String addr = objectToString(attributes.get("ADDR"));
        String town = objectToString(attributes.get("TOWN"));
        String name = objectToString(attributes.get("NAME"));
        String type = objectToString(attributes.get("DISCHARGER_TYPE3"));
        String state = objectToString(attributes.get("STATE"));
        String mph = objectToString(attributes.get("MPH"));
        String psdyName = objectToString(attributes.get("PSDY_NAME"));
        String psdyId = objectToString(attributes.get("PSDY_ID"));
        final DrainageUserBean drainageUserBean = new DrainageUserBean(id);
        drainageUserBean.setName(name);
        drainageUserBean.setAddress(addr);
        drainageUserBean.setTown(town);
        drainageUserBean.setType(type);
        drainageUserBean.setState(state);
        drainageUserBean.setMph(mph);
        drainageUserBean.setPsdyName(psdyName);
        drainageUserBean.setPsdyId(psdyId);
        if (graphic.getGeometry() != null) {
            Point point = (Point) graphic.getGeometry();
            drainageUserBean.setX(objectToDouble(point.getX()));
            drainageUserBean.setY(objectToDouble(point.getY()));
        }
        return drainageUserBean;
    }

    private static String objectToString(Object object) {
        if (object == null) {
            return "";
        }
        return StringUtil.getNotNullString(object.toString(), "");
    }

    private static double objectToDouble(Object object) {
        if (object == null) {
            return 0;
        }
        return Double.valueOf(object.toString());
    }

    private static int objectToInteger(Object object) {
        if (object == null) {
            return 0;
        }
        return Integer.valueOf(object.toString());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.address);
        dest.writeString(this.state);
        dest.writeString(this.name);
        dest.writeString(this.level);
        dest.writeString(this.town);
        dest.writeString(this.type);
        dest.writeDouble(this.x);
        dest.writeDouble(this.y);
        dest.writeString(this.mph);
        dest.writeString(this.unitId);
        dest.writeString(this.psdyName);
        dest.writeString(this.psdyId);
    }


    protected DrainageUserBean(Parcel in) {
        this.id = in.readString();
        this.address = in.readString();
        this.state = in.readString();
        this.name = in.readString();
        this.level = in.readString();
        this.town = in.readString();
        this.type = in.readString();
        this.x = in.readDouble();
        this.y = in.readDouble();
        this.mph = in.readString();
        this.unitId = in.readString();
        this.psdyName = in.readString();
        this.psdyId = in.readString();
    }

    public static final Parcelable.Creator<DrainageUserBean> CREATOR = new Parcelable.Creator<DrainageUserBean>() {
        @Override
        public DrainageUserBean createFromParcel(Parcel source) {
            return new DrainageUserBean(source);
        }

        @Override
        public DrainageUserBean[] newArray(int size) {
            return new DrainageUserBean[size];
        }
    };
}
