package com.augurit.agmobile.gzpssb.journal.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名：com.augurit.agmobile.gzpssb.journal.model
 * 类描述：
 * 创建人：luobiao
 * 创建时间：2018/12/19 10:20
 * 修改人：luobiao
 * 修改时间：2018/12/19 10:20
 * 修改备注：
 */
public class PSHJournal implements Parcelable {
    /**
     * mph : null
     * reportType : null
     * superviseOrgId : null
     * usery : null
     * originRoad : null
     * checkPerson : null
     * sGuid : null
     * userx : null
     * addr : null
     * checkTime : null
     * originAttrTwo : null
     * waterLevel : null
     * description : null
     * originAttrFive : null
     * checkState : null
     * checkPersonId : null
     * addrLatest : null
     * updateTime : null
     * attrTwo : null
     * parentOrgId : 1063
     * directOrgId : null
     * originx : null
     * originy : null
     * pcode : null
     * originAttrOne : null
     * teamMember : null
     * recordTime : 1545384062000
     * userAddr : null
     * layerId : null
     * checkDesription : null
     * attrThree : null
     * pshName : null
     * layerUrl : null
     * coox : null
     * cooy : null
     * originAddr : null
     * noneMph : null
     * writerName : 技术支持2
     * parentOrgName : 广州市水务局
     * layerName : null
     * road : null
     * attrFour : null
     * directOrgName : null
     * id : 4
     * objectId : null
     * teamOrgName : null
     * pshId : null
     * writerId : Augur2
     * attrOne : null
     * isAddFeature : null
     * teamOrgId : null
     * noneInputMph : null
     * attrFive : null
     * originAttrFour : null
     * childCode : null
     * personUserId : null
     * originAttrThree : null
     * correctType : null
     * cityVillage : null
     * y : null
     * superviseOrgName : null
     * x : null
     * usid : null
     */

    private String mph;
    private String reportType;
    private String superviseOrgId;
    private String originRoad;
    private String checkPerson;
    private String sGuid;
    private String addr;
    private Long checkTime;
    private String originAttrTwo;
    private String waterLevel;
    private String description;
    private String originAttrFive;
    private String checkState;
    private String checkPersonId;
    private String addrLatest;
    private Long updateTime;
    private String attrTwo;
    private String parentOrgId;
    private String directOrgId;
    private String originx;
    private String originy;
    private String pcode;
    private String originAttrOne;
    private String teamMember;
    private Long recordTime;
    private String userAddr;
    private String checkDesription;
    private String attrThree;
    private String pshName;
    private String originAddr;
    private String noneMph;
    private String writerName;
    private String parentOrgName;
    private String road;
    private String attrFour;
    private String directOrgName;
    private Long id;
    private String objectId;
    private String teamOrgName;
    private Long pshId;
    private String writerId;
    private String attrOne;
    private String teamOrgId;
    private String noneInputMph;
    private String attrFive;
    private String originAttrFour;
    private String childCode;
    private String personUserId;
    private String originAttrThree;
    private double y;
    private String superviseOrgName;
    private double x;
    private String loginName;
    private String zgjy;
    private int order;//排列顺序用的
    private String attachment;//删除的附件
    private List<Photo> photos;
    private List<Photo> thumbnailPhotos;

    public List<String> deletedPhotoIds;

    public String getDeletedPhotoIds(){

        if(ListUtil.isEmpty(deletedPhotoIds)){
            return null;
        }
        String ids = "";
        for (int i = 0; i < deletedPhotoIds.size() ; i ++){
            if (i == deletedPhotoIds.size() - 1){
                ids  += deletedPhotoIds.get(i);
            }else {
                ids  += deletedPhotoIds.get(i) + ",";
            }
        }
        return ids;
    }

    public String getMph() {
        return mph;
    }

    public void setMph(String mph) {
        this.mph = mph;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getSuperviseOrgId() {
        return superviseOrgId;
    }

    public void setSuperviseOrgId(String superviseOrgId) {
        this.superviseOrgId = superviseOrgId;
    }

    public String getOriginRoad() {
        return originRoad;
    }

    public void setOriginRoad(String originRoad) {
        this.originRoad = originRoad;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public String getsGuid() {
        return sGuid;
    }

    public void setsGuid(String sGuid) {
        this.sGuid = sGuid;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Long getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Long checkTime) {
        this.checkTime = checkTime;
    }

    public String getOriginAttrTwo() {
        return originAttrTwo;
    }

    public void setOriginAttrTwo(String originAttrTwo) {
        this.originAttrTwo = originAttrTwo;
    }

    public String getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginAttrFive() {
        return originAttrFive;
    }

    public void setOriginAttrFive(String originAttrFive) {
        this.originAttrFive = originAttrFive;
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

    public String getAddrLatest() {
        return addrLatest;
    }

    public void setAddrLatest(String addrLatest) {
        this.addrLatest = addrLatest;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getAttrTwo() {
        return attrTwo;
    }

    public void setAttrTwo(String attrTwo) {
        this.attrTwo = attrTwo;
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public String getDirectOrgId() {
        return directOrgId;
    }

    public void setDirectOrgId(String directOrgId) {
        this.directOrgId = directOrgId;
    }

    public String getOriginx() {
        return originx;
    }

    public void setOriginx(String originx) {
        this.originx = originx;
    }

    public String getOriginy() {
        return originy;
    }

    public void setOriginy(String originy) {
        this.originy = originy;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getOriginAttrOne() {
        return originAttrOne;
    }

    public void setOriginAttrOne(String originAttrOne) {
        this.originAttrOne = originAttrOne;
    }

    public String getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(String teamMember) {
        this.teamMember = teamMember;
    }

    public Long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Long recordTime) {
        this.recordTime = recordTime;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getCheckDesription() {
        return checkDesription;
    }

    public void setCheckDesription(String checkDesription) {
        this.checkDesription = checkDesription;
    }

    public String getAttrThree() {
        return attrThree;
    }

    public void setAttrThree(String attrThree) {
        this.attrThree = attrThree;
    }

    public String getPshName() {
        return pshName;
    }

    public void setPshName(String pshName) {
        this.pshName = pshName;
    }

    public String getOriginAddr() {
        return originAddr;
    }

    public void setOriginAddr(String originAddr) {
        this.originAddr = originAddr;
    }

    public String getNoneMph() {
        return noneMph;
    }

    public void setNoneMph(String noneMph) {
        this.noneMph = noneMph;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
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

    public String getAttrFour() {
        return attrFour;
    }

    public void setAttrFour(String attrFour) {
        this.attrFour = attrFour;
    }

    public String getDirectOrgName() {
        return directOrgName;
    }

    public void setDirectOrgName(String directOrgName) {
        this.directOrgName = directOrgName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTeamOrgName() {
        return teamOrgName;
    }

    public void setTeamOrgName(String teamOrgName) {
        this.teamOrgName = teamOrgName;
    }

    public Long getPshId() {
        return pshId;
    }

    public void setPshId(Long pshId) {
        this.pshId = pshId;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getAttrOne() {
        return attrOne;
    }

    public void setAttrOne(String attrOne) {
        this.attrOne = attrOne;
    }

    public String getTeamOrgId() {
        return teamOrgId;
    }

    public void setTeamOrgId(String teamOrgId) {
        this.teamOrgId = teamOrgId;
    }

    public String getNoneInputMph() {
        return noneInputMph;
    }

    public void setNoneInputMph(String noneInputMph) {
        this.noneInputMph = noneInputMph;
    }

    public String getAttrFive() {
        return attrFive;
    }

    public void setAttrFive(String attrFive) {
        this.attrFive = attrFive;
    }

    public String getOriginAttrFour() {
        return originAttrFour;
    }

    public void setOriginAttrFour(String originAttrFour) {
        this.originAttrFour = originAttrFour;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public String getPersonUserId() {
        return personUserId;
    }

    public void setPersonUserId(String personUserId) {
        this.personUserId = personUserId;
    }

    public String getOriginAttrThree() {
        return originAttrThree;
    }

    public void setOriginAttrThree(String originAttrThree) {
        this.originAttrThree = originAttrThree;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getSuperviseOrgName() {
        return superviseOrgName;
    }

    public void setSuperviseOrgName(String superviseOrgName) {
        this.superviseOrgName = superviseOrgName;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
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

    public void setDeletedPhotoIds(List<String> deletedPhotoIds) {
        this.deletedPhotoIds = deletedPhotoIds;
    }

    public String getZgjy() {
        return zgjy;
    }

    public void setZgjy(String zgjy) {
        this.zgjy = zgjy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mph);
        dest.writeString(this.reportType);
        dest.writeString(this.superviseOrgId);
        dest.writeString(this.originRoad);
        dest.writeString(this.checkPerson);
        dest.writeString(this.sGuid);
        dest.writeString(this.addr);
        dest.writeValue(this.checkTime);
        dest.writeString(this.originAttrTwo);
        dest.writeString(this.waterLevel);
        dest.writeString(this.description);
        dest.writeString(this.originAttrFive);
        dest.writeString(this.checkState);
        dest.writeString(this.checkPersonId);
        dest.writeString(this.addrLatest);
        dest.writeValue(this.updateTime);
        dest.writeString(this.attrTwo);
        dest.writeString(this.parentOrgId);
        dest.writeString(this.directOrgId);
        dest.writeString(this.originx);
        dest.writeString(this.originy);
        dest.writeString(this.pcode);
        dest.writeString(this.originAttrOne);
        dest.writeString(this.teamMember);
        dest.writeValue(this.recordTime);
        dest.writeString(this.userAddr);
        dest.writeString(this.checkDesription);
        dest.writeString(this.attrThree);
        dest.writeString(this.pshName);
        dest.writeString(this.originAddr);
        dest.writeString(this.noneMph);
        dest.writeString(this.writerName);
        dest.writeString(this.parentOrgName);
        dest.writeString(this.road);
        dest.writeString(this.attrFour);
        dest.writeString(this.directOrgName);
        dest.writeValue(this.id);
        dest.writeString(this.objectId);
        dest.writeString(this.teamOrgName);
        dest.writeValue(this.pshId);
        dest.writeString(this.writerId);
        dest.writeString(this.attrOne);
        dest.writeString(this.teamOrgId);
        dest.writeString(this.noneInputMph);
        dest.writeString(this.attrFive);
        dest.writeString(this.originAttrFour);
        dest.writeString(this.childCode);
        dest.writeString(this.personUserId);
        dest.writeString(this.originAttrThree);
        dest.writeDouble(this.y);
        dest.writeString(this.superviseOrgName);
        dest.writeDouble(this.x);
        dest.writeString(this.loginName);
        dest.writeString(this.zgjy);
        dest.writeInt(this.order);
        dest.writeString(this.attachment);
        dest.writeList(this.photos);
        dest.writeList(this.thumbnailPhotos);
        dest.writeStringList(this.deletedPhotoIds);
    }

    public void readFromParcel(Parcel source) {
        this.mph = source.readString();
        this.reportType = source.readString();
        this.superviseOrgId = source.readString();
        this.originRoad = source.readString();
        this.checkPerson = source.readString();
        this.sGuid = source.readString();
        this.addr = source.readString();
        this.checkTime = (Long) source.readValue(Long.class.getClassLoader());
        this.originAttrTwo = source.readString();
        this.waterLevel = source.readString();
        this.description = source.readString();
        this.originAttrFive = source.readString();
        this.checkState = source.readString();
        this.checkPersonId = source.readString();
        this.addrLatest = source.readString();
        this.updateTime = (Long) source.readValue(Long.class.getClassLoader());
        this.attrTwo = source.readString();
        this.parentOrgId = source.readString();
        this.directOrgId = source.readString();
        this.originx = source.readString();
        this.originy = source.readString();
        this.pcode = source.readString();
        this.originAttrOne = source.readString();
        this.teamMember = source.readString();
        this.recordTime = (Long) source.readValue(Long.class.getClassLoader());
        this.userAddr = source.readString();
        this.checkDesription = source.readString();
        this.attrThree = source.readString();
        this.pshName = source.readString();
        this.originAddr = source.readString();
        this.noneMph = source.readString();
        this.writerName = source.readString();
        this.parentOrgName = source.readString();
        this.road = source.readString();
        this.attrFour = source.readString();
        this.directOrgName = source.readString();
        this.id = (Long) source.readValue(Long.class.getClassLoader());
        this.objectId = source.readString();
        this.teamOrgName = source.readString();
        this.pshId = (Long) source.readValue(Long.class.getClassLoader());
        this.writerId = source.readString();
        this.attrOne = source.readString();
        this.teamOrgId = source.readString();
        this.noneInputMph = source.readString();
        this.attrFive = source.readString();
        this.originAttrFour = source.readString();
        this.childCode = source.readString();
        this.personUserId = source.readString();
        this.originAttrThree = source.readString();
        this.y = source.readDouble();
        this.superviseOrgName = source.readString();
        this.x = source.readDouble();
        this.loginName = source.readString();
        this.zgjy = source.readString();
        this.order = source.readInt();
        this.attachment = source.readString();
        this.photos = new ArrayList<Photo>();
        source.readList(this.photos, Photo.class.getClassLoader());
        this.thumbnailPhotos = new ArrayList<Photo>();
        source.readList(this.thumbnailPhotos, Photo.class.getClassLoader());
        this.deletedPhotoIds = source.createStringArrayList();
    }

    public PSHJournal() {
    }

    protected PSHJournal(Parcel in) {
        this.mph = in.readString();
        this.reportType = in.readString();
        this.superviseOrgId = in.readString();
        this.originRoad = in.readString();
        this.checkPerson = in.readString();
        this.sGuid = in.readString();
        this.addr = in.readString();
        this.checkTime = (Long) in.readValue(Long.class.getClassLoader());
        this.originAttrTwo = in.readString();
        this.waterLevel = in.readString();
        this.description = in.readString();
        this.originAttrFive = in.readString();
        this.checkState = in.readString();
        this.checkPersonId = in.readString();
        this.addrLatest = in.readString();
        this.updateTime = (Long) in.readValue(Long.class.getClassLoader());
        this.attrTwo = in.readString();
        this.parentOrgId = in.readString();
        this.directOrgId = in.readString();
        this.originx = in.readString();
        this.originy = in.readString();
        this.pcode = in.readString();
        this.originAttrOne = in.readString();
        this.teamMember = in.readString();
        this.recordTime = (Long) in.readValue(Long.class.getClassLoader());
        this.userAddr = in.readString();
        this.checkDesription = in.readString();
        this.attrThree = in.readString();
        this.pshName = in.readString();
        this.originAddr = in.readString();
        this.noneMph = in.readString();
        this.writerName = in.readString();
        this.parentOrgName = in.readString();
        this.road = in.readString();
        this.attrFour = in.readString();
        this.directOrgName = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.objectId = in.readString();
        this.teamOrgName = in.readString();
        this.pshId = (Long) in.readValue(Long.class.getClassLoader());
        this.writerId = in.readString();
        this.attrOne = in.readString();
        this.teamOrgId = in.readString();
        this.noneInputMph = in.readString();
        this.attrFive = in.readString();
        this.originAttrFour = in.readString();
        this.childCode = in.readString();
        this.personUserId = in.readString();
        this.originAttrThree = in.readString();
        this.y = in.readDouble();
        this.superviseOrgName = in.readString();
        this.x = in.readDouble();
        this.loginName = in.readString();
        this.zgjy = in.readString();
        this.order = in.readInt();
        this.attachment = in.readString();
        this.photos = new ArrayList<Photo>();
        in.readList(this.photos, Photo.class.getClassLoader());
        this.thumbnailPhotos = new ArrayList<Photo>();
        in.readList(this.thumbnailPhotos, Photo.class.getClassLoader());
        this.deletedPhotoIds = in.createStringArrayList();
    }

    public static final Parcelable.Creator<PSHJournal> CREATOR = new Parcelable.Creator<PSHJournal>() {
        @Override
        public PSHJournal createFromParcel(Parcel source) {
            return new PSHJournal(source);
        }

        @Override
        public PSHJournal[] newArray(int size) {
            return new PSHJournal[size];
        }
    };
}
