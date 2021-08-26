package com.augurit.agmobile.gzps.publicaffair.util;

import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.publicaffair.model.UploadFacilityDetail;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcl on 2017/11/17.
 */

public class UploadFacilityUtil {

    /**
     *
     * @param uploadFacilityDetail
     * @return
     */
    public static UploadedFacility getUploadedFacility(UploadFacilityDetail uploadFacilityDetail){

        UploadedFacility data = uploadFacilityDetail.getData();

        List<ServerAttachment.ServerAttachmentDataBean> rows = uploadFacilityDetail.getRows();
        if(!ListUtil.isEmpty(rows)){
            List<Photo> photos = new ArrayList<>();
            for (ServerAttachment.ServerAttachmentDataBean dataBean2 : rows){
                Photo photo = new Photo();
                photo.setPhotoName(dataBean2.getAttName());
                photo.setId(Long.valueOf(dataBean2.getId()));
                photo.setPhotoPath(dataBean2.getAttPath());
                photo.setThumbPath(dataBean2.getThumPath());
                photos.add(photo);
            }
            data.setPhotos(photos);
        }

        return data;
    }
}
