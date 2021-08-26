package com.augurit.agmobile.gzps.drainage_unit_monitor.model;

public class PsdyWtjc {
    private String markPersonId;
    private String markPerson;
    private String jclx;
    private String wellType;
    private String parentOrgName;
    private String teamOrgId;
    private String parentOrgId;
    private String directOrgId;
    private String psdyId;
    private String id;
    private String directOrgName;
    private String jgId;
    private String ywjc;
    private String psdyName;
    private String teamOrgName;
    private String wellId;
    private MarkTime markTime;
    public void setMarkPersonId(String markPersonId) {
        this.markPersonId = markPersonId;
    }
    public String getMarkPersonId() {
        return markPersonId;
    }

    public void setMarkPerson(String markPerson) {
        this.markPerson = markPerson;
    }
    public String getMarkPerson() {
        return markPerson;
    }

    public void setJclx(String jclx) {
        this.jclx = jclx;
    }
    public String getJclx() {
        return jclx;
    }

    public void setWellType(String wellType) {
        this.wellType = wellType;
    }
    public String getWellType() {
        return wellType;
    }

    public void setParentOrgName(String parentOrgName) {
        this.parentOrgName = parentOrgName;
    }
    public String getParentOrgName() {
        return parentOrgName;
    }

    public void setTeamOrgId(String teamOrgId) {
        this.teamOrgId = teamOrgId;
    }
    public String getTeamOrgId() {
        return teamOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }
    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setDirectOrgId(String directOrgId) {
        this.directOrgId = directOrgId;
    }
    public String getDirectOrgId() {
        return directOrgId;
    }

    public void setPsdyId(String psdyId) {
        this.psdyId = psdyId;
    }
    public String getPsdyId() {
        return psdyId;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setDirectOrgName(String directOrgName) {
        this.directOrgName = directOrgName;
    }
    public String getDirectOrgName() {
        return directOrgName;
    }

    public void setJgId(String jgId) {
        this.jgId = jgId;
    }
    public String getJgId() {
        return jgId;
    }

    public void setYwjc(String ywjc) {
        this.ywjc = ywjc;
    }
    public String getYwjc() {
        return ywjc;
    }

    public void setPsdyName(String psdyName) {
        this.psdyName = psdyName;
    }
    public String getPsdyName() {
        return psdyName;
    }

    public void setTeamOrgName(String teamOrgName) {
        this.teamOrgName = teamOrgName;
    }
    public String getTeamOrgName() {
        return teamOrgName;
    }

    public void setWellId(String wellId) {
        this.wellId = wellId;
    }
    public String getWellId() {
        return wellId;
    }

    public void setMarkTime(MarkTime markTime) {
        this.markTime = markTime;
    }
    public MarkTime getMarkTime() {
        return markTime;
    }

    public static class MarkTime {

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
        public void setDate(int date) {
            this.date = date;
        }
        public int getDate() {
            return date;
        }

        public void setDay(int day) {
            this.day = day;
        }
        public int getDay() {
            return day;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }
        public int getHours() {
            return hours;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }
        public int getMinutes() {
            return minutes;
        }

        public void setMonth(int month) {
            this.month = month;
        }
        public int getMonth() {
            return month;
        }

        public void setNanos(int nanos) {
            this.nanos = nanos;
        }
        public int getNanos() {
            return nanos;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }
        public int getSeconds() {
            return seconds;
        }

        public void setTime(long time) {
            this.time = time;
        }
        public long getTime() {
            return time;
        }

        public void setTimezoneOffset(int timezoneOffset) {
            this.timezoneOffset = timezoneOffset;
        }
        public int getTimezoneOffset() {
            return timezoneOffset;
        }

        public void setYear(int year) {
            this.year = year;
        }
        public int getYear() {
            return year;
        }

    }
}
