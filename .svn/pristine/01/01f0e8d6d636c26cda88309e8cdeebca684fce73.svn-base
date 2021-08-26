package com.augurit.agmobile.gzps.drainage_unit_monitor.model;


import com.augurit.agmobile.gzpssb.monitor.model.WellMonitorInfo;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: liangsh
 * @createTime: 2021/5/8
 */
public class JbjMonitorInfoBean implements Serializable {

    private JcData jcData;
    private WtData wtData;
    private JgData jgData;

    public JcData getJcData() {
        return jcData;
    }

    public void setJcData(JcData jcData) {
        this.jcData = jcData;
    }

    public WtData getWtData() {
        return wtData;
    }

    public void setWtData(WtData wtData) {
        this.wtData = wtData;
    }

    public JgData getJgData() {
        return jgData;
    }

    public void setJgData(JgData jgData) {
        this.jgData = jgData;
    }

    public static class JcData extends WellMonitorInfo implements Serializable {
        public String jcSfwk = "0";
        public List<JbjJgListBean.Attachment> jbjJcAttachment;
        public List<JbjJgListBean.Attachment> sldJcAttachment;
        public List<JbjJgListBean.Attachment> sfdsJcAttachment;
    }

    public static class WtData extends ProblemBean implements Serializable {
        public String wtSfwk = "0";
        public String id;
        public String state;
        public String sfTj; //1为任务轮到当前登录人处理
        public Map<String, List<JbjJgListBean.Attachment>> attachments;
    }

    public static class JgData implements Serializable {

        private String loginName;
        private String psdyId;//排水单元ID
        private String psdyName;//排水单元名称
        private String wellId;//井objectId
        private String wellType;//井来源，0外围，1市排、2科排
//        private long jgsj;

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

        public String getWellId() {
            return wellId;
        }

        public void setWellId(String wellId) {
            this.wellId = wellId;
        }

        public String getWellType() {
            return wellType;
        }

        public void setWellType(String wellType) {
            this.wellType = wellType;
        }

        /*public long getJgsj() {
            return jgsj;
        }

        public void setJgsj(long jgsj) {
            this.jgsj = jgsj;
        }*/
    }
}
