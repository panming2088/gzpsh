package com.augurit.agmobile.gzpssb.uploadevent.model;

import com.augurit.agmobile.gzps.uploadevent.model.EventDetail;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liangsh on 2017/11/17.
 */

public class PshEventAffair implements Serializable{


    /**
     * handling : 32
     * finished : 8
     * list : [{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":985,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"测试1","sbsj":{"date":15,"day":3,"hours":15,"minutes":59,"month":10,"nanos":0,"seconds":6,"time":1510732746000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":986,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"了解","sbsj":{"date":15,"day":3,"hours":16,"minutes":1,"month":10,"nanos":0,"seconds":30,"time":1510732890000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":987,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"444","sbsj":{"date":15,"day":3,"hours":16,"minutes":1,"month":10,"nanos":0,"seconds":36,"time":1510732896000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":988,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"测试","sbsj":{"date":15,"day":3,"hours":16,"minutes":1,"month":10,"nanos":0,"seconds":44,"time":1510732904000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":989,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"测试2017115","sbsj":{"date":15,"day":3,"hours":16,"minutes":20,"month":10,"nanos":0,"seconds":2,"time":1510734002000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"1234","code":"111","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":990,"jdmc":"123","jjcd":"2","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"奥格","sbsj":{"date":15,"day":3,"hours":16,"minutes":57,"month":10,"nanos":0,"seconds":1,"time":1510736221000,"timezoneOffset":-480,"year":117},"sslx":"1","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"12","x":"","y":"","yjgcl":"12"},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":991,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"测试","sbsj":{"date":15,"day":3,"hours":17,"minutes":13,"month":10,"nanos":0,"seconds":41,"time":1510737221000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":992,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"奥格4","sbsj":{"date":15,"day":3,"hours":17,"minutes":32,"month":10,"nanos":0,"seconds":23,"time":1510738343000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""}]
     */

    private int handling;
    private int finished;
    private List<PSHEventListItem> list;

    public int getHandling() {
        return handling;
    }

    public void setHandling(int handling) {
        this.handling = handling;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public List<PSHEventListItem> getList() {
        return list;
    }

    public void setList(List<PSHEventListItem> list) {
        this.list = list;
    }
}
