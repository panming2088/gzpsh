package com.augurit.agmobile.gzpssb.uploadfacility.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.liteorm.db.annotation.PrimaryKey;
import com.augurit.am.fw.db.liteorm.db.enums.AssignType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by augur on 18/2/5.
 */

public class FacilitiesBean implements Parcelable,Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private long dbid;
    /**
     * attachment : [{"affId":27,"attName":"jedwkpxpv_20180305151834_img.jpg","attPath":"http://139.159.243.185:8085/img/nw/cjImgimg/201803/20180305/jedwkpxpv_20180305151834_img.jpg","attTime":1520234314000,"id":9,"mime":"image/*","thumPath":"http://139.159.243.185:8085/img/nw/cjImgimgSmall/201803/20180305/jedwkpxpv_20180305151834_img.jpg"}]
     * createTime : 1520234845872
     * fieldName :
     * id : 27
     * markId : 55
     * name : 总
     * remark : 没
     * type : 格栅
     */
    private String problem_id; //上报内容ID
    private long createTime;
    private String fieldName;
    private Long id;
    private Long markId;
    private String name;
    private List<Photo> photos;
    private List<Photo> thumbnailPhotos;
    private String remark;
    private String type;
    private List<AttachmentEntity> attachment;
    private String attachmentIds;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(String problem_id) {
        this.problem_id = problem_id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMarkId() {
        return markId;
    }

    public void setMarkId(Long markId) {
        this.markId = markId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AttachmentEntity> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<AttachmentEntity> attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(String attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.dbid);
        dest.writeString(this.problem_id);
        dest.writeLong(this.createTime);
        dest.writeString(this.fieldName);
        dest.writeValue(this.id);
        dest.writeValue(this.markId);
        dest.writeString(this.name);
        dest.writeList(this.photos);
        dest.writeList(this.thumbnailPhotos);
        dest.writeString(this.remark);
        dest.writeString(this.type);
        dest.writeTypedList(this.attachment);
        dest.writeString(this.attachmentIds);
    }

    public FacilitiesBean() {
    }

    protected FacilitiesBean(Parcel in) {
        this.dbid = in.readLong();
        this.problem_id = in.readString();
        this.createTime = in.readLong();
        this.fieldName = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.markId = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.photos = new ArrayList<Photo>();
        in.readList(this.photos, Photo.class.getClassLoader());
        this.thumbnailPhotos = new ArrayList<Photo>();
        in.readList(this.thumbnailPhotos, Photo.class.getClassLoader());
        this.remark = in.readString();
        this.type = in.readString();
        this.attachment = in.createTypedArrayList(AttachmentEntity.CREATOR);
        this.attachmentIds = in.readString();
    }

    public static final Creator<FacilitiesBean> CREATOR = new Creator<FacilitiesBean>() {
        @Override
        public FacilitiesBean createFromParcel(Parcel source) {
            return new FacilitiesBean(source);
        }

        @Override
        public FacilitiesBean[] newArray(int size) {
            return new FacilitiesBean[size];
        }
    };
}
