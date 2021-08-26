package com.augurit.agmobile.gzps.drainage_unit_monitor.model;

import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemBean;

import java.io.Serializable;

public class JbjMonitorArg implements Serializable {

    public String subtype = ""; //接驳井、接户井
    public String jbjType = ""; //雨水、污水、雨污合流
    public String psdyId; //排水单元ID
    public String psdyName; //排水单元名称
    public String wellType; //井来源，0外围、1市排、2科排
    public String usid;//接驳井usid
    public String jbjObjectId;//接驳井objectid
    public String X, Y; //接驳井所在的x、y坐标
    public String addr;
    //    private String gj;
//    private boolean subtype;
    public String reportType; //AMFindResult中的REPORT_TYPE

    public JbjMonitorInfoBean jbjMonitorInfoBean;

    public JbjMonitorInfoBean.WtData wtData;

    public boolean readOnly = false; //界面只读

    public int checkType = 1; //1地面检查，2开盖检查，3立管检查
}
