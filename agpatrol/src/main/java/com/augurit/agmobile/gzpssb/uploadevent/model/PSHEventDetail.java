package com.augurit.agmobile.gzpssb.uploadevent.model;

import java.io.Serializable;
import java.util.List;

/**
 * 事件详情
 *
 * Created by xcl on 2017/11/11.
 */

public class PSHEventDetail implements Serializable {


    /**
     * form : {"appSbsj":1546482376000,"bz":"","code":"","directOrgId":"","directOrgName":"","files":[{"mime":"image/*","name":"_probleam_20190103102755_img.jpg","path":"http://139.159.243.185:8081/img/PshWtsbFile/img/201901/20190103/jqfzoeid1__probleam_20190103102755_img.jpg","thumbPath":"http://139.159.243.185:8081/img/PshWtsbFile/imgSmall/201901/20190103/jqfzoeid1__probleam_20190103102755_img.jpg","time":1546482376000}],"id":12,"isbyself":"","jjcd":"1","kjArea":"天河区","kjTown":"新塘街道","kjVillage":"高塘社区","loginname":"Augur2","objectId":"","parentOrgId":"1063","parentOrgName":"广州市水务局","pshid":10647,"pshmc":"还是计算机","reportaddr":"广东省广州市天河区高普路1031号高塘软件园内,天河区建设工程质量监督检测室东北57米","reportx":113.406324,"reporty":23.174639,"sbr":"技术支持2","sbsj":{"date":3,"day":4,"hours":10,"minutes":26,"month":0,"nanos":0,"seconds":16,"time":1546482376000,"timezoneOffset":-480,"year":119},"sfzf":0,"sggc":[],"sjwcsj":null,"sslx":"","state":"0","szwz":"广东省广州市天河区高普路1027号","teamOrgId":"","teamOrgName":"","userid":2379,"wtlx":"A204001,A204004,A204007","wtms":"环境","x":113.40711,"y":23.1751,"yjwcsj":null,"zfbz":""}
     * code : 200
     * nextlink : [{"linkcode":2,"linkname":"河长办审核"},{"linkcode":1,"linkname":"执法"}]
     */

    private FormBean form;
    private int code;
    private String name;
    private List<NextlinkBean> nextlink;

    public FormBean getForm() {
        return form;
    }

    public void setForm(FormBean form) {
        this.form = form;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NextlinkBean> getNextlink() {
        return nextlink;
    }

    public void setNextlink(List<NextlinkBean> nextlink) {
        this.nextlink = nextlink;
    }

    public static class FormBean {
        /**
         * appSbsj : 1546482376000
         * bz :
         * code :
         * directOrgId :
         * directOrgName :
         * files : [{"mime":"image/*","name":"_probleam_20190103102755_img.jpg","path":"http://139.159.243.185:8081/img/PshWtsbFile/img/201901/20190103/jqfzoeid1__probleam_20190103102755_img.jpg","thumbPath":"http://139.159.243.185:8081/img/PshWtsbFile/imgSmall/201901/20190103/jqfzoeid1__probleam_20190103102755_img.jpg","time":1546482376000}]
         * id : 12
         * isbyself :
         * jjcd : 1
         * kjArea : 天河区
         * kjTown : 新塘街道
         * kjVillage : 高塘社区
         * loginname : Augur2
         * objectId :
         * parentOrgId : 1063
         * parentOrgName : 广州市水务局
         * pshid : 10647
         * pshmc : 还是计算机
         * reportaddr : 广东省广州市天河区高普路1031号高塘软件园内,天河区建设工程质量监督检测室东北57米
         * reportx : 113.406324
         * reporty : 23.174639
         * sbr : 技术支持2
         * sbsj : {"date":3,"day":4,"hours":10,"minutes":26,"month":0,"nanos":0,"seconds":16,"time":1546482376000,"timezoneOffset":-480,"year":119}
         * sfzf : 0
         * sggc : []
         * sjwcsj : null
         * sslx :
         * state : 0
         * szwz : 广东省广州市天河区高普路1027号
         * teamOrgId :
         * teamOrgName :
         * userid : 2379
         * wtlx : A204001,A204004,A204007
         * wtms : 环境
         * x : 113.40711
         * y : 23.1751
         * yjwcsj : null
         * zfbz :
         */

        private long appSbsj;
        private String bz;
        private String code;
        private String directOrgId;
        private String directOrgName;
        private int id;
        private String isbyself;
        private String jjcd;
        private String kjArea;
        private String kjTown;
        private String kjVillage;
        private String loginname;
        private String objectId;
        private String parentOrgId;
        private String parentOrgName;
        private int pshid;
        private String pshmc;
        private String reportaddr;
        private double reportx;
        private double reporty;
        private String sbr;
        private int sfzf;
        private long sjwcsj;
        private String sslx;
        private String state;
        private String szwz;
        private String teamOrgId;
        private String teamOrgName;
        private int userid;
        private String wtlx;
        private String wtms;
        private double x;
        private double y;
        private long yjwcsj;
        private String zfbz;
        private List<FilesBean> files;
        private List<?> sggc;

        private String psdyId;
        private String psdyName;
        private String zgjy;

        public long getAppSbsj() {
            return appSbsj;
        }

        public void setAppSbsj(long appSbsj) {
            this.appSbsj = appSbsj;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDirectOrgId() {
            return directOrgId;
        }

        public void setDirectOrgId(String directOrgId) {
            this.directOrgId = directOrgId;
        }

        public String getDirectOrgName() {
            return directOrgName;
        }

        public void setDirectOrgName(String directOrgName) {
            this.directOrgName = directOrgName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIsbyself() {
            return isbyself;
        }

        public void setIsbyself(String isbyself) {
            this.isbyself = isbyself;
        }

        public String getJjcd() {
            return jjcd;
        }

        public void setJjcd(String jjcd) {
            this.jjcd = jjcd;
        }

        public String getKjArea() {
            return kjArea;
        }

        public void setKjArea(String kjArea) {
            this.kjArea = kjArea;
        }

        public String getKjTown() {
            return kjTown;
        }

        public void setKjTown(String kjTown) {
            this.kjTown = kjTown;
        }

        public String getKjVillage() {
            return kjVillage;
        }

        public void setKjVillage(String kjVillage) {
            this.kjVillage = kjVillage;
        }

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getParentOrgId() {
            return parentOrgId;
        }

        public void setParentOrgId(String parentOrgId) {
            this.parentOrgId = parentOrgId;
        }

        public String getParentOrgName() {
            return parentOrgName;
        }

        public void setParentOrgName(String parentOrgName) {
            this.parentOrgName = parentOrgName;
        }

        public int getPshid() {
            return pshid;
        }

        public void setPshid(int pshid) {
            this.pshid = pshid;
        }

        public String getPshmc() {
            return pshmc;
        }

        public void setPshmc(String pshmc) {
            this.pshmc = pshmc;
        }

        public String getReportaddr() {
            return reportaddr;
        }

        public void setReportaddr(String reportaddr) {
            this.reportaddr = reportaddr;
        }

        public double getReportx() {
            return reportx;
        }

        public void setReportx(double reportx) {
            this.reportx = reportx;
        }

        public double getReporty() {
            return reporty;
        }

        public void setReporty(double reporty) {
            this.reporty = reporty;
        }

        public String getSbr() {
            return sbr;
        }

        public void setSbr(String sbr) {
            this.sbr = sbr;
        }

        public int getSfzf() {
            return sfzf;
        }

        public void setSfzf(int sfzf) {
            this.sfzf = sfzf;
        }

        public long getSjwcsj() {
            return sjwcsj;
        }

        public void setSjwcsj(long sjwcsj) {
            this.sjwcsj = sjwcsj;
        }

        public String getSslx() {
            return sslx;
        }

        public void setSslx(String sslx) {
            this.sslx = sslx;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getSzwz() {
            return szwz;
        }

        public void setSzwz(String szwz) {
            this.szwz = szwz;
        }

        public String getTeamOrgId() {
            return teamOrgId;
        }

        public void setTeamOrgId(String teamOrgId) {
            this.teamOrgId = teamOrgId;
        }

        public String getTeamOrgName() {
            return teamOrgName;
        }

        public void setTeamOrgName(String teamOrgName) {
            this.teamOrgName = teamOrgName;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getWtlx() {
            return wtlx;
        }

        public void setWtlx(String wtlx) {
            this.wtlx = wtlx;
        }

        public String getWtms() {
            return wtms;
        }

        public void setWtms(String wtms) {
            this.wtms = wtms;
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

        public long getYjwcsj() {
            return yjwcsj;
        }

        public void setYjwcsj(long yjwcsj) {
            this.yjwcsj = yjwcsj;
        }

        public String getZfbz() {
            return zfbz;
        }

        public void setZfbz(String zfbz) {
            this.zfbz = zfbz;
        }

        public List<FilesBean> getFiles() {
            return files;
        }

        public void setFiles(List<FilesBean> files) {
            this.files = files;
        }

        public List<?> getSggc() {
            return sggc;
        }

        public void setSggc(List<?> sggc) {
            this.sggc = sggc;
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

        public static class FilesBean {
            /**
             * mime : image/*
             * name : _probleam_20190103102755_img.jpg
             * path : http://139.159.243.185:8081/img/PshWtsbFile/img/201901/20190103/jqfzoeid1__probleam_20190103102755_img.jpg
             * thumbPath : http://139.159.243.185:8081/img/PshWtsbFile/imgSmall/201901/20190103/jqfzoeid1__probleam_20190103102755_img.jpg
             * time : 1546482376000
             */

            private String mime;
            private String name;
            private String path;
            private String thumbPath;
            private long time;

            public String getMime() {
                return mime;
            }

            public void setMime(String mime) {
                this.mime = mime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getThumbPath() {
                return thumbPath;
            }

            public void setThumbPath(String thumbPath) {
                this.thumbPath = thumbPath;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }
        }
    }

    public static class NextlinkBean implements Serializable{
        /**
         * linkcode : 2
         * linkname : 河长办审核
         */

        private int linkcode;
        private String linkname;

        public int getLinkcode() {
            return linkcode;
        }

        public void setLinkcode(int linkcode) {
            this.linkcode = linkcode;
        }

        public String getLinkname() {
            return linkname;
        }

        public void setLinkname(String linkname) {
            this.linkname = linkname;
        }
    }
}
