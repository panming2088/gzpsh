package com.augurit.agmobile.gzps.drainage_unit_monitor.model;

import retrofit2.http.Query;

public class Data {
    private String loginName;
    private String psdyId;
    private String psdyName;
    private String gyqsyhjl;
    private String aqyhjcjl;
    private String kzaqpxjl;
    private String yclssywjl;
    private String markPerson;
    private MarkTime markTime;

    public class MarkTime {

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
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getGyqsyhjl() {
        return gyqsyhjl;
    }

    public void setGyqsyhjl(String gyqsyhjl) {
        this.gyqsyhjl = gyqsyhjl;
    }

    public String getAqyhjcjl() {
        return aqyhjcjl;
    }

    public void setAqyhjcjl(String aqyhjcjl) {
        this.aqyhjcjl = aqyhjcjl;
    }

    public String getKzaqpxjl() {
        return kzaqpxjl;
    }

    public void setKzaqpxjl(String kzaqpxjl) {
        this.kzaqpxjl = kzaqpxjl;
    }

    public String getYclssywjl() {
        return yclssywjl;
    }

    public void setYclssywjl(String yclssywjl) {
        this.yclssywjl = yclssywjl;
    }

    public String getMarkPerson() {
        return markPerson;
    }

    public void setMarkPerson(String markPerson) {
        this.markPerson = markPerson;
    }

    public MarkTime getMarkTime() {
        return markTime;
    }

    public void setMarkTime(MarkTime markTime) {
        this.markTime = markTime;
    }
}
