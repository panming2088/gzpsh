package com.augurit.agmobile.gzpssb.uploadfacility.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.augurit.agmobile.gzps.uploadfacility.model.ApprovalOpinion;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.liteorm.db.annotation.Ignore;
import com.augurit.am.fw.db.liteorm.db.annotation.PrimaryKey;
import com.augurit.am.fw.db.liteorm.db.enums.AssignType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 设施新增实体类
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.identification.model
 * @createTime 创建时间 ：17/11/3
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/11/3
 * @modifyMemo 修改备注：
 */

public class NWUploadedFacility implements  Serializable, Parcelable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private long dbid;
    /**
     * addr : 雨水口
     * attrFive :
     * attrFour :
     * attrOne : 水封井
     * attrThree : 侧入
     * attrTwo : 雨污合流
     * componentType : 天河区南山路华南理工大学内,南秀村-37栋附近18米附近
     * description : 测试
     * directOrgId : 1081
     * directOrgName : 黄埔区市政建设有限公司
     * id : 62
     * markPerson : 钟无鑫
     * markPersonId : zhongwx
     * markTime : {"date":13,"day":1,"hours":0,"minutes":13,"month":10,"nanos":0,"seconds":20,"time":1510503200000,"timezoneOffset":-480,"year":117}
     * parentOrgId : 1068
     * parentOrgName : 黄埔区水务局
     * road :
     * superviseOrgId :
     * superviseOrgName :
     * teamOrgId : 1126
     * teamOrgName : 养护1班组
     * updateTime : null
     * x : 113.33803229
     * y : 23.14962483
     */
    @Expose
    private Long time;//保存草稿的时间，用于列表的顺序
    private String fieldName;
    private String loginName;
    private String addr;
    private String yjAttrFive;
    private String yjAttrFour;
    private String yjAttrOne;
    private String yjAttrThree;
    private String yjAttrTwo;
    private String yjComb;
    private int yjCombId;
    private String componentType;
    private String description;
    private String directOrgId;
    private String directOrgName;
    private String state;//设施状态
    private String objectId;
    private String yjType;
    /**
     * 唯一标识，由服务端生成并返回
     */
    private Long id;
    private String markPerson;
    private String markPersonId;
    private String userId;
    private Long markTime;
    private String parentOrgId;
    private String parentOrgName;
    private String road;
    private String currentArea;
    private String currentTown;
    private String currentVillage;
    private String burgOrgId;
    private Long updateTime;
    private double x;
    private double y;

    /**
     * 是否五倍距离巡检设施
     */
    private String isSpecial;

    /**
     * 上报类型名称
     */
    private String collectType;
    /**
     * 上报类型编码
     */
    private String reportType;


    /**
     * 农污数据上报类型
     */
    private String checkType;


    /**
     * 设施点属性
     */
    private String cmbNumber;//设施点编号
    private String cmbName; //设施点名称
    /**
     * 设施处理水量
     */
    private String cmbDesWater;
    /**
     * 处理工艺
     */
    private String cmbTreatProce;
    /**
     * 投入运行时间
     */
    private String cmbRunTime;
    /**
     * 设计出水标准
     */
    private String cmbStandard;
    /**
     * 街道办责任人
     */
    private String cmbStreetPeople;
    private String cmbStreetPeoplePhone;
    private String cmbVillPeople;
    private String cmbVillPeoplePhone;
    /**
     * 维管单位
     */
    private String cmbMaUnit;
    private String cmbMaUnitPeople;
    private String cmbMaUnitPhone;
    /**
     * 占地面积
     */
    private String cmbCovArea;

    /**
     * 管网长度
     */
    private String gwcd;

    /**
     * 接入检查井数量
     */
    private String jrjcjs;
    /**
     * 有无动力
     */
    private String cmbIsDyn;

    /**
     * 审核状态
     */
    private String checkState;
    private String checkPersonId;
    private String checkPerson;
    private String checkDesription;
    /**
     * 由于当请求图片地址后会出现顺序乱的情况，所以加入这个字段进行维护原有顺序
     */
    @Ignore
    private int order;

    private String attachmentIds;
    private List<AttachmentEntity> attachment;
    private List<Photo> photos;
    private List<Photo> thumbnailPhotos;
    private String layerUrl;
    private int layerId;
    private String layerName;
    private String usid;
    /**
     * 是否有跟部件绑定，如果有跟部件进行绑定，说明是数据确认，否则说明是数据新增；
     * 有两种数值：1: 表示跟部件进行了绑定，是数据确认，其他则是数据新增。
     */
    private int isBinding;

    /**
     * 用户当前所处的位置
     */
    private String userAddr;

    /**
     * 用户当前位置的经度
     */
    @SerializedName("userX")
    private double userLocationX;

    /**
     * 用户当前位置的纬度
     */
    @SerializedName("userY")
    private double userLocationY;

    /**
     * 再次编辑时删除的图片id
     */
    @Expose
    public List<String> deletedPhotoIds;

    /**
     * 审批意见
     */
    private List<ApprovalOpinion> approvalOpinions;

    /**
     * 附属设施
     */
    private List<FacilitiesBean> facilitiesBeen;

//    /**
//     * 大类编码 ， 用","自行分割
//     */
//    @SerializedName("pcode")
//    private String pCode;
//
//    /**
//     * 小类编码 ，用","自行分割
//     */
//    private String childCode;

//    private String burgOrgName;//村的名字
//    private String burgOrgId;//村的ID

//    public String getBurgOrgName() {
//        return burgOrgName;
//    }
//
//    public void setBurgOrgName(String burgOrgName) {
//        this.burgOrgName = burgOrgName;
//    }
//
//    public String getBurgOrgId() {
//        return burgOrgId;
//    }
//
//    public void setBurgOrgId(String burgOrgId) {
//        this.burgOrgId = burgOrgId;
//    }


    public String getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(String isSpecial) {
        this.isSpecial = isSpecial;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getYjAttrFive() {
        return yjAttrFive;
    }

    public void setYjAttrFive(String yjAttrFive) {
        this.yjAttrFive = yjAttrFive;
    }

    public String getYjAttrFour() {
        return yjAttrFour;
    }

    public void setYjAttrFour(String yjAttrFour) {
        this.yjAttrFour = yjAttrFour;
    }

    public String getYjAttrOne() {
        return yjAttrOne;
    }

    public void setYjAttrOne(String yjAttrOne) {
        this.yjAttrOne = yjAttrOne;
    }


    public String getYjType() {
        return yjType;
    }

    public void setYjType(String yjType) {
        this.yjType = yjType;
    }

    public String getYjAttrThree() {
        return yjAttrThree;
    }

    public void setYjAttrThree(String yjAttrThree) {
        this.yjAttrThree = yjAttrThree;
    }

    public String getYjAttrTwo() {
        return yjAttrTwo;
    }

    public void setYjAttrTwo(String yjAttrTwo) {
        this.yjAttrTwo = yjAttrTwo;
    }

    public String getYjComb() {
        return yjComb;
    }

    public void setYjComb(String yjComb) {
        this.yjComb = yjComb;
    }

    public int getYjCombId() {
        return yjCombId;
    }

    public void setYjCombId(int yjCombId) {
        this.yjCombId = yjCombId;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarkPerson() {
        return markPerson;
    }

    public void setMarkPerson(String markPerson) {
        this.markPerson = markPerson;
    }

    public String getMarkPersonId() {
        return markPersonId;
    }

    public void setMarkPersonId(String markPersonId) {
        this.markPersonId = markPersonId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Long markTime) {
        this.markTime = markTime;
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

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(String currentArea) {
        this.currentArea = currentArea;
    }

    public String getCurrentTown() {
        return currentTown;
    }

    public void setCurrentTown(String currentTown) {
        this.currentTown = currentTown;
    }

    public String getCurrentVillage() {
        return currentVillage;
    }

    public void setCurrentVillage(String currentVillage) {
        this.currentVillage = currentVillage;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
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

    public String getCollectType() {
        return collectType;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCmbNumber() {
        return cmbNumber;
    }

    public void setCmbNumber(String cmbNumber) {
        this.cmbNumber = cmbNumber;
    }

    public String getCmbName() {
        return cmbName;
    }

    public void setCmbName(String cmbName) {
        this.cmbName = cmbName;
    }

    public String getCmbDesWater() {
        return cmbDesWater;
    }

    public void setCmbDesWater(String cmbDesWater) {
        this.cmbDesWater = cmbDesWater;
    }

    public String getCmbTreatProce() {
        return cmbTreatProce;
    }

    public void setCmbTreatProce(String cmbTreatProce) {
        this.cmbTreatProce = cmbTreatProce;
    }

    public String getCmbRunTime() {
        return cmbRunTime;
    }

    public void setCmbRunTime(String cmbRunTime) {
        this.cmbRunTime = cmbRunTime;
    }

    public String getCmbStandard() {
        return cmbStandard;
    }

    public void setCmbStandard(String cmbStandard) {
        this.cmbStandard = cmbStandard;
    }

    public String getCmbStreetPeople() {
        return cmbStreetPeople;
    }

    public void setCmbStreetPeople(String cmbStreetPeople) {
        this.cmbStreetPeople = cmbStreetPeople;
    }

    public String getCmbStreetPeoplePhone() {
        return cmbStreetPeoplePhone;
    }

    public void setCmbStreetPeoplePhone(String cmbStreetPeoplePhone) {
        this.cmbStreetPeoplePhone = cmbStreetPeoplePhone;
    }

    public String getCmbVillPeople() {
        return cmbVillPeople;
    }

    public void setCmbVillPeople(String cmbVillPeople) {
        this.cmbVillPeople = cmbVillPeople;
    }

    public String getCmbVillPeoplePhone() {
        return cmbVillPeoplePhone;
    }

    public void setCmbVillPeoplePhone(String cmbVillPeoplePhone) {
        this.cmbVillPeoplePhone = cmbVillPeoplePhone;
    }

    public String getCmbMaUnit() {
        return cmbMaUnit;
    }

    public void setCmbMaUnit(String cmbMaUnit) {
        this.cmbMaUnit = cmbMaUnit;
    }

    public String getCmbMaUnitPeople() {
        return cmbMaUnitPeople;
    }

    public void setCmbMaUnitPeople(String cmbMaUnitPeople) {
        this.cmbMaUnitPeople = cmbMaUnitPeople;
    }

    public String getCmbMaUnitPhone() {
        return cmbMaUnitPhone;
    }

    public void setCmbMaUnitPhone(String cmbMaUnitPhone) {
        this.cmbMaUnitPhone = cmbMaUnitPhone;
    }

    public String getCmbCovArea() {
        return cmbCovArea;
    }

    public void setCmbCovArea(String cmbCovArea) {
        this.cmbCovArea = cmbCovArea;
    }

    public String getCmbIsDyn() {
        return cmbIsDyn;
    }

    public void setCmbIsDyn(String cmbIsDyn) {
        this.cmbIsDyn = cmbIsDyn;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCheckPersonId() {
        return checkPersonId;
    }

    public void setCheckPersonId(String checkPersonId) {
        this.checkPersonId = checkPersonId;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public String getCheckDesription() {
        return checkDesription;
    }

    public void setCheckDesription(String checkDesription) {
        this.checkDesription = checkDesription;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(String attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public List<AttachmentEntity> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<AttachmentEntity> attachment) {
        this.attachment = attachment;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Photo> getThumbnailPhotos() {
        return thumbnailPhotos;
    }

    public void setThumbnailPhotos(List<Photo> thumbnailPhotos) {
        this.thumbnailPhotos = thumbnailPhotos;
    }

    public String getLayerUrl() {
        return layerUrl;
    }

    public void setLayerUrl(String layerUrl) {
        this.layerUrl = layerUrl;
    }

    public int getLayerId() {
        return layerId;
    }

    public void setLayerId(int layerId) {
        this.layerId = layerId;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public int getIsBinding() {
        return isBinding;
    }

    public void setIsBinding(int isBinding) {
        this.isBinding = isBinding;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public double getUserLocationX() {
        return userLocationX;
    }

    public void setUserLocationX(double userLocationX) {
        this.userLocationX = userLocationX;
    }

    public double getUserLocationY() {
        return userLocationY;
    }

    public void setUserLocationY(double userLocationY) {
        this.userLocationY = userLocationY;
    }

    public List<String> getDeletedPhotoIds() {
        return deletedPhotoIds;
    }

    public void setDeletedPhotoIds(List<String> deletedPhotoIds) {
        this.deletedPhotoIds = deletedPhotoIds;
    }

    public List<ApprovalOpinion> getApprovalOpinions() {
        return approvalOpinions;
    }

    public void setApprovalOpinions(List<ApprovalOpinion> approvalOpinions) {
        this.approvalOpinions = approvalOpinions;
    }

    public List<FacilitiesBean> getFacilitiesBeen() {
        return facilitiesBeen;
    }

    public void setFacilitiesBeen(List<FacilitiesBean> facilitiesBeen) {
        this.facilitiesBeen = facilitiesBeen;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getGwcd() {
        return gwcd;
    }

    public void setGwcd(String gwcd) {
        this.gwcd = gwcd;
    }

    public String getJrjcjs() {
        return jrjcjs;
    }

    public void setJrjcjs(String jrjcjs) {
        this.jrjcjs = jrjcjs;
    }

    public String getBurgOrgId() {
        return burgOrgId;
    }

    public void setBurgOrgId(String burgOrgId) {
        this.burgOrgId = burgOrgId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.dbid);
        dest.writeValue(this.time);
        dest.writeString(this.fieldName);
        dest.writeString(this.loginName);
        dest.writeString(this.addr);
        dest.writeString(this.yjAttrFive);
        dest.writeString(this.yjAttrFour);
        dest.writeString(this.yjAttrOne);
        dest.writeString(this.yjAttrThree);
        dest.writeString(this.yjAttrTwo);
        dest.writeString(this.yjComb);
        dest.writeInt(this.yjCombId);
        dest.writeString(this.componentType);
        dest.writeString(this.description);
        dest.writeString(this.directOrgId);
        dest.writeString(this.directOrgName);
        dest.writeString(this.state);
        dest.writeString(this.objectId);
        dest.writeString(this.yjType);
        dest.writeValue(this.id);
        dest.writeString(this.markPerson);
        dest.writeString(this.markPersonId);
        dest.writeString(this.userId);
        dest.writeValue(this.markTime);
        dest.writeString(this.parentOrgId);
        dest.writeString(this.parentOrgName);
        dest.writeString(this.road);
        dest.writeString(this.currentArea);
        dest.writeString(this.currentTown);
        dest.writeString(this.currentVillage);
        dest.writeString(this.burgOrgId);
        dest.writeValue(this.updateTime);
        dest.writeDouble(this.x);
        dest.writeDouble(this.y);
        dest.writeString(this.isSpecial);
        dest.writeString(this.collectType);
        dest.writeString(this.reportType);
        dest.writeString(this.checkType);
        dest.writeString(this.cmbNumber);
        dest.writeString(this.cmbName);
        dest.writeString(this.cmbDesWater);
        dest.writeString(this.cmbTreatProce);
        dest.writeString(this.cmbRunTime);
        dest.writeString(this.cmbStandard);
        dest.writeString(this.cmbStreetPeople);
        dest.writeString(this.cmbStreetPeoplePhone);
        dest.writeString(this.cmbVillPeople);
        dest.writeString(this.cmbVillPeoplePhone);
        dest.writeString(this.cmbMaUnit);
        dest.writeString(this.cmbMaUnitPeople);
        dest.writeString(this.cmbMaUnitPhone);
        dest.writeString(this.cmbCovArea);
        dest.writeString(this.gwcd);
        dest.writeString(this.jrjcjs);
        dest.writeString(this.cmbIsDyn);
        dest.writeString(this.checkState);
        dest.writeString(this.checkPersonId);
        dest.writeString(this.checkPerson);
        dest.writeString(this.checkDesription);
        dest.writeInt(this.order);
        dest.writeString(this.attachmentIds);
        dest.writeTypedList(this.attachment);
        dest.writeList(this.photos);
        dest.writeList(this.thumbnailPhotos);
        dest.writeString(this.layerUrl);
        dest.writeInt(this.layerId);
        dest.writeString(this.layerName);
        dest.writeString(this.usid);
        dest.writeInt(this.isBinding);
        dest.writeString(this.userAddr);
        dest.writeDouble(this.userLocationX);
        dest.writeDouble(this.userLocationY);
        dest.writeStringList(this.deletedPhotoIds);
        dest.writeTypedList(this.approvalOpinions);
        dest.writeTypedList(this.facilitiesBeen);
    }

    public NWUploadedFacility() {
    }

    protected NWUploadedFacility(Parcel in) {
        this.dbid = in.readLong();
        this.time = (Long) in.readValue(Long.class.getClassLoader());
        this.fieldName = in.readString();
        this.loginName = in.readString();
        this.addr = in.readString();
        this.yjAttrFive = in.readString();
        this.yjAttrFour = in.readString();
        this.yjAttrOne = in.readString();
        this.yjAttrThree = in.readString();
        this.yjAttrTwo = in.readString();
        this.yjComb = in.readString();
        this.yjCombId = in.readInt();
        this.componentType = in.readString();
        this.description = in.readString();
        this.directOrgId = in.readString();
        this.directOrgName = in.readString();
        this.state = in.readString();
        this.objectId = in.readString();
        this.yjType = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.markPerson = in.readString();
        this.markPersonId = in.readString();
        this.userId = in.readString();
        this.markTime = (Long) in.readValue(Long.class.getClassLoader());
        this.parentOrgId = in.readString();
        this.parentOrgName = in.readString();
        this.road = in.readString();
        this.currentArea = in.readString();
        this.currentTown = in.readString();
        this.currentVillage = in.readString();
        this.burgOrgId = in.readString();
        this.updateTime = (Long) in.readValue(Long.class.getClassLoader());
        this.x = in.readDouble();
        this.y = in.readDouble();
        this.isSpecial = in.readString();
        this.collectType = in.readString();
        this.reportType = in.readString();
        this.checkType = in.readString();
        this.cmbNumber = in.readString();
        this.cmbName = in.readString();
        this.cmbDesWater = in.readString();
        this.cmbTreatProce = in.readString();
        this.cmbRunTime = in.readString();
        this.cmbStandard = in.readString();
        this.cmbStreetPeople = in.readString();
        this.cmbStreetPeoplePhone = in.readString();
        this.cmbVillPeople = in.readString();
        this.cmbVillPeoplePhone = in.readString();
        this.cmbMaUnit = in.readString();
        this.cmbMaUnitPeople = in.readString();
        this.cmbMaUnitPhone = in.readString();
        this.cmbCovArea = in.readString();
        this.gwcd = in.readString();
        this.jrjcjs = in.readString();
        this.cmbIsDyn = in.readString();
        this.checkState = in.readString();
        this.checkPersonId = in.readString();
        this.checkPerson = in.readString();
        this.checkDesription = in.readString();
        this.order = in.readInt();
        this.attachmentIds = in.readString();
        this.attachment = in.createTypedArrayList(AttachmentEntity.CREATOR);
        this.photos = new ArrayList<Photo>();
        in.readList(this.photos, Photo.class.getClassLoader());
        this.thumbnailPhotos = new ArrayList<Photo>();
        in.readList(this.thumbnailPhotos, Photo.class.getClassLoader());
        this.layerUrl = in.readString();
        this.layerId = in.readInt();
        this.layerName = in.readString();
        this.usid = in.readString();
        this.isBinding = in.readInt();
        this.userAddr = in.readString();
        this.userLocationX = in.readDouble();
        this.userLocationY = in.readDouble();
        this.deletedPhotoIds = in.createStringArrayList();
        this.approvalOpinions = in.createTypedArrayList(ApprovalOpinion.CREATOR);
        this.facilitiesBeen = in.createTypedArrayList(FacilitiesBean.CREATOR);
    }

    public static final Creator<NWUploadedFacility> CREATOR = new Creator<NWUploadedFacility>() {
        @Override
        public NWUploadedFacility createFromParcel(Parcel source) {
            return new NWUploadedFacility(source);
        }

        @Override
        public NWUploadedFacility[] newArray(int size) {
            return new NWUploadedFacility[size];
        }
    };
}
