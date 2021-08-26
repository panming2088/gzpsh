package com.augurit.agmobile.gzpssb.fire.model;

import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;

import java.io.Serializable;
import java.util.List;

public class GroundfireBean implements Serializable {
    private  Long id;
    private  String imgPath;      //平台返回缩略图路径
    private  String markId;      //平台返回缩略图路径
    private  String markPersonId;      //上报人ID
    private  String markPerson ;       //上报人名称
    private  long markTime ;         //上报时间
    private  String addr ;             //基本地址
    private  String area ;             //区
    private  String town ;            // 镇街
    private  String village ;          //社区居委
    private  String name ;             //地上式消防栓
    private  String sfwh ;             //是否完好/0：否；1：是
    private  String sfls ;             //是否漏水/0：否；1：是
    private  String szwz  ;            //所在位置/0：公共区域；1：排水单元内部
    private  String updateTime  ;     // 修改时间
    private  String directOrgId  ;     //维管单位ID
    private  String directOrgName  ;   //维管单位
    private  String teamOrgId ;       // 镇街ID
    private  String teamOrgName ;      //镇街
    private  String parentOrgId ;      //区级机构ID
    private  String parentOrgName;     //区级机构
    private  String loginName;
    private  double x   ;             //x坐标
    private  double y  ;               //y坐标
    private  String userAddr;
    private  String road;
    private  String description;   //上报说明
    private  double userLocationY;
    private  double userLocationX;
    //现场照片
    private  String deleteId;
    private List<Photo> photos8;
    private String hasCert8;//是否现场图片
    private List<Photo> thumbnailPhotos8;
    private Long objectId;
    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }
    public List<HasCert8AttachmentBean> getHasCert8Attachment() {
        return hasCert8Attachment;
    }

    public void setHasCert8Attachment(List<HasCert8AttachmentBean> hasCert8Attachment) {
        this.hasCert8Attachment = hasCert8Attachment;
    }

    private List<HasCert8AttachmentBean> hasCert8Attachment;
    public static class HasCert8AttachmentBean implements Serializable{
        /**
         * attName : 20180413183530_hasCert1_img.jpg
         * attPath : http://139.159.243.185:8080/img/pshFile/img/201804/20180413/jfxtiqwbj_20180413183530_hasCert1_img.jpg
         * attTime : {"date":13,"day":5,"hours":18,"minutes":35,"month":3,"nanos":0,"seconds":30,"time":1523615730000,"timezoneOffset":-480,"year":118}
         * attType : 2
         * id : 158
         * mime : image/*
         * sewerageUserId : 69
         * thumPath : http://139.159.243.185:8080/img/pshFile/imgSmall/201804/20180413/jfxtiqwbj_20180413183530_hasCert1_img.jpg
         */

        private String attName;
        private String attPath;
        private HasCert8AttachmentBean.AttTimeBean attTime;
        private String attType;
        private int id;
        private String mime;
        private String sewerageUserId;
        private String thumPath;

        public String getAttName() {
            return attName;
        }

        public void setAttName(String attName) {
            this.attName = attName;
        }

        public String getAttPath() {
            return attPath;
        }

        public void setAttPath(String attPath) {
            this.attPath = attPath;
        }

        public HasCert8AttachmentBean.AttTimeBean getAttTime() {
            return attTime;
        }

        public void setAttTime(HasCert8AttachmentBean.AttTimeBean attTime) {
            this.attTime = attTime;
        }

        public String getAttType() {
            return attType;
        }

        public void setAttType(String attType) {
            this.attType = attType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMime() {
            return mime;
        }

        public void setMime(String mime) {
            this.mime = mime;
        }

        public String getSewerageUserId() {
            return sewerageUserId;
        }

        public void setSewerageUserId(String sewerageUserId) {
            this.sewerageUserId = sewerageUserId;
        }

        public String getThumPath() {
            return thumPath;
        }

        public void setThumPath(String thumPath) {
            this.thumPath = thumPath;
        }

        public static class AttTimeBean implements Serializable {
            /**
             * date : 13
             * day : 5
             * hours : 18
             * minutes : 35
             * month : 3
             * nanos : 0
             * seconds : 30
             * time : 1523615730000
             * timezoneOffset : -480
             * year : 118
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }
    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarkPersonId() {
        return markPersonId;
    }

    public void setMarkPersonId(String markPersonId) {
        this.markPersonId = markPersonId;
    }

    public String getMarkPerson() {
        return markPerson;
    }

    public void setMarkPerson(String markPerson) {
        this.markPerson = markPerson;
    }

    public long getMarkTime() {
        return markTime;
    }

    public void setMarkTime(long markTime) {
        this.markTime = markTime;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
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

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSfwh() {
        return sfwh;
    }

    public void setSfwh(String sfwh) {
        this.sfwh = sfwh;
    }

    public String getSfls() {
        return sfls;
    }

    public void setSfls(String sfls) {
        this.sfls = sfls;
    }

    public String getSzwz() {
        return szwz;
    }

    public void setSzwz(String szwz) {
        this.szwz = szwz;
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUserLocationY() {
        return userLocationY;
    }

    public void setUserLocationY(double userLocationY) {
        this.userLocationY = userLocationY;
    }

    public double getUserLocationX() {
        return userLocationX;
    }

    public void setUserLocationX(double userLocationX) {
        this.userLocationX = userLocationX;
    }

    public List<Photo> getPhotos8() {
        return photos8;
    }

    public void setPhotos8(List<Photo> photos8) {
        this.photos8 = photos8;
    }

    public String getHasCert8() {
        return hasCert8;
    }

    public void setHasCert8(String hasCert8) {
        this.hasCert8 = hasCert8;
    }

    public List<Photo> getThumbnailPhotos8() {
        return thumbnailPhotos8;
    }

    public void setThumbnailPhotos8(List<Photo> thumbnailPhotos8) {
        this.thumbnailPhotos8 = thumbnailPhotos8;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
