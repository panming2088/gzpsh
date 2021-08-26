package com.augurit.agmobile.gzpssb.uploadevent.model;

import com.augurit.am.fw.db.liteorm.db.annotation.PrimaryKey;
import com.augurit.am.fw.db.liteorm.db.enums.AssignType;

import java.io.Serializable;

/**
 * 包名：com.augurit.agmobile.gzpssb.uploadevent.model
 * 类描述：
 * 创建人：luobiao
 * 创建时间：2019/1/2 10:42
 * 修改人：luobiao
 * 修改时间：2019/1/2 10:42
 * 修改备注：
 */
public class ProblemBean implements Serializable {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private long dbid;
    private String x;//门牌坐标x
    private String y;//门牌坐标x
    private String szwz;//排水户位置
    private String pshmc;//排水户名称
    private String wtlx;//问题类型
    private String jjcd;//紧急程度
    private String wtms;//问题描述
    private String reportx;//用户所在坐标
    private String reporty;//用户所在坐标
    private String reportaddr;//用户所在地址
    private String loginname;//登入用户名
    private String SBR;//上报人;//登入用户名
    private Long pshid;//排水户id
    private String sslx;//设施类型，排水户：psh，接驳井：jbj，接户井：jhj，空地：other
    private String reportType;
    private String psdyId;
    private String psdyName;
    private String zgjy;
    private String wtly; //问题来源:  0:问题上报 1:日常巡检 2: 排水单元监管

    private String pshDj; //0：一级，1：二级

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getSzwz() {
        return szwz;
    }

    public void setSzwz(String szwz) {
        this.szwz = szwz;
    }

    public String getPshmc() {
        return pshmc;
    }

    public void setPshmc(String pshmc) {
        this.pshmc = pshmc;
    }

    public String getWtlx() {
        return wtlx;
    }

    public void setWtlx(String wtlx) {
        this.wtlx = wtlx;
    }

    public String getJjcd() {
        return jjcd;
    }

    public void setJjcd(String jjcd) {
        this.jjcd = jjcd;
    }

    public String getWtms() {
        return wtms;
    }

    public void setWtms(String wtms) {
        this.wtms = wtms;
    }

    public String getReportx() {
        return reportx;
    }

    public void setReportx(String reportx) {
        this.reportx = reportx;
    }

    public String getReporty() {
        return reporty;
    }

    public void setReporty(String reporty) {
        this.reporty = reporty;
    }

    public String getReportaddr() {
        return reportaddr;
    }

    public void setReportaddr(String reportaddr) {
        this.reportaddr = reportaddr;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public Long getPshid() {
        return pshid;
    }

    public void setPshid(Long pshid) {
        this.pshid = pshid;
    }

    public String getSBR() {
        return SBR;
    }

    public void setSBR(String SBR) {
        this.SBR = SBR;
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getSslx() {
        return sslx;
    }

    public void setSslx(String sslx) {
        this.sslx = sslx;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getPsdyId() {
        return psdyId;
    }

    public void setPsdyId(String psdyId) {
        this.psdyId = psdyId;
    }

    public String getPsdyName() {
        return psdyName;
    }

    public void setPsdyName(String psdyName) {
        this.psdyName = psdyName;
    }

    public String getZgjy() {
        return zgjy;
    }

    public void setZgjy(String zgjy) {
        this.zgjy = zgjy;
    }

    public String getWtly() {
        return wtly;
    }

    public void setWtly(String wtly) {
        this.wtly = wtly;
    }

    public String getPshDj() {
        return pshDj;
    }

    public void setPshDj(String pshDj) {
        this.pshDj = pshDj;
    }
}
