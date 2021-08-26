package com.augurit.agmobile.gzps.uploadfacility.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.augurit.agmobile.gzpssb.bean.DoorNOBean;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.liteorm.db.annotation.PrimaryKey;
import com.augurit.am.fw.db.liteorm.db.enums.AssignType;
import com.augurit.am.fw.utils.ListUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

public class UploadedFacility implements Parcelable {

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
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private long dbid;
    private long time;
    private String addr;
    private String attrFive;
    private String attrFour;
    private String attrOne;
    private String attrThree;
    private String attrTwo;
    private String componentType;
    private String description;
    private String directOrgId;
    private String directOrgName;
    private String gpbh;
    private String objectId;

    private double riverx;
    private double rivery;

    private String attrSix;

    private String attrSeven;

    /**
     * 唯一标识，由服务端生成并返回
     */
    private Long id;
    private String markPerson;
    private String markPersonId;
    private Long markTime;
    private String parentOrgId;
    private String parentOrgName;
    private String road;
    private String superviseOrgId;
    private String superviseOrgName;
    private String teamOrgId;
    private String teamOrgName;
    private Long updateTime;
    private double x;
    private double y;
    /**
     *审核状态
     */
    private String checkState;
    private String checkPersonId;
    private String checkPerson;
    private String checkDesription;
    /**
     * 由于当请求图片地址后会出现顺序乱的情况，所以加入这个字段进行维护原有顺序
     */
    private int order;

    private List<Photo> photos;
    private List<Photo> thumbnailPhotos;
    private String layerUrl;
    private int layerId;
    private String layerName;
    private String usid;

    private List<DoorNOBean> mpBeen;
    private List<Photo> wellPhotos;
    private List<Photo> wellThumbnailPhotos;
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
     * 大类编码 ， 用","自行分割
     */
    @SerializedName("pcode")
    private String pCode;

    /**
     * 小类编码 ，用","自行分割
     */
    private String childCode;

    /**
     * 管理状态
     */
    private String cityVillage;

    private String tqzq; //天气
    private String pskpszt;//排水口排水状态
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

//    public Long getCheckTime() {
//        return checkTime;
//    }
//
//    public void setCheckTime(Long checkTime) {
//        this.checkTime = checkTime;
//    }

    public String getCheckDesription() {
        return checkDesription;
    }

    public void setCheckDesription(String checkDesription) {
        this.checkDesription = checkDesription;
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

    public String getDeletedPhotoIdsStr(){

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

        //return Arrays.toString(deletedPhotoIds.toArray(new String[]{})).replace("[","").replace("]","");
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

    public String getGpbh() {
        return gpbh;
    }

    public void setGpbh(String gpbh) {
        this.gpbh = gpbh;
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

    public List<DoorNOBean> getMpBeen() {
        return mpBeen;
    }

    public void setMpBeen(List<DoorNOBean> mpBeen) {
        this.mpBeen = mpBeen;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.dbid);
        dest.writeLong(this.time);
        dest.writeString(this.addr);
        dest.writeString(this.attrFive);
        dest.writeString(this.attrFour);
        dest.writeString(this.attrOne);
        dest.writeString(this.attrThree);
        dest.writeString(this.attrTwo);
        dest.writeString(this.componentType);
        dest.writeString(this.description);
        dest.writeString(this.directOrgId);
        dest.writeString(this.directOrgName);
        dest.writeString(this.gpbh);
        dest.writeString(this.objectId);
        dest.writeDouble(this.riverx);
        dest.writeDouble(this.rivery);
        dest.writeString(this.attrSix);
        dest.writeString(this.attrSeven);
        dest.writeValue(this.id);
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
        dest.writeDouble(this.x);
        dest.writeDouble(this.y);
        dest.writeString(this.checkState);
        dest.writeString(this.checkPersonId);
        dest.writeString(this.checkPerson);
        dest.writeString(this.checkDesription);
        dest.writeInt(this.order);
        dest.writeList(this.photos);
        dest.writeList(this.thumbnailPhotos);
        dest.writeString(this.layerUrl);
        dest.writeInt(this.layerId);
        dest.writeString(this.layerName);
        dest.writeString(this.usid);
        dest.writeList(this.mpBeen);
        dest.writeList(this.wellPhotos);
        dest.writeList(this.wellThumbnailPhotos);
        dest.writeInt(this.isBinding);
        dest.writeString(this.userAddr);
        dest.writeDouble(this.userLocationX);
        dest.writeDouble(this.userLocationY);
        dest.writeStringList(this.deletedPhotoIds);
        dest.writeTypedList(this.approvalOpinions);
        dest.writeString(this.pCode);
        dest.writeString(this.childCode);
        dest.writeString(this.cityVillage);
        dest.writeString(this.tqzq);
        dest.writeString(this.pskpszt);
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
    }

    public void readFromParcel(Parcel source) {
        this.dbid = source.readLong();
        this.time = source.readLong();
        this.addr = source.readString();
        this.attrFive = source.readString();
        this.attrFour = source.readString();
        this.attrOne = source.readString();
        this.attrThree = source.readString();
        this.attrTwo = source.readString();
        this.componentType = source.readString();
        this.description = source.readString();
        this.directOrgId = source.readString();
        this.directOrgName = source.readString();
        this.gpbh = source.readString();
        this.objectId = source.readString();
        this.riverx = source.readDouble();
        this.rivery = source.readDouble();
        this.attrSix = source.readString();
        this.attrSeven = source.readString();
        this.id = (Long) source.readValue(Long.class.getClassLoader());
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
        this.x = source.readDouble();
        this.y = source.readDouble();
        this.checkState = source.readString();
        this.checkPersonId = source.readString();
        this.checkPerson = source.readString();
        this.checkDesription = source.readString();
        this.order = source.readInt();
        this.photos = new ArrayList<Photo>();
        source.readList(this.photos, Photo.class.getClassLoader());
        this.thumbnailPhotos = new ArrayList<Photo>();
        source.readList(this.thumbnailPhotos, Photo.class.getClassLoader());
        this.layerUrl = source.readString();
        this.layerId = source.readInt();
        this.layerName = source.readString();
        this.usid = source.readString();
        this.mpBeen = new ArrayList<DoorNOBean>();
        source.readList(this.mpBeen, DoorNOBean.class.getClassLoader());
        this.wellPhotos = new ArrayList<Photo>();
        source.readList(this.wellPhotos, Photo.class.getClassLoader());
        this.wellThumbnailPhotos = new ArrayList<Photo>();
        source.readList(this.wellThumbnailPhotos, Photo.class.getClassLoader());
        this.isBinding = source.readInt();
        this.userAddr = source.readString();
        this.userLocationX = source.readDouble();
        this.userLocationY = source.readDouble();
        this.deletedPhotoIds = source.createStringArrayList();
        this.approvalOpinions = source.createTypedArrayList(ApprovalOpinion.CREATOR);
        this.pCode = source.readString();
        this.childCode = source.readString();
        this.cityVillage = source.readString();
        this.tqzq = source.readString();
        this.pskpszt = source.readString();
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
    }

    public UploadedFacility() {
    }

    protected UploadedFacility(Parcel in) {
        this.dbid = in.readLong();
        this.time = in.readLong();
        this.addr = in.readString();
        this.attrFive = in.readString();
        this.attrFour = in.readString();
        this.attrOne = in.readString();
        this.attrThree = in.readString();
        this.attrTwo = in.readString();
        this.componentType = in.readString();
        this.description = in.readString();
        this.directOrgId = in.readString();
        this.directOrgName = in.readString();
        this.gpbh = in.readString();
        this.objectId = in.readString();
        this.riverx = in.readDouble();
        this.rivery = in.readDouble();
        this.attrSix = in.readString();
        this.attrSeven = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
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
        this.x = in.readDouble();
        this.y = in.readDouble();
        this.checkState = in.readString();
        this.checkPersonId = in.readString();
        this.checkPerson = in.readString();
        this.checkDesription = in.readString();
        this.order = in.readInt();
        this.photos = new ArrayList<Photo>();
        in.readList(this.photos, Photo.class.getClassLoader());
        this.thumbnailPhotos = new ArrayList<Photo>();
        in.readList(this.thumbnailPhotos, Photo.class.getClassLoader());
        this.layerUrl = in.readString();
        this.layerId = in.readInt();
        this.layerName = in.readString();
        this.usid = in.readString();
        this.mpBeen = new ArrayList<DoorNOBean>();
        in.readList(this.mpBeen, DoorNOBean.class.getClassLoader());
        this.wellPhotos = new ArrayList<Photo>();
        in.readList(this.wellPhotos, Photo.class.getClassLoader());
        this.wellThumbnailPhotos = new ArrayList<Photo>();
        in.readList(this.wellThumbnailPhotos, Photo.class.getClassLoader());
        this.isBinding = in.readInt();
        this.userAddr = in.readString();
        this.userLocationX = in.readDouble();
        this.userLocationY = in.readDouble();
        this.deletedPhotoIds = in.createStringArrayList();
        this.approvalOpinions = in.createTypedArrayList(ApprovalOpinion.CREATOR);
        this.pCode = in.readString();
        this.childCode = in.readString();
        this.cityVillage = in.readString();
        this.tqzq = in.readString();
        this.pskpszt = in.readString();
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
    }

    public static final Parcelable.Creator<UploadedFacility> CREATOR = new Parcelable.Creator<UploadedFacility>() {
        @Override
        public UploadedFacility createFromParcel(Parcel source) {
            return new UploadedFacility(source);
        }

        @Override
        public UploadedFacility[] newArray(int size) {
            return new UploadedFacility[size];
        }
    };
}
