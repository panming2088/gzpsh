package com.augurit.agmobile.gzps.drainage_unit_monitor.model;

public class JBJ {

    private String sort;
    private int sfjg;
    private int rowno;
    private String subtype;
    private String xzq;
    private long objectid;
    private String addr;
    private double y;
    private String ly;
    private double x;
    private String usid;
    private String markId;
    private String reportType;
    private boolean selected = false;

    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getSort() {
        return sort;
    }

    public void setSfjg(int sfjg) {
        this.sfjg = sfjg;
    }
    public int getSfjg() {
        return sfjg;
    }

    public void setRowno(int rowno) {
        this.rowno = rowno;
    }
    public int getRowno() {
        return rowno;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
    public String getSubtype() {
        return subtype;
    }

    public void setXzq(String xzq) {
        this.xzq = xzq;
    }
    public String getXzq() {
        return xzq;
    }

    public void setObjectid(long objectid) {
        this.objectid = objectid;
    }
    public long getObjectid() {
        return objectid;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getY() {
        return y;
    }

    public void setLy(String ly) {
        this.ly = ly;
    }
    public String getLy() {
        return ly;
    }

    public void setX(double x) {
        this.x = x;
    }
    public double getX() {
        return x;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}