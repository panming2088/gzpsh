package com.augurit.agmobile.gzps.uploadfacility.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.augurit.agmobile.gzpssb.bean.DoorNOBean;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.utils.ListUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 设施纠错实体类
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.identification.model
 * @createTime 创建时间 ：17/11/3
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/11/3
 * @modifyMemo 修改备注：
 */

public class ModifiedFacility implements Parcelable {

    /**
     * 标识地址，纠错种类包含“位置错误”时上报，种类2和4
     */
    private String originAddr;
    private String addr;
    private String attrFive;
    private String attrFour;
    private String attrOne;
    private String attrThree;
    private String attrTwo;
    private String objectId;

    private String originAttrOne;
    private String originAttrTwo;
    private String originAttrThree;
    private String originAttrFour;
    private String originAttrFive;
    private String originAttrSix;
    private String originAttrSeven;
    private String teamMember;

    private String originRoad;

    /**
     * 修正后的地址（当标识位置错误时上传）
     */
    private double x;
    private double y;
    /**
     * 修正类型，包括：位置错误，信息错误，位置和信息错误，设施错误
     */
    private String correctType;
    /**
     * 上报类型  "conform": 数据确认; "modify" : 数据纠错
     */
    private String reportType;
    private String description;
    /**
     *审核状态
     */
    private String checkState;
    private String checkPersonId;
    private String checkPerson;
    private String checkDesription;

    /**
     * 直属机构id
     */
    private String directOrgId;
    private String directOrgName;
    /**
     * 唯一标识，由服务端生成并返回
     */
    private Long id;
    private String layerName;
    private String layerUrl;
    private String markPerson;
    private String markPersonId;
    /**
     * 首次上传时间
     */
    private Long markTime;
    private String parentOrgId;
    private String parentOrgName;
    private String road;
    /**
     * 监管机构
     */
    private String superviseOrgId;
    private String superviseOrgName;
    private String teamOrgId;
    private String teamOrgName;
    /**
     * 最近更新时间
     */
    private Long updateTime;
    /**
     * 设施原始地址
     */
    @SerializedName("orginX")
    private double originX;

    @SerializedName("orginY")
    private double originY;
    /**
    * 由于当请求图片地址后会出现顺序乱的情况，所以加入这个字段进行维护原有顺序
    */
    private int order;
    private List<Photo> photos;
    private List<Photo> thumbnailPhotos;
    private List<Photo> wellPhotos;
    private List<Photo> wellThumbnailPhotos;
    private List<DoorNOBean> mpBeen;
    private double riverx;
    private double rivery;

    private String attrSix;

    private String attrSeven;

    /**
     * 设施的唯一标识（跟id不一样，id 是oracle数据库中的唯一标识，usid 是设施在arcgis服务中的唯一标识）
     */
    private String usid;

    /**
     * 用户当前所处的位置
     */
    private String userAddr;

    /**
     * 用户当前位置的经度
     */
    private double userX;

    /**
     * 用户当前位置的纬度
     */
    private double userY;


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
     * 大类编码 ， 用","自行分割
     */
    @SerializedName("pcode")
    private String pCode;

    /**
     * 小类编码 ，用","自行分割
     */
    private String childCode;

    private String cityVillage;

    private String tqzq; //天气
    private String pskpszt;//排水口排水状态
    private String gpbh;//有无挂牌
    private String clff;//现场测流方法
    private String cljg;//测流结果
    private String ph;//ph值
    private String adnd;//氮氨浓度
    private String videoPath;
    private String videoId;

    private String sfgjjd;//是否关键节点
    private String gjjdBh;//关键节点编号
    private String gjjdZrr;//责任人
    private String gjjdLxdh;//联系电话

    private String yjbh;//窨井编号
    private double wellLength;
    private Long trackId;
    private boolean isAllowEditWellType;

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public String getOriginAddr() {
        return originAddr;
    }

    public void setOriginAddr(String originAddr) {
        this.originAddr = originAddr;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAttrFive() {
        return attrFive;
    }

    public void setAttrFive(String attrFive) {
        this.attrFive = attrFive;
    }

    public String getAttrFour() {
        return attrFour;
    }

    public void setAttrFour(String attrFour) {
        this.attrFour = attrFour;
    }

    public String getAttrOne() {
        return attrOne;
    }

    public void setAttrOne(String attrOne) {
        this.attrOne = attrOne;
    }

    public String getAttrThree() {
        return attrThree;
    }

    public void setAttrThree(String attrThree) {
        this.attrThree = attrThree;
    }

    public String getAttrTwo() {
        return attrTwo;
    }

    public void setAttrTwo(String attrTwo) {
        this.attrTwo = attrTwo;
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

    public String getCorrectType() {
        return correctType;
    }

    public void setCorrectType(String correctType) {
        this.correctType = correctType;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getLayerUrl() {
        return layerUrl;
    }

    public void setLayerUrl(String layerUrl) {
        this.layerUrl = layerUrl;
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

    public Long getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Long markTime) {
        this.markTime = markTime;
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

    public String getSuperviseOrgId() {
        return superviseOrgId;
    }

    public void setSuperviseOrgId(String superviseOrgId) {
        this.superviseOrgId = superviseOrgId;
    }

    public String getSuperviseOrgName() {
        return superviseOrgName;
    }

    public void setSuperviseOrgName(String superviseOrgName) {
        this.superviseOrgName = superviseOrgName;
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

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public double getOriginX() {
        return originX;
    }

    public void setOriginX(double originX) {
        this.originX = originX;
    }

    public double getOriginY() {
        return originY;
    }

    public void setOriginY(double originY) {
        this.originY = originY;
    }

    public double getUserX() {
        return userX;
    }

    public void setUserX(double userX) {
        this.userX = userX;
    }

    public double getUserY() {
        return userY;
    }

    public void setUserY(double userY) {
        this.userY = userY;
    }

    public String getOriginAttrOne() {
        return originAttrOne;
    }

    public void setOriginAttrOne(String originAttrOne) {
        this.originAttrOne = originAttrOne;
    }

    public String getOriginAttrTwo() {
        return originAttrTwo;
    }

    public void setOriginAttrTwo(String originAttrTwo) {
        this.originAttrTwo = originAttrTwo;
    }

    public String getOriginAttrThree() {
        return originAttrThree;
    }

    public void setOriginAttrThree(String originAttrThree) {
        this.originAttrThree = originAttrThree;
    }

    public String getOriginAttrFour() {
        return originAttrFour;
    }

    public void setOriginAttrFour(String originAttrFour) {
        this.originAttrFour = originAttrFour;
    }

    public String getOriginAttrFive() {
        return originAttrFive;
    }

    public void setOriginAttrFive(String originAttrFive) {
        this.originAttrFive = originAttrFive;
    }

    public String getOriginRoad() {
        return originRoad;
    }

    public void setOriginRoad(String originRoad) {
        this.originRoad = originRoad;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }


    public List<ApprovalOpinion> getApprovalOpinions() {
        return approvalOpinions;
    }

    public void setApprovalOpinions(List<ApprovalOpinion> approvalOpinions) {
        this.approvalOpinions = approvalOpinions;
    }

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

        //return Arrays.toString(deletedPhotoIds.toArray(new String[]{})).replace("[","").replace("]","").replace(" ","");
    }

    public void setDeletedPhotoIds(List<String> deletedPhotoIds) {
        this.deletedPhotoIds = deletedPhotoIds;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }


    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public String getCityVillage() {
        return cityVillage;
    }

    public void setCityVillage(String cityVillage) {
        this.cityVillage = cityVillage;
    }

    public double getRiverx() {
        return riverx;
    }

    public void setRiverx(double riverx) {
        this.riverx = riverx;
    }

    public double getRivery() {
        return rivery;
    }

    public void setRivery(double rivery) {
        this.rivery = rivery;
    }

    public String getAttrSix() {
        return attrSix;
    }

    public void setAttrSix(String attrSix) {
        this.attrSix = attrSix;
    }

    public String getAttrSeven() {
        return attrSeven;
    }

    public void setAttrSeven(String attrSeven) {
        this.attrSeven = attrSeven;
    }

    public List<Photo> getWellPhotos() {
        return wellPhotos;
    }

    public void setWellPhotos(List<Photo> wellPhotos) {
        this.wellPhotos = wellPhotos;
    }

    public List<Photo> getWellThumbnailPhotos() {
        return wellThumbnailPhotos;
    }

    public void setWellThumbnailPhotos(List<Photo> wellThumbnailPhotos) {
        this.wellThumbnailPhotos = wellThumbnailPhotos;
    }

    public List<DoorNOBean> getMpBeen() {
        return mpBeen;
    }

    public void setMpBeen(List<DoorNOBean> mpBeen) {
        this.mpBeen = mpBeen;
    }

    public String getTqzq() {
        return tqzq;
    }

    public void setTqzq(String tqzq) {
        this.tqzq = tqzq;
    }

    public String getPskpszt() {
        return pskpszt;
    }

    public void setPskpszt(String pskpszt) {
        this.pskpszt = pskpszt;
    }

    public String getGpbh() {
        return gpbh;
    }

    public void setGpbh(String gpbh) {
        this.gpbh = gpbh;
    }

    public String getClff() {
        return clff;
    }

    public void setClff(String clff) {
        this.clff = clff;
    }

    public String getCljg() {
        return cljg;
    }

    public void setCljg(String cljg) {
        this.cljg = cljg;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getAdnd() {
        return adnd;
    }

    public void setAdnd(String adnd) {
        this.adnd = adnd;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getSfgjjd() {
        return sfgjjd;
    }

    public void setSfgjjd(String sfgjjd) {
        this.sfgjjd = sfgjjd;
    }

    public String getGjjdBh() {
        return gjjdBh;
    }

    public void setGjjdBh(String gjjdBh) {
        this.gjjdBh = gjjdBh;
    }

    public String getGjjdZrr() {
        return gjjdZrr;
    }

    public void setGjjdZrr(String gjjdZrr) {
        this.gjjdZrr = gjjdZrr;
    }

    public String getGjjdLxdh() {
        return gjjdLxdh;
    }

    public void setGjjdLxdh(String gjjdLxdh) {
        this.gjjdLxdh = gjjdLxdh;
    }

    public String getYjbh() {
        return yjbh;
    }

    public void setYjbh(String yjbh) {
        this.yjbh = yjbh;
    }

    public String getOriginAttrSix() {
        return originAttrSix;
    }

    public void setOriginAttrSix(String originAttrSix) {
        this.originAttrSix = originAttrSix;
    }

    public String getOriginAttrSeven() {
        return originAttrSeven;
    }

    public void setOriginAttrSeven(String originAttrSeven) {
        this.originAttrSeven = originAttrSeven;
    }

    public String getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(String teamMember) {
        this.teamMember = teamMember;
    }

    public double getWellLength() {
        return wellLength;
    }

    public void setWellLength(double wellLength) {
        this.wellLength = wellLength;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public boolean isAllowEditWellType() {
        return isAllowEditWellType;
    }

    public void setAllowEditWellType(boolean allowEditWellType) {
        isAllowEditWellType = allowEditWellType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originAddr);
        dest.writeString(this.addr);
        dest.writeString(this.attrFive);
        dest.writeString(this.attrFour);
        dest.writeString(this.attrOne);
        dest.writeString(this.attrThree);
        dest.writeString(this.attrTwo);
        dest.writeString(this.objectId);
        dest.writeString(this.originAttrOne);
        dest.writeString(this.originAttrTwo);
        dest.writeString(this.originAttrThree);
        dest.writeString(this.originAttrFour);
        dest.writeString(this.originAttrFive);
        dest.writeString(this.originAttrSix);
        dest.writeString(this.originAttrSeven);
        dest.writeString(this.teamMember);
        dest.writeString(this.originRoad);
        dest.writeDouble(this.x);
        dest.writeDouble(this.y);
        dest.writeString(this.correctType);
        dest.writeString(this.reportType);
        dest.writeString(this.description);
        dest.writeString(this.checkState);
        dest.writeString(this.checkPersonId);
        dest.writeString(this.checkPerson);
        dest.writeString(this.checkDesription);
        dest.writeString(this.directOrgId);
        dest.writeString(this.directOrgName);
        dest.writeValue(this.id);
        dest.writeString(this.layerName);
        dest.writeString(this.layerUrl);
        dest.writeString(this.markPerson);
        dest.writeString(this.markPersonId);
        dest.writeValue(this.markTime);
        dest.writeString(this.parentOrgId);
        dest.writeString(this.parentOrgName);
        dest.writeString(this.road);
        dest.writeString(this.superviseOrgId);
        dest.writeString(this.superviseOrgName);
        dest.writeString(this.teamOrgId);
        dest.writeString(this.teamOrgName);
        dest.writeValue(this.updateTime);
        dest.writeDouble(this.originX);
        dest.writeDouble(this.originY);
        dest.writeInt(this.order);
        dest.writeList(this.photos);
        dest.writeList(this.thumbnailPhotos);
        dest.writeList(this.wellPhotos);
        dest.writeList(this.wellThumbnailPhotos);
        dest.writeList(this.mpBeen);
        dest.writeDouble(this.riverx);
        dest.writeDouble(this.rivery);
        dest.writeString(this.attrSix);
        dest.writeString(this.attrSeven);
        dest.writeString(this.usid);
        dest.writeString(this.userAddr);
        dest.writeDouble(this.userX);
        dest.writeDouble(this.userY);
        dest.writeStringList(this.deletedPhotoIds);
        dest.writeTypedList(this.approvalOpinions);
        dest.writeString(this.pCode);
        dest.writeString(this.childCode);
        dest.writeString(this.cityVillage);
        dest.writeString(this.tqzq);
        dest.writeString(this.pskpszt);
        dest.writeString(this.gpbh);
        dest.writeString(this.clff);
        dest.writeString(this.cljg);
        dest.writeString(this.ph);
        dest.writeString(this.adnd);
        dest.writeString(this.videoPath);
        dest.writeString(this.videoId);
        dest.writeString(this.sfgjjd);
        dest.writeString(this.gjjdBh);
        dest.writeString(this.gjjdZrr);
        dest.writeString(this.gjjdLxdh);
        dest.writeString(this.yjbh);
        dest.writeDouble(this.wellLength);
        dest.writeValue(this.trackId);
        dest.writeByte(this.isAllowEditWellType ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.originAddr = source.readString();
        this.addr = source.readString();
        this.attrFive = source.readString();
        this.attrFour = source.readString();
        this.attrOne = source.readString();
        this.attrThree = source.readString();
        this.attrTwo = source.readString();
        this.objectId = source.readString();
        this.originAttrOne = source.readString();
        this.originAttrTwo = source.readString();
        this.originAttrThree = source.readString();
        this.originAttrFour = source.readString();
        this.originAttrFive = source.readString();
        this.originAttrSix = source.readString();
        this.originAttrSeven = source.readString();
        this.teamMember = source.readString();
        this.originRoad = source.readString();
        this.x = source.readDouble();
        this.y = source.readDouble();
        this.correctType = source.readString();
        this.reportType = source.readString();
        this.description = source.readString();
        this.checkState = source.readString();
        this.checkPersonId = source.readString();
        this.checkPerson = source.readString();
        this.checkDesription = source.readString();
        this.directOrgId = source.readString();
        this.directOrgName = source.readString();
        this.id = (Long) source.readValue(Long.class.getClassLoader());
        this.layerName = source.readString();
        this.layerUrl = source.readString();
        this.markPerson = source.readString();
        this.markPersonId = source.readString();
        this.markTime = (Long) source.readValue(Long.class.getClassLoader());
        this.parentOrgId = source.readString();
        this.parentOrgName = source.readString();
        this.road = source.readString();
        this.superviseOrgId = source.readString();
        this.superviseOrgName = source.readString();
        this.teamOrgId = source.readString();
        this.teamOrgName = source.readString();
        this.updateTime = (Long) source.readValue(Long.class.getClassLoader());
        this.originX = source.readDouble();
        this.originY = source.readDouble();
        this.order = source.readInt();
        this.photos = new ArrayList<Photo>();
        source.readList(this.photos, Photo.class.getClassLoader());
        this.thumbnailPhotos = new ArrayList<Photo>();
        source.readList(this.thumbnailPhotos, Photo.class.getClassLoader());
        this.wellPhotos = new ArrayList<Photo>();
        source.readList(this.wellPhotos, Photo.class.getClassLoader());
        this.wellThumbnailPhotos = new ArrayList<Photo>();
        source.readList(this.wellThumbnailPhotos, Photo.class.getClassLoader());
        this.mpBeen = new ArrayList<DoorNOBean>();
        source.readList(this.mpBeen, DoorNOBean.class.getClassLoader());
        this.riverx = source.readDouble();
        this.rivery = source.readDouble();
        this.attrSix = source.readString();
        this.attrSeven = source.readString();
        this.usid = source.readString();
        this.userAddr = source.readString();
        this.userX = source.readDouble();
        this.userY = source.readDouble();
        this.deletedPhotoIds = source.createStringArrayList();
        this.approvalOpinions = source.createTypedArrayList(ApprovalOpinion.CREATOR);
        this.pCode = source.readString();
        this.childCode = source.readString();
        this.cityVillage = source.readString();
        this.tqzq = source.readString();
        this.pskpszt = source.readString();
        this.gpbh = source.readString();
        this.clff = source.readString();
        this.cljg = source.readString();
        this.ph = source.readString();
        this.adnd = source.readString();
        this.videoPath = source.readString();
        this.videoId = source.readString();
        this.sfgjjd = source.readString();
        this.gjjdBh = source.readString();
        this.gjjdZrr = source.readString();
        this.gjjdLxdh = source.readString();
        this.yjbh = source.readString();
        this.wellLength = source.readDouble();
        this.trackId = (Long) source.readValue(Long.class.getClassLoader());
        this.isAllowEditWellType = source.readByte() != 0;
    }

    public ModifiedFacility() {
    }

    protected ModifiedFacility(Parcel in) {
        this.originAddr = in.readString();
        this.addr = in.readString();
        this.attrFive = in.readString();
        this.attrFour = in.readString();
        this.attrOne = in.readString();
        this.attrThree = in.readString();
        this.attrTwo = in.readString();
        this.objectId = in.readString();
        this.originAttrOne = in.readString();
        this.originAttrTwo = in.readString();
        this.originAttrThree = in.readString();
        this.originAttrFour = in.readString();
        this.originAttrFive = in.readString();
        this.originAttrSix = in.readString();
        this.originAttrSeven = in.readString();
        this.teamMember = in.readString();
        this.originRoad = in.readString();
        this.x = in.readDouble();
        this.y = in.readDouble();
        this.correctType = in.readString();
        this.reportType = in.readString();
        this.description = in.readString();
        this.checkState = in.readString();
        this.checkPersonId = in.readString();
        this.checkPerson = in.readString();
        this.checkDesription = in.readString();
        this.directOrgId = in.readString();
        this.directOrgName = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.layerName = in.readString();
        this.layerUrl = in.readString();
        this.markPerson = in.readString();
        this.markPersonId = in.readString();
        this.markTime = (Long) in.readValue(Long.class.getClassLoader());
        this.parentOrgId = in.readString();
        this.parentOrgName = in.readString();
        this.road = in.readString();
        this.superviseOrgId = in.readString();
        this.superviseOrgName = in.readString();
        this.teamOrgId = in.readString();
        this.teamOrgName = in.readString();
        this.updateTime = (Long) in.readValue(Long.class.getClassLoader());
        this.originX = in.readDouble();
        this.originY = in.readDouble();
        this.order = in.readInt();
        this.photos = new ArrayList<Photo>();
        in.readList(this.photos, Photo.class.getClassLoader());
        this.thumbnailPhotos = new ArrayList<Photo>();
        in.readList(this.thumbnailPhotos, Photo.class.getClassLoader());
        this.wellPhotos = new ArrayList<Photo>();
        in.readList(this.wellPhotos, Photo.class.getClassLoader());
        this.wellThumbnailPhotos = new ArrayList<Photo>();
        in.readList(this.wellThumbnailPhotos, Photo.class.getClassLoader());
        this.mpBeen = new ArrayList<DoorNOBean>();
        in.readList(this.mpBeen, DoorNOBean.class.getClassLoader());
        this.riverx = in.readDouble();
        this.rivery = in.readDouble();
        this.attrSix = in.readString();
        this.attrSeven = in.readString();
        this.usid = in.readString();
        this.userAddr = in.readString();
        this.userX = in.readDouble();
        this.userY = in.readDouble();
        this.deletedPhotoIds = in.createStringArrayList();
        this.approvalOpinions = in.createTypedArrayList(ApprovalOpinion.CREATOR);
        this.pCode = in.readString();
        this.childCode = in.readString();
        this.cityVillage = in.readString();
        this.tqzq = in.readString();
        this.pskpszt = in.readString();
        this.gpbh = in.readString();
        this.clff = in.readString();
        this.cljg = in.readString();
        this.ph = in.readString();
        this.adnd = in.readString();
        this.videoPath = in.readString();
        this.videoId = in.readString();
        this.sfgjjd = in.readString();
        this.gjjdBh = in.readString();
        this.gjjdZrr = in.readString();
        this.gjjdLxdh = in.readString();
        this.yjbh = in.readString();
        this.wellLength = in.readDouble();
        this.trackId = (Long) in.readValue(Long.class.getClassLoader());
        this.isAllowEditWellType = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ModifiedFacility> CREATOR = new Parcelable.Creator<ModifiedFacility>() {
        @Override
        public ModifiedFacility createFromParcel(Parcel source) {
            return new ModifiedFacility(source);
        }

        @Override
        public ModifiedFacility[] newArray(int size) {
            return new ModifiedFacility[size];
        }
    };
}
