package com.augurit.agmobile.gzpssb.fire.util;

import com.augurit.agmobile.gzpssb.fire.model.GroundfireBean;
import com.augurit.agmobile.gzpssb.monitor.model.WellMonitorInfo;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;

public class Attachment2PhotoUtil {
public  final  static String PSH_TYPE = "1" +
        "" +
        "";
public  final  static String FIRE_TYPE = "9";

    public static List<Photo> toPhotos(List<GroundfireBean.HasCert8AttachmentBean> hasCert8Attachment){
        ArrayList<Photo>photos = new ArrayList<>();
        if (!ListUtil.isEmpty(hasCert8Attachment)) {
            for (GroundfireBean.HasCert8AttachmentBean attachmentBean : hasCert8Attachment) {
                Photo photo = new Photo();
                photo.setId(attachmentBean.getId());
                photo.setPhotoName(attachmentBean.getAttName());
                photo.setHasBeenUp(1);
                photo.setPhotoPath(attachmentBean.getAttPath());
                photo.setThumbPath(attachmentBean.getThumPath());
                photo.setField1("photo");
                photos.add(photo);
            }
        }
        return  photos;
    }
    public static List<Photo> toThumb(List<GroundfireBean.HasCert8AttachmentBean> hasCert8Attachment){
        ArrayList<Photo>photos = new ArrayList<>();
        if (!ListUtil.isEmpty(hasCert8Attachment)) {
            for (GroundfireBean.HasCert8AttachmentBean attachmentBean : hasCert8Attachment) {
                Photo photo = new Photo();
                photo.setId(attachmentBean.getId());
                photo.setPhotoName(attachmentBean.getAttName());
                photo.setHasBeenUp(1);
                photo.setPhotoPath(attachmentBean.getThumPath());
                photo.setField1("photo");
                photos.add(photo);
            }
        }
        return  photos;
    }

    public static List<Photo> toWellPhotos(List<WellMonitorInfo.HasCertAttachmentBean> hasCert8Attachment){
        ArrayList<Photo>photos = new ArrayList<>();
        if (!ListUtil.isEmpty(hasCert8Attachment)) {
            for (WellMonitorInfo.HasCertAttachmentBean attachmentBean : hasCert8Attachment) {
                Photo photo = new Photo();
                photo.setId(attachmentBean.getId());
                photo.setPhotoName(attachmentBean.getAttName());
                photo.setHasBeenUp(1);
                photo.setPhotoPath(attachmentBean.getAttPath());
                photo.setThumbPath(attachmentBean.getThumPath());
                photo.setField1("photo");
                photos.add(photo);
            }
        }
        return  photos;
    }
}
